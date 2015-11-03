package ijaux.hypergeom.morphology;

import ijaux.datatype.Pair;
import ijaux.funct.PrimitiveElementFunction;
import ijaux.stats.CubeHistogram;
import ijaux.stats.HyperCubeHistogram;

public class RankMax<E extends Number> extends MorphoFunction<E> {

	PrimitiveElementFunction<E> mmorpho;
	
	public RankMax(StructureElement<E> se) {
		super(se);
 
	}
	
	@SuppressWarnings("unchecked")
	public void initFunction(final CubeHistogram<E> ch) {
		Pair<Number,Number> p=null;
		if (ch!=null) {
			p=(Pair<Number,Number>) ch.getMinMax();
			se.setMinMax(ch);
		}
		System.out.println("initFunction: " +type);
		if (type==byte.class)  {
			System.out.println("byte case");
			final ByteRank be=  new ByteRank((StructureElement<Byte>)se);
			
			mmorpho= (PrimitiveElementFunction<E>) be;
			return;
		}
		
		if (type==short.class)  {
			System.out.println("short case");
			final ShortRank be=  new ShortRank((StructureElement<Short>)se);
			if (p!=null)
				be.setMinMax( p.second.intValue(), p.first.intValue());
		 
			mmorpho= (PrimitiveElementFunction<E>) be;
			return;
		}
		
		if (type==float.class)  {
			System.out.println("float case");
			final FloatRank be=  new FloatRank((StructureElement<Float>)se);
			if (p!=null)
				be.setMinMax(p.second.floatValue(),p.first.floatValue());
			
			mmorpho= (PrimitiveElementFunction<E>) be;
			return;
		}
		
	}
	
	 
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutput()
 */
	@Override
	public E getOutput() {
		return mmorpho.getOutput();
	}

/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutputByte()
 */
	@Override
	public byte getOutputByte() {
		return mmorpho.getOutputByte();
	}
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutputDouble()
 */
	@Override
	public double getOutputDouble() {
		return mmorpho.getOutputDouble();
	}
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutputFloat()
 */
	@Override
	public float getOutputFloat() {
		return mmorpho.getOutputFloat();
	}
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutputInt()
 */
	@Override
	public int getOutputInt() {
		return mmorpho.getOutputInt();
	}
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutputShort()
 */
	@Override
	public short getOutputShort() {
		return mmorpho.getOutputShort();
	}
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#transform(java.lang.Number, java.lang.Number)
 */
	@Override
	public void transform(E a, E b) {
		mmorpho.transform(a, b);
		
	}
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#transformByte(byte, byte)
 */
	@Override
	public void transformByte(byte a, byte b) {
		mmorpho.transformByte(a, b);
		
	}
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#transformShort(short, short)
 */
	@Override
	public void transformShort(short a, short b) {
		mmorpho.transformShort(a, b);		
	}
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#transformInt(int, int)
 */
	@Override
	public void transformInt(int a, int b) {
		mmorpho.transformInt(a, b);	
		
	}
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#transformFloat(float, float)
 */
	@Override
	public void transformFloat(float a, float b) {
		mmorpho.transformFloat(a, b);	
		
	}
	
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#transformDouble(double, double)
 */
	@Override
	public void transformDouble(double a, double b) {
		mmorpho.transformDouble(a, b);	
		
	}
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#transformBool(boolean, boolean)
 */
	@Override
	public void transformBool(boolean a, boolean b) {
		mmorpho.transformBool(a, b);	
		
	}
	 
	/*
	  * Rank max for byte primitive type
	  */
 private class ByteRank extends MorphoFunction<Byte> {
	 boolean tmax=true;
	 int gret=0;
	 
/*
 * 
 */
	public ByteRank(StructureElement<Byte> se) {
		super(se);
	}
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutput()
 */
	@Override
	public Byte getOutput() {
		return  getOutputByte();
	}
/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutputByte()
 */
	
	@Override
	public byte getOutputByte() {
		return (byte)getOutputInt();
	}

/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutputInt()
 */
	@Override
	public int getOutputInt() {
		gret=0;		
		if (tmax)
		     gret=-1;   
        //System.out.print( (tmax )+" ");
		tmax=true;
		 
		return gret & byteMask;
	}

/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#transform(java.lang.Number, java.lang.Number)
 */
	@Override
	public void transform(Byte a, Byte b) {
		//System.out.print((a.intValue() & 0xFF) +" ");
		//System.out.print("a:"+a+" ");
		transformInt(a,b);
		
	}
	
	@Override
	public void transformInt(int a, int b) {		
		 //System.out.print((a<b) +",");
		 tmax= tmax && ((a & byteMask)<=(b & byteMask));
		
	}

	
	 
	 
	 
 }

 /*
  * Rank max  for short primitive type
  */
 private class ShortRank extends MorphoFunction<Short> {
	 public ShortRank(StructureElement<Short> se) {
		super(se);
		// TODO Auto-generated constructor stub
	}
	 
	 boolean tmax=true;
	 int gret=0;
	 
	 
 
	
	
	@Override
	public int getOutputInt() {
		gret=0;		
		if (tmax)
		     gret=-1;   
        //System.out.print( (tmax )+" ");
		tmax=true;

		return gret & shortMask;
	}
	
	/*
	 * (non-Javadoc)
	 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutput()
	 */
		@Override
		public Short getOutput() {
			return  getOutputShort();
		}
	/*
	 * (non-Javadoc)
	 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutputByte()
	 */
		
		@Override
		public short getOutputShort() {
			return (short)getOutputInt();
		}
		
		@Override
		public void transformInt(int a, int b) {	 
			 //System.out.print((a<b) +",");
			 tmax= tmax && ((a & shortMask)<=(b & shortMask));
			
		}

		@Override
		public void transform(Short a, Short b) {
			//System.out.println(a +" "+b);
			transformInt(a,b);
			
		}

 } // end class
 
 /*
  * Rank max for short primitive type
  */
 private class FloatRank extends MorphoFunction<Float> {
	 
	 public FloatRank(StructureElement<Float> se) {
		super(se);		
	}
	 
	 
	 boolean tmax=true;
	 float gret=0;
	 
 
	
	
	@Override
	public float getOutputFloat() {
		gret=0;		
		if (tmax)
		     gret=1;   
        //System.out.print( (tmax )+" ");
		tmax=true;
	 
		return gret ;
	}
	
	/*
	 * (non-Javadoc)
	 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutput()
	 */
		@Override
		public Float getOutput() {
			return  getOutputFloat();
		}
	/*
	 * (non-Javadoc)
	 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutputByte()
	 */
		
		 
		
		@Override
		public void transformFloat(float a, float b) {
			 //System.out.print((a<b) +",");
			 tmax= tmax && (a<=b);
			// gret=b;
		}

		@Override
		public void transform(Float a, Float b) {
			//System.out.print(b +"-"+a +"; ");
			transformFloat(a,b);
			
		}

 }

}
