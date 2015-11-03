/**
 * 
 */
package ijaux.datatype.access;


import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.index.Indexing;

/**
 * @author Dimiter Prodanov
 *
 */
public abstract class PagedAccess<N> extends Access<N> {
	
	 
	protected int pageSize=1;
	
	protected int x=0;
	protected int y=0;	

	 
	public PagedAccess(Object cpixels, Indexing<int[]> aind) {
		setPixels(cpixels);
		setIndexing(aind);
		debug=false;
	}
	 
	public void setPixels(Object cpixels) {
		Class<?> c=cpixels.getClass();
		if (debug) 
			System.out.println(c.getCanonicalName());
		if (isPrimitive2DArray(c)){
			type=Util.getPrimitiveType(c);

			pixels=cpixels;
			setpixels=true;

		} else {
			try {
				final Object[] o= (Object[]) cpixels;
				c=o[0].getClass();
				if (debug) 
					System.out.println(c.getCanonicalName());
				if (isPrimitiveArray(c)){
					type=Util.getPrimitiveType(c);

					//pixels=cpixels;
					pixels=Util.castObjectArray(o);
					setpixels=true;
				}
			} catch (ClassCastException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IllegalArgumentException("Not a 2D array");
			}
			
		}
		
	 
	}
	
	

	protected boolean isPrimitive2DArray(Class<?> c) {
		return (c==byte[][].class || 
				c==short[][].class || 
				c==int[][].class ||
				c==float[][].class ||
				c==double[][].class ||
				c==boolean[][].class ||
				c==char[][].class);
	}
	
	protected boolean isPrimitiveArray(Class<?> c) {
		return (c==byte[].class || 
				c==short[].class || 
				c==int[].class ||
				c==float[].class ||
				c==double[].class ||
				c==boolean[].class ||
				c==char[].class);
	}
	
	public static <N> PagedAccess  <N>rawAccess(Object pixels, Indexing<int[]> index) throws UnsupportedTypeException {

		Class<?> type=Util.getPrimitiveType(pixels.getClass());
		
		if (pixels.getClass()==Object[].class) {
			final Object o= ((Object[]) pixels)[0];
			type=Util.getPrimitiveType(o.getClass());
			return get(pixels, index, type);
		}
		if (debug) System.out.println("PAT:" +type);
		
		return get(pixels, index, type);

	}
	
	@SuppressWarnings("unchecked")
	private static <N> PagedAccess <N> get(Object pixels, Indexing<int[]> index, Class<?> type) throws UnsupportedTypeException {
		if (type==byte.class) return (PagedAccess<N>) new PagedByteAccess(pixels, index);
		if (type==short.class) return (PagedAccess<N>) new PagedShortAccess(pixels, index);
		if (type==int.class) return (PagedAccess<N>) new PagedIntAccess(pixels, index);
		if (type==float.class) return (PagedAccess<N>) new PagedFloatAccess(pixels, index);
		if (type==double.class) return (PagedAccess<N>) new PagedDoubleAccess(pixels, index);
		throw new UnsupportedTypeException("Not primitive or unsupported type : " + pixels.getClass());

	}
	
	/*
	 * Factory type method
	 */
	public static <N> PagedAccess<N> cloneAccess(Object pixels, Indexing<int[]> index) 
			throws UnsupportedTypeException {
		 Access<N>  access=rawAccess(  pixels, index);
		 return cloneAccess(access);
	}
	
	/*
	 * Factory type method
	 */
	@SuppressWarnings("unchecked")
	public static <N> PagedAccess<N> cloneAccess(Access<N> access) {
		final int sz[] =access.size();
		final Class<?> type=access.getClass();
		 Indexing<int[]> index=access.pIndex;
		 if (type==byte.class) {
			 byte[][] bpixels=new byte[sz[0]][sz[1]];
			 return (PagedAccess<N>) new PagedByteAccess(bpixels, index);
		 }
		 if (type==short.class)  {
			 short[][] spixels=new short[sz[0]][sz[1]];
			 return (PagedAccess<N>) new PagedShortAccess(spixels, index);
		 }
		 if (type==int.class)  {
			 int[][] ipixels=new int[sz[0]][sz[1]];
			 return (PagedAccess<N>) new PagedIntAccess(ipixels, index);
		 }
		 if (type==float.class)  {
			 float[][] fpixels=new float[sz[0]][sz[1]];
			 return (PagedAccess<N>) new PagedFloatAccess(fpixels, index);
		 }
		 if (type==double.class) {
			 double[][] dpixels=new double[sz[0]][sz[1]];
			 return (PagedAccess<N>) new PagedDoubleAccess(dpixels, index);
		 }
		 return null;
	}
	
	
	public byte[][] getByte2DArray() {
		if (type == byte.class)
		return (byte[][]) pixels;
		else throw new UnsupportedOperationException("not a byte[][]");
	}
	
	public short[][] getShort2DArray() {
		if (type == short.class)
		return (short[][]) pixels;
		else throw new UnsupportedOperationException("not a short[][]");
	}
	
	public float[][] getFloat2DArray() {
		if (type == float.class)
		return (float[][]) pixels;
		else throw new UnsupportedOperationException("not a float[][]");
	}
	
	public double[][] getDouble2DArray() {
		if (type == double.class)
		return (double[][]) pixels;
		else throw new UnsupportedOperationException("not a double[][]");
	}
	
	public int[][] getInt2DArray() {
		if (type == int.class)
		return (int[][]) pixels;
		else throw new UnsupportedOperationException("not an int[][]");
	}
	
	
	public boolean[][] getBool2DArray() {
		if (type == boolean.class)
		return (boolean[][]) pixels;
		else throw new UnsupportedOperationException("not an boolean[][]");
	}
	
	public char[][] getChar2DArray() {
		if (type == char.class)
		return (char[][]) pixels;
		else throw new UnsupportedOperationException("not an boolean[][]");
	}
	
	@Override
	public byte[] getByteArray() {
		 throw new UnsupportedOperationException("not a byte[]");
	}
	
	@Override
	public short[] getShortArray() {
		throw new UnsupportedOperationException("not a short[]");
	}
	
	@Override
	public float[] getFloatArray() {
		 throw new UnsupportedOperationException("not a float[]");
	}
	
	@Override
	public double[] getDoubleArray() {
		 throw new UnsupportedOperationException("not a double[]");
	}
	
	@Override
	public int[] getIntArray() {
		 throw new UnsupportedOperationException("not an int[]");
	}
	
	@Override
	public boolean[] getBoolArray() {
		 throw new UnsupportedOperationException("not an boolean[]");
	}
	
	@Override
	public char[] getCharArray() {
	  throw new UnsupportedOperationException("not an boolean[]");
	}
	
}
