package ijaux.compat;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;
import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.datatype.access.PagedAccess;
import ijaux.hypergeom.index.BaseIndex;

public class ImagePlusAccess<N> {
	protected PagedAccess<N> pa=null;
	protected Access<N> access=null;
	protected Class<?> type;
	boolean is2D=true;
	int size=0;
	/*
	 * 2D image
	 */
	public ImagePlusAccess (ImageProcessor ip) throws UnsupportedTypeException {
		final int w=ip.getWidth();
		final int h=ip.getHeight();
		final int[]dims={w, h};
		BaseIndex index=new BaseIndex( dims );
		access=Access.rawAccess(ip.getPixels(), index);
		size=w*h;
		type=access.getType();
	}
	
	/*
	 *  3,4,5D image
	 */
	public ImagePlusAccess (ImagePlus imp) throws UnsupportedTypeException {
		ImagePlusIndex index=new  ImagePlusIndex(  imp);
		final ImageStack is=imp.getImageStack();
		int[] imdims=imp.getDimensions();
		is2D=(imdims[2]==1) && (imdims[3]==1) && (imdims[4]==1);
		//System.out.println("is2D "+ is2D +" ("+imdims[2]+" "+imdims[3]+" "+imdims[4] +")");
		if (is2D) {
			access=Access.rawAccess(imp.getProcessor().getPixels(), index);			 
		} else {
			//System.out.println("stack size: "+is.getImageArray().length);
			pa=PagedAccess.rawAccess(is.getImageArray(), index);
		}
		
		size= Util.cumprod(imdims);
	}
	
	public Access<N> getAccess() {
		if (is2D)
			return access;
		else
			return pa;
	}
	
	public int[] size() {
		if (is2D)
			return access.size();
		else
			return pa.size();	
	}
	
	public int totSize() {
		return size;	
	}
	
	public Access<?>  cloneAccess() {
		if (is2D)
			return  Access.cloneAccess(access);
		else
			return PagedAccess.cloneAccess(pa);
		
	}
	
	public static <N> ImagePlusAccess<N> getPlusAccess (Object obj) throws UnsupportedTypeException {
		Class<?> type=obj.getClass();
		System.out.println("otype: "+ type);
		if (type==ImageProcessor.class
				|| type==FloatProcessor.class
				|| type==ByteProcessor.class
				|| type==ShortProcessor.class
				|| type==ColorProcessor.class)	 	
			return new ImagePlusAccess<N> ((ImageProcessor )obj);
		if (type==ImagePlus.class)		
			return new ImagePlusAccess<N> ((ImagePlus )obj);
 
		return null;

	}
	
	public static <N> Access<N> get (Object obj) {
 
		try {
			Class<?> type=obj.getClass();
			//System.out.println("otype: "+ type);
			if (type==ImageProcessor.class
					|| type==FloatProcessor.class
					|| type==ByteProcessor.class
					|| type==ShortProcessor.class
					|| type==ColorProcessor.class)	{
				final ImagePlusAccess<N> access=new  ImagePlusAccess<N>((ImageProcessor )obj);
				return access.getAccess();
			}
			if (type==ImagePlus.class){
				final ImagePlusAccess<N> access=new  ImagePlusAccess<N>((ImagePlus )obj);
				return access.getAccess();

			}		 
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static ImageProcessor getProcessor(Object pixels, int width, int height) throws UnsupportedTypeException {
			Access<?> access = Access.rawAccess(pixels, null);
			return getProcessor(access, width, height);
		 
	}

	public static ImageProcessor getProcessor(Access<?> access, int width, int height) {
		final Class<?> type=access.getType();
		final int sz=access.size()[0];
		if (width*height!=sz)
			throw new IllegalArgumentException("size does not match "+sz);
		
		//if (debug)
		//System.out.println("AT:" +type);
		
		if (type==byte.class) {
			ByteProcessor bp=new ByteProcessor(width, height);
			bp.setPixels(access.getByteArray());
			return bp;
		}

		if (type==short.class) {
			ShortProcessor sp=new ShortProcessor(width, height);
			sp.setPixels(access.getShortArray());
			return sp;
		}
		
		if (type==float.class) {
			FloatProcessor fp=new FloatProcessor(width, height);			 
			fp.setPixels(access.getFloatArray());
			return fp;
		}
		
		if (type==int.class) {
			ColorProcessor cp=new ColorProcessor(width, height);			 
			cp.setPixels(access.getIntArray());
			return cp;
		}
		return null;
	}
}
