package org.ruihua.GUITrans.AppsGUITransformDLProj.UserInvolvementGUI4Mapping;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.AndroidGUIElement;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.AndroidGUIPage;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.iOSXCUITestElement;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.iOSXCUITestPage;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.PageSourceCodeUnderstanding.AndroidXMLHandlingTools;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.PageSourceCodeUnderstanding.iOSXMLHandlingTools;
import org.ruihua.GUITrans.AppsGUITransformDLProj.util.DataFileRead;
import org.ruihua.GUITrans.AppsGUITransformDLProj.util.DataFileWrite;
import org.ruihua.GUITrans.AppsGUITransformDLProj.util.IOSElement2AndroidElementRelation;
import org.ruihua.GUITrans.AppsGUITransformDLProj.util.IOSPage2AndroidPageRelation;
import org.ruihua.GUITrans.AppsGUITransformDLProj.util.MapControlsFromMappedCorrespondingGUIPages;
import org.ruihua.GUITrans.AppsGUITransformDLProj.util.iOS2AndroidUnmappedElementSetRelation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class MappingRelationCheckerInvolveUser {
	
	public static final String STRING_UI_NAME = "Pages & Controls Mapping UI";
	
	public static final String STRING_APP_FOLDER_START = "AppName";
	public static final String STRING_IOS_PAGE_FOLDER_MARK = "iOS/";
	public static final String STRING_ANDROID_PAGE_FOLDER_MARK = "Android/";
	
	public static final int INT_HAVE_CONTROLL_MAPPING_RELATION = 1;
	public static final int INT_NOT_HAVE_CONTROLL_MAPPING_RELATION = 0;
	
	public static final int INT_ADD_CONTROL_MAPPING_RELATION = 0;
	public static final int INT_DEL_CONTROL_MAPPING_RELATION = 1;
	
	public static final int INT_IOS_PAGE = 0;
	public static final int INT_ANDROID_PAGE = 1;
	
	
	
	
	public MappingRelationDisplayFrame mappingRelationDisplayFrame;//Note! This member crossed with this class!
	
	public String pageMappingRelationDatabaseFile;
	
	public List<String> allAppsInfo;
	public List<List<IOSPage2AndroidPageRelation>> pageMappingRelations4AllAppsFromInitialFile;
	
	public static int currentAppIndex = 0;
	public static int currentPageRelationIndex = -1;
	
	
	
	public IOSPage2AndroidPageRelation currentMappedPageRelation;
	public Set<Entry<String, iOS2AndroidUnmappedElementSetRelation>> entry4MapUnmappedControls;
	
	public Iterator<Entry<String, iOS2AndroidUnmappedElementSetRelation>> iterator4MapUnmappedControls;
	
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public String mappingRelationDatabaseFolder;
	public String basicFileFolder;
	
	
	public String dateStamp;
	
	public String initialDateStamp;
	public String reviseDateStamp;
	
	
	
	/**
	 * Interaction with Frame Related
	 * */
	
	public void startTestCase0() {
		
//		System.out.println(allAppsInfo.get(0) + STRING_IOS_PAGE_FOLDER_MARK + pageMappingRelations4AllAppsFromInitialFile.get(0).get(0).iOSPageFileName + ".png");
//		System.out.println(allAppsInfo.get(0) + STRING_ANDROID_PAGE_FOLDER_MARK + pageMappingRelations4AllAppsFromInitialFile.get(0).get(0).androidPageFileName + ".png");
		
		mappingRelationDisplayFrame = new MappingRelationDisplayFrame("Pages & Controls Mapping UI", 
				allAppsInfo.get(0) + STRING_IOS_PAGE_FOLDER_MARK + pageMappingRelations4AllAppsFromInitialFile.get(0).get(0).iOSPageFileName + ".png",
				allAppsInfo.get(0) + STRING_ANDROID_PAGE_FOLDER_MARK + pageMappingRelations4AllAppsFromInitialFile.get(0).get(0).androidPageFileName + ".png"
				);
	}
	
	public void startUI(String basicFileDir) {
		this.mappingRelationDisplayFrame = new MappingRelationDisplayFrame(STRING_UI_NAME);
		this.mappingRelationDisplayFrame.mappingRelationCheckerInvolveUser = this;//
		
		this.basicFileFolder = basicFileDir;
		
//		if((allAppsInfo.size() > 0)&&(pageMappingRelations4AllAppsFromInitialFile.get(0).size() > 0)) {
//			currentAppIndex = 0;
//			currentPageRelationIndex = -1;
//		}
//		else {
//			//!!!!!!!!!!!!!!!!
//			System.out.println("===== Error for the collected data (When starting UI) =====");
//		}
		
	}
	
	
	public boolean noMappedPages() {
		
		int countPages = 0;
		
		int lenAppList = this.allAppsInfo.size();
		
		for(int i = 0; i < lenAppList; i ++) {
			countPages = countPages + pageMappingRelations4AllAppsFromInitialFile.get(i).size();
		}
		
		//for testing
		//countPages = 0;
		
		if(countPages == 0) {
			return true;
		}
		
		return false;
	}
	
	private boolean nextMappedPage() {
		
		int tempCurrentPageRelationIndex = currentPageRelationIndex + 1;
		
		System.out.println("[Next Func] current App's mapping relations: " + pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).size());
		
		if(tempCurrentPageRelationIndex < pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).size()) {
			currentPageRelationIndex = tempCurrentPageRelationIndex;
		}
		else {
			
			int tempCurrentAppIndex = currentAppIndex + 1;
			
			
			while(true
//					tempCurrentAppIndex < allAppsInfo.size()
					) {
				
				System.out.println("[Next Func] tempCurrentAppIndex: " + tempCurrentAppIndex);
				
				if((tempCurrentAppIndex < allAppsInfo.size())&&(pageMappingRelations4AllAppsFromInitialFile.get(tempCurrentAppIndex).size() > 0)) {
					currentAppIndex = tempCurrentAppIndex;
					currentPageRelationIndex = 0;
//					System.out.println("Exit [Next Func] 1");
//					return true;
					break;
				}
				else if((tempCurrentAppIndex < allAppsInfo.size())&&(pageMappingRelations4AllAppsFromInitialFile.get(tempCurrentAppIndex).size() <= 0)) {
					tempCurrentAppIndex = tempCurrentAppIndex + 1;
				}
				else if(tempCurrentAppIndex >= allAppsInfo.size()) {
					//!!!!!!!!!!!!!!!!!!!
					System.out.println("No more apps!");
//					System.out.println("Exit [Next Func] 2");
					return false;
//					break;
				}
				else {
					//
				}
				
			}
			
			
			
		}
		
//		System.out.println("Exit [Next Func] 1");
		return true;
	}
	
	private boolean movebackMappedPages() {
		
		if(currentPageRelationIndex > 0) {
			currentPageRelationIndex = currentPageRelationIndex - 1;
		}
		else {
			if(currentAppIndex > 0) {
				currentAppIndex = currentAppIndex -1;
				currentPageRelationIndex = pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).size() - 1;
			}
			else {
				//!!!!!!!!!!!!!!!!!!!
				System.out.println("No more apps!");
				return false;
			}
		}
		
		
		return true;
	}
	
	
	public boolean loadMappedPages() {
		
		boolean nextPageCouple = this.nextMappedPage();
		if(!nextPageCouple) {
			System.out.println("===== Error for the collected data =====");
			//return nextPageCouple;
			//System.exit(0);
		}
		System.out.println(currentAppIndex + ", " + currentPageRelationIndex);
		
		if(currentAppIndex < 0 || currentPageRelationIndex < 0) {
			System.out.println("===== Error for the collected data: mapped page index error OR no mapped pages now =====");
			return false;
		}
		
		this.mappingRelationDisplayFrame.inputTwoPictures(
				allAppsInfo.get(currentAppIndex) + STRING_IOS_PAGE_FOLDER_MARK + pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).get(currentPageRelationIndex).iOSPageFileName + ".png", 
				allAppsInfo.get(currentAppIndex) + STRING_ANDROID_PAGE_FOLDER_MARK + pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).get(currentPageRelationIndex).androidPageFileName + ".png"
				);
		
		int pageMLen = 0;
		int controlMLen = 0;
		
		int count = this.allAppsInfo.size();
		for(int i = 0; i < count; i ++) {
			int count_ = this.pageMappingRelations4AllAppsFromInitialFile.get(i).size();
			for(int j = 0; j < count_; j ++) {
				pageMLen ++;
				int count__ = this.pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).matchElements.size();
				for(int l = 0; l < count__; l ++) {
					controlMLen ++;
					
					
				}
				
			}
			
		}
		
		System.out.println("PMs: " + pageMLen + ", CMs:" + controlMLen);
		
		
		return nextPageCouple;
	}
	
	public boolean loadLastMappedPages() {
		
		boolean lastPageCouple = this.movebackMappedPages();
		if(!lastPageCouple) {
			System.out.println("===== Error for the collected data =====");
			//return lastPageCouple;
		}
		System.out.println(currentAppIndex + ", " + currentPageRelationIndex);
		
		this.mappingRelationDisplayFrame.inputTwoPictures(
				allAppsInfo.get(currentAppIndex) + STRING_IOS_PAGE_FOLDER_MARK + pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).get(currentPageRelationIndex).iOSPageFileName + ".png", 
				allAppsInfo.get(currentAppIndex) + STRING_ANDROID_PAGE_FOLDER_MARK + pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).get(currentPageRelationIndex).androidPageFileName + ".png"
				);
		return lastPageCouple;
	}
	
	
	public boolean deleteCurrentMappedPages() {//affect the Data Files
		
		//revise the data file & remove the related data in cache (the *allAppsInfo* and *pageMappingRelations4AllAppsFromInitialFile*)
		
		String appNameTemp = this.allAppsInfo.get(currentAppIndex);
		String[] srcs = appNameTemp.split("/");
		appNameTemp = srcs[srcs.length-1];//this may cause basic error
		String controlMappingRelationsDataFileAppFolderTemp = this.mappingRelationDatabaseFolder + appNameTemp + "/";
		IOSPage2AndroidPageRelation relationTemp = this.pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).get(currentPageRelationIndex);
		String controlMappingRelationsFileNameTemp = "[Relation](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
				+ "-" + relationTemp.androidPageFileName + ".txt";
		String unmappedControlsFileNameTemp = "[UnmappedControls](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
				+ "-" + relationTemp.androidPageFileName + ".txt";
		File fileTemp = new File(controlMappingRelationsDataFileAppFolderTemp + controlMappingRelationsFileNameTemp);
		fileTemp.renameTo(new File(controlMappingRelationsDataFileAppFolderTemp + "[*Deleted*]" + controlMappingRelationsFileNameTemp));
		fileTemp = new File(controlMappingRelationsDataFileAppFolderTemp + unmappedControlsFileNameTemp);
		fileTemp.renameTo(new File(controlMappingRelationsDataFileAppFolderTemp + "[*Deleted*]" + unmappedControlsFileNameTemp));
		
		
		this.pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).remove(currentPageRelationIndex);
		
		DataFileWrite.writePageMappingRelations2DataFile(this.pageMappingRelationDatabaseFile, 
				this.pageMappingRelations4AllAppsFromInitialFile, 
				this.allAppsInfo
				);
		
		
		if(this.noMappedPages()) {
			return false;
		}
		
		//move back the current indexes
		boolean hasLastPage = this.movebackMappedPages();
		
		if(hasLastPage) {
			//
		}
		else {
			currentPageRelationIndex = currentPageRelationIndex - 1;
		}
		
		//continue to load mapped pages
		boolean hasNextPageCouple = this.loadMappedPages();
		
		
		return true;
	}
	
	
	
	public void loadAllMappedControlsOnTwoMappedPages() {
		
		int currentAppIndexTemp = currentAppIndex;
		int currentPageRelationIndexTemp = currentPageRelationIndex;
		
		
//		String iosPageFileName = pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndexTemp).get(currentPageRelationIndexTemp).iOSPageFileName + ".xml";
//		String androidPageFileName = pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndexTemp).get(currentPageRelationIndexTemp).androidPageFileName + ".xml";
//		
//		String iosPageFileNameFullPath = allAppsInfo.get(currentAppIndexTemp) + STRING_IOS_PAGE_FOLDER_MARK + iosPageFileName;
//		String androidPageFileNameFullPath = allAppsInfo.get(currentAppIndexTemp) + STRING_ANDROID_PAGE_FOLDER_MARK + androidPageFileName;
//		
//		iOSXCUITestPage iosPageTemp = GUIPageXMLFileReader.readiOSPageXMLFile(iosPageFileNameFullPath);
//		pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndexTemp).get(currentPageRelationIndexTemp).iOSPage = iosPageTemp;
//		AndroidGUIPage androidPageTemp = GUIPageXMLFileReader.readAndroidPageXMLFile(androidPageFileNameFullPath);
//		pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndexTemp).get(currentPageRelationIndexTemp).androidPage = androidPageTemp;
		iOSXCUITestPage iosPageTemp = pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndexTemp).get(currentPageRelationIndexTemp).iOSPage;
		AndroidGUIPage androidPageTemp = pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndexTemp).get(currentPageRelationIndexTemp).androidPage;
		
		List<IOSElement2AndroidElementRelation> elementRelationListTemp = MapControlsFromMappedCorrespondingGUIPages.calculateReliableControls4TwoPages(iosPageTemp, androidPageTemp);
		
		pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndexTemp).get(currentPageRelationIndexTemp).matchElements = elementRelationListTemp;
		
		System.out.println("matched elements list length: " + elementRelationListTemp.size());
		
		for(int i = 0; i < elementRelationListTemp.size(); i ++) {
			
			List<Integer> controliOS = new ArrayList<Integer>();
			List<Integer> controlAndroid = new ArrayList<Integer>();
			
			iOSXCUITestElement iosElementTemp = elementRelationListTemp.get(i).iOSPageElement;
			controliOS.add(iosElementTemp.Ex); controliOS.add(iosElementTemp.Ey); 
			controliOS.add((iosElementTemp.Ex + iosElementTemp.Ewidth));
			controliOS.add((iosElementTemp.Ey + iosElementTemp.Eheight));
			
			AndroidGUIElement androidElementTemp = elementRelationListTemp.get(i).androidPageElement;
			controlAndroid.add(androidElementTemp.Ex1); controlAndroid.add(androidElementTemp.Ey1);
			controlAndroid.add(androidElementTemp.Ex2); controlAndroid.add(androidElementTemp.Ey2);
			
			this.mappingRelationDisplayFrame.inputRectsAndRelation(controliOS, controlAndroid, i);
			
			
			System.out.print("(" + elementRelationListTemp.get(i).textSimilarity + ") ");
		}
		System.out.println();
		
		
		IOSPage2AndroidPageRelation relationTemp = pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndexTemp).get(currentPageRelationIndexTemp);
				
		MapControlsFromMappedCorrespondingGUIPages.calculate2SetUnmappedElements_2(relationTemp, iosPageTemp, androidPageTemp, elementRelationListTemp);
		
		int leniOSUnmapped = relationTemp.unmappedIOSElements_2.size();
		int lenAndroidUnmapped = relationTemp.unmappedAndroidElements_2.size();
		
		System.out.println("Unmapped iOS Elements: " + leniOSUnmapped + "; Unmapped Android Elements: " + lenAndroidUnmapped);
		
		for(int i = 0; i < leniOSUnmapped; i ++) {
			List<Integer> controliOS = new ArrayList<Integer>();
			iOSXCUITestElement iosElementTemp = relationTemp.unmappedIOSElements_2.get(i);
			controliOS.add(iosElementTemp.Ex); controliOS.add(iosElementTemp.Ey); 
			controliOS.add((iosElementTemp.Ex + iosElementTemp.Ewidth));
			controliOS.add((iosElementTemp.Ey + iosElementTemp.Eheight));
			this.mappingRelationDisplayFrame.inputRectsWithoutRelation(controliOS, MappingRelationDisplayFrame.INT_IOS_GUI_CONTROL, i);
		}
		
		for(int i = 0; i < lenAndroidUnmapped; i ++) {
			List<Integer> controlAndroid = new ArrayList<Integer>();
			AndroidGUIElement androidElementTemp = relationTemp.unmappedAndroidElements_2.get(i);
			controlAndroid.add(androidElementTemp.Ex1); controlAndroid.add(androidElementTemp.Ey1);
			controlAndroid.add(androidElementTemp.Ex2); controlAndroid.add(androidElementTemp.Ey2);
			this.mappingRelationDisplayFrame.inputRectsWithoutRelation(controlAndroid, MappingRelationDisplayFrame.INT_ANDROID_GUI_CONTROL, i);
		}
		
		this.currentMappedPageRelation = relationTemp;
		
		MapControlsFromMappedCorrespondingGUIPages.calculateIOSRelativePositionSet_2(relationTemp, iosPageTemp, androidPageTemp, elementRelationListTemp);
		
		MapControlsFromMappedCorrespondingGUIPages.calculate2MapElementSetByRelativePositions(relationTemp, elementRelationListTemp);
		
		this.iterator4MapUnmappedControls = relationTemp.umappedElementSetRelations.entrySet().iterator();
		
		
	}
	
	public void loadAllControlsOnTwoMappedPages() {
		
		int currentAppIndexTemp = currentAppIndex;
		int currentPageRelationIndexTemp = currentPageRelationIndex;
		
		
		List<IOSElement2AndroidElementRelation> elementRelationListTemp = pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndexTemp).get(currentPageRelationIndexTemp).matchElements;
		
		
		//System.out.println(this.allAppsInfo.get(currentAppIndexTemp) + ": " + currentAppIndexTemp + "," + currentPageRelationIndexTemp);
		//System.out.println("matched existing? : " + elementRelationListTemp);
		System.out.println("matched elements list length: " + elementRelationListTemp.size());
		
		for(int i = 0; i < elementRelationListTemp.size(); i ++) {
			
			List<Integer> controliOS = new ArrayList<Integer>();
			List<Integer> controlAndroid = new ArrayList<Integer>();
			
			iOSXCUITestElement iosElementTemp = elementRelationListTemp.get(i).iOSPageElement;
			controliOS.add(iosElementTemp.Ex); controliOS.add(iosElementTemp.Ey); 
			controliOS.add((iosElementTemp.Ex + iosElementTemp.Ewidth));
			controliOS.add((iosElementTemp.Ey + iosElementTemp.Eheight));
			
			AndroidGUIElement androidElementTemp = elementRelationListTemp.get(i).androidPageElement;
			controlAndroid.add(androidElementTemp.Ex1); controlAndroid.add(androidElementTemp.Ey1);
			controlAndroid.add(androidElementTemp.Ex2); controlAndroid.add(androidElementTemp.Ey2);
			
			this.mappingRelationDisplayFrame.inputRectsAndRelation(controliOS, controlAndroid, i);
			
			
			System.out.print("(" + elementRelationListTemp.get(i).textSimilarity + ") ");
		}
		System.out.println();
		
		
		IOSPage2AndroidPageRelation relationTemp = pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndexTemp).get(currentPageRelationIndexTemp);
		
		int leniOSUnmapped = relationTemp.unmappedIOSElements_2.size();
		int lenAndroidUnmapped = relationTemp.unmappedAndroidElements_2.size();
		
		System.out.println("Unmapped iOS Elements: " + leniOSUnmapped + "; Unmapped Android Elements: " + lenAndroidUnmapped);
		
		for(int i = 0; i < leniOSUnmapped; i ++) {
			List<Integer> controliOS = new ArrayList<Integer>();
			iOSXCUITestElement iosElementTemp = relationTemp.unmappedIOSElements_2.get(i);
			controliOS.add(iosElementTemp.Ex); controliOS.add(iosElementTemp.Ey); 
			controliOS.add((iosElementTemp.Ex + iosElementTemp.Ewidth));
			controliOS.add((iosElementTemp.Ey + iosElementTemp.Eheight));
			this.mappingRelationDisplayFrame.inputRectsWithoutRelation(controliOS, MappingRelationDisplayFrame.INT_IOS_GUI_CONTROL, i);
		}
		
		for(int i = 0; i < lenAndroidUnmapped; i ++) {
			List<Integer> controlAndroid = new ArrayList<Integer>();
			AndroidGUIElement androidElementTemp = relationTemp.unmappedAndroidElements_2.get(i);
			controlAndroid.add(androidElementTemp.Ex1); controlAndroid.add(androidElementTemp.Ey1);
			controlAndroid.add(androidElementTemp.Ex2); controlAndroid.add(androidElementTemp.Ey2);
			this.mappingRelationDisplayFrame.inputRectsWithoutRelation(controlAndroid, MappingRelationDisplayFrame.INT_ANDROID_GUI_CONTROL, i);
		}
		
		this.currentMappedPageRelation = relationTemp;
		
		
		//calculate for promoting
		MapControlsFromMappedCorrespondingGUIPages.calculateIOSRelativePositionSet_2(relationTemp, 
				pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndexTemp).get(currentPageRelationIndexTemp).iOSPage, 
				pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndexTemp).get(currentPageRelationIndexTemp).androidPage, 
				elementRelationListTemp);
		MapControlsFromMappedCorrespondingGUIPages.calculate2MapElementSetByRelativePositions(relationTemp, elementRelationListTemp);
		this.iterator4MapUnmappedControls = relationTemp.umappedElementSetRelations.entrySet().iterator();
		//?
		
	}
	
	
	
	
	
	
	public List<Integer> deleteOneSelectedRelation(int e2eMappingRelation) {//Only affect the cache
		
		List<Integer> result = new ArrayList<Integer>();
		
		IOSPage2AndroidPageRelation relationTemp = pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).get(currentPageRelationIndex);
		
		iOSXCUITestElement iosElementTemp = relationTemp.matchElements.get(e2eMappingRelation).iOSPageElement;
		AndroidGUIElement androidElementTemp = relationTemp.matchElements.get(e2eMappingRelation).androidPageElement;
		
		relationTemp.unmappedIOSElements_2.add(iosElementTemp);
		relationTemp.unmappedAndroidElements_2.add(androidElementTemp);
		result.add(relationTemp.unmappedIOSElements_2.size()-1);
		result.add(relationTemp.unmappedAndroidElements_2.size()-1);
		
		//System.out.println("Check cache (before remove): " + relationTemp.matchElements.get(e2eMappingRelation) + ", " + relationTemp.matchElements.get(e2eMappingRelation).textSimilarity + ", " + relationTemp.matchElements.size());
		
		relationTemp.matchElements.remove(e2eMappingRelation);
		this.updateE2ERelationsInCache4RemoveOpt(INT_DEL_CONTROL_MAPPING_RELATION, e2eMappingRelation, 0, 0);
		
		//System.out.println("Check cache (after remove): " + relationTemp.matchElements.get(e2eMappingRelation) + ", " + relationTemp.matchElements.get(e2eMappingRelation).textSimilarity + ", " + relationTemp.matchElements.size());
		
		
		return result;
	}
	
	public int addOneNewE2ERelation(int iosElementIndex, int androidELementIndex) {
		
		IOSPage2AndroidPageRelation pageRelationTemp = pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).get(currentPageRelationIndex);
		
		IOSElement2AndroidElementRelation e2eRelationTemp = new IOSElement2AndroidElementRelation();
		
		e2eRelationTemp.iOSPageElement = pageRelationTemp.unmappedIOSElements_2.get(iosElementIndex);
		e2eRelationTemp.androidPageElement = pageRelationTemp.unmappedAndroidElements_2.get(androidELementIndex);
		pageRelationTemp.matchElements.add(e2eRelationTemp);
		pageRelationTemp.unmappedIOSElements_2.remove(iosElementIndex);
		pageRelationTemp.unmappedAndroidElements_2.remove(androidELementIndex);
		
		this.updateE2ERelationsInCache4RemoveOpt(INT_ADD_CONTROL_MAPPING_RELATION, 0, iosElementIndex, androidELementIndex);
		
		
		return pageRelationTemp.matchElements.size() - 1;
	}
	
	public void updateE2ERelationsInCache4RemoveOpt(int symbol, int relationIndex, int unmappediOSIndex, int unmappedAndroidIndex) {
		
		if(symbol == INT_ADD_CONTROL_MAPPING_RELATION) {//add one e2e relation
			
			int lenShapeItems = MappingRelationDisplayFrame.itemList.size();
			
			for(int i = 0; i < lenShapeItems; i ++) {
				
				Shape shapeTemp = MappingRelationDisplayFrame.itemList.get(i);
				
				if((shapeTemp.currentChoice == 5)&&(!shapeTemp.inRelation)&&(
						((shapeTemp.typeiOSorAndroid == Shape.INT_IOS_ELEMENT)&&(shapeTemp.elementOrRelationIndex > unmappediOSIndex))||
						((shapeTemp.typeiOSorAndroid == Shape.INT_ANDROID_ELEMENT)&&(shapeTemp.elementOrRelationIndex > unmappedAndroidIndex))
						)) {
					shapeTemp.elementOrRelationIndex = shapeTemp.elementOrRelationIndex - 1;
				}
			}
			
		}
		else {//delete one e2e relation
			
			int lenShapeItems = MappingRelationDisplayFrame.itemList.size();
			
			for(int i = 0; i < lenShapeItems; i ++) {
				
				Shape shapeTemp = MappingRelationDisplayFrame.itemList.get(i);
				
				if(
						((shapeTemp.currentChoice == 5)||(shapeTemp.currentChoice == 4))&&
						(shapeTemp.inRelation)&&
						(shapeTemp.elementOrRelationIndex > relationIndex)) {
					
					shapeTemp.elementOrRelationIndex = shapeTemp.elementOrRelationIndex - 1;
					
				}
				
			}
			
			
		}
		
	}
	
	public void updateE2ERelationsFromCache2DataFiles() {//affect the data files
		
		IOSPage2AndroidPageRelation relationTemp = pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).get(currentPageRelationIndex);
		
		String appNameTemp = this.allAppsInfo.get(currentAppIndex);
		String[] srcs = appNameTemp.split("/");
		appNameTemp = srcs[srcs.length-1];//this may cause basic error
		String controlMappingRelationsDataFileAppFolderTemp = this.mappingRelationDatabaseFolder + appNameTemp + "/";
		
		String controlMappingRelationsFileNameTemp = controlMappingRelationsDataFileAppFolderTemp + 
				"[Relation](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
				+ "-" + relationTemp.androidPageFileName + ".txt";
		String unmappedControlsFileNameTemp = controlMappingRelationsDataFileAppFolderTemp + 
				"[UnmappedControls](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
				+ "-" + relationTemp.androidPageFileName + ".txt";
		
		DataFileWrite.writeControlMappingRelations2DataFile(controlMappingRelationsFileNameTemp, relationTemp.matchElements);
		DataFileWrite.writeUnmappedControls2DataFile(unmappedControlsFileNameTemp, relationTemp.unmappedIOSElements_2, relationTemp.unmappedAndroidElements_2);
		
	}
	
	public void updateE2ERelationsFromDataFiles2Cache() {
		//update the information from Data File to the relation pointed by *relationTemp*
		
		IOSPage2AndroidPageRelation relationTemp = pageMappingRelations4AllAppsFromInitialFile.get(currentAppIndex).get(currentPageRelationIndex);
		
		String appNameTemp = this.allAppsInfo.get(currentAppIndex);
		String[] srcs = appNameTemp.split("/");
		appNameTemp = srcs[srcs.length-1];//this may cause basic error
		String controlMappingRelationsDataFileAppFolderTemp = this.mappingRelationDatabaseFolder + appNameTemp + "/";
		
		String controlMappingRelationsFileNameTemp = controlMappingRelationsDataFileAppFolderTemp + 
				"[Relation](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
				+ "-" + relationTemp.androidPageFileName + ".txt";
		String unmappedControlsFileNameTemp = controlMappingRelationsDataFileAppFolderTemp + 
				"[UnmappedControls](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
				+ "-" + relationTemp.androidPageFileName + ".txt";
		
		//read from Data File
		DataFileRead.readControlMappingRelationsFromDataFile(controlMappingRelationsFileNameTemp, relationTemp);
		DataFileRead.readUnmappedControlsFromDataFile(unmappedControlsFileNameTemp, relationTemp);
		
		
	}
	
	
	
	
	
	public void loadOneMappedSet4UnmappedControls() {
		
		if(!this.iterator4MapUnmappedControls.hasNext()) {
			System.out.println("========== no more unmapped controls in the couple of pages ==========");
			return ;
		}
		
		Entry<String, iOS2AndroidUnmappedElementSetRelation> entry = this.iterator4MapUnmappedControls.next();
		
		System.out.println("========== Type : " + entry.getKey() + " ==========");
		
		int lenIOS = entry.getValue().iOSElementsIndex.size();
		int lenAndroid = entry.getValue().androidElementsIndex.size();
		
		System.out.println("iOS: " + lenIOS + ", Android: " + lenAndroid);
		
		System.out.print("iOS - ");
		for(int k1 = 0; k1 < lenIOS; k1 ++) {
			
			List<Integer> controliOS = new ArrayList<Integer>();
			iOSXCUITestElement iosElementTemp = this.currentMappedPageRelation.unmappedIOSElements.get(entry.getValue().iOSElementsIndex.get(k1)).specifiedControl;
			controliOS.add(iosElementTemp.Ex); controliOS.add(iosElementTemp.Ey); 
			controliOS.add((iosElementTemp.Ex + iosElementTemp.Ewidth));
			controliOS.add((iosElementTemp.Ey + iosElementTemp.Eheight));
			this.mappingRelationDisplayFrame.inputRectsWithoutRelation(controliOS, MappingRelationDisplayFrame.INT_IOS_GUI_CONTROL, k1);
			System.out.print("[" + controliOS.get(0) + "," + controliOS.get(1) + "," + controliOS.get(2) + "," + controliOS.get(3) + "](" + iosElementTemp.EName +"," + iosElementTemp.ELabel + ") ");
			
		}
		System.out.println();
		
		System.out.print("Android - ");
		for(int k2 = 0; k2 < lenAndroid; k2 ++) {
			
			List<Integer> controlAndroid = new ArrayList<Integer>();
			AndroidGUIElement androidElementTemp = this.currentMappedPageRelation.unmappedAndroidElements.get(entry.getValue().androidElementsIndex.get(k2)).specifiedControl;
			controlAndroid.add(androidElementTemp.Ex1); controlAndroid.add(androidElementTemp.Ey1);
			controlAndroid.add(androidElementTemp.Ex2); controlAndroid.add(androidElementTemp.Ey2);
			this.mappingRelationDisplayFrame.inputRectsWithoutRelation(controlAndroid, MappingRelationDisplayFrame.INT_ANDROID_GUI_CONTROL, k2);
			
			System.out.print("[" + controlAndroid.get(0) + "," + controlAndroid.get(1) + "," + controlAndroid.get(2) + "," + controlAndroid.get(3) + "](" + androidElementTemp.EText +") ");
		}
		System.out.println();
		
	}
	
	
	
	
	
	
	
	/**
	 * Data Files Related
	 * */
	
	public void checkNewResourceFilesWhenInitializingWithDF() {
		
		//Try to find: new apps, new PM relations, but no new e2e relations (only occurs in this app, is not supported by the increment of resources)
		
		File dirFile = new File(this.basicFileFolder);
		if(!dirFile.isDirectory()) {
			if(dirFile.isFile()) {
				System.out.println("#################### Error: basic dir isFile ####################");
			}
			System.out.println("#################### Error: basic dir is not file or dir ####################");
			return ;
		}
		
		String[] fileList = dirFile.list();
		int lenDirFile = fileList.length;
		for(int i = 0; i < lenDirFile; i ++) {
			
			
			if(fileList[i].startsWith(STRING_APP_FOLDER_START)) {
				
				
				String appDirTemp = this.basicFileFolder + fileList[i] + "/";
				
				if(this.allAppsInfo.contains(appDirTemp)) {
					//this app exists, then check the changes of page mapping relations
					
					//System.out.println("############### Test ################");
					
					String iosDirTemp = appDirTemp + STRING_IOS_PAGE_FOLDER_MARK;
					String androidDirTemp = appDirTemp + STRING_ANDROID_PAGE_FOLDER_MARK;
					
					String appNameTemp = fileList[i];
					String controlMappingRelationsDataFileAppFolderTemp = this.mappingRelationDatabaseFolder + appNameTemp + "/";
					
					
					String iosNewPageName = null;
					String androidNewPageName = null;
					
					int appIndexTemp = -1;
					int lenAppList = this.allAppsInfo.size();
					for(int count = 0; count < lenAppList; count ++) {
						if(this.allAppsInfo.get(count).equals(appDirTemp)) {
							appIndexTemp = count;
							break;
						}
					}
					List<IOSPage2AndroidPageRelation> pMRList = this.pageMappingRelations4AllAppsFromInitialFile.get(appIndexTemp);
					
					List<IOSPage2AndroidPageRelation> deletedPMRList = new ArrayList<IOSPage2AndroidPageRelation>();
					File pMRDFDirTempFile = new File(controlMappingRelationsDataFileAppFolderTemp); 
					String[] pMRDFNameList = pMRDFDirTempFile.list();
					for(int cc = 0; cc < pMRDFNameList.length; cc ++) {
						if(pMRDFNameList[cc].contains("[*Deleted*]") && pMRDFNameList[cc].contains("[Relation]") ) {
							
							System.out.println("===== deal with [*Deleted*] =====");
							String pMRDFNameT = pMRDFNameList[cc].substring(0, pMRDFNameList[cc].lastIndexOf("."));
							String[] args4DelPMRs = pMRDFNameT.split("\\[Relation\\]");
							String[] args4DelPMRs_1 = args4DelPMRs[1].split("\\)");
							//System.out.println(pMRDFNameT + ", " + args4DelPMRs[0] + ", " + args4DelPMRs[1]);
							
							String[] args4DelPMRs_2 = args4DelPMRs_1[1].split("-");
							
							IOSPage2AndroidPageRelation p2pRT = new IOSPage2AndroidPageRelation();
							p2pRT.iOSPageFileName = args4DelPMRs_2[0];
							p2pRT.androidPageFileName = args4DelPMRs_2[1];
							deletedPMRList.add(p2pRT);
						}
					}
					
					
					
					File iosDirTempFile = new File(iosDirTemp);
					String[] iosDirTempFileList = iosDirTempFile.list();
					for(int j = 0; j < iosDirTempFileList.length; j ++) {
						if(iosDirTempFileList[j].endsWith(".xml") && !iosDirTempFileList[j].contains("_rootPage")) {
							String metaFileNameTemp = iosDirTempFileList[j].substring(0, iosDirTempFileList[j].lastIndexOf("."));
							if(isListContains(iosDirTempFileList, metaFileNameTemp+".png")) {
								if(this.isPMRelationListContainMetaFileName(pMRList, metaFileNameTemp, MappingRelationCheckerInvolveUser.INT_IOS_PAGE)
										//&& ()
										) {
									//do nothing
								}
								else {
									//label this ios page, add this PMR after checking android page
									iosNewPageName = metaFileNameTemp;
								}
							}
						}
					}
					
					File androidDirTempFile = new File(androidDirTemp);
					String[] androidDirTempFileList = androidDirTempFile.list();
					for(int j = 0; j < androidDirTempFileList.length; j ++) {
						if(androidDirTempFileList[j].endsWith(".xml") && !androidDirTempFileList[j].contains("_rootPage")) {
							String metaFileNameTemp = androidDirTempFileList[j].substring(0, androidDirTempFileList[j].lastIndexOf("."));
							if(isListContains(androidDirTempFileList, metaFileNameTemp+".png")) {
								if(this.isPMRelationListContainMetaFileName(pMRList, metaFileNameTemp, MappingRelationCheckerInvolveUser.INT_ANDROID_PAGE)) {
									//do nothing
								}
								else {
									//label this ios page, add this PMR after checking android page
									androidNewPageName = metaFileNameTemp;
								}
							}
						}
					}
					
					if((iosNewPageName != null) && (androidNewPageName != null) && (!this.isDeletedPMRelationListContainFileName(deletedPMRList, iosNewPageName, androidNewPageName))) {//add this PMR
						
						IOSPage2AndroidPageRelation p2pRelationTemp = new IOSPage2AndroidPageRelation();
						
						int idTemp = pMRList.get(pMRList.size() - 1).identifier + 1;
						p2pRelationTemp.identifier = idTemp;
						p2pRelationTemp.iOSPageFileName = iosNewPageName;
						p2pRelationTemp.androidPageFileName = androidNewPageName;
						

						//Control Mapping Relation Data File Name
						IOSPage2AndroidPageRelation relationTemp = p2pRelationTemp;
						String controlMappingRelationsFileNameTemp = "[Relation](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
								+ "-" + relationTemp.androidPageFileName + ".txt";
						String unmappedControlsFileNameTemp = "[UnmappedControls](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
								+ "-" + relationTemp.androidPageFileName + ".txt";
						
						//Set the page information
						String iosPageFileName = relationTemp.iOSPageFileName + ".xml";
						String androidPageFileName = relationTemp.androidPageFileName + ".xml";
						String iosPageFileNameFullPath = appDirTemp + STRING_IOS_PAGE_FOLDER_MARK + iosPageFileName;
						String androidPageFileNameFullPath = appDirTemp + STRING_ANDROID_PAGE_FOLDER_MARK + androidPageFileName;
						iOSXCUITestPage iosPageTemp = GUIPageXMLFileReader.readiOSPageXMLFile(iosPageFileNameFullPath);
						relationTemp.iOSPage = iosPageTemp;
						AndroidGUIPage androidPageTemp = GUIPageXMLFileReader.readAndroidPageXMLFile(androidPageFileNameFullPath);
						relationTemp.androidPage = androidPageTemp;
						
						if((iosPageTemp == null)||(androidPageTemp == null)) {
							System.out.println("iosPageTemp == null or androidPageTemp == null");
							relationTemp.matchElements = new ArrayList<IOSElement2AndroidElementRelation>();
							relationTemp.unmappedIOSElements_2 = new ArrayList<iOSXCUITestElement>();
							relationTemp.unmappedAndroidElements_2 = new ArrayList<AndroidGUIElement>();
							continue;
						}
						
						//Mapped Controls
						List<IOSElement2AndroidElementRelation> elementRelationListTemp = MapControlsFromMappedCorrespondingGUIPages.calculateReliableControls4TwoPages(iosPageTemp, androidPageTemp);
						relationTemp.matchElements = elementRelationListTemp;
						DataFileWrite.writeControlMappingRelations2DataFile(controlMappingRelationsDataFileAppFolderTemp+controlMappingRelationsFileNameTemp, elementRelationListTemp);
						
						//Unmapped Controls
						MapControlsFromMappedCorrespondingGUIPages.calculate2SetUnmappedElements_2(relationTemp, iosPageTemp, androidPageTemp, elementRelationListTemp);
						DataFileWrite.writeUnmappedControls2DataFile(controlMappingRelationsDataFileAppFolderTemp+unmappedControlsFileNameTemp, relationTemp.unmappedIOSElements_2, relationTemp.unmappedAndroidElements_2);
						
						pMRList.add(p2pRelationTemp);
						//update p2pRelation DF
						DataFileWrite.writePageMappingRelations2DataFile(this.pageMappingRelationDatabaseFile, this.pageMappingRelations4AllAppsFromInitialFile, this.allAppsInfo);
					}
					
					
					
					
				}
				else {
					System.out.println();
					
					//this app does not exist before
					String iosDirTemp = appDirTemp + STRING_IOS_PAGE_FOLDER_MARK;
					String androidDirTemp = appDirTemp + STRING_ANDROID_PAGE_FOLDER_MARK;
					
					this.allAppsInfo.add(appDirTemp);
					List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = null;
					
					File iosDirTempFile = new File(iosDirTemp);
					List<String> iosDirTempFileSelectedList = new ArrayList<String>();
					String[] iosDirTempFileList = iosDirTempFile.list();
					for(int j = 0; j < iosDirTempFileList.length; j ++) {
						if(iosDirTempFileList[j].endsWith(".xml") && !iosDirTempFileList[j].contains("_rootPage")) {
							String metaFileNameTemp = iosDirTempFileList[j].substring(0, iosDirTempFileList[j].lastIndexOf("."));
							if(isListContains(iosDirTempFileList, metaFileNameTemp+".png")) {
								iosDirTempFileSelectedList.add(metaFileNameTemp);
//								System.out.println(metaFileNameTemp);
							}
						}
					}
					
					File androidDirTempFile = new File(androidDirTemp);
					List<String> androidDirTempFileSelectedList = new ArrayList<String>();
					String[] androidDirTempFileList = androidDirTempFile.list();
					for(int j = 0; j < androidDirTempFileList.length; j ++) {
						if(androidDirTempFileList[j].endsWith(".xml") && !androidDirTempFileList[j].contains("_rootPage")) {
							String metaFileNameTemp = androidDirTempFileList[j].substring(0, androidDirTempFileList[j].lastIndexOf("."));
							if(isListContains(androidDirTempFileList, metaFileNameTemp+".png")) {
								androidDirTempFileSelectedList.add(metaFileNameTemp);
//								System.out.println(metaFileNameTemp);
							}
						}
					}
					
					//Read data from resources and store in cache
					pageMappingRelationListTemp = constructPageMappingRelationsFromFileNameLists(iosDirTempFileSelectedList, androidDirTempFileSelectedList);
					this.pageMappingRelations4AllAppsFromInitialFile.add(pageMappingRelationListTemp);
					
					//add the PMRs of the new App to the PMR DataFile
					DataFileWrite.addPageMappingRelationFromOneNewApp2DataFile(this.pageMappingRelationDatabaseFile, pageMappingRelationListTemp, appDirTemp);
					
					
					//Construct the e2eRelation data files (includeing directory)
					String appNameTemp = fileList[i];
					String controlMappingRelationsDataFileAppFolderTemp = this.mappingRelationDatabaseFolder + appNameTemp + "/";
					
					File fileTemp = new File(controlMappingRelationsDataFileAppFolderTemp);
					if(!fileTemp.getParentFile().exists()) {fileTemp.getParentFile().mkdirs();}
					fileTemp.mkdir();
					
					int lenPageMappingRelationList = pageMappingRelationListTemp.size();
					for(int j = 0; j < lenPageMappingRelationList; j ++) {
						
						//Control Mapping Relation Data File Name
						IOSPage2AndroidPageRelation relationTemp = pageMappingRelationListTemp.get(j);
						String controlMappingRelationsFileNameTemp = "[Relation](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
								+ "-" + relationTemp.androidPageFileName + ".txt";
						String unmappedControlsFileNameTemp = "[UnmappedControls](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
								+ "-" + relationTemp.androidPageFileName + ".txt";
						
						//Set the page information
						String iosPageFileName = pageMappingRelationListTemp.get(j).iOSPageFileName + ".xml";
						String androidPageFileName = pageMappingRelationListTemp.get(j).androidPageFileName + ".xml";
						String iosPageFileNameFullPath = appDirTemp + STRING_IOS_PAGE_FOLDER_MARK + iosPageFileName;
						String androidPageFileNameFullPath = appDirTemp + STRING_ANDROID_PAGE_FOLDER_MARK + androidPageFileName;
						iOSXCUITestPage iosPageTemp = GUIPageXMLFileReader.readiOSPageXMLFile(iosPageFileNameFullPath);
						pageMappingRelationListTemp.get(j).iOSPage = iosPageTemp;
						AndroidGUIPage androidPageTemp = GUIPageXMLFileReader.readAndroidPageXMLFile(androidPageFileNameFullPath);
						pageMappingRelationListTemp.get(j).androidPage = androidPageTemp;
						
						if((iosPageTemp == null)||(androidPageTemp == null)) {
							System.out.println("iosPageTemp == null or androidPageTemp == null");
							pageMappingRelationListTemp.get(j).matchElements = new ArrayList<IOSElement2AndroidElementRelation>();
							pageMappingRelationListTemp.get(j).unmappedIOSElements_2 = new ArrayList<iOSXCUITestElement>();
							pageMappingRelationListTemp.get(j).unmappedAndroidElements_2 = new ArrayList<AndroidGUIElement>();
							continue;
						}
						
						//Mapped Controls
						List<IOSElement2AndroidElementRelation> elementRelationListTemp = MapControlsFromMappedCorrespondingGUIPages.calculateReliableControls4TwoPages(iosPageTemp, androidPageTemp);
						pageMappingRelationListTemp.get(j).matchElements = elementRelationListTemp;
						DataFileWrite.writeControlMappingRelations2DataFile(controlMappingRelationsDataFileAppFolderTemp+controlMappingRelationsFileNameTemp, elementRelationListTemp);
						
						//Unmapped Controls
						MapControlsFromMappedCorrespondingGUIPages.calculate2SetUnmappedElements_2(relationTemp, iosPageTemp, androidPageTemp, elementRelationListTemp);
						DataFileWrite.writeUnmappedControls2DataFile(controlMappingRelationsDataFileAppFolderTemp+unmappedControlsFileNameTemp, relationTemp.unmappedIOSElements_2, relationTemp.unmappedAndroidElements_2);
					}
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	public void checkNewResourceFilesWhenInitializingWithManuallyCollectedDF() {
		
		//Try to find: new apps, new PM relations, but no new e2e relations (only occurs in this app, is not supported by the increment of resources)
		
		File dirFile = new File(this.basicFileFolder);
		if(!dirFile.isDirectory()) {
			if(dirFile.isFile()) {
				System.out.println("#################### Error: basic dir isFile ####################");
			}
			System.out.println("#################### Error: basic dir is not file or dir ####################");
			return ;
		}
		
		String[] fileList = dirFile.list();
		int lenDirFile = fileList.length;
		for(int i = 0; i < lenDirFile; i ++) {
			
			
			if(fileList[i].startsWith(STRING_APP_FOLDER_START)) {
				
				
				String appDirTemp = this.basicFileFolder + fileList[i] + "/";
				
				
				String iosDirTemp = appDirTemp + STRING_IOS_PAGE_FOLDER_MARK;
				String androidDirTemp = appDirTemp + STRING_ANDROID_PAGE_FOLDER_MARK;
				
				String appNameTemp = fileList[i];
				String controlMappingRelationsDataFileAppFolderTemp = this.mappingRelationDatabaseFolder + appNameTemp + "/";
				
				
				String iosNewPageName = null;
				String androidNewPageName = null;
				
				int appIndexTemp = -1;
				int lenAppList = this.allAppsInfo.size();
				for(int count = 0; count < lenAppList; count ++) {
					if(this.allAppsInfo.get(count).equals(appDirTemp)) {
						appIndexTemp = count;
						break;
					}
				}
				List<IOSPage2AndroidPageRelation> pMRList = this.pageMappingRelations4AllAppsFromInitialFile.get(appIndexTemp);
				
				
				if(this.allAppsInfo.contains(appDirTemp) && pMRList.size() > 0) {
					//this app exists, then check the changes of page mapping relations
					
					//System.out.println("############### Test ################");
					
//					String iosDirTemp = appDirTemp + STRING_IOS_PAGE_FOLDER_MARK;
//					String androidDirTemp = appDirTemp + STRING_ANDROID_PAGE_FOLDER_MARK;
//					
//					String appNameTemp = fileList[i];
//					String controlMappingRelationsDataFileAppFolderTemp = this.mappingRelationDatabaseFolder + appNameTemp + "/";
//					
//					
//					String iosNewPageName = null;
//					String androidNewPageName = null;
//					
//					int appIndexTemp = -1;
//					int lenAppList = this.allAppsInfo.size();
//					for(int count = 0; count < lenAppList; count ++) {
//						if(this.allAppsInfo.get(count).equals(appDirTemp)) {
//							appIndexTemp = count;
//							break;
//						}
//					}
//					List<IOSPage2AndroidPageRelation> pMRList = this.pageMappingRelations4AllAppsFromInitialFile.get(appIndexTemp);
					
					List<IOSPage2AndroidPageRelation> deletedPMRList = new ArrayList<IOSPage2AndroidPageRelation>();
					File pMRDFDirTempFile = new File(controlMappingRelationsDataFileAppFolderTemp); 
					String[] pMRDFNameList = pMRDFDirTempFile.list();
					for(int cc = 0; cc < pMRDFNameList.length; cc ++) {
						if(pMRDFNameList[cc].contains("[*Deleted*]") && pMRDFNameList[cc].contains("[Relation]") ) {
							
							System.out.println("===== deal with [*Deleted*] =====");
							String pMRDFNameT = pMRDFNameList[cc].substring(0, pMRDFNameList[cc].lastIndexOf("."));
							String[] args4DelPMRs = pMRDFNameT.split("\\[Relation\\]");
							String[] args4DelPMRs_1 = args4DelPMRs[1].split("\\)");
							//System.out.println(pMRDFNameT + ", " + args4DelPMRs[0] + ", " + args4DelPMRs[1]);
							
							String[] args4DelPMRs_2 = args4DelPMRs_1[1].split("-");
							
							IOSPage2AndroidPageRelation p2pRT = new IOSPage2AndroidPageRelation();
							p2pRT.iOSPageFileName = args4DelPMRs_2[0];
							p2pRT.androidPageFileName = args4DelPMRs_2[1];
							deletedPMRList.add(p2pRT);
						}
					}
					
					
					
					File iosDirTempFile = new File(iosDirTemp);
					String[] iosDirTempFileList = iosDirTempFile.list();
					for(int j = 0; j < iosDirTempFileList.length; j ++) {
						if(iosDirTempFileList[j].endsWith(".xml") && !iosDirTempFileList[j].contains("_rootPage")) {
							String metaFileNameTemp = iosDirTempFileList[j].substring(0, iosDirTempFileList[j].lastIndexOf("."));
							if(isListContains(iosDirTempFileList, metaFileNameTemp+".png")) {
								if(this.isPMRelationListContainMetaFileName(pMRList, metaFileNameTemp, MappingRelationCheckerInvolveUser.INT_IOS_PAGE)
										//&& ()
										) {
									//do nothing
								}
								else {
									//label this ios page, add this PMR after checking android page
									iosNewPageName = metaFileNameTemp;
								}
							}
						}
					}
					
					File androidDirTempFile = new File(androidDirTemp);
					String[] androidDirTempFileList = androidDirTempFile.list();
					for(int j = 0; j < androidDirTempFileList.length; j ++) {
						if(androidDirTempFileList[j].endsWith(".xml") && !androidDirTempFileList[j].contains("_rootPage")) {
							String metaFileNameTemp = androidDirTempFileList[j].substring(0, androidDirTempFileList[j].lastIndexOf("."));
							if(isListContains(androidDirTempFileList, metaFileNameTemp+".png")) {
								if(this.isPMRelationListContainMetaFileName(pMRList, metaFileNameTemp, MappingRelationCheckerInvolveUser.INT_ANDROID_PAGE)) {
									//do nothing
								}
								else {
									//label this ios page, add this PMR after checking android page
									androidNewPageName = metaFileNameTemp;
								}
							}
						}
					}
					
					if((iosNewPageName != null) 
							&& (androidNewPageName != null) 
							&& (!this.isDeletedPMRelationListContainFileName(deletedPMRList, iosNewPageName, androidNewPageName))
							&& (pMRList.size() > 0)
							) {//add this PMR
						
						IOSPage2AndroidPageRelation p2pRelationTemp = new IOSPage2AndroidPageRelation();
						System.out.println(" ========== pMRList.size(): " + pMRList.size() + " ========== ");
						int idTemp = pMRList.get(pMRList.size() - 1).identifier + 1;
						p2pRelationTemp.identifier = idTemp;
						p2pRelationTemp.iOSPageFileName = iosNewPageName;
						p2pRelationTemp.androidPageFileName = androidNewPageName;
						

						//Control Mapping Relation Data File Name
						IOSPage2AndroidPageRelation relationTemp = p2pRelationTemp;
						String controlMappingRelationsFileNameTemp = "[Relation](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
								+ "-" + relationTemp.androidPageFileName + ".txt";
						String unmappedControlsFileNameTemp = "[UnmappedControls](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
								+ "-" + relationTemp.androidPageFileName + ".txt";
						
						//Set the page information
						String iosPageFileName = relationTemp.iOSPageFileName + ".xml";
						String androidPageFileName = relationTemp.androidPageFileName + ".xml";
						String iosPageFileNameFullPath = appDirTemp + STRING_IOS_PAGE_FOLDER_MARK + iosPageFileName;
						String androidPageFileNameFullPath = appDirTemp + STRING_ANDROID_PAGE_FOLDER_MARK + androidPageFileName;
						iOSXCUITestPage iosPageTemp = GUIPageXMLFileReader.readiOSPageXMLFile(iosPageFileNameFullPath);
						relationTemp.iOSPage = iosPageTemp;
						AndroidGUIPage androidPageTemp = GUIPageXMLFileReader.readAndroidPageXMLFile(androidPageFileNameFullPath);
						relationTemp.androidPage = androidPageTemp;
						
						if((iosPageTemp == null)||(androidPageTemp == null)) {
							System.out.println("iosPageTemp == null or androidPageTemp == null");
							relationTemp.matchElements = new ArrayList<IOSElement2AndroidElementRelation>();
							relationTemp.unmappedIOSElements_2 = new ArrayList<iOSXCUITestElement>();
							relationTemp.unmappedAndroidElements_2 = new ArrayList<AndroidGUIElement>();
							continue;
						}
						
						
						
						//Mapped Controls
//						List<IOSElement2AndroidElementRelation> elementRelationListTemp = MapControlsFromMappedCorrespondingGUIPages.calculateReliableControls4TwoPages(iosPageTemp, androidPageTemp);
						List<IOSElement2AndroidElementRelation> elementRelationListTemp = new ArrayList<IOSElement2AndroidElementRelation>();
						relationTemp.matchElements = elementRelationListTemp;
						DataFileWrite.writeControlMappingRelations2DataFile(controlMappingRelationsDataFileAppFolderTemp+controlMappingRelationsFileNameTemp, elementRelationListTemp);
						
						//Unmapped Controls
						MapControlsFromMappedCorrespondingGUIPages.calculate2SetUnmappedElements_2(relationTemp, iosPageTemp, androidPageTemp, elementRelationListTemp);
						DataFileWrite.writeUnmappedControls2DataFile(controlMappingRelationsDataFileAppFolderTemp+unmappedControlsFileNameTemp, relationTemp.unmappedIOSElements_2, relationTemp.unmappedAndroidElements_2);
						
						pMRList.add(p2pRelationTemp);
						//update p2pRelation DF
						DataFileWrite.writePageMappingRelations2DataFile(this.pageMappingRelationDatabaseFile, this.pageMappingRelations4AllAppsFromInitialFile, this.allAppsInfo);
					}
					
					
					
					
				}
				else {
					//this app does not exist before
//					String iosDirTemp = appDirTemp + STRING_IOS_PAGE_FOLDER_MARK;
//					String androidDirTemp = appDirTemp + STRING_ANDROID_PAGE_FOLDER_MARK;
					
					this.allAppsInfo.add(appDirTemp);
					List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = null;
					
					File iosDirTempFile = new File(iosDirTemp);
					List<String> iosDirTempFileSelectedList = new ArrayList<String>();
					String[] iosDirTempFileList = iosDirTempFile.list();
					for(int j = 0; j < iosDirTempFileList.length; j ++) {
						if(iosDirTempFileList[j].endsWith(".xml") && !iosDirTempFileList[j].contains("_rootPage")) {
							String metaFileNameTemp = iosDirTempFileList[j].substring(0, iosDirTempFileList[j].lastIndexOf("."));
							if(isListContains(iosDirTempFileList, metaFileNameTemp+".png")) {
								iosDirTempFileSelectedList.add(metaFileNameTemp);
//								System.out.println(metaFileNameTemp);
							}
						}
					}
					
					File androidDirTempFile = new File(androidDirTemp);
					List<String> androidDirTempFileSelectedList = new ArrayList<String>();
					String[] androidDirTempFileList = androidDirTempFile.list();
					for(int j = 0; j < androidDirTempFileList.length; j ++) {
						if(androidDirTempFileList[j].endsWith(".xml") && !androidDirTempFileList[j].contains("_rootPage")) {
							String metaFileNameTemp = androidDirTempFileList[j].substring(0, androidDirTempFileList[j].lastIndexOf("."));
							if(isListContains(androidDirTempFileList, metaFileNameTemp+".png")) {
								androidDirTempFileSelectedList.add(metaFileNameTemp);
//								System.out.println(metaFileNameTemp);
							}
						}
					}
					
					//Read data from resources and store in cache
					pageMappingRelationListTemp = constructManuallyCollectedPageMappingRelationsFromFileNameLists(iosDirTempFileSelectedList, androidDirTempFileSelectedList);
					this.pageMappingRelations4AllAppsFromInitialFile.add(pageMappingRelationListTemp);
					
					//add the PMRs of the new App to the PMR DataFile
					DataFileWrite.addPageMappingRelationFromOneNewApp2DataFile(this.pageMappingRelationDatabaseFile, pageMappingRelationListTemp, appDirTemp);
					
					
					//Construct the e2eRelation data files (includeing directory)
//					String appNameTemp = fileList[i];
//					String controlMappingRelationsDataFileAppFolderTemp = this.mappingRelationDatabaseFolder + appNameTemp + "/";
					
					File fileTemp = new File(controlMappingRelationsDataFileAppFolderTemp);
					if(!fileTemp.getParentFile().exists()) {fileTemp.getParentFile().mkdirs();}
					fileTemp.mkdir();
					
					int lenPageMappingRelationList = pageMappingRelationListTemp.size();
					for(int j = 0; j < lenPageMappingRelationList; j ++) {
						
						//Control Mapping Relation Data File Name
						IOSPage2AndroidPageRelation relationTemp = pageMappingRelationListTemp.get(j);
						String controlMappingRelationsFileNameTemp = "[Relation](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
								+ "-" + relationTemp.androidPageFileName + ".txt";
						String unmappedControlsFileNameTemp = "[UnmappedControls](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
								+ "-" + relationTemp.androidPageFileName + ".txt";
						
						//Set the page information
						String iosPageFileName = pageMappingRelationListTemp.get(j).iOSPageFileName + ".xml";
						String androidPageFileName = pageMappingRelationListTemp.get(j).androidPageFileName + ".xml";
						String iosPageFileNameFullPath = appDirTemp + STRING_IOS_PAGE_FOLDER_MARK + iosPageFileName;
						String androidPageFileNameFullPath = appDirTemp + STRING_ANDROID_PAGE_FOLDER_MARK + androidPageFileName;
						iOSXCUITestPage iosPageTemp = GUIPageXMLFileReader.readiOSPageXMLFile(iosPageFileNameFullPath);
						pageMappingRelationListTemp.get(j).iOSPage = iosPageTemp;
						AndroidGUIPage androidPageTemp = GUIPageXMLFileReader.readAndroidPageXMLFile(androidPageFileNameFullPath);
						pageMappingRelationListTemp.get(j).androidPage = androidPageTemp;
						
						if((iosPageTemp == null)||(androidPageTemp == null)) {
							System.out.println("iosPageTemp == null or androidPageTemp == null");
							pageMappingRelationListTemp.get(j).matchElements = new ArrayList<IOSElement2AndroidElementRelation>();
							pageMappingRelationListTemp.get(j).unmappedIOSElements_2 = new ArrayList<iOSXCUITestElement>();
							pageMappingRelationListTemp.get(j).unmappedAndroidElements_2 = new ArrayList<AndroidGUIElement>();
							continue;
						}
						
						//Mapped Controls
//						List<IOSElement2AndroidElementRelation> elementRelationListTemp = MapControlsFromMappedCorrespondingGUIPages.calculateReliableControls4TwoPages(iosPageTemp, androidPageTemp);
						List<IOSElement2AndroidElementRelation> elementRelationListTemp = new ArrayList<IOSElement2AndroidElementRelation>();
						pageMappingRelationListTemp.get(j).matchElements = elementRelationListTemp;
						DataFileWrite.writeControlMappingRelations2DataFile(controlMappingRelationsDataFileAppFolderTemp+controlMappingRelationsFileNameTemp, elementRelationListTemp);
						
						//Unmapped Controls
						MapControlsFromMappedCorrespondingGUIPages.calculate2SetUnmappedElements_2(relationTemp, iosPageTemp, androidPageTemp, elementRelationListTemp);
						DataFileWrite.writeUnmappedControls2DataFile(controlMappingRelationsDataFileAppFolderTemp+unmappedControlsFileNameTemp, relationTemp.unmappedIOSElements_2, relationTemp.unmappedAndroidElements_2);
					}
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	public void initialAllRelationDatabaseFileWithExistingDataFile(String basicFileDir, String selectedExistingDF) {
		
		this.reviseDateStamp = "[Date " + LocalDateTime.now().format(formatter) + "]";
		
		String newPMRDFFolderName = "";
		
		String[] args = selectedExistingDF.split("_");
		
		if(args.length == 5) {//Existing DF has been revised.
			
			newPMRDFFolderName = selectedExistingDF.substring(0, selectedExistingDF.lastIndexOf("_")) + "_Revise" + this.reviseDateStamp + "/";
			System.out.println(newPMRDFFolderName);
			
		}
		else {// == 4
			
			newPMRDFFolderName = selectedExistingDF + "_Revise" + this.reviseDateStamp + "/";
			System.out.println(newPMRDFFolderName);
		}
		this.mappingRelationDatabaseFolder = basicFileDir + newPMRDFFolderName;
		this.pageMappingRelationDatabaseFile = this.mappingRelationDatabaseFolder + "PageMappingRelations" + ".txt";
		
		//rename!
		File fileTemp = new File(basicFileDir + selectedExistingDF + "/");
		fileTemp.renameTo(new File(this.mappingRelationDatabaseFolder));
		
		//Read from DF to cache
		//S1 App Names
		DataFileRead.readAppInformationAndPageMappingRelationsFromDataFile(this.pageMappingRelationDatabaseFile, this);
		
		//System.out.println(this.allAppsInfo.size() + "," + this.pageMappingRelations4AllAppsFromInitialFile.size());
		//this.printAllRelations();
		
		int allAppInfoLen = this.allAppsInfo.size();
		for(int i = 0; i < allAppInfoLen; i ++) {
			
			String appNameTemp = this.allAppsInfo.get(i);
			String[] srcs = appNameTemp.split("/");
			appNameTemp = srcs[srcs.length-1];//this may cause basic error
			String controlMappingRelationsDataFileAppFolderTemp = this.mappingRelationDatabaseFolder + appNameTemp + "/";
			
			int lenPageMappingRelationList = this.pageMappingRelations4AllAppsFromInitialFile.get(i).size();
			for(int j = 0; j < lenPageMappingRelationList; j ++) {
				
				//Control Mapping Relation Data File Name
				IOSPage2AndroidPageRelation relationTemp = this.pageMappingRelations4AllAppsFromInitialFile.get(i).get(j);
				String controlMappingRelationsFileNameTemp = "[Relation](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
						+ "-" + relationTemp.androidPageFileName + ".txt";
				String unmappedControlsFileNameTemp = "[UnmappedControls](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
						+ "-" + relationTemp.androidPageFileName + ".txt";
				
				//Set the page information
				String iosPageFileName = pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).iOSPageFileName + ".xml";
				String androidPageFileName = pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).androidPageFileName + ".xml";
				String iosPageFileNameFullPath = allAppsInfo.get(i) + STRING_IOS_PAGE_FOLDER_MARK + iosPageFileName;
				String androidPageFileNameFullPath = allAppsInfo.get(i) + STRING_ANDROID_PAGE_FOLDER_MARK + androidPageFileName;
				iOSXCUITestPage iosPageTemp = GUIPageXMLFileReader.readiOSPageXMLFile(iosPageFileNameFullPath);
				pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).iOSPage = iosPageTemp;
				AndroidGUIPage androidPageTemp = GUIPageXMLFileReader.readAndroidPageXMLFile(androidPageFileNameFullPath);
				pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).androidPage = androidPageTemp;
				
				if((iosPageTemp == null)||(androidPageTemp == null)) {
					System.out.println("iosPageTemp == null or androidPageTemp == null");
					pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).matchElements = new ArrayList<IOSElement2AndroidElementRelation>();
					pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).unmappedIOSElements_2 = new ArrayList<iOSXCUITestElement>();
					pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).unmappedAndroidElements_2 = new ArrayList<AndroidGUIElement>();
					continue;
				}
				
				//Mapped Controls and Unmapped Controls
				DataFileRead.readControlMappingRelationsFromDataFile(controlMappingRelationsDataFileAppFolderTemp + controlMappingRelationsFileNameTemp, relationTemp);
				DataFileRead.readUnmappedControlsFromDataFile(controlMappingRelationsDataFileAppFolderTemp + unmappedControlsFileNameTemp, relationTemp);
				
				
			}
			
		}
		
		System.out.println("Only initial with EDFs");
		this.printAllControlsInRelations();
		
		//Check new resources
		this.checkNewResourceFilesWhenInitializingWithDF();
		
		System.out.println("Initial with EDFs and the resource flies");
		this.printAllControlsInRelations();
		
		
	}
	
	public void initialAllManuallyCollectedRelationDatabaseFileWithExistingDataFile(String basicFileDir, String selectedExistingDF) {
		
		this.reviseDateStamp = "[Date " + LocalDateTime.now().format(formatter) + "]";
		
		String newPMRDFFolderName = "";
		
		String[] args = selectedExistingDF.split("_");
		
		if(args.length == 5) {//Existing DF has been revised.
			
			newPMRDFFolderName = selectedExistingDF.substring(0, selectedExistingDF.lastIndexOf("_")) + "_Revise" + this.reviseDateStamp + "/";
			System.out.println(newPMRDFFolderName);
			
		}
		else {// == 4
			
			newPMRDFFolderName = selectedExistingDF + "_Revise" + this.reviseDateStamp + "/";
			System.out.println(newPMRDFFolderName);
		}
		this.mappingRelationDatabaseFolder = basicFileDir + newPMRDFFolderName;
		this.pageMappingRelationDatabaseFile = this.mappingRelationDatabaseFolder + "PageMappingRelations" + ".txt";
		
		//rename!
		File fileTemp = new File(basicFileDir + selectedExistingDF + "/");
		fileTemp.renameTo(new File(this.mappingRelationDatabaseFolder));
		
		//Read from DF to cache
		//S1 App Names
		DataFileRead.readAppInformationAndPageMappingRelationsFromDataFile(this.pageMappingRelationDatabaseFile, this);
		
		for(int k = 0; k < this.pageMappingRelations4AllAppsFromInitialFile.size(); k ++) {
			System.out.println(this.allAppsInfo.get(k) + ", " + this.pageMappingRelations4AllAppsFromInitialFile.get(k).size());
		}
		
		
		//System.out.println(this.allAppsInfo.size() + "," + this.pageMappingRelations4AllAppsFromInitialFile.size());
		//this.printAllRelations();
		
		int allAppInfoLen = this.allAppsInfo.size();
		for(int i = 0; i < allAppInfoLen; i ++) {
			
			String appNameTemp = this.allAppsInfo.get(i);
			String[] srcs = appNameTemp.split("/");
			appNameTemp = srcs[srcs.length-1];//this may cause basic error
			String controlMappingRelationsDataFileAppFolderTemp = this.mappingRelationDatabaseFolder + appNameTemp + "/";
			
			int lenPageMappingRelationList = this.pageMappingRelations4AllAppsFromInitialFile.get(i).size();
			for(int j = 0; j < lenPageMappingRelationList; j ++) {
				
				//Control Mapping Relation Data File Name
				IOSPage2AndroidPageRelation relationTemp = this.pageMappingRelations4AllAppsFromInitialFile.get(i).get(j);
				String controlMappingRelationsFileNameTemp = "[Relation](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
						+ "-" + relationTemp.androidPageFileName + ".txt";
				String unmappedControlsFileNameTemp = "[UnmappedControls](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
						+ "-" + relationTemp.androidPageFileName + ".txt";
				
				//Set the page information
				String iosPageFileName = pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).iOSPageFileName + ".xml";
				String androidPageFileName = pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).androidPageFileName + ".xml";
				String iosPageFileNameFullPath = allAppsInfo.get(i) + STRING_IOS_PAGE_FOLDER_MARK + iosPageFileName;
				String androidPageFileNameFullPath = allAppsInfo.get(i) + STRING_ANDROID_PAGE_FOLDER_MARK + androidPageFileName;
				iOSXCUITestPage iosPageTemp = GUIPageXMLFileReader.readiOSPageXMLFile(iosPageFileNameFullPath);
				pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).iOSPage = iosPageTemp;
				AndroidGUIPage androidPageTemp = GUIPageXMLFileReader.readAndroidPageXMLFile(androidPageFileNameFullPath);
				pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).androidPage = androidPageTemp;
				
				if((iosPageTemp == null)||(androidPageTemp == null)) {
					System.out.println("iosPageTemp == null or androidPageTemp == null");
					pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).matchElements = new ArrayList<IOSElement2AndroidElementRelation>();
					pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).unmappedIOSElements_2 = new ArrayList<iOSXCUITestElement>();
					pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).unmappedAndroidElements_2 = new ArrayList<AndroidGUIElement>();
					continue;
				}
				
				//Mapped Controls and Unmapped Controls
				DataFileRead.readControlMappingRelationsFromDataFile(controlMappingRelationsDataFileAppFolderTemp + controlMappingRelationsFileNameTemp, relationTemp);
				DataFileRead.readUnmappedControlsFromDataFile(controlMappingRelationsDataFileAppFolderTemp + unmappedControlsFileNameTemp, relationTemp);
				
				
			}
			
		}
		
		System.out.println("Only initial with EDFs");
		this.printAllControlsInRelations();
		
		//Check new resources
		this.checkNewResourceFilesWhenInitializingWithManuallyCollectedDF();
		
		System.out.println("Initial with EDFs and the resource flies");
		this.printAllControlsInRelations();
		
		
	}
	
	
	
	public void initialControlMappingRelationDataFile(String basicFileDir) {
		
		if((this.mappingRelationDatabaseFolder == null) || (this.allAppsInfo == null) || (this.pageMappingRelations4AllAppsFromInitialFile == null) ) {
			System.out.println("Error: Page Mapping Relations database should be initialed before Control Mapping Relaiotns!");
			return ;
		}
		
		//
		
		int allAppInfoLen = this.allAppsInfo.size();
		for(int i = 0; i < allAppInfoLen; i ++) {
			
			String appNameTemp = this.allAppsInfo.get(i);
			String[] srcs = appNameTemp.split("/");
			appNameTemp = srcs[srcs.length-1];//this may cause basic error
			String controlMappingRelationsDataFileAppFolderTemp = this.mappingRelationDatabaseFolder + appNameTemp + "/";
			
			File fileTemp = new File(controlMappingRelationsDataFileAppFolderTemp);
			if(!fileTemp.getParentFile().exists()) {fileTemp.getParentFile().mkdirs();}
			fileTemp.mkdir();
			
			int lenPageMappingRelationList = this.pageMappingRelations4AllAppsFromInitialFile.get(i).size();
			for(int j = 0; j < lenPageMappingRelationList; j ++) {
				
				//Control Mapping Relation Data File Name
				IOSPage2AndroidPageRelation relationTemp = this.pageMappingRelations4AllAppsFromInitialFile.get(i).get(j);
				String controlMappingRelationsFileNameTemp = "[Relation](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
						+ "-" + relationTemp.androidPageFileName + ".txt";
				String unmappedControlsFileNameTemp = "[UnmappedControls](" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
						+ "-" + relationTemp.androidPageFileName + ".txt";
				
				//Set the page information
				String iosPageFileName = pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).iOSPageFileName + ".xml";
				String androidPageFileName = pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).androidPageFileName + ".xml";
				String iosPageFileNameFullPath = allAppsInfo.get(i) + STRING_IOS_PAGE_FOLDER_MARK + iosPageFileName;
				String androidPageFileNameFullPath = allAppsInfo.get(i) + STRING_ANDROID_PAGE_FOLDER_MARK + androidPageFileName;
				
				System.out.println("============ ios page name: " + iosPageFileNameFullPath + ", android page name: " + androidPageFileNameFullPath + " ============");
				
				iOSXCUITestPage iosPageTemp = GUIPageXMLFileReader.readiOSPageXMLFile(iosPageFileNameFullPath);
//				iOSXCUITestPage iosPageCopy = iOSXCUITestPage.traverse2CopyTree4LayoutRepresentationModel(iosPageTemp);
//				iOSXCUITestElement countParts_ios_main_window_subtree = iOSXCUITestPage.traverse2GetOutlineLevel04LayoutRepresentationModel(iosPageCopy);
//				iOSXCUITestElement mainWindow = countParts_ios_main_window_subtree;
//				iOSXMLHandlingTools.iOSXMLSourceCodeRemoveUselessLeafNodeEXBackgroundNode(mainWindow);
//				iOSXMLHandlingTools.iOSXMLSourceCodeCompressDuplicatedContainer(mainWindow);
//				iOSXMLHandlingTools.dealWithWholePageContainer(mainWindow);
//				iOSXMLHandlingTools.cutNotVisiableSubtree(mainWindow);
//				iOSXMLHandlingTools.predict4RemoveSplitingLines(mainWindow);
//				iosPageTemp.elementRoot = mainWindow;
				
				pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).iOSPage = iosPageTemp;
				
				AndroidGUIPage androidPageTemp = GUIPageXMLFileReader.readAndroidPageXMLFile(androidPageFileNameFullPath);
				
				AndroidGUIPage androidPageCopy = AndroidGUIPage.traverse2CopyTree4LayoutRepresentationModel(androidPageTemp);
//				AndroidGUIElement countParts_android_main_subtree = AndroidGUIPage.traverse2GetOutlineLevel04LayoutRepresentationModel(androidPageCopy);
//				AndroidGUIElement mainSubtree_android = countParts_android_main_subtree;
//				AndroidXMLHandlingTools.androidXMLSourceCodeRemoveUselessLeafNodeEXBackgroundNode(mainSubtree_android);
//				AndroidXMLHandlingTools.androidXMLSourceCodeCompressDuplicatedContainer(mainSubtree_android);
//				androidPageTemp.elementRoot = mainSubtree_android;
				
				pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).androidPage = androidPageTemp;
				
				
				if((iosPageTemp == null)||(androidPageTemp == null)) {
					System.out.println("iosPageTemp == null or androidPageTemp == null");
					pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).matchElements = new ArrayList<IOSElement2AndroidElementRelation>();
					pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).unmappedIOSElements_2 = new ArrayList<iOSXCUITestElement>();
					pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).unmappedAndroidElements_2 = new ArrayList<AndroidGUIElement>();
					continue;
				}
				
				//Mapped Controls
//				List<IOSElement2AndroidElementRelation> elementRelationListTemp = MapControlsFromMappedCorrespondingGUIPages.calculateReliableControls4TwoPages(iosPageTemp, androidPageTemp);
				List<IOSElement2AndroidElementRelation> elementRelationListTemp = new ArrayList<IOSElement2AndroidElementRelation>();
				pageMappingRelations4AllAppsFromInitialFile.get(i).get(j).matchElements = elementRelationListTemp;
				DataFileWrite.writeControlMappingRelations2DataFile(controlMappingRelationsDataFileAppFolderTemp+controlMappingRelationsFileNameTemp, elementRelationListTemp);
				
				//Unmapped Controls
				MapControlsFromMappedCorrespondingGUIPages.calculate2SetUnmappedElements_2(relationTemp, iosPageTemp, androidPageTemp, elementRelationListTemp);
				DataFileWrite.writeUnmappedControls2DataFile(controlMappingRelationsDataFileAppFolderTemp+unmappedControlsFileNameTemp, relationTemp.unmappedIOSElements_2, relationTemp.unmappedAndroidElements_2);
				
				
			}
			
		}
		
	}
	
	
	public void initialPageMappingRelationDatabaseFile(String basicFileDir) {
		
		this.dateStamp = "[Date " + LocalDateTime.now().format(formatter) + "]";
		
		this.mappingRelationDatabaseFolder = basicFileDir + "Mapping_Relation_Database_Initial" + this.dateStamp + "/";
		
		String pMRDFile = this.mappingRelationDatabaseFolder + "PageMappingRelations" + ".txt";
		this.pageMappingRelationDatabaseFile = pMRDFile;
		
		File file = new File(pMRDFile);
		
		if(!file.getParentFile().exists()) {file.getParentFile().mkdirs();}
//		if(!file.exists()) {
//			try {
////				file.createNewFile();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		File dirFile = new File(basicFileDir);
		if(!dirFile.isDirectory()) {
			if(dirFile.isFile()) {
				System.out.println("#################### Error: basic dir isFile ####################");
			}
			System.out.println("#################### Error: basic dir is not file or dir ####################");
			return ;
		}
		
		List<List<IOSPage2AndroidPageRelation>> pageMappingRelations4AllApps = new ArrayList<List<IOSPage2AndroidPageRelation>>();
		List<String> appsNameAndLocationInfo = new ArrayList<String>();
		
		String[] fileList = dirFile.list();
		int lenDirFile = fileList.length;
		for(int i = 0; i < lenDirFile; i ++) {
			
			if(fileList[i].startsWith(STRING_APP_FOLDER_START)) {
				
				
				String appDirTemp = basicFileDir + fileList[i] + "/";
				String iosDirTemp = appDirTemp + STRING_IOS_PAGE_FOLDER_MARK;
				String androidDirTemp = appDirTemp + STRING_ANDROID_PAGE_FOLDER_MARK;
				
				appsNameAndLocationInfo.add(appDirTemp);//include locations
				
				List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = null;
				
				File iosDirTempFile = new File(iosDirTemp);
				List<String> iosDirTempFileSelectedList = new ArrayList<String>();
				String[] iosDirTempFileList = iosDirTempFile.list();
				for(int j = 0; j < iosDirTempFileList.length; j ++) {
					if(iosDirTempFileList[j].endsWith(".xml") && !iosDirTempFileList[j].contains("_rootPage")) {
						String metaFileNameTemp = iosDirTempFileList[j].substring(0, iosDirTempFileList[j].lastIndexOf("."));
						if(isListContains(iosDirTempFileList, metaFileNameTemp+".png")) {
							iosDirTempFileSelectedList.add(metaFileNameTemp);
//							System.out.println(metaFileNameTemp);
						}
					}
				}
				
				File androidDirTempFile = new File(androidDirTemp);
				List<String> androidDirTempFileSelectedList = new ArrayList<String>();
				String[] androidDirTempFileList = androidDirTempFile.list();
				for(int j = 0; j < androidDirTempFileList.length; j ++) {
					if(androidDirTempFileList[j].endsWith(".xml") && !androidDirTempFileList[j].contains("_rootPage")) {
						String metaFileNameTemp = androidDirTempFileList[j].substring(0, androidDirTempFileList[j].lastIndexOf("."));
						if(isListContains(androidDirTempFileList, metaFileNameTemp+".png")) {
							androidDirTempFileSelectedList.add(metaFileNameTemp);
//							System.out.println(metaFileNameTemp);
						}
					}
				}
				
				pageMappingRelationListTemp = constructPageMappingRelationsFromFileNameLists(iosDirTempFileSelectedList, androidDirTempFileSelectedList);
				pageMappingRelations4AllApps.add(pageMappingRelationListTemp);
				
			}
			
		}
		
		this.allAppsInfo = appsNameAndLocationInfo;
		this.pageMappingRelations4AllAppsFromInitialFile = pageMappingRelations4AllApps;
		
		DataFileWrite.writePageMappingRelations2DataFile(pMRDFile, pageMappingRelations4AllApps, appsNameAndLocationInfo);
		
		
	}
	
	public void initialManuallyCollectedPageMappingRelationDatabaseFile(String basicFileDir) {
		
		this.dateStamp = "[Date " + LocalDateTime.now().format(formatter) + "]";
		
		this.mappingRelationDatabaseFolder = basicFileDir + "Mapping_Relation_Database_Initial" + this.dateStamp + "/";
		
		String pMRDFile = this.mappingRelationDatabaseFolder + "PageMappingRelations" + ".txt";
		this.pageMappingRelationDatabaseFile = pMRDFile;
		
		File file = new File(pMRDFile);
		
		if(!file.getParentFile().exists()) {file.getParentFile().mkdirs();}
//		if(!file.exists()) {
//			try {
////				file.createNewFile();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		File dirFile = new File(basicFileDir);
		if(!dirFile.isDirectory()) {
			if(dirFile.isFile()) {
				System.out.println("#################### Error: basic dir isFile ####################");
			}
			System.out.println("#################### Error: basic dir is not file or dir ####################");
			return ;
		}
		
		List<List<IOSPage2AndroidPageRelation>> pageMappingRelations4AllApps = new ArrayList<List<IOSPage2AndroidPageRelation>>();
		List<String> appsNameAndLocationInfo = new ArrayList<String>();
		
		String[] fileList = dirFile.list();
		int lenDirFile = fileList.length;
		for(int i = 0; i < lenDirFile; i ++) {
			
			if(fileList[i].startsWith(STRING_APP_FOLDER_START)) {
				
				
				String appDirTemp = basicFileDir + fileList[i] + "/";
				String iosDirTemp = appDirTemp + STRING_IOS_PAGE_FOLDER_MARK;
				String androidDirTemp = appDirTemp + STRING_ANDROID_PAGE_FOLDER_MARK;
				
				appsNameAndLocationInfo.add(appDirTemp);//include locations
				
				List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = null;
				
				File iosDirTempFile = new File(iosDirTemp);
				List<String> iosDirTempFileSelectedList = new ArrayList<String>();
				String[] iosDirTempFileList = iosDirTempFile.list();
				for(int j = 0; j < iosDirTempFileList.length; j ++) {
					if(iosDirTempFileList[j].endsWith(".xml") && !iosDirTempFileList[j].contains("_rootPage")) {
						String metaFileNameTemp = iosDirTempFileList[j].substring(0, iosDirTempFileList[j].lastIndexOf("."));
						if(isListContains(iosDirTempFileList, metaFileNameTemp+".png")) {
							iosDirTempFileSelectedList.add(metaFileNameTemp);
//							System.out.println(metaFileNameTemp);
						}
					}
				}
				
				File androidDirTempFile = new File(androidDirTemp);
				List<String> androidDirTempFileSelectedList = new ArrayList<String>();
				String[] androidDirTempFileList = androidDirTempFile.list();
				for(int j = 0; j < androidDirTempFileList.length; j ++) {
					if(androidDirTempFileList[j].endsWith(".xml") && !androidDirTempFileList[j].contains("_rootPage")) {
						String metaFileNameTemp = androidDirTempFileList[j].substring(0, androidDirTempFileList[j].lastIndexOf("."));
						if(isListContains(androidDirTempFileList, metaFileNameTemp+".png")) {
							androidDirTempFileSelectedList.add(metaFileNameTemp);
//							System.out.println(metaFileNameTemp);
						}
					}
				}
				
				pageMappingRelationListTemp = constructManuallyCollectedPageMappingRelationsFromFileNameLists(iosDirTempFileSelectedList, androidDirTempFileSelectedList);
				pageMappingRelations4AllApps.add(pageMappingRelationListTemp);
				
			}
			
		}
		
		this.allAppsInfo = appsNameAndLocationInfo;
		this.pageMappingRelations4AllAppsFromInitialFile = pageMappingRelations4AllApps;
		
		DataFileWrite.writePageMappingRelations2DataFile(pMRDFile, pageMappingRelations4AllApps, appsNameAndLocationInfo);
		
		
	}
	
	
	
	
	
	public static List<IOSPage2AndroidPageRelation> constructPageMappingRelationsFromFileNameLists(List<String> iosFileNameSelectedList, List<String> androidFileNameSelectedList) {
		
		List<IOSPage2AndroidPageRelation> resultIOSPage2AndroidPageRelations = new ArrayList<IOSPage2AndroidPageRelation>();
		
		String iOSFileNameTemp = "";
		String androidFileNameTemp = "";
		
		
		
		int identifier = 0;
		while(identifier < 25) {//this parameter is related with the countMax of the page colletcting process
			
			IOSPage2AndroidPageRelation relationTemp = new IOSPage2AndroidPageRelation();
			relationTemp.identifier = identifier;
			
			iOSFileNameTemp = "";
			
			int leniOS = iosFileNameSelectedList.size();
			for(int i = 0; i < leniOS; i ++) {
				String temp = iosFileNameSelectedList.get(i);
				String[] args = temp.split("_");
				if((args.length > 1)&&(Integer.parseInt(args[0]) == identifier)) {
					iOSFileNameTemp = temp;
				}
				
			}
			
			androidFileNameTemp = "";
			
			int lenAndroid = androidFileNameSelectedList.size();
			for(int i = 0; i < lenAndroid; i ++) {
				String temp = androidFileNameSelectedList.get(i);
				String[] args = temp.split("_");
				if((args.length > 1)&&(Integer.parseInt(args[0]) == identifier)) {
					androidFileNameTemp = temp;
				}
				
			}
			
			if((!iOSFileNameTemp.equals("")) && (!androidFileNameTemp.equals(""))) {
				relationTemp.iOSPageFileName = iOSFileNameTemp;
				relationTemp.androidPageFileName = androidFileNameTemp;
				resultIOSPage2AndroidPageRelations.add(relationTemp);
			}
			
			identifier ++;
		}
		
		
		IOSPage2AndroidPageRelation relationRootPage = new IOSPage2AndroidPageRelation();
		relationRootPage.iOSPageFileName = null;
		relationRootPage.androidPageFileName  = null;
		relationRootPage.identifier = identifier;
		
		int leniOS = iosFileNameSelectedList.size();
		for(int i = 0; i < leniOS; i ++) {
			String temp = iosFileNameSelectedList.get(i);
			if(temp.equals("rootPage")) {
				relationRootPage.iOSPageFileName = temp;
			}
		}
		int lenAndroid = androidFileNameSelectedList.size();
		for(int i = 0; i < lenAndroid; i ++) {
			String temp = androidFileNameSelectedList.get(i);
			if(temp.equals("rootPage")) {
				relationRootPage.androidPageFileName = temp;
			}
		}
		if((relationRootPage.iOSPageFileName != null)||(relationRootPage.androidPageFileName != null) ) {
			resultIOSPage2AndroidPageRelations.add(relationRootPage);
		}
		
		
		return resultIOSPage2AndroidPageRelations;
	}
	
	public static List<IOSPage2AndroidPageRelation> constructManuallyCollectedPageMappingRelationsFromFileNameLists(List<String> iosFileNameSelectedList, List<String> androidFileNameSelectedList) {
		
		List<IOSPage2AndroidPageRelation> resultIOSPage2AndroidPageRelations = new ArrayList<IOSPage2AndroidPageRelation>();
		
		String iOSFileNameTemp = "";
		String androidFileNameTemp = "";
		
		
		
		int identifier = 0;
		while(identifier < 200) {//this parameter is related with the countMax of the page colletcting process
			
			IOSPage2AndroidPageRelation relationTemp = new IOSPage2AndroidPageRelation();
			relationTemp.identifier = identifier;
			
			iOSFileNameTemp = "";
			
			int leniOS = iosFileNameSelectedList.size();
			for(int i = 0; i < leniOS; i ++) {
//				String temp = iosFileNameSelectedList.get(i);
//				String[] args = temp.split(".");
//				if((args.length > 1)&&(Integer.parseInt(args[0]) == identifier)) {
//					iOSFileNameTemp = temp;
//				}
				if(Integer.parseInt(iosFileNameSelectedList.get(i)) == identifier) {
					iOSFileNameTemp = iosFileNameSelectedList.get(i);
				}
				
			}
			
			androidFileNameTemp = "";
			
			int lenAndroid = androidFileNameSelectedList.size();
			for(int i = 0; i < lenAndroid; i ++) {
//				String temp = androidFileNameSelectedList.get(i);
//				String[] args = temp.split(".");
//				if((args.length > 1)&&(Integer.parseInt(args[0]) == identifier)) {
//					androidFileNameTemp = temp;
//				}
				if(Integer.parseInt(androidFileNameSelectedList.get(i)) == identifier) {
					androidFileNameTemp = androidFileNameSelectedList.get(i);
				}
			}
			
			if((!iOSFileNameTemp.equals("")) && (!androidFileNameTemp.equals(""))) {
				relationTemp.iOSPageFileName = iOSFileNameTemp;
				relationTemp.androidPageFileName = androidFileNameTemp;
				resultIOSPage2AndroidPageRelations.add(relationTemp);
			}
			
			identifier ++;
		}
		
		
		//Actually, do not work in this function
		IOSPage2AndroidPageRelation relationRootPage = new IOSPage2AndroidPageRelation();
		relationRootPage.iOSPageFileName = null;
		relationRootPage.androidPageFileName  = null;
		relationRootPage.identifier = identifier;
		
		int leniOS = iosFileNameSelectedList.size();
		for(int i = 0; i < leniOS; i ++) {
			String temp = iosFileNameSelectedList.get(i);
			if(temp.equals("rootPage")) {
				relationRootPage.iOSPageFileName = temp;
			}
		}
		int lenAndroid = androidFileNameSelectedList.size();
		for(int i = 0; i < lenAndroid; i ++) {
			String temp = androidFileNameSelectedList.get(i);
			if(temp.equals("rootPage")) {
				relationRootPage.androidPageFileName = temp;
			}
		}
		if((relationRootPage.iOSPageFileName != null)||(relationRootPage.androidPageFileName != null) ) {
			resultIOSPage2AndroidPageRelations.add(relationRootPage);
		}
		
		
		return resultIOSPage2AndroidPageRelations;
	}
	
	
	
	public static boolean isListContains(String[] stringList, String target) {
		int len = stringList.length;
		for(int i = 0; i < len; i ++) {
			if(target.equals(stringList[i])) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isDeletedPMRelationListContainFileName(List<IOSPage2AndroidPageRelation> DelPMRList, String iosPF, String androidPF) {
		
		int len = DelPMRList.size();
		for(int i = 0; i < len; i ++) {
			
			if((DelPMRList.get(i).iOSPageFileName.equals(iosPF))&&(DelPMRList.get(i).androidPageFileName.equals(androidPF)) ) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public boolean isPMRelationListContainMetaFileName(List<IOSPage2AndroidPageRelation> pMRList, String metaFileName, int iOSorAndroid) {
		
		int len = pMRList.size();
		
		if(iOSorAndroid == MappingRelationCheckerInvolveUser.INT_IOS_PAGE) {
			for(int i = 0; i < len; i ++) {
				if(pMRList.get(i).iOSPageFileName.equals(metaFileName)) {
					return true;
				}
			}
		}
		else {
			for(int i = 0; i < len; i ++) {
				if(pMRList.get(i).androidPageFileName.equals(metaFileName)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	
	public void printAllApp() {
		int l = this.allAppsInfo.size();
		for(int i = 0; i < l; i ++) {
			System.out.println(this.allAppsInfo.get(i));
		}
	}
	public void printAllRelations() {
		int l = this.pageMappingRelations4AllAppsFromInitialFile.size();
		for(int i = 0; i < l; i ++) {
			List<IOSPage2AndroidPageRelation> pMRListTemp = this.pageMappingRelations4AllAppsFromInitialFile.get(i);
			int ll = pMRListTemp.size();
			for(int j = 0; j < ll; j ++) {
				if(j == 0) {
					System.out.println(this.allAppsInfo.get(i));
				}
				System.out.println("    " + pMRListTemp.get(j).identifier + "," + pMRListTemp.get(j).iOSPageFileName + "," + pMRListTemp.get(j).androidPageFileName);
				
			}
			
			
		}
	}
	public void printAllControlsInRelations() {
		int l = this.pageMappingRelations4AllAppsFromInitialFile.size();
		for(int i = 0; i < l; i ++) {
			List<IOSPage2AndroidPageRelation> pMRListTemp = this.pageMappingRelations4AllAppsFromInitialFile.get(i);
			int ll = pMRListTemp.size();
			for(int j = 0; j < ll; j ++) {
				if(j == 0) {
					System.out.println(this.allAppsInfo.get(i));
				}
				
				System.out.println("    " + pMRListTemp.get(j).identifier + "," + pMRListTemp.get(j).iOSPageFileName + 
						"(" + pMRListTemp.get(j).matchElements.size() + ", ios " + pMRListTemp.get(j).unmappedIOSElements_2.size() + ", android " + pMRListTemp.get(j).unmappedAndroidElements_2.size() + ")," + 
						pMRListTemp.get(j).androidPageFileName);
				
			}
			
			
		}
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		String picName1 = "/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/Demo_April_2/AppName_eleme_2/iOS/rootPage.png";
		String picName2 = "/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/Demo_April_2/AppName_eleme_2/Android/rootPage.png";
		
		String picNameNode = "NodePIC.png";
		
		
		//Tool (version 1) default file directory
//		String basicFileDirName = "/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/Demo_April_2/";
		
		//Tool (version 2, for manually collected data) default file directory 
		String basicFileDirName = "/Users/jiruihua/Desktop/AppGUIMapping/MappedAppPages/TestSet_Demo_3_17_Apps/";
		
		
		
		
		
		MappingRelationCheckerInvolveUser b = new MappingRelationCheckerInvolveUser();
		
		b.startUI(basicFileDirName);
		
		
		
		
	}
}
