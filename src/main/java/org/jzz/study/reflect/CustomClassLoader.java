package org.jzz.study.reflect;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

//动态热替换class，自定义类加载器
public class CustomClassLoader extends ClassLoader{
	private static final String baseDir = "D:\\opt\\java\\studyProj\\classLoaderTest";//加载路径
	private Set<String> dynaclazns; // 需要由该类加载器直接加载的类名
	
	public CustomClassLoader() {
		super(null); // 指定父类加载器为 null
		dynaclazns = new HashSet<>();
	}
	
	private Class loadDirectly(String className) {
		String filePath = baseDir + File.separator + className + ".class";
		File classFile = new File(filePath);
		Class cls = null;
		byte[] raw = new byte[(int) classFile.length()];
		try {
			InputStream fin = new FileInputStream(classFile);
			fin.read(raw);
			fin.close();
			cls = defineClass(className, raw, 0, raw.length);
			dynaclazns.add(className);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cls;
		
	}
	
	@Override
	protected Class loadClass(String className, boolean resolve) throws ClassNotFoundException{

		Class cls = findLoadedClass(className);	//
		
		if (!dynaclazns.contains(className) && cls == null) {	
			cls = getSystemClassLoader().loadClass(className);
		}
		if (cls == null) {
			throw new ClassNotFoundException(className);  
		}
		if (resolve) {
			resolveClass(cls);   
		}
		return cls;
	}
	
	
	public static void main(String[] args) throws Exception{
		String version = "";
		
		while (true) {
			CustomClassLoader loader = new CustomClassLoader();
			Class cls = loader.loadDirectly("Foo");
			Method method = cls.getMethod("getVersion", new Class[] {});	
			Object versionObj = method.invoke(null);	//调用静态方法,无参
			if (!version.equals((String)versionObj)) {
				version = (String)versionObj;
				method = cls.getMethod("sayHello", new Class[] {});	
				method.invoke(cls.newInstance(), null);	//调用实例方法
			}
			Thread.sleep(500); 
		}
	}

}
