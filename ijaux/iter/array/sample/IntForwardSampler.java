package ijaux.iter.array.sample;

import ijaux.iter.ForwardIterator;
import ijaux.iter.array.IntForwardIterator;

import java.util.NoSuchElementException;

public final class IntForwardSampler extends IntForwardIterator implements ForwardIterator<Integer> {
	private int[] ipixels;
	
	public IntForwardSampler(Object cpixels) {
		super(cpixels);
		ipixels=(int[]) pixels; 
	}

 
	@Override
	public int nextInt() {
		final int b=ipixels[i];
		i+=step;
		return b;
	}

 

	@Override
	public void putInt(int val) {
		//System.out.print(i);
		ipixels[i]=val;
		i+=step;
	}

 


}
