package test.parallel;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.*;
import ijaux.datatype.*;
import ijaux.datatype.access.*;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;

public class MtreadingTest {
	

	    static int n_cpus = Runtime.getRuntime().availableProcessors();  
		  
	    
	    static final boolean debug=false;
		
		public static void main(String[] args) {
			//n_cpus=1;
			new MtreadingTest();
			System.out.println("CPUs "+n_cpus+"");
			int[] dims={2000, 2000};
			
			final int size=Util.cumprod(dims);
			byte[] byte_pixels=Util.rampByte(size, 100);
			
			final Thread[] threads =  new Thread[n_cpus];
			
			int csize=size/n_cpus;
			
			System.out.println("size "+size+" csize "+csize+"");
			final int overlap=10;
			
			try {
				final Access<Byte> accesin=Access.rawAccess(byte_pixels, null);
				
				  for (int ithread = 0; ithread < threads.length; ithread++) {  
					  final int offset=ithread*csize;
					  int climit=offset+ csize +overlap; 
					  if (ithread== threads.length-1)
						  climit=size;
					  final int limit=climit;
					  
					  //System.out.println("offset "+offset+" limit "+limit);
					  
					  final int u=ithread;
					  
					  threads[ithread] = new Thread() {  
						  
                 		 	int _id=u;
                 		 	boolean _debug=debug;
                 		 	
			                { setPriority(Thread.NORM_PRIORITY); }  
			  
			             // task specific code goes here
			                public void run() {  
			                   	for (int i=offset; i<limit; i++) {
			                		int p=accesin.elementInt(i)+1;
			                		accesin.putInt(i, p);
			                		}
			                	
			                	if(_debug)
			                		System.out.println("finishing "+limit+" "+_id);
			                } // end run
			                
			           
			                }; // end thread
			            // Concurrently run in as many threads as CPUs
				  } // end for
				  long time=-System.currentTimeMillis();
				  startAndJoin( threads);
				  time+=System.currentTimeMillis();
				  System.out.println("run time "+time+" ");
				  
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
				  
			} catch (UnsupportedTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		public static void startAndJoin(Thread[] threads)  
	    {  
	        for (int ithread = 0; ithread < threads.length; ++ithread)  
	        {  
	            threads[ithread].setPriority(Thread.NORM_PRIORITY);  
	            threads[ithread].start();  
	            //System.out.println("running ");
	        }  
	  
	        try  
	        {     
	            for (int ithread = 0; ithread < threads.length; ++ithread)  
	                threads[ithread].join();  
	            // join specific code
	        } catch (InterruptedException ie)  
	        {  
	            throw new RuntimeException(ie);  
	        }  
	    }  
}
