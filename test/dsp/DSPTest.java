package test.dsp;

import ij.CompositeImage;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.Constants;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.ComplexArray;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.dsp.FFT;
import ijaux.dsp.SamplingWindow;
import ijaux.dsp.WindowTypes;
import ijaux.hypergeom.ComplexCube;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.dsp.FFTD;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.dir.VectorCursor;

public class DSPTest implements Constants {

	
	
			
	public DSPTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[]dim={32, 8};
		int n=Util.cumprod(dim);
		byte[] byte_pixels=Util.rampByte(n, 16);

		double[] double_pixels=Util.rampDouble(n, 16);

		ComplexArray carr=new ComplexArray  (double_pixels);

		Util.printByteArray(byte_pixels);
		


		System.out.println("dimensions");
		Util.printIntArray(dim);

		int xdir=0;

		PixelCube<Double,BaseIndex> pc=new PixelCube<Double,BaseIndex>( dim,double_pixels );
		pc.setIndexing(BASE_INDEXING);
		VectorCursor<double[]> block=(VectorCursor<double[]>) pc.iteratorBlock();
		block.setDirection(xdir);
		
		//System.out.println(pc);

		/*System.out.println("iterating ");
		int i=0;
		while (block.hasNext()) {
			System.out.println("line  ... " +i++);
			double[] arr=block.next();
			Util.printDoubleArray(arr);
		}*/
		
	
		
		ComplexCube cc= FFTD.fft( pc, +1, xdir);
		//nt[] cdim=cc.getDimensions();
		//System.out.println("dimensions cc");
		//Util.printIntArray(cdim);
			 
		cc.setIndexing();
		
		cc.fftshift(xdir);
		
		System.out.println(cc);
	
		new ImageJ();
		PixLib plib=new PixLib();
		try {
			plib.imageFrom("real", pc).show();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImagePlus imgp=plib.imageFrom("input", cc, FFT_R, 0);
		imgp.show();
		
		
		ComplexCube cc2= FFTD.fftxd( pc, +1);
		cc2.fftshift();
		
		ImagePlus imgp2=plib.imageFrom("input", cc2, FFT_R, 0);
		imgp2.show();
		
		System.out.println("1D fft");
		FFT.fft1d(carr, +1, n);
		System.out.println(carr);
		
		System.out.println("1D ifft");
		FFT.fft1d(carr, -1, n);
		carr.scale();
		System.out.println(carr);
		
		SamplingWindow swnd=SamplingWindow.createWindow(WindowTypes.HANNING, n);
		System.out.println("Hanning window");
		System.out.println(swnd);
		
		double[] double_pixels2=Util.rampDouble(n, 16);

		ComplexArray carr2=new ComplexArray  (double_pixels2);
		
		FFT.fft1d(carr2, +1, n, swnd.getWindow());
		System.out.println(carr2);
		
		//ComplexArray shifted=carr.clone();
		
		//System.out.println("shifted");
		//DSP.fftshift1d(shifted);
		//System.out.println(shifted);
			
		
			 
	}

}
