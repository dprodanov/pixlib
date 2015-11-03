package test;

import ij.ImagePlus;
import ij.process.ByteProcessor;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.compat.ImagePlusAccess;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.index.BaseIndex;

public class ImagePlusTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		int[] dims={500, 500};
		int sz=Util.cumprod(dims);
		byte[] byte_pixels=Util.rampByte(sz, 255);
		
		//PixLib plib=new PixLib();
		 
		ByteProcessor bp=new ByteProcessor(dims[0],dims[1],byte_pixels, null);
		ImagePlus imp=new ImagePlus("test byte", bp);
		 
		imp.show();
		
		int[]pos=new int[]{ 2, 2};
		BaseIndex bi=new BaseIndex(dims);
		
		
		try {
			Access<Byte> ac=Access.rawAccess(byte_pixels, bi);
			
			double d2= ac.elementDouble(pos);
			 
			int uind=bi.indexOf(pos);
			 System.out.println("element at "+d2 +" index: "+uind);
			
			ImagePlusAccess<Byte> ipc = new ImagePlusAccess (imp);
			
			Access<Byte> ac2=ipc.getAccess();
			double d=ac2 .elementDouble(pos);
			 
			 System.out.println("element at "+d);

		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		 
	}

}
