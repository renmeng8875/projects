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
package com.yy.ent.mobile.thrift.client.failover;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.transport.TTransport;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.yy.ent.mobile.thrift.client.pool.ThriftConnectionPool;
import com.yy.ent.mobile.thrift.client.pool.ThriftServerInfo;

/**
 * @author Tingkun Zhang
 */
public class FailoverChecker {

	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	private List<ThriftServerInfo> serverInfoList;

	private List<ThriftServerInfo> backupServerInfoList;

	private FailoverCheckingStrategy<ThriftServerInfo> failoverCheckingStrategy;

	private ThriftConnectionPool poolProvider;

	private ConnectionValidator connectionValidator;

	private ScheduledExecutorService checkExecutor;

	public FailoverChecker(ConnectionValidator connectionValidator) {
		this(connectionValidator, new FailoverCheckingStrategy<ThriftServerInfo>());
	}

	public FailoverChecker(ConnectionValidator connectionValidator, FailoverCheckingStrategy<ThriftServerInfo> failoverCheckingStrategy) {
		this.connectionValidator = connectionValidator;
		this.failoverCheckingStrategy = failoverCheckingStrategy;
	}

	public void startChecking() {
		if (connectionValidator != null) {
			ThreadFactory bossThreadFactory = new ThreadFactoryBuilder().setNameFormat("Fail Checking Worker").build();
			checkExecutor = Executors.newSingleThreadScheduledExecutor(bossThreadFactory);
			checkExecutor.scheduleAtFixedRate(new Checker(), 10000, 5000, TimeUnit.MILLISECONDS);
		}
	}

	public void stopChecking() {
		if (checkExecutor != null)
			checkExecutor.shutdown();

	}

	public List<ThriftServerInfo> getAvailableServers() {
		return getAvailableServers(false);
	}

	private List<ThriftServerInfo> getAvailableServers(boolean all) {
		List<ThriftServerInfo> returnList = new ArrayList<ThriftServerInfo>();
		Set<ThriftServerInfo> failedServers = failoverCheckingStrategy.getFailed();
		for (ThriftServerInfo thriftServerInfo : serverInfoList) {
			if (!failedServers.contains(thriftServerInfo))
				returnList.add(thriftServerInfo);
		}
		if ((all || returnList.isEmpty()) && !backupServerInfoList.isEmpty()) {
			for (ThriftServerInfo thriftServerInfo : backupServerInfoList) {
				if (!failedServers.contains(thriftServerInfo))
					returnList.add(thriftServerInfo);
			}
		}
		return returnList;
	}

	private class Checker implements Runnable {
		@Override
		public void run() {
			for (ThriftServerInfo thriftServerInfo : getAvailableServers(true)) {
				TTransport tt = null;
				boolean valid = false;
				try {
					tt = poolProvider.getConnection(thriftServerInfo);
					valid = connectionValidator.isValid(tt);
				} catch (Exception e) {
					valid = false;
					logger.warn(e.getMessage(), e);
				} finally {
					if (tt != null) {
						if (valid) {
							poolProvider.returnConnection(thriftServerInfo, tt);
						} else {
							failoverCheckingStrategy.fail(thriftServerInfo);
							poolProvider.returnBrokenConnection(thriftServerInfo, tt);
						}
					} else {
						failoverCheckingStrategy.fail(thriftServerInfo);
					}
				}
			}
		}
	}

	public void setConnectionPool(ThriftConnectionPool poolProvider) {
		this.poolProvider = poolProvider;
	}

	public void setServerInfoList(List<ThriftServerInfo> serverInfoList) {
		this.serverInfoList = serverInfoList;
	}

	public void setBackupServerInfoList(List<ThriftServerInfo> backupServerInfoList) {
		this.backupServerInfoList = backupServerInfoList;
	}

	public FailoverCheckingStrategy<ThriftServerInfo> getFailoverCheckingStrategy() {
		return failoverCheckingStrategy;
	}

}
