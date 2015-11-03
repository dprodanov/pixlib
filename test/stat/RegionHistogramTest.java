package test.stat;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.*;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.funct.ElementFunction;
import ijaux.hypergeom.*;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.morphology.*;
import ijaux.iter.seq.*;
import ijaux.stats.RegionHistogram;


public class RegionHistogramTest implements Constants {

	PixLib plib=new PixLib();
	
	@SuppressWarnings("unchecked")
	public RegionHistogramTest() throws UnsupportedTypeException {
		
		//plib.debug=true;
		
		new ImageJ();
	
		float radius=1.0f;
		int ndim=2;
			 
		
		StructureElement<Short> se=new StructureElement<Short>(radius, ndim, Short.class);
		 
		se.createDiamondMask(radius, false);
		System.out.println(se.getVect());
		
		PixelCube<Short,BaseIndex> pc= (PixelCube<Short,BaseIndex>) se.getMask();
		 
		ImagePlus imp2=plib.imageFrom(se.getName()+" SE",pc);
		imp2.show();
		
		final int[] dims={10,10};
		final int n=10*10;
		//final byte[] byte_pixels=Util.rampByte(n,128);
		//final float[] int_pixels=Util.rampFloat(n,128);
		final short[] int_pixels=Util.rampShort(n,128);
		PixelCube<Short,BaseIndex> pci=new PixelCube<Short,BaseIndex>(dims,int_pixels);
		pci.setIterationPattern(IP_FWD+IP_SINGLE);
		pci.setIndexing(BASE_INDEXING);
		
		RegionHistogram<Short> rh= new RegionHistogram<Short>( pci,   se);
		 
		rh.debug();
		 
		
		final int idx=pci.getIndex().indexOf(new int[]{2,2});
		System.out.println(idx);
		
		rh.doHistogram(idx);
		 
		System.out.println(rh);
		
		ImagePlus imp3=plib.imageFrom("data",pci);
		imp3.show();
		
		rh.clear();
		System.out.println("");
		PixelCube wnd=rh.region.neighborhood(idx);
		
		System.out.println("");
		System.out.println("neighborhood");
		System.out.println(wnd);
		
		System.out.println("data");
		System.out.println(pci);
		
		ImagePlus imp4=plib.imageFrom("wnd",wnd);
		imp4.show();
		
		/*
		
		PixelForwardIterator iter=(PixelForwardIterator) rh.region.iterator();
		while (iter.hasNext()) {
			final int idx=rh.region.index();
			rh.region.elementTransform(idx);
			System.out.println(rh);
			map.
		}
		 */
			/*
		 se.createSquareMask(radius);
		 System.out.println(se.getVect());
		 pc= (PixelCube<Byte,BaseIndex>) se.getMask();
		 System.out.println(se.getVect());
		ImagePlus imp3=plib.imageFrom("square SE",pc);
		imp3.show();
			
		
		final int dir=1;
		se.createLineMask(radius, dir);
		System.out.println(se.getVect());
		pc= (PixelCube<Byte,BaseIndex>) se.getMask();
		System.out.println(se.getVect());
		ImagePlus imp4=plib.imageFrom("Line SE "+ dir ,pc);
		imp4.show();
		*/
			 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new RegionHistogramTest();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
