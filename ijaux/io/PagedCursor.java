/**
 * 
 */
package ijaux.io;

import java.io.File;
import java.io.IOException;

/**
 * @author dprodanov
 *
 */
public abstract class PagedCursor extends Cursor {
	
	protected int pageSize=256;
	
	protected int pageIndex=-1;
	
	protected File currentFile;

	public File getCurrentFile() {
		return currentFile;
	}

	protected int capacity=pageSize;
	
	public void openFileCache() throws IOException {
		File tmp=createTmpFile();
		open(tmp, "rw");		 
	}
	
	public abstract int dec();
	
	public abstract int inc(); 
	
	public abstract int  maxPage();

	public abstract  int seek(int page);
	
	public abstract int seek(long page); 
	
	public abstract boolean hasArray();
	
	public abstract void clear();
}
