package UIProject;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import UIProject.Module.ChipAndPic;
import UIProject.Module.PageModule;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class FileHelpler {

    static public ChipAndPic readXMLFile(String xmlPath, String picPath){
        ChipAndPic res=null;
        AndroidGUIPage page = GUIPageXMLFileReader.readAndroidPageXMLFile(xmlPath);
        List<AndroidGUIElement> chips = AndroidGUIPage.deriveLeafSetofTree(page);
        File pic=new File(picPath);
        try {
            BufferedImage screenshot= ImageIO.read(pic);
            res=new ChipAndPic(chips,screenshot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    static public void saveChipsImage(List<BufferedImage> chipsImage,String dirPath){
        for(int count =0; count<chipsImage.size(); count++){
            String path=dirPath+count+".png";
            saveImage(chipsImage.get(count),path);
        };
    }

    static public void saveImage(BufferedImage image, String path){
        int width=image.getWidth();
        int height=image.getHeight();
        Image _image=image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics=buffImg.createGraphics();
        graphics.drawImage(_image,0,0,null);
        graphics.dispose();
        try {
            OutputStream out=new FileOutputStream(path);
            ImageIO.write(buffImg, "png", out);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}