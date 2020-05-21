package org.jzz.study.net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.xml.crypto.Data;

import org.jzz.study.util.Print;
import org.jzz.study.util.PropertiesHolder;
/*
 java.mail.host=smtp.qq.com
java.mail.username=1450724851@qq.com
java.mail.password=yrhqkucwujrvgdhg
java.mail.smtp.auth=true
java.mail.smtp.starttls.enable=true
java.mail.smtp.starttls.required=true
java.mail.default-encoding=UTF-8
 */
/**
①、Message 类:javax.mail.Message 类是创建和解析邮件的核心 API，这是一个抽象类，通常使用它的子类javax.mail.internet.MimeMessage 类。
	它的实例对象表示一份电子邮件。客户端程序发送邮件时，首先使用创建邮件的 JavaMail API 创建出封装了邮件数据的 Message 对象，然后把这个对象传递给邮件发送API（Transport 类） 发送。
	客户端程序接收邮件时，邮件接收API把接收到的邮件数据封装在Message 类的实例中，客户端程序在使用邮件解析API从这个对象中解析收到的邮件数据。
②、Transport 类：javax.mail.Transport 类是发送邮件的核心API 类，它的实例对象代表实现了某个邮件发送协议的邮件发送对象，
	例如 SMTP 协议，客户端程序创建好 Message 对象后，只需要使用邮件发送API 得到 Transport 对象，
	然后把 Message 对象传递给 Transport 对象，并调用它的发送方法，就可以把邮件发送给指定的 SMTP 服务器。
③、Store 类：javax.mail.Store 类是接收邮件的核心 API 类，它的实例对象代表实现了某个邮件接收协议的邮件接收对象，
	例如 POP3 协议，客户端程序接收邮件时，只需要使用邮件接收 API 得到 Store 对象，然后调用 Store 对象的接收方法，
	就可以从指定的 POP3 服务器获得邮件数据，并把这些邮件数据封装到表示邮件的 Message 对象中。
④、Session 类：javax.mail.Session 类用于定义整个应用程序所需的环境信息，以及收集客户端与邮件服务器建立网络连接的会话信息，
	例如邮件服务器的主机名、端口号、采用的邮件发送和接收协议等。Session 对象根据这些信息构建用于邮件收发的 Transport 和 Store 对象，以及为客户端创建 Message 对象时提供信息支持。
 *
 */
public class MailTest {
	private Session session;
	private String sourceAddr;
	private String username; 
	private String password;
	private Transport sessionTransport;
	
	public MailTest() throws Exception {
		Properties pro = PropertiesHolder.getProperties("config.properties");
		username = pro.getProperty("mail.username"); 
		password = pro.getProperty("mail.password");
		sourceAddr = username;
		
		Properties mailPro = new Properties();
		mailPro.setProperty("mail.smtp.auth", pro.getProperty("mail.smtp.auth"));
		mailPro.setProperty("mail.transport.protocol", pro.getProperty("mail.transport.protocol"));
		mailPro.setProperty("mail.smtp.host", pro.getProperty("mail.smtp.host"));
		
		this.session = Session.getInstance(mailPro);
		
		sessionTransport = session.getTransport();	//不能在此处初始化时进行连接。。。则不能打印debug日志，且可能报空指针错误
		sessionTransport.connect(username, password);
//		session.setDebug(false);
		session.setDebug(true);//调试信息
	}
	
	public void sendTextMail(String address) throws Exception{
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(sourceAddr));
		//RecipientType：.CC 抄送 .BCC密送 .TO发送
		message.setRecipient(RecipientType.TO, new InternetAddress(address));	
		message.setSubject("studyProj.MailTest.sendTextMail");
		message.setContent("简单文本邮件&#10;第二段xxx", "text/html;charset=UTF-8");	//怎么换行，\n和html转义都不行
		message.setSentDate(new Date());
		Transport transport = session.getTransport();
		Print.print(password);
		transport.connect(username, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
	
	public void sendComplexMail(String address) throws Exception {
		String path = this.getClass().getResource("/").getPath();

		//图片节点
		MimeBodyPart imgPart = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource(path.substring(1) + "img/huaji.jpg"));
		imgPart.setDataHandler(dh);
		imgPart.setContentID("huaji.jpg");// 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
		//文本节点
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent("这是一张图片<br/><a href='http://www.cnblogs.com/ysocean/p/7666061.html'><img src='cid:huaji.jpg'/></a>", "text/html;charset=UTF-8");
		// 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
		MimeMultipart multi = new MimeMultipart();
		multi.addBodyPart(textPart);
		multi.addBodyPart(imgPart);
		multi.setSubType("related");
		// 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
        // 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        // 上面的 mailTestPic 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
		MimeBodyPart text_img_part = new MimeBodyPart();
		text_img_part.setContent(multi);
		//附件节点
		MimeBodyPart attachment = new MimeBodyPart();
		DataHandler dh2 = new DataHandler(new FileDataSource(path.substring(1) + "beanDemo.txt"));
		Print.print(path.substring(1) + "beanDemo.txt");
		attachment.setDataHandler(dh2);
		attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
		//文本+图片、附件混合
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text_img_part);
		mm.addBodyPart(attachment);
		mm.setSubType("mixed");
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(sourceAddr));
		message.setRecipient(RecipientType.TO, new InternetAddress(address));	
		message.setSubject("studyProj.MailTest.sendComplexMail");
		// 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
		message.setContent(mm);	
		message.setSentDate(new Date());
		sessionTransport.sendMessage(message, message.getAllRecipients());
		sessionTransport.close();
	}
	
	public static void main(String[] args){
		try {
			MailTest mailTest = new MailTest();
//			mailTest.sendTextMail("jzzperson@163.com");
			mailTest.sendComplexMail("jzzperson@163.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
