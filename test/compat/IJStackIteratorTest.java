package test.compat;

import static test.TestUtil.*;

import ij.ImageJ;
import ij.ImagePlus;
import ij.ImageStack;
import ijaux.Util;
import ijaux.compat.IJLineIteratorStack;



public class IJStackIteratorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int width=100;
		int height=50;
		int n =width*height;
		float[] slide1=Util.rampFloat(n, 10);
		float[] slide2=Util.rampFloat(n, 20);
		float[] slide3=Util.rampFloat(n, 25);
		
		ImageStack is=new ImageStack(width, height);
		is.addSlice("1", slide1);
		is.addSlice("2", slide2);
		is.addSlice("3", slide3);
		
		byte[] bslide1=Util.rampByte(n, 10);
		byte[] bslide2=Util.rampByte(n, 20);
		byte[] bslide3=Util.rampByte(n, 25);
		
		ImageStack is2=new ImageStack(width, height);
		is2.addSlice("1", bslide1);
		is2.addSlice("2", bslide2);
		is2.addSlice("3", bslide3);
		
		new ImageJ();
			 		
		IJLineIteratorStack<float[]> iter= 
				new IJLineIteratorStack<float[]>(is, 0);
		int k=61;
		float[] line=iter.getLineFloat(k, 0);
		printvector(line);
		System.out.println(iter.size());
		
		
		/*
		 int u=0;
		 while (iter.hasNext()) {
			line=iter.next();
			System.out.println("line: " + (u++)+" \n");
			printvector(line);
		}*/
		
		float[] uline=new float[height];
		setvalue(uline, -1.0f);
		iter.putLine(uline, 51, 1);
		
		new ImagePlus("float stack", is).show();
		
		//////////////////
		// byte case
		/////////////////
		
		IJLineIteratorStack<byte[]> iter2= 
				new IJLineIteratorStack<byte[]>(is2, 0);
				
		 k=61;
		byte[] line2=iter2.getLineByte( k, 0);
		printvector(line2);
		System.out.println(iter2.size());
	 
		
		/*while (iter.hasNext()) {
			line=iter.next();
			System.out.println("line: " + (u++)+" \n");
			printvector(line);
		}*/
		
		byte[] uline2=new byte[height];
		setvalue(uline2, -1);
		
		byte[] uline3=new byte[width];
		setvalue(uline3, -1);
		iter2.putLine(uline2, width/2+1, 1); //Oy
		iter2.putLine(uline3, height/2+1, 0); //Ox
		
		new ImagePlus("byte stack", is2).show();
		
		
		
		
	}
	

}
