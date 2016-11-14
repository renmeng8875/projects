package com.yy.ent.mobile.thrift.client.pool;

import org.apache.thrift.transport.TTransport;

public interface ThriftConnectionPool {

    public TTransport getConnection(ThriftServerInfo thriftServerInfo);

    public void returnConnection(ThriftServerInfo thriftServerInfo, TTransport transport);

    public void returnBrokenConnection(ThriftServerInfo thriftServerInfo, TTransport transport);

	public void close();

}
