package ijaux.stats;

import java.lang.reflect.Array;
import java.util.Set;

import ij.*;
import ijaux.Constants;
import ijaux.Util;
import ijaux.datatype.ElementOutput;
import ijaux.datatype.Pair;
import ijaux.funct.AbstractElementFunction;
import ijaux.funct.AbstractIterableFunction;
import ijaux.funct.ElementFunction;
import ijaux.funct.IterableFunction;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.Region;
import ijaux.hypergeom.morphology.StructureElement;
import ijaux.iter.seq.RasterForwardIterator;

/**
 *
 * @author  dprodanov
 */
public class RegionHistogram<E extends Number> implements Constants {
    //public static final int MAX=255,MIN=0;

	protected HistogramMap<E,Integer> map;
 
    public Region<E> region;
	
    public int rank=0;
    public boolean debug=false;
    
    /** Creates a new instance of RegionalHistogram */
    public RegionHistogram(PixelCube<E,?> pc, StructureElement<E> se, int iterationPattern) {
    	region=new Region<E>(pc, se.getVect());
    	region.setIterationPattern(iterationPattern);
    	init(region);
    	
    }
    
    /** Creates a new instance of RegionalHistogram */
    public RegionHistogram(PixelCube<E,?> pc, StructureElement<E> se) {
    	this(pc,se, IP_SINGLE+IP_FWD);
    }
    
    
    public void debug() {
    	region.debug=debug;
    	map.debug=debug;
    }
    
    public final ElementFunction<E,E> histAdd
	=new AbstractElementFunction<E,E> () {

		@Override
		public E getOutput() {
			/*
			map.updateArrays();
			Object obj=Array.get(map.getSparse().first, rank);
			if (type==byte.class) {
				E aux=(E) obj;
				return (byte)(aux.intValue() & 0xFF);
			}
			*/
			return null;
		}

		@Override
		public void transform(E a, E b) {
			if (a!=null) {
				//if (Util.isIntType(type)) {
					//final Integer x=(a.intValue() & b.intValue());
					//addPixel((E)x);
					addPixel(a);
				//}
				//System.out.print(a+" ");
			}
			
		}

 

		@Override
		public boolean getOutputBoolean() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public byte getOutputByte() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double getOutputDouble() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public float getOutputFloat() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getOutputInt() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public short getOutputShort() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void transformBool(boolean a, boolean b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void transformByte(byte a, byte b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void transformDouble(double a, double b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void transformFloat(float a, float b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void transformInt(int a, int b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void transformShort(short a, short b) {
			// TODO Auto-generated method stub
			
		}

	
		
	};
	
	public final ElementFunction<E, E> histRemove
	=new AbstractElementFunction<E,E> () {

		@Override
		public E getOutput() {
			return null;
		}

		@Override
		public void transform(E a, E b) {
			if (a!=null)
				removePixel(a);
			
		}

	 

		@Override
		public boolean getOutputBoolean() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public byte getOutputByte() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double getOutputDouble() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public float getOutputFloat() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getOutputInt() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public short getOutputShort() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void transformBool(boolean a, boolean b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void transformByte(byte a, byte b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void transformDouble(double a, double b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void transformFloat(float a, float b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void transformInt(int a, int b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void transformShort(short a, short b) {
			// TODO Auto-generated method stub
			
		}

	 
		
	};
	
    /**
	 * @param reg
	 */
	@SuppressWarnings("unchecked")
	private void init(Region<E> reg) {
		Class<?> c=reg.getType();
		System.out.println(c);
		if (c==int.class || c==short.class || c== byte.class) {
			System.out.println("Integer type histogram map");
			map=(HistogramMap<E,Integer>) new SparseIntHistogram();
			return;
		}  
		if (c==double.class || c==float.class) {
			System.out.println("Float type histogram map");
			map=(HistogramMap<E,Integer>) new SparseFloatHistogram();
			return;
		}  
		throw new IllegalArgumentException("unsupported type "+c);		 
		
	}
    

	
	private void addPixel(E pixval) {
		if (map.containsKey(pixval)) {		
			int cnt=map.get(pixval).intValue();
			cnt++;
			map.put(pixval, cnt);
			//System.out.println("i="+i +" pix " +pixels[i] +" cnt " +cnt);
		} else {
			map.put(pixval, 1);
		}
	}
	
	private void removePixel(E pixval) {
		if (map.containsKey(pixval)) {
			int cnt=map.get(pixval);
			if (cnt>1) {
				cnt--;
				map.put(pixval, cnt);
				//System.out.println("i="+i +" pix " +pixels[i] +" cnt " +cnt);
			} else{
				map.remove(pixval);
			}
		}
	
	}
	
	public String toString() {
		return map.toString();
	}
	
	public void clear() {
		map.clear();
	}
	
	
	public void doHistogram(int index) {
		//region.setElementFnct(histAdd);
		region.elementTransform(histAdd,index);
	}
/*
	public void addToMap(int index,  byte[] pixels, int[][] pg) {
		addMask(index, this.width,  this.height,  pixels,  pg, this.type);
		//updateMinMax();
	}
	
	private void addMask(int index, int width,  int height, byte[] pixels, int[][] pg, int type) {
		int k=0;
		int bin=0;
		final int i=index/width;
		final int j=index%width;

		for (int g=0;g<pg.length;g++){
			final int y=i+pg[g][0];
			final int x=j+pg[g][1];
			try {
				if  ((x>=width) || (y>=height) || (x<0) || (y<0) ) {
					if (type==0) {
						k=0; }
					else {
						k=MAX;
					}
				}
				else {
					k=pixels[x+width*y]&0xFF;
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {

				k=x+width*y;
				IJ.log("AIOB x: "+x+" y: "+y+" index: "+k);
			}

			if (type==0)   {
				bin=k+pg[g][2]-MAX;
			}
			else {
				bin=k-pg[g][2]+MAX;
			}
			final byte b=(byte)(bin & 0xFF);
			addPixel(b);
		} // end for
		size=histmap.size();
	}
    
  	
	*/
	

	/*
	
	public void removeFromMap(int index,  byte[] pixels, int[][] pg) {
		removeMask(index, this.width,  this.height,  pixels,  pg, this.type);
		//updateMinMax();
	}
	
	private void removeMask(int index, int width,  int height, byte[] pixels, int[][] pg, int type) {
		  
		int k=0;
		int bin=0;
		final int i=index/width;
		final int j=index%width;

		for (int g=0;g<pg.length;g++){
			final int y=i+pg[g][0];
			final int x=j+pg[g][1];
			try {
				if  ((x>=width) || (y>=height) || (x<0) || (y<0) ) {
					if (type==0) {
						k=0; }
					else {
						k=255;
					}
				}
				else {
					k=pixels[x+width*y]&0xFF;
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {

				k=x+width*y;
				IJ.log("AIOB x: "+x+" y: "+y+" index: "+k);
			}

			if (type==0)   {
				bin=k+pg[g][2]-MAX;
			}
			else {
				bin=k-pg[g][2]+MAX;
			}
			final byte b=(byte)(bin & 0xFF);
			removePixel(b);
		} // end for
		size=histmap.size();
		
	}

*/







	 


    
} // end LocalHistogram
