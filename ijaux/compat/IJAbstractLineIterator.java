package ijaux.compat;

import ijaux.datatype.Typing;

import java.util.Iterator;

/*  @author Dimiter Prodanov
 * 	Prototype class for handling pixel data in blocks
 *  <E> can be either a primitive container array as supported by ImageJ 
 */

public abstract class IJAbstractLineIterator<E> implements Iterator<E>, Typing{
	public final static int Ox=0, Oy=1, Oz=2;
	
	protected int dir;
	protected int size;
	protected int cnt;
	protected int btype;
	
	protected int blength;
	
	protected int[] intbuffer;
	protected byte[] bytebuffer;
	protected short[] shortbuffer;
	protected float[] floatbuffer;

	protected Class<?> returnType;
	
	
	protected void initbuffer(int dir) {
		switch (btype) {
			case 32: {
				floatbuffer=new float[blength];
				break;
				}
			case 16:{
				shortbuffer=new short[blength];
				break;
				}
			case 8: {
				bytebuffer=new byte[blength];
				break;
				}
			case 24:
				intbuffer=new int[blength];
		}
	}
	
	@SuppressWarnings("unchecked")
	protected E xget() {
		if (btype==32)
			return (E) floatbuffer;
		if (btype==16)
			return (E) shortbuffer;
		if (btype==8)
			return (E) bytebuffer;
		if (btype==24)
			return (E) intbuffer;
		return null;
	}
	
	public abstract void putLine(E line, int k, int dir);
	
	@Override
	public boolean hasNext() {
		return cnt<size;
	}
	
	@Override
	public void remove() {
		// TODO Auto-generated method stub			
	}
	
	public int size() {
		return size;
	}
	
	public void fwd() {
		cnt++;
	}
	
	public void bck() {
		cnt--;
	}
	
	public void reset() {
		cnt=0;
	}
	
	public Class<?> getType() { return returnType; }

	public boolean eq(Class<?> c) { return (c==returnType); }

	/**
	 * @param pix
	 */
	protected void setType(Object pix) {
		if (pix instanceof byte[])
			btype=8;
		if (pix instanceof short[])
			btype=16;
		if (pix instanceof int[])
			btype=24;
		if (pix instanceof float[])
			btype=32;
		returnType=pix.getClass();
	}

	public abstract byte[] getLineByte(int k, int dir);

	public abstract float[] getLineFloat(int k, int dir);

	public abstract short[] getLineShort(int k, int dir) ;

	public abstract int[] getLineInt(int k, int dir);

	public abstract void putLineShort(short[] line, int k, int dir);

	public abstract void putLineFloat(float[] line, int k, int dir) ;

	public abstract void putLineByte(byte[] line, int k, int dir) ;

	public abstract void putLineInt(int[] line, int k, int dir) ;
	
}