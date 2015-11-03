package ijaux.stats;



import ijaux.Util;
import ijaux.datatype.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;


/*
 * K - key; V - value
 */

public abstract class HistogramMap<K,V extends Number> extends TreeMap<K,V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7495529910206316110L;
	final static int nparams=6;
	double[] hstat= new double[nparams]; // 0 - min, 1 - step, 2 - max, 3 - sum, 4 - size; 5 - area; 
	protected boolean iscalc=false;
	public boolean debug=false;
	
	Class<?> type;
	
	boolean typeset=false;
	
	protected float[] ramp(float offset, float step, int n) {
		float[] ret=new  float[n];
		ret[0]=offset;
		for (int k=1; k<n; k++)
			ret[k]=ret[k-1]+step;
		
		return ret;
	}
	
	protected int[] ramp(int offset, int step, int n) {
		int[] ret=new  int[n];
		ret[0]=offset;
		for (int k=1; k<n; k++)
			ret[k]=ret[k-1]+step;
		
		return ret;
	}
	
	public boolean isCalc() {
		return iscalc;
	}
	
	public double[] getStats() {
		return hstat;
	}
	
	public abstract void updateStats();
	
	public abstract Pair<?,?> getResampled(int nbins);
	
	public abstract Pair<?,?> getSparse();
	
	public abstract Pair<?,?> getCumulant();
	
	public abstract <T> void addToMap(T pixels) throws UnsupportedTypeException ;
	
	public abstract <T> void removeFromMap(T pixels) throws UnsupportedTypeException;
		
	public <T> void setType(T pixels) {
		final Class<?>c=pixels.getClass();
		if (pixels.getClass().isArray()) {
			type=Util.getPrimitiveType(c);
			typeset=true;
			//System.out.println(type);
		} else {
			throw new IllegalArgumentException("not an array "+c);
		}
	}

	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer(2000);
		int cnt=0;
		for (Iterator<Entry<K, V>> keyValue = entrySet().iterator(); keyValue.hasNext();) {
			final Map.Entry<K, V> entry = keyValue.next();
			sb.append(cnt +": [ "+entry.getKey() +" "+entry.getValue() +"] \n");
			cnt++;
		}
		
		return sb.toString();
	}

	public abstract void updateArrays();
	
	 
}
