package UIProject.util;

import UIProject.Module.Element.Chip;
import UIProject.Module.Element.Domain;
import UIProject.Module.Element.UIElement;
import org.json.JSONArray;
import org.json.JSONObject;

//用于处理json结构
public class JSONHelpler {

    //将ue结构转为json
    public static JSONObject ueTree2Json(UIElement ue){
        JSONObject result=new JSONObject();
        result.put("id",ue.getId());
        result.put("type",ue.getType());
        result.put("depth",ue.getDepth());
        result.put("x",ue.getStartX());
        result.put("y",ue.getStartY());
        result.put("width",ue.getWidth());
        result.put("height",ue.getHeight());
        if(ue instanceof Chip){
            result.put("class","Chip");
        }
        else{
            result.put("class","Domain");
        }
        JSONArray children=new JSONArray();
        for(int i=0;i<ue.getChildren().size();i++){
            JSONObject child=ueTree2Json(ue.getChildren().get(i));
            children.put(child);
        }
        result.put("children",children);
        return result;
    }

    //json转为ue结构（不包含bufferedimage结构的转换）
    public static UIElement json2UETree(JSONObject obj){
        UIElement result;
        String objClass=obj.get("class").toString();
        if(objClass.equals("Chip")){
            result=new Chip();
        }
        else{
            result=new Domain();
        }
        result.setId(Integer.parseInt(obj.get("id").toString()));
        result.setType(obj.get("type").toString());
        result.setDepth(Integer.parseInt(obj.get("depth").toString()));
        result.setX(Integer.parseInt(obj.get("x").toString()));
        result.setY(Integer.parseInt(obj.get("y").toString()));
        result.setWidth(Integer.parseInt(obj.get("width").toString()));
        result.setHeight(Integer.parseInt(obj.get("height").toString()));
        JSONArray children= (JSONArray) obj.get("children");
        for(int i=0;i<children.length();i++){
            JSONObject child=children.getJSONObject(i);
            result.getChildren().add(json2UETree(child));
        }
        return result;
    }
}
