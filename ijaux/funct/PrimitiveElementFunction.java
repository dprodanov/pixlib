package ijaux.funct;

import ijaux.Constants;

public interface PrimitiveElementFunction<A> extends ElementFunction<A,A>, Constants  {
	
	@Override
	void transform(A a, A b);
	
	void transformByte(byte a, byte b);
	
	void transformShort(short a, short b);
	
	void transformInt(int a, int b);	
	
	void transformFloat(float a, float b);	
	
	void transformDouble(double a, double b);
	
	void transformBool(boolean a, boolean b);
	 
	//void transform(char a, char b);
	
	@Override
	A getOutput();
	
	byte getOutputByte();
	
	short getOutputShort();
	
	int getOutputInt();
	
	float getOutputFloat();
	
	double getOutputDouble();

	boolean getOutputBoolean();
	
//	char getOutputChar();
}
