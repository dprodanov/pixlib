package test.index;
import ijaux.*;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.BaseIndex2D;
import ijaux.hypergeom.index.CenteredIndex;
 


public class IndexTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] dims={2000,3000};
		
		int[] coords={3,1};
		
		int[] coords2={1,3};
		
		final int idx=6;
	 	
		System.out.println("Indexed coordinates... ");
		System.out.println(dims[0] +" " + dims[1]);

		int index=Util.getIndex( coords, dims, false);
		
		System.out.println("calculated index: " +index);
		
	
		
		index=Util.getIndex( coords, dims, false);
		System.out.println("calculated Util index: " +index);
		
		
		BaseIndex bi=new BaseIndex( dims,0  );
		
		BaseIndex2D bi2d=new BaseIndex2D( dims  );
		
		//oi.calcCoordinates();
		index=bi.indexOf(coords);
		
		System.out.print(" coordinates < ");
		for (int c:coords) 
			System.out.print(c+" ");
		System.out.println(">");
		System.out.println(" calculated base index: " +bi);
		
		index=bi.computeIndex(coords);
		
		System.out.print(" coordinates < ");
		for (int c:coords) 
			System.out.print(c+" ");
		System.out.println(">");
		System.out.println(" calculated base index: " +bi);
		
		
		index=bi2d.indexOf(coords);
		
		System.out.print(" coordinates < ");
		for (int c:coords) 
			System.out.print(c+" ");
		System.out.println(">");
		System.out.println(" calculated base index: " +bi2d);
		
		long start=System.currentTimeMillis();
		for (int i=0; i<dims[0]; i ++) {
			for (int j=0; j<dims[1]; j ++) {
				coords[0]=i;
				coords[1]=j;
				index=bi.indexOf(coords);
			}
		}
		long t0=System.currentTimeMillis();
		
		for (int i=0; i<dims[0]; i ++) {
			for (int j=0; j<dims[1]; j ++) {
				coords[0]=i;
				coords[1]=j;
				index=bi.computeIndex(coords);
			}
		}
		long t1=System.currentTimeMillis();
		
		for (int i=0; i<dims[0]; i ++) {
			for (int j=0; j<dims[1]; j ++) {
				coords[0]=i;
				coords[1]=j;
				index=bi2d.indexOf(coords);
			}
		}
		long t2=System.currentTimeMillis();
		
		for (int i=0; i<dims[0]; i ++) {
			for (int j=0; j<dims[1]; j ++) {
				coords[0]=i;
				coords[1]=j;
				index=bi2d.computeIndex(coords);
			}
		}
		long t3=System.currentTimeMillis();
		
		System.out.println("BaseIndex indexOf time "+ (t0-start));
		System.out.println("BaseIndex computeIndex time "+ (t1-t0));
		System.out.println("BaseIndex2D indexOf time "+ (t2-t1));
		System.out.println("BaseIndex2D computeIndex time "+ (t3-t2));
	}

}
