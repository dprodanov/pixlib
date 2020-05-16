package ijaux.datatype;

/*
 *  marker interface for separable kernels and structure elements
 */
public interface Separable<VectorType> {

	/**
	 * 
	 * @return
	 */
	public int getNKernels();
	
	/**
	 * 
	 * @return
	 */
	public VectorType[] getKernelArrays();
	
	/**
	 * 
	 */
	public void split();
	
	/**
	 * 
	 * @return
	 */
	public VectorType join();
	
	/**
	 * 
	 * @return
	 */
	public default boolean isSeparable() {
		return true;
	}
}
