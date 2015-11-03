package ijaux.hypergeom;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

import ijaux.*;
import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.datatype.oper.Op;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.CenteredIndex;

/*
 * @author Dimiter Prodanov
 */
public class VectorCube<E extends Number> extends PairCube<int[], E>{
	
	private Class<?> type;
		
	public boolean debug=false;
	
	E limboval;
	
	protected int indexing=0;
	
	private CenteredIndex ci;
	
	/*
	 * creates an empty vector cube
	 */
	public VectorCube(int[] dim, Class<?> type, boolean rawtype) {
		super(dim, int[].class, Util.getPrimitiveType(type));
		Class<?> c=Util.getPrimitiveType(type);
		//System.out.println(type +": " + c);
		iterPattern=IP_SINGLE+IP_FWD+ IP_PRIM;
		if (c==null) {
			c=Util.getTypeMapping(type);			
			System.out.println(type +": " + c);
			this.type=c;
			btype=c;
		}
		this.type=btype;
		ci=calculateCI(dim);
		aind=ci;
		indexing=CENTERED_INDEXING;
	}

	/*
	 * creates an empty vector cube type restricted
	 */
	public VectorCube(int[] dim, Class<? extends Number> type) {
		super(dim, int[].class, Util.getTypeMapping(type));
		iterPattern=IP_SINGLE+IP_FWD+ IP_PRIM;
		this.type=btype;
		ci=calculateCI(dim);
		aind=ci;
		indexing=CENTERED_INDEXING;
	}
	
	/*
	 * creates a vector cube from a pixel array
	 */
	public VectorCube(int[] dim,  Object cpixels, E dummy, int indexing) {
		super(dim, int[].class, Util.getTypeMapping(dummy.getClass()));
		iterPattern=IP_SINGLE+IP_FWD+ IP_PRIM;
		this.type=btype;
		ci=calculateCI(dim);
		aind=ci;
		indexing=CENTERED_INDEXING;
		//System.out.println("type: " +type);
		PixelCube<E,CenteredIndex> pc=new PixelCube<E,CenteredIndex> (dim, cpixels, Util.getTypeMapping(dummy.getClass()));
		pc.setIndexing(indexing);
		pc.setIterationPattern(iterPattern);
		type=pc.getType();
		calculate(pc);
	}
	
	/*
	 * creates a vector cube from a pixel array
	 */
	public VectorCube(int[] dim,  Object cpixels,   int indexing) {
		this (dim, cpixels.getClass(), true);
		//System.out.println("type: " +type);
		type=this.getType();
		PixelCube<E,CenteredIndex> pc=new PixelCube<E,CenteredIndex> (dim, cpixels, type);
		pc.setIndexing(indexing);
		pc.setIterationPattern(iterPattern);
		type=pc.getType();
		calculate(pc);
	}
	
	public VectorCube<E> clone () {
		return new VectorCube<E>(dim, type,true);
	}
	
	public void addPixels(Object pixels) {		
		PixelCube<E,CenteredIndex> pc=new PixelCube<E,CenteredIndex> (dim, pixels, type);
		calculate(pc);	
	}
	
	/*
	 * creates a vector cube from a pixel cube
	 */
	public VectorCube (PixelCube<E,CenteredIndex> mask){
		 this(mask,CENTERED_INDEXING);
	}
	
	/*
	 * creates a vector cube from a pixel cube
	 */
	public VectorCube (PixelCube<E,?> mask, int indexing){
		super (mask.getDimensions(), int[].class, mask.getType());
		int ip=mask.iterationPattern();
		mask.setIterationPattern(iterPattern);
		mask.setIndexing(indexing);
		this.indexing=indexing;
		type=mask.getType();
		aind=mask.pIndex;
		calculate(mask);
	
		mask.setIterationPattern(ip);
	}
	
	public void setIndexing(int indexing) {
		this.indexing = indexing;
		switch (indexing) {
			case BASE_INDEXING: {
				aind= new BaseIndex(dim);
	 
				break;
			}
			case CENTERED_INDEXING: {
				aind= new CenteredIndex(dim);
		 
				break;
			}
		}
	}
	

	
	public void calculate(PixelCube<E,?>  pc) {
		int cnt=0;
		for (E v:pc) {
			final int[] ci=pc.getCoords(cnt);
			cnt++;
			addElement(ci, v);
		}
		//size=vector.size();
	}
	
	// TODO
	public synchronized VectorCube<E> projectSubspace(int[] ord, int[] origin, int[] dims) {
		if (origin==null) {
			origin=new int[ndim];
		}
		int[] span=new int[ndim];
		int[] offset=new int[ndim];		
		int[] newdims=new int[ord.length];
		 
		int volume=1;
	//	int index=aind.translateTo(origin);
	//	if (debug) System.out.println("translating to " + aind);
		
		for (int u=0; u<ord.length; u++) {
			final int k=ord[u];
			final int sz=dims[k];
			newdims[u]=sz-origin[k];
			volume*=newdims[u];		
			span[k]=sz;
			offset[k]=origin[k];
			//System.out.println(k+"- "+ newdims[u] );
		}

		Object newpixels=Array.newInstance(getType(), volume);
		if (debug) System.out.println(newpixels.getClass().getCanonicalName() +" "+ volume);
		
		VectorCube<E> vcnew=new VectorCube<E>(newdims,  type, true);
		
		ListIterator<Pair<int[], E>> li=vector.listIterator();
		 
		//pif.set(index);
		//if(debug) System.out.println("setting index to "+index);
		
		while (li.hasNext() ) {	
			final Pair<int[], E> p=li.next();
			aind.translateTo(p.first);
			 
			if (aind.in(offset, span, ord)) {
				 vcnew.addElement(p);
			}
			
		}
	
		 
		
		return vcnew;		
	}
	 
	/*
	 * reduces extra dimensionality
	 */
	
	/*public int[] trimDims(int[] dims) {
		int cnt=0;
		for (int d:dims) {
			if (d>1) cnt++;
		}
		int[] newdims=new int[cnt];
		cnt=0;
		for (int d:dims) {
			if (d>1) {
				newdims[cnt]=d;
				cnt++;
			}
		}
		return newdims;
	}*/
	
	public void setLimbo(E value) {
		limboval=value;
	}
	
	 
	@Override
	public Iterator<Pair<int[], E>>  iterator() {
		return  vector.listIterator();
	}

	private int[][] coords=null;
	private Object cvals;
	
	public void split () {
		int sz=vector.size();
		int n=ci.getNDim();
		coords=new int[sz][n];
		cvals=Array.newInstance(type, sz);
		try {
			Access<E> ac=Access.rawAccess(cvals, null);
			int i=0;
			for (Pair<int[], E> p:vector) {
				coords[i]=p.first;
				ac.putE(i,p.second);
				i++;
			}
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * removes zero elements
	 */
	public void trim() {
		ListIterator<Pair<int[], E>> li=vector.listIterator();		
		ArrayList<Pair<int[], E>> zeros=new ArrayList<Pair<int[], E>>();
		while (li.hasNext()) {
			Pair<int[], E> p=li.next();	 
			if (p.second.floatValue()==0) {				
				zeros.add(p);
			}
					 
		}
		for (Pair<int[], E> p: zeros) {
			removeElement(p);
		}
	}
	
	public Pair<int[],int[]> calcMinMax() {
		Set<Entry<Integer, Integer>> entries=lookup.entrySet();
		int min=0; int max=0;
		for (Entry<Integer, Integer> e:entries) {
			int key=e.getKey();
			if( min>=key)
				min=key;
			if( max<=key)
				max=key;
			
			
		} // end for
//		System.out.println("min "+min+" max "+max);
		int[] cmin=ci.coordsOf(min);
		int[] cmax=ci.coordsOf(max);
//		Util.printIntArray(cmin);
//		Util.printIntArray(cmax);
		return Pair.of(cmin,cmax);
	}
	
	public int[][] coordsArray () {
		return coords;
	}
	
	@SuppressWarnings("unchecked")
	public <N> N valueArray() {
		return (N) cvals;
	}
	
	public void flip() {		
		for (Pair<int[],E> p:vector) {
			int pind=aind.indexOf(p.first);
			final int ind =vector.lastIndexOf(p);
			reflectArr(p.first);
			lookup.remove(pind);
			pind=ci.indexOf(p.first);
			lookup.put(pind, ind);
			
		}
	}
	
	private void reflectArr(int[] arr) {
		for (int i=0; i<arr.length; i++) {
			arr[i]=-arr[i];
		}
		
	}

	public void invert() {			
		try {
			@SuppressWarnings("unchecked")
			Op<E> op= (Op<E>) Op.get(type);
			//System.out.println(type);
			for (Pair<int[],E> p:vector) {
				//System.out.println(p.second.getClass());
				p.second=op.invE(p.second);
			}
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public Class<?> getType() {
		return btype;
	}

	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer(2000);
		ListIterator<Pair<int[], E>> li=vector.listIterator();
		int[] center=ci.getCenter();
		sb.append("[ ");
		for (int d: center) {
			sb.append(d+" ");
		}
		sb.append("]\n");
		int cnt=0;
		while (li.hasNext()) {
			final Pair<int[], E> p=li.next();
			sb.append(cnt +" val: "+p.second +" [");
			for(int c:p.first) {
				sb.append(c+" ");
			}
			sb.append("idx: "+ci.indexOf(p.first));
			sb.append("]\n");
			cnt++;
		}
		return sb.toString();
	}
	
	public Object toArray() {
		Object pixels=Array.newInstance(type, size);
		Class<?> type=Util.getPrimitiveType(pixels.getClass());

		try {
			Access<E> access=Access.rawAccess(pixels, aind);
			if (debug) System.out.println("array type " +type);
		ListIterator<Pair<int[], E>> li=vector.listIterator();
		int cnt=0;
		while (li.hasNext()) {
			final Pair<int[], E> p=li.next();
			access.putE(cnt, p.second);
			cnt++;
		}
		return pixels;
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private CenteredIndex calculateCI(int[] dims) {
		this.dim=dims;
		CenteredIndex ci=new CenteredIndex(dim);
		ci.setIndex(0);
		return ci;
	}
	
	public CenteredIndex getCI() {
		return calculateCI(dim);
	}
	
	private BaseIndex calculateBI(int[] dims) {
		this.dim=dims;
		BaseIndex bi=new BaseIndex(dim);
		bi.setIndex(0);
		return bi;
	}
	
	public BaseIndex getBI() {
		return calculateBI(dim);
	}
	
	public Object toOrderedArray() {
		final int  size=Util.cumprod(dim);
		Object pixels=Array.newInstance(type, size);
		Class<?> type=Util.getPrimitiveType(pixels.getClass());

		try {
			Access<E> access=Access.rawAccess(pixels, aind);
			if (debug) System.out.println("array type " +type);
			ListIterator<Pair<int[], E>> li=vector.listIterator();

			while (li.hasNext()) {
				final Pair<int[], E> p=li.next();
				access.putV(p.first, p.second);
	
			}
			return pixels;
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
		return null;
	
	}
	
 
	
	public PixelCube<E,BaseIndex> toCube() {
		PixelCube<E, BaseIndex> ret= new PixelCube<E, BaseIndex>(dim,toOrderedArray());
		ret.setIndexing(BASE_INDEXING);
		return ret;
	}

	@Override
	public boolean eq(Class<?> c) {
		return type==c;
	}
	

}
