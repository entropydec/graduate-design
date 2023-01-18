package org.ruihua.GUITrans.AppsGUITransformDLProj.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.*;

public class IOSPage2AndroidPageRelation {
	
	public String iOSPageFileName;
	public iOSXCUITestPage iOSPage;
	public String androidPageFileName;
	public AndroidGUIPage androidPage;
	
	public String appInfo;//not used
	public int identifier;
	
	public List<IOSElement2AndroidElementRelation> matchElements;
	
	public List<IOSElementRelativePositionSetInPage> unmappedIOSElements;
	public List<AndroidElementRelativePositionSetInPage> unmappedAndroidElements;
	
	public List<iOSXCUITestElement> unmappedIOSElements_2;
	public List<AndroidGUIElement> unmappedAndroidElements_2;
	
	public HashMap<String, iOS2AndroidUnmappedElementSetRelation> umappedElementSetRelations;
	
	
	public IOSPage2AndroidPageRelation() {
		//this.matchElements = new ArrayList<IOSElement2AndroidElementRelation>();
	}
	
	public void printIOSPage2AndroidPageRelation() {
		System.out.println(
				"ID-" + identifier
				+ "-[iOSPageFileName: " + iOSPageFileName
				+ " <-> androidPageFileName:" + androidPageFileName + "]"
				);
	}
	
	
}
