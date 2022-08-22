import ij.plugin.PlugIn;
import ij.ImagePlus;
import ij.ImageStack;

import ij.IJ;
import ij.process.ImageProcessor;

public class RGBtoGray_ implements PlugIn{
	private ImagePlus image, newOutput;
	
	public void run(String arg) {
		this.image = IJ.getImage();
		if (this.image.getType() != ImagePlus.GRAY8) {
			IJ.error("The image must be Type GRAY8 in order to run this plugin");
		}
		else if (this.image.getImageStackSize() != 3){
			IJ.error("The image must be a stack of three Type GRAY8 images in order to run this plugin");
		}
		else {
			joinChannels();
		}	
	}
	
	private void joinChannels(){		
		int x, y, pixelValue[] = {0,0,0};		
		
		ImageProcessor[] processors = this.getProcessors();			
		
		for (x = 0; x < processors[0].getWidth(); x++) {
			for (y = 0; y < processors[0].getHeight(); y++) {
				pixelValue[0] = processors[0].getPixel(x, y);
				pixelValue[1] = processors[1].getPixel(x, y);
				pixelValue[2] = processors[2].getPixel(x, y);
				processors[3].putPixel(x, y, pixelValue);
			}
		}
		
		this.image.close();
		newOutput.show();		
	}
	
	private ImageProcessor[] getProcessors() {	
		ImageStack stack = image.getImageStack();		
		ImageProcessor[] processadores = new ImageProcessor[4];
		processadores[0] = stack.getProcessor(1);
		processadores[1] = stack.getProcessor(2);
		processadores[2] = stack.getProcessor(3);
		
		newOutput = IJ.createImage("RGB Joined image", "RGB", processadores[0].getWidth(), processadores[0].getHeight(), 1);
		
		processadores[3] = newOutput.getProcessor();
		return processadores;
	}

}