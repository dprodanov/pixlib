package ijaux.hypergeom.morphology;

import ijaux.datatype.Pair;
import ijaux.funct.PrimitiveElementFunction;
import ijaux.stats.CubeHistogram;
import ijaux.stats.HyperCubeHistogram;

public class Dilation<E extends Number> extends MorphoFunction<E> {

	PrimitiveElementFunction<E> mmorpho;
	
	public Dilation(StructureElement<E> se) {
		super(se);
		Pair<Number,Number> p=se.getMinMax();
		init();
	}
	
	public Dilation(StructureElement<E> se, Pair<E,E> minmax) {
		super(se);
		if (minmax!=null) {
			se.setMinMax(minmax);
			Pair<Number,Number> p=se.getMinMax();
			init();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void initFunction(final CubeHistogram<E> ch) {
		Pair<Number,Number> p=null;
		if (ch!=null) {
			p=(Pair<Number,Number>) ch.getMinMax();
			se.setMinMax(ch);
			init();
		}
		
		
	}

	@SuppressWarnings("unchecked")
	private void init() {
		System.out.println("initFunction: " +type);
		if (type==byte.class)  {
			System.out.println("byte case");
			final ByteDilation be=  new ByteDilation((StructureElement<Byte>)se);
			mmorpho= (MorphoFunction<E>) be;
			return;
		}
		
		if (type==short.class)  {
			System.out.println("short case");
			final ShortDilation be=  new ShortDilation((StructureElement<Short>)se);
			mmorpho= (MorphoFunction<E>) be;
			return;
		}
		
		if (type==float.class)  {
			System.out.println("float case");
			final FloatDilation be=  new FloatDilation((StructureElement<Float>)se);
			mmorpho= (MorphoFunction<E>) be;
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
	
	@Override
	public void setMinMax(double dmin, double dmax) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMinMax(int imin, int imax) {
		// TODO Auto-generated method stub
		
	}
	/*
	  * Dilation for byte primitive type
	  */
 class ByteDilation extends MorphoFunction<Byte> {
	 int gmin=0;
	 int gmax=0xFF;
	 
	 int tmax=gmin;
	 int tmin=gmax;
	 
/*
 * 
 */
	public ByteDilation(StructureElement<Byte> se) {
		super(se);
//		Pair<Number,Number> p=se.minmax;
//		gmin=p.first.intValue();
		tmin=gmin;
		 
//		gmax=p.second.intValue();
		tmax=gmax;
		
		System.out.println(">gmin byte: "+gmin);
		System.out.println(">gmax byte: "+gmax);
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
		return (byte)(getOutputInt() & byteMask);
	}

/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutputInt()
 */
	@Override
	public int getOutputInt() {
		
        final int ret=tmax-gmax;
        //System.out.print( ret+"; ");
        //tmin=gmin;
        //tmax=gmax;
        tmin=gmax;
        tmax=gmin;
        //System.out.print( tmin+"; "+(ret )+" ");
		return ret;
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
		int k= (a & byteMask) + (b & byteMask);	
		if (k>=tmax) {
			tmax=k;	
		}
		
	}

	
	
	@Override
	public void setMinMax(double dmin, double dmax) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setMinMax(int imin, int imax) {
		gmin= imin;
		gmax= imax;
		
		System.out.println("gmin byte: "+gmin);
		System.out.println("gmax byte: "+gmax);
	}
	 
	 
 }

 /*
  * Dilation for short primitive type
  */
 class ShortDilation extends MorphoFunction<Short> {
	 	 
	 int gmax=0xFFFF;
	 int gmin=0;
	 
	 int tmin=gmax;
 	 int tmax=gmin;
	 
	 public ShortDilation(StructureElement<Short> se) {
		super(se);
		Pair<Number,Number> p=se.minmax;
		gmin=p.first.intValue();
		tmin=gmin;
		 
		gmax=p.second.intValue();
		tmax=gmax;
		
		System.out.println("gmin short: "+gmin);
		System.out.println("gmax short: "+gmax);
	}
	 
	
	 
	@Override
	public void setMinMax(double dmin, double dmax) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setMinMax(int imin, int imax) {
		gmin=imin;
		gmax=imax;
		
		System.out.println("gmin short: "+gmin);
		System.out.println("gmax short: "+gmax);
	}
	
	
	@Override
	public int getOutputInt() {
		
        final int ret=tmax-gmax;
        //System.out.print( ret+"; ");
        tmin=gmin;
        tmax=gmax;
        //System.out.print( tmin+"; "+(ret )+" ");
		return ret & shortMask;
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
			//System.out.println(a +" "+b);
			final int k= (a & shortMask) + (b & shortMask);	
			if (k>tmax) {
				tmax=k;	
			}
			
		}

		@Override
		public void transform(Short a, Short b) {
			//System.out.println(a +" "+b);
			transformInt(a,b);
			
		}

 } // end class
 
 /*
  * Dilation for short primitive type
  */
 class FloatDilation extends MorphoFunction<Float> {
	 
	 float gmin=+1;
	 float tmin=gmin;
 
	 float gmax=-1;
	 float tmax=gmax;
	 
	 public FloatDilation(StructureElement<Float> se) {
		super(se);		
		Pair<Number,Number> p=se.minmax;
		gmax=p.second.floatValue();
		gmin=p.first.floatValue();
		tmin=gmin;
		tmax=gmax;
		System.out.println("gmin float: "+gmin);
		System.out.println("gmax float: "+gmax);
	}
	 

	 
	@Override
	public void setMinMax(double dmin, double dmax) {
		gmin=(float) dmin;
		gmax=(float) dmax;
		
		System.out.println("gmin float set: "+gmax);
		System.out.println("gmax float set: "+gmax);

		
	}
	
	@Override
	public void setMinMax(int imin, int imax) {
		
	}
	
	
	@Override
	public float getOutputFloat() {
		
        final float ret=tmax-gmax;
        //System.out.print( ret+"; ");
        tmin=gmin;
        tmax=gmax;
        //System.out.print( tmin+"; "+(ret )+" ");
		return ret ;
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
			final float k= a  + b;	
			if (k>tmax) {
				tmax=k;	
			}
			
		}

		@Override
		public void transform(Float a, Float b) {
			//System.out.print((a.intValue() & 0xFF) +" ");
			//System.out.print("a:"+a+" ");
			transformFloat(a,b);
			
		}

 }

}
