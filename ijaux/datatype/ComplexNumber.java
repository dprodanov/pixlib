package ijaux.datatype;



public class ComplexNumber extends Pair<Double,Double> 

implements ProductSpaceReflexive<ComplexNumber,ComplexNumber>, Typing {
 
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5513067895742945334L;
	private boolean isPolar=false;
	
	public static ComplexNumber zero() {
		return new ComplexNumber(0,0,false);
	}
	
	public ComplexNumber(double a, double b, boolean polar) {
		super(a,b);
		if (polar){
			polarForm(a,b);
		}
		isPolar=polar;
	}
	
	public void polarForm(double magnitude, double angle) {
		   
	      first=(magnitude * Math.cos(angle));
	      second=(magnitude * Math.sin(angle));
	      //isPolar=true;

	}
	
	public static ComplexNumber fromPolar(double magnitude, double angle) {
		    double first=(magnitude * Math.cos(angle));
		    double second=(magnitude * Math.sin(angle));
		    return (ComplexNumber) new ComplexNumber (first, second, false);

	}

	public double Re(){
		return first;
	}
	
	public double Im(){
		return second;
	}
	 
	
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



	 public String toString() {
/*		    if (this.first == 0) {
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
		    }*/
		 String astr="";
			if (!isPolar) {
				
					astr+=" ("+first+" "+ second+"j)";
			

			} else {
				
					astr+=" (r="+first+" fi="+ second+")";
			
			}
			 astr+="";
			 return astr;
	 }

	public ComplexNumber invs() {
		final double s=mod()*mod();
		return new ComplexNumber(Re()/s, -Im()/s, false);
	}

	@Override
	public void mult( ComplexNumber b) {
		first=Re()*b.Re() - Im()*b.Re(); 
		second=Re()*b.Im() + Im()*b.Re();
	}

	@Override
	public ComplexNumber norm() {
		return new ComplexNumber(mod()*mod(), 0, false); 
	}

	@Override
	public void add(ComplexNumber a) {
		first+= a.Re(); 
		second+=a.Im(); 
	}

	public void inv() {
		first=-first;
		second=-second;
		//return  new ComplexNumber(-Re(), -Im(), false);
	}

	@Override
	public void scale(double scalar) {
		first=scalar*Re(); 
		second=scalar*Im();
	}

	@Override
	public void sub(ComplexNumber a) {
		first-=a.Re();
		second-=a.Im(); 
	}

	@Override
	public void div(ComplexNumber a) {
		final double s=mod()*mod();
		first=(Re()*a.Re() + Im()*a.Re())/s;
		second=(Re()*a.Im() - Im()*a.Im())/s;
	}

	@Override
	public Class<?> getType() {
		return double.class;
	}

	@Override
	public boolean eq(Class<?> c) {
		return c==double.class;
	}



}
