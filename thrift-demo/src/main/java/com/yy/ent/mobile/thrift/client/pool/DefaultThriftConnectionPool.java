package com.yy.ent.mobile.thrift.client.pool;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.thrift.transport.TTransport;

public final class DefaultThriftConnectionPool implements ThriftConnectionPool {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DefaultThriftConnectionPool.class);


	private final GenericKeyedObjectPool<ThriftServerInfo, TTransport> connections;

	public DefaultThriftConnectionPool(GenericKeyedObjectPoolConfig config) {
		connections = new GenericKeyedObjectPool(new ThriftConnectionFactory(), config);
	}

	public DefaultThriftConnectionPool(KeyedPooledObjectFactory<ThriftServerInfo, TTransport> factory, GenericKeyedObjectPoolConfig config) {
		connections = new GenericKeyedObjectPool(factory, config);
	}

	@Override
	public TTransport getConnection(ThriftServerInfo thriftServerInfo) {
		try {
			return connections.borrowObject(thriftServerInfo);
		} catch (Exception e) {
			logger.warn("fail to get connection for {}", thriftServerInfo, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void returnConnection(ThriftServerInfo thriftServerInfo, TTransport transport) {
		connections.returnObject(thriftServerInfo, transport);
	}

	@Override
	public void returnBrokenConnection(ThriftServerInfo thriftServerInfo, TTransport transport) {
		try {
			connections.invalidateObject(thriftServerInfo, transport);
		} catch (Exception e) {
			logger.warn("fail to invalid object:{},{}", new Object[] { thriftServerInfo, transport, e });
		}
	}

	@Override
	public void close() {
		connections.close();
	}
}
