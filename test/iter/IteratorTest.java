package test.iter;
import java.util.List;

import ijaux.iter.*;
import ijaux.iter.dir.*;
 
import ijaux.iter.seq.RasterRandomIterator;

/**
 * 
 */

/**
 * @author prodanov
 *
 */

public class IteratorTest {

	static int[] int_pixels={0, -1,   2,  3, 4, 
							 5,  6,   7,  8, 9, 
							10, 11,  12, 13, 14};
	
	static short[] short_pixels={0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10};
	
	static byte[] byte_pixels={0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -127};
	
	static RasterRandomIterator<Integer> iter_int= new RasterRandomIterator<Integer>((Object)int_pixels);
	
	static RasterRandomIterator<Short> iter_short= new RasterRandomIterator<Short>((Object)short_pixels);
	
	static RasterRandomIterator<Byte> iter_byte= new RasterRandomIterator<Byte>((Object)byte_pixels);
	
	
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
	System.out.println("Integer case");
	System.out.println("Forward iteration");
	while (iter_int.hasNext()) {
		System.out.print(" " +iter_int.next().intValue());
	}
	System.out.println("\nLast index " +iter_int.index());
	
	//System.out.println("first element " +iter_int.get2(0).getClass().getCanonicalName());
	iter_int.reset();
	
	System.out.println("Backward iteration");
	while (iter_int.hasPrevious()) {
		System.out.print(" " +iter_int.previous().intValue());
	}
	System.out.println("\nLast index " +iter_int.index());

	
	
	/*
	System.out.println("Short case");
	while (iter_short.hasNext()) {
		System.out.println("retriving " +iter_short.next().shortValue());
	}
	System.out.println("Last index " +iter_short.index());
	iter_short.reset();
	while (iter_short.hasPrevious()) {
		System.out.println("retriving " +iter_short.previous().shortValue());
	}
	System.out.println("Last index " +iter_short.index());

	
	
	System.out.println("Byte case");
	while (iter_byte.hasNext()) {
		int b=iter_byte.next().byteValue()&0xFF;
		System.out.println("retriving " + b);
	}
	System.out.println("Last index " +iter_byte.index());
	iter_byte.reset();
	while (iter_byte.hasPrevious()) {
		System.out.println("retriving " +iter_byte.previous().byteValue());
	}
	System.out.println("Last index " +iter_byte.index());

*/
/*		
	int bsize=3;

	System.out.println("Integer block iteration case");
	PixelBlockIterator<Integer[]> block_iter_int= new PixelBlockIterator<Integer[]>(bsize ,(Object)int_pixels);
	Integer[] f=block_iter_int.first(); 
	
		
	for (Integer  q: f) 
		System.out.println("retriving first block " +q.intValue());
	
	
	f=block_iter_int.last(); 
	
	
	for (Integer  q: f) 
		System.out.println("retriving last block " +q.intValue());
	
	block_iter_int.set(0);
	System.out.println("forward iteration");
	while (block_iter_int.hasNext()) {
		f=block_iter_int.next();
		for (Integer  q: f) 
			System.out.println(block_iter_int.index() +" retriving " +q.intValue());
	}
	block_iter_int.reset();
	
	System.out.println("backward iteration");
	while (block_iter_int.hasPrevious()) {
		f=block_iter_int.previous();
		for (Integer  q: f) 
			System.out.println(block_iter_int.index() +" retriving " +q.intValue());
	}
*/
	
	final int[] dims={5,3};
	final int[] d={1,0}; //xy
	
	
	
	
	/*System.out.println("Integer forward direction iteration case");
	PixelDirForwardIterator<Integer> fwd_dir_iter_int = 
		new PixelDirForwardIterator<Integer>(d, dims);
		fwd_dir_iter_int.setPixels((Object)int_pixels);
	
	System.out.println("first elment: " +fwd_dir_iter_int.first());
	//int c=0;
	while (fwd_dir_iter_int.hasNext()) {
		System.out.print(" " +fwd_dir_iter_int.next());
	}
	System.out.println("\nLast index " +fwd_dir_iter_int.index());
		
	fwd_dir_iter_int.setOriginIndex(1);
	//System.out.println("first " +fwd_dir_iter_int.first());
	while (fwd_dir_iter_int.hasNext()) {
		System.out.print(" " +fwd_dir_iter_int.next());
	}
	System.out.println("\nLast index " +fwd_dir_iter_int.index());
	
	
	
	System.out.println("Integer backward direction iteration case");
	PixelDirBackwardIterator<Integer> bck_dir_iter_int = 
		new PixelDirBackwardIterator<Integer>(d, dims);
	
		bck_dir_iter_int.setPixels((Object)int_pixels);
	
	System.out.println("last element " +bck_dir_iter_int.last());
	//int c=0;
	while (bck_dir_iter_int.hasPrevious()) {
		System.out.print(" " +bck_dir_iter_int.previous());
	}
	System.out.println("\nLast index " +bck_dir_iter_int.index());
		
	//bck_dir_iter_int.setOriginIndex(2);
	//bck_dir_iter_int.
	while (bck_dir_iter_int.hasPrevious()) {
		System.out.print(" " +bck_dir_iter_int.previous());
	}
	System.out.println("\nLast index " +bck_dir_iter_int.index());
	
 
	System.out.println("Integer direction alternating iteration case");
	PixelZIterator<Integer> dirz_iter_int = 
		new PixelZIterator<Integer>(d, dims);
	dirz_iter_int.setPixels((Object)int_pixels);
	
	//System.out.println("first " +dirz_iter_int.first());

	while (dirz_iter_int.hasNext()) {
		System.out.print(" " +dirz_iter_int.next());
	}
	System.out.println("\nLast index " +dirz_iter_int.index());*/
	
	
	} // end main
	
	
	
	
	
	} // end class
	
	



