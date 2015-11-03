/**
 * 
 */
package ijaux.iter.seq;


import ijaux.iter.ForwardIterator;

import java.util.*;

/**
 * @author prodanov
 * @param <E>
 *
 */
public class RasterForwardIterator<E extends Number> 
							 extends RasterIterator<E> 
							 implements ForwardIterator<E> {

	
		
	public RasterForwardIterator(Object cpixels) {
		super.setPixels(cpixels);
	}
	
 
 
	public E first() {
		i=0;
		return access.element(i);
		 
	}


	@Override
	public E next() {
		return access.element(i++);
	}
	
	@Override
	public void put(E value){
		super.put(value);
		//System.out.print(i+",");
		i++;
		
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

	

}
