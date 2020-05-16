package ijaux.datatype;

import java.io.Serializable;

/*
 * based on http://stackoverflow.com/questions/156275/what-is-the-equivalent-of-the-c-pairl-r-in-java
 */

public class Pair<A, B> implements Serializable, Cloneable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7573914150036728958L;
	/**
	 * 
	 */
 	public A first;
    public B second;

    /**
     * 
     * @param first
     * @param second
     */
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
    
    public static <A,B> Pair<A,B> of (A first, B second) {
        return new Pair<A,B>(first,second);
    }
    
    /**
     * 
     * @param p
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <A,B> Pair<A,B> of (Pair<?,?> p) {
        return new Pair<A,B>((A)p.first,(B)p.second);
    }
    
    /**
     * 
     * @param pair
     * @return
     */
    public static <B,A> Pair<B,A> swap (Pair<A,B> pair) {
    	return new Pair<B,A>(pair.second,pair.first);
    }
    
    /**
     * 
     * @return
     */
    public  Pair<B,A> swap() {
    	return new Pair<B,A>(second,first);
    }
    
    // synonymous
    /**
     * 
     * @return
     */
    public  Pair<B,A> transpose() {
    	return new Pair<B,A>(second,first);
    }
    
    /**
     * 
     * @param f
     * @param s
     */
    public void set (A f, B s) {
    	first=f;
    	second=s;
    }
    
    /**
     * 
     * @param n
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <A,B> Pair<A,B>[] array (int n) {
        return new Pair[n];
    }
    
    @Override
    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    /**
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<?, ?> other = (Pair<?,?>) obj;
        if (this.first != other.first && 
                (this.first == null || !this.first.equals(other.first))) {
            return false;
        }
        if (this.second != other.second && 
                (this.second == null || !this.second.equals(other.second))) {
            return false;
        }
        return true;
    }

    /**
     * 
     */
    @Override
    public String toString(){ 
           return "<" + first + " : " + second + ">"; 
    }
    
   

} //END

