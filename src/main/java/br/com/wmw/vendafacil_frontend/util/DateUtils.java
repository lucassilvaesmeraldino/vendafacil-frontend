package br.com.wmw.vendafacil_frontend.util;

import totalcross.sys.Vm;
import totalcross.util.Date;
import totalcross.util.InvalidDateException;

public class DateUtils {
	
	private DateUtils() {
	}

	public static Date convert(String stringDate, Byte formatDate) {
		Date date = new Date();
		try {
			date = new Date(stringDate, formatDate);
		} catch (InvalidDateException e) {
			Vm.debug(e.getMessage());
		}
		return date;
	}
	
	public static boolean isPresentOrFutureDate(String stringDate, Byte formatDate) {
		Date date = DateUtils.convert(stringDate, formatDate);
		return date.isAfter(new Date()) || date.equals(new Date());
	}
}
