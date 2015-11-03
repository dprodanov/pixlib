package ijaux.dsp;

import ijaux.Constants;
import ijaux.datatype.ComplexArray;
import ijaux.datatype.Pair;
import ijaux.hypergeom.index.Indexing;
 
/*
 * Basic Digital Signal Processing class
 */
public class FFT extends DSP implements Constants{


	
	
	
	
	
	
	
	
	/*public static <N extends Number> ComplexCube fft(PixelCube<N,?> pc, final int sign, double[] window) {
		int n= pc.getNDimensions();
		//int[] dim=pc.getDimensions();
		int[] sdir = Util.rampInt(n, n);
		Util.printIntArray(sdir);
		ComplexCube cp=new ComplexCube(pc);
		
		for (int d: sdir) {
			System.out.println("dir "+d);
			//System.out.println(cp);
			cp=fft(cp,  sign, d, window);
		}
		return cp;
			
	}*/
	
	public static ComplexArray fftxd(double[] real, double[] imag,  int sign, int[] dim ) {
		ComplexArray carr=null;
		for (int i=0; i<dim.length; i++) {
			final int nfft=nextpow2(dim[i]);
			carr=fftC2C1d(real, imag,  sign, nfft);
			real=carr.Re();
			imag=carr.Im();
		}
		return carr;
	}
	
	public static ComplexArray fftxd(double[] real, double[] imag,  int sign, int[] dim, double[] window ) {
		ComplexArray carr=null;
		for (int i=0; i<dim.length; i++) {
			final int nfft=nextpow2(dim[i]);
			carr=fft1d(real, imag,  sign, nfft, window);
			real=carr.Re();
			imag=carr.Im();
		}
		
		return carr;
	}
	
	
	public static void window(double s[], double wnd[]) {
		if (wnd.length!=s.length)
			throw new IllegalArgumentException("window array length mismatch "+wnd.length +" "+ s.length);
		for (int i=0; i<s.length; i++)
			s[i]*=wnd[i];		
	}
	
	public static void window(ComplexArray arr, double[] wnd) {
		final double re[]=arr.Re();
		window(re,wnd);
		final double im[]=arr.Im();
		window(im,wnd);
		
	}
	
	public static double[] zeroPaddEnd(double s[]) {
		final int n=s.length;
		final int k=nextpow2(n);
		final int n2=DSP.pow2(k);
		//System.out.println("n2 " +n2);
		double[] spad=new double[n2];
		System.arraycopy(s, 0, spad, 0, n);
		return spad;
	}
	
	public static float[] zeroPaddEnd(float s[]) {
		final int n=s.length;
		final int k=nextpow2(n);
		final int n2=DSP.pow2(k);
		//System.out.println("n2 " +n2);
		float[] spad=new float[n2];
		System.arraycopy(s, 0, spad, 0, n);
		return spad;
	}

	public static double[] truncate(double s[], int k) {
		if (k>s.length)
			throw new IllegalArgumentException(k+">"+s.length);
		 
		double[] spad=new double[k];
		System.arraycopy(s, 0, spad, 0, k);
		return spad;
	}
	
	public static float[] truncate(float s[], int k) {
		if (k>s.length)
			throw new IllegalArgumentException(k+">"+s.length);
		 
		float[] spad=new float[k];
		System.arraycopy(s, 0, spad, 0, k);
		return spad;
	}
	
	public static int nfft(int z) {
		return DSP.pow2(DSP.nextpow2(z));
	}
	
 

	public static int nextpow2(int u) {
		int i=0;
		if (u<0)
			u=-u;
		if (u<=1)
			return 0;
		if ((u& (u-1)) ==0) {
			i--;
		}
		while (u >0) {
			u>>=1;
			//System.out.println(u +" "+i);
			i++;
		}

		return i;	
	}

	public static final double TWOPI = 2*Math.PI;
	
	public static void fft1d(ComplexArray arr, final int sign, final int nfft, double[] wnd)  {		
		window(arr,wnd);
		fft1d(arr,sign,nfft);
	}
	
	
	
	

	// based on Erik Meijering's code
	// Modified version of the algorithm described in Numerical Recipes
	// in C: The Art of Scientific Computing, second edition, Cambridge
	// University Press, Cambridge, 1992, pp. 507-508.
	// Matlab convention
	public static void fft1d(ComplexArray arr, final int sign, final int nfft) {
		double[] real=arr.Re();
		double[] imag=arr.Im();
	 
		if (nfft>arr.length()) {
			real=zeroPaddEnd(real);
			imag=zeroPaddEnd(imag);
		}
		if (nfft<arr.length()) {
			real=truncate(real,nfft);
			imag=truncate(imag,nfft);
		}
		// Bit reversal:
		// the Cooley-Tukey algorithm 
		// http://www.drdobbs.com/cpp/a-simple-and-efficient-fft-implementatio/199500857
		final int len = real.length;
		final int hlen = len/2;
		for (int i=0, j=0; i<len; ++i) {
			if (j > i) {
				final double rt = real[j]; 
				real[j] = real[i]; 
				real[i] = rt;
				final double it = imag[j]; 
				imag[j] = imag[i]; 
				imag[i] = it;
			}
			int m = hlen;
			while (m >= 2 && j >= m) { j -= m; m >>= 1; }
			j += m;
		}

		// Danielson-Lanczos algorithm:
		int N = 2, hN = 1;
		while (N <= len) {
			final double theta = sign*TWOPI/N;
			double tmp = Math.sin(0.5*theta);
			final double alpha = -2.0*tmp*tmp;
			final double beta = Math.sin(theta);
			double wr = 1.0;
			double wi = 0.0;
			for (int k=0; k<hN; ++k) {
				for (int i=k, j=k+hN; i<len; i+=N, j+=N) {
					final double tmpr = wr*real[j] - wi*imag[j];
					final double tmpi = wr*imag[j] + wi*real[j];
					real[j] = real[i] - tmpr;
					imag[j] = imag[i] - tmpi;
					real[i] += tmpr;
					imag[i] += tmpi;
				}
				wr += (tmp=wr)*alpha - wi*beta;
				wi += wi*alpha + tmp*beta;
			}
			hN = N; N <<= 1;
		}
	}

	public static void fftshift1d(ComplexArray arr) {
		final int k=(int)arr.length()/2;
		double[] re=arr.Re();
		double[] im=arr.Im();
		//final int n=re.length-1;
		
		for (int i=0; i<k; i++) {
			double tmp=re[i];
			re[i]=re[k+i];
			re[k+i]=tmp;
			
			tmp=im[i];
			im[i]=im[k+i];
			im[k+i]=tmp;
		}
	}
	
	//konop's
	public static void fftshift1d(double[] arr) {
		final int k=arr.length/2;		
		for (int i=0; i<k; i++) {
			double tmp=arr[i];
			arr[i]=arr[k+i];
			arr[k+i]=tmp;			
		}
	}
	
	public static void ifftshift1d(double[] arr) {
		final int k= arr.length/2;
		 
		for (int i= arr.length-1; i>=k; i--) {
			double tmp=arr[i];
			arr[i]=arr[i-k];
			arr[i-k]=tmp;
		}
	}
	
	public static void fftshift1d(Pair<double[], double[]> arr) {
		final int k=(int)arr.first.length/2;
		double[] re=arr.first;
		double[] im=arr.second;
		//final int n=re.length-1;
		
		for (int i=0; i<k; i++) {
			double tmp=re[i];
			re[i]=re[k+i];
			re[k+i]=tmp;
			
			tmp=im[i];
			im[i]=im[k+i];
			im[k+i]=tmp;
		}
	}
	
	public static void ifftshift1d(Pair<double[], double[]> arr) {
		final int k=(int)arr.first.length/2;
		double[] re=arr.first;
		double[] im=arr.second;
		
		for (int i=(int)arr.first.length-1; i>=k; i--) {
			double tmp=re[i];
			re[i]=re[i-k];
			re[i-k]=tmp;
			
			tmp=im[i];
			im[i]=im[i-k];
			im[i-k]=tmp;
		}
	}
	
	//end of konop's	
	
	public static void fftshift1d(int[] arr) {
		final int k=arr.length/2;		
		for (int i=0; i<k; i++) {
			int tmp=arr[i];
			arr[i]=arr[k+i];
			arr[k+i]=tmp;			
		}
	}
	
	public static void ifftshift1d(int[] arr) {
		final int k= arr.length/2;
		 
		for (int i= arr.length-1; i>=k; i--) {
			int tmp=arr[i];
			arr[i]=arr[i-k];
			arr[i-k]=tmp;

		}
	}
	
	public static void ifftshift1d(ComplexArray arr) {
		final int k=(int)arr.length()/2;
		double[] re=arr.Re();
		double[] im=arr.Im();
		
		for (int i=(int)arr.length()-1; i>=k; i--) {
			double tmp=re[i];
			re[i]=re[i-k];
			re[i-k]=tmp;
			
			tmp=im[i];
			im[i]=im[i-k];
			im[i-k]=tmp;
		}
	}
	
	public static ComplexArray fft1d(double[] real, double[] imag, final int sign, final int nfft, final double[] wnd) {
		window(real,wnd);
		window(imag,wnd);
		return fftC2C1d(real,imag,sign, nfft);
	}
	
	public static ComplexArray fftC2C1d(double[] real, double[] imag, final int sign, final int nfft) {
		//double[] imag=new double[real.length];
		//System.out.println("sz real: " + real.length);
		//System.out.println("sz imag: " + imag.length);
		if (nfft>real.length) {
			real=zeroPaddEnd(real);
			imag=zeroPaddEnd(imag);
		}
		if (nfft<real.length) {
			real=truncate(real,nfft);
			imag=truncate(imag,nfft);
		}
		if (real.length!= imag.length)
			throw new IllegalArgumentException("Array size mismatch " + real.length +" " +imag.length );
		//System.out.println("sz real: " + real.length);
		//System.out.println("sz imag: " + imag.length);
		// Bit reversal:
		final int len = real.length;
		final int hlen = len/2;
		for (int i=0, j=0; i<len; ++i) {
			if (j > i) {
				final double rt = real[j]; real[j] = real[i]; real[i] = rt;
				final double it = imag[j]; imag[j] = imag[i]; imag[i] = it;
			}
			int m = hlen;
			while (m >= 2 && j >= m) { j -= m; m >>= 1; }
			j += m;
		}

		// Danielson-Lanczos algorithm:
		int N = 2, hN = 1;
		while (N <= len) {
			final double theta = sign*TWOPI/N;
			double tmp = Math.sin(0.5*theta);
			final double alpha = -2.0*tmp*tmp;
			final double beta = Math.sin(theta);
			double wr = 1.0;
			double wi = 0.0;
			for (int k=0; k<hN; ++k) {
				for (int i=k, j=k+hN; i<len; i+=N, j+=N) {
					final double tmpr = wr*real[j] - wi*imag[j];
					final double tmpi = wr*imag[j] + wi*real[j];
					real[j] = real[i] - tmpr;
					imag[j] = imag[i] - tmpi;
					real[i] += tmpr;
					imag[i] += tmpi;
				}
				wr += (tmp=wr)*alpha - wi*beta;
				wi += wi*alpha + tmp*beta;
			}
			hN = N; N <<= 1;
		}
		return new ComplexArray(real,imag, false);
	} // end
	
	public static ComplexArray fftR2C1d(double[] real, final int sign, final int nfft) {		
		//System.out.println("sz real: " + real.length);
		if (nfft>real.length) {
			real=zeroPaddEnd(real);
		}
		if (nfft<real.length) {
			real=truncate(real,nfft);
		}
		double[] imag=new double[real.length];

		// Bit reversal:
		final int len = real.length;
		final int hlen = len/2;
		for (int i=0, j=0; i<len; ++i) {
			if (j > i) {
				final double rt = real[j]; real[j] = real[i]; real[i] = rt;
				//final double it = imag[j]; imag[j] = imag[i]; imag[i] = it; //all are zeros
			}
			int m = hlen;
			while (m >= 2 && j >= m) { j -= m; m >>= 1; }
			j += m;
		}

		// Danielson-Lanczos algorithm:
		int N = 2, hN = 1;
		while (N <= len) {
			final double theta = sign*TWOPI/N;
			double tmp = Math.sin(0.5*theta);
			final double alpha = -2.0*tmp*tmp;
			final double beta = Math.sin(theta);
			double wr = 1.0;
			double wi = 0.0;
			for (int k=0; k<hN; ++k) {
				for (int i=k, j=k+hN; i<len; i+=N, j+=N) {
					final double tmpr = wr*real[j] - wi*imag[j];
					final double tmpi = wr*imag[j] + wi*real[j];
					real[j] = real[i] - tmpr;
					imag[j] = imag[i] - tmpi;
					real[i] += tmpr;
					imag[i] += tmpi;
				}
				wr += (tmp=wr)*alpha - wi*beta;
				wi += wi*alpha + tmp*beta;
			}
			hN = N; N <<= 1;
		}
		return new ComplexArray(real,imag, false);
	} // end
	/*
	 *  for compatibility with vanilla ImageJ
	 */
	public static ComplexArray fft1d(float[] r, float[] im, final int sign, final int nfft) {
		//double[] imag=new double[real.length];
		//System.out.println("sz real: " + real.length);
		//System.out.println("sz imag: " + imag.length);
		double[] real=arrF2D(r);
		double[] imag=arrF2D(im);
		if (nfft>real.length) {
			real=zeroPaddEnd(real);
			imag=zeroPaddEnd(imag);
		}
		if (nfft<real.length) {
			real=truncate(real,nfft);
			imag=truncate(imag,nfft);
		}
		if (real.length!= imag.length)
			throw new IllegalArgumentException("Array size mismatch " + real.length +" " +imag.length );
		//System.out.println("sz real: " + real.length);
		//System.out.println("sz imag: " + imag.length);
		// Bit reversal:
		final int len = real.length;
		final int hlen = len/2;
		for (int i=0, j=0; i<len; ++i) {
			if (j > i) {
				final double rt = real[j]; 
				real[j] = real[i]; 
				real[i] = rt;

			}
			int m = hlen;
			while (m >= 2 && j >= m) { j -= m; m >>= 1; }
			j += m;
		}

		// Danielson-Lanczos algorithm:
		int N = 2, hN = 1;
		while (N <= len) {
			final double theta = sign*TWOPI/N;
			double tmp = Math.sin(0.5*theta);
			final double alpha = -2.0*tmp*tmp;
			final double beta = Math.sin(theta);
			double wr = 1.0;
			double wi = 0.0;
			for (int k=0; k<hN; ++k) {
				for (int i=k, j=k+hN; i<len; i+=N, j+=N) {
					final double tmpr = (wr*real[j] - wi*imag[j]);
					final double tmpi =  (wr*imag[j] + wi*real[j]);
					real[j] = real[i] - tmpr;
					imag[j] = imag[i] - tmpi;
					real[i] += tmpr;
					imag[i] += tmpi;
				}
				wr += (tmp=wr)*alpha - wi*beta;
				wi += wi*alpha + tmp*beta;
			}
			hN = N; N <<= 1;
		}
		return new ComplexArray(real,imag, false);
	} // end
	

	public static double sinc(double x) {
		if (x==0) return 1.0d;
		final double px=Math.PI*x;
		return Math.sin(px)/px;
	}
	
	
	
	
}
