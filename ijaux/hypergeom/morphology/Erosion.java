package ijaux.hypergeom.morphology;

import ijaux.datatype.Pair;
import ijaux.funct.PrimitiveElementFunction;
import ijaux.stats.CubeHistogram;
import ijaux.stats.HyperCubeHistogram;

public class Erosion<E extends Number> extends MorphoFunction<E> {

	MorphoFunction<E> mmorpho;
	
	public Erosion(StructureElement<E> se) {
		super(se);
		init();
	}
	
	public Erosion(StructureElement<E> se, Pair<E,E> minmax) {
		super(se);
		if (minmax!=null) {
			se.setMinMax(minmax);
			init();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void initFunction(final CubeHistogram<E> ch) {
		
		if (ch!=null) {
			Pair<Number,Number> p= (Pair<Number, Number>) ch.getMinMax();
			se.setMinMax(ch);			
			init();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		System.out.println("initFunction: " +type);
		if (type==byte.class)  {
			System.out.println("byte case");
			final ByteErosion be=  new ByteErosion((StructureElement<Byte>)se);
			mmorpho=  (MorphoFunction<E>) be;
			return;
		}
		
		if (type==short.class)  {
			System.out.println("short case");
			final ShortErosion be=  new ShortErosion((StructureElement<Short>)se);		 
			mmorpho=  (MorphoFunction<E>) be;
			return;
		}
		
		if (type==float.class)  {
			System.out.println("float case");
			final FloatErosion fe=  new FloatErosion((StructureElement<Float>)se);	
			mmorpho= (MorphoFunction<E>) fe;
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
	  * Erosion for byte primitive type
	  */
 class ByteErosion extends MorphoFunction<Byte> {
	  int gmin=0;
	  int gmax=0xFF;
	 
	 int tmax=gmin;
	 int tmin=gmax;
	 
/*
 * 
 */
	public ByteErosion(StructureElement<Byte> se) {
		super(se);
		//Pair<Number,Number> p=se.minmax;
		//gmin=p.first.intValue();
		//gmin=se.getMin();
		//gmax=se.getMax();
		 
		//gmax=p.second.intValue();
		//tmin=gmin;
		//tmax=gmax;
		//tmin=255;
		//tmax=0;
		
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
		return (byte)(getOutputInt() & byteMask ) ;
	}

/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutputInt()
 */
	@Override
	public int getOutputInt() {
		
        final int ret=tmin+gmax;
        //System.out.print( (tmin & byteMask)+"; "+(ret )+" ");
        //System.out.print( ret+"; ");
        tmin=gmax;
        tmax=gmin;
        //tmin=255;
        //tmax=0;
      //System.out.print("\n");
		return ret;
	}

/*
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.MorphoFunction#transform(java.lang.Number, java.lang.Number)
 */
	@Override
	public void transform(Byte a, Byte b) {
		//System.out.print((a.intValue() & 0xFF) +" ");
		transformInt(a,b);		
	}
	
	@Override
	public void transformInt(int a, int b) {
		final int k= (a & byteMask) - (b & byteMask);	
		if (k<=tmin) {
			tmin=k;	
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
//		System.out.println("setting minmax byte");
//		System.out.println("gmin : "+gmin+" gmax: "+gmax);
		
	}
	 
	 
 }

 /*
  * Erosion for short primitive type
  */
 class ShortErosion extends MorphoFunction<Short> {
	 int gmin=65535;
	 int tmin=gmin;
 
	 int gmax=0;
	 int tmax=gmax;
	 
	 
	 public ShortErosion(StructureElement<Short> se) {
		super(se);
		Pair<Number,Number> p=se.minmax;
		gmin=p.first.intValue();
		gmax=p.second.intValue();
		tmin=gmin;
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
		gmin= imin;
		gmax= imax;
		
		System.out.println("gmin short: "+gmin);
		System.out.println("gmax short: "+gmax);
	}
	
	
	@Override
	public int getOutputInt() {
		
        final int ret=tmin+gmax;
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
		public Short getOutput() {
			return  getOutputShort();
		}
	/*
	 * (non-Javadoc)
	 * @see ijaux.hypergeom.morphology.MorphoFunction#getOutputByte()
	 */
		
		@Override
		public short getOutputShort() {
			return (short)(getOutputInt() & shortMask);
		}
		
		@Override
		public void transformInt(int a, int b) {
			//System.out.println(a +" "+b);
			final int k= (a & shortMask) - (b & shortMask);	
			if (k<tmin) {
				tmin=k;	
			}
			
		}

		@Override
		public void transform(Short a, Short b) {
			//System.out.println(a +" "+b);
			transformInt(a,b);
			
		}

 } // end class
 
 /*
  * Erosion for short primitive type
  */
 class FloatErosion extends MorphoFunction<Float> {
	 float gmin=+1;
	 float tmin=gmin;
 
	 float gmax=-1;
	 float tmax=gmax;
	 
	 public FloatErosion(StructureElement<Float> se) {
		super(se);		
		Pair<Number,Number> p=se.minmax;
		gmin=p.first.floatValue();
		gmax=p.second.floatValue();
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
		
        final float ret=tmin+gmax;
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
			final float k= a  - b;	
			if (k<tmin) {
				tmin=k;	
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
