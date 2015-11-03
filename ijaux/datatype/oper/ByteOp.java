package ijaux.datatype.oper;

public class ByteOp extends Op<Byte> {
	 
		@Override
		public <V extends Number> Byte addE(Byte a, V b) {
			return (byte) (add(a,b.intValue()) & byteMask);
		}

		@Override
		public <V extends Number> Byte subE(Byte a, V b) {
			return (byte) (sub(a,b.intValue()) & byteMask);
		}

		@Override
		public <V extends Number> Byte multE(Byte a, V b) {
			return (byte) mult(a,b.intValue());
		}

		@Override
		public <V extends Number> Byte divE(Byte a, V b) {
			return (byte)(div(a,b.intValue()) & byteMask);
		}

		@Override
		public <V extends Number> Byte minE(Byte a, V b) {
			return (byte)(min(a,b.intValue()) & byteMask);
		}

		@Override
		public <V extends Number> Byte maxE(Byte a, V b) {
			return (byte)(max(a,b.intValue()) & byteMask);
		}

		@Override
		public Byte invE(Byte b) {
			return invu(b);
		}

		 
	
		
	} // end class