package ijaux.funct;


import ijaux.datatype.ElementOutput;

import java.util.HashMap;

public abstract class AbstractElementFunction<A, B> implements ElementFunction<A, B>,   ElementOutput {

	private HashMap<String,Object> params=new HashMap<String,Object>();
	
	protected Class<?> type;
	

	public void setParam(String key, Object value) {
		 params.put(key, value);
	}

	public Object getParam(String key) {
		 return params.get(key);
	}


}
