package ijaux;

import java.awt.Rectangle;

import ij.*;
import ij.process.*;
import ijaux.compat.ImagePlusCube;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.GridIndex;
/*
 *  Basic service class of PixLib
 *  @ version 1.0
 *  	different philosophy; mostly data types
 *   0.9.5
 */
public class PixLib implements Constants {
	
	final static int defaultdimorder=_Z;//ZTC;
	
	public boolean debug=false;
	
	
	/*
	 *  Returns false if the PixLib version is less than the one specified. */
	public static boolean versionLessThan(String version) {
		return libVersion.compareTo(version)<0;
	}
	
	/*
	 * 
	 */
	public static String version() {
		return libVersion;
	}
	
	
	public static ImageProcessor getMask(ImageProcessor ip, Rectangle r) {

		if (ip instanceof ByteProcessor) {
			return getByteMask((ByteProcessor) ip, r);
		}

		if (ip instanceof ShortProcessor) {
			return getShortMask((ShortProcessor) ip, r);
		}

		if (ip instanceof FloatProcessor) {
			return getFloatMask((FloatProcessor) ip, r);
		}	 

		return null;
	}


	/* Extracts the ByteProcessor within a rectangular roi
	 * 
	 * @param ip
	 * @param r
	 * @return ByteProcessor
	 */
	public static ByteProcessor getByteMask(ByteProcessor ip, Rectangle r) throws ArrayIndexOutOfBoundsException {

		int width = ip.getWidth();
		byte[] pixels = (byte[])ip.getPixels();

		float[] cTable=ip.getCalibrationTable();

		int xloc=(int)r.getX(); int yloc=(int)r.getY();
		int w=(int)r.getWidth();
		int h=(int)r.getHeight();
		//IJ.log("roi length " +w*h);
		byte[] mask=new byte[w*h];

		for (int cnt=0; cnt<mask.length;cnt++) {
			int index=xloc+cnt%w + (cnt/w)*width +yloc*width;
			if (index<pixels.length) 
				mask[cnt]=(byte)(pixels[index] & 0xFF);  

		}
		ByteProcessor bp= new ByteProcessor(w, h, mask,ip.getColorModel());

		bp.setCalibrationTable(cTable);
		return  bp;

	}


	/* Extracts the FloatProcessor within a rectangular roi
	 * 
	 * @param ip
	 * @param r
	 * @return FloatProcessor
	 */
	public static ShortProcessor getShortMask(ShortProcessor ip, Rectangle r) throws ArrayIndexOutOfBoundsException{

		int width = ip.getWidth();
		float[] cTable=ip.getCalibrationTable();
		short[] pixels = (short[])ip.getPixels();


		int xloc=(int)r.getX(); int yloc=(int)r.getY();
		int w=(int)r.getWidth();
		int h=(int)r.getHeight();
		//IJ.log("roi length " +w*h);
		short[] mask=new short[w*h];

		for (int cnt=0; cnt<mask.length;cnt++) {
			int index=xloc+cnt%w + (cnt/w)*width +yloc*width;
			if (index<pixels.length)
				mask[cnt]=pixels[index];  

		}
		ShortProcessor sp= new ShortProcessor(w, h, mask, ip.getColorModel());
		sp.setCalibrationTable(cTable);
		return sp;

	}

	/* Extracts the FloatProcessor within a rectangular roi
	 * 
	 * @param ip
	 * @param r
	 * @return FloatProcessor
	 */
	public static FloatProcessor getFloatMask(FloatProcessor ip, Rectangle r) throws ArrayIndexOutOfBoundsException {

		int width = ip.getWidth();
		float[] cTable=ip.getCalibrationTable();
		float[] pixels = (float[])ip.getPixels();


		int xloc=(int)r.getX(); int yloc=(int)r.getY();
		int w=(int)r.getWidth();
		int h=(int)r.getHeight();
		//IJ.log("roi length " +w*h);
		float[] mask=new float[w*h];

		for (int cnt=0; cnt<mask.length;cnt++) {
			int index=xloc+cnt%w + (cnt/w)*width +yloc*width;
			if (index<pixels.length)
				mask[cnt]=pixels[index];  

		}
		FloatProcessor fp=new FloatProcessor(w, h, mask, ip.getColorModel());
		fp.setCalibrationTable(cTable);

		return fp;
	}
	
	public static ImagePlusCube getImageCube(ImagePlus imp) {
		return new ImagePlusCube(imp);
	}
	
	/* (non-Javadoc)
	 * @see ijaux.IJFactory#cubeFrom(ij.ImagePlus, int)
	 */
 
	@SuppressWarnings("unchecked")
	public <E extends Number, I extends BaseIndex> PixelCube<E,I> cubeFrom(ImagePlus imp,  int indextype) {
		final int[] imdims=imp.getDimensions(); // 5D but with ones as dims
	 
	 
		int nd=imp.getNDimensions();
 
		int[] dim=new int[nd];
		int cnt=0;
		for (final int d:imdims) {
			if (d>1)  dim[cnt++]=d;
 		}
		// ImageJ order XYCZT
		switch (nd) {
			case 0: throw new IllegalArgumentException("N dimenisons is "+ nd); 
			case 1: 
			case 2: {
				PixelCube<?,I> pc=cubeFrom(imp.getProcessor(), indextype);
				return (PixelCube<E, I>) pc;
			}
			case 3: {
				PixelCube<?,I> pc=cubeFrom(imp.getStack(), indextype);
				return (PixelCube<E, I>) pc;
			}
			case 4: {	// one for the money 
				if (debug) System.out.println("x "+ imdims[0]+" y "+imdims[1] +" z "+imdims[3] +" t "+ imdims[4]);
				
				PixelCube<?,I> pc=cubeFrom(imp.getStack(), indextype);
				// 0 - width, 1 - height, 2 - channels, 3 - slices z, 4 -frames t
				final int[] newdims={imdims[0], imdims[1], imdims[3], imdims[4]}; 
				
				pc.reshapeTo(newdims);
				return (PixelCube<E, I>) pc;			
			}
			case 5: {  // two for the show
				PixelCube<?,I> pc=cubeFrom(imp.getStack(), indextype);
			 	pc.reshapeTo(imdims);
				return (PixelCube<E, I>) pc;
			}
		}
		
		
		return null;
	} // >=3D 
	
	public <I extends BaseIndex> PixelCube<?,I> cubeFrom(ImageStack is, int indextype) {
		final int nSlices=is.getSize();
		final int[] dim={is.getWidth(), is.getHeight(), nSlices};
		if (debug) System.out.println("x "+ dim[0]+" y "+dim[1] +" z x t "+dim[2]);

		final ImageProcessor ip=is.getProcessor(1);
		Object pixels=ip.getPixelsCopy();
		//extending from the second slice
		for (int i=2; i<=nSlices; i++) {
			pixels=Util.extend(pixels, is.getPixels(i));
		}
 
		if (ip instanceof ByteProcessor) {
			PixelCube<Byte,I> pc=new PixelCube<Byte,I>(dim, pixels);
			pc.setIndexing(indextype);
			return pc;
		}
		
		if (ip instanceof ShortProcessor) {
			PixelCube<Short,I> pc=new PixelCube<Short,I>(dim, pixels);
			pc.setIndexing(indextype);
			return pc;
		}
		
		if (ip instanceof ColorProcessor) {
			PixelCube<Integer,I> pc=new PixelCube<Integer,I>(dim, pixels);
			pc.setIndexing(indextype);
			return pc;
		}
		
		if (ip instanceof FloatProcessor) {
			PixelCube<Float,I> pc=new PixelCube<Float,I>(dim, pixels);
			pc.setIndexing(indextype);
			return pc;
		}	
		
		return null;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see ijaux.IJFactory#cubeFrom(ij.process.ImageProcessor)
	 */
	@SuppressWarnings("unchecked")
	public <E extends Number, I extends BaseIndex>  PixelCube<E,I> cubeFrom(ImageProcessor ip, int indextype) { // 2D images
		final int[] dim={ip.getWidth(), ip.getHeight()};
		final Object pixels=ip.getPixels();
		
		if (ip instanceof ByteProcessor) {
			PixelCube<Byte,I> pc=new PixelCube<Byte,I>(dim, pixels);
			pc.setIndexing(indextype);
			return (PixelCube<E, I>) pc;
		}
		
		if (ip instanceof ShortProcessor) {
			PixelCube<Short,I> pc=new PixelCube<Short,I>(dim, pixels);
			pc.setIndexing(indextype);
			return (PixelCube<E, I>) pc;
		}
		
		if (ip instanceof ColorProcessor) {
			PixelCube<Integer,I> pc=new PixelCube<Integer,I>(dim, pixels);
			pc.setIndexing(indextype);
			return (PixelCube<E, I>) pc;
		}
		
		if (ip instanceof FloatProcessor) {
			PixelCube<Float,I> pc=new PixelCube<Float,I>(dim, pixels);
			pc.setIndexing(indextype);
			return (PixelCube<E, I>) pc;
		}	
		
		return null;
	} 
	
	
	/* (non-Javadoc)
	 * @see ijaux.IJFactory#imageFrom(java.lang.String, ijaux.hypergeom.PixelCube)
	 *
	public  CompositeImage imageFrom(String title, ComplexCube  cube, int mode, int xdir)   {
		final int ndim=cube.getNDimensions();
		if (ndim >2) 
			throw new IllegalArgumentException(ndim +" dimensions not supported");
		
		final int[] dims=cube.getDimensions();
		
		
		switch (ndim) {
		case 1: 
		case 2:{ 
			if (mode==FFT_R) {
				ImageStack is = strack_reim2(cube, dims);
				ImagePlus img=new ImagePlus(title,is);
				return new CompositeImage(img);
			}
			if (mode==FFT_ABS) {
				ImageStack is = strack_abs2(cube, dims, xdir);
				ImagePlus img=new ImagePlus(title,is);
				return new CompositeImage(img);
			}
		}
		}
		return null;
	}
 */
	/**
	 * @param cube
	 * @param dims
	 * @return
	 *
	private ImageStack strack_reim2(ComplexCube cube, final int[] dims) {
		PixelCube<Double,?> re=cube.RealCube();
		PixelCube<Double,?> im=cube.ImCube();
		ImageStack is=new ImageStack (dims[0], dims[1]);
		try {
			ImageProcessor ipr=processorFrom(re);
			ImageProcessor ipi=processorFrom(im);
			is.addSlice("re",ipr);
			is.addSlice("im", ipi);

		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}
 */
	/**
	 * @param cube
	 * @param dims
	 * @return
	 *
	private ImageStack strack_abs2(ComplexCube cube, final int[] dims, int xdir) {
		double[] pwr=cube.norm2();
		double[] phase=cube.phase();
		
		ImageStack is=new ImageStack (dims[0], dims[1]);

		float[] pixels=new float[dims[0]*dims[1]];
		float[] pixels2=new float[dims[0]*dims[1]];
		//final double fl=dims[xdir];
		for (int i=0; i<pwr.length; i++) {
			pixels[i]= (float) (pwr[i]);
			pixels2[i]= (float) phase[i];
		}
		FloatProcessor fp1=new FloatProcessor(dims[0], dims[1], pixels, null);
		FloatProcessor fp2=new FloatProcessor(dims[0], dims[1], pixels2, null);
		is.addSlice("pwr", fp1);
		is.addSlice("phase", fp2);
		return   is;
	 
	}
 */
 
	/* (non-Javadoc)
	 * @see ijaux.IJFactory#imageFrom(java.lang.String, ijaux.hypergeom.PixelCube)
	 */
	public <E extends Number, I extends GridIndex> ImagePlus imageFrom(String title, PixelCube<E,I> cube, int order) throws UnsupportedTypeException {
		final int ndim=cube.getNDimensions();
		if (ndim >5) 
			throw new IllegalArgumentException(ndim +" dimensions not supported by ImageJ");
		
		final int[] dims=cube.getDimensions();
		
		switch (ndim) {
			case 1: 
			case 2:{
				ImageProcessor ip=processorFrom(cube);
				ImagePlus imp=new ImagePlus(title, ip);
				return imp;
			}
			case 3:{
				if (debug) System.out.println("case 3D: x "+dims[0]+" y "+dims[1]+" z "+dims[2]);
				ImageStack is=stack3DFrom(cube, dorder);
				ImagePlus imp=new ImagePlus(title, is);
				
				return imp;
			}
			case 4: {
				ImageStack is=stack4DFrom(cube, dorder);
				ImagePlus imp=new ImagePlus(title, is);
				imp.setDimensions(1, dims[2], dims[3]);
				if (debug) System.out.println("z "+dims[3]+" t "+dims[2]);
			
				Util.shuffle(imp,order);
				imp.setOpenAsHyperStack(true);
				return imp;
			}
			case 5: {
				ImageStack is=stack5DFrom(cube, dorder);
				ImagePlus imp=new ImagePlus(title, is);
				imp.setDimensions(dims[4], dims[3], dims[2]); // CZT default for IJ;
				if (debug) System.out.println("c "+ dims[4]+" z "+dims[3]+" t "+dims[2]);
				
				Util.shuffle(imp,order);
				imp.setOpenAsHyperStack(true);
				return imp;
			}
		}
		return null;
	}

	
	
	public ImageProcessor processorFrom(PixelCube<?,?> cube) throws UnsupportedTypeException {
		final Class<?> c=cube.getType();
		final int[] dims=cube.getDimensions();
		final int width=dims[0]; 
		int height=1;
		try {
			height=dims[1];
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println("1D case");
		}
		
		if(debug) 
			System.out.println("proc: width "+width+" height "+height);
		if (c==byte.class) {
			if(debug) System.out.println( "byte case");
			return new ByteProcessor(width, height,  cube.getAccess().getByteArray(), null);		
		}
		
		if (c==short.class) {		
			if(debug) System.out.println( "short case");
			return new ShortProcessor(width, height,   cube.getAccess().getShortArray(), null);
		}		
 
		if (c==float.class) {
			if(debug) System.out.println( "float case");
			return new FloatProcessor(width, height,  cube.getAccess().getFloatArray(), null);
		}
	 
		if (c==int.class) {		
			if(debug) System.out.println( "int case");
			return new ColorProcessor(width, height,  cube.getAccess().getIntArray());
		}
		
		if (c==double.class) {
			float[] pixels=new float[width*height];
			Access<Double> ac=(Access<Double>) cube.getAccess();
			for (int i=0; i<ac.length(); i++) {
				pixels[i]=ac.elementFloat(i);
			}
			return new FloatProcessor(width, height, pixels, null);
			//throw new UnsupportedTypeException("double is not supported");
		}
		return null;
	}

	public ImageProcessor getXYPlane(PixelCube<?,?> cube, int[] ord ) throws UnsupportedTypeException {
		if (debug) System.out.print("producing processor ...");
		final int[] dims=cube.getDimensions();
		final int[] origin=new int[dims.length];
		//final int[] ord={0, 1}; // xy plane
		PixelCube<?,?> plane=cube.projectSubspace(ord, origin, dims, true);
		return processorFrom(plane);
	}
	
	public <E extends Number, I extends GridIndex> ImageStack stack3DFrom(PixelCube<E,I> cube, int[] dorder) {
		if (debug) 
			System.out.print("producing stack ...");
		final int[] dims=cube.getDimensions();
		final int ndim=dims.length;
		if (ndim!=3) throw new IllegalArgumentException("3D cube required");
		final int width=dims[0], height=dims[1], depth=dims[2];
		
		ImageStack is=new ImageStack(width, height);
		if (debug) 
			System.out.println(" width="+width+" height="+height+" depth="+depth);
		
		final int[] origin=new int[dims.length];
		final int d2=dorder[0] ;
		//System.out.println(" "+dims[d2]);
		//debug=true;
		for (int i=0; i<dims[d2]; i++) {
			final int[] ord={0,1};
			if (debug) 
				System.out.println("x "+ origin[0]+" y "+origin[1]+" level "+origin[2]);
			final PixelCube<E,I> plane=cube.projectSubspace(ord, origin, dims, false);	
			//int[] sdims=plane.getDimensions();
			//printdim(sdims);
			//new ImagePlus( "plane "+ origin[2],processorFrom(plane)).show();
			try {
				final ImageProcessor ip=processorFrom(plane);
				if (debug) System.out.println("ip: width="+ip.getWidth()+" height="+ip.getHeight());
				is.addSlice(""+i, ip );
				origin[d2]++;
			} catch (UnsupportedTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
			
		}
		
		return is;
	}


	private int[] dorder={2,3,4}; // default dimension ordering
	// 2 3 : z {t c}
	// MRI t z; z t
	// confocal c z
	
	
	public static void printdim(int [] dim) {
		System.out.print("[ ");
		for (int d: dim) {
			System.out.print(d+" ");
		}
		System.out.println("]");
	}
	
	public void setOrdering(int[] sorder) {
		if (sorder.length<2 || sorder.length>3) return;
		dorder=sorder;
	}
	
	
	
	public <E extends Number, I extends GridIndex> ImageStack stack4DFrom(PixelCube<E,I> cube, int[] dorder) {
		if (debug) System.out.println("producing hyperstack ...");

		final int[] dims=cube.getDimensions();
		final int ndim=dims.length;
		if (ndim!=4) throw new IllegalArgumentException("4D cube required");
		if (dorder.length<2) throw new IllegalArgumentException("wrong dimension ordering");
		final int d2=dorder[0] ;
		final int d3=dorder[1] ;	
		
		final int depth=dims[d2]*dims[d3];

		final int width=dims[0], height=dims[1];  
		System.out.println("dimensions "+ndim);
		System.out.println("width="+width+" height="+height+" sdepth="+depth);

		ImageStack is=new ImageStack(width, height);
		
		int[] origin=new int[dims.length];

		
		for (origin[d2]=0; origin[d2]<dims[d2]; origin[d2]++) { //z
			
			for (origin[d3]=0; origin[d3]<dims[d3]; origin[d3]++) { //t or c
				
				final int[] ord={0, 1}; // xy
				if (debug) System.out.println("x "+ origin[0]+" y "+origin[1]+" level "+origin[3]);
				final PixelCube<E,I> volume=cube.projectSubspace(ord, origin, dims, false);	
				// x y  plane
		 
				is.addSlice(""+origin[d2], volume.getAccess().getArray());
				//System.out.println(i);
				if (debug) System.out.println(volume);
				//System.out.println(" "+is.getSize() +" " +is.getWidth() + " " + is.getHeight() );

			}
		}
		return is;
	}
	

	// 2 3 4 : z c t
	// 
	
	public <E extends Number, I extends GridIndex> ImageStack stack5DFrom(PixelCube<E,I> cube, int[] dorder) {
		if (debug) System.out.println("producing hyperstack ...");

		final int[] dims=cube.getDimensions();
		final int ndim=dims.length;
		if (ndim!=5) throw new IllegalArgumentException("4D cube required");
		if (dorder.length<3) throw new IllegalArgumentException("wrong dimension ordering");
		int[] origin=new int[dims.length];
		final int d2=dorder[0] ;
		final int d3=dorder[1] ;
		final int d4=dorder[2] ;	
		
		final int depth=dims[d2]*dims[d3]*dims[d4];

		final int width=dims[0], height=dims[1];  
		System.out.println("dimensions "+ndim);
		System.out.println(" width="+width+" height="+height+" sdepth="+depth);

		ImageStack is=new ImageStack(width, height);
		
		for (origin[d2]=0; origin[d2]<dims[d2]; origin[d2]++) {
			
			for (origin[d3]=0; origin[d3]<dims[d3]; origin[d3]++) {
				
				for (origin[d4]=0; origin[d4]<dims[d4]; origin[d4]++) {	
					
					final int[] ord={0, 1}; // xy
					if (debug) System.out.println("x "+ origin[0]+" y "+origin[1]+" level "+origin[3]);
					final PixelCube<E,I> volume=cube.projectSubspace(ord, origin, dims, false);	
					// x y  plane
				 
					is.addSlice(""+origin[d2], volume.getAccess().getArray());
					//System.out.println(i);
					if (debug) System.out.println(volume);
					//System.out.println(" "+is.getSize() +" " +is.getWidth() + " " + is.getHeight() );
				}
			}
		}
		return is;
	}
	


	 
	/* (non-Javadoc)
	 * @see ijaux.IJFactory#imageFrom(java.lang.String, ijaux.hypergeom.PixelCube)
	 */
	public <E extends Number, I extends BaseIndex> ImageStack stackFrom(PixelCube<E,I> cube, int order) throws UnsupportedTypeException {
		final int ndim=cube.getNDimensions();
		if (ndim >5) 
			throw new IllegalArgumentException(ndim +" dimensions not supported by ImageJ");
		
		final int[] dims=cube.getDimensions();

		switch (ndim) {
			case 1: 
			case 2:{
				ImageProcessor ip=processorFrom(cube);
				ImageStack is=new ImageStack(dims[0], dims[1]);
				is.addSlice("", ip);
				return is;
			}
			case 3:{
				if (debug) System.out.println("case 3D: x "+dims[0]+" y "+dims[1]+" z "+dims[2]);
				ImageStack is=stack3DFrom(cube, dorder);		
				return is;
			}
			case 4: {
				ImageStack is=stack4DFrom(cube, dorder);
				if (debug) System.out.println("z "+dims[3]+" t "+dims[2]);
				if (order!=_Z) {
					int[] ord={dims[2], dims[3]};
					Util.shuffle(is,ord, order);
				}
				return is;
			}
			case 5: {
				ImageStack is=stack5DFrom(cube, dorder);			
				if (debug) System.out.println("c "+ dims[4]+" z "+dims[3]+" t "+dims[2]);
				if (order!=_Z) {
					// CZT default for IJ;
					int[] ord={dims[4], dims[3], dims[2]};
					Util.shuffle(is,ord, order);
				}
				return is;
			}
		}
		return null;
	}
	
	public <E extends Number, I extends GridIndex> ImagePlus imageFrom(String title, PixelCube<E, I> cube) throws UnsupportedTypeException {	 
		return imageFrom(  title,   cube, defaultdimorder);
	}

	

    public static Class<?> mapPrimitiveType(ImageProcessor ip) {
    	if (ip instanceof ByteProcessor)
    		return byte.class;
    	if (ip instanceof ShortProcessor)
    		return short.class;
    	if (ip instanceof FloatProcessor)
    		return float.class;
    	
    	new UnsupportedTypeException("type: "+ ip.getClass());
		return null;
    }
    
    public static Class<?> mapArrayType(ImageProcessor ip) {
    	if (ip instanceof ByteProcessor)
    		return byte[].class;
    	if (ip instanceof ShortProcessor)
    		return short[].class;
    	if (ip instanceof FloatProcessor)
    		return float[].class;
    	
    	new UnsupportedTypeException("type: "+ ip.getClass());
		return null;
    }
	 
	 
}