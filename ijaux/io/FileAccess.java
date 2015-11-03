package ijaux.io;

import java.io.IOException;

import ijaux.datatype.Pair;
import ijaux.datatype.access.Access;
import ijaux.datatype.access.IndexedAccess;
import ijaux.datatype.access.PrimitiveAccess;
import ijaux.iter.IndexedIterator;

public abstract class FileAccess<N> extends IndexedAccess<N>
											implements PrimitiveAccess<int[]> {

	protected Access<N> access;
	
	protected String accessmode = "r";

	protected Cursor cursor;
	
	protected boolean isOpened=false;
	
	/*
	public void setPixels(Object cpixels) {
		 // we void this method
	}
	*/
	
	public abstract void open() ;
	
	public void close () {
		try {
			cursor.close();
			isOpened=false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public byte elementByte(int index) {
		return access.elementByte(index);
	}

	@Override
	public byte elementByte(int[] coords) {
		return access.elementByte(coords);
	}

	@Override
	public short elementShort(int index) {
		return access.elementShort(index);
	}

	@Override
	public short elementShort(int[] coords) {
		return access.elementShort(coords);
	}

	@Override
	public int elementInt(int index) {
		return access.elementInt(index);
	}

	@Override
	public int elementInt(int[] coords) {
		return access.elementInt(coords);
	}

	@Override
	public float elementFloat(int index) {
		return access.elementFloat(index);
	}

	@Override
	public float elementFloat(int[] coords) {
		return access.elementFloat(coords);
	}

	@Override
	public double elementDouble(int index) {
		return access.elementDouble(index);
	}

	@Override
	public double elementDouble(int[] coords) {
		return access.elementDouble(coords);
	}

	@Override
	public boolean elementBool(int index) {
		return access.elementBool(index);
	}

	@Override
	public boolean elementBool(int[] coords) {
		return access.elementBool(coords);
	}

	@Override
	public char elementChar(int index) {
		return access.elementChar(index);
	}

	@Override
	public char elementChar(int[] coords) {
		return access.elementChar(coords);
	}

	@Override
	public void putByte(int index, byte value) {
		access.putByte(index, value);
	}

	@Override
	public void putShort(int index, short value) {
		access.putShort(index, value);
	}

	@Override
	public void putInt(int index, int value) {
		access.putInt(index, value);
	}

	@Override
	public void putFloat(int index, float value) {
		access.putFloat(index, value);
	}

	@Override
	public void putDouble(int index, double value) {
		access.putDouble(index, value);
	}

	@Override
	public void putBool(int index, boolean value) {
		access.putBool(index, value);
	}

	@Override
	public void putChar(int index, char value) {
		access.putChar(index, value);
	}
	
	@Override
	public void putInt(int[] coords, int value) {
		access.putInt(coords, value);
		
	}

	@Override
	public void putDouble(int[] coords, double value) {
		access.putDouble(coords, value);
		
	}

	@Override
	public void putBool(int[] coords, boolean value) {
		access.putBool(coords, value);
		
	}
	
	@Override
	public void putDoubleArray(double[] array) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void putFloatArray(float[] array) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void putByteArray(byte[] array) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putShortArray(short[] array) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void putIntArray(int[] array) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void putBoolArray(boolean[] array) {
		// TODO Auto-generated method stub
		
	}


	
	@Override
	public N element(int index) {
		return access.element(index);
	}

	@Override
	public void putE(int index, N value) {
		access.putE(index, value);
	}

	@Override
	public N element(int[] coords) {
		return access.element(coords);
	}

	@Override
	public void putV(int[] coords, N value) {
		access.putV(coords, value);
	}

	@Override
	public void put(Pair<int[], N> pair) {
		access.put(pair);
	}

	@Override
	public char[] getCharArray() {
		return cursor.array(cursor.asCharBuffer());
	}

	@Override
	public byte[] getByteArray() {
		return cursor.array(cursor.asByteBuffer());
	}

	@Override
	public short[] getShortArray() {
		return cursor.array(cursor.asShortBuffer());
	}

	@Override
	public float[] getFloatArray() {
		return cursor.array(cursor.asFloatBuffer());
	}

	@Override
	public double[] getDoubleArray() {
		return cursor.array(cursor.asDoubleBuffer());
	}

	@Override
	public int[] getIntArray() {
		return cursor.array(cursor.asIntBuffer());
	}

	@Override
	public boolean[] getBoolArray() {
		return null;
	}

}