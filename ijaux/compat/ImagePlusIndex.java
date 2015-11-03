package ijaux.compat;


import ij.ImagePlus;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.hypergeom.index.Indexing;

public class ImagePlusIndex extends BaseIndex {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6361514738049276412L;
	private ImagePlusCoords impc;

		
	public ImagePlusIndex(ImagePlus imp) {	
		super(imp.getDimensions(), true);
		impc=new ImagePlusCoords(imp);
	}

	
	@Override
	public int translate(int[] x) {
		impc.translate(x);
		return super.translate(x);
	}

	@Override
	public int translateTo(int[] x) {
		impc.translateTo(x);
		return super.translateTo(x);
	}

	public void setPositionGUI() {
		impc.setPositionGUI();
	}
	
	/*
	 * Auxiliary class
	 */
	class ImagePlusCoords{
		final int width=0, 
				   height=1,
				   channel=2, //c
				   frame=3, //t 
				   slice=4; //z
		
		int ndim=5;
		
		ImagePlus imp;
		
		public ImagePlusCoords(ImagePlus imp) {
			ndim=imp.getNDimensions();
			
			this.imp=imp;
		}
		
		
		void translateTo(int[] x) {
			// (int channel, int slice, int frame)
			imp.setPositionWithoutUpdate(x[channel]++, x[frame]++, x[slice]++);
			
		}
		
		void translate(int[]x) {
			final int aslice=imp.getCurrentSlice()-1 + x[slice];
			final int aframe=imp.getFrame()+x[frame];
			final int achannel=imp.getChannel()+ x[channel];
			imp.setPositionWithoutUpdate(achannel, aframe, aslice);
			
		}
		
		public void setPositionGUI() {
			imp.setPosition(coords[channel]++, coords[frame]++, coords[slice]++);
		}
	}



}
