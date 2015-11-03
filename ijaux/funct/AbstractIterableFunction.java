/**
 * 
 */
package ijaux.funct;

import ijaux.iter.AbstractIterator;
import ijaux.iter.seq.RasterForwardIterator;

import java.util.HashMap;



/**
 * @author prodanov
 * @param <T>
 *
 */
public abstract class AbstractIterableFunction<A extends AbstractIterator<?>, B extends AbstractIterator<?>> 
implements IterableFunction<A, B> {
	
	private HashMap<String,Object> params=new HashMap<String,Object>();
	
	protected Class<?> type;

	
	public void setParam(String key, Object value) {
		 params.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public Object getParam(String key) {
		 return params.get(key);
	}
 

}
