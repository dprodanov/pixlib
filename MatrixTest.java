import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.Access;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.BaseIndex2D;
import ijaux.hypergeom.index.GridIndex;
import ijaux.hypergeom.index.Indexing;
import Jama.Matrix;


public class MatrixTest {

	
	public MatrixTest () throws UnsupportedTypeException {
		 System.gc();
		int[] dima={200, 200};
	      int n=Util.cumprod(dima);
	      double [] am=Util.rampDouble(n, n);
	      double [] bm=Util.rampDouble(n, n);
	     
	      
	    // BaseIndex2D ind2d=new BaseIndex2D(dima);
	   //  AuxIndex ind2d=new AuxIndex(dima);
	      
	  
	    	  
		//	final Access<Double> ac=Access.rawAccess(am,  ind2d);
		//	final Access<Double> bc=Access.rawAccess(bm,  ind2d);
			
			
			long  start=System.currentTimeMillis();
			//int []  ik=new int[2];
			//int []  kj=new int[2];
			//int []  ij=new int[2];
			
			 
			mult(dima, am, bm);
			start=System.currentTimeMillis()- start;
			
			System.out.println("matrix multiplication raw "+ start);
			
			double[][] vals =new double[dima[0]][dima[1]]; 
			double[][] vals1 =new double[dima[0]][dima[1]]; 
			
			Matrix A = new Matrix(vals);
			Matrix B = new Matrix(vals1);
			
			start=System.currentTimeMillis();
			
			Matrix C=A.times(B);
			start=System.currentTimeMillis()- start;
			System.out.println("matrix multiplication JAMA "+ start);
		 
	}
	/**
	 * @param dima
	 * @param n
	 * @param am
	 * @param bm
	 */
	private double[] mult(int[] dima,  double[] am, double[] bm) {
		final int n=dima[0]*dima[1];
		double [] ret=new double[n];
	
		int ai=0;
		for (int i=0; i<dima[0]; i++) {
			for (int j=0; j<dima[1]; j++) {	
				int aint=ai;
				int bind=j;
				
				double value=0;
				while (bind<n) {
					value+=am[aint++]*bm[bind];
					bind+=dima[1];
					
				} // end for

				ret[ai +j]=value;
			} // end for
			 ai+=dima[0];
		} // end for
		
		return ret;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 System.gc();
	      
	      int[] dims={100, 120, 120};
	      
	      GridIndex gind=GridIndex.create(dims);
	      int[] pnt={9, 3, 5};
	      gind.translateTo(pnt);
	      
	      int imax=gind.max();
	      
	      System.out.println("n-D case "+ imax);
	      System.out.println(gind);
	      System.out.println(gind.getType());
	      
	     
	      long time1=System.currentTimeMillis();
	      for (int i=0; i<imax; i++) {
	    	  gind.setIndex(i);
	    	  gind.isValid();
	      }
	      time1=System.currentTimeMillis() -time1;
	      
	      System.out.println("time " +time1);
	      
	      dims=new int[]{1000, 1440};
	      
	      gind=GridIndex.create(dims);
	      pnt=new int[]{9, 5};
	      gind.translateTo(pnt);
	      
	      imax=gind.max();
	      System.out.println("2D case " +imax);
	      System.out.println(gind);
	      System.out.println(gind.getType());
	      
	      time1=System.currentTimeMillis();
	      for (int i=0; i<imax; i++) {
	    	  gind.setIndex(i);
	    	  gind.isValid();
	      }
	      time1=System.currentTimeMillis() -time1;
	      
	      System.out.println("time " +time1);
	      
	      gind=new BaseIndex(dims);
	      pnt=new int[]{9, 5};
	      gind.translateTo(pnt);
	      
	      imax=gind.max();
	      System.out.println("2D case " +imax);
	      System.out.println(gind.getType());
	      
	      time1=System.currentTimeMillis();
	      for (int i=0; i<imax; i++) {
	    	  gind.setIndex(i);
	    	  gind.isValid();
	      }
	      time1=System.currentTimeMillis() -time1;
	      
	      System.out.println("time " +time1);
	      
	      dims=new int[]{1440000};
	      
	      gind=GridIndex.create(dims);
	      pnt=new int[]{9};
	      gind.translateTo(pnt);
	      
	      imax=gind.max();
	      System.out.println("1D case "+ imax);
	      System.out.println(gind);
	      System.out.println(gind.getType());
	      
	      time1=System.currentTimeMillis();
	      for (int i=0; i<imax; i++) {
	    	  gind.setIndex(i);
	    	  gind.isValid();
	      }
	      time1=System.currentTimeMillis() -time1;
	      
	      System.out.println("time " +time1);
	      
	      gind=new BaseIndex(dims);
	      pnt=new int[]{9};
	      gind.translateTo(pnt);
	      
	      imax=gind.max();
	      System.out.println("1D case "+ imax);
	      System.out.println(gind.getType());
	      
	      time1=System.currentTimeMillis();
	      for (int i=0; i<imax; i++) {
	    	  gind.setIndex(i);
	    	  gind.isValid();
	      }
	      time1=System.currentTimeMillis() -time1;
	      
	      System.out.println("time " +time1);
	      
	     
	      try {
			new MatrixTest ();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      
	}
	
	class AuxIndex implements Indexing<int[]> {

		private int[] coords;
		private int index=0;
		
		private int[] dim;

		public AuxIndex(int[] dims ) {
			final int n=dims.length;
			if (n!=2) throw new IllegalArgumentException("dimensions != 2");
			coords=new int[n];
			dim=dims;
		}
		
		@Override
		public int[] getCoordinates() {
			return coords;
		}

		@Override
		public int index() {
			return indexOf(coords);
		}

		@Override
		public int indexOf(int[] x) {
			return x[1]*dim[0]+x[0];
		}

		@Override
		public boolean isValid() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void reshape(int[] dims) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setIndex(int idx) {
			index=idx;
			
		}

		@Override
		public int translate(int[] x) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int translateTo(int[] x) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean contains(int[] vect) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean contains(int[] vect, int[] set) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean geq(int[] vect, int[] set) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean in(int[] inner, int[] outer, int[] set) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean leq(int[] vect, int[] set) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean out(int[] inner, int[] outer, int[] set) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getNDim() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setIndexAndUpdate(int idx) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int[] getDim() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setCoordinates(int[] x) {
			// TODO Auto-generated method stub
			
		}

	
		
	}
	
	 
	/*
	  public Matrix times (Matrix B) {
      if (B.m != n) {
         throw new IllegalArgumentException("Matrix inner dimensions must agree.");
      }
      Matrix X = new Matrix(m,B.n);
      double[][] C = X.getArray();
      double[] Bcolj = new double[n];
      for (int j = 0; j < B.n; j++) {
         for (int k = 0; k < n; k++) {
            Bcolj[k] = B.A[k][j];
         }
         for (int i = 0; i < m; i++) {
            double[] Arowi = A[i];
            double s = 0;
            for (int k = 0; k < n; k++) {
               s += Arowi[k]*Bcolj[k];
            }
            C[i][j] = s;
         }
      }
      return X;
   }

	 */

}
