package ijaux.iter.compl;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ijaux.Util;
import ijaux.datatype.Pair;
import ijaux.iter.AbstractIterator;


public class PairIterator<C extends Pair<?,?>> extends AbstractIterator<C> {
  
	protected  ArrayList<C>	array;
	private boolean setarray=false;
	
	public PairIterator(){
		
	}

	public PairIterator (ArrayList<C> arr) {
		array=arr;
		size=arr.size();
		final Pair<?,?> p=array.get(0);
		returnType=Util.getTypeMapping(p.first.getClass());
		setarray=true;
	}
	
	public PairIterator(C[] arr) {
		setarray=setArray(arr);
		if (setarray) {
			final Pair<?,?> p=arr[0];
			returnType=Util.getTypeMapping(p.first.getClass());
		}
			
	}
	
	public boolean setArray(C[] arr) {
		array=new ArrayList<C>();
		for (C val:arr) {
			array.add(val);
		}
		size=arr.length;
		return true;
	}
	
	public void validate() {
		size=array.size();
		if (size>0) {
			final Pair<?,?> p=array.get(0);
			returnType=Util.getTypeMapping(p.first.getClass());
			setarray=true;
		}
	}
	
	@Override
	public boolean hasNext() {
 		return (i<size);
	}


	@Override
	public C next() {
		return array.get(i++);
	}

	@Override
	public void put(C value) {
		array.add(value);	
		i++;
	}
	
	@Override
	public void remove() { 
		array.remove(i);
		size--;
	}

	
}
