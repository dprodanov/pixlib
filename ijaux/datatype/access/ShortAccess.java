package ijaux.datatype.access;

import ijaux.datatype.Pair;
import ijaux.hypergeom.index.Indexing;

public class ShortAccess extends Access<Short> {
	private short [] spixels;
	
	private int mask=shortMask;
	
	public ShortAccess(Object cpixels, Indexing<int[]> aind) {
		super.setPixels(cpixels);
		super.setIndexing(aind);
		spixels=(short[]) pixels; 
	}
	
	public void setMask(int m) {
		mask=m;
	}
	
	@Override
	public Short element(int index) {
		return elementShort(index);
	}

	@Override
	public Short element(int[] coords) {
		return elementShort(coords);
	}
	@Override
	public boolean elementBool(int index) {
		return (elementShort(index)>0);
	}

	@Override
	public boolean elementBool(int[] coords) {
		return (elementShort(coords)>0);
	}

	@Override
	public byte elementByte(int index) {
		return (byte)(elementShort(index) & mask);
	}

	@Override
	public byte elementByte(int[] coords) {
		return (byte)(elementShort(coords) & mask);
	}

	@Override
	public char elementChar(int index) {
		return (char)(elementInt(index)) ;
	}

	@Override
	public char elementChar(int[] coords) {
		return (char)(elementInt(coords)) ;
	}

	@Override
	public double elementDouble(int index) {
		return elementShort(index) & mask;
	}

	@Override
	public double elementDouble(int[] coords) {
		return elementShort(coords) & mask;
	}

	@Override
	public float elementFloat(int index) {
		return elementShort(index) & mask;
	}

	@Override
	public float elementFloat(int[] coords) {
		return elementShort(coords) & mask;
	}

	@Override
	public int elementInt(int index) {
		return elementShort(index) & mask;
	}

	@Override
	public int elementInt(int[] coords) {
		return elementShort(coords) & mask;
	}

	@Override
	public short elementShort(int index) {
		return spixels[index];
	}

	@Override
	public short elementShort(int[] coords) {
		index=pIndex.indexOf(coords);
		return elementShort(index);
	}

	@Override
	public void putBool(int index, boolean value) {
		final int b= (value)? -1:0;
		putShort(index, (short)b);
		
	}

	@Override
	public void putByte(int index, byte value) {
		putShort(index, value);
		
	}

	@Override
	public void putChar(int index, char value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putDouble(int index, double value) {
		putInt( index, (int) value);
		
	}

	@Override
	public void putFloat(int index, float value) {
		putInt( index, (int) value);
		
	}

	@Override
	public void putInt(int index, int value) {
		putShort(index, (short)(value & shortMask));
		
	}

	@Override
	public void putShort(int index, short value) {
		spixels[index]=value;
		
	}
	
	@Override
	public void putE(int index, Short value) {
		putShort(index,  value);
		
	}
	
	@Override
	public void put(Pair<int[],Short> pair) {
		putV(pair.first, pair.second);
	}
	
	@Override
	public void putV(int[] coords, Short value) {
		index=pIndex.indexOf(coords);
		putShort(index, value);
	}
	
	@Override
	public int[] size() {
		final int a=spixels.length;
		return new int[]{a,0};
	}

	@Override
	public void putInt(int[] coords, int value) {
		index=pIndex.indexOf(coords);		
		putShort(index, (short)(value & shortMask));
		
	}

	@Override
	public void putDouble(int[] coords, double value) {
		index=pIndex.indexOf(coords);		
		putInt( index, (int) value);
		
	}

	@Override
	public void putBool(int[] coords, boolean value) {
		index=pIndex.indexOf(coords);
		final int b= (value)? -1:0;
		putShort(index, (short)b);
		
	}

}
