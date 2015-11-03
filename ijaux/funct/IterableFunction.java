package ijaux.funct;

import java.util.Iterator;

/*
 * IterableFunction is the interface for defining iterations over functions
 *  
 */


public interface IterableFunction<A extends Iterator<?>,B extends Iterator<?>>{
	
	  public void setIO(A in, B out);
	  
	  public void run();
	  
	  public <V> void setParam(String key, V value);
	  
	  public <V> V getParam(String key);
	 
}

