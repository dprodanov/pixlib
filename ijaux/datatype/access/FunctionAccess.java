package ijaux.datatype.access;

import ijaux.funct.ElementFunction;
import ijaux.funct.SimpleFunction;

/*
 * Visits an element of the underlying container specified by the index or coordinate array
 * and transforms it with the SimpleFunction<N> f
 */
public interface FunctionAccess {

	public abstract <N> N element(int index, N value, SimpleFunction<N> f);
	
	public abstract <N> N element(int index, int value, SimpleFunction<N> f);

	public abstract <N> N element(int index, float value, SimpleFunction<N> f);
	
	public abstract <N> N element(int index, double value, SimpleFunction<N> f);
	
	public abstract <N> N element(int index, boolean value, SimpleFunction<N> f);
	
	
	
	public abstract <N> N element(int[] coords, N value, SimpleFunction<N> f);
	
	public abstract <N> N element(int[] coords, int value, SimpleFunction<N> f);

	public abstract <N> N element(int[] coords, float value, SimpleFunction<N> f);
	
	public abstract <N> N element(int[] coords, double value, SimpleFunction<N> f);
	
	public abstract <N> N element(int[] coords, boolean value, SimpleFunction<N> f);

	public abstract int[] size();

}