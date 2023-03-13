package UIProject.Module;

import UIProject.util.FileHelpler;
import UIProject.Controller.PMHelpler;

import java.io.File;
import java.util.ArrayList;

public class Scene {

    public static final String XML="page.xml";
    public static final String PICTURE="page.png";

    String sceneName;

    public ArrayList<PageModule> pageModules;

    public Scene(){
        this.sceneName="";
        this.pageModules=null;
    }

    public Scene(String name, ArrayList<PageModule> pageModules){
        this.sceneName=name;
        this.pageModules=new ArrayList<>(pageModules);
    }

    public Scene(String path){
        File file=new File(path);
        this.sceneName=file.getName();

        this.pageModules=new ArrayList<>();
        ArrayList<String> orders= FileHelpler.getFileNamesOf(path);

        //TODO：
        //判断该pm文件夹是xml和picture
        //            或者是pmdir
        for(String order:orders){
            String orderPath=path+order;
            ArrayList<String> filesInOrder=FileHelpler.getFileNamesOf(orderPath);


            String xml=path+order+XML;
            String pic=path+order+PICTURE;
            PageModule pm=PMHelpler.createPageModuleByXMLAndPicture(xml,pic);
            this.pageModules.add(pm);
        }
    }

    public Scene(String name, String path){
        this(path);
        this.sceneName=name;
    }

    public String getSceneName(){
        return this.sceneName;
    }

    public ArrayList<PageModule> getPageModules() {
        return pageModules;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public void setPageModules(ArrayList<PageModule> pageModules) {
        this.pageModules = pageModules;
    }
}
