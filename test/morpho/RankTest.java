package test.morpho;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.*;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.*;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.morphology.*;
import ijaux.iter.seq.*;
import ijaux.stats.CubeHistogram;


public class RankTest {

	PixLib plib=new PixLib();
	
	@SuppressWarnings("unchecked")
	public RankTest() throws UnsupportedTypeException {
		
		//plib.debug=true;
	
		float radius=1.0f;
		
		int[] dim={100, 100};
		int ndim=dim.length;
		final int size=Util.cumprod(dim);
	 
		byte[] pixels_byte=   Util.rampByte(size, 50);//randByte(size);
		
		PixelCube<Byte,BaseIndex> cube=new PixelCube<Byte,BaseIndex>(dim, pixels_byte, byte.class,  BaseIndex.class);
		
		 StructureElement<Byte> se=new StructureElement<Byte>(radius, ndim, Byte.class);
		 
		 se.createDiamondMask(radius, false);
		
 
		 PixelCube<Byte,BaseIndex> pc= (PixelCube<Byte,BaseIndex>) se.getMask();
		// System.out.println("Structure element:");
		// System.out.println(se.getVect());
		 
		 ImagePlus imp4=plib.imageFrom("test byte",cube);
		 imp4.show();
		 
		
		 ImagePlus imp2=plib.imageFrom("diamond SE",pc);
		 imp2.show();
		
	
		 MorphoProcessorXD<Byte> mproc=new MorphoProcessorXD<Byte>(se);
		 Region<Byte> reg=new Region<Byte>(cube,se);
		 
		// System.out.println("Region:");
		// System.out.println(reg);
		 
		 Region<Byte> regout=mproc.rankMax(reg);
		
		// System.out.println(regout);
		
		 ImagePlus imp3=plib.imageFrom("rank max byte",regout);
		 imp3.show();


		 short[] pixels_short=   Util.randShort(size);

		 PixelCube<Short,BaseIndex> cube_sh=new PixelCube<Short,BaseIndex>(dim, pixels_short, short.class, BaseIndex.class);

		 StructureElement<Short> se_sh=new StructureElement<Short>(radius, ndim, Short.class);

		 se_sh.createDiamondMask(radius, false);


		 PixelCube<Short,BaseIndex> pc_sh= (PixelCube<Short,BaseIndex>) se_sh.getMask();

		 ImagePlus imp6=plib.imageFrom("test short",cube_sh);
		 imp6.show();

		 

		 MorphoProcessorXD<Short> mproc_sh=new MorphoProcessorXD<Short>(se_sh);
		 Region<Short> reg_sh=new Region<Short>(cube_sh,se_sh);


		 ImagePlus imp7=plib.imageFrom("diamond SE",pc_sh);
		 imp7.show();

		 Region<Short> regout_sh=mproc_sh.rankMax(reg_sh);

		 // System.out.println(regout);

		 ImagePlus imp8=plib.imageFrom("rank max short",regout_sh);
		 imp8.show();
		

		 
		 //float[] pixels_float=Util.rampFloat(size, 10);   //Util.randFloat(size);
		 float[] pixels_float=Util.randFloat(size);	
			PixelCube<Float,BaseIndex> cube_float=new PixelCube<Float,BaseIndex>(dim, pixels_float, float.class, BaseIndex.class);
			
			 StructureElement<Float> se_float=new StructureElement<Float>(radius, ndim, Float.class);
			 
			 se_float.createDiamondMask(radius, false);
			
	 
			 PixelCube<Float,BaseIndex> pc_float= (PixelCube<Float,BaseIndex>) se_float.getMask();

			 ImagePlus imp9=plib.imageFrom("test float",cube_float);
			 imp9.show();
			 
				
			 MorphoProcessorXD<Float> mproc_float=new MorphoProcessorXD<Float>(se_float);
			 Region<Float> reg_float=new Region<Float>(cube_float,se_float);
			 
			
			 ImagePlus imp10=plib.imageFrom("diamond SE",pc_float);
			 imp10.show();
			long time=System.currentTimeMillis();
				
			 Region<Float> regout_float=mproc_float.rankMax(reg_float);
			 long time1=System.currentTimeMillis()- time;
			 System.out.println("using Access: "+time1);
			 mproc_float.useIter();
			 regout_float=mproc_float.rankMax(reg_float);
			 long time2=System.currentTimeMillis()- time;
			 System.out.println("using ArrayIterator: "+time2);
				 ImagePlus imp11=plib.imageFrom("rank float",regout_sh);
				 imp11.show();
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
			new RankTest();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
