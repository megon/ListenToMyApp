package com.listenMyApp.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class TimezoneDTO {
	
	static private TimezoneDTO instance = null;
	static private Map<String, String> availableTimeZones = new LinkedHashMap<String, String>();
	
	static{
		List<String> timezones = new ArrayList<String>();
		timezones.add("Pacific/Midway");
		timezones.add("Pacific/Samoa");
		timezones.add("US/Hawaii");
		timezones.add("US/Alaska");
		timezones.add("America/Tijuana");
		timezones.add("US/Arizona");
		timezones.add("America/Chihuahua");
		timezones.add("America/Mazatlan");
		timezones.add("Canada/Central");
		timezones.add("US/Central");
		timezones.add("America/Mexico_City");
		timezones.add("America/Monterrey");
		timezones.add("Canada/Saskatchewan");
		timezones.add("America/Bogota");
		timezones.add("Canada/Eastern");
		timezones.add("US/East-Indiana");
		timezones.add("America/Lima");
		timezones.add("America/Caracas");
		timezones.add("America/La_Paz");
		timezones.add("America/Santiago");
		timezones.add("Canada/Newfoundland");
		timezones.add("America/Sao_Paulo");
		timezones.add("America/Argentina/Buenos_Aires");
		timezones.add("Atlantic/Azores");
		timezones.add("Atlantic/Cape_Verde");
		timezones.add("Africa/Casablanca");
		timezones.add("Europe/Dublin");   
		timezones.add("Europe/Lisbon");   
		timezones.add("Europe/London");   
		timezones.add("Africa/Monrovia");   
		timezones.add("Etc/UTC");   
		timezones.add("Europe/Amsterdam");   
		timezones.add("Europe/Belgrade");   
		timezones.add("Europe/Berlin");   
		timezones.add("Europe/Bratislava"); 
		timezones.add("Europe/Brussels");   
		timezones.add("Europe/Budapest");   
		timezones.add("Europe/Copenhagen"); 
		timezones.add("Europe/Gibraltar");  
		timezones.add("Europe/Ljubljana");  
		timezones.add("Europe/Luxembourg"); 
		timezones.add("Europe/Madrid");   
		timezones.add("Europe/Malta");   
		timezones.add("Europe/Monaco");  
		timezones.add("Europe/Oslo");   
		timezones.add("Europe/Paris");  
		timezones.add("Europe/Podgorica");   
		timezones.add("Europe/Prague");   
		timezones.add("Europe/Rome");   
		timezones.add("Europe/San_Marino");   
		timezones.add("Europe/Sarajevo");   
		timezones.add("Europe/Skopje");   
		timezones.add("Europe/Stockholm");
		timezones.add("Europe/Tirane");   
		timezones.add("Europe/Vaduz");   
		timezones.add("Europe/Vatican"); 
		timezones.add("Europe/Vienna");  
		timezones.add("Europe/Warsaw");  
		timezones.add("Europe/Zagreb");  
		timezones.add("Europe/Zurich");  
		timezones.add("Poland");   
		timezones.add("Africa/Blantyre");   
		timezones.add("Africa/Bujumbura");  
		timezones.add("Africa/Cairo");   
		timezones.add("Africa/Gaborone");
		timezones.add("Africa/Harare");  
		timezones.add("Africa/Johannesburg");   
		timezones.add("Africa/Kigali");   
		timezones.add("Africa/Lubumbashi"); 
		timezones.add("Africa/Lusaka");   
		timezones.add("Africa/Maputo");   
		timezones.add("Africa/Maseru");   
		timezones.add("Africa/Mbabane");  
		timezones.add("Africa/Tripoli");  
		timezones.add("Asia/Amman");   
		timezones.add("Asia/Beirut");  
		timezones.add("Asia/Damascus");
		timezones.add("Asia/Gaza");   
		timezones.add("Asia/Istanbul");   
		timezones.add("Asia/Jerusalem");  
		timezones.add("Asia/Nicosia");   
		timezones.add("Asia/Tel_Aviv");  
		timezones.add("Egypt");   
		timezones.add("Europe/Athens");   
		timezones.add("Europe/Bucharest");
		timezones.add("Europe/Chisinau"); 
		timezones.add("Europe/Helsinki"); 
		timezones.add("Europe/Istanbul"); 
		timezones.add("Europe/Kaliningrad"); 
		timezones.add("Europe/Kiev");   
		timezones.add("Europe/Mariehamn");   
		timezones.add("Europe/Minsk");   
		timezones.add("Europe/Nicosia"); 
		timezones.add("Europe/Riga");   
		timezones.add("Europe/Simferopol");   
		timezones.add("Europe/Sofia");   
		timezones.add("Europe/Tallinn"); 
		timezones.add("Europe/Tiraspol");
		timezones.add("Europe/Uzhgorod");
		timezones.add("Europe/Vilnius"); 
		timezones.add("Europe/Zaporozhye");   
		timezones.add("Asia/Baghdad");   
		timezones.add("Asia/Bahrain");   
		timezones.add("Asia/Kuwait");   
		timezones.add("Asia/Qatar");   
		timezones.add("Asia/Riyadh");  
		timezones.add("Europe/Moscow");
		timezones.add("Asia/Dubai");   
		timezones.add("Asia/Kabul");   
		timezones.add("Asia/Jakarta"); 
		timezones.add("Asia/Saigon");  
		timezones.add("Asia/Vientiane");   
		timezones.add("Asia/Hong_Kong");   
		timezones.add("Asia/Kuala_Lumpur");
		timezones.add("Asia/Kuching");   
		timezones.add("Asia/Macao");   
		timezones.add("Asia/Macau");   
		timezones.add("Asia/Shanghai");
		timezones.add("Asia/Singapore");
		timezones.add("Asia/Taipei");   
		timezones.add("Asia/Seoul");   
		timezones.add("Asia/Tokyo");   
		timezones.add("Australia/Adelaide");
		timezones.add("Asia/Vladivostok");  
		timezones.add("Australia/Brisbane");
		timezones.add("Australia/Canberra");
		timezones.add("Australia/Currie");  
		timezones.add("Australia/Melbourne");   
		timezones.add("Australia/Queensland");  
		timezones.add("Australia/Sydney");
		timezones.add("Pacific/Honolulu");
		
		TimeZone timezone;
		
		for (String key : timezones){
			timezone = TimeZone.getTimeZone(key);
			
			int rawOffset = timezone.getRawOffset();
			int hour = rawOffset / (60*60*1000);
			int minute = Math.abs(rawOffset / (60*1000)) % 60;
			if (hour <0){
				availableTimeZones.put("(" + timezone.getDisplayName() + " " + String.format("%03d", hour) + ":" + String.format("%02d", minute) + ")", key);
			}
			else{
				availableTimeZones.put("(" + timezone.getDisplayName() + " " + String.format("%02d", hour) + ":" + String.format("%02d", minute) + ")", key);
			}
			
		}
		
	}
	
	protected TimezoneDTO(){}
	
	static public TimezoneDTO instance(){
		if (instance == null){
			return new TimezoneDTO();
		}
		else {
			return instance;
		}
	}
	
	public Map<String, String> getAvailableTimeZones(){
		return availableTimeZones;
	}
}
