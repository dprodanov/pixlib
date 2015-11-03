/**
 * 
 */
package ijaux.iter.dir;

import ijaux.iter.ForwardIterator;

/**
 * @author prodanov
 * @param <E>
 *
 */
public class PixelDirForwardIterator<E extends Number> extends LineIterator<E> implements
		ForwardIterator<E> {
	
	private int cnt=-1;

	public PixelDirForwardIterator(int[] d,  int[] dims) {
		super(d,dims);
		cnt=0;
		setOriginIndex(cnt);
	}
	
	public void setOriginIndex(int c) {
		super.setOriginIndex(c);
		cnt=0;
	}
	

	@Override
	public E first() {
		setOriginIndex(0);
		//System.out.println(i);
		return access.element(i);
	}


	
	@Override
	public E next() {
		E element=access.element(i);
		i=pIndex.inc(dir[0], 1);
	//	i=pIndex.inc(dir[1], 1);
	//	pIndex.calcCoordinates();
		//System.out.print("\n"+pIndex.toString());
		cnt++;
		return element;
	}
	

	
	@Override
	public boolean hasNext() {
		return (cnt<dims[dir[0]]);
	}

	@Override
	public void reset() {
		super.reset();
		cnt=0;
	}

}
