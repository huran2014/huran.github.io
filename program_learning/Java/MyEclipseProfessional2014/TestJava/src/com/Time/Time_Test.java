package com.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TimeOperation to=new TimeOperation();
		to.viewTimeLong();
		to.viewTimeDate();
		to.viewTimeCalendar();
		to.TimeDatetoString();
		to.TimeDatetoCalendar();
		to.TimeCalendartwoweeksagoSaturday();
		
	}

}

class TimeOperation
{
	private long TimeLong;
	private Date TimeDate;
	private Calendar TimeCalendar;
	
	public TimeOperation() {
		super();
		this.TimeLong = System.currentTimeMillis();
		this.TimeDate = new Date(this.TimeLong);
		this.TimeCalendar = Calendar.getInstance();
	}
	
	public void viewTimeLong()
	{
		System.out.println("TimeLong is "+this.TimeLong);
	}
	
	public void viewTimeDate()
	{
		System.out.println("TimeDate is "+this.TimeDate);
	}
	
	public void viewTimeCalendar()
	{
		System.out.println("TimeCalendar is "+this.TimeCalendar.getTime().toString());
	}
	
	public void TimeStringtoDate(String time) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		System.out.println("TimeStringtoDate of SimpleDateFormat.parse is "+sdf.parse(time));
	}
	
	public void TimeDatetoString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		System.out.println("TimeDatetoString of SimpleDateFormat.format is "+sdf.format(this.TimeDate));
	}
	
	public void TimeDatetoCalendar()
	{
		this.TimeCalendar.setTime(this.TimeDate);
		System.out.println("TimeDatetoCalendar of setTime is "+this.TimeCalendar.getTime().toString());
	}
	
	public void TimeCalendartoDate()
	{
		System.out.println("TimeCalendartoDate of getTime is "+this.TimeCalendar.getTime().toString());
	}
	
	public void TimeCalendartwoweeksagoSaturday()
	{
		this.TimeCalendar.add(Calendar.WEEK_OF_MONTH, -2);
		this.TimeCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		System.out.println("the saturday of 2 weeks before is "+this.TimeCalendar.getTime().toString());
	}
}