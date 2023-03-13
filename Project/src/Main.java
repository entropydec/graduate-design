import UIProject.Module.PageModule;
import UIProject.util.FileHelpler;
import UIProject.Controller.PMHelpler;
import UIProject.Controller.Switcher;

import java.awt.image.BufferedImage;

public class Main {

    public static void pre(){
        PageModule pm= PMHelpler.createPageModuleByXMLAndPicture("data/input/example.xml","data/input/example.png");
        pm.printTree();
        FileHelpler.savePageModule(pm,"data/output/");

        BufferedImage img=pm.createPicture();
        FileHelpler.saveImage(img,"data/output/out.png");

        PageModule pm2= Switcher.createOneSimilarPageModule(pm);
        BufferedImage img2=pm2.createPicture();
        FileHelpler.saveImage(img2,"data/output/random.png");
    }

    public static void main(String[] args){
        pre();
    }
}
