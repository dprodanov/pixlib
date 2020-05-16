package ijaux.compat;


import java.awt.Color;
import java.io.*;
import java.lang.*;

import ijaux.datatype.LinearSpaceReflexive;
 
public class RGB extends Number 
implements Serializable, Cloneable, LinearSpaceReflexive<RGB, RGB> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9051175080873187602L;
	protected int r=0;
	protected int g=0;
	protected int b=0;
	protected int argb=0;
	
	private int mode;
	
	public static final int R=0xFF0000, G=0x00FF00, B=0x0000FF, I=0x80;
	
	public RGB(int val) {
		argb=val;
		
		r= (argb & R) >> 16;
		g= (argb & G) >> 8;
		b=  argb & B;
	}

	
	public static RGB fromRGB(final int argb) {
		int r= (argb & R) >> 16;
		int g= (argb & G) >> 8;
		int b=  argb & B;
		return new RGB(r,g,b);
	}
	
	public static int toRGB(int r, int g, int b) {
				return ( r << 16 ) | ( g << 8 ) | b;
	}
	
	public RGB(byte a, byte b, byte c) {
		r=a;
		g=b;
		b=c;
		argb=toRGB(a, b, c);
	}
	
	public RGB(int a, int b, int c) {
		r=a & 0xFF;
		g=b & 0xFF;
		c=c & 0xFF;
		argb=toRGB(a, b, c);
	}

	public int R() {
		return r;
	}

	public int G() {
		return g;
	}

	public int B() {
		return b;
	}

	public void setMode(int m) {
		mode=m;
	}
	
	@Override
	public double doubleValue() {
		return 0;
	}

	@Override
	public float floatValue() {
		return 0;
	}

	@Override
	public int intValue() {
		return argb;
	}

	@Override
	public long longValue() {
		return argb;
	}

	@Override
	public void add(RGB vt) {
		r+=vt.R(); 
		g+=vt.G(); 
		b+=vt.B();
	}

	@Override
	public void inv() {
		r=0xFF-r; 
		g=0xFF-g; 
		b= 0xFF-b;
	}	
	
	/*
	public RGB inv() {
		return new RGB(0xFF-r, 0xFF-g, 0xFF-b);
	}
*/
	@Override
	public void scale(double scalar) {
		r=(int)scalar*r; 
		g=(int)scalar*g; 
		b=(int)scalar*b;
	}

	@Override
	public void sub(RGB vt) {
		r-=vt.R(); 
		g-=vt.G();
		b-=vt.B();
	}
 
	public static byte splitRGB(final int pixel, final int mode) {
		int ret=0;
	    switch (mode) {
			case R:
				ret= ((pixel & R)>>16);
				break;
			case G:
				ret = ((pixel & G)>>8);
				 break;
			case B:
				ret = (pixel & B);
		        break;
			case I:
				final int r=((pixel & R)>>16);
				final int g=((pixel & G)>>8);
				final int b=(pixel & B);
	 
				float[] hsb = new float[3];
				hsb = Color.RGBtoHSB(r, g, b, hsb);
				ret = (int)(hsb[2]*255);
				break;
	    }
	    
	    return (byte)(ret & 0xff);
	}
	
	public static int[] splitRGB(final int pixel) {
		final int r=((pixel & R)>>16);
		final int g=((pixel & G)>>8);
		final int b=(pixel & B);
  
	    return new int[]{r,g,b};
	}
	
	public int swapRGB(int c,  int mode) {
		return swapRGB( c, argb, mode);
	}
	
	public static int swapRGB(int c, int pixel, int mode) {
		int ret=0;
		switch (mode) {
	case R:    		
		ret = 0xff000000 | ((c&0xff)<<16) | ((pixel&0xff)<<8) | pixel&0xff;
		
    	break;
	case G:
		ret = 0xff000000 | ((pixel&0xff)<<16) | ((c&0xff)<<8) | pixel&0xff;
		
		 break;
	case B:
		ret = 0xff000000 | ((pixel&0xff)<<16) | ((pixel&0xff)<<8) | c&0xff;
        
		break;
	case I:
		final float f= (c & 0xff)/255.0f;
		final int r=(pixel & R)>>16;
		final int g=(pixel & G)>>8;
		final int b=pixel & B;

		float[] hsb = new float[3];
		hsb = Color.RGBtoHSB(r, g, b, hsb);
	
		ret = Color.HSBtoRGB(hsb[0], hsb[1], f);
		
		break;
		}
		return ret;
		
	}
	 

}
