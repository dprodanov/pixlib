package ijaux.io;


import java.nio.ByteBuffer;
import java.nio.Buffer;
import java.nio.ShortBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.FloatBuffer;
import java.nio.DoubleBuffer;
import java.nio.CharBuffer;

public class CursorProduct {
	public byte[] array(ByteBuffer buffer) {
		return (byte[]) arrayObj(buffer);
	}

	public Object arrayObj(Buffer buf) {
		if (buf instanceof ByteBuffer) {
			if (buf.hasArray())
				return buf.array();
			else {
				ByteBuffer bb = (ByteBuffer) buf;
				byte[] ret = new byte[bb.limit()];
				bb.get(ret);
				return ret;
			}
		}
		if (buf instanceof ShortBuffer) {
			if (buf.hasArray())
				return buf.array();
			else {
				ShortBuffer bb = (ShortBuffer) buf;
				short[] ret = new short[bb.limit()];
				bb.get(ret);
				return ret;
			}
		}
		if (buf instanceof IntBuffer) {
			if (buf.hasArray())
				return buf.array();
			else {
				IntBuffer bb = (IntBuffer) buf;
				int[] ret = new int[bb.limit()];
				bb.get(ret);
				return ret;
			}
		}
		if (buf instanceof LongBuffer) {
			if (buf.hasArray())
				return buf.array();
			else {
				LongBuffer bb = (LongBuffer) buf;
				long[] ret = new long[bb.limit()];
				bb.get(ret);
				return ret;
			}
		}
		if (buf instanceof FloatBuffer) {
			if (buf.hasArray())
				return buf.array();
			else {
				FloatBuffer bb = (FloatBuffer) buf;
				float[] ret = new float[bb.limit()];
				bb.get(ret);
				return ret;
			}
		}
		if (buf instanceof DoubleBuffer) {
			if (buf.hasArray())
				return buf.array();
			else {
				DoubleBuffer bb = (DoubleBuffer) buf;
				double[] ret = new double[bb.limit()];
				bb.get(ret);
				return ret;
			}
		}
		if (buf instanceof CharBuffer) {
			if (buf.hasArray())
				return buf.array();
			else {
				CharBuffer bb = (CharBuffer) buf;
				char[] ret = new char[bb.limit()];
				bb.get(ret);
				return ret;
			}
		}
		return null;
	}

	public short[] array(ShortBuffer buffer) {
		return (short[]) arrayObj(buffer);
	}

	public int[] array(IntBuffer buffer) {
		return (int[]) arrayObj(buffer);
	}

	public long[] array(LongBuffer buffer) {
		return (long[]) arrayObj(buffer);
	}

	public float[] array(FloatBuffer buffer) {
		return (float[]) arrayObj(buffer);
	}

	public double[] array(DoubleBuffer buffer) {
		return (double[]) arrayObj(buffer);
	}

	public char[] array(CharBuffer buffer) {
		return (char[]) arrayObj(buffer);
	}
}