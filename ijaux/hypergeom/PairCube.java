/**
 * 
 */
package ijaux.hypergeom;

import ijaux.Constants;
import ijaux.datatype.Pair;
import ijaux.datatype.Typing;
import ijaux.hypergeom.index.Indexing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/*
 * @author Dimiter Prodanov
 */

public abstract class PairCube<VectorType, B> implements 
HyperCube<VectorType, Pair<VectorType, B>>, Cloneable, Constants {

	/**
	 * 
	 */
	// storage array
	protected ArrayList< Pair<VectorType, B>> vector=new ArrayList< Pair<VectorType, B>>();
	// inverse lookup
	protected ConcurrentHashMap<Integer, Integer> lookup=new ConcurrentHashMap<Integer, Integer>();


	protected int iterPattern=0;
	
	protected int size=0;
	protected int ndim=1;
	protected int index=0;
	protected int[] dim;
	
	protected Class<?> atype;
	protected Class<?> btype;
	
	protected Indexing<VectorType> aind;
	
	public PairCube(int[] dim, Class<?> atype, Class<?> btype) {
		this.dim=dim;
		this.ndim=dim.length;
		this.atype=atype;
		this.btype=btype;
	}


	public Pair<VectorType, B> element(int index) {
		//System.out.println("lookup coordinates "+coords);
		this.index=index;
		return vector.get(index);
	}
	

	public Pair<VectorType, B> element(VectorType coords) {	
		final int bind=aind.indexOf(coords);
		//System.out.println("lookup index "+bind);
		final int ind=lookup.get(bind);
		//System.out.println("lookup index "+ind);
		return vector.get(ind);
 	}
	
 	
 	public int updateSize() {
 		size=vector.size();
 		return size;
 	}
 	
 	
 	public void addElement(VectorType coords, B val) {
		final Pair<VectorType,B> p=Pair.of(coords, val);
		vector.add(p);
		final int ind =vector.lastIndexOf(p);
		//System.out.println("adding "+ind +" "+coords);
		final int pind=aind.indexOf(coords);
		lookup.put(pind, ind);
		size=vector.size();
	}
 	
 	public void addElement(final Pair<VectorType,B> p) {
		//final Pair<VectorType,B> p=Pair.of(coords, val);
		vector.add(p);
		final int ind =vector.lastIndexOf(p);
		//System.out.println("adding "+ind +" "+p.first);
		final int pind=aind.indexOf(p.first);
		lookup.put(pind, ind);
		//size=vector.size();
	}
 	
 	
 	public void removeElement(final Pair<VectorType,B> p) {
 		final int ind =vector.lastIndexOf(p);
 		final int pind=aind.indexOf(p.first);
 		vector.remove(ind);
 		lookup.remove(pind);
 		size--;
 	}
 	
	@Override
	public int[] getDimensions() {
		return dim;
	}

	@Override
	public int getNDimensions() {
		return ndim;
	}

	@Override
	public int index() {
		return index;
	}

	@Override
	public int iterationPattern() {
		return iterPattern;
	}

	public int size() {
		size=vector.size();

		return size;
	}
	
	
	public void setIterationPattern(int ip) {
		iterPattern=ip;
	}
	
	public void reset() {
		vector.clear();
		lookup.clear();
		size=0;
	}
	
	public boolean isValid() {
		return (vector.size()==lookup.size());
	}
	
	@Override
	public Iterator<Pair<VectorType, B>>   iterator() {
		return  vector.listIterator();
	}

	@Override
	public VectorType coordinates() {
		return aind.getCoordinates();
	}
}
