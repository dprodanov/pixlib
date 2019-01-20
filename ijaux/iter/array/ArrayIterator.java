package ijaux.iter.array;

import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.funct.*;
import ijaux.iter.AbstractIterator;

import java.lang.reflect.Array;


public abstract class ArrayIterator<N> extends AbstractIterator<N> implements PrimitiveIterator {
  
	protected Object pixels;
	 
	protected boolean setpixels=false;
 
	protected int step=1;
	
	//protected SimpleFunction<N> funct;
	
	protected int mask=byteMask;
	
	@SuppressWarnings("unchecked")
	public static <N> ArrayIterator<N>  rawIterator(Object pixels) throws UnsupportedTypeException {
		Class<?> type=Util.getPrimitiveType(pixels.getClass());
		//System.out.println("AF:" +type);
		if (type==byte.class) return (ArrayIterator<N>)new ByteForwardIterator(pixels);
		if (type==short.class) return (ArrayIterator<N>) new ShortForwardIterator(pixels);
		if (type==int.class) return (ArrayIterator<N>) new IntForwardIterator(pixels);
		if (type==float.class) return (ArrayIterator<N>) new FloatForwardIterator(pixels);
		if (type==double.class) return (ArrayIterator<N>) new DoubleForwardIterator(pixels);
		throw new UnsupportedTypeException("Not a primitive  or unsupported type");
	}
	
	/*
	@SuppressWarnings("unchecked")
	public static <N> FunctionIterator<N>  functIterator(Object pixels, SimpleFunction<N> funct) throws UnsupportedTypeException {
		Class<?> type=Util.getPrimitiveType(pixels.getClass());
		//System.out.println("AF:" +type);
		
		if (type==byte.class) {
			final SimpleFunction<Byte> f=(SimpleFunction<Byte>) funct;
			return (FunctionIterator<N>) new ByteFunctIterator(pixels,f);
		}
		if (type==short.class) {
			final SimpleFunction<Short> f=(SimpleFunction<Short>) funct;
			return  (FunctionIterator<N>) new ShortFunctIterator(pixels, f);
		}
		if (type==int.class) {
			final SimpleFunction<Integer> f=(SimpleFunction<Integer>) funct;
			return  (FunctionIterator<N>) new IntFunctIterator(pixels, f);
		}
		if (type==float.class) {
			final SimpleFunction<Float> f=(SimpleFunction<Float>) funct;
			return  (FunctionIterator<N>) new FloatFunctIterator(pixels, f);
		}
		if (type==double.class) {
			final SimpleFunction<Double> f=(SimpleFunction<Double>) funct;
			return  (FunctionIterator<N>) new DoubleFunctIterator(pixels,f);
		}
		throw new UnsupportedTypeException("Not a primitive  or unsupported type");
	}
	
	*/
	/*
	@SuppressWarnings("unchecked")
	public static <N> FunctionIterator<N>  functIterator(ArrayIterator<N> iter, SimpleFunction<N> funct) 
			 {
		Class<?> type=iter.returnType;
		//System.out.println("AF:" +type);
		
		if (type==byte.class) {
			final SimpleFunction<Byte> f=(SimpleFunction<Byte>) funct;
			return (FunctionIterator<N>) new ByteFunctIterator(iter.pixels,f);
		}
		if (type==short.class) {
			final SimpleFunction<Short> f=(SimpleFunction<Short>) funct;
			return  (FunctionIterator<N>) new ShortFunctIterator(iter.pixels, f);
		}
		if (type==int.class) {
			final SimpleFunction<Integer> f=(SimpleFunction<Integer>) funct;
			return  (FunctionIterator<N>) new IntFunctIterator(iter.pixels, f);
		}
		if (type==float.class) {
			final SimpleFunction<Float> f=(SimpleFunction<Float>) funct;
			return  (FunctionIterator<N>) new FloatFunctIterator(iter.pixels, f);
		}
		if (type==double.class) {
			final SimpleFunction<Double> f=(SimpleFunction<Double>) funct;
			return  (FunctionIterator<N>) new DoubleFunctIterator(iter.pixels,f);
		}
		
		return null;
	}
	*/
	
	@Override
	public void remove() { throw new UnsupportedOperationException(); }
	
	@Override
	public boolean hasNext() {
 		return (i<size);
	}

	public void setStep(int s) {
		step=s;
	}
	
	/* (non-Javadoc)
	 * @see ijaux.iter.array.PrimitiveIterator#nextInt()
	 */
	public abstract int nextInt();
	
	/* (non-Javadoc)
	 * @see ijaux.iter.array.PrimitiveIterator#nextByte()
	 */
	public abstract byte nextByte();
	
	/* (non-Javadoc)
	 * @see ijaux.iter.array.PrimitiveIterator#nextShort()
	 */
	public abstract short nextShort();
	
	/* (non-Javadoc)
	 * @see ijaux.iter.array.PrimitiveIterator#nextFloat()
	 */
	public abstract float nextFloat();
	
	/* (non-Javadoc)
	 * @see ijaux.iter.array.PrimitiveIterator#nextDouble()
	 */
	public abstract double nextDouble();
	
	
	public boolean nextBool() {
		return false;
	}
	
	public char nextChar() {
		return (char)nextShort();
	}
		
	/* (non-Javadoc)
	 * @see ijaux.iter.array.PrimitiveIterator#putInt(int)
	 */
	public abstract void putInt(int val);
	
	/* (non-Javadoc)
	 * @see ijaux.iter.array.PrimitiveIterator#putByte(byte)
	 */
	public abstract void putByte(byte val);
	
	/* (non-Javadoc)
	 * @see ijaux.iter.array.PrimitiveIterator#putShort(short)
	 */
	public abstract void putShort(short val);
	
	/* (non-Javadoc)
	 * @see ijaux.iter.array.PrimitiveIterator#putFloat(float)
	 */
	public abstract void putFloat(float val);
	
	/* (non-Javadoc)
	 * @see ijaux.iter.array.PrimitiveIterator#putDouble(double)
	 */
	public abstract void putDouble(double val);
	
	/* (non-Javadoc)
	 * @see ijaux.iter.array.PrimitiveIterator#putBool(boolean)
	 */
	public void putBool(boolean val) {
		
	}
	
	/* (non-Javadoc)
	 * @see ijaux.iter.array.PrimitiveIterator#putChar(char)
	 */
	public void putChar(char val) {
		putShort((short)val);
	}
		
	public void setPixels(Object cpixels) {
		Class<? extends Object> c=cpixels.getClass();
		//System.out.println(c.getCanonicalName());
 
		if (c.isArray()) {
			pixels=cpixels;
			size=Array.getLength(pixels);
			returnType=Util.getTypeMapping(pixels.getClass());
			setpixels=true;
		} else {
			throw new IllegalArgumentException("Not an array");
		}
	 
	}
	
	public void reset(){
		if (i>=size)
			set(size-1);
		if (i<0)
			set(0);
	}
	
	/*
	public SimpleFunction<N> getFunct() {
		return funct;
	}

	public void setFunct(SimpleFunction<N> funct) {
		this.funct = funct;
	}
	*/
	
	public void setMask(int m) {
		mask=m;
	}
	
 
}
