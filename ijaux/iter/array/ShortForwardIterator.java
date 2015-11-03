package ijaux.iter.array;


import ijaux.iter.ForwardIterator;

import java.util.NoSuchElementException;

public  class ShortForwardIterator extends ArrayIterator<Short> 
implements ForwardIterator<Short> {	
	
	private short [] spixels;

	
	public ShortForwardIterator(Object cpixels) {
		super.setPixels(cpixels);
		spixels=(short[]) pixels; 
		mask=shortMask;
	}



	@Override
	public Short next() {
		return Short.valueOf(nextShort());
	}

	@Override
	public byte nextByte() {
		return (byte) (nextShort() & byteMask) ;
	}

	@Override
	public double nextDouble() {
		return nextShort() & mask;
	}

	@Override
	public float nextFloat() {
		return nextShort() & mask;
	}

	@Override
	public int nextInt() {
		return nextShort() & mask;
	}

	@Override
	public short nextShort() {
		return spixels[i++];
	}

	@Override
	public void put(Short val) {
		putShort(val);
	}


	@Override
	public void putInt(int val) {
		final short b=(short) (val & mask);
		putShort(b);
	}


	@Override
	public void putByte(byte val) {
		putShort(val);
	}


	@Override
	public void putShort(short val) {
		spixels[i]=val;	
		i++;
	}


	@Override
	public void putFloat(float val) {
		putShort((short) val);
	}

	@Override
	public void putDouble(double val) {
		putShort((short) val);
	}


	@Override
	public Short first() {
		i=0;
		return spixels[i];
	}
}
