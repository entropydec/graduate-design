package UIProject.Module;

import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import UIProject.Module.Element.UIElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PageModule {

    public BufferedImage screenshot;

    public UIElement root;

    public PageModule(AndroidGUIPage agp, String picPath){
        File pic=new File(picPath);
        try {
            this.screenshot= ImageIO.read(pic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
