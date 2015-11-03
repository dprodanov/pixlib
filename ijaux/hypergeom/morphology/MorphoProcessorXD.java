/**
 * 
 */
package ijaux.hypergeom.morphology;


import ijaux.Constants;
import ijaux.Util;
import ijaux.datatype.Pair;
import ijaux.datatype.Typing;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.*;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.GridIndex;
import ijaux.iter.array.ArrayIterator;
import ijaux.stats.CubeHistogram;
import ijaux.stats.HyperCubeHistogram;
/**
 * @author dprodanov
 *
 */
public class MorphoProcessorXD<E extends Number>
implements  Morphology<Region<E>>, Constants, Typing {
	
	public final static String version="1.1.7";
	
	StructureElement<?> se;
	Region<?> region;
	Class<?> type;
	
	boolean scaled=false;
 
	public MorphoProcessorXD(StructureElement<?> ase ) {	
		 se=ase;
		 type=se.getType();

	}

	@SuppressWarnings("unchecked")
	public MorphoProcessorXD(Region<?> reg ) throws UnsupportedTypeException {
		 se=new StructureElement<E>((VectorCube<E>) reg.getVectorCube());
		
		 region=reg;
		 type=reg.getType();
		 CubeHistogram<E> ch = new CubeHistogram<E>((PixelCube<E, ?>) region);
		 //System.out.println(ch);
		 //System.out.println("min max :"+se.getMinMax());
		 se.setMinMax(ch);
		 scaled=true;
		//System.out.println("scaling ... \n " +se);
	}
	
	@SuppressWarnings("unchecked")
	private  void  setLimboErosion(Region<E> reg, CubeHistogram<E> ch) {
		
		if (reg.getType()== byte.class) {
			final Byte gmax=Byte.valueOf((byte) -1);
			System.out.println("limbo gmax " + (gmax.intValue() & byteMask));
			reg.setLimbo((E) gmax);
			return;
		}
		if (reg.getType()== short.class) {
			final Short gmax=Short.valueOf((short) -1);
			System.out.println("limbo gmax " + (gmax.intValue() & shortMask));
			reg.setLimbo((E) gmax);
			return;
		}
		if (reg.getType()== float.class) {
		
			Pair<Float,Float> p=(Pair<Float, Float>) ch.getMinMax();
			final Float gmax=  p.second;
			System.out.println("limbo gmax " + gmax);
			reg.setLimbo( (E) gmax);
			return;
		}
		 
	}

	@SuppressWarnings("unchecked")
	private  void setLimboDilation(Region<E> reg, CubeHistogram<E> ch) {
		
		if (reg.getType()== byte.class) {
			final Byte gmin=Byte.valueOf((byte) 0);
			System.out.println("limbo gmin " + (gmin.intValue() & byteMask));
			reg.setLimbo((E) gmin);
			return;
		}
		if (reg.getType()== short.class) {
			final Short gmin=Short.valueOf((short) 0);
			System.out.println("limbo gmin " + (gmin.intValue() & shortMask));
			reg.setLimbo((E) gmin);
			return;
		}
		if (reg.getType()== float.class) {	
			Pair<Float,Float> p=(Pair<Float, Float>) ch.getMinMax();
			final Float gmin=  p.first;
			System.out.println("limbo gmin " + gmin);
			reg.setLimbo( (E) gmin);
			return;
		}
		 
	}
	
	public void setStructureElement(StructureElement<E> ase) {
		se=ase;
	}

	public  Region<E> close2(Region<E> reg) {
		return erode(dilate(reg));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public  Region<E> close(Region<E> reg) {
		//return erode(dilate(reg));

		try {
			CubeHistogram<E> ch = new CubeHistogram<E>((PixelCube<E,BaseIndex>)reg);

			Erosion<E> er=new Erosion<E>((StructureElement<E>) se);

			er.initFunction(ch);

			Dilation<E> dil=new Dilation<E>((StructureElement<E>) se);

			dil.initFunction(ch);


			reg.setIterationPattern(IP_DEFAULT);
			reg.setIndexing(BASE_INDEXING);
 
			Region<E> out=new Region<E>((PixelCube<E,?>)reg.clone(), (StructureElement<E>) se);
			//out.setIterationPattern(IP_DEFAULT);
			//out.setIndexing(BASE_INDEXING);

			final ZonalProcessor zp=reg.getProcessor();
			final VectorCube<E> ve=reg.getVectorCube();
			//System.out.println(ve);

			//final double limbodil=0;s
			//Pair<Number,Number> p= (Pair<Number, Number>) ch.getMinMax();
			//final double limboer=p.second.doubleValue();

			Access<E> accessOut=out.getAccess();
 
			BoundaryCondition<int[]> bc=reg.getBoundaryCondition();
			
			final int n=(int) accessOut.length();
			int offset=  Util.cumprod(se.getDimensions())+1; //(int)ve.size();
			System.out.println("offset: "+ offset);
			for (int i=0; i<n+offset; i++) {			
				if (i<n) {
					final E elem=zp.elementTransformCond(ve, dil, bc, i);
					accessOut.putE(i, elem);
				}
				if (i>=offset) {
					final int z=i-offset;
					//System.out.println("z: "+ z);
					final E elem=zp.elementTransformCond(ve, er, bc,z);
					accessOut.putE(z, elem);
				}
			}


			//} // end if

			return out;
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
		return null;

	}



	@SuppressWarnings("unchecked")
	@Override
	public Region<E> dilate(Region<E> reg) {
		CubeHistogram<E> ch;
		try {
			ch = new CubeHistogram<E>((PixelCube<E,BaseIndex>)reg);


			Dilation<E> dil=new Dilation<E>((StructureElement<E>) se);

			dil.initFunction(ch);
		 
			reg.setIterationPattern(IP_DEFAULT);
			reg.setIndexing(BASE_INDEXING);
			reg.setElementFnct(dil);
			setLimboDilation(reg, ch);

			Region<E> out=new Region<E>((PixelCube<E,?>)reg.clone(),(StructureElement<E>) se);
			out.setIterationPattern(IP_DEFAULT);
			out.setIndexing(BASE_INDEXING);
			final ZonalProcessor zp=reg.getProcessor();
			VectorCube<E> ve=reg.getVectorCube();
			//System.out.println(ve);
			BoundaryCondition<int[]> bc=reg.getBoundaryCondition();

			if (useIter) {
				ArrayIterator<E> iter=(ArrayIterator<E>) reg.iterator();
				ArrayIterator<E> iterout=(ArrayIterator<E>) out.iterator();

				while (iter.hasNext() && iterout.hasNext()) {
					int idx=iter.index();
					//System.out.print(idx+" ");
					//final E elem=zp.elementTransform(ve, dil, idx);
					final E elem=zp.elementTransformCond(ve,dil,bc,idx);
					iter.inc();
					iterout.put(elem);
				}

			} else {
				Access<E> accessOut=out.getAccess();
				final int n=accessOut.size()[0];
				for (int i=0; i<n; i++) {
					//final E elem=reg.elementTransform(dil,i);
					//final E elem=zp.elementTransform(ve, dil, i);
					final E elem=zp.elementTransformCond(ve,dil,bc,i);
					accessOut.putE(i, elem);
				}
			}

			return out;
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Region<E> rankMax(Region<E> reg) {
		CubeHistogram<E> ch;
		try {
			ch = new CubeHistogram<E>((PixelCube<E,BaseIndex>)reg);
			
			
			int ndim=reg.getNDimensions();
			
			final int[] center=new int[ndim];

			RankMax<E> dil=new RankMax<E>((StructureElement<E>) se);

			dil.initFunction(ch);

			reg.setIterationPattern(IP_DEFAULT);
			reg.setIndexing(BASE_INDEXING);
			reg.setElementFnct(dil);
			setLimboDilation(reg, ch);
			//setLimboErosion(reg, ch);
			
			Region<E> out=new Region<E>((PixelCube<E,?>)reg.clone(), (StructureElement<E>) se);
			out.setIterationPattern(IP_DEFAULT);
			out.setIndexing(BASE_INDEXING);


			if (useIter) {
				ArrayIterator<E> iter=(ArrayIterator<E>) reg.iterator();
				ArrayIterator<E> iterout=(ArrayIterator<E>) out.iterator();

				while (iter.hasNext() && iterout.hasNext()) {
					final int idx=iter.index();
					//System.out.print(idx+" ");
					final E elem=reg.elementTransform(idx, center);
					iter.inc();				 
					if (elem.intValue()==0)		
						iterout.putInt(0);
					else 
						iterout.inc();
				}

			} else {
				Access<E> accessOut=out.getAccess();
				final int n=accessOut.size()[0];
				for (int i=0; i<n; i++) {					
					final E elem=reg.elementTransform(i, center); // we return a masking element
					if (elem.intValue()==0) {
					//System.out.print(elem.intValue()+"+");
					   accessOut.putInt(i, 0);
					}
				}
			}

			return out;
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Region<E> rankMin(Region<E> reg) {
		CubeHistogram<E> ch;
		try {
			ch = new CubeHistogram<E>((PixelCube<E,BaseIndex>)reg);
			
			
			int ndim=reg.getNDimensions();
			
			final int[] center=new int[ndim];

			RankMin<E> dil=new RankMin<E>((StructureElement<E>) se);

			dil.initFunction(ch);

			reg.setIterationPattern(IP_DEFAULT);
			reg.setIndexing(BASE_INDEXING);
			reg.setElementFnct(dil);
			//setLimboDilation(reg, ch);
			setLimboErosion(reg, ch);
			
			Region<E> out=new Region<E>((PixelCube<E,?>)reg.clone(), (StructureElement<E>) se);
			out.setIterationPattern(IP_DEFAULT);
			out.setIndexing(BASE_INDEXING);


			if (useIter) {
				ArrayIterator<E> iter=(ArrayIterator<E>) reg.iterator();
				ArrayIterator<E> iterout=(ArrayIterator<E>) out.iterator();

				while (iter.hasNext() && iterout.hasNext()) {
					final int idx=iter.index();
					//System.out.print(idx+" ");
					final E elem=reg.elementTransform(idx, center);
					iter.inc();				 
					if (elem.intValue()==0)		
						iterout.putInt(0);
					else 
						iterout.inc();
				}

			} else {
				Access<E> accessOut=out.getAccess();
				final int n=accessOut.size()[0];
				for (int i=0; i<n; i++) {					
					final E elem=reg.elementTransform(i, center); // we return a masking element
					if (elem.intValue()==0) {
					//System.out.print(elem.intValue()+"+");
					   accessOut.putInt(i, 0);
					}
				}
			}

			return out;
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private boolean useIter=false;
	
	public void useIter() {
		useIter=true;
	}

	public void useAccess() {
		useIter=false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Region<E> erode(Region<E> reg) {
		try {
			CubeHistogram<E> ch = new CubeHistogram<E>((PixelCube<E,BaseIndex>)reg);
 
			Erosion<E> er=new Erosion<E>((StructureElement<E>) se);

			er.initFunction(ch);

			reg.setIterationPattern(IP_DEFAULT);
			reg.setIndexing(BASE_INDEXING);
			reg.setElementFnct(er);
			setLimboErosion(reg, ch);

			Region<E> out=new Region<E>((PixelCube<E,?>)reg.clone(), (StructureElement<E>) se);
			//out.setIterationPattern(IP_DEFAULT);
			//out.setIndexing(BASE_INDEXING);

			final ZonalProcessor zp=reg.getProcessor();
			VectorCube<E> ve=reg.getVectorCube();
			//System.out.println(ve);
			
			BoundaryCondition<int[]> bc=reg.getBoundaryCondition();
			//System.out.println(bc);
			if (useIter) {
				
				
				ArrayIterator<E> iter=(ArrayIterator<E>) reg.iterator();
				ArrayIterator<E> iterout=(ArrayIterator<E>) out.iterator();

				while (iter.hasNext() && iterout.hasNext()) {
					int idx=iter.index();
					//System.out.print(idx+" ");
	
					//final E elem=zp.elementTransform(ve, er, idx);
					final E elem=zp.elementTransformCond(ve, er, bc,idx);
					iter.inc();
					iterout.put(elem);
				}

			} else {
				Access<E> accessOut=out.getAccess();
				final int n=accessOut.size()[0];
				for (int idx=0; idx<n; idx++) {
		
					//System.out.print(" i="+i+",");
					//final E elem=zp.elementTransform(ve, er, idx);
					final E elem=zp.elementTransformCond(ve, er, bc,idx);
					
					accessOut.putE(idx, elem);
				}
			}

			return out;
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
		return null;

	}

	public Region<E> open2(Region<E> reg) {
		return dilate(erode(reg));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Region<E> open(Region<E> reg) {
	
		try {
			CubeHistogram<E> ch = new CubeHistogram<E>((PixelCube<E,BaseIndex>)reg);

			Erosion<E> er=new Erosion<E>((StructureElement<E>) se);

			er.initFunction(ch);

			Dilation<E> dil=new Dilation<E>((StructureElement<E>) se);

			dil.initFunction(ch);


			reg.setIterationPattern(IP_DEFAULT);
			reg.setIndexing(BASE_INDEXING);
 
			Region<E> out=new Region<E>((PixelCube<E,?>)reg.clone(), (StructureElement<E>) se);


			final ZonalProcessor zp=reg.getProcessor();
			VectorCube<E> ve=reg.getVectorCube();
			//System.out.println(ve);

			//final double limbodil=0;s
			//Pair<Number,Number> p= (Pair<Number, Number>) ch.getMinMax();
			//final double limboer=p.second.doubleValue();

			Access<E> accessOut=out.getAccess();
 
			BoundaryCondition<int[]> bc=reg.getBoundaryCondition();
			
			final int n=(int) accessOut.length();
			int offset= 2* Util.cumprod(se.getDimensions()); //(int)ve.size();
			System.out.println("offset: "+ offset);
			for (int i=0; i<n+offset; i++) {			
				if (i<n) {
					final E elem=zp.elementTransformCond(ve, er, bc, i);
					accessOut.putE(i, elem);
				}
				if (i>=offset) {
					final int z=i-offset;
					//System.out.println("z: "+ z);
					final E elem=zp.elementTransformCond(ve, dil, bc,z);
					accessOut.putE(z, elem);
				}
			}


			//} // end if

			return out;
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * @param out 
	 * @param ind 
	 * @param vcoords 
	 * @param ivals 
	 * @param accessin 
	 * @param mc 
	 * 
	 */
	public void minmaxFloat(final Access<?> accessin,  final GridIndex ind, final int[][] vcoords, final float[] ivals, 
			final BoundaryCondition<int[]> mc, final Access<?> out ) {
		
		final int[] dims=ind.getDim();
		final int sz=vcoords.length;
		ind.setIndexAndUpdate(0);
		int[] ct=dims.clone();
		final int limit=accessin.size()[0];
		
		for (int i=0; i<limit; i++) {		
			float sum=0;		
			final int[] coords=ind.getCoordinates();
		
			// iteration over kernel
			for (int k=0; k<sz; k++) {		
				// adding coordinates
				for (int u=0; u<ct.length; u++) {
					ct[u]= (coords[u] +vcoords[k][u]) ;
				}
				// checking for validity
				boolean valid=true;	
				for (int a=0; a<dims.length; a++) {
					valid= valid &&(ct [a] >=0) && (ct [a] <dims[a]);	
				}
				
				int u;
				try {											 			
					if (valid ) {
						u=ind.indexOf(ct);
					}
					else{
						final int[] coords_aux=mc.getCoordsAt(coords);
						u=ind.indexOf(coords_aux);							
					}	// end else
					final float elem=accessin.elementFloat(u);			
					sum+=elem* ivals[k];
					
				} catch (Exception e) {
					//if (debug) {
					System.out.println(e+": "+ind +" ");
					Util.printIntArray(vcoords[k]);			
					//}
				} // end catch		

			} // end for

			out.putFloat(i, sum);
			ind.inc();
		} // end for
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public boolean eq(Class<?> c) {
		return type==c;
	}

	

}
