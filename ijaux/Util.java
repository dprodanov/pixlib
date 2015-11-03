package ijaux;

import ij.ImagePlus;
import ij.ImageStack;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.PixelCube;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Library of useful static methods
 * 
 */
public final class Util implements Constants {
	
	
	private Util() {}
	/*
	 * boolean, byte, char, short, int, long, float, and double. 
	 */
	
	private final static Class<?>[] primtypes={
		byte.class,
		short.class,
		int.class,
		float.class,
		double.class,
		boolean.class,
		char.class,
		
		byte[].class,
		short[].class,
		int[].class,
		float[].class,
		double[].class,
		boolean[].class,
		char[].class,
		
		byte[][].class,
		short[][].class,
		int[][].class,
		float[][].class,
		double[][].class,
		boolean[][].class,
		char[][].class
	};
	
	private final static Class<?>[] classes={
		byte.class,
		short.class,
		int.class,
		float.class,
		double.class,
		boolean.class,
		char.class,
		
		byte.class,
		short.class,
		int.class,
		float.class,
		double.class,
		boolean.class,
		char.class,
		
		byte.class,
		short.class,
		int.class,
		float.class,
		double.class,
		boolean.class,
		char.class
	};
	
	private static final Map<Class<?>, Class<? >> TYPE_MAP1 = 
	    new ConcurrentHashMap<Class<?>, Class<? >> ();

	static {
	    for (int i=0; i<primtypes.length ;i++) {
	    	TYPE_MAP1.put(primtypes[i],classes[i]); 
	    }
	}
	
	public static final Class<?> getPrimitiveType(Class<?> c) {
		return TYPE_MAP1.get(c);
	}
	
	
	private final static Class<?>[] primtypes2={
		byte.class,
		short.class,
		int.class,
		float.class,
		double.class,
		boolean.class,
		char.class,
		byte[].class,
		short[].class,
		int[].class,
		float[].class,
		double[].class,
		boolean[].class,
		char[].class,
	};
	
 
	private final static Class<?>[] numberclasses={
		java.lang.Byte.class,
		java.lang.Short.class,
		java.lang.Integer.class, 
		java.lang.Float.class,
		java.lang.Double.class,
		java.lang.Boolean.class,
		java.lang.Character.class,
		
		java.lang.Byte.class,
		java.lang.Short.class,
		java.lang.Integer.class, 
		java.lang.Float.class,
		java.lang.Double.class,
		java.lang.Boolean.class,
		java.lang.Character.class,
		
		java.lang.Byte.class,
		java.lang.Short.class,
		java.lang.Integer.class, 
		java.lang.Float.class,
		java.lang.Double.class,
		java.lang.Boolean.class,
		java.lang.Character.class
	};
	
	private static final Map<Class<?>, Class<? extends Object>> TYPE_MAP = 
	    new ConcurrentHashMap<Class<?>, Class<? extends Object>> ();

	static {
	    for (int i=0; i<primtypes2.length ;i++) {
	    	//System.out.println(primtypes2[i]);
	    	//System.out.println(numberclasses[i]);
	    	TYPE_MAP.put(primtypes2[i],numberclasses[i]);
	    	
	    }
	    for (int i=0; i<classes.length ;i++) { 
	    	TYPE_MAP.put(numberclasses[i],classes[i]);
	    }
	}
	
	public static final Class<?> getTypeMapping(Class<?> c) throws NullPointerException  {
		final Class<?> map=TYPE_MAP.get(c);
		if (map==null) throw new NullPointerException("Class not mapped");
		else return map;
	}
	
	
	
	public static int[] getCoordinates(int index, int[] offset, int[] dims) {		
		int n=dims.length;
		int[] ret=new int[n];
		int rt= index;
		for (int i=0; i<n; i++) {
			ret[i]=rt % dims[i] - offset[i]%dims[i];
			rt=rt/dims[i];
			//System.out.println("x["+i+"]=" +ret[i]+ " d: " +dims[i] +" o: " +offset[i] +" rt: " + rt);
		}
		
		return ret;
	}
	
	public static int[] getCoordinates(int index, int[] dims, boolean centered) {		
		if (centered) {
			int[] center=new int[dims.length];
			int cnt=0;
			for(final int c:dims) {
				//System.out.println(c/2);
				center[cnt++]=c/2;
				
			}
			return getCoordinates(  index, center,  dims);
		}
		else 
		return getCoordinates(  index, new int[dims.length],  dims);
	}
	
	public static int getIndex(int[] x,  int[] offset, int[] dims) {
		int n=dims.length;
		//int idc=x[n-1] +offset[n-1]%dims[n-1];
		int idc=(x[0] + offset[0])%dims[0];
		int[] dc=cumprodarr(dims,n);
		int u=0;
		if (x[0] % dims[0] ==0 && x[0]>0) u++;
		
		for (int i=1; i<n; i++) {
			if (x[i] % dims[i] ==0 && x[i]>0) u++;
			//if (x[i]>= dims[i]) x[i]= dims[i]-1;
			//idc+=((x[i] + offset[i])%dims[i]) *cumprod(dims,i);
			//idc+=(x[i] + offset[i]%dims[i]) *dc[i];
			idc+=((x[i] + offset[i])%dims[i]) *dc[i-1];

			//System.out.println("i "+i+" x " +x[i]+ " d: " +dims[i] +" index: " +idc);
		}
		if (u>0) idc=-idc;
		if (u==n) idc--;
		
		return idc;
	}
	
	public static int[] cumprodarr(int[] x, int i) {
		if (i>x.length) i=x.length;
		int[] ret = new int[i];
		int p=1;
		
		for(int c=0; c<i; c++) {
			p*=x[c];
			ret[c]=p;
			//System.out.println(p);
		}
		return ret;
	}
	public static int cumprod(int[] x, int i) {
		int p=1;
		if (i>x.length) i=x.length;
		for(int c=0; c<i; c++)
			p*=x[c];
		//System.out.println(p);
		return p;
	}
	
	public static int cumprod(int[] x) {
		return cumprod(x, x.length);
	}
	
	public static int getIndex(int[] x, int[] dims,  boolean centered) {
		if (centered) {
			int[] center=new int[dims.length];
			int cnt=0;
			for(final int c:dims) {
				//System.out.println(c/2);
				center[cnt++]=c/2;
				
			}
			return getIndex(  x, center,  dims);
		}
		else 
		return getIndex(  x, new int[dims.length],  dims);
	}
	
	public static boolean validateCube(int[] dims, Object pixels) {
		Class<? > c=pixels.getClass();	 
		if (c.isArray()) { 
			int prod=1;
			for (int p:dims) { 
				//System.out.print(p +", ");
				prod*=p;
			}	
			//System.out.println(prod);
			//System.out.println(Array.getLength(pixels));
			return (Array.getLength(pixels)== prod);
			
		} else
		return false;
	}
	
	// default CZT order
	public static void shuffle(ImageStack is, int[] ord, int order) {
		if (ord.length<2)
			return;		
		int nChannels = ord[0];
		int nSlices = ord[1];
		int nFrames = ord[2];
		int first=C, middle=Z, last=T;
		int nFirst=nChannels, nMiddle=nSlices, nLast=nFrames;
		switch (order) {
		case _Z: return;
		case CTZ: first=C; middle=T; last=Z;
		nFirst=nChannels; nMiddle=nFrames; nLast=nSlices;
		break;
		case ZCT: first=Z; middle=C; last=T;
		nFirst=nSlices; nMiddle=nChannels; nLast=nFrames;
		break;
		case ZTC: first=Z; middle=T; last=C;
		nFirst=nSlices; nMiddle=nFrames; nLast=nChannels;
		break;
		case TCZ: first=T; middle=C; last=Z;
		nFirst=nFrames; nMiddle=nChannels; nLast=nSlices;
		break;
		case TZC: first=T; middle=Z; last=C;
		nFirst=nFrames; nMiddle=nSlices; nLast=nChannels;
		break;
		}
		if (order!=CZT) {
			Object[] images1 = is.getImageArray();
			Object[] images2 = new Object[images1.length];
			System.arraycopy(images1, 0, images2, 0, images1.length);
			int[] index = new int[3];
			for (index[2]=0; index[2]<nFrames; ++index[2]) {
				for (index[1]=0; index[1]<nSlices; ++index[1]) {
					for (index[0]=0; index[0]<nChannels; ++index[0]) {
						int dstIndex = index[0] + index[1]*nChannels + index[2]*nChannels*nSlices;
						int srcIndex = index[first] + index[middle]*nFirst + index[last]*nFirst*nMiddle;
						images1[dstIndex] = images2[srcIndex];
					}
				}
			}
		}
	}
	
	static final int C=0, Z=1, T=2;
	/** 
	 *  from HyperStackConverter;
	 * Changes the dimension order of a 4D or 5D stack from 
	the specified order (CTZ, ZCT, ZTC, TCZ or TZC, _Z) to 
	the XYCZT order used by ImageJ. _Z does nothing*/
	public static void shuffle(ImagePlus imp, int order) {
		int nChannels = imp.getNChannels();
		int nSlices = imp.getNSlices();
		int nFrames = imp.getNFrames();
		int first=C, middle=Z, last=T;
		int nFirst=nChannels, nMiddle=nSlices, nLast=nFrames;
		switch (order) {
		case _Z: return;
		case CTZ: first=C; middle=T; last=Z;
		nFirst=nChannels; nMiddle=nFrames; nLast=nSlices;
		break;
		case ZCT: first=Z; middle=C; last=T;
		nFirst=nSlices; nMiddle=nChannels; nLast=nFrames;
		break;
		case ZTC: first=Z; middle=T; last=C;
		nFirst=nSlices; nMiddle=nFrames; nLast=nChannels;
		break;
		case TCZ: first=T; middle=C; last=Z;
		nFirst=nFrames; nMiddle=nChannels; nLast=nSlices;
		break;
		case TZC: first=T; middle=Z; last=C;
		nFirst=nFrames; nMiddle=nSlices; nLast=nChannels;
		break;
		}
		if (order!=CZT) {
			Object[] images1 = imp.getImageStack().getImageArray();
			Object[] images2 = new Object[images1.length];
			System.arraycopy(images1, 0, images2, 0, images1.length);
			int[] index = new int[3];
			for (index[2]=0; index[2]<nFrames; ++index[2]) {
				for (index[1]=0; index[1]<nSlices; ++index[1]) {
					for (index[0]=0; index[0]<nChannels; ++index[0]) {
						int dstIndex = index[0] + index[1]*nChannels + index[2]*nChannels*nSlices;
						int srcIndex = index[first] + index[middle]*nFirst + index[last]*nFirst*nMiddle;
						images1[dstIndex] = images2[srcIndex];
					}
				}
			}
		}
	}
	
	
	public static Object rand(int n, Class<?> c) {
		if (c.isPrimitive()) {
			// Random generator = new Random( System.currentTimeMillis() );
			Random generator = new Random(  );
			if (n<0) n=-n;
			Object arr=Array.newInstance(c, n);
			for (int r=0; r<n; r++) {
				Object value=null;
			
				if (c==byte.class) value=(byte)(generator.nextInt() & 0xFF);
				else if (c==short.class) value=(short)(generator.nextInt() & 0xFFFF);
				else if (c==int.class) value=(int)(generator.nextInt() & 0xFFFFFF);
				else if (c==float.class) value= generator.nextFloat() ;
				else if (c==double.class) value= generator.nextDouble();
				Array.set(arr, r, value);				
			}
			return arr;
		} else {
			throw new IllegalArgumentException ("Not a primitive type"); 
		}
	}
	
	public static byte[] randByte(int n) {
		return (byte[]) Util.rand(n, byte.class);
	}
	
	public static short[] randShort(int n) {
		return (short[]) Util.rand(n, short.class);
	}
	
	public static int[] randInt(int n) {
		return (int[]) Util.rand(n, int.class);
	}
	
	public static float[] randFloat(int n) {
		return (float[]) Util.rand(n, float.class);
	}
	
	public static double[] randDouble(int n) {
		return (double[]) Util.rand(n, double.class);
	}
	public static Object ramp(int n, Class<?> c) {
		if (c.isPrimitive()) {
			if (n<0) n=-n;
			Object arr=Array.newInstance(c, n);
			for (int r=0; r<n; r++) {
				Object value=null;
				if (c==byte.class) value=(byte)(r & 0xFF);
				else if (c==short.class) value=(short)(r & 0xFFFF);
				else if (c==int.class) value=(int)(r & 0xFFFFFF);
				else if (c==float.class) value=(float)r;
				else if (c==double.class) value=(double)r;
				Array.set(arr, r, value);				
			}
			return arr;
		} else {
			throw new IllegalArgumentException ("Not a primitive type"); 
		}
	}
	
	public static Object rampAlt(int n, int div, Class<?> c) {
		if (c.isPrimitive()) {
			if (n<0) n=-n;
			Object arr=Array.newInstance(c, n);
			for (int r=0; r<n; r++) {
				Object value=null;
				if (c==byte.class) value=(byte)((r % div) & 0xFF);
				else if (c==short.class) value=(short)((r % div) & 0xFFFF);
				else if (c==int.class) value=((r % div) & 0xFFFFFF);
				else if (c==float.class) value=(float)(r % div);
				else if (c==double.class) value=(double)(r % div);
				Array.set(arr, r, value);				
			}
			return arr;
		} else {
			throw new IllegalArgumentException ("Not a primitive type"); 
		}
	}
	
	public static Object rampAlt2(int n, int div, Class<?> c) {
		if (c.isPrimitive()) {
			if (n<0) n=-n;
			Object arr=Array.newInstance(c, n);
			for (int r=0; r<n; r++) {
				Object value=null;
				if (c==byte.class) value=(byte)(((int)r / div) & 0xFF);
				else if (c==short.class) value=(short)(((int)r / div) & 0xFFFF);
				else if (c==int.class) value=(((int)r / div) & 0xFFFFFF);
				else if (c==float.class) value=(float)((int)r / div);
				else if (c==double.class) value=(double)((int)r / div);
				Array.set(arr, r, value);				
			}
			return arr;
		} else {
			throw new IllegalArgumentException ("Not a primitive type"); 
		}
	}
	
	
	public static int[] rampInt(int n, int div) {
		if (div!=0) return (int[]) rampAlt(n,div,int.class);
		return (int[]) ramp(n,int.class);
	}
	
	public static byte[] rampByte(int n, int div) {
		if (div!=0) return (byte[]) rampAlt(n,div,byte.class);
		return (byte[]) ramp(n,byte.class);
	}
	
	public static short[] rampShort(int n, int div) {
		if (div!=0) return (short[]) rampAlt(n,div,short.class);
		return (short[]) ramp(n,short.class);
	}
	
	public static float[] rampFloat(int n, int div) {
		if (div!=0) return (float[]) rampAlt(n,div,float.class);
		return (float[]) ramp(n,float.class);
	}
	
	public static double[] rampDouble(int n, int div) {
		if (div!=0) return (double[]) rampAlt(n,div,double.class);
		return (double[]) ramp(n,double.class);
	}
	
	public static Object extend(Object arr, Object extension) {
		final Class<?> cp=arr.getClass();
		final Class<?> ce=extension.getClass();
 
		if (cp.isArray() && ce.isArray() )
			if (ce.isInstance(arr)) {	
				Class<?>retclass=Util.getPrimitiveType(cp);
				if (retclass==null) retclass =Array.get(arr, 0).getClass();
				
				final int plength=Array.getLength(arr);
				final int elength=Array.getLength(extension);
				final int flength=elength+plength;
				Object newarr=Array.newInstance(retclass, flength);
				System.arraycopy(arr, 0, newarr, 0, plength);
				System.arraycopy(extension, 0, newarr, plength, elength);
 			 
				return newarr;
			}
		 return null;
	}
	/*
	 * The code is taken from 
	 * "Reflecting generics" by Ian Robertson, June 23, 2007
	 *  http://www.artima.com/weblogs/viewpost.jsp?thread=208860
	 * 
	 */
	
	   /**
	   * Get the underlying class for a type, or null if the type is a variable type.
	   * @param type the type
	   * @return the underlying class
	   */
	  public static Class<?> getClass(Type type) {
	    if (type instanceof Class) {
	      return (Class<?>) type;
	    }
	    else if (type instanceof ParameterizedType) {
	      return getClass(((ParameterizedType) type).getRawType());
	    }
	    else if (type instanceof GenericArrayType) {
	      Type componentType = ((GenericArrayType) type).getGenericComponentType();
	      Class<?> componentClass = getClass(componentType);
	      if (componentClass != null ) {
	        return Array.newInstance(componentClass, 0).getClass();
	      }
	      else {
	        return null;
	      }
	    }
	    else {
	      return null;
	    }
	  } 

	  /**
	   * Get the actual type arguments a child class has used to extend a generic base class.
	   *
	   * @param baseClass the base class
	   * @param childClass the child class
	   * @return a list of the raw classes for the actual type arguments.
	   */

	public static <T> List<Class<?>> getTypeArguments(
	    Class<T> baseClass, Class<? extends T> childClass) {
	    Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
	    Type type = childClass;
	    // start walking up the inheritance hierarchy until we hit baseClass
	    while (! getClass(type).equals(baseClass)) {
	      if (type instanceof Class) {
	        // there is no useful information for us in raw types, so just keep going.
	        type = ((Class<?>) type).getGenericSuperclass();
	      }
	      else {
	        ParameterizedType parameterizedType = (ParameterizedType) type;
	        Class<?> rawType = (Class<?>) parameterizedType.getRawType();
	  
	        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
	        TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
	        for (int i = 0; i < actualTypeArguments.length; i++) {
	          resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
	        }
	  
	        if (!rawType.equals(baseClass)) {
	          type = rawType.getGenericSuperclass();
	        }
	      }
	    }
	  
	    // finally, for each actual type argument provided to baseClass, determine (if possible)
	    // the raw class for that type argument.
	    Type[] actualTypeArguments;
	    if (type instanceof Class) {
	      actualTypeArguments = ((Class<?>) type).getTypeParameters();
	    }
	    else {
	      actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
	    }
	    List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
	    // resolve types by chasing down type variables.
	    for (Type baseType: actualTypeArguments) {
	      while (resolvedTypes.containsKey(baseType)) {
	        baseType = resolvedTypes.get(baseType);
	      }
	      typeArgumentsAsClasses.add(getClass(baseType));
	    }
	    return typeArgumentsAsClasses;
	  }
	  
		public static boolean isIntType(Class<?> c) {
			boolean ret=(c==int.class);
			ret =ret  || (c==Integer.class);
			return ret;
		}
		
		public static boolean isByteType(Class<?> c) {
			boolean ret=(c==byte.class);
			ret =ret  || (c==Byte.class);
			return ret;
		}
		
		public static boolean isShortType(Class<?> c) {
			boolean ret=  (c==short.class);
			ret =ret  || (c==Short.class);
			return ret;
		}
		
		public static boolean isFloatType(Class<?> c) {
			boolean ret=  (c==float.class);
			ret =ret  || (c==Float.class);
			return ret;
		}
		
		public static boolean isDoubleType(Class<?> c) {
			boolean ret=  (c==double.class);
			ret =ret  || (c==Double.class);
			return ret;
		}
		
		public static Number getInt (int trval, Class<?> type) {
			//System.out.println(type);
			if (type==byte.class || type==Byte.class) {
				return  Byte.valueOf((byte) (trval & 0xFF));
			}
			if (type==short.class || type==Short.class) {	 
				return Short.valueOf((short)(trval & 0xFFFF));
			}
			if (type==int.class || type==Integer.class) {
				return Integer.valueOf( trval & 0xFFFFFF);	 
			}
			return null;
		}
		
		public static Number getFloat (float trval, Class<?> type) {
			if (type==float.class || type==Float.class) {
				return  Float.valueOf( trval);
			}
			if (type==double.class || type==Double.class) {
				return  Double.valueOf( trval);
			}
			return null;
		}
		
	
		public static PixelCube<?,?> createCube(PixelCube<?,?> pc) {
			//System.out.print(pc.getType().getName());
			int[] dims=pc.getDimensions();
			long size=pc.size();
			return new PixelCube(dims, Array.newInstance(pc.getType(), (int)size));
		}
		
		
		public static int[] trimDims(ImagePlus imp) {
			final int[] imdims=imp.getDimensions(); // 5D but with ones as dims
			int nd=imp.getNDimensions();
	 
			int[] dim=new int[nd];
			int cnt=0;
			for (final int d:imdims) {
				if (d>1)  dim[cnt++]=d;
	 		}
			return dim;
		}
		
		public static int[] trimDims(int[] imdims) {	
			int nd=0;
			for (final int d:imdims) {
				if (d>1)  nd++;
	 		} 
			int[] dim=new int[nd];
			int cnt=0;
			for (final int d:imdims) {
				if (d>1)  dim[cnt++]=d;
	 		}
			return dim;
		}
		
		public static void printByteArray(byte[] dim) {			
			System.out.print("[ ");
			for (int d: dim) {
				System.out.print(d+" ");
			}
			System.out.println("]");
		}
		
		public static void printIntArray(int[] dim) {			
			System.out.print("[ ");
			for (int d: dim) {
				System.out.print(d+" ");
			}
			System.out.print("]");
		}
		
		public static void printFloatArray(float[] dim) {			
			System.out.print("[ ");
			for (float d: dim) {
				System.out.print(d+" ");
			}
			System.out.println("]");
		}
		
		public static void printDoubleArray(double[] dim) {			
			System.out.print("[ ");
			for (double d: dim) {
				System.out.print(d+" ");
			}
			System.out.println("]");
		}
		
		public static <K extends Iterable<?>>void printArray(K dim) {			
			System.out.print("[ ");
			for (Object d: dim) {
				System.out.print(d+" ");
			}
			System.out.println("]");
		}
		
		@SuppressWarnings("unchecked")
		public static <N> N parseArray(String tokenString, Class<?> rtype, String ctoken){		        
 			String[] result = tokenString.split(ctoken);
			Class<?> ctype=Util.getPrimitiveType((Class<?>)rtype);
			Object arr=Array.newInstance( ctype, result.length);
			try {
				final Access<?> access=Access.rawAccess(arr, null);
				for (int i=0; i<result.length; i++) {
					double d=0;
					 
					try {
						Double di=Double.parseDouble(result[i]);
						d=di;
					}
					catch (NumberFormatException e){ }
		
					access.putDouble(i, d);
				}
			} catch (UnsupportedTypeException e) {
				e.printStackTrace();
			}		

			return (N)arr;
		}
		
		@SuppressWarnings("unchecked")
		public static <N> N parseArray(String tokenString, Class<?> rtype){		        
			StringTokenizer st = new StringTokenizer(tokenString);
	 		int n = st.countTokens();
			Class<?> ctype=Util.getPrimitiveType((Class<?>)rtype);
			Object arr=Array.newInstance( ctype, n);
			try {
				final Access<?> access=Access.rawAccess(arr, null);
				for (int i=0; i<n; i++) {
					final double d =  parseNum(st);
					access.putDouble(i, d);
				}
			} catch (UnsupportedTypeException e) {
				e.printStackTrace();
			}		

			return (N)arr;
		}
		 
		@SuppressWarnings("unchecked")
		public static <T> T castObjectArray(Object[] o) {
			Class<?> c=o[0].getClass();
		 
			int n= o.length;
			//System.out.print("cast :n: "+n);
			
			// hack for ImageJ
			 
			int cnt=0;
			for (Object u:o) {
				if (u==null)
					cnt++;
			}
			n-=cnt;
			
			if (c==byte[].class ) {
				byte[][] ret=new byte[n][];		
				for (int i=0;i<n; i++) {
					ret[i]=(byte[])o[i];
				}
				return (T) ret;
			}
			
			if (c==short[].class ) {
				short[][] ret=new short[n][];		 
				for (int i=0;i<n; i++) {
					ret[i]=(short[])o[i];
				}
				return (T) ret;
			}
			if (c==int[].class ) {
				int[][] ret=new int[n][];		 
				for (int i=0;i<n; i++) {
					ret[i]=(int[])o[i];
				}
				return (T) ret;
			}
			if (c==float[].class ) {
				float[][] ret=new float[n][];		 
				for (int i=0;i<n; i++) {
					ret[i]=(float[])o[i];
				}
				return (T) ret;
			}
			if (c==double[].class ) {
				double[][] ret=new double[n][];		 
				for (int i=0;i<n; i++) {
					ret[i]=(double[])o[i];
				}
				return (T) ret;
			}
			
			if (c==boolean[].class ) {
				boolean[][] ret=new boolean[n][];		 
				for (int i=0;i<n; i++) {
					ret[i]=(boolean[])o[i];
				}
				return (T) ret;
			}
			
			if (c==char[].class ) {
				char[][] ret=new char[n][];		 
				for (int i=0;i<n; i++) {
					ret[i]=(char[])o[i];
				}
				return (T) ret;
			}
			
			return null;
		}
		public static double parseNum(StringTokenizer st) {
			Double d=Double.valueOf(0);
			String token = st.nextToken();
			try {d =   Double.parseDouble(token);}
			catch (NumberFormatException e){}
			return d;
		}

		@SuppressWarnings("unchecked")
		public static <N> N range(N array, int[] rng) {					
			int i0=rng[0];
			int l=i0+rng[1]+1;
			Class<?> c=array.getClass();
			Object ret =Array.newInstance(getPrimitiveType(c), l);
			//System.out.println(getPrimitiveType(c));
			
			System.arraycopy(array, i0, ret, 0, l);
			return (N) ret;
		}

		@SuppressWarnings("unchecked")
		public static <K> ArrayList<K> flip(ArrayList<K> arr) {
			ArrayList<K> ret= new ArrayList<K>();
			int c=arr.size()-1;
			if (c==1) 
				return (ArrayList<K>) arr.clone();
			for (int i=c; i>=0; i--) {
				ret.add(arr.get(i));
			}
			return ret;
		}
	
		public static <K> void flipr(ArrayList<K> arr) {
			arr=flip(arr);
		}

}
