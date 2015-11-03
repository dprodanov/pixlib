/**
 * 
 */
package ijaux.iter.seq;


import ijaux.iter.RandomIterator;

import java.util.*;

/**
 * @author prodanov
 * @param <E>
 *
 */
public class RasterRandomIterator<E extends Number> 
							 extends RasterIterator<E> 
							 implements RandomIterator<E> {

	
	
	public RasterRandomIterator(Object cpixels) {
		super.setPixels(cpixels);
	}
	
 
 
	public E first() {
		i=0;
		return access.element(i);	 
	}


	public E last() {
		i=size-1;
 		return access.element(i);
	}

 
	@Override
	public E next() {
		return access.element(i++);	
	}
	
 
	public E previous() {
		return access.element(i--);	 
	}
	
	
	public void reset(){
		if (i>=size)
			set(size-1);
		if (i<0)
			set(0);
	}

	@Override
	public boolean hasNext() {
 		return (i<size);
	}

	public boolean hasPrevious() {
		return (i>0);
	}

}
