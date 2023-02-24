package UIProject.util;

import UIProject.Module.Element.UIElement;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UEHelpler {

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

}
