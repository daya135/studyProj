package org.jzz.study.util;

import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import org.omg.IOP.Encoding;

public class ByteUtils {
	public static String ByteToString(byte[] bytes) {
		StringBuffer strBuffer = new StringBuffer();
		for (byte b : bytes) {
			String bString = Integer.toHexString(0xff & b);
			strBuffer.append(bString);
		}
		return strBuffer.toString();		
	}
	
	public static String BuffToString(ByteBuffer buffer) {
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < buffer.limit(); i ++) {
			byte b = buffer.get(i);
			String bstr = Integer.toHexString(0xff & b);
			strBuffer.append(bstr);
		}
		return strBuffer.toString();		
	}
	
	public static String BuffToString(CharBuffer buffer) {
		Charset cs = Charset.forName ("unicode");
		return BuffToString(cs.encode(buffer));	
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(ByteToString("你好".getBytes()));
		System.out.println(ByteToString("你好".getBytes("UTF-8")));
		
		ByteBuffer buff = ByteBuffer.allocate(24);
		CharBuffer cbuff = buff.asCharBuffer();
		cbuff.put("你好");
		byte[] bytes = new byte[24];
		buff.get(bytes);
		
		System.out.println(ByteToString(bytes));
	}
}
