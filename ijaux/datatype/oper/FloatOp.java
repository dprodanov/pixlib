package ijaux.datatype.oper;

public class FloatOp extends Op<Float> {

	@Override
	public <V extends Number> Float addE(Float a, V b) {
		return a+b.floatValue();
	}

	@Override
	public <V extends Number> Float subE(Float a, V b) {
		return a-b.floatValue();
	}

	@Override
	public <V extends Number> Float multE(Float a, V b) {
		return a*b.floatValue();
	}

	@Override
	public <V extends Number> Float divE(Float a, V b) {
		return a/b.floatValue();
	}

	@Override
	public <V extends Number> Float minE(Float a, V b) {
		return min(a, b.floatValue());
	}

	@Override
	public <V extends Number> Float maxE(Float a, V b) {
		return  max(a, b.floatValue());
	}

	@Override
	public Float invE(Float b) {
		return -b;
	}

		 
		 
		
	} // end class