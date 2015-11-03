package ijaux.hypergeom.index;

import ijaux.Util;
import ijaux.datatype.Pair;


public abstract class GridIndex implements Indexing<int[]>, Cloneable {
	
	protected int n=-1; // number dimensions
	
	protected int[] dim={};
	
	protected int index=0;
	
	protected int[] coords={};
	
	protected int maxind=0, minind=0;
	
	protected boolean updated=false;
 
	public static boolean debug=false;
	
	protected boolean isvalid=false;
	
	protected Class<?> type;
	
	
	public static GridIndex create(int[] dims) {
		final int n=dims.length;
		//System.out.println("dims "+n);
		switch (n) {
			case 1: return new BaseIndex1D(dims);
			case 2: return new BaseIndex2D(dims);
			default: return new BaseIndex(dims);
		}
		
	}
	
	
	public static boolean isMatched(GridIndex a, GridIndex b) {
		return (a.n==b.n); 
	}
	
	public GridIndex clone() {
		return GridIndex.create(dim) ;
	}
	public Class<?> getType() {
		type=this.getClass();
		return type;
	}
	
	public void setDim(int[] dims) {
		n=dims.length;
		dim=dims;
	}
	
	public int[] getDim() {
		return dim;
	}
	
	public int getNDim() {
		return n;
	}

	@Override
	public int[] getCoordinates() {
		return coords;
	}
	

	public int[] getCoordinatesCopy() {
		return coords.clone();
	}
	
	/* Returns the current index without updating
	 * (non-Javadoc)
	 * @see ijaux.hypergeom.index.Indexing#index()
	 */
	@Override
	public int index() {
		return index;
	}

	@Override
	public abstract int indexOf(int [] x);
	
	public int computeIndex(int[] x) {
		index=indexOf(x);
		coords=x;
		return index;
	}
	
	@Override
	public abstract boolean isValid();

	public boolean validate(int[] coords) {
		boolean valid=true;	
		for (int i=0; i<dim.length; i++) {
		valid= valid &&(coords [i] >=0) && (coords [i] <dim[1]);			
		}
		/*if (!valid) {
		System.out.print("nv2: ");
		Util.printIntArray(coords);
	}*/
		return valid;
	}
	
	protected int ti=-1;
	
	@Override
	public void reshape(int[] dims) {
		n=dims.length;
		//System.out.println("n ="+n);
		dim=dims;
		//transposition
		for (int d=0; d<n; d++) {
			if (dims[d]==1)
				ti=d;			
		}
		//System.out.println("ti "+ti);
		int prod=1;
		for (int c:dims) prod*=c;
		maxind=prod;
		updated=true;
	}

	@Override
	public void setIndex(int idx) {
		index=idx;
	}
	
	
 /* Translates and updates index position. We assume that coordinates are up to date.
  * (non-Javadoc)
  * @see ijaux.hypergeom.index.Indexing#translate(java.lang.Object)
  */
	@Override
	public int translate(int[] x) {	
		for (int i=0; i<n; i++) {
			coords[i]+=x[i];
		}
		index=indexOf(coords);
		return index;
	}

	@Override
	public int translateTo(int[] x) {
		for (int i=0; i<n; i++) {
			coords[i]=x[i];
		}
		return indexOf(coords);	
	}
	
	public boolean geq (int[] vect) {
		int[] coord=getCoordinates();
		boolean valid=true;
		for (int m=0; m< coord.length; m++) {
			//System.out.print("m "+ c[m] +", n "+ vect[m]+" "+ (c[m]<=vect[m])+",");
			valid= valid && (coord[m]>=vect[m]);
			 
		}
		//System.out.println("final: "+valid);
		return valid;
	}
	
	public boolean in (int[] low, int[] hi ) {
		int[] c=getCoordinates();
	
		boolean valid=true;
		for (int m=0; m< c.length; m++) {		 
			valid= valid && (c[m]>=low[m]) && (c[m]<=hi[m]);
			 
		}
		//System.out.println("final: "+valid);
		return valid;
	}	
	
	/* (non-Javadoc)
	 * @see ijaux.hypergeom.Ordering#geq(int[], int[])
	 */
	public boolean geq (int[] vect, int[] set) {
		int[] coord=getCoordinates();
		boolean valid= coord[set[0]]<=vect[set[0]];
		for (int m=1; m< set.length; m++) {
			//System.out.print("m "+ c[m] +", n "+ vect[m]+" "+ (c[m]<=vect[m])+",");
			valid= valid || (coord[set[m]]>=vect[set[m]]);
			 
		}
		return valid;
	}	
	
	
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
	
	public boolean contains (int[] vect, int[] set ) {
		int[] c=getCoordinates();
	
		boolean valid=true;
		for (int m=0; m< set.length; m++) {
			//System.out.print("m "+ c[m] +", n "+ vect[m]+" "+ (c[m]<=vect[m])+",");
			valid= valid && (c[set[m]]<=vect[set[m]]);
			 
		}
		//System.out.println("final: "+valid);
		return valid;
	}	
	
	/* (non-Javadoc)
	 * @see ijaux.hypergeom.Ordering#in(int[], int[], int[])
	 */
	public boolean in (int[] low, int[] hi, int[] set ) {
		int[] c=getCoordinates();
	
		boolean valid=true;
		for (int m=0; m< set.length; m++) {		 
			valid= valid && (c[set[m]]>=low[set[m]]) && (c[set[m]]<=hi[set[m]]);
			 
		}
		//System.out.println("final: "+valid);
		return valid;
	}	
	
	public int max() {
		/*
		int p=1;
		for (int d:dim) p*=d;
		return p;
		*/
		return maxind;
	}
 

	@Override
	public boolean leq(int[] vect, int[] set) {
		return !geq(vect, set);
	}

	@Override
	public boolean out(int[] low, int[] hi, int[] set) {
		return !in(low,hi,set);
	}

	
	@Override
	public String toString() {
		StringBuffer sb= new StringBuffer();
		sb.append("index="+index);
		for (int i=0; i<n; i++) {			 
			sb.append(" x["+i+"]=" +coords[i]);
		}
		
		return sb.toString();
	}

	@Override
	public void setCoordinates(int[] x) {
		for (int i=0; i<coords.length; i++) {
			coords[i]=x[i];
		}
		updated=true;
	}
	
	
	public void set(Pair<Integer,int[]> p) {
		index=p.first;
		for (int i=0; i<coords.length; i++) {
			coords[i]=p.second[i];
		}
	}
	
	public Pair<Integer,int[]> get() {
		return Pair.of(index,coords.clone());
	}
	/*
	 * increasing index in direction <i>k</i> with a <i>step </i>
	 */
	public abstract int inc(int k, int step);
	
	/*
	 * decreasing index in direction <i>k</i> with a <i>step </i>
	 */
	public abstract int dec(int k, int step);

	public abstract void warp();
	
	public abstract void inc();
	
	public abstract void dec();
}
