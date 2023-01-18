package org.ruihua.GUITrans.AppsGUITransformDLProj.util;

import java.util.*;

import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.AndroidElementRelativePositionSetInPage;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.IOSElementRelativePositionSetInPage;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.position.RelativePosition;

public class iOS2AndroidUnmappedElementSetRelation {
	
	public String elementSetLabel;
	public List<IOSElementRelativePositionSetInPage> iOSElements;
	public List<Integer> iOSElementsIndex;
	public List<AndroidElementRelativePositionSetInPage> androidElements;
	public List<Integer> androidElementsIndex;
	
	public iOS2AndroidUnmappedElementSetRelation() {
		
		
		this.iOSElements = new ArrayList<IOSElementRelativePositionSetInPage>();
		this.androidElements = new ArrayList<AndroidElementRelativePositionSetInPage>();
		this.iOSElementsIndex = new ArrayList<Integer>();
		this.androidElementsIndex = new ArrayList<Integer>();
	}
	
	public static String calculateElementSetLabel(List<RelativePosition> relativePositionList) {
		
		String resultlabel = "";
		
		int len = relativePositionList.size();
		
		for(int i = 0; i < len; i ++) {
			if( (relativePositionList.get(i).realtivePosition.x >= 0) && (relativePositionList.get(i).realtivePosition.y >= 0)) {
				resultlabel = resultlabel + 1;
			}
			else if( (relativePositionList.get(i).realtivePosition.x >= 0) && (relativePositionList.get(i).realtivePosition.y < 0)) {
				resultlabel = resultlabel + 2;
			}
			else if( (relativePositionList.get(i).realtivePosition.x < 0) && (relativePositionList.get(i).realtivePosition.y >= 0)) {
				resultlabel = resultlabel + 3;
			}
			else if( (relativePositionList.get(i).realtivePosition.x < 0) && (relativePositionList.get(i).realtivePosition.y < 0)) {
				resultlabel = resultlabel + 4;
			}
		}
		
		
		return resultlabel;
	}
	/*
	public static void main(String[] args) {
		
		String r = "";
		
		r = r + 1;
		System.out.println(r);
		
		r = r + 1;
		System.out.println(r);
		
	}
	*/
	
}
