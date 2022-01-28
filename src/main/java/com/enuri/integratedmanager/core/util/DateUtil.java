package com.enuri.integratedmanager.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	public static String getDateString(String format) {
		LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
	    return now.format(dateTimeFormatter);
	}
}
