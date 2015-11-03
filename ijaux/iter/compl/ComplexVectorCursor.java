package ijaux.iter.compl;

import ijaux.Util;
import ijaux.datatype.ComplexArray;
import ijaux.datatype.ComplexNumber;
import ijaux.datatype.access.Access;
import ijaux.datatype.access.ComplexAccess;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.GridIndex;
import ijaux.hypergeom.index.Indexing;
import ijaux.iter.AbstractIterator;
import ijaux.iter.dir.VectorIterator;



public class ComplexVectorCursor extends VectorIterator<double[][]> {
 
	
	protected ComplexAccess access;
	
	protected double[][] buffer2;
	protected double[] buffer;
	
	public ComplexVectorCursor(ComplexArray c, int[] dim) {
		pointIdx= new BaseIndex(dim);
		access=new ComplexAccess(c, pointIdx);
		returnType=access.getType();
		size=Util.cumprod(dim);
		this.dim=dim;
		n=dim.length;
		coordinates=new int[n];
		dir=Util.rampInt(n, n);
		//Util.printIntArray(dim);
	}

	@Override
	public boolean hasNext() {
		//System.out.println("printing  ..." +i+" / " +( size/blockSize) + ": "+ " axis "+  axis);
		return i < size/blockSize;
	}

	/*
	 *  returns an array of size [blockSize][2]
	 *  format: re[][0], im[][1]
	 */
	@Override
	public double[][] next() {
		final double[][] ret=	getDoubleArray2();
		i++;
		return ret;
	}
 
	public ComplexArray nextC() {
		final double[] ret=	getDoubleArray();
	
		double[] re=new double[blockSize];
		double[] im=new double[blockSize];
		for (int k=0; k<ret.length; k+=2) {
			final int cnt=k/2;
			re[cnt]=ret[k];
			im[cnt]=ret[k+1];
		}
		i++;
		return new ComplexArray(re,im, false);
	}
	
	/*
	 *  returns an array of size [blockSize][2]
	 *  format: re[][0], im[][1]
	 */
	public double[][] getDoubleArray2() {
		//final double[][] ret=new double[blockSize][2];
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			buffer2[i]=access.element(coordinates);	
			if (debug)
				Util.printIntArray(coordinates);
			coordinates[x]++;
		
		}

		return buffer2;
	}
	

	public double[] getDoubleArray() {
		//final double[] ret=new double[blockSize*2];
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		//System.out.print(blockSize+" ");
		for (int  k=0; k<2*blockSize; k+=2) {
			final double[] c=access.element(coordinates);	
			buffer[k]=c[0];
			buffer[k+1]=c[1];
			if (debug)
				Util.printIntArray(coordinates);
			coordinates[x]++;
		
		}

		return buffer;
	}
	 
	
	


	/*
	 *  requires an array of size [blockSize][2]
	 *  format: re[][0], im[][1]
	 */
	@Override
	public void put(double[][] array) {
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		for (int i=0; i<blockSize; i++) {
			access.putV(coordinates, array[i]); 
			coordinates[x]++;
		}

		i++;
		
	}

	public void put(double[] array) {
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		//Util.printIntArray(coordinates);
		for (int i=0; i<blockSize; i++) {
			final ComplexNumber c= new ComplexNumber(array[2*i], array[2*i+1], false);
			access.putV(coordinates, c); 
			coordinates[x]++;
		}
		//System.out.print(i);
		i++;
	}
	
	public void put(ComplexArray array) {
		final int x=dir[0];
		coordinates[x]=0;
		nest(coordinates,i);
		//Util.printIntArray(coordinates);
		for (int i=0; i<blockSize; i++) {
			final ComplexNumber c=array.element(i);
			access.putV(coordinates, c); 
			coordinates[x]++;
		}
		//System.out.print(i);
		i++;
		
	}
	
	 
	public void setDirection(int xdir) {
		dir=new int[n];
		dir[0]=xdir;
		int[] udim=new int [n-1];		 
		int u=0;
		for (int k=0; k<n; k++) {
			if (k!=xdir) {
				udim[u]=dim[k];
				u++;
				dir[u]=k;
			}

		}
		//Util.printIntArray(udim);
		
		lineIdx= GridIndex.create(udim);	
		//dir=perm(n,xdir);
		//Util.printIntArray(dir);
		blockSize=dim[dir[0]];
		
		buffer2=new double[blockSize][2];
		buffer=new double[blockSize*2];
		//axis=1;	
		reset();
	}
 
}
