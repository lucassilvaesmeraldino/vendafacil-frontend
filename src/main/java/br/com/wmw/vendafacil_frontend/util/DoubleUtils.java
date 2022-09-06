package br.com.wmw.vendafacil_frontend.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import totalcross.sys.Vm;

public class DoubleUtils {

	private DoubleUtils() {
	}
	
	public static Double round(Double dbValue, RoundingMode roundingMode){
		Double result = Double.valueOf(0);
		try {
			result = BigDecimal.valueOf(dbValue).setScale(2, roundingMode).doubleValue();
		} catch (ArithmeticException | IllegalArgumentException e) {
			Vm.debug(e.getMessage());
		}
		return result;
	}
	
	public static boolean isBetween(Double dbFirst, Double dbLast, Double dbValue) {
		return dbValue >= dbFirst && dbValue < dbLast;
	}
}
