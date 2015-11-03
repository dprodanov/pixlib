/**
 * 
 */
package ijaux.iter.seq;

//import java.lang.reflect.*;
import ijaux.*;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.iter.AbstractIterator;

import java.lang.reflect.Array;
import java.util.*;
 

/**
 * @author Dimiter Prodanov
 *
 */
public abstract class RasterIterator<E> extends AbstractIterator<E> implements RandomAccess {
	
	protected Object pixels;
 
	
	protected boolean setpixels=false;
	
	protected Access<E> access;
	
	@Override
	public void remove() { throw new UnsupportedOperationException(); }
	 
	@Override
	public void put(E value) {
		access.putE(i, value);
	}
	
	public void putInt(int value) {
		access.putInt(i, value);
	}
	
	public void putShort(short value) {
		access.putShort(i, value);
	}
	
	public void putByte(byte value) {
		access.putByte(i, value);
	}
	
	public void putFloat(float value) {
		access.putFloat(i, value);
	}
	
	public void putDouble(double value) {
		access.putDouble(i, value);
	}
 
	protected synchronized Object getArray (int z, int blockSize) {
		
		  	Object aret =  Array.newInstance(returnType, blockSize);
		  	try {
		  		Access<E> aux=Access.rawAccess(aret, null);
		  		int u=0;
		  		for (int i=z; i<z+blockSize; i++) {
		  			final E value=access.element(i%size);
		  			aux.putE(u, value);
		  			u++;
		  		}
		  		this.i+=blockSize;
			} catch (UnsupportedTypeException e) {
				getPrimitiveArray(i,blockSize);
				e.printStackTrace();
			}
		 
		return  aret;
	}
	
	private Object getPrimitiveArray(int z, int blockSize) {
	
		if (returnType==byte.class) {
			byte[] aret = new byte[blockSize]; 
			int u=0;
			for (int i=z; i<z+blockSize; i++) {
				aret[u] =access.elementByte(i%size);// Array.getByte(pixels, i%size);
				u++;
			}
			this.i+=blockSize;
			return   aret;
		}
		
		if (returnType==short.class) {
			short[] aret = new short[blockSize]; 
			int u=0;
			for (int i=z; i<z+blockSize; i++) {
				aret[u] =access.elementShort(i%size);//Array.getShort(pixels, i%size);
				u++;
			}
			this.i+=blockSize;
			return   aret;
		}
		
		if (returnType==int.class) {
			int[] aret = new int[blockSize]; 
			int u=0;
			for (int i=z; i<z+blockSize; i++) {
				aret[u] =access.elementInt(i%size);//Array.getInt(pixels, i%size);
				u++;
			}
			this.i+=blockSize;
			return   aret;
		}
		if (returnType==float.class) {
			float[] aret = new float[blockSize]; 
			int u=0;
			for (int i=z; i<z+blockSize; i++) {
				aret[u] =access.elementFloat(i%size);//Array.getFloat(pixels, i%size);
				u++;
			}
			this.i+=blockSize;
			return  aret;
		}
		
		if (returnType==double.class) {
			double[] aret = new double[blockSize]; 
			int u=0;
			for (int i=z; i<z+blockSize; i++) {
				aret[u] =access.elementDouble(i%size);//Array.getDouble(pixels, i%size);
				u++;
			}
			this.i+=blockSize;
			return   aret;
		}
		
		return null;
	}
	
	public void setPixels(Object cpixels) {
		Class<? extends Object> c=cpixels.getClass();
		if (c.isArray()) {
			pixels=cpixels;
			size=Array.getLength(pixels);
			returnType=Util.getTypeMapping(pixels.getClass());
			setpixels=true;
			setAccess(cpixels);
		} else {
			throw new IllegalArgumentException("Not an array");
		}
	 
	}
	
	public void setAccess(Object pixels)   {
		try {
			access=Access.rawAccess(pixels, null);
			
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
	}

}
