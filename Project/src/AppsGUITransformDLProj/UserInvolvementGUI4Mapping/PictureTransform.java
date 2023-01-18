package AppsGUITransformDLProj.UserInvolvementGUI4Mapping;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class PictureTransform {
	
	public static final int INT_IOS_XML2PNG_RATE = 3;
	public static final int INT_ANROID_XML2PNG_RATE = 1;
//	public static final int INT_IOS_XML2PNG_RATE = 1;
//	public static final int INT_ANROID_XML2PNG_RATE = 1;
	
	
	public static final int INT_IOS_PLATFORM = 1;
	public static final int INT_ANDROID_PLATFORM = 2;
	public static final int INT_FOCUSED_LINE_LABEL = 3;
	
	
	//PNG to Display
//	public static final double DOUBLE_IOS_TRANSFORM_RATIO = 0.5;
//	public static final double DOUBLE_ANDROID_TRANSFORM_RATIO = 0.35;
	public static final double DOUBLE_IOS_TRANSFORM_RATIO = 0.3;
	public static final double DOUBLE_ANDROID_TRANSFORM_RATIO = 0.3;
	public static final double DOUBLE_FOCUSED_LINE_LABEL_TRANSFORM_RATIO = 0.2;
	
	public static Image changePictureSize(String srcPicName, int platformType) {
		
		Image image = null;
		
		try {
			double ratio = 0;
			
			if(platformType == INT_IOS_PLATFORM) {
				ratio = DOUBLE_IOS_TRANSFORM_RATIO;
			}
			else if(platformType == INT_ANDROID_PLATFORM) {
				ratio = DOUBLE_ANDROID_TRANSFORM_RATIO;
			}
			else if(platformType == INT_FOCUSED_LINE_LABEL) {
				ratio = DOUBLE_FOCUSED_LINE_LABEL_TRANSFORM_RATIO;
			}
			else {
				System.out.println("### Error: Illegel parameter in changePictureSize ###");
				//Illegel parameter!
			}
			
			
			File file = new File(srcPicName);
			BufferedImage bufferedImage = ImageIO.read(file);
			int width = (int) ((new Integer(bufferedImage.getWidth())).doubleValue()*ratio);
			int height = (int) ((new Integer(bufferedImage.getHeight())).doubleValue()*ratio);
			
			System.out.println(bufferedImage.getWidth() + ", " + bufferedImage.getHeight());
			
			image = bufferedImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
	        image = op.filter(bufferedImage, null);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return image;
		
	}
	
	public static Image changePictureSize4CheckMultiCandidates(String srcPicName, int platformType) {
		
		Image image = null;
		
		try {
			double ratio = 0;
			
			if(platformType == INT_IOS_PLATFORM) {
				ratio = DOUBLE_IOS_TRANSFORM_RATIO;
			}
			else if(platformType == INT_ANDROID_PLATFORM) {
				ratio = DOUBLE_ANDROID_TRANSFORM_RATIO;
			}
			else if(platformType == INT_FOCUSED_LINE_LABEL) {
				ratio = DOUBLE_FOCUSED_LINE_LABEL_TRANSFORM_RATIO;
			}
			else {
				System.out.println("### Error: Illegel parameter in changePictureSize ###");
				//Illegel parameter!
			}
			
			
			File file = new File(srcPicName);
			BufferedImage bufferedImage = ImageIO.read(file);
			int width = (int) ((new Integer(bufferedImage.getWidth())).doubleValue()*ratio);
			int height = (int) ((new Integer(bufferedImage.getHeight())).doubleValue()*ratio);
			
			System.out.println(bufferedImage.getWidth() + ", " + bufferedImage.getHeight());
			
			image = bufferedImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio*0.6, ratio*0.6), null);
	        image = op.filter(bufferedImage, null);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return image;
		
	}
	
	public static List<Integer> transformGUIControlLocation(int x1, int y1, int x2, int y2, int platformType) {
		
		double ratio = 0;
		int rateXML2PNG = 0;
		
		if(platformType == INT_IOS_PLATFORM) {
			ratio = DOUBLE_IOS_TRANSFORM_RATIO;
			rateXML2PNG = INT_IOS_XML2PNG_RATE;
		}
		else if(platformType == INT_ANDROID_PLATFORM) {
			ratio = DOUBLE_ANDROID_TRANSFORM_RATIO;
			rateXML2PNG = INT_ANROID_XML2PNG_RATE;
		}
		else {
			System.out.println("### Error: Illegel parameter in transformGUIControlLocation ###");
			//Illegel parameter!
		}
		
		int x_1 = (int) ((new Integer(x1*rateXML2PNG)).doubleValue()*ratio);
		int y_1 = (int) ((new Integer(y1*rateXML2PNG)).doubleValue()*ratio);
		int x_2 = (int) ((new Integer(x2*rateXML2PNG)).doubleValue()*ratio);
		int y_2 = (int) ((new Integer(y2*rateXML2PNG)).doubleValue()*ratio);
		
		List<Integer> result = new ArrayList<Integer>();
		result.add(x_1); result.add(y_1); result.add(x_2); result.add(y_2);
		
		return result;
	}
	
	public static List<Integer> transformGUIControlLocation4CheckMultiCandidates(int x1, int y1, int x2, int y2, int platformType) {
		
		double ratio = 0;
		int rateXML2PNG = 0;
		
		if(platformType == INT_IOS_PLATFORM) {
			ratio = DOUBLE_IOS_TRANSFORM_RATIO;
			rateXML2PNG = INT_IOS_XML2PNG_RATE;
//			rateXML2PNG = 1;
		}
		else if(platformType == INT_ANDROID_PLATFORM) {
			ratio = DOUBLE_ANDROID_TRANSFORM_RATIO;
			rateXML2PNG = INT_ANROID_XML2PNG_RATE;
			rateXML2PNG = 1;
		}
		else {
			System.out.println("### Error: Illegel parameter in transformGUIControlLocation ###");
			//Illegel parameter!
		}
		
		int x_1 = (int) ((new Integer(x1*rateXML2PNG)).doubleValue()*ratio*0.6);
		int y_1 = (int) ((new Integer(y1*rateXML2PNG)).doubleValue()*ratio*0.6);
		int x_2 = (int) ((new Integer(x2*rateXML2PNG)).doubleValue()*ratio*0.6);
		int y_2 = (int) ((new Integer(y2*rateXML2PNG)).doubleValue()*ratio*0.6);
		
		List<Integer> result = new ArrayList<Integer>();
		result.add(x_1); result.add(y_1); result.add(x_2); result.add(y_2);
		
		return result;
	}
	
	
	
}
