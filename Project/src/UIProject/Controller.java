package UIProject;

import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import UIProject.Module.PageModule;
import UIProject.util.FileHelpler;

import java.awt.image.BufferedImage;

public class Controller {

    public static PageModule createPageModuleByXMLAndPicture(String xmlPath, String picPath, String pmPath){
        AndroidGUIPage apg=GUIPageXMLFileReader.readAndroidPageXMLFile("0.xml");
        PageModule result=new PageModule(apg,picPath);
        FileHelpler.savePageModule(result,pmPath);
        return result;
    }

    public static PageModule createPageModuleByPMDirectory(String pmPath){
        PageModule result=new PageModule(pmPath);
        return result;
    }

    public static void createPictureByPageModule(PageModule pm, String picPath){
        BufferedImage img=pm.createPicture();
        FileHelpler.saveImage(img,picPath);
    }
}
