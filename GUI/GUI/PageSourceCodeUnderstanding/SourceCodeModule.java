package org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.PageSourceCodeUnderstanding;

import java.util.ArrayList;
import java.util.List;

import org.ruihua.GUITrans.AppsGUITransformDLProj.AppiumExecution4Evaluation.TestMigrationFromiOS2AndroidonTestSet;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.*;

public class SourceCodeModule {
	
	public static final int INT_MODULE_LABEL_IOS = 0;
	public static final int INT_MODULE_LABEL_ANDROID = 1;
	
	public int moduleLabel;
	
	public List<iOSXCUITestElement> iosModule;
	public List<AndroidGUIElement> androidModule;
	
	
	public SourceCodeModule(int typeLabel) {
		this.moduleLabel = typeLabel;
		if(this.moduleLabel == INT_MODULE_LABEL_IOS) {
			iosModule = new ArrayList<iOSXCUITestElement>();
		}
		else {
			androidModule = new ArrayList<AndroidGUIElement>();
		}
	}
	
	public static SourceCodeModule constructAndroidSourceCodeModule(AndroidGUIElement ele) {
		
		SourceCodeModule result = new SourceCodeModule(INT_MODULE_LABEL_ANDROID);
		
		result.androidModule = new ArrayList<AndroidGUIElement>();
		AndroidGUIPage.traverseTree(ele, 0, 0, result.androidModule);
		
		return result;
	}
	
	public static SourceCodeModule constructAndroidSourceCodeModule_2(AndroidGUIElement ele) {
		
		SourceCodeModule result = new SourceCodeModule(INT_MODULE_LABEL_ANDROID);
		
		result.androidModule = new ArrayList<AndroidGUIElement>();
		for(int i = 0; i < ele.sibilings.size(); i ++) {
			if(ele.sibilings.get(i).sibilings.size() > 0) {
				//do nothing
			}
			else {
				result.androidModule.add(ele.sibilings.get(i));
			}
		}
		
		return result;
	}
	
	
	
	public static void printSourceCodeModule(SourceCodeModule m) {
		
		List<String> content = new ArrayList<String>();
		for(int i = 0; i < m.androidModule.size(); i ++) {
			if(m.androidModule.size() > 20) {
				continue;
			}
			
			String temp = m.androidModule.get(i).EText;
			content.add(temp);
			System.out.print(m.androidModule.get(i).EHead + "[" + m.androidModule.get(i).locationTypeOrder + "]" + " <[" + m.androidModule.get(i).Ex1 + "," +  m.androidModule.get(i).Ey1 + "][" +  m.androidModule.get(i).Ex2 + "," +  m.androidModule.get(i).Ey2 + "]>" + temp + "; ");
			
		}
		System.out.println();
		
	}
	
	
	
	
	
	
}
