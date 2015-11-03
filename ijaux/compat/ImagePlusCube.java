package ijaux.compat;

import java.util.concurrent.ConcurrentHashMap;

import ij.ImagePlus;
import ij.ImageStack;
import ijaux.Util;
import ijaux.datatype.Typing;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.*;

import ijaux.hypergeom.HyperCube;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.iter.AbstractIterator;
import ijaux.iter.IndexedIterator;

public class ImagePlusCube implements HyperCube<int[],Number>, Typing {
	protected Object pixels;
	private ImagePlus imp;
	protected ImagePlusIndex impi;
	 
	
	protected int[] dim;
	protected int n=5;
	protected int size;
	
	protected Access<Number> access=null;
	protected Class<?> type;
	
	public static boolean debug = false;
	
	private static ConcurrentHashMap<Integer, Class<?>> typesmap=new ConcurrentHashMap<Integer,Class<?>>();
	static {
		typesmap.put(ImagePlus.GRAY8, byte.class);
		typesmap.put(ImagePlus.COLOR_256, byte.class);
		typesmap.put(ImagePlus.GRAY16, short.class);
		typesmap.put(ImagePlus.GRAY32, float.class);
		typesmap.put(ImagePlus.COLOR_RGB, int.class);
	}
	
	public ImagePlusCube (ImagePlus aimp) {
		imp=aimp;
		dim=trimDims(imp.getDimensions());
		n=dim.length;
		impi=new ImagePlusIndex(imp);
		size=impi.max();//Util.cumprod(dim);
		if (debug) System.out.println("n "+n);
		setPixels(aimp);
		impi=new ImagePlusIndex(aimp);
	}
	
	
	private void setPixels(ImagePlus aimp) {
		
		type=typesmap.get(aimp.getType());
		if (debug) System.out.println(type);
		switch (n) {
			case 1:  
			case 2:  {
				pixels= aimp.getProcessor().getPixels();
				break;
			}
			case 3: {
				fromStack(aimp.getImageStack(), dim[2],dim[0]*dim[1]);
				break;
			}	
			case 4: {
				fromStack(aimp.getImageStack(), dim[2]*dim[3],dim[0]*dim[1]);
				break;
			}
			case 5: {
				fromStack(aimp.getImageStack(), dim[2]*dim[3]*dim[4],dim[0]*dim[1]);
				break;
			}
		}

		try {
			setAccess();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * @param aimp
	 */
	private void fromStack(ImageStack aimp, int u, int w) {
		if (type==byte.class) {
			byte[][] pixels= new byte[u][w] ;
			for (int s=0; s<pixels.length; s++)
				pixels[s]=(byte[])aimp.getPixels(s+1);
			
			this.pixels=pixels;
			return;
		}
		if (type==short.class) {
			short[][] pixels= new short[u][w] ; ;				 
			for (int s=0; s<pixels.length; s++)
				pixels[s]=(short[])aimp.getPixels(s+1);
			
			this.pixels=pixels;
			return;
		}
		if (type==int.class) {
			int[][] pixels= new int[u][w] ; ;
			for (int s=0; s<pixels.length; s++)
				pixels[s]=(int[])aimp.getPixels(s+1);
		
			this.pixels=pixels;
			return;
		}
		if (type==float.class) {
			float[][] pixels= new float[u][w] ; ;
			for (int s=0; s<pixels.length; s++)
				pixels[s]=(float[])aimp.getPixels(s+1);
		
			this.pixels=pixels;
			return;
		}
	}
	
	private void setAccess() throws UnsupportedTypeException {
		if (debug) System.out.println(pixels.getClass().getCanonicalName());
		if (n>2)  
			access=PagedAccess.rawAccess (pixels, impi);
		else {
			access=Access.rawAccess (pixels, impi);
			
		}
	}
	
	public Access<Number> getAccess() {
		return access;
	}
	
	public int size() {
		return size;
	}
	/*
	 * reduces extra dimensionality
	 */
	
	public int[] trimDims(int[] dims) {
		int cnt=0;
		for (int d:dims) {
			if (d>1) cnt++;
		}
		int[] newdims=new int[cnt];
		cnt=0;
		for (int d:dims) {
			if (d>1) {
				newdims[cnt]=d;
				cnt++;
			}
		}
		return newdims;
	}
	
	@Override
	public int[] getDimensions() {
		return dim;
	}

	@Override
	public int getNDimensions() {
		return n;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public int index() {
		return impi.index();
	}

	@Override
	public int iterationPattern() {
		return IP_SINGLE+IP_FWD;
	}

	@Override
	public IndexedIterator<Number> iterator() {
		return new ImagePlusIterator(imp, access);
	}
	
	public ImagePlusIndex getIndex() {

		return impi;
	}

	
	public PixelCube<?,BaseIndex> projectSubspace (int[] ord, int[] origin, int[] dims, boolean trim) {
		//TODO
		return null;
	}
	/*
	 * Auxiliary class
	 */
	class ImagePlusIterator  extends AbstractIterator<Number> {
		 
		protected Access<Number> access;
		
		public ImagePlusIterator(ImagePlus imp, Access<Number> access) {
		 
			int[] dim=imp.getDimensions();
			size=Util.cumprod(dim);
			this.access=access;
		}

		
		@Override
		public boolean hasNext() {
	 		return (i<size);
		}

		@Override
		public Number next() {
			return access.element(i++);
		}

		@Override
		public void put(Number value) {
			access.putE(i, value);	
		}

		@Override
		public void remove() { throw new UnsupportedOperationException(); }


		public Class<?> getType() {
			return access.getType();
		}

		 

	}

	@Override
	public boolean eq(Class<?> c) {
		return type==c;
	}


	@Override
	public int[] coordinates() {
		return impi.getCoordinates();
	}
	
}
