package ijaux.compat;

import ij.ImageStack;
import ij.process.ImageProcessor;

import java.util.Iterator;

public class IJLineIteratorStack<E> extends IJAbstractLineIterator<E> {
 
	private ImageStack stack=null;
 
	public static boolean debug=false;
	
	public IJLineIteratorStack(ImageStack is, int xdir) {
		stack=is;
		dir=xdir;
		final int width=is.getWidth();
		final int height=is.getHeight();
		final int depth=is.getSize();
		
		if (depth==0)
			throw new IllegalArgumentException("empty stack");

		switch (dir) {
			case Ox: {		 
				size=height*depth; 
				blength=width;
				break;
			}
			case Oy: {
				size=width*depth;
				blength=height;
				break;
			}
			case Oz: {
				size=width*height;
				blength=depth;
				break;
			}
		} // end 
		
		Object pix=is.getPixels(1);
		// workaround ImageJ feature
		setType(pix);
		
		initbuffer(dir);
		if (debug)
			System.out.println("\nbitedpth "+ btype);
			
	}

	@Override
	public void putLineByte(byte[] line, int k, int dir) {
		final int width=stack.getWidth();
		final int height=stack.getHeight();
		final int depth=stack.getSize();
 
		switch (dir) {
			case Ox: {
				if (debug)
					System.out.println("puting line in Ox");
				final int lineno=height*depth;
				int offset=k*width;
	 
				int z=offset/(width*height);
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z +" offset : "+ offset);
				offset=(k % height)*width;
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();
					try {
						if (aux[z]!=null)
							System.arraycopy(line, 0, aux[z], offset  , width);
					 
						//System.out.println(":"+offset/z);
					} catch (Exception e) {
						System.out.println("offset"+(offset ));
						e.printStackTrace();
					}
				}
				break;
			}
			case Oy: {
				if (debug)
					System.out.println("puting line in  Oy");
				final int lineno=width*depth;
				int offset=k*height;
				k=k % width;
				int z=offset/(width*height);
				
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z);				
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();	
					try {
						byte[] pixels= (byte[])aux[z];
						if (pixels!=null)
							for (int y=0; y<height; y++) {
								pixels[k+y*width]=line[y];	
								//System.out.print( "("+ k +" " +y +"),");
							}					
					} catch (Exception e) {
						//System.out.println("k "+ k );
						e.printStackTrace();
					}
				}
				break;
			}
			case Oz:{
				if (debug)
					System.out.println("puting line in Oz");
				final int lineno=width*height;
				//System.out.println("max lines "+lineno);
				
				if (k>=0 && k<lineno) {
					Object[] aux=stack.getImageArray();					 
					for (int z=0; z<depth; z++) {
						try {
							byte[] pixels= (byte[])aux[z];
							if (pixels!=null)
								pixels[k]=line[z];
							//System.out.print( "("+ k +" "+ z +"),");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				break;
			}
		}
		
	}
	
	@Override
	public void putLineShort(short[] line, int k, int dir) {
		final int width=stack.getWidth();
		final int height=stack.getHeight();
		final int depth=stack.getSize();
 
		switch (dir) {
			case Ox: {
				if (debug)
					System.out.println("puting line in Ox");
				final int lineno=height*depth;
				int offset=k*width;
	 
				int z=offset/(width*height);
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z +" offset : "+ offset);
				offset=(k % height)*width;
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();
					try {
						if (aux[z]!=null)
							System.arraycopy(line, 0, aux[z], offset  , width);
					 
						//System.out.println(":"+offset/z);
					} catch (Exception e) {
						System.out.println("offset"+(offset ));
						e.printStackTrace();
					}
				}
				break;
			}
			case Oy: {
				if (debug)
					System.out.println("puting line in  Oy");
				final int lineno=width*depth;
				int offset=k*height;
				k=k % width;
				int z=offset/(width*height);
				
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z);				
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();	
					try {
						short[] pixels= (short[])aux[z];
						if (pixels!=null)
							for (int y=0; y<height; y++) {
								pixels[k+y*width]=line[y];	
								//System.out.print( "("+ k +" " +y +"),");
							}					
					} catch (Exception e) {
						//System.out.println("k "+ k );
						e.printStackTrace();
					}
				}
				break;
			}
			case Oz:{
				if (debug)
					System.out.println("puting line in Oz");
				final int lineno=width*height;
				//System.out.println("max lines "+lineno);
				
				if (k>=0 && k<lineno) {
					Object[] aux=stack.getImageArray();					 
					for (int z=0; z<depth; z++) {
						try {
							short[] pixels= (short[])aux[z];
							if (pixels!=null)
								pixels[k]=line[z];
							//System.out.print( "("+ k +" "+ z +"),");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				break;
			}
		}
	}
	
	@Override
	public void putLineInt( int[] line, int k, int dir) {
		final int width=stack.getWidth();
		final int height=stack.getHeight();
		final int depth=stack.getSize();
 
		switch (dir) {
			case Ox: {
				if (debug)
					System.out.println("puting line in Ox");
				final int lineno=height*depth;
				int offset=k*width;
	 
				int z=offset/(width*height);
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z +" offset : "+ offset);
				offset=(k % height)*width;
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();
					try {
						if (aux[z]!=null)
							System.arraycopy(line, 0, aux[z], offset  , width);
					 
						//System.out.println(":"+offset/z);
					} catch (Exception e) {
						System.out.println("offset"+(offset ));
						e.printStackTrace();
					}
				}
				break;
			}
			case Oy: {
				if (debug)
					System.out.println("puting line in  Oy");
				final int lineno=width*depth;
				int offset=k*height;
				k=k % width;
				int z=offset/(width*height);
				
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z);				
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();	
					try {
						int[] pixels= (int[])aux[z];
						if (pixels!=null)
							for (int y=0; y<height; y++) {
								pixels[k+y*width]=line[y];	
								//System.out.print( "("+ k +" " +y +"),");
							}					
					} catch (Exception e) {
						//System.out.println("k "+ k );
						e.printStackTrace();
					}
				}
				break;
			}
			case Oz:{
				if (debug)
					System.out.println("puting line in Oz");
				final int lineno=width*height;
				//System.out.println("max lines "+lineno);
				
				if (k>=0 && k<lineno) {
					Object[] aux=stack.getImageArray();					 
					for (int z=0; z<depth; z++) {
						try {
							int[] pixels= (int[])aux[z];
							if (pixels!=null)
								pixels[k]=line[z];
							//System.out.print( "("+ k +" "+ z +"),");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				break;
			}
		}
	}
	
	@Override
	public void putLineFloat(float[] line, int k, int dir) {
		final int width=stack.getWidth();
		final int height=stack.getHeight();
		final int depth=stack.getSize();
 
		switch (dir) {
			case Ox: {
				if (debug)
					System.out.println("puting line in Ox");
				final int lineno=height*depth;
				int offset=k*width;
	 
				int z=offset/(width*height);
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z +" offset : "+ offset);
				offset=(k % height)*width;
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();
					try {
						if (aux[z]!=null)
							System.arraycopy(line, 0, aux[z], offset  , width);
					 
						//System.out.println(":"+offset/z);
					} catch (Exception e) {
						System.out.println("offset"+(offset ));
						e.printStackTrace();
					}
				}
				break;
			}
			case Oy: {
				if (debug)
					System.out.println("puting line in  Oy");
				final int lineno=width*depth;
				int offset=k*height;
				k=k % width;
				int z=offset/(width*height);
				
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z);				
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();	
					try {
						float[] pixels= (float[])aux[z];
						if (pixels!=null)
							for (int y=0; y<height; y++) {
								pixels[k+y*width]=line[y];	
								//System.out.print( "("+ k +" " +y +"),");
							}					
					} catch (Exception e) {
						//System.out.println("k "+ k );
						e.printStackTrace();
					}
				}
				break;
			}
			case Oz:{
				if (debug)
					System.out.println("puting line in Oz");
				final int lineno=width*height;
				//System.out.println("max lines "+lineno);
				
				if (k>=0 && k<lineno) {
					Object[] aux=stack.getImageArray();					 
					for (int z=0; z<depth; z++) {
						try {
							float[] pixels= (float[])aux[z];
							if (pixels!=null)
								pixels[k]=line[z];
							//System.out.print( "("+ k +" "+ z +"),");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				break;
			}
		}
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public E next() {
		Object ret=null;
		if (btype==32)
			ret=getLineFloat( cnt, dir);
		if (btype==16)
			ret=getLineShort( cnt, dir);
		if (btype==8)
			ret=getLineByte( cnt, dir);
		if (btype==24)
			ret=getLineInt( cnt, dir);
		cnt++;
		return (E) ret;
	}

	public void putLine(E line, int k, int dir) {
		if (btype==32)
			putLineFloat((float[])line, k, dir);
		if (btype==16)
			putLineShort((short[]) line, k, dir);
		if (btype==8)
			putLineByte((byte[])line, k, dir);
		if (btype==24)
			putLineInt((int[]) line, k, dir);
	}
	
	/*
	 *  
	 */
	@Override
	public float[] getLineFloat(int k, int dir) {
		final int width=stack.getWidth();
		final int height=stack.getHeight();
		final int depth=stack.getSize();
		final float[] ret=(float[]) xget();
		switch (dir) {
			case Ox: {
				if (debug)
					System.out.println("fetching direction Ox");
				final int lineno=height*depth;
				int offset=k*width;
				//float[] ret=new float[width];
				int z=offset/(width*height);
				offset=(k % height)*width;
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z);
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();
					try {
						if (aux[z]!=null)
						System.arraycopy(aux[z], offset  , ret, 0, ret.length);
						//System.out.println(":"+offset);
					} catch (Exception e) {
						System.out.println("offset"+(offset ));
						e.printStackTrace();
					}
				}
				return ret;
			}
			case Oy: {
				if (debug)
					System.out.println("fetching direction Oy");
				final int lineno=width*depth;
				int offset=k*height;
				k=k % width;
				//float[] ret=new float[height];
				int z=offset/(width*height);
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z);				
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();	
					try {
						float[] pixels= (float[])aux[z];
						if (pixels!=null)
							for (int y=0; y<height; y++) {
								ret[y]=pixels[k+y*width];					 
								//System.out.print( "("+ k +" " +y +"),");
							}					
					} catch (Exception e) {
						//System.out.println("k "+ k );
						e.printStackTrace();
					}
				}
				return ret;
			}
			case Oz:{
				if (debug)
					System.out.println("fetching direction Oz");
				final int lineno=width*height;
				//System.out.println("max lines "+lineno);
				//float[] ret=new float[depth];
				
				if (k>=0 && k<lineno) {
					Object[] aux=stack.getImageArray();					 
					for (int z=0; z<depth; z++) {
						try {
							float[] pixels= (float[])aux[z];
							if (pixels!=null)
								ret[z]=pixels[k];
							//System.out.print( "("+ k +" "+ z +"),");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				return ret;
			}
		}
		return null;
	}
	
	/*
	 *  
	 */
	public short[] getLineShort(int k, int dir) {
		final int width=stack.getWidth();
		final int height=stack.getHeight();
		final int depth=stack.getSize();
		short[] ret=(short[]) xget();
		switch (dir) {
			case Ox: {
				if (debug)
					System.out.println("fetching direction Ox");
				final int lineno=height*depth;
				int offset=k*width;
				//short[] ret=new short[width];
				int z=offset/(width*height);
				offset=(k % height)*width;
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z);
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();
					try {
						if (aux[z]!=null)
						System.arraycopy(aux[z], offset , ret, 0, ret.length);
						//System.out.println(":"+offset/z);
					} catch (Exception e) {
						System.out.println("offset"+(offset ));
						e.printStackTrace();
					}
				}
				return ret;
			}
			case Oy: {
				if (debug)
					System.out.println("fetching direction Oy");
				final int lineno=width*depth;
				int offset=k*height;
				k=k % width;
				//short[] ret=new short[height];
				int z=offset/(width*height);
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z);				
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();	
					try {
						short[] pixels= (short[])aux[z];
						if (pixels!=null)
							for (int y=0; y<height; y++) {
								ret[y]=pixels[k+y*width];					 
								//System.out.print( "("+ k +" " +y +"),");
							}					
					} catch (Exception e) {
						//System.out.println("k "+ k );
						e.printStackTrace();
					}
				}
				return ret;
			}
			case Oz:{
				if (debug)
					System.out.println("fetching direction Oz");
				final int lineno=width*height;
				//System.out.println("max lines "+lineno);
				//short[] ret=new short[depth];
				
				if (k>=0 && k<lineno) {
					Object[] aux=stack.getImageArray();					 
					for (int z=0; z<depth; z++) {
						try {
							short[] pixels= (short[])aux[z];
							if (pixels!=null)
								ret[z]=pixels[k];
							//System.out.print( "("+ k +" "+ z +"),");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				return ret;
			}
		}
		return null;
	}
	
	/*
	 *  
	 */
	@Override
	public byte[] getLineByte( int k, int dir) {
		final int width=stack.getWidth();
		final int height=stack.getHeight();
		final int depth=stack.getSize();
		byte[] ret=(byte[]) xget();
		switch (dir) {
			case Ox: {
				if (debug)
					System.out.println("fetching direction Ox");
				final int lineno=height*depth;
				int offset=k*width;
				//byte[] ret=new byte[width];
				int z=offset/(width*height);
				offset=(k % height)*width;
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z);
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();
					try {
						if (aux[z]!=null)
						System.arraycopy(aux[z], offset , ret, 0, ret.length);
						//System.out.println(":"+offset/z);
					} catch (Exception e) {
						System.out.println("offset"+(offset));
						e.printStackTrace();
					}
				}
				return ret;
			}
			case Oy: {
				if (debug)
					System.out.println("fetching direction Oy");
				final int lineno=width*depth;
				int offset=k*height;
				k=k % width;
				//byte[] ret=new byte[height];
				int z=offset/(width*height);
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z);				
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();	
					try {
						byte[] pixels= (byte[])aux[z];
						if (pixels!=null)
							for (int y=0; y<height; y++) {
								ret[y]=pixels[k+y*width];					 
								//System.out.print( "("+ k +" " +y +"),");
							}					
					} catch (Exception e) {
						//System.out.println("k "+ k );
						e.printStackTrace();
					}
				}
				return ret;
			}
			case Oz:{
				if (debug)
					System.out.println("fetching direction Oz");
				final int lineno=width*height;
				//System.out.println("max lines "+lineno);
				//byte[] ret=new byte[depth];
				
				if (k>=0 && k<lineno) {
					Object[] aux=stack.getImageArray();					 
					for (int z=0; z<depth; z++) {
						try {
							byte[] pixels= (byte[])aux[z];
							if (pixels!=null)
								ret[z]=pixels[k];
							//System.out.print( "("+ k +" "+ z +"),");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				return ret;
			}
		}
		return null;
	}
	
	/*
	 *  
	 */
	public int[] getLineInt( int k, int dir) {
		final int width=stack.getWidth();
		final int height=stack.getHeight();
		final int depth=stack.getSize();
		int[] ret=(int[]) xget();
		switch (dir) {
			case Ox: {
				if (debug)
					System.out.println("fetching direction Ox");
				final int lineno=height*depth;
				int offset=k*width;
				//int[] ret=new int[width];
				int z=offset/(width*height);
				offset=(k % height)*width;
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z);
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();
					try {
						if (aux[z]!=null)
						System.arraycopy(aux[z], offset , ret, 0, ret.length);
						//System.out.println(":"+offset/z);
					} catch (Exception e) {
						System.out.println("offset"+(offset ));
						e.printStackTrace();
					}
				}
				return ret;
			}
			case Oy: {
				if (debug)
					System.out.println("fetching direction Oy");
				final int lineno=width*depth;
				int offset=k*height;
				k=k % width;
				//int[] ret=new int[height];
				int z=offset/(width*height);
				//System.out.println("max lines "+lineno);
				//System.out.println("z :"+z);				
				if (z>=0 && z<lineno) {
					Object[] aux=stack.getImageArray();	
					try {
						int[] pixels= (int[])aux[z];
						if (pixels!=null)
							for (int y=0; y<height; y++) {
								ret[y]=pixels[k+y*width];					 
								//System.out.print( "("+ k +" " +y +"),");
							}					
					} catch (Exception e) {
						//System.out.println("k "+ k );
						e.printStackTrace();
					}
				}
				return ret;
			}
			case Oz:{
				if (debug)
					System.out.println("fetching direction Oz");
				final int lineno=width*height;
				//System.out.println("max lines "+lineno);
				//int[] ret=new int[depth];
				
				if (k>=0 && k<lineno) {
					Object[] aux=stack.getImageArray();					 
					for (int z=0; z<depth; z++) {
						try {
							int[] pixels= (int[])aux[z];
							if (pixels!=null)
								ret[z]=pixels[k];
							//System.out.print( "("+ k +" "+ z +"),");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				return ret;
			}
		}
		return null;
	}
	
} // end class
