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

import java.util.List;

import org.apache.thrift.TServiceClient;

import com.yy.ent.mobile.thrift.client.failover.FailoverChecker;
import com.yy.ent.mobile.thrift.client.pool.ThriftConnectionPool;
import com.yy.ent.mobile.thrift.client.pool.ThriftServerInfo;
import com.yy.ent.mobile.thrift.client.utils.ThriftClientUtils;

/**
 * @author Tingkun Zhang
 */
public class WeightThriftClient extends DefaultThriftClient {

	/**
	 * @param failoverStategy
	 * @param poolProvider
	 * @param servers
	 */
	public WeightThriftClient(FailoverChecker failoverChecker, ThriftConnectionPool poolProvider, String servers) {
		this(failoverChecker, poolProvider, servers, null);
	}

	public WeightThriftClient(FailoverChecker failoverChecker, ThriftConnectionPool poolProvider, String servers, String backupServers) {
		super(failoverChecker, poolProvider, servers, backupServers);
	}

	@Override
	public <X extends TServiceClient> X iface(Class<X> ifaceClass) {
		List<ThriftServerInfo> servers = getAvaliableServers();
		if (servers == null || servers.isEmpty()) {
			throw new NullPointerException("servers could not be null");
		}
		int[] chances = new int[servers.size()];
		for (int i = 0; i < servers.size(); i++) {
			chances[i] = servers.get(i).getChance();
		}
		return iface(ifaceClass, servers.get(ThriftClientUtils.chooseWithChance(chances)));
	}

}
