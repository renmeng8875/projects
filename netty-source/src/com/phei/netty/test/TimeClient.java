/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phei.netty.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 */
public class TimeClient {

	private Socket socket;
	
	public void init() throws Exception{
		int port = 8080;
		this.socket = new Socket("127.0.0.1", port);;
	}
    /**
     * @param args
     */
    public  void send() {
	BufferedReader in = null;
	PrintWriter out = null;
	try {
	    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    out = new PrintWriter(socket.getOutputStream(), true);
	    out.println("QUERY TIME ORDER");
	    String resp = in.readLine();
	    System.out.println("client get response from server : " + resp);
	} catch (Exception e) {
	    e.printStackTrace();
	} 
    }
    
    public static void main(String[] args) throws Exception {
    	TimeClient client = new TimeClient();
    	client.init();
		for (int i = 0; i < 100; i++) {
			client.send();
		}
	}
}
