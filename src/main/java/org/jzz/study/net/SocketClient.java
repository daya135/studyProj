package org.jzz.study.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
	
	public static void testSocket() throws IOException{
		Socket socket=new Socket("127.0.0.1",12321);
		System.out.println("客户端连接成功");
		Scanner scanner=new Scanner(System.in);
		BufferedWriter write=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));     //可用PrintWriter
		BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String readline = null;
		
		new Thread() {
			@Override
			public void run() {
				while(true)  { //循环接收消息
					try {
						System.out.println(in.readLine());
					} catch (IOException e) {
						e.printStackTrace();
						break;
					}
				}
			}
		}.start();
		
		while(true)   //循环发消息
		{
			readline=scanner.nextLine();
			write.write(readline+'\n');	//write()要加'\n'
			write.flush();
//			socket.shutdownOutput();
		}
	}
	
	public static void main(String[] args) throws IOException {
		testSocket();
	}
}
