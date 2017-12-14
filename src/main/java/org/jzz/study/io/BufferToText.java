package org.jzz.study.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import org.jzz.study.util.ByteUtils;

/* 重点理解:ByteBuffer是跟文件对接的，原封不动的保存文件字节内容！！
 * 所以不管是read还是write，使用Bytebuffer时，直接将其视为带编码的文件内容。
 * 那么就可以理解代码中的字符串进入到Bytebuff时，一定是经过了编码动作。
 * 而，从bytebuffer转换为可以Charbuffer（（一个char存储一个字符））时，一定是发生了解码动作，变为为java内码
 */
public class BufferToText {
	private static final int BSIZE = 1024;
	public static void main(String[] args) throws Exception{
		String defalutCharSet = Charset.defaultCharset().name();
		String encoding = System.getProperty("file.encoding");
		System.out.println("Charset.defaultCharset：" + defalutCharSet);
		System.out.println("file.encoding：" + encoding);
		
		FileChannel  fc = new FileOutputStream("data2.txt").getChannel();
		fc.write(ByteBuffer.wrap("Some text 你好".getBytes()));
		fc.close();
		fc = new FileInputStream("data2.txt").getChannel();
		ByteBuffer buff = ByteBuffer.allocate(BSIZE);
		fc.read(buff);
		buff.flip();
		//Dosen't work
		System.out.println("buff（default encoding）：" + ByteUtils.BuffToString(buff));
		System.out.println("不手动解码（推测自动用的UTF-16BE）：" + buff.asCharBuffer());
		// Decode using this System's default Charset
		buff.rewind();
		System.out.println("使用默认编码方式（ " + encoding + "）手动解码: " + Charset.forName(encoding).decode(buff));
		buff.rewind();
		System.out.println("使用unicode手动解码：" + Charset.forName("unicode").decode(buff));
		System.out.println();
		
		
		//Or, we could encode with something that will print
		
		fc = new FileOutputStream("data2.txt").getChannel();
		fc.write(ByteBuffer.wrap("Some text 你好".getBytes("UTF-16BE")));
		System.out.println("构造buff写入文件（UTF-16BE）：" + ByteUtils.BuffToString(ByteBuffer.wrap("Some text 你好".getBytes("UTF-16BE")))); 
		fc.close();
		fc = new FileInputStream("data2.txt").getChannel();
		buff.clear();
		fc.read(buff);
		buff.flip();
		System.out.println("从文件读取的buff：" + ByteUtils.BuffToString(buff));
		System.out.println("不手动解码（推测自动用的UTF-16BE）：" + buff.asCharBuffer()); 
		System.out.println("使用 UTF-8手动解码：" + Charset.forName("UTF-8").decode(buff));
		buff.rewind();
		System.out.println("使用UTF-16BE手动解码：" + Charset.forName("UTF-16BE").decode(buff));
		buff.rewind();
		System.out.println("使用unicode手动解码：" + Charset.forName("unicode").decode(buff));
		System.out.println();
		
		fc = new FileOutputStream("data2.txt").getChannel();
		fc.write(ByteBuffer.wrap("Some text 你好".getBytes("unicode")));
		System.out.println("构造buff写入文件（unicode）：" + ByteUtils.BuffToString(ByteBuffer.wrap("Some text 你好".getBytes("unicode")))); 
		fc.close();
		fc = new FileInputStream("data2.txt").getChannel();
		buff.clear();
		fc.read(buff);
		buff.flip();
		System.out.println("从文件读取的buff：" + ByteUtils.BuffToString(buff));
		System.out.println("不手动解码（推测自动用的UTF-16BE）：" + buff.asCharBuffer()); 
		System.out.println("使用 UTF-8手动解码：" + Charset.forName("UTF-8").decode(buff));
		buff.rewind();
		System.out.println("使用UTF-16BE手动解码：" + Charset.forName("UTF-16BE").decode(buff));
		buff.rewind();
		System.out.println("使用unicode手动解码：" + Charset.forName("unicode").decode(buff));
		System.out.println();
		
		//为什么这种方式能够正确输出??
		fc = new FileOutputStream("data2.txt").getChannel();
		buff = ByteBuffer.allocate(BSIZE);
		buff.asCharBuffer().put("Some text 你好"); //注意！这里将utf-8转码为unicode后存储在buff中
		fc.write(buff);
		fc.close();
		fc = new FileInputStream("data2.txt").getChannel();
		buff.clear();
		fc.read(buff);
		buff.flip();
		System.out.println("Charbuff.put 方式构造的buff：" + ByteUtils.BuffToString(buff));	
		System.out.println("不手动解码（推测自动用的UTF-16BE）：" + buff.asCharBuffer()); 
		System.out.println("使用 UTF-8手动解码：" + Charset.forName("UTF-8").decode(buff));
		buff.rewind();
		System.out.println("使用UTF-16BE手动解码：" + Charset.forName("UTF-16BE").decode(buff));
		buff.rewind();
		System.out.println("使用unicode手动解码：" + Charset.forName("unicode").decode(buff));

	}
}
