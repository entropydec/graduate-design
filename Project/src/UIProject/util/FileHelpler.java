package UIProject.util;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import UIProject.Module.ChipAndPic;
import UIProject.Module.Element.Chip;
import UIProject.Module.Element.Domain;
import UIProject.Module.Element.UIElement;
import UIProject.Module.PageModule;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileHelpler {

    public static ChipAndPic readXMLFile(String xmlPath, String picPath){
        ChipAndPic res=null;
        AndroidGUIPage page = GUIPageXMLFileReader.readAndroidPageXMLFile(xmlPath);
        List<AndroidGUIElement> chips = AndroidGUIPage.deriveLeafSetofTree(page);
        File pic=new File(picPath);
        try {
            BufferedImage screenshot= ImageIO.read(pic);
            res=new ChipAndPic(chips,screenshot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static void saveImages(List<BufferedImage> images,String dirPath){
        for(int count =0; count<images.size(); count++){
            String path=dirPath+count+".png";
            saveImage(images.get(count),path);
        };
    }

    public static void saveImage(BufferedImage image, String path){
        int width=image.getWidth();
        int height=image.getHeight();
        Image _image=image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics=buffImg.createGraphics();
        graphics.drawImage(_image,0,0,null);
        graphics.dispose();
        try {
            OutputStream out=new FileOutputStream(path);
            ImageIO.write(buffImg, "png", out);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void savePageModule(PageModule pm,String directory){
        JSONObject jsonObject=JSONHelpler.ueTree2Json(pm.getRoot());
        FileHelpler.saveJson(jsonObject,directory+"ueTree.json");
        //mkdir for images
        String imageDir=directory+"images/";
        File folder=new File(imageDir);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.setWritable(true, false);
            folder.mkdirs();
        }
        saveUEImage(pm.getRoot(),imageDir);
    }

    public static void saveUEImage(UIElement node, String dir){
        if(node instanceof Chip) {
            Chip chip=(Chip) node;
            FileHelpler.saveImage(chip.getImage(),dir+chip.getId()+".png");
        }
        else{
            Domain domain=(Domain) node;
            FileHelpler.saveImage(domain.getOuterFrame(),dir+domain.getId()+".png");
        }
        for(int i=0;i<node.getChildren().size();i++){
            saveUEImage(node.getChildren().get(i),dir);
        }
    }

    public static void saveJson(JSONObject obj, String path){
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(obj.toString(4));
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject readJson(String path) throws IOException {
        File file=new File(path);
        FileReader fileReader=new FileReader(file);
        Reader reader=new InputStreamReader((new FileInputStream(file)), StandardCharsets.UTF_8);
        int ch=0;
        StringBuffer sb=new StringBuffer();
        while((ch=reader.read())!=-1){
            sb.append((char) ch);
        }
        fileReader.close();
        reader.close();
        String json= sb.toString();
        JSONObject result=new JSONObject(json);
        return result;
    }

    public static BufferedImage copyImage(BufferedImage source){
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    public static void main(String[] args) throws IOException {
        JSONObject object=FileHelpler.readJson("data/0/ueTree.json");
        UIElement ue=JSONHelpler.json2UETree(object);
    }
}