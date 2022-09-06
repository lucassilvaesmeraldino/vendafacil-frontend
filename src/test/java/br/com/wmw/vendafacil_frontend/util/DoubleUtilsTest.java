package br.com.wmw.vendafacil_frontend.util;


import java.math.BigDecimal;
import java.math.RoundingMode;

import totalcross.unit.TestCase;

public class DoubleUtilsTest extends TestCase{

	private static final Double DOUBLE_VALUE = Double.valueOf(25.489632514022);
	private static final Double SECOND_DOUBLE_VALUE = Double.valueOf(101.5087);
	private static final Double FIRST_DOUBLE_SEQUENCE = Double.valueOf(0);
	private static final Double SECOND_DOUBLE_SEQUENCE = Double.valueOf(100);
	private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

	public void shouldRoundTheDoubleValue() {
		Double doubleRounded = BigDecimal.valueOf(DOUBLE_VALUE).setScale(2, ROUNDING_MODE).doubleValue();
		assertEquals(doubleRounded, DoubleUtils.round(DOUBLE_VALUE, ROUNDING_MODE));
	}
	
	public void shouldReturnTrueWhenTheDoubleValueIsBetweenPastValues() {
		assertTrue(DoubleUtils.isBetween(FIRST_DOUBLE_SEQUENCE, SECOND_DOUBLE_SEQUENCE, DOUBLE_VALUE));
	}
	
	public void shouldReturnFalseWhenTheDoubleValueIsNotBetweenPastValues() {
		assertTrue(!DoubleUtils.isBetween(FIRST_DOUBLE_SEQUENCE, SECOND_DOUBLE_SEQUENCE, SECOND_DOUBLE_VALUE));
	}

	@Override
	public void testRun() {
		shouldRoundTheDoubleValue();
		shouldReturnTrueWhenTheDoubleValueIsBetweenPastValues();
		shouldReturnFalseWhenTheDoubleValueIsNotBetweenPastValues();
	}
}
