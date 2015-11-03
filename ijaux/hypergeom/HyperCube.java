package ijaux.hypergeom;

import ijaux.Constants;
import ijaux.datatype.Typing;
//import ijaux.datatype.access.Access;


/*
 * Generic interface:
 * VectorType - primitive array of either : byte[], int[], short[], float[], double[]
 *
 * @author Dimiter Prodanov
 */
public interface HyperCube<VectorType, E> extends Iterable<E>, Constants, Typing  {
	
	int iterationPattern();
		
	int getNDimensions();
	
	int[] getDimensions();
	
	int size();
	
	int index();
	
	VectorType coordinates();
	
	//Access<E> getAccess();
	
}
