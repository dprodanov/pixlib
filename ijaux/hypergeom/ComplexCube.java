/**
 * 
 */
package ijaux.hypergeom;

import ijaux.Util;
import ijaux.datatype.ComplexArray;
import ijaux.datatype.ComplexNumber;
import ijaux.datatype.access.Access;
import ijaux.datatype.access.ComplexAccess;
import dsp.SamplingWindow;
import dsp.WindowTypes;
import ijaux.hypergeom.index.*;
import ijaux.iter.compl.ComplexVectorCursor;

import java.util.Iterator;

/*
 * @author Dimiter Prodanov
 */

public class ComplexCube extends RasterCube<ComplexNumber, int[]> 
implements Cloneable {

	protected ComplexArray pixels;

	protected ComplexAccess access;

	private SamplingWindow wnd=null;
	
	int dir=0;

	public ComplexCube(int[] dim) {
		this.dim=dim;
		size=Util.cumprod(dim);
		n=dim.length;
		pIndex=  new BaseIndex(dim);
		indexingType=pIndex.getClass();
		pixels=ComplexArray.create(size);
		access=new ComplexAccess(pixels, pIndex);
	}

	public ComplexCube(PixelCube<?,?> pc) {
		Access<?> ac=pc.getAccess();
		final int l=(int)ac.length();
		dim=pc.dim;
		n=dim.length;
		size=pc.size();
		double [] re=new double[l];

		for (int i=0; i<l; i++) {
			re[i]=ac.elementDouble(i);
		}
		pixels=new ComplexArray(re);
		pIndex=  new BaseIndex(dim);
		indexingType=pIndex.getClass();
		access=new ComplexAccess(pixels, pIndex);

	}

	public ComplexCube(int[] dim, ComplexArray pixels) {
		this.dim=dim;
		this.pixels=pixels;

		size=Util.cumprod(dim);
		n=dim.length;
		access=new ComplexAccess(pixels, null);
		pIndex=  new BaseIndex(dim);
		indexingType=pIndex.getClass();
		access=new ComplexAccess(pixels, pIndex);
	}

	public void setPixels(ComplexArray pixels) {
		final int length=(int)pixels.length();
		if (size!= length)
			throw new IllegalArgumentException("size mismatch " + length);
		this.pixels=pixels;
		access=new ComplexAccess(pixels, null);
		pIndex=  new BaseIndex(dim);
		indexingType=pIndex.getClass();
		access=new ComplexAccess(pixels, pIndex);
		
	}
	
	public double[] Re() {
		return pixels.Re();
	}
	
	public PixelCube<Double,?> RealCube() {		
		return new PixelCube(dim, pixels.Re(), double.class, pIndex.getClass());
	}
	
	public double[] Im() {
		return pixels.Im();
	}
	
	public double[] norm2() {
		return pixels.norm2();
	}
	
	public double [] phase() {
		return pixels.arg();
	}
	
	public void scale(){
		pixels.scale();
	}
	
	public PixelCube<Double,?> ImCube() {		
		return new PixelCube(dim, pixels.Im(), double.class, pIndex.getClass());
	}
 
	public   Indexing<int[]> getIndex() {
		return  pIndex;
	}
	
	
	public void insert(ComplexArray array, int dir, int row) {
	
		final int width=dim[dir];
		//System.out.println("inserting row "+row);
		//System.out.println ("array " +array);
		if (array.length()>width)
			throw new IllegalArgumentException("array size mistmatch " + array.length() +" "+ width);			
		//pIndex.setIndex(0);
		int[] coords=coordinates();
		Util.printIntArray(coords);
		//coords[dir]=0;
		System.out.println("inserting elements "+width +" dir "+dir);
		for (int i=0; i<width; i++) {
			access.putV(coords, array.element(i));
			coords[dir]++;
		}
		coords[dir]=0;
		coords[row]++;
		final int index=pIndex.indexOf(coords);
		pIndex.setIndexAndUpdate(index);
		//pIndex.translateTo(coords);
		System.out.println("coords "+ index);
		Util.printIntArray(coords);
	}
	
	
	public void setWindow(int n, WindowTypes type) {
		wnd=SamplingWindow.createWindow(type, n);
	}

	public void setWindow(WindowTypes type) {
		int n= dim[dir];
		wnd=SamplingWindow.createWindow(type, n);
	}
	
	public void setIndexing() {
		pIndex=  new BaseIndex(dim);
		indexingType=pIndex.getClass();
		access.setIndexing(pIndex);
	}


	@Override
	public Iterator<ComplexNumber> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public ComplexAccess getAccess() {
		return access;
	}

	public String toString(){
		 return pixels.toString();
	}

	public ComplexVectorCursor iteratorBlock() {
		return new ComplexVectorCursor(pixels, dim);
	}

	
	public void fftshift(int dir) {
		ComplexVectorCursor block=iteratorBlock();
		block.setDirection(dir);
		while ( block.hasNext()) {
			final ComplexArray carr=block.nextC();
			block.dec();
			//System.out.println((i++)+" " );			
			fftshift1d(carr);
			//System.out.println ("cs " +carr);
			//System.out.println("iter " +" dir " +dir);
			block.put(carr);
			
		}
		
	}
	

	public void fftshift1d(ComplexArray arr) {
		final int k=(int)arr.length()/2;
		double[] re=arr.Re();
		double[] im=arr.Im();
		//final int n=re.length-1;
		
		for (int i=0; i<k; i++) {
			double tmp=re[i];
			re[i]=re[k+i];
			re[k+i]=tmp;
			
			tmp=im[i];
			im[i]=im[k+i];
			im[k+i]=tmp;
		}
	}
	//konop's
		public void fftshift1d(double[] arr) {
			final int k=arr.length/2;		
			for (int i=0; i<k; i++) {
				double tmp=arr[i];
				arr[i]=arr[k+i];
				arr[k+i]=tmp;			
			}
		}

	public void fftshift() {
		for (int i=0;i<n;i++)
			fftshift(i); 
	}
	
	
	public void ifftshift(int dir) {
		ComplexVectorCursor block=iteratorBlock();
		block.setDirection(dir);
		while ( block.hasNext()) {
			final ComplexArray carr=block.nextC();
			block.dec();
			//System.out.println((i++)+" " );			
			fftshift1d(carr);
			//System.out.println ("cs " +carr);
			//System.out.println("iter " +" dir " +dir);
			block.put(carr);
			
		}
		
	}
	
	public void ifftshift() {
		for (int i=0;i<n;i++)
			fftshift(i); 
	}
}
