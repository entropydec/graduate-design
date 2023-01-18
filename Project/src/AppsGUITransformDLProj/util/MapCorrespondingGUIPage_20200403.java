package AppsGUITransformDLProj.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.commons.io.FileUtils;
import AppsGUITransformDLProj.GUI.*;


/**
 * Comparing to the original version, this class applies a new method.
 * */
public class MapCorrespondingGUIPage_20200403 {
	
	//maybe could locate the folder of the saved page files
	public String appInfo;
	
	public String overallFileFolder;
	
	//overallFileFolder+iOS/
	public String iOSPageFileFolder;
	//overallFileFolder+Android/
	public String androidPageFileFolder;
	
	public String iOSPageFileMinimizedFolder;
	public String androidPageFileMinimizedFolder;
	
	//fileName-page
	public HashMap<String, iOSXCUITestPage> iOSPageSet;
	public HashMap<String, AndroidGUIPage> androidPageSet;
	
	//fileName-page(the set has been minimized)
	public HashMap<String, iOSXCUITestPage> iOSPageMinimizedSet;
	public HashMap<String, AndroidGUIPage> androidPageMinimizedSet;
	
	//file location to file location (do not include the file extension)
	public HashMap<String, List<String>> correspondingPageRelations;
	public HashMap<String, List<List<String>>> correspondingPageRelations_20200328;
	
	//Classify equivalence set
	public List<HashMap<String, iOSXCUITestPage>> iOSPageEquivalenceSet;
	public List<HashMap<String, AndroidGUIPage>> androidPageEquivalenceSet;
	
	//fileName-page
	public HashMap<String, iOSXCUITestPage> iOSPageSetforParallelExecution;
	public HashMap<String, AndroidGUIPage> androidPageSetforParallelExecution;
	
	
	
	public MapCorrespondingGUIPage_20200403(String oFFolder) {
		this.appInfo = "";
		
		this.overallFileFolder = oFFolder;
		
		this.iOSPageFileFolder = oFFolder + "iOS/";
		this.androidPageFileFolder = oFFolder + "Android/";
		
		this.iOSPageFileMinimizedFolder = oFFolder + "iOS_Mini/";
		this.androidPageFileMinimizedFolder = oFFolder + "Android_Mini/";
		
		File fIOSMF = new File(this.iOSPageFileMinimizedFolder);
		if(!fIOSMF.exists()) fIOSMF.mkdir();
		File fAndroidMF = new File(this.androidPageFileMinimizedFolder);
		if(!fAndroidMF.exists()) fAndroidMF.mkdir();
		
		this.iOSPageSet = new HashMap<String, iOSXCUITestPage>();
		this.androidPageSet = new HashMap<String, AndroidGUIPage>();
		
		this.iOSPageMinimizedSet = new HashMap<String, iOSXCUITestPage>();
		this.androidPageMinimizedSet = new HashMap<String, AndroidGUIPage>();
		
		this.correspondingPageRelations = new HashMap<String, List<String>>();
		this.correspondingPageRelations_20200328 = new HashMap<String, List<List<String>>>();
		
		//Classify equivalence set
		this.iOSPageEquivalenceSet = new ArrayList<HashMap<String, iOSXCUITestPage>>();
		this.androidPageEquivalenceSet = new ArrayList<HashMap<String, AndroidGUIPage>>();
		
		this.iOSPageSetforParallelExecution = new HashMap<String, iOSXCUITestPage>();
		this.androidPageSetforParallelExecution = new HashMap<String, AndroidGUIPage>();
	}
	
	
	public void setiOSPageSetforParallelExecution() {//file name : order_className_[orderN]
		
		this.iOSPageSetforParallelExecution.clear();
		//ApplicationUnderTest.outputConsole2File(this.overallFileFolder);
		
		File dirFile = new File(this.iOSPageFileFolder);
		if(!dirFile.isDirectory()) {
			if(dirFile.isFile()) {
				System.out.println("#################### Error in setiOSPageSetforParallelExecution: dir isFile ####################");
			}
			System.out.println("#################### Error in setiOSPageSetforParallelExecution: dir is not file or dir ####################");
			return ;
		}
		String[] fileList = dirFile.list();
		int lenDirFile = fileList.length;
		for(int i = 0; i < lenDirFile; i ++) {
			if(fileList[i].endsWith(".xml")) {
				String fileNameTemp = this.iOSPageFileFolder + fileList[i];
				//System.out.println(fileNameTemp);
				iOSXCUITestPage pageTemp = GUIPageXMLFileReader.readiOSPageXMLFile(fileNameTemp);
				this.iOSPageSetforParallelExecution.put(fileList[i], pageTemp);
			}
		}
		
		System.out.println("ios file list len: " + this.iOSPageSetforParallelExecution.size());
	}
	
	public void setAndroidPageSetforParallelExecution() {

		this.androidPageSetforParallelExecution.clear();
		
		File dirFile = new File(this.androidPageFileFolder);
		if(!dirFile.isDirectory()) {
			if(dirFile.isFile()) {
				System.out.println("#################### Error in setiOSPageSet: dir isFile ####################");
			}
			System.out.println("#################### Error in setiOSPageSet: dir is not file or dir ####################");
			return ;
		}
		String[] fileList = dirFile.list();
		int lenDirFile = fileList.length;
		for(int i = 0; i < lenDirFile; i ++) {
			if(fileList[i].endsWith(".xml")) {
				String fileNameTemp = this.androidPageFileFolder + fileList[i];
				//System.out.println(fileNameTemp);
				AndroidGUIPage pageTemp = GUIPageXMLFileReader.readAndroidPageXMLFile(fileNameTemp);
				this.androidPageSetforParallelExecution.put(fileList[i], pageTemp);
			}
		}
		
		System.out.println("android file list len: " + this.androidPageSetforParallelExecution.size());
	}
	
	
	
	
	public void setiOSPageSet() {
		
		this.iOSPageSet.clear();
		//ApplicationUnderTest.outputConsole2File(this.overallFileFolder);
		
		File dirFile = new File(this.iOSPageFileFolder);
		if(!dirFile.isDirectory()) {
			if(dirFile.isFile()) {
				System.out.println("#################### Error in setiOSPageSet: dir isFile ####################");
			}
			System.out.println("#################### Error in setiOSPageSet: dir is not file or dir ####################");
			return ;
		}
		String[] fileList = dirFile.list();
		int lenDirFile = fileList.length;
		for(int i = 0; i < lenDirFile; i ++) {
			if(fileList[i].endsWith(".xml")) {
				String fileNameTemp = this.iOSPageFileFolder + fileList[i];
				//System.out.println(fileNameTemp);
				iOSXCUITestPage pageTemp = GUIPageXMLFileReader.readiOSPageXMLFile(fileNameTemp);
				this.iOSPageSet.put(fileList[i], pageTemp);
			}
		}
		
		System.out.println("len: " + this.iOSPageSet.size());
	}
	
	public void setAndroidPageSet() {

		this.androidPageSet.clear();
		
		File dirFile = new File(this.androidPageFileFolder);
		if(!dirFile.isDirectory()) {
			if(dirFile.isFile()) {
				System.out.println("#################### Error in setiOSPageSet: dir isFile ####################");
			}
			System.out.println("#################### Error in setiOSPageSet: dir is not file or dir ####################");
			return ;
		}
		String[] fileList = dirFile.list();
		int lenDirFile = fileList.length;
		for(int i = 0; i < lenDirFile; i ++) {
			if(fileList[i].endsWith(".xml")) {
				String fileNameTemp = this.androidPageFileFolder + fileList[i];
				//System.out.println(fileNameTemp);
				AndroidGUIPage pageTemp = GUIPageXMLFileReader.readAndroidPageXMLFile(fileNameTemp);
				this.androidPageSet.put(fileList[i], pageTemp);
			}
		}
		
		System.out.println("len: " + this.androidPageSet.size());
	}
	
	public IOSElement2AndroidElementRelation chooseOneNewPossiblePageMappingRelation(List<IOSElement2AndroidElementRelation> wholeList, List<IOSElement2AndroidElementRelation> currentList) {
		
		//IOSElement2AndroidElementRelation selectedRelation = new IOSElement2AndroidElementRelation();
		IOSElement2AndroidElementRelation selectedRelation = null;
		
		int lenWhole = wholeList.size();
		int lenCurrent = currentList.size();
		
		for(int i = 0; i < lenCurrent; i ++) {
			
			IOSElement2AndroidElementRelation selectedRelationTemp = currentList.get(i);
			iOSXCUITestElement selectedIOSElementTemp = selectedRelationTemp.iOSPageElement;
			AndroidGUIElement selectedAndroidElementTemp = selectedRelationTemp.androidPageElement;
			
			int existLabel = 0;
			
			for(int j = 0; j < lenWhole; j ++) {
				
				IOSElement2AndroidElementRelation comparingRelationTemp = wholeList.get(j);
				iOSXCUITestElement comparingIOSElementTemp = comparingRelationTemp.iOSPageElement;
				AndroidGUIElement comparingAndroidElementTemp = comparingRelationTemp.androidPageElement;
				
				/*
				if(
				(selectedIOSElementTemp.EName.equals(comparingIOSElementTemp.EName)) ||
				(selectedIOSElementTemp.ELabel.equals(comparingIOSElementTemp.ELabel))) {
					
					if(this.calculateOverlapAreaofIOSGUIElements(selectedIOSElementTemp, comparingIOSElementTemp) > 0) {
						existLabel ++;
						break;
					}
					
				}
				else if(selectedAndroidElementTemp.EText.equals(comparingAndroidElementTemp.EText)) {
					
					if(this.calculateOverlapAreaofAndroidGUIElements(selectedAndroidElementTemp, comparingAndroidElementTemp) > 0) {
						existLabel ++;
						break;
					}
					
				}*/
				
				
				if(
				(selectedIOSElementTemp.EName.equals(comparingIOSElementTemp.EName)) ||
				(selectedIOSElementTemp.ELabel.equals(comparingIOSElementTemp.ELabel))) {
					
					if(this.calculateOverlapAreaofIOSGUIElements(selectedIOSElementTemp, comparingIOSElementTemp) > 0) {
						
						if(selectedAndroidElementTemp.EText.equals(comparingAndroidElementTemp.EText)) {
							
							if(this.calculateOverlapAreaofAndroidGUIElements(selectedAndroidElementTemp, comparingAndroidElementTemp) > 0) {
								existLabel ++;
								break;
							}
							
						}
						
						
					}
							
				}
				
				
				
				
			}
			
			if(existLabel == 0) {
				selectedRelation = selectedRelationTemp;
				break;
			}
			else {//existLabel == 1
				//do nothing, and continue the next cycle.
			}
			
		}
		
		
		
		return selectedRelation;
	}
	
	
	public List<IOSElement2AndroidElementRelation> findConfirmedCorrespondingControlsfromRootPages() {
		
		//preset the threshold
		double similarityValue = 0.8;
		
		iOSXCUITestPage iOSRootPage = this.iOSPageSet.get("rootPage.xml");
		AndroidGUIPage androidRootPage = this.androidPageSet.get("rootPage.xml");
		
		/*
		if(iOSRootPage == null) {
			System.out.println("iOSRootPage is null");
		}
		else {
			iOSXCUITestPage.printPageTree(iOSRootPage);
		}
		if(androidRootPage == null) {
			System.out.println("androidRootPage is null");
		}
		else {
			AndroidGUIPage.printPageTree(androidRootPage);
		}
		*/
		
		List<iOSXCUITestElement> iOSRootPageElementList = iOSXCUITestPage.deriveLeafSetofTree(iOSRootPage);
		iOSRootPageElementList = removeSameLocationOnefromLeafSetIOS(iOSRootPageElementList);
		
		List<AndroidGUIElement> androidRootPageElementList = AndroidGUIPage.deriveLeafSetofTree(androidRootPage);
		androidRootPageElementList = removeSameLocationOnefromLeafSetAndroid(androidRootPageElementList);
		
		List<IOSElement2AndroidElementRelation> relationList = new ArrayList<IOSElement2AndroidElementRelation>();
		
		int lenIOSElementList = iOSRootPageElementList.size();
		int lenAndroidElementList = androidRootPageElementList.size();
		
		for(int i = 0; i < lenIOSElementList; i ++) {
			
			iOSXCUITestElement iOSElementTemp = iOSRootPageElementList.get(i);
			for(int j = 0; j < lenAndroidElementList; j ++) {
				AndroidGUIElement androidElementTemp = androidRootPageElementList.get(j);
				
				IOSElement2AndroidElementRelation relationTemp = new IOSElement2AndroidElementRelation();
				
				relationTemp.iOSPageElement = iOSElementTemp;
				relationTemp.androidPageElement = androidElementTemp;
				
				relationTemp.textSimilarity = calculateTextSimilarity(iOSElementTemp, androidElementTemp);
				
				List<Double> overlapCalculation = calculateOverlapAreaofTwoGUIElements(androidElementTemp, iOSElementTemp);
				relationTemp.overlapIntersection = overlapCalculation.get(0);
				relationTemp.overlapUnionSet = overlapCalculation.get(1);
				
				relationList.add(relationTemp);
			}
			
		}
		
		List<IOSElement2AndroidElementRelation> resultList = new ArrayList<IOSElement2AndroidElementRelation>();
		
		int lenRelationList = relationList.size();
		for(int k = 0; k < lenRelationList; k ++) {
			
			if((relationList.get(k).textSimilarity != null) && (relationList.get(k).overlapIntersection > 0)) {
				resultList.add(relationList.get(k));
				
			}
			
		}
		
		return resultList;
		
	}
	
	public String calculateTextSimilarity(iOSXCUITestElement iOSElement, AndroidGUIElement androidElement) {
		
		String androidText = androidElement.EText;
		//Value???
		String iOSName = iOSElement.EName;
		String iOSLabel = iOSElement.ELabel;
		
		String result = null;
		if(androidText == null) {
			//return null;
		}
		else if( androidText.equals(iOSName) || androidText.equals(iOSLabel)) {
			result = androidText;
			//System.out.println();
		}
		
		return result;
	}
	
	/**
	 * Currently, this method is same as calculateTextSimilarityofTwotPages
	 * */
	public List<IOSElement2AndroidElementRelation> findConfirmedCorrespondingControlsfromTwoCorrespondingPages(iOSXCUITestPage iosPage, AndroidGUIPage androidPage) {
		
		//preset the threshold
		double similarityValue = 0.8;
		
		
		List<IOSElement2AndroidElementRelation> resultList = new ArrayList<IOSElement2AndroidElementRelation>();
		
		if(iosPage == null) {
			System.out.println("iosPage is null");
			return resultList;
		}
		if(androidPage == null) {
			System.out.println("androidPage is null");
			return resultList;
		}
		
		
		List<iOSXCUITestElement> iOSPageElementList = iOSXCUITestPage.deriveLeafSetofTree(iosPage);
		iOSPageElementList = removeSameLocationOnefromLeafSetIOS(iOSPageElementList);
		
		List<AndroidGUIElement> androidPageElementList = AndroidGUIPage.deriveLeafSetofTree(androidPage);
		androidPageElementList = removeSameLocationOnefromLeafSetAndroid(androidPageElementList);
		
		List<IOSElement2AndroidElementRelation> relationList = new ArrayList<IOSElement2AndroidElementRelation>();
		
		int lenIOSElementList = iOSPageElementList.size();
		int lenAndroidElementList = androidPageElementList.size();
		
		for(int i = 0; i < lenIOSElementList; i ++) {
			
			iOSXCUITestElement iOSElementTemp = iOSPageElementList.get(i);
			for(int j = 0; j < lenAndroidElementList; j ++) {
				AndroidGUIElement androidElementTemp = androidPageElementList.get(j);
				
				IOSElement2AndroidElementRelation relationTemp = new IOSElement2AndroidElementRelation();
				
				relationTemp.iOSPageElement = iOSElementTemp;
				relationTemp.androidPageElement = androidElementTemp;
				
				relationTemp.textSimilarity = calculateTextSimilarity(iOSElementTemp, androidElementTemp);
				
				List<Double> overlapCalculation = calculateOverlapAreaofTwoGUIElements(androidElementTemp, iOSElementTemp);
				relationTemp.overlapIntersection = overlapCalculation.get(0);
				relationTemp.overlapUnionSet = overlapCalculation.get(1);
				
				relationList.add(relationTemp);
			}
			
		}
		
		
		
		int lenRelationList = relationList.size();
		for(int k = 0; k < lenRelationList; k ++) {
			
			if((relationList.get(k).textSimilarity != null) && (relationList.get(k).overlapIntersection > 0)) {
				resultList.add(relationList.get(k));
				
			}
			
		}
		
		return resultList;
		
	}
	
	
	
	public List<IOSElement2AndroidElementRelation> calculateTextSimilarityofTwotPages(iOSXCUITestPage iosPage, AndroidGUIPage androidPage) {
		
		//preset the threshold
		double similarityValue = 0.8;
		
		
		List<IOSElement2AndroidElementRelation> resultList = new ArrayList<IOSElement2AndroidElementRelation>();
		
		if(iosPage == null) {
			System.out.println("iosPage is null");
			return resultList;
		}
		if(androidPage == null) {
			System.out.println("androidPage is null");
			return resultList;
		}
		
		
		List<iOSXCUITestElement> iOSPageElementList = iOSXCUITestPage.deriveLeafSetofTree(iosPage);
		iOSPageElementList = removeSameLocationOnefromLeafSetIOS(iOSPageElementList);
		
		List<AndroidGUIElement> androidPageElementList = AndroidGUIPage.deriveLeafSetofTree(androidPage);
		androidPageElementList = removeSameLocationOnefromLeafSetAndroid(androidPageElementList);
		
		List<IOSElement2AndroidElementRelation> relationList = new ArrayList<IOSElement2AndroidElementRelation>();
		
		int lenIOSElementList = iOSPageElementList.size();
		int lenAndroidElementList = androidPageElementList.size();
		
		for(int i = 0; i < lenIOSElementList; i ++) {
			
			iOSXCUITestElement iOSElementTemp = iOSPageElementList.get(i);
			for(int j = 0; j < lenAndroidElementList; j ++) {
				AndroidGUIElement androidElementTemp = androidPageElementList.get(j);
				
				IOSElement2AndroidElementRelation relationTemp = new IOSElement2AndroidElementRelation();
				
				relationTemp.iOSPageElement = iOSElementTemp;
				relationTemp.androidPageElement = androidElementTemp;
				
				relationTemp.textSimilarity = calculateTextSimilarity(iOSElementTemp, androidElementTemp);
				
				List<Double> overlapCalculation = calculateOverlapAreaofTwoGUIElements(androidElementTemp, iOSElementTemp);
				relationTemp.overlapIntersection = overlapCalculation.get(0);
				relationTemp.overlapUnionSet = overlapCalculation.get(1);
				
				relationList.add(relationTemp);
			}
			
		}
		
		
		
		int lenRelationList = relationList.size();
		for(int k = 0; k < lenRelationList; k ++) {
			
			if((relationList.get(k).textSimilarity != null) && (relationList.get(k).overlapIntersection > 0)) {
				resultList.add(relationList.get(k));
				
			}
			
		}
		
		return resultList;
		
	}
	
	
	
	
	
	
	
	
	
	public void minimizeiOSPageSet() {
		//always keep the root page
		//int len = this.iOSPageSet.size();
		
		this.iOSPageMinimizedSet.put("rootPage.xml", this.iOSPageSet.get("rootPage.xml"));
		
		for(Map.Entry<String, iOSXCUITestPage> entryPSet: this.iOSPageSet.entrySet()) {
			int label = this.iOSPageMinimizedSet.size();
			for(Map.Entry<String, iOSXCUITestPage> entryMPSet: this.iOSPageMinimizedSet.entrySet()) {
				if(iOSXCUITestPage.compareElementTree(entryPSet.getValue(), entryMPSet.getValue())) {
					break;
				}
				else {
					label --;
				}
			}
			if(label == 0) {
				this.iOSPageMinimizedSet.put(entryPSet.getKey(), entryPSet.getValue());
			}
		}
		
		System.out.println("MPSet size: " + this.iOSPageMinimizedSet.size());
	}
	
	public void classifyEquivalenceIOSPageSet() {
		
		for(Map.Entry<String, iOSXCUITestPage> entryMiniPSet: this.iOSPageMinimizedSet.entrySet()) {
			HashMap<String, iOSXCUITestPage> tempClassSet = new HashMap<String, iOSXCUITestPage>();
			tempClassSet.put(entryMiniPSet.getKey(), entryMiniPSet.getValue());
			this.iOSPageEquivalenceSet.add(tempClassSet);
		}
		
		for(Map.Entry<String, iOSXCUITestPage> entryPSet: this.iOSPageSet.entrySet()) {
			
			int label_2 = 0;
			for(Map.Entry<String, iOSXCUITestPage> entryMPSet: this.iOSPageMinimizedSet.entrySet()) {
				
				if(entryPSet.getKey().equals(entryMPSet.getKey())) {
					label_2 = 1;
					break;
				}
				else if(iOSXCUITestPage.compareElementTree(entryPSet.getValue(), entryMPSet.getValue())) {
					
					int lenTemp = this.iOSPageEquivalenceSet.size();
					int label_1 = 0;
					for(int i = 0; i < lenTemp; i ++) {
						if(this.iOSPageEquivalenceSet.get(i).containsKey(entryMPSet.getKey())) {
							this.iOSPageEquivalenceSet.get(i).put(entryPSet.getKey(), entryPSet.getValue());
							label_1 = 1;
							break;
						}
					}
					if(label_1 == 0) {
						System.out.println("Warning: Page [" + entryMPSet.getKey() + "] is not a known page in the equivalence set (label_1 warning)!");
					}
					else {
						label_2 = 1;
					}
					break;
				}
				
			}
			if(label_2 == 0) {
				System.out.println("Warning: Page [" + entryPSet.getKey() + "] could not find a similar page in the minimized set (label_2 warning)!");
			}
		}
		
		int lenEquivalenceSet = this.iOSPageEquivalenceSet.size();
		for(int i = 0; i < lenEquivalenceSet; i ++) {
			System.out.print("" + this.iOSPageEquivalenceSet.get(i).size() + ": ");
			for(Map.Entry<String, iOSXCUITestPage> entryTPSet: this.iOSPageEquivalenceSet.get(i).entrySet()) {
				System.out.print("" + entryTPSet.getKey() + " ");
			}
			System.out.println();
		}
		
	}
	
	
	
	public void minimizeAndroidPageSet() {
		
		//int len = this.iOSPageSet.size();
		
		this.androidPageMinimizedSet.put("rootPage.xml", this.androidPageSet.get("rootPage.xml"));
		
		for(Map.Entry<String, AndroidGUIPage> entryPSet: this.androidPageSet.entrySet()) {
			int label = this.androidPageMinimizedSet.size();
			for(Map.Entry<String, AndroidGUIPage> entryMPSet: this.androidPageMinimizedSet.entrySet()) {
				if(AndroidGUIPage.compareElementTree(entryPSet.getValue(), entryMPSet.getValue())) {
					//System.out.println("SamePage: " + entryPSet.getKey() + ", " + entryMPSet.getKey());
					break;
				}
				else {
					label --;
				}
			}
			if(label == 0) {
				this.androidPageMinimizedSet.put(entryPSet.getKey(), entryPSet.getValue());
			}
		}
		
		System.out.println("MPSet size: " + this.androidPageMinimizedSet.size());
	}
	
	public void classifyEquivalenceAndroidPageSet() {
		
		for(Map.Entry<String, AndroidGUIPage> entryMiniPSet: this.androidPageMinimizedSet.entrySet()) {
			HashMap<String, AndroidGUIPage> tempClassSet = new HashMap<String, AndroidGUIPage>();
			tempClassSet.put(entryMiniPSet.getKey(), entryMiniPSet.getValue());
			this.androidPageEquivalenceSet.add(tempClassSet);
		}
		
		for(Map.Entry<String, AndroidGUIPage> entryPSet: this.androidPageSet.entrySet()) {
			
			int label_2 = 0;
			for(Map.Entry<String, AndroidGUIPage> entryMPSet: this.androidPageMinimizedSet.entrySet()) {
				
				if(entryPSet.getKey().equals(entryMPSet.getKey())) {
					label_2 = 1;
					break;
				}
				else if(AndroidGUIPage.compareElementTree(entryPSet.getValue(), entryMPSet.getValue())) {
					
					int lenTemp = this.androidPageEquivalenceSet.size();
					int label_1 = 0;
					for(int i = 0; i < lenTemp; i ++) {
						if(this.androidPageEquivalenceSet.get(i).containsKey(entryMPSet.getKey())) {
							this.androidPageEquivalenceSet.get(i).put(entryPSet.getKey(), entryPSet.getValue());
							label_1 = 1;
							break;
						}
					}
					if(label_1 == 0) {
						System.out.println("Warning: Page [" + entryMPSet.getKey() + "] is not a known page in the equivalence set (label_1 warning)!");
					}
					else {
						label_2 = 1;
					}
					break;
				}
				
			}
			if(label_2 == 0) {
				System.out.println("Warning: Page [" + entryPSet.getKey() + "] could not find a similar page in the minimized set (label_2 warning)!");
			}
		}
		
		int lenEquivalenceSet = this.androidPageEquivalenceSet.size();
		for(int i = 0; i < lenEquivalenceSet; i ++) {
			System.out.print("" + this.androidPageEquivalenceSet.get(i).size() + ": ");
			for(Map.Entry<String, AndroidGUIPage> entryTPSet: this.androidPageEquivalenceSet.get(i).entrySet()) {
				System.out.print("" + entryTPSet.getKey() + " ");
			}
			System.out.println();
		}
		
	}
	
	
	
	/*public void copyIOSMinmizedPageSet2itsFolder() {
		
		for(Map.Entry<String, iOSXCUITestPage> entryMPSet: this.iOSPageMinimizedSet.entrySet()) {
			
			String sourceFileName = this.iOSPageFileFolder + entryMPSet.getKey();
			File srcFile = new File(sourceFileName);
			String targetFileName = this.iOSPageFileMinimizedFolder + entryMPSet.getKey();
			File tarFile = new File(targetFileName);
			try {
				FileUtils.copyFile(srcFile, tarFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String sourcePICFileName = sourceFileName.substring(0, sourceFileName.lastIndexOf(".")) + ".png";
			File srcPFile = new File(sourcePICFileName);
			String targetPICFileName = targetFileName.substring(0, targetFileName.lastIndexOf(".")) + ".png";
			File tarPFile = new File(targetPICFileName);
			try {
				FileUtils.copyFile(srcPFile, tarPFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void copyAndroidMinmizedPageSet2itsFolder() {
		
		for(Map.Entry<String, AndroidGUIPage> entryMPSet: this.androidPageMinimizedSet.entrySet()) {
			
			String sourceFileName = this.androidPageFileFolder + entryMPSet.getKey();
			File srcFile = new File(sourceFileName);
			String targetFileName = this.androidPageFileMinimizedFolder + entryMPSet.getKey();
			File tarFile = new File(targetFileName);
			try {
				FileUtils.copyFile(srcFile, tarFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String sourcePICFileName = sourceFileName.substring(0, sourceFileName.lastIndexOf(".")) + ".png";
			File srcPFile = new File(sourcePICFileName);
			String targetPICFileName = targetFileName.substring(0, targetFileName.lastIndexOf(".")) + ".png";
			File tarPFile = new File(targetPICFileName);
			try {
				FileUtils.copyFile(srcPFile, tarPFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	
	
	
	public void MapCorrespondingPages() {
		
		//iOSPageMinimizedSet
		//androidPageMinimizedSet
		
		HashMap<String, List<iOSXCUITestElement>> iOSPageFeaturedElementSet = new HashMap<String, List<iOSXCUITestElement>>();
		for(Map.Entry<String,iOSXCUITestPage> entryIOSPMSet: this.iOSPageMinimizedSet.entrySet()) {
			List<iOSXCUITestElement> tempIOS = iOSXCUITestPage.deriveLeafSetofPruneTreeBasedOnHeuristicComparisonOpt(entryIOSPMSet.getValue());
			List<iOSXCUITestElement> tempMinimizedIOS = removeSameLocationOnefromLeafSetIOS(tempIOS);
			iOSPageFeaturedElementSet.put(entryIOSPMSet.getKey(), tempMinimizedIOS);
		}
		
		HashMap<String, List<AndroidGUIElement>> androidPageFeaturedElementSet = new HashMap<String, List<AndroidGUIElement>>();
		for(Map.Entry<String,AndroidGUIPage> entryAndroidPMSet: this.androidPageMinimizedSet.entrySet()) {
			List<AndroidGUIElement> tempAndroid = AndroidGUIPage.deriveLeafSetofPruneTreeBasedOnHeuristicComparisonOpt(entryAndroidPMSet.getValue());
			List<AndroidGUIElement> tempMinimizedAndroid = removeSameLocationOnefromLeafSetAndroid(tempAndroid);
			androidPageFeaturedElementSet.put(entryAndroidPMSet.getKey(), tempMinimizedAndroid);
		}
		
		
		
		//this.correspondingPageRelations
		for(Map.Entry<String, List<AndroidGUIElement>> entryAndroidPMSet: androidPageFeaturedElementSet.entrySet()) {
			
			List<AndroidGUIElement> tempAndroidPage = entryAndroidPMSet.getValue();
			List<List<String>> values = new ArrayList<List<String>>();
			
			//String maxKey = "";
			//double maxValue = 0;
			
			for(Map.Entry<String, List<iOSXCUITestElement>> entryIOSPMSet: iOSPageFeaturedElementSet.entrySet()) {
				
				List<iOSXCUITestElement> tempIOSPage = entryIOSPMSet.getValue();
				double tempV = this.calculateSimilarityofTwoPages(tempAndroidPage, tempIOSPage);
//				if(tempV > maxValue) {
//					maxKey = entryIOSPMSet.getKey();
//					maxValue = tempV;
//				}
				List<String> temp = new ArrayList<String>();
				temp.add(entryIOSPMSet.getKey());
				temp.add("" + tempV);
				values.add(temp);
			}
			
//			List<String> temp = new ArrayList<String>();
//			temp.add(maxKey);
//			temp.add("" + maxValue);
//			values.add(temp);
			this.correspondingPageRelations_20200328.put(entryAndroidPMSet.getKey(), values);
		}
		
		this.printCorrespondingPageRelations_20200328();
		
	}
	
	public void MapCorrespondingPagesbyTriggerElementLocationandElementOverlap() {
		
		System.out.println("MapCorrespondingPagesbyTriggerElementLocationandElementOverlap");
		
		int lenIOSPageEquiSet = this.iOSPageEquivalenceSet.size();
		int lenAndroidPageEquiSet = this.androidPageEquivalenceSet.size();
		
		for(int i = 0; i < lenIOSPageEquiSet; i ++) {
			
			
			HashMap<String, List<List<String>>> mappingRealtionBetweenEquiSets = new HashMap<String, List<List<String>>>();
			
			for(int j = 0; j < lenAndroidPageEquiSet; j ++) {
				
				HashMap<String, List<iOSXCUITestElement>> iOSPageFeaturedElementSet = new HashMap<String, List<iOSXCUITestElement>>();
				for(Map.Entry<String,iOSXCUITestPage> entryIOSPMSet: this.iOSPageEquivalenceSet.get(i).entrySet()) {
					List<iOSXCUITestElement> tempIOS = iOSXCUITestPage.deriveLeafSetofPruneTreeBasedOnHeuristicComparisonOpt(entryIOSPMSet.getValue());
					List<iOSXCUITestElement> tempMinimizedIOS = removeSameLocationOnefromLeafSetIOS(tempIOS);
					iOSPageFeaturedElementSet.put(entryIOSPMSet.getKey(), tempMinimizedIOS);
				}
				
				HashMap<String, List<AndroidGUIElement>> androidPageFeaturedElementSet = new HashMap<String, List<AndroidGUIElement>>();
				for(Map.Entry<String,AndroidGUIPage> entryAndroidPMSet: this.androidPageEquivalenceSet.get(j).entrySet()) {
					List<AndroidGUIElement> tempAndroid = AndroidGUIPage.deriveLeafSetofPruneTreeBasedOnHeuristicComparisonOpt(entryAndroidPMSet.getValue());
					List<AndroidGUIElement> tempMinimizedAndroid = removeSameLocationOnefromLeafSetAndroid(tempAndroid);
					androidPageFeaturedElementSet.put(entryAndroidPMSet.getKey(), tempMinimizedAndroid);
				}
				
				
				for(Map.Entry<String, List<AndroidGUIElement>> entryAndroidPMSet: androidPageFeaturedElementSet.entrySet()) {
					
					List<AndroidGUIElement> tempAndroidPage = entryAndroidPMSet.getValue();
					List<List<String>> values = new ArrayList<List<String>>();
					List<String> valueoverlap = new ArrayList<String>();
					
					//String maxKey = "";
					//double maxValue = 0;
					int count = 0;
					for(Map.Entry<String, List<iOSXCUITestElement>> entryIOSPMSet: iOSPageFeaturedElementSet.entrySet()) {
						
						if(count == 0) {
							
							List<iOSXCUITestElement> tempIOSPage = entryIOSPMSet.getValue();
							double tempV = this.calculateSimilarityofTwoPages(tempAndroidPage, tempIOSPage);
//							
							valueoverlap.add(entryIOSPMSet.getKey());
							valueoverlap.add("" + tempV);
							
							count ++;
						}
						
						if(entryAndroidPMSet.getKey().equals("rootPage.xml") || entryIOSPMSet.getKey().equals("rootPage.xml")) {
							
						}
						else {
							List<Double> locationDistanceTemp = this.compareTwoPagebyTriggerElementLocation(entryIOSPMSet.getKey(), entryAndroidPMSet.getKey());
							
							//if(locationDistanceTemp.get(0) < 50 && locationDistanceTemp.get(1) < 100) {
								
								List<String> temp = new ArrayList<String>();
								temp.add(entryIOSPMSet.getKey());
								temp.add(valueoverlap.get(1));
								temp.add("" + locationDistanceTemp.get(0));
								temp.add("" + locationDistanceTemp.get(1));
								values.add(temp);
							//}
						}
					}
					
//					List<String> temp = new ArrayList<String>();
//					temp.add(maxKey);
//					temp.add("" + maxValue);
//					values.add(temp);
					//this.correspondingPageRelations_20200328.put(entryAndroidPMSet.getKey(), values);
					if(mappingRealtionBetweenEquiSets.containsKey(entryAndroidPMSet.getKey())) {
						System.out.println("================ Warning: have contained " + entryAndroidPMSet.getKey() + " ==================");
//						List<List<String>> vt = mappingRealtionBetweenEquiSets.get(entryAndroidPMSet.getKey());
//						int lenIII = values.size();
//						int iniIII = vt.size();
//						for(int iii = iniIII; iii < lenIII; iii ++) {
//							vt.add(values.get(iii - iniIII));
//						}
					}
					//optional use else here.
					mappingRealtionBetweenEquiSets.put(entryAndroidPMSet.getKey(), values);
				}
				
				//this.printCorrespondingPageRelations_20200328();
				//this.correspondingPageRelations_20200328.clear();
				
				
			}
			
			String sourceR = "";
			String targetR = "";
			double w = 1080;
			double h = 1920;
			double overlap = 0;
			List<List<String>> valuess = new ArrayList<List<String>>();
			
			for(Map.Entry<String, List<List<String>>> entryLongList: mappingRealtionBetweenEquiSets.entrySet()) {
				
				for(int ii = 0; ii < entryLongList.getValue().size(); ii ++) {
					
					if(entryLongList.getValue().get(ii).size() > 2) {
						double wTemp = Double.parseDouble(entryLongList.getValue().get(ii).get(2));
						double hTemp = Double.parseDouble(entryLongList.getValue().get(ii).get(3));
						double olTemp = Double.parseDouble(entryLongList.getValue().get(ii).get(1));
						
						if((olTemp >= overlap)&&((wTemp + hTemp) <= (w + h))&&(Math.abs(wTemp - hTemp) <= Math.abs(w - h))) {
							overlap = olTemp;
							w = wTemp;
							h = hTemp;
							sourceR = entryLongList.getKey();
							targetR = entryLongList.getValue().get(ii).get(0);
						}
					}
					
				}
				
			}
			
			List<String> vss = new ArrayList<String>();
			vss.add(targetR);
			vss.add("" + overlap);
			vss.add("" + w);
			vss.add("" + h);
			valuess.add(vss);
			this.correspondingPageRelations_20200328.put(sourceR, valuess);
			
			mappingRealtionBetweenEquiSets.clear();
		}
		
		this.printCorrespondingPageRelations_20200328();
	}
	
	
	
	public void MapCorrespondingPagesbyTriggerElementLocationandElementOverlap_20200401() {
		
		System.out.println("MapCorrespondingPagesbyTriggerElementLocationandElementOverlap_20200401");
		
		int lenIOSPageEquiSet = this.iOSPageEquivalenceSet.size();
		int lenAndroidPageEquiSet = this.androidPageEquivalenceSet.size();
		
		HashMap<String, List<List<String>>> mappingRealtionBetweenEquiSets = new HashMap<String, List<List<String>>>();
		//List<Integer> countttt = new ArrayList<Integer>();
		
		for(int i = 0; i < lenIOSPageEquiSet; i ++) {
			
			for(int j = 0; j < lenAndroidPageEquiSet; j ++) {
				
				HashMap<String, List<iOSXCUITestElement>> iOSPageFeaturedElementSet = new HashMap<String, List<iOSXCUITestElement>>();
				for(Map.Entry<String,iOSXCUITestPage> entryIOSPMSet: this.iOSPageEquivalenceSet.get(i).entrySet()) {
					List<iOSXCUITestElement> tempIOS = iOSXCUITestPage.deriveLeafSetofPruneTreeBasedOnHeuristicComparisonOpt(entryIOSPMSet.getValue());
					List<iOSXCUITestElement> tempMinimizedIOS = removeSameLocationOnefromLeafSetIOS(tempIOS);
					iOSPageFeaturedElementSet.put(entryIOSPMSet.getKey(), tempMinimizedIOS);
				}
				
				HashMap<String, List<AndroidGUIElement>> androidPageFeaturedElementSet = new HashMap<String, List<AndroidGUIElement>>();
				for(Map.Entry<String,AndroidGUIPage> entryAndroidPMSet: this.androidPageEquivalenceSet.get(j).entrySet()) {
					List<AndroidGUIElement> tempAndroid = AndroidGUIPage.deriveLeafSetofPruneTreeBasedOnHeuristicComparisonOpt(entryAndroidPMSet.getValue());
					List<AndroidGUIElement> tempMinimizedAndroid = removeSameLocationOnefromLeafSetAndroid(tempAndroid);
					androidPageFeaturedElementSet.put(entryAndroidPMSet.getKey(), tempMinimizedAndroid);
				}
				//countttt.add(iOSPageFeaturedElementSet.size());
				//countttt.add(androidPageFeaturedElementSet.size());
				
				for(Map.Entry<String, List<AndroidGUIElement>> entryAndroidPMSet: androidPageFeaturedElementSet.entrySet()) {
					
					List<AndroidGUIElement> tempAndroidPage = entryAndroidPMSet.getValue();
					List<List<String>> values = new ArrayList<List<String>>();
					List<String> valueoverlap = new ArrayList<String>();
					
					//String maxKey = "";
					//double maxValue = 0;
					int count = 0;
					for(Map.Entry<String, List<iOSXCUITestElement>> entryIOSPMSet: iOSPageFeaturedElementSet.entrySet()) {
						
						if(count == 0) {
							
							List<iOSXCUITestElement> tempIOSPage = entryIOSPMSet.getValue();
							double tempV = this.calculateSimilarityofTwoPages(tempAndroidPage, tempIOSPage);
//							
							valueoverlap.add(entryIOSPMSet.getKey());
							valueoverlap.add("" + tempV);
							
							count ++;
						}
						
						if(entryAndroidPMSet.getKey().equals("rootPage.xml") || entryIOSPMSet.getKey().equals("rootPage.xml")) {
							
						}
						else {
							List<Double> locationDistanceTemp = this.compareTwoPagebyTriggerElementLocation(entryIOSPMSet.getKey(), entryAndroidPMSet.getKey());
							
							//if(locationDistanceTemp.get(0) < 50 && locationDistanceTemp.get(1) < 100) {
								
								List<String> temp = new ArrayList<String>();
								temp.add(entryIOSPMSet.getKey());
								temp.add(valueoverlap.get(1));
								temp.add("" + locationDistanceTemp.get(0));
								temp.add("" + locationDistanceTemp.get(1));
								values.add(temp);
							//}
						}
					}
					
//					List<String> temp = new ArrayList<String>();
//					temp.add(maxKey);
//					temp.add("" + maxValue);
//					values.add(temp);
					//this.correspondingPageRelations_20200328.put(entryAndroidPMSet.getKey(), values);
					if(mappingRealtionBetweenEquiSets.containsKey(entryAndroidPMSet.getKey())) {
						System.out.println("================ Warning: have contained " + entryAndroidPMSet.getKey() + " ==================");
						
						List<List<String>> vt = mappingRealtionBetweenEquiSets.get(entryAndroidPMSet.getKey());
						int lenIII = values.size();
						
						for(int iii = 0; iii < lenIII; iii ++) {
							vt.add(values.get(iii));
						}
						mappingRealtionBetweenEquiSets.put(entryAndroidPMSet.getKey(), vt);
					}
					else {
						mappingRealtionBetweenEquiSets.put(entryAndroidPMSet.getKey(), values);
					}
				}
				
				//this.printCorrespondingPageRelations_20200328();
				//this.correspondingPageRelations_20200328.clear();
				
				
			}
			
			/*
			String sourceR = "";
			String targetR = "";
			double w = 1080;
			double h = 1920;
			double overlap = 0;
			List<List<String>> valuess = new ArrayList<List<String>>();
			
			for(Map.Entry<String, List<List<String>>> entryLongList: mappingRealtionBetweenEquiSets.entrySet()) {
				
				for(int ii = 0; ii < entryLongList.getValue().size(); ii ++) {
					
					if(entryLongList.getValue().get(ii).size() > 2) {
						double wTemp = Double.parseDouble(entryLongList.getValue().get(ii).get(2));
						double hTemp = Double.parseDouble(entryLongList.getValue().get(ii).get(3));
						double olTemp = Double.parseDouble(entryLongList.getValue().get(ii).get(1));
						
						if((olTemp >= overlap)&&((wTemp + hTemp) <= (w + h))&&(Math.abs(wTemp - hTemp) <= Math.abs(w - h))) {
							overlap = olTemp;
							w = wTemp;
							h = hTemp;
							sourceR = entryLongList.getKey();
							targetR = entryLongList.getValue().get(ii).get(0);
						}
					}
					
				}
				
			}
			
			List<String> vss = new ArrayList<String>();
			vss.add(targetR);
			vss.add("" + overlap);
			vss.add("" + w);
			vss.add("" + h);
			valuess.add(vss);
			this.correspondingPageRelations_20200328.put(sourceR, valuess);
			
			mappingRealtionBetweenEquiSets.clear();
			
			*/
		}
		
		//this.printCorrespondingPageRelations_20200328();
		
		//printCorrespondingPageRelationsAccordingtoInputData(mappingRealtionBetweenEquiSets);
		
//		int lc = countttt.size();
//		System.out.println("countttt size: " + lc);
//		for(int i = 0; i < lc; i = i + 2) {
//			String temp = "[" + countttt.get(i) +", "+ countttt.get(i+1) + "]";
//			System.out.println(temp);
//		}
		
		HashMap<String, List<List<String>>> mappingRealtionBetweenEquiPFSets = new HashMap<String, List<List<String>>>();
		for(int i = 0; i < lenAndroidPageEquiSet; i ++) {
			
			for(Map.Entry<String, AndroidGUIPage> entryList: this.androidPageEquivalenceSet.get(i).entrySet()) {
				
				List<List<String>> temp = mappingRealtionBetweenEquiSets.get(entryList.getKey());
				temp = dominatingSolutionSet(temp);
				mappingRealtionBetweenEquiPFSets.put(entryList.getKey(), temp);
			}
			System.out.println("===== EquiSet: " + i + " =====");
			printCorrespondingPageRelationsAccordingtoInputData(mappingRealtionBetweenEquiPFSets);
			mappingRealtionBetweenEquiPFSets.clear();
			
		}
//		for(Map.Entry<String, List<List<String>>> entryLongList: mappingRealtionBetweenEquiSets.entrySet()) {
//			//if()
//			System.out.println("entryLongList.getValue() length: " + entryLongList.getKey());
//			List<List<String>> temp = dominatingSolutionSet(entryLongList.getValue());
//			mappingRealtionBetweenEquiPFSets.put(entryLongList.getKey(), temp);
//		}
		
		//printCorrespondingPageRelationsAccordingtoInputData(mappingRealtionBetweenEquiPFSets);
		
		
		
		
	}
	
	
	public List<List<String>> dominatingSolutionSet(List<List<String>> wholeSet) {//comapring length equals to 3 (1-4)
		
		List<List<String>> paretoFrontSet = new ArrayList<List<String>>();
		
		//paretoFrontSet.add(wholeSet.get(0));
		if(wholeSet == null) {
			System.out.println("wholeSet == null : ");
			return paretoFrontSet;
		}
		int lenO = wholeSet.size();
		
		for(int i = 0; i < lenO; i ++) {
			
//			int labelDominateSetChange = 1;
//			int lenB = paretoFrontSet.size();
//			while(labelDominateSetChange == 1) {
//				labelDominateSetChange = 0;
//				for(int j = 0; j < lenB; j ++) {
//					if(dominateSolution(wholeSet.get(i), paretoFrontSet.get(j))) {
//						paretoFrontSet.remove(j);
//						paretoFrontSet.add(wholeSet.get(i));
//						labelDominateSetChange ++;
//						break;
//					}
//				}
//			}
			
			if(comparingSolution(wholeSet.get(i), null))
				paretoFrontSet.add(wholeSet.get(i));
			
			
		}
		
		return paretoFrontSet;
	}
	
	public List<List<String>> dominatingSolutionSet_containsProblems(List<List<String>> wholeSet) {//comapring length equals to 3 (1-4)
		
		List<List<String>> paretoFrontSet = new ArrayList<List<String>>();
		
		paretoFrontSet.add(wholeSet.get(0));
		
		int lenO = wholeSet.size();
		
		for(int i = 0; i < lenO; i ++) {
			
			int labelDominateSetChange = 1;
			int lenB = paretoFrontSet.size();
			while(labelDominateSetChange == 1) {
				labelDominateSetChange = 0;
				for(int j = 0; j < lenB; j ++) {
					if(dominateSolution(wholeSet.get(i), paretoFrontSet.get(j))) {
						paretoFrontSet.remove(j);
						paretoFrontSet.add(wholeSet.get(i));
						labelDominateSetChange ++;
						break;
					}
				}
			}
			
		}
		
		return paretoFrontSet;
	}
	
	public boolean dominateSolution(List<String> s1, List<String> s2){
		
		double wTemp1 = Double.parseDouble(s1.get(2));
		double hTemp1 = Double.parseDouble(s1.get(3));
		double olTemp1 = Double.parseDouble(s1.get(1));
		
		double wTemp2 = Double.parseDouble(s2.get(2));
		double hTemp2 = Double.parseDouble(s2.get(3));
		double olTemp2 = Double.parseDouble(s2.get(1));
		
		if((olTemp1 >= olTemp2) && (wTemp1 <= wTemp2) && (hTemp1 <= hTemp2)) {
			return true;//s1 dominate s2
		}
		else {
			return false;
		}
		
	}
	
	public boolean comparingSolution(List<String> s1, List<String> s2){
		
		double wTemp1 = Double.parseDouble(s1.get(2));
		double hTemp1 = Double.parseDouble(s1.get(3));
		double olTemp1 = Double.parseDouble(s1.get(1));
		
		//double wTemp2 = Double.parseDouble(s2.get(2));
		//double hTemp2 = Double.parseDouble(s2.get(3));
		//double olTemp2 = Double.parseDouble(s2.get(1));
		
		if((wTemp1 <= 50) && (hTemp1 <= 50)) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	
	
	
	
	public List<Double> compareTwoPagebyTriggerElementLocation(String iOSPageFileName, String androidPageFileName) {
		
		//iOSXCUITestPage iOSPage = this.iOSPageSet.get(iOSPageFileName);
		//AndroidGUIPage androidPage = this.androidPageSet.get(androidPageFileName);
		
		double iOSWidth = (double)this.iOSPageSet.get("rootPage.xml").pageWidth;
		double iOSHeight = (double)this.iOSPageSet.get("rootPage.xml").pageHeight;
		double androidWidth = (double)this.androidPageSet.get("rootPage.xml").pageWidth;
		double androidHeight = (double)this.androidPageSet.get("rootPage.xml").pageHeight;
		
		iOSXCUITestElement triggerElementIOS = this.traceIOSTreeNodebyFileName(iOSPageFileName);
		AndroidGUIElement triggerElementAndroid = this.traceAndroidTreeNodebyFileName(androidPageFileName);
		
		double iOSElementX1 = (double)triggerElementIOS.Ex;
		double iOSElementY1 = (double)triggerElementIOS.Ey;
		
		iOSElementX1 = iOSElementX1/iOSWidth*androidWidth;
		iOSElementY1 = iOSElementY1/iOSHeight*androidHeight;
		
		double androidElementX1 = triggerElementAndroid.Ex1;
		double androidElementY1 = triggerElementAndroid.Ey1;
		
		double distanceW = 0;
		double distanceH = 0;
		
		if(iOSElementX1 > androidElementX1) { distanceW = iOSElementX1 - androidElementX1;}
		else {distanceW = androidElementX1 - iOSElementX1;}
		if(iOSElementY1 > androidElementY1) { distanceH = iOSElementY1 - androidElementY1;}
		else {distanceH = androidElementY1 - iOSElementY1;}
		
		List<Double> result = new ArrayList<Double>();
		result.add(distanceW);
		result.add(distanceH);
		
		
		/*
		List<AndroidGUIElement> androidPageFeatureList = AndroidGUIPage.deriveLeafSetofPruneTreeBasedOnHeuristicComparisonOpt(androidPage);
		androidPageFeatureList = MapCorrespondingGUIPage.removeSameLocationOnefromLeafSetAndroid(androidPageFeatureList);
		List<iOSXCUITestElement> iOSPageFeatureList = iOSXCUITestPage.deriveLeafSetofPruneTreeBasedOnHeuristicComparisonOpt(iOSPage);
		iOSPageFeatureList = MapCorrespondingGUIPage.removeSameLocationOnefromLeafSetIOS(iOSPageFeatureList);
		
		double similarityofTwoPages = this.calculateSimilarityofTwoPages(androidPageFeatureList, iOSPageFeatureList);
		
		result.add(similarityofTwoPages);
		*/
		
		return result;
	}
	
	
	
	/**
	 * The multi-mapping elements are conflicts, will be delt with in future.
	 * Also, we manually set the threshold as 0.5.
	 * */
	public double calculateSimilarityofTwoPages(List<AndroidGUIElement> androidPage, List<iOSXCUITestElement> iOSPage) {
		//manually set threshold
		double threshold = 0.2;
		
		List<Double> overlapList = new ArrayList<Double>();
		
		int lenAndroidPageSet = androidPage.size();
		int lenIOSPageSet = iOSPage.size();
		
		if(lenAndroidPageSet <= 0) {
			System.out.println("Error in calculateSimilarityofTwoPages: lenAndroidPageSet = " + lenAndroidPageSet);
			return 0;
		}
		
		for(int i = 0; i < lenAndroidPageSet; i ++) {
			
			AndroidGUIElement focusedAndroidElement = androidPage.get(i);
			double maxOverlapRate = 0;
			double maxRealOverlap = 0;
			for(int j = 0; j < lenIOSPageSet; j ++) {
				List<Double> overlapTemp = calculateOverlapAreaofTwoGUIElements(focusedAndroidElement, iOSPage.get(j));
				double countTemp = overlapTemp.get(0)/overlapTemp.get(1);
				if(maxOverlapRate < countTemp) {
					maxOverlapRate = countTemp;
					maxRealOverlap = overlapTemp.get(0);
				}
			}
			overlapList.add(maxRealOverlap);
		}
		
		double count = 0;
		for(int i = 0; i < overlapList.size(); i ++) {
//			if(overlapList.get(i) >= threshold) {
//				count = count + 1;
//			}
			count = count + overlapList.get(i);
		}
		//count = count/overlapList.size();
		
		return count;
	}
	
	
	public List<Double> calculateSimilarityofTwoPages_20200406(List<AndroidGUIElement> androidPage, List<iOSXCUITestElement> iOSPage) {
		//manually set threshold
		double threshold = 0.2;
		
		double iOSWidth = (double)this.iOSPageSet.get("rootPage.xml").pageWidth;
		double iOSHeight = (double)this.iOSPageSet.get("rootPage.xml").pageHeight;
		double androidWidth = (double)this.androidPageSet.get("rootPage.xml").pageWidth;
		double androidHeight = (double)this.androidPageSet.get("rootPage.xml").pageHeight;
		
		//List<Double> overlapList = new ArrayList<Double>();
		double wholeOverlapArea = 0;
		double wholeCompareAreaIOS = 0;
		double wholeCompareAreaAndroid = 0;
		
		int lenAndroidPageSet = androidPage.size();
		int lenIOSPageSet = iOSPage.size();
//		
//		if(lenAndroidPageSet <= 0) {
//			System.out.println("Error in calculateSimilarityofTwoPages: lenAndroidPageSet = " + lenAndroidPageSet);
//			return 0;
//		}
		
		for(int i = 0; i < lenAndroidPageSet; i ++) {
			
			AndroidGUIElement focusedAndroidElement = androidPage.get(i);
			//double maxOverlapRate = 0;
			//double maxRealOverlap = 0;
			for(int j = 0; j < lenIOSPageSet; j ++) {
				List<Double> overlapTemp = calculateOverlapAreaofTwoGUIElements(focusedAndroidElement, iOSPage.get(j));
//				double countTemp = overlapTemp.get(0)/overlapTemp.get(1);
//				if(maxOverlapRate < countTemp) {
//					maxOverlapRate = countTemp;
//					maxRealOverlap = overlapTemp.get(0);
//				}
				wholeOverlapArea = wholeOverlapArea + overlapTemp.get(0);
				
				wholeCompareAreaIOS = wholeCompareAreaIOS + overlapTemp.get(1);
				wholeCompareAreaAndroid = wholeCompareAreaAndroid + overlapTemp.get(2);
			}
			//overlapList.add(maxRealOverlap);
		}
		
		wholeCompareAreaIOS = wholeCompareAreaIOS/lenAndroidPageSet;
		wholeCompareAreaAndroid = wholeCompareAreaAndroid/lenIOSPageSet;
		
		
		/*
		for(int i = 0; i < lenAndroidPageSet; i ++) {
			AndroidGUIElement focusedAndroidElement = androidPage.get(i);
			double x1 = focusedAndroidElement.Ex1;
			double x2 = focusedAndroidElement.Ex2;
			double y1 = focusedAndroidElement.Ey1;
			double y2 = focusedAndroidElement.Ey2;
			
			wholeCompareArea = wholeCompareArea + (x2-x1)*(y2-y1);
			
		}
		
		for(int j = 0; j < lenIOSPageSet; j ++) {
			iOSXCUITestElement focusedIOSElement = iOSPage.get(j);
			double w = focusedIOSElement.Ewidth;
			double h = focusedIOSElement.Eheight;
			
			wholeCompareArea = wholeCompareArea + (w/iOSWidth*androidWidth)*(h/iOSHeight*androidHeight);
		}
		*/
		
		
		//wholeCompareArea = wholeCompareArea - wholeOverlapArea;
		List<Double> result = new ArrayList<Double>();
		result.add(wholeOverlapArea); //result.add(wholeCompareArea);
		result.add(wholeCompareAreaIOS); result.add(wholeCompareAreaAndroid);
		
		
		return result;
	}
	
	
	
	
	/**
	 * When using this attribute, we can set a threshold.
	 * */
	public List<Double> calculateOverlapAreaofTwoGUIElements(AndroidGUIElement androidElement, iOSXCUITestElement iOSElement) {
		
		double iOSWidth = (double)this.iOSPageSet.get("rootPage.xml").pageWidth;
		double iOSHeight = (double)this.iOSPageSet.get("rootPage.xml").pageHeight;
		double androidWidth = (double)this.androidPageSet.get("rootPage.xml").pageWidth;
		double androidHeight = (double)this.androidPageSet.get("rootPage.xml").pageHeight;
		
		double iOSElementX1 = (double)iOSElement.Ex;
		double iOSElementX2 = (double)(iOSElement.Ex + iOSElement.Ewidth);
		double iOSElementY1 = (double)iOSElement.Ey;
		double iOSElementY2 = (double)(iOSElement.Ey + iOSElement.Eheight);
		
		iOSElementX1 = iOSElementX1/iOSWidth*androidWidth;
		iOSElementX2 = iOSElementX2/iOSWidth*androidWidth;
		iOSElementY1 = iOSElementY1/iOSHeight*androidHeight;
		iOSElementY2 = iOSElementY2/iOSHeight*androidHeight;
		
		double androidElementX1 = androidElement.Ex1;
		double androidElementX2 = androidElement.Ex2;
		double androidElementY1 = androidElement.Ey1;
		double androidElementY2 = androidElement.Ey2;
		
		double iOSArea = (iOSElementX2 - iOSElementX1)*(iOSElementY2 - iOSElementY1);
		double androidArea = (androidElementX2 - androidElementX1)*(androidElementY2 - androidElementY1);
		
		double overlapWidth = 0;
		double overlapHeight = 0;
		
		
		if(iOSElementX2 <= androidElementX1 || androidElementX2 <= iOSElementX1 || iOSElementY2 <= androidElementY1 || androidElementY2 <= iOSElementY1) {
			//overlapArea is 0
		}
		else {
			if(androidElementX2 >= iOSElementX2) {
				if(iOSElementX1 >= androidElementX1) {
					overlapWidth = iOSElementX2 - iOSElementX1;
				}
				else {//iOSElementX1 < androidElementX1
					overlapWidth = iOSElementX2 - androidElementX1;
				}
			}
			else {//iOSElementX2 > androidElementX2
				if(iOSElementX1 >= androidElementX1) {
					overlapWidth = androidElementX2 - iOSElementX1;
				}
				else {//iOSElementX1 < androidElementX1
					overlapWidth = androidElementX2 - androidElementX1;
				}
			}
			
			if(androidElementY2 >= iOSElementY2) {
				if(iOSElementY1 >= androidElementY1) {
					overlapHeight = iOSElementY2 - iOSElementY1;
				}
				else {//iOSElementY1 < androidElementY1
					overlapHeight = iOSElementY2 - androidElementY1;
				}
			}
			else {//iOSElementY2 > androidElementY2
				if(iOSElementY1 >= androidElementY1) {
					overlapHeight = androidElementY2 - iOSElementY1;
				}
				else {//iOSElementY1 < androidElementY1
					overlapHeight = androidElementY2 - androidElementY1;
				}
			}
		}
		
		double overlapArea = overlapWidth*overlapHeight;
		double wholeArea = iOSArea + androidArea;
		//double result = overlapArea/(iOSArea + androidArea - overlapArea);
		
		List<Double> Result = new ArrayList<Double>();
		Result.add(overlapArea);
		//Result.add(wholeArea);
		Result.add(iOSArea);
		Result.add(androidArea);
		
		return Result;
	}
	
	public double calculateOverlapAreaofIOSGUIElements(iOSXCUITestElement iOSElement1, iOSXCUITestElement iOSElement2) {
		
		
		double iOSElement1_X1 = (double)iOSElement1.Ex;
		double iOSElement1_X2 = (double)(iOSElement1.Ex + iOSElement1.Ewidth);
		double iOSElement1_Y1 = (double)iOSElement1.Ey;
		double iOSElement1_Y2 = (double)(iOSElement1.Ey + iOSElement1.Eheight);
		
		double iOSElement2_X1 = (double)iOSElement2.Ex;
		double iOSElement2_X2 = (double)(iOSElement2.Ex + iOSElement2.Ewidth);
		double iOSElement2_Y1 = (double)iOSElement2.Ey;
		double iOSElement2_Y2 = (double)(iOSElement2.Ey + iOSElement2.Eheight);
		
		
		double overlapWidth = 0;
		double overlapHeight = 0;
		
		
		if(iOSElement1_X2 <= iOSElement2_X1 || iOSElement2_X2 <= iOSElement1_X1 || iOSElement1_Y2 <= iOSElement2_Y1 || iOSElement2_Y2 <= iOSElement1_Y1) {
			//overlapArea is 0
		}
		else {
			if(iOSElement2_X2 >= iOSElement1_X2) {
				if(iOSElement1_X1 >= iOSElement2_X1) {
					overlapWidth = iOSElement1_X2 - iOSElement1_X1;
				}
				else {//iOSElementX1 < androidElementX1
					overlapWidth = iOSElement1_X2 - iOSElement2_X1;
				}
			}
			else {//iOSElementX2 > androidElementX2
				if(iOSElement1_X1 >= iOSElement2_X1) {
					overlapWidth = iOSElement2_X2 - iOSElement1_X1;
				}
				else {//iOSElementX1 < androidElementX1
					overlapWidth = iOSElement2_X2 - iOSElement2_X1;
				}
			}
			
			if(iOSElement2_Y2 >= iOSElement1_Y2) {
				if(iOSElement1_Y1 >= iOSElement2_Y1) {
					overlapHeight = iOSElement1_Y2 - iOSElement1_Y1;
				}
				else {//iOSElementY1 < androidElementY1
					overlapHeight = iOSElement1_Y2 - iOSElement2_Y1;
				}
			}
			else {//iOSElementY2 > androidElementY2
				if(iOSElement1_Y1 >= iOSElement2_Y1) {
					overlapHeight = iOSElement2_Y2 - iOSElement1_Y1;
				}
				else {//iOSElementY1 < androidElementY1
					overlapHeight = iOSElement2_Y2 - iOSElement2_Y1;
				}
			}
		}
		
		
		
		double overlapArea = overlapWidth*overlapHeight;
		
		
		return overlapArea;
	}
	
	public double calculateOverlapAreaofAndroidGUIElements(AndroidGUIElement iOSElement1, AndroidGUIElement iOSElement2) {
		
		
		double iOSElement1_X1 = (double)iOSElement1.Ex1;
		double iOSElement1_X2 = (double)iOSElement1.Ex2;
		double iOSElement1_Y1 = (double)iOSElement1.Ey1;
		double iOSElement1_Y2 = (double)iOSElement1.Ey2;
		
		double iOSElement2_X1 = (double)iOSElement2.Ex1;
		double iOSElement2_X2 = (double)iOSElement2.Ex2;
		double iOSElement2_Y1 = (double)iOSElement2.Ey1;
		double iOSElement2_Y2 = (double)iOSElement2.Ey2;
		
		
		double overlapWidth = 0;
		double overlapHeight = 0;
		
		
		if(iOSElement1_X2 <= iOSElement2_X1 || iOSElement2_X2 <= iOSElement1_X1 || iOSElement1_Y2 <= iOSElement2_Y1 || iOSElement2_Y2 <= iOSElement1_Y1) {
			//overlapArea is 0
		}
		else {
			if(iOSElement2_X2 >= iOSElement1_X2) {
				if(iOSElement1_X1 >= iOSElement2_X1) {
					overlapWidth = iOSElement1_X2 - iOSElement1_X1;
				}
				else {//iOSElementX1 < androidElementX1
					overlapWidth = iOSElement1_X2 - iOSElement2_X1;
				}
			}
			else {//iOSElementX2 > androidElementX2
				if(iOSElement1_X1 >= iOSElement2_X1) {
					overlapWidth = iOSElement2_X2 - iOSElement1_X1;
				}
				else {//iOSElementX1 < androidElementX1
					overlapWidth = iOSElement2_X2 - iOSElement2_X1;
				}
			}
			
			if(iOSElement2_Y2 >= iOSElement1_Y2) {
				if(iOSElement1_Y1 >= iOSElement2_Y1) {
					overlapHeight = iOSElement1_Y2 - iOSElement1_Y1;
				}
				else {//iOSElementY1 < androidElementY1
					overlapHeight = iOSElement1_Y2 - iOSElement2_Y1;
				}
			}
			else {//iOSElementY2 > androidElementY2
				if(iOSElement1_Y1 >= iOSElement2_Y1) {
					overlapHeight = iOSElement2_Y2 - iOSElement1_Y1;
				}
				else {//iOSElementY1 < androidElementY1
					overlapHeight = iOSElement2_Y2 - iOSElement2_Y1;
				}
			}
		}
		
		
		
		double overlapArea = overlapWidth*overlapHeight;
		
		
		return overlapArea;
	}
	
	
	
	
	
	public void printCorrespondingPageRelations_20200328() {//need revise!
		
		String outputResult = "Corresponding Page Relations::\n";
		
		for(Map.Entry<String, List<List<String>>> entryCPRelationSet: this.correspondingPageRelations_20200328.entrySet()) {
			
			String tempString = entryCPRelationSet.getKey();
			List<List<String>> tempStringList = entryCPRelationSet.getValue();
			
			outputResult = outputResult + "<" + tempString + "> --- ";
			
			int lenList = tempStringList.size();
//			if(lenList>5) {
//				lenList = 5;
//			}
			int i = 0;
			for(; i < lenList; ) {
				outputResult = outputResult + "[" + tempStringList.get(i).get(0) + "," + tempStringList.get(i).get(1) + "(" + tempStringList.get(i).get(2) + "," + tempStringList.get(i).get(3) + ")] ";
				i = i + 1;
			}
			outputResult = outputResult + "\n";
		}
		
		System.out.println(outputResult);
		
		
	}
	
	public void printCorrespondingPageRelationsAccordingtoInputData(HashMap<String, List<List<String>>> correspondingPageRelationsTemp) {//need revise!
		
		String outputResult = "Temp Corresponding Page Relations::\n";
		
		for(Map.Entry<String, List<List<String>>> entryCPRelationSet: correspondingPageRelationsTemp.entrySet()) {
			
			String tempString = entryCPRelationSet.getKey();
			List<List<String>> tempStringList = entryCPRelationSet.getValue();
			
			outputResult = outputResult + "<" + tempString + ">" + tempStringList.size() + " --- ";
			
			int lenList = tempStringList.size();
//			if(lenList>5) {
//				lenList = 5;
//			}
			int i = 0;
			for(; i < lenList; ) {
				outputResult = outputResult + "[" + tempStringList.get(i).get(0) + "," + tempStringList.get(i).get(1) + "(" + tempStringList.get(i).get(2) + "," + tempStringList.get(i).get(3) + ")] ";
				i = i + 1;
			}
			outputResult = outputResult + "\n";
		}
		
		System.out.println(outputResult);
		
		
	}
	
	
	public static List<iOSXCUITestElement> removeiOSElementOutOfBound(List<iOSXCUITestElement> inputList, int width, int height){
		
		List<iOSXCUITestElement> result = new ArrayList<iOSXCUITestElement>();
		
		for(int i = 0; i < inputList.size(); i ++) {
			iOSXCUITestElement focusedElement = inputList.get(i);
			if((focusedElement.Ex <= width)&&(focusedElement.Ey <= height)) {
				result.add(focusedElement);
			}
		}
		
		return result;
	}
	
	
	public static List<AndroidGUIElement> removeAndroidElementOutOfBound(List<AndroidGUIElement> inputList, int width, int height){
		
		List<AndroidGUIElement> result = new ArrayList<AndroidGUIElement>();
		
		for(int i = 0; i < inputList.size(); i ++) {
			AndroidGUIElement focusedElement = inputList.get(i);
			if((focusedElement.Ex1 <= width)&&(focusedElement.Ey1 <= height)) {
				result.add(focusedElement);
			}
		}
		
		return result;
	}
	
	
	public static List<AndroidGUIElement> removeSameLocationOnefromLeafSetAndroid(List<AndroidGUIElement> inputList){
		List<AndroidGUIElement> result = new ArrayList<AndroidGUIElement>();
		
		for(int i = 0; i < inputList.size(); i ++) {
			AndroidGUIElement focusedElement = inputList.get(i);
			int label_ResultL = result.size();
			int label = 0;
			for(int j = 0; j < label_ResultL; j ++) {
				if(AndroidGUIElement.isSameLoaction(focusedElement, result.get(j))) {
					label ++;
				}
			}
			if(label == 0) {
				result.add(focusedElement);
			}
		}
		//System.out.println("removeSameLocationOnefromLeafSet: size-" + result.size());
		
		return result;
	}
	
	public static List<iOSXCUITestElement> removeSameLocationOnefromLeafSetIOS(List<iOSXCUITestElement> inputList){
		List<iOSXCUITestElement> result = new ArrayList<iOSXCUITestElement>();
		
		for(int i = 0; i < inputList.size(); i ++) {
			iOSXCUITestElement focusedElement = inputList.get(i);
			int label_ResultL = result.size();
			int label = 0;
			for(int j = 0; j < label_ResultL; j ++) {
				if(iOSXCUITestElement.isSameLoaction(focusedElement, result.get(j))) {
					label ++;
				}
			}
			if(label == 0) {
				result.add(focusedElement);
			}
		}
		//System.out.println("removeSameLocationOnefromLeafSet: size-" + result.size());
		
		return result;
	}
	
	public static List<AndroidGUIElement> removeElementsOutOfLocationBoundfromLeafSetAndroid(List<AndroidGUIElement> inputList){
		List<AndroidGUIElement> result = new ArrayList<AndroidGUIElement>();
		
		for(int i = 0; i < inputList.size(); i ++) {
			AndroidGUIElement focusedElement = inputList.get(i);
			int label_ResultL = result.size();
			int label = 0;
			for(int j = 0; j < label_ResultL; j ++) {
				if(AndroidGUIElement.isSameLoaction(focusedElement, result.get(j))) {
					label ++;
				}
			}
			if(label == 0) {
				result.add(focusedElement);
			}
		}
		//System.out.println("removeSameLocationOnefromLeafSet: size-" + result.size());
		
		return result;
	}
	
	
	
	public iOSXCUITestElement traceIOSTreeNodebyFileName(String keyString) {
		
		if(keyString.equals("rootPage.xml")) {
			System.out.println("Warning!: When tracing iOS tree node, the key String is root Page.");
			return null;
		}
		//System.out.println(keyString);
		String combinationofNameandOrder = keyString.substring(0, keyString.lastIndexOf("."));
		//System.out.println(combinationofNameandOrder);
		String[] src = combinationofNameandOrder.split("\\[");
		String elementName = src[0];
		int orderNum = Integer.parseInt(src[1].split("\\]")[0]);
		//System.out.println("test: traceIOSTreeNodebyFileName[" + elementName + ", " + orderNum + "]");
		
		iOSXCUITestElement elementTemp = iOSXCUITestPage.findPageTreeNodebyFileName(this.iOSPageSet.get("rootPage.xml"), elementName, orderNum);
		if(elementTemp == null) {
			System.out.println("Warning: no match page tree node!");
		}
		else {
			//iOSXCUITestElement.printElement(elementTemp);
		}
		
		return elementTemp;
	}
	
	public AndroidGUIElement traceAndroidTreeNodebyFileName(String keyString) {
		
		if(keyString.equals("rootPage.xml")) {
			System.out.println("Warning!: When tracing iOS tree node, the key String is root Page.");
			return null;
		}
		//System.out.println(keyString);
		String combinationofNameandOrder = keyString.substring(0, keyString.lastIndexOf("."));
		//System.out.println(combinationofNameandOrder);
		String[] src = combinationofNameandOrder.split("\\[");
		String elementName = src[0];
		int orderNum = Integer.parseInt(src[1].split("\\]")[0]);
		//System.out.println("test: traceAndroidTreeNodebyFileName[" + elementName + ", " + orderNum + "]");
		
		AndroidGUIElement elementTemp = AndroidGUIPage.findPageTreeNodebyFileName(this.androidPageSet.get("rootPage.xml"), elementName, orderNum);
		if(elementTemp == null) {
			System.out.println("Warning: no match page tree node!");
		}
		else {
			//AndroidGUIElement.printElement(elementTemp);
		}
		
		return elementTemp;
	}
	
	
	
	public static void main(String[] args) {
		
		
		
		//MapCorrespondingGUIPage mappingPages = new MapCorrespondingGUIPage("/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/AppName_demo_SohuIOS_1/");
		//MapCorrespondingGUIPage_20200403 mappingPages = new MapCorrespondingGUIPage_20200403("/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/AppName_demo_0/");
		MapCorrespondingGUIPage_20200403 mappingPages = new MapCorrespondingGUIPage_20200403("/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/Demo_April/AppName_iFeng_2/");
		mappingPages.setiOSPageSetforParallelExecution();
		//ApplicationUnderTest.outputConsole2File("/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/AppName_demo_0/" + LocalDateTime.now());
		
		//mappingPages.setiOSPageSet();
		//mappingPages.minimizeiOSPageSet();
		//mappingPages.copyIOSMinmizedPageSet2itsFolder();
		
		//mappingPages.classifyEquivalenceIOSPageSet();
		
		//mappingPages.setAndroidPageSet();
		//mappingPages.minimizeAndroidPageSet();
		//mappingPages.copyAndroidMinmizedPageSet2itsFolder();
		
		//mappingPages.classifyEquivalenceAndroidPageSet();
		
		
		//System.out.println(iOSXCUITestPage.compareElementTree(mappingPages.iOSPageSet.get("XCUIElementTypeOther[0].xml"), mappingPages.iOSPageSet.get("XCUIElementTypeWindow[0].xml")));
		//System.out.println(AndroidGUIPage.compareElementTree(mappingPages.androidPageSet.get("android.widget.LinearLayout[18].xml"), mappingPages.androidPageSet.get("android.widget.ImageView[14].xml")));
		//System.out.println(AndroidGUIPage.compareElementTree(mappingPages.androidPageSet.get("android.widget.LinearLayout[18].xml"), mappingPages.androidPageSet.get("android.widget.LinearLayout[12].xml")));
		//System.out.println(AndroidGUIPage.compareElementTree(mappingPages.androidPageSet.get("android.widget.FrameLayout[13].xml"), mappingPages.androidPageSet.get("android.widget.LinearLayout[21].xml")));
		//System.out.println(AndroidGUIPage.compareElementTree(mappingPages.androidPageSet.get("android.widget.FrameLayout[31].xml"), mappingPages.androidPageSet.get("android.widget.FrameLayout[13].xml")));
		//System.out.println(AndroidGUIPage.compareElementTree(mappingPages.androidPageSet.get("android.widget.ImageView[1].xml"), mappingPages.androidPageSet.get("android.widget.RelativeLayout[0].xml")));
		//System.out.println(AndroidGUIPage.compareElementTree(mappingPages.androidPageSet.get("android.widget.FrameLayout[4].xml"), mappingPages.androidPageSet.get("android.widget.RelativeLayout[3].xml")));
		//System.out.println(AndroidGUIPage.compareElementTree(mappingPages.androidPageSet.get("android.widget.TextView[12].xml"), mappingPages.androidPageSet.get("android.widget.TextView[11].xml")));
		//System.out.println(AndroidGUIPage.compareElementTree(mappingPages.androidPageSet.get("android.widget.TextView[2].xml"), mappingPages.androidPageSet.get("android.widget.RelativeLayout[14].xml")));
		//System.out.println(AndroidGUIPage.compareElementTree(mappingPages.androidPageSet.get("android.widget.TextView[1].xml"), mappingPages.androidPageSet.get("android.widget.FrameLayout[2].xml")));
		//System.out.println(AndroidGUIPage.compareElementTree(mappingPages.androidPageSet.get("android.widget.FrameLayout[31].xml"), mappingPages.androidPageSet.get("android.widget.FrameLayout[30].xml")));
		//System.out.println(AndroidGUIPage.compareElementTree(mappingPages.androidPageSet.get("android.widget.LinearLayout[5].xml"), mappingPages.androidPageSet.get("android.widget.LinearLayout[7].xml")));
		
		//int i = 0; 
		//for(Map.Entry<String, iOSXCUITestPage> entryPSet: mappingPages.iOSPageSet.entrySet()) {
			//System.out.println("orderNum: " + i);
			//mappingPages.traceIOSTreeNodebyFileName(entryPSet.getKey());
			//i ++;
		//}
		//for(Map.Entry<String, AndroidGUIPage> entryPSet: mappingPages.androidPageSet.entrySet()) {
			//System.out.println("orderNum: " + i);
			//mappingPages.traceAndroidTreeNodebyFileName(entryPSet.getKey());
			//i ++;
		//}
		
		
		
//		System.out.println("XCUIElementTypeOther[2]");
//		iOSXCUITestPage.printPageTree(mappingPages.iOSPageSet.get("XCUIElementTypeOther[2].xml"));
//		System.out.println("XCUIElementTypeOther[13]");
//		iOSXCUITestPage.printPageTree(mappingPages.iOSPageSet.get("XCUIElementTypeOther[13].xml"));
//		System.out.println("============ Detail for Comparison. ============");
//		
//		boolean re = iOSXCUITestPage.compareElementTree(mappingPages.iOSPageSet.get("XCUIElementTypeOther[2].xml"), mappingPages.iOSPageSet.get("XCUIElementTypeOther[13].xml"));
//		System.out.println(re);
//		
		//mappingPages.MapCorrespondingPages();
		//mappingPages.MapCorrespondingPagesbyTriggerElementLocationandElementOverlap();
		//mappingPages.MapCorrespondingPagesbyTriggerElementLocationandElementOverlap_20200401();
		
		//mappingPages.findConfirmedCorrespondingControlsfromRootPages();
		
	}
}
