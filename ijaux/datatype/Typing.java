package ijaux.datatype;


/**
 * type checking and conservation - should be implemented by all major classes
 * @author Dimiter Prodanov
 *
 */
public interface Typing {


	/**
	 * returns the underlying primitive data type of the object
	 * @return
	 */
	Class<?> getType();
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	default boolean  eq(Class<?> c) { return getType()==c; }
	
}
