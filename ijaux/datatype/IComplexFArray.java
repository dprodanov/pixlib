package ijaux.datatype;

import ijaux.datatype.access.ElementAccess;


/*
 *  interlaced complex array. Good for signal processing
 */
public class IComplexFArray implements ProductSpaceReflexive<float[],IComplexFArray>, 
ElementAccess<Integer, ComplexFNumber>, Typing {
 
	private float[] data=null;
	 
	private static final long serialVersionUID = -4789934942912445718L;
	private int length=0;
 
	/**
	 * 
	 * @param capacity
	 * @return
	 */
	public static IComplexFArray create(int capacity) {	 
		double[]a=new double[2*capacity];	
		return new IComplexFArray(a);
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 */
	public IComplexFArray(float[] a, float[] b) {	
		if (a!=null && b!=null) {
			length=2*a.length;
			if (a.length!=b.length)
				throw new IllegalArgumentException("array size mismatch");		
			data=new float[length];
			
			for (int i=0, k=0; i< a.length; k+=2, i++) {
				data[k]=a[i];
				data[k+1]=b[i];
			}
			return;
		} 
		if (a!=null && b==null) {
			length=2*a.length;				
			data=new float[length];	
			// filling even positions
			for (int i=0, k=0; i< a.length; k+=2, i++) {
				data[k]=a[i];
			}
			return;
		} 
		if (a==null && b!=null) {
			length=2*b.length;				
			data=new float[length];	
			// filling odd positions
			for (int i=1, k=0; i< b.length; k+=2, i++) {
				data[k]=b[i];
			}
			return;
		} 
	}
	
	/**
	 * 
	 * @param a
	 */
	public IComplexFArray(float[] a) {
		length=a.length;
		data=a;
	}
	
	/**
	 * 
	 * @param a
	 */
	public IComplexFArray(double[] a) {
		length=a.length;
		data=new float[length];
		for (int i=0; i<a.length; i++)
			data[i]=  (float) a[i];
	}
	
	/**
	 * 
	 * @param magnitude
	 * @param angle
	 */
	public void polarForm(double[] magnitude, double[] angle) {
		if (magnitude.length!=angle.length)
			throw new IllegalArgumentException("array size mismatch");
		length=2*magnitude.length;
		data=new float[ length];
		for (int i=0; i<magnitude.length; i+=2) {
			data[i]=(float) (magnitude[i] * Math.cos(angle[i]));
			data[i+1]=(float) (magnitude[i] * Math.sin(angle[i]));
		}

	}
	
	/**
	 * 
	 * @return
	 */
	ComplexFArray split() {
		float[] re=Re();
		float[] im=Im();		
		return new ComplexFArray(re, im, false);
	}
	
	/**
	 * 
	 * @return
	 */
	public float[] Re(){
		float[] ret =new float[length/2];
		for (int i=0; i<ret.length; i++)
			ret[i]=data[i<<1];
		return ret;
	}
	
	/**
	 * 
	 * @return
	 */
	public float[] Im(){
		float[] ret =new float[length/2];
		for (int i=0; i<ret.length; i++)
			ret[i]=data[(i<<1)+1];
		return ret;
	}
	 
	/**
	 * 
	 * @param i
	 * @return
	 */
	public float Re(int i){
		return data[i];
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public float Im(int i){
		return data[i+1];
	}
	
	/**
	 * 
	 */
	public IComplexFArray clone() {
		float[] sfirst=data.clone();
		return new IComplexFArray(sfirst);
	}
	
	/**
	 * 
	 * @return
	 */
	public IComplexFArray conjugate() {
		float[] xi=data.clone();			
		for (int i=1; i<length; i+=2)
			xi[i]=-xi[i];		
	    return new IComplexFArray(xi);
	}
	
	/**
	 * 
	 */
	public void uconjugate() {			
		for (int i=1; i<length; i+=2)
			data[i]=-data[i];		
	}
	
	 /**
    Modulus of this Complex number
    (the distance from the origin in polar coordinates).
    @return |z| where z is this Complex number.
	*/
	public double mod(int i) {
	    if (data[i]!=0 || data[i+1]!=0) {
	        return Math.sqrt(data[i]*data[i]+data[i+1]*data[i+1]);
	    } else {
	        return 0d;
	    }
	}
	
	/**
	 * 
	 * @return
	 */
	public float[] mod() {
	    float [] m=new float [length/2];
		for (int i=0; i<length; i+=2)
	        m[i>>1]=(float) Math.sqrt(data[i]*data[i]+data[i+1]*data[i+1]);     
	    return m;
	}

	/**
	 * 
	 */
	@Override
	public float[] norm(){
		return mod();
	}
	
	/**
	    Argument of this Complex number 
	    (the angle in radians with the x-axis in polar coordinates).
	    @return arg(z) where z is this Complex number.
	 */
	public double arg(int i) {
		//return Math.atan2(first[i],second[i]);
		return Math.atan2(data[i+1],data[i]);
	}

	/**
	 * 
	 * @return
	 */
	public double[] arg() {
		double [] ret=new double [length/2];
		for (int i=0; i<length; i+=2) {
			//ret[i]=Math.atan2(first[i],second[i]);
			ret[i>>1]=Math.atan2(data[i+1],data[i]);
		}
		return ret;
	}

	/**
	 * 
	 */
	public IComplexFArray invs() {
		final double[] s=norm2();
		double[] a=new double [length]; 
		for (int i=0;i <length; i++) {
			a[i]=data[i]/s[i];
		}
		return new IComplexFArray(a);
	}

	/**
	 * RE : r[1]*r[2] - i[1]*i[2] 
	 * IM : i[1]*r[2] + r[1]*i[2]
	*/
	@Override
	public void mult(IComplexFArray b) {
		if (validate(b)) {
			double tmpr;
			for (int i=0; i<length; i+=2) {
 
				tmpr=(float) (Re(i)*b.Re(i) - Im(i)*b.Im(i)); 
 
				data[i+1]=(float) (Re(i)*b.Im(i) + Im(i)*b.Re(i));
				data[i]=(float) tmpr;
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public double[] norm2() {
		double[] dnorm=new double[length/2];
		for(int i=0; i<length; i++) {
			dnorm[i>>1]=data[i]*data[i]+data[i+1]*data[i+1];
		}
		return dnorm;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public float  norm2(int i) {
		return data[i]*data[i]+data[i+1]*data[i+1];
	}

	/**
	 * 
	 */
	@Override
	public void add(IComplexFArray a) {
		if (validate(a)) {
			for(int i=0; i<length; i++) {
				data[i]+= a.data[i]; 
			}
		}
	}

	/**
	 * 
	 * @param a
	 */
	public void add(double a) {
			for(int i=0; i<length; i++) {
				data[i]+= a; 
			}
	}

	/**
	 * 
	 */
	public void inv() {
		for(int i=0; i<length; i++) {
			data[i]=-data[i];
		}
	}

	/**
	 * 
	 * @param i
	 */
	public void inv(int i) {
		  	data[i]=-data[i];
			data[i+1]=-data[i+1];
	}
	
	/**
	 * 
	 */
	@Override
	public void scale(double scalar) {
		for(int i=0; i<length; i++) {
			data[i]*=scalar; 
		}
	}

 
	/**
	 * 
	 */
	@Override
	public void sub(IComplexFArray a) {
		if (validate(a)) {
			for(int i=0; i<length; i+=2) {
				data[i]-=a.Re(i);
				data[i+1]-=a.Im(i); 
			}
		}
	}

	/**
	 * 	s: (r[2]^2+i[2]^2)
	 *  RE: (r[1]*r[2]+i[1]*i[2])/s
	 *  IM: (i[1]*r[2]-r[1]*i[2])/s
	 */
	@Override
	public void div(IComplexFArray a) {
		if (validate(a)) {
			double tmpr,s;
			for (int i=0; i<length; i+=2) {
				s=data[i]*data[i]+data[i+1]*data[i+1];
				tmpr=(Re(i)*a.Re(i) + Im(i)*a.Im(i))/s;
				data[i+1]=(float) ((Im(i)*a.Re(i) - Re(i)*a.Im(i))/s);
				data[i]=(float) tmpr;
			}
			}
	}

	/**
	 * 
	 * @param a
	 * @return
	 */
	public boolean validate(IComplexFArray a) {
		return length==a.length;
	}

	/**
	 * 
	 */
	@Override
	public ComplexFNumber element(int index) {
		return new ComplexFNumber(data[index], data[index+1], false);
	}

	/**
	 * 
	 */
	@Override
	public void putE(int index, ComplexFNumber value) {
		data[index]=value.Re();
		data[index+1]=value.Im();
	}
	
	/**
	 * 
	 * @param index
	 * @param real
	 * @param imag
	 */
	public void putF(int index, float real, float imag) {
		data[index]=real;
		data[index+1]=imag;
	}

	/**
	 * 
	 */
	@Override
	public ComplexFNumber element(Integer coords) {
		return new ComplexFNumber(data[coords], data[coords+1], false);
	}

	/**
	 * 
	 */
	@Override
	public void putV(Integer coords, ComplexFNumber value) {
		putE(coords,value);
	}

	/**
	 * 
	 */
	@Override
	public void put(Pair<Integer, ComplexFNumber> pair) {
		putE(pair.first, pair.second);
	}

	/**
	 * 
	 */
	@Override
	public int[] size() { 
		return new int[]{length/2,2};
	}
	
	/**
	 * 
	 */
	@Override
	public long length() { 
		return length;
	}

	/**
	 * 
	 */
	@Override
	public Class<?> getType() {
		return double.class;
	}

	/**
	 * 
	 */
	@Override
	public boolean eq(Class<?> c) {
		return c==double.class;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		String astr="[\n";
		for(int i=0; i<length; i+=2) {
			astr+=" "+(i>>1)+" ("+data[i]+" "+ data[i+1]+"j)\n";
		}	
		astr+="]";
		return astr;
	}

	/**
	 * 
	 * @return
	 */
	public float[] getArray() {
		return data;
	}

} //
