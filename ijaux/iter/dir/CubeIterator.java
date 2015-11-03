/**
 * 
 */
package ijaux.iter.dir;

import java.util.ArrayList;

import ijaux.datatype.Pair;
import ijaux.datatype.PairedArray;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.*;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.*;
import ijaux.iter.seq.RasterForwardIterator;
import ijaux.iter.seq.RasterIterator;

/**
 *  @author Dimiter Prodanov
 *
 */
public abstract class CubeIterator<E extends Number> extends RasterIterator<E> {
	protected BaseIndex pIndex;
	protected int[] ord;
	protected int ndims=1;
	protected int[] origin;
	protected int[] dims;
	protected int[] span;
	protected int[] offset;
	protected int volume=0;
	protected ArrayList<Integer> spaceIndex;
	protected boolean indexed=false;
	
	public CubeIterator(int[] ord,  int[] sdims) {
		dims=sdims;
		ndims=dims.length;
		offset=new int[ndims];
		origin=new int[ndims];
		span=new int[ndims];
		pIndex=new BaseIndex(dims, 0);	
		setSubspace(ord, dims);		
	}

	public CubeIterator(int[] ord, int[]sorigin, int[] sdims) {
		dims=sdims;
		ndims=dims.length;
		offset=new int[ndims];
		span=new int[ndims];
	
		pIndex=new BaseIndex(sdims, 0);
		setOrigin(sorigin);
		
		setSubspace(ord,dims);
	}

	/**
	 * @param sorigin
	 */
	private int setOrigin(int[] sorigin) {
	    if (sorigin==null)  return -1;		 
		if (sorigin.length!=dims.length) throw new IllegalArgumentException("sizes do not match");
		origin=sorigin;	 
		return pIndex.translateTo(origin);
	}
	
	private void setSubspace(int[] sord, int[] dim) {
		ord=sord;
		int volume=1;
		for (int u=0; u<ord.length; u++) {
			final int k=ord[u];
			final int sz=dim[k];
			//System.out.println(k+"--" + sz+" "+ (sz-origin[k]) );
			volume*=sz-origin[k];
			span[k]=sz;
			offset[k]=origin[k];
			
			System.out.println(k+"--" + offset[k]+" "+  span[k]);

		}
		this.volume=volume; 
		
		
	}
	
	@Override
	public void setPixels(Object cpixels) {
		super.setPixels(cpixels);
		if (!isValid())
			throw new IllegalArgumentException("Wrong partition of data");
		
		spaceIndex=new ArrayList<Integer>(volume);
		
		indexSubspaceIt();
	}

	/**
	 * 
	 */
	public void indexSubspaceIt() {
		if (setpixels) {
			RasterForwardIterator<E> pif=new RasterForwardIterator<E>(pixels);
			pIndex.translateTo(origin);
			while (pif.hasNext()) {
				final int ind=pif.index();
				pIndex.setIndex(ind);
				pIndex.warp();
				pif.next();
				if (pIndex.in(offset, span, ord)) {
					//System.out.print (ind +",");
					spaceIndex.add(ind);
				}

			}
			indexed=true;
		}
	}

	/**
	 * 
	 */
	public void indexSubspaceAc() {
		if (setpixels) {
			pIndex.translateTo(origin);	
			final int a=pIndex.index();
			//Access<E> access = Access.rawAccess(pixels, pIndex);
			final int n=access.size()[0];		
			for (int i=a; i<n; i++) {
				pIndex.setIndex(i);		 
				pIndex.warp();
				if (pIndex.in(offset, span, ord)) {
					//System.out.print (ind +",");
					spaceIndex.add(i);
				}
			}
			indexed=true;
		} // end if
	}
	 
	public boolean isValid() {
		int prod=1;
		for (int p:dims)
			prod*=p;
		return prod==size;
	}
	

}
