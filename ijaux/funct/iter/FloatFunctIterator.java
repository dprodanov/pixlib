package ijaux.funct.iter;

import ijaux.funct.SimpleFunction;
import ijaux.iter.array.FloatForwardIterator;

public class FloatFunctIterator extends FloatForwardIterator   implements
	FunctionIterator<Float> 	{
		
		private float[] fpixels;

		public FloatFunctIterator(Object cpixels) {
			super(cpixels);
			fpixels=(float[]) pixels; 
		}
		
		public FloatFunctIterator(Object pixels, SimpleFunction<Float> f) {
			this(pixels);
			setFunct(f);
		}
		
		
		@Override
		public <N> Float nextf(N a) {
			final float elem= fpixels[i];
			final Float result=funct.op(elem, a);
			fpixels[i++]=result;		
			return result;	
		}

		@Override
		public Float nextf(byte a) {
			return nextf(a);
		}

		@Override
		public Float nextf(short a) {
			return nextf(a);
		}

		@Override
		public Float nextf(int a) {
			final float elem= fpixels[i];
			final Float result=funct.opFloat(elem, a);
			fpixels[i++]=result;
			return result;	 
		}

		@Override
		public Float nextf(float a) {
			final float elem= fpixels[i];
			final Float result=funct.opFloat(elem, a);
			fpixels[i++]=result;		
			return result;	 
		}

		@Override
		public Float nextf(double a) {
			final double elem= fpixels[i];
			final Float result=funct.opDouble(elem, a);
			fpixels[i++]=result;		
			return result;	 
		}

		@Override
		public Float nextf(boolean a) {
			final boolean elem= nextBool();
			dec();
			final Float result=funct.opBool(elem, a);
			fpixels[i++]=result;
			return result;
		}

		@Override
		public Float nextf(char a) {
			return nextf((int)a);
		}
}
