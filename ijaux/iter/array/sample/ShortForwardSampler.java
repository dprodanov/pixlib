package ijaux.iter.array.sample;


import ijaux.iter.ForwardIterator;
import ijaux.iter.array.ShortForwardIterator;

import java.util.NoSuchElementException;

public final class ShortForwardSampler extends ShortForwardIterator implements ForwardIterator<Short> {	
	private short [] spixels;

	
	public ShortForwardSampler(Object cpixels) {
		super(cpixels);
		spixels=(short[]) pixels; 
		mask=shortMask;
	}

	 

	@Override
	public short nextShort() {
		final short s=spixels[i];
		i+=step;
		return s;
	}



	@Override
	public void putShort(short val) {
		spixels[i]=val;	
		i+=step;
	}

 
}
