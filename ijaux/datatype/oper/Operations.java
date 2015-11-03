package ijaux.datatype.oper;

public interface Operations<E, V> {

	// to be implemented by the primitive operation types
		public abstract   E addE(E a, V b);
		
		// to be implemented by the primitive operation types
		public abstract  E subE(E a, V b);

		// to be implemented by the primitive operation types
		public abstract  E multE(E a, V b);
		
		// to be implemented by the primitive operation types
		public abstract  E divE(E a, V b);
		
		// to be implemented by the primitive operation types
		public abstract E minE(E a, V b);
		
		// to be implemented by the primitive operation types
		public abstract  E maxE(E a, V b);
}
