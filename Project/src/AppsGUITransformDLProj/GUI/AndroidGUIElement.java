package AppsGUITransformDLProj.GUI;

import java.util.ArrayList;
import java.util.List;

public class AndroidGUIElement implements Comparable<AndroidGUIElement> {
	
	public String EHead;//<head...
	
	public String EIndex;//index
	
	public String EPackage;//package
	
	public String EClass;//class
	
	public String EText;//text
	
	public String ECheckable;//checkable
	
	public String EChecked;//checked
	
	public String EClickable;//clickable
	
	public String EEnable;//enable
	
	public String EFocusable;//focusable
	
	public String EFocused;//focused
	
	public String ELongclickable;//long-clickable
	
	public String EPassword;//password
	
	public String EScrollable;//scrollable
	
	public String ESelected;//selected
	
	public String EBounds;//bounds [x1,y1][x2,y2]
	public int Ex1;//[x1,y1]
	public int Ey1;//
	public int Ex2;//[x2,y2]
	public int Ey2;//
	
	public String EDisplayed;//displayed
	
	public String EContent_desc;//content-desc
	
	public String EResource_id;//resource-id
	
	//selection-end
	//long-clickable
	//selection-start
	
	
	
	
	public String EEnd;
	public boolean isContainer;
	
	
	public AndroidGUIElement parent;
	public List<AndroidGUIElement> sibilings;
	public int pageTreeLevel;
	
	public int locationTypeOrder;
	
	public int lineNumber;
	
	
	public AndroidGUIElement() {
		this.parent = null;
		this.sibilings = new ArrayList<AndroidGUIElement>();
		this.pageTreeLevel = -1;
		this.locationTypeOrder = -1;
	}
	
	public static void printElement(AndroidGUIElement ele) {
		
		System.out.println(
				"<" + ele.EHead + " " + 
				"index=\"" + ele.EIndex + "\" " +
				"package=\"" + ele.EPackage + "\" " +
				"class=\"" + ele.EClass + "\" " +
				"text=\"" + ele.EText + "\" " +
				"checkable=\"" + ele.ECheckable + "\" " +
				"checked=\"" + ele.EChecked + "\" " +
				"clickable=\"" + ele.EClickable + "\" " +
				"enable=\"" + ele.EEnable + "\" " +
				"focusable=\"" + ele.EFocusable + "\" " +
				"long-clickabl=\"" + ele.ELongclickable + "\" " +
				"password=\"" + ele.EPassword + "\" " +
				"scrollable=\"" + ele.EScrollable + "\" " +
				"selected=\"" + ele.ESelected + "\" " +
				"bounds=\"" + ele.EBounds + "\" " +
				"([" + ele.Ex1 + "," + ele.Ey1 + "][" + ele.Ex2 + "," + ele.Ey2 + "]) " +
				"displayed=\"" + ele.EDisplayed + "\" " +
				"(location: " + ele.locationTypeOrder + ")"
						);
		
		System.out.println("level:" + ele.pageTreeLevel + ". ");
	}
	
	public int compareTo(AndroidGUIElement o) {
		// TODO Auto-generated method stub
		if(this.EHead.compareTo(o.EHead) > 0) {
			return -2;
		}
		else if(this.EHead.compareTo(o.EHead) < 0) {
			return 2;
		}
		else { 
			if(this.sibilings.size() > o.sibilings.size()) {
				return -1;
			}
			else if(this.sibilings.size() < o.sibilings.size()) {
				return 1;
			} else {
				return 0;
			}
			
		}
		
	}
	
	public static boolean isSameLoaction(AndroidGUIElement e1, AndroidGUIElement e2) {
		
		if((e1.Ex1 == e2.Ex1)&&(e1.Ex2 == e2.Ex2)&&(e1.Ey1 == e2.Ey1)&&(e1.Ey2 == e2.Ey2)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isSameElements(AndroidGUIElement e1, AndroidGUIElement e2) {
		
		if( e1.EHead.equals(e2.EHead) &&
				e1.EBounds.equals(e2.EBounds) &&
				(e1.Ex1 == e2.Ex1) && (e1.Ey1 == e2.Ey1) && (e1.Ex2 == e2.Ex2) && (e1.Ey2 == e2.Ey2) &&
				(e1.pageTreeLevel == e2.pageTreeLevel) && (e1.locationTypeOrder == e2.locationTypeOrder)
				) {
			return true;
		}
		
		return false;
	}
	
	public AndroidGUIElement copyElement() {
		
		AndroidGUIElement eleCopy = new AndroidGUIElement();
		
		eleCopy.EHead = this.EHead;//<head...
		
		eleCopy.EIndex = this.EIndex;//index
		
		eleCopy.EPackage = this.EPackage;//package
		
		eleCopy.EClass = this.EClass;//class
		
		eleCopy.EText = this.EText;//text
		
		eleCopy.ECheckable = this.ECheckable;//checkable
		
		eleCopy.EChecked = this.EChecked;//checked
		
		eleCopy.EClickable = this.EClickable;//clickable
		
		eleCopy.EEnable = this.EEnable;//enable
		
		eleCopy.EFocusable = this.EFocusable;//focusable
		
		eleCopy.EFocused = this.EFocused;//focused
		
		eleCopy.ELongclickable = this.ELongclickable;//long-clickable
		
		eleCopy.EPassword = this.EPassword;//password
		
		eleCopy.EScrollable = this.EScrollable;//scrollable
		
		eleCopy.ESelected = this.ESelected;//selected
		
		eleCopy.EBounds = this.EBounds;//bounds [x1,y1][x2,y2]
		eleCopy.Ex1 = this.Ex1;//[x1,y1]
		eleCopy.Ey1 = this.Ey1;//
		eleCopy.Ex2 = this.Ex2;//[x2,y2]
		eleCopy.Ey2 = this.Ey2;//
		
		eleCopy.EDisplayed = this.EDisplayed;//displayed
		
		eleCopy.EContent_desc = this.EContent_desc;//content-desc
		
		eleCopy.EResource_id = this.EResource_id;//resource-id
		
		

		eleCopy.EEnd = this.EEnd;
		eleCopy.isContainer = this.isContainer;
		
		
//		eleCopy.parent = this.parent;
		
		eleCopy.pageTreeLevel = this.pageTreeLevel;
		
		eleCopy.locationTypeOrder = this.locationTypeOrder;
		
		eleCopy.lineNumber = this.lineNumber;
		
		eleCopy.sibilings = new ArrayList<AndroidGUIElement>();
//		for(int i = 0; i < this.sibilings.size(); i ++) {
//			eleCopy.sibilings.add(this.sibilings.get(i));
//		}
		
		
		return eleCopy;
	}
	
	
	

}
