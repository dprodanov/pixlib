package test.datastruct;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;

import ijaux.*;
import ijaux.datatype.ElementOutput;
import ijaux.datatype.Pair;
import ijaux.funct.AbstractElementFunction;
import ijaux.hypergeom.*;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.CenteredIndex;
import ijaux.iter.*;
import ijaux.iter.dir.*;

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
public class PixelCubeTest2     {
	
	
	int[] int_pixels={0, -1,   2,  3, 4, 
					  5,  6,   7,  8, 9, 
		             10, 11,  12, 13, 14,
		             15, 16,  17, 18, 19
		             };
	int[] int_se= {0, 1, 0, 
				   1,  2, 1,
				   0, 1, 0,};
	int[] sdims={3,3};
	int[] dims={5,2,2};
	int [] ord={0,1};
	int [] origin={0,0,0};
	        
	PixelCube<Integer,BaseIndex> pc=new PixelCube<Integer,BaseIndex>(dims,int_pixels, int.class, BaseIndex.class);
	
	
	PixelCube<Integer,CenteredIndex> se=new PixelCube<Integer,CenteredIndex>(sdims,int_se);
	
	
	
	HashMap<String,? super Object> arg;
	 
	VectorCube<Integer> ve=new VectorCube<Integer>(se);

	public PixelCubeTest2() {
        
		
		//pc.indexing();
		pc.setIterationPattern(3);
		arg=new HashMap<String, Object>();
		arg.put("coef", Double.valueOf(2.5));
		
		 
		se.setIterationPattern(3);
		
		
		/*
		 * trivial example
		 */
		AbstractElementFunction<Integer, int[]> copy=new AbstractElementFunction<Integer,int[]>() {
			int element=0;
			 
			@Override
			public void transform(int[] a, int[] b) {
				element=a[0];		
			}

			@Override
			public Integer getOutput() {
				return element;
			}

			@Override
			public void transformBool(boolean a, boolean b) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void transformByte(byte a, byte b) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void transformDouble(double a, double b) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void transformFloat(float a, float b) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void transformInt(int a, int b) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void transformShort(short a, short b) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean getOutputBoolean() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public byte getOutputByte() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public double getOutputDouble() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public float getOutputFloat() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getOutputInt() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public short getOutputShort() {
				// TODO Auto-generated method stub
				return 0;
			}

			 
			 
		 };
	 
	 
		//	System.out.println(copy.transform(dims));
		 
		ve.setLimbo(255);
		
		pc.setIndexing(PixelCube.BASE_INDEXING);
		//new PixelCube.Index<BaseIndex>(){}.set(pc);
		
		
		PixelCube<Integer, BaseIndex> pc2=pc.projectSubspace(ord, origin, dims, false);
		for (int value:pc2) {
			System.out.print(value+",");
	
		}
		pc2.setIterationPattern(3);
		pc2.setIndexing(PixelCube.BASE_INDEXING);
		Region<Integer> reg=new Region<Integer> (pc2,ve);
		 PixelCube<Integer, BaseIndex> ret=reg.neighborhood(3);
		 System.out.println("\nRetriving");
		 for (int value:ret) {
			 
				System.out.print(value+",");
			 
			}
		 
	}
	

	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PixelCubeTest2 ft = new PixelCubeTest2();
		
		
		
		System.out.println("\nProcessing test");
		
		for (int value:ft.se) {
	 
			System.out.print(value+",");
		 
		}
		System.out.println();
		/*
		for (Pair<int[], Integer> p:ft.ve) {
			System.out.print("[");
			for (int u:p.first) {
				System.out.print(u+",");
			}
			System.out.print("]:");
			System.out.print(p.second+"\n");
		 
		}
		*/
		System.out.print(ft.ve);
	}

	 


	
} // end class
