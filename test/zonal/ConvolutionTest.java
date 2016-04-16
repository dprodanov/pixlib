package test.zonal;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.*;
import ijaux.datatype.UnsupportedTypeException;
import dsp.BCFactory;
import dsp.BCTypes;
import dsp.BCFactory.MirrorCondition;
import ijaux.hypergeom.*;
import ijaux.hypergeom.convolution.Convolver;
import ijaux.hypergeom.convolution.Kernel;
import ijaux.hypergeom.index.BaseIndex;


public class ConvolutionTest implements Constants {

	PixLib plib=new PixLib();
	
	@SuppressWarnings("unchecked")
	public ConvolutionTest() {
	 	
		int[] dim={100, 100, 10};
		//int ndim=dim.length;
		final int size=Util.cumprod(dim);
	 
		//byte[] pixels_byte=   Util.randByte(size);
		float[] pixels_float=   Util.randFloat(size);
		
		//Byte b=new Byte((byte)0);
		
		Float f=new Float(0f);
		
		PixelCube<Float,BaseIndex> cube=new PixelCube<Float,BaseIndex>(dim, pixels_float, f);
		cube.setIndexing(BASE_INDEXING);
		int[] dimx={4, 2};
		float [] akern={1/2f, -1/2f, 1/2f, 
						 -1/2f, -1/2f, -1/2f, 
//						 -1/2f, -1/2f, -1/2f, 
//						 -1/2f, -1/2f, -1/2f, 
						 1/2f, -1/2f};
	 	
//		short [] akern2={2, -1, 0, 
//				2, -1, 0, 
//				2, -1, 0};
		Float dummy=new Float(0f);
		Kernel<Float, float[]> kern=new Kernel<Float, float[]>(akern, dummy,  dimx);
		
		float [][] akern2={{1f, -1f, 1f}, 
						   {1f, -1f, 1f},
						   {1f, -1f, 1f}};
		int[][] dims={{3},{3}};
	 			
		 
		 System.out.println(kern.getVect());
 
		 PixelCube<Float,BaseIndex> pc= (PixelCube<Float,BaseIndex>) kern.getMask();
		  System.out.println("Kernel:");
		 
		  System.out.println(pc);
		  
		 ImagePlus imp4;
		try {
			imp4 = plib.imageFrom("test byte",cube);
			imp4.show();
			 
			
			 try {
				ImagePlus imp2=plib.imageFrom("kernel",pc);
				 imp2.show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 Region<Float> reg=new Region<Float>(cube,kern);
		 // System.out.println("Region:");
		// System.out.println(reg);
		 
		 
		 
	
		 Convolver<Float> mproc=new Convolver<Float>(kern);
		
		 
		 @SuppressWarnings("rawtypes")
		 MirrorCondition bc= (MirrorCondition) BCFactory.create(BCTypes.MIRROR,  reg.getIndex());
		 //TranslatedCondition bc= reg.new TranslatedCondition(reg.getIndex());
		 //StaticCondition bc= reg.new StaticCondition(reg.getIndex());
		 mproc.setBoundaryCondition(bc);
		 Region<Float> regout=mproc.convolve(reg);
		
		// System.out.println(regout);
		  //Region<Float> regout2=mproc.convolveSeparable(reg,sc);
		 
		 ImagePlus imp3=null;
		try {
			imp3 = plib.imageFrom("convolved byte",regout);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 imp3.show();
		
		 /*
		  
		 
		 float[] pixels_float=   Util.randFloat(size);
			
			PixelCube<Float,BaseIndex> cube_sh=new PixelCube<Float,BaseIndex>(dim, pixels_float,  BaseIndex.class);
			
			 StructureElement<Float> se_sh=new StructureElement<Float>(radius, ndim, Float.class);
			 
			 se_sh.createDiamondMask(radius, false);
			
	 
			 PixelCube<Float,BaseIndex> pc_sh= (PixelCube<Float,BaseIndex>) se_sh.getMask();

			 ImagePlus imp6=plib.imageFrom("test float",cube_sh);
			 imp6.show();
			 
				
			 MorphoProcessorXD<Float> mproc_sh=new MorphoProcessorXD<Float>(se_sh);
			 Region<Float> reg_sh=new Region<Float>(cube_sh,se_sh);
			 
			
			 ImagePlus imp7=plib.imageFrom("diamond SE",pc_sh);
			 imp7.show();
			long time=System.currentTimeMillis();
				
			 Region<Float> regout_sh=mproc_sh.erode(reg_sh);
			 long time1=System.currentTimeMillis()- time;
			 System.out.println("using Access: "+time1);
			 mproc_sh.useIter();
			 regout_sh=mproc_sh.erode(reg_sh);
			 long time2=System.currentTimeMillis()- time;
			 System.out.println("using ArrayIterator: "+time2);
				 ImagePlus imp8=plib.imageFrom("eroded short",regout_sh);
				 imp8.show();*/
	}

	private void printdim(int [] dim) {
		System.out.print("[ ");
		for (int d: dim) {
			System.out.print(d+" ");
		}
		System.out.println("]");
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
		new ConvolutionTest();

	}

}
