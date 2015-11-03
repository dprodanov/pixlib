package ijaux.hypergeom.morphology;

import ijaux.hypergeom.HyperCube;
import ijaux.hypergeom.Region;
import ijaux.hypergeom.index.BaseIndex;

 
 

public interface Morphology<E extends HyperCube<?,?>> {
 
		
		public final static int ORIG=0,PLUS=1,MINUS=-1;
		
	    /** Performs erosion 
	     * @param  reg the Region
	     */
	    public E  erode(E reg);
	    
	    /** Performs dilation 
	     *  @param  reg the Region
	     */
	    public E  dilate(E reg);
	    
	    /** Performs erosion followed by  dilation
	     *  with arbitrary structural element SE
	     * @param reg the Region
	     */
	    public E  open(E reg);
	    
	    /** Performs dilation followed by erosion 
	     *  with arbitrary structural element se
	     * @param  reg the Region
	     */
	    public E  close(E reg);

 
}
