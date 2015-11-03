package ijaux.hypergeom.convolution;

import java.lang.reflect.Array;
import java.util.Iterator;

import ijaux.Constants;
import ijaux.Util;
import ijaux.datatype.Pair;
import ijaux.datatype.Typing;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.VectorCube;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.morphology.StructureElement;
/*
 * @author Dimiter Prodanov
 */
public class Kernel<E extends Number, N extends Object> implements  
Constants, Iterable<Pair<int[], E>>, Typing{

	protected Class<?> type;
	protected Pair<Number,Number> minmax;
	
	protected VectorCube<E> vc;
 
	private boolean iscalc=false;
	
	protected int[] dim;
	protected int ndim=0;
	protected int size=0;
	
	protected double w=1.0;
  	
	public E dummy=null;
 	
	public Kernel(Object arr, E dummy) {
		this.dummy=dummy;
		type=Util.getTypeMapping(dummy.getClass());
		Class<?> ctype=Util.getPrimitiveType(arr.getClass());
		if (type!=ctype)
			throw new IllegalArgumentException("type mismatch");
		//TODO
	}
	
	public Kernel(VectorCube<E> avc){
		vc=avc;
		type=vc.getType();
		dim=vc.getDimensions();
		ndim=vc.getNDimensions();
		size=(int) vc.size();

		iscalc=true; // since we assign a VectorCube
		weight(vc);
	}
	 
	public Kernel(StructureElement<E> se) {
		this(se.getVect());
	}
 
	public  Kernel(N arr, E dummy, int[] dims){
		//System.out.println("c: " +dummy.getClass());
		//Class<?> ctype= Util.getTypeMapping( dummy.getClass());
		//System.out.println(".."+Array.getLength(arr) );
		this.dummy=dummy;
		this.vc =new VectorCube<E>(dims, arr, dummy, CENTERED_INDEXING);
		type=vc.getType();
		
		//System.out.println(type);
		dim=vc.getDimensions();
		System.out.println("vc dim");
		Util.printIntArray(dim);
		ndim=vc.getNDimensions();
		size=(int) vc.size();

		iscalc=true; // since we assign a VectorCube
		weight(arr);
	}
	
	public static Kernel<?,?> create (KernelTypes type, int ndim) {
		Kernel<Float, float[]> kern=null;
		Float dummy=new Float(0f);
		switch (type) {
						case LAPLACIANx2:{
							float  [] lkern={0f, 1f, 0f, 
									1f, -4f, 1f,
									0f, 1f, 0f};
							int[] dimx={3,3};
							kern=new Kernel<Float, float[]>(lkern, dummy,  dimx);
							break;
						}
						case LAPLACIANxN: {
							int[] dimx=new int[ndim];
							for (int i=0; i<ndim; i++) dimx[i]=3;
							
							break;
						}
		}
		
		return kern;
	}
	
	
	
	public void flip() {		
		vc.flip();
	}
	
	
	
	public Class<?> getType() {
		return type;
	}
	
	public int[] getDimensions() {
		return dim;
	}
	

	
	public double getWeight() {
		return w;
	}

	public void setWeight(double w) {
		this.w = w;
	}
	
	protected void weight(VectorCube<E> se) {
		double c=0.0;
		for (Pair<int[], E> p:se) {
			c+=p.second.doubleValue();
		}
		w=c;
	}

	protected void weight(N arr) {
		double c=0.0;		
		try {
			Access<?> access = Access.rawAccess(arr, null);
			for (int i=0; i<access.size()[0]; i++) {
			c+=access.elementDouble(i);
		}
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//System.out.println("weight: "+c);
		w=c;
	}
	
	public PixelCube<E, ?> getMask() {
		//System.out.println(vc);
		Object pixels=vc.toOrderedArray();
		printdim(dim);
		if (!iscalc) {
			int size=Util.cumprod(dim);
			pixels=Array.newInstance(type, size);
			//System.out.println("type "+pixels.getClass()+" size "+size);
			
		}
		PixelCube<E,BaseIndex> pc=new PixelCube<E,BaseIndex>(dim,pixels);   
		pc.setIndexing(BASE_INDEXING);
		pc.setIterationPattern(IP_SINGLE+IP_FWD);
		return pc;
	}

	private void printdim(int [] dim) {
		System.out.print("[ ");
		for (int d: dim) {
			System.out.print(d+" ");
		}
		System.out.println("]");
	}
	
	public VectorCube<E> getVect() {
		return vc;
	}
	

	@Override
	public Iterator<Pair<int[], E>> iterator() {
		return vc.iterator();
	}
	
	@Override
	public boolean eq(Class<?> c) {
		return type==c;
	}

	public void trim() {
		vc.trim();		
	}

}
