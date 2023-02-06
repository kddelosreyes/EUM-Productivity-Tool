package com.project.eum.prodtool.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateTimeUtils {
	
	public static final String MMDDYYYY = "MM/dd/yyyy";

	private static final DateFormat SQL_DATE_FORMATTER = new SimpleDateFormat("YYYY-MM-dd");
	
	public static LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
		return timestamp == null ?
				null : 
				new Date(timestamp.getTime())
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime()
				.truncatedTo(ChronoUnit.MINUTES);
	}
	
	public static LocalDate convertDateToLocalDate(java.sql.Date date) {
		return date.toLocalDate();
	}
	
	public static Date convertLocalDateToDate(LocalDate localDate) {
		return Date.from(localDate
				.atStartOfDay(ZoneId
						.systemDefault())
				.toInstant());
	}
	
	public static LocalTime convertTimeToLocalTime(Time time) {
		return time.toLocalTime();
	}
	
	public static LocalDate convertDateToLocalDate(Date date) {
		return date.toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
	}
	
	public static Long minutesInBetween(LocalDateTime start, LocalDateTime end) {
		if (end == null) {
			return 0L;
		} else {
			return ChronoUnit.MINUTES.between(start, end);
		}
	}
	
	public static Boolean isInBetweenExclusive(LocalTime target, LocalTime start, LocalTime end) {
		return target.isAfter(start) && target.isBefore(end);
	}
	
	public static Boolean isTimeOnOrBefore(LocalTime target, LocalTime start) {
		return target.isBefore(start) || target.equals(start);
	}
	
	public static String toSqlDateString(LocalDate localDate) {
		return SQL_DATE_FORMATTER.format(convertLocalDateToDate(localDate));
	}
	
	public static String toSqlDateString(Date date) {
		return SQL_DATE_FORMATTER.format(date);
	}
	
	public static Date toDateFormat(String strDate, String format) {
		try {
			return new SimpleDateFormat(format).parse(strDate);
		} catch (ParseException exc) {
			return null;
		}
	}
	
	public static String formatTime(LocalTime time) {
		if (time == null) {
			return "NT";
		}
		return time.format(DateTimeFormatter.ofPattern("hh:mm a"));
	}
	
	public static boolean isWeekend(LocalDate date) {
		int dayOfWeek = date.get(ChronoField.DAY_OF_WEEK);
		return dayOfWeek == 6 || dayOfWeek == 7;
	}
	
	public static boolean hasOverlap(LocalDate firstInit, LocalDate firstEnd, LocalDate secondInit,
			LocalDate secondEnd) {
		if ((firstInit.isBefore(secondInit) && firstEnd.isBefore(secondInit) && firstInit.isBefore(secondEnd) && firstEnd.isBefore(secondEnd)) ||
				(secondInit.isBefore(firstInit) && secondEnd.isBefore(firstInit) && secondInit.isBefore(firstEnd) && secondEnd.isBefore(firstEnd))) {
			return false;
		} else {
			return true;
		}
	}
	
}
