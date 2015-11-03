package test.datastruct;
import ijaux.datatype.Pair;


public class PairTest {
	
	public static void main(String[] args) {
        Pair<String, String>
            p1 = new Pair<String, String>("a", "b"),
            p2 = new Pair<String, String>("a", null),
            p3 = new Pair<String, String>("a", "b"),
            p4 = new Pair<String, String>(null, null);
        System.out.println(p1.equals(new Pair<Integer, Integer>(new Integer(1), new Integer(2))) + " should be false");
        System.out.println(p4.equals(p2) + " should be false");
        System.out.println(p2.equals(p4) + " should be false");
        System.out.println(p1.equals(p3) + " should be true");
        System.out.println(p4.equals(p4) + " should be true");
    }


}
