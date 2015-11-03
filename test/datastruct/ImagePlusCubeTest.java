package test.datastruct;
import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageProcessor;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.compat.ImagePlusCube;
import ijaux.compat.ImagePlusIndex;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;


public class ImagePlusCubeTest {
	
	static int n1=256;
	
	static int[] dim1={256,1};
	
	static int n2=256*256;
	
	static int[] dim2={256,256};
	
	static int n3=256*256*256;
	
	static int[] dim3={256,256,256};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//byte[] byte_pixels1=Util.rampByte(n1, 16);
		//byte[] byte_pixels2=Util.rampByte(n2, 16);
		byte[] byte_pixels3=Util.rampByte(n3, 128);
		
		runByte(byte_pixels3);
		
		float[] float_pixels3=Util.rampFloat(n3, 128);
		runFloat(float_pixels3);
	}

	private static void runByte(byte[] byte_pixels3) {
		PixLib plib=new PixLib();
		System.out.println("PixLib wrapper");
		
		PixelCube<Byte,BaseIndex> pc=new PixelCube<Byte,BaseIndex>( dim3, (Object)byte_pixels3, byte.class, BaseIndex.class);
		ImagePlus imp1 = null;
		try {
			imp1 = plib.imageFrom("1", pc);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		long start=System.currentTimeMillis();
		ImagePlusCube ipc=new ImagePlusCube(imp1);
		long time1=System.currentTimeMillis();
		
		int size=(int) ipc.size();
		
		Access<?> access=ipc.getAccess();
		
	 
		float max=Float.MIN_VALUE;
		int ind=0;
		//iterates over the image and finds the 1st global maximum
		long time2=System.currentTimeMillis();
		for (int idx=0; idx<size; idx ++) {
			final float c=access.elementFloat(idx);
			if (c>max) {
				max=c;
				ind=idx;
			}
		}
		long time3=System.currentTimeMillis();
		ImagePlusIndex bi=ipc.getIndex();
		//calculates the image coordinates
		bi.setIndex(ind);
		//int[] coords=bi.getCoordinates();
		
	 
	 
		long duration=time3-start;
	 
		System.out.println("Maximum of "+  access.elementFloat(ind)  +" found at "+bi);
		
		System.out.println("init time "+(time1-start));
		System.out.println("iteration time "+(time3-time2));
		System.out.println("duration "+ duration +" ms \n speed " + (float)size/duration +" pix/ms");
		
		System.out.println("native ImageJ");
		long time4=System.currentTimeMillis();
		runIJ(imp1);
		long time5=System.currentTimeMillis();
		duration=time5-time4;
		
		//System.out.println("run time "+duration);
		System.out.println("duration "+ duration +" ms \n speed " + (float)size/duration +" pix/ms");
	}
	
	
	private static void runFloat(float[] byte_pixels3) {
		PixLib plib=new PixLib();
		System.out.println("PixLib wrapper");
		
		PixelCube<Float,BaseIndex> pc=new PixelCube<Float,BaseIndex>( dim3, (Object)byte_pixels3, float.class, BaseIndex.class);
		ImagePlus imp1=null;
		try {
			imp1 = plib.imageFrom("1", pc);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		long start=System.currentTimeMillis();
		ImagePlusCube ipc=new ImagePlusCube(imp1);
		long time1=System.currentTimeMillis();
		
		int size=(int) ipc.size();
		
		Access<?> access=ipc.getAccess();
		
	 
		float max=Float.MIN_VALUE;
		int ind=0;
		//iterates over the image and finds the 1st global maximum
		long time2=System.currentTimeMillis();
		for (int idx=0; idx<size; idx ++) {
			final float c=access.elementFloat(idx);
			if (c>max) {
				max=c;
				ind=idx;
			}
		}
		long time3=System.currentTimeMillis();
		ImagePlusIndex bi=ipc.getIndex();
		//calculates the image coordinates
		bi.setIndex(ind);
		//int[] coords=bi.getCoordinates();
		
	 
	 
		long duration=time3-start;
	 
		System.out.println("Maximum of "+  access.elementFloat(ind)  +" found at "+bi);
		
		System.out.println("init time "+(time1-start));
		System.out.println("iteration time "+(time3-time2));
		System.out.println("duration "+ duration +" ms \n speed " + (float)size/duration +" pix/ms");
		
		System.out.println("native ImageJ");
		long time4=System.currentTimeMillis();
		runIJ(imp1);
		long time5=System.currentTimeMillis();
		duration=time5-time4;
		
		//System.out.println("run time "+duration);
		System.out.println("duration "+ duration +" ms \n speed " + (float)size/duration +" pix/ms");
	}
	
	public static int[] runIJ(ImagePlus imp) {
		int index=0, z=0;
		float max = -Float.MAX_VALUE;
		ImageStack stack = imp.getStack();
		int width = imp.getWidth();
		int height = imp.getHeight();
		int n = width*height;
		int images = imp.getStackSize();
		for (int img=1; img<=images; img++) {
			ImageProcessor ip = stack.getProcessor(img);
			for (int i=0; i<n; i++) {
				float v = ip.getf(i);
				if (v>max) {
					max = v; 
					index = i;
					z = img-1;
				}
			}
		}
		int x = index%width;
		int y = index/width;
		int[] pos = new int[3];
		pos[0]=x; pos[1]=y; pos[2]=z;
		return pos;
	}

}
