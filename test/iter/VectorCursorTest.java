package test.iter;


import ij.ImageJ;
import ij.ImagePlus;
import ijaux.Constants;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.dir.VectorCursor;

public class VectorCursorTest implements Constants {

	
	
			
	public VectorCursorTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[]dim={8,16};
		int n=Util.cumprod(dim);
		byte[] byte_pixels=Util.rampByte(n, 8);		
		int xdir=1;
		//int[] dir={1,0};
		Util.printByteArray(byte_pixels);
		System.out.println("dimensions");
		Util.printIntArray(dim);	
		
		try {
			VectorCursor<byte[]> block=new VectorCursor<byte[]>(byte_pixels,   dim);
			
			System.out.println("permutation test");
			int[] kperm=block.perm(dim.length, 3);
			Util.printIntArray(kperm);
	
			
			block.setDirection(xdir);
			
			System.out.println(xdir+" axis iteration order");
			Util.printIntArray(block.getAxes());
			
			System.out.println("block size " + block.getBlockSize());
			System.out.println("length " + block.length());
			
			block.debug=true;
			int i=0;
			
			System.out.println("skipping ");
			while (block.hasNext()) {
				System.out.println("line  ... " +i++);
				block.inc();
				//Util.printByteArray(arr);
			}
			block.reset();
			i=0;
			System.out.println("iterating ");
			while (block.hasNext()) {
				System.out.println("line  ... " +i++);
				byte[] arr=block.next();
				Util.printByteArray(arr);
			}
			block.reset();
			i=0;
			while (block.hasNext()) {
				System.out.println("line  ... " +i++);
				byte[] array=Util.randByte(dim[xdir]);
				 
				Util.printByteArray(array);
				block.putByteArray(array);
			}
			PixelCube<Byte,BaseIndex> pc=new PixelCube<Byte,BaseIndex>( dim,byte_pixels );
			pc.setIndexing(BASE_INDEXING);
			
			
		
			new ImageJ();
			PixLib plib=new PixLib();
			ImagePlus imgp=plib.imageFrom("input", pc, 0);
	
			imgp.show();
		
			
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
