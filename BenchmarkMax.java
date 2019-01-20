import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ijaux.PixLib;
import ijaux.datatype.Pair;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.IndexedIterator;


public class BenchmarkMax implements PlugIn {

	private PixLib plib=new PixLib();
  
    
	@Override
	public void run(String arg) {

		
		// Opens the M51 galaxy example
        String name = "http://rsb.info.nih.gov/ij/images/m51.tif";
		ImagePlus imp = IJ.openImage(name);
		imp.show();
		
		long time1=System.currentTimeMillis();
		// converts the ImagePlus to a PixelCube
		PixelCube<Number,BaseIndex>  pc=plib.cubeFrom(imp, PixLib.BASE_INDEXING);
		
		
		int idx=pc.index();
		Pair<Number,Integer> max=  (Pair<Number, Integer>)Pair.of(pc.element(idx),idx);
		
		//iterates over the image and finds the 1st global maximum
		long time2=System.currentTimeMillis();
		for (IndexedIterator<?> iter= pc.iterator();iter.hasNext(); iter.next()) {
			final Number c=pc.element(idx);
			if (c.floatValue()>max.first.floatValue())
				max=(Pair<Number, Integer>)Pair.of(c,idx);
			idx= iter.index(); 
		}
		BaseIndex bi=pc.getIndex();
		//calculates the image coordinates
		bi.setIndex(max.second);
		long time3=System.currentTimeMillis();
		System.out.println(max);
		int[] coords=bi.getCoordinates();
		IJ.makePoint(coords[0], coords[1]);
		System.out.println(bi);
		System.out.println("initiation time: "+ (time2-time1)+" ms\n loop duration " + (time3-time2) +" ms" );
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ImageJ();
		new BenchmarkMax().run(null);
		

	}

}
