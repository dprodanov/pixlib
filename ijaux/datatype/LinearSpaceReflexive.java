package ijaux.datatype;
/*
 * Abstract Linear Space interface
 */

/**
 * 
 * @author Dimiter Prodanov
 *
 * @param <T>
 * @param <VectorType>
 */
public interface LinearSpaceReflexive<T, VectorType> {

	/**
	 * 
	 * @param vt
	 */
	void add(VectorType vt);
	
	/**
	 * 
	 * @param vt
	 */
	void sub(VectorType  vt);
	
	/**
	 * 
	 * @param scalar
	 */
	void scale(double scalar);
	
	/**
	 * 
	 */
	void inv();
}
