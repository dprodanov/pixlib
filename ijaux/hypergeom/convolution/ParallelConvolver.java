package ijaux.hypergeom.convolution;

import java.util.concurrent.atomic.AtomicInteger;

import ijaux.Constants;
import ijaux.datatype.ElementOutput;
import ijaux.datatype.access.Access;
import ijaux.funct.ElementFunction;
import ijaux.hypergeom.BoundaryCondition;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.Region;
import ijaux.hypergeom.VectorCube;
import ijaux.hypergeom.ZonalProcessor;
import ijaux.parallel.ForkJoinTask;
import ijaux.parallel.ThreadedProcessor;

public class ParallelConvolver<E extends Number> 
extends ThreadedProcessor implements Constants{
public final static String version="1.0";
	
	private BoundaryCondition<int[]> bc;

	protected Kernel<E, ? > kernel;
	protected Region<E> region;
	protected Convolution<E > conv;
	
	private boolean convorder=false;
	
	public ParallelConvolver(Kernel<E, ? >  kern ) {
		  this (kern, false);
	}
	
	public ParallelConvolver(Kernel<E, ? >  kern, boolean flip) {
		  kernel=kern;
		  conv= new Convolution<E>(kernel);
		  if (flip)
			  toggleOrder();
	}
	
	public <N> ParallelConvolver(Region<E> reg ) {
		 this(reg,false);
	}
	
	public <N> ParallelConvolver(Region<E> reg, boolean flip ) {
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
	public <N extends Number> Region<N> convolveParallel(Region<N> reg){
		 
		 	reg.setIterationPattern(IP_DEFAULT);
			reg.setIndexing(BASE_INDEXING);
			reg.setElementFnct((ElementFunction<?, N>) conv);
			if (bc==null) {
				throw new IllegalArgumentException("no boundary condition specified");
			}
			reg.setBoundaryCondition(bc);
			 
			Region<N> out=new Region<N>((PixelCube<N,?>)reg.clone(), kernel);
 
			
			final ZonalProcessor zp=reg.getProcessor();
			final VectorCube<N> vc=(VectorCube<N>) kernel.getVect();
			
			final ElementFunction<?,N> fnct=reg.getElementFnct();
			final Access<N> accessOut=out.getAccess();
				final int n=accessOut.size()[0];
				for (int i=0; i<n; i++) {
					final N elem=zp.elementTransformCond(vc,fnct, bc,i);
					accessOut.putE(i, elem);
				}
	 
			
			return out;
		 
	 } //
	private Region<E> out;
	
	private class ConvTask implements ForkJoinTask, Runnable{
		private Access<?> accessin=null;
		AtomicInteger id=new AtomicInteger(-1);
		private int offset=0;
		private int limit=-1;
		//private int overlap=0;
		boolean _debug=false;
		
		private Access<E> accessOut=null;
		
		public ConvTask(Region<E> reg ) {
			reg.setIterationPattern(IP_DEFAULT);
			reg.setIndexing(BASE_INDEXING);
			reg.setElementFnct((ElementFunction<?, E>) conv);
			if (bc==null) {
				throw new IllegalArgumentException("no boundary condition specified");
			}
			reg.setBoundaryCondition(bc);
			 
			out=new Region<E>((PixelCube<E,?>)reg.clone(), kernel);
			accessOut=out.getAccess();
		}
		
		@Override
		public void run() {
			if (_debug)
				System.out.println("processing id "+id);

			if (_debug)
				System.out.println("offset "+offset+" limit "+limit);
				 
			for (int ind= offset; ind<limit; ind++ ) {
				final double value=accessin.elementDouble(ind);
				accessOut.putDouble(ind, value);
			}
		}

		@Override
		public void fork(int _id) {
			id.set(_id);
			if (_debug)	
				System.out.println("forking "+id);

			int size=accessin.size()[0]*accessin.size()[1]; // PagedAccess
			if (size==0) // Access
				size=accessin.size()[0];
			int csize=size/n_cpus;

			//System.out.println("size "+size+" csize "+csize);
			offset=_id*csize;
			limit=offset+ csize; 
			if (_id== n_cpus-1)
				limit=size;
		
		}

		@Override
		public void join() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
