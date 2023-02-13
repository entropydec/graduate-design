package UIProject.Module;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import UIProject.util.FileHelpler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ChipAndPic {

    public List<AndroidGUIElement> chips;

    public BufferedImage screenshot;

    public ChipAndPic(List<AndroidGUIElement> chips, BufferedImage screenshot){
        this.chips=chips;
        this.screenshot=screenshot;
    }

    public List<BufferedImage> getImageOfChips(){
        List<BufferedImage> resList=new ArrayList<BufferedImage>();
        chips.forEach(chip->{
            int x=chip.Ex1;
            int y=chip.Ey1;
            int width=chip.Ex2-x;
            int height=chip.Ey2-y;
            BufferedImage chipImage=screenshot.getSubimage(x,y,width,height);
            resList.add(chipImage);
        });
        return resList;
    }

    public void drawImage(){
        List<BufferedImage> chipImages=this.getImageOfChips();
        BufferedImage buffImg = new BufferedImage(1080, 2270, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics=buffImg.createGraphics();
        for(int i=0;i<chipImages.size();i++){
            BufferedImage chipImage=chipImages.get(i);
            AndroidGUIElement chip=chips.get(i);
            int x= chip.Ex1;
            int y= chip.Ey1;
            int width=chipImage.getWidth();
            int height=chipImage.getHeight();
            Image _image= chipImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            graphics.drawImage(_image,x,y,null);
        }
        graphics.dispose();
        try {
            OutputStream out=new FileOutputStream("data/out.png");
            ImageIO.write(buffImg, "png", out);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static public void main(String args[]){
        ChipAndPic tmp=FileHelpler.readXMLFile("data/0.xml","data/0.png");
        List<BufferedImage> list=tmp.getImageOfChips();
        FileHelpler.saveImages(list,"data/chips/");
        tmp.drawImage();
    }
}
