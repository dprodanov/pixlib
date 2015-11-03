package ijaux.funct;

import ijaux.hypergeom.VectorCube;

public interface IndexedTransformer<E extends Number, IndexType>{

	public E elementTransform(VectorCube<E> ve, ElementFunction<?,E> elementFnct, IndexType v);
	
	public int elementTransformI(VectorCube<E> ve, ElementFunction<?,E> elementFnct, IndexType v);
	
	public float elementTransformF(VectorCube<E> ve, ElementFunction<?,E> elementFnct, IndexType v);
	
	public double elementTransformD(VectorCube<E> ve, ElementFunction<?,E> elementFnct, IndexType v);
	
	public boolean elementTransformB(VectorCube<E> ve, ElementFunction<?,E> elementFnct, IndexType v);
	
	public char elementTransformC(VectorCube<E> ve, ElementFunction<?,E> elementFnct, IndexType v);
}
