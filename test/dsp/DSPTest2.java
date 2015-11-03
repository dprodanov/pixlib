package test.dsp;

import ij.CompositeImage;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.Constants;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.ComplexArray;
import ijaux.datatype.ComplexNumber;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.dsp.FFT;
import ijaux.dsp.SamplingWindow;
import ijaux.dsp.WindowTypes;
import ijaux.hypergeom.ComplexCube;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.compl.ComplexVectorCursor;
import ijaux.iter.dir.VectorCursor;

public class DSPTest2 implements Constants {

	
	
			
	public DSPTest2() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[]dim={32, 16};
		int n=Util.cumprod(dim);
		int xdir=0;
		
		double[] double_pixels=Util.rampDouble(n, 16);
		System.out.println("sinc 0 " +FFT.sinc(0)+ " "+
				Math.atan2(0.0d,1.0d));
		ComplexNumber c= new ComplexNumber (0,1, false);
		
		System.out.println("complex argument of " +c +" : " + c.arg());
		

		SamplingWindow swnd=SamplingWindow.createWindow(WindowTypes.HANNING, dim[xdir]);
		
		System.out.println(swnd);
		
		double[] upix=Util.range(double_pixels, new int[]{0, dim[xdir]-1});
		FFT.window(upix, swnd.getWindow());
		
		Util.printDoubleArray(upix);
		
		ComplexCube cp=new ComplexCube(dim);
		ComplexArray cpixels=new ComplexArray(double_pixels, double_pixels, false);
		//System.out.println(cpixels);
		cp.setPixels(cpixels);
		ComplexVectorCursor block=cp.iteratorBlock();
		block.setDirection(xdir);
		int i=0;
		while (block.hasNext()) {
			ComplexArray bb=block.nextC();
			FFT.window(bb, swnd.getWindow());
			System.out.println((i++)+" " + bb );
			block.dec();
			block.put(bb);
		}
		new ImageJ();
		PixLib plib=new PixLib();
		ImagePlus imgp2=plib.imageFrom("input", cp, FFT_ABS, 0);
		imgp2.show();

	}

}
