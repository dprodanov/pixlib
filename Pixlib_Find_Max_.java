import java.awt.BasicStroke;
import java.awt.geom.GeneralPath;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.Toolbar;
import ij.plugin.PlugIn;
import ij.process.ByteProcessor;
import ijaux.*;
import ijaux.compat.ImagePlusAccess;
import ijaux.compat.ImagePlusCube;
import ijaux.compat.ImagePlusIndex;
import ijaux.datatype.Pair;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.datatype.access.*;
import ijaux.hypergeom.*;
import ijaux.hypergeom.index.*;
import ijaux.iter.IndexedIterator;
 
public class Pixlib_Find_Max_ implements PlugIn {
	//private PixLib plib=new PixLib();
 /*
	public void run(String arg) {
			
		ImagePlus imp = IJ.getImage();
		long start = System.currentTimeMillis();
		
		// wraps the ImagePlus to a HyperCube
		ImagePlusCube ipc=new ImagePlusCube(imp);
		long time1 = System.currentTimeMillis();
		
		int size=Util.cumprod(imp.getDimensions());
		
		int idx= 0;
		Access<?> access=ipc.getAccess();
		int ind=idx;
		
		idx = optimize(size, access);
		
		ind = work(ipc, size, idx, access, ind);
		
		ImagePlusIndex bi=ipc.getIndex();
		//calculates the image coordinates
		bi.setIndex(ind);
		int[] coords=bi.getCoordinates();
		long time2=System.currentTimeMillis();
		IJ.showTime(imp, start, "", imp.getStackSize());
	 
		//IJ.makePoint(coords[0],coords[1]);
		drawCross(  coords,   imp);
		IJ.log("Maximum of "+  access.elementFloat(ind)  +" found at "+bi);
		long duration=time2-start;
 
		System.out.println("run time: "+ duration+" ms\n speed " + ((float)size)/duration +" pix/ms \n" +
				" loop run " +(time2-time1)+" ms loop speed " + ((float)size)/(time2-time1));
		
		IJ.log("run time: "+ duration+" ms\n speed " + ((float)size)/duration +" pix/ms \n" +
				" loop run " +(time2-time1)+" ms loop speed " + ((float)size)/(time2-time1));

	}

	 */
	 
	public void run(String arg) {
		
		ImagePlus imp = IJ.getImage();
		long start = System.currentTimeMillis();
		
		// wraps the ImagePlus to a HyperCube
		ImagePlusCube ipc=new ImagePlusCube(imp);
		long time1 = System.currentTimeMillis();
		
		int size=Util.cumprod(imp.getDimensions());
		
		int idx= 0;
		//Access<?> access=ipc.getAccess();
		Access<?> access=ImagePlusAccess.get(imp);//ipc.getAccess();
	
		float max=access.elementFloat(idx);
		int ind=idx;
		
		//iterates over the image and finds the 1st global maximum
		for (idx=0; idx<size; idx++) {
			final float c=access.elementFloat(idx);
			if (c>max) {
				max=c;
				ind=idx;
			}
		}
		
		ImagePlusIndex bi=ipc.getIndex();
		//calculates the image coordinates
		bi.setIndex(ind);
		int[] coords=bi.getCoordinates();
		long time2=System.currentTimeMillis();
		showTime(imp, start);
	 
		IJ.makePoint(coords[0],coords[1]);
		 
		IJ.log("Maximum of "+  access.elementFloat(ind)  +" found at "+bi);
		long duration=time2-start;
 
		System.out.println("run time: "+ duration+" ms\n speed " + ((float)size)/duration +" pix/ms \n" +
				" loop run " +(time2-time1)+" ms loop speed " + ((float)size)/(time2-time1));
		
		IJ.log("run time: "+ duration+" ms\n speed " + ((float)size)/duration +" pix/ms \n" +
				" loop run " +(time2-time1)+" ms loop speed " + ((float)size)/(time2-time1));


	}

	   void showTime(ImagePlus imp, long start) {
	      int images = imp.getStackSize();
	      double time = (System.currentTimeMillis()-start)/1000.0;
	      IJ.showTime(imp, start, "", images);
	      double mpixels = (double)(imp.getWidth())*imp.getHeight()*images/1000000;
	      IJ.log("\n"+imp);
	      IJ.log(IJ.d2s(mpixels/time,1)+" million pixels/second");
	   }

 


	void drawCross(int[] coords, ImagePlus imp) {
		int centerx=coords[0];
		int centery=coords[1];
		ImageCanvas canvas = imp.getCanvas();
		if (canvas==null) return;
		else {
			GeneralPath path = new GeneralPath();
			final float arm  = 5;
			float x = (centerx);
			float y = (centery);
			path.moveTo(x-arm, y);
			path.lineTo(x+arm, y);
			path.moveTo(x, y-arm);
			path.lineTo(x, y+arm);
			canvas.setDisplayList(path,Toolbar.getForegroundColor(), new BasicStroke(2));

		}
			
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ImageJ();
		PixLib plib=new PixLib();
		
		int[] dim={6000, 6000};
		int ndim=dim.length;
		final int size=Util.cumprod(dim);
	 
		byte[] pixels_byte=   Util.randByte(size);
		PixelCube<Byte,BaseIndex> cube=new PixelCube<Byte,BaseIndex>(dim, pixels_byte,  byte.class, BaseIndex.class);
		
		ImagePlus imp4;
		try {
			imp4 = plib.imageFrom("test byte",cube);
			 imp4.show();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		new Pixlib_Find_Max_().run(null);
		

	}


 }