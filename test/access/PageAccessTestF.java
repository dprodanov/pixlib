package test.access;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.datatype.access.PagedAccess;
import ijaux.datatype.access.PagedByteAccess;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;


public class PageAccessTestF {
	
	static int n=256*256*16;
	
	static int[] dim={256,256*16};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.gc();
		float[] float_pixels=Util.rampFloat(n, 16);
		
		byte[] byte_pixels=Util.rampByte(n, 16);
		
		
		short[] short_pixels=Util.rampShort(n, 16);
		
		byte[][] bpixels2D=new byte[2][n];
		bpixels2D[0]=Util.rampByte(n, 16);
		bpixels2D[1]=Util.rampByte(n, 16);
		
		short[][] spixels2D=new short[2][n];
		spixels2D[0]=Util.rampShort(n, 16);
		spixels2D[1]=Util.rampShort(n, 16);
		
		float[][] fpixels2D=new float[2][n];
		fpixels2D[0]=Util.rampFloat(n, 16);
		fpixels2D[1]=Util.rampFloat(n, 16);
		
		
		
		try {
			/*
			 * Raw access case
			 */
			long start=System.currentTimeMillis();
			Access<?> access= Access.rawAccess(byte_pixels, null);
			
			long time0=System.currentTimeMillis();
			for (int k=0; k<n; k++) {
				final byte b=access.elementByte(k);
			}
			long time1=System.currentTimeMillis();
			
			PagedAccess<?> pa= PagedAccess.rawAccess ((Object)bpixels2D, null);
			
			long pa1=System.currentTimeMillis();
			for (int k=0; k<2*n; k++) {
				final short b=pa.elementByte(k);
			}
			long time2=System.currentTimeMillis();
			
			 
			
			System.out.println("Access testing for array size: "+n);
			
			System.out.println("Raw access");
			System.out.println("Initiation time (byte[]): "+(time0-start));
			System.out.println("Initiation time (byte[][]): "+(pa1-time0));
			System.out.println("Loop run time (byte[]): "+(time1-time0) );
			System.out.println("Loop run time (byte[][]): "+(time2-pa1) );
		 
			
			 
			long start1=System.currentTimeMillis();
			access= Access.rawAccess(short_pixels, null);
			
			long time10=System.currentTimeMillis();
			for (int k=0; k<n; k++) {
				final short b=access.elementShort(k);
			}
			long time11=System.currentTimeMillis();
			
			pa= PagedAccess.rawAccess ((Object)spixels2D, null);
			
			long pa10=System.currentTimeMillis();
			for (int k=0; k<2*n; k++) {
				final short b=pa.elementShort(k);
			}
			long time12=System.currentTimeMillis();
			
			 
			
			System.out.println("Access testing for array size: "+n);
			
			System.out.println("Raw access");
			System.out.println("Initiation time (short[]): "+(time10-start1));
			System.out.println("Initiation time (short[][]): "+(pa10-time10));
			System.out.println("Loop run time (short[]): "+(time11-time10) );
			System.out.println("Loop run time (short[][]): "+(time12-pa10) );
			
			
			long start2=System.currentTimeMillis();
			access= Access.rawAccess(float_pixels, null);
			
			long time20=System.currentTimeMillis();
			for (int k=0; k<n; k++) {
				final float b=access.elementFloat(k);
			}
			long time21=System.currentTimeMillis();
			
			pa= PagedAccess.rawAccess ((Object)fpixels2D, null);
			
			long pa20=System.currentTimeMillis();
			for (int k=0; k<2*n; k++) {
				final float b=pa.elementFloat(k);
			}
			long time22=System.currentTimeMillis();
			
			 
			
			System.out.println("Access testing for array size: "+n);
			
			System.out.println("Raw access");
			System.out.println("Initiation time (float[]): "+(time20-start2));
			System.out.println("Initiation time (float[][]): "+(pa20-time20));
			System.out.println("Loop run time (float[]): "+(time21-time20) );
			System.out.println("Loop run time (float[][]): "+(time22-pa20) );
			//System.out.println("Loop run time (float): "+(time3-time2) );
	
			
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
