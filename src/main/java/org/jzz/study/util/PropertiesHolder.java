package org.jzz.study.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHolder {

	public static Properties getProperties(String fileName) throws FileNotFoundException, IOException {
		Properties pro = new Properties();
		String pathString = PropertiesHolder.class.getResource("/").getPath();//只到达class所在的路径(包路径)
		pro.load(new FileInputStream(pathString + "/" + fileName));	//maven打包后将配置文件放到class目录根目录
		return pro;
	}
}
