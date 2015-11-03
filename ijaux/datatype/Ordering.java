package ijaux.datatype;

public interface Ordering<VectorType> {
	
	 boolean contains(VectorType vect);
	 
	 boolean contains(VectorType vect, int[] set);

	 boolean geq(VectorType vect, int[] set);
	 
	 boolean leq(VectorType vect, int[] set);

	 boolean in(VectorType inner, VectorType outer, int[] set);

	 boolean out(VectorType inner, VectorType outer, int[] set);
}