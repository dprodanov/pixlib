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

public class VectorCursorBench implements Constants {

	
	
			
	public VectorCursorBench() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[]dim={1000,1000};
		int n=Util.cumprod(dim);
		byte[] byte_pixels=Util.rampByte(n, 8);		
		int xdir=0;
		
		System.out.println("dimensions");
		Util.printIntArray(dim);	
		
		try {
			VectorCursor<byte[]> block=new VectorCursor<byte[]>(byte_pixels,   dim);
			
			block.setDirection(xdir);
			
			System.out.println(xdir+" axis iteration order");
			Util.printIntArray(block.getAxes());
			
			System.out.println("block size " + block.getBlockSize());
			System.out.println("length " + block.length());
			
			block.debug=false;
			//int i=0;
			
			
			long time=-System.currentTimeMillis();
			while (block.hasNext()) {
				//System.out.println("line  ... " +i++);
				block.inc();
				//Util.printByteArray(arr);
			}
			time+=System.currentTimeMillis();
			block.reset();
			
			System.out.println("ffwd in "+time );
			
			time=-System.currentTimeMillis();
			while (block.hasNext()) {
				//System.out.println("line  ... " +i++);
				byte[] arr=block.next();
				//Util.printByteArray(arr);
			}
			time+=System.currentTimeMillis();
			System.out.println("getting... " + time);
			block.reset();
			byte[] array=Util.randByte(dim[xdir]);
			time=-System.currentTimeMillis();
			while (block.hasNext()) {
				//System.out.println("line  ... " +i++);
				
				 
				//Util.printByteArray(array);
				block.putByteArray(array);
			}
			time+=System.currentTimeMillis();
			System.out.println("putting... " + time);
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
