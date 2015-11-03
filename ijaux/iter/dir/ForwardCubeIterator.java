/**
 * 
 */
package ijaux.iter.dir;

import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.ForwardIterator;
import ijaux.iter.seq.RasterIterator;

/**
 * @author prodanov
 *
 */
public class ForwardCubeIterator<E extends Number> extends CubeIterator<E> 
							 implements ForwardIterator<E>{
	
	public ForwardCubeIterator(int[] d, int[] sdims) {
		super(d, sdims);
	}
	
	public ForwardCubeIterator(int[] d, int[] sorigin, int[] sdims) {
		super(d, sorigin, sdims);
	}
	


	private int cnt=0;
	
	@Override
	public E next() {
		final int i=spaceIndex.get(cnt); 
		//System.out.print(i+";");
		E ret= access.element(i);
	 	
		cnt++;
		return ret;
		
	}
	
	@Override
	public E first() {
		final int i=spaceIndex.get(0); 
		return access.element(i);
	}

	@Override
	public boolean hasNext() {
		return (cnt<volume);
	}


}
