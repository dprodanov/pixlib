package test;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;


public class PixelCubeGUITest {
	PixLib plib=new PixLib();
	
	public PixelCubeGUITest () {
		int[] dims={5,3,4};
		int n=Util.cumprod(dims);
		byte[] int_pixels=Util.rampByte(n,0);
		
		int [] ord={0,1};
		 
		        
		PixelCube<Byte,BaseIndex> pc=new PixelCube<Byte,BaseIndex>(dims,int_pixels);
		pc.setIndexing(PixelCube.BASE_INDEXING);
		
		ImagePlus imp;
		try {
			imp = new ImagePlus("test", plib.getXYPlane(pc, ord));
			new ImageJ();
			imp.show();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new PixelCubeGUITest();
		

	}

}
