package com.h5.chat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

public class ChatInbound extends MessageInbound{

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		// TODO Auto-generated method stub
		// 接收客户端数据
		//System.out.println(arg0);
	}

	@Override
	protected void onTextMessage(CharBuffer msg) throws IOException {
		if("".equals(msg.toString()))
			return;
		// 接收客户端数据
		System.out.println("onTextMessage" + msg.toString());
		
		ChatCenter.bordcast(this, msg);
	}

	@Override
	protected void onOpen(WsOutbound outbound) {
		super.onOpen(outbound);
		System.out.println("open:" + outbound);
	}
	
	@Override
	protected void onClose(int status) {
		super.onClose(status);
		
		System.out.println("close:" + status);
		//ChatCenter.remove(this);
	}

}
