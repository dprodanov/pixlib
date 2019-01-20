package ijaux.hypergeom;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.Iterator;

import ijaux.Util;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.CenteredIndex;
import ijaux.hypergeom.index.Indexing;
import ijaux.io.Cursor;
import ijaux.io.Cursor.CursorTypes;
import ijaux.io.PagedFileAccess;

/*
 * @author Dimiter Prodanov
 */
//TODO
public class FileCube<E extends Number, I extends Indexing<int[]>> 
implements HyperCube<int[],E>, Cloneable{

	protected int[] dim;
	protected int n=-1;
	protected Indexing<int[]> pIndex;
	protected int iterPattern=IP_DEFAULT;
	int blockSize=1;
	private int[] dir=new int[2];
	
	protected int size=0; 
	protected Class<?> indexingType;
	protected Class<?> type;
	
	public static boolean debug=true;
	PagedFileAccess<E> pa;
	
	
	
	public FileCube(int[] sdim,   E dummy) {
		size=Util.cumprod(sdim);
		dim=sdim;
		type=Util.getTypeMapping(dummy.getClass());
		 
		 
	}
	
	public void setPixelsFromFile(File pixfile, int pgsize, boolean direct) { 
		blockSize=pgsize; 
		pa=new PagedFileAccess<E>(pixfile, CursorTypes.FileMapCursor,   pgsize,  type,   direct) ;
			
	}
	
	
	public PagedFileAccess<E> getAccess() {
		return pa;
	}
	
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int iterationPattern() {
		return iterPattern;
	}
	
	@Override
	public Class<?> getType() {
		return type;
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
	
	
	public void reshapeTo(int [] newdims) {
		int prod=1;
		for (int p:newdims)
			prod*=p;
		if (prod!=size)
			throw new IllegalArgumentException("Size of element array does not match");
		else {
			dim=newdims;
			blockSize=dim[0];
			n=dim.length;
			pIndex.reshape(dim);
		}
	}
	
	public int[] getDir() {
		return dir;
	}

	public void setDir(int[] dir) {
		this.dir = dir;
	}
	
	@SuppressWarnings("unchecked")
	void indexing(Class<?> c) {
	
		indexingType=c;
		try {
			if (debug) System.out.println(c.getCanonicalName());
			Class<?>[] par={int[].class, int.class};
			Constructor<?> con=c.getConstructor(par);
			Object[] init={dim,0};
			pIndex=(I)con.newInstance(init);
 
		} catch (Exception e) {
		 
			e.printStackTrace();
		}
		
	}
	 
	
	@SuppressWarnings("unchecked")
	public void setIndexing(final int indextype) {
		switch (indextype) {
			case BASE_INDEXING: {
				pIndex=(I) new BaseIndex(dim);
				indexingType=pIndex.getClass();
				break;
			}
			case CENTERED_INDEXING: {
				pIndex=(I) new CenteredIndex(dim);
				indexingType=pIndex.getClass();
				break;
			}
		}
		
	}
	
	@Override
	public boolean eq(Class<?> c) {
		return type==c;
	}

	@Override
	public int[] coordinates() {
		return pIndex.getCoordinates();
	}


}
