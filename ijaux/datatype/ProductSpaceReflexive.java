package ijaux.datatype;


public interface ProductSpaceReflexive<T,VectorType> extends LinearSpaceReflexive<T, VectorType> {
	
	void mult(VectorType b);
	
	void div(VectorType b);
	
	T norm();
	
	VectorType  invs();
	

}
