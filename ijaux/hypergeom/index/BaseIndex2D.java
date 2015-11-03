package ijaux.hypergeom.index;

import ijaux.Util;

import java.io.Serializable;

public class BaseIndex2D extends GridIndex implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1305435124658595433L;

	public BaseIndex2D(int[] dims ) {
		n=dims.length;
		dim=dims;
		if (n>2)
			reshape(dims);
		if (n!=2) throw new IllegalArgumentException("dimensions != 2");
		coords=new int[n];
		int prod=1;
		for (int c:dims) prod*=c;
		maxind=prod;
		updated=true;
		warp();
	}
	
	public BaseIndex2D(int[] dims, int idx) {	 	
		n=dims.length;
		dim=dims;
		if (n>2)
			reshape(dims);
		if (n!=2) throw new IllegalArgumentException("dimensions != 2");
		index=idx;
		coords=new int[n];
		int prod=1;
		for (int c:dims) prod*=c;
		maxind=prod;
		updated=true;
		warp();
	}
	
	@Override
	public void setIndex(int idx) {
		index=idx;
		updated=true;
	}
	

	public void setIndexAndUpdate(int idx) {
		index=idx;
		warp();
	}
	
	
	public void warp() {
		coords[1]=index/dim[0];
		coords[0]=index%dim[0];		
		updated=false;
	}
	

	
	@Override
	public int indexOf(int[] x) {
		return x[1]*dim[0]+x[0];
	}

	@Override
	public boolean isValid() {
		boolean valid=true;	
		valid= (coords [0] >=0) && (coords [1] >=0);	
		valid= valid && (coords [0] < dim[0]) && (coords [1] <dim[1]);
		/*if (!valid) {
			System.out.print("nv2: ");
			Util.printIntArray(coords);
		}*/
		isvalid=valid;
		return isvalid;
	}
	
	

	@Override
	public int dec(int k, int step) {
		if ((k>n) || (k<0)) return index;
		 
		coords[k]-=step;
		updated=true;
		//Util.printIntArray(coords);
		index= indexOf(coords);	
		//System.out.println("2D: "+ index);
		return index;
	}

	@Override
	public int inc(int k, int step) {
		if ((k>n) || (k<0)) return index;
		coords[k]+=step;
		updated=true;
		index= indexOf(coords);	
		return index;
	}

	@Override
	public  void inc() {
		index++;
		/*coords[0]++;
		if (coords[0]==dim[0]) {
			coords[0]=0;
			coords[1]++;
		}		
		updated=false;*/
		warp();
	}

	@Override
	public  void dec() {
		index--;	
		warp();
	}

	

	
}
