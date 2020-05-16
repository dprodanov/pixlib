package ijaux.datatype;


/**
 * 
 * @author Dimiter Prodanov
 *
 */
public class ComplexNumber extends Pair<Double,Double> 

implements ProductSpaceReflexive<ComplexNumber,ComplexNumber>, Typing {
 
 
	private static final long serialVersionUID = -5513067895742945334L;
	private boolean isPolar=false;
	
	/*
	 *  specific numbers
	 */
	/**
	* A ComplexNumber  "0 + 0 i".
	*/
	public static ComplexNumber zero() {
		return new ComplexNumber(0,0,false);
	}
	
	/**
	* A ComplexNumber representing "NaN + NaN i".
	*/
	public static ComplexNumber NaN() {
		return new ComplexNumber(Double.NaN, Double.NaN, false);
	}

	/**
	* A ComplexNumber representing "+INF + INF i"
	*/
	public static final ComplexNumber INF() {
		return new ComplexNumber(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, false);
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param polar
	 */
	public ComplexNumber(double a, double b, boolean polar) {
		super(a,b);
		if (polar){
			polarForm(a,b);
		}
		isPolar=polar;
	}
	
	/**
	 * 
	 * @param magnitude
	 * @param angle
	 */
	public void polarForm(double magnitude, double angle) {
		   
	      first=(magnitude * Math.cos(angle));
	      second=(magnitude * Math.sin(angle));
	      //isPolar=true;

	}
	
	/**
	 * 
	 * @param magnitude
	 * @param angle
	 * @return
	 */
	public static ComplexNumber fromPolar(double magnitude, double angle) {
		    double first=(magnitude * Math.cos(angle));
		    double second=(magnitude * Math.sin(angle));
		    return (ComplexNumber) new ComplexNumber (first, second, false);

	}

	/**
	 * 
	 * @return
	 */
	public double Re(){
		return first;
	}
	
	/**
	 * 
	 * @return
	 */
	public double Im(){
		return second;
	}
	 
	/**
	 * 
	 * @return
	 */
	public ComplexNumber conjugate() {
	    return new ComplexNumber(first, second * (-1), false);
	  }


	 /**
    Modulus of this Complex number
    (the distance from the origin in polar coordinates).
    @return |z| where z is this Complex number.
	*/
	public double mod() {
	    if (first!=0 || second!=0) {
	        return Math.sqrt(first*first+second*second);
	    } else {
	        return 0d;
	    }
	}

	/**
	    Argument of this Complex number 
	    (the angle in radians with the x-axis in polar coordinates).
	    @return arg(z) where z is this Complex number.
	 */
	public double arg() {
		//return Math.atan2(first,second);
		return Math.atan2(second,first);
	}


	/**
	 * 
	 */
	 public String toString() {
 
		 String astr="";
			if (!isPolar) {		
					astr+=" ("+first+" "+ second+"j)";
			} else {
					astr+=" (r="+first+" fi="+ second+")";
			}
			 astr+="";
			 return astr;
	 }
	 
	 /**
	  * 
	  */
	public ComplexNumber invs() {
		double s=mod();
		s*=s;
		return new ComplexNumber(Re()/s, -Im()/s, false);
	}

	/** 
	 * RE : r[1]*r[2] - i[1]*i[2] 
	 * IM : i[1]*r[2] + r[1]*i[2]
	*/
	@Override
	public void mult( ComplexNumber b) {
		final double tmpr =first*b.Re() - second*b.Im(); 
		second =  second*b.Re() + first*b.Im();
		first=tmpr;
	} 
	
	/**
	 * 
	 */
	@Override
	public ComplexNumber norm() {
		return new ComplexNumber(mod()*mod(), 0, false); 
	}

	/**
	 * 
	 */
	@Override
	public void add(ComplexNumber a) {
		first+= a.Re(); 
		second+=a.Im(); 
	}

	public void inv() {
		first=-first;
		second=-second;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 */
	public void setD(double a, double b ) {
		first=a;
		second=b;
	}
	
	/**
	 * 
	 */
	@Override
	public void scale(double scalar) {
		first=scalar*Re(); 
		second=scalar*Im();
	}

	/**
	 * 
	 */
	@Override
	public void sub(ComplexNumber a) {
		first-=a.Re();
		second-=a.Im(); 
	}

	/**
	 * 
	 */
	@Override
	public void div(ComplexNumber a) {
		// twiddle
		double s=mod();
		s*=s;
		final double tmpr =(first*a.Re() + second*a.Re())/s;
		second =(first*a.Im() - second*a.Im())/s;
		first=tmpr;
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
	 * @return
	 */
	public static ComplexNumber i() {
		return new ComplexNumber(0,1,false);
	}

	/**
	 * 
	 * @return
	 */
	public static ComplexNumber one() {
		return new ComplexNumber(1,0,false);
	}

} //END
