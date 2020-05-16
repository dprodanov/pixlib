package ijaux.datatype;

import ijaux.datatype.access.ElementAccess;


/*
 * based on the Pair class
 */
public class ComplexArray 
extends Pair<double[],double[]> 

implements ProductSpaceReflexive<ComplexArray,ComplexArray>, 
ElementAccess<Integer, ComplexNumber>, Typing {
 
 
	private int length=0;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5513067895742945334L;
	private boolean isPolar=false;

	/**
	 * 
	 * @param capacity
	 * @return
	 */
	public static ComplexArray create(int capacity) {	 
		double[]a=new double[capacity];
		double[]b=new double[capacity];
		return new ComplexArray(a,b, false);
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param polar
	 */
	public ComplexArray(double[] a, double[] b, boolean polar) {
		super(a,b);
		isPolar=polar;
		if (a==null && !polar)
			a=new double[b.length]; // shortcut for pure imaginary case
		if (b==null && !polar)
			b=new double[a.length]; // shortcut for pure real case
		
		length=a.length;
		if (a.length!=b.length)
			throw new IllegalArgumentException("array size mismatch");
	
		if (polar){
			polarForm(a,b);
		}
	}
	

	/**
	 * only real valued input
	 * @param a
	 */
	public ComplexArray(double[] a) {
		super(a,null);
		double[] b=new double [a.length];
		second=b;
		length=a.length;
	}
	
	/**
	 * 
	 * @return
	 */
	public IComplexArray join() {
		return new IComplexArray (first, second);
	}
	
	/**
	 * 
	 * @param magnitude
	 * @param angle
	 */
	public void polarForm(double[] magnitude, double[] angle) {
		if (magnitude.length!=angle.length)
			throw new IllegalArgumentException("array size mismatch");
		first=new double[magnitude.length];
		second=new double[magnitude.length];
		isPolar=true;
		  for (int i=0; i<first.length; i++) {
			  first[i]=(magnitude[i] * Math.cos(angle[i]));
			  second[i]=(magnitude[i] * Math.sin(angle[i]));
		  }

	}
	
	/**
	 * 
	 * @param magnitude
	 * @param angle
	 * @return
	 */
	public static ComplexArray fromPolar(double[] magnitude, double[] angle) {
		    return new ComplexArray (magnitude, angle, true);
	}

	/**
	 * 
	 * @return
	 */
	public double[] Re(){
		return first;
	}
	
	/**
	 * 
	 * @return
	 */
	public double[] Im(){
		return second;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public double Re(int i){
		return first[i];
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public double Im(int i){
		return second[i];
	}
	
	/**
	 * 
	 */
	public ComplexArray clone() {
		double[] sfirst=first.clone();
		double[] ssecond=second.clone();
		return new ComplexArray(sfirst, ssecond, false);
	}
	
	/**
	 * 
	 * @return
	 */
	public ComplexArray conjugate() {
		//double[] sfirst=new double[first.length];
		double[] sfirst=first.clone();
		double[] ssecond=new double[first.length];
		
		for (int i=0; i<first.length; i++)
			ssecond[i]=-second[i];
		
		//System.arraycopy(first, 0,  sfirst, 0, first.length);
		
	    return new ComplexArray(sfirst, ssecond, false);
	  }
	
	/**
    * Modulus of this Complex number 
    * (the distance from the origin in polar coordinates).
    * @return |z| where z is this Complex number.
	*/
	public double mod(int i) {
	    if (first[i]!=0 || second[i]!=0) {
	        return Math.sqrt(first[i]*first[i]+second[i]*second[i]);
	    } else {
	        return 0d;
	    }
	}
	
	/**
	 * 
	 * @return
	 */
	public double[] mod() {
	    double [] m=new double [first.length];
		for (int i=0; i<first.length; i++)
	        m[i]=Math.sqrt(first[i]*first[i]+second[i]*second[i]);
	     
	    return m;
	}

	/**
	 *   Argument of this Complex number 
	 *   (the angle in radians with the x-axis in polar coordinates).
	 *  @return arg(z) where z is this Complex number.
	 */
	public double arg(int i) {
		//return Math.atan2(first[i],second[i]);
		return Math.atan2(second[i],first[i]);
	}

	/**
	 * 
	 * @return
	 */
	public double[] arg() {
		double [] ret=new double [first.length];
		for (int i=0; i<first.length; i++) {
			//ret[i]=Math.atan2(first[i],second[i]);
			ret[i]=Math.atan2(second[i],first[i]);
		}
		return ret;
	}

	/**
	 * 
	 */
	public ComplexArray invs() {
		final double[] s=norm2();
		double[] a=new double [first.length]; 
		double[] b=a.clone();
		for (int i=0;i <first.length; i++) {
			a[i]=first[i]/s[i];
			b[i]=-second[i]/s[i];
		}
		return new ComplexArray(a, b, false);
	}

	/**  
	 * RE : r[1]*r[2] - i[1]*i[2] 
	 * IM : i[1]*r[2] + r[1]*i[2]
	*/
	@Override
	public void mult( ComplexArray b) {
		if (validate(b)) {
			double tmpr;
			for (int i=0; i<first.length; i++) {
				tmpr =Re(i)*b.Re(i) - Im(i)*b.Im(i);
				second[i]=Re(i)*b.Im(i) + Im(i)*b.Re(i);
				first[i]= tmpr;
				
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public ComplexArray norm() { 
		double[] z=new double[length];
		return new ComplexArray(norm2(), z, false); 
	}
	
	/**
	 * 
	 * @return
	 */
	public double[] norm2() {
		double[] dnorm=new double[first.length];
		for(int i=0; i<first.length; i++) {
			dnorm[i]=first[i]*first[i]+second[i]*second[i];
		}
		return dnorm;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public double  norm2(int i) {
		return first[i]*first[i]+second[i]*second[i];
	}

	@Override
	public void add(ComplexArray a) {
		if (validate(a)) {
			for(int i=0; i<first.length; i++) {
				first[i]+= a.Re(i); 
				second[i]+=a.Im(i); 
			}
		}
	}

	/**
	 * 
	 * @param a
	 */
	public void add(double a) {
			for(int i=0; i<first.length; i++) {
				first[i]+= a; 
			}
	}

	/**
	 * 
	 */
	public void inv() {
		for(int i=0; i<first.length; i++) {
			first[i]=-first[i];
			second[i]=-second[i];
		}
	}

	/**
	 * 
	 * @param i
	 */
	public void inv(int i) {
		  	first[i]=-first[i];
			second[i]=-second[i];
	}
	
	/**
	 * 
	 */
	@Override
	public void scale(double scalar) {
		for(int i=0; i<first.length; i++) {
			first[i]*=scalar; 
			second[i]*=scalar;
		}
	}

	/**
	 * 
	 */
	public void scale() {
		final double scalar=1/(double)first.length;
		for(int i=0; i<first.length; i++) {
			first[i]*=scalar; 
			second[i]*=scalar;
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void sub(ComplexArray a) {
		if (validate(a)) {
			for(int i=0; i<first.length; i++) {
				first[i]-=a.Re(i);
				second[i]-=a.Im(i); 
			}
		}
	}

	/**
	 * 	s: (r[2]^2+i[2]^2)
	 *  RE: (r[1]*r[2]+i[1]*i[2])/s
	 *  IM: (i[1]*r[2]-r[1]*i[2])/s
	 *  @param a
	 */
	@Override
	public void div(ComplexArray a) {
		if (validate(a)) {
			double tmpr, s;
			for (int i=0; i<first.length; i++) {
				s= first[i]*first[i]+second[i]*second[i];
				tmpr=(Re(i)*a.Re(i) + Im(i)*a.Im(i))/s;
				second[i]=(Im(i)*a.Re(i) - Re(i)*a.Im(i))/s;
				first[i]=tmpr;
			}
			}
	}

	/**
	 * 
	 * @param a
	 * @return
	 */
	public boolean validate(ComplexArray a) {
		return first.length==a.first.length && second.length==a.second.length;
	}

	/**
	 * 
	 */
	@Override
	public ComplexNumber element(int index) {
		return new ComplexNumber(first[index], second[index], false);
	}

	/**
	 * 
	 */
	@Override
	public void putE(int index, ComplexNumber value) {
		first[index]=value.Re();
		second[index]=value.Im();
	}

	/**
	 * 
	 */
	@Override
	public ComplexNumber element(Integer coords) {
		return new ComplexNumber(first[coords], second[coords], false);
	}

	/**
	 * 
	 */
	@Override
	public void putV(Integer coords, ComplexNumber value) {
		putE(coords,value);
	}

	/**
	 * 
	 */
	@Override
	public void put(Pair<Integer, ComplexNumber> pair) {
		putE(pair.first, pair.second);
	}

	/**
	 * 
	 */
	@Override
	public int[] size() { 
		return new int[]{length,0};
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
	/*
	@Override
	public boolean eq(Class<?> c) {
		return c==double.class;
	}
	*/
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		String astr="[ ";
		if (!isPolar) {
			for(int i=0; i<length; i++) {
				astr+=i+" ("+first[i]+" "+ second[i]+"j)\n";
			}

		} else {
			for(int i=0; i<length; i++) {
				astr+=i+" (r="+first[i]+" fi="+ second[i]+")\n";
			}
		}
		 astr+=" ]";
		 return astr;
	}

} //END
