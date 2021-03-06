package test.parallel;
import java.util.concurrent.atomic.AtomicInteger;

import ij.ImageJ;
import ij.ImagePlus;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.parallel.ConcurentProcessor;
import ijaux.parallel.ForkJoinJob;
import ijaux.parallel.ForkJoinTask;
import ijaux.parallel.ThreadedProcessor;


public class MThreadingTest3  {

	int n_cpus=ConcurentProcessor.n_cpus;
	ConcurentProcessor<Boolean> cproc=new ConcurentProcessor<Boolean>();
	
	
	public MThreadingTest3() {
		System.out.println("MThreadingTest3 ");

	}


	public static void main(String[] args) {
		//n_cpus=1;
		MThreadingTest3 m=new MThreadingTest3();
		//System.out.println("CPUs "+n_cpus+"");
		int[] dims={2000, 2000};
		
		final int size=Util.cumprod(dims);
		byte[] byte_pixels=Util.rampByte(size, 100);
		
		try {
			Access<Byte> accesin=Access.rawAccess(byte_pixels, null);

			for (int i=0; i<m.n_cpus; i++) {
				m.cproc.addJob(m.new ProcJob(accesin,0));
				
			}			  
			m.cproc.call();
			
			System.out.println("run time "+m.cproc.time()+" ");
			System.out.println("interrupted "+m.cproc.getCounter());

		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}

		

		PixelCube<Byte,BaseIndex> pci=new PixelCube<Byte,BaseIndex>(dims,byte_pixels);
		new ImageJ();
		PixLib plib=new PixLib();


		ImagePlus imp1 = null;
		try {
			imp1 = plib.imageFrom("test",pci);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imp1.show();

	}
	

	private class ProcJob implements ForkJoinJob<Boolean>{
		
		final Access<Byte> accesin;
		AtomicInteger id=new AtomicInteger(-1);
		private int offset=0;
		private int limit=-1;
		private int overlap=0;
		
		boolean _debug=false;
		
		public ProcJob(Access<Byte> accesin, int _overlap) {
	 
			this.accesin=accesin;
			overlap=_overlap;

		}
	
		public void join() {
			// TODO Auto-generated method stub
			
		}


		public void fork(int _id) {
			id.set(_id);
			if (_debug)	
				System.out.println("forking "+id);

			int size=accesin.size()[0];
			int csize=size/n_cpus;

			//System.out.println("size "+size+" csize "+csize);
 			offset=_id*csize;
			limit=offset+ csize +overlap; 
			if (_id== n_cpus-1)
				limit=size;
  
		}

		// Task specific code goes here
				// Concurrently run in as many threads as CPUs
		@Override
		public Boolean call() throws Exception {
			if (_debug)
				System.out.println("processing id "+id);
			
			if (_debug)
				System.out.println("offset "+offset+" limit "+limit);
			for (int i=offset; i<limit; i++) {
				final int p=accesin.elementInt(i)+1;
				accesin.putInt(i, p);
			}
			return true;
		}

	}

		
	
}
