/**
 * 
 */
package ijaux.hypergeom.dsp;

import ijaux.dsp.FFT;
import ijaux.dsp.SamplingWindow;
import ijaux.dsp.WindowTypes;
import ijaux.hypergeom.ComplexCube;
import ijaux.hypergeom.PixelCube;

/**
 * @author Dimiter Prodanov
 *
 */
public class FFTProcessor {
	SamplingWindow wnd=null;
 
	public FFTProcessor () {
		
	}
	
	public FFTProcessor (int n, WindowTypes type) {
		setWindow(n,type);
	}
	
	public void setWindow(int n, WindowTypes type) {
		wnd=SamplingWindow.createWindow(type, n);
	}
	
	public void window (double[] pix) {
		FFT.window(pix, wnd.getWindow());
	}
	
	public <N extends Number> ComplexCube fft(PixelCube<N,?> pc, final int sign, final int dir) {
		final ComplexCube cc=FFTD.fft(pc, +1, dir, wnd.getWindow());
		return cc;
	}
	
	public <N extends Number> PixelCube<Double,?> ifft(ComplexCube pc, final int sign, final int dir) {
		final ComplexCube cc=FFTD.fft(pc, -1, dir, wnd.getWindow());
		return cc.RealCube();
	}
	
	public <N extends Number> ComplexCube fft(PixelCube<N,?> pc, final int sign) {
		final ComplexCube cc=FFTD.fftxd(pc, +1, wnd.getWindow());
		cc.scale();
		return cc;
	}
	
	public <N extends Number> PixelCube<Double,?> ifft(ComplexCube pc, final int sign) {
		final ComplexCube cc=FFTD.fftxd(pc, -1, wnd.getWindow());
		cc.scale();
		return cc.RealCube();
	}
}
