package UIProject.Module;

import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import UIProject.util.FileHelpler;
import UIProject.Module.Element.Chip;
import UIProject.Module.Element.Domain;
import UIProject.Module.Element.UIElement;
import UIProject.util.ImageHelpler;
import UIProject.util.JSONHelpler;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class PageModule {

    public BufferedImage screenshot;

    public UIElement root;

    public ArrayList<Domain> domains;

    public ArrayList<Chip> chips;

    public boolean zoom;

    public PageModule(AndroidGUIPage agp, String picPath){
        File pic=new File(picPath);
        try {
            this.screenshot= ImageIO.read(pic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(UIElement.isChip(agp.elementRoot.EHead)) {
            this.root = new Chip(agp.elementRoot, screenshot);
        }
        else {
            this.root = new Domain(agp.elementRoot, screenshot);
            Domain domain=(Domain) this.root;
            domain.drawFrame(screenshot);
        }
        this.initTreeDepth();
        this.initTwoLists();
    }

    public PageModule(String pmPath) throws IOException {
        this.zoom=false;
        JSONObject object=FileHelpler.readJson(pmPath+"ueTree.json");
        this.root= JSONHelpler.json2UETree(object);
        setNodeImage(this.root,pmPath+"images/");
        this.initTwoLists();
    }

    public void initTwoLists(){
        this.domains=new ArrayList<Domain>();
        this.chips=new ArrayList<Chip>();
        Stack<UIElement> stack=new Stack<UIElement>();
        stack.push(this.root);
        while(!stack.isEmpty()){
            UIElement ue=stack.pop();
            if(ue instanceof Domain){
                Domain domain=(Domain) ue;
                this.domains.add(domain);
            }
            else{
                Chip chip=(Chip) ue;
                this.chips.add(chip);
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
            image= ImageHelpler.zoomImage(image,node.getWidth(),node.getHeight());

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

    public ArrayList<Domain> getDomains(){
        return this.domains;
    }

    public ArrayList<Chip> getChips() {
        return this.chips;
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
        try {
            pm=new PageModule("data/9/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(pm == null)
            return;
        BufferedImage image1=pm.createPicture();
        FileHelpler.saveImage(image1,"data/9/out.png");
    }

    public static void test2(){
        AndroidGUIPage page = GUIPageXMLFileReader.readAndroidPageXMLFile("data/9/9.xml");
        PageModule pm = new PageModule(page, "data/9/9.png");
        FileHelpler.savePageModule(pm,"data/9/");
        pm.printTree();
    }

    public static void main(String[] args) {
        test1();
    }
}
