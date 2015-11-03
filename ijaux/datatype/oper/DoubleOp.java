package ijaux.datatype.oper;

public class DoubleOp extends Op<Double> {

	@Override
	public <V extends Number> Double addE(Double a, V b) {
		return a+b.doubleValue();
	}

	@Override
	public <V extends Number> Double subE(Double a, V b) {
		return a-b.doubleValue();
	}

	@Override
	public <V extends Number> Double multE(Double a, V b) {
		return a*b.doubleValue();
	}

	@Override
	public <V extends Number> Double divE(Double a, V b) {
		return a/b.doubleValue();
	}

	@Override
	public <V extends Number> Double minE(Double a, V b) {
		return min(a, b.doubleValue());
	}

	@Override
	public <V extends Number> Double maxE(Double a, V b) {
		return  max(a, b.doubleValue());
	}

	@Override
	public Double invE(Double b) {
		return -b;
	}

		 
		 
		
	} // end class