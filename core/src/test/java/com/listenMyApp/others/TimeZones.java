package com.listenMyApp.others;

import java.util.TimeZone;

public class TimeZones {

	public static void main(String[] args){
		for (String idTimeZone : TimeZone.getAvailableIDs()){
			TimeZone timeZone = TimeZone.getTimeZone(idTimeZone);
			
			int rawOffset = timeZone.getRawOffset();
			int hour = rawOffset / (60*60*1000);
			int minute = Math.abs(rawOffset / (60*1000)) % 60;
			System.out.println(idTimeZone + " - " + timeZone.getDisplayName() + " " + hour + ":" + minute);
		}
	}
}
	
	
/*	TimeZone tz = TimeZone.getTimeZone(TimeZoneIds[i]);
	String tzName = tz.getDisplayName(tz.inDaylightTime(date), TimeZone.LONG);
	System.out.print(TimeZoneIds[i] + ":   ");
	// Get the number of hours from GMT
	int rawOffset = tz.getRawOffset();
	int hour = rawOffset / (60*60*1000);
	int minute = Math.abs(rawOffset / (60*1000)) % 60;
	System.out.println(tzName + " " + hour + ":" + minute);
*/	
