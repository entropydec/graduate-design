package UIProject.Module.Element;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import UIProject.Module.PageModule;
import UIProject.util.FileHelpler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Domain extends UIElement{

    public static final int EXCHANGECHIPINDOMAIN=0;
    public static final int EXCHANGEDOMAININDOMAIN=1;

    public static final int TYPEANDSIZE=2;
    public static final int ONLYSIZE=3;

    //仅domain的type和size相同即可
    public static final int DOMAINSIMILARITY_1=4;
    //domain的type和size相同，孩子的数量相同
    public static final int DOMAINSIMILARITY_2=5;
    //domain的size相同，孩子数量相同，孩子的分布相似（如何界定相似？）
    public static final int DOMAINSIMILARITY_3=6;
    //domain的type和size相同，孩子数量相同，孩子的分布相似
    public static final int DOMAINSIMILARITY_4=7;

    //孩子具有同数量的Domain和Chip
    public static final int CHILDRENSIMILARITY_1=8;
    //孩子具有类似的位置分布
    public static final int CHILDRENSIMILARITY_2=9;

    public BufferedImage outerFrame;

    public Domain(){
        super();
    }

    @Override
    public Domain clone() {
        Domain clone = (Domain) super.clone();
        clone.outerFrame=FileHelpler.copyImage(outerFrame);
        return clone;
    }

    public BufferedImage getOuterFrame() {
        return outerFrame;
    }

    public Domain(AndroidGUIElement age, BufferedImage image){
        super(age,image);
        this.outerFrame=null;
    }

    public void setOuterFrame(BufferedImage outerFrame){
        this.outerFrame=outerFrame;
    }

    public void drawFrame(BufferedImage screenshot){
        BufferedImage image= FileHelpler.copyImage(screenshot);
        Graphics2D graphics=image.createGraphics();
        for(int i=0;i<children.size();i++){
            UIElement child=children.get(i);
            int x= child.getStartX();
            int y= child.getStartY();
            int width=child.getWidth();
            int height=child.getHeight();
            graphics.setColor(new Color(255,255,255,255));
            graphics.fillRect(x,y,width,height);
        }
        graphics.dispose();
        this.outerFrame = image.getSubimage(position.x,position.y,size.width,size.height);
    }

    public ArrayList<ArrayList<UIElement>> getExchangeableChildren(int method, int keyType){
        ArrayList<ArrayList<UIElement>> result= new ArrayList<>();
        if(method==EXCHANGECHIPINDOMAIN)
            result=getExchangeableChildren_1(keyType);
        else if(method==EXCHANGEDOMAININDOMAIN)
            result=getExchangeableChildren_2(keyType, CHILDRENSIMILARITY_1);
        return result;
    }

    public ArrayList<ArrayList<UIElement>> getExchangeableChildren(int method, int keyType1, int keyType2){
        ArrayList<ArrayList<UIElement>> result= new ArrayList<>();
        if(method==EXCHANGEDOMAININDOMAIN)
            result=getExchangeableChildren_2(keyType1, keyType2);
        return result;
    }

    //交换domain中的chip，直接用chip的属性构造map的key
    public ArrayList<ArrayList<UIElement>> getExchangeableChildren_1(int keyType){
        ArrayList<ArrayList<UIElement>> result=new ArrayList<>();
        for (int i=0;i<children.size()-1;i++) {
            for (int j = i + 1; j < children.size(); j++) {
                UIElement ue1 = children.get(i);
                UIElement ue2 = children.get(j);
                if(ue1 instanceof Chip
                        &&ue2 instanceof Chip) {
                    String key1 = ue1.getAttributeKey(keyType);
                    String key2 = ue2.getAttributeKey(keyType);
                    if (key1.equals(key2)
                            && ue1.isAboutSizeOf(ue2)) {
                        ArrayList<UIElement> list = new ArrayList<UIElement>();
                        list.add(ue1);
                        list.add(ue2);
                        result.add(list);
                    }
                }
            }
        }
        /*HashMap<String, ArrayList<UIElement>> map=new HashMap<String, ArrayList<UIElement>>();
        for (UIElement child : this.children) {
            if(child instanceof Chip) {
                String key = child.getExchangeKey(keyType);
                if (map.containsKey(key)) {
                    ArrayList<UIElement> list = map.get(key);
                    list.add(child);
                    map.put(key,list);
                } else {
                    ArrayList<UIElement> list = new ArrayList<UIElement>();
                    list.add(child);
                    map.put(key,list);
                }
            }
        }
        map.values().forEach(list->{
            if(list.size()>1)
                result.add(list);
        });*/
        return result;
    }

    //交换domain中的domain，需要比较domain的相似度
    public ArrayList<ArrayList<UIElement>> getExchangeableChildren_2(int keyType1, int keyType2){
        ArrayList<ArrayList<UIElement>> result=new ArrayList<>();
        for (int i=0;i<children.size()-1;i++) {
            for (int j = i + 1; j < children.size(); j++) {
                UIElement ue1 = children.get(i);
                UIElement ue2 = children.get(j);
                if(ue1 instanceof Domain
                        &&ue2 instanceof Domain) {
                    Domain domain1=(Domain) ue1;
                    Domain domain2=(Domain) ue2;
                    JSONObject key1 = domain1.getJSONKey(keyType1,keyType2);
                    JSONObject key2 = domain2.getJSONKey(keyType1,keyType2);
                    if (key1.equals(key2)
                            && ue1.isAboutSizeOf(ue2)) {
                        ArrayList<UIElement> list = new ArrayList<UIElement>();
                        list.add(ue1);
                        list.add(ue2);
                        result.add(list);
                    }
                }
            }
        }
        /*HashMap<String, ArrayList<UIElement>> map=new HashMap<String, ArrayList<UIElement>>();
        for(UIElement child : this.children) {
            if(child instanceof Domain){
                Domain domain=(Domain) child;
                JSONObject key=domain.getJSONKey(keyType1,keyType2);
                if (map.containsKey(key.toString())) {
                    ArrayList<UIElement> list = map.get(key.toString());
                    list.add(child);
                    map.put(key.toString(),list);
                } else {
                    ArrayList<UIElement> list = new ArrayList<UIElement>();
                    list.add(child);
                    map.put(key.toString(),list);
                }
            }
        }
        map.values().forEach(list->{
            if(list.size()>1)
                result.add(list);
        });*/
        return result;
    }

    public JSONObject getJSONKey(int keyType1, int keyType2) {
        JSONObject result=new JSONObject();
        if(keyType1==DOMAINSIMILARITY_1){
            result.put("type",this.type);
        }
        else if(keyType1==DOMAINSIMILARITY_2){
            result.put("type",this.type);
            result.put("number",this.children.size());
        }
        else if(keyType1==DOMAINSIMILARITY_3){
            result.put("number",this.children.size());
            result.put("children",this.getJSONKeyOfChildren(keyType2));
        }
        else if(keyType1==DOMAINSIMILARITY_4){
            result.put("type",this.type);
            result.put("number",this.children.size());
            result.put("children",this.getJSONKeyOfChildren(keyType2));
        }
        return result;
    }

    public JSONArray getJSONKeyOfChildren(int keyType){
        JSONArray list=new JSONArray();
        for(UIElement ue:this.children){
            JSONObject obj=new JSONObject();
            if(keyType==CHILDRENSIMILARITY_1){
                if(ue instanceof Domain)
                    obj.put("class","Domain");
                else if(ue instanceof Chip)
                    obj.put("class","Chip");
            }
            else if(keyType==CHILDRENSIMILARITY_2){
                //TODO:

            }
            list.put(obj);
        }
        return list;
    }

    public static void test1(){
        PageModule pm=null;
        pm=new PageModule("data/9/");
        if(pm == null)
            return;
        for(Domain domain:pm.getDomains().values()) {
            ArrayList<ArrayList<UIElement>> list = domain.getExchangeableChildren(Domain.EXCHANGEDOMAININDOMAIN, Domain.DOMAINSIMILARITY_1);
            if(list.size()>0&&domain.getId()==7) {
                int id1 = list.get(0).get(0).getId();
                int id2 = list.get(0).get(1).getId();
                pm.switchTwoNode(id1, id2);
                BufferedImage image1 = pm.createPicture();
                FileHelpler.saveImage(image1, "data/9/out_1.png");
            }
        }
    }

    public static void test2(){
        PageModule pm=null;
        pm=new PageModule("data/9/");
        if(pm == null)
            return;
        for(Domain domain:pm.getDomains().values()) {
            ArrayList<ArrayList<UIElement>> list = domain.getExchangeableChildren(Domain.EXCHANGECHIPINDOMAIN, Domain.TYPEANDSIZE);
            if(list.size()>0&&domain.getId()==9) {
                int id1 = list.get(0).get(0).getId();
                int id2 = list.get(0).get(1).getId();
                pm.switchTwoNode(id1, id2);
                BufferedImage image1 = pm.createPicture();
                FileHelpler.saveImage(image1, "data/9/out_2.png");
            }
        }
    }

    //test the copy()
    public static void test3(){
        PageModule pm=null;
        pm=new PageModule("data/9/");
        if(pm == null)
            return;
        for(Domain domain:pm.getDomains().values()) {
            ArrayList<ArrayList<UIElement>> list = domain.getExchangeableChildren(Domain.EXCHANGECHIPINDOMAIN, Domain.TYPEANDSIZE);
            if(list.size()>0&&domain.getId()==9) {
                int id1 = list.get(0).get(0).getId();
                int id2 = list.get(0).get(1).getId();
                PageModule pm2=pm.clone();
                pm2.switchTwoNode(id1, id2);
                BufferedImage image1 = pm.createPicture();
                FileHelpler.saveImage(image1, "data/9/out_31.png");

                BufferedImage image2 = pm2.createPicture();
                FileHelpler.saveImage(image2, "data/9/out_32.png");
            }
        }
    }

    public static void main(String[] args){
        test1();
        test2();
        test3();
    }
}
