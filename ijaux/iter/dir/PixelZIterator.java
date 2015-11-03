/**
 * 
 */
package ijaux.iter.dir;

import ijaux.iter.ForwardIterator;

/**
 * @author prodanov
 *
 */
public class PixelZIterator<E> extends LineIterator<E> implements
		ForwardIterator<E> {

	private int cnt=-1;
	private int area=1;

	
	public PixelZIterator(int[] d, int[] dims) {
		super(d, dims);
		final int k=d[0];
		final int p=d[1];
		area=dims[k]*dims[p];
		//System.out.println("N="+area);
		//System.out.println("ND="+ndims);
		cnt=0;
	}

	/* (non-Javadoc)
	 * @see ijaux.iter.ForwardIterator#first()
	 */
	@Override
	public E first() {
		setOriginIndex(0);
		return access.element(i);
	}
	
	@Override
	public E next() {
		E element=access.element(i);
		
		int[] trv = forward();
 
		//System.out.println(pIndex.toString());
		i=pIndex.translate(trv);
		//System.out.println(pIndex.toString());
		
		return element;
	}
	
	@Override
	public void put (E value) {
		super.put(value);
		int[] trv = forward();
		 
		//System.out.println(pIndex.toString());
		i=pIndex.translate(trv);
	}

	/**
	 * @return int[]
	 */
	private int[] forward() {
		final int k=dir[0];
		final int p=dir[1];
		final int rowcnt=cnt/dims[k];
 
		cnt++;
		final int newrowcnt=cnt/dims[k];
		
		int[] trv=new int[ndims];
		final int w=newrowcnt-rowcnt;
		trv[k]=(1 - 2*(rowcnt%2))*(1-w);
		trv[p]=w;
		//System.out.println("\nw="+(1-w));
		//System.out.println("\ntr["+k+"]="+trv[k]+ " tr["+p+"]="+trv[p]);
		return trv;
	}

	/* (non-Javadoc)
	 * @see ijaux.iter.ForwardIterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		//System.out.println("cnt="+cnt);
		return (cnt<area);
	}

}
