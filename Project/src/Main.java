import UIProject.Controller;
import UIProject.Module.PageModule;
import UIProject.util.FileHelpler;
import UIProject.util.Switcher;

import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void pre(){
        PageModule pm=Controller.createPageModuleByXMLAndPicture("data/input/example.xml","data/input/example.png");
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
