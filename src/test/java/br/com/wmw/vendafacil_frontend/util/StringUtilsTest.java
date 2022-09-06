package br.com.wmw.vendafacil_frontend.util;

import totalcross.unit.TestCase;

public class StringUtilsTest extends TestCase{
	
	private static final String STRING = "Um produto de qualidade e com um preço sensacional!";

	public void shouldReturnAReducedStringWhenIsLargerThanTheSpecifiedSize() {
		String stringReduced = STRING.substring(0, 20).concat("...");
		assertEquals(stringReduced, StringUtils.reduceLength(STRING, 22));
	}
	
	public void shouldReturnTheSameStringWhenIsSmallerThanTheSpecifiedSize() {
		assertEquals(STRING, StringUtils.reduceLength(STRING, 70));
	}

	@Override
	public void testRun() {
		shouldReturnAReducedStringWhenIsLargerThanTheSpecifiedSize();
		shouldReturnTheSameStringWhenIsSmallerThanTheSpecifiedSize();
	}

}
