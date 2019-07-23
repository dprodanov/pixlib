package ijaux;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;

import static java.lang.Math.*;

public class TestUtil {

	public static<A> double absdiff(A x, A y, boolean norm) {
		try {
			Access<?> ax=Access.rawAccess(x, null);
			Access<?> ay=Access.rawAccess(y, null);
			int n=(int) ax.length();
			final int n1=(int) ay.length();
		if (n!=n1) 
			throw new IllegalArgumentException();
		double sum_x=0;
		
		for (int i=0; i<n; i++) {
			final double xi=ax.elementDouble(i);
			final double yi=ay.elementDouble(i);
			sum_x+=abs(xi-yi) ;		 
		}
		
		if (norm)
			sum_x/=(double)n;
  
		return sum_x;	
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("not an array");		
	}
	/*public static double sqdiff(float[] x, float[] y, boolean norm) {
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
	}*/
	
	public static<A> double sqdiff(A x, A y, boolean norm) {
		try {
			Access<?> ax=Access.rawAccess(x, null);
			Access<?> ay=Access.rawAccess(y, null);
			int n=(int) ax.length();
			final int n1=(int) ay.length();
			if (n!=n1) 
				throw new IllegalArgumentException();
			double sum_x=0;
			
			for (int i=0; i<n; i++) {
				final double xi=ax.elementDouble(i);
				final double yi=ay.elementDouble(i);
				sum_x+=(xi-yi)*(xi-yi);			 
			}
			
			if (norm)
				sum_x/=(double)n;
	  
			return sum_x;	
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("not an array");		
	}
	

	public static<A> double sqrtdiff(A x, A y, boolean norm) {
		try {
			Access<?> ax=Access.rawAccess(x, null);
			Access<?> ay=Access.rawAccess(y, null);
			int n=(int) ax.length();
			final int n1=(int) ay.length();
			if (n!=n1) 
				throw new IllegalArgumentException();
			double sum_x=0;
			
			for (int i=0; i<n; i++) {
				final double xi=ax.elementDouble(i);
				final double yi=ay.elementDouble(i);
				sum_x+=(xi-yi)*(xi-yi);			 
			}
			sum_x=sqrt(sum_x);
			
			if (norm)
				sum_x/=(double)n;
	  
			return sum_x;	
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("not an array");		
	}
	
	
	
	/*public static double corrcoef(float[] x, float[] y) {
		int n=x.length;
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
		n--;
		sum_x/=(double)n;
		sum_y/=(double)n;
		double r=sum_xy-n*sum_x*sum_y;
		r/=Math.sqrt(sum_x2 - n* sum_x*sum_x)*Math.sqrt(sum_y2 - n* sum_y*sum_y);
		return r;
		
	}*/
	
	public static<A> double corrcoef(A x, A y) {
		try {
			Access<?> ax=Access.rawAccess(x, null);
			Access<?> ay=Access.rawAccess(y, null);
			int n=(int) ax.length();
			final int n1=(int) ay.length();
			if (n!=n1) 
				throw new IllegalArgumentException();
			double sum_x=0, sum_y=0,sum_x2=0, sum_y2=0, sum_xy=0;
			
			for (int i=0; i<n; i++) {
				final double xi=ax.elementDouble(i);
				final double yi=ay.elementDouble(i);
				sum_x+=xi;
				sum_y+=yi;
				
				sum_xy+=xi*yi;
				sum_x2+=xi*xi;
				sum_y2+=yi*yi;			
			}
			n--;
			sum_x/=(double)n;
			sum_y/=(double)n;
			double r=sum_xy-n*sum_x*sum_y;
			r/=Math.sqrt(sum_x2 - n* sum_x*sum_x)*Math.sqrt(sum_y2 - n* sum_y*sum_y);
			return r;
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("not an array");		
	}
	
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
