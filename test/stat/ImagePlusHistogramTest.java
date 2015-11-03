package test.stat;

import ij.*;
import ijaux.Constants;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.compat.ImagePlusHistogram;
import ijaux.compat.ImagePlusHistogram.SimpleStatistics;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;

public class ImagePlusHistogramTest implements Constants {

	final int[] dims={50,15, 2, 2};
	final int n=Util.cumprod(dims);
	final short[] int_pixels=Util.rampShort(n,120);
	PixelCube<Short,BaseIndex> pci=new PixelCube<Short,BaseIndex>(dims,int_pixels);
	
	final float[] float_pixels=Util.rampFloat(n,120);
	PixelCube<Float,BaseIndex> pcf=new PixelCube<Float,BaseIndex>(dims,float_pixels);
	
	PixLib plib=new PixLib();
	
	public ImagePlusHistogramTest() throws UnsupportedTypeException {
		pci.setIndexing(BASE_INDEXING);
		pcf.setIndexing(BASE_INDEXING);
		
		ImagePlus imp4=plib.imageFrom("test short",pci);
		imp4.show();
		ImagePlusHistogram iph=new ImagePlusHistogram(imp4, 75);
		System.out.println(iph);
		
		SimpleStatistics stats=iph.getStatistics();
		
		System.out.println(stats);
		
		
		ImagePlus imp3=plib.imageFrom("test float",pcf);
		imp3.show();
		ImagePlusHistogram iph2=new ImagePlusHistogram(imp3, 200);
		System.out.println(iph2);
		
		SimpleStatistics stats2=iph.getStatistics();
		
		System.out.println(stats2);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new ImagePlusHistogramTest();
			new ImageJ();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
