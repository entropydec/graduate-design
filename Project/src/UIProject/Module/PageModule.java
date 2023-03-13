package UIProject.Module;

import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import UIProject.util.FileHelpler;
import UIProject.Module.Element.Chip;
import UIProject.Module.Element.Domain;
import UIProject.Module.Element.UIElement;
import UIProject.Controller.PMHelpler;
import UIProject.Controller.UEHelpler;
import UIProject.util.JSONHelpler;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class PageModule implements Cloneable{

    public BufferedImage screenshot;

    public UIElement root;

    public HashMap<Integer,Domain> domains;

    public HashMap<Integer,Chip> chips;

    public HashSet<String> types;

    public boolean zoom;

    public PageModule(AndroidGUIPage agp, BufferedImage pic){

        if(UIElement.isChip(agp.elementRoot.EHead)) {
            this.root = new Chip(agp.elementRoot, screenshot);
        }
        else {
            this.root = new Domain(agp.elementRoot, screenshot);
            Domain domain=(Domain) this.root;
            domain.drawFrame(screenshot);
        }

        this.screenshot=pic;

        this.initTreeDepth();
        this.initSet();
    }

    public PageModule(String pmPath) {
        this.zoom=false;
        JSONObject object= null;
        try {
            object = FileHelpler.readJson(pmPath+"ueTree.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(object!=null) {
            this.root = JSONHelpler.json2UETree(object);
            setNodeImage(this.root, pmPath + "images/");
            this.initSet();
        }
    }

    @Override
    public PageModule clone() {
        try {
            PageModule clone = (PageModule) super.clone();
            clone.screenshot=FileHelpler.copyImage(this.screenshot);
            clone.root=this.root.clone();
            HashMap<Integer,Domain> domains=new HashMap<Integer,Domain>();
            HashMap<Integer,Chip> chips=new HashMap<Integer,Chip>();
            HashSet<String> types=new HashSet<String>();
            clone.initSet();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void initSet(){
        this.domains=new HashMap<Integer,Domain>();
        this.chips=new HashMap<Integer,Chip>();
        this.types=new HashSet<String>();
        Stack<UIElement> stack=new Stack<UIElement>();
        stack.push(this.root);
        while(!stack.isEmpty()){
            UIElement ue=stack.pop();
            this.types.add(ue.getType());
            if(ue instanceof Domain){
                Domain domain=(Domain) ue;
                this.domains.put(domain.getId(),domain);
            }
            else{
                Chip chip=(Chip) ue;
                this.chips.put(chip.getId(),chip);
            }
            for(int i=0;i<ue.getChildren().size();i++){
                stack.push(ue.getChildren().get(i));
            }
        }
    }

    public void setNodeImage(UIElement node, String imageDir){
        BufferedImage image=null;
        File pic=new File(imageDir+node.getId()+".png");
        try {
            image= ImageIO.read(pic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(image==null){
            System.out.println("image is null in setNodeImage");
            return;
        }

        if(!node.isFit(image)
                &&this.zoom)
            image= UEHelpler.zoomImage(image,node.getWidth(),node.getHeight());

        if(node instanceof Chip){
            Chip chip=(Chip) node;
            chip.setImage(image);
        }
        else {
            Domain domain=(Domain) node;
            domain.setOuterFrame(image);
        }
        for(int i=0;i<node.getChildren().size();i++){
            setNodeImage(node.getChildren().get(i),imageDir);
        }
    }

    public UIElement getRoot(){
        return this.root;
    }

    public HashMap<Integer,Domain> getDomains(){
        return this.domains;
    }

    public HashMap<Integer,Chip> getChips() {
        return this.chips;
    }

    public int getWidth(){
        return root.getWidth();
    }

    public int getHeight(){
        return root.getHeight();
    }

    public BufferedImage createPicture(){
        int width=this.root.getWidth();
        int height=this.root.getHeight();
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics=buffImg.createGraphics();
        drawImage(this.root,graphics);
        graphics.dispose();
        this.screenshot=buffImg;
        return buffImg;
    }

    public void drawImage(UIElement ue, Graphics2D graphics){
        int x = ue.getStartX();
        int y = ue.getStartY();
        int width = ue.getWidth();
        int height = ue.getHeight();
        if (ue instanceof Chip) {
            Chip chip = (Chip) ue;
            BufferedImage image = chip.getImage();
            Image _image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            graphics.drawImage(_image, x, y, null);
        }
        else if(ue instanceof Domain){
            Domain domain = (Domain) ue;
            BufferedImage image = domain.getOuterFrame();
            Image _image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            graphics.drawImage(_image, x, y, null);
        }
        for(int i=0;i<ue.getChildren().size();i++) {
            drawImage(ue.getChildren().get(i), graphics);
        }
    }

    public void initTreeDepth(){
        this.setNodeDepth(this.root,0);
    }

    public void setNodeDepth(UIElement node,int depth){
        node.setDepth(depth);
        for(int i=0;i<node.getChildren().size();i++){
            this.setNodeDepth(node.getChildren().get(i),depth+1);
        }
    }

    public void switchTwoNode(int id1,int id2){
        UIElement ue1=domains.get(id1);
        if(ue1==null)
            ue1=chips.get(id1);
        UIElement ue2=domains.get(id2);
        if(ue2==null)
            ue2=chips.get(id2);
        ue1.changePositionWith(ue2);
    }

    public void printTree(){
        this.printNodeTree(root);
    }

    public void printNodeTree(UIElement node){
        for(int i=0;i< node.depth;i++){
            System.out.print("\t");
        }
        if(node instanceof Domain){
            System.out.print("<Domain id=" +node.id
                                +" type="+node.type+"/>"+"\n");
        }
        else{
            System.out.print("<Chip id=" +node.id
                    +" type="+node.type+"/>"+"\n");
        }
        for(int i=0;i<node.getChildren().size();i++){
            printNodeTree(node.getChildren().get(i));
        }
    }

    public static void test1(){
        PageModule pm=null;
        pm=new PageModule("data/9/");
        BufferedImage image1=pm.createPicture();
        FileHelpler.saveImage(image1,"data/9/out.png");
    }

    public static void test2(){
        PageModule pm = PMHelpler.
                createPageModuleByXMLAndPicture(
                        "data/9/9.xml","data/9/9.png");
        FileHelpler.savePageModule(pm,"data/9/");
        System.out.print(pm.types);
    }

    public static void main(String[] args) {
        test2();
        test1();
    }
}
