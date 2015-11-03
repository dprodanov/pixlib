package ijaux.hypergeom;

import ijaux.Util;
import ijaux.datatype.Pair;
import ijaux.datatype.access.Access;
import ijaux.funct.ElementFunction;
import ijaux.funct.IndexedTransformer;
import ijaux.hypergeom.index.Indexing;
import ijaux.iter.compl.ComplexVectorCursor;
import ijaux.iter.dir.VectorCursor;
import ijaux.parallel.ThreadedProcessor;

public class ZonalProcessor {

	private Indexing<int[]> pIndex;
	public boolean debug=false;
	private Access<?> access;

	public ZonalProcessor(Access<?> acc) {
		access=  acc;
		pIndex=access.getIndexing();
	}


	//mode: min, max, sort; median, window

	/*
	 * transforms a pixel in the local neighborhood specified by ind using a constant 
	 *  boundary condition.
	 */

	@SuppressWarnings("unchecked")
	public <E extends Number> E elementTransform(VectorCube<E> ve, ElementFunction<?, E> elementFnct, int idx) {

		for (Pair<int[], E> p:ve) {	
			pIndex.setIndexAndUpdate(idx);
			//System.out.println(idx+" ["+pIndex +"] "); 
			pIndex.translate(p.first);
			
			//System.out.print(" ["+pIndex +"],");

			try {		
				if (pIndex.isValid()) {
					//pIndex.warp();
					int u=pIndex.index();
					final E elem=(E) access.element(u); 
					//System.out.print(elem+" "+ u+" ");
					///System.out.print(" g="+elem +" ");
					elementFnct.transform(elem, p.second);
				}
				else{
					// outside strategy		 
					if (debug) 
						System.out.println("\n outside ["+pIndex +"] " + ve.limboval); 
					elementFnct.transform(ve.limboval, p.second);
					//return ve.limboval;
				}	
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Exception: "+ e+": ["+pIndex+"]");
				System.out.println(ve.limboval +" "+ p.second);
				//elementFnct.transform(ve.limboval, p.second);
				//e.printStackTrace();
			}		
		}

		return (E)elementFnct.getOutput();

	}
	
	/*
	 * transforms a pixel in the local neighborhood specified by ind using a constant 
	 *  boundary condition.
	 */
	@SuppressWarnings("unchecked")
	public <E extends Number> E elementTransform(VectorCube<E> ve, ElementFunction<?, E> elementFnct, int limboval, int idx) {

		for (Pair<int[], E> p:ve) {	
			pIndex.setIndexAndUpdate(idx);
			//System.out.println(idx+" ["+pIndex +"] "); 
			pIndex.translate(p.first);
			//pIndex.warp();
			int u=pIndex.index();
			//System.out.print(" ["+pIndex +"],");

			try {		
				if (pIndex.isValid()) {
					final E elem=(E) access.element(u); 
					///System.out.print(" g="+elem +" ");
					elementFnct.transform(elem, p.second);
				}
				else{
					// outside strategy		 
					if (debug) System.out.println("\n outside ["+pIndex +"] " + ve.limboval); 
					elementFnct.transformInt(limboval, p.second.intValue());
				}	
			} catch (Exception e) {
				System.out.println(e+": "+pIndex);
				System.out.println(limboval +" "+ p.second);
				elementFnct.transform(ve.limboval, p.second);
				//e.printStackTrace();
			}		
		}

		return (E)elementFnct.getOutput();

	}

	/*
	 * transforms a pixel in the local neighborhood specified by ind using a constant 
	 *  boundary condition.
	 */
	@SuppressWarnings("unchecked")
	public <E extends Number> E elementTransform(VectorCube<E> ve, ElementFunction<?, E> elementFnct, double limboval, int idx) {

		for (Pair<int[], E> p:ve) {	
			pIndex.setIndexAndUpdate(idx);
			//System.out.println(idx+" ["+pIndex +"] "); 
			pIndex.translate(p.first);
			try {		
				if (pIndex.isValid()) {
					//pIndex.warp();
					int u=pIndex.index();
					final E elem=(E) access.element(u); 
					///System.out.print(" g="+elem +" ");
					elementFnct.transform(elem, p.second);
				}
				else{
					// outside strategy		 
					if (debug) System.out.println("\n outside ["+pIndex +"] " + ve.limboval); 
					elementFnct.transformDouble(limboval, p.second.doubleValue());
				}	
			} catch (Exception e) {
				System.out.println(e+": "+pIndex);
				System.out.println(limboval +" "+ p.second);
				elementFnct.transform(ve.limboval, p.second);
				//e.printStackTrace();
			}		
		}

		return (E)elementFnct.getOutput();

	}


	@SuppressWarnings("unchecked")
	public <E extends Number>E elementTransform(VectorCube<E> ve, ElementFunction<?, E> elementFnct, int idx, int[] coords) {

		final Pair<int[], E> q=ve.element(coords);				
		pIndex.setIndexAndUpdate(idx);
		int u=pIndex.translate(q.first);
		final E w=(E) access.element(u);

		//System.out.print(w+", ");
		for (Pair<int[], E> p:ve) {	
			pIndex.setIndex(idx);
			//System.out.println(idx+" ["+pIndex +"] "); 
			pIndex.translate(p.first);
			//pIndex.warp();
			u=pIndex.index();

			//System.out.print(" ["+pIndex +"],");
			//pIndex.getCoordinates();
			try {		
				//if (pIndex.contains(pIndex.getCoordinates()) && pIndex.isValid()) {
				if (pIndex.isValid()) {
					final E elem=(E) access.element(u); 
					///System.out.print(" g="+elem +" ");
					elementFnct.transform(elem, w);
				}
				else{
					// outside strategy		 
					if (debug) System.out.println("\n outside ["+pIndex +"] " + ve.limboval); 
					//elementFnct.transform(ve.limboval, w);
					return ve.limboval;
				}	
			} catch (Exception e) {
				System.out.println(e+": "+pIndex);
				System.out.println(ve.limboval +" "+ w);
				elementFnct.transform(ve.limboval, w);
			}

		}
		//ve.addElement(q);
		return (E)elementFnct.getOutput();

	}
	/*
	 * transforms a pixel in the local neighborhood specified by ind and respects 
	 * pre-specified boundary conditions.
	 */
	@SuppressWarnings("unchecked")
	public <E extends Number> E elementTransformCond(VectorCube<E> ve, ElementFunction<?, E> elementFnct, BoundaryCondition<int[]> bc, int idx) {

		for (Pair<int[], E> p:ve) {	
			pIndex.setIndexAndUpdate(idx);
			pIndex.translate(p.first);
			
			try {		
				if (pIndex.isValid()) {
					//pIndex.warp();
					int u=pIndex.index();
					//int u=pIndex.indexOf(pIndex.getCoordinates());
					final E elem=(E) access.element(u); 
					elementFnct.transform(elem, p.second);
				}
				else{
					// outside strategy		 
					if (debug) 
						System.out.println("\n outside ["+pIndex +"] "); 
					int[] coords=bc.getCoordsAt(pIndex.getCoordinates());
					E elem=(E) access.element(coords);
					elementFnct.transform(elem, p.second);
				}	
			} catch (Exception e) {
				if (debug) {
					System.out.println(e+": "+pIndex);
					Util.printIntArray(p.first);
					//System.out.println(ve.limboval +" "+ p.second);
				}
				//elementFnct.transform(ve.limboval, p.second);
				//e.printStackTrace();
			}			
		}

		return (E)elementFnct.getOutput();

	}


}
