package test.index;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.CenteredIndex;


public class CneteredIndexTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] dims={3,3};
		CenteredIndex ci=new CenteredIndex(dims);
		int[] coords={1,0};
		System.out.println("ind: "+ci.computeIndex(coords));
		ci.setIndex(5);
		System.out.println(ci);
		ci.warp();
		System.out.println(ci);
		
		 coords=new int[]{1,-1};
		System.out.println("ind: "+ci.computeIndex(coords));
		
		coords=new int[]{0,1};
		BaseIndex bi=new BaseIndex(dims);
		System.out.println("ind: "+bi.computeIndex(coords));
		System.out.println(bi);

	}

}
