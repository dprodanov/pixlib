package ijaux.iter.array;

import ijaux.iter.ForwardIterator;

public class DoubleForwardIterator extends ArrayIterator<Double> implements ForwardIterator<Double> {
	private double[] dpixels;
	
	public DoubleForwardIterator(Object cpixels) {
		super.setPixels(cpixels);
		dpixels=(double[]) pixels; 
	}
	
	@Override
	public Double next() {
		return  nextDouble();
	}

	@Override
	public byte nextByte() {
		return (byte)  nextDouble();
	}

	@Override
	public double nextDouble() {
		return  dpixels[i++];
	}

	@Override
	public float nextFloat() {
		return (float) nextDouble();
	}

	@Override
	public int nextInt() {
		return (int) nextDouble();
	}

	@Override
	public short nextShort() {
		return  (short )nextDouble();
	}

	@Override
	public void put(Double val) {
		putDouble(val);
	}

	@Override
	public void putInt(int val) {
		putDouble(val);
	}

	@Override
	public void putByte(byte val) {
		putDouble(val);	
	}

	@Override
	public void putShort(short val) {
		putDouble(val);
	}

	@Override
	public void putFloat(float val) {
		putDouble(val);
	}

	@Override
	public void putDouble(double val) {
		dpixels[i]=val;
		i++;
	}
	

	@Override
	public Double first() {
		i=0;
		return dpixels[i];
	}

}
