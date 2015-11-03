package ijaux.hypergeom.morphology;

import ijaux.datatype.Pair;
import ijaux.funct.AbstractComplexFunction;

public abstract class MorphoComplexFunction<E extends Number> extends AbstractComplexFunction<E> {

	StructureElement<E> se; 
	
	public MorphoComplexFunction(StructureElement<E> se) {		 
		type=se.getType();
		this.se=se; 
	}

	public  void setMinMax(double dmin, double dmax) {
		
	}
	
	public  void setMinMax(int imin, int imax) {
		
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

	@Override
	public Pair<E, E> getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

}
