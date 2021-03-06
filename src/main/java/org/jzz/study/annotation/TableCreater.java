package org.jzz.study.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class TableCreater {
	public static String CreateTable(String className) throws ClassNotFoundException {
		String Sql = "CREATE TABLE ";
		Class<?> cl = Class.forName(className);
		DBTable dbTable = cl.getAnnotation(DBTable.class);	//此处传入的参数就相当于key,以查找目标类获得对应注解对象
		Sql += dbTable.name() == "" ? className.toUpperCase() : dbTable.name();	//调用注解对象的方法，获得一些信息
		Sql += " (\n"; 
		for (Field field : cl.getDeclaredFields()) {
			Annotation[] annotations = field.getAnnotations();
			if(annotations.length < 1) continue;
			if (annotations[0] instanceof SQLInteger) {
				SQLInteger sInt = (SQLInteger)annotations[0];
				Sql += "    ";
				Sql += sInt.value() == "" ? field.getName().toUpperCase() : sInt.value();
				Sql += " INT";
				Sql += ProcessConstrains(sInt.constrains());
				Sql += ",\n";
			}
			if (annotations[0] instanceof SQLString) {
				SQLString sString = (SQLString)annotations[0];
				Sql += "    ";
				Sql += sString.value() == "" ? field.getName().toUpperCase() : sString.value();
				Sql += " VARCHAR(";
				Sql += String.valueOf(sString.len());
				Sql += ")";
				Sql += ProcessConstrains(sString.constrains());
				Sql += ",\n";
			}
			
		}
		Sql = Sql.substring(0, Sql.lastIndexOf(","));
		Sql += "\n)";
		
		return Sql;
	}
	
	public static String ProcessConstrains(Constrains con) {
		String constrains = "";
		if (!con.allowNull()) {
			constrains += " NOT NULL";
		}
		if (con.primaryKey()) {
			constrains += " PRIMARY KEY";
		}
		if (con.unique()) {
			constrains += " UNIQUE";
		}
		return constrains;
	}
	
	public static void main(String args[]) throws ClassNotFoundException {
		System.out.println(CreateTable("org.jzz.study.annotation.TestBean"));
	}
}
