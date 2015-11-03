package ijaux.io;


import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/*
 * prototype class for handling file cursors/caches
 * backed by nio ByteBuffers
 */
public abstract class Cursor {
	
	protected int bytesPerPixel=1; // byte
	
	protected Class<?> returnType=byte.class;
	
	protected ArrayList<File> files= new ArrayList<File>();
	
	protected int nFiles=0;
	
	public static boolean debug=true;
	
	protected static ConcurrentHashMap<Class<?>,Integer> map=new ConcurrentHashMap<Class<?>,Integer>();

	protected String accessmode="r";
	
	static  {
		int[]sz={
			Byte.SIZE/8,
			Short.SIZE/8,
			Integer.SIZE/8,
			Float.SIZE/8,
			Double.SIZE/8,
			Character.SIZE/8
		};
		
		Class<?>[] c={
			byte.class,
			short.class,
			int.class,
			float.class,
			double.class,
			char.class
		};
		
		for (int i=0; i<sz.length; i++) {
			map.put(c[i], sz[i]);
		}
	}
	
	/**
	 * @param mode
	 */
	public void accessMode(String mode) {
		if (!(mode.equals("r") || 
				mode.equals("rw") || 
				mode.equals("rwd") || 
				mode.equals("rws") ))
				throw new IllegalArgumentException("not a valid opening mode");
		accessmode=mode;
	}
	
	/*
	 * removes all files registered in the ArrayList
	 */
	public void removeFiles() {
		files.clear();
		nFiles=0;
	}
	
	/*
	 * registers an array of files in the ArrayList
	 */
	public int registerFiles(File[] fpath) {
		int cnt=0;
		for (File f:fpath) {
			if (f.isFile() && f.exists() && f.canRead() && files.add(f)) {
				cnt++;
			}
		}
		nFiles+=cnt;
		return cnt;
	}
	
	public File createTmpFile() {
		Random random=new Random();
		random.setSeed(System.currentTimeMillis());
		int rnd=random.nextInt();
		String name="tmp_"+Integer.toHexString(rnd)+".raw";
		if (debug) System.out.println(name);
		File tmp=new File(name);
		 
		if (!tmp.exists())
			try {
				tmp.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		return tmp;
	}
	
	public byte[] array (ByteBuffer buffer) {
		return (byte[]) arrayObj (buffer);
	}
	
	public short[] array (ShortBuffer buffer) {
		return (short[]) arrayObj (buffer);
	}
	
	public int[] array (IntBuffer buffer) {
		return (int[]) arrayObj (buffer);
	}
	
	public long[] array (LongBuffer buffer) {
		return (long[]) arrayObj (buffer);
	}
	
	public float[] array (FloatBuffer buffer) {
		return (float[]) arrayObj (buffer);
	}
	
	public double[] array (DoubleBuffer buffer) {
		return (double[]) arrayObj (buffer);
	}
	
	public char[] array (CharBuffer buffer) {
		return (char[]) arrayObj (buffer);
	}
	
	Object arrayObj(Buffer buf) {
		
		if (buf instanceof ByteBuffer) {
			if (buf.hasArray()) return buf.array();
			else {
				ByteBuffer bb= (ByteBuffer) buf;
				byte[] ret=new byte [bb.limit()];
				bb.get(ret);
				return ret;
			} 	
		}// end if
		
		if (buf instanceof ShortBuffer) {
			if (buf.hasArray()) return buf.array();
			else {
				ShortBuffer bb= (ShortBuffer) buf;
				short[] ret=new short [bb.limit()];
				bb.get(ret);
				return ret;
			} 	
		}// end if
		
		if (buf instanceof IntBuffer) {
			if (buf.hasArray()) return buf.array();
			else {
				IntBuffer bb= (IntBuffer) buf;
				int[] ret=new int [bb.limit()];
				bb.get(ret);
				return ret;
			} 	
		}// end if
		
		
		if (buf instanceof LongBuffer) {
			if (buf.hasArray()) return buf.array();
			else {
				LongBuffer bb= (LongBuffer) buf;
				long[] ret=new long [bb.limit()];
				bb.get(ret);			
				return ret;
			} 	
		}// end if
		
		if (buf instanceof FloatBuffer) {
			if (buf.hasArray()) return buf.array();
			else {
				FloatBuffer bb= (FloatBuffer) buf;	
				float[] ret=new float [bb.limit()];
				bb.get(ret);		
				return ret;
			} 	
		}// end if
		
		if (buf instanceof DoubleBuffer) {
			if (buf.hasArray()) return buf.array();
			else {
				DoubleBuffer bb= (DoubleBuffer) buf;
				double[] ret=new double [bb.limit()];
				bb.get(ret);
				return ret;
			} 	
		}// end if
		
		if (buf instanceof CharBuffer) {
			if (buf.hasArray()) return buf.array();
			else {
				CharBuffer bb= (CharBuffer) buf;
				char[] ret=new char [bb.limit()];
				bb.get(ret);
				return ret;
			} 	
		}// end if
		return null;
	}
	/*
	 * gives the file pointer in a single file
	 */
	
	public abstract long pointerOf(long pos);
	
	/*
	 * closes the cursor
	 */
	public abstract void close() throws IOException;
	
	/*
	 * opens a file and initializes a cursor
	 */
	public abstract void open(File f, String mode) throws IOException;
	
	/*
	 * gives access to the ByteBuffer view of the underlying ByteBuffer
	 */
	public abstract ByteBuffer asByteBuffer() ;
	
	/*
	 * gives access to the ShortBuffer view of the underlying ByteBuffer
	 */
	public abstract ShortBuffer asShortBuffer();  
	
	/*
	 * gives access to the IntBuffer view of the underlying ByteBuffer
	 */
	public abstract IntBuffer asIntBuffer();  
	
	/*
	 * gives access to the FloatBuffer view of the underlying ByteBuffer
	 */
	public abstract FloatBuffer asFloatBuffer();  
	
	/*
	 * gives access to the DoubleBuffer view of the underlying ByteBuffer
	 */
	public abstract DoubleBuffer asDoubleBuffer();  
	
	/*
	 * gives access to the CharBuffer view of the underlying ByteBuffer
	 */
	public abstract CharBuffer asCharBuffer();  
	
	
	public abstract boolean hasArray();
	
	/*
	 * lists currently implemented Cursor classes
	 */
	public enum CursorTypes {
		FileCursor,
		FileMapCursor,
		MappedCursor,
		FileListCursor;
	}
}
