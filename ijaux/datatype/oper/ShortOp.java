package ijaux.datatype.oper;

public class ShortOp extends Op<Short> {

	@Override
	public <V extends Number> Short addE(Short a, V b) {
		return (short) (add(a,b.intValue()) & shortMask);
	}

	@Override
	public <V extends Number> Short subE(Short a, V b) {
		return (short) (sub(a,b.intValue()) & shortMask);
	}

	@Override
	public <V extends Number> Short multE(Short a, V b) {
		return (short) (mult(a,b.intValue()) & shortMask);
	}

	@Override
	public <V extends Number> Short divE(Short a, V b) {
		return (short) (div(a,b.intValue()) & shortMask);
	}

	@Override
	public <V extends Number> Short minE(Short a, V b) {
		return (short)(min(a,b.intValue()) & shortMask);
	}

	@Override
	public <V extends Number> Short maxE(Short a, V b) {
		return (short)(max(a,b.intValue()) & shortMask);
	}

	@Override
	public Short invE(Short b) {
		return invu(b);
	}

		 
		 
		
	} // end class