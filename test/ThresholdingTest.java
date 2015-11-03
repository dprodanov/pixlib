package test;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.Constants;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.funct.impl.Thresholding;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.morphology.StructureElement;
import ijaux.iter.seq.RasterForwardIterator;


public class ThresholdingTest implements Constants {

	final int[] dims={50,30};
	final int n=1500;
	final short[] int_pixels=Util.rampShort(n,50);
	PixelCube<Short,BaseIndex> pci=new PixelCube<Short,BaseIndex>(dims,int_pixels);
	
	public ThresholdingTest() {
		pci.setIterationPattern(IP_FWD+IP_SINGLE);
		pci.setIndexing(BASE_INDEXING);
		
		Thresholding<Short> tr=new Thresholding<Short>(32768, 0, 24, 1024, short.class);
		PixelCube<Short,BaseIndex> outc=(PixelCube<Short, BaseIndex>) pci.clone();
		 
		RasterForwardIterator<Short>  in =(RasterForwardIterator<Short>) pci.iterator();
		RasterForwardIterator<Short>  out =(RasterForwardIterator<Short>) outc.iterator();
		tr.setIO(in,  out);
		tr.run();
		
		new ImageJ();
		PixLib plib=new PixLib();
		
		
		ImagePlus imp1 = null;
		try {
			imp1 = plib.imageFrom("test",pci);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imp1.show();
		
		ImagePlus imp2 = null;
		try {
			imp2 = plib.imageFrom("thresholded",outc);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imp2.show();
	 
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ThresholdingTest();

	}

}
