/**
 * 
 */
package ijaux.iter;





/**
 * @author prodanov
 * @param <E>
 *
 */
public interface RandomIterator<E> extends ForwardIterator<E>, BackwardIterator<E> {
	
	E previous();
	
	int index();
	
	boolean hasPrevious();
	
	void set (int i);
	
	E first();
	
	E last();

}
