package UIProject.Module.Element;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import UIProject.FileHelpler;
import UIProject.Module.ChipAndPic;
import UIProject.Module.PageModule;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
}

public class UIElement {

    public static final String LINEARLAYOUT="android.widget.LinearLayout";
    public static final String FRAMELAYOUT="android.widget.FrameLayout";
    public static final String RELATIVELAYOUT="android.widget.RelativeLayout";

    public static final String IMAGEVIEW="android.widget.ImageView";
    public static final String TEXTVIEW="android.widget.TextView";
    public static final String EDITTEXT="android.widget.EditText";

    public Position position;

    public Size size;

    public ArrayList<UIElement> children;

    public UIElement(Position position,Size size){
        this.position=position;
        this.size=size;
    }

    public UIElement(AndroidGUIElement age, BufferedImage image){
        this.position=new Position(age.Ex1, age.Ey1);
        this.size=new Size(age.Ex2-age.Ex1,age.Ey2-age.Ey1);
        this.children=new ArrayList<UIElement>();
        //TODO:create children
        int count=age.sibilings.size();
        if(count!=0){
            createChildren(age, image);
        }
    }

    public UIElement(){
        this(new Position(),new Size());
    }

    public Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }

    public List<UIElement> getChildren() {
        return children;
    }

    public  boolean isCoincide(AndroidGUIElement age1,AndroidGUIElement age2){
        return age1.Ex1==age2.Ex1
                &&age1.Ex2==age2.Ex2
                &&age1.Ey1==age2.Ey1
                &&age1.Ey2==age2.Ey2;
    }

    public void createChildren(AndroidGUIElement age,BufferedImage image){
        if(age.sibilings.size()==1&&isCoincide(age,age.sibilings.get(0))){
            createChildren(age.sibilings.get(0),image);
        }
        else {
            for (int i = 0; i < age.sibilings.size(); i++) {
                if (!isChip(age.sibilings.get(i).EHead)) {
                    Domain domain = new Domain(age.sibilings.get(i), image);
                    this.children.add(domain);
                } else {
                    Chip chip = new Chip(age.sibilings.get(i), image);
                    this.children.add(chip);
                }
            }
        }
    }

    static public boolean isChip(String head){
        if(head.equals(UIElement.LINEARLAYOUT)
                ||head.equals(UIElement.FRAMELAYOUT)
                ||head.equals(UIElement.RELATIVELAYOUT))
            return false;
        else
            return true;
    }

    static public void main(String args[]){
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
            int a=1;
        }
    }
}
