package br.com.wmw.vendafacil_frontend.util;

import totalcross.ui.font.Font;

public class Fonts {
	
	public static final int FONT_DEFAULT_SIZE = 12;

	public static final Font latoMediumDefaultSize = Font.getFont("fonts/Lato Medium", false, Fonts.FONT_DEFAULT_SIZE);
	public static final Font latoMediumPlus1 = Fonts.latoMediumDefaultSize.adjustedBy(1);
	public static final Font latoMediumPlus2 = Fonts.latoMediumDefaultSize.adjustedBy(2);
	public static final Font latoMediumPlus4 = Fonts.latoMediumDefaultSize.adjustedBy(4);
	public static final Font latoMediumMinus1 = Fonts.latoMediumDefaultSize.adjustedBy(-1);
	public static final Font latoMediumMinus2 = Fonts.latoMediumDefaultSize.adjustedBy(-2);
	public static final Font latoMediumMinus4 = Fonts.latoMediumDefaultSize.adjustedBy(-4);

	public static final Font latoBoldDefaultSize = Font.getFont("fonts/Lato Bold", false, Fonts.FONT_DEFAULT_SIZE);
	public static final Font latoBoldMinus1 = Fonts.latoMediumDefaultSize.adjustedBy(-1);
	public static final Font latoBoldMinus2 = Fonts.latoMediumDefaultSize.adjustedBy(-2);
	public static final Font latoBoldMinus4 = Fonts.latoMediumDefaultSize.adjustedBy(-4);
	public static final Font latoBoldPlus1 = Fonts.latoMediumDefaultSize.adjustedBy(1);
	public static final Font latoBoldPlus2 = Fonts.latoMediumDefaultSize.adjustedBy(2);
	public static final Font latoBoldPlus4 = Fonts.latoMediumDefaultSize.adjustedBy(4);
	public static final Font latoBoldPlus6 = Fonts.latoMediumDefaultSize.adjustedBy(6);
	public static final Font latoBoldPlus8 = Fonts.latoMediumDefaultSize.adjustedBy(8);
	public static final Font latoBoldPlus12 = Fonts.latoMediumDefaultSize.adjustedBy(12);

	public static final Font latoLightDefaultSize = Font.getFont("fonts/Lato Light", false, Fonts.FONT_DEFAULT_SIZE);
	public static final Font latoLightPlus1 = Fonts.latoLightDefaultSize.adjustedBy(1);
	public static final Font latoLightPlus2 = Fonts.latoLightDefaultSize.adjustedBy(2);
	public static final Font latoLightPlus4 = Fonts.latoLightDefaultSize.adjustedBy(4);
	public static final Font latoLightPlus6 = Fonts.latoLightDefaultSize.adjustedBy(6);
	public static final Font latoLightMinus1 = Fonts.latoLightDefaultSize.adjustedBy(-1);
	public static final Font latoLightMinus2 = Fonts.latoLightDefaultSize.adjustedBy(-2);
	public static final Font latoLightMinus4 = Fonts.latoLightDefaultSize.adjustedBy(-4);

	public static final Font latoRegularDefaultSize = Font.getFont("fonts/Lato Regular", false, Fonts.FONT_DEFAULT_SIZE);
	public static final Font latoRegularMinus5 = Fonts.latoRegularDefaultSize.adjustedBy(-5);
	
	private Fonts() {		
	}

}
