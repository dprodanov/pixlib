package ijaux.stats;


import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ijaux.Util;
import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;


public class SparseIntHistogram extends HistogramMap<Integer,Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2030535152081916261L;
	
	protected Pair<int[],int[]> histarray;
	
	public SparseIntHistogram (){
		
	}
	
	public SparseIntHistogram(Object pixels) {
		setType(pixels);
		addToMap(pixels);
	}
	
	public SparseIntHistogram(Access<?> access) {	 
		addToMap(access);
	}

	public <N> void addToMap( Access<N> access) {
		final Class<?> ctype=access.getType();
  
		if ( ctype==byte.class 
				|| ctype==short.class 
				|| ctype==int.class )
			addI(access); 
		else 
			throw new IllegalArgumentException("type does not match "+ctype);
		// you can't add int[] or byte[] or short[];
 		
		if (!typeset) type=ctype;				
	}
	
	protected <N> void addI(Access<N> access) {
		int cnt=1;
		for (int i=0; i<access.length(); i++) {
			final int pixval=access.elementInt(i);
			if (containsKey(pixval)) {	
				cnt=get(pixval);
				cnt++;
				//System.out.println("i="+i +" pix " +pixels[i] +" cnt " +cnt);
			}  
			put(pixval, cnt);
		}
	}  
	
	@Override
	public <N> void addToMap(N pixels)  {		
		try {
			Access<N> access=Access.rawAccess(pixels, null);	
			addToMap(access);
			if (!typeset) setType(pixels);
				
		} catch (UnsupportedTypeException e) {
			throw new IllegalArgumentException("type not supported");
		}
		 
	}
	 
	
	public <N> void removeFromMap(Access<N> access) {
		final Class<?> ctype=access.getType();
		if ( ctype!=byte.class || ctype!=short.class)
			throw new IllegalArgumentException("type does not match "+ctype);
		// you can't add int[] or byte[] or short[];
		if (!typeset) type=ctype;
					
		removeI(access);
	}

	protected <N> void removeI(Access<N> access) {
		for (int i=0; i<access.length(); i++) {
			final int pixval=access.elementInt(i);
			if (this.containsKey(pixval)) {
				int cnt=this.get(pixval);
				if (cnt>1) {
					cnt--;
					this.put(pixval, cnt);
					//System.out.println("i="+i +" pix " +pixels[i] +" cnt " +cnt);
				} else{
					this.remove(pixval);
				}
			}
		}
	}
	 

	public <N> void removeFromMap(N pixels)  {		
		try {
			Access<N> access=Access.rawAccess(pixels, null);
			removeFromMap(access);
				
		} catch (UnsupportedTypeException e) {
			throw new IllegalArgumentException("type not supported");
		}
		 
	}
 
 	 

	public Pair<float[],int[]> getResampled(int nbins) {
		if (!iscalc)
			updateArrays();
		if (nbins<size() ) {
			int[] valuearray=histarray.first;		 
			float min=valuearray[0]; 
			float max=valuearray[valuearray.length-1];
			System.out.println("Dense case: "+ nbins + " bins, size "+ size());
			
			return resample(histarray, min, max,nbins);			
		} else { // sparse case			
			int[] valuearray=histarray.first;		 
			float min=valuearray[0]; 
			float max=valuearray[valuearray.length-1];
			nbins=(int)(max-min+1);
			System.out.println("Sparse case: "+ nbins+ " bins, size "+ size());
			
			return resample(histarray, min, max,nbins);
		}
		
	}
	
	public Pair<int[],int[]>  getSparse() {
		return histarray;
	}
	
	public void updateArrays() {
		final int size=size();
		int[] histogram=new int[size];
		int[] binarray=new int[size];
		Iterator<Entry<Integer, Integer>> keyValue = this.entrySet().iterator();
		for (int i = 0; i < size; i++){
			final Map.Entry<Integer, Integer> entry = keyValue.next();
			binarray[i] = entry.getKey();
			histogram[i] = entry.getValue();
			//System.out.println("i="+1+" v=" +valuearray[i] +" h="+histogram[i]);
		}
		histarray=new Pair<int[],int[]>(binarray,histogram);
		iscalc=true;		
	}
	

	public Pair<int[],int[]>  resample(Pair<int[],int[]>hist, int min, int max, int nbins) {
		int[] valuearray=hist.first;
		int[] histogram=hist.second;
		//int min=valuearray[0]; 
		//int max=valuearray[valuearray.length-1];

		int step=(max-min)/(nbins-1);
		
		if (step==0)  step=1;
		 
		debug=true;

		if (debug) System.out.println("step: " +step);
	 
		
		int[] binarray =ramp(min,  (int)step, nbins);
		int[] rh=new int[nbins];
		if (debug) System.out.println("binarray: " +binarray.length);
		if (debug) System.out.println("histarray: " +rh.length);
		if (debug) System.out.println("valuearray: " +valuearray.length);
		
		if (debug)
			Util.printIntArray(binarray);
	
		double scale=1/((max-min)/((double)nbins-1));
		
		if (debug) System.out.println("scale: " +scale);
		//System.out.print("min "+min +" max "+max+ " "+step +"\n");
	
		for (int i=0; i<valuearray.length; i++) {
			int bin=(int)((valuearray[i]-min)*scale);

			//System.out.println("bin "+bin +" va "+valuearray[i]);
			rh[bin]+=histogram[i];
	
		}

		//if (debug)
		//	Util.printIntArray(rh);
	
		return new Pair<int[],int[]>(binarray,rh);
		 
	}
	
	public Pair<float[],int[]>  resample(Pair<int[],int[]>hist, float min, float max, int nbins) {
		int[] valuearray=hist.first;
		int[] histogram=hist.second;
		//int min=valuearray[0]; 
		//int max=valuearray[valuearray.length-1];

		float step= ((float)(max-min))/(nbins-1);
		
		if (step==0)  step=1;
		 
		//debug=true;

		if (debug) System.out.println("step: " +step);
	 
		
		float[] binarray =ramp(min,   step, nbins);
		int[] rh=new int[nbins];
		if (debug) System.out.println("binarray: " +binarray.length);
		if (debug) System.out.println("histarray: " +rh.length);
		if (debug) System.out.println("valuearray: " +valuearray.length);
		
		if (debug)
			Util.printFloatArray(binarray);
	

		double scale=1/step;
		
		if (debug) System.out.println("scale: " +scale);
		//System.out.print("min "+min +" max "+max+ " "+step +"\n");

		for (int i=0; i<valuearray.length; i++) {
			int bin=(int)((valuearray[i]-min)*scale);
			//System.out.println("bin "+bin +" va "+valuearray[i]);
			rh[bin]+=histogram[i];
	
		}
		
	
		//if (debug)
		//	Util.printIntArray(rh);
	
		return new Pair<float[],int[]>(binarray,rh);
		 
	}
	
	@Override
	public void updateStats() {
		final int[] binarray=histarray.first;
		final int[] histogram=histarray.second;
		
		final int size=this.size();
		
		double sum=0, sum1=0;
		hstat[0]=binarray[0]; // min
		hstat[2]=binarray[size-1]; // max
		hstat[4]=size; // size
		hstat[1]=(hstat[2]-hstat[0])/(hstat[4]-1);
		for (int i = 0; i < size; i++){
			sum+=histogram[i]*binarray[i];
			sum1+=histogram[i];
		}
		hstat[3]=sum; //volume
		hstat[5]=sum1;//area 
		
	}

	@Override
	public Pair<int[], int[]> getCumulant() {
		final int[] histogram=histarray.second;
		final int size=this.size();
		
		int[] chistogram=new int[size];
		int[] binarray=new int[size];
		System.arraycopy(histarray.first, 0, binarray, 0, histarray.first.length);
		int sum=0;
		for (int i = 0; i < size; i++){
			sum+=histogram[i];
			chistogram[i]=sum;
		}
		return Pair.of(binarray,chistogram);
	}
	
	

}
