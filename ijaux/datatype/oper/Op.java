package ijaux.datatype.oper;

import ijaux.Constants;
import ijaux.datatype.ComplexArray;
import ijaux.datatype.ComplexNumber;
import ijaux.datatype.UnsupportedTypeException;

public abstract class Op<E> implements Constants  {
	 
	/*
	 * Factory pattern
	 */
	public static Op<?> get(Object o) throws UnsupportedTypeException {
		 return get(o.getClass());
	}
	
	/*
	 * Factory pattern
	 */
	public static <N>  Op<?> get(Class<N>c  ) throws UnsupportedTypeException {
 
		if (c==byte.class || c== Byte.class)
			return ( Op<Byte> ) new ByteOp();
		if (c==short.class || c== Short.class)
			return ( Op<Short> ) new ShortOp();
		if (c==int.class || c== Integer.class)
			return ( Op<Integer> ) new IntOp();
		if (c==float.class || c== Float.class)
			return ( Op<Float> ) new FloatOp();
		if (c==double.class || c== Double.class)
			return ( Op<Double> ) new DoubleOp();

		
		throw new UnsupportedTypeException();
	}
	/*
	 *  invariant primitive methods
	 */
	public boolean inv(boolean b) {
		return !b;
	}
	
	public float inv(float b) {
		return -b;
	}
	
	public double inv(double b) {
		return -b;
	}
	
	public byte inv(byte b) {
		return (byte)(-b);
	}
	
	public byte invu(byte b) {
		return (byte)(0xFF- (b & byteMask));
	}
	
	public short inv(short b) {
		return (short)(-b);
	}
	
	public short invu(short b) {
		return (short)(0xFFFF - (b & shortMask));
	}
	
	public int inv(int b) {
		return -b;
	}
	
	public abstract E invE (E b);
	/*
	 *  invariant primitive methods
	 */
	
	public int add(byte a, byte b) {
		return  a +b;
	}
	
	// unsigned add
	public int addu(byte a, byte b) {
		return  ( ((int)a) & byteMask+ ((int)b) & byteMask);
	}
	
	 
	public int add(short a, short b) {
		return  a +b;
	}
	
	// unsigned add
	public int addu(short a, short b) {
		return  ( ((int)a) & shortMask+ ((int)b) & shortMask);
	}
	
	public int add(int a, int b) {
		return  (a + b);
	}	
	
	public float add(float a, float b) {
		return  (a +b);
	}
	
	public double add(double a, double b) {
		return  (a +b);
	}
	
	
	public boolean add(boolean a, boolean b) {
		return  a || b;
	}
	
	public ComplexNumber add(final ComplexNumber a, final ComplexNumber b) {
		final ComplexNumber ret=ComplexNumber.zero();
		final double re=a.Re()+b.Re();
		final double im=a.Im()+b.Im();
		ret.first=re;
		ret.second=im;
		return  ret;
	}
	
	public byte[] add(byte[]a, byte[]b) {
		byte[] ret=new byte [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]=(byte)((a[i] +b[i]) & byteMask);
		}
		return ret;
	}
	
	public short[] add(short[]a, short[]b) {
		short[] ret=new short [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]=(short)((a[i] +b[i]) & shortMask);
		}
		return ret;
	}
	
	
	
	public int[] add(int[]a, int[]b) {
		int[] ret=new int [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] +b[i]) ;
		}
		return ret;
	}
	
	public float[] add(float[]a, float[]b) {
		float[] ret=new float [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] +b[i]) ;
		}
		return ret;
	}
	
	public double[] add(double[]a, double[]b) {
		double[] ret=new double [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] +b[i]) ;
		}
		return ret;
	}
	
	public boolean[] add(boolean[]a, boolean[]b) {
		boolean[] ret=new boolean [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] || b[i]) ;
		}
		return ret;
	}
	
	public ComplexArray add(ComplexArray a, ComplexArray b) {
		int sz= (int) a.length();
		ComplexArray ret= ComplexArray.create(sz);
		
		double[] rea=a.Re();
		double[] reb=b.Re();		
		ret.first=add(rea,reb);
		
		double[] ima=a.Im();
		double[] imb=b.Im();		
		ret.second=add(ima,imb);
		 
		return ret;
	}
	
	
///////////////////////////////
	public int sub(byte a, byte b) {
		return  a - b;
	}
	
	public int sub(short a, short b) {
		return  a - b;
	}
	
	public int subu(byte a, byte b) {
		return  ((int)a) & byteMask   - ((int) b) & byteMask;
	}
	
	public int subu(short a, short b) {
		return  ((int)a) & shortMask   - ((int) b) & shortMask;
	}
	
	public int sub(int a, int b) {
		return  (a - b);
	}
	
	public float sub(float a, float b) {
		return  (a - b);
	}
	
	public double sub(double a, double b) {
		return  (a - b);
	}
	
	public boolean sub(boolean a, boolean b) {
		return  (a || !b);
	}
	
	public ComplexNumber sub(final ComplexNumber a, final ComplexNumber b) {
		final ComplexNumber ret=ComplexNumber.zero();
		final double re=a.Re()-b.Re();
		final double im=a.Im()-b.Im();
		ret.first=re;
		ret.second=im;
		return  ret;
	}
	
	public byte[] sub(byte[]a, byte[]b) {
		byte[] ret=new byte [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]=(byte)((a[i] - b[i]) & byteMask);
		}
		return ret;
	}
	
	public short[] sub(short[]a, short[]b) {
		short[] ret=new short [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]=(short)((a[i] -b[i]) & shortMask);
		}
		return ret;
	}
	
	public int[] sub(int[]a, int[]b) {
		int[] ret=new int [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] -b[i]) ;
		}
		return ret;
	}
	
	public float[] sub(float[]a, float[]b) {
		float[] ret=new float [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] -b[i]) ;
		}
		return ret;
	}
	
	public double[] sub(double[]a, double[]b) {
		double[] ret=new double [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] - b[i]) ;
		}
		return ret;
	}
	
	public boolean[] sub(boolean[]a, boolean[]b) {
		boolean[] ret=new boolean [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] || ! b[i]) ;
		}
		return ret;
	}
	
	public ComplexArray sub(ComplexArray a, ComplexArray b) {
		int sz= (int) a.length();
		ComplexArray ret= ComplexArray.create(sz);
		
		double[] rea=a.Re();
		double[] reb=b.Re();		
		ret.first=sub(rea,reb);
		
		double[] ima=a.Im();
		double[] imb=b.Im();		
		ret.second=sub(ima,imb);
		 
		return ret;
	}
	
///////////////////////////////
	public byte mult(byte a, byte b) {
		return (byte) (a*b);
	}
	
	public short mult(short a, short b) {
		return  (short) (a*b);
	}
	
	public int multu(byte a, byte b) {
		return ( ((int)a & byteMask )* ((int) b ) & byteMask);
	}
	
	public int multu(short a, short b) {
		return  ( ((int)a & shortMask )* ((int) b ) & shortMask);
	}
	
	
	public int mult(int a, int b) {
		return  (a * b);
	}
	
	public float mult(float a, float b) {
		return  (a * b);
	}
	
	public double mult(double a, double b) {
		return  (a * b);
	}
	
	public boolean mult(boolean a, boolean b) {
		return  (a && b);
	}
	
	public ComplexNumber mult(final ComplexNumber a, final ComplexNumber b) {
		final ComplexNumber ret=ComplexNumber.zero();
		final double re=a.Re()*b.Re();
		final double im=a.Im()*b.Im();
		ret.first=re;
		ret.second=im;
		return  ret;
	}
	
	public byte[] mult(byte[]a, byte[]b) {
		byte[] ret=new byte [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]=(byte)((a[i] * b[i]) & byteMask);
		}
		return ret;
	}
	
	public short[] mult(short[]a, short[]b) {
		short[] ret=new short [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]=(short)((a[i] * b[i]) & shortMask);
		}
		return ret;
	}
	
	public int[] mult(int[]a, int[]b) {
		int[] ret=new int [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] * b[i]) ;
		}
		return ret;
	}
	
	public float[] mult(float[]a, float[]b) {
		float[] ret=new float [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] * b[i]) ;
		}
		return ret;
	}
	
	public double[] mult(double[]a, double[]b) {
		double[] ret=new double [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] * b[i]) ;
		}
		return ret;
	}
	
	public boolean[] mult(boolean[]a, boolean[]b) {
		boolean[] ret=new boolean [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] && b[i]) ;
		}
		return ret;
	}
	
	
	public ComplexArray mult(ComplexArray a, ComplexArray b) {
		int sz= (int) a.length();
		ComplexArray ret= ComplexArray.create(sz);
		
		double[] rea=a.Re();
		double[] reb=b.Re();		
		ret.first=mult(rea,reb);
		
		double[] ima=a.Im();
		double[] imb=b.Im();		
		ret.second=mult(ima,imb);
		 
		return ret;
	}
	
///////////////////////////////
	public int div(byte a, byte b) {
		return  a / b ;
	}
	
	public int div(short a, short b) {
		return  a/ b;
	}
	
	public int divu(byte a, byte b) {
		return  ( ((int)a & byteMask )/ ((int) b ) & byteMask);
	}
	
	public int divu(short a, short b) {
		return  ( ((int)a & shortMask )/ ((int) b ) & shortMask);
	}
	
	public int div(int a, int b) {
		return  (a / b);
	}
	
	public float div(float a, float b) {
		return  (a / b);
	}
	
	public double div(double a, double b) {
		return  (a / b);
	}
	
	public boolean div(boolean a, boolean b) {
		return  (a && ! b);
	}
	
	public ComplexNumber div(final ComplexNumber a, final ComplexNumber b) {
		final ComplexNumber ret=ComplexNumber.zero();
		final double re=a.Re()/b.Re();
		final double im=a.Im()/b.Im();
		ret.first=re;
		ret.second=im;
		return  ret;
	}
	
	public byte[] div(byte[]a, byte[]b) {
		byte[] ret=new byte [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]=(byte) (a[i]/b[i]);
		}
		return ret;
	}
	
	public short[] div(short[]a, short[]b) {
		short[] ret=new short [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]=(short) (a[i]/b[i]);
		}
		return ret;
	}
	
	public byte[] divu(byte[]a, byte[]b) {
		byte[] ret=new byte [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]=(byte)(divu(a[i], b[i]) & byteMask);
		}
		return ret;
	}
	
	public short[] divu(short[]a, short[]b) {
		short[] ret=new short [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]=(short)(divu(a[i], b[i]) & shortMask);
		}
		return ret;
	}
	
	public int[] div(int[]a, int[]b) {
		int[] ret=new int [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] / b[i]) ;
		}
		return ret;
	}
	
	public float[] div(float[]a, float[]b) {
		float[] ret=new float [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] / b[i]) ;
		}
		return ret;
	}
	
	public double[] div(double[]a, double[]b) {
		double[] ret=new double [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] / b[i]) ;
		}
		return ret;
	}
	
	public boolean[] div(boolean[]a, boolean[]b) {
		boolean[] ret=new boolean [a.length];
		for (int i=0; i<a.length; i++) {
			ret[i]= (a[i] && ! b[i]) ;
		}
		return ret;
	}
	
	
	public ComplexArray div(ComplexArray a, ComplexArray b) {
		int sz= (int) a.length();
		ComplexArray ret= ComplexArray.create(sz);
		
		double[] rea=a.Re();
		double[] reb=b.Re();		
		ret.first=div(rea,reb);
		
		double[] ima=a.Im();
		double[] imb=b.Im();		
		ret.second=div(ima,imb);
		 
		return ret;
	}
	
/////////////////////////////////////////
	
	public int min(byte a, byte b) {
		return  Math.min(a & byteMask , b & byteMask);
	}
	
	public int min(short a, short b) {
		return  Math.min (a & shortMask , b & shortMask);
	}
	
	public int min(int a, int b) {
		return  Math.min( a , b);
	}
	
	public float min(float a, float b) {
		return  Math.min( a , b);
	}
	
	public double min(double a, double b) {
		return  Math.min( a , b);
	}
	
	public ComplexNumber min(final ComplexNumber a, final ComplexNumber b) {
		final ComplexNumber ret=ComplexNumber.zero();
		final double re=min(a.Re(),b.Re());
		final double im=min(a.Im(),b.Im());
		ret.first=re;
		ret.second=im;
		return  ret;
	}
	
/////////////////////////////////////////	
	public int max(byte a, byte b) {
		return  Math.max(a & byteMask , b & byteMask);
	}
	
	public int max(short a, short b) {
		return  Math.max (a & shortMask , b & shortMask);
	}
	
	public int max(int a, int b) {
		return  Math.max( a , b);
	}
	
	public float max(float a, float b) {
		return  Math.max( a , b);
	}
	
	public double max(double a, double b) {
		return  Math.min( a , b);
	}
	
	public ComplexNumber max(final ComplexNumber a, final ComplexNumber b) {
		final ComplexNumber ret=ComplexNumber.zero();
		final double re=max(a.Re(),b.Re());
		final double im=max(a.Im(),b.Im());
		ret.first=re;
		ret.second=im;
		return  ret;
	}
	
/////////////////////////////////////////	
	
	// to be implemented by the primitive operation types
	public abstract <V extends Number>  E addE(E a, V b);
	
	// to be implemented by the primitive operation types
	public abstract <V extends Number>  E subE(E a, V b);

	// to be implemented by the primitive operation types
	public abstract <V extends Number> E multE(E a, V b);
	
	// to be implemented by the primitive operation types
	public abstract <V extends Number> E divE(E a, V b);
	
	// to be implemented by the primitive operation types
	public abstract <V extends Number> E minE(E a, V b);
	
	// to be implemented by the primitive operation types
	public abstract <V extends Number>  E maxE(E a, V b);
	
///////////////////////////////	
	
	
	
}
