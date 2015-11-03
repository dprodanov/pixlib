package test.datastruct;
import ijaux.datatype.Pair;
import ijaux.hypergeom.ShapeCube;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;


public class ShapeCubeTest {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Shape[] rect= {new Rectangle (0,0,10,10), 
						   new Rectangle (0,0,20,10),
						   new Polygon(new int[] {10, 20, 30}, new int[] {20, 10, 50}, 3) };
		 
		int[] dim={100, 100};
		ShapeCube<Shape> sc= new ShapeCube<Shape>( dim);
		sc.addElement(new int[] {0,0}, rect[0]);
		
		sc.addElement(new int[] {10,10}, rect[1]);
		sc.addElement(new int[] {20,0}, rect[0]);
		sc.addElement(new int[] {100,100}, rect[2]);
		System.out.println("printing shape cube");
		System.out.println(sc);
		
		System.out.println("retriving shape by index");
		Pair<int[], Shape> sp=sc.element(3);
		System.out.println(sp);
		
		System.out.println("retriving shape by coordinates");
		Pair<int[], Shape> sp1=sc.element(new int[] {10,10});

		System.out.println(sp1);
	}
}
