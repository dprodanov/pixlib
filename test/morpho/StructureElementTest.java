package test.morpho;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.*;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.*;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.morphology.*;
import ijaux.iter.seq.*;


public class StructureElementTest {

	PixLib plib=new PixLib();
	
	@SuppressWarnings("unchecked")
	public StructureElementTest() {
		
		//plib.debug=true;
		
		new ImageJ();
	
		float radius=1.0f;
		
		int[] dim={3,3 };
		int ndim=dim.length;
		
		String s=" 0 -1  0 -1 0 -1 0 -1 0";
		
		byte[] b= Util.parseArray(s, byte[].class);
		
		 StructureElement<Byte> se=new StructureElement<Byte>(radius, ndim, Byte.class);
		 
		 StructureElement<Byte> se2=new StructureElement<Byte>(b, dim);
		 
		// se.createCircularMask(radius, false);
	/*
		 PixelCube<Byte,BaseIndex> pc= (PixelCube<Byte,BaseIndex>) se.getMask();
		 System.out.println(se.getVect());
			ImagePlus imp1=plib.imageFrom("circular SE",pc);
			imp1.show();
*/

		 se.createDiamondMask(radius, false);
		
		 //se.setMinMax((byte)0, (byte)5);
		 //se.createSquareMask(radius);
		 
		 System.out.println(se.getVect());
		 
		 System.out.println("scaling...");
		
		 se.scale(3.0f);
		 System.out.println(se.getVect());
		 se.createCircularMask(radius, false);
		 
		 System.out.println(se2);
		 System.out.println("\ninverting...");
		 se2.getVect().invert();
		  
		 System.out.println(se2);
		 System.out.print("min " +  se.getMin()+" : max "+ se.getMax());
		 
		 PixelCube<Byte,BaseIndex> pc= (PixelCube<Byte,BaseIndex>) se.getMask();
		 
		 ImagePlus imp2=null;
		try {
			imp2 = plib.imageFrom("diamond SE",pc);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imp2.show();
		 PixelCube<Byte,BaseIndex> pc2= (PixelCube<Byte,BaseIndex>) se2.getMask();
		 
		 
		 ImagePlus imp3=null;
			try {
				imp3 = plib.imageFrom("String SE",pc2);
			} catch (UnsupportedTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imp3.show();
		 
			/*
		 se.createSquareMask(radius);
		 System.out.println(se.getVect());
		 pc= (PixelCube<Byte,BaseIndex>) se.getMask();
		 System.out.println(se.getVect());
		ImagePlus imp3=plib.imageFrom("square SE",pc);
		imp3.show();
			
		
		final int dir=1;
		se.createLineMask(radius, dir);
		System.out.println(se.getVect());
		pc= (PixelCube<Byte,BaseIndex>) se.getMask();
		System.out.println(se.getVect());
		ImagePlus imp4=plib.imageFrom("Line SE "+ dir ,pc);
		imp4.show();
		*/
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new StructureElementTest();

	}

}
