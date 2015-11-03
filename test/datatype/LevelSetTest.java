package test.datatype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import ijaux.Util;
import ijaux.datatype.LevelSet;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.LevelCube;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;

public class LevelSetTest {

	final int[] dims={50,30,4};
	final int n=Util.cumprod(dims);
	final short[] int_pixels=Util.rampShort(n,12);
	PixelCube<Short,BaseIndex> pcs=new PixelCube<Short,BaseIndex>(dims,int_pixels);
	
	public LevelSetTest() throws UnsupportedTypeException {
		// TODO Auto-generated constructor stub
		int[] levels={ 0, 2, 4, 6, 8};
		
		System.out.println("level set");
		LevelSet<Short> levset=new LevelSet((short)11,levels );
		System.out.println(levset);
		
		LevelCube<Short> lc=new  LevelCube<Short>(pcs);
		
		levset=lc.getLSet((short)3);
		System.out.println(levset);
		
		System.out.println("iterator");
		for (short s:lc)
		System.out.print(s +", ");
		
		Set<Entry<Short,ArrayList<Integer>>> set= lc.entrySet();
		
		System.out.println("\nEntry set");
		for(Entry<Short,ArrayList<Integer>> e:set)
			System.out.println(e);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new LevelSetTest();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
