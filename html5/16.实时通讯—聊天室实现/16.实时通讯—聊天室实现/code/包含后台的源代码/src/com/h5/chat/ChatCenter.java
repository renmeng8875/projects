package com.h5.chat;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.*;

public class ChatCenter {

	/**
	 * 客户端websocket列表
	 */
	private static List<ChatInbound> clients  = new ArrayList<ChatInbound>();
	
	public static void addClient(ChatInbound inbound) {
		clients.add(inbound);
	}
	
	public static void bordcast(ChatInbound inbound,CharBuffer msg) {
		String strMsg = msg.toString();
		try {
			for(ChatInbound client : clients) {
			
				if(!client.equals(inbound)) {
					System.out.println(client + " send " + CharBuffer.wrap(strMsg));
					client.getWsOutbound().writeTextMessage(CharBuffer.wrap(strMsg));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void remove(ChatInbound inbound) {
		clients.remove(inbound);
	}
}
