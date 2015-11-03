package ijaux.iter.array.sample;

import ijaux.iter.ForwardIterator;
import ijaux.iter.array.ByteForwardIterator;

import java.util.NoSuchElementException;

public class ByteForwardSampler extends ByteForwardIterator implements ForwardIterator<Byte> {

	protected byte [] bpixels;

	
	public ByteForwardSampler(Object cpixels) {
		super(cpixels);
		bpixels=(byte[]) pixels; 
	}

	
	@Override
	public byte nextByte() {
		final byte b=bpixels[i];
		i+=step;
		return b;
	}

	

	@Override
	public void putByte(byte val) {
		bpixels[i]=val;		
		i+=step;
	}

	 
 
	 

}
