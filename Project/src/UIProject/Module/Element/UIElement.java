package UIProject.Module.Element;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import UIProject.util.FileHelpler;
import UIProject.util.JSONHelpler;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class Position{

    public int x;

    public int y;

    public Position(){
        this.x=0;
        this.y=0;
    }

    public Position(int x,int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class Size{

    public int width;

    public int height;

    public Size(){
        this.width=0;
        this.height=0;
    }

    public Size(int w,int h){
        this.width=w;
        this.height=h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

public class UIElement {

    public static final String LINEARLAYOUT="android.widget.LinearLayout";
    public static final String FRAMELAYOUT="android.widget.FrameLayout";
    public static final String RELATIVELAYOUT="android.widget.RelativeLayout";
    public static final String VIEWGROUP="android.view.ViewGroup";

    public static final String IMAGEVIEW="android.widget.ImageView";
    public static final String TEXTVIEW="android.widget.TextView";
    public static final String EDITTEXT="android.widget.EditText";
    public static final String VIEW="android.view.View";

    public int id;

    public String type;

    public int depth;

    public Position position;

    public Size size;

    public String description;

    public ArrayList<UIElement> children;

    public HashSet<String> types;

    public UIElement(Position position,Size size){
        this.position=position;
        this.size=size;
        this.children=new ArrayList<>();
        this.types=new HashSet<>();
    }

    public UIElement(AndroidGUIElement age, BufferedImage image){
        this.position=new Position(age.Ex1, age.Ey1);
        this.size=new Size(age.Ex2-age.Ex1,age.Ey2-age.Ey1);
        this.type=age.EHead;
        this.id=age.lineNumber;
        this.children=new ArrayList<UIElement>();
        this.types=new HashSet<>();
        int count=age.sibilings.size();
        if(count!=0){
            createChildren(age, image);
        }
    }

    public UIElement(){
        this(new Position(),new Size());
    }

    public int getStartX(){
        return position.x;
    }

    public int getStartY(){
        return position.y;
    }

    public int getWidth() {
        return size.width;
    }

    public int getHeight() {
        return size.height;
    }

    public int getId() {
        return id;
    }

    public int getDepth() {
        return depth;
    }

    public String getType(){
        return type;
    }

    public HashSet<String> getTypes(){
        return types;
    }

    public void setDepth(int depth){
        this.depth=depth;
    }

    public void setId(int id){
        this.id=id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setX(int x){
        int oldX=this.position.x;
        this.position.x=x;
        for(int i=0;i<children.size();i++){
            UIElement ue=children.get(i);
            ue.setX(ue.getStartX()+x-oldX);
        }
    }

    public void setY(int y){
        int oldY=this.position.y;
        this.position.y=y;
        for(int i=0;i<children.size();i++){
            UIElement ue=children.get(i);
            ue.setY(ue.getStartY()+y-oldY);
        }
    }

    public void setWidth(int width){
        this.size.width=width;
    }

    public void setHeight(int height){
        this.size.height=height;
    }

    public List<UIElement> getChildren() {
        return children;
    }

    public boolean isCoincide(AndroidGUIElement age1,AndroidGUIElement age2){
        return age1.Ex1==age2.Ex1
                &&age1.Ex2==age2.Ex2
                &&age1.Ey1==age2.Ey1
                &&age1.Ey2==age2.Ey2;
    }

    public void createChildren(AndroidGUIElement age, BufferedImage image){
        if(age.sibilings.size()==1&&isCoincide(age,age.sibilings.get(0))){
            createChildren(age.sibilings.get(0),image);
        }
        else {
            for (int i = 0; i < age.sibilings.size(); i++) {
                if (!isChip(age.sibilings.get(i).EHead)) {
                    Domain domain = new Domain(age.sibilings.get(i), image);
                    domain.drawFrame(image);
                    this.children.add(domain);
                } else {
                    Chip chip = new Chip(age.sibilings.get(i), image);
                    chip.removeDomain(image);
                    this.children.add(chip);
                }
            }
        }
    }

    public boolean isFit(BufferedImage img){
        int width= img.getWidth();
        int height=img.getHeight();
        if(this.getWidth()==width
                &&this.getHeight()==height)
            return true;
        return false;
    }

    public static boolean isChip(String head){
        if(head.equals(UIElement.LINEARLAYOUT)
                ||head.equals(UIElement.FRAMELAYOUT)
                ||head.equals(UIElement.RELATIVELAYOUT)
                ||head.equals(UIElement.VIEWGROUP))
            return false;
        else
            return true;
    }

    public String getExchangeKey(int keyType){
        String result= "";
        if(keyType==Domain.TYPEANDSIZE)
            result=this.getType();
        return result;
    }

    public void changePositionWith(UIElement ue){
        int x= this.getStartX();
        int y= this.getStartY();
        this.setX(ue.getStartX());
        this.setY(ue.getStartY());
        this.setX(x);
        this.setY(y);
    }

    static public void main(String[] args){
        AndroidGUIPage page = GUIPageXMLFileReader.readAndroidPageXMLFile("data/0.xml");
        File pic=new File("data/0.png");
        BufferedImage screenshot=null;
        try {
            screenshot= ImageIO.read(pic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(screenshot!=null) {
            UIElement ui = new UIElement(page.elementRoot, screenshot);
            JSONObject obj= JSONHelpler.ueTree2Json(ui);
            FileHelpler.saveJson(obj,"data/j.json");
        }
    }
}
