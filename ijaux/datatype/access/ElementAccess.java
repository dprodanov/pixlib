package ijaux.datatype.access;

import ijaux.datatype.Pair;

public interface ElementAccess<VectorType,E> {
	
	E element(int index);
	
	void putE(int index, E value);
	
	E element(VectorType coords);
	
	void putV(VectorType coords, E value);
	
	void put(Pair<VectorType, E> pair);
	
	/*
	 *  returns an array of the size
	 *  in simple Access the form is [array.length, 0];
	 *  in PagedAccess the form is [page.length, length] 
	 */
	int[] size();
	
	long length();
}
