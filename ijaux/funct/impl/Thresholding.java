/**
 * 
 */
package ijaux.funct.impl;

import ijaux.Util;
import ijaux.funct.AbstractIterableFunction;
import ijaux.iter.seq.RasterForwardIterator;

/**
 * @author prodanov
 * 
 * The class implements generic intensity thresholding functionality
 *
 */
public class Thresholding<E extends Number> extends AbstractIterableFunction<RasterForwardIterator<E>, RasterForwardIterator<E>> {
	int   itmin=0, itmax=0;
	float   ftmin, ftmax=0;
	E value, cvalue;

	protected RasterForwardIterator<E> in;
	protected RasterForwardIterator<E> out;
	
	@SuppressWarnings("unchecked")
	public Thresholding(int val, int cval, int tmin, int tmax, Class<?> c) {
 
		type=c;
		value=(E) getInt(val);
		cvalue=(E) getInt(cval);
		
		if (c==byte.class || c==Byte.class) {
			itmin=tmin & 0xFF;
			itmax=tmax & 0xFF;

		}
		if (c==short.class || c==Short.class) {
			itmin=tmin & 0xFFFF;
			itmax=tmax & 0xFFFF;
	
		}
		if (c==int.class || c==Integer.class) {
			itmin=tmin & 0xFFFFFF;
			itmax=tmax & 0xFFFFFF;

		}
		System.out.println("value " +value+ " cvalue "+cvalue);
		System.out.println("tmin "+ itmin+" / "+ftmin);
		System.out.println("tmax "+ itmax+" / "+ftmax);
	}
	
	@SuppressWarnings("unchecked")
	public Thresholding(float val, float cval, float tmin, float tmax, Class<?> c) {
		type=c;
		ftmin=tmin;
		ftmax=tmax;
		value=(E) getFloat(val);
		cvalue=(E) getFloat(cval);
	}
	
	@SuppressWarnings("unchecked")
	public void setThreshold (int trval, int trcval) {
		 value =(E) getInt(trval);
		 cvalue =(E) getInt(trcval);
	}	
	
	@SuppressWarnings("unchecked")
	public void setThreshold (float trval, int trcval) {
		value=(E) getFloat(trval);
		cvalue =(E) getFloat(trcval);
	}
	
	public void setMin (int min) {
		itmin=min;
	}
	
	public void setMin (float min) {
		ftmin=min;
	}
	
	public void setMax (int max) {
		itmax=max;
	}
	
	public void setMax (float max) {
		ftmax=max;
	}
	
	public Number getInt (int trval) {
		return Util.getInt(trval, type);
	}
	
	public Number getFloat (float trval) {
		return Util.getFloat(trval, type);
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
				if (elem.doubleValue()>= ftmin && elem.doubleValue()<= ftmax ) 
					out.put(value);
				else 
					out.put(cvalue);
			} else
			if (elem instanceof Byte || elem instanceof Short) {
				if (elem.intValue()>= itmin  && elem.intValue()<= itmax ) 		
					out.put(value);
				else
					out.put(cvalue);
			}
		}
		
	}

	 
} //end class