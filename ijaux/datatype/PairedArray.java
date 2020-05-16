/**
 * 
 */
package ijaux.datatype;

import java.util.*;

/**
 * 
 * @author Dimiter Prodanov
 *
 * @param <A>
 * @param <B>
 */
public class PairedArray<A,B> extends ArrayList<Pair<A,B>> {
	

	private static final long serialVersionUID = -118831542452620567L;

	/**
	 * 
	 * @param cap
	 */
	public PairedArray(int cap) {
		super(cap);
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean add(A a, B b) {
		return add(new Pair<A,B>(a,b));
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public A getFirst(int i) {
		return get(i).first;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public B getSecond(int i) {
		return get(i).second;
	}
	

}
