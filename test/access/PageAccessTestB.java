package test.access;
import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.datatype.access.PagedAccess;


public class PageAccessTestB {

	static int[] dim={256,256*4};
	static int n=Util.cumprod(dim);
	
	byte[] byte_pixels=Util.rampByte(n, 16);

	byte[][] bpixels2D=new byte[2][n];

	public PageAccessTestB () {
		bpixels2D[0]=Util.rampByte(n, 16);
		bpixels2D[1]=Util.rampByte(n, 16);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new PageAccessTestB().test(3);

	}
	void test(int niter) {
		System.out.println("Access testing for array size: "+n);

		try {
			/*
			 * Raw access case
			 */
			for (int c=0; c< niter;c++) {
				System.gc();
				long start=System.currentTimeMillis();
				Access<Byte> access= Access.rawAccess(byte_pixels, null);

				long time0=System.currentTimeMillis();
				for (int k=0; k<n; k++) {
					final byte b=access.elementByte(k);
				}
				long time1=System.currentTimeMillis();

				PagedAccess<Byte> pa= PagedAccess.rawAccess (bpixels2D, null);

				long pa1=System.currentTimeMillis();
				
				for (int k=0; k<2*n; k++) {
					final byte b=pa.elementByte(k);
				}
				long time2=System.currentTimeMillis();

				for (int k=0; k<2; k++) {
					for (int j=0; j<n; j++) {
						final byte b= bpixels2D[k][j];
					}
				}
				long time3=System.currentTimeMillis();
				for (int k=0; k<n; k++) {
					final byte b=byte_pixels [k];
				}
				long time4=System.currentTimeMillis();

				System.out.println("Raw access");
				System.out.println("Initiation time (byte[]): "+(time0-start));
				System.out.println("Initiation time (corr) byte[][]): "+(pa1-time0)/2.0f);
				System.out.println("Loop run time (byte[]): "+(time1-time0) );
				System.out.println("Loop run time (raw byte[]): "+(time4-time3) );
				System.out.println("Loop run time (byte[][]): "+(time2-pa1)/2.0f );
				System.out.println("Loop run time (raw byte[][]): "+(time3-time2)/2.0f );

			}
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
