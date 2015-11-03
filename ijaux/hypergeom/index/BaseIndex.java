package ijaux.hypergeom.index;

import ijaux.Util;

import java.io.Serializable;




public class BaseIndex extends GridIndex implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8538968068406821206L;
	/**
	 * 
	 */
	 
	protected int[] dc;
 	
	public BaseIndex(int[] dims ) {	 
		reshape(dims);
		coords=new int[n];
		ret=coords.clone();
		dc=Util.cumprodarr(dim, dim.length);
		maxind=dc[n-1];
	}
	
	public BaseIndex(int[] dims, boolean trim ) {	 
		if (trim) {
			dim=Util.trimDims(dims);
			n=dim.length;
			//Util.printIntArray(dim);
		} else
			reshape(dims);
		coords=new int[n];
		ret=coords.clone();
		dc=Util.cumprodarr(dim, dim.length);
		maxind=dc[n-1];
	}
	
	public BaseIndex(int[] dims, int idx) {	 
		reshape(dims);
		index=idx;
		coords=new int[n];
		ret=coords.clone();
		dc=Util.cumprodarr(dim, dim.length);
		warp();
	 
	}
	
	/*
	@Override
	public void reshape(int[] dims) {
		n=dims.length;
		//System.out.println("n ="+n);
		dim=dims;
		int prod=1;
		for (int c:dims) prod*=c;
		maxind=prod;
		updated=true;
	}
	*/
	
	public void setDim(int[] dims) {
		n=dims.length;
		dim=dims;
		warp();
	}
	/*
	public int[] getDim() {
		return dim;
	}
	 */
	@Override
	public void setIndex(int idx) {
		index=idx;
		updated=true;
	}
	

	public void setIndexAndUpdate(int idx) {
		index=idx;
		warp();
	}
 

	
	@Override
	public int indexOf(int[] x) {
		int idc=x[0] ;
		for (int i=1; i<n; i++) {
			idc+=x[i] *dc[i-1];	 
			//System.out.println("i "+i+" x " +x[i]+ " d: " +dc[i-1] +" index: " +idc);
		}
		return idc;
	}
	
/*	@Override
	public int indexOf(int[] x) {
		int z=x[0] % dim[0];
		int idc=z ;
 		//System.out.println("**");
		int u=0;
		if (x[0] % dim[0] ==0 && x[0]>0) u++;
		for (int i=1; i<n; i++) {
			z= x[i] % dim[i];
			if (z ==0 && x[i]>0) u++;
			idc+=z *dc[i-1];	 
			//System.out.println("i "+i+" x " +x[i]+ " d: " +dc[i-1] +" index: " +idc);
		}
		//System.out.println(idc + " " + u  );
		if (u>0) idc=-idc;
		if (u==n) idc--;

		return idc;
	}*/


	private int[] ret;
	
	@Override
	public void warp() {		
		int rt= index;
		for (int i=0; i<n; i++) {
			ret[i]=rt % dim[i];
			rt=rt/dim[i];
			//System.out.print(" u["+i+"]=" +ret[i]);
		}
		updated=false;
		coords=ret;				 
	}
	
	/* 
	 * (non-Javadoc)
	 * @see ijaux.datatype.Ordering#contains(java.lang.Object)
	 *  
	 */
	/*
	public boolean contains (int[] vect) {
		int[] coord=getCoordinates();
		boolean valid=true;
		for (int m=0; m< coord.length; m++) {
			//System.out.print("m "+ c[m] +", n "+ vect[m]+" "+ (c[m]<=vect[m])+",");
			valid= valid && (coord[m]<=vect[m]) ;
			 
		}
		//System.out.println("final: "+valid);
		return valid;
	}	
	*/
	
	public boolean isValid() {
		boolean valid=true;	
		for (int m=0; m< coords.length; m++) {
			//System.out.print("x["+m +"]="+ coord[m] +", dim= "+ dim[m]+" "+ (coord[m]<dim[m])+",\n");
			final boolean dim1= (dim[m]== 1) && (coords[m]==1);
			valid= valid && (coords[m]<dim[m] || dim1 ) && (coords[m]>=0);
			 
		}
		isvalid=valid;
		return isvalid;
	}
	
	public boolean valid(int[] coord) {
		///System.out.println(this);
		
		boolean valid=true;	
		for (int m=0; m< coord.length; m++) {
			//System.out.print("x["+m +"]="+ coord[m] +", dim= "+ dim[m]+" "+ (coord[m]<dim[m])+",\n");
			final boolean dim1=(dim[m]== 1) && (coord[m]==dim[m]);
			valid= valid && (coord[m]<=dim[m] || dim1 ) && (coord[m]>=0);
			 
		}
			return valid;
	}
		
	

	public int inc(int k, int step) {
		if ((k>n) || (k<0)) return index;
		if (updated)
			warp();
		coords[k]+=step;

		index=indexOf(coords);
		return index;
	 
	}
	
	public int dec(int k, int step) {
		if ((k>n) || (k<0)) return index;
		if (updated)
			warp();
		coords[k]-=-step;
		updated=true;
		index= indexOf(coords);	
		return index;
	}
	
	@Override
	public  void inc() {
		index++;		
		//updated=true;
		warp();
	}

	@Override
	public  void dec() {
		index--;	
		//updated=true;
		warp();
	}
	
	public int max() {
		/*
		int p=1;
		for (int d:dim) p*=d;
		return p;
		*/
		return dc[dc.length-1]-1;
	}
}
