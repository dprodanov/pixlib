package ijaux.datatype;

/**
 * 
 * @author Dimiter Prodanov
 *
 * @param <VectorType>
 */
public interface Ordering<VectorType> {
	
	/**
	 * 
	 * @param vect
	 * @return
	 */
	 boolean contains(VectorType vect);
	 
	 /**
	  * 
	  * @param vect
	  * @param set
	  * @return
	  */
	 boolean contains(VectorType vect, int[] set);

	 /**
	  * 
	  * @param vect
	  * @param set
	  * @return
	  */
	 boolean geq(VectorType vect, int[] set);
	 
	 /**
	  * 
	  * @param vect
	  * @param set
	  * @return
	  */
	 boolean leq(VectorType vect, int[] set);

	 /**
	  * 
	  * @param inner
	  * @param outer
	  * @param set
	  * @return
	  */
	 boolean in(VectorType inner, VectorType outer, int[] set);

	 /**
	  * 
	  * @param inner
	  * @param outer
	  * @param set
	  * @return
	  */
	 boolean out(VectorType inner, VectorType outer, int[] set);
}