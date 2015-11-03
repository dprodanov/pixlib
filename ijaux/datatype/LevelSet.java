package ijaux.datatype;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class LevelSet<N extends Number> extends Pair<N,ArrayList<Integer>>
implements Iterable<Integer> {

	/** bits of wisdom:
	 *  i++ evaluates to the current value of i, and then increments the value of i by one.
			++i increments the value of i by one, and then evaluates to this new value of i.
	 */
	private static final long serialVersionUID = 1826353482111912854L;

	public N element=null;
	
	public ArrayList<Integer> indices=null;
	
	public LevelSet(N element, ArrayList<Integer> indices) {
		super(element, indices);
		this.element=element;
		this.indices=indices;
	}
	
	public LevelSet(N element, int[] indarray) {
		super(element, null);
		this.element=element;
		indices=new ArrayList<Integer>();
		add(indarray);
		second=indices;
	}
	
	
	public int[] getArray() {
		final Iterator<Integer> iter=indices.iterator();
		final int sz=indices.size();
		int[] ret=new int[sz];
		int i=0;
		while (iter.hasNext()) 
			ret[i++]=iter.next();
		return ret;
	}
	
	public void add(int[] ind) {
		for (int i:ind)
			indices.add(i);
	}
	
	public void remove(int[] ind) {
		for (int i:ind)
			remove(i);
	}
	
	/*
	 * adds an element ind
	 */
	public void add(int ind){
		indices.add(ind);
	}
	
	/*
	 *  removes the element ind
	 */
	public void remove(int ind) {
		int sindex=indices.indexOf(ind);
		indices.remove(sindex);
	}


	@Override
	public Iterator<Integer> iterator() {
		return indices.iterator();
	}
	
	@Override
	public String toString() {
		String ret="["+ element +" :<";
		 for (int u:indices)
			 ret+=u+",";
		return ret.substring(0, ret.length()-1) +">]";
	}

}
