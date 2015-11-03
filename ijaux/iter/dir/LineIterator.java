package ijaux.iter.dir;

import ijaux.Util;
import ijaux.hypergeom.index.*;
import ijaux.iter.seq.*;

public abstract class LineIterator<E> extends RasterIterator<E> {
	
	protected GridIndex pIndex;
	protected int[] dir=new int[LINE];  // plane dir[0] - direction of iteration; dir [1] aux direction for
						   // plane definition
	protected int ndims=1;
	protected int origin=0;
	protected int[] dims;

	public LineIterator(int[] d,  int[] dims) {
		pIndex=GridIndex.create(dims);//new BaseIndex(dims, i);
		pIndex.setIndex(i);
		ndims=pIndex.getNDim();
		this.dims=dims;
		dir=d;
		setDirection(dir);
		//size();
	}
	
	@Override
	public void setPixels(Object cpixels) {
		super.setPixels(cpixels);
		if (!isValid(size, dims))
			throw new IllegalArgumentException("Wrong partition of data");
		//size=dims[dir[0]];
	}
	
	
	public void setOriginIndex(int c) {
		origin=c;
		int [] ov=new int[ndims];
		ov[dir[0]]=c;//dims[dir[1]];
		//Util.printIntArray(ov);
		//Util.printIntArray(dims);
		i=pIndex.indexOf(ov);
		//System.out.println(dir[1]+" index="+i);
	}
	
	public int[] getDirection(){
		return dir;
	}
	
	public void setDirection(int d[]) {
		if (d[0]==d[1]) throw new IllegalArgumentException("Ambiguous direction");
		if (d[0]<ndims && d[1]<ndims && d[0]>=0 && d[1]>=0)
			dir=d;
		else throw new IllegalArgumentException("Wrong index");
	}
	
	@Override
	public void inc() {
		setOriginIndex(origin++);
	}
	
	@Override
	public void dec() {
		setOriginIndex(origin--);
	}
	
	public boolean isValid(int size, int[] dims) {
		int prod=1;
		for (int p:dims)
			prod*=p;
		return prod==size;
	}
	
	protected void size() {
		int prod=1;
		for (int p:dims)
			prod*=p;
		size=prod;
	}
}
