package ijaux.datatype;



public class ComplexFNumber extends Pair<Float,Float> 

implements ProductSpaceReflexive<ComplexFNumber,ComplexFNumber>, Typing {
 
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5513067895742945334L;
	private boolean isPolar=false;
	
	public static ComplexFNumber zero() {
		return new ComplexFNumber(0,0,false);
	}
	
	public ComplexFNumber(float a, float b, boolean polar) {
		super(a,b);
		if (polar){
			polarForm(a,b);
		}
		isPolar=polar;
	}
	
	public void polarForm(double magnitude, double angle) { 
	      first=(float) (magnitude * Math.cos(angle));
	      second=(float) (magnitude * Math.sin(angle));
	      //isPolar=true;

	}
	
	public static ComplexFNumber fromPolar(double magnitude, double angle) {
		    float first=(float) (magnitude * Math.cos(angle));
		    float second=(float) (magnitude * Math.sin(angle));
		    return (ComplexFNumber) new ComplexFNumber (first, second, false);

	}

	public float Re(){
		return first;
	}
	
	public float Im(){
		return second;
	}
	 
	
	public ComplexFNumber conjugate() {
	    return new ComplexFNumber(first, second * (-1), false);
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

	public ComplexFNumber invs() {
		final double s=mod()*mod();
		return new ComplexFNumber((float)(Re()/s), (float)(-Im()/s), false);
	}

	/* RE : r[1]*r[2] - i[1]*i[2] 
	 * IM : i[1]*r[2] + r[1]*i[2]
	*/
	@Override
	public void mult( ComplexFNumber b) {
		final double tmpr =first*b.Re() - second*b.Re(); 
		second=(float)first*b.Im() + second*b.Re();
		first=(float) tmpr;
	}
 
	@Override
	public ComplexFNumber norm() {
		return new ComplexFNumber((float) (mod()*mod()), 0, false); 
	}

	@Override
	public void add(ComplexFNumber a) {
		first+= a.Re(); 
		second+=a.Im(); 
	}

	public void inv() {
		first=-first;
		second=-second;
		//return  new ComplexNumber(-Re(), -Im(), false);
	}
	
	public void setF(float a, float b ) {
		first=a;
		second=b;
	}

	@Override
	public void scale(double scalar) {
		first=(float) (scalar*Re()); 
		second=(float) (scalar*Im());
	}

	@Override
	public void sub(ComplexFNumber a) {
		first-=a.Re();
		second-=a.Im(); 
	}

	@Override
	public void div(ComplexFNumber a) {
		// twiddle
		double s=mod();
		s*=s;
		final double tmpr =(first*a.Re() + second*a.Re())/s;
		second =(float) ((first*a.Im() - second*a.Im())/s);
		first=(float) tmpr;
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
