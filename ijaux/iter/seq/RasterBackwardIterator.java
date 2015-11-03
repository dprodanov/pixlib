/**
 * 
 */
package ijaux.iter.seq;


import ijaux.iter.BackwardIterator;

import java.util.*;

/**
 * @author prodanov
 * @param <E>
 *
 */
public class RasterBackwardIterator<E extends Number> 
							 extends RasterIterator<E> 
							 implements BackwardIterator<E> {

	
	
	public RasterBackwardIterator(Object cpixels) {
		super.setPixels(cpixels);
	}
	
 
	public E last() {
		i=size-1;
 		return access.element(i);
	}

 

	public E previous() {
		if (i>0) {
			 return access.element(i--);
			
		} else {
			throw new NoSuchElementException();
		}
	}
	
	
	public void reset(){
		if (i>=size)
			set(size-1);
		if (i<0)
			set(0);
	}

	@Override
	public void put(E value){
		super.put(value);
		i--;
	}
	
	@Override
	public boolean hasNext() {
 		return (i<size);
	}
	
	public boolean hasPrevious() {
		return (i>0);
	}

	@Override
	public E next() {
		throw new UnsupportedOperationException();
	}


}
