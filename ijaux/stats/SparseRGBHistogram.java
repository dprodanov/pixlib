package ijaux.stats;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ijaux.compat.RGB;
import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;

//TODO implementation
public class SparseRGBHistogram extends HistogramMap<int[], Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8782188195551774089L;
	protected Pair<int[][],int[]> histarray;


	public void addToMap(int[] pixels) {
		if (!typeset) setType(pixels);
		final int size=Array.getLength(pixels);

		for (int i=0; i<size; i++) {

			int[] pixval=new int[3];
			if (type==int.class) {
				pixval=RGB.splitRGB(Array.getInt(pixels, i));
			} else {
				throw new IllegalArgumentException("unsupported type "+type.toString());
			}

			int cnt=1;
			
			if (this.containsKey(pixval)) {	
				cnt=this.get(pixval);
				cnt++;
				//System.out.println("i="+i +" pix " +pixels[i] +" cnt " +cnt);
			}  
			
			this.put(pixval, cnt);	
		}

	}
	

	public void removeFromMap(int[] pixels) {
		if (!typeset) setType(pixels);
		final int size=Array.getLength(pixels);

		for (int i=0; i<size; i++) {

			int[] pixval=new int[3];
			if (type==int.class) {
				pixval=RGB.splitRGB(Array.getInt(pixels, i));
			} else {
				throw new IllegalArgumentException("unsupported type "+type.toString());
			}

			if (this.containsKey(pixval)) {
				int cnt=this.get(pixval);
				if (cnt>1) {
					cnt--;
					this.put(pixval, cnt);
					//System.out.println("i="+i +" pix " +pixels[i] +" cnt " +cnt);
				} else{
					Object retval=Array.get(pixels, i);
					this.remove(retval);
				}
			}
		}
 
		
	}

	@Override
	public Pair<?, ?> getResampled(int nbins) {
		if (!iscalc)
			updateArrays();
		return resample(histarray);
	}
	
	Pair<int[],int[][]> resample(Pair<int[][],int[]>hist) {
		int[][] valuearray=hist.first;
		int[] histogram=hist.second;
	
 
		int[] binarray =ramp(0,  1, 255);
		int[][] rh=new int[256][3];
		if (debug) System.out.println("binarray: " +binarray.length);
		if (debug) System.out.println("histarray: " +rh.length);
		
		for (int i=0; i<valuearray.length; i++) {
			final int rbin=valuearray[i][0];
			final int gbin=valuearray[i][1];
			final int bbin=valuearray[i][2];
			if (rbin>0) rh[rbin][0]+=histogram[i];
			if (gbin>0) rh[gbin][1]+=histogram[i];
			if (bbin>0) rh[bbin][2]+=histogram[i];
			//cnts+=rh[bin];
		}
		
		//System.out.print(cnts +" ");
		//int hs=rh[nbins-1];
		//rh[nbins-1]=0;
		//rh[nbins-1]+=hs;
	
		return new Pair<int[],int[][]>(binarray,rh);
		 
	}

	@Override
	public void updateStats() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateArrays() {
		final int size=size();
		int[] histogram=new int[size];
		int[][] binarray=new int[size][3];
		//int[] binarray2=new int[size];
		Iterator<Entry<int[], Integer>> keyValue = this.entrySet().iterator();
		for (int i = 0; i < size; i++){
			final Map.Entry<int[], Integer> entry = keyValue.next();
			binarray[i] = entry.getKey();
			//final int r= binarray[i][0];
			//final int g= binarray[i][1];
			//final int b= binarray[i][2];
	
			/*
			if (r!=0) {
				histogram[i][0] = entry.getValue();
				//binarray2[i]=r;
			}
			if (g!=0) {
				histogram[i][1] = entry.getValue();
				//binarray2[i]=g;
			}
			if (b!=0) {
				histogram[i][2] = entry.getValue();
				//binarray2[i]=b;
			}
			*/
			//System.out.println("i="+1+" v=" +valuearray[i] +" h="+histogram[i]);
		}
		histarray=new Pair<int[][],int[]>(binarray,histogram);
		iscalc=true;	
		
	}

	@Override
	public Pair<int[][],int[]> getSparse() {
		return histarray;
	}

	// TODO implement
	@Override
	public Pair<?, ?> getCumulant() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T> void addToMap(T pixels) throws UnsupportedTypeException {
		addToMap((int[]) pixels);		
	}


	@Override
	public <T> void removeFromMap(T pixels) throws UnsupportedTypeException {
		removeFromMap((int[]) pixels);		
	}



}
