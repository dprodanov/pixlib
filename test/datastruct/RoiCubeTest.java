package test.datastruct;
import ij.gui.PolygonRoi;
import ij.gui.Roi;
import ijaux.compat.RoiCube;
import ijaux.datatype.Pair;
import ijaux.hypergeom.ShapeCube;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;


public class RoiCubeTest {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Roi[] rect= {new Roi(new Rectangle (0,0,10,10)), 
					 new Roi(new Rectangle (0,0,50,30)),
					 new Roi(new Rectangle (60,47,50,30)),
					 new PolygonRoi(new Polygon(new int[] {10, 20, 30}, new int[] {20, 10, 50}, 3), Roi.POLYGON) 
					};
		 
		int[] dim={100, 100, 3};
		RoiCube sc= new RoiCube(dim, Roi.class);
		sc.addElement(new int[] {0,0,0}, rect[0]);
		
		sc.addElement(new int[] {10,10, 1}, rect[1]);
		sc.addElement(new int[] {20,0, 2}, rect[0]);
		sc.addElement(new int[] {100,100, 3}, rect[2]);
		sc.addElement(new int[] {99,99, 2}, rect[1]);
		sc.addElement(new int[] {99,100, 2}, rect[1]);
		sc.addElement(new int[] {100,100, 2}, rect[1]);
		System.out.println("printing Roi cube");
		System.out.println(sc);
		
		System.out.println("retriving Roi by index");
		Pair<int[], Roi> sp=sc.element(2);
		System.out.println(sp);
		
		System.out.println("retriving Roi by coordinates");
		Pair<int[], Roi> sp1=sc.element(new int[] {100,100,3});

		System.out.println(sp1);
		System.out.println(printPolygon(sp1.second.getPolygon()));
		double area=sc.roiArea(sp1.second);
		double volume=sc.stackVolume();
		System.out.println("area "+area +" vol " + volume);
	}
	
	static String printPolygon(Polygon p) {
		final int np=p.npoints;
		//System.out.println("np "+np);
		final int[] x=p.xpoints;
		final int[] y=p.ypoints;
		StringBuffer sb=new StringBuffer(200);
		sb.append("[");
		for (int i=0; i<np; i++) {
			sb.append("("+x[i]+","+y[i]+")");		 
		}
		sb.append("]");
		return sb.toString();
	}
}
