package test.morpho;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.*;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.*;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.BaseIndex2D;
import ijaux.hypergeom.morphology.*;
import ijaux.iter.seq.*;
import ijaux.stats.CubeHistogram;


public class DilationTest {

	PixLib plib=new PixLib();
	
	@SuppressWarnings("unchecked")
	public DilationTest() throws UnsupportedTypeException {
		
		//plib.debug=true;
		
	
		float radius=2.0f;
		
		int[] dim={50, 50};
		int ndim=dim.length;
		final int size=Util.cumprod(dim);
	 
		byte[] pixels_byte= Util.randByte(size);
		//byte[] pixels_byte=Util.rampByte(size, 50);//  ;
		
		PixelCube<Byte,BaseIndex> cube=new PixelCube<Byte,BaseIndex>(dim, pixels_byte, byte.class,  BaseIndex.class);
		 
		StructureElement<Byte> se=new StructureElement<Byte>(radius, ndim, byte.class, true);

		se.createDiamondMask(radius, false);
		
		System.out.println ("dims");
		Util.printIntArray(se.getDimensions());
 
		 PixelCube<Byte,BaseIndex> pc= (PixelCube<Byte,BaseIndex>) se.getMask();
		// System.out.println("Structure element:");
		// System.out.println(se.getVect());
		 
		 ImagePlus imp4=plib.imageFrom("test byte",cube);
		 imp4.show();
		 
		
		 ImagePlus imp2=plib.imageFrom("diamond SE",pc);
		 imp2.show();
		
	
		 MorphoProcessorXD<Byte> mproc=new MorphoProcessorXD<Byte>(se);
		 Region<Byte> reg=new Region<Byte>(cube,se);
		 reg.getIndex().debug=true;
		// System.out.println("Region:");
		// System.out.println(reg);
		 
		 Region<Byte> regout=mproc.dilate(reg);
		
		// System.out.println(regout);
		
		 ImagePlus imp3=plib.imageFrom("dilated byte",regout);
		 imp3.show();
		
		 /*
		 short[] pixels_short=   Util.randShort(size);
			
			PixelCube<Short,BaseIndex> cube_sh=new PixelCube<Short,BaseIndex>(dim, pixels_short,  BaseIndex.class);
			
			 StructureElement<Short> se_sh=new StructureElement<Short>(radius, ndim, Short.class);
			 
			 se_sh.createDiamondMask(radius, false);
			
	 
			 PixelCube<Short,BaseIndex> pc_sh= (PixelCube<Short,BaseIndex>) se_sh.getMask();

			 ImagePlus imp6=plib.imageFrom("test",cube_sh);
			 imp6.show();
			 
			//CubeHistogram<Short> ch=new CubeHistogram<Short>(pc_sh);
			
			 MorphoProcessorXD<Short> mproc_sh=new MorphoProcessorXD<Short>(se_sh);
			 Region<Short> reg_sh=new Region<Short>(cube_sh,se_sh);
			 
			
			 ImagePlus imp7=plib.imageFrom("diamond SE",pc_sh);
			 imp7.show();
			 
			 Region<Short> regout_sh=mproc_sh.erode(reg_sh);
				
				// System.out.println(regout);
				
				 ImagePlus imp8=plib.imageFrom("eroded",regout_sh);
				 imp8.show();
		 */
		// Util.rampFloat(size, 50);// 
		 float[] pixels_float= Util.randFloat(size);
			
			PixelCube<Float,BaseIndex> cube_sh=new PixelCube<Float,BaseIndex>(dim, pixels_float, float.class, BaseIndex.class);
			
			 StructureElement<Float> se_sh=new StructureElement<Float>(radius, ndim, Float.class, false);
			 
			 se_sh.createDiamondMask(radius, false);
			
	 
			 PixelCube<Float,BaseIndex> pc_sh= (PixelCube<Float,BaseIndex>) se_sh.getMask();

			 ImagePlus imp6=plib.imageFrom("test float",cube_sh);
			 imp6.show();
			 
				
			 MorphoProcessorXD<Float> mproc_sh=new MorphoProcessorXD<Float>(se_sh);
			 Region<Float> reg_sh=new Region<Float>(cube_sh,se_sh);
			 
			 reg_sh.getIndex().debug=false;
			 ImagePlus imp7=plib.imageFrom("diamond SE",pc_sh);
			 imp7.show();
			long time=System.currentTimeMillis();
				
			 Region<Float> regout_sh=mproc_sh.dilate(reg_sh);
			 long time1=System.currentTimeMillis()- time;
			 System.out.println("using Access: "+time1);
			 mproc_sh.useIter();
			 regout_sh=mproc_sh.dilate(reg_sh);
			 long time2=System.currentTimeMillis()- time;
			 System.out.println("using ArrayIterator: "+time2);
				 ImagePlus imp8=plib.imageFrom("dilated float",regout_sh);
				 imp8.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.setProperty("plugins.dir", args[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ImageJ();
		try {
			new DilationTest();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
