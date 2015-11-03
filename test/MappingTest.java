package test;
import ijaux.Util;


public class MappingTest {

	static Class<?> type;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		type=Util.getTypeMapping(Byte.class);
		System.out.println(type.getCanonicalName());
		
		type=Util.getTypeMapping(byte.class);
		System.out.println(type.getCanonicalName());

		type=Util.getTypeMapping(byte[].class);
		System.out.println(type.getCanonicalName());
		
		type=Util.getTypeMapping(Short.class);
		System.out.println(type.getCanonicalName());
		
		type=Util.getTypeMapping(short.class);
		System.out.println(type.getCanonicalName());

		type=Util.getTypeMapping(short[].class);
		System.out.println(type.getCanonicalName());
		
		try {
			type=Util.getTypeMapping(Byte[].class);
			System.out.println(type.getCanonicalName());
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
