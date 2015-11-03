package ijaux.datatype;

/*
 *  marker interface for separable kernels and structure elements
 */
public interface Separable<VectorType> {

	public int getNKernels();
	
	public VectorType[] getKernelArrays();
	
	public void split();
	
	public VectorType join();
	
	public boolean isSeparable();
}
