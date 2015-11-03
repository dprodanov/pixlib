package ijaux.hypergeom.morphology;

 
import ijaux.funct.AbstractElementFunction;
import ijaux.funct.PrimitiveElementFunction;
 

public abstract class MorphoFunction<E extends Number> extends AbstractElementFunction<E, E> 
implements PrimitiveElementFunction<E>{

	StructureElement<E> se; 
	
	public MorphoFunction(StructureElement<E> se) {		 
		type=se.getType();
		this.se=se; 
	}

	public  void setMinMax(double dmin, double dmax) {
		
	}
	
	public  void setMinMax(int imin, int imax) {
		
	}
	

	
	/*
	 * Boiler plate code
	 */
	/*
	 * (non-Javadoc)
	 * @see ijaux.funct.ElementFunction#getOutput()
	 */
	@Override
	public E getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getOutputBoolean() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getOutputByte() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getOutputDouble() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getOutputFloat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOutputInt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getOutputShort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void transform(E a, E b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transformBool(boolean a, boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transformByte(byte a, byte b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transformDouble(double a, double b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transformFloat(float a, float b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transformInt(int a, int b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transformShort(short a, short b) {
		// TODO Auto-generated method stub
		
	}

	 

}
