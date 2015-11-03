package ijaux.hypergeom.morphology;

import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.VectorCube;

public interface Structuring {
	public final static int FREE=-1;
	public final static int CIRCLE=0;
	public final static int DIAMOND=1;
	public final static int LINE=2;
	public final static int VLINE=3;
	public final static int HLINE=4;
	public final static int CLINE=5;
	public final static int HPOINTS=6;
	public final static int VPOINTS=5;
	public final static int SQARE=7;
	public final static int RING=8;
	public final static int ZLINE=10;
	public final static int ZPOINTS=11;

	public final static int MAXSIZE=151;

	int[] getDimensions();

	PixelCube<?,?> getMask();
	VectorCube<?> getVect();

}
