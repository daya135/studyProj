package org.jzz.study.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {
	
	public static void copyFile(String source, String dest) {
		try {
			FileInputStream fInputStream = new FileInputStream(source);
			FileOutputStream fOutputStream = new FileOutputStream(dest);
			byte[] tmp = new byte[2048];
			int bytesToRead = 0;
			while ((bytesToRead = fInputStream.read(tmp)) != -1) {
				fOutputStream.write(tmp, 0, bytesToRead);
			}
			fInputStream.close();
			fOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public static void copyFileNIO(String source, String dest) {
		 //NIO 为所有的原始类型提供(Buffer)缓存支持。字符集编码解码解决方案。
		 //Channel ：一个新的原始I/O 抽象。 支持锁和内存映射文件的文件访问接口。 提供多路(non-bloking) 非阻塞式的高伸缩性网络I/O 
		try {
			FileInputStream fInputStream = new FileInputStream(source);
			FileOutputStream fOutputStream = new FileOutputStream(dest);	
			
			FileChannel inChannel = fInputStream.getChannel();//通道与流的不同之处在于通道是双向的。
			FileChannel outChannel = fOutputStream.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(4096);	//每个非布尔基本类型都有一个缓冲区类
			while((inChannel.read(buffer)) != -1) {
				buffer.flip();
				outChannel.write(buffer);
				buffer.clear();
			}
			inChannel.close();
			outChannel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	//列出文件夹下文件列表，自定义分隔符拼接字符串
	public static void ListFile(String dirStr, String spliteStr) {
		File dirFile = new File(dirStr);
		if (!dirFile.exists() || !dirFile.isDirectory() )
			System.out.println("文件不存在或不是目录!!" + dirStr);
		File[] files = dirFile.listFiles();
		for (File file : files) {
			System.out.print(file.getName());
			System.out.print(spliteStr);
		}
	}
	
	public static void main(String[] args) {
//		String dirStr = "D:/Document/eclipse-/UnitTest/target/lib/";
//		ListFile(dirStr, "D:/");
		
		String source = "D:/Download/美国众神.mp4";
		Long now = System.nanoTime();
		copyFileNIO(source, "D:/Download/美国众神1.mp4");
		System.out.println((System.nanoTime() - now)/1000000000);
		now = System.nanoTime();
		copyFile(source, "D:/Download/美国众神2.mp4");
		System.out.println((System.nanoTime() - now)/1000000000);
	}
	
    /**
     * @param path 文件绝对路径
     * @return 文本内容
     */
    public static String getString(String path) {
        File file = new File(path);
        return getString(file);
    }

    /**
     * @param file 文件对象
     * @return 文本内容
     */
    public static String getString(File file) {
        if (!file.exists()) {
            return "没有找到文件";
        }

        try {
            FileInputStream stream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            line = reader.readLine();
            if (line != null) {
                do {
                    if (line.length() > 2) {
                        if ((line.charAt(0) == '*') && (line.charAt(1) == '*')) {
                            continue;
                        }
                    }
                    sb.append(line);
                    sb.append("\r\n");
                } while ((line = reader.readLine()) != null);
                sb.delete(sb.length() - 2, sb.length());
            }
            reader.close();
            stream.close();
            return sb.toString();
        } catch (Exception e) {
        	 e.printStackTrace();
        }

        return "";

    }

    /**
     * @param path 文件地址
     * @param value 写入内容
     * @param append 是否追加写入
     */
    public static void writeString(String path, String value, Boolean append) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile(); //不存在的文件直接创建
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
            writer.write(value, 0, value.length());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
