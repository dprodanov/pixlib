package ijaux.iter.array.sample;

import ijaux.iter.ForwardIterator;
import ijaux.iter.array.DoubleForwardIterator;

public final class DoubleForwardSampler extends DoubleForwardIterator implements ForwardIterator<Double> {
	private double[] dpixels;
	
	public DoubleForwardSampler(Object cpixels) {
		super(cpixels);
		dpixels=(double[]) pixels; 
	}
	
	

	@Override
	public double nextDouble() {
		final double d= dpixels[i];
		i+=step;
		return d;
	}
 

	@Override
	public void putDouble(double val) {
		dpixels[i]=val;
		i+=step;
	}

	 
}
