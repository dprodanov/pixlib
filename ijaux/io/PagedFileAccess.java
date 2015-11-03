package ijaux.io;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.datatype.access.PrimitiveAccess;
import ijaux.hypergeom.index.Indexing;
import ijaux.iter.IndexedIterator;
import ijaux.iter.array.ArrayIterator;

 
public class PagedFileAccess<N> extends FileAccess<N> 
							 implements IndexedIterator<Access<N>>, 
							 			Iterable<N> {
	
	private PagedCursor pgcursor;

	
	private File currentFile;
	
	
	private int numpages=1;
	
	
	
	public PagedFileAccess() {
	}
	
	public PagedFileAccess(File f, Cursor.CursorTypes ctype, int pgsize, Class<?> type, boolean direct) {
		this.type=type;
		if (f.exists() && f.isFile()) {
			setCursor(ctype, pgsize, direct);
			currentFile=f;
		} else {
			throw new IllegalArgumentException("not a valid path or file attributes");
		}
	}
	
	public void setCursor(Cursor.CursorTypes type, int pgsize, boolean direct) {
		switch (type) {
			case FileCursor: {
				pgcursor=new FileCursor(pgsize, this.type, direct);
				cursor=pgcursor;
				break;
			}
			case FileMapCursor: {
				pgcursor=new FileMapCursor(pgsize, this.type);
				cursor=pgcursor;
				break;
			}
		}

	}
	
	public PagedCursor getCursor() {
		return pgcursor;
	}
	
	public void config(File f, String mode) {
		currentFile=f;
		accessmode=mode;
	}
	
	public void open () {
		try {
			pgcursor.open(currentFile, accessmode);
			numpages=pgcursor.maxPage();
			isOpened=true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public int seek (int z) {
		int bytesRead=pgcursor.seek(z);
		if (bytesRead>0 )  {
		 
			if (type==byte.class) pixels=pgcursor.array(pgcursor.asByteBuffer());
			if (type==short.class) pixels=pgcursor.array(pgcursor.asShortBuffer());
			if (type==int.class) pixels=pgcursor.array(pgcursor.asIntBuffer());
			if (type==float.class) pixels=pgcursor.array(pgcursor.asFloatBuffer());
			if (type==double.class) pixels=pgcursor.array(pgcursor.asDoubleBuffer());
			index=z;
			try {
				access=Access.rawAccess(pixels, null);
			} catch (UnsupportedTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bytesRead;
	}
	
	
	
	
	public void setAccess(Indexing<int[]> index) {
		if (isOpened) {
			try {
				 access=Access.rawAccess(pixels, index);	
			} catch (UnsupportedTypeException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayIterator<N> pageIterator() {
		if (isOpened) {
			try {
				return ArrayIterator.rawIterator(pixels); 	
			} catch (UnsupportedTypeException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

	@Override
	public boolean hasNext() {
		return (index<numpages);
	}

	@Override
	public Access<N> next() {
		seek(index++);
		return access;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();	
	}

	@Override
	public int index() {
		return index;
	}

	@Override
	public void set(int i) {
		seek (i);
	}

	@Override
	public void inc() {
		pgcursor.inc();
	}

	@Override
	public void dec() {
		pgcursor.dec();
	}

	@Override
	public Iterator<N> iterator() {
		return pageIterator();
	}
	
	@Override
	public int[] size() { 
		return new int[]{numpages,0};
	}

	@Override
	public long length() {
		return numpages;
	}

	

	
	/*
	@Override
	public void putInt(int[] coords, int value) {
		access.putInt(coords, value);
		
	}

	@Override
	public void putDouble(int[] coords, double value) {
		access.putDouble(coords, value);
		
	}

	@Override
	public void putBool(int[] coords, boolean value) {
		access.putBool(coords, value);
		
	}
*/
}
