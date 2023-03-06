package UIProject.util;

import UIProject.Module.Element.Chip;
import UIProject.Module.Element.Domain;
import UIProject.Module.Element.UIElement;
import org.json.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UEHelpler {
    public static final int ONLYSIZE=10;
    public static final int TYPEANDSIZE=11;

    //仅domain的type和size相同即可
    public static final int DOMAINSIMILARITY_1=12;
    //domain的type和size相同，孩子的数量相同
    public static final int DOMAINSIMILARITY_2=13;
    //domain的size相同，孩子数量相同，孩子的分布相似（如何界定相似？）
    public static final int DOMAINSIMILARITY_3=14;
    //domain的type和size相同，孩子数量相同，孩子的分布相似
    public static final int DOMAINSIMILARITY_4=15;

    //孩子具有同数量的Domain和Chip
    public static final int CHILDRENSIMILARITY_1=16;
    //孩子具有较高的相似性，分布相似，对应的孩子具有相似的属性
    public static final int CHILDRENSIMILARITY_2=17;

    public static BufferedImage zoomImage(BufferedImage img, int width, int height){
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Image _img = img.getScaledInstance(width , height, Image.SCALE_DEFAULT);
        Graphics2D graphics = result.createGraphics();
        graphics.drawImage(img, 0, 0,null);
        graphics.dispose();
        return result;
    }

    public static ArrayList<ArrayList<Integer>> getExchangeID(ArrayList<ArrayList<UIElement>> couples){
        ArrayList<ArrayList<Integer>> result=new ArrayList<ArrayList<Integer>>();
        for(ArrayList<UIElement> couple:couples){
            ArrayList<Integer> list=new ArrayList<Integer>();
            int id1=couple.get(0).getId();
            int id2=couple.get(1).getId();
            list.add(id1);
            list.add(id2);
            result.add(list);
        }
        return result;
    }

    public static boolean compareChips(Chip chip1, Chip chip2, int keyType){
        if(keyType<ONLYSIZE
            ||keyType>TYPEANDSIZE)
            return false;
        String key1 = chip1.getAttributeKey(keyType);
        String key2 = chip2.getAttributeKey(keyType);
        if (key1.equals(key2)
                && chip1.isAboutSizeOf(chip2)) {
            return true;
        }
        return false;
    }

    public static boolean compareDomains(Domain domain1, Domain domain2, int keyType1, int keyType2){
        if(keyType1<DOMAINSIMILARITY_1
            ||keyType1>DOMAINSIMILARITY_4
            ||keyType2<CHILDRENSIMILARITY_1
            ||keyType2>CHILDRENSIMILARITY_2)
            return false;
        if(keyType2<CHILDRENSIMILARITY_2) {
            JSONObject obj1 = domain1.getJSONKey(keyType1, keyType2);
            JSONObject obj2 = domain2.getJSONKey(keyType1, keyType2);
            if (obj1.toString().equals(obj2.toString()))
                return true;
        }
        else if(keyType1>=DOMAINSIMILARITY_3){
            //比较孩子的分布
            ArrayList<UIElement> children1=domain1.getChildren();
            ArrayList<UIElement> children2=domain2.getChildren();
            for(int i=0;i<children1.size();i++){
                UIElement child1=children1.get(i);
                UIElement child2=children2.get(i);
                String class1=child1.getAttributeKey(UIElement.CLASS);
                String class2=child2.getAttributeKey(UIElement.CLASS);
                if(class1.equals(class2)
                    &&child1.isAboutSizeOf(child2))
                    continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static ArrayList<Boolean> generateRandomList(int n){
        ArrayList<Boolean> result=new ArrayList<>();
        double random_d = Math.random() * Math.pow(2, n);
        int random=(int) random_d;
        for(int i=0;i<n;i++){
            if(random%2==1)
                result.add(true);
            else
                result.add(false);
            random=random/2;
        }
        return result;
    }
}
