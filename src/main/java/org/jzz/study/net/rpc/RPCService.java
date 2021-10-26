package org.jzz.study.net.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RPCService {
	private static Map<String, String> producerMap = new HashMap<String, String>();
	static {
		producerMap.put("org.jzz.study.net.rpc.ProducerService", "org.jzz.study.net.rpc.ProducerServiceImpl");
	}
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(10086);
		ExecutorService executor = Executors.newCachedThreadPool();
		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("客户端连接成功，" + socket.getPort());
			executor.execute(new RPCTask(socket));
		}
	}
	
	static class RPCTask implements Runnable {
		Socket socket;
		
		public RPCTask(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				MsgBean msg = (MsgBean)in.readObject();
				System.out.println("接收的消息为：" + msg);
				socket.shutdownInput();
				Class cl = Class.forName(producerMap.get(msg.getClassName()));
				Method method = cl.getMethod(msg.getMethodName(), msg.getMethodParmType());
				Object obj = method.invoke(cl.newInstance(), msg.getParams());
				System.out.println("执行结果：" + obj);
				if (!"void".equals(method.getReturnType().getName())) {
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					out.writeObject(obj);
					socket.shutdownOutput();
					out.close();
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
