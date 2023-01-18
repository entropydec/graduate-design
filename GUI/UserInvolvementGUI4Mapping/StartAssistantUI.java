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

public class StartAssistantUI {
	
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
	
	
	
	
	public MappingRelationDisplayFrame4SupportCheckSceenShotComparison mappingRelationDisplayFrame;//Note! This member crossed with this class!
	
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
	
	
	public List<List<String>> testsetExecutionResultsDetails;
	public List<List<String>> testsetETCDetails;
	
	public int currentETCIndex = -1;
	
	
	
	
	/**
	 * Interaction with Frame Related
	 * */
	
	
	public void startUI(String basicFileDir) {
		this.mappingRelationDisplayFrame = new MappingRelationDisplayFrame4SupportCheckSceenShotComparison(STRING_UI_NAME);
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
	
	
	public boolean nextETCIndex() {
		currentETCIndex ++;
		if(currentETCIndex >= testsetETCDetails.size()) {
			currentETCIndex --;
			return false;
		}
		else {
			//do nothing
		}
		return true;
	}
	public boolean lastETCIndex() {
		currentETCIndex --;
		if(currentETCIndex < 0) {
			currentETCIndex ++;
			return false;
		}
		return true;
	}
	
	public String nextETC() {
		
//		System.out.println("*** nextETC ***");
		boolean nextLabel = this.nextETCIndex();
		String controlsInfo = null;
		if(nextLabel) {
			//input pics
			this.inputTwoMappedPages();
			//input controls
			controlsInfo = this.inputControlRectangles();
			//Set related text info
		}
		
		
		
		return controlsInfo;
	}
	
	public String lastETC() {
		
		boolean lastLabel = this.lastETCIndex();
		String controlsInfo = null;
		if(lastLabel) {
			this.inputTwoMappedPages();
			controlsInfo = this.inputControlRectangles();
		}
		
		return controlsInfo;
	}
	
	
	
	
	public void inputTwoMappedPages() {
		String iosPageName = testsetETCDetails.get(currentETCIndex).get(3);
		String androidPageName = testsetETCDetails.get(currentETCIndex).get(4);
		this.mappingRelationDisplayFrame.inputTwoPictures(iosPageName+".png", androidPageName+".png");
	}
	public String inputControlRectangles() {
		
		String result = "";
		
		String iosPageNamePath = testsetETCDetails.get(currentETCIndex).get(3);
		iOSXCUITestPage iosPage = GUIPageXMLFileReader.readiOSPageXMLFile(iosPageNamePath+".xml");
		String androidPageName = testsetETCDetails.get(currentETCIndex).get(4);
		AndroidGUIPage androidPage = GUIPageXMLFileReader.readAndroidPageXMLFile(androidPageName+".xml");
		
		//prepare for iOS control
		String iosControlTemp = testsetETCDetails.get(currentETCIndex).get(0);
		String[] iosControlTempSrc = iosControlTemp.split("\\[");
		String[] iosControlTempSrc2 = iosControlTempSrc[1].split("\\]");
		String iOSControlType = iosControlTempSrc[0];
		int iOSLocationOrder = Integer.parseInt(iosControlTempSrc2[0]);
		result = result + iOSControlType + "[" + iOSLocationOrder + "]: ";
		//iOS control
		iOSXCUITestElement iosElementTemp = iOSXCUITestPage.findPageTreeNodebyFileName(iosPage, iOSControlType, iOSLocationOrder);
		List<Integer> controliOS = new ArrayList<Integer>();
		controliOS.add(iosElementTemp.Ex); controliOS.add(iosElementTemp.Ey); 
		controliOS.add((iosElementTemp.Ex + iosElementTemp.Ewidth));
		controliOS.add((iosElementTemp.Ey + iosElementTemp.Eheight));
		this.mappingRelationDisplayFrame.inputRectAndItsColor(controliOS, MappingRelationDisplayFrame4SupportCheckSceenShotComparison.INT_IOS_GUI_CONTROL, 0, Color.green, 0);
		
		List<String> exeResultTemp = this.testsetExecutionResultsDetails.get(currentETCIndex);
		
		if(!exeResultTemp.get(0).equals("1")&&!exeResultTemp.get(0).equals("2")) {
			return "Not MultiRresults nor NotIllustration";
		}
		if(exeResultTemp.get(1).equals("-1")) {
			System.out.println("========= candidates do not contain the target (-1) =========");
			return "candidates do not contain the target (-1)";
		}
		int targetRank = Integer.parseInt(exeResultTemp.get(1));
		
		
		int len = exeResultTemp.size();
		System.out.println("currentETCIndex: " + currentETCIndex + ", exeResultTemp.get(0): " + exeResultTemp.get(0) + ", targetRank: " + targetRank + ", len: " + (len-2) + ", " + iosElementTemp.EHead + "[" + iosElementTemp.locationTypeOrder + "], iOS Text: " + iosElementTemp.ELabel + ", " + iosElementTemp.EName + ", " + iosElementTemp.EValue);
		for(int i = 2; i < len; i ++) {
			
			String[] androidControlTemp = exeResultTemp.get(i).split("\\[");
			String controlTypeTemp = androidControlTemp[0];
			int controlLoc = Integer.parseInt(androidControlTemp[1]);
			
			AndroidGUIElement ele = AndroidGUIPage.findPageTreeNodebyFileName(androidPage, controlTypeTemp, controlLoc);
			List<Integer> controlAndroid = new ArrayList<Integer>();
			controlAndroid.add(ele.Ex1); controlAndroid.add(ele.Ey1);
			controlAndroid.add(ele.Ex2); controlAndroid.add(ele.Ey2);
			
			if(targetRank + 2 == i) {
				this.mappingRelationDisplayFrame.inputRectAndItsColor(controlAndroid, MappingRelationDisplayFrame4SupportCheckSceenShotComparison.INT_ANDROID_GUI_CONTROL, 0, Color.red, i-2);
			}
			else {
				this.mappingRelationDisplayFrame.inputRectAndItsColor(controlAndroid, MappingRelationDisplayFrame4SupportCheckSceenShotComparison.INT_ANDROID_GUI_CONTROL, 0, Color.blue, i-2);
			}
			System.out.println("[///////](" + (i-2) + ")" + ele.EHead + "[" + ele.locationTypeOrder + "][" + ele.Ex1 + "," + ele.Ey1 + "][" + ele.Ex2 + "," + ele.Ey2 + "], " + ele.EText + ", " + ele.EContent_desc + ", " + ele.EResource_id);
			
			result = result + "(" + (i-2) + ")" + ele.EHead + "[" + ele.locationTypeOrder + "] "; 
		}
		System.out.println();
		
		return result;
	}
	
	
	public void readEasyTestCaseSetAndExeResultsIntoCache(String basicFileDirName, String easyTestCaseSetIndexFileWithLabelinProcess, String testsetExeResultDataFile) {
		
		
		List<String> testsetExeResults = DataFileRead.readTestSetDataExecutionResultFromDataFile(testsetExeResultDataFile);
		List<List<String>> testsetExeResultsAna = new ArrayList<List<String>>();
		for(int i = 0; i < testsetExeResults.size(); i ++) {
			testsetExeResultsAna.add(StartAssistantUI.analysisSingleTestExecutionResult(testsetExeResults.get(i)));
		}
		
		List<List<String>> testset = DataFileRead.readTestSetDataFromDataFile(easyTestCaseSetIndexFileWithLabelinProcess);
		
		this.testsetExecutionResultsDetails = testsetExeResultsAna;
		this.testsetETCDetails = testset;
		
		
		
		
		
		
		/*
		
		List<String> appNameList = new ArrayList<String>();
		
		List<String> methodProcess = new ArrayList<String>();
		List<IOSElement2AndroidElementRelation> mappedControls = new ArrayList<IOSElement2AndroidElementRelation>();
		
		
		int lenofTestSet = testset.size();
		
		for(int i = 0; i < lenofTestSet; i ++) {
			
			if(testsetExeResultsAna.get(i).get(0).equals("1")
					&& testsetExeResultsAna.get(i).get(0).equals("2")
					) {
				
			}
			
			System.out.println("===== Illustrating the " + i + "th test case Info =====");
			appNameList.add(testset.get(i).get(2));
			
			String controlTemp = testset.get(i).get(0);
			String[] controlTempSrc = controlTemp.split("\\[");
			String[] controlTempSrc2 = controlTempSrc[1].split("\\]");
			String iOSControlType = controlTempSrc[0];
			int iOSLocationOrder = Integer.parseInt(controlTempSrc2[0]);
			
			String controlTemp_a = testset.get(i).get(1);
			String[] controlTempSrc_a = controlTemp_a.split("\\[");
			String[] controlTempSrc2_a = controlTempSrc_a[1].split("\\]");
			String androidControlType = controlTempSrc_a[0];
			int androidLocationOrder = Integer.parseInt(controlTempSrc2_a[0]);
			
			String iosFileNamePathTemp = testset.get(i).get(3);
			String androidFileNamePathTemp = testset.get(i).get(4);
			
			
			
			
			
		}
		*/
		
		
		
		
		
	}
	
	
	public static List<String> analysisSingleTestExecutionResult(String resultData) {
		
		List<String> analysisResult = new ArrayList<String>();
		
		int dealMethodType = 0;
		
		if(resultData.contains("NotILLUSTRATED") && resultData.contains("SwitchControls")) {
			dealMethodType = 3;
		}
		else if(resultData.contains("NotILLUSTRATED") && !resultData.contains("SwitchControls")) {
			dealMethodType = 2;
		}
		else if(resultData.contains("MultiResults")) {
			dealMethodType = 1;
		}
		else {
			
		}
		
		analysisResult.add("" + dealMethodType);
		//currently we apply MethodType 1 and MethodType 2
		if((dealMethodType == 1) 
				||(dealMethodType == 2)
				) {
			
			int targetRank_Order = -1;
			String targetRank_Order_String = "-1";
			
			String[] srcs_TargetRank = resultData.split("TargetRank: ");
			if(srcs_TargetRank[1].startsWith("-1")) {
				analysisResult.add(targetRank_Order_String);
			}
			else {
				
				String[] srcs_TargetRank_Order = srcs_TargetRank[1].split(",");
				targetRank_Order_String = srcs_TargetRank_Order[0];
				targetRank_Order = Integer.parseInt(srcs_TargetRank_Order[0]);
				
				analysisResult.add(targetRank_Order_String);
				
				String[] srcs_candidatesRank_pre = srcs_TargetRank_Order[1].split("\\) ");
				String[] srcs_candidatesRank = srcs_candidatesRank_pre[1].split(" ");
				
				int lenCandidates = srcs_candidatesRank.length;
				for(int i = 0; i < lenCandidates - 1; i ++) {
					String[] candidateTemp =  srcs_candidatesRank[i].split("\\]");
					analysisResult.add(candidateTemp[0]);
				}
				
				if(targetRank_Order > 4) {//Maually Setting
					String[] tarTemp = srcs_candidatesRank_pre[0].split("\\(");
					String[] tarTempForm = tarTemp[1].split("]");
					analysisResult.add(tarTempForm[0]);
				}
				else {
					String[] candidateTemp =  srcs_candidatesRank[lenCandidates - 1].split("\\]");
					analysisResult.add(candidateTemp[0]);
				}
				
			}
			
		}
		
		
		return analysisResult;
		
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		//Test Set 2
		String basicFileDirName = "/Users/jiruihua/Desktop/AppGUIMapping/MappedAppPages/TestSet_Demo_2/";
		String currentFileFolder = "Mapping_Relation_Database_Initial[Date 2020-11-17 13:07:46]_Revise[Date 2020-11-17 17:33:02]/";
		String easyTestCaseSetIndexFileWithLabelinProcess = basicFileDirName + currentFileFolder + "TestSetWithLabelinProcess_manually.txt";
		
//		String testsetExeResultDataFile = basicFileDirName + "ControlScreenShotDirectory_D_112+32/" + "OutputLog[2020-11-26 19:31:44].txt";
//		String testsetExeResultDataFile = basicFileDirName + "ControlScreenShotDirectory_E_RemoveTextField_114+83/" + "OutputLog[2020-11-30 13:56:23].txt";
//		String testsetExeResultDataFile = basicFileDirName + "ControlScreenShotDirectory_F_114+83_110+31/" + "OutputLog[2020-11-30 18:55:15].txt";
//		String testsetExeResultDataFile = basicFileDirName + "ControlScreenShotDirectory_I_113+25_ScreenShotGetMethod_1/" + "OutputLog[2020-12-01 03:04:04].txt";
		
		String testsetExeResultDataFile = basicFileDirName + "ControlScreenShotDirectory_N_Completed_Apply_iC2AM/" + "OutputLog[2020-12-02 11:32:11].txt";
		
		
		
		StartAssistantUI b = new StartAssistantUI();
		
		b.readEasyTestCaseSetAndExeResultsIntoCache(basicFileDirName, easyTestCaseSetIndexFileWithLabelinProcess, testsetExeResultDataFile);
		
		b.startUI(basicFileDirName);
		
		
		
		
	}
}
