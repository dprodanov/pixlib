package test.stat;
import ij.ImagePlus;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.compat.ImagePlusHistogram;
import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.stats.CubeHistogram;
import ijaux.stats.HyperCubeHistogram;


public class CubeHistogramTest {

	final int[] dims={50,30,4};
	final int n=Util.cumprod(dims);
	final short[] int_pixels=Util.rampShort(n,12);
	PixelCube<Short,BaseIndex> pci=new PixelCube<Short,BaseIndex>(dims,int_pixels);
	
	final float[] float_pixels=Util.rampFloat(n,5);
	PixelCube<Float,BaseIndex> pcf=new PixelCube<Float,BaseIndex>(dims,float_pixels);
	
	@SuppressWarnings("unchecked")
	public CubeHistogramTest() throws UnsupportedTypeException {
		int nbins=256;
		//System.out.println(pc);
		CubeHistogram<Short> chi=new CubeHistogram<Short>(pci, nbins);
		
		System.out.println("min: max");
		System.out.println(chi.getMinMax());
		final Pair<int[],  int[]> res=(Pair<int[],  int[]>) chi.resample();
		
		for (int i=0; i<res.second.length; i++) {
			System.out.print(i+": " +res.first[i] +" -> "+ res.second[i]+"\n");
		}
		
		System.out.println(chi);
		
		
		chi.updateStats();
		chi.doHistogram();
		double[] hstat=chi.hstat;
		System.out.println(hstat[0] +" " +hstat[1] +" " + hstat[2] +" "+hstat[3] +" " +hstat[4] +" " +hstat[5]);
		
		CubeHistogram<Float> chf=new CubeHistogram<Float>(pcf, nbins);
		System.out.println(chf);
		
		
		
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new CubeHistogramTest();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
