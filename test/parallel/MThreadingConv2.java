package test.parallel;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.Constants;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.funct.ElementFunction;
import ijaux.funct.PrimitiveElementFunction;
import ijaux.hypergeom.BCFactory;
import ijaux.hypergeom.BCFactory.MirrorCondition;
import ijaux.hypergeom.BCFactory.StaticCondition;
import ijaux.hypergeom.BCTypes;
import ijaux.hypergeom.BoundaryCondition;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.Region;
import ijaux.hypergeom.VectorCube;
import ijaux.hypergeom.convolution.Kernel;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.GridIndex;
import ijaux.parallel.ConcurentProcessor;
import ijaux.parallel.ForkJoinJob;


import java.util.concurrent.atomic.AtomicInteger;

 
public class MThreadingConv2 implements Constants {

	 ConcurentProcessor<Boolean> cproc=new ConcurentProcessor<Boolean>();
	 int n_cpus= ConcurentProcessor.n_cpus;
	
	
	public MThreadingConv2() {
		System.out.println("detected " + n_cpus +" CPUs");
		//int poolsize=2*n_cpus;
		//System.out.println("thread pool size: "+ poolsize);
		int[] dims = {500, 500};
		
		int size=Util.cumprod(dims);
		
		float[] pixels=Util. rampFloat(size, 50);
		//float[] pixels=Util.randFloat(size);
		PixelCube<Float,BaseIndex> pc=new PixelCube<Float,BaseIndex>( dims,pixels );
		pc.setIndexing(BASE_INDEXING);
		// Laplacian kernel
		/*float [] lkern={0f, 1f, 0f, 
				   1f, -4f, 1f,
				   0f, 1f, 0f};
			int[] dimx={3,3};	   */
		float [] lkern={
		0, 0, 1, 0, 0,
		0, 1, 2, 1, 0,
		1, 2,-16,2, 1,
		0, 1, 2, 1 ,0,
		0, 0, 1, 0, 0};
		int[] dimx={5,5};
		Float dummy=new Float(0f);
		Kernel<Float, float[]> kern=new Kernel<Float, float[]>(lkern, dummy,  dimx);
		kern.flip();
		VectorCube<Float> vc=kern.getVect();
		Region<Float> reg=new Region<Float>(pc,vc);
		
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

		for (int i=0; i<n_cpus; i++) {

			cproc.addJob(new ConvTask2(reg,oacc, 0));
		}	
		cproc.call();
		System.out.println("run time "+cproc.time()+" ms");
		System.out.println("speed  "+ ((float)size)/cproc.time()/1000+" M pix/s");
		System.out.println("interrupted "+cproc.getCounter());
		
		try {
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
		
		new MThreadingConv2();
		
	}
	
	class ConvSim<E extends Number> implements PrimitiveElementFunction<E> {

		 double sum=0;

		@Override
		public void transformBool(boolean a, boolean b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void transformByte(byte a, byte b) {
			transformDouble(a & byteMask,b & byteMask);
			
		}

		@Override
		public void transformDouble(double a, double b) {
			sum+=a*b;
			
		}

		@Override
		public void transformFloat(float a, float b) {
			sum+=a*b;
			
		}

		@Override
		public void transformInt(int a, int b) {
			transformDouble(a,b);
			
		}

		@Override
		public void transformShort(short a, short b) {
			transformDouble(a & shortMask,b & shortMask);
			
		}

		@Override
		public void transform(E a, E b) {			
			sum+=a.doubleValue()*b.doubleValue();
		}

		@Override
		public E getOutput() {
			double ret=sum;
			sum=0;
			return (E) new Double(ret);
		}

		@Override
		public byte getOutputByte() {
			return (byte) sum;
		}

		@Override
		public short getOutputShort() {
			return (short) sum;
		}

		@Override
		public int getOutputInt() {
			return (int) sum;
		}

		@Override
		public float getOutputFloat() {
			float ret=(float) sum;
			sum=0;
			return ret;
		}

		@Override
		public double getOutputDouble() {
			double ret=sum;
			sum=0;
			return ret;
		}

		@Override
		public boolean getOutputBoolean() {
			return false;
		}
		
	}
	 
	
	class ConvTask2 implements ForkJoinJob<Boolean>  {
		private AtomicInteger id=new AtomicInteger(-1);
		private Region<?> areg=null;
		private int offset=0;
		private int limit=0;
		private int overlap=0;
		private boolean debug=true;
		private GridIndex ind;
		private Access<?> out=null;
		private Access<Number> accessin;
		private VectorCube<Number> vc;
		
		private int[][] vcoords=null;
		
		private float[] ivals=null;
		
		public ConvTask2(Region<?> reg, Access<?> oacc, int _overlap ) {			
			areg=reg;
			overlap=_overlap;
			out=oacc;
			ind=areg.getIndex().clone();
			vc=(VectorCube<Number>) areg.getVectorCube();
			vc.split();
			vcoords=vc.coordsArray();
			ivals=vc.valueArray();
			accessin=(Access<Number>) areg.getAccess();
			accessin.setIndexing(ind);
		}
		
		final ConvSim<Number> cs =new ConvSim<Number>();
		
		@Override
		public Boolean call() throws Exception {
		 
			//System.out.println("running id "+id);
			//System.out.println("offset "+offset+" limit "+limit);
			
			//System.out.println(ind.getClass());
			MirrorCondition mc=(MirrorCondition) BCFactory.create(BCTypes.MIRROR, ind);
			//StaticCondition mc=(StaticCondition) BCFactory.create(BCTypes.STATIC, ind);
			
				//VectorCube<Number> ret= vc.clone(); 
			for (int i=offset; i<limit; i++) {
				//VectorCube<?> ret=neighborhoodV2(access, ind, vc, i);
				//float  value=elementTransformCond(accessin, ind, vc, cs, mc, i);
				float  value=elementTransformCond2(accessin, ind, vcoords, ivals,cs, mc, i);
				//float value=-1;
				out.putFloat(i, value);
				//neighborhoodV(access, ind, vc, i,ret);
				//areg.neighborhoodV(areg.getIndex(), i);
			}
			//ret.reset();
			return true;
		}
		
	
		public void fork(int _id) {
			id.set(_id);
				//System.out.println("forking "+id);
				int size=areg.size();
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
		
		public <E extends Number>  VectorCube<E> neighborhoodV(Access<E> access, GridIndex pIndex,
				VectorCube<E>  ve, int idx, VectorCube<E> vc) {
			 
					 
			for (Pair<int[], E> p:ve) {
				pIndex.setIndexAndUpdate(idx);
				int u=pIndex.translate(p.first);
				//if (debug) System.out.print(" idx "+u +",");
			
				if (pIndex.isValid()) {
					final E obj=access.element(u);
					vc.addElement( p.first,obj);
				} else {
					//System.out.print(" "+pIndex +",");
				}
					
			}
			//System.out.print(vc2);
			return vc;
			
		}
		@Override
		public String toString() {
			return id.toString();
		}
	
	
	public <E extends Number>  VectorCube<E> neighborhoodV2(Access<E> access, GridIndex pIndex,
			VectorCube<E>  ve, int idx) {
		 
		VectorCube<E> vc=ve.clone();
		//vc.debug=true;
			 
		for (Pair<int[], E> p:ve) {
			pIndex.setIndexAndUpdate(idx);
			int u=pIndex.translate(p.first);
			if (pIndex.isValid()) {
				final E obj=access.element(u);
				vc.addElement( p.first,obj);
			} else {
				//System.out.print(" "+pIndex +",");
			}
				
		}
		//System.out.print(vc2);
		return vc;
		
	}
	
	public <E extends Number> float elementTransformCond(Access<E> access, GridIndex pIndex,
			VectorCube<E> ve, PrimitiveElementFunction<E> elementFnct, BoundaryCondition<int[]> bc, 
			int idx) {

		for (Pair<int[], E> p:ve) {	
			pIndex.setIndexAndUpdate(idx);
			pIndex.translate(p.first);
			try {	
			
				if (pIndex.isValid()) {
					//pIndex.warp();
					int u=pIndex.index();
					final float elem=access.elementFloat(u); 
					elementFnct.transformFloat(elem, p.second.floatValue());
				}
				else{
					// outside strategy		 
//					System.out.print("< "+ idx +" : ");
//					Util.printIntArray(pIndex.getCoordinates());
					//int[] coords=bc.getCoordsAt(pIndex.getCoordinates());
					//if (debug) 
//						Util.printIntArray(coords);
//						System.out.print(" >, ");
//						//System.out.print(" ["+pIndex +"], " ); 
					//float elem=access.elementFloat(coords);
					//Util.printIntArray(p.first);
					//elementFnct.transformFloat(elem, p.second.floatValue());
				
				}	
			} catch (Exception e) {
				if (debug) {
					System.out.println(e+": "+pIndex +" ");
					Util.printIntArray(p.first);
					//System.out.println(ve.limboval +" "+ p.second);
				}
				//elementFnct.transform(ve.limboval, p.second);
				//e.printStackTrace();
			}			
		}

		return  elementFnct.getOutputFloat();

	}
	
	public float elementTransformCond2(Access<?> access, GridIndex gIndex,
			int[][] cc, float[] val, PrimitiveElementFunction<?> elementFnct, BoundaryCondition<int[]> bc, 
			int idx) {
		int sz=cc.length;
		double sum=0;
		
		for (int i=0; i<sz; i++) {	
			gIndex.setIndexAndUpdate(idx);
			gIndex.translate(cc[i]);
			try {	
		
				if (gIndex.isValid()) {
					int u=gIndex.index();
					final float elem=access.elementFloat(u); 
					sum+=elem* val[i];
				}
				else{
					// outside strategy		 
					//					System.out.print("< "+ idx +" : ");
					//					Util.printIntArray(pIndex.getCoordinates());
					int[] coords=bc.getCoordsAt(gIndex.getCoordinates());
					//if (debug) 
					//		Util.printIntArray(coords);
					//		System.out.print(" >, ");
					//		//System.out.print(" ["+pIndex +"], " ); 
					float elem=access.elementFloat(coords);
					//Util.printIntArray(p.first);
					sum+=elem* val[i];

				}	
			} catch (Exception e) {
				if (debug) {
					System.out.println(e+": "+gIndex +" ");
					Util.printIntArray(cc[i]);
					//System.out.println(ve.limboval +" "+ p.second);
				}
				//elementFnct.transform(ve.limboval, p.second);
				//e.printStackTrace();
			}			
		}

		return  (float) sum;

	}
	
}
 
}
