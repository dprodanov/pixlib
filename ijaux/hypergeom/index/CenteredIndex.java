package ijaux.hypergeom.index;

import ijaux.Util;



public class CenteredIndex extends BaseIndex {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8680510324249109811L;
	
	protected int[] center={};

	int u=0;
	int center_index=0;
	
	public CenteredIndex(int[] dims ) {
		super(dims);
		init(dims);
		warp();
		u=Util.cumprod(dims);
	}
	
	public CenteredIndex(int[] dims, int idx) {
		super(dims);	
		init(dims);
		index=idx;
		warp();
	}

	/**
	 * @param dims
	 */
	private void init(int[] dims) {
		center=new int[n];
		int cnt=0;
		for(final int c:dims) {
			center[cnt++]=c/2;
		}
		final int u=super.indexOf(center);
		center_index=u;
		minind=-u;
		maxind+=u;
	}

	
	@Override
	public void warp() {		
		int n=dim.length;
		int[] ret=new int[n];
		int rt= index;
		//boolean inlimit=(rt/maxind==0) && isValid();

		/*if (!inlimit) {
			if (debug) System.out.println("not in limit");
				
			for (int i=0; i<n; i++) {
				final int auxdim=Math.max(dim[i], Math.abs(coords[i]) +center[i]);
				ret[i]=rt % auxdim -  center[i];
				rt=rt/auxdim;
				//System.out.print(" u["+i+"]=" +ret[i] +" auxdim " + auxdim);
			}
		
		} 
		else {*/
		for (int i=0; i<n; i++) {
			ret[i]=rt % dim[i] -  center[i];
			rt=rt/dim[i];
			//System.out.print(" i= "+i+" x= " +ret[i]);
		}
		//}
		coords=ret;
		 
	}
	
	public int[] coordsOf(int rt) {
		int n=dim.length;
		int[] ret=new int[n];
		for (int i=0; i<n; i++) {
			ret[i]=rt % dim[i] -  center[i];
			rt=rt/dim[i];
			//System.out.print(" i= "+i+" x= " +ret[i]);
		}
		return ret;
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
	
	@Override
	public int[] getCoordinates(){
		if (updated)
			warp();
		return coords;
	}
	
	@Override
	public int translate(int[] x) {
		if (x.length!=n) return index;
		
		if (updated)
			warp();
		
		for (int i=0; i<n; i++) {
			coords[i]+=x[i];
		}
		updated=true;
		return indexOf(coords);
	 
	}
	
	@Override
	public int inc(int k, int step) {
		if ((k>n) || (k<0)) return index;
		if (updated)
			this.warp();
		coords[k]+=step;
		updated=true;
		return indexOf(coords);
	 
	}
	
	@Override
	public int dec(int k, int step) {
		if ((k>n) || (k<0)) return index;
		if (updated)
			this.warp();
		coords[k]-=-step;
		updated=true;
		return indexOf(coords);	 
	}
	
	public int indexOf(int[] x) {
		int z= (x[0] +center[0]) % dim[0];
		int idc=z ;
 		//System.out.println("**");
		int u=0;
		if (x[0] % dim[0] ==0 && x[0]>0) u++;
		for (int i=1; i<n; i++) {
			z= (x[i] + center[i]) % dim[i];
			if (z ==0 && (x[i] +center[i]) >0) u++;
			idc+=z *dc[i-1];	 
			//System.out.println("i "+i+" x " +x[i]+ " d: " +dc[i-1] +" index: " +idc);
		}
		//System.out.println(idc + " " + u  );
		if (u>0) idc=-idc;
		if (u==n) idc--;

		return idc;
	}

	/*
	public int indexOf(int[] x) {
		//System.out.println("Centered indexof "+(n-1));
		
//		int idc=x[n-1] + center[n-1];
//		for (int i=n-1; i>0; i--) {
//			idc=idc*dim[i] +x[i-1] + center[i-1];
//			//System.out.println("i "+i+" x " +x[i-1]+ " d: " +dim[i] +" c: " + center[i-1] +" => index: " +idc);
//
//		}
		int z=x[n-1]+ center[n-1];
	    int	idc=z ;
		for (int i=1; i<n; i++) {
			z= x[n-i-1]  + center[n-i-1];
			idc=idc*dim[i] + z;	 
			//System.out.println("i "+i+" x " +x[i]+ " d: " +dc[i-1] +" index: " +idc);
		}
		
		//if (ti>-1)		idc=idc;
//		System.out.println("offset "+ u/dim[ti]);
		index=idc;
		coords=x;
		return index;
	}
*/
	public int[] getCenter() {
		return center;
	}
	
	public String toString() {
		StringBuffer sb=new StringBuffer(100);
	    int ind=indexOf(coords);
		sb.append("[");
		for (int i=0; i<n; i++) {			 
			sb.append(" x["+i+"]=" +coords[i]);
		}
		sb.append(", ");
		sb.append(ind);
		sb.append("]");
		return sb.toString();
	}
	
}
