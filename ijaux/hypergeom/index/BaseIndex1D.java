package ijaux.hypergeom.index;

import java.io.Serializable;

public class BaseIndex1D extends GridIndex implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4156246947311104574L;

	public BaseIndex1D(int[] dims ) {	 
		reshape(dims);
		if (n!=1) throw new IllegalArgumentException("dimensions != 1");
		coords=new int[1];
	}
	
	public BaseIndex1D(int[] dims, int idx) {	 	
		reshape(dims);
		if (n!=1) throw new IllegalArgumentException("dimensions != 1");
		index=idx;
		//warp();
	}
	
/*	@Override
	public int[] getCoordinates() {
		return coords;
	}*/



	@Override
	public int indexOf(int[] x) {
		return x[0];
	}
	
	@Override
	public boolean isValid() {		
		final boolean dim1= (dim[0]== 1) && (coords[0]==dim[0]);
		final boolean valid = (coords[0]<dim[0] || dim1 ) && (coords[0]>=0);
		isvalid=valid;
		return isvalid;
	}

	@Override
	public int dec(int k, int step) {
		if ( k!=0) return index;
		coords[0]-=step;
		index= indexOf(coords);	
		return index;
	}

	@Override
	public int inc(int k, int step) {
		if ( k!=0) return index;
		coords[0]+=step;
		index= indexOf(coords);	
		return index;
	}

	@Override
	public void setIndexAndUpdate(int idx) {
		index=idx;	
		coords[0]=index;
	}

	@Override
	public void warp() {
		coords[0]=index;		
	}

	@Override
	public synchronized void inc() {
		index++;
		coords[0]++;
	}

	@Override
	public synchronized void dec() {
		index--;
		coords[0]--;
		
	}


}
