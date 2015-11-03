package ijaux.funct.iter;

import ijaux.funct.SimpleFunction;
import ijaux.iter.array.ShortForwardIterator;

public class ShortFunctIterator extends ShortForwardIterator implements
FunctionIterator<Short>
{
	
	private short[] spixels;

	public ShortFunctIterator(Object cpixels) {
		super(cpixels);
		spixels=(short[]) pixels; 
	}
	
	public ShortFunctIterator(Object pixels, SimpleFunction<Short> f) {
		this(pixels);
		setFunct(f);
	}
	
	
	@Override
	public <N> Short nextf(N a) {
		final int elem= spixels[i] & mask;
		final Short result=funct.op(elem, a);
		spixels[i++]=result;		
		return result;	
	}

	@Override
	public Short nextf(byte a) {
		return nextf(a);
	}

	@Override
	public Short nextf(short a) {
		return nextf(a);
	}

	@Override
	public Short nextf(int a) {
		final int elem= spixels[i];
		final Short result=funct.opInt(elem, a);
		spixels[i++]=result;
		return result;	 
	}

	@Override
	public Short nextf(float a) {
		final float elem= spixels[i] & mask;
		final Short result=funct.opFloat(elem, a);
		spixels[i++]=result;		
		return result;	 
	}

	@Override
	public Short nextf(double a) {
		final double elem= spixels[i] & mask;
		final Short result=funct.opDouble(elem, a);
		spixels[i++]=result;		
		return result;	 
	}

	@Override
	public Short nextf(boolean a) {
		final boolean elem= nextBool();
		dec();
		final Short result=funct.opBool(elem, a);
		spixels[i++]=result;
		return result;
	}

	@Override
	public Short nextf(char a) {
		return nextf((int)a);
	}

}