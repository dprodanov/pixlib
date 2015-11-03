package test.datastruct;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;


public class PixelCubeSwappingTest {

	
	
	public static void main(String[] args) {
		PixLib plib=new PixLib();
		
		int[] dims={100,100,100};
		int n=Util.cumprod(dims);
		byte[] int_pixels=Util.rampByte(n,0);
		
		int [] ord={0,2};
		 
		int [] ord2={1,2};
		        
		PixelCube<Byte,BaseIndex> pc=new PixelCube<Byte,BaseIndex>(dims,int_pixels);
		pc.setIndexing(PixelCube.BASE_INDEXING);
		
		//System.out.print(pc);
		 
		PixelCube<Byte,BaseIndex> pc2=pc.swap(ord);
		pc2.setIndexing(PixelCube.BASE_INDEXING);
		//System.out.print(pc2);
		
		PixelCube<Byte,BaseIndex> pc3=pc.swap(ord2);
		pc2.setIndexing(PixelCube.BASE_INDEXING);
		
		new ImageJ();
		
		try {
			ImagePlus imp1 = plib.imageFrom("test",pc);
			imp1.show();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		
		try {
			ImagePlus imp2 = plib.imageFrom("swapped 1",pc2);
			imp2.show();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ImagePlus imp3 = plib.imageFrom("swapped 1",pc3);
			imp3.show();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
