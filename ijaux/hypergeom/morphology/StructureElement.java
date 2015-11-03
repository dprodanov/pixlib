package ijaux.hypergeom.morphology;

import java.lang.reflect.Array;
import java.util.Iterator;


import ijaux.*;
import ijaux.datatype.*;
import ijaux.datatype.oper.Op;
import ijaux.hypergeom.*;
import ijaux.hypergeom.index.CenteredIndex;
import ijaux.stats.CubeHistogram;
import ijaux.stats.HyperCubeHistogram;

public class StructureElement<E extends Number> implements 
Structuring, Constants, Iterable<Pair<int[], E>>, Typing {
	
	private Class<?> type;
	protected Pair<Number,Number> minmax;
	
	protected VectorCube<E> vc=null;
	
	//protected VectorCube<Float> vcf=null; //TODO
 
	private boolean iscalc=false;

	protected float radius=0;
	
	protected int[] dim=null;
	protected int ndim=0;
	protected int size=0;
	
	protected String name="";
	public boolean debug=false;
	
	public StructureElement(float radius, int ndim,  Class<?> c, boolean rawtype) {
		int rad=(int)(2*radius+1);
		this.ndim=ndim;
		calcDim(rad, ndim); 
		if (c.isPrimitive())
			type=c;
		else
			type=Util.getTypeMapping(c);
		//System.out.println("type " +type.getCanonicalName());
		
		vc= new VectorCube<E>(dim,c, rawtype);
		this.radius=radius;
		//System.out.println(c.getCanonicalName());
		//type=Util.getTypeMapping(c);
		
		defaultMinMax();
	}
	
	public StructureElement(float radius, int ndim,  Class<? extends Number> c) {
		int rad=(int)(2*radius+1);
		this.ndim=ndim;
		calcDim(rad, ndim); 
		type=Util.getTypeMapping(c);
		vc= new VectorCube<E>(dim,c);
		this.radius=radius;
		//System.out.println(c.getCanonicalName());
		//type=Util.getTypeMapping(c);
		//System.out.println("type " +type.getCanonicalName());
		defaultMinMax();
	}
	
	
	public StructureElement(Object cpixels, int[] dims) {
		final Class<?> ctype=Util.getPrimitiveType(cpixels.getClass());
		VectorCube<E> avc=new VectorCube<E>(dims,  cpixels,CENTERED_INDEXING);
		vc=avc;
		//type=vc.getType();
		type=ctype;
		dim=vc.getDimensions();
		ndim=vc.getNDimensions();
		size=(int)vc.size();
		name="vector cube";
		//defaultMinMax();
		minmax(vc);
		iscalc=true; // since we assign a VectorCube
		// TODO : calculate "radius"; calculate minmax;
	}
	
	public StructureElement(VectorCube<E> avc){
		vc=avc;
		type=vc.getType();
		dim=vc.getDimensions();
		ndim=vc.getNDimensions();
		size=(int)vc.size();
		name="vector cube";
		//defaultMinMax();
		minmax(vc);
		iscalc=true; // since we assign a VectorCube
		// TODO : calculate "radius"
	}
	
	public Class<?> getType() {
		return type;
	}
 
	protected void calcDim(int rad, int ndim) {
		int prod=1;
		dim=new int[ndim];
		for (int i=0; i<dim.length; i++) {
			dim[i]=rad;
			prod*=rad;
		}
		size=prod;
	}
	 
	private void minmax(VectorCube<E> vc) {
		Iterator<Pair<int[], E>> iter=vc.iterator();
		
		final Class<?> c=vc.getType();
		if (c==float.class || c==double.class) {
			double _min=0;
			double _max=0;
			while (iter.hasNext()) {
				final Pair<int[], E> p=iter.next();
				final E u=p.second;
				if (u.doubleValue() <_min) _min=u.doubleValue();
				if (u.doubleValue() >_max) _max=u.doubleValue();
			}
			minmax=Pair.of((Number)_min, (Number)_max);
		} //
		if (c==byte.class || c==short.class || c==int.class) {
			int _min=0;
			int _max=0;
			while (iter.hasNext()) {
				final Pair<int[], E> p=iter.next();
				final E u=p.second;
				final int uu=TypeView.asInt(u);
				if (uu<_min) _min=uu;
				if (uu >_max) _max=uu;
			}
			minmax=Pair.of((Number)_min, (Number)_max);
		} //
	}
	
	private void defaultMinMax() {
		 
		if (type==byte.class) {
			minmax=Pair.of((Number)((byte) 0), (Number)((byte) 1));
			return;
		}
		if (type==short.class) {
			minmax=Pair.of((Number)((short) 0), (Number)((short) 1));
			return;
		}
		if (type==int.class) {
			minmax=Pair.of((Number)((int) 0), (Number)((int) 1));
			return;
		}
		if (type==float.class) {
			minmax=Pair.of((Number)0f, (Number)1f);
			return;
		}
		if (type==double.class) {
	 			minmax=Pair.of((Number)0d, (Number)1d);
			return;
		}
		 
	}
	
	@SuppressWarnings("unchecked")
	public  <T extends Number> void  setMinMax(T tmin, T tmax) {
			Pair<Number,Number> minmax1=minmax;
			minmax=(Pair<Number, Number>) Pair.of(tmin, tmax);	
			final float f=minmax.second.floatValue()/minmax1.second.floatValue();
			System.out.println("scaling:"+f);
			scale(f);
	}
	
	public Pair<Number, Number> getMinMax() {
		return minmax;
	}
	
	@SuppressWarnings("unchecked")
	public <E> E getMin() {
		return (E) minmax.first;
	}
	
	
	@SuppressWarnings("unchecked")
	public <E> E getMax() {
		return (E) minmax.second;
	}
	
	@SuppressWarnings("unchecked")
	public void setMinMax(final CubeHistogram<?> ch) {
		Pair<Number,Number> minmax1=minmax;
		//System.out.println(minmax1);
		minmax=(Pair<Number,Number> )ch.getMinMax();
		//System.out.println(minmax);

		final float f=minmax.second.floatValue()/minmax1.second.floatValue();
		if (f>1.0f) {
			System.out.println("scaling:"+f);
			scale(f);
		}
	}
	
	public <N extends Number> void setMinMax(Pair<N,N> mm) {
		Pair<Number,Number> minmax1=minmax;
		minmax= Pair.of(mm);
		// maximal
		final float f=minmax.second.floatValue()/minmax1.second.floatValue();
		System.out.println("scaling:"+f);
		scale(f);		
	}
	
	@SuppressWarnings("unchecked")
	public void scale(float f) {
		try {			
			Op<Number> op= (Op<Number>) Op.get(type);
			for (int i=0; i<vc.size(); i++) {
				Pair<int[],E> p=vc.element(i);			
				p.second=(E)op.multE(p.second, f);
				//System.out.print(p.second.getClass()+" : ");
			}
			minmax.first=op.multE(minmax.first, f);
			minmax.second=op.multE(minmax.second, f);
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
		}
		//minmax(vc);
		
	}
	
	public void trim() {
		vc.trim();		
	}
	
	public void flip() {		
		vc.flip();
	}
	
	
	public String getName() {
		return name;
	}

	@SuppressWarnings("unchecked")
	public void createMaskSparse(final float radius, final int[] dim, MetricSpace<? super CenteredIndex> ms) {
		vc.reset();
		final int size=Util.cumprod(dim);
		if (debug) System.out.println("mask size " + size);
        CenteredIndex ci = new CenteredIndex(dim);
        final E maxv=(E)minmax.second;
        for (int i=0; i<size; i++) {
        	ci.setIndex(i);
        	if(ms.norm(ci)<radius) {
        		vc.addElement(ci.getCoordinates(),maxv);
        	} 
        }
        iscalc=true;
	}
	
	@SuppressWarnings("unchecked")
	public void createMaskSparse2(final float radius, final int[] dim, MetricSpace<? super CenteredIndex> ms) {
		vc.reset();
		final int size=Util.cumprod(dim);
		if (debug) System.out.println("mask size " + size);
        CenteredIndex ci = new CenteredIndex(dim);
        final E maxv=(E)minmax.second;
        for (int i=0; i<size; i++) {
        	ci.setIndex(i);
        	if(ms.norm(ci)<=radius) {
        		vc.addElement(ci.getCoordinates(),maxv);
        	} 
         	
        }
        iscalc=true;
	}
	
	@SuppressWarnings("unchecked")
	public void createMaskSparseEq(final float radius, final int[] dim, MetricSpace<? super CenteredIndex> ms) {
		vc.reset();
		final int size=Util.cumprod(dim);
		if (debug) System.out.println("mask size " + size);
        CenteredIndex ci = new CenteredIndex(dim);
        final E maxv=(E)minmax.second;
        for (int i=0; i<size; i++) {
        	ci.setIndex(i);
        	if(ms.norm(ci)==radius) {
        		vc.addElement(ci.getCoordinates(),maxv);
        	}     	
        	
        }
        iscalc=true;
	}
	
	@SuppressWarnings("unchecked")
	public void createMaskCompl(final float radius, final int[] dim, MetricSpace<? super CenteredIndex> ms) {
		vc.reset();
		final int size=Util.cumprod(dim);
        if (debug) System.out.println("mask size " + size);
        CenteredIndex ci = new CenteredIndex(dim);
        final E minv=(E)minmax.first;
        final E maxv=(E)minmax.second;
        for (int i=0; i<size; i++) {
        	ci.setIndex(i);
        	if(ms.norm(ci)<radius) {
        		vc.addElement(ci.getCoordinates(),maxv);
  //      		System.out.println(i+" i: "+ci);
        	} else 
        		vc.addElement(ci.getCoordinates(),minv);
         }
        iscalc=true;
	}
	
	@SuppressWarnings("unchecked")
	public void createMaskCompl2(final float radius, final int[] dim, MetricSpace<? super CenteredIndex> ms) {
		vc.reset();
		final int size=Util.cumprod(dim);
        if (debug) System.out.println("mask size " + size);
        CenteredIndex ci = new CenteredIndex(dim);
        final E minv=(E)minmax.first;
        final E maxv=(E)minmax.second;
        for (int i=0; i<size; i++) {
        	ci.setIndex(i);
        	if(ms.norm(ci)<=radius) {
        		vc.addElement(ci.getCoordinates(),maxv);
        	} else 
        		vc.addElement(ci.getCoordinates(),minv);     	
        	
        }
        iscalc=true;
	}
	
	@SuppressWarnings("unchecked")
	public void createMaskComplEq(final float radius, final int[] dim, MetricSpace<? super CenteredIndex> ms) {
		vc.reset();
		final int size=Util.cumprod(dim);
        if (debug) System.out.println("mask size " + size);
        CenteredIndex ci = new CenteredIndex(dim);
        final E minv=(E)minmax.first;
        final E maxv=(E)minmax.second;
        for (int i=0; i<size; i++) {
        	ci.setIndex(i);
        	if(ms.norm(ci)==radius) {
        		vc.addElement(ci.getCoordinates(),maxv);
        	} else 
        		vc.addElement(ci.getCoordinates(),minv);
        }
        iscalc=true;
	}
	
	
	public void  createCircularMask(final float radius , boolean complete) {
		name="sphere";
        final float r2 = radius*radius+1 ;
        if (debug) System.out.println("circle radius^2 "+radius+" r2 "+r2);
        EuclideanIndex ein=new EuclideanIndex(dim);
        if (complete)
        	createMaskCompl(r2, dim, ein );
        else
        	createMaskSparse(r2, dim, ein );
    }
	
	public void  createDiamondMask(final float radius, boolean complete ) {
		name="diamond";
        final float d = (((int)(radius+0.5))*2 )/2 +1;
        if (debug) System.out.println("diamond radius "+radius+" d "+d);
        BlockIndex ein=new BlockIndex(dim);
        if (complete)
            createMaskCompl(d, dim, ein );
        else
        	createMaskSparse(d, dim, ein );
  
    }
	
	public void  createSquareMask(final float radius ) {
		name="cube";
		final float d = (((int)(radius+0.5))*2 )/2+1;
		if (debug) System.out.println("square radius "+radius+" d "+d);
		SqIndex bin=new SqIndex(dim);
        createMaskCompl(d, dim, bin);
    }
	
	public void  createLineMask(final float radius, int dir) {
		name="line";
		final float d = (((int)(radius+0.5))*2 )/2+1;
		if (debug) System.out.println("line radius "+radius+" d "+d);
        LineIndex2 lin=new LineIndex2(dim, dir, radius/(ndim-1));
        createMaskSparse(d, dim, lin);
	}
    	
	public void  create2PointMask(final float radius, int dir) {
		name="line";
		final float d = (((int)(radius+0.5))*2 )/2+1;
		if (debug) System.out.println("line radius "+radius+" d "+d);
        LineIndex2 lin=new LineIndex2(dim, dir, radius/(ndim-1));
        createMaskSparseEq(d, dim, lin);
	}

/* Returns the Structure Element as a Pixel Cube
 * (non-Javadoc)
 * @see ijaux.hypergeom.morphology.Structuring#getMask()
 */
	@Override
	public PixelCube<E, ?> getMask() {
		//Object pixels=vc.toArray();
		Object pixels=vc.toOrderedArray();
		if (!iscalc) {
			int size=Util.cumprod(dim);
			pixels=Array.newInstance(type, size);
			System.out.println("type "+pixels.getClass()+" size "+size);
		}
		PixelCube<E,CenteredIndex> pc=new PixelCube<E,CenteredIndex>(dim,pixels);   
		pc.setIndexing(BASE_INDEXING);
		pc.setIterationPattern(IP_SINGLE+IP_FWD);
		return pc;
	}

	@Override
	public VectorCube<E> getVect() {
		return vc;
	}

	 

	
	class EuclideanIndex extends CenteredIndex implements MetricSpace<CenteredIndex> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6023941276555124800L;

		public EuclideanIndex(int[] dims) {
			super(dims);
		}

		@Override
		public double distance(CenteredIndex a, CenteredIndex b) {
			if (!isMatched(a,b)) throw new IllegalArgumentException("dimensions do not match");
			double s=0;
			final int[] ac=a.getCoordinates();
			final int[] bc=b.getCoordinates();
			for (int i=0; i<n; i++) {
				s+=(ac[i]-bc[i])*(ac[i]-bc[i]);
			}
			return s; 
		}

		
		@Override
		public double norm(CenteredIndex a) {
			double s=0;
			final int[] bc=a.getCoordinates();
			for (int i=0; i<n; i++) {
				s+= bc[i]* bc[i];
			}
			return s; 
		}

	
		
	} // end internal class
	
	class BlockIndex extends CenteredIndex implements MetricSpace<CenteredIndex> {

 

		/**
		 * 
		 */
		private static final long serialVersionUID = -3358404800094092671L;

		public BlockIndex(int[] dims) {
			super(dims);
		}

		@Override
		public double distance(CenteredIndex a, CenteredIndex b) {
			if (!isMatched(a,b)) throw new IllegalArgumentException("dimensions do not match");
			double s=0;
			final int[] ac=a.getCoordinates();
			final int[] bc=b.getCoordinates();
			for (int i=0; i<n; i++) {
				s+=Math.abs(ac[i]-bc[i]);
			}
			return s;
		}

		@Override
		public double norm(CenteredIndex a) {
			double s=0;
			final int[] bc=a.getCoordinates();
			for (int i=0; i<n; i++) {
				s+= Math.abs(bc[i]);
			}
			return s;
		}

	
		
	} // end inner class

	class SqIndex extends CenteredIndex implements MetricSpace<CenteredIndex> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1512543303932515146L;
		 
		
		public SqIndex(int[] dims  ) {
			super(dims);
			 
		}

		@Override
		public double distance(CenteredIndex a, CenteredIndex b) {
			if (!isMatched(a,b)) throw new IllegalArgumentException("dimensions do not match");
			
			final int[] ac=a.getCoordinates();
			final int[] bc=b.getCoordinates(); 
			double s=0;			
			for (int i=0; i<n; i++) {
				s=Math.max(s,  Math.abs(ac[i]-bc[i]));
			}
			 
			return s;
		}

		@Override
		public double norm(CenteredIndex a) {
			
			final int[] bc=a.getCoordinates();
		 
			double s=0;			
			for (int i=0; i<n; i++) {
				s=Math.max(s,  Math.abs(bc[i]));
			}
			 
			return s;
		}
		
	} // end inner class
	class LineIndex extends CenteredIndex implements MetricSpace<CenteredIndex> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1512543303932515146L;
		int dir;
		
		public LineIndex(int[] dims, int dir) {
			super(dims);
			this.dir=dir;
		}

		@Override
		public double distance(CenteredIndex a, CenteredIndex b) {
			if (!isMatched(a,b)) throw new IllegalArgumentException("dimensions do not match");
			
			final int[] ac=a.getCoordinates();
			final int[] bc=b.getCoordinates(); 
				 
			final double s=Math.abs(ac[dir]-bc[dir]);
			 
			return s;
		}

		@Override
		public double norm(CenteredIndex a) {
			
			final int[] bc=a.getCoordinates();
		 
			final double s= Math.abs(bc[dir]);
			 
			return s;
		}
		
	} // end inner class
	
	class LineIndex2 extends CenteredIndex implements MetricSpace<CenteredIndex> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1512543303932515146L;
		int dir=0;
		double bias=0;
		
		public LineIndex2(int[] dims, int dir, double bias) {
			super(dims);
			if (dir >= dims.length) dir= dir % dims.length;
			dir= dims.length/2 - dir;
			this.dir=dir;
			this.bias=bias;
		}

		@Override
		public double distance(CenteredIndex a, CenteredIndex b) {
			if (!isMatched(a,b)) throw new IllegalArgumentException("dimensions do not match");
			double s=0;
			final int[] ac=a.getCoordinates();
			final int[] bc=b.getCoordinates(); 
			for (int i=0; i<n; i++) {
			   if (i==dir) s+=Math.abs(ac[dir]-bc[dir]);
			   else s+=bias;
			}
			 
			return s;
		}

		@Override
		public double norm(CenteredIndex a) {
			double s=0;
			final int[] bc=a.getCoordinates();
		 
			for (int i=0; i<n; i++) {
				   if (i==dir) s+=Math.abs(bc[dir]);
				   else s+=bias;
				}
			System.out.println("line distance "+s); 
			return s;
		}
		
	} // end inner class
	
	@Override
	public int[] getDimensions() {
		return dim;
	}

	@Override
	public Iterator<Pair<int[], E>> iterator() {

		return vc.iterator();
	}


	@Override
	public boolean eq(Class<?> c) {
		return type==c;
	}
	
	@Override
	public String toString() {
		return vc.toString();
	}
	
} // end class
