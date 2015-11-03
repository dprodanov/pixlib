package ijaux.iter.dir;

import ijaux.*;
import ijaux.datatype.*;
import ijaux.datatype.access.*;
import ijaux.hypergeom.index.*;

/*
 *  Implements cursor -like treatment of image data.
 */
public class VectorCursor<E> extends VectorIterator<E> implements BlockAccess  {
		
	protected Access<?> access;
	
	public VectorCursor(Object pixels, int [] dim) throws UnsupportedTypeException {
		pointIdx= GridIndex.create(dim);
		access=Access.rawAccess(pixels, pointIdx);
		returnType=access.getType();
		size=Util.cumprod(dim);
		this.dim=dim;
		n=dim.length;
		coordinates=new int[n];
		dir=Util.rampInt(n, n);
	}	
	
	public <T extends Number> VectorCursor(Access<T> acc, int[] dim) {
		access=acc;	 
		returnType= access.getType();
		pointIdx= GridIndex.create(dim);
		access.setIndexing(pointIdx);		
		size=Util.cumprod(dim);
		dim=pointIdx.getDim();
		n=dim.length;
		coordinates=new int[n];
		dir=Util.rampInt(n, n);
	}

	public <T extends Number> VectorCursor(Access<T> acc, Indexing<int[]> aind) {
		access=acc;
		returnType=access.getType();
		access.setIndexing(aind);
		pointIdx=aind;
		dim=pointIdx.getDim();
		n=aind.getNDim();
		size=Util.cumprod(dim);
		coordinates=new int[n];
		dir=Util.rampInt(n, n);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E next() {
		Object ret=null;		
		if (returnType== byte.class)
			ret= getByteArray();
		if (returnType== short.class)
			ret= getShortArray();
		if (returnType== int.class)
			ret=  getIntArray();
		if (returnType== float.class)
			ret=  getFloatArray();	
		if (returnType== double.class)
			ret=  getDoubleArray();
		if (returnType== boolean.class)
			ret=  getBoolArray();
		i++;
		return (E) ret;
	}

	public byte[] nextByte() {
		final byte[] ret= getByteArray();
		i++;
		return ret;
	}
	
	public short[] nextShort() {
		final short[] ret=  getShortArray();
		i++;
		return ret;
	}
	
	public int[] nextInt() {
		final int[] ret=  getIntArray();
		i++;
		return ret;
	}
	
	public float[] nextFloat() {
		final float[] ret=  getFloatArray();
		i++;
		return ret;
	}
	
	public double[] nextDouble() {		
		final double [] ret=  getDoubleArray();
		i++;
		return ret;
	}

	public boolean[] nextBool() {	
		final boolean[] ret=  getBoolArray();
		i++;
		return ret;
	}

	public char[] nextChar() {	
		final char[] ret=  getCharArray();
		i++;
		return ret;
	}
	
 
	@Override
	public byte[] getByteArray() {
		//final byte[]ret=new byte[blockSize];
		byte[]ret= (byte[]) buffer;
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		
		for (int k=0; k<blockSize; k++) {
			try {
				ret[k]=access.elementByte(coordinates);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("ArrayIndexOutOfBoundsException : coordinates ...");
				Util.printIntArray(coordinates);
			}		
			coordinates[x]++;		
		}		
		
		return ret;
	}


	

	@Override
	public short[] getShortArray() {
		//final short[]ret=new short[blockSize];
		short[]ret= (short[]) buffer;
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			ret[i]=access.elementShort(coordinates);		
			//Util.printIntArray(coordinates);
			coordinates[x]++;
		
		}

		return ret;
	}

	@Override
	public float[] getFloatArray() {
		//final float[]ret=new float[blockSize];
		float[]ret= (float[]) buffer;
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			ret[i]=access.elementFloat(coordinates);		
			//Util.printIntArray(coordinates);
			coordinates[x]++;
		
		}

		return ret;
	}

	@Override
	public double[] getDoubleArray() {
		//final double[]ret=new double[blockSize];
		double[]ret= (double[]) buffer;
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		//Util.printIntArray(coordinates);
		for (int i=0; i<blockSize; i++) {
			ret[i]=access.elementDouble(coordinates);					
			coordinates[x]++;
		
		}

		return ret;
	}

	@Override
	public void putDoubleArray(double[] array) {
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			access.putDouble(coordinates, array[i]);
			coordinates[x]++;
		}

		i++;
	}
	
	@Override
	public void putFloatArray(float[] array) {
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			access.putDouble(coordinates, array[i]);
			coordinates[x]++;
		}

		i++;
	}
	
	@Override
	public void putByteArray(byte[] array) {
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			access.putInt(coordinates, array[i]);
			coordinates[x]++;
		}
		i++;
	}
	
	@Override
	public int[] getIntArray() {
		//final int[]ret=new int[blockSize];
		int[]ret= (int[]) buffer;
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			ret[i]=access.elementInt(coordinates);		
			//Util.printIntArray(coordinates);
			coordinates[x]++;
		
		}

		return ret;
	}

	@Override
	public boolean[] getBoolArray() {
		//final boolean[]ret=new boolean[blockSize];
		boolean[]ret= (boolean[]) buffer;
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			ret[i]=access.elementBool(coordinates);		
			//Util.printIntArray(coordinates);
			coordinates[x]++;
		
		}
		return ret;
	}

	@Override
	public char[] getCharArray() {
		//final char[]ret=new char[blockSize];
		char[]ret= (char[]) buffer;
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			ret[i]=access.elementChar(coordinates);		
			//Util.printIntArray(coordinates);
			coordinates[x]++;
		
		}

		return ret;
	}

	@Override
	public void put(E value) {
		//System.out.println("block size "+blockSize +" "+ returnType);
		if (returnType== byte.class)
				putByteArray((byte[])value);
		if (returnType== short.class)
			  putShortArray((short[])value);
		if (returnType== int.class)
			 putIntArray((int[])value);
		if (returnType== float.class)
			putFloatArray((float[]) value);
		if (returnType== double.class)
			 putDoubleArray((double[]) value);
		if (returnType== boolean.class)
			 putBoolArray((boolean[])value);
		
	}

	@Override
	public void putIntArray(int[] array) {
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			access.putInt(coordinates, array[i]);
			coordinates[x]++;
		}
		i++;
		
	}

	@Override
	public void putShortArray(short[] array) {
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			access.putInt(coordinates, array[i]);
			coordinates[x]++;
		}
		i++;
		
	}

	@Override
	public void putBoolArray(boolean[] array) {
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			access.putBool(coordinates, array[i]);
			coordinates[x]++;
		}
		i++;
		
	}



}
