package UIProject.Module;

import UIProject.util.FileHelpler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class AppPMSet {

    String appName;

    HashMap<String, Scene> pmMap;

    public AppPMSet(){
        this.appName="";
        this.pmMap=null;
    }

    public AppPMSet(String name, HashMap<String, Scene> pmMap){
        this.appName=name;
        this.pmMap=new HashMap<String, Scene>(pmMap);
    }

    public AppPMSet(String name, String appPath){
        this.appName=name;
        this.pmMap=new HashMap<>();
        ArrayList<String> scenes= FileHelpler.getFileNamesOf(appPath);

        for(String scene: scenes){
            ArrayList<PageModule> pms=new ArrayList<>();

        }
    }

    public static void main(String[] args){

    }
}
