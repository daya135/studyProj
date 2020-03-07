package org.jzz.study;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;

import org.jzz.study.util.Print;

class testBean {
	private int age = 10;
	private String name = "test";

}

public class Test {

	private static final int[] a = { 1, 2 };

	public static String reverse(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		return reverse(str.substring(1)) + str.charAt(0);
	}
	
	public static void testSocket() throws IOException{
		Socket socket=new Socket("127.0.0.1",12321);
		System.out.println("客户端连接成功");
		Scanner scanner=new Scanner(System.in);
		BufferedWriter write=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));     //可用PrintWriter
		BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String readline;
		while(true)   //循环发消息
		{
			readline=scanner.nextLine();
			write.write(readline+'\n');  //write()要加'\n'
			write.flush();
//			socket.shutdownOutput();
			System.out.println(in.readLine());
		}
	}

	public static void main(String[] args) throws Exception {
		// System.out.println();
//		String str1 = "hello";
//		String str = "你好你";
//		Print.print(reverse(str1));
//		byte[] bytes = str.getBytes("utf-8");
//		for (byte b : bytes) {
//			System.out.print(Integer.toHexString(b & 0x00FF));
//		}
		testSocket();

	}
}
