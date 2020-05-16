package ijaux.datatype;

/**
 * 
 * @author Dimiter Prodanov
 *
 * @param <T>
 * @param <VectorType>
 */
public interface ProductSpaceReflexive<T,VectorType> extends LinearSpaceReflexive<T, VectorType> {
	
	/**
	 * 
	 * @param b
	 */
	void mult(VectorType b);
	
	/**
	 * 
	 * @param b
	 */
	void div(VectorType b);
	
	/**
	 * 
	 * @return
	 */
	T norm();
	
	/**
	 * 
	 * @return
	 */
	VectorType  invs();
	

}
