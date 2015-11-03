package test.dsp;

import ijaux.Util;
import ijaux.dsp.FFT;

/*
 *  tests of nextpow2, fftshift1d and ifftshift1d
 */
public class DSPTest3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("nextpow2 4 " + FFT.nextpow2(4));
		
		System.out.println("nextpow2 3 " + FFT.nextpow2(3));

		System.out.println("nextpow2 16 " + FFT.nextpow2(16));
		System.out.println("nextpow2 31 " +FFT.nextpow2(31));

		System.out.println("nextpow2 64 " +FFT.nextpow2(64));
		
		
		System.out.println("nfft 4 " + FFT.nfft(4));
		
		System.out.println("nfft 3 " + FFT.nfft(3));

		System.out.println("nfft 16 " + FFT.nfft(16));
		System.out.println("nfft 31 " +FFT.nfft(31));

		System.out.println("nfft 64 " +FFT.nfft(64));
		
		int n=10;
		int[] arr=Util.rampInt(n, n);
		System.out.println("array");
		Util.printIntArray(arr);
		System.out.println("\nfft shift");
		FFT.fftshift1d(arr);
		Util.printIntArray(arr);
		
		System.out.println("\nifft shift");
		FFT.ifftshift1d(arr);
		Util.printIntArray(arr);
	}

}
