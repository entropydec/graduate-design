package org.ruihua.GUITrans.AppsGUITransformDLProj.GUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.ruihua.GUITrans.AppsGUITransformDLProj.AppiumExecutor.CommunicateWithAUTbyAppium;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.PageSourceCodeUnderstanding.SourceCodeModule;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class AndroidGUIPage {
	
	public AndroidGUIXMLFileHeadElement fileHead;
	public AndroidGUIHierarchyElement hierarchy;
	public AndroidGUIElement elementRoot;
	
	public int pageWidth;
	public int pageHeight;
	
	public AndroidGUIPage() {
		
		this.pageWidth = -1;
		this.pageHeight = -1;
	}
	/*
	//prototype
	public static void exploreAppLevelOnePagesFromRootPage(CommunicateWithAUTbyAppium comSUT) {
		//Start from root
		IOSDriver iOSDriver= comSUT.setupIOSAppiumConnectionWithApp();
		comSUT.takeScreenPageSource(iOSDriver, "fileName_"+"rootPage");
		comSUT.takeScreenShot(iOSDriver, "fileName_"+"rootPage");
		comSUT.resetIOSApp(iOSDriver);
		
		AndroidGUIPage appRootPage = GUIPageXMLFileReader.readiOSPageXMLFile("fileName_"+"rootPage");
		traverseVisitPageTree(appRootPage.elementRoot, comSUT, "");
	}
	*/
	public static void traverseVisitPageTree(AndroidGUIElement ele, CommunicateWithAUTbyAppium comSUT, String fileFolderName) {
		//the iterating process do not include the root node
		
		int len = ele.sibilings.size();
		//String output = "[" + ele.EHead + ", " + ele.pageTreeLevel + ", <" + ele.Ex + ", " + ele.Ey + ">" + "](";
		for(int i = 0; i < len; i ++) {
			//output = output + "[" + ele.sibilings.get(i).EHead + ", " + ele.sibilings.get(i).pageTreeLevel + ", <" + ele.sibilings.get(i).Ex + ", " + ele.sibilings.get(i).Ey + ">]";
			//visit the element node --- 
			//click the element (need to trannsform the element information to script location method), 
			//wait to transit, 
			//save the page xml file and screenshot, 
			//go back to last state.
			AndroidGUIElement.printElement(ele.sibilings.get(i));
			
			//if(ele.sibilings.get(i).EEnable.equals("false") || ele.sibilings.get(i).EVisible.equals("false")) {
			//	System.out.println("EEnable or EVisible is false!");
			//	continue;
			//}
			AndroidDriver androidDriver = null;
			try {
			System.out.println("+++ " + i + ", step_1");
			androidDriver= comSUT.setupAndroidAppiumConnectionWithApp();
			System.out.println("+++ " + i + ", step_2");
			//try {//some cases: xml file records visibile is true, however when clicking it shows false === this happend
//			comSUT.clickAndroidGUIElement(androidDriver, ele.sibilings.get(i).EClass, ele.sibilings.get(i).locationTypeOrder, "");
			//} catch(Exception ex) {
			//	ex.printStackTrace();
			//	System.out.print("Click exception!::");
			//	AndroidGUIElement.printElement(ele.sibilings.get(i));
			//	continue;
			//}
			System.out.println("+++ " + i + ", step_3");
			comSUT.takeScreenPageSource(androidDriver, fileFolderName+ele.sibilings.get(i).EClass + "[" + ele.sibilings.get(i).locationTypeOrder + "]");
			System.out.println("+++ " + i + ", step_4");
			comSUT.takeScreenShot(androidDriver, fileFolderName+ele.sibilings.get(i).EClass + "[" + ele.sibilings.get(i).locationTypeOrder + "]");
			System.out.println("+++ " + i + ", step_5");
			comSUT.resetAndroidApp(androidDriver);
			} catch(Exception eAll) {
				eAll.printStackTrace();
				System.out.print("Overall exception!::");
				AndroidGUIElement.printElement(ele.sibilings.get(i));
				comSUT.resetAndroidApp(androidDriver);
			}
			
		}
		//output = output + ")";
		//System.out.println(output);
		for(int i = 0; i < len; i ++) {
			traverseVisitPageTree(ele.sibilings.get(i), comSUT, fileFolderName);
		}
		
	}
	
	
	
	
	public static boolean compareElementTree(AndroidGUIPage page1, AndroidGUIPage page2) {
		
		//System.out.println("<" + page1.hierarchy.Ewidth  + ", " + page1.hierarchy.Eheight + ">");
		if(page1.hierarchy.Ewidth != page2.hierarchy.Ewidth || page1.hierarchy.Eheight != page2.hierarchy.Eheight) {	
			System.out.println("Warning!: when compare element tree: the compared page in different size!");
		}
		
		if(page1.elementRoot.EHead.equals(page2.elementRoot.EHead)) {
			return traverseComparePageTrees(page1.elementRoot, page2.elementRoot, page1.hierarchy.Ewidth, page1.hierarchy.Eheight);
		}
		else {
			
			return false;
		}
		
	}
	
	public static boolean traverseComparePageTrees(AndroidGUIElement ele1, AndroidGUIElement ele2, int width, int height) {
		int len1 = ele1.sibilings.size();
		int len2 = ele2.sibilings.size();
		
		Collections.sort(ele1.sibilings);
		Collections.sort(ele2.sibilings);
		
		if(len1 == len2) {
			for(int i = 0; i < len1; i ++) {
				
				
				if(ele1.sibilings.get(i).Ex1 > width || ele1.sibilings.get(i).Ey1 > height || ele2.sibilings.get(i).Ex1 > width || ele2.sibilings.get(i).Ey1 > height) {
					//out of the page scrope, so do nothing (means same)
					//System.out.println("Out of page scrope!");
				}
				else if(ele1.sibilings.get(i).EHead.equals(ele2.sibilings.get(i).EHead)) {
					//do nothing
					//and could set more compare rules here
				}
				else {
					//System.out.println("False_1[ ele1Head: " + ele1.sibilings.get(i).EHead + "(" + ele1.sibilings.get(i).pageTreeLevel + "<" + ele1.sibilings.get(i).EBounds + ">)" + ", ele2Head:" + ele2.sibilings.get(i).EHead + "(" + ele2.sibilings.get(i).pageTreeLevel + "<" + ele2.sibilings.get(i).EBounds + ">)" + "]");
					return false;
				}
			}
//			for(int i = 0; i < len1; i ++) {
//				
//				for(int j = 0; j < len1; j ++) {
//					
//				}
//				
//			}
			
			for(int i = 0; i < len1; i ++) {
				if(ele1.sibilings.get(i).EHead.equals("android.support.v7.widget.RecyclerView") && ele2.sibilings.get(i).EHead.equals("android.support.v7.widget.RecyclerView")) {
					
				}
				else if(ele1.sibilings.get(i).EHead.equals("android.webkit.WebView") && ele2.sibilings.get(i).EHead.equals("android.webkit.WebView")) {
					
				}
				else if(traverseComparePageTrees(ele1.sibilings.get(i), ele2.sibilings.get(i), width, height)) {
					//true, then do nothing
				}
				else {
					//System.out.println("False_2_fromItsSubtree[ ele1Head: " + ele1.sibilings.get(i).EHead + "(" + ele1.sibilings.get(i).pageTreeLevel + "<" + ele1.sibilings.get(i).EBounds + ">)" + ", ele2Head:" + ele2.sibilings.get(i).EHead + "(" + ele2.sibilings.get(i).pageTreeLevel + "<" + ele2.sibilings.get(i).EBounds + ">)" + "]");
					return false;
				}
			}
			
		}
		else {
			//System.out.println("len1: " + len1 + ", len2: " + len2 + ".");
			//System.out.println("False_3_sibilingLength[ ele1Head: " + ele1.EHead + "(" + ele1.pageTreeLevel + "<" + ele1.EBounds + ">)" + ", ele2Head:" + ele2.EHead + "(" + ele2.pageTreeLevel + "<" + ele2.EBounds + ">)" + "]");
			return false;
		}
		
		return true;
	}
	
	public static List<AndroidGUIElement> deriveLeafSetofPruneTreeBasedOnHeuristicComparisonOpt(AndroidGUIPage page) {
		List<AndroidGUIElement> leafNodeSetofPrunedPageTree = new ArrayList<AndroidGUIElement>();
		
		if(page.elementRoot.sibilings.size() == 0) {
			leafNodeSetofPrunedPageTree.add(page.elementRoot);
			return leafNodeSetofPrunedPageTree;
		}
		
		traverseTreeforPrune(page.elementRoot, page.pageWidth, page.pageHeight, leafNodeSetofPrunedPageTree);
		
		return leafNodeSetofPrunedPageTree;
	}
	
	public static void traverseTreeforPrune(AndroidGUIElement ele, int width, int height, List<AndroidGUIElement> leafNodeSetofPrunedPageTree) {
		//At first, we ignore the location out of scrope which is merely seen in Android app.
		
		int len = ele.sibilings.size();
		if(len == 0) {
			leafNodeSetofPrunedPageTree.add(ele);
		}
		for(int i = 0; i < len; i ++) {
			if(ele.sibilings.get(i).EHead.equals("android.support.v7.widget.RecyclerView")) {
				//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
			}
			else if(ele.sibilings.get(i).EHead.equals("android.webkit.WebView")) {
				//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
			}
			else {
				traverseTreeforPrune(ele.sibilings.get(i), width, height, leafNodeSetofPrunedPageTree);
			}
		}
		
	}
	
	//broadcast the clickable attri
	public static List<AndroidGUIElement> deriveLeafSetofTreeWithBroadcastClickable(AndroidGUIPage page) {
		List<AndroidGUIElement> leafNodeSetofPageTree = new ArrayList<AndroidGUIElement>();
		
		if(page.elementRoot.sibilings.size() == 0) {
			leafNodeSetofPageTree.add(page.elementRoot);
			return leafNodeSetofPageTree;
		}
		
		traverseTreeWithBroadcastClickable(page.elementRoot, leafNodeSetofPageTree);
		
		return leafNodeSetofPageTree;
	}
	
	//broadcast the clickable attri
	public static void traverseTreeWithBroadcastClickable(AndroidGUIElement ele, List<AndroidGUIElement> leafNodeSetofPageTree) {
		//At first, we ignore the location out of scrope which is merely seen in Android app.
		
		int len = ele.sibilings.size();
		if(len == 0) {
			leafNodeSetofPageTree.add(ele);
		}
		for(int i = 0; i < len; i ++) {
			if(ele.EClickable.equals("true")) {
				traverseSubtree2ReviseClickableAttriAndCollectLeaves(ele.sibilings.get(i), leafNodeSetofPageTree);
			}
			else {
				traverseTreeWithBroadcastClickable(ele.sibilings.get(i), leafNodeSetofPageTree);
			}
			
		}
		
	}
	
	public static void traverseSubtree2ReviseClickableAttriAndCollectLeaves(AndroidGUIElement ele, List<AndroidGUIElement> leafNodeSetofPageTree) {
		
		int len = ele.sibilings.size();
		if(len == 0) {
			ele.EClickable = "true";
			leafNodeSetofPageTree.add(ele);
		}
		for(int i = 0; i < len; i ++) {
			traverseSubtree2ReviseClickableAttriAndCollectLeaves(ele.sibilings.get(i), leafNodeSetofPageTree);
		}
		
		
	}
	
	
	
	public static List<AndroidGUIElement> deriveLeafSetofTree(AndroidGUIPage page) {
		List<AndroidGUIElement> leafNodeSetofPageTree = new ArrayList<AndroidGUIElement>();
		
		if(page.elementRoot.sibilings.size() == 0) {
			leafNodeSetofPageTree.add(page.elementRoot);
			return leafNodeSetofPageTree;
		}
		
		traverseTree(page.elementRoot, page.pageWidth, page.pageHeight, leafNodeSetofPageTree);
		
		return leafNodeSetofPageTree;
	}
	
	public static void traverseTree(AndroidGUIElement ele, int width, int height, List<AndroidGUIElement> leafNodeSetofPageTree) {
		//At first, we ignore the location out of scrope which is merely seen in Android app.
		
		int len = ele.sibilings.size();
		if(len == 0) {
			leafNodeSetofPageTree.add(ele);
		}
		for(int i = 0; i < len; i ++) {
			/*
			if(ele.sibilings.get(i).EHead.equals("android.support.v7.widget.RecyclerView")) {
				//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
			}
			else if(ele.sibilings.get(i).EHead.equals("android.webkit.WebView")) {
				//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
			}
			else {
				traverseTree(ele.sibilings.get(i), width, height, leafNodeSetofPageTree);
			}*/
			traverseTree(ele.sibilings.get(i), width, height, leafNodeSetofPageTree);
			
		}
		
	}
	
	//trail 1st: define a code module in the same level of the same subtree
	public static List<SourceCodeModule> dividePageTreeintoModules(AndroidGUIPage page) {
		
		List<SourceCodeModule> speratedSourceCodeModules = new ArrayList<SourceCodeModule>();
		
		if(page.elementRoot.sibilings.size() == 0) {
			return speratedSourceCodeModules;
		}
		
		traverseTree4SourceCodeModule(page.elementRoot, speratedSourceCodeModules);
		
		return speratedSourceCodeModules;
	}
	public static void traverseTree4SourceCodeModule(AndroidGUIElement ele, List<SourceCodeModule> speratedSourceCodeModules) {
		
		int len = ele.sibilings.size();
		if(len == 0) {
			//do nothing
		}
		else {
			
			int moduledLabel = 0;
			
			for(int i = 0; i < len; i ++) {
				
				
				
				int lenSibilings = ele.sibilings.get(i).sibilings.size();
				
				if((lenSibilings == 0) && (moduledLabel == 0)) {
					//construct a module
					speratedSourceCodeModules.add(SourceCodeModule.constructAndroidSourceCodeModule(ele));
					moduledLabel = 1;
				}
				else {
					traverseTree4SourceCodeModule(ele.sibilings.get(i), speratedSourceCodeModules);
				}
			}
		}
		
	}
	
	//trail 2nd: define 
	public static List<SourceCodeModule> dividePageTreeintoModules_2(AndroidGUIPage page) {
		
		List<SourceCodeModule> speratedSourceCodeModules = new ArrayList<SourceCodeModule>();
		
		if(page.elementRoot.sibilings.size() == 0) {
			return speratedSourceCodeModules;
		}
		
		traverseTree4SourceCodeModule_2(page.elementRoot, speratedSourceCodeModules);
		
		return speratedSourceCodeModules;
	}
	public static void traverseTree4SourceCodeModule_2(AndroidGUIElement ele, List<SourceCodeModule> speratedSourceCodeModules) {
		
		int len = ele.sibilings.size();
		if(len == 0) {
			//do nothing
		}
		else {
			
			int moduledLabel = 0;
			
			for(int i = 0; i < len; i ++) {
				
				
				
				int lenSibilings = ele.sibilings.get(i).sibilings.size();
				
				if((lenSibilings == 0) && (moduledLabel == 0)) {
					//construct a module
					speratedSourceCodeModules.add(SourceCodeModule.constructAndroidSourceCodeModule_2(ele));
					moduledLabel = 1;
				}
				else {
					traverseTree4SourceCodeModule_2(ele.sibilings.get(i), speratedSourceCodeModules);
				}
			}
		}
		
	}
	
	
	
	
	
	
	//remove the part of last page in current page tree
//	public static AndroidGUIPage keepCurrentPageTreeFromFocusedPagesTree(AndroidGUIPage page) {
//		
//		
//		
//	}
	
	
	
	
	
	
	
	
	
	public static AndroidGUIElement findPageTreeNodebyFileName(AndroidGUIPage page, String elementName, int orderNum) {
		AndroidGUIElement root = page.elementRoot;
		return traverseSearchPageTree(root, elementName, orderNum);
		
	}
	
	public static AndroidGUIElement traverseSearchPageTree(AndroidGUIElement ele, String elementName, int orderNum) {
		
		int len = ele.sibilings.size();
		AndroidGUIElement temp = null;
		
		if((ele.locationTypeOrder == orderNum) && ele.EHead.equals(elementName)) {
			return ele;
		}
		else {
			for(int i = 0; i < len; i ++) {
				temp = traverseSearchPageTree(ele.sibilings.get(i), elementName, orderNum);
				if(temp != null) {
					return temp;
				}
			}
			return temp;
		}
		
	}
	
	public static AndroidGUIElement findPageTreeNodebyAttributeText(AndroidGUIPage page, String text) {
		AndroidGUIElement root = page.elementRoot;
		return traverseSearchPageTreebyAttributeText(root, text);
		
	}
	
	public static AndroidGUIElement traverseSearchPageTreebyAttributeText(AndroidGUIElement ele, String text) {
		
		int len = ele.sibilings.size();
		AndroidGUIElement temp = null;
		
		if(ele.EText != null && ele.EText.equals(text)) {
			return ele;
		}
		else {
			for(int i = 0; i < len; i ++) {
				temp = traverseSearchPageTreebyAttributeText(ele.sibilings.get(i), text);
				if(temp != null) {
					return temp;
				}
			}
			return temp;
		}
		
	}
	
	
	
	
	
	public static void printPageTree(AndroidGUIPage page) {
		AndroidGUIElement root = page.elementRoot;
		traversePrintPageTree(root);
		
	}
	
	public static void traversePrintPageTree(AndroidGUIElement ele) {
		
		
		int len = ele.sibilings.size();
		String output = "[" + ele.EHead + ", " + ele.EText + ", " + ele.locationTypeOrder + ", <" + ele.EBounds + ">" + "](";
		for(int i = 0; i < len; i ++) {
			output = output + "[" + ele.sibilings.get(i).EHead + ", " + ele.sibilings.get(i).EText + ", " + ele.sibilings.get(i).locationTypeOrder + ", <" + ele.sibilings.get(i).EBounds + ">]";
		}
		output = output + ")";
		System.out.println(output);
		for(int i = 0; i < len; i ++) {
			traversePrintPageTree(ele.sibilings.get(i));
		}
		
	}
	
	public static void traversePrintStructuredPageTree(AndroidGUIElement ele, int blanksCount) {
		
		
		int len = ele.sibilings.size();
		String output_ = "";
		for(int i = 0; i < blanksCount; i ++) {
			output_ = output_ + " ";
		}
		output_ = output_ + "[" + ele.EHead + ", " + ele.EText + ", " + ele.locationTypeOrder + ", <" + ele.EBounds + ">]";
		System.out.println(output_);
		
//		iOSXCUITestElement.printElement(ele);
//		System.out.println(ele.sibilings.size());
//		iOSXCUITestElement.printElement(ele.sibilings.get(0));
		
		
		for(int i = 0; i < len; i ++) {
			traversePrintStructuredPageTree(ele.sibilings.get(i), blanksCount + 2);
		}
		
		
	}
	
	
	
	
	
	
	
	
	public static List<List<List<Integer>>> traverse2RemoveDuplicatedInfoofPageTreeUsingBFS(AndroidGUIPage page) {
		AndroidGUIElement root = page.elementRoot;
		
//		HashMap<String, Integer> elementCalculation = new HashMap<String, Integer>();
//		root.locationTypeOrder = GUIPageXMLFileReader.labelTypeOrder(elementCalculation, root.EType);
		
		List<List<Integer>> heightScrope = new ArrayList<List<Integer>>();
		List<List<Integer>> widthScrope = new ArrayList<List<Integer>>();
		
		Queue<AndroidGUIElement> queue = new LinkedList<AndroidGUIElement>();
		queue.add(root);
		traverse2RemoveDuplicatedInfoofPageTreeUsingBFS(queue, widthScrope, heightScrope);
		
		List<List<List<Integer>>> re = new ArrayList<List<List<Integer>>>();
		re.add(widthScrope);
		re.add(heightScrope);
		
		return re;
	}
	
	public static void traverse2RemoveDuplicatedInfoofPageTreeUsingBFS(Queue<AndroidGUIElement> queue, List<List<Integer>> widthScrope, List<List<Integer>> heightScrope) {
		
		Queue<AndroidGUIElement> next_queue = new LinkedList<AndroidGUIElement>();
		
		List<Integer> widthScropeOneTSLevel = new ArrayList<Integer>();
		List<Integer> heightScropeOneTSLevel = new ArrayList<Integer>();
		
		while(!queue.isEmpty()) {
			AndroidGUIElement ele = queue.poll();
			
			int len = ele.sibilings.size();
			
			for(int i = 0; i < len; i ++) {
				next_queue.add(ele.sibilings.get(i));
			}
			widthScropeOneTSLevel.add(ele.Ex1);
			widthScropeOneTSLevel.add(ele.Ex2);
			heightScropeOneTSLevel.add(ele.Ey1);
			heightScropeOneTSLevel.add(ele.Ey2);
		}
		
		widthScrope.add(widthScropeOneTSLevel);
		heightScrope.add(heightScropeOneTSLevel);
		
		if(!next_queue.isEmpty()) {
			traverse2RemoveDuplicatedInfoofPageTreeUsingBFS(next_queue, widthScrope, heightScrope);
		}
		
		
	}
	
	
	public static List<AndroidGUIElement> deriveAllNodeSetofTree(AndroidGUIPage page) {
		List<AndroidGUIElement> leafNodeSetofPageTree = new ArrayList<AndroidGUIElement>();
		
		if(page.elementRoot.sibilings.size() == 0) {
			leafNodeSetofPageTree.add(page.elementRoot);
			return leafNodeSetofPageTree;
		}
		
		traverseTree4AllNodes(page.elementRoot, page.pageWidth, page.pageHeight, leafNodeSetofPageTree);
		
		return leafNodeSetofPageTree;
	}
	
	public static void traverseTree4AllNodes(AndroidGUIElement ele, int width, int height, List<AndroidGUIElement> leafNodeSetofPageTree) {
		//At first, we ignore the location out of scrope which is merely seen in Android app.
		
		int len = ele.sibilings.size();
//		if(len == 0) {
//			leafNodeSetofPageTree.add(ele);
//		}
		for(int i = 0; i < len; i ++) {
			/*
			if(ele.sibilings.get(i).EHead.equals("android.support.v7.widget.RecyclerView")) {
				//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
			}
			else if(ele.sibilings.get(i).EHead.equals("android.webkit.WebView")) {
				//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
			}
			else {
				traverseTree(ele.sibilings.get(i), width, height, leafNodeSetofPageTree);
			}*/
			leafNodeSetofPageTree.add(ele);
			traverseTree4AllNodes(ele.sibilings.get(i), width, height, leafNodeSetofPageTree);
			
		}
		
	}
	
	public static AndroidGUIPage traverse2CopyTree4LayoutRepresentationModel(AndroidGUIPage page) {
		
		AndroidGUIPage treeCopy4LayoutRepresentationModel = new AndroidGUIPage();
		treeCopy4LayoutRepresentationModel.fileHead = page.fileHead;
		treeCopy4LayoutRepresentationModel.hierarchy = page.hierarchy;
		treeCopy4LayoutRepresentationModel.pageWidth = page.pageWidth;
		treeCopy4LayoutRepresentationModel.pageHeight = page.pageHeight;
		
		AndroidGUIElement eleCopy = page.elementRoot.copyElement();
		treeCopy4LayoutRepresentationModel.elementRoot = eleCopy;
		
		if(page.elementRoot.sibilings.size() == 0) {
			return treeCopy4LayoutRepresentationModel;
		}
		
		traverse2CopyTree4LayoutRepresentationModel(page.elementRoot, page.pageWidth, page.pageHeight, treeCopy4LayoutRepresentationModel.elementRoot);
		
		return treeCopy4LayoutRepresentationModel;
	}
	
	public static void traverse2CopyTree4LayoutRepresentationModel(AndroidGUIElement ele, int width, int height, AndroidGUIElement eleCopyInPageCopy) {
		//At first, we ignore the location out of scrope which is merely seen in Android app.
		
		int len = ele.sibilings.size();
		
		for(int i = 0; i < len; i ++) {
			/*
			if(ele.sibilings.get(i).EHead.equals("android.support.v7.widget.RecyclerView")) {
				//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
			}
			else if(ele.sibilings.get(i).EHead.equals("android.webkit.WebView")) {
				//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
			}
			else {
				traverseTree(ele.sibilings.get(i), width, height, leafNodeSetofPageTree);
			}*/
			AndroidGUIElement eleCopy = ele.sibilings.get(i).copyElement();
			eleCopy.parent = eleCopyInPageCopy;
			eleCopyInPageCopy.sibilings.add(eleCopy);
			traverse2CopyTree4LayoutRepresentationModel(ele.sibilings.get(i), width, height, eleCopy);
			
		}
		
	}
	
	
	public static AndroidGUIElement traverse2GetOutlineLevel04LayoutRepresentationModel(AndroidGUIPage page) {
		
		//create another root element for the next CompressContainer opts
		AndroidGUIElement virtureRoot = new AndroidGUIElement();
		virtureRoot.sibilings.add(page.elementRoot);
		page.elementRoot.parent = virtureRoot;
		
		List<AndroidGUIElement> reList = new ArrayList<AndroidGUIElement>();
		
		int re = -1;
	
		if(page.elementRoot.sibilings.size() == 0) {
			return page.elementRoot;
		}
		else if(page.elementRoot.sibilings.size() == 1) {
			//We suppose the root element always links to one node or no node
			reList = traverse2GetOutlineLevel04LayoutRepresentationModel(page.elementRoot.sibilings.get(0), 0, page.pageWidth, page.pageHeight);
			int lenReList = reList.size();
			for(int i = 0; i < lenReList; i ++) {
				reList.get(i).parent = page.elementRoot;
			}
		}
		else {
			reList = page.elementRoot.sibilings;
		}
//		re = page.elementRoot.sibilings.size();
		if(reList.size() != 0) {
			page.elementRoot.sibilings = reList;
		}
		
		return page.elementRoot;
	}
	
	public static List<AndroidGUIElement> traverse2GetOutlineLevel04LayoutRepresentationModel(AndroidGUIElement ele, int sibilingOrder, int width, int height) {
		//At first, we ignore the location out of scrope which is merely seen in Android app.
		
		int len = ele.sibilings.size();
		if(len == 0) { return ele.sibilings;}
		else if(len == 1) {
			
//			AndroidGUIElement onlyOneSibiling = ele.sibilings.get(0);
//			onlyOneSibiling.parent = ele.parent;
//			ele.parent.sibilings.add(sibilingOrder, onlyOneSibiling);
//			ele.parent.sibilings.remove(sibilingOrder+1);
			
			return traverse2GetOutlineLevel04LayoutRepresentationModel(ele.sibilings.get(0), 0, width, height);
			
		}
		else {
			return ele.sibilings;
//			for(int i = 0; i < len; i ++) {
//				/*
//				if(ele.sibilings.get(i).EHead.equals("android.support.v7.widget.RecyclerView")) {
//					//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
//				}
//				else if(ele.sibilings.get(i).EHead.equals("android.webkit.WebView")) {
//					//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
//				}
//				else {
//					traverseTree(ele.sibilings.get(i), width, height, leafNodeSetofPageTree);
//				}*/
//				traverse2GetOutlineLevel04LayoutRepresentationModelUsingBFS(ele.sibilings.get(i), i, width, height);
//				
//			}
			
		}
		
		
	}
	
	
	public static void traverse2CompressOneChild4LayoutRepresentationModelUsingBFS(AndroidGUIPage page) {
		
		if(page.elementRoot.sibilings.size() == 0) {
			return ;
		}
		else if(page.elementRoot.sibilings.size() == 1) {
			//We suppose the root element always links to one node or no node
			traverse2CompressOneChild4LayoutRepresentationModelUsingBFS(page.elementRoot.sibilings.get(0), 0, page.pageWidth, page.pageHeight);
		}
		else {
			for(int i = 0; i < page.elementRoot.sibilings.size(); i ++) {
				traverse2CompressOneChild4LayoutRepresentationModelUsingBFS(page.elementRoot.sibilings.get(i), i, page.pageWidth, page.pageHeight);
			}
		}
		
		
		return ;
	}
	
	public static void traverse2CompressOneChild4LayoutRepresentationModelUsingBFS(AndroidGUIElement ele, int sibilingOrder, int width, int height) {
		//At first, we ignore the location out of scrope which is merely seen in Android app.
		
		int len = ele.sibilings.size();
		if(len == 0) {}
		else if(len == 1) {
			
			AndroidGUIElement onlyOneSibiling = ele.sibilings.get(0);
			onlyOneSibiling.parent = ele.parent;
			ele.parent.sibilings.add(sibilingOrder, onlyOneSibiling);
			ele.parent.sibilings.remove(sibilingOrder+1);
			
			traverse2CompressOneChild4LayoutRepresentationModelUsingBFS(onlyOneSibiling, sibilingOrder, width, height);
			
		}
		else {
			for(int i = 0; i < len; i ++) {
				/*
				if(ele.sibilings.get(i).EHead.equals("android.support.v7.widget.RecyclerView")) {
					//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
				}
				else if(ele.sibilings.get(i).EHead.equals("android.webkit.WebView")) {
					//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
				}
				else {
					traverseTree(ele.sibilings.get(i), width, height, leafNodeSetofPageTree);
				}*/
				traverse2CompressOneChild4LayoutRepresentationModelUsingBFS(ele.sibilings.get(i), i, width, height);
				
			}
			
		}
		
		
	}
	
	
	
	
	
//	public static List<List<List<Integer>>> traverse2GetOutlineLevel04LayoutRepresentationModelUsingBFS(AndroidGUIPage page) {
//		AndroidGUIElement root = page.elementRoot;
//		
////		HashMap<String, Integer> elementCalculation = new HashMap<String, Integer>();
////		root.locationTypeOrder = GUIPageXMLFileReader.labelTypeOrder(elementCalculation, root.EType);
//		
//		List<List<Integer>> heightScrope = new ArrayList<List<Integer>>();
//		List<List<Integer>> widthScrope = new ArrayList<List<Integer>>();
//		
//		Queue<AndroidGUIElement> queue = new LinkedList<AndroidGUIElement>();
//		queue.add(root);
//		traverse2GetOutlineLevel04LayoutRepresentationModelUsingBFS(queue, widthScrope, heightScrope);
//		
//		List<List<List<Integer>>> re = new ArrayList<List<List<Integer>>>();
//		re.add(widthScrope);
//		re.add(heightScrope);
//		
//		return re;
//	}
//	
//	public static void traverse2GetOutlineLevel04LayoutRepresentationModelUsingBFS(Queue<AndroidGUIElement> queue, List<List<Integer>> widthScrope, List<List<Integer>> heightScrope) {
//		
//		Queue<AndroidGUIElement> next_queue = new LinkedList<AndroidGUIElement>();
//		
//		List<Integer> widthScropeOneTSLevel = new ArrayList<Integer>();
//		List<Integer> heightScropeOneTSLevel = new ArrayList<Integer>();
//		
//		while(!queue.isEmpty()) {
//			AndroidGUIElement ele = queue.poll();
//			
//			int len = ele.sibilings.size();
//			
//			for(int i = 0; i < len; i ++) {
//				next_queue.add(ele.sibilings.get(i));
//			}
//			widthScropeOneTSLevel.add(ele.Ex1);
//			widthScropeOneTSLevel.add(ele.Ex2);
//			heightScropeOneTSLevel.add(ele.Ey1);
//			heightScropeOneTSLevel.add(ele.Ey2);
//		}
//		
//		widthScrope.add(widthScropeOneTSLevel);
//		heightScrope.add(heightScropeOneTSLevel);
//		
//		if(!next_queue.isEmpty()) {
//			traverse2GetOutlineLevel04LayoutRepresentationModelUsingBFS(next_queue, widthScrope, heightScrope);
//		}
//		
//		
//	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
//		String androidRootPage = "/Users/jiruihua/Desktop/AppGUIMapping/MappedAppPages/TestSet_Demo/AppName_QQMusic/Android/2.xml";
//		AndroidGUIPage androidPage = GUIPageXMLFileReader.readAndroidPageXMLFile(androidRootPage);
//		
//		List<SourceCodeModule> re = AndroidGUIPage.dividePageTreeintoModules_2(androidPage);
//		System.out.println(re.size());
//		for(int i = 0; i < re.size(); i ++) {
//			System.out.print(i + "(" + re.get(i).androidModule.size() + "): ");
//			SourceCodeModule.printSourceCodeModule(re.get(i));
//		}
		
		String text = "aa   bb   cc";
		String[] srcs = text.split(" ");
		
		int len = srcs.length;
		for(int i = 0; i < len; i ++) {
			if(!srcs[i].equals("")) {
				System.out.println(i + ": " + srcs[i] + ".");
			}
			
		}
		
		
		
		
	}
	
	
	
}
