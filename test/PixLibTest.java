package test;
import ij.ImageJ;
import ij.ImagePlus;
import ij.process.ByteProcessor;
import ijaux.*;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
 
import ijaux.iter.seq.RasterForwardIterator;

import java.lang.reflect.Array;
import java.lang.reflect.TypeVariable;
import java.util.List;

 


public class PixLibTest implements Constants {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PixLibTest pt=new PixLibTest();
		
		/*
		byte[] byte_pixels={0, -1,   2,  3, 4, 
				  5,  6,   7,  8, 9, 
	             10, 11,  12, 13, 14,
	             15, 16,  17, 18, 19
	             };

		       int[] dims={5,4};
		*/
		
		byte[] byte_pixels=Util.rampByte(1000000, 255);
		
		int[] dims={1000, 1000};
	 
		PixLib plib=new PixLib();
		 
		ByteProcessor bp=new ByteProcessor(dims[0],dims[1],byte_pixels, null);
		
		PixelCube<Byte, BaseIndex> pc= plib.cubeFrom(bp, BASE_INDEXING);

		PixelCube<Byte, BaseIndex> pc2=new PixelCube<Byte, BaseIndex>(pc);
		
	//	new PixelCube.Index<BaseIndex>(pc2){}; typing mechanism

	
		pc.setIterationPattern(IP_FWD+IP_SINGLE);
		RasterForwardIterator<Byte> iter=(RasterForwardIterator<Byte>) pc.iterator();
		
		pc2.setIterationPattern(PixelCube.IP_FWD+PixelCube.IP_SINGLE);
		RasterForwardIterator<Byte> iter2=(RasterForwardIterator<Byte>) pc2.iterator();
		
		long time1=System.currentTimeMillis();
		while (iter.hasNext() ) {
			final byte b=(byte) ((255 - iter.next()) & 0xFF);
			iter2.put(b);
		}
		int i=0;
		long time2=System.currentTimeMillis();
		while (i<byte_pixels.length) {
			final byte b=(byte) ((255 - byte_pixels[i]) & 0xFF);
			byte_pixels[i]=b;
			i++;
		}
		long time3=System.currentTimeMillis();
		
		System.out.println("rtime: "+(time2-time1));
		System.out.println("prim rtime: "+(time3-time2));
		
		
		ByteProcessor bp2 = null;
		try {
			bp2 = (ByteProcessor) plib.processorFrom(pc2);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new ImageJ();
		ImagePlus imp1 = null;
		try {
			imp1 = plib.imageFrom("test",pc2);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imp1.show();
		ImagePlus imp2=new ImagePlus("inverted test",bp2);
		imp2.show();
		
		PixelCube<Byte, BaseIndex> pc3= plib.cubeFrom(imp1, PixelCube.BASE_INDEXING);
		 
		TypeVariable<?>[] tp=pc3.getClass().getTypeParameters();
		for(TypeVariable<?> t:tp)
			System.out.println(t.getName());

		byte[] nbyte_pixels=Util.rampByte(10000,51);
		//byte[] nbyte_pixels=(byte[])Util.rampAlt2(10000,100, byte.class);
		//int[] ndims={100,100};
		//int[] ndims={10,10, 100};
		int[] ndims={10,10, 10, 10};
		PixelCube<Byte,BaseIndex> pc4=new PixelCube<Byte,BaseIndex>(ndims,nbyte_pixels, byte.class, BaseIndex.class);
		
		ImagePlus imp3 = null;
		try {
			imp3 = plib.imageFrom("test2",pc4);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imp3.show();
		
		/*pc3= plib.cubeFrom(imp3, PixelCube.BASE_INDEXING);
		
		ImagePlus imp4=plib.imageFrom("test2",pc3);
		imp4.show();
		 */
		
	}
	
	/*
	public  class Dummy<E,I> {
		
		 @SuppressWarnings("unchecked")
		 
		public Dummy (PixelCube pc) {
			List<Class<?>> clist=Util.getTypeArguments(Dummy.class, getClass());
			Class<? extends BaseIndex> c=  (Class<? extends BaseIndex>) clist.get(0);
			System.out.println(c.getCanonicalName());
			//indexing( c);
		}
		 
	}*/

}
