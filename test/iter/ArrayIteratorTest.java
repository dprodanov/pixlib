package test.iter;
import java.util.List;

import ijaux.iter.*;
import ijaux.iter.array.ByteForwardIterator;
import ijaux.iter.array.ShortForwardIterator;
import ijaux.iter.dir.*;
 
import ijaux.iter.seq.RasterRandomIterator;

/**
 * 
 */

/**
 * @author prodanov
 *
 */

public class ArrayIteratorTest {

	static int[] int_pixels={0, -1,   2,  3, 4, 
							 5,  6,   7,  8, 9, 
							10, 11,  12, 13, 14};
	
	static short[] short_pixels={0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10};
	
	static byte[] byte_pixels={0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -127};
	
	//static PixelRandomIterator<Integer> iter_int= new PixelRandomIterator<Integer>((Object)int_pixels);
	
	static ShortForwardIterator iter_short= new ShortForwardIterator((Object)short_pixels);
	
	static ByteForwardIterator iter_byte= new ByteForwardIterator((Object)byte_pixels);
	
	
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	/*	
	System.out.println("Integer case");
	System.out.println("Forward iteration");
	while (iter_int.hasNext()) {
		System.out.print(" " +iter_int.next().intValue());
	}
	System.out.println("\nLast index " +iter_int.index());
	
	//System.out.println("first element " +iter_int.get2(0).getClass().getCanonicalName());
	iter_int.reset();
	
	 */
	
	

	System.out.println("Short case");
	while (iter_short.hasNext()) {
		int b=(iter_short.nextShort()) & 0xFFF;
		System.out.println("retriving " + b);
	}
	System.out.println("Last index " +iter_short.index());
	//iter_short.reset();
	/*
	while (iter_short.hasPrevious()) {
		System.out.println("retriving " +iter_short.previous().shortValue());
	}
	System.out.println("Last index " +iter_short.index());

	*/
	
	
	System.out.println("Byte case");
	while (iter_byte.hasNext()) {
		int b=(iter_byte.nextByte()) &0xFF;
		System.out.println("retriving " + b);
	}
	System.out.println("Last index " +iter_byte.index());
	/*
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
	 
	
	
	} // end main
	
	
	
	
	
	} // end class
	
	



