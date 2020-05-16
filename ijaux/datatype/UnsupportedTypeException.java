package ijaux.datatype;

/**
 * 
 * @author Dimiter Prodanov
 *
 */
public class UnsupportedTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9046001319614582814L;

	public UnsupportedTypeException() {
	}

	public UnsupportedTypeException(String message) {
		super(message);
	}

	public UnsupportedTypeException(Throwable cause) {
		super(cause);
	}

	public UnsupportedTypeException(String message, Throwable cause) {
		super(message, cause);
	}

}
