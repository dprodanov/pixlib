/**
 * 
 */
package ijaux.funct.impl;

import java.lang.reflect.Array;
import java.util.Arrays;

import ijaux.Util;
import ijaux.funct.AbstractIterableFunction;
import ijaux.iter.seq.RasterForwardIterator;

/**
 * @author prodanov
 * 
 * The class implements generic intensity thresholding functionality
 *
 */
public class Labeling<E extends Number> extends AbstractIterableFunction<RasterForwardIterator<E>, RasterForwardIterator<E>> {
	int[] iborders;
	float[] fborders;
	E[] labels;
	//ArrayWrapper wrapper =new ArrayWrapper();
	
	protected RasterForwardIterator<E> in;
	protected RasterForwardIterator<E> out;
	
 
	public Labeling(byte[] tvals, int[] tborders) {
		type=Util.getPrimitiveType(tvals.getClass());
		if (tvals.length!=tborders.length-1) 
			throw new IllegalArgumentException("wrong length of data arrays");
		
		iborders=tborders;	
		Arrays.sort(tvals);	
		labels=ArrayWrapper.box(tvals);
		Arrays.sort(iborders);	
	}
	
	public Labeling(short[] tvals, int[] tborders) {
		type=Util.getPrimitiveType(tvals.getClass());
		//System.out.println(type);
		if (tvals.length!=tborders.length-1) 
			throw new IllegalArgumentException("wrong length of data arrays");
		
		iborders=tborders;	
		Arrays.sort(tvals);	
		labels=ArrayWrapper.box(tvals);
		Arrays.sort(iborders);	
	}
	
 
	public Labeling(float[] fvals, float[] tborders) {
		type=Util.getPrimitiveType(fvals.getClass());
		if (fvals.length!=tborders.length-1) 
			throw new IllegalArgumentException("wrong length of data arrays");
		
		fborders=tborders;	
		Arrays.sort(fvals);
		labels=ArrayWrapper.box(fvals);
		Arrays.sort(fborders);	 
	}
	
	public Labeling(double[] fvals, float[] tborders) {
		type=Util.getPrimitiveType(fvals.getClass());
		if (fvals.length!=tborders.length-1) 
			throw new IllegalArgumentException("wrong length of data arrays");
		
		fborders=tborders;	
		Arrays.sort(fvals);
		labels=ArrayWrapper.box(fvals);
		Arrays.sort(fborders);	 
	}
	
	

	public void setLabels (int[] tr) {
		labels=ArrayWrapper.box(tr);
	}	
	

	public void setLabels (float[] tr) {
		labels=ArrayWrapper.box(tr);
	}
	

	public void setBorders (int[] tr) {
		iborders=tr;
	}	
	

	public void setBorders (float[] tr) {
		fborders=tr;
	}
	
	@Override
	public void setIO(RasterForwardIterator<E> in, RasterForwardIterator<E> out) {
		if (in.getPrimitiveType()!=type) {		
			//System.out.println(Util.getTypeMapping(in.getReturnType()));
			throw new IllegalArgumentException("type mismatch "+ type);
		}
		this.in=in;
		this.out=out;
		this.in.reset();
		this.out.reset();	
	}

	@Override
	public void run() {
		while (in.hasNext()) {
			E elem=(E) in.next();
			if (elem instanceof Double || elem instanceof Float) {
				out.put(mapFloat(elem));
			} else
			if (elem instanceof Byte || elem instanceof Short) {
				out.put(mapInteger(elem));
			}
		}
	}
	
	/*
	 * assuming ordered sequence
	 */
	public E mapFloat (E elem) {
		boolean bmin=false;
		for (int i=0; i<fborders.length; i++) {
			bmin=(elem.floatValue()<fborders[i]) || bmin;
			if (bmin)   {
				//System.out.println(i+" "+ elem.floatValue()+" "+fborders[i] +" "+labels[i]);
	
				return labels[i-1];
			}
		}
		return labels[labels.length-1];
	}
	
	/*
	 * assuming ordered sequence
	 */
	public E mapInteger (E elem) {
		boolean bmin=false;
		for (int i=0; i<iborders.length; i++) {			
			bmin=(elem.intValue()<iborders[i]) || bmin;	
			if (bmin)  {
				//System.out.println(" "+iborders[i] +" "+labels[i-1]);
				return labels[i-1];
			}
		}
		return labels[labels.length-1];
	}
	 
} //end class