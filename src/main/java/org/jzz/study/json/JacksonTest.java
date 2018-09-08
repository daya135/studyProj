package org.jzz.study.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonTest {
	
	public static void toJsonTest() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		BeanDemo bean1 = BeanDemo.getFullBean();
		String json = mapper.writeValueAsString(bean1);
	    System.out.println(json);
	    
	    BeanDemo bean2 = BeanDemo.getPartBean();
	    json = mapper.writeValueAsString(bean2);
	    System.out.println(json);
	    
	    List<BeanDemo> beans = new ArrayList<BeanDemo>();
	    beans.add(bean1);
	    beans.add(bean2);
	    json = mapper.writeValueAsString(beans);
	    System.out.println(json);
	    
	}
	
	// 自定义日期格式版本2  未完成!
	public static void toJsonTest2() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		DeserializationConfig cfg = mapper.getDeserializationConfig();
		//设置JSON时间格式  
		SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 
		
		
		BeanDemo2 bean1 = BeanDemo2.getFullBean();
		String json = mapper.writeValueAsString(bean1);
	    System.out.println(json);
	    
	}
	
	public static void toBeanTest() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		
		String str = "{\"id\":123,\"name\":\"testname\",\"birth\":\"2018-09-08 16:23:08\"}"; //数字不用加引号
		BeanDemo bean = mapper.readValue(str, new TypeReference<BeanDemo>() {}); //缺少的属性为空值
		System.out.println(bean);
		
		String strs = "[{\"id\":123,\"birth\":\"2018-09-08 08:37:24\"},{\"id\":123,\"birth\":\"2018-09-08 08:37:24\"}]";
		List<BeanDemo> beans = mapper.readValue(strs, new TypeReference<List<BeanDemo>>() {}); //缺少的属性为空值
		System.out.println(beans.get(0));
		System.out.println(beans.get(1));
	}
	
	public static void main(String[] args) throws Exception{
		toJsonTest();
		toBeanTest();
	}
}
