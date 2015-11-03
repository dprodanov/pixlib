package ijaux.iter;

import ijaux.Constants;
import ijaux.Util;
import ijaux.datatype.Typing;

/* Implements the abstract Iterator pattern over pixel data
 *  
 */
public abstract class AbstractIterator<T> implements IndexedIterator<T>,  Typing, Constants {
	protected int size=-1;
	protected int i=0;
	protected Class<?> returnType;
	
	
	public Class<?> getPrimitiveType() {return Util.getTypeMapping(returnType); }
	
	
	//public Class<?> getReturnType() { return returnType; }
	/*
	 * Methods from Typing
	 */
	/*
	 * (non-Javadoc)
	 * @see ijaux.datatype.Typing#getType()
	 */
	public Class<?> getType() { return returnType; }

	public boolean eq(Class<?> c) { return (c==returnType); }
	
	
	
	/* Iterator methods
	 */
	
	@Override
	public void remove() { throw new UnsupportedOperationException(); }
	
	@Override
	public abstract T next();  
	
	public abstract boolean hasNext(); 
	
	/*
	 * IndexedIterator methods
	 */
	/*
	 * (non-Javadoc)
	 * @see ijaux.iter.IndexedIterator#index()
	 */

	public int index() { return i; }
 /*
  * (non-Javadoc)
  * @see ijaux.iter.IndexedIterator#set(int)
  */
	public void set(int i) { this.i=i;	}
	/*
	 * (non-Javadoc)
	 * @see ijaux.iter.IndexedIterator#inc()
	 */
	public void inc() { i++; }
	/*
	 * (non-Javadoc)
	 * @see ijaux.iter.IndexedIterator#dec()
	 */
	public void dec() { i--; }
	
	
	
	public abstract void put(T value);
	
	public int length() {
		return size;
	}
	
	public void reset() { i=0; }
	
	public int[] perm(int n, int x) {
		int[] ret=new int[n];		
		ret[0]=x;
		for (int i=1; i<n; i++) {
			ret[i]=(++x) % n;
		}		
		return ret;
	}

}
