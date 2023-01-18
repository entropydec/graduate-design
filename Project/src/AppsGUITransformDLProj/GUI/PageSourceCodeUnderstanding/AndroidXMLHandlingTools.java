package AppsGUITransformDLProj.GUI.PageSourceCodeUnderstanding;

import java.util.ArrayList;
import java.util.List;

import AppsGUITransformDLProj.GUI.*;

public class AndroidXMLHandlingTools {
	
	/**
	 * Remove the useless leaf node: 
	 * 1.the background node, which is a leaf node and share page space with the other container node
	 * Currently, every level of a tree we have one background node, and it is the first one confrortable with the overlap rule
	 * 2. width = 0 or 1, height = 0 or 1
	 * */
	
	public static void androidXMLSourceCodeRemoveUselessLeafNodeEXBackgroundNode(AndroidGUIElement mainSubtree) {
		
		traverse2RemoveUselessLeafNode(mainSubtree);
		traverse2RemoveUselessLeafNode_Not2DElements(mainSubtree);
		traverse2RemoveUselessLeafNode_WholePageBackgroudElements(mainSubtree
				, Math.abs(mainSubtree.Ex2 - mainSubtree.Ex1)
				, Math.abs(mainSubtree.Ey2 - mainSubtree.Ey1)
				);//everytime remove a leaf may create empty subtree whitch is a new leaf (such leaf is a whole page element)
		
	}
	
	//Container Background
	public static void traverse2RemoveUselessLeafNode(AndroidGUIElement ele) {
		
		int index = 0;
		for(; index < ele.sibilings.size(); index ++) {
			if(isUselessBackgroundNode(ele.sibilings.get(index))) {
				break;
			}
		}
		
		if(index < ele.sibilings.size()) {
			ele.sibilings.remove(index);
		}
		for(int i = 0 ; i < ele.sibilings.size(); i ++) {
			traverse2RemoveUselessLeafNode(ele.sibilings.get(i));
		}
		
	}
	public static boolean isUselessBackgroundNode(AndroidGUIElement ele) {
		
		if(ele.sibilings.size() != 0) {//a container node
			return false;
		}
		//A leaf node
		AndroidGUIElement eleFatherNode = ele.parent;
		List<AndroidGUIElement> fathersSibilings = eleFatherNode.sibilings;
		if(fathersSibilings.size() > 1) {
			
			double sharedRate = rateOfSharedCoveringSpaceInFatherContainer(eleFatherNode, ele);
			if(sharedRate > 0.99) {
				return true;
			}
			
		}
		
		
		return false;
	}
	
	//Not 2D Element
	public static void traverse2RemoveUselessLeafNode_Not2DElements(AndroidGUIElement ele) {
		List<AndroidGUIElement> newSibilings = new ArrayList<AndroidGUIElement>();
		
		for(int i = 0; i < ele.sibilings.size(); i ++) {
			if(is2DElement(ele.sibilings.get(i))) {
				newSibilings.add(ele.sibilings.get(i));
				
			}
			else {
				//remove (here, the node must a leaf node)
			}
		}
		
		ele.sibilings = newSibilings;
		for(int i = 0 ; i < ele.sibilings.size(); i ++) {
			traverse2RemoveUselessLeafNode_Not2DElements(ele.sibilings.get(i));
		}
		
	}
	public static boolean is2DElement(AndroidGUIElement ele) {
		int wi = Math.abs(ele.Ex2 - ele.Ex1);
		int he = Math.abs(ele.Ey2 - ele.Ey1);
		if(wi == 0 || wi == 1 || he == 0 || he == 1) {
			return false;
		}
		return true;
	}
	
	//WholePageBackgroud
	public static void traverse2RemoveUselessLeafNode_WholePageBackgroudElements(AndroidGUIElement ele, int W, int H) {
		
		List<AndroidGUIElement> newSibilings = new ArrayList<AndroidGUIElement>();
		
		for(int i = 0; i < ele.sibilings.size(); i ++) {
			int wi = Math.abs(ele.sibilings.get(i).Ex2 - ele.sibilings.get(i).Ex1);
			int he = Math.abs(ele.sibilings.get(i).Ey2 - ele.sibilings.get(i).Ey1);
			if(ele.sibilings.get(i).sibilings.size() == 0 && wi == W && he == H) {
				
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
	
	public static void androidXMLSourceCodeCompressDuplicatedContainer(AndroidGUIElement mainSubtreeWithoutBackground) {
		
		traverse2CompressDuplicatedContainer(mainSubtreeWithoutBackground, 0);//its parents do not been used, so here use default 0
		
		if(mainSubtreeWithoutBackground.sibilings.size() == 0) {
			//do nothing
		}
		else if(mainSubtreeWithoutBackground.sibilings.size() == 1) {
			traverse2CompressDuplicatedContainer(mainSubtreeWithoutBackground.sibilings.get(0), 0);
			if(mainSubtreeWithoutBackground.sibilings.size() == 1) {
				mainSubtreeWithoutBackground.sibilings = mainSubtreeWithoutBackground.sibilings.get(0).sibilings;
			}
		}
		else {
			// > 1
			int lenNextSibilings = mainSubtreeWithoutBackground.sibilings.size();
			for(int i = 0; i < lenNextSibilings; i ++) {
				traverse2CompressDuplicatedContainer(mainSubtreeWithoutBackground.sibilings.get(i), i);
			}
		}
		
	}
	public static void traverse2CompressDuplicatedContainer(AndroidGUIElement ele, int sibilingOrder) {
		
//		System.out.println("=== trigger! ===");
		
		int len = ele.sibilings.size();
		if(len == 0) {}
		else if(len == 1) {
			
			AndroidGUIElement onlyOneSibiling = ele.sibilings.get(0);
			onlyOneSibiling.parent = ele.parent;
			ele.parent.sibilings.add(sibilingOrder, onlyOneSibiling);
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
	 * extract Layout UIProject.Module Page Representation Model from the pre-treated page structure tree
	 * */
	public static AndroidGUIElement extractLayoutModulePageRepresentationModel(AndroidGUIElement mainSubtreePreHandled) {
		
		OutlineLayoutRepresentationModel outlineLayoutRepresentationModel = new OutlineLayoutRepresentationModel(
				"Android", 
				null, 
				mainSubtreePreHandled, 
				(mainSubtreePreHandled.Ex2 - mainSubtreePreHandled.Ex1), 
				(mainSubtreePreHandled.Ey2 - mainSubtreePreHandled.Ey1)
				);
		
		AndroidGUIElement mainSubtreeCopy4RepresentationModel = mainSubtreePreHandled.copyElement();
		mainSubtreeCopy4RepresentationModel.sibilings.clear();
		for(int i = 0; i < outlineLayoutRepresentationModel.androidRankedSibilingChildrenList.size(); i ++) {
			
			//current line of nodes
			List<AndroidGUIElement> currentLineTemp = outlineLayoutRepresentationModel.androidRankedSibilingChildrenList.get(i);
			
			//construct a new container node for the ranked line nodes
			AndroidGUIElement virtualContainerTemp = new AndroidGUIElement();
			mainSubtreeCopy4RepresentationModel.sibilings.add(virtualContainerTemp);
			virtualContainerTemp.parent = mainSubtreeCopy4RepresentationModel;
			
			for(int j = 0 ; j < currentLineTemp.size(); j ++) {
				AndroidGUIElement virtualLineNodeTemp = currentLineTemp.get(j).copyElement();
				virtualContainerTemp.sibilings.add(virtualLineNodeTemp);
				virtualLineNodeTemp.parent = virtualContainerTemp;
				
				//Insert virtualLineNodeTemp's sibilings, and continue to break the multiline in the line module
				traverse2ExtractLayoutModulePageRepresentationModel(currentLineTemp.get(j), (mainSubtreePreHandled.Ex2 - mainSubtreePreHandled.Ex1), (mainSubtreePreHandled.Ey2 - mainSubtreePreHandled.Ey1), virtualLineNodeTemp);
				
			}
			
			
		}
		
		
		return mainSubtreeCopy4RepresentationModel;
		
	}
	
	public static void traverse2ExtractLayoutModulePageRepresentationModel(AndroidGUIElement eleOriginal, int W, int H, AndroidGUIElement eleVirtualNode) {
		
		if(eleOriginal.sibilings.size() == 0) {
			//leaf node
			//do nothing
		}
		else {
			OutlineLayoutRepresentationModel eleOriOutlineLayoutRepresentationModel = new OutlineLayoutRepresentationModel(
					"Android", 
					null, 
					eleOriginal, 
					W, 
					H
					);
			int len = eleOriOutlineLayoutRepresentationModel.androidRankedSibilingChildrenList.size();
			
			for(int i = 0; i < len; i ++) {
				//current line of nodes
				List<AndroidGUIElement> currentLineTemp = eleOriOutlineLayoutRepresentationModel.androidRankedSibilingChildrenList.get(i);
				//construct a new container node for the ranked line nodes
				AndroidGUIElement virtualContainerTemp = new AndroidGUIElement();
				eleVirtualNode.sibilings.add(virtualContainerTemp);
				virtualContainerTemp.parent = eleVirtualNode;
				for(int j = 0 ; j < currentLineTemp.size(); j ++) {
					AndroidGUIElement virtualLineNodeTemp = currentLineTemp.get(j).copyElement();
					virtualContainerTemp.sibilings.add(virtualLineNodeTemp);
					virtualLineNodeTemp.parent = virtualContainerTemp;
					
					//Insert virtualLineNodeTemp's sibilings, and continue to break the multiline in the line module
					traverse2ExtractLayoutModulePageRepresentationModel(currentLineTemp.get(j), W, H, virtualLineNodeTemp);
					
				}
				
			}
			
			
		}
		
		
	}
	
	/**
	 * ???
	 * Compress Advance: Go directly to leaf nodes
	 * # compress the Virtual Container: ?
	 * */
	public static void advanceCompress4FormalPageLayoutModuleRepresentationModel(AndroidGUIElement mainSubtreeWithoutBackground) {
		
		if(mainSubtreeWithoutBackground.sibilings.size() == 0) {
			//do nothing
		}
		else if(mainSubtreeWithoutBackground.sibilings.size() == 1) {
			
			traverse2AdvanceCompress4FormalPageLayoutModuleRepresentationModel(mainSubtreeWithoutBackground.sibilings.get(0), 0);
			
		}
		else {
			int lenNextSibilings = mainSubtreeWithoutBackground.sibilings.size();
			for(int i = 0; i < lenNextSibilings; i ++) {
				traverse2AdvanceCompress4FormalPageLayoutModuleRepresentationModel(mainSubtreeWithoutBackground.sibilings.get(i), i);
			}
		}
		
	}
	
	public static void traverse2AdvanceCompress4FormalPageLayoutModuleRepresentationModel(AndroidGUIElement ele, int sibilingOrder) {
		int len = ele.sibilings.size();
		if(len == 0) {
			//do nothing
		}
		else if(len == 1) {
			AndroidGUIElement onlyOneSibiling = ele.sibilings.get(0);
			onlyOneSibiling.parent = ele.parent;
			ele.parent.sibilings.add(sibilingOrder, onlyOneSibiling);
			
			ele.parent.sibilings.remove(sibilingOrder+1);
			traverse2AdvanceCompress4FormalPageLayoutModuleRepresentationModel(onlyOneSibiling, sibilingOrder);
			
		}
		else {
			for(int i = 0; i < len; i ++) {
				traverse2AdvanceCompress4FormalPageLayoutModuleRepresentationModel(ele.sibilings.get(i), i);
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	//Tree Search additional opts
	
	public static void traverse2CollectAllNodes(AndroidGUIElement ele, List<AndroidGUIElement> allNodeSetofPageTree) {
		allNodeSetofPageTree.add(ele);
		for(int i = 0 ; i < ele.sibilings.size(); i ++) {
			traverse2CollectAllNodes(ele.sibilings.get(i), allNodeSetofPageTree);
		}
	}
	
	public static void traverse2CollectLeafNodes(AndroidGUIElement ele, List<AndroidGUIElement> allNodeSetofPageTree) {
		if(ele.sibilings.size() == 0) {
			allNodeSetofPageTree.add(ele);
		}
		for(int i = 0 ; i < ele.sibilings.size(); i ++) {
			traverse2CollectLeafNodes(ele.sibilings.get(i), allNodeSetofPageTree);
		}
	}
	
	
	
	//Two tree nodes shared covering space calculate opts: ele1 > ele2
	//Another method: compare the two index and give a threshold
	public static double rateOfSharedCoveringSpaceInFatherContainer(AndroidGUIElement ele1, AndroidGUIElement ele2) {
		
		int x1_ele1 = ele1.Ex1;
		int y1_ele1 = ele1.Ey1;
		int x2_ele1 = ele1.Ex2;
		int y2_ele1 = ele1.Ey2;
		
		int x1_ele2 = ele2.Ex1;
		int y1_ele2 = ele2.Ey1;
		int x2_ele2 = ele2.Ex2;
		int y2_ele2 = ele2.Ey2;
		
		if(x1_ele1 > x2_ele2 || y1_ele1 > y2_ele2 || x2_ele1 < x1_ele2 || y2_ele1 < y1_ele2) {
			return 0;
		}
		
		int height = Math.min(x2_ele1, x2_ele2) - Math.max(x1_ele1, x1_ele2);
		int width = Math.min(y2_ele1, y2_ele2) - Math.max(y1_ele1, y1_ele2);
		
		double sharedOverlapArea = (double)(height*width);
		double fatherArea = (double)((x2_ele1-x1_ele1)*(y2_ele1-y1_ele1));
		double rate = sharedOverlapArea/fatherArea;
		
		return rate;
	}

}
