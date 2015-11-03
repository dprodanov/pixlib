import java.lang.reflect.Array;

import ij.ImageJ;
import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import ijaux.*;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.array.ByteForwardIterator;
import ijaux.iter.seq.RasterForwardIterator;


public class BenchmarkB implements Constants {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int size=4000000;
		byte[] byte_pixels=Util.rampByte(size, 255);
		
		byte[] byte_pixels_out=new byte[size];
		int[] dims={2000, 2000};
	 
		PixLib plib=new PixLib();
		 
		ByteProcessor bp=new ByteProcessor(dims[0],dims[1],byte_pixels, null);
		
		PixelCube<Byte, BaseIndex> pc= plib.cubeFrom(bp, BASE_INDEXING);

		PixelCube<Byte, BaseIndex> pc2=new PixelCube<Byte, BaseIndex>(pc);
		
 	
		pc.setIterationPattern(IP_SINGLE+IP_FWD + IP_PRIM);
		ByteForwardIterator iter=(ByteForwardIterator) pc.iterator();
		
		pc2.setIterationPattern(IP_SINGLE+IP_FWD + IP_PRIM);
	
		
		long[] times=new long[5];
		
		for (int i=0; i<times.length; i++) {
			ByteForwardIterator iter2=(ByteForwardIterator) pc2.iterator();
			int k=0;
			//byte[] outpix=new byte[size];
			
			long time1=System.currentTimeMillis();
			while (iter.hasNext() ) {
				final byte b=(byte) ((255 - iter.nextByte()) & 0xFF);
				iter.dec();			
				iter.putByte(b);
			}
			
			
			long time2=System.currentTimeMillis();
			k=0;
			/*
			 * we can't get any better than that
			 */
			while (k<byte_pixels.length) {
				final byte b=(byte) ((255 - byte_pixels[k]) & 0xFF);
				byte_pixels_out[k]=b;
				k++;
			}
			long time3=System.currentTimeMillis();
			k=0;
			long time4=System.currentTimeMillis();
			/*
			 * naive approach
			 */
			while (k<byte_pixels.length) {
				
				final byte b=(byte) ((255 - (Byte)Array.get(byte_pixels, k)) & 0xFF);
				Array.set(byte_pixels_out, k, b);
				k++;
			}
			long time5=System.currentTimeMillis();
			
			invertImageProcessor(bp);
			
			
			
			long time6=System.currentTimeMillis();
			try {
				k=0;
				Access<Byte> accesin=Access.rawAccess(((Object)byte_pixels), null);
				Access<Byte> accesout=Access.rawAccess(((Object)byte_pixels_out), null);
				time6=System.currentTimeMillis();
				while (k<byte_pixels.length) {
					accesout.putE(k, accesin.element(k));
					k++;
					 
				}
			} catch (UnsupportedTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			long time7=System.currentTimeMillis();
			
			System.out.println(" pixlib time: "+(time2-time1) 
							 + " primitive rtime: "+(time3-time2)
							 + " reflexion rtime: "+(time5-time4)
							 + " bproc time: "+(time6-time5) 
							 + " acess time: "+(time7-time6) 
							 );
			final float rat=(time2-time1)/(float)(time3-time2);
			System.out.println("ra "+rat);
		
			final float rat2=(time2-time1)/(float)(time5-time4);
			System.out.println("rb "+rat2);
			
			iter.set(0);
			iter2.set(0);
			System.gc();
		}
		
		try {
			ByteProcessor bp2=(ByteProcessor) plib.processorFrom(pc2);
			new ImageJ();
			//ImagePlus imp1=plib.imageFrom("test",pc2);
			//imp1.show();
			ImagePlus imp2=new ImagePlus("inverted test",bp);
			imp2.show();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
	private static void invertImageProcessor(ByteProcessor ip) {
		for (int i=0; i<ip.getPixelCount(); i++) {
			ip.setf(i, 255 - ip.getf(i));
		}
	}

}
