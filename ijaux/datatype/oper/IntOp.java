package ijaux.datatype.oper;

public class IntOp extends Op<Integer> {

	@Override
	public <V extends Number> Integer addE(Integer a, V b) {
		return a+b.intValue();
	}

	@Override
	public <V extends Number> Integer subE(Integer a, V b) {
		return a-b.intValue();
	}

	@Override
	public <V extends Number> Integer multE(Integer a, V b) {
		return a*b.intValue();
	}

	@Override
	public <V extends Number> Integer divE(Integer a, V b) {
		return a/b.intValue();
	}

	@Override
	public <V extends Number> Integer minE(Integer a, V b) {
		return min(a, b.intValue());
	}

	@Override
	public <V extends Number> Integer maxE(Integer a, V b) {
		return  max(a, b.intValue());
	}

	@Override
	public Integer invE(Integer b) {
		return -b;
	}

		 
		 
		
	} // end class