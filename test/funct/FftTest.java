package test.funct;

import ijaux.Util;

public class FftTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int u=2000;
		
		int uu=nextpow2(  u);
		 System.out.println("nextpow2 " +uu );
		 
		 int k=0;
		
		 System.out.println("pow: 2 ^ " +k+" =" +pow2(  k) );
		 
		 System.out.println("pow: 2 ^ " +uu+" =" +pow2(  uu) );
		 
		 double[]  tmp=Util.rampDouble(5, 6);
		 System.out.println("length " +tmp.length);
		 Util.printDoubleArray(tmp);
		 double[] spad=zeroPaddS(tmp);
		 System.out.println("length " +spad.length);
		 Util.printDoubleArray(spad);
		 System.out.println(spad[1]);
	}
	
	public static int pow2(int n) {
		if (n==0) return 1;
		return 2<<(n-1);
	}
	
	public static int nextpow2(int u) {
		int i=0;
		while (u >0) {
			u=u>>1;
			//System.out.println(u +" "+i);
			i++;
		}
		return i;	
	}
	
	 public static double[] zeroPaddS(double s[]) {
		 final int n=s.length;
		 final int k=nextpow2(n);
		 final int n2=pow2(k);
		 //System.out.println("n2 " +n2);
		 double[] spad=new double[n2];
		 System.arraycopy(s, 0, spad, 0, n);
		 return spad;
	 }
}
