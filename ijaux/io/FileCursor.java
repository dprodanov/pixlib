package ijaux.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.OverlappingFileLockException;

/**
 * Cursor backed by a ByteBuffer accessing a specified RandomAccessFile
 * 
 * @author dprodanov
 *
 */
public class FileCursor extends PagedCursor{
	
	private RandomAccessFile raf;
	
	private FileChannel inChannel;
	
	private long fpointer=0;
	
	private int headerSize=0;
	
	private byte[] headerBytes={};
	
	private ByteBuffer buffer; 
	
	ByteBuffer headerbuffer; // to be accessed by eventual decoders/encoders in the same package

	private boolean pageLoaded=false;
	
	private int maxpg=0;
	
	private FileLock lock;

	private boolean isOpen=false;

	public FileCursor (boolean direct) {
		capacity=bytesPerPixel*pageSize;
		if (direct)
			buffer = ByteBuffer.allocateDirect(capacity);
		else
			buffer = ByteBuffer.allocate(capacity);
		buffer.order(ByteOrder.nativeOrder());
		if (debug) System.out.println("default order "+buffer.order() + " direct "+ buffer.isDirect());
	}
	
	public FileCursor (int pgsize, Class<?> c, boolean direct) {
		pageSize=pgsize;
		returnType=c;
		bytesPerPixel=map.get(c);
		capacity=bytesPerPixel*pageSize;
		if (direct)
			buffer = ByteBuffer.allocateDirect(capacity);
		else
			buffer = ByteBuffer.allocate(capacity);
		buffer.order(ByteOrder.nativeOrder());
		if (debug) System.out.println("default order "+buffer.order() + " direct "+ buffer.isDirect());

	}
	

	
	public void open(File f, String mode) throws IOException {
		if (debug) System.out.println(f.getAbsolutePath());
		if (!(mode.equals("r") || 
			mode.equals("rw") || 
			mode.equals("rwd") || 
			mode.equals("rwd") ))
			throw new IllegalArgumentException("not a valid opening mode");
		
		if (f.exists() && f.isFile() && f.canRead()) {
			currentFile=f;
			if (files.add(f)) nFiles++;
			raf = new RandomAccessFile(f.getAbsolutePath(), mode);
			inChannel = raf.getChannel();
			isOpen=true;
			
			if ((mode.equals("rw") || mode.equals("rwd") || mode.equals("rwd")) && f.canWrite()) { 
				try {
			        lock = inChannel.tryLock();
			    } catch (OverlappingFileLockException e) {
			        // File is already locked in this thread or virtual machine
			    }

			}
				
		}
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
	
	public int maxPage () {
		try {
			maxpg=(int)((length()- headerSize)/capacity);
			return maxpg;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}		
	}
	
	public void clear() {
		buffer.clear();
		pageIndex=-1;
		fpointer=0;
	}
	
	public void close() throws IOException {
		if (isOpen) {
			if (lock!=null) lock.release();
			inChannel.close();
			raf.close();
			isOpen=false;
		}
		files.remove(currentFile);
	}
	
	
	public void config (int headerSize, ByteOrder bo, boolean loadHeader) {
		this.headerSize=headerSize;	
		fpointer=headerSize;
		if (bo!=null)
			buffer.order(bo);
		if (headerSize>0 && loadHeader)
			try {
				mapHeader(headerSize, bo);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

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
	
	public long pointerOf(long pos) {
		return headerSize +bytesPerPixel* pos;
	}
	

	public int pageOf() {
		final int pageIndex=(int)((fpointer-headerSize)/capacity);
		return pageIndex;
	}
	
	public int seek(long pos) {
		if (!isOpen) return -1;
		if (pos<0) pos=0;
		long fp=pointerOf(pos);
		int offset=(int)(fp/capacity);
		fp= (int)offset* capacity;
		try {
			return mapPage(fp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean hasArray() {
		return buffer.hasArray();
	}
	
	public int seek(int page) {
		if (!isOpen) return -1;
		if (page<0) page=0;
		long fp=(long)page*capacity+headerSize ;
		try {
			if (pageIndex !=page)
				return mapPage(fp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	private int mapPage(long fp) throws IOException {
		int offset=(int)((fp-headerSize)/capacity);
		
		fpointer=(long)offset*capacity +headerSize;
		//System.out.println("offset: "+offset+ " fp : "+fpointer);
		//raf.seek(fpointer);
		inChannel.position(fpointer);
		int bytesRead = inChannel.read(buffer);
		
	//	System.out.println("offset: "+offset+ " fp : "+fpointer +" bytesRead "+ bytesRead);

		buffer.flip();
		
		pageLoaded=bytesRead>0;
		if (pageLoaded) {
			fpointer+=bytesRead;
			pageIndex=offset;
		}
		return bytesRead;
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
	
	
	public int inc() {
		fpointer+=capacity;
		try {
			return mapPage(fpointer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int dec() {
		fpointer-=capacity;
		try {
			return mapPage(fpointer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ByteBuffer getRawBuffer() {
		return buffer;
	}


	
}
