package ijaux.hypergeom;

import ijaux.Util;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.CenteredIndex;
import ijaux.hypergeom.index.Indexing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
/*
 * topographic representation
 */
public class LevelCube<E extends Number> implements HyperCube<int[], E>, Cloneable {

	protected TreeMap<E,ArrayList<Integer>> levelmap=new TreeMap<E,ArrayList<Integer>> ();
	
	public boolean debug=false;
		
	private boolean typeset=false;
	protected Indexing<int[]> pIndex;
	protected int size=0;
	protected int[] dim;
	protected int iterPattern=0;
	protected int n=-1;
	
	protected Class<?> indexingType;
	protected Class<?> type;
	protected LevelSet<E> levelset=null;
	
	public LevelCube(int[] dims, E dummy) {
		dim=dims;
		n=dim.length;
		size=Util.cumprod(dims);
		type=Util.getTypeMapping(dummy.getClass());
		typeset=true;
	}
	
	public LevelCube(int[] dims, Access<?> access) {
		dim=dims;
		n=dim.length;
		size=Util.cumprod(dims);
		type=access.getType();
		typeset=true;
		addToMap(access);
	}
	
	
	public LevelCube (PixelCube<E,?> pc) {
		dim=pc.getDimensions();
		n=pc.getNDimensions();
		size=pc.size();
		type=pc.getType();
		typeset=true;
		Access<E> access=pc.getAccess();
		addToMap(access);
	}
	
	@SuppressWarnings("unchecked")
	public <N> void addToMap(Access<N> access) {
		ArrayList<Integer> arr=null;
		for (int i=0; i<access.length(); i++) {
			final N pixval=access.element(i);
			if (levelmap.containsKey(pixval)) {	
				 arr=levelmap.get(pixval);
				 arr.add(i);			 
				//System.out.println("i="+i +" pix " +pixels[i] +" cnt " +cnt);
			}  else {
				ArrayList<Integer> arr2=new ArrayList<Integer>();
				arr2.add(i);
				levelmap.put((E)pixval, arr2);
			}

		}
	}  
	public void setIndexing(final int indextype) {
		switch (indextype) {
			case BASE_INDEXING: {
				pIndex= new BaseIndex(dim);
				indexingType=pIndex.getClass();	
				break;
			}
			case CENTERED_INDEXING: {
				pIndex= new CenteredIndex(dim);
				indexingType=pIndex.getClass();		
				break;
			}
		}
		
	}
	public LevelSet<E> getLSet(E level) {
		ArrayList<Integer> list=levelmap.get(level);
		levelset=new LevelSet<E> (level,list);
		return levelset;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public boolean eq(Class<?> c) {
		return type==c;
	}

	@Override
	public int iterationPattern() {
		return iterPattern;
	}

	@Override
	public int getNDimensions() {
		return n;
	}

	@Override
	public int[] getDimensions() {
		return dim;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int index() {
		return pIndex.index();
	}

	@Override
	public int[] coordinates() {
		return pIndex.getCoordinates();
	}

	@Override
	public Iterator<E> iterator() {
		return levelmap.navigableKeySet().iterator();
	}

	public Set<Entry<E,ArrayList<Integer>>> entrySet() {
		return levelmap.entrySet();
	}
	
}
