package test;
import ijaux.funct.impl.*;
import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;

import java.lang.*;

public class ArrayWrapperTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int n = 2000000;
		final short[] short_pixels = Util.rampShort(n, 0);
		final int[] int_pixels = Util.rampInt(n, 0);
		final byte[] byte_pixels = Util.rampByte(n, 0);
		final float[] float_pixels = Util.rampFloat(n, 0);
		
		final double[] double_pixels = Util.rampDouble(n, 0);
		//ArrayWrapper wrapper = new ArrayWrapper();
		
		for (int i = 0; i < 10; i++) {
		
			 
			long time1 = System.currentTimeMillis();
		
			Short[] sharr = ArrayWrapper.box(short_pixels);
			long time2 = System.currentTimeMillis();
			long time5 = System.currentTimeMillis();
			//ArrayWrapper<Byte> wrapperByte = new ArrayWrapper<Byte>();
			Byte[] bytearr = ArrayWrapper.box(byte_pixels);
			long time6 = System.currentTimeMillis();
			long time3 = System.currentTimeMillis();
			//ArrayWrapper<Integer> wrapperInt = new ArrayWrapper<Integer>();
			try {
				Integer[] intarr = ArrayWrapper.box((Object)int_pixels);

			} catch (UnsupportedTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long time4 = System.currentTimeMillis();
			
			
			
			long time9 = System.currentTimeMillis();
			//ArrayWrapper<Float> wrapperFloat = new ArrayWrapper<Float>();
			Double[] doublearr = ArrayWrapper.box(double_pixels);
			long time10 = System.currentTimeMillis();
			
			long time7 = System.currentTimeMillis();
			//ArrayWrapper<Float> wrapperFloat = new ArrayWrapper<Float>();
			Float[] floatarr = ArrayWrapper.box(float_pixels);
			long time8 = System.currentTimeMillis();
			
			System.out.println("short " + (time2 - time1) + " int "
					+ (time4 - time3) + " byte " + (time6 - time5) + " float "
					+ (time8 - time7) +" double "+ (time10-time9));
			System.gc();
		}
	}

}
