package org.jzz.study.java8;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import org.jzz.study.util.Print;

public class DateTimeApiTest {
	public static void main(String[] args) {
		final Clock clock = Clock.systemUTC();	//返回时间根据系统时钟和UTC(世界标准时间)
		Print.print(clock.instant());
		Print.print(clock.millis());
		
		final LocalDate localDate = LocalDate.now();
		final LocalDate dateFromClock = LocalDate.now( clock );
		System.out.println( localDate );
        System.out.println( dateFromClock );
        
        // Get the local date and local time
        final LocalTime time = LocalTime.now();
        final LocalTime timeFromClock = LocalTime.now( clock );
        System.out.println( time );
        System.out.println( timeFromClock );
        
        Print.print("闰年检测 " + localDate.toString()  + " "+ localDate.isLeapYear()); 
        testDate();
	}
	
	
	static void testDate() {
		Date date = new Date();
		date.getYear();	//这个方法已经过时了,用Calendar替换
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Print.print(calendar.get(Calendar.YEAR));
		
		Print.print(1 << 3);
	}
}
