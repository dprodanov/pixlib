package ijaux.stats;

import ijaux.datatype.*;
import ijaux.hypergeom.*;


public class CubeHistogram<E extends Number> extends HyperCubeHistogram {
	
	public CubeHistogram(PixelCube<E,?> pc, int bins) throws UnsupportedTypeException {
		init(pc);
		nBins=bins;
		updateStats();
	}
	
	public CubeHistogram(PixelCube<E,?> pc) throws UnsupportedTypeException {
		init(pc);
		updateStats();
	}
	
	
	/**
	 * @param pc
	 * @throws UnsupportedTypeException 
	 */
	@SuppressWarnings("unchecked")
	public void init(PixelCube<E, ?> pc) throws UnsupportedTypeException {
		final Class<?> c=pc.getType();
		if (c==int.class || c==short.class || c== byte.class) {
			System.out.println("Integer type histogram map");
			map= (HistogramMap<E, Integer>) new SparseIntHistogram(pc.getAccess());
			//map.addToMap(pc.getAccess().getArray());
			return;
		}  
		if (c==double.class || c==float.class) {
			System.out.println("Float type histogram map");
			map=(HistogramMap<E,Integer>) new SparseFloatHistogram(pc.getAccess());
			//map.addToMap(pc.getAccess().getArray());
			return;
		}  
		//throw new IllegalArgumentException("unsupported type "+c.toString());		 
		
	}
	

}
