package UIProject.Module;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import UIProject.FileHelpler;
import UIProject.Module.Element.Chip;
import UIProject.Module.Element.Domain;
import UIProject.Module.Element.UIElement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        this.root=new UIElement(agp.elementRoot, screenshot);
    }

    public BufferedImage createPicture(){
        BufferedImage buffImg = new BufferedImage(1080, 2270, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics=buffImg.createGraphics();
        drawImage(root,graphics);
        graphics.dispose();
        return buffImg;
    }

    public void drawImage(UIElement ue, Graphics2D graphics){
        int x = ue.getStartX();
        int y = ue.getStartY();
        int width = ue.getWidth();
        int height = ue.getHeight();
        if (ue instanceof Chip) {
            Chip chip = (Chip) ue;
            BufferedImage image = chip.getImage();
            Image _image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            graphics.drawImage(_image, x, y, null);
        }
        else if(ue instanceof Domain){
            Domain chip = (Domain) ue;
            BufferedImage image = chip.getOuterFrame();
            Image _image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            graphics.drawImage(_image, x, y, null);
        }
        for(int i=0;i<ue.getChildren().size();i++) {
            drawImage(ue.getChildren().get(i), graphics);
        }
    }

    public static void main(String[] args) {
        AndroidGUIPage page = GUIPageXMLFileReader.readAndroidPageXMLFile("data/0.xml");
        PageModule pm = new PageModule(page, "data/0.png");
        BufferedImage image=pm.createPicture();
        FileHelpler.saveImage(image,"data/test.png");
    }
}
