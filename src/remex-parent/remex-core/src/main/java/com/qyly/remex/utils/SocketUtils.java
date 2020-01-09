package com.qyly.remex.utils;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * socket工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class SocketUtils {

	/**
	 * ip + 端口，是否可连接
	 * @param host
	 * @param port
	 * @return
	 */
	public static boolean isConnectable(String host, int port) {
		if (Assist.isBlank(host)) {
			return false;
		}
		
		try {
			@SuppressWarnings("resource")
			Socket socket = new Socket();
			
			SocketAddress socketAddress = new InetSocketAddress(host, port);
			socket.connect(socketAddress, 250);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
