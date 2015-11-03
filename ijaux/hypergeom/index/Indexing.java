package ijaux.hypergeom.index;

import ijaux.datatype.Ordering;
/*
 * The interface determining abstract indexing properties
 * over a vector in a vector space
 */

public interface Indexing<VectorType> extends Ordering<VectorType> {
	
	void setIndex(int idx);
	
	void setIndexAndUpdate (int idx);
	
	int index();
	
	int indexOf(VectorType x);
	
	VectorType getCoordinates();
	
	/*
	 * synchronizes the linear index
	 */
	int translateTo(VectorType x);
	
	/*
	 * synchronizes the linear index
	 */
	int translate(VectorType x);
	
	void reshape(VectorType dims);
	
	boolean isValid();

	int getNDim();
	
	VectorType getDim();
	
	/*
	 * does not synchronize the linear index  
	 */
	void setCoordinates(VectorType x);
	/*
	void warp();
	
	void inc();
	
	void dec();
	*/
}