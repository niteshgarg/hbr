package com.oneminuut.hbr.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.oneminuut.hbr.exception.HBRException;

public class HBRUtil {

	private static final Logger logger = Logger.getLogger(HBRUtil.class);

	/*
	 * Method returns the stack trace of exception in string format. Used for
	 * logging of exception.
	 */
	public static String getExceptionDescriptionString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		return stringWriter.toString();
	}

	/*
	 * Method will return comma separated string of Errors
	 * 
	 * @param result
	 * 
	 * @return
	 */
	public static String getErrorString(BindingResult result) {
		StringBuilder builder = new StringBuilder("");
		List<FieldError> fieldErrors = result.getFieldErrors();
		int size = fieldErrors.size();
		int counter = 0;

		for (FieldError fieldError : fieldErrors) {			
			counter++;
			
			if(fieldError.getDefaultMessage() != null) {
				builder.append(fieldError.getDefaultMessage());
			} else {
				builder.append((fieldError.getCodes())[3]);
			}
								
			if (counter < size) {
				builder.append(",");
			}
		}
		return builder.toString();
	}

	public static String getIPAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	/**
	 * Utility method to convert a String to date
	 * 
	 * @param str
	 *            Date string to convert
	 * @param formatString
	 *            format like 'MM-dd-yyyy'
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String str, String formatString) {
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(formatString);
			date = (Date) dateFormat.parse(str);
		} catch (ParseException e) {
			logger.fatal(getExceptionDescriptionString(e));
			throw new HBRException();
		}
		return date;
	}

	public static String convertDateToString(Date date) {
		String dateStr = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateStr = dateFormat.format(date);
		return dateStr;
	}
	

	public static double calculateDiscount(BigDecimal salePrice,
			BigDecimal retailPrice) {

		BigDecimal discount = retailPrice.subtract(salePrice);
		BigDecimal discountFraction = discount.divide(retailPrice,
				new MathContext(4));
		BigDecimal discountPercent = discountFraction.multiply(new BigDecimal(
				100), new MathContext(4));
		return discountPercent.doubleValue();
	}
	
	
	public static String convertDateToString(Date date, String pattern) {
		String dateStr = "";
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		dateStr = dateFormat.format(date);
		return dateStr;
	}
	
	public static String getDealTimeToExpire(Date date) {

		long milliSeconds = date.getTime() - new Date().getTime();
		long minutes = milliSeconds / (60 * 1000);
		long hours = milliSeconds / (60 * 60 * 1000);
		int days = (int) (hours / 24);

		String ageString = "";

		String ageDays = null;
		String ageHours = null;
		String ageMinutes = null;
		
		if (days > 0) {
			ageDays = days == 1 ? days + " day" : days + " days";
		}
		
		if (hours > 0) {
			hours = hours - (24 * days);
			ageHours = hours == 1 ? hours + " hour" : hours + " hours";
		}
		if (minutes > 0) {
			minutes = minutes - (60 * (hours + (24 * days)));
			ageMinutes = minutes == 1 ? minutes + " minute" : minutes
					+ " minutes";
		}

		if (ageDays != null) {
			ageString = ageDays;
		}
		if (ageHours != null) {
			if ("".equalsIgnoreCase(ageString)) {
				ageString = ageHours;
			} else {
				ageString = ageString + " " + ageHours;
			}
		}
		if (ageMinutes != null) {
			if ("".equalsIgnoreCase(ageString)) {
				ageString = ageMinutes;
			} else {
				ageString = ageString + " " + ageMinutes;
			}
		}

		if (!"".equalsIgnoreCase(ageString)) {
			ageString = ageString + " " + "remaining";
		}
		
		if ("".equalsIgnoreCase(ageString)) {
			ageString = "Deal Expired";
		}
		
		return ageString;
	}	
}
