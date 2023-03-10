package UIProject.Controller;

import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import UIProject.Module.Element.Domain;
import UIProject.Module.PageModule;
import UIProject.util.FileHelpler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PMHelpler {

    //通过截图和xml布局文件构造页面模型
    //TODO:读取失败的操作
    public static PageModule createPageModuleByXMLAndPicture(String xmlPath, String picPath){
        AndroidGUIPage apg=GUIPageXMLFileReader.readAndroidPageXMLFile(xmlPath);

        BufferedImage screenshot=null;
        File pic=new File(picPath);
        try {
            screenshot= ImageIO.read(pic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PageModule result=new PageModule(apg,screenshot);
        //FileHelpler.savePageModule(result,pmPath);
        return result;
    }

    //通过外存页面模型数据构造页面模型
    public static PageModule createPageModuleByPMDirectory(String pmPath){
        PageModule result=new PageModule(pmPath);
        return result;
    }

    //通过页面模型生成并存储图片
    public static BufferedImage createPictureByPageModule(PageModule pm, String picPath){
        BufferedImage img=pm.createPicture();
        FileHelpler.saveImage(img,picPath);
        return img;
    }

    public static void main(String[] args){
        PageModule pm= PMHelpler.createPageModuleByPMDirectory("data/9/");
        for(int i=0;i<10;i++) {
            PageModule pm2 = Switcher.createOneSimilarPageModule(pm,
                    Domain.EXCHANGECHIPINDOMAIN,
                    UEHelpler.TYPEANDSIZE,
                    UEHelpler.DOMAINSIMILARITY_1,
                    UEHelpler.CHILDRENSIMILARITY_1);
            BufferedImage img = pm2.createPicture();
            FileHelpler.saveImage(img, "data/9/random/random"+i+".png");
        }
    }
}
