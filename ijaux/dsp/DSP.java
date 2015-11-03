package ijaux.dsp;

/*
 * Basic Digital Signal Processing class
 */
public class DSP {

	public static int nextpow2(int u) {
		int i=0;
		if (u<0) u=-u;
		if (u<=1)
			return 0;
		if ((u&(u-1)) ==0) { i--; }
		while (u>0) {
			u>>=1;
			//System.out.println(u +" "+i);
			i++;
		}
		return i;	
	}
	
	public static int pow2(int n) {
		if (n==0) return 1;
	/*	if ((n & (n - 1)) == 0) {
			return n; // x is already a power-of-two number 
		}*/
		return 2<<(n-1);
	}
	
	
	//The filter is a "Direct Form II Transposed"
		//  implementation of the standard difference equation:
		//   a(1)*y(n) = b(1)*x(n) + b(2)*x(n-1) + ... + b(nb+1)*x(n-nb)
		//                         - a(2)*y(n-1) - ... - a(na+1)*y(n-na)
		//  
		// based on Chen Yangquan <elecyq@nus.edu.sg> 1998-11-11
		// http://mechatronics.ece.usu.edu/yqchen/filter.c/
		
		public static double[] filter2(double[]b, double[]a,  double[]x)
		{
			double[] y= new double[x.length];
		    int ord=Math.min(a.length, b.length);
		    
		    // initial conditions
		    y[0]=b[0] * x[0];
		    for (int i=1;i<ord;i++){	  
		        for (int j=0;j<=i;j++) {
		            y[i]+=b[j]*x[i-j];
		          //  System.out.print(j+" ");
		        }
		       // System.out.println("\\ "+i);
		        for (int j=1;j<=i;j++) {
		            y[i]-=a[j]*y[i-j];
		        }
		    }
		    /* end of initial part */
		    for (int i=ord;i<x.length;i++) {
		        for (int j=0;j<b.length;j++)
		            y[i]+=b[j]*x[i-j];	        
		        for (int j=1;j<a.length;j++)
		            y[i]-=a[j]*y[i-j];
		    }
		    
		    return y;
		    
		} /* end of filter */
		
		public static float[] filter2(float[]b, float[]a,  float[]x)
		{
			float[] y= new float[x.length];
			 
			int ord=Math.min(a.length, b.length);
		    
		    // initial conditions
		    y[0]=b[0] * x[0];
		    for (int i=1;i<ord;i++){	  
		        for (int j=0;j<=i;j++) {
		            y[i]+=b[j]*x[i-j];
		          //  System.out.print(j+" ");
		        }
		       // System.out.println("\\ "+i);
		        for (int j=1;j<=i;j++) {
		            y[i]-=a[j]*y[i-j];
		        }
		    }
		    /* end of initial part */
		    for (int i=ord;i<x.length;i++) {
		        for (int j=0;j<b.length;j++)
		            y[i]+=b[j]*x[i-j];	        
		        for (int j=1;j<a.length;j++)
		            y[i]-=a[j]*y[i-j];
		    }	    
		    
		    return y;
		    
		} /* end of filter */
		
		/**
	     * Partial factorial,
	     * @param m power of x
	     * @param n number of derivatives
	     *
	     * @return m*(m-1)*...(m - n)
	     */
	    public static double pfact(int m, int n){
	        int p = 1;
	        for(int i = 0; i<n; i++){
	            p*=(m - i);
	        }
	        return p;
	    }
	    
	    
	    /**
		 * @param kernel
		 */
		public static void flip(double[] kernel) {
			final int s=kernel.length-1;
			for (int i=0; i< kernel.length/2; i++) {
				final double c=kernel[i];
				kernel[i]=kernel[s-i];
				kernel[s-i]=c;
			}
		}
		
		public static void flip(float[] kernel) {
			final int s=kernel.length-1;
			for (int i=0; i< kernel.length/2; i++) {
				final float c=kernel[i];
				kernel[i]=kernel[s-i];
				kernel[s-i]=c;
			}
		}
		
		// implements Matlab's function flipud
		public static void flipud (double[][] kernel) {		
			final int s=kernel.length-1;
			for (int i=0; i< kernel.length/2; i++) {
				final double[] c=kernel[i];
				kernel[i]=kernel[s-i];
				kernel[s-i]=c;
			}
			
		}
		
		// implements Matlab's function flipud
		public static void flipud (float[][] kernel) {		
			final int s=kernel.length-1;
			for (int i=0; i< kernel.length/2; i++) {
				final float[] c=kernel[i];
				kernel[i]=kernel[s-i];
				kernel[s-i]=c;
			}
			
		}
		
		// implements Matlab's function flipr
		public static void flipr(double[][] kernel) {		
			for (int i=0; i< kernel.length; i++) {
				flip( kernel[i]);
			}
		}
		
		// implements Matlab's function flipr
		public static void flipr(float[][] kernel) {		
			for (int i=0; i< kernel.length; i++) {
				flip( kernel[i]);
			}
		}

		public static float[] arrD2F (double[] arr) {
			float[] ret=new float[arr.length];		
			for (int i=0; i<ret.length; i++)
				ret[i]=(float) arr[i];
			
			return ret;
		}

		public static double[] arrF2D (float[] arr) {
			double[] ret=new double[arr.length];
			
			for (int i=0; i<ret.length; i++)
				ret[i]=arr[i];
			
			return ret;
		}
}
