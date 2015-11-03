/**
 * 
 */
package ijaux.compat;


import ij.ImagePlus;
import ij.gui.Roi;
import ij.measure.Calibration;
import ijaux.Util;
import ijaux.datatype.Pair;
import ijaux.hypergeom.PairCube;
import ijaux.hypergeom.index.BaseIndex;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import java.util.Map.Entry;
 

/**
 * @author prodanov
 *
 */
public class RoiCube extends PairCube<int[], Roi> {

	int maxind=0;
	
	public RoiCube(int[] dim, Class<? super Roi> btype) {
		super(dim, int[].class, btype);
		aind=new BaseIndex(dim);
		maxind=((BaseIndex)aind).max();
		//System.out.println(maxind);
	}
	
	public RoiCube(ImagePlus imp) {
		super(Util.trimDims(imp), int[].class, imp.getRoi().getClass());
		aind=new  ImagePlusIndex( imp);
		final int[] dims=imp.getDimensions();
		int p=1;
		for (int d:dims) p*=d;
		maxind=p;
		 
	}

	
	
	@Override
	public void addElement(int[] coords, Roi val) {
		if (ndim!=coords.length)
			throw new IllegalArgumentException("Array dimensions do not match "+ndim);
		
		if (aind.indexOf(coords)<=maxind) {
			super.addElement(coords, val);
			//System.out.println(aind.indexOf(coords) + " " +maxind);
		}
	}
	
	@Override
	public void addElement(final Pair<int[],Roi> p) {
		if (ndim!=p.first.length)
			throw new IllegalArgumentException("Array dimensions do not match "+ndim);
		if (aind.indexOf(p.first)<=maxind)
			super.addElement(p);
	}
	
	@Override
	public Pair<int[], Roi> element(int[] coords) {
		if (ndim!=coords.length)
			throw new IllegalArgumentException("Array dimensions do not match "+ndim);

		return super.element(coords);
	}
	/*
	 * imports the Hashtable in the ImageJ Roi manager
	 */
	public void addAll(HashMap<Integer,Roi> ht) {
		Set<Entry<Integer,Roi>> entries=ht.entrySet();
		 for (Entry<Integer,Roi> x:entries) {
			 final Roi roi=x.getValue();
		  
			 final Rectangle box=roi.getBounds();
			  addElement(new int[] {box.x,box.y, x.getKey()}, roi);
		 }
		
	}
	
 

	/*
	 * imports the Hashtable in the ImageJ Roi manager
	 */
	public void addAll(Roi[] rois, int [] z) {
		int c=0;
		for (Roi roi:rois) {
			  final Rectangle box=roi.getBounds();
			  addElement(new int[] {box.x,box.y, z[c]}, roi);
			  c++;
		}
	}

	@Override
	public Class<?> getType() {
		return btype;
	}

	@Override
	public Iterator<Pair<int[], Roi>> iterator() {
		return  vector.listIterator();
	}
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer(2000);
		ListIterator<Pair<int[], Roi>> li=vector.listIterator();

		int cnt=0;
		while (li.hasNext()) {
			final Pair<int[], Roi> p=li.next();
			sb.append(cnt +" val: [ "+p.second.getTypeAsString() +" "+p.second+ "] [");
			for(int c:p.first) {
				sb.append(c+" ");
			}
			sb.append("idx: "+aind.indexOf(p.first));
			sb.append("]\n");
			cnt++;
		}
		return sb.toString();
	}

	/*
	 * volume of the roi set calculated as a stack of 1 pixel-thick cylinders
	 */
	public double stackVolume( ) {
		double vol=0;
		ListIterator<Pair<int[], Roi>> li=vector.listIterator();	
		
		 
		Pair<int[], Roi> p=li.next();
	 
		for (int cnt=1;cnt<size(); cnt++) {
			final double a=Math.abs(roiArea(p.second));	
			
			int z=p.first[2];
	
			p=li.next();
			//System.out.println(p.first[2]-z);
			vol+=a*(p.first[2]-z);		
		}
		
		return vol;
	}
	
	public double stackVolume(Calibration cal) {
		return  (stackVolume()*cal.pixelDepth);
	}
	
	public double roiArea(Roi oval) {
	  final Polygon p=oval.getPolygon();
	  if(p!=null) {
		  //System.out.println(printPolygon(p) );
		  return polyArea(p);	
	  }
		return 0;
	}
	
	public double roiArea(Roi oval, Calibration cal) {
		final float da=(float) (cal.pixelHeight* cal.pixelWidth);
		return roiArea(oval)*da;
	}
	
	
	
	 public double polyArea (Polygon poli) {
	    	
			int n=poli.npoints;
			if (n<=0) return 0;
			
			//System.out.println(" npoints: " +n);
			int[] x=poli.xpoints;
			
			int[] y=poli.ypoints;
			double a=0;
			for (int i=0; i<n-1; i++) {
				//System.out.println(i+" x["+i +"]=" +x[i]+ 
				//		" y["+i+ " ]=" + y[i]);
				a+=x[i]*y[i+1]-x[i+1]*y[i];
			}
			a+=x[n-1]*y[0]-x[0]*y[n-1];
			//System.out.println(" x[0]=" +x[0]+ " y[0]=" + y[0]);
			//n--;
			//System.out.println(n+ " x["+n +"]=" +x[n]+ " y["+n+ " ]=" + y[n]);
			a=Math.abs(a)/2;
			//System.out.println("area: "+a);
			return a;
		}
	 
	public String printPolygon(Polygon p) {
		final int np=p.npoints;
		//System.out.println("np "+np);
		final int[] x=p.xpoints;
		final int[] y=p.ypoints;
		StringBuffer sb=new StringBuffer(200);
		sb.append("Polygon: [");
		for (int i=0; i<np; i++) {
			sb.append("("+x[i]+","+y[i]+")");		 
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public boolean eq(Class<?> c) {		 
		return btype==c;
	}


	 
 }