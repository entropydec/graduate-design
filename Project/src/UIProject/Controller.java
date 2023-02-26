package UIProject;

import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import UIProject.Module.Element.Domain;
import UIProject.Module.Element.UIElement;
import UIProject.Module.PageModule;
import UIProject.util.FileHelpler;
import UIProject.util.UEHelpler;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Controller {
    //生成随机页面模型的最大数
    public static final int MAXGENERATEPM=10;

    //通过截图和xml布局文件构造页面模型
    public static PageModule createPageModuleByXMLAndPicture(String xmlPath, String picPath){
        AndroidGUIPage apg=GUIPageXMLFileReader.readAndroidPageXMLFile("0.xml");
        PageModule result=new PageModule(apg,picPath);
        //FileHelpler.savePageModule(result,pmPath);
        return result;
    }

    //通过外存页面模型数据构造页面模型
    public static PageModule createPageModuleByPMDirectory(String pmPath){
        PageModule result=new PageModule(pmPath);
        return result;
    }

    //通过页面模型生成并存储图片
    public static BufferedImage createPictureByPageModule(PageModule pm){
        BufferedImage img=pm.createPicture();
        //FileHelpler.saveImage(img,picPath);
        return img;
    }

    //获取某个domain中的可交换UIElement对
    public static ArrayList<ArrayList<Integer>> getExchangeableIDwithUEID(PageModule pm, int id, int method, int keyType1, int keyType2){
        ArrayList<ArrayList<Integer>> result=new ArrayList<>();
        Domain domain=pm.getDomains().get(id);
        ArrayList<ArrayList<UIElement>> couples=new ArrayList<>();
        if(method==Domain.EXCHANGECHIPINDOMAIN) {
            couples = domain.getExchangeableChildren(method, keyType1);
        }
        else if(method==Domain.EXCHANGEDOMAININDOMAIN){
            couples = domain.getExchangeableChildren(method,keyType1,keyType2);
        }
        result= UEHelpler.getExchangeID(couples);
        return result;
    }

    //获取某个页面模型中的所有可交换UIElement对
    public static ArrayList<ArrayList<Integer>> getExchangeableID(PageModule pm, int method, int keyType1, int keyType2){
        ArrayList<ArrayList<Integer>> result=new ArrayList<>();
        for(Domain domain:pm.getDomains().values()){
            int id=domain.getId();
            ArrayList<ArrayList<Integer>> couples=getExchangeableIDwithUEID(pm,id,method,keyType1,keyType2);
            result.addAll(couples);
        }
        return result;
    }

    public static PageModule createOneSimilarPageModule(PageModule pm, int method, int keyType1, int keyType2){
        PageModule result=pm.clone();
        ArrayList<ArrayList<Integer>> exchangeIDs= getExchangeableID(pm,method,keyType1,keyType2);
        int count=exchangeIDs.size();
        ArrayList<Boolean> clue=UEHelpler.generateRandomList(count);
        for(int i=0;i<count;i++){
            if(clue.get(i)){
                int id1=exchangeIDs.get(i).get(0);
                int id2=exchangeIDs.get(i).get(1);
                result.switchTwoNode(id1,id2);
            }
        }
        return result;
    }

    //获取某种转换策略下转换得到的页面模型列表
    public static ArrayList<PageModule> createSimilarPageModule(PageModule pm, int method, int keyType1, int keyType2){
        ArrayList<PageModule> result=new ArrayList<PageModule>();
        ArrayList<ArrayList<Integer>> exchangeIDs= getExchangeableID(pm,method,keyType1,keyType2);

        return result;
    }

    public static void main(String[] args){
        PageModule pm=Controller.createPageModuleByPMDirectory("data/9/");
        PageModule pm2=Controller.createOneSimilarPageModule(pm,Domain.EXCHANGEDOMAININDOMAIN,UEHelpler.DOMAINSIMILARITY_2,UEHelpler.CHILDRENSIMILARITY_1);

        BufferedImage img=pm2.createPicture();
        FileHelpler.saveImage(img,"data/9/random.png");
    }
}
