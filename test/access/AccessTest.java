package test.access;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;


public class AccessTest {
	
	static int n=256*256*256;
	
	static int[] dim={256,256,256};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.gc();
		float[] float_pixels=Util.rampFloat(n, 16);
		
		byte[] byte_pixels=Util.rampByte(n, 16);
		
		
		short[] short_pixels=Util.rampShort(n, 16);
		
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
			
			access= Access.rawAccess(short_pixels, null);
			
			for (int k=0; k<n; k++) {
				final short b=access.elementShort(k);
			}
			long time2=System.currentTimeMillis();
			
			access= Access.rawAccess(float_pixels, null);
			
			for (int k=0; k<n; k++) {
				final float b=access.elementFloat(k);
			}
			long time3=System.currentTimeMillis();
			
			System.out.println("Access testing for array size: "+n);
			
			System.out.println("Raw access");
			System.out.println("Initiation time (byte): "+(time0-start));
			
			System.out.println("Loop run time (byte): "+(time1-time0) );
			System.out.println("Loop run time (short): "+(time2-time1) );
			System.out.println("Loop run time (float): "+(time3-time2) );
			
			/*
			 *  Testing conversion
			 */
			
			System.gc();
			access= Access.rawAccess(byte_pixels, null);
			System.out.println("Data conversion: ");
			
			long time20=System.currentTimeMillis();
			for (int k=0; k<n; k++) {
				final Number b=(Number)access.element(k);
			}
			
			
			long time21=System.currentTimeMillis();
			for (int k=0; k<n; k++) {
				final float b=access.elementFloat(k);
			}
			
			long time22=System.currentTimeMillis();
			
			for (int k=0; k<n; k++) {
				final byte b=access.elementByte(k);
			}
		
			long time23=System.currentTimeMillis();
			
			System.out.println("Loop run time (Number): "+(time21-time20) );
			System.out.println("Loop run time (float): "+(time22-time21) );
			System.out.println("Loop run time (byte): "+(time23-time22) );
			
			/*
			 * PixelCube case
			 */
			System.gc();
			long start1=System.currentTimeMillis();
			PixelCube<Byte, BaseIndex> pcb =new PixelCube<Byte, BaseIndex>(dim, byte_pixels);
			pcb.setIndexing(PixelCube.BASE_INDEXING);
			long pc1=System.currentTimeMillis();
			
			long time10=System.currentTimeMillis();
			access=pcb.getAccess();
			for (int k=0; k<n; k++) {
				final byte b=access.elementByte(k);
			}
			long time11=System.currentTimeMillis();
			
			
			
			PixelCube<Short, BaseIndex> pcs =new PixelCube<Short, BaseIndex>(dim, short_pixels);
			pcs.setIndexing(PixelCube.BASE_INDEXING);
			
			long time12=System.currentTimeMillis();
			access=pcs.getAccess();
			for (int k=0; k<n; k++) {
				final short b=access.elementShort(k);
			}
			long time13=System.currentTimeMillis();
			
			PixelCube<Float, BaseIndex> pcf =new PixelCube<Float, BaseIndex>(dim, float_pixels);
			pcf.setIndexing(PixelCube.BASE_INDEXING);
			
			long time14=System.currentTimeMillis();
			access=pcs.getAccess();
			for (int k=0; k<n; k++) {
				final short b=access.elementShort(k);
			}
			long time15=System.currentTimeMillis();
			
			System.out.println("Access from a PixelCube");
			System.out.println("Initiation time (byte): "+(pc1-start1));
			System.out.println("Loop run time (byte): "+(time11-time10) );
			System.out.println("Loop run time (short): "+(time13-time12) );
			System.out.println("Loop run time (float): "+(time15-time14) );
			
			
			
			
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
