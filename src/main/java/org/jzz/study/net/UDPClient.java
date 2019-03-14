package org.jzz.study.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
	private static int PORT = 12322;
	
	public static void main(String[] args) throws IOException{
		InetAddress address = InetAddress.getByName("127.0.0.1");
		byte[] data = "hello".getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, address, PORT);
		DatagramSocket socket = new DatagramSocket();
		socket.send(packet);
		
		byte[] data2 = new byte[1024];
		DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
		socket.receive(packet2);
		String reply = new String(data2, 0, packet2.getLength());
        System.out.println("我是客户端，服务器说：" + reply);
        // 4.关闭资源
        socket.close();
	
	}
}
