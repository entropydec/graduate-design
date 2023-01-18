package AppsGUITransformDLProj.GUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//import AppsGUITransformDLProj.AppiumExecutor.CommunicateWithAUTbyAppium;
import AppsGUITransformDLProj.GUI.PageSourceCodeUnderstanding.SourceCodeModule;
import AppsGUITransformDLProj.GUI.PageSourceCodeUnderstanding.iOSXMLHandlingTools;

//import io.appium.java_client.ios.IOSDriver;

public class iOSXCUITestPage {
	
	public iOSXCUITestXMLFileHeadElement fileHead;
	public iOSXCUITestAppiumAUTElement appiumAUT;
	public iOSXCUITestElement elementRoot;
	
	public int pageWidth;
	public int pageHeight;
	
	public iOSXCUITestPage() {
		
		this.pageWidth = -1;
		this.pageHeight = -1;
	}
	
	//prototype
	/*public static void exploreAppLevelOnePagesFromRootPage(CommunicateWithAUTbyAppium comSUT) {
		//Start from root
		IOSDriver iOSDriver= comSUT.setupIOSAppiumConnectionWithApp();
		comSUT.takeScreenPageSource(iOSDriver, "fileName_"+"rootPage");
		comSUT.takeScreenShot(iOSDriver, "fileName_"+"rootPage");
		comSUT.resetIOSApp(iOSDriver);
		
		iOSXCUITestPage appRootPage = GUIPageXMLFileReader.readiOSPageXMLFile("fileName_"+"rootPage");
		traverseVisitPageTree(appRootPage.elementRoot, comSUT, "");
	}
	
	public static void traverseVisitPageTree(iOSXCUITestElement ele, CommunicateWithAUTbyAppium comSUT, String fileFolderName) {
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
			System.out.println("type:" + ele.sibilings.get(i).EType + ", order:" + ele.sibilings.get(i).locationTypeOrder + ", enable:" + ele.sibilings.get(i).EEnable + ", visible:" + ele.sibilings.get(i).EVisible);
			
			//if(ele.sibilings.get(i).EEnable.equals("false") || ele.sibilings.get(i).EVisible.equals("false")) {
			//	System.out.println("EEnable or EVisible is false!");
			//	continue;
			//}
			
			IOSDriver iOSDriver = null;
			
			try {
			System.out.println("+++ " + i + ", step_1");
			iOSDriver= comSUT.setupIOSAppiumConnectionWithApp();
			System.out.println("+++ " + i + ", step_2");
			try {//some cases: xml file records visibile is true, however when clicking it shows false === this happend
//			comSUT.clickIOSGUIElement(iOSDriver, ele.sibilings.get(i).EType, ele.sibilings.get(i).locationTypeOrder, "");
			} catch(Exception ex) {
				ex.printStackTrace();
				iOSXCUITestElement.printElement(ele.sibilings.get(i));
				continue;
			}
			System.out.println("+++ " + i + ", step_3");
			comSUT.takeScreenPageSource(iOSDriver, fileFolderName+ele.sibilings.get(i).EType + "[" + ele.sibilings.get(i).locationTypeOrder + "]");
			System.out.println("+++ " + i + ", step_4");
			comSUT.takeScreenShot(iOSDriver, fileFolderName+ele.sibilings.get(i).EType + "[" + ele.sibilings.get(i).locationTypeOrder + "]");
			System.out.println("+++ " + i + ", step_5");
			comSUT.resetIOSApp(iOSDriver);
			} catch(Exception eAll) {
				eAll.printStackTrace();
				System.out.print("Overall exception!::");
				iOSXCUITestElement.printElement(ele.sibilings.get(i));
				comSUT.resetIOSApp(iOSDriver);
			}
		}
		//output = output + ")";
		//System.out.println(output);
		for(int i = 0; i < len; i ++) {
			traverseVisitPageTree(ele.sibilings.get(i), comSUT, fileFolderName);
		}
		
	}*/
	
	
	public static boolean compareElementTree(iOSXCUITestPage page1, iOSXCUITestPage page2) {
		
		if(page1.elementRoot.EHead.equals(page2.elementRoot.EHead)) {
			return traverseComparePageTrees(page1.elementRoot, page2.elementRoot);
		}
		else {
			
			return false;
		}
		
	}
	
	public static boolean traverseComparePageTrees(iOSXCUITestElement ele1, iOSXCUITestElement ele2) {
		int len1 = ele1.sibilings.size();
		int len2 = ele2.sibilings.size();
		if(len1 == len2) {
			
			//Collections.sort(ele1.sibilings);
			//Collections.sort(ele2.sibilings);
			
			List<Integer> label_XCUIElementTypeScrollView = new ArrayList<Integer>();
			List<Integer> label_XCUIElementTypeOtherImage = new ArrayList<Integer>();
			
			for(int i = 0; i < len1; i ++) {
				
				//System.out.println("<" + ele1.sibilings.get(i).EHead + ", " + ele2.sibilings.get(i).EHead + ">");
				
//				if(ele1.sibilings.get(i).EHead.equals("XCUIElementTypeScrollView")) {
//					System.out.println("XCUIElementTypeScrollView's sibilings size:" + ele1.sibilings.get(i).sibilings.size());
//				}
//				if(ele2.sibilings.get(i).EHead.equals("XCUIElementTypeScrollView")) {
//					System.out.println("XCUIElementTypeScrollView's sibilings size:" + ele2.sibilings.get(i).sibilings.size());
//				}
				//ScrollView-
				if((ele1.sibilings.get(i).EHead.equals("XCUIElementTypeScrollView"))
						&& (ele1.sibilings.get(i).sibilings.size() == 1)
						&& (ele1.sibilings.get(i).sibilings.get(0).EHead.equals(ele2.sibilings.get(i).EHead))
						) {
					//System.out.println("ele1: XCUIElementTypeScrollView rule");
					label_XCUIElementTypeScrollView.add(i);
				}
				else if((ele2.sibilings.get(i).EHead.equals("XCUIElementTypeScrollView"))
						&& (ele2.sibilings.get(i).sibilings.size() == 1)
						&& (ele2.sibilings.get(i).sibilings.get(0).EHead.equals(ele1.sibilings.get(i).EHead))
						) {
					//System.out.println("ele2: XCUIElementTypeScrollView rule");
					label_XCUIElementTypeScrollView.add(i);
				}
				//Other-Image
				else if((ele1.sibilings.get(i).EHead.equals("XCUIElementTypeOther"))
						&& (ele1.sibilings.get(i).sibilings.size() == 1)
						&& (ele2.sibilings.get(i).sibilings.get(0).EHead.equals("XCUIElementTypeImage"))
						) {
					//System.out.println("ele1: XCUIElementTypeScrollView rule");
					label_XCUIElementTypeOtherImage.add(i);
				}
				else if((ele2.sibilings.get(i).EHead.equals("XCUIElementTypeOther"))
						&& (ele2.sibilings.get(i).sibilings.size() == 1)
						&& (ele1.sibilings.get(i).sibilings.get(0).EHead.equals("XCUIElementTypeImage"))
						) {
					//System.out.println("ele2: XCUIElementTypeScrollView rule");
					label_XCUIElementTypeOtherImage.add(i);
				}
				else if(ele1.sibilings.get(i).EHead.equals(ele2.sibilings.get(i).EHead)) {
					//do nothing
					//and could set more compare rules here
				}
				else {
					//System.out.println("False_1[ ele1Head: " + ele1.sibilings.get(i).EHead + "(" + ele1.sibilings.get(i).pageTreeLevel + ")" + ", ele2Head:" + ele2.sibilings.get(i).EHead + "(" + ele2.sibilings.get(i).pageTreeLevel + ")" + "]");
					return false;
				}
			}
			
			
			
			for(int i = 0; i < len1; i ++) {
				if(ele1.sibilings.get(i).EHead.equals("XCUIElementTypeCell")) {
					
				}
				else if(label_XCUIElementTypeScrollView.contains(i)) {
					//System.out.println("label_XCUIElementTypeScrollView contains:" + i);
				}
//				else if(ele1.sibilings.get(i).EHead.equals("XCUIElementTypeScrollView") || ele2.sibilings.get(i).EHead.equals("XCUIElementTypeScrollView")) {
//					
//				}
				else if(label_XCUIElementTypeOtherImage.contains(i)) {
					//System.out.println("label_XCUIElementTypeOtherImage:" + i);
				}
				else if(traverseComparePageTrees(ele1.sibilings.get(i), ele2.sibilings.get(i))) {
					//true, then do nothing
				}
				else {
					//System.out.println("False_2_fromItsSubtree[ ele1Head: " + ele1.sibilings.get(i).EHead + "(" + ele1.sibilings.get(i).pageTreeLevel + ")" + ", ele2Head:" + ele2.sibilings.get(i).EHead + "(" + ele2.sibilings.get(i).pageTreeLevel + ")" + "]");
					return false;
				}
			}
			
		}
		else {
			//System.out.println("len1: " + len1 + ", len2: " + len2 + ".");
			//System.out.println("False_3_sibilingLength[ ele1Head: " + ele1.EHead + "(" + ele1.pageTreeLevel + "<" + ele1.Ex + "," + ele1.Ey + ">)" + ", ele2Head:" + ele2.EHead + "(" + ele2.pageTreeLevel + "<" + ele2.Ex + "," + ele2.Ey  + ">)" + "]");
			return false;
		}
		
		return true;
	}
	
	public static List<iOSXCUITestElement> deriveLeafSetofPruneTreeBasedOnHeuristicComparisonOpt(iOSXCUITestPage page) {
		List<iOSXCUITestElement> leafNodeSetofPrunedPageTree = new ArrayList<iOSXCUITestElement>();
		
		if(page.elementRoot.sibilings.size() == 0) {
			leafNodeSetofPrunedPageTree.add(page.elementRoot);
			return leafNodeSetofPrunedPageTree;
		}
		
		traverseTreeforPrune(page.elementRoot, page.pageWidth, page.pageHeight, leafNodeSetofPrunedPageTree);
		
		return leafNodeSetofPrunedPageTree;
	}
	
	public static void traverseTreeforPrune(iOSXCUITestElement ele, int width, int height, List<iOSXCUITestElement> leafNodeSetofPrunedPageTree) {
		//At first, we ignore the location out of scrope which is merely seen in Android app.
		
		int len = ele.sibilings.size();
		if(len == 0) {
			leafNodeSetofPrunedPageTree.add(ele);
		}
		for(int i = 0; i < len; i ++) {
			if(ele.sibilings.get(i).EHead.equals("XCUIElementTypeCell")) {
				//leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
			}
			//else if(ele.sibilings.get(i).EHead.equals("android.webkit.WebView")) {
			//	leafNodeSetofPrunedPageTree.add(ele.sibilings.get(i));
			//}
			else {
				traverseTreeforPrune(ele.sibilings.get(i), width, height, leafNodeSetofPrunedPageTree);
			}
		}
		
	}
	
	public static List<iOSXCUITestElement> deriveLeafSetofTree(iOSXCUITestPage page) {
		List<iOSXCUITestElement> leafNodeSetofPageTree = new ArrayList<iOSXCUITestElement>();
		
		if(page.elementRoot.sibilings.size() == 0) {
			leafNodeSetofPageTree.add(page.elementRoot);
			return leafNodeSetofPageTree;
		}
		
		traverseTree(page.elementRoot, page.pageWidth, page.pageHeight, leafNodeSetofPageTree);
		
		return leafNodeSetofPageTree;
	}
	
	public static void traverseTree(iOSXCUITestElement ele, int width, int height, List<iOSXCUITestElement> leafNodeSetofPageTree) {
		//At first, we ignore the location out of scrope which is merely seen in Android app.
		
		int len = ele.sibilings.size();
		if(len == 0) {
			leafNodeSetofPageTree.add(ele);
		}
		for(int i = 0; i < len; i ++) {
			traverseTree(ele.sibilings.get(i), width, height, leafNodeSetofPageTree);
		}
		
	}
	
	public static SourceCodeModule constructRelativeModuleinPageTree(iOSXCUITestPage page, String elementName, int orderNum){
		
		SourceCodeModule promotedSourceCodeModule = new SourceCodeModule(SourceCodeModule.INT_MODULE_LABEL_IOS);
		
		traverseTree4SourceCodeModule(page.elementRoot, elementName, orderNum, promotedSourceCodeModule);
		
		return promotedSourceCodeModule;
	}
	
	public static void traverseTree4SourceCodeModule(iOSXCUITestElement ele, String elementName, int orderNum, SourceCodeModule promotedSourceCodeModule) {
		
		int len = ele.sibilings.size();
		
		if(len == 0) {
			return ;
		}
		else {
			for(int i = 0; i < len; i ++) {
				
				iOSXCUITestElement eleTemp = ele.sibilings.get(i);
				if((eleTemp.locationTypeOrder == orderNum) && eleTemp.EHead.equals(elementName)) {
					
					for(int j = 0; j < len; j ++) {
						if(ele.sibilings.get(j).sibilings.size() == 0) {
							promotedSourceCodeModule.iosModule.add(ele.sibilings.get(j));
						}
					}
					return ;
				}
				else {
					traverseTree4SourceCodeModule(eleTemp, elementName, orderNum, promotedSourceCodeModule);
				}
				
				
			}
		}
		
		
	}
	
	
	
	
	
	public static iOSXCUITestElement findPageTreeNodebyFileName(iOSXCUITestPage page, String elementName, int orderNum) {
		iOSXCUITestElement root = page.elementRoot;
		return traverseSearchPageTree(root, elementName, orderNum);
		
	}
	
	public static iOSXCUITestElement traverseSearchPageTree(iOSXCUITestElement ele, String elementName, int orderNum) {
		
		int len = ele.sibilings.size();
		iOSXCUITestElement temp = null;
		
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
	
	public static iOSXCUITestElement findPageTreeNodebyAttributeLabel(iOSXCUITestPage page, String label) {
		iOSXCUITestElement root = page.elementRoot;
		return traverseSearchPageTreebyAttributeLabel(root, label);
		
	}
	
	public static iOSXCUITestElement traverseSearchPageTreebyAttributeLabel(iOSXCUITestElement ele, String label) {
		
		int len = ele.sibilings.size();
		iOSXCUITestElement temp = null;
		
		if(ele.ELabel != null && ele.ELabel.equals(label)) {
			return ele;
		}
		else {
			for(int i = 0; i < len; i ++) {
				temp = traverseSearchPageTreebyAttributeLabel(ele.sibilings.get(i), label);
				if(temp != null) {
					return temp;
				}
			}
			return temp;
		}
		
	}
	
	
	
	public static void traverse2SetLocationTypeOrderofPageTreeUsingBFS(iOSXCUITestPage page) {
		iOSXCUITestElement root = page.elementRoot;
		
		HashMap<String, Integer> elementCalculation = new HashMap<String, Integer>();
		root.locationTypeOrder = GUIPageXMLFileReader.labelTypeOrder(elementCalculation, root.EType);
		
		Queue<iOSXCUITestElement> queue = new LinkedList<iOSXCUITestElement>();
		traverse2SetlocationTypeOrderofPageTreeUsingBFS(root, elementCalculation, queue);
		
	}
	
	public static void traverse2SetlocationTypeOrderofPageTreeUsingBFS(iOSXCUITestElement ele, HashMap<String, Integer> elementCalculation, Queue<iOSXCUITestElement> queue) {
		
		
		int len = ele.sibilings.size();
		
		for(int i = 0; i < len; i ++) {
			queue.add(ele.sibilings.get(i));
			ele.sibilings.get(i).locationTypeOrder = GUIPageXMLFileReader.labelTypeOrder(elementCalculation, ele.sibilings.get(i).EType);
		}
		
		if(!queue.isEmpty()) {
			traverse2SetlocationTypeOrderofPageTreeUsingBFS(queue.poll(), elementCalculation, queue);
		}
		
	}
	
	
	
	public static List<List<List<Integer>>> traverse2RemoveDuplicatedInfoofPageTreeUsingBFS(iOSXCUITestPage page) {
		iOSXCUITestElement root = page.elementRoot;
		
//		HashMap<String, Integer> elementCalculation = new HashMap<String, Integer>();
//		root.locationTypeOrder = GUIPageXMLFileReader.labelTypeOrder(elementCalculation, root.EType);
		
		List<List<Integer>> heightScrope = new ArrayList<List<Integer>>();
		List<List<Integer>> widthScrope = new ArrayList<List<Integer>>();
		
		Queue<iOSXCUITestElement> queue = new LinkedList<iOSXCUITestElement>();
		queue.add(root);
		traverse2RemoveDuplicatedInfoofPageTreeUsingBFS(queue, widthScrope, heightScrope);
		
		List<List<List<Integer>>> re = new ArrayList<List<List<Integer>>>();
		re.add(widthScrope);
		re.add(heightScrope);
		
		return re;
	}
	
	public static void traverse2RemoveDuplicatedInfoofPageTreeUsingBFS(Queue<iOSXCUITestElement> queue, List<List<Integer>> widthScrope, List<List<Integer>> heightScrope) {
		
		Queue<iOSXCUITestElement> next_queue = new LinkedList<iOSXCUITestElement>();
		
		List<Integer> widthScropeOneTSLevel = new ArrayList<Integer>();
		List<Integer> heightScropeOneTSLevel = new ArrayList<Integer>();
		
		while(!queue.isEmpty()) {
			iOSXCUITestElement ele = queue.poll();
			
			int len = ele.sibilings.size();
			
			for(int i = 0; i < len; i ++) {
				next_queue.add(ele.sibilings.get(i));
			}
			widthScropeOneTSLevel.add(ele.Ex);
			widthScropeOneTSLevel.add(ele.Ex+ele.Ewidth);
			heightScropeOneTSLevel.add(ele.Ey);
			heightScropeOneTSLevel.add(ele.Ey+ele.Eheight);
		}
		
		widthScrope.add(widthScropeOneTSLevel);
		heightScrope.add(heightScropeOneTSLevel);
		
		if(!next_queue.isEmpty()) {
			traverse2RemoveDuplicatedInfoofPageTreeUsingBFS(next_queue, widthScrope, heightScrope);
		}
		
		
	}
	
	
	public static iOSXCUITestPage traverse2CopyTree4LayoutRepresentationModel(iOSXCUITestPage page) {
		
		iOSXCUITestPage treeCopy4LayoutRepresentationModel = new iOSXCUITestPage();
		treeCopy4LayoutRepresentationModel.fileHead = page.fileHead;
		treeCopy4LayoutRepresentationModel.appiumAUT = page.appiumAUT;
		treeCopy4LayoutRepresentationModel.pageWidth = page.pageWidth;
		treeCopy4LayoutRepresentationModel.pageHeight = page.pageHeight;
		
		iOSXCUITestElement eleCopy = page.elementRoot.copyElement();
		treeCopy4LayoutRepresentationModel.elementRoot = eleCopy;
		
		if(page.elementRoot.sibilings.size() == 0) {
			return treeCopy4LayoutRepresentationModel;
		}
		
		traverse2CopyTree4LayoutRepresentationModel(page.elementRoot, page.pageWidth, page.pageHeight, treeCopy4LayoutRepresentationModel.elementRoot);
		
		
		return treeCopy4LayoutRepresentationModel;
	}
	
	public static void traverse2CopyTree4LayoutRepresentationModel(iOSXCUITestElement ele, int width, int height, iOSXCUITestElement eleCopyInPageCopy) {
		//At first, we ignore the location out of scrope which is merely seen in Android app.
		
		int len = ele.sibilings.size();
		
		for(int i = 0; i < len; i ++) {
			iOSXCUITestElement eleCopy = ele.sibilings.get(i).copyElement();
			eleCopy.parent = eleCopyInPageCopy;
			eleCopyInPageCopy.sibilings.add(eleCopy);
			traverse2CopyTree4LayoutRepresentationModel(ele.sibilings.get(i), width, height, eleCopy);
			
		}
		
	}
	
	
	
	public static iOSXCUITestElement traverse2GetOutlineLevel04LayoutRepresentationModel(iOSXCUITestPage page) {
		
		List<iOSXCUITestElement> reList = new ArrayList<iOSXCUITestElement>();
//		int re = -10;
		
//		if(page.elementRoot.sibilings == null) {
//			return re;
//		}
		
		//Start from the 1st TypeWindow
		List<iOSXCUITestElement> mainWindows = iOSXMLHandlingTools.iOSXMLSourceCodeMainWindowSubtreeSelector(page);
		
		//currently, all contain only one main window
		iOSXCUITestElement eleRoot = new iOSXCUITestElement();//virtual
		if(mainWindows.size() < 1) {
			return eleRoot;
		}
		else {
			eleRoot = mainWindows.get(0);
		}
		
//		iOSXCUITestElement.printElement(eleRoot);
		
		if(eleRoot.sibilings.size() == 0) {
			return eleRoot;
//			return re;
		}
		else if(eleRoot.sibilings.size() == 1) {
			//We suppose the root element always links to one node or no node
			reList = traverse2GetOutlineLevel04LayoutRepresentationModel(page.elementRoot.sibilings.get(0), 0, page.pageWidth, page.pageHeight);
			int lenReList = reList.size();
			for(int i = 0; i < lenReList; i ++) {
				reList.get(i).parent = eleRoot;
			}
//			re = traverse2GetOutlineLevel04LayoutRepresentationModel(page.elementRoot.sibilings.get(0), 0, page.pageWidth, page.pageHeight);
//			re = reList.size();
		}
		else {
//			System.out.println("more: " + eleRoot.sibilings.size());
//			re = eleRoot.sibilings.size();
			reList = eleRoot.sibilings;
		}
		
//		re = page.elementRoot.sibilings.size();
		
		if(reList.size() != 0) {
			eleRoot.sibilings = reList;
		}
		
		return eleRoot;
//		return re;
	}
	
	public static List<iOSXCUITestElement> traverse2GetOutlineLevel04LayoutRepresentationModel(iOSXCUITestElement ele, int sibilingOrder, int width, int height) {
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
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void printPageTree(iOSXCUITestPage page) {
		iOSXCUITestElement root = page.elementRoot;
		traversePrintPageTree(root);
		
	}
	
	public static void traversePrintPageTree(iOSXCUITestElement ele) {
		
//		
//		int len = ele.sibilings.size();
//		String output = "[" + ele.EHead + ", " + ele.pageTreeLevel + ", " + ele.locationTypeOrder + ", <" + ele.Ex + ", " + ele.Ey + ">" + "](";
//		for(int i = 0; i < len; i ++) {
//			output = output + "[" + ele.sibilings.get(i).EHead + ", " + ele.sibilings.get(i).pageTreeLevel + ", " + ele.sibilings.get(i).locationTypeOrder + ", <" + ele.sibilings.get(i).Ex + ", " + ele.sibilings.get(i).Ey + ">]";
//		}
//		output = output + ")";
//		System.out.println(output);
//		for(int i = 0; i < len; i ++) {
//			traversePrintPageTree(ele.sibilings.get(i));
//		}
		
		int len = ele.sibilings.size();
		String output = "[" + ele.EHead + ", " + ele.EName + ", " + ele.locationTypeOrder + ", <" + ele.Ex + ", " + ele.Ey + "><" + (ele.Ex + ele.Ewidth) + ", " + (ele.Ey + ele.Eheight) + ">](";
		for(int i = 0; i < len; i ++) {
			output = output + "[" + ele.sibilings.get(i).EHead + ", " + ele.sibilings.get(i).EName + ", " + ele.sibilings.get(i).locationTypeOrder + ", <" + ele.sibilings.get(i).Ex + ", " + ele.sibilings.get(i).Ey + "><" + (ele.sibilings.get(i).Ex + ele.sibilings.get(i).Ewidth) + ", " + (ele.sibilings.get(i).Ey + ele.sibilings.get(i).Eheight) + ">]";
		}
		output = output + ")";
		System.out.println(output);
		for(int i = 0; i < len; i ++) {
			traversePrintPageTree(ele.sibilings.get(i));
		}
		
		
		
	}
	
	public static void traversePrintStructuredPageTree(iOSXCUITestElement ele, int blanksCount) {
		
//		int len = ele.sibilings.size();
//		String output = "[" + ele.EHead + ", " + ele.EName + ", " + ele.locationTypeOrder + ", <" + ele.Ex + ", " + ele.Ey + "><" + (ele.Ex + ele.Ewidth) + ", " + (ele.Ey + ele.Eheight) + ">](";
//		for(int i = 0; i < len; i ++) {
//			output = output + "[" + ele.sibilings.get(i).EHead + ", " + ele.sibilings.get(i).EName + ", " + ele.sibilings.get(i).locationTypeOrder + ", <" + ele.sibilings.get(i).Ex + ", " + ele.sibilings.get(i).Ey + "><" + (ele.sibilings.get(i).Ex + ele.sibilings.get(i).Ewidth) + ", " + (ele.sibilings.get(i).Ey + ele.sibilings.get(i).Eheight) + ">]";
//		}
//		output = output + ")";
//		System.out.println(output);
//		for(int i = 0; i < len; i ++) {
//			traversePrintPageTree(ele.sibilings.get(i));
//		}
//		System.out.println("================= Start =================");
		
		int len = ele.sibilings.size();
		String output_ = "";
		for(int i = 0; i < blanksCount; i ++) {
			output_ = output_ + " ";
		}
		output_ = output_ + "[" + ele.EHead + ", " + ele.EName + ", " + ele.locationTypeOrder + ", <" + ele.Ex + ", " + ele.Ey + "><" + (ele.Ex + ele.Ewidth) + ", " + (ele.Ey + ele.Eheight) + ">]";
		System.out.println(output_);
		
		
//		iOSXCUITestElement.printElement(ele);
//		System.out.println(ele.sibilings.size());
//		iOSXCUITestElement.printElement(ele.sibilings.get(0));
		
		
		for(int i = 0; i < len; i ++) {
			traversePrintStructuredPageTree(ele.sibilings.get(i), blanksCount + 2);
		}
		
	}
	
	
	
	
	
	
	
	
}
