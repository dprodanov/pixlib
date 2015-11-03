package test.funct;
import ij.*;
import ijaux.funct.impl.*;
import ijaux.*;
import ijaux.datatype.*;
import ijaux.hypergeom.*;
import ijaux.hypergeom.index.*;
import ijaux.iter.seq.*;

import java.awt.Label;
import java.lang.*;

public class LabelingTest implements Constants {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int[] dims={50,30};
		final int n=1500;
		final short[] short_pixels=Util.rampShort(n,0);
		
		//final int[] int_pixels=Util.rampInt(n,0);	
		final float[] float_pixels=Util.rampFloat(n,0);
		
		final short[] labels={0, 1, 2};
		final int[] borders={0, 10, 200, 1000};
		
		final float[] flabels={0, 1, 2};
		final float[] fborders={0.0f, 10, 200, 1000};
		
		PixelCube<Short,BaseIndex> pci=new PixelCube<Short,BaseIndex>(dims,short_pixels);
		pci.setIterationPattern(IP_FWD+IP_SINGLE);
		pci.setIndexing(BASE_INDEXING);
		
		PixelCube<Short,BaseIndex> outc=(PixelCube<Short, BaseIndex>) pci.clone();
		 
		RasterForwardIterator<Short>  in =(RasterForwardIterator<Short>) pci.iterator();
		RasterForwardIterator<Short>  out =(RasterForwardIterator<Short>) outc.iterator();
		
		Labeling<Short> labeling= new Labeling<Short>(labels, borders);
		labeling.setIO(in,  out);
	
		
		labeling.run();
		new ImageJ();
		PixLib plib=new PixLib();
		
		
		ImagePlus imp1 = null;
		try {
			imp1 = plib.imageFrom("test",pci);
		} catch (UnsupportedTypeException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		imp1.show();
		
		ImagePlus imp2 = null;
		try {
			imp2 = plib.imageFrom("thresholded",outc);
		} catch (UnsupportedTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		imp2.show();
	 
		PixelCube<Float,BaseIndex> pcif=new PixelCube<Float,BaseIndex>(dims,float_pixels);
		pcif.setIterationPattern(IP_FWD+IP_SINGLE);
		pcif.setIndexing(BASE_INDEXING);
		
		PixelCube<Float,BaseIndex> outcf=(PixelCube<Float, BaseIndex>) pcif.clone();
		 
		RasterForwardIterator<Float>  inf =(RasterForwardIterator<Float>) pcif.iterator();
		RasterForwardIterator<Float>  outf =(RasterForwardIterator<Float>) outcf.iterator();
		
		Labeling<Float> labelingf= new Labeling<Float>(flabels, fborders);
		labelingf.setIO(inf,  outf);
 
		Float elem=Float.valueOf( 3.0f);
		System.out.println(	labelingf.mapFloat ( elem).getClass());
		
		labelingf.run();
		
		//PixLib plibf=new PixLib();
		ImagePlus imp3 = null;
		try {
			imp3 = plib.imageFrom("test",pcif);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imp3.show();
		
		ImagePlus imp4 = null;
		try {
			imp4 = plib.imageFrom("thresholded",outcf);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imp4.show();
		 
	}

}
