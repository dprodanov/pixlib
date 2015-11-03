package test.datatype;

import static org.junit.Assert.*;
import ijaux.TestUtil;
import ijaux.Util;
import ijaux.datatype.IComplexArray;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IComplexArrayTest     {
	
	static final double[] xr={31,	-14.0710678118655f,		5,	  0.0710678118654755f,	
							 -5,	  0.0710678118654755f,	5,	-14.0710678118655f};

	static final double[] xi={0,	-4.82842712474619f,		 4,	-0.828427124746190f,	
							 0,	 0.828427124746190f,	-4,	 4.82842712474619f};
	
	static double[] xic;
	
	static IComplexArray icarr=null;

	static double[] iterleavedarray=null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		icarr=new IComplexArray(xr, xi);
	}

	@Before
	public void setUp() throws Exception {
		icarr=new IComplexArray(xr, xi);
		iterleavedarray=new double[xr.length*2];
		for (int i=0, c=0; i<xr.length; i+=2, c++){
			iterleavedarray[i]=xr[c];
			iterleavedarray[i+1]=xi[c];
		}
		
		xic=new double[xi.length];
		for (int i=0 ; i<xi.length; i++){		 
			xic[i]=-xi[i];
		}
	}

	@Test
	public final void testCreate() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		IComplexArray arr=IComplexArray.create(10);
	}

	@Test
	public final void testIComplexArrayDoubleArrayDoubleArray() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");	
		new IComplexArray(xr, xi);
		
	}

	@Test
	public final void testIComplexArrayDoubleArray() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		new IComplexArray(iterleavedarray);
	}

	@Test
	public final void testPolarForm() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testRe() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: Re");
		double[] re=icarr.Re();
		double sd=TestUtil.sqdiff(re, xr, true);	
		if (sd!=0) {
			System.out.println ("expected ");	
			Util.printDoubleArray(xr);
			System.out.println ("tested ");
			Util.printDoubleArray(re);
		}
		assertEquals(0,sd,0);
	}

	@Test
	public final void testIm() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: Im");
		double[] im=icarr.Im();
		double sd=TestUtil.sqdiff(im, xi, true);	
		if (sd!=0) {
			System.out.println ("expected ");	
			Util.printDoubleArray(xi);
			System.out.println ("tested ");
			Util.printDoubleArray(im);
		}
		assertEquals(0,sd,0);
	}

	@Test
	public final void testReInt() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: Re(i)");
		int i=4;
		double re=icarr.Re(i);
		double sd=Math.abs(re-xr[i]);	
		if (sd!=0) {
			System.out.println ("expected " + xr[i]);	
			System.out.println ("tested "+re);
			System.out.println (icarr);
		 
		}
		assertEquals(0,sd,0);
	}

	@Test
	public final void testImInt() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: Im(i)");
		int i=4;
		double im=icarr.Im(i);
		double sd=Math.abs(im-xi[i]);	
		if (sd!=0) {
			System.out.println ("expected " + xi[i]);	
			System.out.println ("tested "+im);
			System.out.println (icarr);
		 
		}
		assertEquals(0,sd,0);
	}

/*	@Test
	public final void testClone() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}*/

	@Test
	public final void testConjugate() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: conjugate");
		IComplexArray arr2=icarr.conjugate();
		double[] im=arr2.Im();
		double sd=TestUtil.corrcoef(im, xi);	
		if (sd!=-1) {
			System.out.println ("expected ");	
			Util.printDoubleArray(xic);
			System.out.println ("tested ");
			Util.printDoubleArray(im);
		}
		assertEquals(-1,sd,0);
	}
	
	@Test
	public final void testUconjugate() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: uconjugate");
		icarr.uconjugate();
		double[] im=icarr.Im();
		double sd=TestUtil.corrcoef(im, xi);	
		if (sd!=-1) {
			System.out.println ("expected ");	
			Util.printDoubleArray(xic);
			System.out.println ("tested ");
			Util.printDoubleArray(im);
		}
		assertEquals(-1,sd,0);
	}

	@Test
	public final void testModInt() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testMod() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testNorm() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testArgInt() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testArg() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testInvs() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testMult() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testNorm2() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testNorm2Int() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddIComplexArray() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddDouble() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testInv() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testInvInt() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testScale() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSub() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDiv() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testValidate() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: validate");
		IComplexArray arr=IComplexArray.create(xr.length);
		boolean b=icarr.validate(arr);
		assertEquals(true, b);
	}

	@Test
	public final void testElementInt() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testPutE() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testElementInteger() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testPutV() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testPut() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSize() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: size");
		int[] sz=icarr.size();
		int l2=xr.length;
		assertEquals(l2,sz[0],0);
	}

	@Test
	public final void testLength() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: length");
		long l=icarr.length();
		long l2=xr.length*2;
		assertEquals(l2,l,0);
	
	}

	@Test
	public final void testGetType() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: getType()");
		assertEquals(double.class, icarr.getType());
	}

	@Test
	public final void testEq() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: create");
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testToString() {
		System.out.println ("********************************");
		System.out.println ("IComplexArray: toString()");
		System.out.println (icarr.toString());
	}

}
