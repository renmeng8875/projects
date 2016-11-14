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
package com.yy.ent.mobile.thrift.client.pool;

import java.util.concurrent.TimeUnit;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author Tingkun Zhang
 */
public class ThriftConnectionFactory implements KeyedPooledObjectFactory<ThriftServerInfo, TTransport> {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DefaultThriftConnectionPool.class);

	private int timeout;

	public ThriftConnectionFactory(int timeout) {
		this.timeout = timeout;
	}

	public ThriftConnectionFactory() {
		this((int) TimeUnit.SECONDS.toMillis(5));
	}

	@Override
	public PooledObject<TTransport> makeObject(ThriftServerInfo info) throws Exception {
		TSocket transport = new TSocket(info.getHost(), info.getPort());
		//这里我去掉了两行,在demo中用frameTransport调不通
		//tsocket.setTimeout(timeout);
		//TFramedTransport transport = new TFramedTransport(tsocket);

		transport.open();
		DefaultPooledObject<TTransport> result = new DefaultPooledObject<TTransport>(transport);
		logger.trace("make new thrift connection:{}", info);
		return result;
	}

	@Override
	public void destroyObject(ThriftServerInfo info, PooledObject<TTransport> p) throws Exception {
		TTransport transport = p.getObject();
		if (transport != null) {
			transport.close();
			logger.trace("close thrift connection:{}", info);
		}
	}

	@Override
	public boolean validateObject(ThriftServerInfo info, PooledObject<TTransport> p) {
		try {
			return p.getObject().isOpen();
		} catch (Throwable e) {
			logger.warn("fail to validate tsocket:{}", info, e);
			return false;
		}
	}

	@Override
	public void activateObject(ThriftServerInfo info, PooledObject<TTransport> p) throws Exception {
		// do nothing
	}

	@Override
	public void passivateObject(ThriftServerInfo info, PooledObject<TTransport> p) throws Exception {
		// do nothing
	}

}
