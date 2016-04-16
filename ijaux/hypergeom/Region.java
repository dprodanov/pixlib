/**
 * 
 */
package ijaux.hypergeom;

import java.util.ArrayList;

import ijaux.Util;
import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import dsp.BoundaryCondition;
import ijaux.funct.AbstractElementFunction;
import ijaux.funct.ElementFunction;
import ijaux.funct.RegionTransformer;
import ijaux.hypergeom.convolution.Kernel;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.GridIndex;
import ijaux.hypergeom.index.Indexing;
import ijaux.hypergeom.morphology.StructureElement;
import ijaux.iter.array.ArrayIterator;
import ijaux.iter.dir.VectorCursor;


/*
 * @author Dimiter Prodanov
 */

public class Region<E extends Number> extends PixelCube<E, BaseIndex>  {
	
	protected VectorCube<E> ve;
	protected ShapeCube<?> scube;

	protected ZonalProcessor zproc;

	ElementFunction<?,E> elementFnct;

	
	BoundaryCondition<int[]> bc;
	
	protected ArrayList<ElementFunction<?,E>> flist=new ArrayList<ElementFunction<?,E>>();
	
	public Region(PixelCube<E, ?> pc, VectorCube<E> vex) {
		super(pc.dim, pc.pixels, pc.type, BaseIndex.class);
		if (pc.getType()!=vex.getType())
			throw new IllegalArgumentException ("types mismatch");
		
		if ( vex.getNDimensions()!= n)
			throw new IllegalArgumentException ("dimensions mismatch");
		// getting the Bounding box
		ve=vex;
		
		zproc=new ZonalProcessor(access);
	}

	public Region(PixelCube<E, ?> pc, StructureElement<E> se) {
		super(pc.dim, pc.pixels, pc.type, BaseIndex.class);
		if (pc.getType()!=se.getType())
			throw new IllegalArgumentException ("types mismatch: " + pc.getType()+" "+ se.getType());
		// getting the Bounding box
		se.getDimensions();
		ve=se.getVect();
		zproc=new ZonalProcessor(access);
	}
	
	@SuppressWarnings("unchecked")
	public<N extends Number> Region(PixelCube<E, ?> pc, Kernel<N,?> kern) {
		super(pc.dim, pc.pixels, pc.type, BaseIndex.class);
			// getting the Bounding box
		kern.getDimensions();
		ve=(VectorCube<E>) kern.getVect();
		zproc=new ZonalProcessor(access);
	}
	
	public Region(PixelCube<E, ?> pc, int[] ord, int[] origin, VectorCube<E> vex) {
		super(pc.dim, BaseIndex.class);	 
		if (pc.getType()!=vex.getType())
			throw new IllegalArgumentException ("types mismatch");
		PixelCube<E, ?> pcaux=projectSubspace(ord, origin, pc.dim, true);
		pixels=pcaux.pixels;
		dim=trimDims(dim);
		// getting the Bounding box
		
		ve=vex.projectSubspace(ord, origin, pc.dim);
		zproc=new ZonalProcessor(access);
	}

	public Region<E> clone() {
		PixelCube<E,BaseIndex> pc=super.clone();
		return new Region<E>(pc, ve);
	}
	
	public ZonalProcessor getProcessor() {
		return zproc;
	}
		
	 
	public VectorCube<E> getVectorCube() {
		return ve;
	}

	public ElementFunction<?, E> getElementFnct() {
		return elementFnct;
	}


	public void setElementFnct(ElementFunction<?, E> elementFnct) {
		this.elementFnct = elementFnct;
	}

	public boolean addFunct(ElementFunction<?, E> f) {
		return flist.add(f);
	}
	
	public boolean removeFunct(ElementFunction<?, E> f) {
		return flist.remove(f);
	}
	
	@SuppressWarnings("unchecked")
	public ElementFunction<?, E>[] getFunctArray() {
		final int n=flist.size();
		
		ElementFunction<?, E>[] ret=new ElementFunction[n];
		int cnt=0;
		for (ElementFunction<?, E> f:flist) {
			ret[cnt++]=f;
		}
		return ret;
	}
	
	public void resetFunctArray(){
		flist.clear();
	}

	public void setElement(StructureElement<E> ase ) {
		ve = ase.getVect();
	}
	  
	
	/*public Region<E> processBy(ElementFunction<?,E> elementFnct) {
		setIndexing(BASE_INDEXING);
		setIterationPattern(IP_DEFAULT);
 
		ElementFunction<?, E> auxf=getElementFnct();
		setElementFnct(elementFnct);
		Region<E> out=new Region<E>(this, ve);
		out.setIterationPattern(IP_DEFAULT);
		out.setIndexing(BASE_INDEXING);
		
		ArrayIterator<E> iter=(ArrayIterator<E>) iterator();
		ArrayIterator<E> iterout=(ArrayIterator<E>) out.iterator();
	
		while (iter.hasNext() && iterout.hasNext()) {
			final int idx=index();
			iterout.put(elementTransform(elementFnct,idx));
			iter.inc();
		}		
		setElementFnct(auxf);
		return out;
	}*/
	
	
	public void setLimbo(E value) {
		ve.limboval=value;
	}
		
	/* element function - identity
	 * array function - iterator.put
	 */
		
		public PixelCube<E, BaseIndex>  neighborhood(int idx) {
	 
			final Class<?> ctype=Util.getPrimitiveType(getType());
			//System.out.println(ve.getType() +" ve size  "+ve.dim.length +" " + ctype);			
			VectorCube<E> vc=new VectorCube<E>(ve.dim, ctype, true);
			//vc.debug=true;
			// System.out.println("wnd length "+ve.size + " type "+ vc2.getType() +" "+ctype);
				 
			for (Pair<int[], E> p:ve) {
				pIndex.setIndexAndUpdate(idx);
				int u=pIndex.translate(p.first);
				if (debug) System.out.print(" idx "+u +",");
			
				if (pIndex.isValid()) {
					final E obj=access.element(u);
					vc.addElement( p.first,obj);
				}				 
					
			}
			//System.out.print(vc2);
			return vc.toCube();
			
		}
		
		public VectorCube<E> neighborhoodV(GridIndex aind, int idx) {
			GridIndex pIndex=aind.clone();
			//final Class<?> ctype=Util.getPrimitiveType(getType());
			//System.out.println(ve.getType() +" ve size  "+ve.dim.length +" " + ctype);			
			VectorCube<E> vc=new VectorCube<E>(ve.dim, ve.getType(), true);
			//vc.debug=true;
			// System.out.println("wnd length "+ve.size + " type "+ vc2.getType() +" "+ctype);
				 
			for (Pair<int[], E> p:ve) {
				pIndex.setIndexAndUpdate(idx);
				int u=pIndex.translate(p.first);
				//if (debug) System.out.print(" idx "+u +",");
			
				if (pIndex.isValid()) {
					final E obj=access.element(u);
					vc.addElement( p.first,obj);
				} else {
					if (debug) System.out.print(" "+pIndex +",");
				}
					
			}
			//System.out.print(vc2);
			return vc;
			
		}
		
/*		public void rank (int r, int idx) {
			 
			final Class<?> ctype=Util.getPrimitiveType(getType());//Util.getTypeMapping(ve.getType());
			//System.out.println(ve.getType() +" ve size  "+ve.dim.length +" " + ctype);			
			int size=ve.size();
			try {
				final Access<E> ua=(Access<E>) Access.create(ctype, size, null);
				BaseIndex bi=ve.getBI();
				for (Pair<int[], E> p:ve) {
					pIndex.setIndex(idx);
					int u=pIndex.translate(p.first);
					if (debug) System.out.print(" idx "+u +",");
				
					if (bi.contains(pIndex.getCoordinates())) {
						//final E obj=(E)Array.get(pixels, u);
						final E obj=access.element(u);
						//if (debug) System.out.print(obj+" ");
						final E old=ua.element(r);
						if (old.floatValue()<obj.floatValue())
							ua.putE(r, obj);
					}				 
						
				}
			
				//System.out.print(vc2);
				
			} catch (UnsupportedTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/
	
		//mode: min, max, sort; median, window
		public E elementTransform(int idx) {
			return zproc.elementTransform(ve, elementFnct, idx);
		}
		
		/*
		 * transforms a pixel in the local neighborhood specified by ind using a constant 
		 *  boundary condition.
		 */
		/*@SuppressWarnings("unchecked")
		public E elementTransform(int idx) {
			 
			for (Pair<int[], E> p:ve) {	
				pIndex.setIndex(idx);
				//System.out.println(idx+" ["+pIndex +"] "); 
				int u=pIndex.translate(p.first);
				//System.out.print(" ["+pIndex +"],");
			
				try {		
					if (pIndex.isValid()) {
						final E elem=access.element(u); 
						///System.out.print(" g="+elem +" ");
						elementFnct.transform(elem, p.second);
					}
					else{
						// outside strategy		 
						if (debug) System.out.println("\n outside ["+pIndex +"] " + ve.limboval); 
						elementFnct.transform(ve.limboval, p.second);
					}	
				} catch (Exception e) {
					System.out.println(e+": "+pIndex);
					System.out.println(ve.limboval +" "+ p.second);
					elementFnct.transform(ve.limboval, p.second);
					//e.printStackTrace();
				}
				//cnt++;			
			}
			
			return (E)elementFnct.getOutput();
			
		}*/
		
		/*
		 * transforms a pixel in the local neighborhood specified by ind using a constant 
		 *  boundary condition.
		 */
		@SuppressWarnings("unchecked")
		public E elementTransform(ElementFunction<?, E> elementFnct, int idx) {
			 
			for (Pair<int[], E> p:ve) {	
				pIndex.setIndex(idx);
				//System.out.println(idx+" ["+pIndex +"] "); 
				int u=pIndex.translate(p.first);
				//System.out.print(" ["+pIndex +"],");
			
				try {		
					if (pIndex.isValid()) {
						final E elem=access.element(u); 
						///System.out.print(" g="+elem +" ");
						elementFnct.transform(elem, p.second);
					}
					else{
						// outside strategy		 
						if (debug) System.out.println("\n outside ["+pIndex +"] " + ve.limboval); 
						elementFnct.transform(ve.limboval, p.second);
					}	
				} catch (Exception e) {
					System.out.println(e+": "+pIndex);
					System.out.println(ve.limboval +" "+ p.second);
					elementFnct.transform(ve.limboval, p.second);
					//e.printStackTrace();
				}
				//cnt++;			
			}
			
			return (E)elementFnct.getOutput();
			
		}
		
		public E elementTransform(int idx, int[] coords) {
			return zproc.elementTransform(ve, elementFnct, idx, coords);				
		}
		
		//TODO
		public < T> void lineTrnasform(PixelCube<E,?> pc, int dir) {
			VectorCursor<T> block=(VectorCursor<T>) pc.iteratorBlock();
			block.setDirection(dir);
			while (block.hasNext()) {
				T blk=block.next();
				
			}
		}
		
		@SuppressWarnings("unchecked")
		public E elementTransform(ElementFunction<?, E> elementFnct, int idx, int[] coords) {
			 
			final Pair<int[], E> q=ve.element(coords);
			
			pIndex.setIndexAndUpdate(idx);
			int u=pIndex.translate(q.first);
			final E w=access.element(u);
 
			for (Pair<int[], E> p:ve) {	
				pIndex.setIndex(idx);
				//System.out.println(idx+" ["+pIndex +"] "); 
				u=pIndex.translate(p.first);
				
				//System.out.print(" ["+pIndex +"],");
				//pIndex.getCoordinates();
				try {		
			 		if (pIndex.isValid()) {
						final E elem=access.element(u); 
						///System.out.print(" g="+elem +" ");
						elementFnct.transform(elem, w);
					}
					else{
						// outside strategy		 
						if (debug) System.out.println("\n outside ["+pIndex +"] " + ve.limboval); 
						elementFnct.transform(ve.limboval, w);
					}	
				} catch (Exception e) {
					System.out.println(e+": "+pIndex);
					System.out.println(ve.limboval +" "+ w);
					//elementFnct.transform(ve.limboval, w);
					//e.printStackTrace();
				}
				//cnt++;			
			}
			return (E)elementFnct.getOutput();
			
		}
 		

		public E elementTransformCond(  int idx) {
			return  zproc.elementTransformCond(ve, elementFnct,  bc,  idx) ;			
		}
		
		 
		
		public BoundaryCondition<int[]> getBoundaryCondition() {
			return bc;
		}

		public void setBoundaryCondition(BoundaryCondition<int[]> bc) {
			this.bc = bc;
		}
	 
		public void setVectorCube(VectorCube<E> ve) {
			if (getType()!=ve.getType())
				throw new IllegalArgumentException ("types mismatch");
			this.ve = ve;
		}

		public void setShapeCube(ShapeCube<?> scube) {			
			this.scube = scube;
		}

		
/*
 *  Boundary conditions implementations	
 */

/*public class MirrorCondition implements BoundaryCondition<int[]> {
	Indexing<int[]> aind;
	int[] scoords;
	
	public MirrorCondition(Indexing<int[]> sind) {
		aind=sind;
		scoords=new int[sind.getNDim()];
	}
	
	@Override
	public int[] getCoordsAt(int[] coords) {	 
		for (int i=0; i<coords.length;i++) {
			scoords[i]=coords[i];
			final int a=Math.abs(coords[i]) % dim[i]+1;
			if (coords[i]>=dim[i]) {					 
				scoords[i]=   dim[i] - a;
			} 
			
			if (coords[i] < 0) {
				scoords[i]=    a-1;
			}
		}
		return scoords;
	}

	@Override
	public int getIndexAt(int index) {
		int sind=aind.index();
		aind.setIndexAndUpdate(index);
		int[] coords=aind.getCoordinates();
		coords=getCoordsAt(coords);
		int ret=aind.indexOf(coords);
		aind.setIndexAndUpdate(sind);
		return ret;
	}
	
	@Override
	public String toString() {
		return "MirrorCondition";
	}
	
} // end class
*/
/*public class TranslatedCondition implements BoundaryCondition<int[]> {
	Indexing<int[]> aind;
	int[] scoords;
	
	public TranslatedCondition(Indexing<int[]> sind) {
		aind=sind;
		scoords=new int[sind.getNDim()];
	}
	
	@Override
	public int[] getCoordsAt(int[] coords) {	 
		for (int i=0; i<coords.length;i++) {
			scoords[i]=coords[i];
			//final int a=coords[i]% dim[i];
			if (coords[i]>dim[i])
				scoords[i]=coords[i]-dim[i];
			if (coords[i]<0)
				scoords[i]=coords[i]+dim[i];
		}
		return scoords;
	}

	@Override
	public int getIndexAt(int index) {
		int sind=aind.index();
		aind.setIndexAndUpdate(index);
		int[] coords=aind.getCoordinates();
		coords=getCoordsAt(coords);
		int ret=aind.indexOf(coords);
		aind.setIndexAndUpdate(sind);
		return ret;
	}

	@Override
	public String toString() {
		return "TranslatedCondition";
	}
	 
} // end class
*/
/*public class StaticCondition implements BoundaryCondition<int[]> {
	Indexing<int[]> aind;
	int[] scoords;
	
	public StaticCondition(Indexing<int[]> sind) {
		aind=sind;
		scoords=new int[sind.getNDim()];
	}
	
	@Override
	public int[] getCoordsAt(int[] coords) {	 
		for (int i=0; i<coords.length;i++) {
			scoords[i]=coords[i];
			if (coords[i]>=dim[i])
				scoords[i]=dim[i]-1;
			if (coords[i]<0)
				scoords[i]=0;
		}
		return scoords;
	}

	@Override
	public int getIndexAt(int index) {
		int sind=aind.index();
		aind.setIndexAndUpdate(index);
		int[] coords=aind.getCoordinates();
		coords=getCoordsAt(coords);
		int ret=aind.indexOf(coords);
		aind.setIndexAndUpdate(sind);
		return ret;
	}
	 
	@Override
	public String toString() {
		return "StaticCondition";
	}
} // end class
*/
}
