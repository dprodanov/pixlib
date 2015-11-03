package ijaux.iter;

import java.util.Iterator;

/* @author Dimiter Prodanov
 * Defines navigable iteration behavior
 */
public interface IndexedIterator<E> extends Iterator<E> {
	
	int index();
	
	void set(int i);
	
	void inc();
	
	void dec();
}
