package ijaux.datatype;

import ijaux.datatype.access.ElementAccess;


/*
 * based on the Pair class
 */
public class ComplexFArray 
extends Pair<float[],float[]> 

implements ProductSpaceReflexive<ComplexFArray,ComplexFArray>, 
ElementAccess<Integer, ComplexFNumber>, Typing {
 
 
	private int length=0;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5513067895742945334L;
	private boolean isPolar=false;

	public static ComplexFArray create(int capacity) {	 
		float[]a=new float[capacity];
		float[]b=new float[capacity];
		return new ComplexFArray(a,b, false);
	}
	
	public ComplexFArray(float[] a, float[] b, boolean polar) {
		super(a,b);
		isPolar=polar;
		if (a==null && !polar)
			a=new float[b.length]; // shortcut for pure imaginary case
		if (b==null && !polar)
			b=new float[a.length]; // shortcut for pure real case
		
		length=a.length;
		if (a.length!=b.length)
			throw new IllegalArgumentException("array size mismatch");
	
		if (polar){
			polarForm(a,b);
		}
	}
	
	/*
	 *  only real valued 
	 */
	public ComplexFArray(float[] a) {
		super(a,null);
		float[] b=new float [a.length];
		second=b;
		length=a.length;
	}
	
	public IComplexFArray join() {
		return new IComplexFArray (first, second);
	}
	
	public void polarForm(float[] magnitude, float[] angle) {
		if (magnitude.length!=angle.length)
			throw new IllegalArgumentException("array size mismatch");
		first=new float[magnitude.length];
		second=new float[magnitude.length];
		isPolar=true;
		  for (int i=0; i<first.length; i++) {
			  first[i]=(float) (magnitude[i] * Math.cos(angle[i]));
			  second[i]=(float) (magnitude[i] * Math.sin(angle[i]));
		  }

	}
	
	public static ComplexFArray fromPolar(float[] magnitude, float[] angle) {
		    return new ComplexFArray (magnitude, angle, true);
	}

	public float[] Re(){
		return first;
	}
	
	public float[] Im(){
		return second;
	}
	 
	public float Re(int i){
		return first[i];
	}
	
	public float Im(int i){
		return second[i];
	}
	
	public ComplexFArray clone() {
		float[] sfirst=first.clone();
		float[] ssecond=second.clone();
		return new ComplexFArray(sfirst, ssecond, false);
	}
	
	public ComplexFArray conjugate() {
		//double[] sfirst=new double[first.length];
		float[] sfirst=first.clone();
		float[] ssecond=new float[first.length];
		
		for (int i=0; i<first.length; i++)
			ssecond[i]=-second[i];
		
		//System.arraycopy(first, 0,  sfirst, 0, first.length);
		
	    return new ComplexFArray(sfirst, ssecond, false);
	  }
	 /**
    Modulus of this Complex number
    (the distance from the origin in polar coordinates).
    @return |z| where z is this Complex number.
	*/
	public double mod(int i) {
	    if (first[i]!=0 || second[i]!=0) {
	        return Math.sqrt(first[i]*first[i]+second[i]*second[i]);
	    } else {
	        return 0d;
	    }
	}
	
	public double[] mod() {
	    double [] m=new double [first.length];
		for (int i=0; i<first.length; i++)
	        m[i]=Math.sqrt(first[i]*first[i]+second[i]*second[i]);
	     
	    return m;
	}

	/**
	    Argument of this Complex number 
	    (the angle in radians with the x-axis in polar coordinates).
	    @return arg(z) where z is this Complex number.
	 */
	public double arg(int i) {
		//return Math.atan2(first[i],second[i]);
		return Math.atan2(second[i],first[i]);
	}

	public double[] arg() {
		double [] ret=new double [first.length];
		for (int i=0; i<first.length; i++) {
			//ret[i]=Math.atan2(first[i],second[i]);
			ret[i]=Math.atan2(second[i],first[i]);
		}
		return ret;
	}

	
	/*

	 public String toString() {
		    if (this.first == 0) {
		      if (this.second == 0) {
		        return "0";
		      } else {
		        return (this.second + "i");
		      }
		    } else {
		      if (this.second == 0) {
		        return String.valueOf(this.first);
		      } else if (this.second < 0) {
		        return(this.first + " " + this.second + "i");
		      } else {
		        return(this.first + " +" + this.second + "i");
		      }
		    }
		  }
*/
	public ComplexFArray invs() {
		final float[] s=norm2();
		float[] a=new float [first.length]; 
		float[] b=a.clone();
		for (int i=0;i <first.length; i++) {
			a[i]=first[i]/s[i];
			b[i]=-second[i]/s[i];
		}
		return new ComplexFArray(a, b, false);
	}

	/* RE : r[1]*r[2] - i[1]*i[2] 
	 * IM : i[1]*r[2] + r[1]*i[2]
	*/
	@Override
 
	public void mult(ComplexFArray b) {
  		if (validate(b)) {
			double tmpr, tmpi;
			for (int i=0; i<length; i+=2) {
				tmpr=(float) (Re(i)*b.Re(i) - Im(i)*b.Im(i)); 
				tmpi=(float) (Re(i)*b.Im(i) + Im(i)*b.Re(i));
				first[i]=  (float) tmpr;
				second[i]= (float) tmpi;			
 
			}
		}
	}
	
	@Override
	public ComplexFArray norm() { 
		float[] z=new float[length];
		return new ComplexFArray(norm2(), z, false); 
	}
	
	public float[] norm2() {
		float[] dnorm=new float[first.length];
		for(int i=0; i<first.length; i++) {
			dnorm[i]=first[i]*first[i]+second[i]*second[i];
		}
		return dnorm;
	}
	
	public double  norm2(int i) {
		return first[i]*first[i]+second[i]*second[i];
	}

	@Override
	public void add(ComplexFArray a) {
		if (validate(a)) {
			for(int i=0; i<first.length; i++) {
				first[i]+= a.Re(i); 
				second[i]+=a.Im(i); 
			}
		}
	}

	public void add(double a) {
			for(int i=0; i<first.length; i++) {
				first[i]+= a; 
			}
	}

	public void inv() {
		for(int i=0; i<first.length; i++) {
			first[i]=-first[i];
			second[i]=-second[i];
		}
	}

	public void inv(int i) {
		  	first[i]=-first[i];
			second[i]=-second[i];
	}
	
	@Override
	public void scale(double scalar) {
		for(int i=0; i<first.length; i++) {
			first[i]*=scalar; 
			second[i]*=scalar;
		}
	}

	public void scale() {
		final double scalar=1/(double)first.length;
		for(int i=0; i<first.length; i++) {
			first[i]*=scalar; 
			second[i]*=scalar;
		}
	}
	@Override
	public void sub(ComplexFArray a) {
		if (validate(a)) {
			for(int i=0; i<first.length; i++) {
				first[i]-=a.Re(i);
				second[i]-=a.Im(i); 
			}
		}
	}

	/*
	 * 	s: (r[2]^2+i[2]^2)
	 *  RE: (r[1]*r[2]+i[1]*i[2])/s
	 *  IM: (i[1]*r[2]-r[1]*i[2])/s
	 */
	@Override
	public void div(ComplexFArray a) {
		if (validate(a)) {
			double tmpr, s;
			for (int i=0; i<first.length; i++) {
				s= first[i]*first[i]+second[i]*second[i];
				tmpr=(Re(i)*a.Re(i) + Im(i)*a.Im(i))/s;
				second[i]=(float) ((Im(i)*a.Re(i) - Re(i)*a.Im(i))/s);
				first[i]=(float) tmpr;
			}
			}
	}

	public boolean validate(ComplexFArray a) {
		return first.length==a.first.length && second.length==a.second.length;
	}

	@Override
	public ComplexFNumber element(int index) {
		return new ComplexFNumber(first[index], second[index], false);
	}

	@Override
	public void putE(int index, ComplexFNumber value) {
		first[index]=value.Re();
		second[index]=value.Im();
	}

	@Override
	public ComplexFNumber element(Integer coords) {
		return new ComplexFNumber(first[coords], second[coords], false);
	}

	@Override
	public void putV(Integer coords, ComplexFNumber value) {
		putE(coords,value);
	}

	@Override
	public void put(Pair<Integer, ComplexFNumber> pair) {
		putE(pair.first, pair.second);
	}

	@Override
	public int[] size() { 
		return new int[]{length,0};
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
 
}
