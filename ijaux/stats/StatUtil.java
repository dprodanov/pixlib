package ijaux.stats;

/*
 *  Basic statistical utils
 */
public final class StatUtil {
	
	private StatUtil(){};
	
	/*
	 * squared differences
	 */
	
	public static double sd(byte[] x, byte[] y, int mask, boolean norm) {
		final int n=x.length;
		final int n1=y.length;
		if (n!=n1) 
			throw new IllegalArgumentException();
		float[] fx=new float[n];
		float[] fy=new float[n];
		
		for (int i=0; i<n; i++) {
			fx[i]=x[i] & mask;
			fy[i]=y[i] & mask;
		}		
		return sd(fx, fy, norm); 
	}
	
	public static double sd(short[] x, short[] y, int mask, boolean norm) {
		final int n=x.length;
		final int n1=y.length;
		if (n!=n1) 
			throw new IllegalArgumentException();
		float[] fx=new float[n];
		float[] fy=new float[n];
		
		for (int i=0; i<n; i++) {
			fx[i]=x[i] & mask;
			fy[i]=y[i] & mask;
		}		
		return sd(fx, fy, norm); 
	}
	
	public static double sd(float[] x, float[] y, boolean norm) {
		final int n=x.length;
		final int n1=y.length;
		if (n!=n1) 
			throw new IllegalArgumentException();
		double sum_x=0;
		
		for (int i=0; i<n; i++) {
			sum_x+=(x[i]-y[i])*(x[i]-y[i]);			 
		}
		
		if (norm)
			sum_x/=(double)n;
  
		return sum_x;		
	}
	
	public static double sd(double[] x, double[] y, boolean norm) {
		final int n=x.length;
		final int n1=y.length;
		if (n!=n1) 
			throw new IllegalArgumentException();
		double sum_x=0;
		
		for (int i=0; i<n; i++) {
			sum_x+=(x[i]-y[i])*(x[i]-y[i]);			 
		}
		
		if (norm)
			sum_x/=(double)n;
  
		return sum_x;		
	}
	
	/*
	 * correlation coefficients
	 */
	public static double corrcoef(short[] x, short[] y, int mask) {
		final int n=x.length;
		final int n1=y.length;
		if (n!=n1) 
			throw new IllegalArgumentException();
		float[] fx=new float[n];
		float[] fy=new float[n];
		
		for (int i=0; i<n; i++) {
			fx[i]=x[i] & mask;
			fy[i]=y[i] & mask;
		}		
		return corrcoef(fx, fy); 
	}
	
	public static double corrcoef(byte[] x, byte[] y, int mask) {
		final int n=x.length;
		final int n1=y.length;
		if (n!=n1) 
			throw new IllegalArgumentException();
		float[] fx=new float[n];
		float[] fy=new float[n];
		
		for (int i=0; i<n; i++) {
			fx[i]=x[i] & mask;
			fy[i]=y[i] & mask;
		}		
		return corrcoef(fx, fy); 
	}
	
	public static double corrcoef(float[] x, float[] y) {
		final int n=x.length;
		final int n1=y.length;
		if (n!=n1) 
			throw new IllegalArgumentException();
		double sum_x=0, sum_y=0,sum_x2=0, sum_y2=0, sum_xy=0;
		
		for (int i=0; i<n; i++) {
			sum_x+=x[i];
			sum_y+=y[i];
			
			sum_xy+=x[i]*y[i];
			sum_x2+=x[i]*x[i];
			sum_y2+=y[i]*y[i];			
		}
		
		sum_x/=(double)n;
		sum_y/=(double)n;
		double r=sum_xy-n*sum_x*sum_y;
		r/=Math.sqrt(sum_x2 - n* sum_x*sum_x)*Math.sqrt(sum_y2 - n* sum_y*sum_y);
		return r;
		
	}
	
	public static double corrcoef(double[] x, double[] y) {
		final int n=x.length;
		final int n1=y.length;
		if (n!=n1) 
			throw new IllegalArgumentException();
		double sum_x=0, sum_y=0,sum_x2=0, sum_y2=0, sum_xy=0;
		
		for (int i=0; i<n; i++) {
			sum_x+=x[i];
			sum_y+=y[i];
			
			sum_xy+=x[i]*y[i];
			sum_x2+=x[i]*x[i];
			sum_y2+=y[i]*y[i];			
		}
		
		sum_x/=(double)n;
		sum_y/=(double)n;
		double r=sum_xy-n*sum_x*sum_y;
		r/=Math.sqrt(sum_x2 - n* sum_x*sum_x)*Math.sqrt(sum_y2 - n* sum_y*sum_y);
		return r;
		
	}

}
