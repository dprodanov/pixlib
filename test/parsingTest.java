package test;
import ijaux.Util;


public class parsingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s="1.1  2  4";
		
		double[] d= Util.parseArray(s, double[].class);
		System.out.println(d[0]+" "+d[1]);
	}

}
