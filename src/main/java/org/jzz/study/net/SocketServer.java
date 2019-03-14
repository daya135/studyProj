package org.jzz.study.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
	public static void startSimpleServer(int port) throws IOException{
		ServerSocket serverSocket = new ServerSocket(port);
		InetAddress address = InetAddress.getLocalHost();
		String ip = address.getHostAddress();
		System.out.println("~~~服务端已就绪，等待客户端接入~，服务端ip地址: " + ip);
		final Socket socket = serverSocket.accept(); //进入阻塞
		String msg = "用户:" + socket.getInetAddress()+ ":"+ socket.getPort() + "~加入了聊天室";
		System.out.println(msg);
		final BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		final PrintWriter pw =  new PrintWriter(new BufferedWriter(  
                new OutputStreamWriter(socket.getOutputStream(), "UTF-8")),true); 
		String info = null;
		while (true) {
			try {
				if(socket.isConnected() && (info = br.readLine()) != null){	//循环读取客户端的信息
		            System.out.println("客户端发送过来的信息" + info);
		            pw.println(info);
		            pw.flush();
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static ServerSocket chatSocket;
	private static List<Socket> clientList = new ArrayList<Socket>();	//客户端socket列表
	
	static class ChatServer implements Runnable{
		private Socket socket;
		private BufferedReader in = null;
		private String msg = "";
		
	    public ChatServer(Socket socket) {
			this.socket = socket;
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				msg = "用户:" + this.socket.getInetAddress()+ ":"+ this.socket.getPort() + "~加入了聊天室"  
                        +"当前在线人数:" + clientList.size(); 
				this.sendmsg();
			} catch (IOException e) {e.printStackTrace();}
		}

		@Override
		public void run() {
			try {
				while (true) {
					if ((msg = in.readLine()) != null) {
						if ("bye".equals(msg)) {
                            clientList.remove(socket);
                            in.close();
                            msg = "用户:" + socket.getInetAddress() + ":"+ this.socket.getPort() 
                                    + "退出:" +"当前在线人数:"+clientList.size();  
                            System.out.println(msg);
                            this.sendmsg();
                            socket.close();
                            break;
						} else {
							msg = socket.getInetAddress()+ ":"+ this.socket.getPort() + " 说: " + msg;  
                            this.sendmsg(); 
						}
					}
				}
			} catch (IOException e) {e.printStackTrace();}
		}
		
		//收到信息的服务端sokect为连接上服务端的每个客户端socket发送信息
        public void sendmsg()
        {
            System.out.println(msg);
            int num = clientList.size();
            for(int index = 0; index < num; index++)
            {
                Socket mSocket = clientList.get(index);  
                PrintWriter pout = null;  
                try {  
					pout = new PrintWriter(new BufferedWriter(  
                            new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8")),true); 
                    pout.println(msg);  
                }catch (IOException e) {e.printStackTrace();}  
            }
        }
	}
	
	public static void startChatServer(int port) throws IOException {
		chatSocket = new ServerSocket(port);
		ExecutorService executorService = Executors.newCachedThreadPool();
		System.out.println("服务端运行中...\n");
		Socket client = null;
		while (true) {
			client = chatSocket.accept();
			clientList.add(client);
			executorService.execute(new ChatServer(client));
		}
	}
 	
	public static void main(String[] args) throws IOException  {
//		SocketServer.startSimpleServer(12321);
		SocketServer.startChatServer(12321);
	}
}
