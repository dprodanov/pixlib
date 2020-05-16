/**
 * 
 */
package ijaux.datatype;

/**
 * @author prodanov
 * implement lexicographic comparison
 */
public class ComparablePair<A, B> extends Pair<A, B> implements  Comparable<ComparablePair<A, B>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7252423905081317439L;


	/**
	 * @param first
	 * @param second
	 */
	public ComparablePair(A first, B second) {
		super(first, second);
  
	}
	
	/**
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
    public static <A,B> ComparablePair<A,B> of (A first, B second) {
        return new ComparablePair<A,B>(first,second);
    }
	
    /**
     * 
     * @param o1
     * @param o2
     * @return
     */
    @SuppressWarnings("unchecked")
	private static int compare(Object o1, Object o2) {
        return o1 == null ? o2 == null ? 0 : -1 : o2 == null ? +1 : ((Comparable<Object>) o1).compareTo(o2);
    }

    /**
     * 
     */
	@Override
	public int compareTo(ComparablePair<A, B> pair) {
		 int cmp = compare(first, pair.first);
		    return cmp == 0 ? compare(second, pair.second) : cmp;
	}

} //END
