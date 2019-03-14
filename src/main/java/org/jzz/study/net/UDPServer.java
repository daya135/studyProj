package org.jzz.study.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
	private static int PORT = 12322;
	
	public static void main(String[] args) throws IOException{
		DatagramSocket datagramSocket = new DatagramSocket(PORT);
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		System.out.println("***UDP服务器端已经启动，等待客户端发送数据***");
		datagramSocket.receive(packet); // 阻塞
		String info = new String(data, 0, packet.getLength());
		System.out.println("收到客户端数据: " + info);
		
		/*
         * 向客户端响应数据
         */
        // 1.定义客户端的地址、端口号、数据
        InetAddress address = packet.getAddress();
        System.out.println("准备回复客户端" + address);
        int port = packet.getPort();
        byte[] data2 = "欢迎您!".getBytes();
        // 2.创建数据报，包含响应的数据信息
        DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);
        // 3.响应客户端
        datagramSocket.send(packet2);
        // 4.关闭资源
        datagramSocket.close();
	}
}
