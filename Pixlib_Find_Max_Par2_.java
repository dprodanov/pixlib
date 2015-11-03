import java.awt.BasicStroke;
import java.awt.geom.GeneralPath;
import java.util.concurrent.atomic.AtomicInteger;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.Toolbar;
import ij.plugin.PlugIn;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.compat.*;
import ijaux.datatype.*;
import ijaux.datatype.access.*;
import ijaux.hypergeom.*;
import ijaux.hypergeom.index.*;
import ijaux.parallel.*;



public class Pixlib_Find_Max_Par2_     implements PlugIn {

	ConcurentProcessor<Boolean> cproc=new ConcurentProcessor<Boolean>();
	final int n_cpus=ConcurentProcessor.n_cpus;
	
	private Pair<Integer,Float>[] res=  Pair.array(n_cpus);
	private float max=-1;
	private int ind=-1;
	
	@Override
	public void run(String arg) {
		ImagePlus imp = IJ.getImage();
		long start = System.currentTimeMillis();

		// wraps the ImagePlus to a HyperCube
		ImagePlusCube ipc=new ImagePlusCube(imp);
		long time1 = System.currentTimeMillis();

		int size=Util.cumprod(imp.getDimensions());

		final Access<?> access = ipc.getAccess();

	 ////
		for (int i=0; i<n_cpus; i++) {
			 
			cproc.addJob(new ProcTask(access,0));
		}	
		cproc.call();
		////
		ImagePlusIndex bi=ipc.getIndex();
		//calculates the image coordinates
		bi.setIndex(ind);
		int[] coords=bi.getCoordinates();
		long time2=System.currentTimeMillis();
		showTime(imp, start);

		//IJ.makePoint(coords[0],coords[1]);
		 drawCross( coords, imp) ;
		//final Access<?> access=ipc.getAccess();
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

		int[] dim={500, 200, 100};
		//int ndim=dim.length;
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


		new Pixlib_Find_Max_Par2_().run(null);


	}




	private class ProcTask implements ForkJoinJob<Boolean>{

		final Access<?> accessin;
		AtomicInteger id=new AtomicInteger(-1);
		private int offset=0;
		private int limit=-1;
		private int overlap=0;

		boolean _debug=false;

		
		public ProcTask(Access<?> ac, int _overlap) { 
			this.accessin=ac;
			overlap=_overlap;

		}

	 
		public void join() {
			float _max=-1;
			int _ind=-1;
			for (Pair<Integer,Float> p:res) {

				if (p.second>_max) {
					_max=p.second;
					_ind=p.first;
				}
			}
			max=_max;
			ind=_ind;


		}


		public void fork(int _id) {
			id.set(_id);
			if (_debug)	
				System.out.println("forking "+id);

			int size=accessin.size()[0]*accessin.size()[1]; // PagedAccess
			if (size==0) // Access
				size=accessin.size()[0];
			int csize=size/n_cpus;

			//System.out.println("size "+size+" csize "+csize);
			offset=_id*csize;
			limit=offset+ csize +overlap; 
			if (_id== n_cpus-1)
				limit=size;

		}

		@Override
		public Boolean call() throws Exception {
			if (_debug)
				System.out.println("processing id "+id);

			if (_debug)
				System.out.println("offset "+offset+" limit "+limit);
			float _max=accessin.elementFloat(offset);


			int _ind=offset;

			//iterates over the image and finds the 1st global maximum

			for (int ind= offset; ind<limit; ind++ ) {
				final float c=accessin.elementFloat(ind);
				if (c>_max) {
					_max=c;
					_ind=ind ;
				}

			}


			res[id.get()]=Pair.of(_ind, _max);



			return true;
		}

	}

	/*
	private class ProcTask2 extends AccessTask {



		public ProcTask2(Access<?> accesin, int _overlap) {
			super(accesin, _overlap);

		}

		@Override
		public void join() {
			float _max=-1;
			int _ind=-1;
			for (Pair<Integer,Float> p:res) {

				if (p.second>_max) {
					_max=p.second;
					_ind=p.first;
				}
			}
			max=_max;
			ind=_ind;

		}

		@Override
		public void run() {
			if (_debug)
				System.out.println("processing id "+id);

			if (_debug)
				System.out.println("offset "+offset+" limit "+limit);
			float _max=accessin.elementFloat(offset);
			int _ind=offset;

			//iterates over the image and finds the 1st global maximum

			for (int idx=offset; idx<limit; idx++) {
				final float c=accessin.elementFloat(idx);
				if (c>_max) {
					_max=c;
					_ind=idx;
				}
			}



			res[id.get()]=Pair.of(_ind, _max);
		}

	}
	*/
}
