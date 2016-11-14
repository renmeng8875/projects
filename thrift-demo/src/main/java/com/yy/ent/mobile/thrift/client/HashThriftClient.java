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

import org.apache.thrift.TServiceClient;

import com.google.common.base.Charsets;
import com.yy.ent.mobile.thrift.client.failover.FailoverChecker;
import com.yy.ent.mobile.thrift.client.pool.ThriftConnectionPool;
import com.yy.ent.mobile.thrift.client.utils.MurmurHash3;

/**
 * @author Tingkun Zhang
 */
public class HashThriftClient extends DefaultThriftClient {

	/**
	 * @param failoverStategy
	 * @param poolProvider
	 * @param servers
	 */
	public HashThriftClient(FailoverChecker failoverChecker, ThriftConnectionPool poolProvider, String servers) {
		this(failoverChecker, poolProvider, servers, null);
	}

	public HashThriftClient(FailoverChecker failoverChecker, ThriftConnectionPool poolProvider, String servers, String backupServers) {
		super(failoverChecker, poolProvider, servers, backupServers);
	}

	public <X extends TServiceClient> X iface(Class<X> ifaceClass, String key) {
		byte[] b = key.getBytes(Charsets.UTF_8);
		return iface(ifaceClass, MurmurHash3.murmurhash3_x86_32(b, 0, b.length, 0x1234ABCD));
	}

}
