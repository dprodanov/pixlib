/**
 * 
 */
package ijaux.funct.impl;

import ijaux.datatype.Pair;
import ijaux.funct.AbstractElementFunction;

 

/**
 * @author prodanov
 *
 */
public class MinMax<E extends Number> extends AbstractElementFunction< Pair<E,E>, E>  {
	protected E tmin=null;
	protected E tmax=null;
	
	public boolean isMin=true;
	private int mask=0xFFFFFF;
	
	public MinMax(boolean min, Class<?> c) {
		isMin=min;
		type=c;
		init();
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		if (type==byte.class) {
			tmin=(E) Byte.valueOf(Byte.MAX_VALUE);
			tmax=(E) Byte.valueOf(Byte.MIN_VALUE);
			return;
		}
		if (type==short.class) {
			tmin=(E) Short.valueOf(Short.MAX_VALUE);
			tmax=(E) Short.valueOf(Short.MIN_VALUE);
			return;
		}
		if (type==float.class) {
			tmin=(E) Float.valueOf(Float.MAX_VALUE);
			tmax=(E) Float.valueOf(Float.MIN_VALUE);
			return;
		}
		if (type==double.class) {
			tmin=(E) Double.valueOf(Double.MAX_VALUE);
			tmax=(E) Double.valueOf(Double.MIN_VALUE);
			return;
		}
	}
	
 	@Override
	public Pair<E,E> getOutput() {
		 return Pair.of(tmin, tmax);
	}

 	public E get() {
 		if (isMin)
 			return tmin;
 		else 
 			return tmax;
 	}
 	
 	public void setMask(int amask) {
 		mask=amask;
 	}
 	
	@Override
	public void transform(E a, E b) {
		if (a instanceof Double || a instanceof Float) {
			if (a.doubleValue()> tmax.doubleValue()) {
				tmax=a;
				return;
			}
			if (a.doubleValue() <tmin.doubleValue() ) {
				tmin=a;
				return;
			}
		} else
		if (a instanceof Byte || a instanceof Short ) {
			if ((a.intValue() & mask)> (tmax.intValue() & mask))
				tmax=a;
			if ((a.intValue() & mask) <(tmax.intValue() & mask) ) 
				tmin=a;
		}
		
	}

	@Override
	public void transformBool(boolean a, boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transformByte(byte a, byte b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transformDouble(double a, double b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transformFloat(float a, float b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transformInt(int a, int b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transformShort(short a, short b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getOutputBoolean() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getOutputByte() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getOutputDouble() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getOutputFloat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOutputInt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getOutputShort() {
		// TODO Auto-generated method stub
		return 0;
	}

 
}
