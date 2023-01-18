package org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.PageSourceCodeUnderstanding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.*;

public class MappedRefinedCrossplatformPageStructure {
	
	public String pageFileNameKey;
	
	public iOSXCUITestElement iosRefindedPageStructure;
	
	public AndroidGUIElement androidRefindedPageStructure;
	
	public static void dynamicTuningAlignTreeStructure(iOSXCUITestElement iosRefindedPageStructure, AndroidGUIElement androidRefindedPageStructure) {
		
		int iosPageStructurePartitionCount = iosRefindedPageStructure.sibilings.size();
		int androidPageStructurePartitionCount = androidRefindedPageStructure.sibilings.size();
		
		
		if(iosPageStructurePartitionCount == androidPageStructurePartitionCount) {
			//do nothing, and we use the current structures for the next mapping work
		}
		else if(iosPageStructurePartitionCount < androidPageStructurePartitionCount) {
			
		}
		else {
			//iosPageStructurePartitionCount > androidPageStructurePartitionCount
			
		}
		
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		
//		InputStreamReader is = null;
//		BufferedReader br = null;
//		is = new InputStreamReader(System.in);
//		br = new BufferedReader(is);
//		for(int i = 0; i < 2; i ++) {
//			System.out.println("Input to trigger the next mapped pages structures:");
//			try { String t = br.readLine(); System.out.println(":: " + t); } catch (IOException e) { e.printStackTrace(); }
//			
//			
//			
//			
//		} 
		
	}
}
