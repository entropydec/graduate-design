package UIProject.Controller;

import UIProject.Module.Element.Domain;
import UIProject.Module.Element.UIElement;
import UIProject.Module.PageModule;

import java.util.ArrayList;

public class Switcher {
    //生成随机页面模型的最大数
    public static final int MAXGENERATEPM=10;

    //获取某个domain中的可交换UIElement对
    public static ArrayList<ArrayList<Integer>> getExchangeableIDwithUEID(PageModule pm, int id, int method, int keyType0, int keyType1, int keyType2){
        ArrayList<ArrayList<Integer>> result=new ArrayList<>();
        Domain domain=pm.getDomains().get(id);
        ArrayList<ArrayList<UIElement>> couples=new ArrayList<>();
        if(method==Domain.EXCHANGECHIPINDOMAIN) {
            couples = domain.getExchangeableChildren(method, keyType0);
        }
        else if(method==Domain.EXCHANGEDOMAININDOMAIN){
            couples = domain.getExchangeableChildren(method, keyType1, keyType2);
        }
        else if(method==Domain.CHIPANDDOMAIN){
            couples=domain.getExchangeableChildren(method,keyType0,keyType1,keyType2);
        }
        result= UEHelpler.getExchangeID(couples);
        return result;
    }

    //通过参数列表获得可交换UIElement对
    public static ArrayList<ArrayList<Integer>> getExchangeableID(PageModule pm, ArrayList<Integer> params){
        ArrayList<ArrayList<Integer>> result=new ArrayList<>();

        int method=params.get(0);
        int keytype0=Domain.EXCHANGECHIPINDOMAIN;
        int keyType1=UEHelpler.DOMAINSIMILARITY_1;
        int keyType2=UEHelpler.CHILDRENSIMILARITY_1;

        if(method==Domain.EXCHANGECHIPINDOMAIN){
            keytype0=params.get(1);
        }
        else if(method==Domain.EXCHANGEDOMAININDOMAIN){
            keyType1=params.get(1);
            keyType2=params.get(2);
        }
        else if(method==Domain.CHIPANDDOMAIN){
            keytype0=params.get(1);
            keyType1=params.get(2);
            keyType2=params.get(3);
        }

        for(Domain domain:pm.getDomains().values()){
            int id=domain.getId();
            ArrayList<ArrayList<Integer>> couples=getExchangeableIDwithUEID(pm,id,method,keytype0,keyType1,keyType2);
            result.addAll(couples);
        }
        return result;
    }

    //获取某个页面模型中的所有可交换UIElement对
    public static ArrayList<ArrayList<Integer>> getExchangeableID(PageModule pm, int method, int keytype0, int keyType1, int keyType2){
        ArrayList<ArrayList<Integer>> result=new ArrayList<>();
        for(Domain domain:pm.getDomains().values()){
            int id=domain.getId();
            ArrayList<ArrayList<Integer>> couples=getExchangeableIDwithUEID(pm,id,method,keytype0,keyType1,keyType2);
            result.addAll(couples);
        }
        return result;
    }

    //生成一个随机的页面模型并返回
    public static PageModule createOneSimilarPageModule(PageModule pm){
        PageModule result=pm.clone();
        ArrayList<ArrayList<Integer>> exchangeIDs= getExchangeableID(pm,
                Domain.CHIPANDDOMAIN,
                UEHelpler.ONLYSIZE,
                UEHelpler.DOMAINSIMILARITY_2,
                UEHelpler.CHILDRENSIMILARITY_1);
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

    //ODO:利用参数列表进行操作
    public static PageModule createOneSimilarPageModule(PageModule pm, ArrayList<Integer> params){
        PageModule result=pm.clone();
        ArrayList<ArrayList<Integer>> exchangeIDs= getExchangeableID(pm,params);
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

    //根据确定的策略返回一个页面模型
    public static PageModule createOneSimilarPageModule(PageModule pm, int method, int keyType0,int keyType1, int keyType2){
        PageModule result=pm.clone();
        ArrayList<ArrayList<Integer>> exchangeIDs= getExchangeableID(pm,method,keyType0,keyType1,keyType2);
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
    public static ArrayList<PageModule> createSimilarPageModule(PageModule pm, int method, int keyType0,int keyType1, int keyType2){
        ArrayList<PageModule> result=new ArrayList<PageModule>();
        //ArrayList<ArrayList<Integer>> exchangeIDs= getExchangeableID(pm,method,keyType0,keyType1,keyType2);
        for(int i=0;i<MAXGENERATEPM;i++){
            PageModule newPM=createOneSimilarPageModule(pm,method,keyType0,keyType1,keyType2);
            result.add(newPM);
        }
        return result;
    }

    public static void main(String[] args){

    }
}
