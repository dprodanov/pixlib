package test;

import ijaux.datatype.ComplexFNumber;
import ijaux.datatype.ComplexNumber;

public class TypingTest {

	public TypingTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		 
		ComplexFNumber one=ComplexFNumber.one();
		
		System.out.println( one.eq(float.class));
		ComplexNumber one1=ComplexNumber.one();
		System.out.println( one1.eq(double.class));
	}

}
