package ijaux.datatype;

import ijaux.datatype.access.ElementAccess;


/*
 *  interlaced complex array. Good for signal processing
 */
public class IComplexArray implements ProductSpaceReflexive<double[],IComplexArray>, 
ElementAccess<Integer, ComplexNumber>, Typing {
 
	private double[] data=null;
	 
	private static final long serialVersionUID = -4789934942912445718L;
	private int length=0;
 
	public static IComplexArray create(int capacity) {	 
		double[]a=new double[2*capacity];	
		return new IComplexArray(a);
	}
	
	public IComplexArray(double[] a, double[] b) {	
		if (a!=null && b!=null) {
			length=2*a.length;
			if (a.length!=b.length)
				throw new IllegalArgumentException("array size mismatch");		
			data=new double[length];
			
			for (int i=0, k=0; i< a.length; k+=2, i++) {
				data[k]=a[i];
				data[k+1]=b[i];
			}
			return;
		} 
		if (a!=null && b==null) {
			length=2*a.length;				
			data=new double[length];	
			// filling even positions
			for (int i=0, k=0; i< a.length; k+=2, i++) {
				data[k]=a[i];
			}
			return;
		} 
		if (a==null && b!=null) {
			length=2*b.length;				
			data=new double[length];	
			// filling odd positions
			for (int i=1, k=0; i< b.length; k+=2, i++) {
				data[k]=b[i];
			}
			return;
		} 
	}
	
	/*
	 *  
	 */
	public IComplexArray(double[] a) {
		length=a.length;
		data=a;
	}
	
	/*
	 *  
	 */
	public IComplexArray(float[] a) {
		length=a.length;
		data=new double[length];
		for (int i=0; i<a.length; i++)
			data[i]= (double) a[i];
	}
	
	public void polarForm(double[] magnitude, double[] angle) {
		if (magnitude.length!=angle.length)
			throw new IllegalArgumentException("array size mismatch");
		length=2*magnitude.length;
		data=new double[ length];
		for (int i=0; i<magnitude.length; i+=2) {
			data[i]=(magnitude[i] * Math.cos(angle[i]));
			data[i+1]=(magnitude[i] * Math.sin(angle[i]));
		}

	}
	
	ComplexArray split() {
		double[] re=Re();
		double[] im=Im();		
		return new ComplexArray(re, im, false);
	}
	
/*	public static IComplexArray fromPolar(double[] magnitude, double[] angle) {
		    return new IComplexArray (magnitude, angle, true);
	}*/

	public double[] Re(){
		double[] ret =new double[length/2];
		for (int i=0; i<ret.length; i++)
			ret[i]=data[i<<1];
		return ret;
	}
	
	public double[] Im(){
		double[] ret =new double[length/2];
		for (int i=0; i<ret.length; i++)
			ret[i]=data[(i<<1)+1];
		return ret;
	}
	 
	public double Re(int i){
		return data[i];
	}
	
	public double Im(int i){
		return data[i+1];
	}
	
	public IComplexArray clone() {
		double[] sfirst=data.clone();
		return new IComplexArray(sfirst);
	}
	
	public IComplexArray conjugate() {
		double[] xi=data.clone();			
		for (int i=1; i<length; i+=2)
			xi[i]=-xi[i];		
	    return new IComplexArray(xi);
	}
	
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
	
	public double[] mod() {
	    double [] m=new double [length/2];
		for (int i=0; i<length; i+=2)
	        m[i>>1]=Math.sqrt(data[i]*data[i]+data[i+1]*data[i+1]);     
	    return m;
	}

	@Override
	public double[] norm(){
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

	public double[] arg() {
		double [] ret=new double [length/2];
		for (int i=0; i<length; i+=2) {
			//ret[i]=Math.atan2(first[i],second[i]);
			ret[i>>1]=Math.atan2(data[i+1],data[i]);
		}
		return ret;
	}

	

	public IComplexArray invs() {
		final double[] s=norm2();
		double[] a=new double [length]; 
		for (int i=0;i <length; i++) {
			a[i]=data[i]/s[i];
		}
		return new IComplexArray(a);
	}

	/* RE : r[1]*r[2] - i[1]*i[2] 
	 * IM : i[1]*r[2] + r[1]*i[2]
	*/
	@Override
	public void mult(IComplexArray b) {
		if (validate(b)) {
			double tmpr;
			for (int i=0; i<length; i+=2) {
 				tmpr=Re(i)*b.Re(i) - Im(i)*b.Im(i); 
 				data[i+1]=Re(i)*b.Im(i) + Im(i)*b.Re(i);
				data[i]=tmpr;
			}
		}
	}

	
	
	public double[] norm2() {
		double[] dnorm=new double[length/2];
		for(int i=0; i<length; i++) {
			dnorm[i>>1]=data[i]*data[i]+data[i+1]*data[i+1];
		}
		return dnorm;
	}
	
	public double  norm2(int i) {
		return data[i]*data[i]+data[i+1]*data[i+1];
	}

	@Override
	public void add(IComplexArray a) {
		if (validate(a)) {
			for(int i=0; i<length; i++) {
				data[i]+= a.data[i]; 
			}
		}
	}

	public void add(double a) {
			for(int i=0; i<length; i++) {
				data[i]+= a; 
			}
	}

	public void inv() {
		for(int i=0; i<length; i++) {
			data[i]=-data[i];
		}
	}

	public void inv(int i) {
		  	data[i]=-data[i];
			data[i+1]=-data[i+1];
	}
	
	@Override
	public void scale(double scalar) {
		for(int i=0; i<length; i++) {
			data[i]*=scalar; 
		}
	}

 
	
	@Override
	public void sub(IComplexArray a) {
		if (validate(a)) {
			for(int i=0; i<length; i+=2) {
				data[i]-=a.Re(i);
				data[i+1]-=a.Im(i); 
			}
		}
	}

	/*
	 * 	s: (r[2]^2+i[2]^2)
	 *  RE: (r[1]*r[2]+i[1]*i[2])/s
	 *  IM: (i[1]*r[2]-r[1]*i[2])/s
	 */
	@Override
	public void div(IComplexArray a) {
		if (validate(a)) {
			double tmpr,s;
			for (int i=0; i<length; i+=2) {
				s= data[i]*data[i]+data[i+1]*data[i+1];
				tmpr=(Re(i)*a.Re(i) + Im(i)*a.Im(i))/s;
				data[i+1]=(Im(i)*a.Re(i) - Re(i)*a.Im(i))/s;
				data[i]=tmpr;
			}
			}
	}

	public boolean validate(IComplexArray a) {
		return length==a.length;
	}

	@Override
	public ComplexNumber element(int index) {
		return new ComplexNumber(data[index], data[index+1], false);
	}

	@Override
	public void putE(int index, ComplexNumber value) {
		data[index]=value.Re();
		data[index+1]=value.Im();
	}

	@Override
	public ComplexNumber element(Integer coords) {
		return new ComplexNumber(data[coords], data[coords+1], false);
	}

	@Override
	public void putV(Integer coords, ComplexNumber value) {
		putE(coords,value);
	}

	@Override
	public void put(Pair<Integer, ComplexNumber> pair) {
		putE(pair.first, pair.second);
	}

	@Override
	public int[] size() { 
		return new int[]{length/2,2};
	}
	
	@Override
	public long length() { 
		return length;
	}

	@Override
	public Class<?> getType() {
		return double.class;
	}

	@Override
	public boolean eq(Class<?> c) {
		return c==double.class;
	}
	
	@Override
	public String toString() {
		String astr="[\n";
		for(int i=0; i<length; i+=2) {
			astr+=" "+(i>>1)+" ("+data[i]+" "+ data[i+1]+"j)\n";
		}	
		astr+="]";
		return astr;
	}

	

}
