package ijaux.datatype.access;

import ijaux.Constants;

public interface PrimitiveAccess<VectorType> extends Constants {

	/*
	 *  read methods
	 */
	
	byte elementByte(int index);
	
	byte elementByte(VectorType coords);
	
	short elementShort(int index);
	
	short elementShort(VectorType coords);
	
	int elementInt(int index);
	
	int elementInt(VectorType coords);
	
	float elementFloat(int index);
	
	float elementFloat(VectorType coords);
	
	double elementDouble(int index);
	
	double elementDouble(VectorType coords);
	
	boolean elementBool(int index);
	
	boolean elementBool(VectorType coords);
	
	char elementChar(int index);
	
	char elementChar(VectorType coords);
	
	/*
	 *  write methods
	 */
	
	
	void putByte(int index, byte value);
	
	void putShort(int index, short value);
	
	void putInt(int index, int value);
	
	void putFloat(int index, float value);
	
	void putDouble(int index, double value);
	
	void putBool(int index, boolean value);
	
	void putChar(int index, char value);
	
	void putInt(VectorType coords, int value);
	
	void putDouble(VectorType coords, double value);
	
	void putBool(VectorType coords, boolean value);
	
}
