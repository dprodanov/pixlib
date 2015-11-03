/**
 * 
 */
package ijaux.iter.dir;

import ijaux.Util;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.BackwardIterator;

/**
 * @author prodanov
 * @param <E>
 *
 */
public class PixelDirBackwardIterator<E extends Number> 
	extends LineIterator<E> implements
		BackwardIterator<E> {
	
	private int cnt=-1;
 
	
	public PixelDirBackwardIterator(int[] d, int[] dims) {
		super(d, dims);
		cnt=dims[dir[0]];
		setOriginIndex(cnt);
		size();
		
		reset(dims);
	}

	public void reset(int[] dims) {
		int cc=0;
		int [] coords=new int[dims.length];
		for (int zd:dims) {
			coords[cc]=zd-1;
			cc++;
		}
		//Util.printIntArray(coords);
		i=pIndex.translateTo(coords);
	}
	
	public void setOriginIndex(int c) {
		super.setOriginIndex(c);
		cnt=dims[dir[0]];
		//System.out.println("setOriginIndex: "+ i);
	}
 
	@Override
	public E last() {
		setOriginIndex(dims[dir[0]]-1);
		final E element=access.element(i);
		//System.out.println("index "+i);
		//System.out.println(dims[dir[0]]+" "+access.size()[0]);
		//i=pIndex.dec(dir[0], 1);
		//System.out.println("index "+i);
		//cnt--;
 		return element;
	}



	@Override
	public E previous() {
		final E element=access.element(i);
		i=pIndex.dec(dir[0], 1);
		cnt--;
		return element;
	}
	
	@Override
	public boolean hasNext() {
 		return false;
	}



	@Override
	public boolean hasPrevious() {		 
		return cnt>0;
	}
	
	@Override
	public E next() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void reset() {
		super.reset();
		cnt=0;
	}


}
