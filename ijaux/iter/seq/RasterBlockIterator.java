package ijaux.iter.seq;


import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.datatype.access.BlockAccess;
import ijaux.iter.RandomIterator;

import java.util.*;

public class RasterBlockIterator<E extends Number> extends RasterIterator<Access<E>> 
implements RandomIterator<Access<E>> {

		 
		private int blockSize=16;
 
		
		
		public RasterBlockIterator(int blocksize) {
			blockSize=blocksize;
		}
		
		public RasterBlockIterator(int blocksize, Object cpixels) {
			blockSize=blocksize;
			super.setPixels(cpixels);
			
		}
		
		public void inc() { i+=blockSize; }
		
		public void dec() { i-=blockSize; }
		
		public int getBlockSize() {
			return blockSize;
		}

		public void setBlockSize(int blockSize) {
			this.blockSize = blockSize;
		}

		public  Access<E> first() {
			i=0;
			return get(i);

		}

		 
		protected Access<E> get (int z) {
			Access<E> aux=null;
			try {
				aux = Access.rawAccess(getArray(i,blockSize), null);
			} catch (UnsupportedTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return aux;//getArray(i,blockSize);
		}
		 
		
		public Access<E> last() {
			i=size;
			return get(i-blockSize);
		}

		@Override
		public Access<E> next() {
			//System.out.println(i);
			if  (i-blockSize<size) { 
				return get(i);
				
			} else {
				throw new NoSuchElementException();
			}
		}
		

		public Access<E> previous() {
			//System.out.println(i);
			if (i>=0) {
				i-=blockSize;
				final Access<E> ret= get(i);
				i-=blockSize;
				return ret;
				
			} else {
				throw new NoSuchElementException();
			}
			
		}
		
		
		public void reset(){
			if (i>size+blockSize-1)
				set(i-blockSize);
				
			if (i<0)
				set(0);
		}

		@Override
		public boolean hasNext() {
	 		return (i<size);
		}

		public boolean hasPrevious() {
			return (i>0);
		}

	}

