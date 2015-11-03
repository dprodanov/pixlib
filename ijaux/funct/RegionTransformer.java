package ijaux.funct;

import ijaux.hypergeom.VectorCube;

public interface RegionTransformer<E extends Number> {
	
	E elementTransform(VectorCube<E> ve, ElementFunction<?,E> elementFnct, int idx);
	
	//public byte elementTransformB(int idx);
	
	//public short elementTransformS(int idx);
	
	public int elementTransformI(VectorCube<E> ve, ElementFunction<?,E> elementFnct, int idx);
	
	public float elementTransformF(VectorCube<E> ve, ElementFunction<?,E> elementFnct, int idx);
	
	public double elementTransformD(VectorCube<E> ve, ElementFunction<?,E> elementFnct, int idx);
	
	public boolean elementTransformB(VectorCube<E> ve, ElementFunction<?,E> elementFnct, int idx);
	
	public char elementTransformC(VectorCube<E> ve, ElementFunction<?,E> elementFnct, int idx);

	

}
