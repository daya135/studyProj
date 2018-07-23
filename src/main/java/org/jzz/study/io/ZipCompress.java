package org.jzz.study.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Enumeration;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static org.jzz.study.util.Print.*;

public class ZipCompress {
	public static void compress(String[] fileNames) throws IOException {
		FileOutputStream fout = new FileOutputStream("test.zip");
		CheckedOutputStream cout = new CheckedOutputStream(fout, new Adler32());
		ZipOutputStream zout = new ZipOutputStream(cout);
		BufferedOutputStream bos = new BufferedOutputStream(zout);
		
		zout.setComment("A test of Java Zipping");
		for (String fileName : fileNames) {
			print("Writting file " + fileName);
			BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
			byte[] buf = new byte[1024];
			int c=0;
			int n = 0;
			while((c = bin.read(buf)) > 0) {
				print(n++);
				bos.write(buf, 0, c);
			}
			bin.close();
			bos.flush();
		}
		bos.close();
	}
	
	public static void unzip(String zipName) throws IOException {
		print("reading file");
		FileInputStream fin = new FileInputStream(zipName);
		CheckedInputStream cin = new CheckedInputStream(fin, new Adler32());
		ZipInputStream zin = new ZipInputStream(cin);
		BufferedInputStream bin = new BufferedInputStream(zin);
		
		ZipEntry ze;
		while((ze = zin.getNextEntry()) != null) {
			print("reading file from zip ： " + ze);
		}
		bin.close();
		
		ZipFile zf = new ZipFile(zipName);
		Enumeration e = zf.entries();
		while(e.hasMoreElements()) {
			ZipEntry ze2 = (ZipEntry)e.nextElement();
			print("File:" + ze2);
		}
	}
	
	public static void main(String[] a) throws IOException {
		String[] files = new String[2];
		files[0] = "D:\\Bin\\updatelog.log";
		files[1] = "D:\\Bin\\nodepad_tempfile.txt";
//		compress(files);
		unzip("D:\\Download\\A111.zip");
	}
}
