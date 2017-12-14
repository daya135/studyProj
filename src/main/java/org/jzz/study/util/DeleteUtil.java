package org.jzz.study.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DeleteUtil {
	public static void httpDelete(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url);
		HttpResponse httpResponse = httpClient.execute(httpDelete);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		if(statusCode == 200){
			//InputStream in = httpResponse.getEntity().getContent();  //要处理该数据流是否为GZIP流
			System.out.println(EntityUtils.toString(httpResponse.getEntity()));
		} 	
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		//HttpDelete请求参数则可以直接拼接到url字符串后面，当然此处没有参数
		String url = "http://localhost:8761/eureka/apps/SPBOOTDEMO/Merin-work.hna.net:spbootDemo:8080";
		DeleteUtil.httpDelete(url);
	}
}
