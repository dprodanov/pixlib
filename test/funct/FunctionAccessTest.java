package test.funct;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.datatype.access.ByteAccess;
import ijaux.datatype.access.FunctionAccess;
import ijaux.funct.SimpleFunction;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.Indexing;


public class FunctionAccessTest {
	
	public FunctionAccessTest() {
		int[] dims={255, 5000};

		final int size=Util.cumprod(dims);
		byte[] byte_pixels=Util.rampByte(size, 255);

		PixelCube<Byte,BaseIndex> pci=new PixelCube<Byte,BaseIndex>(dims,byte_pixels);
		new ImageJ();
		
		final SimpleFunction<Byte> invert=new SimpleFunction<Byte>() {


			@Override
			public Byte opInt(int a, int elem) {
				return (byte) (a- elem & 0xFF);
			}


			@Override
			public Byte opFloat(float a, float elem) {
				return (byte) ((int)a- ((int)elem) & 0xFF);
			}


			@Override
			public <A, B> Byte op(A a, B b) {
				final Number i1=(Number)a;
				final Number i2=(Number)b;
				return (byte) (i1.intValue() - i2.intValue() & 0xFF);
			}


			@Override
			public Byte opDouble(double a, double elem) {
				return (byte) ((int)a- ((int)elem) & 0xFF);
			}


			@Override
			public Byte opBool(boolean a, boolean b) {
				byte u=0;
				return (a & b)?u: (byte)(1-u);
			}

		}; // end class
		
	
		 task1(byte_pixels, invert, pci);
		
		task2( pci);
		
		
	
		

	}

	private void task2(PixelCube<Byte, BaseIndex> pci) {
		final Access<Byte> access=pci.getAccess();
		final int size=access.size()[0];
		
		long time=-System.currentTimeMillis();
		for (int i=0; i<size; i++) {			
			final int ret=255-access.elementInt(i);
			access.putInt(i, ret);
		}
		time+=System.currentTimeMillis();
		System.out.println("exec time (Byte Access): " +time);
		try {
			ImagePlus imp1  = plib.imageFrom("test BA",pci);
			imp1.show();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void task1(byte[] byte_pixels,
			final SimpleFunction<Byte> invert, PixelCube<Byte, BaseIndex> pci) {
	
		final ByteFunctionAccess baf=new ByteFunctionAccess(byte_pixels, null);
		final int size=baf.size()[0];
		long time=-System.currentTimeMillis();
		for (int i=0; i<size; i++) {			
			final byte b=baf.element(i, 255, invert);
			baf.putByte(i, b);
		}
		time+=System.currentTimeMillis();
		System.out.println("exec time (Byte Fnct Access): " +time);
		
		try {
			ImagePlus imp1  = plib.imageFrom("test BAF",pci);
			imp1.show();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static PixLib plib=new PixLib();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new FunctionAccessTest();
	}
	
	class ByteFunctionAccess extends ByteAccess implements FunctionAccess  {

		public ByteFunctionAccess(Object cpixels, Indexing<int[]> aind) {
			super(cpixels, aind);
		}

		@Override
		public <N> N element(int index, N value, SimpleFunction<N> f) {
			final byte b=elementByte(index);
			N ret=f.op(value, b);
			return ret;
		}
		
		@Override
		public <N> N element(int index, int value, SimpleFunction<N> f) {
			final byte b=elementByte(index);
			N ret=f.opInt(value, b);
			return ret;
		}

		@Override
		public <N> N element(int[] coords, N value, SimpleFunction<N> f) {
			final byte b=elementByte(index);
			N ret=f.op(value, b);
			return ret;
		}

		@Override
		public <N> N element(int index, float value, SimpleFunction<N> f) {
			final float b=elementFloat(index);
			N ret=f.opFloat(value, b);
			return ret;
		}

		@Override
		public <N> N element(int index, double value, SimpleFunction<N> f) {
			final double b=elementDouble(index);
			N ret=f.opDouble(value, b);
			return ret;
		}

		@Override
		public <N> N element(int index, boolean value, SimpleFunction<N> f) {
			final boolean b=elementBool(index);
			N ret=f.opBool(value, b);
			return ret;
		}

		@Override
		public <N> N element(int[] coords, int value, SimpleFunction<N> f) {
			index=pIndex.indexOf(coords);
			return element(index, value, f);
		}

		@Override
		public <N> N element(int[] coords, float value, SimpleFunction<N> f) {
			index=pIndex.indexOf(coords);
			return element(index, value, f);
		}

		@Override
		public <N> N element(int[] coords, double value, SimpleFunction<N> f) {
			index=pIndex.indexOf(coords);
			return element(index, value, f);
		}

		@Override
		public <N> N element(int[] coords, boolean value, SimpleFunction<N> f) {
			index=pIndex.indexOf(coords);
			return element(index, value, f);
		}

		 
		
	}
}
