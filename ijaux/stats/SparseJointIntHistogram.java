package ijaux.stats;

import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;

/*
 * joint histogram of 2 arrays of int
 */
public class SparseJointIntHistogram extends HistogramMap<Pair<Integer,Integer>, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5597519236124984487L;
	
	protected Pair<int[][],int[]> histarray;
	
	@Override
	public void updateStats() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pair<?, ?> getResampled(int nbins) {
		if (!iscalc)
			updateArrays();
		return resample(histarray, nbins);
	}

	private Pair<?, ?> resample(Pair<int[][], int[]> histarray2, int nbins) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<?, ?> getSparse() {
		return histarray;
	}

	@Override
	public Pair<?, ?> getCumulant() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addToMap(Object pixels) {
		// TODO Auto-generated method stub
		
	}
	
	public void addToMap(Object pixels, Object pixels2) throws UnsupportedTypeException {
		if (pixels.getClass()!=pixels2.getClass())
			throw new IllegalArgumentException("type mismatch");
		
		if (!typeset) setType(pixels);
		
		if (type==int.class || type==float.class || type==double.class) {
			throw new IllegalArgumentException("unsupported type "+type.toString());
		}

		Access<?> access=Access.rawAccess(pixels, null);
		Access<?> access2=Access.rawAccess(pixels2, null);
		if (access.size()[0]!=access2.size()[0])
			throw new IllegalArgumentException("size mismatch");
		
		for (int i=0; i<access.size()[0]; i++) {

			final int pixval=access.elementInt(i);
			final int pixval2=access2.elementInt(i);
			int cnt=1;
			Pair<Integer, Integer> p=Pair.of(pixval, pixval2);
			if (this.containsKey(p)) {	
				cnt=this.get(p);
				cnt++;
				//System.out.println("i="+i +" pix " +p +" cnt " +cnt);
			}  
			this.put(p, cnt);	
		}
	}

	@Override
	public void removeFromMap(Object pixels) {
		// TODO Auto-generated method stub
		
	}
	

	public void removeFromMap(Object pixels, Object pixels2) throws UnsupportedTypeException {
		if (pixels.getClass()!=pixels2.getClass())
			throw new IllegalArgumentException("type mismatch");
		
		if (!typeset) setType(pixels);
		
		if (type==int.class || type==float.class || type==double.class) {
			throw new IllegalArgumentException("unsupported type "+type.toString());
		}

		Access<?> access=Access.rawAccess(pixels, null);
		Access<?> access2=Access.rawAccess(pixels2, null);
		if (access.size()[0]!=access2.size()[0])
			throw new IllegalArgumentException("size mismatch");
		
		for (int i=0; i<access.size()[0]; i++) {

			final int pixval=access.elementInt(i);
			final int pixval2=access2.elementInt(i);
	
			Pair<Integer, Integer> p=Pair.of(pixval, pixval2);
			
			if (this.containsKey(p)) {
				int cnt=this.get(pixval);
				if (cnt>1) {
					cnt--;
					this.put(p, cnt);
					//System.out.println("i="+i +" pix " +pixels[i] +" cnt " +cnt);
				} else{
					this.remove(p);
				}
			}
		}
		
	}

	@Override
	public void updateArrays() {
		// TODO Auto-generated method stub
		
	}

}
