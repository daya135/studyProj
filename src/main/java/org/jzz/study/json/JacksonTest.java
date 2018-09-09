package org.jzz.study.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
	
	
	public static void toJsonTreeMode() throws IOException {
		JsonNodeFactory factory = new JsonNodeFactory(true);
		JsonFactory jsonFactory = new JsonFactory();
		JsonGenerator generator = jsonFactory.createGenerator(System.out);
		ObjectMapper mapper = new ObjectMapper();
		 
        //创建节点和数据,一个ObjectNode代表一个节点对象
        ObjectNode node1 = factory.objectNode();
        ObjectNode node2 = factory.objectNode();
        node1.put("A", "a");
        node1.put("B", "b");
        node2.set("C", node1);
 
        // 根节点
        ObjectNode root = factory.objectNode();
        root.put("root", "root");
        root.set("children", node2);
        mapper.writeTree(generator, root);
        //输出{"root":"root","children":{"C":{"A":"a","B":"b"}}}
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
	
	 public static void testTreeModel() throws Exception {
	        ObjectMapper objectMapper = new ObjectMapper();
			JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();
	        // 创建一个model
	        ObjectNode node = nodeFactory.objectNode();
	        node.put("age", 18);
	        // 新增
	        node.put("name", "周杰伦");
	        // 如果存在同名的则是替换操作
	        node.put("age", 19);
	        ArrayNode coursesNode = node.putArray("courses");
	        coursesNode.add("思想政治");
	        coursesNode.add("高等数学");
	        // 获取节点类型
	        System.out.println(node.getNodeType());
	        System.out.println(coursesNode.getNodeType());
	        // 移除第一个
	        coursesNode.remove(0);
	        // 输出
	        System.out.println(node.toString());
	        String jsonStr = "{\"age\":19,\"name\":\"周杰伦\",\"courses\":[\"高等数学\"]}";
	        JsonNode jsonNode = objectMapper.readTree(jsonStr);
	        ArrayNode arrayNode = (ArrayNode) jsonNode.withArray("courses");
	        arrayNode.add("马列");
	        for (int i = 0;i < arrayNode.size();i++){
	            System.out.println(arrayNode.get(i).asText());
	        }
	        System.out.println(jsonNode.toString());

	    }
	
	public static void main(String[] args) throws Exception{
//		toJsonTest();
//		toBeanTest();
		toJsonTreeMode();
		testTreeModel();
	}
}
