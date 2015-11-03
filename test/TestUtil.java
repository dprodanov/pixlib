package test;

public class TestUtil {
	
	public static void printvector(float[] data) {
		for (int i=0; i<data.length; i++) {		
			System.out.print(data[i]+",");		
		}
	}
	
	public static void printvector(short[] data) {
		for (int i=0; i<data.length; i++) {		
			System.out.print(data[i]+",");		
		}
	}
	
	public static void printvector(int[] data) {
		for (int i=0; i<data.length; i++) {		
			System.out.print(data[i]+",");		
		}
	}
	
	public static void printvector(byte[] data) {
		for (int i=0; i<data.length; i++) {		
			System.out.print(data[i]+",");		
		}
	}
	
	public static void printvector(double[] data) {
		for (int i=0; i<data.length; i++) {		
			System.out.print(data[i]+",");		
		}
	}
	
	public static void setvalue(byte[] data, int value) {
		for (int i=0; i<data.length; i++) {		
			data[i]=(byte) value;	
		}
	}
	public static void setvalue(short[] data, int value) {
		for (int i=0; i<data.length; i++) {		
			data[i]=(byte) value;	
		}
	}
	
	public static void setvalue(int[] data, int value) {
		for (int i=0; i<data.length; i++) {		
			data[i]=(byte) value;	
		}
	}
	
	public static void setvalue(float[] data, float value) {
		for (int i=0; i<data.length; i++) {		
			data[i]=(byte) value;	
		}
	}
	
	public static void setvalue(double[] data, double value) {
		for (int i=0; i<data.length; i++) {		
			data[i]=(byte) value;	
		}
	}

}
