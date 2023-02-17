package UIProject.Module.Element;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import UIProject.util.FileHelpler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chip extends UIElement{
    
    public BufferedImage image;

    public Chip(){
        super();
    }

    public BufferedImage getImage() {
        return image;
    }

    public Chip(Position position, Size size, BufferedImage image){
        super(position,size);
        this.image=image;
    }

    public Chip(AndroidGUIElement age, BufferedImage screenshot){
        super(age,screenshot);
        this.image=screenshot.getSubimage(position.x,position.y,size.width,size.height);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void removeDomain(BufferedImage screenshot){
        BufferedImage img= FileHelpler.copyImage(screenshot);
        Graphics2D graphics=img.createGraphics();
        for(int i=0;i<children.size();i++){
            UIElement child=children.get(i);
            if(child instanceof Domain) {
                int x = child.getStartX();
                int y = child.getStartY();
                int width = child.getWidth();
                int height = child.getHeight();
                graphics.setColor(new Color(255, 255, 255, 255));
                graphics.fillRect(x, y, width, height);
            }
        }
        graphics.dispose();
        this.image = img.getSubimage(position.x,position.y,size.width,size.height);
    }
}
