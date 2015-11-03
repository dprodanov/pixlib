package ijaux.io;

 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.channels.FileChannel.MapMode;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cursor backed by a MappedByteBuffer accessing a one RandomAccessFile at a time
 * 
 * @author dprodanov
 *
 */
public class FileListCursor extends MappedCursor implements Iterator<File>{
	
	private File currentFile, headerFile;
	
	private RandomAccessFile raf;
	
	private FileChannel inChannel;
	
	//private long fpointer;
		
	//private int headerSize=0;
	
	//private byte[] headerBytes={};

	//private int capacity=65535;

	//private MappedByteBuffer  buffer; 
	
	//ByteBuffer headerbuffer; // to be accessed by eventual decoders/encoders in the same package
	
	//private MapMode axmode;

	private FileLock lock;

	//private boolean isOpen=false;
	
	//private ByteOrder bo;

	private int fileIndex=0;

	//private String accessmode="r";
	
	public FileListCursor (Class<?> c) {
		super(c);
		/*
		bytesPerPixel=map.get(c);
		returnType=c;
		bo=ByteOrder.nativeOrder();
		*/
	}
	
	public FileListCursor (File[] list, int ind, Class<?> c) {
		this(c);
		registerFiles(list);
		if (ind>0) fileIndex=ind;
		currentFile=list[fileIndex];
	}
	
	/*
	public int getCapacity() {
		return capacity;
	}
	*/
	public void open(String mode) throws IOException {
		System.out.println(currentFile.getAbsolutePath());
		accessMode(mode);
		openCurrent();
	}

	/**
	 * @param mode
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public int openCurrent() throws FileNotFoundException,
			IOException {
		if (debug) System.out.println(currentFile.getAbsolutePath());
		if (currentFile.exists() && currentFile.isFile() && currentFile.canRead()) {
			raf = new RandomAccessFile(currentFile.getAbsolutePath(), accessmode);
			inChannel = raf.getChannel();
		
			isOpen=true;
			
			if ((accessmode.equals("rw") || accessmode.equals("rwd") || accessmode.equals("rwd")) && currentFile.canWrite()) { 
				axmode=MapMode.READ_WRITE;
				try {
			        lock = inChannel.tryLock();
			    } catch (OverlappingFileLockException e) {
			        // File is already locked in this thread or virtual machine
			    }

			}
				
			if (accessmode.equals("r")) axmode=MapMode.READ_ONLY;
			
			capacity=(int)(inChannel.size()/bytesPerPixel);
			/*
			 * opening code
			 */
			int size=(int)inChannel.size();
			if (debug) System.out.println("size "+size);
			fpointer=0;
			if (currentFile.equals(headerFile)) 
				fpointer=headerSize;
			buffer=inChannel.map(axmode, 0, size);
			buffer.order(bo);
			
			return buffer.limit();
		}
		return -1;
	}

	/**
	 * @param mode
	 */
	/*
	public void accessMode(String mode) {
		if (!(mode.equals("r") || 
				mode.equals("rw") || 
				mode.equals("rwd") || 
				mode.equals("rws") ))
				throw new IllegalArgumentException("not a valid opening mode");
		accessmode=mode;
	}
	*/
	public void open(File f, String mode) throws IOException {
		if (f!=null && files.contains(f)) {
			currentFile=f;
			open(mode);
		}
	}
	
	public void openNext() {
		setFile(fileIndex++);
	}
	
	public boolean setFile(int i) {
		
		if (i<nFiles) {
			File f=currentFile;
			try {
				close();
				currentFile=files.get(i);		
				return (openCurrent()>0);
			} catch (IOException e) {
				currentFile=f;
				e.printStackTrace();
			}
		}
		return false;
	}

	/*
	 * gives the size of the current file in bytes
	 */

	public long length () throws IOException {
		if (isOpen)
			return inChannel.size();
		else
			return -1;
	} 

	
	public void clear() {
		buffer.clear();
		fpointer=0;
	}
	
	public void close() throws IOException {
		if (isOpen) {
			if (lock!=null) lock.release();
			inChannel.close();
			raf.close();
			isOpen=false;
		}
	}
 
	public void configHeader(File f, int headerSize) {
		if (f.exists() && f.isFile() && f.canRead()) { 
			headerFile=f;
			this.headerSize=headerSize;	
		}
			
	}
	
	public void config (String mode, ByteOrder bo, boolean loadHeader) {
 
		if (bo!=null)
			this.bo=bo;
		accessMode(mode);
		
		if (headerSize>0 && loadHeader)
			try {
				mapHeader(headerSize, bo);
			} catch (IOException e) {
				e.printStackTrace();
			}
 
	}

	/*
	private void mapHeader(int headerSize, ByteOrder bo) throws IOException {
		if (debug) System.out.println("headerSize "+headerSize);
		long pos=inChannel.position();
		inChannel.position(0);
		headerbuffer=ByteBuffer.allocate(headerSize);
		 if (bo!=null)
			 headerbuffer.order(bo);
		 else 
			 headerbuffer.order(ByteOrder.nativeOrder());
		
		
		inChannel.read(headerbuffer);
		headerbuffer.flip();
		inChannel.position(pos);
		headerBytes=array(headerbuffer);
	}
	
	public byte[] getHeader() {
		return headerBytes;
	}
	*/
	
	/*
	public long pointerOf(long pos) {
		return headerSize +bytesPerPixel* pos;
	}

	
	public boolean hasArray() {
		return buffer.hasArray();
	}
	
	public void seek(int ind) {
		buffer.position(ind);
	}
	
	
	public ByteBuffer asByteBuffer() {
		if (returnType==byte.class) return buffer;
		return null;
	}
	
	public ShortBuffer asShortBuffer() {
		if (returnType==short.class) return buffer.asShortBuffer();
		return null;
	}
	
	public IntBuffer asIntBuffer() {
		if (returnType==int.class) return buffer.asIntBuffer();
		return null;
	}
	
	public FloatBuffer asFloatBuffer() {
		if (returnType==float.class) return buffer.asFloatBuffer();
		return null;
	}
	
	public DoubleBuffer asDoubleBuffer() {
		if (returnType==double.class) return buffer.asDoubleBuffer();
		return null;
	}
	
	public CharBuffer asCharBuffer() {
		if (returnType==char.class) return buffer.asCharBuffer();
		return null;
	}
	
	 
	public MappedByteBuffer getRawBuffer() {
		return buffer;
	}
*/
	public boolean hasNext() {
		return fileIndex<nFiles;
	}

	public File next() {
		currentFile=files.get(fileIndex++);
		return currentFile;
	}

	public void remove() {
		files.remove(fileIndex);
		currentFile=files.get(fileIndex);
		nFiles--;
	}


	
}
