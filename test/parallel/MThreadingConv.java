package test.parallel;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.Constants;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.datatype.access.PrimitiveAccess;
import ijaux.datatype.oper.Op;
import dsp.*;
import dsp.BCFactory.TranslatedCondition;
import ijaux.funct.ElementFunction;
import ijaux.funct.PrimitiveElementFunction;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.Region;
import ijaux.hypergeom.VectorCube;
import ijaux.hypergeom.convolution.Kernel;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.BaseIndex2D;
import ijaux.hypergeom.index.GridIndex;
import ijaux.parallel.ConcurentProcessor;
import ijaux.parallel.ForkJoinJob;


import java.util.concurrent.atomic.AtomicInteger;

 
public class MThreadingConv implements Constants {

	 ConcurentProcessor<Boolean> cproc=new ConcurentProcessor<Boolean>();
	 int n_cpus= ConcurentProcessor.n_cpus;
	
	
	public MThreadingConv() {
		System.out.println("detected " + n_cpus +" CPUs");

		int[] dims = {400, 400, 20};
		
		int size=Util.cumprod(dims);
		
		float[] pixels=Util. rampFloat(size, 50);
		//float[] pixels=Util.randFloat(size);
		PixelCube<Float,BaseIndex> pc=new PixelCube<Float,BaseIndex>( dims,pixels );
		pc.setIndexing(BASE_INDEXING);
		// Laplacian kernel
/*		float [] lkern={0, 1, 0, 
				   1, -4, 1,
				   0, 1, 0};
			int[] dimx={3,3};	*/   
		// LoG kernel
		float [] lkern={
		0, 0, 1, 0, 0,
		0, 1, 2, 1, 0,
		1, 2,-16,2, 1,
		0, 1, 2, 1 ,0,
		0, 0, 1, 0, 0};
		int[] dimx={5,5,1};
		Float dummy=new Float(0f);
		Kernel<Float, float[]> kern=new Kernel<Float, float[]>(lkern, dummy,  dimx);
		kern.flip();
		kern.trim();		
		VectorCube<Float> vc=kern.getVect();
		//System.out.println(vc);
		//Pair<int[],int[]> cpair=vc.calcMinMax();
		
		//int[] cmin=cpair.first;
		//int[] cmax=cpair.second;
		
		Region<Float> reg=new Region<Float>(pc,vc);
		
		vc.split();
		int[][] vcoords=vc.coordsArray();
		float[] ivals=vc.valueArray();
		ret=dimx.clone();
		
		PixelCube<Float,BaseIndex> out=new PixelCube<Float,BaseIndex>( dims,new float[size] );
		out.setIndexing(BASE_INDEXING);
		
		new ImageJ();
		PixLib plib=new PixLib();
		
		try {
			ImagePlus imgp2 = plib.imageFrom("input", pc);
			imgp2.show();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final Access<?> oacc = out.getAccess();
		TranslatedCondition mc=(TranslatedCondition) BCFactory.create(BCTypes.TRANSLATED, reg.getIndex());
		GridIndex ind=GridIndex.create(dims);
		
		
		long time=-System.currentTimeMillis();	
		convolve(reg.getAccess(),   ind,  vcoords,  ivals,  mc, oacc );
		time+=System.currentTimeMillis();
		System.out.println("run time "+time+" ms");
		System.out.println("speed  "+ ((float)size)/time/1000+" M pix/s");
		
		
	/*	for (int i=0; i<n_cpus; i++) {
			cproc.addJob(new ConvTask2(reg,oacc,0));
		}	
		cproc.call();
		System.out.println("run time "+cproc.time()+" ms");
		System.out.println("speed  "+ ((float)size)/cproc.time()/1000+" M pix/s");
		System.out.println("interrupted "+cproc.getCounter());

*/		try {
			ImagePlus imgp3 = plib.imageFrom("output", out);
			imgp3.show();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new MThreadingConv();
		
	}
	
	public boolean valid(int[] coords, int[] dim) {
		boolean valid=true;	
		for (int i=0; i<dim.length; i++) {
		valid= valid &&(coords [i] >=0) && (coords [i] <dim[1]);			 		
		}
		/*if (!valid) {
		System.out.print("nv2: ");
		Util.printIntArray(coords);
	}*/
		return valid;
	}
	 
	private int[] ret;
	public int[] addc(int[]a, int[]b) {	
		for (int i=0; i<ret.length; i++) {
			ret[i]= (a[i] +b[i]) ;
		}
		return ret;
	}
	/*
	 *  manual optimization
	 */
	class ConvTask2 implements ForkJoinJob<Boolean>  {
		private AtomicInteger id=new AtomicInteger(-1);
		private int offset=0;
		private int limit=0;
		private int overlap=0;
		private boolean debug=true;
		private GridIndex ind;
		private Access<?> out;
		private Access<?> accessin;
		private VectorCube<?> vc;

		private int[][] vcoords=null;
		
		private float[] ivals=null;
		
		private int size=1;

		public ConvTask2(final Region<?> reg, final Access<?> oacc,  int _overlap ) {			
	
			overlap=_overlap;
	 		size=reg.size();

			ind= GridIndex.create(reg.getDimensions());
			vc=reg.getVectorCube();	
			vc.split();
			vcoords=vc.coordsArray();
			ivals=vc.valueArray();
	 
			accessin= Access.of(reg.getAccess(), ind);
			out=Access.of(oacc, ind);

			ret=new int [reg.getNDimensions()];
		}
		
		

		
	 	@Override
		public Boolean call() throws Exception {
		 
			
	 		//final MirrorCondition mc=(MirrorCondition) BCFactory.create(BCTypes.MIRROR, ind);
			//final StaticCondition mc=(StaticCondition) BCFactory.create(BCTypes.STATIC, ind);
			final TranslatedCondition mc=(TranslatedCondition) BCFactory.create(BCTypes.TRANSLATED, ind);

	 
			final int[] dims=ind.getDim();
			final int sz=vcoords.length;
			ind.setIndexAndUpdate(offset);
			int[] ct=dims.clone();
			
			for (int i=offset; i<limit; i++) {		
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
			
			return true;
	 	}

	
	 	public void fork(int _id) {
	 		id.set(_id);
	 		//System.out.println("forking "+id);
	 		int csize=size/n_cpus;

	 		//System.out.println("size "+size+" csize "+csize);
	 		offset=_id*csize;

	 		limit=offset+ csize +overlap; 
	 		if (_id== n_cpus-1)
	 			limit=size;
	 	}

		public void join() {
				//System.out.println("joining "+id);			
			
		}
		
		 
		@Override
		public String toString() {
			return id.toString();
		}
	
	
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
	public void convolve(final Access<?> accessin,  final GridIndex ind, final int[][] vcoords, final float[] ivals, 
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
 
}
