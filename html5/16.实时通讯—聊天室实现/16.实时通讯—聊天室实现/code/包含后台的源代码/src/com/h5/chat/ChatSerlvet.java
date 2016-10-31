package com.h5.chat;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;


public class ChatSerlvet extends WebSocketServlet{

	@Override
	protected StreamInbound createWebSocketInbound(String arg0) {	
		ChatInbound bound = new ChatInbound();
		System.out.println(bound);
		ChatCenter.addClient(bound);
		return bound;
	}
	
}
