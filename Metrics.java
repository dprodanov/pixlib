import ijaux.datatype.ProductSpace;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.Indexing;


public class Metrics<I extends BaseIndex>  {
 
	double [][] g;
	double ndims;
	
	public Metrics(int n) {
		g=new double[n][n];
		ndims=n;
	}
	
	public void transpose()  {
		for (int i=0; i< ndims; i++) {
			for (int j=i+1; j< ndims; j++) {
				g[j][i]=g[i][j];
			}
		}
	}
	
	public double OrthogonalDistance(I a, I b) {
		int[] ac=a.getCoordinates();
		int[] bc=b.getCoordinates();
		double d=0;
		
		if( ac.length!=bc.length || ac.length!=ndims)
			throw new IllegalArgumentException("dimensions do not match");
		
		
		for (int i=0;i<ndims; i++) {
			d+=g[i][i]*(ac[i]-bc[i])*(ac[i]-bc[i]);
		}
		
		return Math.sqrt(d);
	}
	
	public double distance(I a, I b) {
		int[] ac=a.getCoordinates();
		int[] bc=b.getCoordinates();
		double d=0;
		
		if( ac.length!=bc.length || ac.length!=ndims)
			throw new IllegalArgumentException("dimensions do not match");
		
		
		for (int i=0;i<ndims; i++) 
			for (int j=i;i<ndims; i++) { // makes use of the symmetry
			d+=g[i][j]*(ac[i] - bc[i])*(ac[j] - bc[j]);
		}
		
		return Math.sqrt(d);
	}

}
