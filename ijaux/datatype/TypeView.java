package ijaux.datatype;

import ijaux.Constants;
import ijaux.datatype.access.Access;

/**
 * 
 * @author Dimiter Prodanov
 *
 */
public class TypeView implements Constants {
	int mask=byteMask;

	public TypeView (int _mask) {
		mask=_mask;
	}
	
	public int byteView(byte b) {
		return  (b & mask);
	}
	
	public int shortView(short b) {
		return  (b & mask);
	}
	
	public int intView(int b) {
		return  (b & mask);
	}
	
	public float floatView(float f) {
		return f;
	}
	
	public double doubleView(double f) {
		return f;
	}
	
	public static int view(byte b) {
		return  (b & byteMask);
	}
	
	public static int view(Byte b) {
		return  (b & byteMask);
	}
	
	public static int view(short b) {
		return  (b & shortMask);
	}
	
	public static int view(Short b) {
		return  (b & shortMask);
	}
	
	public static <N extends Number>  int asInt(N b) {
		//System.out.println("generic view");
		if (b.getClass()==Byte.class) return b.intValue() & byteMask;
		if (b.getClass()==Short.class) return b.intValue() & shortMask;
		if (b.getClass()==Integer.class) return b.intValue();
		throw new IllegalArgumentException("not supported "+b.getClass());
	}
	
	public static <N>float[] asFloat(N o) throws UnsupportedTypeException {
		final Access<?> access=Access.rawAccess(o, null);
		final int len=(int)access.length();
		float [] ret= new float [len];
		for (int i=0; i<len; i++) {
			ret[i]=access.elementFloat(i);
		}
		return ret;
	}
	
	public static <N>double[] asDouble(N o) throws UnsupportedTypeException {
		final Access<?> access=Access.rawAccess(o, null);
		final int len=(int)access.length();
		double [] ret= new double [len];
		for (int i=0; i<len; i++) {
			ret[i]=access.elementDouble(i);
		}
		return ret;
	}
	
	public static <N>int[] asInt(N o) throws UnsupportedTypeException {
		final Access<?> access=Access.rawAccess(o, null);
		final int len=(int)access.length();
		int [] ret= new int [len];
		for (int i=0; i<len; i++) {
			ret[i]=access.elementInt(i);
		}
		return ret;
	}
	
	public static <N>short[] asShort(N o) throws UnsupportedTypeException {
		final Access<?> access=Access.rawAccess(o, null);
		final int len=(int)access.length();
		short [] ret= new short [len];
		for (int i=0; i<len; i++) {
			ret[i]=access.elementShort(i);
		}
		return ret;
	}
	
	public static <N>byte[] asByte(N o) throws UnsupportedTypeException {
		final Access<?> access=Access.rawAccess(o, null);
		final int len=(int)access.length();
		byte [] ret= new byte [len];
		for (int i=0; i<len; i++) {
			ret[i]=access.elementByte(i);
		}
		return ret;
	}
		
}
