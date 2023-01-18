package AppsGUITransformDLProj.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import AppsGUITransformDLProj.GUI.*;
import AppsGUITransformDLProj.GUI.position.RelativePosition;

public class MapControlsFromMappedCorrespondingGUIPages {
	
	public MappedCorrespondingGUIPages mappedCorrespondingGUIPages;
	
	public List<List<List<List<String>>>> wholeQueueMatch;
	
	List<List<String>> matchTypeList;
	
	
	
	
	public MapControlsFromMappedCorrespondingGUIPages(String oFFolder) {
		
		this.mappedCorrespondingGUIPages = new MappedCorrespondingGUIPages(oFFolder);
		this.mappedCorrespondingGUIPages.initalMappedCorrespondingGUIPageSet();
		this.calculateReliableContolMappingRelation4MappedCorrespondingGUIPages();
		
		this.wholeQueueMatch = new ArrayList<List<List<List<String>>>>();
		
	}
	
	private void calculateReliableContolMappingRelation4MappedCorrespondingGUIPages() {
		
		int lenOfAppInfos = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.size();
		
		for(int i = 0; i < lenOfAppInfos; i ++) {
			
			List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i);
			int lenOfPageMappingRelationListTemp = pageMappingRelationListTemp.size();//actually it is 25
			
			for(int j = 0; j < lenOfPageMappingRelationListTemp; j ++) {
				
				IOSPage2AndroidPageRelation relationTemp = pageMappingRelationListTemp.get(j);
				relationTemp.matchElements = calculateReliableControls4TwoPages(relationTemp.iOSPage, relationTemp.androidPage);
				
			}
			
		}
		
		
	}
	
	
	public static List<IOSElement2AndroidElementRelation> calculateReliableControls4TwoPages(iOSXCUITestPage iosPage, AndroidGUIPage androidPage) {
		
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
		
		double iOSWidth = 0;
		double iOSHeight = 0;
		double androidWidth = 0;
		double androidHeight = 0;
		
		if(iosPage.pageWidth != 0) iOSWidth = (double)iosPage.pageWidth;
		else return resultList;
		if(iosPage.pageHeight != 0) iOSHeight = (double)iosPage.pageHeight;
		else return resultList;
		if(androidPage.pageWidth != 0) androidWidth = (double)androidPage.pageWidth;
		else return resultList;
		if(androidPage.pageHeight != 0) androidHeight = (double)androidPage.pageHeight;
		else return resultList;
		
		
		List<iOSXCUITestElement> iOSPageElementList = iOSXCUITestPage.deriveLeafSetofTree(iosPage);
		iOSPageElementList = MapCorrespondingGUIPage_20200403.removeSameLocationOnefromLeafSetIOS(iOSPageElementList);
		
		List<AndroidGUIElement> androidPageElementList = AndroidGUIPage.deriveLeafSetofTree(androidPage);
		androidPageElementList = MapCorrespondingGUIPage_20200403.removeSameLocationOnefromLeafSetAndroid(androidPageElementList);
		
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
				
				List<Double> overlapCalculation = calculateOverlapAreaofTwoGUIElements(iOSElementTemp, androidElementTemp, iOSWidth, iOSHeight, androidWidth, androidHeight);
				relationTemp.overlapIntersection = overlapCalculation.get(0);
				relationTemp.overlapUnionSet = overlapCalculation.get(1);
				
				relationList.add(relationTemp);
			}
			
		}
		
		int lenRelationList = relationList.size();
		for(int k = 0; k < lenRelationList; k ++) {
			
			if((relationList.get(k).textSimilarity != null) && (!relationList.get(k).textSimilarity.equals("")) && (relationList.get(k).overlapIntersection > 0)) {
				resultList.add(relationList.get(k));
				
			}
			
		}
		
		return resultList;
		
		
		
		
	}
	
	public static String calculateTextSimilarity(iOSXCUITestElement iOSElement, AndroidGUIElement androidElement) {
		
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
	
	public static List<Double> calculateOverlapAreaofTwoGUIElements(iOSXCUITestElement iOSElement, AndroidGUIElement androidElement, double iOSWidth, double iOSHeight, double androidWidth, double androidHeight) {
		
		//System.out.println("iOSWidth: " + iOSWidth + ", iOSHeight: " + iOSHeight + ", androidWidth: " + androidWidth + ", androidHeight: " + androidHeight);
		
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
	
	/**
	 * Map the other elements
	 * Approach key 1: tree path representation
	 * Approach key 2: queue interval
	 * 
	 * */
	
	public void selectEffectivePageMappingRelations() {
		
		int lenOfAppInfos = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.size();
		
		for(int i = 0; i < lenOfAppInfos; i ++) {
			
			List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i);
			int lenOfPageMappingRelationListTemp = pageMappingRelationListTemp.size();//actually it is 25
			
			List<IOSPage2AndroidPageRelation> newPageMappingRelationListTemp = new ArrayList<IOSPage2AndroidPageRelation>();
			
			for(int j = 0; j < lenOfPageMappingRelationListTemp; j ++) {
				
				IOSPage2AndroidPageRelation pageRelationTemp = pageMappingRelationListTemp.get(j);
				
				if( (pageRelationTemp.iOSPage != null)&&
					(pageRelationTemp.androidPage != null) &&
					(pageRelationTemp.matchElements.size() > 0)
					) {
					
					newPageMappingRelationListTemp.add(pageRelationTemp);
					
//					int lenOFElementMappingRelationsTemp = pageRelationTemp.matchElements.size();
//					for(int k = 0; k < lenOFElementMappingRelationsTemp; k ++) {
//						
//						IOSElement2AndroidElementRelation elementRelationTemp = pageRelationTemp.matchElements.get(k);
//						
//					}
					
				}
				
			}
			
			this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).clear();
			int lenn = newPageMappingRelationListTemp.size();
			for(int jj = 0; jj < lenn; jj ++) {
				this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).add(newPageMappingRelationListTemp.get(jj));
			}
			
		}
		
		this.printMappedControlsRelations();
		
	}
	
	
	public void calculateComfirmedPointsInQueue() {
		
		int lenOfAppInfos = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.size();
		
		for(int i = 0; i < lenOfAppInfos; i ++) {
			
			List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i);
			int lenOfPageMappingRelationListTemp = pageMappingRelationListTemp.size();//actually it is 25
			
			//System.out.println("# " + this.mappedCorrespondingGUIPages.appList.get(i) + " #");
			
			//List<IOSPage2AndroidPageRelation> newPageMappingRelationListTemp = new ArrayList<IOSPage2AndroidPageRelation>();
			List<List<List<String>>> appQueueTemp = new ArrayList<List<List<String>>>();
			for(int j = 0; j < lenOfPageMappingRelationListTemp; j ++) {
				
				IOSPage2AndroidPageRelation pageRelationTemp = pageMappingRelationListTemp.get(j);
				//System.out.print("** " + pageRelationTemp.iOSPageFileName + " <===> " + pageRelationTemp.androidPageFileName + " **");
				List<List<String>> pageQueueTemp = findIntervalInTwoQueues(pageRelationTemp.iOSPage, pageRelationTemp.androidPage, pageRelationTemp.matchElements);
				appQueueTemp.add(pageQueueTemp);
			}
			
			this.wholeQueueMatch.add(appQueueTemp);
			
		}
		
		
	}
	
	
	public List<List<String>> findIntervalInTwoQueues(iOSXCUITestPage iosPage, AndroidGUIPage androidPage, List<IOSElement2AndroidElementRelation> relationList) {
		
		List<iOSXCUITestElement> iOSPageElementList = iOSXCUITestPage.deriveLeafSetofTree(iosPage);
		iOSPageElementList = MapCorrespondingGUIPage_20200403.removeSameLocationOnefromLeafSetIOS(iOSPageElementList);
		
		List<AndroidGUIElement> androidPageElementList = AndroidGUIPage.deriveLeafSetofTree(androidPage);
		androidPageElementList = MapCorrespondingGUIPage_20200403.removeSameLocationOnefromLeafSetAndroid(androidPageElementList);
		
		int lenIOSPage = iOSPageElementList.size();
		int lenAndroidPage = androidPageElementList.size();
		int lenList = relationList.size();
		
		
		List<List<String>> queueMatch = new ArrayList<List<String>>();
		
		for(int i = 0; i < lenList; i ++ ) {
			
			IOSElement2AndroidElementRelation elementRelationTemp = relationList.get(i);
			List<String> temp = new ArrayList<String>();
			
			for(int i_iOSPage = 0; i_iOSPage < lenIOSPage; i_iOSPage ++) {
				
				if( (elementRelationTemp.iOSPageElement.EHead.equals(iOSPageElementList.get(i_iOSPage).EHead)) &&
					(elementRelationTemp.iOSPageElement.locationTypeOrder == iOSPageElementList.get(i_iOSPage).locationTypeOrder)
					) {
					temp.add("" + i_iOSPage);
					temp.add(":" + elementRelationTemp.iOSPageElement.EHead + "[" + elementRelationTemp.iOSPageElement.locationTypeOrder 
							+ "]<(" + elementRelationTemp.iOSPageElement.Ex + "," + elementRelationTemp.iOSPageElement.Ey 
							+ ")(w-" + elementRelationTemp.iOSPageElement.Ewidth + ",h-" + elementRelationTemp.iOSPageElement.Eheight + ")>");
					break;
				}
				
			}
			
			for(int i_androidPage = 0; i_androidPage < lenAndroidPage; i_androidPage ++) {
				
				if( (elementRelationTemp.androidPageElement.EHead.equals(androidPageElementList.get(i_androidPage).EHead)) &&
						(elementRelationTemp.androidPageElement.locationTypeOrder == androidPageElementList.get(i_androidPage).locationTypeOrder)
						) {
						temp.add("" + i_androidPage);
						temp.add(":" + elementRelationTemp.androidPageElement.EHead + "[" + elementRelationTemp.androidPageElement.locationTypeOrder
								+ "]<(" + elementRelationTemp.androidPageElement.Ex1 + "," + elementRelationTemp.androidPageElement.Ey1
								+ ")(" + elementRelationTemp.androidPageElement.Ex2 + "," + elementRelationTemp.androidPageElement.Ey2 + ")>"
								);
						break;
					}
			}
			
			queueMatch.add(temp);
		}
		
		return queueMatch;
		//this.queueMatch.add(queueMatch);
		//this.printQueueMatch();
		
	}
	
	
	public void calculateRelativePositionSet4UnmappedElements() {
		
		int lenOfAppInfos = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.size();
		
		for(int i = 0; i < lenOfAppInfos; i ++) {
			
			List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i);
			int lenOfPageMappingRelationListTemp = pageMappingRelationListTemp.size();//actually it is 25
			
			
			for(int j = 0; j < lenOfPageMappingRelationListTemp; j ++) {
				
				IOSPage2AndroidPageRelation pageRelationTemp = pageMappingRelationListTemp.get(j);
				
				calculateIOSRelativePositionSet_2(pageRelationTemp, pageRelationTemp.iOSPage, pageRelationTemp.androidPage, pageRelationTemp.matchElements);
				
			}
			
		}
		
		
	}
	
	public void calculateIOSRelativePositionSet(IOSPage2AndroidPageRelation pageMappingRelation, iOSXCUITestPage iosPage, AndroidGUIPage androidPage, List<IOSElement2AndroidElementRelation> relationList){
		
		List<iOSXCUITestElement> iOSPageElementList = iOSXCUITestPage.deriveLeafSetofTree(iosPage);
		iOSPageElementList = MapCorrespondingGUIPage_20200403.removeSameLocationOnefromLeafSetIOS(iOSPageElementList);
		
		List<AndroidGUIElement> androidPageElementList = AndroidGUIPage.deriveLeafSetofTree(androidPage);
		androidPageElementList = MapCorrespondingGUIPage_20200403.removeSameLocationOnefromLeafSetAndroid(androidPageElementList);
		
		
		int lenIOSPageEleList = iOSPageElementList.size();
		int lenAndroidPageList = androidPageElementList.size();
		int lenList = relationList.size();
		
		List<IOSElementRelativePositionSetInPage> unmappedIOSElements = new ArrayList<IOSElementRelativePositionSetInPage>();
		
		for(int i = 0; i < lenIOSPageEleList; i ++ ) {
			
			iOSXCUITestElement iosUnmappedElementTemp = iOSPageElementList.get(i);
			IOSElementRelativePositionSetInPage iosRelativePositionSet = new IOSElementRelativePositionSetInPage();
			iosRelativePositionSet.specifiedControl = iosUnmappedElementTemp;
			iosRelativePositionSet.specifiedPage = iosPage;
			
			for(int j = 0; j < lenList; j ++) {
				
				IOSElement2AndroidElementRelation elementRelationTemp = relationList.get(j);
				iOSXCUITestElement iosReferenceElementTemp = elementRelationTemp.iOSPageElement;
				
				double x = iosUnmappedElementTemp.Ex - iosReferenceElementTemp.Ex;
				double y = iosUnmappedElementTemp.Ey - iosReferenceElementTemp.Ey;
				
				RelativePosition rPTemp = new RelativePosition(x, y);
				iosRelativePositionSet.relativePositionSet.add(rPTemp);
			}
			
			unmappedIOSElements.add(iosRelativePositionSet);
			
		}
		
		List<AndroidElementRelativePositionSetInPage> unmappedAndroidElements = new ArrayList<AndroidElementRelativePositionSetInPage>();
		
		for(int i = 0; i < lenAndroidPageList; i ++ ) {
			
			AndroidGUIElement androidUnmappedElementTemp = androidPageElementList.get(i);
			AndroidElementRelativePositionSetInPage androidRelativePositionSet = new AndroidElementRelativePositionSetInPage();
			androidRelativePositionSet.specifiedControl = androidUnmappedElementTemp;
			androidRelativePositionSet.specifiedPage = androidPage;
			
			for(int j = 0; j < lenList; j ++) {
				
				IOSElement2AndroidElementRelation elementRelationTemp = relationList.get(j);
				AndroidGUIElement androidReferenceElementTemp = elementRelationTemp.androidPageElement;
				
				double x = androidUnmappedElementTemp.Ex1 - androidReferenceElementTemp.Ex1;
				double y = androidUnmappedElementTemp.Ey1 - androidReferenceElementTemp.Ey1;
				
				RelativePosition rPTemp = new RelativePosition(x, y);
				androidRelativePositionSet.relativePositionSet.add(rPTemp);
			}
			
			unmappedAndroidElements.add(androidRelativePositionSet);
			
		}
		
		pageMappingRelation.unmappedIOSElements = unmappedIOSElements;
		pageMappingRelation.unmappedAndroidElements = unmappedAndroidElements;
	}
	
	public static void calculateIOSRelativePositionSet_2(IOSPage2AndroidPageRelation pageMappingRelation, iOSXCUITestPage iosPage, AndroidGUIPage androidPage, List<IOSElement2AndroidElementRelation> relationList){
		
		List<iOSXCUITestElement> iOSPageElementList = iOSXCUITestPage.deriveLeafSetofTree(iosPage);
		iOSPageElementList = MapCorrespondingGUIPage_20200403.removeSameLocationOnefromLeafSetIOS(iOSPageElementList);
		
		List<AndroidGUIElement> androidPageElementList = AndroidGUIPage.deriveLeafSetofTree(androidPage);
		androidPageElementList = MapCorrespondingGUIPage_20200403.removeSameLocationOnefromLeafSetAndroid(androidPageElementList);
		
		
		int lenIOSPageEleList = iOSPageElementList.size();
		int lenAndroidPageList = androidPageElementList.size();
		int lenList = relationList.size();
		
		List<IOSElementRelativePositionSetInPage> unmappedIOSElements = new ArrayList<IOSElementRelativePositionSetInPage>();
		
		for(int i = 0; i < lenIOSPageEleList; i ++ ) {
			
			iOSXCUITestElement iosUnmappedElementTemp = iOSPageElementList.get(i);
			IOSElementRelativePositionSetInPage iosRelativePositionSet = new IOSElementRelativePositionSetInPage();
			iosRelativePositionSet.specifiedControl = iosUnmappedElementTemp;
			iosRelativePositionSet.specifiedPage = iosPage;
			
			for(int j = 0; j < lenList; j ++) {
				
				IOSElement2AndroidElementRelation elementRelationTemp = relationList.get(j);
				iOSXCUITestElement iosReferenceElementTemp = elementRelationTemp.iOSPageElement;
				
				double unmapedMiddleX = ((double)iosUnmappedElementTemp.Ewidth)/2;
				double referenceMiddleX = ((double)iosReferenceElementTemp.Ewidth)/2;
				double unmapedMiddleY = ((double)iosUnmappedElementTemp.Ewidth)/2;
				double referenceMiddleY = ((double)iosReferenceElementTemp.Ewidth)/2;
				
				
				double x = ((double)iosUnmappedElementTemp.Ex + unmapedMiddleX) - ((double)iosReferenceElementTemp.Ex + referenceMiddleX);
				double y = ((double)iosUnmappedElementTemp.Ey + unmapedMiddleY) - ((double)iosReferenceElementTemp.Ey + referenceMiddleY);
				
				RelativePosition rPTemp = new RelativePosition(x, y);
				iosRelativePositionSet.relativePositionSet.add(rPTemp);
			}
			
			unmappedIOSElements.add(iosRelativePositionSet);
			
		}
		
		List<AndroidElementRelativePositionSetInPage> unmappedAndroidElements = new ArrayList<AndroidElementRelativePositionSetInPage>();
		
		for(int i = 0; i < lenAndroidPageList; i ++ ) {
			
			AndroidGUIElement androidUnmappedElementTemp = androidPageElementList.get(i);
			AndroidElementRelativePositionSetInPage androidRelativePositionSet = new AndroidElementRelativePositionSetInPage();
			androidRelativePositionSet.specifiedControl = androidUnmappedElementTemp;
			androidRelativePositionSet.specifiedPage = androidPage;
			
			for(int j = 0; j < lenList; j ++) {
				
				IOSElement2AndroidElementRelation elementRelationTemp = relationList.get(j);
				AndroidGUIElement androidReferenceElementTemp = elementRelationTemp.androidPageElement;
				
				double unmapedMiddleX = ((double)androidUnmappedElementTemp.Ex2 - (double)androidUnmappedElementTemp.Ex1) ;
				double referenceMiddleX = ((double)androidReferenceElementTemp.Ex2 - (double)androidReferenceElementTemp.Ex1);
				double unmapedMiddleY = ((double)androidUnmappedElementTemp.Ey2 - (double)androidUnmappedElementTemp.Ey1);
				double referenceMiddleY = ((double)androidReferenceElementTemp.Ey2 - (double)androidReferenceElementTemp.Ey1);
				
				
				double x = ((double)androidUnmappedElementTemp.Ex1 + unmapedMiddleX) - ((double)androidReferenceElementTemp.Ex1 + referenceMiddleX);
				double y = ((double)androidUnmappedElementTemp.Ey1 + unmapedMiddleY) - ((double)androidReferenceElementTemp.Ey1 + referenceMiddleY);
				
				RelativePosition rPTemp = new RelativePosition(x, y);
				androidRelativePositionSet.relativePositionSet.add(rPTemp);
			}
			
			unmappedAndroidElements.add(androidRelativePositionSet);
			
		}
		
		pageMappingRelation.unmappedIOSElements = unmappedIOSElements;
		pageMappingRelation.unmappedAndroidElements = unmappedAndroidElements;
	}
	
	
	
	
	public void calculateUnmappedElementSetRelations() {
		
		int lenOfAppInfos = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.size();
		
		for(int i = 0; i < lenOfAppInfos; i ++) {
			
			List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i);
			int lenOfPageMappingRelationListTemp = pageMappingRelationListTemp.size();//actually it is 25
			
			
			for(int j = 0; j < lenOfPageMappingRelationListTemp; j ++) {
				
				IOSPage2AndroidPageRelation pageRelationTemp = pageMappingRelationListTemp.get(j);
				
				calculate2MapElementSetByRelativePositions(pageRelationTemp, pageRelationTemp.matchElements);
				
				
			}
			
		}
		
		
	}
	
	public static void calculate2MapElementSetByRelativePositions(IOSPage2AndroidPageRelation pageMappingRelation, List<IOSElement2AndroidElementRelation> relationList){
		
		List<IOSElementRelativePositionSetInPage> unmappedIOSElements = pageMappingRelation.unmappedIOSElements;
		List<AndroidElementRelativePositionSetInPage> unmappedAndroidElements = pageMappingRelation.unmappedAndroidElements;
		
		int lenIOSElementRelativePositionSet = unmappedIOSElements.size();
		int lenAndroidElementRelativePositionSet = unmappedAndroidElements.size();
		
		
		HashMap<String, iOS2AndroidUnmappedElementSetRelation> unmappedElementSetRelationsStatistic = new HashMap<String, iOS2AndroidUnmappedElementSetRelation>();
		
		for(int i = 0; i < lenIOSElementRelativePositionSet; i ++) {
			List<RelativePosition> rPList = unmappedIOSElements.get(i).relativePositionSet;
			
			String setLabel = iOS2AndroidUnmappedElementSetRelation.calculateElementSetLabel(rPList);
			
			if(unmappedElementSetRelationsStatistic.containsKey(setLabel)) {
				unmappedElementSetRelationsStatistic.get(setLabel).iOSElements.add(unmappedIOSElements.get(i));
				unmappedElementSetRelationsStatistic.get(setLabel).iOSElementsIndex.add(i);
			}
			else {
				iOS2AndroidUnmappedElementSetRelation tempSetRelations = new iOS2AndroidUnmappedElementSetRelation();
				tempSetRelations.elementSetLabel = setLabel;
				tempSetRelations.iOSElements.add(unmappedIOSElements.get(i));
				tempSetRelations.iOSElementsIndex.add(i);
				unmappedElementSetRelationsStatistic.put(setLabel, tempSetRelations);
			}
			
		}
		
		for(int i = 0; i < lenAndroidElementRelativePositionSet; i ++) {
			List<RelativePosition> rPList = unmappedAndroidElements.get(i).relativePositionSet;
			
			String setLabel = iOS2AndroidUnmappedElementSetRelation.calculateElementSetLabel(rPList);
			
			if(unmappedElementSetRelationsStatistic.containsKey(setLabel)) {
				unmappedElementSetRelationsStatistic.get(setLabel).androidElements.add(unmappedAndroidElements.get(i));
				unmappedElementSetRelationsStatistic.get(setLabel).androidElementsIndex.add(i);
			}
			else {
				iOS2AndroidUnmappedElementSetRelation tempSetRelations = new iOS2AndroidUnmappedElementSetRelation();
				tempSetRelations.elementSetLabel = setLabel;
				tempSetRelations.androidElements.add(unmappedAndroidElements.get(i));
				tempSetRelations.androidElementsIndex.add(i);
				unmappedElementSetRelationsStatistic.put(setLabel, tempSetRelations);
			}
			
		}
		
		
		pageMappingRelation.umappedElementSetRelations = unmappedElementSetRelationsStatistic;
		
		
	}
	
	
	public static void calculate2SetUnmappedElements_2(IOSPage2AndroidPageRelation pageMappingRelation, iOSXCUITestPage iosPage, AndroidGUIPage androidPage, List<IOSElement2AndroidElementRelation> matchElementList) {
		
		int iOSWidth = iosPage.pageWidth;
		int iOSHeight = iosPage.pageHeight;
		int androidWidth = androidPage.pageWidth;
		int androidHeight = androidPage.pageHeight;
		System.out.println("Bounds => iOS : " + iOSWidth + ", " + iOSHeight + "; " + "Anroid : " + androidWidth + ", " + androidHeight + ".");
		
		List<iOSXCUITestElement> iOSPageElementList = iOSXCUITestPage.deriveLeafSetofTree(iosPage);
		int lenIOSPageEleList = iOSPageElementList.size();
		iOSPageElementList = MapCorrespondingGUIPage_20200403.removeSameLocationOnefromLeafSetIOS(iOSPageElementList);
		
		List<AndroidGUIElement> androidPageElementList = AndroidGUIPage.deriveLeafSetofTree(androidPage);
		int lenAndroidPageList = androidPageElementList.size();
		androidPageElementList = MapCorrespondingGUIPage_20200403.removeSameLocationOnefromLeafSetAndroid(androidPageElementList);
		
		
		int lenList = matchElementList.size();
		
		List<iOSXCUITestElement> iOSPageElementList2 = MapCorrespondingGUIPage_20200403.removeiOSElementOutOfBound(iOSPageElementList, iOSWidth, iOSHeight);
		List<AndroidGUIElement> androidPageElementList2 = MapCorrespondingGUIPage_20200403.removeAndroidElementOutOfBound(androidPageElementList, androidWidth, androidHeight);
		
		int lenIOSPageEleList2 = iOSPageElementList2.size();
		int lenAndroidPageList2 = androidPageElementList2.size();
		
		System.out.println("iOS: " + iOSPageElementList.size() + "," + lenIOSPageEleList + 
				"; android: " + androidPageElementList.size() + "," + lenAndroidPageList + 
				"; iOS (no duplication): " + lenIOSPageEleList2 + 
				"; android (no duplication): " + lenAndroidPageList2
				);
		
		iOSPageElementList = iOSPageElementList2;
		androidPageElementList = androidPageElementList2;
		
		
		List<iOSXCUITestElement> unmappediOSElements_2 = new ArrayList<iOSXCUITestElement>();
		
		for(int i = 0; i < iOSPageElementList.size(); i ++ ) {
			
			iOSXCUITestElement iosUnmappedElementTemp = iOSPageElementList.get(i);
			boolean containedInMatchList = false;
			
			for(int j = 0; j < lenList; j ++) {
				IOSElement2AndroidElementRelation elementRelationTemp = matchElementList.get(j);
				iOSXCUITestElement iosReferenceElementTemp = elementRelationTemp.iOSPageElement;
				
				if(iosUnmappedElementTemp.EHead.equals(iosReferenceElementTemp.EHead) 
						&& (iosUnmappedElementTemp.locationTypeOrder == iosReferenceElementTemp.locationTypeOrder)
						) {
					containedInMatchList = true;
					break;
				}
				else {
					//unmappediOSElements_2.add(iosUnmappedElementTemp);
					//do nothing
				}
			}
			
			if(!containedInMatchList) {
				unmappediOSElements_2.add(iosUnmappedElementTemp);
			}
			
			
			//Note this following part!
//			if(lenList == 0) {
//				unmappediOSElements_2.add(iosUnmappedElementTemp);
//			}
			
		}
		
		List<AndroidGUIElement> unmappedAndroidElements_2 = new ArrayList<AndroidGUIElement>();
		
		for(int i = 0; i < androidPageElementList.size(); i ++ ) {
			
			AndroidGUIElement androidUnmappedElementTemp = androidPageElementList.get(i);
			boolean containedInMatchList = false;
			
			for(int j = 0; j < lenList; j ++) {
				
				IOSElement2AndroidElementRelation elementRelationTemp = matchElementList.get(j);
				AndroidGUIElement androidReferenceElementTemp = elementRelationTemp.androidPageElement;
				
				if(androidUnmappedElementTemp.EHead.equals(androidReferenceElementTemp.EHead) 
						&& (androidUnmappedElementTemp.locationTypeOrder == androidReferenceElementTemp.locationTypeOrder)
						) {
					containedInMatchList = true;
					break;
				}
				else {
					//do nothing
				}
			
			}
			
			if(!containedInMatchList) {
				unmappedAndroidElements_2.add(androidUnmappedElementTemp);
			}
			
//			if(lenList == 0) {
//				unmappedAndroidElements_2.add(androidUnmappedElementTemp);
//			}
			
		}
		
		pageMappingRelation.unmappedIOSElements_2 = unmappediOSElements_2;
		pageMappingRelation.unmappedAndroidElements_2 = unmappedAndroidElements_2;
		
		System.out.println("*** iOS: " + pageMappingRelation.unmappedIOSElements_2.size() + ", Android: " + pageMappingRelation.unmappedAndroidElements_2.size());
		
	}
	
	
	
	
	
	
	
	
	/**
	 * print methods
	 * */
	public void printMappedControlsRelations() {
		
		int len = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.size();
		
		System.out.println("=========== print mapped control relations ===========");
		int count1 = 0;
		//int count2 = 0;
		
		List<List<String>> typeMatch11 = new ArrayList<List<String>>();
		
		
		for(int i = 0; i < len; i ++) {
			
			List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i);
			System.out.println("# " + this.mappedCorrespondingGUIPages.appList.get(i) + " #");
			
			int len_1 = pageMappingRelationListTemp.size();
			for(int j = 0; j < len_1; j ++) {
				
				
				IOSPage2AndroidPageRelation relationTemp = pageMappingRelationListTemp.get(j);
				System.out.print("** " + relationTemp.iOSPageFileName + " <===> " + relationTemp.androidPageFileName + " **");
				
				int len_2 = relationTemp.matchElements.size();
				for(int k = 0; k < len_2; k ++) {
					
					List<String> typeMatch11Temp = new ArrayList<String>();
					typeMatch11Temp.add(relationTemp.matchElements.get(k).iOSPageElement.EHead);
					typeMatch11Temp.add(relationTemp.matchElements.get(k).androidPageElement.EHead);
					typeMatch11.add(typeMatch11Temp);
					
					
					
					System.out.print("["
						+ relationTemp.matchElements.get(k).textSimilarity + ", <"
						+ relationTemp.matchElements.get(k).overlapIntersection + ", "
						+ relationTemp.matchElements.get(k).overlapUnionSet + ">] "
					);
					
				}
				System.out.println();
				count1 ++;
			}
			
		}
		
		System.out.println("total: " + count1);
		
		List<List<String>> typeMatch11_copy = new ArrayList<List<String>>();
		int len_TM11 = typeMatch11.size();
		
		for(int ii = 0; ii < len_TM11; ii ++) {
			
			List<String> temppp = typeMatch11.get(ii);
			
			int len_TM11Copy = typeMatch11_copy.size();
			int labelllll = 0;
			for(int jj = 0; jj < len_TM11Copy; jj ++) {
				
				List<String> copyVersionTemp = typeMatch11_copy.get(jj);
				
				if(copyVersionTemp.get(0).equals(temppp.get(0)) && copyVersionTemp.get(1).equals(temppp.get(1))) {
					labelllll = 1;
					break;
				}
				
			}
			
			if(labelllll == 0) {
				typeMatch11_copy.add(temppp);
			}
			
		}
		
		int len_TM11Copy2 = typeMatch11_copy.size();
		System.out.println("total type pairs:" + len_TM11Copy2);
		for(int iii = 0; iii < len_TM11Copy2; iii ++) {
			System.out.println("" + typeMatch11_copy.get(iii).get(0) + ", " + typeMatch11_copy.get(iii).get(1));
		}
		
		this.matchTypeList = typeMatch11_copy;
		
		
	}
	
	public void printQueueMatch() {
		
		int lenQueueMatch = this.wholeQueueMatch.size();
		
		for(int i = 0; i < lenQueueMatch; i ++) {
			
			System.out.println("# " + this.mappedCorrespondingGUIPages.appList.get(i) + " #");
			
			List<List<List<String>>> tempLLL = this.wholeQueueMatch.get(i);
			int len_i = tempLLL.size();
			
			for(int j = 0; j < len_i; j ++) {
				//System.out.print("+" + j + "," + len_i + "+");
				System.out.print("** " + this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).iOSPageFileName + " <===> " + this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).androidPageFileName + " ** ");
				
				List<List<String>> tempLL = tempLLL.get(j);
				System.out.print("{");
				for(int k = 0; k < tempLL.size(); k ++) {
					System.out.print("[");
					
					List<String> tempL = tempLL.get(k);
					int lennn = tempL.size();
					
					for(int m = 0; m < lennn; m ++) {
						
						System.out.print(tempL.get(m) + ":");
						
					}
					
					
					System.out.print("]");
				}
				System.out.print("} ");
				System.out.println();
			}
			//System.out.println();
			
			
		}
		
		
	}
	
	
	public void printUnmappedElementsRelativePositionSet() {
		
		int len = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.size();
		
		for(int i = 0; i < len; i ++) {
			
			List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i);
			System.out.println("# " + this.mappedCorrespondingGUIPages.appList.get(i) + " #");
			
			int len_1 = pageMappingRelationListTemp.size();
			for(int j = 0; j < len_1; j ++) {
				
				IOSPage2AndroidPageRelation relationTemp = pageMappingRelationListTemp.get(j);
				System.out.println("** " + relationTemp.iOSPageFileName + " <===> " + relationTemp.androidPageFileName + " **");
				
				int len_2 = relationTemp.unmappedIOSElements.size();
				for(int k = 0; k < len_2; k ++) {
					relationTemp.unmappedIOSElements.get(k).printIOSELementRelativePositionSet();
				}
				
				int len_3 = relationTemp.unmappedAndroidElements.size();
				for(int k = 0; k < len_3; k ++) {
					relationTemp.unmappedAndroidElements.get(k).printAndroidELementRelativePositionSet();
				}
				
			}
			
		}
		
	}
	
	public void printUnmappedElementSetRelations() {
		
		int len = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.size();
		
		int count_total = 0;
		int count_ios0 = 0;
		int count_android0 = 0;
		int count_map = 0;
		int count_mapNN = 0;
		int count_StrLenLess5_NN = 0;
		int count_StrLenLess5 = 0;
		int count_StrLenBigger10 = 0;
		int count_fail = 0;
		int count_n_1 = 0;
		int count_n_2 = 0;
		int count_n_3 = 0;
		int count_n_4 = 0;
		int count_n_5 = 0;
		int count_n_6 = 0;
		int count_n_7 = 0;
		int count_n_8 = 0;
		int count_n_9 = 0;
		int count_n_10 = 0;
		
		int count_map22 = 0;
		int count_map21 = 0;
		int count_map12 = 0;
		int count_map13 = 0;
		int count_map31 = 0;
		int count_map2n = 0;
		int count_mapn2 = 0;
		int count_map1n = 0;
		int count_mapn1 = 0;
		
		List<List<String>> typeMatch11 = new ArrayList<List<String>>();
		
		int count_matchTML = 0;
		
		
		for(int i = 0; i < len; i ++) {
			
			List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i);
			System.out.println("# " + this.mappedCorrespondingGUIPages.appList.get(i) + " #");
			
			int len_1 = pageMappingRelationListTemp.size();
			for(int j = 0; j < len_1; j ++) {
				
				IOSPage2AndroidPageRelation relationTemp = pageMappingRelationListTemp.get(j);
				System.out.println("** " + relationTemp.iOSPageFileName + " <===> " + relationTemp.androidPageFileName + " **");
				
				for(Entry<String, iOS2AndroidUnmappedElementSetRelation> entry: relationTemp.umappedElementSetRelations.entrySet()) {
					System.out.println("*** " + entry.getKey() + " ***");
					
					count_total ++;
					
					
					
					
					int lenIOS = entry.getValue().iOSElementsIndex.size();
					int lenAndroid = entry.getValue().androidElementsIndex.size();
					if(lenIOS == 0) {
						count_ios0 ++;
						
						
					}
					if(lenAndroid == 0) {
						count_android0 ++;
						
						
					}
					if(lenIOS == 0 || lenAndroid == 0) {
						count_fail ++;
						
						if(entry.getKey().length() > 10)
							count_StrLenBigger10 ++;
						if(entry.getKey().length() == 1)
							count_n_1 ++;
						if(entry.getKey().length() == 2)
							count_n_2 ++;
						if(entry.getKey().length() == 3)
							count_n_3 ++;
						if(entry.getKey().length() == 4)
							count_n_4 ++;
						if(entry.getKey().length() == 5)
							count_n_5 ++;
						if(entry.getKey().length() == 6)
							count_n_6 ++;
						if(entry.getKey().length() == 7)
							count_n_7 ++;
						if(entry.getKey().length() == 8)
							count_n_8 ++;
						if(entry.getKey().length() == 9)
							count_n_9 ++;
						if(entry.getKey().length() == 10)
							count_n_10 ++;
					}
					
					
					if(lenIOS == 1 && lenAndroid == 1) {
						count_map ++;
						
						List<String> typeMatch11Temp = new ArrayList<String>();
						typeMatch11Temp.add(this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedIOSElements.get(entry.getValue().iOSElementsIndex.get(0)).specifiedControl.EHead);
						typeMatch11Temp.add(this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedAndroidElements.get(entry.getValue().androidElementsIndex.get(0)).specifiedControl.EHead);
						typeMatch11.add(typeMatch11Temp);
						
						int len_TML = this.matchTypeList.size();
						for(int iiii = 0; iiii < len_TML; iiii ++) {
							
							if(this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedIOSElements.get(entry.getValue().iOSElementsIndex.get(0)).specifiedControl.EHead.equals(matchTypeList.get(iiii).get(0))
									&& this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedAndroidElements.get(entry.getValue().androidElementsIndex.get(0)).specifiedControl.EHead.equals(matchTypeList.get(iiii).get(1))
									) {
								count_matchTML ++;
								break;
							}
							
						}
						
					}
					if(lenIOS >= 2 && lenAndroid >=2) {
						count_mapNN ++;
						if(entry.getKey().length() <= 5) {
							count_StrLenLess5_NN ++;
						}
						
					}
					
					if(lenIOS == 2 && lenAndroid == 2) {
						count_map22 ++;
					}
					if(lenIOS == 1 && lenAndroid == 2) {
						count_map12 ++;
					}
					if(lenIOS == 2 && lenAndroid == 1) {
						count_map21 ++;
					}
					if(lenIOS == 1 && lenAndroid == 3) {
						count_map13 ++;
					}
					if(lenIOS == 3 && lenAndroid == 1) {
						count_map31 ++;
					}
					if(lenIOS > 3 && lenAndroid == 1) {
						count_mapn1 ++;
					}
					if(lenIOS == 1 && lenAndroid > 3) {
						count_map1n ++;
					}
					if(lenIOS > 3 && lenAndroid == 2) {
						count_mapn2 ++;
					}
					if(lenIOS == 2 && lenAndroid > 3) {
						count_map2n ++;
					}
					
					
					
					System.out.print("IOS: ");
					for(int k1 = 0; k1 < lenIOS; k1 ++) {
						
						//System.out.print("matchElements size: " + this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).matchElements.size());
						//entry.getValue().iOSElementsIndex.get(k1)
						
						double x1 = (double)this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedIOSElements.get(entry.getValue().iOSElementsIndex.get(k1)).specifiedControl.Ex;
						double x2 = (double)(this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedIOSElements.get(entry.getValue().iOSElementsIndex.get(k1)).specifiedControl.Ex + 
								this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedIOSElements.get(entry.getValue().iOSElementsIndex.get(k1)).specifiedControl.Ewidth);
						double y1 = (double)this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedIOSElements.get(entry.getValue().iOSElementsIndex.get(k1)).specifiedControl.Ey;
						double y2 = (double)(this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedIOSElements.get(entry.getValue().iOSElementsIndex.get(k1)).specifiedControl.Ey + 
								this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedIOSElements.get(entry.getValue().iOSElementsIndex.get(k1)).specifiedControl.Eheight);
						
						System.out.print(entry.getValue().iOSElementsIndex.get(k1) +
						"[" + this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedIOSElements.get(entry.getValue().iOSElementsIndex.get(k1)).specifiedControl.EHead + 
						"<" + (x1/375*1080) + "," + (y1/667*1920) + ";" + (x2/375*1080) + "," + (y2/667*1920) + ">] "
						);
					}
					System.out.println();
					
					
					System.out.print("Android: ");
					for(int k2 = 0; k2 < lenAndroid; k2 ++) {
						System.out.print(entry.getValue().androidElementsIndex.get(k2) + 
						"[" + this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedAndroidElements.get(entry.getValue().androidElementsIndex.get(k2)).specifiedControl.EHead + 
						"<" + this.mappedCorrespondingGUIPages.mappedCorrespondingPages.get(i).get(j).unmappedAndroidElements.get(entry.getValue().androidElementsIndex.get(k2)).specifiedControl.EBounds + 
						">] "
						);
					}
					System.out.println();
					
				}
				
				
			}
			
		}
		
		System.out.println("count total: " + count_total);
		System.out.println("count ios == 0: " + count_ios0);
		System.out.println("count android == 0: " + count_android0);
		System.out.println("count 1-1: " + count_map);
		System.out.println("count n-n: " + count_mapNN);
		
		//System.out.println("count String length <= 5: " + count_StrLenLess5);
		System.out.println("count String length <= 5 in N-N: " + count_StrLenLess5_NN);
		
		
		
		System.out.println("count fail: " + count_fail);
		
		System.out.println("count String length == 1: " + count_n_1);
		System.out.println("count String length == 2: " + count_n_2);
		System.out.println("count String length == 3: " + count_n_3);
		System.out.println("count String length == 4: " + count_n_4);
		System.out.println("count String length == 5: " + count_n_5);
		System.out.println("count String length == 6: " + count_n_6);
		System.out.println("count String length == 7: " + count_n_7);
		System.out.println("count String length == 8: " + count_n_8);
		System.out.println("count String length == 9: " + count_n_9);
		System.out.println("count String length == 10: " + count_n_10);
		
		System.out.println("count String length > 10: " + count_StrLenBigger10);
		
		System.out.println("count 2-2: " + count_map22);
		System.out.println("count 1-2: " + count_map12);
		System.out.println("count 2-1: " + count_map21);
		System.out.println("count 1-3: " + count_map13);
		System.out.println("count 3-1: " + count_map31);
		
		System.out.println("count n-2: " + count_mapn2);
		System.out.println("count 2-n: " + count_map2n);
		System.out.println("count 1-n: " + count_map1n);
		System.out.println("count n-1: " + count_mapn1);
		
		List<List<String>> typeMatch11_copy = new ArrayList<List<String>>();
		int len_TM11 = typeMatch11.size();
		
		for(int ii = 0; ii < len_TM11; ii ++) {
			
			List<String> temppp = typeMatch11.get(ii);
			
			int len_TM11Copy = typeMatch11_copy.size();
			int labelllll = 0;
			for(int jj = 0; jj < len_TM11Copy; jj ++) {
				
				List<String> copyVersionTemp = typeMatch11_copy.get(jj);
				
				if(copyVersionTemp.get(0).equals(temppp.get(0)) && copyVersionTemp.get(1).equals(temppp.get(1))) {
					labelllll = 1;
					break;
				}
				
			}
			
			if(labelllll == 0) {
				typeMatch11_copy.add(temppp);
			}
			
		}
		
		int len_TM11Copy2 = typeMatch11_copy.size();
		System.out.println("total type pairs:" + len_TM11Copy2);
		for(int iii = 0; iii < len_TM11Copy2; iii ++) {
			System.out.println("" + typeMatch11_copy.get(iii).get(0) + ", " + typeMatch11_copy.get(iii).get(1));
		}
		
		
		System.out.println(" match the typeMatchList: " + count_matchTML);
		
		
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		
		
		String overallFileFolder = "/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/Demo_April_2/";
		
		//ApplicationUnderTest.outputConsole2File(overallFileFolder + "test_" + LocalDateTime.now());
		
		MapControlsFromMappedCorrespondingGUIPages test = new MapControlsFromMappedCorrespondingGUIPages(overallFileFolder);
		
		//test.printMappedControlsRelations();
		test.selectEffectivePageMappingRelations();
		//test.calculateComfirmedPointsInQueue();
		//test.printQueueMatch();
		test.printMappedControlsRelations();
		test.calculateRelativePositionSet4UnmappedElements();
		test.printUnmappedElementsRelativePositionSet();
		test.calculateUnmappedElementSetRelations();
		test.printUnmappedElementSetRelations();
		
	}
}
