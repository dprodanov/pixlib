/**
 * 
 */
package ijaux.datatype;

import java.util.*;

/**
 * @author prodanov
 *
 */
public class PairedArray<A,B> extends ArrayList<Pair<A,B>> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -118831542452620567L;

	public PairedArray(int cap) {
		super(cap);
	}
	
	public boolean add(A a, B b) {
		return add(new Pair<A,B>(a,b));
	}
	
	public A getFirst(int i) {
		return get(i).first;
	}
	
	public B getSecond(int i) {
		return get(i).second;
	}
	

}
