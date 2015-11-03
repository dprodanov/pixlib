/**
 * 
 */
package ijaux.datatype.access;

import ijaux.Util;
import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.index.Indexing;

import java.lang.reflect.Array;

/**
 * @author Dimiter Prodanov
 *	in fact ArrayAccess 
 */
public abstract class Access<N> extends IndexedAccess<N>
								implements PrimitiveAccess<int[]>  {
			
			 
	protected boolean setpixels=false;

	protected Indexing<int[]> pIndex;
	
	protected int mask=byteMask;
	
	public void setMask(int m) {
		mask=m;
	}
	
	public void setPixels(Object cpixels) {
		Class<? extends Object> c=cpixels.getClass();
		if (debug) System.out.println(c.getCanonicalName());
		if (isPrimitive1DArray(c)) {
			pixels=cpixels;
			setpixels=true;
			type=Util.getPrimitiveType(c);
		} else {
			throw new IllegalArgumentException("Not a 1D array");
		}
	 
	}

	public void setIndexing(Indexing<int[]> pindex) {
		pIndex=pindex;
	}
	
	public Indexing<int[]> getIndexing() {
		return pIndex;
	}

	@SuppressWarnings("unchecked")
	@Override
	public N element(int index) {
		return (N) Array.get(pixels,index);
	}
	
	public void putE(int index, N value) {
		Array.set(pixels, index, value);
	}

	@Override
	public N element(int[] coords) {
		int ind=pIndex.indexOf(coords);
		return element(ind);
	}
	
	public void putV(int[] coords, N value) {
		int ind=pIndex.indexOf(coords);
		Array.set(pixels, ind, value);
	}
	
	public void put(Pair<int[],N> pair) {
		putV(pair.first, pair.second);
	}
	
	/* (non-Javadoc)
	 * @see ijaux.datatype.access.BlockAccess#getByteArray()
	 */
	@Override
	public byte[] getByteArray() {
		if (type == byte.class)
		return (byte[]) pixels;
		else throw new UnsupportedOperationException("not a byte[]");
	}
	
	/* (non-Javadoc)
	 * @see ijaux.datatype.access.BlockAccess#getShortArray()
	 */
	@Override
	public short[] getShortArray() {
		if (type == short.class)
		return (short[]) pixels;
		else throw new UnsupportedOperationException("not a short[]");
	}
	
	/* (non-Javadoc)
	 * @see ijaux.datatype.access.BlockAccess#getFloatArray()
	 */
	@Override
	public float[] getFloatArray() {
		if (type == float.class)
		return (float[]) pixels;
		else throw new UnsupportedOperationException("not a float[]");
	}
	
	/* (non-Javadoc)
	 * @see ijaux.datatype.access.BlockAccess#getDoubleArray()
	 */
	@Override
	public double[] getDoubleArray() {
		if (type == double.class)
		return (double[]) pixels;
		else throw new UnsupportedOperationException("not a double[]");
	}
	
	/* (non-Javadoc)
	 * @see ijaux.datatype.access.BlockAccess#getIntArray()
	 */
	@Override
	public int[] getIntArray() {
		if (type == int.class)
		return (int[]) pixels;
		else throw new UnsupportedOperationException("not an int[]");
	}
	
	
	/* (non-Javadoc)
	 * @see ijaux.datatype.access.BlockAccess#getBoolArray()
	 */
	@Override
	public boolean[] getBoolArray() {
		if (type == boolean.class)
		return (boolean[]) pixels;
		else throw new UnsupportedOperationException("not an boolean[]");
	}
	
	/* (non-Javadoc)
	 * @see ijaux.datatype.access.BlockAccess#getCharArray()
	 */
	@Override
	public char[] getCharArray() {
		if (type == char.class)
		return (char[]) pixels;
		else throw new UnsupportedOperationException("not an boolean[]");
	}
	
	public void putDoubleArray(double[] array) {
		if (type == double.class) {
			System.arraycopy(array, 0, pixels, 0, array.length);
		}	
			else throw new UnsupportedOperationException("not a double[]");
	}
	
	public void putFloatArray(float[] array) {
		if (type == float.class) {
			System.arraycopy(array, 0, pixels, 0, array.length);
		}	
			else throw new UnsupportedOperationException("not a float[]");
	}
	
	public void putByteArray(byte[] array) {
		if (type == byte.class) {
			System.arraycopy(array, 0, pixels, 0, array.length);
		}	
			else throw new UnsupportedOperationException("not a byte[]");
	}
	
	public void putShortArray(short[] array) {
		if (type == short.class) {
			System.arraycopy(array, 0, pixels, 0, array.length);
		}	
			else throw new UnsupportedOperationException("not a short[]");
	}
	
	public void putIntArray(int[] array) {
		if (type == int.class) {
			System.arraycopy(array, 0, pixels, 0, array.length);
		}	
			else throw new UnsupportedOperationException("not a int[]");
	}
	
	public void putBoolArray(boolean[] array) {
		if (type == boolean.class) {
			System.arraycopy(array, 0, pixels, 0, array.length);
		}	
			else throw new UnsupportedOperationException("not a boolean[]");
	}
	
	public Object getArray() {
		return pixels;
	}
	
	protected boolean isPrimitive1DArray(Class<?> c) {
		return (c==byte[].class || 
				c==short[].class || 
				c==int[].class ||
				c==float[].class ||
				c==double[].class ||
				c==boolean[].class ||
				c==char[].class);
	}
	
	public Class<?> getType() {
		return type;
	}
	
	public long length(){
		final int[] sz=size();
		if (sz[1] ==0) sz[1]=1;
		return sz[0]*sz[1];
	}
	
	/*
	 * Factory type method
	 */
	@SuppressWarnings("unchecked")
	public static <N> Access<N>  rawAccess(Object pixels, Indexing<int[]> index) 
			throws UnsupportedTypeException {
		Class<?> type=Util.getPrimitiveType(pixels.getClass());
		if (debug) System.out.println("AT:" +type);
		if (type==byte.class) return (Access<N>) new ByteAccess(pixels, index);
		if (type==short.class) return (Access<N>) new ShortAccess(pixels, index);
		if (type==int.class) return (Access<N>) new IntAccess(pixels, index);
		if (type==float.class) return (Access<N>) new FloatAccess(pixels, index);
		if (type==double.class) return (Access<N>) new DoubleAccess(pixels, index);
		throw new UnsupportedTypeException("Not primitive  or unsupported type "+type);
	}

	/*
	 * Factory type method
	 */
	public static <N> Access<N> cloneAccess(Object pixels, Indexing<int[]> index) 
			throws UnsupportedTypeException {
		 Access<N>  access=rawAccess(  pixels, index);
		 return cloneAccess(access);
	}
	
	/*
	 * Factory type method. Creates a new empty Access object based on an existing one
	 */
	@SuppressWarnings("unchecked")
	public static <N> Access<N> cloneAccess(Access<N> access) {
		 int sz =access.size()[0];
		 final Class<?> type=access.getType();
		 //System.out.println("cloning "+type);
		 Indexing<int[]> index=access.pIndex;
		 //System.out.println("cloning "+index.getClass());
		 if (type==byte.class) {
			 byte[] bpixels=new byte[sz];
			 return (Access<N>) new ByteAccess(bpixels, index);
		 }
		 if (type==short.class)  {
			 short[] spixels=new short[sz];
			 return (Access<N>) new ShortAccess(spixels, index);
		 }
		 if (type==int.class)  {
			 int[] ipixels=new int[sz];
			 return (Access<N>) new IntAccess(ipixels, index);
		 }
		 if (type==float.class)  {
			 float[] fpixels=new float[sz];
			 return (Access<N>) new FloatAccess(fpixels, index);
		 }
		 if (type==double.class) {
			 double[] dpixels=new double[sz];
			 return (Access<N>) new DoubleAccess(dpixels, index);
		 }
		 return null;
	}
	
	/*
	 * Factory type method. Creates a new empty Access object
	 */
	public static Access<?> create(Class<?> type, int n, Indexing<int[]> index) throws UnsupportedTypeException {
		Object obj=Array.newInstance(type, n);	
		return rawAccess(obj,index);
	}
	
	/*
	 * Factory type method. Creates a new Access object based on an existing one
	 */
	public static <N> Access<N> of (Access<N> access, Indexing<int[]> index) {
		try {
			return rawAccess(access.pixels, index);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
