package ijaux.datatype.access;

import ijaux.datatype.Pair;
import ijaux.hypergeom.index.Indexing;

public class PagedDoubleAccess extends PagedAccess<Double> {
	private double[][] dpixels;
	
	public PagedDoubleAccess(Object cpixels, Indexing<int[]> aind) {
		super(cpixels,aind);
		dpixels=(double[][]) pixels; 
		pageSize=dpixels[0].length;
		
	}
	
	@Override
	public Double element(int index) {
		return elementDouble(index);
	}

	@Override
	public Double element(int[] coords) {
		return elementDouble(coords);
	}
	
	@Override
	public boolean elementBool(int index) {
		return (elementDouble(index)>0);
	}

	@Override
	public boolean elementBool(int[] coords) {
		return (elementDouble(coords)>0);
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
		x=index/pageSize;
		//y=index  - pageSize*x;//index% pageSize;
		y=index% pageSize;
		return dpixels[x][y];
	}

	@Override
	public double elementDouble(int[] coords) {
		index=pIndex.indexOf(coords);
		return elementDouble(index);
	}

	@Override
	public float elementFloat(int index) {
		// TODO Auto-generated method stub
		return (float)elementDouble(index);
	}

	@Override
	public float elementFloat(int[] coords) {
		return (float)elementDouble(coords);
	}

	@Override
	public int elementInt(int index) {
		return (int)elementDouble(index);
	}

	@Override
	public int elementInt(int[] coords) {
		return (int)elementDouble(coords);
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
		final double b= (value)? 1:0;
		putDouble(index, b);
		
	}

	@Override
	public void putByte(int index, byte value) {
		putDouble(index, value);
		
	}

	@Override
	public void putChar(int index, char value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putDouble(int index, double value) {
		x=index/pageSize;
		y=index  - pageSize*x;//index% pageSize;
		//y=index% pageSize;
		dpixels[x][y]=value;
		
	}

	@Override
	public void putFloat(int index, float value) {
		putDouble(index, value);
		
	}

	@Override
	public void putInt(int index, int value) {
		putDouble(index, value);
		
	}

	@Override
	public void putShort(int index, short value) {
		putDouble(index, value);
		
	}
	
	@Override
	public void putE(int index, Double value) {
		putDouble(index,  value);
		
	}
	
	@Override
	public void put(Pair<int[],Double> pair) {
		putV(pair.first, pair.second);
	}
	
	@Override
	public void putV(int[] coords, Double value) {
		index=pIndex.indexOf(coords);
		putDouble(index, value);
	}
	
	@Override
	public int[] size() {
		final int a=dpixels.length;
		final int b=dpixels[0].length;
		return new int[]{a,b};
	}

	@Override
	public void putInt(int[] coords, int value) {
		index=pIndex.indexOf(coords);
		putDouble(index,value);
		
	}

	@Override
	public void putDouble(int[] coords, double value) {
		index=pIndex.indexOf(coords);
		putDouble(index,value);
		
	}

	@Override
	public void putBool(int[] coords, boolean value) {
		index=pIndex.indexOf(coords);
		final double b= (value)? 1:0;
		putDouble(index, b);
	}
}
