/**
 * 
 */
package ijaux.hypergeom;

import ijaux.datatype.Pair;
import ijaux.hypergeom.index.BaseIndex;

import java.awt.Shape;
import java.util.Iterator;
import java.util.ListIterator;
 

/*
 * @author Dimiter Prodanov
 */

public class ShapeCube<S extends Shape> extends PairCube<int[], S> {
	

	
	public boolean debug=true;	

		
	public ShapeCube(int[] dim) {
		super(dim, int[].class, Shape.class);
		aind=new BaseIndex(dim);
	}

	/*
	@Override
	public Pair<int[], S> element(int[] coords) {
		//System.out.println("lookup cordinates "+coords);
		final int bind=aind.indexOf(coords);
		final int ind=lookup.get(bind);
		//System.out.println("lookup index "+ind);
		return vector.get(ind);
 	}
 	*/
	
	/*
 	public void addElement(int[] coords, S val) {
		final Pair<int[],S> p=Pair.of(coords, val);
		vector.add(p);
		final int ind =vector.lastIndexOf(p);
		//System.out.println("adding "+ind +" "+coords);
		final int pind=aind.indexOf(coords);
		lookup.put(pind, ind);
		//size=vector.size();
	}
	*/

	/* returns Shape - the type of coordinates
	 * (non-Javadoc)
	 * @see ijaux.hypergeom.HyperCube#getType()
	 */
	@Override
	public Class<?> getType() {
		return btype;
		//return vector.get(index).getClass();
	}

	 

	@Override
	public Iterator<Pair<int[], S>>  iterator() {
		return  vector.listIterator();
	}

	/*
	public void reset() {
		vector.clear();
		lookup.clear();
		size=0;
	}
	 
	 */
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer(2000);
		ListIterator<Pair<int[], S>> li=vector.listIterator();

		int cnt=0;
		while (li.hasNext()) {
			final Pair<int[], S> p=li.next();
			sb.append(cnt +" val: "+p.second +" [");
			for(int c:p.first) {
				sb.append(c+" ");
			}
			sb.append("idx: "+aind.indexOf(p.first));
			sb.append("]\n");
			cnt++;
		}
		return sb.toString();
	}

	@Override
	public int size() {
		return vector.size();
	}

	/* compares to Shape
	 * (non-Javadoc)
	 * @see ijaux.datatype.Typing#eq(java.lang.Class)
	 */
	@Override
	public boolean eq(Class<?> c) {
		return btype==c;
	}


	
	 

}
