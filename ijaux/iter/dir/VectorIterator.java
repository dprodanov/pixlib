package ijaux.iter.dir;

import java.lang.reflect.Array;
import java.util.Arrays;

import ijaux.Util;
import ijaux.datatype.access.BlockAccess;
import ijaux.hypergeom.index.GridIndex;
import ijaux.hypergeom.index.Indexing;
import ijaux.iter.AbstractIterator;

/*  @author Dimiter Prodanov
 * 	Prototype class for handling pixel data in blocks
 *  <E> can be either a primitive array  or a ComplexArray type
 */
public abstract class VectorIterator<E> extends AbstractIterator<E> {

	protected int blockSize = 16;
	protected Indexing<int[]> pointIdx = null;
	// subspace index
	protected Indexing<int[]> lineIdx = null;
	protected int[] coordinates;
	protected int[] dim;
	protected int n = 1;
	protected int usize = size;
	
	protected Object buffer;
	// axis order
	protected int[] dir;
	
	public boolean debug=false;
	
	public int getDirection(){
		return dir[0];
	}
	
	public void setDirection(int xdir) {
		dir=new int[n];
		dir[0]=xdir;
		int[] udim=new int [n-1];		 
		int u=0;
		for (int k=0; k<n; k++) {
			if (k!=xdir) {
				udim[u]=dim[k];
				u++;
				dir[u]=k;
			}

		}
		
		
		//Util.printIntArray(udim);
		
		// creates a subspace index
		lineIdx= GridIndex.create(udim);	
		//dir=perm(n,xdir);
		//Util.printIntArray(dir);
		blockSize=dim[dir[0]];
		buffer=Array.newInstance(returnType, blockSize);
		
		usize=size/blockSize;
		//if (debug)
		//System.out.println("usize "+usize);
		//axis=1;	
		reset();
	}
	
	public int[] getAxes() {
		return dir;
	}
	
	@Override
	public boolean hasNext() {
		//System.out.println("printing  ..." +i+" / " +( size/blockSize) + ": "+ " axis "+  axis);
		return i < usize;
	}


	public void reset() { 
		i=0; 
		pointIdx.setIndexAndUpdate(0);
		lineIdx.setIndexAndUpdate(0);
	}

	/*
	 * computing global coordinates from the line and point indices 
	 */
	protected void nest(int[] coordinates, int idx) {
		lineIdx.setIndexAndUpdate(idx);
		int[] c = lineIdx.getCoordinates();
		//System.out.println("nesting "+ idx);
		//Util.printIntArray(c);
	
		int u=0;
		for (int i=0; i<n; i++) {
			if (i!=dir[0]) {
				coordinates[i]=c[u];
				//if (coordinates[i]>=dim[i])
				//	coordinates[i]=dim[i]-1;
	
				u++;
			}
	
		}
		if (debug)  {
			System.out.print("coordinates: ");
			Util.printIntArray(coordinates);
		}
	}

	public int getBlockSize() {
		return blockSize;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}