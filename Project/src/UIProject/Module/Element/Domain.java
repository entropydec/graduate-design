package UIProject.Module.Element;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;

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

    public void drawFrame(BufferedImage screenshot){
        this.outerFrame = screenshot.getSubimage(position.x,position.y,size.width,size.height);
        Graphics2D graphics=outerFrame.createGraphics();
        for(int i=0;i<children.size();i++){
            UIElement child=children.get(i);
            int x= child.getStartX();
            int y= child.getStartY();
            int width=child.getWidth();
            int height=child.getHeight();
            graphics.setColor(new Color(255,255,255,0));
            graphics.fillRect(x,y,width,height);
        }
        graphics.dispose();
    }
}
