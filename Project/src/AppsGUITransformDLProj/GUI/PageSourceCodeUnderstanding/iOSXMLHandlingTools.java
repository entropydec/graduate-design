package AppsGUITransformDLProj.GUI.PageSourceCodeUnderstanding;

import java.util.*;

import AppsGUITransformDLProj.GUI.iOSXCUITestElement;
import AppsGUITransformDLProj.GUI.iOSXCUITestPage;

public class iOSXMLHandlingTools {
	
	
	/**
	 * Find the Main Window
	 * */
	public static List<iOSXCUITestElement> iOSXMLSourceCodeMainWindowSubtreeSelector(iOSXCUITestPage page) {
		
		iOSXCUITestElement appNode = page.elementRoot;
		
//		System.out.println("Start: " + appNode.EHead);
		int len = appNode.sibilings.size();//all XCUIElementTypeWindow node
		List<iOSXCUITestElement> candidateMainWindowSubtreeList = new ArrayList<iOSXCUITestElement>();
		for(int i = 0; i < len; i ++) {
//			System.out.println(i + ": " + appNode.sibilings.get(i).EHead);
			candidateMainWindowSubtreeList.add(appNode.sibilings.get(i));
		}
		
		//Remove not visiable
		len = candidateMainWindowSubtreeList.size();
		List<iOSXCUITestElement> candidateMainWindowSubtreeList_RemoveNotVisiable = new ArrayList<iOSXCUITestElement>();
		for(int i = 0 ; i < len; i ++) {
//			System.out.println(candidateMainWindowSubtreeList.get(i).EHead + " - Visiable: " + candidateMainWindowSubtreeList.get(i).EVisible);
//			if(candidateMainWindowSubtreeList.get(i).EVisible.equals("true")) {
			if(isContainVisiableControl(candidateMainWindowSubtreeList.get(i))) {
//				System.out.println("true");
				candidateMainWindowSubtreeList_RemoveNotVisiable.add(candidateMainWindowSubtreeList.get(i));
			}
		}
		
		//Remove the window containing the status bar
		len = candidateMainWindowSubtreeList_RemoveNotVisiable.size();
		List<iOSXCUITestElement> candidateMainWindowSubtreeList_RemoveStatusBar = new ArrayList<iOSXCUITestElement>();
		for(int i = 0 ; i < len; i ++) {
			if(!isStatusBarWindow(candidateMainWindowSubtreeList_RemoveNotVisiable.get(i))) {
				candidateMainWindowSubtreeList_RemoveStatusBar.add(candidateMainWindowSubtreeList_RemoveNotVisiable.get(i));
			}
		}
		
		//Remove useless TypeOther
		len = candidateMainWindowSubtreeList_RemoveStatusBar.size();
		List<iOSXCUITestElement> candidateMainWindowSubtreeList_RemoveUselessTypeOther = new ArrayList<iOSXCUITestElement>();
		for(int i = 0 ; i < len; i ++) {
			if(!isUselessTypeOtherWindow(candidateMainWindowSubtreeList_RemoveStatusBar.get(i))) {
				candidateMainWindowSubtreeList_RemoveUselessTypeOther.add(candidateMainWindowSubtreeList_RemoveStatusBar.get(i));
			}
		}
		
		//Remove TypeKeyboard window
		len = candidateMainWindowSubtreeList_RemoveUselessTypeOther.size();
		List<iOSXCUITestElement> candidateMainWindowSubtreeList_RemoveTypeKeyboard = new ArrayList<iOSXCUITestElement>();
		for(int i = 0 ; i < len; i ++) {
			if(!isTypeKeyboardWindow(candidateMainWindowSubtreeList_RemoveUselessTypeOther.get(i))) {
				candidateMainWindowSubtreeList_RemoveTypeKeyboard.add(candidateMainWindowSubtreeList_RemoveUselessTypeOther.get(i));
			}
		}
		
		
		
		return candidateMainWindowSubtreeList_RemoveTypeKeyboard;
	}
	
	//visiable
	public static boolean isContainVisiableControl(iOSXCUITestElement ele) {
		List<iOSXCUITestElement> allNodeSetofPageTree = new ArrayList<iOSXCUITestElement>();
		
		traverse2CollectAllNodes(ele, allNodeSetofPageTree);
		
		for(int i = 0; i < allNodeSetofPageTree.size(); i ++) {
			if(allNodeSetofPageTree.get(i).EVisible.equals("true")) {
				return true;
			}
		}
		
		return false;
	}
	
	
	//Status Bar
	public static boolean isStatusBarWindow(iOSXCUITestElement ele) {
		
		int len = ele.sibilings.size();
		for(int i = 0; i < len; i ++) {
			if(ele.sibilings.get(i).EHead.equals("XCUIElementTypeStatusBar")) {
				return true;
			}
		}
		
		return false;
	}
	
	//TypeOther [Notice! Should consider the other type in the tree path?]
	public static boolean isUselessTypeOtherWindow(iOSXCUITestElement ele) {
		
		List<iOSXCUITestElement> leafNodeSetofPageTree = new ArrayList<iOSXCUITestElement>();
		iOSXCUITestPage.traverseTree(ele, 0, 0, leafNodeSetofPageTree);
		if(leafNodeSetofPageTree.size() == 1
				&& leafNodeSetofPageTree.get(0).EHead.equals("XCUIElementTypeOther")
				) {
			return true;
		}
		
		
		return false;
	}
	
	//TypeKeyboard
	public static boolean isTypeKeyboardWindow(iOSXCUITestElement ele) {
		
		String keyTypeString = "XCUIElementTypeKeyboard";
		if(isContainSpecificElementType(ele, keyTypeString)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Remove the useless leaf node: 
	 * 1.the background node, which is a leaf node and share page space with the other container node
	 * Currently, every level of a tree we have one background node, and it is the first one confrortable with the overlap rule
	 * 2. width = 0 or 1, height = 0 or 1
	 * */
	
	public static void iOSXMLSourceCodeRemoveUselessLeafNodeEXBackgroundNode(iOSXCUITestElement mainWindow) {
		
		traverse2RemoveUselessLeafNode(mainWindow);
		traverse2RemoveUselessLeafNode_Not2DElements(mainWindow);
		
//		traverse2RemoveUselessLeafNode_WholePageBackgroudElements(mainWindow, mainWindow.Ewidth, mainWindow.Eheight);//everytime remove a leaf may create empty subtree whitch is a new leaf (such leaf is a whole page element)
		List<iOSXCUITestElement> lastNumOfLeaves = new ArrayList<iOSXCUITestElement>();
		traverse2CollectAllNodes(mainWindow, lastNumOfLeaves);
		int countLastNumOfLeaves =  10000;
		int countCurrentNumOfLeaves = lastNumOfLeaves.size();
		lastNumOfLeaves.clear();
		while(countCurrentNumOfLeaves < countLastNumOfLeaves) {
//			System.out.println("=== Trigger ===" + countCurrentNumOfLeaves + ", " + countLastNumOfLeaves);
			traverse2RemoveUselessLeafNode_WholePageBackgroudElements(mainWindow, mainWindow.Ewidth, mainWindow.Eheight);//everytime remove a leaf may create empty subtree whitch is a new leaf (such leaf is a whole page element)
			traverse2CollectAllNodes(mainWindow, lastNumOfLeaves);
			countLastNumOfLeaves = countCurrentNumOfLeaves;
			countCurrentNumOfLeaves = lastNumOfLeaves.size();
			lastNumOfLeaves.clear();
//			System.out.println("=== Triggered ===" + countCurrentNumOfLeaves + ", " + countLastNumOfLeaves);
		}
		
		
//		iOSXCUITestElement.printElement(mainWindow);
		
	}
	public static void traverse2RemoveUselessLeafNode(iOSXCUITestElement ele) {
		
//		List<iOSXCUITestElement> newSibilings = new ArrayList<iOSXCUITestElement>();
//		
//		for(int i = 0; i < ele.sibilings.size(); i ++) {
//			if(isUselessBackgroundNode(ele.sibilings.get(i))) {
//				//remove (here, the node must a leaf node)
//			}
//			else {
//				newSibilings.add(ele.sibilings.get(i));
//			}
//		}
//		
//		ele.sibilings = newSibilings;
//		for(int i = 0 ; i < ele.sibilings.size(); i ++) {
//			traverse2RemoveUselessLeafNode(ele.sibilings.get(i));
//		}
		
		int index = 0;
		for(; index < ele.sibilings.size(); index ++) {
			if(isUselessBackgroundNode(ele.sibilings.get(index))) {
//				System.out.println("In RemoveUselessLeaf Ori: " + index + ", " + ele.sibilings.size() + " " + ele.sibilings.get(index).EHead + "[" + ele.sibilings.get(index).Ex + "," + ele.sibilings.get(index).Ey + "]" + ele.sibilings.get(index).Ewidth + "," + ele.sibilings.get(index).Eheight);
				
				break;
			}
		}
//		System.out.println("In RemoveUselessLeaf Ori: " + index + ", " + ele.sibilings.size());
		if(index < ele.sibilings.size()) {
//			for(int i = 0; i < ele.sibilings.size(); i ++) {
//				System.out.println("In RemoveUselessLeaf Ori: " + ele.sibilings.get(i).EHead + "[" + ele.sibilings.get(i).Ex + "," + ele.sibilings.get(i).Ey + "]" + ele.sibilings.get(i).Ewidth + "," + ele.sibilings.get(i).Eheight);
//			}
			ele.sibilings.remove(index);
//			try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); }
		}
		for(int i = 0 ; i < ele.sibilings.size(); i ++) {
			traverse2RemoveUselessLeafNode(ele.sibilings.get(i));
		}
//		System.out.println("In RemoveUselessLeaf: " + index + ", " + ele.sibilings.size());
	}
	
	//useless leaf node: the background node, which is a leaf node and share page space with the other container node
	public static boolean isUselessBackgroundNode(iOSXCUITestElement ele) {
		
		if(ele.sibilings.size() != 0) {//a container node
			return false;
		}
		//A leaf node
		iOSXCUITestElement eleFatherNode = ele.parent;
		List<iOSXCUITestElement> fathersSibilings = eleFatherNode.sibilings;
		if(fathersSibilings.size() > 1) {
			//Currently, compare the space with its father node who is a container node
			double sharedRate = rateOfSharedCoveringSpaceInFatherContainer(eleFatherNode, ele);
			if(sharedRate > 0.99) {
				return true;
			}
		}
		
		
		return false;
	}
	
	//useless node and its subtree (not 2D Element)
	public static void traverse2RemoveUselessLeafNode_Not2DElements(iOSXCUITestElement ele) {
		
		List<iOSXCUITestElement> newSibilings = new ArrayList<iOSXCUITestElement>();
		
		for(int i = 0; i < ele.sibilings.size(); i ++) {
			
//			iOSXCUITestElement.printElement(ele.sibilings.get(i));
//			try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
			
//			if(ele.sibilings.get(i).EHead.equals("XCUIElementTypeOther") && ele.sibilings.get(i).locationTypeOrder == 0) {
//				iOSXCUITestElement.printElement(ele.sibilings.get(i));
//				System.out.println(is2DElement(ele.sibilings.get(i)));
//				try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); }
//			}
			
			if(is2DElement(ele.sibilings.get(i))) {
				newSibilings.add(ele.sibilings.get(i));
				
			}
			else {
				//remove (here, the node must a leaf node)
//				System.out.println("is2DElement work");
//				iOSXCUITestElement.printElement(ele.sibilings.get(i));
//				try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
		
		ele.sibilings = newSibilings;
		for(int i = 0 ; i < ele.sibilings.size(); i ++) {
			traverse2RemoveUselessLeafNode_Not2DElements(ele.sibilings.get(i));
		}
		
		
	}
	
	public static boolean is2DElement(iOSXCUITestElement ele) {
		if(ele.Ewidth == 0 || ele.Ewidth == 1 || ele.Eheight == 0 || ele.Eheight == 1) {
			return false;
		}
		return true;
	}
	
	//WholePageBackgroud
	public static void traverse2RemoveUselessLeafNode_WholePageBackgroudElements(iOSXCUITestElement ele, int W, int H) {
		
		List<iOSXCUITestElement> newSibilings = new ArrayList<iOSXCUITestElement>();
		
		for(int i = 0; i < ele.sibilings.size(); i ++) {
			
//			iOSXCUITestElement.printElement(ele.sibilings.get(i));
//			try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
			
			if(ele.sibilings.get(i).sibilings.size() == 0 && ele.sibilings.get(i).Ewidth == W && ele.sibilings.get(i).Eheight == H) {
				
			}
			else {
				newSibilings.add(ele.sibilings.get(i));
			}
		}
		
		ele.sibilings = newSibilings;
		for(int i = 0 ; i < ele.sibilings.size(); i ++) {
			traverse2RemoveUselessLeafNode_WholePageBackgroudElements(ele.sibilings.get(i), W, H);
		}
		
		
	}
	
	
	
	
	
	/**
	 * Compress the duplicated container node: the container only has one child is a duplicated container
	 * 
	 * */
	public static void iOSXMLSourceCodeCompressDuplicatedContainer(iOSXCUITestElement mainWindowWithoutBackground) {
		
//		traverse2RemoveUselessLeafNode_WholePageBackgroudElements(mainWindowWithoutBackground, mainWindowWithoutBackground.Ewidth, mainWindowWithoutBackground.Eheight);//everytime remove a leaf may create empty subtree whitch is a new leaf (such leaf is a whole page element)
//		List<iOSXCUITestElement> lastNumOfLeaves = new ArrayList<iOSXCUITestElement>();
//		traverse2CollectAllNodes(mainWindowWithoutBackground, lastNumOfLeaves);
//		int countLastNumOfLeaves =  10000;
//		int countCurrentNumOfLeaves = lastNumOfLeaves.size();
//		lastNumOfLeaves.clear();
//		while(countCurrentNumOfLeaves < countLastNumOfLeaves) {
//			System.out.println("=== Trigger ===" + countCurrentNumOfLeaves + ", " + countLastNumOfLeaves);
//			traverse2RemoveUselessLeafNode_WholePageBackgroudElements(mainWindowWithoutBackground, mainWindowWithoutBackground.Ewidth, mainWindowWithoutBackground.Eheight);//everytime remove a leaf may create empty subtree whitch is a new leaf (such leaf is a whole page element)
//			traverse2CollectAllNodes(mainWindowWithoutBackground, lastNumOfLeaves);
//			countLastNumOfLeaves = countCurrentNumOfLeaves;
//			countCurrentNumOfLeaves = lastNumOfLeaves.size();
//			lastNumOfLeaves.clear();
//			System.out.println("=== Triggered ===" + countCurrentNumOfLeaves + ", " + countLastNumOfLeaves);
//		}
//		
//		iOSXCUITestElement.printElement(mainWindowWithoutBackground);
		
		
//		traverse2CompressDuplicatedContainer(mainWindowWithoutBackground, 0);//its parents do not been used, so here use default 0
		
//		List<iOSXCUITestElement> reList = null;
		
		if(mainWindowWithoutBackground.sibilings.size() == 0) {
			//do nothing
		}
		else if(mainWindowWithoutBackground.sibilings.size() == 1) {
			
//			iOSXCUITestElement onlyOneSibiling = mainWindowWithoutBackground.sibilings.get(0);
//			int lenOnlyChildSibilings = onlyOneSibiling.sibilings.size();
//			for(int i = 0; i < lenOnlyChildSibilings; i ++) {
//				iOSXCUITestElement childTemp = onlyOneSibiling.sibilings.get(i);
//				childTemp.parent = mainWindowWithoutBackground;
//			}
//			mainWindowWithoutBackground.sibilings = onlyOneSibiling.sibilings;
//			for(int i = 0; i < ) {
//				
//			}
			
			traverse2CompressDuplicatedContainer(mainWindowWithoutBackground.sibilings.get(0), 0);
			if(mainWindowWithoutBackground.sibilings.size() == 1) {//only for make sure, actually dosen't work (always true)
				mainWindowWithoutBackground.sibilings = mainWindowWithoutBackground.sibilings.get(0).sibilings;
			}
		}
		else {
			// > 1
			int lenNextSibilings = mainWindowWithoutBackground.sibilings.size();
			for(int i = 0; i < lenNextSibilings; i ++) {
				traverse2CompressDuplicatedContainer(mainWindowWithoutBackground.sibilings.get(i), i);
			}
		}
		
		
		
	}
	
	public static void traverse2CompressDuplicatedContainer(iOSXCUITestElement ele, int sibilingOrder) {
		
		int len = ele.sibilings.size();
		if(len == 0) {}
		else if(len == 1) {
			
			
			
			iOSXCUITestElement onlyOneSibiling = ele.sibilings.get(0);
			onlyOneSibiling.parent = ele.parent;
			ele.parent.sibilings.add(sibilingOrder, onlyOneSibiling);
			
//			iOSXCUITestElement.printElement(ele.parent);
//			System.out.println("End for Validation");
//			iOSXCUITestElement.printElement(ele.parent.sibilings.get(sibilingOrder+1));
//			
//			iOSXCUITestElement.printElement(ele);
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			
			ele.parent.sibilings.remove(sibilingOrder+1);
			
			traverse2CompressDuplicatedContainer(onlyOneSibiling, sibilingOrder);
		}
		else {
			for(int i = 0; i < len; i ++) {
				traverse2CompressDuplicatedContainer(ele.sibilings.get(i), i);
			}
		}
		
		
	}
	
	/**
	 * Separate the container which cover the whole page into the next level
	 * # this info affects the module process #
	 * */
	public static void dealWithWholePageContainer(iOSXCUITestElement mainWindowAfterCompress) {
		
		int len = mainWindowAfterCompress.sibilings.size();
		if(len == 0) {
			//do nothing
		}
		else {
			LinkedList<iOSXCUITestElement> stackImpl = new LinkedList<iOSXCUITestElement>();
			for(int i = 0; i < len; i ++) {
				stackImpl.add(mainWindowAfterCompress.sibilings.get(i));
			}
			
//			iOSXCUITestPage.traversePrintPageTree(mainWindowAfterCompress);
			
			while(!stackImpl.isEmpty()) {
				
//				System.out.println("---------- Start:root ------------");
//				
//				
//				
//				System.out.println("Stack size: " + stackImpl.size());
//				System.out.println("Sibilings size: " + mainWindowAfterCompress.sibilings.size());
//				for(int i = 0; i < stackImpl.size(); i ++) {
//					System.out.println("_# " + stackImpl.get(i).EHead + ": " + stackImpl.get(i).locationTypeOrder);
//					System.out.println("_# " + mainWindowAfterCompress.sibilings.get(i).EHead + ": " + mainWindowAfterCompress.sibilings.get(i).locationTypeOrder);
//				}
				iOSXCUITestElement currentSibilingTemp = stackImpl.getFirst();
//				System.out.println("Cur: " + currentSibilingTemp.EHead + ": " + currentSibilingTemp.locationTypeOrder);
//				System.out.println("Cur's parent: " + currentSibilingTemp.parent.EHead + ": " + currentSibilingTemp.parent.locationTypeOrder + ": " + currentSibilingTemp.parent.EName);
				stackImpl.removeFirst();
				iOSXCUITestElement eleParent = currentSibilingTemp.parent;
				int index = -1;
				
//				System.out.println("eleParent.sibilings.size(): " + eleParent.sibilings.size());
				for(int i = 0; i < eleParent.sibilings.size(); i ++) {
//					System.out.println("# " + eleParent.sibilings.get(i).EHead + ": " + eleParent.sibilings.get(i).locationTypeOrder);
					if(eleParent.sibilings.get(i).EHead.equals(currentSibilingTemp.EHead) && (eleParent.sibilings.get(i).locationTypeOrder == currentSibilingTemp.locationTypeOrder)) {
						index = i;
						break;
					}
				}
				
//				System.out.println("## " + index);
				if(index == -1) {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
//				traverse2DealwithWholePageContainer(currentSibilingTemp, index, W, H, stackImpl);
				traverse2DealwithWholePageContainer(
						currentSibilingTemp, 
						index,
						mainWindowAfterCompress.Ewidth,
						mainWindowAfterCompress.Eheight,
						stackImpl
						);
				
				
//				System.out.println("Stack size [End]: " + stackImpl.size());
//				for(int i = 0; i < stackImpl.size(); i++) {
//					System.out.println("[end] " + stackImpl.get(i).EHead + ": " + stackImpl.get(i).locationTypeOrder);
//				}
				
			}
			
			
			
			
			
		}
		
	}
	
	public static void traverse2DealwithWholePageContainer(iOSXCUITestElement ele, int sibilingOrder, int W, int H, LinkedList<iOSXCUITestElement> lastStack) {
		
//		System.out.println("---------- Start ------------");
		
		if(sibilingOrder == -1) {
			iOSXCUITestElement.printElement(ele.parent);
			System.out.println(ele.parent.sibilings.size());
			iOSXCUITestElement.printElement(ele);
		}
		
		int len = ele.sibilings.size();
		if(len == 0) {
			//do nothing
		}
		else {
			
			if(ele.Ewidth == W && ele.Eheight == H) {
				
				iOSXCUITestElement eleParent = ele.parent;
				eleParent.sibilings.remove(sibilingOrder);
//				System.out.println("sibilingOrder: " + sibilingOrder);
				List<iOSXCUITestElement> nextSibilings = ele.sibilings;
				int lenNextSibilings = nextSibilings.size();
				for(int i = lenNextSibilings - 1; i >= 0; i --) {
					iOSXCUITestElement currentSibilingTemp = nextSibilings.get(i);
					currentSibilingTemp.parent = eleParent;
					eleParent.sibilings.add(sibilingOrder, currentSibilingTemp);
					
					lastStack.addFirst(currentSibilingTemp);
				}
				
			}
			else {
				
				LinkedList<iOSXCUITestElement> stackImpl = new LinkedList<iOSXCUITestElement>();
				for(int i = 0; i < len; i ++) {
					stackImpl.add(ele.sibilings.get(i));
				}
				
				while(!stackImpl.isEmpty()) {
					
					iOSXCUITestElement currentSibilingTemp = stackImpl.getFirst();
					stackImpl.removeFirst();
					iOSXCUITestElement eleParent = currentSibilingTemp.parent;
					int index = -1;
//					System.out.println(currentSibilingTemp.EHead + ": " + currentSibilingTemp.locationTypeOrder);
					for(int i = 0; i < eleParent.sibilings.size(); i ++) {
//						System.out.println("# " + eleParent.sibilings.get(i).EHead + ": " + eleParent.sibilings.get(i).locationTypeOrder);
						if(eleParent.sibilings.get(i).EHead.equals(currentSibilingTemp.EHead) && (eleParent.sibilings.get(i).locationTypeOrder == currentSibilingTemp.locationTypeOrder)) {
							index = i;
							break;
						}
					}
//					System.out.println("## " + index);
//					if(index == -1) {
//						try {
//							Thread.sleep(10000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
					traverse2DealwithWholePageContainer(currentSibilingTemp, index, W, H, stackImpl);
					
					
				}
				
			}
			
		}
		
		
		
		
	}
	
	/**
	 * Cut the invisiable tree
	 * 
	 * */
	public static void cutNotVisiableSubtree(iOSXCUITestElement mainWindowRMWholePageContainer) {
		
		int lenSibilings = mainWindowRMWholePageContainer.sibilings.size();
		
		if(lenSibilings > 1) {
			
			LinkedList<iOSXCUITestElement> stackImpl = new LinkedList<iOSXCUITestElement>();
			for(int i = 0; i < lenSibilings; i ++) {
				stackImpl.add(mainWindowRMWholePageContainer.sibilings.get(i));
			}
			
			while(!stackImpl.isEmpty()) {
				
				iOSXCUITestElement currentSibilingTemp = stackImpl.getFirst();
				stackImpl.removeFirst();
				traverse2CutNotVisiableSubtree(currentSibilingTemp);
				
			}
			
		}
		
	}
	
	public static void traverse2CutNotVisiableSubtree(iOSXCUITestElement ele) {
		
		int lenSibilings = ele.sibilings.size();
		if(lenSibilings == 0) {
			//leaf node
			if(ele.EVisible.equals("false")) {
				//rm and recheck its parent
				iOSXCUITestElement eleParent = ele.parent;
				int index = -1;
				for(int i = 0; i < eleParent.sibilings.size(); i ++) {
					if(eleParent.sibilings.get(i).EHead.equals(ele.EHead) && (eleParent.sibilings.get(i).locationTypeOrder == ele.locationTypeOrder)) {
						index = i;
						eleParent.sibilings.remove(index);
						break;
					}
				}
				if(index == -1) {
					System.out.println("-1");
					iOSXCUITestElement.printElement(ele);
				}
			}
			
		}
		else {
			//lenSibilings > 1
			LinkedList<iOSXCUITestElement> stackImpl = new LinkedList<iOSXCUITestElement>();
			for(int i = 0; i < lenSibilings; i ++) {
				stackImpl.add(ele.sibilings.get(i));
			}
			
			while(!stackImpl.isEmpty()) {
				
				iOSXCUITestElement currentSibilingTemp = stackImpl.getFirst();
				stackImpl.removeFirst();
				traverse2CutNotVisiableSubtree(currentSibilingTemp);
				
			}
			
			if(ele.sibilings.size() == 0) {
				
				if(ele.EVisible.equals("false")) {
					//rm and recheck its parent
					iOSXCUITestElement eleParent = ele.parent;
					int index = -1;
					for(int i = 0; i < eleParent.sibilings.size(); i ++) {
						if(eleParent.sibilings.get(i).EHead.equals(ele.EHead) && (eleParent.sibilings.get(i).locationTypeOrder == ele.locationTypeOrder)) {
							index = i;
							eleParent.sibilings.remove(index);
							break;
						}
					}
					if(index == -1) {
						System.out.println("-1");
						iOSXCUITestElement.printElement(ele);
					}
				}
			}
			
		}
		
		
	}
	
	/**
	 * Predict to remove the spliting lines (need to consider generating new leaves!)
	 * */
	public static void predict4RemoveSplitingLines(iOSXCUITestElement mainWindowRMNotVisialeSubtree) {
		
		traverse2Predict4RemoveSplitingLines(mainWindowRMNotVisialeSubtree, mainWindowRMNotVisialeSubtree.Ewidth, mainWindowRMNotVisialeSubtree.Eheight);
		
	}
	
	public static void traverse2Predict4RemoveSplitingLines(iOSXCUITestElement ele, int W, int H) {
		
		int index = 0;
		for(; index < ele.sibilings.size(); index ++) {
			if(isSplitingLines(ele.sibilings.get(index), W, H)) {
				ele.sibilings.remove(index);
				index --;
			}
		}
		for(int i = 0 ; i < ele.sibilings.size(); i ++) {
			traverse2Predict4RemoveSplitingLines(ele.sibilings.get(i), W, H);
		}
		
	}
	
	public static boolean isSplitingLines(iOSXCUITestElement ele, int W, int H) {
		
		if(ele.sibilings.size() == 0) {//a leaf node
			
			if(
				(ele.Ewidth == W || ele.Eheight == 1 || ele.Eheight == 2)
				&& 
				((ele.EValue == null || ele.EValue.equals(""))
				&& (ele.EName == null || ele.EName.equals(""))
				&& (ele.ELabel == null || ele.ELabel.equals("")))
					) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * extract Layout UIProject.Module Page Representation Model from the pre-treated page structure tree
	 * */
	public static iOSXCUITestElement extractLayoutModulePageRepresentationModel(iOSXCUITestElement mainWindowPreHandled) {
		
		OutlineLayoutRepresentationModel outlineLayoutRepresentationModel = new OutlineLayoutRepresentationModel(
																				"iOS", 
																				mainWindowPreHandled, 
																				null, 
																				mainWindowPreHandled.Ewidth, 
																				mainWindowPreHandled.Eheight
																				);
		
		iOSXCUITestElement mainWindowCopy4RepresentationModel = mainWindowPreHandled.copyElement();
		mainWindowCopy4RepresentationModel.sibilings.clear();
		for(int i = 0; i < outlineLayoutRepresentationModel.iosRankedSibilingChildrenList.size(); i ++) {
			
			//current line of nodes
			List<iOSXCUITestElement> currentLineTemp = outlineLayoutRepresentationModel.iosRankedSibilingChildrenList.get(i);
			
			//construct a new container node for the ranked line nodes
			iOSXCUITestElement virtualContainerTemp = new iOSXCUITestElement();
			mainWindowCopy4RepresentationModel.sibilings.add(virtualContainerTemp);
			virtualContainerTemp.parent = mainWindowCopy4RepresentationModel;
			
			for(int j = 0 ; j < currentLineTemp.size(); j ++) {
				iOSXCUITestElement virtualLineNodeTemp = currentLineTemp.get(j).copyElement();
				virtualContainerTemp.sibilings.add(virtualLineNodeTemp);
				virtualLineNodeTemp.parent = virtualContainerTemp;
				
				//Insert virtualLineNodeTemp's sibilings, and continue to break the multiline in the line module
				traverse2ExtractLayoutModulePageRepresentationModel(currentLineTemp.get(j), mainWindowPreHandled.Ewidth, mainWindowPreHandled.Eheight, virtualLineNodeTemp);
				
			}
			
		}
		
		
//		System.out.println("Current sibilings count: " + outlineLayoutRepresentationModel.iosSibilingChildrenList.size());
//		System.out.println("Current module lines count: " + outlineLayoutRepresentationModel.iosRankedSibilingChildrenList.size());
		
		
		return mainWindowCopy4RepresentationModel;
		
	}
	
	public static void traverse2ExtractLayoutModulePageRepresentationModel(iOSXCUITestElement eleOriginal, int W, int H, iOSXCUITestElement eleVirtualNode) {
		
		
		if(eleOriginal.sibilings.size() == 0) {
			//leaf node
			//do nothing
		}
		else {
			OutlineLayoutRepresentationModel eleOriOutlineLayoutRepresentationModel = new OutlineLayoutRepresentationModel(
					"iOS", 
					eleOriginal, 
					null, 
					W, 
					H
					);
			
			int len = eleOriOutlineLayoutRepresentationModel.iosRankedSibilingChildrenList.size();
			for(int i = 0; i < len; i ++) {
				//current line of nodes
				List<iOSXCUITestElement> currentLineTemp = eleOriOutlineLayoutRepresentationModel.iosRankedSibilingChildrenList.get(i);
				//construct a new container node for the ranked line nodes
				iOSXCUITestElement virtualContainerTemp = new iOSXCUITestElement();
				eleVirtualNode.sibilings.add(virtualContainerTemp);
				virtualContainerTemp.parent = eleVirtualNode;
				for(int j = 0 ; j < currentLineTemp.size(); j ++) {
					iOSXCUITestElement virtualLineNodeTemp = currentLineTemp.get(j).copyElement();
					virtualContainerTemp.sibilings.add(virtualLineNodeTemp);
					virtualLineNodeTemp.parent = virtualContainerTemp;
					
					//Insert virtualLineNodeTemp's sibilings, and continue to break the multiline in the line module
					traverse2ExtractLayoutModulePageRepresentationModel(currentLineTemp.get(j), W, H, virtualLineNodeTemp);
					
				}
				
			}
		}
		
		
		
		
	}
	
	/**
	 * Compress Advance: Go directly to leaf nodes
	 * # compress the Virtual Container: ?
	 * */
	
	public static void advanceCompress4FormalPageLayoutModuleRepresentationModel(iOSXCUITestElement mainWindowWithoutBackground) {
		
//		System.out.println("================= S1: " + mainWindowWithoutBackground.sibilings.size());
//		iOSXCUITestElement.printElement(mainWindowWithoutBackground);
		
		if(mainWindowWithoutBackground.sibilings.size() == 0) {
			//do nothing
		}
		else if(mainWindowWithoutBackground.sibilings.size() == 1) {
			
//			iOSXCUITestElement onlyOneSibiling = mainWindowWithoutBackground.sibilings.get(0);
//			int lenOnlyChildSibilings = onlyOneSibiling.sibilings.size();
//			for(int i = 0; i < lenOnlyChildSibilings; i ++) {
//				iOSXCUITestElement childTemp = onlyOneSibiling.sibilings.get(i);
//				childTemp.parent = mainWindowWithoutBackground;
//			}
//			mainWindowWithoutBackground.sibilings = onlyOneSibiling.sibilings;
//			for(int i = 0; i < ) {
//				
//			}
//			System.out.println("================= S2 =================");
			
			traverse2AdvanceCompress4FormalPageLayoutModuleRepresentationModel(mainWindowWithoutBackground.sibilings.get(0), 0);
			//this does not same as teh original RM one child subtree
		}
		else {
			// > 1
			int lenNextSibilings = mainWindowWithoutBackground.sibilings.size();
			for(int i = 0; i < lenNextSibilings; i ++) {
				traverse2AdvanceCompress4FormalPageLayoutModuleRepresentationModel(mainWindowWithoutBackground.sibilings.get(i), i);
			}
		}
		
	}
	
	public static void traverse2AdvanceCompress4FormalPageLayoutModuleRepresentationModel(iOSXCUITestElement ele, int sibilingOrder) {
		
//		System.out.println("================= S3: " + ele.sibilings.size());
		
		
		int len = ele.sibilings.size();
		if(len == 0) {
//			iOSXCUITestElement.printElement(ele);
//			iOSXCUITestElement.printElement(ele.parent);
//			iOSXCUITestElement.printElement(ele.parent.sibilings.get(0));
		}
		else if(len == 1) {
			
			
			
			iOSXCUITestElement onlyOneSibiling = ele.sibilings.get(0);
			onlyOneSibiling.parent = ele.parent;
			ele.parent.sibilings.add(sibilingOrder, onlyOneSibiling);
			
//			System.out.println("Current ele: ");
//			iOSXCUITestElement.printElement(ele);
//			System.out.println("Ele parent: ");
//			iOSXCUITestElement.printElement(ele.parent);
//			System.out.println("End for Validation");
//			iOSXCUITestElement.printElement(ele.parent.sibilings.get(sibilingOrder+1));
//			
//			iOSXCUITestElement.printElement(ele);
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			
			ele.parent.sibilings.remove(sibilingOrder+1);
			
			traverse2AdvanceCompress4FormalPageLayoutModuleRepresentationModel(onlyOneSibiling, sibilingOrder);
		}
		else {
			for(int i = 0; i < len; i ++) {
				traverse2AdvanceCompress4FormalPageLayoutModuleRepresentationModel(ele.sibilings.get(i), i);
			}
		}
		
//		System.out.println("================= End S3: ");
	}
	
	
	
	
	
	
	
	
	
	
	
	//Tree Search additional opts
	public static boolean isContainSpecificElementType(iOSXCUITestElement ele, String elementName) {
		
		List<iOSXCUITestElement> allNodeSetofPageTree = new ArrayList<iOSXCUITestElement>();
		
		traverse2CollectAllNodes(ele, allNodeSetofPageTree);
		
		for(int i = 0; i < allNodeSetofPageTree.size(); i ++) {
			if(allNodeSetofPageTree.get(i).EHead.equals(elementName)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void traverse2CollectAllNodes(iOSXCUITestElement ele, List<iOSXCUITestElement> allNodeSetofPageTree) {
		allNodeSetofPageTree.add(ele);
		for(int i = 0 ; i < ele.sibilings.size(); i ++) {
			traverse2CollectAllNodes(ele.sibilings.get(i), allNodeSetofPageTree);
		}
	}
	
	public static void traverse2CollectLeafNodes(iOSXCUITestElement ele, List<iOSXCUITestElement> allNodeSetofPageTree) {
		if(ele.sibilings.size() == 0) {
			allNodeSetofPageTree.add(ele);
		}
		for(int i = 0 ; i < ele.sibilings.size(); i ++) {
			traverse2CollectLeafNodes(ele.sibilings.get(i), allNodeSetofPageTree);
		}
	}
	
	public static void traverse2CollectVisiableLeafNodes(iOSXCUITestElement ele, List<iOSXCUITestElement> allNodeSetofPageTree) {
		if((ele.sibilings.size() == 0) && (ele.EVisible.equals("true"))) {
			allNodeSetofPageTree.add(ele);
		}
		for(int i = 0 ; i < ele.sibilings.size(); i ++) {
			traverse2CollectVisiableLeafNodes(ele.sibilings.get(i), allNodeSetofPageTree);
		}
	}
	
	
	//Two tree nodes shared covering space calculate opts: ele1 > ele2
	//Another method: compare the two index and give a threshold
	public static double rateOfSharedCoveringSpaceInFatherContainer(iOSXCUITestElement ele1, iOSXCUITestElement ele2) {
		
		int x1_ele1 = ele1.Ex;
		int y1_ele1 = ele1.Ey;
		int x2_ele1 = ele1.Ex + ele1.Ewidth;
		int y2_ele1 = ele1.Ey + ele1.Eheight;
		
		int x1_ele2 = ele2.Ex;
		int y1_ele2 = ele2.Ey;
		int x2_ele2 = ele2.Ex + ele2.Ewidth;
		int y2_ele2 = ele2.Ey + ele2.Eheight;
		
		if(x1_ele1 > x2_ele2 || y1_ele1 > y2_ele2 || x2_ele1 < x1_ele2 || y2_ele1 < y1_ele2) {
			return 0;
		}
		
		int height = Math.min(x2_ele1, x2_ele2) - Math.max(x1_ele1, x1_ele2);
		int width = Math.min(y2_ele1, y2_ele2) - Math.max(y1_ele1, y1_ele2);
		
		double sharedOverlapArea = (double)(height*width);
		double fatherArea = (double)(ele1.Ewidth*ele1.Eheight);
		double rate = sharedOverlapArea/fatherArea;
		
		
		return rate;
	} 
	
	
	
}
