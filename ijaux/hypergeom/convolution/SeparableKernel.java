package ijaux.hypergeom.convolution;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ijaux.Util;
import ijaux.datatype.Separable;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.array.ArrayIterator;
import ijaux.iter.dir.PixelDirForwardIterator;

/*
 * @author Dimiter Prodanov
 */
public class SeparableKernel<E extends Number,N> extends Kernel<E, N> implements
		Separable<N> {
	protected N[] kernels;
	protected int[][] sdim;
	
	private boolean issplit=false;
	private int nKernels=0;
 
	
	public SeparableKernel(N arr, E dummy, int[] dims) {
		super(arr, dummy, dims);
		split();
	}

 
	
	public SeparableKernel(N[] arr, E dummy, int[][] dims) {
		//super(arr, dummy );
		super(arr[0], dummy, dims[0]);
 		if (!arr[0].getClass().isArray())
			throw new IllegalArgumentException ("Not a double array");
 
		nKernels=arr.length;
		//System.out.println("n kernels:"+nKernels);
		dim=calcDim(arr);
 		
		//System.out.println("dims:");
		//Util.printIntArray(dim);
		ndim=dim.length;
		permutateDims(arr);
		
	
		kernels=arr;
		issplit=true;
		
	}

	private int[] calcDim(N[] arr) {
		ArrayList<Integer> dimlist=new ArrayList<Integer>();
		try {			
			final int s=Array.getLength(arr);			
			for (int u=0; u<s; u++) {
				final Object elem=Array.get(arr, u);
				final int y=Array.getLength(elem);
				dimlist.add(y);
			}
			 
			 
		} catch (Exception ex) {
			//ex.printStackTrace();
		}

		final int s=dimlist.size();
		int[] sdim= new int[s];
		int cnt=0;
		for (int c:dimlist) {
			sdim[cnt]=c;
			cnt++;
			//System.out.print(c+",");
		}
		return sdim;
	}

	private void permutateDims(N[] arr) {
		this.sdim=new int[nKernels][];
		
		for (int k=0; k<nKernels; k++) {
		
			int[] adim=new int[dim.length];
			for (int i=0; i<dim.length; i++) {
					adim[i]=1;
			}
			//final int z=ord[k];
			//System.out.println(z +" "+dim[z]);
			
			final int w=Array.getLength(arr[k]);
			adim[k]=w; //dim[z];					    
			
			//Util.printIntArray(adim);
			
			sdim[k]=new int[dim.length];
			sdim[k]=adim;
			
		}
	}
	
	public int[] getSubDimensions(int k) {		 
		return sdim[k];
	}
	
/*	private void rec (Object arr, ArrayList<Integer> dimlist) {
		try {
			
			final int s=Array.getLength(arr);
			dimlist.add(s);
			arr=Array.get(arr, 0);
			rec(arr,dimlist);
			 
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
	}*/
	
	/*private void calcDim (Object arr, ArrayList<Integer> dimlist) {
		try {			
			final int s=Array.getLength(arr);			
			for (int u=0; u<s; u++) {
				final Object elem=Array.get(arr, u);
				final int y=Array.getLength(elem);
				dimlist.add(y);
			}
			 
			 
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
	}*/
/*	private int[] permDims(int[] dims, int [] ord) {
		final int n=ord.length;
		int[] newdims=new int[n];
		
		for (int c=0; c<n; c++) {
			final int z=ord[c];
			try {
				newdims[c]=dims[z];
			} catch (ArrayIndexOutOfBoundsException ex) {
				newdims[c]=1;
			}
		}
		
		
		return newdims;
	}*/
	
	@Override
	public int getNKernels() {
		return ndim;
	}

	@Override
	public N[] getKernelArrays() {
		return kernels;
	}

	public Kernel<E,N> getKernel(int i) {	
		final Kernel<E,N> k=new Kernel<E,N>(kernels[i], dummy, sdim[i]);
		return k;
	}
	 
	// TODO
	@Override
	public void split() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSeparable() {
		return issplit;
	}

	// TODO
	@SuppressWarnings("unchecked")
	@Override
	public N join() {

		PixelCube<E, BaseIndex> pc=new PixelCube<E,BaseIndex>(dim, dummy);
		
		
		pc.setIndexing(BASE_INDEXING);
		pc.one();

		pc.setIterationPattern(IP_SINGLE+IP_DIR+IP_FWD);
		pc.setDir(new int[]{0,1});
		PixelDirForwardIterator<E> iter=(PixelDirForwardIterator<E>) pc.iterator();
		PixelDirForwardIterator<E> iter2=(PixelDirForwardIterator<E>) pc.iterator();

		for (int i=0; i<nKernels; i++) {
			
			System.out.println("iter: "+i);
			for (int u=0; u<ndim; u++) {
				int[] dir={0,u+1}; //direction
				if (u==ndim-1)
					dir=new int[]{1,0};
				iter.setDirection(dir);
				iter2.setDirection(dir);
				int p=0;
				Access<?> kaccess;
				try {
					kaccess = Access.rawAccess(kernels[i], null);
					final int len=kaccess.size()[0];
					System.out.println(dim[u]+",");
					while (iter.hasNext()) {
						float elem=  iter.next().floatValue();
						elem*=kaccess.elementFloat(p);
						System.out.print(elem+",");
						iter2.putFloat(elem);
						iter2.inc();
						p++;
						p=p%len;
					}
					iter.reset();
				
					iter2.reset();
					System.out.println();
				} catch (UnsupportedTypeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} // end catch

			} // end for

		} // end for
		//System.out.println(pc);
		return (N)pc.getAccess().getArray() ;

	}//
	/*
	 *  tensor product
	 */
	public <N,V> void tPoduct(N arr1,  V arr2) {
		try {
			Access<?> access1=Access.rawAccess(arr1, null);
			Access<?> access2=Access.rawAccess(arr2, null);
			for (int i=0; i<access1.size()[0]; i++) {
				final float f=access1.elementFloat(i)*access2.elementFloat(i);
				access1.putFloat(i, f);
			}
			
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *  dot product
	 */
	public <N,V> float dotPoduct(N arr1,  V arr2) {
		float sum=0;
		try {
			Access<?> access1=Access.rawAccess(arr1, null);
			Access<?> access2=Access.rawAccess(arr2, null);			
			for (int i=0; i<access1.size()[0]; i++) {
				final float f=access1.elementFloat(i)*access2.elementFloat(i);
				sum+=f;
			}
			
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
		return  sum;
	}

}
