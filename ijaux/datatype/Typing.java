package ijaux.datatype;

/*
 * type checking and conservation - should be implemented by all major classes
 */
public interface Typing {

	/*
	 *  returns the underlying primitive data type of the object
	 */
	 Class<?> getType();
	
	boolean eq(Class<?> c);
	
}
