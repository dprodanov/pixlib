package ijaux.funct;

public interface ElementFunction<A, B>  {
		
	void transform(B a, B b);
	
 
	public void transformBool(boolean a, boolean b);  

 
	public void transformByte(byte a, byte b); 


	public void transformDouble(double a, double b);  


	public void transformFloat(float a, float b);  


	public void transformInt(int a, int b);


	public void transformShort(short a, short b) ;
	
	A getOutput();
	



}
