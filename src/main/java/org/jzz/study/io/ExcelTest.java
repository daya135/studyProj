package org.jzz.study.io;

import org.jzz.study.util.Print;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import jxl.*;

public class ExcelTest {
	 public static void readExcel() {
	      File file=  new File("D:\\Desktop\\subwayKnowledge.xls");
	      Print.print("file="+file.getAbsolutePath());
	        try {
	            InputStream is = new FileInputStream(file);
	            Workbook book = Workbook.getWorkbook(is);
	            book.getNumberOfSheets();
	            Sheet sheet = book.getSheet(0);
	            int Rows = sheet.getRows();
	            Print.print("列数" + sheet.getColumns());
	 
	            for (int i = 0; i < Rows; ++i) {
	            	
	                String name = (sheet.getCell(0, i)).getContents();
	                String department = (sheet.getCell(1, i)).getContents();
	                String company = (sheet.getCell(2, i)).getContents();
	                String phone = (sheet.getCell(3, i)).getContents();
	 
	                Print.print("第"+i+"行数据="+name+","+department+","+company+","+phone);
	 
	            }
	            book.close();
	 
	        } catch (Exception e) {
	 
	        	Print.print("e"+e.getMessage());
	        }
	 }
	 
	 public static void main(String[] args) {
		 readExcel();
	 }
	
}
