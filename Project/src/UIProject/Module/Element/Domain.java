package UIProject.Module.Element;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import UIProject.util.FileHelpler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Domain extends UIElement{

    public BufferedImage outerFrame;

    public Domain(){
        super();
    }

    public BufferedImage getOuterFrame() {
        return outerFrame;
    }

    public Domain(AndroidGUIElement age, BufferedImage image){
        super(age,image);
        this.outerFrame=null;
    }

    public void setOuterFrame(BufferedImage outerFrame){
        this.outerFrame=outerFrame;
    }

    public void drawFrame(BufferedImage screenshot){
        BufferedImage image= FileHelpler.copyImage(screenshot);
        this.outerFrame = image.getSubimage(position.x,position.y,size.width,size.height);
        Graphics2D graphics=outerFrame.createGraphics();
        for(int i=0;i<children.size();i++){
            UIElement child=children.get(i);
            int x= child.getStartX();
            int y= child.getStartY();
            int width=child.getWidth();
            int height=child.getHeight();
            graphics.setColor(new Color(255,255,255,255));
            graphics.fillRect(x,y,width,height);
        }
        graphics.dispose();
    }
}
