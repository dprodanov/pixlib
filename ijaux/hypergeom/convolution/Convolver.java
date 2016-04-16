package ijaux.hypergeom.convolution;

import ijaux.Constants;
import ijaux.Util;
import ijaux.datatype.access.Access;
import dsp.BoundaryCondition;
import ijaux.funct.ElementFunction;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.Region;
import ijaux.hypergeom.VectorCube;
import ijaux.hypergeom.ZonalProcessor;
import ijaux.hypergeom.index.GridIndex;
import ijaux.iter.array.ArrayIterator;

 
/*
 * @author Dimiter Prodanov
 */
public class Convolver<E extends Number>  implements   Constants {
	
	public final static String version="2.0";
	
	private BoundaryCondition<int[]> bc;

	protected Kernel<E, ? > kernel;
	protected Region<E> region;
	protected Convolution<E > conv;
	
	private boolean convorder=false;
	
	public Convolver(Kernel<E, ? >  kern ) {
		  this (kern, false);
	}
	
	public Convolver(Kernel<E, ? >  kern, boolean flip) {
		  kernel=kern;
		  conv= new Convolution<E>(kernel);
		  if (flip)
			  toggleOrder();
	}
	
	public <N> Convolver(Region<E> reg ) {
		 this(reg,false);
	}
	
	public <N> Convolver(Region<E> reg, boolean flip ) {
		 kernel=new Kernel<E,N>(reg.getVectorCube());
		 region=reg;		 
		 conv= new Convolution<E>(kernel);
		 if (flip)
			  toggleOrder();
	}
	
	public void toggleOrder() {
		kernel.flip();
		convorder=true;
	}
	
	public boolean isFlipped() {
		return convorder;
	}
	
	@SuppressWarnings("unchecked")
	private <N extends Number> void  setLimbo(Region<N> reg) {
		
		if (reg.getType()== byte.class) {
			final Byte gmax=Byte.valueOf((byte) 0);
			System.out.println("gmax " + (gmax.intValue() & byteMask));
			reg.setLimbo((N) gmax);
			return;
		}
		if (reg.getType()== short.class) {
			final Short gmax=Short.valueOf((short) 0);
			System.out.println("gmax " + (gmax.intValue() & shortMask));
			reg.setLimbo((N) gmax);
			return;
		}
		if (reg.getType()== float.class) {	
			 
			final Float gmin=   Float.valueOf(  0);
			System.out.println("gmax " + gmin.floatValue());
			reg.setLimbo( (N) gmin);
			return;
		}
		 
	}
	
	private boolean useIter=false;
	
	public void useIter() {
		useIter=true;
	}

	public void useAccess() {
		useIter=false;
	}
	
	 
	public BoundaryCondition<int[]> getBoundaryCondition() {
		return bc;
	}

	public void setBoundaryCondition(BoundaryCondition<int[]> bc) {
		this.bc = bc;
	}
	
	@SuppressWarnings("unchecked")
	public <N extends Number> Region<N> convolve(Region<N> reg){
		 
		 	reg.setIterationPattern(IP_DEFAULT);
			reg.setIndexing(BASE_INDEXING);
			reg.setElementFnct((ElementFunction<?, N>) conv);
			if (bc==null) {
				throw new IllegalArgumentException("no boundary condition specified");
			}
			reg.setBoundaryCondition(bc);
			setLimbo(reg);
			Region<N> out=new Region<N>((PixelCube<N,?>)reg.clone(), kernel);
			out.setIterationPattern(IP_DEFAULT);
			out.setIndexing(BASE_INDEXING);
			
			final ZonalProcessor zp=reg.getProcessor();
			final VectorCube<N> vc=(VectorCube<N>) kernel.getVect();
			vc.trim();
			
			final ElementFunction<?,N> fnct=reg.getElementFnct();
			
			if (useIter) {
				ArrayIterator<N> iter=(ArrayIterator<N>) reg.iterator();
				ArrayIterator<N> iterout=(ArrayIterator<N>) out.iterator();
			
				while (iter.hasNext() && iterout.hasNext()) {
					final int idx=iter.index();
					//System.out.print(idx+" ");
					final N elem=zp.elementTransformCond(vc, fnct, bc, idx);
					iter.inc();
					iterout.put(elem);
				}
				
			} else {
				final Access<N> accessOut=out.getAccess();
				final int n=accessOut.size()[0];
				for (int i=0; i<n; i++) {
					final N elem=zp.elementTransformCond(vc,fnct, bc,i);
					accessOut.putE(i, elem);
				}
			}
			
			return out;
		 
	 } //
	
	/**
	 * @param out 
	 * @param ind 
	 * @param vcoords 
	 * @param ivals 
	 * @param accessin 
	 * @param mc 
	 * 
	 */
	public void convolveFloat(final Access<?> accessin,  final GridIndex ind, 
			final int[][] vcoords, final float[] ivals, 
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
 
	@SuppressWarnings("unchecked")
	public <N extends Number, P extends Object> Region<N> convolveSeparable2(Region<N> reg, SeparableKernel<N,P> sc){
	
		System.out.println("nc "+sc.getNKernels());
		for (int i=0; i<sc.getNKernels(); i++ ) {
		 final Kernel<N,P> k=sc.getKernel(i);
		  conv= (Convolution<E>) new Convolution<N>(k);
			reg=convolve( reg);
		}
		return reg;
				
	}
	
	public <N extends Number, P extends Object> Region<N> convolveSeparable(Region<N> reg, SeparableKernel<N,P> sc){
		//reg.setIterationPattern(IP_SINGLE+IP_DIRZ+IP_FWD);
		reg.setIterationPattern(IP_DEFAULT);
		reg.setIndexing(BASE_INDEXING);
		if (bc==null) {
			throw new IllegalArgumentException("no boundary condition specified");
		}
		reg.setBoundaryCondition(bc);
		setLimbo(reg);
		
		Region<N> out=new Region<N>((PixelCube<N,?>)reg.clone(), sc);
		out.setIterationPattern(IP_DEFAULT);
		out.setIndexing(BASE_INDEXING);
		
		final ZonalProcessor zp=reg.getProcessor();
		
		//Util.printIntArray(out.getDimensions());
		//Util.printIntArray(reg.getDimensions());
		//System.out.println("nc "+sc.getNKernels());
		for (int i=0; i<sc.getNKernels(); i++ ) {
			Kernel<N,P> kern=sc.getKernel(i);
			reg=cnv(zp,reg, kern, out);
			reg.setBoundaryCondition(bc);
			setLimbo(reg);

		}
		out=reg;

		return out;
	
	}
	
	private  <N extends Number>   Region<N>  cnv(ZonalProcessor zp, Region<N> regin, Kernel<N,?> sc, Region<N> regout) {
		regin.setElementFnct((ElementFunction<?, N>) new Convolution<N> (sc));
		//System.out.println("cnv:");
		//Util.printIntArray(sc.getDimensions());
		final int[] dir={0,1};
		regin.setDir(dir);
		regout.setDir(dir);
		final VectorCube<N> vc=sc.getVect();//regin.getVectorCube();
		final ElementFunction<?,N> fnct=regin.getElementFnct();
		final BoundaryCondition<int[]> bc=regin.getBoundaryCondition();
				
		if (useIter) {
			ArrayIterator<N> iterin=(ArrayIterator<N>) regin.iterator();
			ArrayIterator<N> iterout=(ArrayIterator<N>) regout.iterator();

			while (iterin.hasNext() && iterout.hasNext()) {
				final int idx=iterin.index();
				//System.out.print(idx+" ");
				final N elem=zp.elementTransformCond(vc, fnct, bc,  idx);
				iterin.inc();
				iterout.put(elem);
			}

		} else {
			final Access<N> accessOut=regout.getAccess();
			final int n=accessOut.size()[0];
			for (int i=0; i<n; i++) {
				final N elem=zp.elementTransformCond(vc, fnct, bc,  i);
				accessOut.putE(i, elem);
			}
		}

		 return regout;
	}
	
	
	
}
