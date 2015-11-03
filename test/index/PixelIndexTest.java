package test.index;
import ijaux.*;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.CenteredIndex;
 


public class PixelIndexTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] dims={3,4};
		
		int[] coords={3,1};
		
		final int idx=6;
		
		/*
		System.out.print("Centered coordinates ");
		int index=Util.getIndex( coords, dims, false);
		
		System.out.println("calculated index: " +index);
		//System.out.println("Centered coordinates");
		Util.getCoordinates( index, dims, false);
		*/
		
		System.out.println("Indexed coordinates... ");
		int index=Util.getIndex( coords, dims, false);
		
		System.out.println("calculated index: " +index);
		
		//System.out.println("Indexed coordinates");
		int[] ucoords=Util.getCoordinates( idx, dims, false);
		System.out.print(" coordinates < ");
		for (int c:ucoords) 
			System.out.print(c+" ");
		System.out.println(">");
		
		index=Util.getIndex( coords, dims, false);
		System.out.println("calculated base index: " +index);
		
		
		BaseIndex bi=new BaseIndex( dims, idx );
		//oi.calcCoordinates();
		index=bi.indexOf(coords);
		
		System.out.print(" coordinates < ");
		for (int c:coords) 
			System.out.print(c+" ");
		System.out.println(">");
		System.out.println(" calculated base index: " +bi);
		bi.warp();
		System.out.println("wrap\n calculated base index: " +bi);
		
		CenteredIndex oi=new CenteredIndex( dims, idx );
		//oi.calcCoordinates();
		index=oi.indexOf(coords);
		System.out.println("calculated centered index: " +index + " "+ oi.isValid());
		
		
		final int[] ndims={10,10, 5,1}; // lattice size
		 
	    final int[] ncoords={1, 1, 1, 1}; 
	    				// {10,10,10};
	    
	     index=Util.getIndex( ncoords, ndims, false);
		
		System.out.println("calculated index: " +index);
		
		
		bi=new BaseIndex( ndims, 0 );
		bi.indexOf(ncoords);
		System.out.println("calculated index: " +bi +" isvalid: "+bi.isValid());
		bi.warp();
		System.out.println("calculated index: " +bi +" isvalid: "+bi.isValid());

		 final int[] ncoords2={10, 10, 6, 1}; 
			// {10,10,10};

		 index=Util.getIndex( ncoords2, ndims, false);

		 System.out.println("calculated index: " +index);

 
		 bi.indexOf(ncoords2);
		 System.out.println("calculated index: " +bi +" isvalid: "+bi.isValid());
		 bi.warp();
		 System.out.println("calculated index: " +bi +" isvalid: "+bi.isValid());
	}

}
