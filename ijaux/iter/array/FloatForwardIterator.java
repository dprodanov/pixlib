package ijaux.iter.array;

import ijaux.iter.ForwardIterator;

public class FloatForwardIterator extends ArrayIterator<Float> implements ForwardIterator<Float> {
	private float[] fpixels;
	
	public FloatForwardIterator(Object cpixels) {
		super.setPixels(cpixels);
		fpixels=(float[]) pixels; 
	}

	
	@Override
	public Float next() {
		return nextFloat();
	}

	@Override
	public byte nextByte() {
		return (byte) nextFloat();
	}

	@Override
	public double nextDouble() {
		return nextFloat();
	}

	@Override
	public float nextFloat() {
		return fpixels[i++];
	}

	@Override
	public int nextInt() {
		return (int)nextFloat();
	}

	@Override
	public short nextShort() {
		return (short) nextFloat();
	}

	@Override
	public void put(Float val) {
		putFloat(val);
	}

	@Override
	public void putInt(int val) {
		putFloat(val);
	}

	@Override
	public void putByte(byte val) {
		putFloat(val);
	}

	@Override
	public void putShort(short val) {
		putFloat(val);
	}

	@Override
	public void putFloat(float val) {
		fpixels[i]=val;
		i++;
	}

	@Override
	public void putDouble(double val) {
		putFloat((float)val);
	}
	


	@Override
	public Float first() {
		i=0;
		return fpixels[i];
	}

}
