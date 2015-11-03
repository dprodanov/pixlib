package ijaux.datatype;
/*
 * Abstract Linear Space interface
 */

public interface LinearSpaceReflexive<T, VectorType> {

	void add(VectorType vt);
	void sub(VectorType  vt);
	void scale(double scalar);
	void inv();
}
