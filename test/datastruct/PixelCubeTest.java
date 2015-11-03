package test.datastruct;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;

import ijaux.*;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.*;
import ijaux.iter.dir.*;
import ijaux.iter.seq.RasterForwardIterator;
import ijaux.iter.seq.RasterRandomIterator;

/**
 * 
 */

/**
 * @author prodanov
 * @param <E>
 * @param <V>
 * @param <T>
 * @param <R>
 *
 */
/**
 * @author prodanov
 *
 * @param <E>
 * @param <V>
 * @param <T>
 */
public class PixelCubeTest     {
	
	/*
	int[] int_pixels={0, 1,  2,  3,  4, 
					  5,  6,  7,  8,  9, 				  
		             10, 11, 12, 13, 14,
		             
		             15, 16, 17, 18, 19,
		             20, 21, 22, 23, 24,
		             25, 26, 27, 28, 29
		             };
	*/
	
	
	static int[] dims={5,3,2,2};
	int n=60;
	int[] int_pixels=Util.rampInt(n,0);
	
	int [] ord={0,2,1};
	int [] origin={2,1,0,0};
	        
	PixelCube<Integer,BaseIndex> pc=new PixelCube<Integer,BaseIndex>(dims,int_pixels);
	
	HashMap<String,? super Object> arg;
	 
	RasterRandomIterator<Integer> iter_int= new RasterRandomIterator<Integer>((Object)int_pixels);



	public PixelCubeTest() {
        
		//pc=new PixelCube<Integer,BaseIndex>(dims,int_pixels);
		//pc.indexing();
		pc.setIterationPattern(3);
		arg=new HashMap<String, Object>();
		arg.put("coef", Double.valueOf(2.5));
		pc.setIndexing(PixelCube.BASE_INDEXING);
		
		System.out.println("Subspace projection testing");
		pc.debug=true;
		PixelCube<Integer, BaseIndex> pc2;
		try {
			pc2 = pc.projectSubspace(ord, origin, dims, false);
			for (int value:pc2) {
				System.out.print(value+",");
		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("\nCube iteration testing");
		ForwardCubeIterator<Integer> iter_int= new ForwardCubeIterator<Integer>(ord,  origin,  dims);
		iter_int.setPixels((Object)int_pixels);
		
		while (iter_int.hasNext())
			System.out.print(iter_int.next()+",");

	}
	

	public void process() {
	 	ForwardIterator<Integer> out= (ForwardIterator) pc.iterator();
//	 	out.set(0);
		 
		double factor=(Double) arg.get("coef");
		

		for (int value:pc) {
			value = (int)(value*factor);
			//System.out.print(value+",");
			out.put(value);
		}
		//System.out.println("Last index " +in.index());
	 
	}
	
	@SuppressWarnings("unchecked")
	public PixelCube<?,?> processnew() {
		
	 	PixelCube<?,?> outcube= (PixelCube<?,?>) Util.createCube(pc);
	 	outcube.setIterationPattern(pc.iterationPattern());
		RasterForwardIterator<? super Number> out=(RasterForwardIterator<? super Number>) outcube.iterator();

	 	out.set(0);
		 
		double factor=(Double) arg.get("coef");
		
		//out.set(0);
		for (int value:pc) {
			value = (int)(value*factor);
			//System.out.print(value+",");
			out.put(value);
		}
		//System.out.println("Last index " +in.index());
		return outcube;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PixelCubeTest ft = new PixelCubeTest();
		
		
		
		System.out.println("\nProcessing test");
		ft.process();
		
		for (int value:ft.pc) {
	 
			System.out.print(value+",");
		 
		}
	}

	 


	
} // end class
