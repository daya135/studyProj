package org.jzz.study.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jzz.study.util.Print;

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

/** 测试jackson相关功能,涉及：
 * JsonNode、bean（Map）、string 之间的转换；
 * JsonNode动态修改结构；
 * json串的输出；
 * 主要类：ObjectMapper、JsonNode、ObjectNode、JsonGenerator；
 */
public class JacksonTest {
	//将javabean映射为json字符串
	public static void beanToJsonStr() throws JsonProcessingException {
		Print.print("beanToJson()--------------------------------");
		ObjectMapper mapper = new ObjectMapper();
		
		BeanDemo bean1 = BeanDemo.getFullBean();
//		mapper.setVisibility(JsonMethod.GETTER, Visibility.NONE);
		String json = mapper.writeValueAsString(bean1);	//关键方法writeValueAsString
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
	public static void beanToJson2() throws JsonProcessingException {
		Print.print("beanToJson2()--------------------------------");
		ObjectMapper mapper = new ObjectMapper();
		DeserializationConfig cfg = mapper.getDeserializationConfig();
		//设置JSON时间格式  
		SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 
		BeanDemo2 bean1 = BeanDemo2.getFullBean();
		String json = mapper.writeValueAsString(bean1);
	    System.out.println(json);
	}
	//将Map映射为json串
	public static void mapToJsonStrTest() throws Exception{
		Print.print("mapToJsonStrTest()--------------------------------");
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", "name from map");
		map.put("age", 11);
		map.put("isMarray", true);
		map.put("map", map.clone());	//如果直接将自身放入，则会造成序列化时死循环！！所以clone一下
		String jsonStr = mapper.writeValueAsString(map);
		Print.print(jsonStr);
		Print.print(map);
	}
	//将json字符串映射为Map
	public static void strToMapTest() throws Exception{
		Print.print("strToMapTest()--------------------------------");
		ObjectMapper mapper = new ObjectMapper();
		
		String str = "{\"id\":123,\"name\":\"testname\",\"birth\":\"2018-09-08 16:23:08\"}"; 
		HashMap map = mapper.readValue(str, new TypeReference<HashMap<String, Object>>() {}); //缺少的属性为空值
		
		Print.print(map.get("name"));
	}
	//将json字符串映射为bean
	public static void strToBeans() throws Exception{
		Print.print("strToBeans()--------------------------------");
		ObjectMapper mapper = new ObjectMapper();
		
		String str = "{\"id\":123,\"name\":\"testname\",\"birth\":\"2018-09-08 16:23:08\"}"; 
		BeanDemo bean = mapper.readValue(str, new TypeReference<BeanDemo>() {}); //缺少的属性为空值
		System.out.println(bean);
		JsonNode jsonNode =  mapper.readValue(str, JsonNode.class);
		Print.print(jsonNode.get("name"));
		
		String strs = "[{\"id\":123,\"birth\":\"2018-09-08 08:37:24\"},{\"id\":123,\"birth\":\"2018-09-08 08:37:24\"}]";
		List<BeanDemo> beans = mapper.readValue(strs, new TypeReference<List<BeanDemo>>() {}); //缺少的属性为空值
		System.out.println(beans.get(0));
		System.out.println(beans.get(1));
	}
	//用其它形式构建bean，主要看mapper.readValue支持的第一个参数
	public static BeanDemo toBeanTest() throws Exception {
		Print.print("toBeanTest()--------------------------------");
		ObjectMapper mapper = new ObjectMapper();
		FileInputStream in = new FileInputStream(JacksonTest.class.getResource("/").getPath().concat("/beanDemo.txt"));
		BeanDemo bean = mapper.readValue(in, BeanDemo.class);
		Print.print(bean);
		return null;
	}
	//用string构建JsonNode(注意不是JSONObject（org.json）)
	public static JsonNode strToJsonNode(String str) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
//		JsonNode jsonNode =  mapper.readValue(str, JsonNode.class);	//方式1
		JsonNode jsonNode = mapper.readTree(str);	//方式2
		return jsonNode;	
	}
	//从bean、Map构建JsonNode（有什么使用场景？？）
	public static JsonNode toJsonNodetest(Object obj) throws Exception {
		Print.print("toJsonNodetest()--------------------------------");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.valueToTree(obj);	//这个方法没有相关重载，且使用json字符串无法映射为Node。
		Print.print(node.get("name"));
		return node;
	}
	//此方法返回的ObjectNode是JsonNode的子类
	//自定义json结构,通过添加节点的形式构建json串
	//需要为工厂指定一个输出流,以便将根节点输出
	public static ObjectNode createJsonTreeMode() throws IOException {
		Print.print("createJsonTreeMode()--------------------------------");
		JsonNodeFactory factory = new JsonNodeFactory(true);
		 
        // 创建节点和数据,一个ObjectNode代表一个节点对象,ObjectNode是JsonNode的曾孙`>`
        ObjectNode node1 = factory.objectNode();
        ObjectNode node2 = factory.objectNode();
        node1.put("A", "a");
        node1.put("B", "b");
        node2.set("C", node1);
 
        // 根节点(逻辑上的)
        ObjectNode root = factory.objectNode();
        root.put("root", "root");	//注意put方法和底下set方法参数不一样
        root.set("children", node2);
        
        Print.print("\nObjectNode.toString: " + root.toString());	//直接调用toString方法
        return root;
	}
	//测试ObjectMapper
	public static void ObjectMapperTest() throws Exception {
		Print.print("ObjectMapperTest()--------------------------------");
		ObjectMapper mapper = new ObjectMapper();
		JsonGenerator generator = new JsonFactory().createGenerator(System.out);
		String jsonStr = "{\"age\":19,\"name\":\"周杰伦\",\"courses\":[\"高等数学\"]}";
		String fileName = JacksonTest.class.getResource("/").getPath() + "/testJsonFile_mapper.json";
		
		Print.print(mapper.writeValueAsString(BeanDemo.getFullBean()));
		
		mapper.writeValue(new File(fileName), BeanDemo.getFullBean()); //将bean输出至文件（没使用generator）
		mapper.writeValue(new File(fileName), strToJsonNode(jsonStr)); //测试覆盖写入，结果为覆盖
		
		mapper.writeValue(System.out, BeanDemo.getFullBean()); //将bean输出至OutputStream
		mapper.writeValue(System.out, strToJsonNode(jsonStr));	//这一句没有输出，想想为什么
		mapper.writeValue(generator, BeanDemo.getFullBean()); //使用generator将bean输出
		mapper.writeValue(generator, strToJsonNode(jsonStr)); //使用generator将JsonNode输出
	}
	//JsonGenerator测试，更高级的json api，支持流式写入等场景
	public static void JsonGeratorTest() throws Exception{
		Print.print("JsonGeratorTest()--------------------------------");
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "{\"age\":19,\"name\":\"周杰伦\",\"courses\":[\"高等数学\"]}";
		
		String fileName = JacksonTest.class.getResource("/").getPath() + "/testJsonFile_generator.json";
		FileOutputStream os = new FileOutputStream(new File(fileName));
		JsonGenerator generator = new JsonFactory().createGenerator(os);	//指定输出流
		mapper.writeTree(generator, strToJsonNode(jsonStr));	//使用generator将JsonNode输出至文件
		
		//流式输出的高级场景
		generator = new JsonFactory().createGenerator(System.out);
//		mapper.writeTree(generator, createJsonTreeMode());	//输出至System.out
		generator.writeStartObject();
		generator.writeStringField("name", "jzz");
		generator.writeNumberField("age", 20);
		generator.writeBooleanField("isMarry", false);
		generator.writeArrayFieldStart("likes");
			generator.writeString("apple");
			generator.writeString("banana");
			generator.writeStartObject();
				generator.writeStringField("isFresh", "true");
				generator.writeStringField("color", "red");
			generator.writeEndObject();
		generator.writeEndArray();	
		generator.writeEndObject();
		generator.flush();
		generator.close();
		
	}
	//测试动态修改json串
	 public static void testTreeModel() throws Exception {
		Print.print("testTreeModel()--------------------------------");
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
		beanToJsonStr();
//		beanToJsonStr2();
		strToBeans();
		createJsonTreeMode();
		testTreeModel();
		toJsonNodetest(BeanDemo.getFullBean());
		toBeanTest();
		strToMapTest();
		mapToJsonStrTest();
		JsonGeratorTest();
//		ObjectMapperTest();
	}
}
