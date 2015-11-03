package ijaux.datatype.access;

import ijaux.datatype.Pair;
import ijaux.hypergeom.index.Indexing;

 

public class PagedFloatAccess extends  PagedAccess<Float> {
	private float[][] fpixels;
	
	public PagedFloatAccess(Object cpixels, Indexing<int[]> aind) {
		super(cpixels, aind);
		fpixels=(float[][]) pixels; 
		pageSize=fpixels[0].length;
	}
	
	
	@Override
	public Float element(int index) {
		return elementFloat(index);
	}

	@Override
	public Float element(int[] coords) {
		return elementFloat(coords);
	}
	@Override
	public boolean elementBool(int index) {
		return (elementFloat(index)>0);
	}

	@Override
	public boolean elementBool(int[] coords) {
		return (elementFloat(coords)>0);
	}

	@Override
	public byte elementByte(int index) {
		return (byte)(elementInt(index) & byteMask);
	}

	@Override
	public byte elementByte(int[] coords) {
		return (byte)(elementInt(coords) & byteMask);
	}

	@Override
	public char elementChar(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char elementChar(int[] coords) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double elementDouble(int index) {
		return elementFloat(index);
	}

	@Override
	public double elementDouble(int[] coords) {
		return elementFloat(coords);
	}

	@Override
	public float elementFloat(int index) {
		x=index/pageSize;
		//y=index  - pageSize*x;//index% pageSize;
		y=index% pageSize;
		return fpixels[x][y];
	}

	@Override
	public float elementFloat(int[] coords) {
		index=pIndex.indexOf(coords);
		return elementFloat(index);
	}

	@Override
	public int elementInt(int index) {
		return (int)elementFloat(index);
	}

	@Override
	public int elementInt(int[] coords) {
		return (int)elementFloat(coords);
	}

	@Override
	public short elementShort(int index) {
		return (short)(elementInt(index) & shortMask);
	}

	@Override
	public short elementShort(int[] coords) {
		return (short)(elementInt(coords) & shortMask);
	}
	
	@Override
	public void putBool(int index, boolean value) {
		final float b= (value)? 1:0;
		putFloat(index, b);
		
	}

	@Override
	public void putByte(int index, byte value) {
		putFloat(index, value);
		
	}

	@Override
	public void putChar(int index, char value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putDouble(int index, double value) {
		putFloat(index, (float)value);
		
	}

	@Override
	public void putFloat(int index, float value) {
		x=index/pageSize;
		y=index  - pageSize*x;//index% pageSize;
		//y=index% pageSize;
		fpixels[x][y]=value;
		
	}

	@Override
	public void putInt(int index, int value) {
		putFloat(index, value);
		
	}

	@Override
	public void putShort(int index, short value) {
		putFloat(index, value);
		
	}
	
	@Override
	public void putE(int index, Float value) {
		putFloat(index,  value);
		
	}
	
	@Override
	public void put(Pair<int[],Float> pair) {
		putV(pair.first, pair.second);
	}
	
	@Override
	public void putV(int[] coords, Float value) {
		index=pIndex.indexOf(coords);
		putFloat(index, value);
	}


	@Override
	public int[] size() {
		final int a=fpixels.length;
		final int b=fpixels[0].length;
		return new int[]{a,b};
	}


	@Override
	public void putInt(int[] coords, int value) {
		index=pIndex.indexOf(coords);
		putFloat(index, value);
		
	}


	@Override
	public void putDouble(int[] coords, double value) {
		index=pIndex.indexOf(coords);
		putFloat(index, (float)value);
		
	}


	@Override
	public void putBool(int[] coords, boolean value) {
		final float b= (value)? 1:0;
		putFloat(index, b);
		
	}

}
