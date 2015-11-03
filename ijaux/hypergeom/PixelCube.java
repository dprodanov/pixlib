/**
 * 
 */
package ijaux.hypergeom;

import java.util.*;
import java.lang.reflect.*;

import ijaux.*;
import ijaux.datatype.Pair;
import ijaux.datatype.Typing;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.datatype.access.BlockAccess;
import ijaux.funct.SimpleFunction;
import ijaux.funct.iter.FunctionIterator;
import ijaux.funct.iter.RasterFunctIterator;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.CenteredIndex;
import ijaux.hypergeom.index.Indexing;
import ijaux.iter.*;
import ijaux.iter.array.*;
import ijaux.iter.dir.*;
import ijaux.iter.seq.*;


/** This class describes a pixel cube backed by an array/ ArrayAccess in memory
/*
 * @author Dimiter Prodanov
 *
 */
public class PixelCube<E extends Number, I extends Indexing<int[]>> 
extends RasterCube<E,int[]>
implements  HyperCube<int[],E>,Cloneable {
	
	protected Object pixels;
	int blockSize=1;
	private int[] dir=new int[2];	
	public static boolean debug=false;
	protected Access<E> access;
	
	//private boolean useIter=false;
	
	public PixelCube(int[] sdim,   E dummy) {
		size=Util.cumprod(sdim);
		dim=sdim;
		type=Util.getTypeMapping(dummy.getClass());
		pixels=Array.newInstance(type, (int) size);
		try {
			setAccess(pixels);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PixelCube(PixelCube<E,?> pc) {
		size=pc.size;
		dim=pc.dim;
		n=pc.n;
		setPixels(Array.newInstance(pc.type, (int) size));
		indexingType=pc.indexingType;
		iterPattern=pc.iterationPattern();
		access=pc.access;
	}
	
	 
	public PixelCube(int[] sdim, Object cpixels, Class<?>dc) { 
		dim=sdim;
		if (isValid(cpixels, dc)) {
			//System.out.println("valid size>>>");
			setPixels(cpixels);
			
			blockSize=dim[0];
			n=dim.length;
			try {
				setAccess(cpixels);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("Size or type do not match : " +size +" " + dc);
		}
	}
	 
	public PixelCube(int[] sdim, Object cpixels, E dummy) { 
		final Class<?> dc=Util.getTypeMapping(dummy.getClass());
		dim=sdim;
		if (isValid(cpixels, dc)) {
			//System.out.println("valid size>>>");
			setPixels(cpixels);
			
			blockSize=dim[0];
			n=dim.length;
			try {
				setAccess(cpixels);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("Not an array ( "+size+" ) or wrong type ( "+dc+" )");
		}
	}
	/*
	 * not type safe
	 */
	public PixelCube(int[] sdim, Object cpixels) {
		dim=sdim;
		if (isValid(cpixels)) {
			//System.out.println("valid size>>>");
			setPixels(cpixels);
			
			blockSize=dim[0];
			n=dim.length;
			try {
				setAccess(cpixels);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("Size does not match : " +size);
		}
	}
	
	
	public PixelCube(int[] sdim, Object cpixels, Class<?>dc, Class<I> c) {
		dim=sdim;
		if (isValid (cpixels, dc)) {
			setPixels(cpixels);
			blockSize=dim[0];
			n=dim.length;
			try {
				setAccess(cpixels);
			} catch (Exception e) {
				e.printStackTrace();
			}
			indexing(c); 
		} else {
			throw new IllegalArgumentException("Dimensions or type do not match");
		}
	}
	 
	public PixelCube(int[] sdim, Class<I> c) {
		dim=sdim;
		blockSize=dim[0];
		n=dim.length;
		int prod=1;
		for (int p:dim) prod*=p;
		size=prod; 
		indexing(c);
	}
	

	@Override
	public PixelCube<E, I> clone() {
		Object newpix=Array.newInstance(type, (int) size);
		System.arraycopy(pixels, 0, newpix, 0, (int) size);
		PixelCube<E,I> pc=new PixelCube<E,I>(dim, newpix);	 
		pc.indexing(indexingType);
		pc.iterPattern=iterationPattern();		
		return pc;		
	}

	/*
	 * checks if pixels is an array and its primitive type matches dc
	 */
	public boolean isValid(Object pixels, Class<?> dc) {
		final Class<? > c=pixels.getClass();	
		final Class<? > c2=Util.getPrimitiveType(c);
		if (debug) 
			System.out.println("pixels: " +c);
		if (debug) 
			System.out.println("dc: " +dc);
		if (c.isArray() && c2==dc) { 
			int prod=1;
			for (int p:dim) { 
				//System.out.print(p +", ");
				prod*=p;
			}	
			size=prod;
		
			if (debug) System.out.println("array length " +Array.getLength(pixels));
			return (Array.getLength(pixels)== prod);
			
		} else
		return false;
	}
	
	/*
	 * checks if pixels is array
	 */
	public boolean isValid(Object pixels) {
		Class<? > c=pixels.getClass();	 
		if (c.isArray()) { 
			int prod=1;
			for (int p:dim) { 
				//System.out.print(p +", ");
				prod*=p;
			}	
			size=prod;
		
			if (debug) System.out.println("array length " +Array.getLength(pixels));
			return (Array.getLength(pixels)== prod);
			
		} else
		return false;
	}
 
	@SuppressWarnings("unchecked")
	public void indexing(Class<?> c) {
	
		indexingType=c;
		try {
			if (debug) 
				System.out.println(c.getCanonicalName());
			Class<?>[] par={int[].class};
			Constructor<?> con=c.getConstructor(par);
			pIndex=(I)con.newInstance(dim);
			if (debug) 
				System.out.println(pIndex.getClass().getCanonicalName());
			access.setIndexing(pIndex);
 
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
				access.setIndexing(pIndex);
				break;
			}
			case CENTERED_INDEXING: {
				pIndex=(I) new CenteredIndex(dim);
				indexingType=pIndex.getClass();
				access.setIndexing(pIndex);
				break;
			}
		}
		
	}
	

	public void setPixels(Object cpixels) {
		Class<? > c=cpixels.getClass();
		if (debug) System.out.println(c.getCanonicalName());
	
		if (c.isArray()) {
			pixels=cpixels;
			if (size!=Array.getLength(pixels))
				throw new IllegalArgumentException("Array lenght does not match "+ size);
			
			if (Array.get(pixels, 0).getClass().isArray())
				throw new IllegalArgumentException("Argument is a multidimensional array");
			type=Util.getPrimitiveType(pixels.getClass());
		} else {
			throw new IllegalArgumentException("Argument is not an array");
		}
	 
	}
	
/*	public int size() {
		return size;
	}
	*/

	public Access<E> getAccess(){
		return access;		
	}
	
	
	@SuppressWarnings("unchecked")
	public I getIndex() {
		return (I)pIndex;
	}
	
	
 
	public void setAccess(Object pixels)   {
		try {
			access=Access.rawAccess(pixels, pIndex);
			
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
	}
	

	/*
	public void useIter() {
		useIter=true;
	}

	public void useAccess() {
		useIter=false;
	}
	*/
	public void one() {			
		for (int i=0; i<size; i++) {			 
			access.putInt(i, 1);
			//System.out.print(access.elementFloat(i)+":");
		}
	}
	
	public synchronized PixelCube<E,I> projectSubspace(int[] ord, int[] origin, int[] dims, boolean trim) {
		if (origin==null) {
			origin=new int[n];
		}
		int[] span=new int[n];
		int[] offset=new int[n];		
		int[] newdims=new int[ord.length];
		 
		int volume=1;
		//debug=true;
		if (debug) Util.printIntArray(origin);
		
		int index=pIndex.translateTo(origin);
	
		if (debug) 
			System.out.println("translating to " + pIndex);
		
		for (int u=0; u<ord.length; u++) {
			final int k=ord[u];
			final int sz=dims[k];
			newdims[u]=sz-origin[k];
			volume*=newdims[u];		
			span[k]=sz;
			offset[k]=origin[k];
			//System.out.println(k+"- "+ newdims[u] );
		}

		Object newpixels=Array.newInstance(getType(), volume);
		if (debug) System.out.println(newpixels.getClass().getCanonicalName() +" "+ volume);
	 
		//if (useIter)
		try {
		
		AbstractIterator<E> newpif = ArrayIterator.rawIterator(newpixels);
		AbstractIterator<E> pif=ArrayIterator.rawIterator (pixels);
			
		pif.set(index);
		if(debug) System.out.println("setting index to "+index);
		
		while (pif.hasNext() && newpif.hasNext() ) {			
			pIndex.setIndex(pif.index());
			pif.inc();
			//final E ret=(E)pif.next();
			if (pIndex.in(offset, span, ord)) {
				pif.dec();
				//System.out.print(pIndex.index()+","+ret+",");
				newpif.put(pif.next());
				//newpif.put(ret);
			}
			
		}
		if (trim)
			newdims=trimDims(newdims); 
	 
		return new PixelCube<E, I>(newdims,newpixels);
		
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return null;
		/* */
	}
	
	/*
	 * reduces extra dimensionality
	 */
	
	public int[] trimDims(int[] dims) {
		int cnt=0;
		for (int d:dims) {
			if (d>1) cnt++;
		}
		int[] newdims=new int[cnt];
		cnt=0;
		for (int d:dims) {
			if (d>1) {
				newdims[cnt]=d;
				cnt++;
			}
		}
		return newdims;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public PixelCube<E,I> swap(int[] ord) {
		if (ord.length!=2) throw new IllegalArgumentException("dimensions do not match 2");
		final int x=ord[0];
		final int y=ord[1];
		
		if (dim[x]!=dim[y])
			throw new IllegalArgumentException("swapped dimensions are not equal "+ dim[x]+" " + dim[y]);
		//System.out.println("x "+x+" "+"y "+y);
		int[] newdim=new int[n];
		System.arraycopy(dim, 0, newdim, 0, n);
		
		newdim[x]=dim[y];
		newdim[y]=dim[x];

		final Access<E> access2 =Access.cloneAccess(access);
		
	
		final int index=pIndex.index();
		
		for (int i=0; i<size; i++) {
			pIndex.setIndex(i);			
			int[] coords=pIndex.getCoordinates();
			
			final int u= coords[x];
			final int v= coords[y];
			coords[x]=v;
			coords[y]=u;
			//final Pair<int[],E> p=Pair.of(coords,access.element(i));
			//System.out.println(p);
			access2.putV(coords,access.element(i));
		}
		pIndex.setIndex(index);
		return new PixelCube<E,I>(newdim, access2.getArray(), type, (Class<I>)indexingType);
		
	}

	public E element(int idx) {
		return access.element(idx);
	}
	

	public E element(int[] coords) {
		return access.element(coords);
	}

	@Override
	public int getNDimensions() {
		return n;
	}

	@Override
	public int index() {		 
		return pIndex.index();
	}
 
	public int[] getDimensions() {
		return dim;
	}
	
	public void reshapeTo(int [] newdims) {
		int prod=1;
		for (int p:newdims)
			prod*=p;
		if (prod!=size)
			throw new IllegalArgumentException("Size of element array does not match: " + prod +" " +size);
		else {
			dim=newdims;
			blockSize=dim[0];
			n=dim.length;
			pIndex.reshape(dim);
		}
	}
	
	public int[] getCoords(int idx) {
		pIndex.setIndex(idx);
		return pIndex.getCoordinates();
	}
	
	
	public boolean contains (I ind) {
		return ind.contains(this.dim);		 
	}	
	
	
	public void setIterationPattern(int ip) {
		iterPattern=ip;
	}
	

	public void setBlockSize(int bs) {
		blockSize=bs;
	}
 
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer(2000);
		RasterForwardIterator<E> iter= new RasterForwardIterator<E>(pixels);
		
		while (iter.hasNext()) {
			sb.append(iter.next());
			sb.append(" ");
		}
		return sb.toString();
	}

	public int[] getDir() {
		return dir;
	}

	public void setDir(int[] dir) {
		this.dir = dir;
	}

	@Override
	public IndexedIterator<E> iterator() {		
		//System.out.print(iterPattern);
		switch (iterPattern) {
			case IP_SINGLE+IP_FWD: return new RasterForwardIterator<E>(pixels);
			case IP_SINGLE+IP_BCK: return new RasterBackwardIterator<E>(pixels);
			case IP_SINGLE+IP_FWD+IP_BCK:  return new RasterRandomIterator<E>(pixels);
			case IP_SINGLE+IP_FWD + IP_PRIM:  try {
												return ArrayIterator.rawIterator(pixels);
												} catch (UnsupportedTypeException e) {
													e.printStackTrace();
												}
			case IP_SINGLE+IP_DIR+IP_BCK: {
				PixelDirBackwardIterator<E> pdfi= new PixelDirBackwardIterator<E> (dir, dim);
				pdfi.setPixels(pixels);
				return pdfi;
				}
			
			case IP_SINGLE+IP_DIR+IP_FWD: {
				PixelDirForwardIterator<E> pdfi= new PixelDirForwardIterator<E> (dir, dim);
				pdfi.setPixels(pixels);
				return pdfi;
				}
			
			case IP_SINGLE+IP_DIRZ+IP_FWD: {
				PixelZIterator<E> pdz=new PixelZIterator<E>( dir,  dim);
				pdz.setPixels(pixels);
				return pdz;		
			}
			
			
			case IP_BLOCK +IP_FWD:
			case IP_BLOCK +IP_BCK:
			case IP_BLOCK +IP_FWD+IP_BCK: //return (IndexedIterator) new RasterBlockIterator<E>(blockSize,pixels);
			
			default: return new RasterRandomIterator<E>(pixels);
		}
		
	}
	
	public BlockAccess iteratorBlock() {
		final VectorCursor<E> iter = new VectorCursor<E>(access, pIndex);
		iter.setDirection(dir[0]);
		return (BlockAccess) iter; 	
	}
	
	public FunctionIterator<E> functIterator (SimpleFunction<E> funct) {
		switch (iterPattern) {
			case IP_SINGLE+IP_PRIM:
			case IP_SINGLE+IP_FWD + IP_PRIM:  try {
				return ArrayIterator.functIterator(pixels, funct);
				} catch (UnsupportedTypeException e) {
					e.printStackTrace();
				}
			case IP_SINGLE:
			case IP_SINGLE+IP_FWD: {
				return new RasterFunctIterator<E>(pixels, funct);
				}
			default: return new RasterFunctIterator<E>(pixels, funct);
		}
	}
	
	public VectorCursor<E> getBlockIterator() {
		return new VectorCursor<E>(access, pIndex);
	}
	
	
	@Override
	public int[] coordinates() {
		return pIndex.getCoordinates();
	}
	
	/*
	 * 
	 */
	
	/*public static abstract class Index<I extends BaseIndex> {
		
		public void set (PixelCube<?, ?> pc) {
			final List<Class<?>> clist=Util.getTypeArguments(Index.class, getClass());
			final Class<?> c=  clist.get(0);
			//System.out.println(c.getCanonicalName());
			pc.indexing( c);
		}
	}*/

	

} // end class
