package test.iter;

import ij.ImageJ;
import ij.ImagePlus;
import ijaux.Constants;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.ComplexArray;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.ComplexCube;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.compl.ComplexVectorCursor;

public class ComplexVectorCursorTest implements Constants {

	
			
	public ComplexVectorCursorTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[]dim={8,16};
		int n=Util.cumprod(dim);
		double[] re_pixels=Util.rampDouble(n, 8);
		double[] im_pixels=Util.rampDouble(n, 8);
		ComplexArray cpixels=new ComplexArray(re_pixels,im_pixels, false);
		int xdir=0;
		//int[] dir={1,0};
		//Util.printByteArray(byte_pixels);
		System.out.println("dimensions");
		Util.printIntArray(dim);	
		
	 
			ComplexVectorCursor  block=new ComplexVectorCursor(cpixels,   dim);
			
			/*System.out.println("permutation test");
			int[] kperm=block.perm(dim.length, 3);
			Util.printIntArray(kperm);
	*/
			
			block.setDirection(xdir);
			
			System.out.println(xdir+" axis iteration order");
			//Util.printIntArray(block.getAxes());
			
			int i=0;
			System.out.println("skipping ");
			while (block.hasNext()) {
				System.out.println("line  ... " +i++);
				block.inc();
				//System.out.println(block.nextC());
			}
			block.reset();
			i=0;
			System.out.println("printing ");
			while (block.hasNext()) {
				System.out.println("line  ... " +i++);
		
				System.out.println(block.nextC());
			}
			block.reset();
			i=0;
			
			while (block.hasNext()) {
				System.out.println("line  ... " +i++);
				double[] array=Util.randDouble(dim[xdir]);
				ComplexArray cc=new ComplexArray(array);
				System.out.println(cc);
				block.put(cc);
			}
			ComplexCube pc=new ComplexCube( dim,cpixels );
			pc.setIndexing();
			
			
		
			new ImageJ();
			PixLib plib=new PixLib();
			ImagePlus imgp=plib.imageFrom("input", pc, FFT_R, 0);
	
			imgp.show();
		
	 

	}

}
