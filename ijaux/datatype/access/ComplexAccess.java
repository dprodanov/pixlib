package ijaux.datatype.access;

import ijaux.datatype.ComplexArray;
import ijaux.datatype.ComplexNumber;
import ijaux.datatype.Pair;
import ijaux.datatype.Typing;
import ijaux.hypergeom.index.Indexing;

public class ComplexAccess implements ElementAccess<int[], double[]>, Typing {
 
	
	protected double[] re, im;
	
	protected Indexing<int[]> pIndex;
	private double[] ua=new double[2];
	
	public ComplexAccess (Object obj, Indexing<int[]> aind) {
		if (obj.getClass()==ComplexArray.class) {
			re=((ComplexArray) obj).first;
			im=((ComplexArray) obj).second;
			pIndex=aind;
			return;
		}
		if (obj.getClass()==double[][].class) {
			re=((double[][])obj)[0];
			im=((double[][])obj)[1];
			pIndex=aind;
			return;
		}
		throw new IllegalArgumentException("not a complex casting");
	}
	
	public ComplexAccess(ComplexArray ca, Indexing<int[]> aind) {
		re=ca.first;
		im=ca.second;
		pIndex=aind;
	}
	
	public ComplexAccess(double[]a, double[]b, Indexing<int[]> aind) {
		re=a;
		im=b;
		pIndex=aind;
	}
	
	public ComplexAccess(double[][]cmp,   Indexing<int[]> aind) {
		re=cmp[0];
		im=cmp[1];
		pIndex=aind;
	}
	
	@Override
	public double[] element(int index) {
		final double a=re[index];
		final double b=im[index];
		return new double[]{a,b};
	}
 
	public ComplexNumber elementC(int index) {
		return new ComplexNumber(re[index],im[index], false);
	}	
 
	public ComplexNumber elementC(int[] coords) {
		final int index=pIndex.indexOf(coords);
		ua[0]=re[index];
		ua[1]=im[index];
		return new ComplexNumber(re[index],im[index], false);
	}
	
	public void putC(int index, ComplexNumber c) {
		re[index]=c.Re();
		im[index]=c.Im();	
	}
	
	@Override
	public void putE(int index, double[] value) {
		re[index]=value[0];
		im[index]=value[1];	
	}

	@Override
	public double[] element(int[] coords) {
		final int index=pIndex.indexOf(coords);
		ua[0]=re[index];
		ua[1]=im[index];
		return ua;
	}

	@Override
	public void putV(int[] coords, double[] value) {
	 
		final int index=pIndex.indexOf(coords);
		re[index]=value[0];
		im[index]=value[1];	
		
	}
	
	public void putV(int[] coords, ComplexNumber c) {
		final int index=pIndex.indexOf(coords);
		//System.out.println(index);
		re[index]=c.Re();
		im[index]=c.Im();	
	}

	@Override
	public void put(Pair<int[], double[]> pair) {
		final int index=pIndex.indexOf(pair.first);
		final double[] value=pair.second;
		re[index]=value[0];
		im[index]=value[1];	
		
	}
	
	public void putC(Pair<int[], ComplexNumber> pair) {
		final int index=pIndex.indexOf(pair.first);
		final ComplexNumber c=pair.second;
		re[index]=c.Re();
		im[index]=c.Im();	
		
	}

	@Override
	public int[] size() {
		return new int[] {re.length,2};
	}
	
	public long length() {
		return re.length;
	}

	public void setIndexing(Indexing<int[]> index) {
		pIndex=index;		
	}

	@Override
	public Class<?> getType() {
		return double.class;
	}

	@Override
	public boolean eq(Class<?> c) {
		return c==double.class;
	}

}
