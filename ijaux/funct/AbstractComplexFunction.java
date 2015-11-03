package ijaux.funct;

import java.util.HashMap;

import ijaux.datatype.Pair;

public abstract class AbstractComplexFunction<E extends Number>  implements ElementFunction<Pair<E,E>, E> {

private HashMap<String,Object> params=new HashMap<String,Object>();
	
	protected Class<?> type;
	

	public void setParam(String key, Object value) {
		 params.put(key, value);
	}

	public Object getParam(String key) {
		 return params.get(key);
	} 
	
	 

	 

}
