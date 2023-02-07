package UIProject.Module.Element;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import AppsGUITransformDLProj.GUI.AndroidGUIPage;

import java.awt.image.BufferedImage;

public class Chip extends UIElement{
    
    public BufferedImage image;

    public Chip(){
        super();
    }

    public Chip(Position position,Size size,BufferedImage image){
        super(position,size);
        this.image=image;
    }


    public Chip(AndroidGUIElement age, BufferedImage image){
        super(age,image);
        this.image=image.getSubimage(position.x,position.y,size.width,size.height);
    }
}
