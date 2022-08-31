package br.com.wmw.vendafacil_frontend.util;

import totalcross.ui.font.Font;

public class Fonts {
	public static final int FONT_DEFAULT_SIZE = 12;

	public static Font latoMediumDefaultSize;
	public static Font latoMediumPlus1;
	public static Font latoMediumPlus2;
	public static Font latoMediumPlus4;
	public static Font latoMediumMinus1;
	public static Font latoMediumMinus2;
	public static Font latoMediumMinus4;

	public static Font latoBoldDefaultSize;
	public static Font latoBoldMinus1;
	public static Font latoBoldMinus2;
	public static Font latoBoldMinus4;
	public static Font latoBoldPlus1;
	public static Font latoBoldPlus2;
	public static Font latoBoldPlus4;
	public static Font latoBoldPlus6;
	public static Font latoBoldPlus8;
	public static Font latoBoldPlus12;

	public static Font latoLightDefaultSize;
	public static Font latoLightPlus1;
	public static Font latoLightPlus2;
	public static Font latoLightPlus4;
	public static Font latoLightPlus6;
	public static Font latoLightMinus1;
	public static Font latoLightMinus2;
	public static Font latoLightMinus4;

	public static Font latoRegularMinus5;
	public static Font latoRegularDefaultSize;

	static {

		// Lato Regular
		Fonts.latoRegularDefaultSize = Font.getFont("fonts/Lato Regular", false, Fonts.FONT_DEFAULT_SIZE);
		Fonts.latoRegularMinus5 = Fonts.latoRegularDefaultSize.adjustedBy(-5);

		// Lato Medium
		Fonts.latoMediumDefaultSize = Font.getFont("fonts/Lato Medium", false, Fonts.FONT_DEFAULT_SIZE);
		Fonts.latoMediumPlus1 = Fonts.latoMediumDefaultSize.adjustedBy(1);
		Fonts.latoMediumPlus2 = Fonts.latoMediumDefaultSize.adjustedBy(2);
		Fonts.latoMediumPlus4 = Fonts.latoMediumDefaultSize.adjustedBy(4);
		Fonts.latoMediumMinus1 = Fonts.latoMediumDefaultSize.adjustedBy(-1);
		Fonts.latoMediumMinus2 = Fonts.latoMediumDefaultSize.adjustedBy(-2);
		Fonts.latoMediumMinus4 = Fonts.latoMediumDefaultSize.adjustedBy(-4);
		// Lato Bold
		Fonts.latoBoldDefaultSize = Font.getFont("fonts/Lato Bold", false, Fonts.FONT_DEFAULT_SIZE);
		Fonts.latoBoldPlus1 = Fonts.latoMediumDefaultSize.adjustedBy(1);
		Fonts.latoBoldPlus2 = Fonts.latoMediumDefaultSize.adjustedBy(2);
		Fonts.latoBoldPlus4 = Fonts.latoMediumDefaultSize.adjustedBy(4);
		Fonts.latoBoldPlus6 = Fonts.latoMediumDefaultSize.adjustedBy(6);
		Fonts.latoBoldPlus8 = Fonts.latoMediumDefaultSize.adjustedBy(8);
		Fonts.latoBoldPlus12 = Fonts.latoMediumDefaultSize.adjustedBy(12);
		Fonts.latoBoldMinus1 = Fonts.latoMediumDefaultSize.adjustedBy(-1);
		Fonts.latoBoldMinus2 = Fonts.latoMediumDefaultSize.adjustedBy(-2);
		Fonts.latoBoldMinus4 = Fonts.latoMediumDefaultSize.adjustedBy(-4);
		// Lato Light
		Fonts.latoLightDefaultSize = Font.getFont("fonts/Lato Light", false, Fonts.FONT_DEFAULT_SIZE);
		Fonts.latoLightPlus1 = Fonts.latoLightDefaultSize.adjustedBy(1);
		Fonts.latoLightPlus2 = Fonts.latoLightDefaultSize.adjustedBy(2);
		Fonts.latoLightPlus4 = Fonts.latoLightDefaultSize.adjustedBy(4);
		Fonts.latoLightPlus6 = Fonts.latoLightDefaultSize.adjustedBy(6);
		Fonts.latoLightMinus1 = Fonts.latoLightDefaultSize.adjustedBy(-1);
		Fonts.latoLightMinus2 = Fonts.latoLightDefaultSize.adjustedBy(-2);
		Fonts.latoLightMinus4 = Fonts.latoLightDefaultSize.adjustedBy(-4);
	}
}
