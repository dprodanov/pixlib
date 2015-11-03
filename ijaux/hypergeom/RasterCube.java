/**
 * 
 */
package ijaux.hypergeom;


import ijaux.datatype.access.ElementAccess;
import ijaux.hypergeom.index.Indexing;
 

/** basic class for the raster image representation
 * @author  Dimiter Prodanov
 * @param <E>
 *
 */
public abstract class RasterCube<E, VectorType> implements HyperCube<VectorType, E> {

	protected Indexing<VectorType> pIndex;
	protected int size=0;
	protected int[] dim;
	protected int iterPattern=0;
	protected int n=-1; // number of dimensions
	
	protected Class<?> indexingType;
	protected Class<?> type;
	 
	 

	@Override
	public int iterationPattern() {
		return iterPattern;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public boolean eq(Class<?> c) {
		return type==c;
	}
	
	@Override
	public int getNDimensions() {
		return n;
	}

	@Override
	public int[] getDimensions() {
		return dim;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int index() {
		return pIndex.index();
	}

	@Override
	public VectorType coordinates() {
		return pIndex.getCoordinates();
	}

	public abstract   ElementAccess<VectorType, ?> getAccess();
}
