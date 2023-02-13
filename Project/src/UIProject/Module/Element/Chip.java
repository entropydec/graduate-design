package UIProject.Module.Element;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import AppsGUITransformDLProj.GUI.AndroidGUIPage;

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
}
