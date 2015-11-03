package ijaux.hypergeom.index;


public class InterpolatedIndex implements Indexing<float[]> {

	protected int[] dim={};
	
	protected int index=0;
	
	protected float[] coords={};
	
	protected int maxind=0, minind=0;
	
	protected boolean updated=false;
 
	public static boolean debug=false;
	
	protected boolean isvalid=false;
	
	protected Class<?> type;
	
	@Override
	public float[] getCoordinates() {
		return coords;
	}

	@Override
	public int index() {
		return index;
	}

	@Override
	public int indexOf(float[] x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isValid() {
		return isvalid;
	}

	@Override
	public void reshape(float[] dims) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIndex(int idx) {
		index=idx;
	}

	@Override
	public int translate(float[] x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int translateTo(float[] x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean contains(float[] vect) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean geq(float[] vect, int[] set) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean in(float[] inner, float[] outer, int[] set) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leq(float[] vect, int[] set) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean out(float[] inner, float[] outer, int[] set) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(float[] vect, int[] set) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNDim() {
		return dim.length;
	}

	@Override
	public void setIndexAndUpdate(int idx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getDim() {
		// TODO Auto-generated method stub
		return null;
	}


	public void warp() {
		// TODO Auto-generated method stub
		
	}


	public void inc() {
		// TODO Auto-generated method stub
		
	}


	public void dec() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCoordinates(float[] x) {
		for (int i=0; i<coords.length; i++)
			coords[i]=x[i];
		
	}

}
