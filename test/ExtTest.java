package test;
import ijaux.Util;

import java.lang.reflect.Array;


public class ExtTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] int_pixels={0, -1,   2,  3, 4, 
				  5,  6,   7,  8, 9, 
	             10, 11,  12, 13, 14,
	             15, 16,  17, 18, 19
	             };

		int[] dims={5,2,2};
		
		Integer[] dim2={5,2,2};
		Integer[] dim3={5,2,2};
		
		int_pixels=(int[]) extend(int_pixels,dims);
		
		for (int p:int_pixels) {
			System.out.print(p+" ");
		}
		
		Integer[] dim4= (Integer[] ) extend(dim2,dim3);
		
		for (int p:dim4) {
			System.out.print(p+" ");
		}
		
		dim4= (Integer[] ) Util.extend(dim2,dim3);
		
		for (int p:dim4) {
			System.out.print(p+" ");
		}

	}
	
	
	public static Object extend(Object arr, Object extension) {
		final Class<?> cp=arr.getClass();
		final Class<?> ce=extension.getClass();
		//System.out.println("arr type "+cp.getCanonicalName());
		//System.out.println("ext type "+ce.getCanonicalName());
		if (cp.isArray() && ce.isArray() )
			if (ce.isInstance(arr)) {	
				Class<?>retclass=Util.getPrimitiveType(cp);
				if (retclass==null) retclass =Array.get(arr, 0).getClass();
				//System.out.println("ret type "+retclass.getName());
				
				final int plength=Array.getLength(arr);
				final int elength=Array.getLength(extension);
				Object newarr=Array.newInstance(retclass, elength+plength);
				//System.out.println("ret type "+newarr.getClass().getCanonicalName());
				
				System.arraycopy(arr, 0, newarr, 0, plength);
				System.arraycopy(extension, 0, newarr, plength, elength);
				//for (int p=0; ++p<elength+plength;) {
				//	System.out.print(Array.get(newarr, p)+" ");
				//}
				 
				return newarr;
			}
		 return null;
	}

}
