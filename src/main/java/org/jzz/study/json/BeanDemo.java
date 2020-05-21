package org.jzz.study.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/* jackson默认的字段属性发现规则如下：
 * 所有被public修饰的字段->public getter-> public setter  ！！！！！！
 * 在生产中要注意字段名称的映射！！！
*/
public class BeanDemo {
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy HH:MM:SS");
	
	private int id;
	private String name;
	private String nickname;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birth;
	private boolean isMarry;
	
	//当字段为private时，get set方法与json序列化有关系！！jackson json串字段属性名称会按照get（其次是setter）来，不一致的话会造成反序列化失败
	//可以用jackson的注解来调节
//	public boolean isMarry() {
//		return isMarry;
//	}
//	public void setMarry(boolean isMarry) {
//		this.isMarry = isMarry;
//	}
	public boolean getIsMarry() {
		return isMarry;
	}
	public void setIsMarry(boolean isMarry) {
		this.isMarry = isMarry;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
//	@JsonSerialize(using=JsonDateSerializer.class)  
	public Date getBirth() {
		return birth;
	}
	
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public static BeanDemo getFullBean() {
		BeanDemo bean = new BeanDemo();
		bean.setName("testname");
		bean.setNickname("pig");
		bean.setId(123);
		bean.setBirth(new Date());
		bean.setIsMarry(true);
		return bean;
	}
	
	public static BeanDemo getPartBean() {
		BeanDemo bean = new BeanDemo();
		bean.setName("testname");
		bean.setId(123);
		bean.setBirth(new Date());
		return bean;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer("id: ");
		str.append(this.id);
		str.append("; name: ");
		str.append(this.name);
		str.append("; nickname: ");
		str.append(this.nickname);
		str.append("; birth: ");
		str.append(dateFormat.format(this.birth));
		str.append("; isMarry: ");
		str.append(this.isMarry);
		return str.toString();
	}
}
