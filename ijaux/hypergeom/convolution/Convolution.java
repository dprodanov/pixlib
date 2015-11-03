package ijaux.hypergeom.convolution;

import ijaux.funct.AbstractElementFunction;
import ijaux.hypergeom.morphology.StructureElement;

/*
 * @author Dimiter Prodanov
 */
public class Convolution<E extends Number> extends AbstractElementFunction<E,E> {
	
	StructureElement<E> se=null;
	Kernel<?,?> kern=null;
	ConvFunction<E> conv;
	
	public static boolean debug=false;
	
	public Convolution(StructureElement<E> se) {
		type=se.getType();
		this.se=se; 
		initFunction();
		conv.weight(se);
	}
	
	public Convolution(Kernel<E,?> kern) {
		type=kern.getType();
		this.kern=kern; 
		initFunction();
	}
	
	@SuppressWarnings("unchecked")
	private void initFunction() {
		if (debug) 
			System.out.println("initFunction: " +type);
		if (type==byte.class)  {
			if (debug) 
				System.out.println("byte case");
			final ConvByte be=  new ConvByte();		
			conv= (ConvFunction<E>) be;
	
		}
		
		if (type==short.class)  {
			if (debug) 
				System.out.println("short case");
			final ConvShort be=  new ConvShort();  
			conv= (ConvFunction<E>) be;
		
		}
		
		if (type==float.class)  {
			if (debug) 
				System.out.println("float case");
			final ConvFloat be=  new ConvFloat();
			conv= (ConvFunction<E>)  be;
		
		}
		
		
	}
	 
	class ConvByte extends ConvFunction<Byte>  {

		@Override
		public void transform(Byte a, Byte b) {
			transformByte(a,b);			
		}

		@Override
		public Byte getOutput() {
			return getOutputByte();
		}
 		
	}
	
	class ConvShort extends ConvFunction<Short>  {

		@Override
		public void transform(Short a, Short b) {
			transformShort(a,b);			
		}

		@Override
		public Short getOutput() {
			return getOutputShort();
		}
		
	}
	
	class ConvFloat extends ConvFunction<Float>  {

		@Override
		public void transform(Float a, Float b) {
			transformFloat(a,b);		
		}

		@Override
		public Float getOutput() {
			return getOutputFloat();
		}
		
	}
	
	class ConvDouble extends ConvFunction<Double>  {

		@Override
		public void transform(Double a, Double b) {
			transformDouble(a,b);		
		}

		@Override
		public Double getOutput() {
			return getOutputDouble();
		}
		
	}

	@Override
	public void transform(E a, E b) {
		conv.transform(a, b);		
	}

	@Override
	public void transformBool(boolean a, boolean b) {
		conv.transformBool(a, b);	
		
	}

	@Override
	public void transformByte(byte a, byte b) {
		conv.transformByte(a, b);	
	}

	@Override
	public void transformDouble(double a, double b) {
		conv.transformDouble(a, b);	
	}

	@Override
	public void transformFloat(float a, float b) {
		conv.transformFloat(a, b);		
	}

	@Override
	public void transformInt(int a, int b) {
		conv.transformInt(a, b);	
	}

	@Override
	public void transformShort(short a, short b) {
		conv.transformShort(a, b);	
	}

	@Override
	public E getOutput() {
		return conv.getOutput();
	}

	@Override
	public boolean getOutputBoolean() {
		return conv.getOutputBoolean();
	}

	@Override
	public byte getOutputByte() {
		return conv.getOutputByte();
	}

	@Override
	public double getOutputDouble() {
		return conv.getOutputDouble();
	}

	@Override
	public float getOutputFloat() {
		return conv.getOutputFloat();
	}

	@Override
	public int getOutputInt() {
		return conv.getOutputInt();
	}

	@Override
	public short getOutputShort() {
		return conv.getOutputShort();
	}

}
