package ijaux.datatype.access;

import java.util.RandomAccess;

public abstract class IndexedAccess<N> implements ElementAccess<int[], N>,
 BlockAccess, RandomAccess {
	
	protected Object pixels;
	
	protected Class<?> type;
	
	protected int index=0;
	
	public static boolean debug=false;
	
	@Override
	public abstract byte[] getByteArray();

	@Override
	public abstract short[] getShortArray();

	@Override
	public abstract float[] getFloatArray();

	@Override
	public abstract double[] getDoubleArray();
	
	@Override
	public abstract int[] getIntArray();

	@Override
	public abstract boolean[] getBoolArray();
 

}
