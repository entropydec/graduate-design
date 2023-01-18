package org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.PageSourceCodeUnderstanding;

import java.util.*;

import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.*;

public class OutlineLayoutRepresentationModel {
	
	public String modelCategory;// iOS or Android
	
	public int orignalPageWidth;
	public int orignalPageHeight;
	
	public String area;
	public int x1;
	public int y1;
	public int x2;
	public int y2;
	
	public String area_Normalize;
	public double x1_Normalize;
	public double y1_Normalize;
	public double x2_Normalize;
	public double y2_Normalize;
	
	public List<iOSXCUITestElement> iosSibilingChildrenList;
	public List<List<iOSXCUITestElement>> iosRankedSibilingChildrenList;
	
	public List<AndroidGUIElement> androidSibilingChildrenList;
	public List<List<AndroidGUIElement>> androidRankedSibilingChildrenList;
	
	
	
	public OutlineLayoutRepresentationModel(String modelCategory, iOSXCUITestElement iosRefindedPageStructure, AndroidGUIElement androidRefindedPageStructure, int pageWidth, int pageHeight) {
		this.modelCategory = modelCategory;
		
		this.orignalPageWidth = pageWidth;
		this.orignalPageHeight = pageHeight;
		
		if(this.modelCategory.equals("iOS")) {
			this.x1 = iosRefindedPageStructure.Ex;
			this.y1 = iosRefindedPageStructure.Ey;
			this.x2 = iosRefindedPageStructure.Ex + iosRefindedPageStructure.Ewidth;
			this.y2 = iosRefindedPageStructure.Ey + iosRefindedPageStructure.Eheight;
			//element list construction
			iosSibilingChildrenList = iosRefindedPageStructure.sibilings;
		}
		else {
			this.x1 = androidRefindedPageStructure.Ex1;
			this.y1 = androidRefindedPageStructure.Ey1;
			this.x2 = androidRefindedPageStructure.Ex2;
			this.y2 = androidRefindedPageStructure.Ey2;
			//element list construction
			androidSibilingChildrenList = androidRefindedPageStructure.sibilings;
		}
		
		
		this.area = "[" + this.x1 + "," + this.y1 + "][" + this.x2 + "," + this.y2 + "]";
		
		this.x1_Normalize = (double)((double)this.x1/(double)this.orignalPageWidth);
		this.y1_Normalize = (double)((double)this.y1/(double)this.orignalPageHeight);
		this.x2_Normalize = (double)((double)this.x2/(double)this.orignalPageWidth);
		this.y2_Normalize = (double)((double)this.y2/(double)this.orignalPageHeight);
		this.area_Normalize = "[" + this.x1_Normalize + "," + this.y1_Normalize + "][" + this.x2_Normalize + "," + this.y2_Normalize + "]";
		
		rankSibilings();
	}
	
	public void rankSibilings() {
		
		if(this.modelCategory.equals("iOS")) {
			
			List<List<iOSXCUITestElement>> currentiOSRankedSibilingChildrenList = new ArrayList<List<iOSXCUITestElement>>();
			
			List<iOSXCUITestElement> currentFirstLine = new ArrayList<iOSXCUITestElement>();
			for(int i = 0; i < this.iosSibilingChildrenList.size(); i ++) {
				currentFirstLine.add(this.iosSibilingChildrenList.get(i));
			}
			List<iOSXCUITestElement> rankedCurrentFirstLine = new ArrayList<iOSXCUITestElement>();
			while(currentFirstLine.size() > 0) {
				
				int len = currentFirstLine.size();
				int max_y2 = currentFirstLine.get(0).Ey + currentFirstLine.get(0).Eheight;
				int max_index = 0;
				for(int i = 1; i < len; i ++) {
					int temp_y2 = currentFirstLine.get(i).Ey + currentFirstLine.get(i).Eheight;
					if(temp_y2 > max_y2) {
						max_y2 = temp_y2;
						max_index = i;
					}
				}
				rankedCurrentFirstLine.add(currentFirstLine.get(max_index));
				currentFirstLine.remove(max_index);
			}
			
			
			List<iOSXCUITestElement> aNewLine = null;
			
			while(rankedCurrentFirstLine.size() > 0) {
				
				int lenDominateSet = 1;
				int dominateSetLabel = 0;
				for( ; lenDominateSet < rankedCurrentFirstLine.size(); lenDominateSet ++) {
					
					dominateSetLabel = 0;
					
					int min_y1_DS = rankedCurrentFirstLine.get(0).Ey;
//					int max_y2_DS = rankedCurrentFirstLine.get(0).Ey + rankedCurrentFirstLine.get(0).Eheight;
					for(int i = 1; i < lenDominateSet; i ++) {
						int min_y1_DS_temp = rankedCurrentFirstLine.get(i).Ey;
//						int max_y2_DS_temp = rankedCurrentFirstLine.get(i).Ey + rankedCurrentFirstLine.get(i).Eheight;
						
						if(min_y1_DS_temp < min_y1_DS) {
							min_y1_DS = min_y1_DS_temp;
						}
//						if(max_y2_DS_temp > max_y2_DS) {
//							max_y2_DS = max_y2_DS_temp;
//						}
					}
					
					for(int i = lenDominateSet; i < rankedCurrentFirstLine.size(); i ++) {
//						int y1_DS_temp = rankedCurrentFirstLine.get(i).Ey;
						int y2_DS_temp = rankedCurrentFirstLine.get(i).Ey + rankedCurrentFirstLine.get(i).Eheight;
						
						if(min_y1_DS >= y2_DS_temp) {
							dominateSetLabel = 1;
							break;
						}
					}
					
					if(dominateSetLabel == 1) {
						aNewLine = new ArrayList<iOSXCUITestElement>();
						for(int j = 0; j < lenDominateSet; j ++) {
							aNewLine.add(rankedCurrentFirstLine.get(j));
							
						}
						while(lenDominateSet > 0) {
							rankedCurrentFirstLine.remove(0);
							lenDominateSet --;
						}
						currentiOSRankedSibilingChildrenList.add(aNewLine);
						break;
					}
					else {
						lenDominateSet ++;
					}
				}
				
				
				if(dominateSetLabel == 0) {
					currentiOSRankedSibilingChildrenList.add(rankedCurrentFirstLine);
					break;
				}
				
			}
			
			
			List<List<iOSXCUITestElement>> currentiOSRankedSibilingChildrenList_ = new ArrayList<List<iOSXCUITestElement>>();
			for(int i = 0; i < currentiOSRankedSibilingChildrenList.size(); i ++) {
				currentiOSRankedSibilingChildrenList_.add(currentiOSRankedSibilingChildrenList.get(i));
			}
			
			this.iosRankedSibilingChildrenList = currentiOSRankedSibilingChildrenList_;
			
			
			
			
			
		}
		else {
			//this.modelCategory.equals("Android")
			
			List<List<AndroidGUIElement>> currentAndroidRankedSibilingChildrenList = new ArrayList<List<AndroidGUIElement>>();
			List<AndroidGUIElement> currentFirstLine = new ArrayList<AndroidGUIElement>();
			for(int i = 0; i < this.androidSibilingChildrenList.size(); i ++) {
				currentFirstLine.add(this.androidSibilingChildrenList.get(i));
			}
			List<AndroidGUIElement> rankedCurrentFirstLine = new ArrayList<AndroidGUIElement>();
			while(currentFirstLine.size() > 0) {
				
				int len = currentFirstLine.size();
				int max_y2 = currentFirstLine.get(0).Ey2;
				int max_index = 0;
				for(int i = 1; i < len; i ++) {
					int temp_y2 = currentFirstLine.get(i).Ey2;
					if(temp_y2 > max_y2) {
						max_y2 = temp_y2;
						max_index = i;
					}
				}
				rankedCurrentFirstLine.add(currentFirstLine.get(max_index));
				currentFirstLine.remove(max_index);
			}
			
			List<AndroidGUIElement> aNewLine = null;
			
			while(rankedCurrentFirstLine.size() > 0) {
				
				int lenDominateSet = 1;
				int dominateSetLabel = 0;
				for( ; lenDominateSet < rankedCurrentFirstLine.size(); lenDominateSet ++) {
					
					dominateSetLabel = 0;
					
					int min_y1_DS = rankedCurrentFirstLine.get(0).Ey1;
//					int max_y2_DS = rankedCurrentFirstLine.get(0).Ey + rankedCurrentFirstLine.get(0).Eheight;
					for(int i = 1; i < lenDominateSet; i ++) {
						int min_y1_DS_temp = rankedCurrentFirstLine.get(i).Ey1;
//						int max_y2_DS_temp = rankedCurrentFirstLine.get(i).Ey + rankedCurrentFirstLine.get(i).Eheight;
						
						if(min_y1_DS_temp < min_y1_DS) {
							min_y1_DS = min_y1_DS_temp;
						}
//						if(max_y2_DS_temp > max_y2_DS) {
//							max_y2_DS = max_y2_DS_temp;
//						}
					}
					
					for(int i = lenDominateSet; i < rankedCurrentFirstLine.size(); i ++) {
//						int y1_DS_temp = rankedCurrentFirstLine.get(i).Ey;
						int y2_DS_temp = rankedCurrentFirstLine.get(i).Ey2;
						
						if(min_y1_DS >= y2_DS_temp) {
							dominateSetLabel = 1;
							break;
						}
					}
					
					if(dominateSetLabel == 1) {
						aNewLine = new ArrayList<AndroidGUIElement>();
						for(int j = 0; j < lenDominateSet; j ++) {
							aNewLine.add(rankedCurrentFirstLine.get(j));
							
						}
						while(lenDominateSet > 0) {
							rankedCurrentFirstLine.remove(0);
							lenDominateSet --;
						}
						currentAndroidRankedSibilingChildrenList.add(aNewLine);
						break;
					}
					else {
						lenDominateSet ++;
					}
				}
				
				
				if(dominateSetLabel == 0) {
					currentAndroidRankedSibilingChildrenList.add(rankedCurrentFirstLine);
					break;
				}
				
			}
			
			List<List<AndroidGUIElement>> currentAndroidRankedSibilingChildrenList_ = new ArrayList<List<AndroidGUIElement>>();
			for(int i = 0; i < currentAndroidRankedSibilingChildrenList.size(); i ++) {
				currentAndroidRankedSibilingChildrenList_.add(currentAndroidRankedSibilingChildrenList.get(i));
			}
			
			this.androidRankedSibilingChildrenList = currentAndroidRankedSibilingChildrenList_;
			
			
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
}
