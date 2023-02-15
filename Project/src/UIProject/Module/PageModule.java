package UIProject.Module;

import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import UIProject.util.FileHelpler;
import UIProject.Module.Element.Chip;
import UIProject.Module.Element.Domain;
import UIProject.Module.Element.UIElement;
import UIProject.util.JSONHelpler;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PageModule {

    public BufferedImage screenshot;

    public UIElement root;

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
        this.setTreeDepth();
    }

    public PageModule(String pmPath) throws IOException {
        JSONObject object=FileHelpler.readJson(pmPath+"ueTree.json");
        this.root= JSONHelpler.json2UETree(object);
        setNodeImage(this.root,pmPath+"images/");
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

    public BufferedImage createPicture(){
        BufferedImage buffImg = new BufferedImage(1080, 2270, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics=buffImg.createGraphics();
        drawImage(this.root,graphics);
        graphics.dispose();
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

    public void setTreeDepth(){
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

    public static void main(String[] args) {
        PageModule pm1=null;
        try {
            pm1=new PageModule("data/0/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(pm1 == null)
            return;
        BufferedImage image1=pm1.createPicture();
        FileHelpler.saveImage(image1,"data/0/out1.png");

        AndroidGUIPage page = GUIPageXMLFileReader.readAndroidPageXMLFile("data/0/0.xml");
        PageModule pm = new PageModule(page, "data/0/0.png");
        FileHelpler.savePageModule(pm,"data/0/");
        pm.printTree();
        BufferedImage image=pm.createPicture();
        FileHelpler.saveImage(image,"data/0/out.png");
    }
}
