package ijaux.funct.iter;


import ijaux.Constants;
import ijaux.funct.SimpleFunction;
import ijaux.iter.IndexedIterator;

/*
 *  Transforms an array by a function. 
 *  We don't pass explicitly the function argument
 */
public interface FunctionIterator<E > extends IndexedIterator<E>, Constants{
	
	public <N> E nextf(N a);
		
	public  E nextf(byte a);
	
	public  E nextf(short a);
	
	public  E nextf(int a);
	
	public  E nextf(float a);
	
	public  E nextf(double a);
	
	public  E nextf(boolean a);
	
	public  E nextf(char a);
	
	
}
