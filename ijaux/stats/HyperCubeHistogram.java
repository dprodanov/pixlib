package ijaux.stats;

import ijaux.Util;
import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;

import java.lang.reflect.Array;

public class HyperCubeHistogram {

	protected HistogramMap<?,Integer> map;
	static final int nparams = 6;
	public double[] hstat = new double[nparams];
	protected Pair<float[], int[]> mixedHistogram;
	public int nBins = 256;
	private boolean iscalc = false;

	

	@SuppressWarnings("unchecked")
	public void doHistogram() {
		 final Pair<?,int[]> p=(Pair<?, int[]>) resample();
		 //System.out.println(p);
		 
		 Object objarr=p.first;
		 try {
			Access<?> access=Access.rawAccess(objarr, null);
			final int size=(int)access.length();
			float[] ret=new float[size];
			
			 for (int i=0;i<size; i++) {
				 //System.out.println(i +" "+ ret[i]);
				 ret[i]=access.elementFloat(i);
			 }
			 mixedHistogram=new Pair<float[], int[]>( ret, p.second);
			// System.out.println(mixedHistogram);
			 
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
		 
	}

	public boolean isCalculated() {
		return iscalc;
	}

	public Pair<?,?> resample() {
		return map.getResampled(nBins);
	}

	public int[] getHistogram() {
		return mixedHistogram.second;
	}

	public float[] getBins() { 
		return mixedHistogram.first;
	}

	@SuppressWarnings("unchecked")
	public Pair<?, ?> getMinMax() {
		final Pair<?,int[]> p=(Pair<?, int[]>) map.getSparse();
	
		Object objarr=p.first;
		if (objarr.getClass().isArray()) {
			 final int length=Array.getLength(objarr);
			 if (Util.isByteType(map.type)|| Util.isShortType(map.type)) {
				 int[] sf=new int[2];
				 System.out.println("Int type");
				 sf[0]=Array.getInt(objarr, 0);
				 sf[1]=Array.getInt(objarr, length-1);
				 return new Pair<Integer, Integer>(sf[0],sf[1]);
			 }
	
			 if (Util.isFloatType(map.type) || Util.isDoubleType(map.type)) {
				 float[] sf=new float[2];
				 System.out.println("Float type");
				 sf[0]=Array.getFloat(objarr, 0);
				 sf[1]=Array.getFloat(objarr, length-1);
				 return new Pair<Float, Float>(sf[0],sf[1]);
			 }
				 
		} else  {
			throw new UnsupportedOperationException("not an array of "+map.type);
		}
		 
		return null;
	}

	public void updateStats() { 
		map.updateArrays();
		map.updateStats();
		hstat= map.hstat;
		iscalc=true;
		doHistogram();
	}

	@Override
	public String toString() {
		return map.toString();
	}

	public Class<?> getType() {
		return map.type;
	}

}