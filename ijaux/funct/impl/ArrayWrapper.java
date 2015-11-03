package ijaux.funct.impl;


import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;

import java.lang.reflect.Array;

public class ArrayWrapper{
	//public E[] array;
	//Class<?> primitiveType;
	
	private ArrayWrapper() {
		
	}
	
	/**
	 * @param ivals
 
	 * @throws NegativeArraySizeException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws ArrayIndexOutOfBoundsException
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Number> E[] box(final int[] ivals)
			throws NegativeArraySizeException, NullPointerException,
			IllegalArgumentException, ArrayIndexOutOfBoundsException {
		
		Object array=Array.newInstance(Util.getTypeMapping(ivals.getClass()), ivals.length);
		
		for (int i=0; i< ivals.length; i++) {
			Array.set(array, i, ivals[i]);
		}
		return (E[]) array;
	}
	
	/**
	 * @param fvals
	 * @throws NegativeArraySizeException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws ArrayIndexOutOfBoundsException
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Number> E[] box(final float[] fvals)
			throws NegativeArraySizeException, NullPointerException,
			IllegalArgumentException, ArrayIndexOutOfBoundsException {
		Float[] array=new Float[fvals.length];
		for (int i=0; i< fvals.length; i++) {
			array[i]=fvals[i];
		}
		return (E[]) array;
	}
	/*
	 * nice but slow
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Number> E[] box(final Object ovals)
		throws NegativeArraySizeException, NullPointerException,
		IllegalArgumentException, ArrayIndexOutOfBoundsException, UnsupportedTypeException {
		if (!ovals.getClass().isArray())
			throw new IllegalArgumentException("argument not an array "+ovals.getClass());
		final int length=Array.getLength(ovals);
		
		//System.out.println(ovals.getClass().getCanonicalName());
		//System.out.println(length);
		Object obj=Array.newInstance(Util.getTypeMapping(ovals.getClass()), length);
		E[] array=(E[]) obj;
		Access<E> access= Access.rawAccess (ovals, null);
		
		
		for (int i=0; i< length; i++) {
			array[i]=access.element(i);
			//Array.set(array, i, Array.get(ovals, i));
		}
		return array;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <E extends Number> E[] box(double[] dvals) 
	throws NegativeArraySizeException, NullPointerException,
	IllegalArgumentException, ArrayIndexOutOfBoundsException {
		//Object array=Array.newInstance(Util.getTypeMapping(dvals.getClass()), dvals.length);
		
		Double[] array=new Double[dvals.length];
		for (int i=0; i< dvals.length; i++) {
			//Array.set(array, i, dvals[i]);
			array[i]=dvals[i];
		}
		return (E[]) array;
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Number> E[] box(final short[] svals) 
	throws NegativeArraySizeException, NullPointerException,
	IllegalArgumentException, ArrayIndexOutOfBoundsException {

		//Object array=Array.newInstance(Util.getTypeMapping(svals.getClass()), svals.length);
		Short[] array=new Short[svals.length];
		for (int i=0; i< svals.length; i++) {
			//Array.set(array, i, svals[i]);
			array[i]=svals[i];
		}
		return (E[]) array;
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Number> E[] box(final byte[] bvals) 
	throws NegativeArraySizeException, NullPointerException,
	IllegalArgumentException, ArrayIndexOutOfBoundsException {

		//Object array=Array.newInstance(Util.getTypeMapping(bvals.getClass()), bvals.length);
		Byte[] array=new Byte[bvals.length];
		for (int i=0; i< bvals.length; i++) {
			//Array.set(array, i, bvals[i]);
			array[i]=bvals[i];
		}
		return (E[]) array;
	}

}
