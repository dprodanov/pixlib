package ijaux.iter.array;

public interface PrimitiveIterator {

	int nextInt();

	byte nextByte();

	short nextShort();

	float nextFloat();

	double nextDouble();
	
	boolean nextBool();
	
	char nextChar();

	void putInt(int val);

	void putByte(byte val);

	void putShort(short val);

	void putFloat(float val);

	void putDouble(double val);
	
	void putBool(boolean val);
	
	void putChar(char val);

}