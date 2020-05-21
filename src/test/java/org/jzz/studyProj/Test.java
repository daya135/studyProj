package org.jzz.studyProj;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.jzz.study.DataStructure.BaseNode;
import org.jzz.study.DataStructure.HuffmanTree;
import org.jzz.study.DataStructure.Tree;
import org.jzz.study.annotation.Constrains;
import org.jzz.study.annotation.DBTable;
import org.jzz.study.annotation.SQLInteger;
import org.jzz.study.annotation.SQLString;
import org.jzz.study.annotation.TableCreater;
import org.jzz.study.util.Print;


public class Test {
	
	@DBTable(name="mybeantable")
	static class mybean {
		@SQLInteger(value = "id", constrains = @Constrains(primaryKey = true))
		int id;
		@SQLInteger(value = "addreess_id", constrains = @Constrains(allowNull = false))
		int address_id;
		@SQLString(value = "name")
		String name;
	}
	
	static public void testMyAnotation() {
		Class<?> beanClass = mybean.class;
		DBTable table = beanClass.getAnnotation(DBTable.class);
		String sql = "CREATE TABLE " + table.name();
		sql += "(\n";
		for (Field field : beanClass.getDeclaredFields()) {
			Annotation[] annotations = field.getAnnotations();
			if (annotations[0] instanceof SQLInteger) {
				sql += "    ";
				SQLInteger sInteger = (SQLInteger)annotations[0];
				sql += sInteger.value();
				sql += " INT";
				sql += TableCreater.ProcessConstrains(sInteger.constrains());
				sql += ",\n";
			}
			if (annotations[0] instanceof SQLString) {
				sql += "    ";
				SQLString sString = (SQLString)annotations[0];
				sql += sString.value();
				sql += " VARCHAR";
				sql += TableCreater.ProcessConstrains(sString.constrains());
				sql += ",\n";
			}
		}
		sql += ")";
		Print.print(sql);
	}
	
	public static void main(String[] args) {
		String site = "出口|卫生间.aaa";
//		Print.print(site.indexOf("\\."));
//		Print.print(site.substring(site.indexOf(".") + 1));
//		StringBuilder sBuffer = new StringBuilder(site);
//		String[] sites = sBuffer.toString().split("|");
//        for (String s : sites) {
//            Print.print( "读取sit内容:" + s);
//        }
		
//		Print.printArr(c, "\n");

//		Print.print(UUID.randomUUID()); //生成唯一uuid
//		
		Print.print(~1);
		
		Print.print(Double.MAX_VALUE / 100);
		int i = 1;
//		
		Print.print(String.format("%2.1f", 123456.234567));
//		testMyAnotation();
		
	}

}
