package br.com.wmw.vendafacil_frontend.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import totalcross.sys.Settings;
import totalcross.unit.TestCase;
import totalcross.util.Date;

public class DateUtilsTest extends TestCase{

	private static final String DATE_DMY = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	private static final String DATE_DMY_LATER = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	private static final String DATE_DMY_BEFORE = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

	public void shouldConvertStringDateToDate() {
		Date date = DateUtils.convert(DATE_DMY, Settings.DATE_DMY);
		String strDate = date.toString(Settings.DATE_DMY, "/");
		assertEquals(DATE_DMY, strDate);
	}
	
	public void shouldReturnTrueWhenDateIsTheSameAsTheCurrentDate() {
		assertTrue(DateUtils.isPresentOrFutureDate(DATE_DMY, Settings.DATE_DMY));
	}
	
	public void shouldReturnTrueWhenDateIsLaterThanTheCurrentDate() {
		assertTrue(DateUtils.isPresentOrFutureDate(DATE_DMY_LATER, Settings.DATE_DMY));
	}
	
	public void shouldReturnFalseWhenDateIsBeforeTheCurrentDate() {
		assertTrue(!DateUtils.isPresentOrFutureDate(DATE_DMY_BEFORE, Settings.DATE_DMY));
	}

	@Override
	public void testRun() {
		shouldConvertStringDateToDate();
		shouldReturnTrueWhenDateIsTheSameAsTheCurrentDate();
		shouldReturnTrueWhenDateIsLaterThanTheCurrentDate();
		shouldReturnFalseWhenDateIsBeforeTheCurrentDate();
	}
}
