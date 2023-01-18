package org.ruihua.GUITrans.AppsGUITransformDLProj.GUI;

import java.util.ArrayList;
import java.util.List;

public class iOSXCUITestElement implements Comparable<iOSXCUITestElement> {
	
	public String EHead;//<head...
	
	public String EType;//type
	
	public String EName;//name
	
	public String ELabel;//label
	
	public String EEnable;//enable
	
	public String EVisible;//visible
	
	public String EX;//x
	public int Ex;//x
	
	public String EY;//y
	public int Ey;//y
	
	public String EWidth;//width
	public int Ewidth;//width
	
	public String EHeight;//height
	public int Eheight;//height
	
	public String EValue;//value
	
	public String EEnd;
	
	public boolean isContainer;
	
	
	public iOSXCUITestElement parent;
	public List<iOSXCUITestElement> sibilings;
	public int pageTreeLevel;
	
	public int locationTypeOrder;
	
	public int lineNumber;
	
	public iOSXCUITestElement() {
		this.parent = null;
		this.sibilings = new ArrayList<iOSXCUITestElement>();
		this.pageTreeLevel = -1;
		this.locationTypeOrder = -1;
	}
	
	public static void printElement(iOSXCUITestElement ele) {
		System.out.println(
				"<" + ele.EHead + " " + 
				"type=\"" + ele.EType + "\" " +
				"name=\"" + ele.EName + "\" " +
				"label=\"" + ele.ELabel + "\" " +
				"enable=\"" + ele.EEnable + "\" " +
				"visible=\"" + ele.EVisible + "\" " +
				"x=\"" + ele.EX + "\"" +
				"(" + ele.Ex + ") " +
				"y=\"" + ele.EY + "\"" +
				"(" + ele.Ey + ") " +
				"width=\"" + ele.EWidth + "\"" +
				"(" + ele.Ewidth + ") " +
				"height=\"" + ele.EHeight + "\"" +
				"(" + ele.Eheight + ")(location: " + ele.locationTypeOrder + ")(lineNumber: " + ele.lineNumber + ")"
						);
		
		System.out.println("level:" + ele.pageTreeLevel + ". ");
	}

	public int compareTo(iOSXCUITestElement o) {
		// TODO Auto-generated method stub
		if(this.EHead.compareTo(o.EHead) > 0) {
			return -1;
		}
		else if(this.EHead.compareTo(o.EHead) < 0) {
			return 1;
		}
		else { return 0;}
		
	}
	
	public static boolean isSameLoaction(iOSXCUITestElement e1, iOSXCUITestElement e2) {
		
		if((e1.Ex == e2.Ex)&&(e1.Ey == e2.Ey)&&((e1.Ex + e1.Ewidth) == (e2.Ex + e2.Ewidth))&&((e1.Ey + e1.Eheight) == (e2.Ey + e2.Eheight))) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isSameElements(iOSXCUITestElement e1, iOSXCUITestElement e2) {
		
		if(e1.EHead.equals(e2.EHead) && 
				//e1.EType.equals(e2.EType) && e1.EName.equals(e2.EName) && e1.ELabel.equals(e2.ELabel) &&
				(e1.Ex == e2.Ex) && (e1.Ey == e2.Ey) && (e1.Ewidth == e2.Ewidth) && (e1.Eheight == e2.Eheight) && 
				(e1.pageTreeLevel == e2.pageTreeLevel) &&
				(e1.locationTypeOrder == e2.locationTypeOrder)
				) {
			return true;
		}
		
		return false;
	}
	
	public iOSXCUITestElement copyElement() {
		
		iOSXCUITestElement eleCopy = new iOSXCUITestElement();
		
		eleCopy.EHead = this.EHead;//<head...
		
		eleCopy.EType = this.EType;//type
		
		eleCopy.EName = this.EName;//name
		
		eleCopy.ELabel = this.ELabel;//label
		
		eleCopy.EEnable = this.EEnable;//enable
		
		eleCopy.EVisible = this.EVisible;//visible
		
		eleCopy.EX = this.EX;//x
		eleCopy.Ex = this.Ex;//x
		
		eleCopy.EY = this.EY;//y
		eleCopy.Ey = this.Ey;//y
		
		eleCopy.EWidth = this.EWidth;//width
		eleCopy.Ewidth = this.Ewidth;//width
		
		eleCopy.EHeight = this.EHeight;//height
		eleCopy.Eheight = this.Eheight;//height
		
		eleCopy.EValue = this.EValue;//value
		
		eleCopy.EEnd = this.EEnd;
		
		eleCopy.isContainer = this.isContainer;
		
//		parent
		
		eleCopy.pageTreeLevel = this.pageTreeLevel;
		
		eleCopy.locationTypeOrder = this.locationTypeOrder;
		
		eleCopy.lineNumber = this.lineNumber;
		
		eleCopy.sibilings = new ArrayList<iOSXCUITestElement>();
		
		return eleCopy;
	}
	
	

}
