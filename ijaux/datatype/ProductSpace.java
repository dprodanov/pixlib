package ijaux.datatype;

/**
 * 
 * @author Dimiter Prodanov
 *
 * @param <T>
 * @param <VectorType>
 */
public interface ProductSpace<T, VectorType> extends
		LinearSpace<T, VectorType> {
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	T mult(VectorType a, VectorType b);
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	T div(VectorType a, VectorType b);

	/**
	 * 
	 * @return
	 */
	VectorType  invs();

}
