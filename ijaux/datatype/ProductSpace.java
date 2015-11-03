package ijaux.datatype;

public interface ProductSpace<T, VectorType> extends
		LinearSpace<T, VectorType> {
	
	T mult(VectorType a, VectorType b);
	
	T div(VectorType a, VectorType b);

	
	VectorType  invs();

}
