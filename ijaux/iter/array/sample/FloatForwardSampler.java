package ijaux.iter.array.sample;

import ijaux.iter.ForwardIterator;
import ijaux.iter.array.FloatForwardIterator;

public final class FloatForwardSampler extends FloatForwardIterator implements ForwardIterator<Float> {
	
	private float[] fpixels;
	
	public FloatForwardSampler(Object cpixels) {
		super(cpixels);
		fpixels=(float[]) pixels; 
	}

	@Override
	public float nextFloat() {
		final float f= fpixels[i];
		i+=step;
		return f;
	}

	@Override
	public void putFloat(float val) {
		fpixels[i]=val;
		i+=step;
	}




}
