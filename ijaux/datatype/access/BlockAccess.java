package ijaux.datatype.access;

public interface BlockAccess {

	byte[] getByteArray();

	short[] getShortArray();

	float[] getFloatArray();

	double[] getDoubleArray();

	int[] getIntArray();

	boolean[] getBoolArray();

	char[] getCharArray();

	void putDoubleArray(double[] array);
	
	void putFloatArray(float[] array);
	
	void putByteArray(byte[] array);
	
	void putIntArray(int[] array);
	
	void putShortArray(short[] array);
	
	void putBoolArray(boolean[] array);
}