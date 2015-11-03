package ijaux.hypergeom;

/*
 * This interface defines implementations of distances and metrics
 */
public interface MetricSpace<VectorType> {

	double distance(VectorType a, VectorType b);
	
	double norm(VectorType a);
	
}
