package ijaux.hypergeom;

/* Generic spec for boundary conditions
 * @author Dimiter Prodanov
 */

public interface BoundaryCondition<VectorType> {

	public VectorType getCoordsAt(VectorType coords);
	
	public int getIndexAt(int index);
	
}
