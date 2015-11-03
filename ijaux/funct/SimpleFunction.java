package ijaux.funct;

/*
 *  Two argument (primitive) function interface
 */
public interface SimpleFunction<N> {
	
	public <A, B>  N op(A a, B b);
	
	/*
	 *  integer type operations
	 */
	public  N opInt(int a, int b);	
	
	/*
	 *  floating point  operations
	 */
	public  N opFloat(float a, float b);
	
	/*
	 *  double precision operations
	 */
	public  N opDouble(double a, double b);
	
	/*
	 *  boolean type operations
	 */
	public  N opBool(boolean a, boolean b);
}
