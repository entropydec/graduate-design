package org.ruihua.GUITrans.AppsGUITransformDLProj.GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUIPageXMLFileReader {

	public static iOSXCUITestPage readiOSPageXMLFile(String pageFileNameiOS) {
		
//		System.out.println(pageFileNameiOS);
		
		iOSXCUITestPage iOSPage = null;
		
		HashMap<String, Integer> elementCalculation = new HashMap<String, Integer>();
		
		File file = new File(pageFileNameiOS);
		BufferedReader reader = null;
		String tempString = null;
		
		
		try {
			iOSPage = new iOSXCUITestPage();
			
			reader = new BufferedReader(new FileReader(file));
			
			
			tempString = reader.readLine();
			String[] srcs = tempString.split("<");
			
			
			iOSXCUITestXMLFileHeadElement xmlFileHead = new iOSXCUITestXMLFileHeadElement();
			String[] srcs_1 = srcs[1].split(" ");
			xmlFileHead.EHead = srcs_1[0];//?xml
			String[] srcs_1_1 = srcs_1[1].split("=\"");
			String[] srcs_1_1_1 = srcs_1_1[1].split("\"");
			xmlFileHead.EVersion = srcs_1_1_1[0];//version
			String[] srcs_1_2 = srcs_1[2].split("=\"");
			String[] srcs_1_2_1 = srcs_1_2[1].split("\"");
			xmlFileHead.EEncoding = srcs_1_2_1[0];//encoding
			//EEnd?
			iOSPage.fileHead = xmlFileHead;
			
			iOSXCUITestAppiumAUTElement appiumAUT = new iOSXCUITestAppiumAUTElement();
			String[] srcs_2 = srcs[2].split(">");
			appiumAUT.EHead = srcs_2[0];
			//EEnd?
			iOSPage.appiumAUT = appiumAUT;
			
			iOSXCUITestElement pageRoot = new iOSXCUITestElement();
			String[] srcs_3 = srcs[3].split(" ");
			pageRoot.EHead = srcs_3[0];//<head...
			String[] srcs_3_type = srcs[3].split("type=\"");
			String[] srcs_3_type_1 = srcs_3_type[1].split("\"");
			pageRoot.EType = srcs_3_type_1[0];//type
			String[] srcs_3_name = srcs[3].split("name=\"");
			String[] srcs_3_name_1 = srcs_3_name[1].split("\"");
			pageRoot.EName = srcs_3_name_1[0];//name
			String[] srcs_3_label = srcs[3].split("label=\"");
			String[] srcs_3_label_1 = srcs_3_label[1].split("\"");
			pageRoot.ELabel = srcs_3_label_1[0];//label
			String[] srcs_3_enabled = srcs[3].split("enabled=\"");
			String[] srcs_3_enabled_1 = srcs_3_enabled[1].split("\"");
			pageRoot.EEnable = srcs_3_enabled_1[0];//enabled
			String[] srcs_3_visible = srcs[3].split("visible=\"");
			String[] srcs_3_visible_1 = srcs_3_visible[1].split("\"");
			pageRoot.EVisible = srcs_3_visible_1[0];//visible
			String[] srcs_3_x = srcs[3].split("x=\"");
			String[] srcs_3_x_1 = srcs_3_x[1].split("\"");
			pageRoot.EX = srcs_3_x_1[0];//x
			pageRoot.Ex = Integer.parseInt(pageRoot.EX);//x
			String[] srcs_3_y = srcs[3].split("y=\"");
			String[] srcs_3_y_1 = srcs_3_y[1].split("\"");
			pageRoot.EY = srcs_3_y_1[0];//y
			pageRoot.Ey = Integer.parseInt(pageRoot.EY);//y
			String[] srcs_3_width = srcs[3].split("width=\"");
			String[] srcs_3_width_1 = srcs_3_width[1].split("\"");
			pageRoot.EWidth = srcs_3_width_1[0];//width
			pageRoot.Ewidth = Integer.parseInt(pageRoot.EWidth);//width
			String[] srcs_3_height = srcs[3].split("height=\"");
			String[] srcs_3_height_1 = srcs_3_height[1].split("\"");
			pageRoot.EHeight = srcs_3_height_1[0];//height
			pageRoot.Eheight = Integer.parseInt(pageRoot.EHeight);//height
			String[] srcs_1_evalue = srcs[3].split("value=\"");
			if(srcs_1_evalue.length > 1) {
				String[] srcs_1_evalue_1 = srcs_1_evalue[1].split("\"");
				pageRoot.EValue = srcs_1_evalue_1[0];//value
			}
			//EEnd
			pageRoot.pageTreeLevel = 0;
			pageRoot.parent = null;
			
			iOSPage.pageWidth = pageRoot.Ewidth;
			iOSPage.pageHeight = pageRoot.Eheight;
			iOSPage.elementRoot = pageRoot;
			
			//iOSXCUITestElement.printElement(pageRoot);
			
			//pageRoot.locationTypeOrder = GUIPageXMLFileReader.labelTypeOrder(elementCalculation, pageRoot.EType);
			
			//iOSXCUITestElement.printElement(pageRoot);
			
			//tempString = reader.readLine();
			//iOSXCUITestElement.printElement(analysisIOSElement(tempString));
			
			iOSXCUITestElement lastElement = pageRoot;
			int lineNO = 0;
			
			int crossLineStartSymbol = 0;
			iOSXCUITestElement crossLinePageElement = null;
			
			while((tempString = reader.readLine()) != null) {
				
				if((tempString.contains("<"))&&(!tempString.endsWith(">"))&&(crossLineStartSymbol == 0)) {
					crossLinePageElement = new iOSXCUITestElement();
					
					crossLinePageElement = analysisCrossLineIOSElement(crossLinePageElement, tempString, elementCalculation, lineNO);
					
					crossLineStartSymbol = 1;
					lineNO ++;
					continue;
				}
				else if((!tempString.contains("<"))&&(crossLineStartSymbol == 1)) {
					
					if(tempString.endsWith(">")) {
						
						crossLinePageElement = analysisCrossLineIOSElement(crossLinePageElement, tempString, elementCalculation, lineNO);
						
						setLinkincludeParentandSibilings(lastElement, crossLinePageElement);
						lastElement = crossLinePageElement;
						
						crossLinePageElement = null;
						crossLineStartSymbol = 0;
						
						lineNO ++;
						continue;
					}
					else {
						crossLinePageElement = analysisCrossLineIOSElement(crossLinePageElement, tempString, elementCalculation, lineNO);
						
						lineNO ++;
						continue;
					}
					
				}
				else if(((tempString.contains("<"))&&(tempString.endsWith(">"))&&(crossLineStartSymbol == 1))
						|| ((!tempString.contains("<"))&&(!tempString.endsWith(">"))&&(crossLineStartSymbol == 0))
						|| ((!tempString.contains("<"))&&(tempString.endsWith(">"))&&(crossLineStartSymbol == 0))
						|| ((tempString.contains("<"))&&(!tempString.endsWith(">"))&&(crossLineStartSymbol == 1))
						) {
					System.out.println("### Warning!: unknwon error while dealing with crossline element. ###");
					System.out.println(tempString);
					lineNO ++;
					continue;
				}
				
				
				iOSXCUITestElement currentElement = analysisIOSElement(tempString, elementCalculation, lineNO);
				lineNO ++;
				
				if(currentElement == null) {
					continue;
				}
//				iOSXCUITestElement.printElement(currentElement);
				setLinkincludeParentandSibilings(lastElement, currentElement);
				
				lastElement = currentElement;
				
			}
			
			iOSXCUITestPage.traverse2SetLocationTypeOrderofPageTreeUsingBFS(iOSPage);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("======= Error occur in File: " + pageFileNameiOS + "," + tempString + "======= ");
			iOSPage = null;
		}
		//System.out.println("FileName: " + pageFileNameiOS);
//		iOSXCUITestPage.printPageTree(iOSPage);
		
		//Set iOS locationTypeOrder using BFS
		
		
		return iOSPage;
	}
	
	public static void setLinkincludeParentandSibilings(iOSXCUITestElement last, iOSXCUITestElement current) {
		
		iOSXCUITestElement parentTemp = last;
		
		while((current.pageTreeLevel - parentTemp.pageTreeLevel) != 1) {
			if((current.pageTreeLevel - parentTemp.pageTreeLevel) > 1 ) {
				System.out.println("Error: when set the link to parent.");
				return ;
			}
			parentTemp = parentTemp.parent;
		}
		
		current.parent = parentTemp;
		parentTemp.sibilings.add(current);
		
	}
	
	
	public static iOSXCUITestElement analysisIOSElement(String line, HashMap<String, Integer> elementCalculation, int lineNO) {
		
		iOSXCUITestElement iOSElement = new iOSXCUITestElement();
		
		if(!line.contains("<")) {
			System.out.println("### Warning!: line does not contain symbol <. ###");
			System.out.println(line);
			return null;
		}
		
		String[] srcs = line.split("<");
		
		if(srcs[1].startsWith("/")) {
			return null;
		}
		
		int level = srcs[0].length();
		iOSElement.pageTreeLevel = level/2;
		
		String[] srcs_1 = srcs[1].split(" ");
		iOSElement.EHead = srcs_1[0];//<head...
		String[] srcs_1_type = srcs[1].split("type=\"");
		if(srcs_1_type.length > 1) {
			String[] srcs_1_type_1 = srcs_1_type[1].split("\"");
			iOSElement.EType = srcs_1_type_1[0];//type
		}
		String[] srcs_1_value = srcs[1].split("value=\"");
		if(srcs_1_value.length > 1) {
			String[] srcs_1_value_1 = srcs_1_value[1].split("\"");
			iOSElement.EValue = srcs_1_value_1[0];//value
		}
		String[] srcs_1_name = srcs[1].split("name=\"");
		if(srcs_1_name.length > 1) {
			String[] srcs_1_name_1 = srcs_1_name[1].split("\"");
			iOSElement.EName = srcs_1_name_1[0];//name
		}
		String[] srcs_1_label = srcs[1].split("label=\"");
		if(srcs_1_label.length > 1) {
			String[] srcs_1_label_1 = srcs_1_label[1].split("\"");
			iOSElement.ELabel = srcs_1_label_1[0];//label
		}
		String[] srcs_1_enabled = srcs[1].split("enabled=\"");
		if(srcs_1_enabled.length > 1) {
			String[] srcs_1_enabled_1 = srcs_1_enabled[1].split("\"");
			iOSElement.EEnable = srcs_1_enabled_1[0];//enabled
			//if(iOSElement.EEnable.equals("false")) {
			//	System.out.println("???");
			//}
		}
		String[] srcs_1_visible = srcs[1].split("visible=\"");
		if(srcs_1_visible.length > 1) {
			String[] srcs_1_visible_1 = srcs_1_visible[1].split("\"");
			iOSElement.EVisible = srcs_1_visible_1[0];//visible
		}
		String[] srcs_1_evalue = srcs[1].split("value=\"");
//		if(srcs_1_evalue.length > 1) {
//			String[] srcs_1_evalue_1 = srcs_1_evalue[1].split("\"");
//			iOSElement.EValue = srcs_1_evalue_1[0];//value
//		}
		String[] srcs_1_x = srcs[1].split("x=\"");
		if(srcs_1_x.length > 1) {
			String[] srcs_1_x_1 = srcs_1_x[1].split("\"");
			iOSElement.EX = srcs_1_x_1[0];//x
			iOSElement.Ex = Integer.parseInt(iOSElement.EX);//x
		}
		String[] srcs_1_y = srcs[1].split("y=\"");
		if(srcs_1_y.length > 1) {
			String[] srcs_1_y_1 = srcs_1_y[1].split("\"");
			iOSElement.EY = srcs_1_y_1[0];//y
			iOSElement.Ey = Integer.parseInt(iOSElement.EY);//y
		}
		String[] srcs_1_width = srcs[1].split("width=\"");
		if(srcs_1_width.length > 1) {
			String[] srcs_1_width_1 = srcs_1_width[1].split("\"");
			iOSElement.EWidth = srcs_1_width_1[0];//width
			iOSElement.Ewidth = Integer.parseInt(iOSElement.EWidth);//width
		}
		String[] srcs_1_height = srcs[1].split("height=\"");
		if(srcs_1_height.length > 1) {
			String[] srcs_1_height_1 = srcs_1_height[1].split("\"");
			iOSElement.EHeight = srcs_1_height_1[0];//height
			iOSElement.Eheight = Integer.parseInt(iOSElement.EHeight);//height
		}
		
		iOSElement.lineNumber = lineNO;
		
		//EEnd
		
		//locationTypeOrder
		if(iOSElement.EType == null) {//???
			System.out.println("===== Read files error: no type atrribute when setting location type order. =====");
			return iOSElement;
		}
		//iOSElement.locationTypeOrder = GUIPageXMLFileReader.labelTypeOrder(elementCalculation, iOSElement.EType);
		
		
		return iOSElement;
	}
	
	public static iOSXCUITestElement analysisCrossLineIOSElement(iOSXCUITestElement iOSElement, String line, HashMap<String, Integer> elementCalculation, int lineNO) {
		
		
		
		String restString = null;
		
		if(line.contains("<")) {
			String[] srcs = null;
			srcs = line.split("<");
			int level = srcs[0].length();
			iOSElement.pageTreeLevel = level/2;
			String[] srcs_1 = srcs[1].split(" ");
			iOSElement.EHead = srcs_1[0];//<head...
			restString = srcs[1];
		}
		else {
			restString = line;
		}
		
		
		
		
		
		String[] srcs_1_type = restString.split("type=\"");
		if(srcs_1_type.length > 1) {
			String[] srcs_1_type_1 = srcs_1_type[1].split("\"");
			iOSElement.EType = srcs_1_type_1[0];//type
		}
		String[] srcs_1_value = restString.split("value=\"");
		if(srcs_1_value.length > 1) {
			String[] srcs_1_value_1 = srcs_1_value[1].split("\"");
			iOSElement.EValue = srcs_1_value_1[0];//value
		}
		String[] srcs_1_name = restString.split("name=\"");
		if(srcs_1_name.length > 1) {
			String[] srcs_1_name_1 = srcs_1_name[1].split("\"");
			iOSElement.EName = srcs_1_name_1[0];//name
		}
		String[] srcs_1_label = restString.split("label=\"");
		if(srcs_1_label.length > 1) {
			String[] srcs_1_label_1 = srcs_1_label[1].split("\"");
			iOSElement.ELabel = srcs_1_label_1[0];//label
		}
		String[] srcs_1_enabled = restString.split("enabled=\"");
		if(srcs_1_enabled.length > 1) {
			String[] srcs_1_enabled_1 = srcs_1_enabled[1].split("\"");
			iOSElement.EEnable = srcs_1_enabled_1[0];//enabled
			//if(iOSElement.EEnable.equals("false")) {
			//	System.out.println("???");
			//}
		}
		String[] srcs_1_visible = restString.split("visible=\"");
		if(srcs_1_visible.length > 1) {
			String[] srcs_1_visible_1 = srcs_1_visible[1].split("\"");
			iOSElement.EVisible = srcs_1_visible_1[0];//visible
		}
		String[] srcs_1_x = restString.split("x=\"");
		if(srcs_1_x.length > 1) {
			String[] srcs_1_x_1 = srcs_1_x[1].split("\"");
			iOSElement.EX = srcs_1_x_1[0];//x
			iOSElement.Ex = Integer.parseInt(iOSElement.EX);//x
		}
		String[] srcs_1_y = restString.split("y=\"");
		if(srcs_1_y.length > 1) {
			String[] srcs_1_y_1 = srcs_1_y[1].split("\"");
			iOSElement.EY = srcs_1_y_1[0];//y
			iOSElement.Ey = Integer.parseInt(iOSElement.EY);//y
		}
		String[] srcs_1_width = restString.split("width=\"");
		if(srcs_1_width.length > 1) {
			String[] srcs_1_width_1 = srcs_1_width[1].split("\"");
			iOSElement.EWidth = srcs_1_width_1[0];//width
			iOSElement.Ewidth = Integer.parseInt(iOSElement.EWidth);//width
		}
		String[] srcs_1_height = restString.split("height=\"");
		if(srcs_1_height.length > 1) {
			String[] srcs_1_height_1 = srcs_1_height[1].split("\"");
			iOSElement.EHeight = srcs_1_height_1[0];//height
			iOSElement.Eheight = Integer.parseInt(iOSElement.EHeight);//height
		}
		
		iOSElement.lineNumber = lineNO;
		
		//EEnd
		
		//locationTypeOrder
		if(iOSElement.EType == null) {//???
			System.out.println("===== Read files error: no type atrribute when setting location type order. =====");
			return iOSElement;
		}
		//iOSElement.locationTypeOrder = GUIPageXMLFileReader.labelTypeOrder(elementCalculation, iOSElement.EType);
		
		
		return iOSElement;
	}
	
	//ios
	
	
	public static int labelTypeOrder(HashMap<String, Integer> elementCalculation, String typeLabel) {
		
		if(elementCalculation.get(typeLabel) == null) {
			elementCalculation.put(typeLabel, 1);
			return 0;
		}
		else {
			int older = elementCalculation.get(typeLabel);
			older = older + 1;
			elementCalculation.put(typeLabel, older);
			return older-1;
		}
	}
	
	public static iOSXCUITestElement analysisIOSElement_DFS(String line, HashMap<String, Integer> elementCalculation) {
		
		iOSXCUITestElement iOSElement = new iOSXCUITestElement();
		String[] srcs = line.split("<");
		
		if(srcs[1].startsWith("/")) {
			return null;
		}
		
		int level = srcs[0].length();
		iOSElement.pageTreeLevel = level/2;
		
		String[] srcs_1 = srcs[1].split(" ");
		iOSElement.EHead = srcs_1[0];//<head...
		String[] srcs_1_type = srcs[1].split("type=\"");
		if(srcs_1_type.length > 1) {
			String[] srcs_1_type_1 = srcs_1_type[1].split("\"");
			iOSElement.EType = srcs_1_type_1[0];//type
		}
		String[] srcs_1_name = srcs[1].split("name=\"");
		if(srcs_1_name.length > 1) {
			String[] srcs_1_name_1 = srcs_1_name[1].split("\"");
			iOSElement.EName = srcs_1_name_1[0];//name
		}
		String[] srcs_1_label = srcs[1].split("label=\"");
		if(srcs_1_label.length > 1) {
			String[] srcs_1_label_1 = srcs_1_label[1].split("\"");
			iOSElement.ELabel = srcs_1_label_1[0];//label
		}
		String[] srcs_1_enabled = srcs[1].split("enabled=\"");
		if(srcs_1_enabled.length > 1) {
			String[] srcs_1_enabled_1 = srcs_1_enabled[1].split("\"");
			iOSElement.EEnable = srcs_1_enabled_1[0];//enabled
			//if(iOSElement.EEnable.equals("false")) {
			//	System.out.println("???");
			//}
		}
		String[] srcs_1_visible = srcs[1].split("visible=\"");
		if(srcs_1_visible.length > 1) {
			String[] srcs_1_visible_1 = srcs_1_visible[1].split("\"");
			iOSElement.EVisible = srcs_1_visible_1[0];//visible
		}
		String[] srcs_1_x = srcs[1].split("x=\"");
		if(srcs_1_x.length > 1) {
			String[] srcs_1_x_1 = srcs_1_x[1].split("\"");
			iOSElement.EX = srcs_1_x_1[0];//x
			iOSElement.Ex = Integer.parseInt(iOSElement.EX);//x
		}
		String[] srcs_1_y = srcs[1].split("y=\"");
		if(srcs_1_y.length > 1) {
			String[] srcs_1_y_1 = srcs_1_y[1].split("\"");
			iOSElement.EY = srcs_1_y_1[0];//y
			iOSElement.Ey = Integer.parseInt(iOSElement.EY);//y
		}
		String[] srcs_1_width = srcs[1].split("width=\"");
		if(srcs_1_width.length > 1) {
			String[] srcs_1_width_1 = srcs_1_width[1].split("\"");
			iOSElement.EWidth = srcs_1_width_1[0];//width
			iOSElement.Ewidth = Integer.parseInt(iOSElement.EWidth);//width
		}
		String[] srcs_1_height = srcs[1].split("height=\"");
		if(srcs_1_height.length > 1) {
			String[] srcs_1_height_1 = srcs_1_height[1].split("\"");
			iOSElement.EHeight = srcs_1_height_1[0];//height
			iOSElement.Eheight = Integer.parseInt(iOSElement.EHeight);//height
		}
		
		//EEnd
		
		//locationTypeOrder
		if(iOSElement.EType == null) {//???
			System.out.println("===== Read files error: no type atrribute when setting location type order. =====");
			return iOSElement;
		}
		iOSElement.locationTypeOrder = GUIPageXMLFileReader.labelTypeOrder(elementCalculation, iOSElement.EType);
		
		
		return iOSElement;
	}
	
	public static AndroidGUIPage readAndroidPageXMLFile(String pageFileNameAndroid) {
		
//		System.out.println("### " + pageFileNameAndroid);
		
		AndroidGUIPage androidPage = null;
		
		HashMap<String, Integer> elementCalculation = new HashMap<String, Integer>();
		
		File file = new File(pageFileNameAndroid);
		BufferedReader reader = null;
		String tempString = null;
		
		
		try {
			androidPage = new AndroidGUIPage();
			
			reader = new BufferedReader(new FileReader(file));
			
			
			//1st line
			tempString = reader.readLine();
			String[] srcs0 = tempString.split("<");
			
			AndroidGUIXMLFileHeadElement xmlFileHead = new AndroidGUIXMLFileHeadElement();
			String[] srcs0_1 = srcs0[1].split(" ");
			xmlFileHead.EHead = srcs0_1[0];//?xml
			String[] srcs0_1_1 = srcs0_1[1].split("=\'");
			String[] srcs0_1_1_1 = srcs0_1_1[1].split("\'");
			xmlFileHead.EVersion = srcs0_1_1_1[0];//version
			String[] srcs0_1_2 = srcs0_1[2].split("=\'");
			String[] srcs0_1_2_1 = srcs0_1_2[1].split("\'");
			xmlFileHead.EEncoding = srcs0_1_2_1[0];//encoding
			String[] srcs0_1_3 = srcs0_1[3].split("=\'");
			String[] srcs0_1_3_1 = srcs0_1_3[1].split("\'");
			xmlFileHead.EStandalone = srcs0_1_3_1[0];//standalone
			//EEnd?
			androidPage.fileHead = xmlFileHead;
			
			//2nd line
			tempString = reader.readLine();
			String[] srcs1 = tempString.split("<");
			
			AndroidGUIHierarchyElement hierarchy = new AndroidGUIHierarchyElement();
			String[] srcs1_1 = srcs1[1].split(" ");
			hierarchy.EHead = srcs1_1[0];//hierarchy
			String[] srcs1_1_1 = srcs1_1[1].split("=\"");
			String[] srcs1_1_1_1 = srcs1_1_1[1].split("\"");
			hierarchy.EIndex = srcs1_1_1_1[0];//index
			String[] srcs1_1_2 = srcs1_1[2].split("=\"");
			String[] srcs1_1_2_1 = srcs1_1_2[1].split("\"");
			hierarchy.EClass = srcs1_1_2_1[0];//class
			String[] srcs1_1_3 = srcs1_1[3].split("=\"");
			String[] srcs1_1_3_1 = srcs1_1_3[1].split("\"");
			hierarchy.ERotation = srcs1_1_3_1[0];//rotation
			String[] srcs1_1_4 = srcs1_1[4].split("=\"");
			String[] srcs1_1_4_1 = srcs1_1_4[1].split("\"");
			hierarchy.EWidth = srcs1_1_4_1[0];//width
			hierarchy.Ewidth = Integer.parseInt(hierarchy.EWidth);
			String[] srcs1_1_5 = srcs1_1[5].split("=\"");
			String[] srcs1_1_5_1 = srcs1_1_5[1].split("\"");
			hierarchy.EHeight = srcs1_1_5_1[0];//height
			hierarchy.Eheight = Integer.parseInt(hierarchy.EHeight);
			//EEnd?
			androidPage.hierarchy = hierarchy;
			
			//AndroidGUIPage
			androidPage.pageWidth = hierarchy.Ewidth;
			androidPage.pageHeight = hierarchy.Eheight;
			
			
			//3th line
			tempString = reader.readLine();
			AndroidGUIElement pageRoot = new AndroidGUIElement();
			String[] srcs = tempString.split("<");
			
			int level = srcs[0].length();
			pageRoot.pageTreeLevel = level/2;
			
			String[] srcs_1 = srcs[1].split(" ");
			pageRoot.EHead = srcs_1[0];//<head...
			String[] srcs_1_index = srcs[1].split("index=\"");
			if(srcs_1_index.length > 1) {
				String[] srcs_1_index_1 = srcs_1_index[1].split("\"");
				pageRoot.EIndex = srcs_1_index_1[0];//type
			}
			String[] srcs_1_package = srcs[1].split("package=\"");
			if(srcs_1_package.length > 1) {
				String[] srcs_1_package_1 = srcs_1_package[1].split("\"");
				pageRoot.EPackage = srcs_1_package_1[0];//package
			}
			String[] srcs_1_class = srcs[1].split("class=\"");
			if(srcs_1_class.length > 1) {
				String[] srcs_1_class_1 = srcs_1_class[1].split("\"");
				pageRoot.EClass = srcs_1_class_1[0];//class
			}
			String[] srcs_1_text = srcs[1].split("text=\"");
			if(srcs_1_text.length > 1) {
				String[] srcs_1_text_1 = srcs_1_text[1].split("\"");
				pageRoot.EText = srcs_1_text_1[0];//text
			}
			String[] srcs_1_checkable = srcs[1].split("checkable=\"");
			if(srcs_1_checkable.length > 1) {
				String[] srcs_1_checkable_1 = srcs_1_checkable[1].split("\"");
				pageRoot.ECheckable = srcs_1_checkable_1[0];//checkable
			}
			String[] srcs_1_checked = srcs[1].split("checked=\"");
			if(srcs_1_checked.length > 1) {
				String[] srcs_1_checked_1 = srcs_1_checked[1].split("\"");
				pageRoot.EChecked = srcs_1_checked_1[0];//checked
			}
			String[] srcs_1_clickable = srcs[1].split("clickable=\"");
			if(srcs_1_clickable.length > 1) {
				String[] srcs_1_clickable_1 = srcs_1_clickable[1].split("\"");
				pageRoot.EClickable = srcs_1_clickable_1[0];//clickable
			}
			String[] srcs_1_enabled = srcs[1].split("enabled=\"");
			if(srcs_1_enabled.length > 1) {
				String[] srcs_1_enabled_1 = srcs_1_enabled[1].split("\"");
				pageRoot.EEnable = srcs_1_enabled_1[0];//enabled
			}
			String[] srcs_1_focusable = srcs[1].split("focusable=\"");
			if(srcs_1_focusable.length > 1) {
				String[] srcs_1_focusable_1 = srcs_1_focusable[1].split("\"");
				pageRoot.EFocusable = srcs_1_focusable_1[0];//focusable
			}
			String[] srcs_1_longclickable = srcs[1].split("long-clickable=\"");
			if(srcs_1_longclickable.length > 1) {
				String[] srcs_1_longclickable_1 = srcs_1_longclickable[1].split("\"");
				pageRoot.ELongclickable = srcs_1_longclickable_1[0];//long-clickable
			}
			String[] srcs_1_password = srcs[1].split("password=\"");
			if(srcs_1_password.length > 1) {
				String[] srcs_1_password_1 = srcs_1_password[1].split("\"");
				pageRoot.EPassword = srcs_1_password_1[0];//password
			}
			String[] srcs_1_scrollable = srcs[1].split("scrollable=\"");
			if(srcs_1_scrollable.length > 1) {
				String[] srcs_1_scrollable_1 = srcs_1_scrollable[1].split("\"");
				pageRoot.EScrollable = srcs_1_scrollable_1[0];//scrollable
			}
			String[] srcs_1_selected = srcs[1].split("selected=\"");
			if(srcs_1_selected.length > 1) {
				String[] srcs_1_selected_1 = srcs_1_selected[1].split("\"");
				pageRoot.ESelected = srcs_1_selected_1[0];//selected
			}
			String[] srcs_1_bounds = srcs[1].split("bounds=\"");
			if(srcs_1_bounds.length > 1) {
				String[] srcs_1_bounds_1 = srcs_1_bounds[1].split("\"");
				pageRoot.EBounds = srcs_1_bounds_1[0];//bounds
				
				//bounds [x1,y1][x2,y2]
				//System.out.println("bounds: " + pageRoot.EBounds);
				String[] srcs_bounds = pageRoot.EBounds.split("\\[");
				String[] srcs_bounds_1 = srcs_bounds[1].split(",");
				pageRoot.Ex1 = Integer.parseInt(srcs_bounds_1[0]);
				String[] srcs_bounds_2 = srcs_bounds[2].split(",");
				pageRoot.Ex2 = Integer.parseInt(srcs_bounds_2[0]);
				String[] srcs_bounds_1_1 = srcs_bounds_1[1].split("\\]");
				pageRoot.Ey1 = Integer.parseInt(srcs_bounds_1_1[0]);
				String[] srcs_bounds_2_1 = srcs_bounds_2[1].split("\\]");
				pageRoot.Ey2 = Integer.parseInt(srcs_bounds_2_1[0]);
				
			}
			String[] srcs_1_displayed = srcs[1].split("displayed=\"");
			if(srcs_1_displayed.length > 1) {
				String[] srcs_1_displayed_1 = srcs_1_displayed[1].split("\"");
				pageRoot.EDisplayed = srcs_1_displayed_1[0];//displayed
			}
			String[] srcs_1_content_desc = srcs[1].split("content-desc=\"");
			if(srcs_1_content_desc.length > 1) {
				String[] srcs_1_content_desc_1 = srcs_1_content_desc[1].split("\"");
				pageRoot.EContent_desc = srcs_1_content_desc_1[0];//content_desc
			}
			String[] srcs_1_resource_id = srcs[1].split("resource-id=\"");
			if(srcs_1_resource_id.length > 1) {
				String[] srcs_1_resource_id_1 = srcs_1_resource_id[1].split("\"");
				pageRoot.EResource_id = srcs_1_resource_id_1[0];//resource-id
			}
			
			//EEnd
			pageRoot.parent = null;
			androidPage.elementRoot = pageRoot;
			//locationTypeOrder
			if(pageRoot.EClass == null) {//???
				System.out.println("===== Read files error: no class atrribute when setting location type order. =====");
			}
			pageRoot.locationTypeOrder = GUIPageXMLFileReader.labelTypeOrder(elementCalculation, pageRoot.EClass);
			
			//AndroidGUIElement.printElement(pageRoot);
			
			
			
			AndroidGUIElement lastElement = pageRoot;
			int lineNO = 0;
			
			int crossLineStartSymbol = 0;
			AndroidGUIElement crossLinePageElement = null;
			
			
			while((tempString = reader.readLine()) != null) {
				
				if((tempString.contains("<"))&&(!tempString.endsWith(">"))&&(crossLineStartSymbol == 0)) {
					crossLinePageElement = new AndroidGUIElement();
					
					crossLinePageElement = analysisCrossLineAndroidElement(crossLinePageElement, tempString, elementCalculation, lineNO);
					
					crossLineStartSymbol = 1;
					lineNO ++;
					continue;
				}
				else if((!tempString.contains("<"))&&(crossLineStartSymbol == 1)) {
					
					if(tempString.endsWith(">")) {
						
						crossLinePageElement = analysisCrossLineAndroidElement(crossLinePageElement, tempString, elementCalculation, lineNO);
						
						setLinkincludeParentandSibilingsAndroid(lastElement, crossLinePageElement);
						lastElement = crossLinePageElement;
						
						crossLinePageElement = null;
						crossLineStartSymbol = 0;
						
						lineNO ++;
						continue;
					}
					else {
						crossLinePageElement = analysisCrossLineAndroidElement(crossLinePageElement, tempString, elementCalculation, lineNO);
						
						lineNO ++;
						continue;
					}
					
				}
				else if(((tempString.contains("<"))&&(tempString.endsWith(">"))&&(crossLineStartSymbol == 1))
						|| ((!tempString.contains("<"))&&(!tempString.endsWith(">"))&&(crossLineStartSymbol == 0))
						|| ((!tempString.contains("<"))&&(tempString.endsWith(">"))&&(crossLineStartSymbol == 0))
						|| ((tempString.contains("<"))&&(!tempString.endsWith(">"))&&(crossLineStartSymbol == 1))
						) {
					System.out.println("### Warning!: unknwon error while dealing with crossline element. ###");
					System.out.println(tempString);
					lineNO ++;
					continue;
				}
				
				
				
				
				AndroidGUIElement currentElement = analysisAndroidElement(tempString, elementCalculation, lineNO);
				lineNO ++;
				if(currentElement == null) {
					continue;
				}
				//AndroidGUIElement.printElement(currentElement);
				//System.out.println(tempString);
				setLinkincludeParentandSibilingsAndroid(lastElement, currentElement);
				
				lastElement = currentElement;
				
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("======= Error occur in File: " + pageFileNameAndroid + "," + tempString + "======= ");
			androidPage = null;
		}
		//System.out.println("FileName: " + pageFileNameAndroid);
//		AndroidGUIPage.printPageTree(androidPage);
		
		return androidPage;
	}
	
	public static void setLinkincludeParentandSibilingsAndroid(AndroidGUIElement last, AndroidGUIElement current) {
		
		AndroidGUIElement parentTemp = last;
		
		
		while((current.pageTreeLevel - parentTemp.pageTreeLevel) != 1) {
			if((current.pageTreeLevel - parentTemp.pageTreeLevel) > 1 ) {
				System.out.println("Error: when set the link to parent.(Android)");
				return ;
			}
			parentTemp = parentTemp.parent;
			if(parentTemp == null) {
				System.out.println("Error: Encounter node follow the root and being at the same level!(Android)");
				return ;
			}
		}
		
		current.parent = parentTemp;
		parentTemp.sibilings.add(current);
		
	}
	
	public static AndroidGUIElement analysisAndroidElement(String line, HashMap<String, Integer> elementCalculation, int lineNO) {
		
		AndroidGUIElement androidElement = new AndroidGUIElement();
		String[] srcs = line.split("<");
		
		if(srcs[1].startsWith("/")) {
			return null;
		}
		
		int level = srcs[0].length();
		androidElement.pageTreeLevel = level/2;
		
		String[] srcs_1 = srcs[1].split(" ");
		androidElement.EHead = srcs_1[0];//<head...
		String[] srcs_1_index = srcs[1].split("index=\"");
		if(srcs_1_index.length > 1) {
			String[] srcs_1_index_1 = srcs_1_index[1].split("\"");
			androidElement.EIndex = srcs_1_index_1[0];//type
		}
		String[] srcs_1_package = srcs[1].split("package=\"");
		if(srcs_1_package.length > 1) {
			String[] srcs_1_package_1 = srcs_1_package[1].split("\"");
			androidElement.EPackage = srcs_1_package_1[0];//package
		}
		String[] srcs_1_class = srcs[1].split("class=\"");
		if(srcs_1_class.length > 1) {
			String[] srcs_1_class_1 = srcs_1_class[1].split("\"");
			androidElement.EClass = srcs_1_class_1[0];//class
		}
		String[] srcs_1_text = srcs[1].split("text=\"");
		if(srcs_1_text.length > 1) {
			String[] srcs_1_text_1 = srcs_1_text[1].split("\"");
			androidElement.EText = srcs_1_text_1[0];//text
		}
		String[] srcs_1_checkable = srcs[1].split("checkable=\"");
		if(srcs_1_checkable.length > 1) {
			String[] srcs_1_checkable_1 = srcs_1_checkable[1].split("\"");
			androidElement.ECheckable = srcs_1_checkable_1[0];//checkable
		}
		String[] srcs_1_checked = srcs[1].split("checked=\"");
		if(srcs_1_checked.length > 1) {
			String[] srcs_1_checked_1 = srcs_1_checked[1].split("\"");
			androidElement.EChecked = srcs_1_checked_1[0];//checked
		}
		String[] srcs_1_clickable = srcs[1].split("clickable=\"");
		if(srcs_1_clickable.length > 1) {
			String[] srcs_1_clickable_1 = srcs_1_clickable[1].split("\"");
			androidElement.EClickable = srcs_1_clickable_1[0];//clickable
		}
		String[] srcs_1_enabled = srcs[1].split("enabled=\"");
		if(srcs_1_enabled.length > 1) {
			String[] srcs_1_enabled_1 = srcs_1_enabled[1].split("\"");
			androidElement.EEnable = srcs_1_enabled_1[0];//enabled
		}
		String[] srcs_1_focusable = srcs[1].split("focusable=\"");
		if(srcs_1_focusable.length > 1) {
			String[] srcs_1_focusable_1 = srcs_1_focusable[1].split("\"");
			androidElement.EFocusable = srcs_1_focusable_1[0];//focusable
		}
		String[] srcs_1_longclickable = srcs[1].split("long-clickable=\"");
		if(srcs_1_longclickable.length > 1) {
			String[] srcs_1_longclickable_1 = srcs_1_longclickable[1].split("\"");
			androidElement.ELongclickable = srcs_1_longclickable_1[0];//long-clickable
		}
		String[] srcs_1_password = srcs[1].split("password=\"");
		if(srcs_1_password.length > 1) {
			String[] srcs_1_password_1 = srcs_1_password[1].split("\"");
			androidElement.EPassword = srcs_1_password_1[0];//password
		}
		String[] srcs_1_scrollable = srcs[1].split("scrollable=\"");
		if(srcs_1_scrollable.length > 1) {
			String[] srcs_1_scrollable_1 = srcs_1_scrollable[1].split("\"");
			androidElement.EScrollable = srcs_1_scrollable_1[0];//scrollable
		}
		String[] srcs_1_selected = srcs[1].split("selected=\"");
		if(srcs_1_selected.length > 1) {
			String[] srcs_1_selected_1 = srcs_1_selected[1].split("\"");
			androidElement.ESelected = srcs_1_selected_1[0];//selected
		}
		String[] srcs_1_bounds = srcs[1].split("bounds=\"");
		if(srcs_1_bounds.length > 1) {
			String[] srcs_1_bounds_1 = srcs_1_bounds[1].split("\"");
			androidElement.EBounds = srcs_1_bounds_1[0];//bounds
			
			//bounds [x1,y1][x2,y2]
			//System.out.println("bounds:" + androidElement.EBounds);
			String[] srcs_bounds = androidElement.EBounds.split("\\[");
			String[] srcs_bounds_1 = srcs_bounds[1].split(",");
			androidElement.Ex1 = Integer.parseInt(srcs_bounds_1[0]);
			String[] srcs_bounds_2 = srcs_bounds[2].split(",");
			androidElement.Ex2 = Integer.parseInt(srcs_bounds_2[0]);
			String[] srcs_bounds_1_1 = srcs_bounds_1[1].split("\\]");
			androidElement.Ey1 = Integer.parseInt(srcs_bounds_1_1[0]);
			String[] srcs_bounds_2_1 = srcs_bounds_2[1].split("\\]");
			androidElement.Ey2 = Integer.parseInt(srcs_bounds_2_1[0]);
			
		}
		String[] srcs_1_displayed = srcs[1].split("displayed=\"");
		if(srcs_1_displayed.length > 1) {
			String[] srcs_1_displayed_1 = srcs_1_displayed[1].split("\"");
			androidElement.EDisplayed = srcs_1_displayed_1[0];//displayed
		}
		String[] srcs_1_content_desc = srcs[1].split("content-desc=\"");
		if(srcs_1_content_desc.length > 1) {
			String[] srcs_1_content_desc_1 = srcs_1_content_desc[1].split("\"");
			androidElement.EContent_desc = srcs_1_content_desc_1[0];//content_desc
		}
		String[] srcs_1_resource_id = srcs[1].split("resource-id=\"");
		if(srcs_1_resource_id.length > 1) {
			String[] srcs_1_resource_id_1 = srcs_1_resource_id[1].split("\"");
			androidElement.EResource_id = srcs_1_resource_id_1[0];//resource-id
		}
		
		androidElement.lineNumber = lineNO;
		
		//EEnd
		
		//locationTypeOrder
		if(androidElement.EClass == null) {//???
			System.out.println("===== Read files error: no class atrribute when setting location type order. =====");
			return androidElement;
		}
		androidElement.locationTypeOrder = GUIPageXMLFileReader.labelTypeOrder(elementCalculation, androidElement.EClass);
		
		
		return androidElement;
	}
	
	public static AndroidGUIElement analysisCrossLineAndroidElement(AndroidGUIElement androidElement, String line, HashMap<String, Integer> elementCalculation, int lineNO) {
		
		String restString = null;
		
		if(line.contains("<")) {
			String[] srcs = null;
			srcs = line.split("<");
			int level = srcs[0].length();
			androidElement.pageTreeLevel = level/2;
			String[] srcs_1 = srcs[1].split(" ");
			androidElement.EHead = srcs_1[0];//<head...
			restString = srcs[1];
		}
		else {
			restString = line;
		}
		
		
		String[] srcs_1_index = restString.split("index=\"");
		if(srcs_1_index.length > 1) {
			String[] srcs_1_index_1 = srcs_1_index[1].split("\"");
			androidElement.EIndex = srcs_1_index_1[0];//type
		}
		String[] srcs_1_package = restString.split("package=\"");
		if(srcs_1_package.length > 1) {
			String[] srcs_1_package_1 = srcs_1_package[1].split("\"");
			androidElement.EPackage = srcs_1_package_1[0];//package
		}
		String[] srcs_1_class = restString.split("class=\"");
		if(srcs_1_class.length > 1) {
			String[] srcs_1_class_1 = srcs_1_class[1].split("\"");
			androidElement.EClass = srcs_1_class_1[0];//class
		}
		String[] srcs_1_text = restString.split("text=\"");
		if(srcs_1_text.length > 1) {
			String[] srcs_1_text_1 = srcs_1_text[1].split("\"");
			androidElement.EText = srcs_1_text_1[0];//text
		}
		String[] srcs_1_checkable = restString.split("checkable=\"");
		if(srcs_1_checkable.length > 1) {
			String[] srcs_1_checkable_1 = srcs_1_checkable[1].split("\"");
			androidElement.ECheckable = srcs_1_checkable_1[0];//checkable
		}
		String[] srcs_1_checked = restString.split("checked=\"");
		if(srcs_1_checked.length > 1) {
			String[] srcs_1_checked_1 = srcs_1_checked[1].split("\"");
			androidElement.EChecked = srcs_1_checked_1[0];//checked
		}
		String[] srcs_1_clickable = restString.split("clickable=\"");
		if(srcs_1_clickable.length > 1) {
			String[] srcs_1_clickable_1 = srcs_1_clickable[1].split("\"");
			androidElement.EClickable = srcs_1_clickable_1[0];//clickable
		}
		String[] srcs_1_enabled = restString.split("enabled=\"");
		if(srcs_1_enabled.length > 1) {
			String[] srcs_1_enabled_1 = srcs_1_enabled[1].split("\"");
			androidElement.EEnable = srcs_1_enabled_1[0];//enabled
		}
		String[] srcs_1_focusable = restString.split("focusable=\"");
		if(srcs_1_focusable.length > 1) {
			String[] srcs_1_focusable_1 = srcs_1_focusable[1].split("\"");
			androidElement.EFocusable = srcs_1_focusable_1[0];//focusable
		}
		String[] srcs_1_longclickable = restString.split("long-clickable=\"");
		if(srcs_1_longclickable.length > 1) {
			String[] srcs_1_longclickable_1 = srcs_1_longclickable[1].split("\"");
			androidElement.ELongclickable = srcs_1_longclickable_1[0];//long-clickable
		}
		String[] srcs_1_password = restString.split("password=\"");
		if(srcs_1_password.length > 1) {
			String[] srcs_1_password_1 = srcs_1_password[1].split("\"");
			androidElement.EPassword = srcs_1_password_1[0];//password
		}
		String[] srcs_1_scrollable = restString.split("scrollable=\"");
		if(srcs_1_scrollable.length > 1) {
			String[] srcs_1_scrollable_1 = srcs_1_scrollable[1].split("\"");
			androidElement.EScrollable = srcs_1_scrollable_1[0];//scrollable
		}
		String[] srcs_1_selected = restString.split("selected=\"");
		if(srcs_1_selected.length > 1) {
			String[] srcs_1_selected_1 = srcs_1_selected[1].split("\"");
			androidElement.ESelected = srcs_1_selected_1[0];//selected
		}
		String[] srcs_1_bounds = restString.split("bounds=\"");
		if(srcs_1_bounds.length > 1) {
			String[] srcs_1_bounds_1 = srcs_1_bounds[1].split("\"");
			androidElement.EBounds = srcs_1_bounds_1[0];//bounds
			
			//bounds [x1,y1][x2,y2]
			//System.out.println("bounds:" + androidElement.EBounds);
			String[] srcs_bounds = androidElement.EBounds.split("\\[");
			String[] srcs_bounds_1 = srcs_bounds[1].split(",");
			androidElement.Ex1 = Integer.parseInt(srcs_bounds_1[0]);
			String[] srcs_bounds_2 = srcs_bounds[2].split(",");
			androidElement.Ex2 = Integer.parseInt(srcs_bounds_2[0]);
			String[] srcs_bounds_1_1 = srcs_bounds_1[1].split("\\]");
			androidElement.Ey1 = Integer.parseInt(srcs_bounds_1_1[0]);
			String[] srcs_bounds_2_1 = srcs_bounds_2[1].split("\\]");
			androidElement.Ey2 = Integer.parseInt(srcs_bounds_2_1[0]);
			
		}
		String[] srcs_1_displayed = restString.split("displayed=\"");
		if(srcs_1_displayed.length > 1) {
			String[] srcs_1_displayed_1 = srcs_1_displayed[1].split("\"");
			androidElement.EDisplayed = srcs_1_displayed_1[0];//displayed
		}
		String[] srcs_1_content_desc = restString.split("content-desc=\"");
		if(srcs_1_content_desc.length > 1) {
			String[] srcs_1_content_desc_1 = srcs_1_content_desc[1].split("\"");
			androidElement.EContent_desc = srcs_1_content_desc_1[0];//content_desc
		}
		String[] srcs_1_resource_id = restString.split("resource-id=\"");
		if(srcs_1_resource_id.length > 1) {
			String[] srcs_1_resource_id_1 = srcs_1_resource_id[1].split("\"");
			androidElement.EResource_id = srcs_1_resource_id_1[0];//resource-id
		}
		
		
		androidElement.lineNumber = lineNO;
		
		//EEnd
		
		//locationTypeOrder
		if(androidElement.EClass == null) {//???
			System.out.println("===== Read files error: no class atrribute when setting location type order. =====");
			return androidElement;
		}
		androidElement.locationTypeOrder = GUIPageXMLFileReader.labelTypeOrder(elementCalculation, androidElement.EClass);
		
		
		return androidElement;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void readFromLayoutFile(String fileNameANdroid, String fileNameiOS) {
		File file = new File(fileNameANdroid);
		File file1 = new File(fileNameiOS);
		BufferedReader reader = null;
		
		double widthAnd = 1080;
		double heightAnd = 1794;
		double widthiOS = 375;
		double heightiOS = 667;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			
			List<List<String>> androidXMLFile = new ArrayList<List<String>>();
			
			int labelforAnd = 0;
			
			while((tempString = reader.readLine()) != null) {
				List<String> lineTempAndroid = new ArrayList<String>();
				String[] srcs = tempString.split("<");
				if(srcs[1].startsWith("/")) {
					labelforAnd ++;
					continue;
				}
				lineTempAndroid.add(srcs[0]);
				String[] srcs_0 = srcs[1].split(" ");
				lineTempAndroid.add(srcs_0[0]);
				
				
				if(labelforAnd < 2) {
					lineTempAndroid.add(srcs[1]);
					androidXMLFile.add(lineTempAndroid);
					labelforAnd ++;
					continue;
				}
				int l = srcs_0.length;
				int ii = 1;
				for(; ii < l; ii ++) {
					//System.out.println(srcs_0[ii]);
					if(srcs_0[ii].startsWith("bounds")) {
						
						break;
					}
				}
				lineTempAndroid.add(getBounds(srcs_0[ii]));
				
				
				androidXMLFile.add(lineTempAndroid);
				labelforAnd ++;
			}
			
			reader = new BufferedReader(new FileReader(file1));
			
			List<List<String>> iOSXMLFile = new ArrayList<List<String>>();
			
			tempString = reader.readLine();
			List<String> lineFirstAndroid = new ArrayList<String>();
			lineFirstAndroid.add(tempString);
			iOSXMLFile.add(lineFirstAndroid);
			while((tempString = reader.readLine()) != null) {
				List<String> lineTempiOS = new ArrayList<String>();
				String[] srcs = tempString.split("<");
				if(srcs[1].startsWith("/")) {
					continue;
				}
				lineTempiOS.add(srcs[0]);
				String[] srcs_0 = srcs[1].split(" ");
				lineTempiOS.add(srcs_0[0]);
				//lineTempiOS.add(srcs[1]);
				
				int lj = srcs_0.length;
				int ij = 1;
				for(; ij < lj; ij ++) {
					//System.out.println(srcs_0[ii]);
					if(srcs_0[ij].startsWith("width")) {
						break;
					}
				}
				int ik = 1;
				for(;ik < lj; ik ++) {
					if(srcs_0[ik].startsWith("height")) {
						break;
					}
				}
				int ix = 1;
				for(; ix < lj; ix ++) {
					//System.out.println(srcs_0[ii]);
					if(srcs_0[ix].startsWith("x=")) {
						break;
					}
				}
				int iy = 1;
				for(;iy < lj; iy ++) {
					if(srcs_0[iy].startsWith("y=")) {
						break;
					}
				}
				lineTempiOS.add(getBoundsiOS(srcs_0[ix], srcs_0[iy], srcs_0[ij], srcs_0[ik]));
				
				
				
				iOSXMLFile.add(lineTempiOS);
			}
			
			//location and size
			String outputdata = "";
			int lenperLine = 100;
			
			outputdata = outputdata + "Android Title:\n<" + androidXMLFile.get(0).get(2) + "\n<" + androidXMLFile.get(1).get(2);
			outputdata = outputdata + "iOS Title:\n" + iOSXMLFile.get(0).get(0) + "\n\n";
			
			outputdata = outputdata + "Comparison:\n";
			
			int lenAndroidFile = androidXMLFile.size();
			int leniOSFile = iOSXMLFile.size();
			
			int i = 2;
			int j = 1;
			for(; i < lenAndroidFile&&j < leniOSFile;) {
				List<String> tempAnd = androidXMLFile.get(i);
				List<String> tempiOS = iOSXMLFile.get(j);
				if(tempAnd.get(0).length() == tempiOS.get(0).length()) {
					
					outputdata = outputdata + tempAnd.get(0) + "<" + tempAnd.get(1) + ">" + tempAnd.get(2);
					
					
					int lenRemained = lenperLine - tempAnd.get(0).length() - 2 - tempAnd.get(1).length() - tempAnd.get(2).length();
					outputdata = insertBlanks(outputdata, lenRemained);
					
					outputdata = outputdata + tempiOS.get(0) + "<" + tempiOS.get(1) + ">" + tempiOS.get(2) + "\n";
					
					
					
					i ++;
					j ++;
				}
				else if(tempAnd.get(0).length() < tempiOS.get(0).length()) {
					outputdata = insertBlanks(outputdata, lenperLine);
					outputdata = outputdata + tempiOS.get(0) + "<" + tempiOS.get(1) + ">" + tempiOS.get(2) + "\n";
					j ++;
				}
				else {//tempAnd.get(0).length() < tempiOS.get(0).length()
					outputdata = outputdata + tempAnd.get(0) + "<" + tempAnd.get(1) + ">" + tempAnd.get(2) +"\n";
					i ++;
				}
				
			}
			while(i < lenAndroidFile) {
				outputdata = outputdata + androidXMLFile.get(i).get(0) + "<" + androidXMLFile.get(i).get(1) + ">" + androidXMLFile.get(i).get(2) + "\n";
				i ++;
			}
			while(j < leniOSFile) {
				outputdata = insertBlanks(outputdata, lenperLine);
				outputdata = outputdata + iOSXMLFile.get(j).get(0) + "<" + iOSXMLFile.get(j).get(1) + ">" + iOSXMLFile.get(j).get(2) + "\n";
				j ++;
			}
			
			
			
			
			System.out.println(outputdata);
			
			
			
			
			/*
			int lenAndroidFile = androidXMLFile.size();
			for(int i = 0 ; i < lenAndroidFile; i ++) {
				System.out.println("line " + i + ": " + androidXMLFile.get(i).get(0).length() + "" + androidXMLFile.get(i).get(0)
						+ androidXMLFile.get(i).get(1) + ", "
						+ androidXMLFile.get(i).get(2)
						);
			}
			
			int leniOSFile = iOSXMLFile.size();
			System.out.println("line 0" + iOSXMLFile.get(0).get(0));
			for(int i = 1 ; i < leniOSFile; i ++) {
				System.out.println("line " + i + ": " + iOSXMLFile.get(i).get(0).length() + "" + iOSXMLFile.get(i).get(0)
						+ iOSXMLFile.get(i).get(1) + ", "
						+ iOSXMLFile.get(i).get(2)
						);
			}
			*/
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public static String getBounds(String src) {
		
		String[] ss = src.split("=");//"[a,b][c,d]"
		//System.out.println(ss[0]);
		//System.out.println(ss[1]);
		String[] ss1 = ss[1].split("\\[");
		//System.out.println("+++++++++" + ss1[0]);
		String[] ss11 = ss1[1].split(",");
		double a = Double.parseDouble(ss11[0]);
		a = (a/1080) * 375;
		a = Math.round(a);
		String[] ss12 = ss11[1].split("\\]");
		double b = Double.parseDouble(ss12[0]);
		b = (b/1794)*667;
		b = Math.round(b);
		String[] ss2 = ss1[2].split("\"");
		String[] ss21 = ss2[0].split(",");
		double c = Double.parseDouble(ss21[0]);
		c = (c/1080) * 375;
		c = Math.round(c);
		String[] ss22 = ss21[1].split("\\]");
		double d = Double.parseDouble(ss22[0]);
		d = (d/1794)*667;
		d = Math.round(d);
		
		String result = "[" + (int)a + "," + (int)b + "][" + (int)c + "," + (int)d + "]";
		
		return result;
	}
	
	public static String getBoundsiOS(String x, String y, String w, String h) {
		
		//System.out.println("+++" + x +"," + y + "," + w + "," + h);
		
		String[] x1 = x.split("=");
		String[] x11 = x1[1].split("\"");
		
		String[] y1 = y.split("=");
		String[] y11 = y1[1].split("\"");
		
		String[] w1 = w.split("=");
		String[] w11 = w1[1].split("\"");
		
		String[] h1 = h.split("=");
		String[] h11 = h1[1].split("\"");
		
		String result = "[" + x11[1] + "," + y11[1] + "][" + w11[1] + "," + h11[1] + "]";
		
		return result;
	}
	
	public static String insertBlanks(String s, int num) {
		//System.out.println(num);
		for(int i = 0; i < num; i ++) {
			s = s + " ";
		}
		
		return s;
	}
	
	
	public static void main(String[] args) {
		/*
		iOSXMLFileAnalyzer.readFromLayoutFile("/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/FoxNews/20200211-Android-03-watch-02.xml", 
				"/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/FoxNews/20200211-iOS-03-watch-01.xml");
		*/
		
		
		
		
		
		
		
		
		
		
		
		//GUIPageXMLFileReader.readiOSPageXMLFile("/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/Demo_April/AppName_Netease_2/iOS/8_XCUIElementTypeButton[2].xml");
		
//		GUIPageXMLFileReader.readAndroidPageXMLFile("/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/Demo_April_2/AppName_businessweek_2/Android/2_android.widget.TextView[2].xml");
		
//		String iosRootPage = "/Users/jiruihua/Desktop/AppGUIMapping/issta19_CaseStudy/CaseStudyTestCases/Wire-UI-Tests/AbsTC_0_Test_01.xml";
//		String iosRootPage = "/Users/jiruihua/Desktop/AppGUIMapping/issta19_CaseStudy/iOSTestCasesExecution/Wire/IntermediaResults/7_testOpenConversation/iOS/0.xml";
		
//		String iosRootPage = "/Users/jiruihua/Desktop/AppGUIMapping/issta19_CaseStudy/CaseStudyTestCases/Wire-UI-Tests/T6_S3.xml";
//		String iosRootPage = "/Users/jiruihua/Desktop/AppGUIMapping/issta19_CaseStudy/iOSTestCasesExecution/WordPress/IntermediaResults/0_testAccountSettings_TWei/iOS/1.xml";

//		String iosRootPage = "/Users/jiruihua/Desktop/AppGUIMapping/issta19_CaseStudy/iOSTestCasesExecution/WordPress/IntermediaResults/30_testReader/iOS/8.xml";
//		String iosRootPage = "/Users/jiruihua/Desktop/AppGUIMapping/issta19_CaseStudy/TestCaseTransformationImplementation/WordPress/T30_S6.xml";
		
		
		
		String iosRootPage = "/Users/jiruihua/Desktop/AppGUIMapping/MappedAppPages/TestSet_Demo_2/AppName_WordPress/iOS/2.xml";
		
		iosRootPage = "/Users/jiruihua/Desktop/AppGUIMapping/MappedAppPages/TestSet_Demo_3/AppName_Teams/iOS/1.xml";
		
		GUIPageXMLFileReader.readiOSPageXMLFile(iosRootPage);
		
		
		
//		String androidRootPage = "/Users/jiruihua/Desktop/AppGUIMapping/issta19_CaseStudy/AndroidTestCasesGeneration/WordPress/IntermediaResults/30_testReader/Android/6.xml";
//		
		String androidRootPage = "/Users/jiruihua/Desktop/AppGUIMapping/MappedAppPages/TestSet_Demo/AppName_QQMusic/Android/2.xml";
		
//		GUIPageXMLFileReader.readAndroidPageXMLFile(androidRootPage);
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
