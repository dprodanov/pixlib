package ijaux.compat;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.stats.HyperCubeHistogram;
import ijaux.stats.SparseFloatHistogram;
import ijaux.stats.SparseIntHistogram;

public class ImagePlusHistogram extends HyperCubeHistogram  {

	@SuppressWarnings("rawtypes")
	public ImagePlusHistogram(ImageProcessor ip, int bins) {	
		try {
			ImagePlusAccess<?> ipa=new ImagePlusAccess(ip);
			doHist(bins, ipa);
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ImagePlusHistogram(ImageProcessor ip) {	
		try {
			ImagePlusAccess<?> ipa=new ImagePlusAccess(ip);
			doHist(nBins, ipa);
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
	}
	
	public ImagePlusHistogram(ImagePlus imp, int bins) {
		ImagePlusCube ipc=new ImagePlusCube (imp);
		try {
			doHist(bins, ipc);
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
	}

	public ImagePlusHistogram(ImagePlus imp) {
		ImagePlusCube ipc=new ImagePlusCube (imp);
		try {
			doHist(nBins, ipc);
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
	}
	
	public ImagePlusHistogram(ImagePlusCube pc, int bins) {
		try {
			doHist(bins, pc);
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	 
	
	public ImagePlusHistogram(ImagePlusCube pc) throws UnsupportedTypeException {
		init(pc);
		updateStats();
	}
	
	private void doHist(int bins, ImagePlusCube ipc)
			throws UnsupportedTypeException {
		init(ipc);
		nBins=bins;
		updateStats();
	}
	
	private void doHist(int bins, ImagePlusAccess<?> ipa)
			throws UnsupportedTypeException {
		init(ipa);
		nBins=bins;
		updateStats();
	}
	
	/**
	 * @param pc
	 * @throws UnsupportedTypeException 
	 */
	public <E extends Number> void init(ImagePlusCube pc) throws UnsupportedTypeException {
		final Class<?> c=pc.getType();
		if (c==int.class || c==short.class || c== byte.class) {
			System.out.println("Integer type histogram map");
			map= new SparseIntHistogram(pc.getAccess());
			return;
		}  
		if (c==double.class || c==float.class) {
			System.out.println("Float type histogram map");
			map= new SparseFloatHistogram(pc.getAccess());
			return;
		}  		
	}
	
	/**
	 * @param pc
	 * @throws UnsupportedTypeException 
	 */
	public <E extends Number> void init(ImagePlusAccess<?> pc) throws UnsupportedTypeException {
		final Class<?> c=pc.type;
		if (c==int.class || c==short.class || c== byte.class) {
			System.out.println("Integer type histogram map");
			map= new SparseIntHistogram(pc.getAccess());
			return;
		}  
		if (c==double.class || c==float.class) {
			System.out.println("Float type histogram map");
			map= new SparseFloatHistogram(pc.getAccess());
			return;
		}  		
	}
	
	public SimpleStatistics getStatistics() {
		return new SimpleStatistics();
	}
	
	public class SimpleStatistics {
		public double mean=0;
		public double mode=0;
		public double var=0;
		public double sd=0;
		public double min=0;
		public double max=0;
		
		public SimpleStatistics() {
			//doHistogram();
			
			int[] counts=getHistogram();
			float[] bin= getBins();
			mean= ((double)hstat[3])/((double)hstat[5]);
			min= hstat[0];
			max= hstat[2];
			int b=0;
			float x=counts[0];
			double sum=0;
			//System.out.println("loop ... " + hstat[5]);
			for (int i=0; i< bin.length; i++) {
				final double u=bin[i]-mean;
				//final double u=(bin[i-1]*bin[i-1]+ 
				//		bin[i-1]*bin[i]+ bin[i]*bin[i])/3.0;
				sum+=u*u*counts[i];
				//System.out.println (bin[i]+" " + counts[i]);
				if (counts[i]>x) {
					x=counts[i];
					b=i;
					//System.out.print(x+",");
				}
					
			}			
			var= sum/((double)hstat[5]);
			sd=Math.sqrt(Math.abs(var));
			mode=bin[b];
		}
		
		public String toString() {
			String s="m="+mean +"\r\n"+
					 "mode= "+mode +"\r\n" +
					 "var= "+var +"\r\n" +
					 "sd= "+sd+"\r\n" +
					 "min=" +min +"\r\n" +
					 "max=" +max;
		 
			return s;
		}
	} // end class
}
