package ijaux.funct.iter;

import ijaux.funct.SimpleFunction;
import ijaux.iter.array.*;

public class DoubleFunctIterator extends DoubleForwardIterator   implements
FunctionIterator<Double> 	{
	
	private double[] dpixels;

	public DoubleFunctIterator(Object cpixels) {
		super(cpixels);
		dpixels=(double[]) pixels; 
	}
	
	public DoubleFunctIterator(Object pixels, SimpleFunction<Double> f) {
		this(pixels);
		setFunct(f);
	}
	
	
	@Override
	public <N> Double nextf(N a) {
		final double elem= dpixels[i];
		final Double result=funct.op(elem, a);
		dpixels[i++]=result;		
		return result;	
	}

	@Override
	public Double nextf(byte a) {
		return nextf(a);
	}

	@Override
	public Double nextf(short a) {
		return nextf(a);
	}

	@Override
	public Double nextf(int a) {
		final double elem= dpixels[i];
		final Double result=funct.opDouble(elem, a);
		dpixels[i++]=result;
		return result;	 
	}

	@Override
	public Double nextf(float a) {
		final double elem= dpixels[i];
		final Double result=funct.opDouble(elem, a);
		dpixels[i++]=result;		
		return result;	 
	}

	@Override
	public Double nextf(double a) {
		final double elem= dpixels[i];
		final Double result=funct.opDouble(elem, a);
		dpixels[i++]=result;		
		return result;	 
	}

	@Override
	public Double nextf(boolean a) {
		final boolean elem= nextBool();
		dec();
		final Double result=funct.opBool(elem, a);
		dpixels[i++]=result;
		return result;
	}

	@Override
	public Double nextf(char a) {
		return nextf((int)a);
	}
}
