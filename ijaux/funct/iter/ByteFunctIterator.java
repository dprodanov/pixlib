package ijaux.funct.iter;

import ijaux.funct.SimpleFunction;
import ijaux.iter.array.ByteForwardIterator;

public class ByteFunctIterator extends ByteForwardIterator implements
FunctionIterator<Byte>
{
	
	private byte[] bpixels;

	public ByteFunctIterator(Object cpixels) {
		super(cpixels);
		bpixels=(byte[]) pixels; 
	}
	
	public ByteFunctIterator(Object pixels, SimpleFunction<Byte> f) {
		this(pixels);
		setFunct(f);
	}
	
	
	@Override
	public <N> Byte nextf(N a) {
		final int elem= bpixels[i] & mask;
		final Byte result=funct.op(elem, a);
		bpixels[i++]=result;		
		return result;	
	}

	@Override
	public Byte nextf(byte a) {
		return nextf(a);
	}

	@Override
	public Byte nextf(short a) {
		return nextf(a);
	}

	@Override
	public Byte nextf(int a) {
		final int elem= bpixels[i];
		final Byte result=funct.opInt(elem, a);
		bpixels[i++]=result;
		return result;	 
	}

	@Override
	public Byte nextf(float a) {
		final float elem= bpixels[i] & mask;
		final Byte result=funct.opFloat(elem, a);
		bpixels[i++]=result;		
		return result;	 
	}

	@Override
	public Byte nextf(double a) {
		final double elem= bpixels[i] & mask;
		final Byte result=funct.opDouble(elem, a);
		bpixels[i++]=result;		
		return result;	 
	}

	@Override
	public Byte nextf(boolean a) {
		final boolean elem= nextBool();
		dec();
		final Byte result=funct.opBool(elem, a);
		bpixels[i++]=result;
		return result;
	}

	@Override
	public Byte nextf(char a) {
		return nextf((int)a);
	}

}
