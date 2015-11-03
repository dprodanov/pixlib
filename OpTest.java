
import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.oper.Op;


/**
 * 
 */

/**
 * @author prodanov
 * @param <E>
 *
 */
public class OpTest {
	 
	public OpTest() {
		// TODO Auto-generated constructor stub
	}

	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			@SuppressWarnings("unchecked")
			Op<Byte> op= (Op<Byte>) Op.get((byte)1 );
			
			Byte a=Byte.valueOf((byte)3);
			Byte b=Byte.valueOf((byte)4);
			System.out.println((byte)op.add((byte)3,  4.4f));
			System.out.println(op.addE(a , b));
			boolean vb=a*b>0;
			System.out.println(a>b);
			
			System.out.println( op.add((byte)127,  127));
			Pair<int[],Float> p=Pair.of(new int[2], 3.5f);
			Op<Float> op2=    (Op<Float>) Op.get(p.second);
			final float u=op2.multE(p.second, 5.6f);
			p.second= Float.valueOf(u);
			System.out.println(p);
			
			
			
			
			
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	




}
