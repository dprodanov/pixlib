package ijaux.datatype.access;

import ijaux.datatype.Pair;
import ijaux.hypergeom.*;
import ijaux.hypergeom.index.Indexing;

public class PagedByteAccess extends PagedAccess<Byte> {

	private byte [][] bpixels;
	
	
	public PagedByteAccess(Object cpixels, Indexing<int[]> aind) {
		super(cpixels, aind);
		bpixels=(byte[][]) pixels; 
		pageSize=bpixels[0].length;
		//System.out.println("clength "+bpixels.length);
		//System.out.println("pageSize "+pageSize);
	}
	


	@Override
	public Byte element(int index) {
		return elementByte(index);
	}

	@Override
	public Byte element(int[] coords) {
		return elementByte(coords);
	}

	@Override
	public boolean elementBool(int index) {
		return (elementByte(index)>0);
	}

	@Override
	public boolean elementBool(int[] coords) {
		return (elementByte(coords)>0);
	}

	@Override
	public byte elementByte(int index) {
/*		byte ret=0;
		try {
			x=index/pageSize;
			//y=index  - pageSize*x;//index% pageSize;
			y=index% pageSize;
			ret= bpixels[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println ("i:"+index+" x: "+x+" y: "+y +" " +pageSize);
		}
		return ret;*/
		x=index/pageSize;
		y=index% pageSize;
		return bpixels[x][y];
	}

	@Override
	public byte elementByte(int[] coords) {
		index=pIndex.indexOf(coords);
		return elementByte(index);
	}

	@Override
	public char elementChar(int index) {
		return (char)(elementInt(index)) ;
	}

	@Override
	public char elementChar(int[] coords) {
		return (char)elementInt(coords);
	}

	@Override
	public double elementDouble(int index) {
		return elementByte(index) & mask;
	}

	@Override
	public double elementDouble(int[] coords) {
		return elementByte(coords) & mask;
	}

	@Override
	public float elementFloat(int index) {
		return elementByte(index) & mask;
	}

	@Override
	public float elementFloat(int[] coords) {
		return elementByte(coords) & mask;
	}

	@Override
	public int elementInt(int index) {
		return elementByte(index) & mask;
	}

	@Override
	public int elementInt(int[] coords) {
		return elementByte(coords) & mask;
	}

	@Override
	public short elementShort(int index) {
		return (short)(elementByte(index) & mask);
	}

	@Override
	public short elementShort(int[] coords) {
		return (short)(elementByte(coords) & mask);
	}

	@Override
	public void putBool(int index, boolean value) {
		final int b= (value)? -1:0;
		putByte(index, (byte)b);
	}

	@Override
	public void putByte(int index, byte value) {
		x=index/pageSize;
		y=index  - pageSize*x;//index% pageSize;
		//y=index% pageSize;
		bpixels[x][y]=value;
		
	}

	@Override
	public void putChar(int index, char value) {
		//putByte(index, (byte)(value & mask));
		
	}

	@Override
	public void putDouble(int index, double value) {
		final byte b=(byte) ((int)value & mask);
		putByte(index, b);
		
	}

	@Override
	public void putFloat(int index, float value) {
		final byte b=(byte) ((int)value & mask);
		putByte(index, b);
		
	}

	@Override
	public void putInt(int index, int value) {
		putByte(index,  (byte)(value & mask));
		
	}

	@Override
	public void putShort(int index, short value) {
		putByte(index,  (byte)(value & mask));
		
	}
	
	@Override
	public void putE(int index, Byte value) {
		putByte(index,  value);
		
	}
	
	@Override
	public void put(Pair<int[],Byte> pair) {
		putV(pair.first, pair.second);
	}
	
	@Override
	public void putV(int[] coords, Byte value) {
		index=pIndex.indexOf(coords);
		putByte(index, value);
	}

	@Override
	public int[] size() {
		final int a=bpixels.length;	
		//System.out.println("clength "+bpixels.length);
		//System.out.println("pageSize "+pageSize);
		return new int[]{a,pageSize};
	}




	@Override
	public void putInt(int[] coords, int value) {
		index=pIndex.indexOf(coords);
		putByte(index,  (byte)(value & mask));		
	}



	@Override
	public void putDouble(int[] coords, double value) {
		index=pIndex.indexOf(coords);
		final byte b=(byte) ((int)value & mask);
		putByte(index, b);
	}



	@Override
	public void putBool(int[] coords, boolean value) {
		final int b= (value)? -1:0;
		putByte(index, (byte)b);
		
	}
	
}
