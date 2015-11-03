package ijaux.hypergeom.convolution;

import ijaux.datatype.Pair;
import ijaux.funct.ElementFunction;
import ijaux.funct.PrimitiveElementFunction;
import ijaux.hypergeom.VectorCube;
import ijaux.hypergeom.morphology.StructureElement;

/*
 * @author Dimiter Prodanov
 */
public abstract class ConvFunction<E extends Number>  implements ElementFunction<E, E> 
, PrimitiveElementFunction<E>{

	private double sum_d=0;
	private int sum_i=0;
	private boolean sum_b=false;
	private double c=1.0d;
	
	public void weight(StructureElement<E> se) {
		c=0;
		for (Pair<int[], E> p:se) {
			c+=p.second.doubleValue();
		}
	}
	
	public void weight(Kernel<E,?> se) {
		c=0;
		for (Pair<int[], E> p:se) {
			c+=p.second.doubleValue();
		}
	}
	
	public void weight(VectorCube<E> se) {
		c=0;
		for (Pair<int[], E> p:se) {
			c+=p.second.doubleValue();
		}
	}

	@Override
	public void transformByte(byte a, byte b) {
		sum_i+=a*b;
	}

	@Override
	public void transformShort(short a, short b) {
		sum_i+=a*b;
	}

	@Override
	public void transformInt(int a, int b) {
		sum_i+=a*b;
	}

	@Override
	public void transformFloat(float a, float b) {
		sum_d+=a*b;
	}

	@Override
	public void transformDouble(double a, double b) {
		sum_d+=a*b;		
	}

	@Override
	public void transformBool(boolean a, boolean b) {
		sum_b=(a && b) || sum_b;		
	}

	@Override
	public byte getOutputByte() {
		final byte ret=(byte)(sum_i/c);
		sum_i=0;
		return ret;
	}

	@Override
	public short getOutputShort() {
		final short ret= (short)(sum_i/c);
		sum_i=0;
		return ret;
	}

	@Override
	public int getOutputInt() {
		final int ret=(int)(sum_i/c);
		sum_i=0;
		return ret;
	}

	@Override
	public float getOutputFloat() {
		final float ret = (float)(sum_d/c);
		sum_d=0;
		return ret;
	}

	@Override
	public double getOutputDouble() {
		final double ret= sum_d/c;
		sum_d=0;
		return ret;
	}

	@Override
	public boolean getOutputBoolean() {
		return sum_b;
	}

}
