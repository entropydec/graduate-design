package UIProject.Module;

import UIProject.util.FileHelpler;

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

    public Scene(String name, String path){
        this.sceneName=name;

        //TODO:读取Scene的path
        ArrayList<String> orders= FileHelpler.getFileNamesOf(path);
        for(String order:orders){

        }
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
