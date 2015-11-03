package ijaux.iter.array;

import ijaux.iter.ForwardIterator;


public class ByteForwardIterator extends ArrayIterator<Byte> implements ForwardIterator<Byte> {

	private byte [] bpixels;
	
	
	public ByteForwardIterator(Object cpixels) {
		super.setPixels(cpixels);
		bpixels=(byte[]) pixels; 
	}
		

	@Override
	public Byte next() {
		return nextByte();
	}

	@Override
	public byte nextByte() {
		return bpixels[i++];
	}

	@Override
	public double nextDouble() {
		return nextByte() & mask;
	}

	@Override
	public float nextFloat() {
		return nextByte() & mask;
	}

	@Override
	public int nextInt() {
		return nextByte() & mask;
	}

	@Override
	public short nextShort() {
		return (short)(nextByte() & mask);
	}
	
	@Override
	public boolean nextBool() {
		return nextByte()>0;
	}

	@Override
	public void put(Byte val) {
		putByte(val);
		
	}

	@Override
	public void putInt(int val) {
		final byte b=(byte) (val & mask);
		putByte(b);
	}

	@Override
	public void putByte(byte val) {
		bpixels[i]=val;		
		i++;
	}

	@Override
	public void putShort(short val) {
		final byte b=(byte) (val & mask);
		putByte(b);
	}

	@Override
	public void putFloat(float val) {
		final byte b=(byte) ((int)val & mask);
		putByte(b);
	}

	@Override
	public void putDouble(double val) {
		final byte b=(byte) ((int)val & mask);
		putByte(b);
	}
	

	@Override
	public Byte first() {
		i=0;
		return bpixels[i];
	}
 
	 

}
