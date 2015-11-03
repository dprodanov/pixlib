import java.lang.reflect.Array;

import ij.ImageJ;
import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ShortProcessor;
import ijaux.*;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.seq.RasterForwardIterator;


public class BenchmarkS implements Constants {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int size=4000000;
		short[] short_pixels=Util.rampShort(size, 255);
		
		short[] short_pixels_out=new short[size];
		int[] dims={2000, 2000};
	 
		PixLib plib=new PixLib();
		 
		ShortProcessor bp=new ShortProcessor(dims[0],dims[1],short_pixels, null);
		
		PixelCube<Short, BaseIndex> pc= plib.cubeFrom(bp, BASE_INDEXING);

		PixelCube<Short, BaseIndex> pc2=new PixelCube<Short, BaseIndex>(pc);
		
 	
		pc.setIterationPattern(IP_FWD+IP_SINGLE);
		RasterForwardIterator<Short> iter=(RasterForwardIterator<Short>) pc.iterator();
		
		pc2.setIterationPattern(IP_FWD+IP_SINGLE);
		RasterForwardIterator<Short> iter2=(RasterForwardIterator<Short>) pc2.iterator();
		
		long[] times=new long[5];
		int k=0;
		
		for (int i=0; i<times.length; i++) {
			long time1=System.currentTimeMillis();
			while (iter.hasNext() ) {
				final short b=(short) ((65535 - iter.next()) & 0xFFF);
				iter2.put(b);
			}
			
			
			long time2=System.currentTimeMillis();
			k=0;
			/*
			 * we can't get any better than that
			 */
			while (k<short_pixels.length) {
				final short b=(short) ((255 - short_pixels[k]) & 0xFF);
				short_pixels_out[k]=b;
				k++;
			}
			long time3=System.currentTimeMillis();
			k=0;
			long time4=System.currentTimeMillis();
			/*
			 * naive approach
			 */
			while (k<short_pixels.length) {
				
				final short b=(byte) ((65535 - (Short)Array.get(short_pixels, k)) & 0xFFF);
				Array.set(short_pixels, k, b);
				k++;
			}
			long time5=System.currentTimeMillis();
			
			bp.invert();
			long time6=System.currentTimeMillis();
			
			System.out.println(" pixlib time: "+(time2-time1) 
							 + " primitive rtime: "+(time3-time2)
							 + " reflexion rtime: "+(time5-time4)
							 + " bp time: "+(time6-time5)
							 );
			final float rat=(time2-time1)/(float)(time3-time2);
			System.out.println("ra "+rat);
		
			final float rat2=(time2-time1)/(float)(time5-time4);
			System.out.println("rb "+rat2);
			
			final float rat3=(time2-time1)/(float)(time6-time5);
			System.out.println("rb "+rat3);
			
			iter.set(0);
			iter2.set(0);
		}
		
		//ShortProcessor bp2=(ShortProcessor) plib.processorFrom(pc2);
		/*
		new ImageJ();
		ImagePlus imp1=plib.imageFrom("test",pc2);
		imp1.show();
		ImagePlus imp2=new ImagePlus("inverted test",bp2);
		imp2.show();
*/
	}

}
