package ijaux.datatype;
/*
 * Linear space -> contrasting
 */
/**
 * Linear space definition
 * 
 * @author Dimiter Prodanov
 *
 * @param <T>
 * @param <VectorType>
 */
public interface LinearSpace<T, VectorType> {
	
	/**
	 * a+b
	 * @param a
	 * @param b
	 * @return 
	 */
	VectorType  add(VectorType a, VectorType b);
	
	/**
	 * a-b
	 * @param a
	 * @param b
	 * @return 
	 */
	VectorType  sub(VectorType a, VectorType b);
	
	/**
	 * scalar*vt
	 * @param scalar
	 * @param vt
	 * @return
	 */
	VectorType  scale(double scalar, VectorType  vt);
	
	/**
	 * inverses the object: -VectorType
	 * @return
	 */
	VectorType  inv();
}
