package org.ruihua.GUITrans.AppsGUITransformDLProj.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.AndroidGUIElement;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.AndroidGUIPage;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.iOSXCUITestElement;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.iOSXCUITestPage;
import org.ruihua.GUITrans.AppsGUITransformDLProj.UserInvolvementGUI4Mapping.IOSPage2AndroidElementRelationWithLabel;

public class DataFileWrite {
	
	public static void addPageMappingRelationFromOneNewApp2DataFile(String pMRDFile, List<IOSPage2AndroidPageRelation> pageMappingRelations4OneNewApp, String appNameAndLocationInfo) {
		
		File file = new File(pMRDFile);
		
		if(!file.exists()) {
			System.out.println("Error when initial with DF: no Page Mapping Relation Data File");
			return ;
		}
		
		String pageMappingData = "";
		
		pageMappingData = pageMappingData + "[AppPagesLocation] " + appNameAndLocationInfo + "\n";
		
		int lenPageMappingRelationTemp = pageMappingRelations4OneNewApp.size();
		
		for(int j = 0; j < lenPageMappingRelationTemp; j ++) {
			
			IOSPage2AndroidPageRelation relationTemp = pageMappingRelations4OneNewApp.get(j);
			
			pageMappingData = pageMappingData + "///////[Relations] (" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
					+ " V.S. " + relationTemp.androidPageFileName
					+ "\n";
			
			//Need to ensure the String could contain data long enough!
		}
		
		try {
			FileWriter fileWritter = new FileWriter(pMRDFile, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(pageMappingData);
			bufferWritter.flush();
			
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writePageMappingRelations2DataFile(String pMRDFile, List<List<IOSPage2AndroidPageRelation>> pageMappingRelations4AllApps, List<String> appsNameAndLocationInfo) {
		
		File file = new File(pMRDFile);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String pageMappingData = "";
		
		int lenApps = appsNameAndLocationInfo.size();
		
		for(int i = 0; i < lenApps; i ++) {
			
			pageMappingData = pageMappingData + "[AppPagesLocation] " + appsNameAndLocationInfo.get(i) + "\n";
			
			int lenPageMappingRelationTemp = pageMappingRelations4AllApps.get(i).size();
			
			for(int j = 0; j < lenPageMappingRelationTemp; j ++) {
				
				IOSPage2AndroidPageRelation relationTemp = pageMappingRelations4AllApps.get(i).get(j);
				
				pageMappingData = pageMappingData + "///////[Relations] (" + relationTemp.identifier + ")" + relationTemp.iOSPageFileName
						+ " V.S. " + relationTemp.androidPageFileName
						+ "\n";
				
				//Need to ensure the String could contain data long enough!
			}
			
		}
		
		try {
			FileWriter fileWritter = new FileWriter(pMRDFile, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(pageMappingData);
			bufferWritter.flush();
			
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void writeControlMappingRelations2DataFile(String controlMappingRelationsDataFile, List<IOSElement2AndroidElementRelation> elementRelationList) {
		
		File file = new File(controlMappingRelationsDataFile);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String controlMappingData = "";
		
		int lenControlMappingRelations = elementRelationList.size();
		for(int i = 0; i < lenControlMappingRelations; i ++) {
			
			controlMappingData = controlMappingData + "" + elementRelationList.get(i).iOSPageElement.EHead
					+ "[" + elementRelationList.get(i).iOSPageElement.locationTypeOrder + "] "
					+ "(" + elementRelationList.get(i).iOSPageElement.Ex + "," + elementRelationList.get(i).iOSPageElement.Ey + ")("
					+ (elementRelationList.get(i).iOSPageElement.Ex + elementRelationList.get(i).iOSPageElement.Ewidth) + ","
					+ (elementRelationList.get(i).iOSPageElement.Ey + elementRelationList.get(i).iOSPageElement.Eheight) + ")"
					+ " V.S. "
					+ elementRelationList.get(i).androidPageElement.EHead
					+ "[" + elementRelationList.get(i).androidPageElement.locationTypeOrder + "] "
					+ "(" + elementRelationList.get(i).androidPageElement.Ex1 + "," + elementRelationList.get(i).androidPageElement.Ey1 + ")("
					+ elementRelationList.get(i).androidPageElement.Ex2 + ","
					+ elementRelationList.get(i).androidPageElement.Ey2 + ")"
					+ "\n";
			
		}
		
		try {
			FileWriter fileWritter = new FileWriter(controlMappingRelationsDataFile, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(controlMappingData);
			bufferWritter.flush();
			
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writeUnmappedControls2DataFile(String UnmappedControlsDataFile, List<iOSXCUITestElement> unmappediOSElements, List<AndroidGUIElement> unmappedAndroidElements) {
		
		File file = new File(UnmappedControlsDataFile);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String unmappedControlsData = "[iOS]\n";
		
		int lenUnmappediOSElements = unmappediOSElements.size();
		for(int i = 0; i < lenUnmappediOSElements; i ++) {
			unmappedControlsData = unmappedControlsData
					+ unmappediOSElements.get(i).EHead + "[" + unmappediOSElements.get(i).locationTypeOrder + "]"
					+ "(" + unmappediOSElements.get(i).Ex + "," + unmappediOSElements.get(i).Ey + ")("
					+ (unmappediOSElements.get(i).Ex + unmappediOSElements.get(i).Ewidth) + ","
					+ (unmappediOSElements.get(i).Ey + unmappediOSElements.get(i).Eheight) + ")"
					+ "\n";
		}
		
		
		unmappedControlsData = unmappedControlsData + "[Android]\n";
		
		int lenUnmappedAndroidElements = unmappedAndroidElements.size();
		for(int i = 0; i < lenUnmappedAndroidElements; i ++) {
			unmappedControlsData = unmappedControlsData
					+ unmappedAndroidElements.get(i).EHead + "[" + unmappedAndroidElements.get(i).locationTypeOrder + "]"
					+ "(" + unmappedAndroidElements.get(i).Ex1 + "," + unmappedAndroidElements.get(i).Ey1 + ")("
					+ unmappedAndroidElements.get(i).Ex2 + "," + unmappedAndroidElements.get(i).Ey2 + ")"
					+ "\n";
		}
		
		try {
			FileWriter fileWritter = new FileWriter(UnmappedControlsDataFile, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(unmappedControlsData);
			bufferWritter.flush();
			
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writeEasyTestSetWithoutLabelBasedonControlMappingRelations2File(String easyTestSetDataFile, List<String> allAppNameAndLocation, List<List<IOSPage2AndroidPageRelation>> pageMappingRelations4AllApps, List<String> easyTestSetDatainCache) {
		
		File file = new File(easyTestSetDataFile);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String testcaseData = "";
		String testcaseDataTemp = "";
		
		int allAppInfoLen = allAppNameAndLocation.size();
		for(int i = 0; i < allAppInfoLen; i ++) {
			
			String appNameTemp = allAppNameAndLocation.get(i);
			String[] srcs = appNameTemp.split("/");
			appNameTemp = srcs[srcs.length-1];//this may cause basic error
			
			int lenPageMappingRelationList = pageMappingRelations4AllApps.get(i).size();
			for(int j = 0; j < lenPageMappingRelationList; j ++) {
				
				//Control Mapping Relation Data in cache
				IOSPage2AndroidPageRelation relationTemp = pageMappingRelations4AllApps.get(i).get(j);
				
				//Set the page information
				String iosPageFileName = pageMappingRelations4AllApps.get(i).get(j).iOSPageFileName;
				String androidPageFileName = pageMappingRelations4AllApps.get(i).get(j).androidPageFileName;
				String iosPageFileNameFullPath = allAppNameAndLocation.get(i) + "iOS/" + iosPageFileName;
				String androidPageFileNameFullPath = allAppNameAndLocation.get(i) + "Android/" + androidPageFileName;
				
				List<IOSElement2AndroidElementRelation> elementRelationList = relationTemp.matchElements;
				
				int lenControlMappingRelations = elementRelationList.size();
				for(int k = 0; k < lenControlMappingRelations; k ++) {
					
					testcaseDataTemp = "" + elementRelationList.get(k).iOSPageElement.EHead
							+ "[" + elementRelationList.get(k).iOSPageElement.locationTypeOrder + "]"
							+ " "
							+ elementRelationList.get(k).androidPageElement.EHead
							+ "[" + elementRelationList.get(k).androidPageElement.locationTypeOrder + "]"
							+ " "
							+ appNameTemp + " "
							+ iosPageFileNameFullPath + " "
							+ androidPageFileNameFullPath
							+ "\n";
					 testcaseData = testcaseData + testcaseDataTemp;
					 easyTestSetDatainCache.add(testcaseDataTemp);
				}
				
			}
			
		}
		
		
		try {
			FileWriter fileWritter = new FileWriter(easyTestSetDataFile, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(testcaseData);
			bufferWritter.flush();
			
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public static void writeEasyTestSetWithLabelBasedonControlMappingRelations2DataFile(String easyTestSetDataFile, List<String> allAppNameAndLocation, List<List<IOSPage2AndroidPageRelation>> pageMappingRelations4AllApps, List<IOSPage2AndroidElementRelationWithLabel> e2eRelationListWithLabel) {
		
		File file = new File(easyTestSetDataFile);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String testcaseData = "";
		String testcaseDataTemp = "";
		
		
		
		int indexofe2eRelationListWithLabelList = 0;
		
		int allAppInfoLen = allAppNameAndLocation.size();
		for(int i = 0; i < allAppInfoLen; i ++) {
			
			String appNameTemp = allAppNameAndLocation.get(i);
			String[] srcs = appNameTemp.split("/");
			appNameTemp = srcs[srcs.length-1];//this may cause basic error
			
			int lenPageMappingRelationList = pageMappingRelations4AllApps.get(i).size();
			for(int j = 0; j < lenPageMappingRelationList; j ++) {
				
				//Control Mapping Relation Data in cache
				IOSPage2AndroidPageRelation relationTemp = pageMappingRelations4AllApps.get(i).get(j);
				
				//Set the page information
				String iosPageFileName = pageMappingRelations4AllApps.get(i).get(j).iOSPageFileName;
				String androidPageFileName = pageMappingRelations4AllApps.get(i).get(j).androidPageFileName;
				String iosPageFileNameFullPath = allAppNameAndLocation.get(i) + "iOS/" + iosPageFileName;
				String androidPageFileNameFullPath = allAppNameAndLocation.get(i) + "Android/" + androidPageFileName;
				
				List<IOSElement2AndroidElementRelation> elementRelationList = relationTemp.matchElements;
				
				int lenControlMappingRelations = elementRelationList.size();
				for(int k = 0; k < lenControlMappingRelations; k ++) {
					
					String rType = "";
					
					if(e2eRelationListWithLabel.get(indexofe2eRelationListWithLabelList).relationType != null) {
						rType = e2eRelationListWithLabel.get(indexofe2eRelationListWithLabelList).relationType;
					}
					
					testcaseDataTemp = "" + elementRelationList.get(k).iOSPageElement.EHead
							+ "[" + elementRelationList.get(k).iOSPageElement.locationTypeOrder + "]"
							+ " "
							+ elementRelationList.get(k).androidPageElement.EHead
							+ "[" + elementRelationList.get(k).androidPageElement.locationTypeOrder + "]"
							+ " "
							+ appNameTemp + " "
							+ iosPageFileNameFullPath + " "
							+ androidPageFileNameFullPath + " "
							+ rType
							+ "\n";
					 testcaseData = testcaseData + testcaseDataTemp;
					 
					 indexofe2eRelationListWithLabelList ++;
					 
				}
				
			}
			
		}
		
		
		try {
			FileWriter fileWritter = new FileWriter(easyTestSetDataFile, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(testcaseData);
			bufferWritter.flush();
			
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public static void writeCollectedIconsAsTrainSet2File(String trainSetDataFile, List<String> iconFileLocation, List<List<String>> iconTextInfo) {
		
		File file = new File(trainSetDataFile);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String trainingData = "";
		
		int lenItems = iconFileLocation.size();
		for(int i = 0; i < lenItems; i ++) {
			
			trainingData = trainingData + iconFileLocation.get(i) + ",";
			
			for(int j = 0; j < iconTextInfo.get(i).size()-1; j ++) {
				trainingData = trainingData + iconTextInfo.get(i).get(j) + " ";
			}
			trainingData = trainingData + iconTextInfo.get(i).get(iconTextInfo.get(i).size()-1) + "\n";

			try {
				FileWriter fileWritter = new FileWriter(trainSetDataFile, true);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				bufferWritter.write(trainingData);
				bufferWritter.flush();
				
				bufferWritter.close();
				fileWritter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			trainingData = "";
			
		}
		
	}
	
	
	
	
	public static void writeEasyTestSetWithLabelBasedonControlMappingRelations2File(String easyTestSetDataFile, List<String> easyTestSetDatainCache) {
		
		File file = new File(easyTestSetDataFile);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String testcaseData = "";
		int len = easyTestSetDatainCache.size();
		for(int i = 0; i < len; i ++) {
			testcaseData = testcaseData + easyTestSetDatainCache.get(i) + "\n";
		}
		
		try {
			FileWriter fileWritter = new FileWriter(easyTestSetDataFile, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(testcaseData);
			bufferWritter.flush();
			
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeEasyTestSetWithLabelBasedonControlMappingRelationsOneByOne2File(String easyTestSetDataFile, String easyTestCase) {
		
		File file = new File(easyTestSetDataFile);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String testcaseData = easyTestCase + "\n";
		
		try {
			FileWriter fileWritter = new FileWriter(easyTestSetDataFile, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(testcaseData);
			bufferWritter.flush();
			
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void writeImageCaptionTestInputJsonFile(String testInputJsonFile, List<String> androidControlImageLocation, List<List<Integer>> androidControlImageSize) {
		
		File file = new File(testInputJsonFile);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String testInputJsonData = "{";
		//first item (0th) is the ios control going to be mapped 
		String annotationsString = "\"annotations\": [";
		String imagesString = "\"images\": [";
		int len = androidControlImageLocation.size();
		for(int i = 0; i < len; i ++) {
			if( i < len - 1) {
				annotationsString = annotationsString + "{\"id\": " + i + ", \"caption\": \"" + androidControlImageLocation.get(i) + "\", \"image_id\": " + i + "}, ";
			}
			else {
				annotationsString = annotationsString + "{\"id\": " + i + ", \"caption\": \"" + androidControlImageLocation.get(i) + "\", \"image_id\": " + i + "}";
			}
			
			if( i < len - 1) {
				imagesString = imagesString + "{\"id\": " + i + ", \"filename\": \"" + androidControlImageLocation.get(i) + "\", \"height\": " + androidControlImageSize.get(i).get(0) + ", \"width\": " + androidControlImageSize.get(i).get(1) + "}, ";
			}
			else {
				imagesString = imagesString + "{\"id\": " + i + ", \"filename\": \"" + androidControlImageLocation.get(i) + "\", \"height\": " + androidControlImageSize.get(i).get(0) + ", \"width\": " + androidControlImageSize.get(i).get(1) + "}";
			}
			
		}
		
		annotationsString = annotationsString + "], ";
		imagesString = imagesString + "], ";
		
		testInputJsonData = testInputJsonData + annotationsString + imagesString;
		testInputJsonData = testInputJsonData + "\"info\": {\"description\": \"Accessibility dataset\"}}";
		try {
			FileWriter fileWritter = new FileWriter(testInputJsonFile, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(testInputJsonData);
			bufferWritter.flush();
			
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public static void writeImageCaptionTestInputJsonFile_WriteCaption(String testInputJsonFile, List<String> androidControlImageLocation, List<List<Integer>> androidControlImageSize, List<String> textInfos) {
		
		File file = new File(testInputJsonFile);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String testInputJsonData = "{";
		//first item (0th) is the ios control going to be mapped 
		String annotationsString = "\"annotations\": [";
		String imagesString = "\"images\": [";
		int len = androidControlImageLocation.size();
		for(int i = 0; i < len; i ++) {
			if( i < len - 1) {
				annotationsString = annotationsString + "{\"id\": " + i + ", \"caption\": \"";
				
				annotationsString = annotationsString + textInfos.get(i);
				
				annotationsString = annotationsString + "\", \"image_id\": " + i + "}, ";
			}
			else {
				annotationsString = annotationsString + "{\"id\": " + i + ", \"caption\": \"";
				
				annotationsString = annotationsString + textInfos.get(i);
				
				annotationsString = annotationsString + "\", \"image_id\": " + i + "}";
				
			}
			
			if( i < len - 1) {
				imagesString = imagesString + "{\"id\": " + i + ", \"filename\": \"" + androidControlImageLocation.get(i) + "\", \"height\": " + androidControlImageSize.get(i).get(0) + ", \"width\": " + androidControlImageSize.get(i).get(1) + "}, ";
			}
			else {
				imagesString = imagesString + "{\"id\": " + i + ", \"filename\": \"" + androidControlImageLocation.get(i) + "\", \"height\": " + androidControlImageSize.get(i).get(0) + ", \"width\": " + androidControlImageSize.get(i).get(1) + "}";
			}
			
		}
		
		annotationsString = annotationsString + "], ";
		imagesString = imagesString + "], ";
		
		testInputJsonData = testInputJsonData + annotationsString + imagesString;
		testInputJsonData = testInputJsonData + "\"info\": {\"description\": \"Accessibility dataset\"}}";
		try {
			FileWriter fileWritter = new FileWriter(testInputJsonFile, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(testInputJsonData);
			bufferWritter.flush();
			
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void writeComparisonTextInfo2File(String textInfoFile, List<String> iosControlTextInfo, List<List<String>> androidCandidateTextInfo) {
		
		File file = new File(textInfoFile);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String textInfoData = "[iOSControl]\n";
		int len_ios = iosControlTextInfo.size();
		for(int i = 0; i < len_ios; i ++) {
			
			textInfoData = textInfoData + iosControlTextInfo.get(i) + "\n";
		}
		int len_candidates = androidCandidateTextInfo.size();
		for(int i = 0; i < len_candidates; i ++) {
			textInfoData = textInfoData + "[AndroidCandidates] " + i + "\n";
			List<String> textInfoList = androidCandidateTextInfo.get(i);
			for(int j = 0; j < textInfoList.size(); j ++) {
				if(textInfoList.get(j).equals("")||textInfoList.get(j) == null) {
					
				}
				else {
					textInfoData = textInfoData + textInfoList.get(j) + "\n";
				}
			}
		}
		
		
		try {
			FileWriter fileWritter = new FileWriter(textInfoFile, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(textInfoData);
			bufferWritter.flush();
			
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
//	public static void writingPageMappingRelations2DataFile(String pMRDFile, IOSPage2AndroidPageRelation pageMappingRelation, String appNameAndLocationInfo) {
//	
//	
//	}
	
	
	public static void main(String[] args) {
		
		String fileName_0 = "/Users/jiruihua/Desktop/AppGUIMapping/MappedAppPages/TestSet_Demo_3/Mapping_Relation_Database_Initial[Date 2020-11-27 14:04:48]/TestSetWithoutLabel.txt";
		String fileName_1 = "/Users/jiruihua/Desktop/AppGUIMapping/MappedAppPages/TestSet_Demo_3/Mapping_Relation_Database_Initial[Date 2020-11-27 14:04:48]/TestSetWithLabelinProcess_manually.txt";
		
		File file = new File(fileName_0);
		BufferedReader reader = null;
		String tempString = null;
		String file_1_StringList = "";
		
		try {
		
			reader = new BufferedReader(new FileReader(file));
			
			while((tempString = reader.readLine()) != null) {
				
				System.out.println(tempString);
				
				if(tempString == "") {
					break;
				}
				file_1_StringList = file_1_StringList + tempString + " 0\n";
				
			}
			
			
		
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		File file_1 = new File(fileName_1);
		if(!file_1.exists()) {
			try {
				file_1.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			FileWriter fileWritter = new FileWriter(fileName_1, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(file_1_StringList);
			bufferWritter.flush();
			
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
