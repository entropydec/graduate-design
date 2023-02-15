package UIProject.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageHelpler {

    public static BufferedImage zoomImage(BufferedImage img, int width, int height){
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Image _img = img.getScaledInstance(width , height, Image.SCALE_DEFAULT);
        Graphics2D graphics = result.createGraphics();
        graphics.drawImage(img, 0, 0,null);
        graphics.dispose();
        return result;
    }
}
