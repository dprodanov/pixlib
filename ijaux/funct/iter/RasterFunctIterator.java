package ijaux.funct.iter;


import ijaux.funct.SimpleFunction;
import ijaux.iter.seq.RasterIterator;


import java.util.RandomAccess;

public class RasterFunctIterator<E> extends RasterIterator<E> implements RandomAccess,
		FunctionIterator<E> {
	
	
	protected SimpleFunction<E> funct;

	public RasterFunctIterator(Object pixels) {
		setPixels(pixels);
	}
	
	public RasterFunctIterator(Object pixels, SimpleFunction< E> f) {
		setPixels(pixels);
		setFunct(f);
	}
	
	public SimpleFunction<E> getFunct() {
		return funct;
	}

	public void setFunct(SimpleFunction<E> funct) {
		this.funct = funct;
	}

	
	
	@Override
	public E next() {
		return access.element(i++);
	}


	@Override
	public boolean hasNext() {
		return (i<size);
	}
	
	@Override
	public E nextf(int a) {
		final int elem= access.elementInt(i);
		final E result=funct.opInt(elem, a);
		access.putE(i, result);
		i++;
		return result;
	} 
	
	@Override
	public <N> E nextf(N a) {
		final E elem=access.element(i);
		final E result=funct.op(elem, a);
		access.putE(i, result);
		i++;
		return result;
	}

	@Override
	public E nextf(byte a) {
		return nextf ((int)a);
	}

	@Override
	public E nextf(short a) {
		return nextf ((int)a);
	}

	@Override
	public E nextf(float a) {
		final float elem= access.elementFloat(i);
		final E result=funct.opFloat(elem, a);
		access.putE(i, result);
		i++;
		return result;
	}

	@Override
	public E nextf(double a) {
		final double elem= access.elementDouble(i);
		final E result=funct.opDouble(elem, a);
		access.putE(i, result);
		i++;
		return result;
	}

	@Override
	public E nextf(boolean a) {
		final boolean elem= access.elementBool(i);
		final E result=funct.opBool(elem, a);
		access.putE(i, result);
		i++;
		return result;
	}

	@Override
	public E nextf(char a) {
		return nextf ((int)a);
	}
	


}
