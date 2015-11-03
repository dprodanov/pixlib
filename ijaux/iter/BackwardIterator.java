/**
 * 
 */
package ijaux.iter;

import ijaux.Constants;

/**
 * @author Dimiter Prodanov
 * @param <E>
 *
 */
public interface BackwardIterator<E> extends Constants, IndexedIterator<E> {
	

	
	boolean hasPrevious();
	
	E previous();
	
	E last();
 
	void put(E value);

}
