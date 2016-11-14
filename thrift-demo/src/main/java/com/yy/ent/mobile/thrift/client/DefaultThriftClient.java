/*
 * Copyright (c) 2014 yy.com. 
 *
 * All Rights Reserved.
 *
 * This program is the confidential and proprietary information of 
 * YY.INC. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with yy.com.
 */
package com.yy.ent.mobile.thrift.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.yy.ent.mobile.thrift.client.failover.FailoverChecker;
import com.yy.ent.mobile.thrift.client.pool.ThriftConnectionPool;
import com.yy.ent.mobile.thrift.client.pool.ThriftServerInfo;
import com.yy.ent.mobile.thrift.client.utils.ThriftClientUtils;

/**
 * @author Tingkun Zhang
 */
public class DefaultThriftClient implements ThriftClient {

	private AtomicInteger i = new AtomicInteger(0);

	private FailoverChecker failoverChecker;
	private ThriftConnectionPool poolProvider;

	public DefaultThriftClient(FailoverChecker failoverChecker, ThriftConnectionPool poolProvider, String servers) {
		this(failoverChecker, poolProvider, servers, null);
	}

	public DefaultThriftClient(FailoverChecker failoverChecker, ThriftConnectionPool poolProvider, String servers, String backupServers) {
		this.poolProvider = poolProvider;
		this.failoverChecker = failoverChecker;
		failoverChecker.setConnectionPool(poolProvider);
		failoverChecker.setServerInfoList(ThriftServerInfo.ofs(servers));
		if (StringUtils.isNotEmpty(backupServers))
			failoverChecker.setBackupServerInfoList(ThriftServerInfo.ofs(backupServers));
		else
			failoverChecker.setBackupServerInfoList(new ArrayList<ThriftServerInfo>());
		failoverChecker.startChecking();
	}

	@Override
	public <X extends TServiceClient> X iface(Class<X> ifaceClass) {
		return iface(ifaceClass, i.getAndDecrement());
	}

	protected <X extends TServiceClient> X iface(Class<X> ifaceClass, int hash) {

		List<ThriftServerInfo> servers = getAvaliableServers();
		if (servers == null || servers.isEmpty()) {
			throw new NullPointerException("servers could not be null");
		}
		hash = Math.abs(hash);
		final ThriftServerInfo selected = servers.get(hash % servers.size());

		return iface(ifaceClass, selected);
	}

	@SuppressWarnings("unchecked")
	protected <X extends TServiceClient> X iface(final Class<X> ifaceClass, final ThriftServerInfo selected) {
		final TTransport transport;
		try {
			transport = poolProvider.getConnection(selected);
		} catch (RuntimeException e) {
			if (e.getCause() != null && e.getCause() instanceof TTransportException)
				failoverChecker.getFailoverCheckingStrategy().fail(selected);
			throw e;
		}
		TProtocol protocol = new TBinaryProtocol(transport);

		ProxyFactory factory = new ProxyFactory();
		factory.setSuperclass(ifaceClass);
		factory.setFilter(new MethodFilter() {
			@Override
			public boolean isHandled(Method m) {
				return ThriftClientUtils.getInterfaceMethodNames(ifaceClass).contains(m.getName());
			}
		});
		try {
			X x = (X) factory.create(new Class[] { org.apache.thrift.protocol.TProtocol.class }, new Object[] { protocol });
			((Proxy) x).setHandler(new MethodHandler() {
				@Override
				public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {

					boolean success = false;
					try {
						Object result = proceed.invoke(self, args);
						success = true;
						return result;
					} finally {
						if (success) {
							poolProvider.returnConnection(selected, transport);
						} else {
							failoverChecker.getFailoverCheckingStrategy().fail(selected);
							poolProvider.returnBrokenConnection(selected, transport);
						}
					}
				}
			});
			return x;
		} catch (NoSuchMethodException | IllegalArgumentException | InstantiationException | IllegalAccessException
				| InvocationTargetException e) {
			throw new RuntimeException("fail to create proxy.", e);
		}
	}

	@Override
	public void close() {
		failoverChecker.stopChecking();
		poolProvider.close();
	}

	@Override
	public List<ThriftServerInfo> getAvaliableServers() {
		return failoverChecker.getAvailableServers();
	}

}
