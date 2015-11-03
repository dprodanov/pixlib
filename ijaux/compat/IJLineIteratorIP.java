package ijaux.compat;

import ij.process.ImageProcessor;

public class IJLineIteratorIP<E> extends IJAbstractLineIterator<E> {

	private ImageProcessor ip=null;
	
	public static boolean debug=false;
	
	public IJLineIteratorIP(ImageProcessor ip, int xdir) {
		this.ip=ip;
		
		if (xdir>1) throw new IllegalArgumentException("illegal direction "+ xdir);
		dir=xdir;
		
		final int width=ip.getWidth();
		final int height=ip.getHeight();
		switch (dir) {
		case Ox: {		 
			size=height; 
			blength=width;
			break;
		}
		case Oy: {
			size=width;
			blength=height;
			break;
		}
 
	} // end 
	
	Object pix=ip.getPixels();
	setType(pix);
	
	btype=ip.getBitDepth();
	initbuffer(dir);
	//System.out.println("\nbitedpth "+ btype);
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
	
	@Override
	public void putLineInt(int[] line, int k, int xdir) {
		final int width=ip.getWidth();
		final int height=ip.getHeight();
		
		switch (xdir) {
			case Ox: {
				if (debug)
					System.out.println("puting line in Ox");
				final int lineno=height;
				int offset=k*width;
				int z=offset/(width*height);
				if (z>=0 && z<lineno) {
					Object aux=ip.getPixels();
					try {	 
						System.arraycopy(line, 0, aux, offset , width);
						//System.out.println(":"+(offset ));
					} catch (Exception e) {
						System.out.println("offset"+(offset));
						e.printStackTrace();
					}
				}
				break;
			}
			case Oy: {
				if (debug)
					System.out.println("puting line in Oy");
				final int lineno=width;				
				k=k % width;						
				if (k>=0 && k<lineno) {				 
					try {
						for (int y=0; y<height; y++) {
							ip.set(k, y, line[y]);		 
							//System.out.print( "("+ k +" " +y +"),");
						}					
					} catch (Exception e) {
						//System.out.println("k "+ k );
						e.printStackTrace();
					}
				}
				break;
			}
		}
	}
	
	@Override
	public void putLineShort(short[] line, int k, int xdir) {
		final int width=ip.getWidth();
		final int height=ip.getHeight();
		
		switch (xdir) {
			case Ox: {
				if (debug)
					System.out.println("puting line in Ox");
				final int lineno=height;
				int offset=k*width;
				int z=offset/(width*height);
				if (z>=0 && z<lineno) {
					Object aux=ip.getPixels();
					try {	 
						System.arraycopy(line, 0, aux, offset , width);
						//System.out.println(":"+(offset ));
					} catch (Exception e) {
						System.out.println("offset"+(offset));
						e.printStackTrace();
					}
				}
				break;
			}
			case Oy: {
				if (debug)
					System.out.println("puting line in Oy");
				final int lineno=width;				
				k=k % width;						
				if (k>=0 && k<lineno) {				 
					try {
						for (int y=0; y<height; y++) {
							ip.set(k, y, line[y]);		 
							//System.out.print( "("+ k +" " +y +"),");
						}					
					} catch (Exception e) {
						//System.out.println("k "+ k );
						e.printStackTrace();
					}
				}
				break;
			}
		}
	}
	
	@Override
	public void putLineByte(byte[] line, int k, int xdir) {
		final int width=ip.getWidth();
		final int height=ip.getHeight();
		
		switch (xdir) {
			case Ox: {
				if (debug)
					System.out.println("puting line in Ox");
				final int lineno=height;
				int offset=k*width;
				int z=offset/(width*height);
				if (z>=0 && z<lineno) {
					Object aux=ip.getPixels();
					try {	 
						System.arraycopy(line, 0, aux, offset , width);
						//System.out.println(":"+(offset ));
					} catch (Exception e) {
						System.out.println("offset"+(offset));
						e.printStackTrace();
					}
				}
				break;
			}
			case Oy: {
				if (debug)
					System.out.println("puting line in Oy");
				final int lineno=width;				
				k=k % width;						
				if (k>=0 && k<lineno) {				 
					try {
						for (int y=0; y<height; y++) {
							ip.set(k, y, line[y]);		 
							//System.out.print( "("+ k +" " +y +"),");
						}					
					} catch (Exception e) {
						//System.out.println("k "+ k );
						e.printStackTrace();
					}
				}
				break;
			}
		}
	}
	
	@Override
	public void putLineFloat(float[] line, int k, int xdir) {
		final int width=ip.getWidth();
		final int height=ip.getHeight();
		
		switch (xdir) {
			case Ox: {
				if (debug)
					System.out.println("puting line in Ox");
				final int lineno=height;
				int offset=k*width;
				int z=offset/(width*height);
				if (z>=0 && z<lineno) {
					Object aux=ip.getPixels();
					try {	 
						System.arraycopy(line, 0, aux, offset , width);
						//System.out.println(":"+(offset ));
					} catch (Exception e) {
						System.out.println("offset"+(offset));
						e.printStackTrace();
					}
				}
				break;
			}
			case Oy: {
				if (debug)
					System.out.println("puting line in Oy");
				final int lineno=width;				
				k=k % width;						
				if (k>=0 && k<lineno) {				 
					try {
						for (int y=0; y<height; y++) {
							ip.setf(k, y, line[y]);		 
							//System.out.print( "("+ k +" " +y +"),");
						}					
					} catch (Exception e) {
						//System.out.println("k "+ k );
						e.printStackTrace();
					}
				}
				break;
			}
		}
	}
	/*
	 *  
	 */
	@Override
	public float[] getLineFloat(int k, int dir) {
		final int width=ip.getWidth();
		final int height=ip.getHeight();
 
		final float[] ret=(float[]) xget();
		switch (dir) {
			case Ox: {
				if (debug)
					System.out.println("fetching direction Ox");
				final int lineno=height;
				int offset=k*width;
				int z=offset/(width*height);
				if (z>=0 && z<lineno) {
					Object aux=ip.getPixels();
					try {	 
						System.arraycopy(aux, offset%height, ret, 0, ret.length);
						//System.out.println(":"+offset/z);
					} catch (Exception e) {
						System.out.println("offset"+(offset % height));
						e.printStackTrace();
					}
				}
				return ret;
			}
			case Oy: {
				if (debug)
					System.out.println("fetching direction Oy");
				final int lineno=width;
				int offset=k*height;
				k=k % width;
				int z=offset/(width*height);		
				if (z>=0 && z<lineno) {				 
					try {
						float[] pixels= (float[]) ip.getPixels();
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
		}
		return null;
	}
	
	/*
	 *  
	 */
	@Override
	public short[] getLineShort(int k, int dir) {
		final int width=ip.getWidth();
		final int height=ip.getHeight();
 
		final short[] ret=(short[]) xget();
		switch (dir) {
			case Ox: {
				if (debug)
					System.out.println("fetching direction Ox");
				final int lineno=height;
				int offset=k*width;
				int z=offset/(width*height);
				if (z>=0 && z<lineno) {
					Object aux=ip.getPixels();
					try {	 
						System.arraycopy(aux , offset % height, ret, 0, ret.length);
						//System.out.println(":"+offset/z);
					} catch (Exception e) {
						System.out.println("offset"+(offset % height));
						e.printStackTrace();
					}
				}
				return ret;
			}
			case Oy: {
				if (debug)
					System.out.println("fetching direction Oy");
				final int lineno=width;
				int offset=k*height;
				k=k % width;
				int z=offset/(width*height);		
				if (z>=0 && z<lineno) {				 
					try {
						short[] pixels= (short[]) ip.getPixels();
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
		}
		return null;
	}
	
	
	/*
	 *  
	 */
	@Override
	public byte[] getLineByte(int k, int dir) {
		final int width=ip.getWidth();
		final int height=ip.getHeight();
 
		final byte[] ret=(byte[]) xget();
		switch (dir) {
			case Ox: {
				if (debug)
					System.out.println("fetching direction Ox");
				final int lineno=height;
				int offset=k*width;
				int z=offset/(width*height);
				if (z>=0 && z<lineno) {
					Object aux=ip.getPixels();
					try {	 
						System.arraycopy(aux, offset % height, ret, 0, ret.length);
						//System.out.println(":"+offset/z);
					} catch (Exception e) {
						System.out.println("offset"+(offset % height));
						e.printStackTrace();
					}
				}
				return ret;
			}
			case Oy: {
				if (debug)
					System.out.println("fetching direction Oy");
				final int lineno=width;
				int offset=k*height;
				k=k % width;
				int z=offset/(width*height);		
				if (z>=0 && z<lineno) {				 
					try {
						byte[] pixels= (byte[]) ip.getPixels();
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
		}
		return null;
	}
	
	/*
	 *  
	 */
	@Override
	public int[] getLineInt( int k, int dir) {
		final int width=ip.getWidth();
		final int height=ip.getHeight();
 
		final int[] ret=(int[]) xget();
		switch (dir) {
			case Ox: {
				if (debug)
					System.out.println("fetching direction Ox");
				final int lineno=height;
				int offset=k*width;
				int z=offset/(width*height);
				if (z>=0 && z<lineno) {
					Object aux=ip.getPixels();
					try {	 
						System.arraycopy(aux , offset % height, ret, 0, ret.length);
						//System.out.println(":"+offset/z);
					} catch (Exception e) {
						System.out.println("offset"+(offset % height));
						e.printStackTrace();
					}
				}
				return ret;
			}
			case Oy: {
				if (debug)
					System.out.println("fetching direction Oy");
				final int lineno=width;
				int offset=k*height;
				k=k % width;
				int z=offset/(width*height);		
				if (z>=0 && z<lineno) {				 
					try {
						int[] pixels= (int[]) ip.getPixels();
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
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public E next() {
		Object ret=null;
		if (btype==32)
			ret=getLineFloat(cnt, dir);
 		if (btype==16)
			ret=getLineShort(cnt, dir);
		if (btype==8)
			ret=getLineByte( cnt, dir);
		if (btype==24)
			ret=getLineInt(cnt, dir); 
		cnt++;
		return (E)ret;
	}

}
