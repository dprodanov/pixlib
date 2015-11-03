package ijaux.datatype;
/*
 * Linear space -> contrasting
 */

public interface LinearSpace<T, VectorType> {

	VectorType  add(VectorType a, VectorType b);
	VectorType  sub(VectorType a, VectorType b);
	VectorType  scale(double scalar, VectorType  vt);
	
	VectorType  inv();
}
