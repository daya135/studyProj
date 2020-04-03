package org.jzz.study.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/* 非jackson耦合的bin版本 */
public class BeanDemo2 {
	
	public int id;
	public String name;
	public String nickname;
	public Date birth;
	public boolean isMarry;
	
	public static BeanDemo2 getFullBean() {
		BeanDemo2 bean = new BeanDemo2();
		bean.name = "testname";
		bean.nickname = "pig";
		bean.id = 123;
		bean.birth = new Date();
		bean.isMarry = true;
		return bean;
	}
	
	public static BeanDemo2 getPartBean() {
		BeanDemo2 bean = new BeanDemo2();
		bean.name = "testname";
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
		str.append(new SimpleDateFormat("mm-dd-yyyy HH:MM:SS").format(this.birth));
		str.append("; isMarry: ");
		str.append(this.isMarry);
		return str.toString();
	}
}
