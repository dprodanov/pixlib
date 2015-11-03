package ijaux.dsp;

public class Conv {
	
	/**
	 * @param kernel
	 */
	public static void flip(float[] kernel) {
		final int s=kernel.length-1;
		for (int i=0; i< kernel.length/2; i++) {
			final float c=kernel[i];
			kernel[i]=kernel[s-i];
			kernel[s-i]=c;
		}
	}
	
	/* 
	 * computes correlation
	 */
	public static float[] lineConvolve(float[] arr, float[] kernel, boolean flip) {
		if (flip)
			flip(kernel);
		
		float[] y= new float[arr.length];
		int kw=kernel.length/2;
 
		//System.out.println("pre loop < "+kw);
		for (int i=0; i<kw; i++) {
			int c=0;
			for (int k=-kw; k<=kw; k++) {
				int q=i-k;
				if (q<0) q=0;
				y[i]+=arr[q]*kernel[c++];
			}			
		}
		// main loop	    
	    for (int i=kw;i<arr.length-kw;i++) {	    	
	        int c=0;
			for (int k=-kw; k<=kw; k++) {
				y[i]+=arr[i-k]*kernel[c++];
			}
	    }
	    
		//System.out.println("post loop => "+(arr.length-kw));
		for (int i=arr.length-kw; i<arr.length; i++) {
			int c=0;
			for (int k=-kw; k<=kw; k++) {
				int q=i-k;
				if (q>=arr.length) q=arr.length-1;
				y[i]+=arr[q]*kernel[c++];
			}
			
		}
	    return y;
	}
}
