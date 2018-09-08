package org.jzz.study.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/* 两种序列化日期格式的注解 */
public class BeanDemo {
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
	
	private int id;
	private String name;
	private String nickname;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birth;
	
	public static BeanDemo getFullBean() {
		BeanDemo bean = new BeanDemo();
		bean.setName("testname");
		bean.setNickname("pig");
		bean.setId(123);
		bean.setBirth(new Date());
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
		str.append(format.format(this.birth));
		return str.toString();
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
}
