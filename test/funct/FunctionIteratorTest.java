package test.funct;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.Constants;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.iter.array.ArrayIterator;
import ijaux.iter.array.ByteForwardIterator;
import ijaux.iter.seq.RasterForwardIterator;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.funct.*;
import ijaux.funct.iter.ByteFunctIterator;
import ijaux.funct.iter.RasterFunctIterator;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.index.BaseIndex;

public class FunctionIteratorTest implements Constants {
	static PixLib plib=new PixLib();
	/**
	 * @param args
	 */
	
	public FunctionIteratorTest() {

		int[] dims={255, 10000};
		new ImageJ();
		
		testByte(dims);
	}

	private void testFloat(int[]dims) {
		final int size=Util.cumprod(dims);
		float[] float_pixels=Util.rampFloat(size, 255);
		PixelCube<Float,BaseIndex> pci=new PixelCube<Float,BaseIndex>(dims,float_pixels);
		

		final SimpleFunction<Float> invert=new SimpleFunction<Float>() {


			@Override
			public Float opInt(int a, int elem) {
				return  Float.valueOf(a- elem );
			}


			@Override
			public Float opFloat(float a, float elem) {
				return (a- elem);
			}


			@Override
			public <A, B> Float op(A a, B b) {
				final Number i1=(Number)a;
				final Number i2=(Number)b;
				return  (i1.floatValue() - i2.floatValue());
			}


			@Override
			public Float opDouble(double a, double elem) {			
				return   (float)( a- elem) ;
			}


			@Override
			public Float opBool(boolean a, boolean b) {
				byte u=0;
				return (a & b)?u: (float)(1-u);
			}

		}; // end class
		
	}
	private void testByte(int[] dims) {
		final int size=Util.cumprod(dims);
		byte[] byte_pixels=Util.rampByte(size, 255);

		PixelCube<Byte,BaseIndex> pci=new PixelCube<Byte,BaseIndex>(dims,byte_pixels);
	

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

		invertRasterIter(pci);

		invertByteIter(pci);

		invertRasterFunctIter(byte_pixels, pci, invert);

		invertByteFunctIter(pci, invert);
	}
	
	public static void main(String[] args) {
		new FunctionIteratorTest();
	}

	private  void invertRasterFunctIter(byte[] byte_pixels,
			PixelCube<Byte, BaseIndex> pci, final SimpleFunction<Byte> invert) {
		pci.setIterationPattern(IP_SINGLE+IP_FWD);
		RasterFunctIterator<Byte>  iter=  (RasterFunctIterator< Byte>) pci.functIterator( invert);

		long time=-System.currentTimeMillis();
		while (iter.hasNext())
			iter.nextf(255);
		time+=System.currentTimeMillis();
		System.out.println("exec time (Raster Fnct iter): " +time);
	
			
		ImagePlus imp1 = null;
		try {
			imp1 = plib.imageFrom("test",pci);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//imp1.show();
	}

	private  void invertByteFunctIter(PixelCube<Byte, BaseIndex> pci, final SimpleFunction<Byte> invert) {
			pci.setIterationPattern(IP_SINGLE+IP_PRIM);
			ByteFunctIterator iter = (ByteFunctIterator)pci.functIterator( invert);

			long time=-System.currentTimeMillis();
			while (iter.hasNext())
				iter.nextf(255);
			time+=System.currentTimeMillis();
			System.out.println("exec time (Byte Fnct iter): " +time);

			ImagePlus imp1 = null;
			try {
				imp1 = plib.imageFrom("test",pci);
			} catch (UnsupportedTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//imp1.show();
		
	}
	
	private  void invertRasterIter(PixelCube<Byte, BaseIndex> pci) {
		
		
		pci.setIterationPattern(IP_FWD + IP_SINGLE);
		final RasterForwardIterator<Byte> iterIn = (RasterForwardIterator<Byte>) pci.iterator();
		long time=-System.currentTimeMillis();
		while (iterIn.hasNext()) {
			final int value = iterIn.next() & 0xff;
			final int result = 255 - value;
			iterIn.dec();
			iterIn.put((byte) result);
		}
		time+=System.currentTimeMillis();
		System.out.println("exec time (Raster iter): " +time);
		
		ImagePlus imp2 = null;
		try {
			imp2 = plib.imageFrom("task 2",pci);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//imp2.show();
	}
	
	private  void invertByteIter(PixelCube<Byte, BaseIndex> pci) {
		
		pci.setIterationPattern(IP_SINGLE+IP_FWD + IP_PRIM);
		final ByteForwardIterator iterIn = (ByteForwardIterator) pci.iterator();
		long time=-System.currentTimeMillis();
		while (iterIn.hasNext()) {
			final int value = iterIn.nextInt();
			final int result = 255 - value;
			iterIn.dec();
			iterIn.put((byte) result);
		}
		time+=System.currentTimeMillis();
		System.out.println("exec time (Array iter): " +time);
		
		ImagePlus imp2 = null;
		try {
			imp2 = plib.imageFrom("task 3",pci);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//imp2.show();
	}

}
