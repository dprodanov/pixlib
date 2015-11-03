package ijaux.hypergeom.dsp;

import ijaux.Util;
import ijaux.datatype.ComplexArray;
import ijaux.dsp.FFT;
import ijaux.hypergeom.ComplexCube;
import ijaux.hypergeom.PixelCube;
import ijaux.iter.compl.ComplexVectorCursor;
import ijaux.iter.dir.VectorCursor;


public class FFTD {

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
	
	public  static <N extends Number> ComplexCube fftxd(PixelCube<N,?> pc, int sign, double[] window) {
		int[] dim=pc.getDimensions();
		ComplexCube cc=fft(pc,  sign, 0);		
		for (int i=1; i<dim.length; i++) {
			cc=fft( cc,sign,  i, window);		 
		}
		
		return cc;
	}

	public  static <N extends Number> ComplexCube fftxd(PixelCube<N,?> pc, int sign) {
		int[] dim=pc.getDimensions();
		ComplexCube cc=fft(pc,  sign, 0);		
		for (int i=1; i<dim.length; i++) {
			cc=fft( cc,sign,  i);		 
		}
		
		return cc;
	}

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
	
	public  static  ComplexCube fftxd(ComplexCube pc, int sign, double[] window) {
		int[] dim=pc.getDimensions();
		ComplexCube cc=fft(pc,  sign, 0);		
		for (int i=1; i<dim.length; i++) {
			cc=fft( cc,sign,  i, window);		 
		}
		
		return cc;
	}

	public static <N extends Number> ComplexCube fft(PixelCube<N,?> pc, final int sign) {
		int n= pc.getNDimensions();
		//int[] dim=pc.getDimensions();
		int[] sdir = Util.rampInt(n, n);
		Util.printIntArray(sdir);
		ComplexCube cp=new ComplexCube(pc);
		
		for (int d: sdir) {
			System.out.println("dir "+d);
			//System.out.println(cp);
			cp=fft(cp,  sign, d);
		}
		return cp;
			
	}

	public static <N extends Number> ComplexCube fft(PixelCube<N,?> pc, final int sign, final int dir) {
		int[] dim=pc.getDimensions();
	
		int bs=dim[dir];
		//System.out.println ("bs " +bs +" "+nextpow2(bs));
		//Util.printIntArray(dim);
		int nfft=FFT.nfft(bs);
		int [] cdim=new int [dim.length];
		System.arraycopy(dim, 0, cdim, 0, dim.length);
		cdim[dir]=nfft;
		//System.out.println ("nfft " +nfft);
		
		ComplexCube cp=new ComplexCube(cdim);
		ComplexVectorCursor block=cp.iteratorBlock();
		block.setDirection(dir);
		//Util.printIntArray(cdim);
		
		@SuppressWarnings("unchecked")
		VectorCursor<N> rb=(VectorCursor<N>) pc.iteratorBlock();
		rb.setDirection(dir);
		
		
		//System.out.println("block size "+rb.getBlockSize());
	
		//int i=0;
		while ( rb.hasNext()) {
			//block.nextC() ;
			//System.out.println((i++)+" " );
			double[]real=rb.nextDouble();
			//rb.inc();
			//Util.printDoubleArray(real);
			double[]imag=new double[real.length];
			//System.out.println ("bs " +real.length);
			final ComplexArray carr= FFT.fftC2C1d(real, imag, sign, nfft);
			//System.out.println ("cs " +carr);
			//System.out.println("iter " +" dir " +dir);
			block.put(carr);
			
		}
		return cp;	
	}

	public static <N extends Number> ComplexCube fft(PixelCube<N,?> pc, final int sign, final int dir, double[] window) {
		int[] dim=pc.getDimensions();
	
		int bs=dim[dir];
		//System.out.println ("bs " +bs +" "+nextpow2(bs));
		//Util.printIntArray(dim);
		int nfft=FFT.nfft(bs);
		int [] cdim=new int [dim.length];
		System.arraycopy(dim, 0, cdim, 0, dim.length);
		cdim[dir]=nfft;
		//System.out.println ("nfft " +nfft);
		
		ComplexCube cp=new ComplexCube(cdim);
		ComplexVectorCursor block=cp.iteratorBlock();
		block.setDirection(dir);
		//Util.printIntArray(cdim);
		
		@SuppressWarnings("unchecked")
		VectorCursor<N> rb=(VectorCursor<N>) pc.iteratorBlock();
		rb.setDirection(dir);
		
		
		//System.out.println("block size "+rb.getBlockSize());
	
		//int i=0;
		while ( rb.hasNext()) {
			//block.nextC() ;
			//System.out.println((i++)+" " );
			double[]real=rb.nextDouble();
			//rb.inc();
			//Util.printDoubleArray(real);
			double[]imag=new double[real.length];
			//System.out.println ("bs " +real.length);
			final ComplexArray carr= FFT.fft1d(real, imag, sign, nfft, window);
			//System.out.println ("cs " +carr);
			//System.out.println("iter " +" dir " +dir);
			block.put(carr);
			
		}
		return cp;	
	}

	public static <N extends Number> ComplexCube fft(ComplexCube cp2, final int sign, 
			final int dir, double[] window) {
		int[] dim=cp2.getDimensions();
		int bs=dim[dir];
		int nfft=FFT.nfft(bs);
		int [] cdim=new int [dim.length];
		System.arraycopy(dim, 0, cdim, 0, dim.length);
		cdim[dir]=nfft;
		
		ComplexCube cp=new ComplexCube(cdim);
		ComplexVectorCursor block=cp.iteratorBlock();
		block.setDirection(dir);
			 
		ComplexVectorCursor rb= cp2.iteratorBlock();
		rb.setDirection(dir);
	
		while ( rb.hasNext()) {
			final ComplexArray carr=rb.nextC();		
			FFT.fft1d(carr, sign, nfft, window);
			block.put(carr);
			
		}
		return cp;	
	}

	public static <N extends Number> ComplexCube fft(ComplexCube cp2, final int sign, final int dir) {
		int[] dim=cp2.getDimensions();
	
		int bs=dim[dir];
		//System.out.println ("bs " +bs +" "+nextpow2(bs));
		//Util.printIntArray(dim);
		int nfft=FFT.nfft(bs);
		int [] cdim=new int [dim.length];
		System.arraycopy(dim, 0, cdim, 0, dim.length);
		cdim[dir]=nfft;
		//System.out.println ("nfft " +nfft);
		
		ComplexCube cp=new ComplexCube(cdim);
		ComplexVectorCursor block=cp.iteratorBlock();
		block.setDirection(dir);
		//Util.printIntArray(cdim);
		
		 
		ComplexVectorCursor rb= cp2.iteratorBlock();
		rb.setDirection(dir);
		
		
		//System.out.println("block size "+rb.getBlockSize());
	
		//int i=0;
		while ( rb.hasNext()) {
			final ComplexArray carr=rb.nextC();
			//System.out.println((i++)+" " );			
			FFT.fft1d(carr, sign, nfft);
			//System.out.println ("cs " +carr);
			//System.out.println("iter " +" dir " +dir);
			block.put(carr);
			
		}
		return cp;	
	}

}
