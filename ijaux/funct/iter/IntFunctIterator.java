package ijaux.funct.iter;

import ijaux.funct.SimpleFunction;
import ijaux.iter.array.*;

public class IntFunctIterator extends IntForwardIterator implements
FunctionIterator<Integer>
{
	
	private int[] ipixels;

	public IntFunctIterator(Object cpixels) {
		super(cpixels);
		ipixels=(int[]) pixels; 
	}
	
	public IntFunctIterator(Object pixels, SimpleFunction<Integer> f) {
		this(pixels);
		setFunct(f);
	}
	
	
	@Override
	public <N> Integer nextf(N a) {
		final int elem= ipixels[i] & mask;
		final Integer result=funct.op(elem, a);
		ipixels[i++]=result;		
		return result;	
	}

	@Override
	public Integer nextf(byte a) {
		return nextf(a);
	}

	@Override
	public Integer nextf(short a) {
		return nextf(a);
	}

	@Override
	public Integer nextf(int a) {
		final int elem= ipixels[i];
		final Integer result=funct.opInt(elem, a);
		ipixels[i++]=result;
		return result;	 
	}

	@Override
	public Integer nextf(float a) {
		final float elem= ipixels[i] & mask;
		final Integer result=funct.opFloat(elem, a);
		ipixels[i++]=result;		
		return result;	 
	}

	@Override
	public Integer nextf(double a) {
		final double elem= ipixels[i] & mask;
		final Integer result=funct.opDouble(elem, a);
		ipixels[i++]=result;		
		return result;	 
	}

	@Override
	public Integer nextf(boolean a) {
		final boolean elem= nextBool();
		dec();
		final Integer result=funct.opBool(elem, a);
		ipixels[i++]=result;
		return result;
	}

	@Override
	public Integer nextf(char a) {
		return nextf((int)a);
	}
}
