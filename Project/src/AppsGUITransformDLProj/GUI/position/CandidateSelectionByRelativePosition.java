package AppsGUITransformDLProj.GUI.position;

import java.util.List;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import AppsGUITransformDLProj.GUI.iOSXCUITestElement;
import AppsGUITransformDLProj.util.IOSElement2AndroidElementRelation;

public class CandidateSelectionByRelativePosition {
	
	public static boolean isCandidate_Rule_equal_RelativePosition_sequence(iOSXCUITestElement iosElement, AndroidGUIElement androidElement, List<IOSElement2AndroidElementRelation> anchorRelationList) {
		
		int iosEleLineNO = iosElement.lineNumber;
		int androidEleLineNO = androidElement.lineNumber;
		
		String iosRelativeLocation = "";
		String androidRelativeLocation = "";
		
		int len = anchorRelationList.size();
		
		for(int i = 0; i < len; i ++) {
			
			IOSElement2AndroidElementRelation ios2androidERTemp = anchorRelationList.get(i);
			
			iOSXCUITestElement iosAnchor = ios2androidERTemp.iOSPageElement;
			AndroidGUIElement androidAnchor = ios2androidERTemp.androidPageElement;
			
			if( iosEleLineNO < iosAnchor.lineNumber) {
				iosRelativeLocation = iosRelativeLocation + "0";
			}
			else {
				iosRelativeLocation = iosRelativeLocation + "1";
			}
			
			if( androidEleLineNO < androidAnchor.lineNumber) {
				androidRelativeLocation = androidRelativeLocation + "0";
			}
			else {
				androidRelativeLocation = androidRelativeLocation + "1";
			}
			
		}
		
//		if(iosRelativeLocation.equals(androidRelativeLocation)) {
//			return true;
//		}
		
		if(compareRelativeLocationTypeString(iosRelativeLocation, androidRelativeLocation, 4)) {
			return true;
		}
		
		return false;
	}
	
	
	public static boolean isCandidate_Rule_equal_RelativePosition_sequence_(iOSXCUITestElement iosElement, AndroidGUIElement androidElement, List<IOSElement2AndroidElementRelation> anchorRelationList) {
		
		
		int iosX1 = iosElement.Ex;
		int iosY1 = iosElement.Ey;
		int iosX2 = (iosElement.Ex + iosElement.Ewidth);
		int iosY2 = (iosElement.Ey + iosElement.Eheight);
		
		int androidX1 = androidElement.Ex1;
		int androidY1 = androidElement.Ey1;
		int androidX2 = androidElement.Ex2;
		int androidY2 = androidElement.Ey2;
		
		
		String iosRelativeLocation = "";
		String androidRelativeLocation = "";
		
		int len = anchorRelationList.size();
		
		for(int i = 0; i < len; i ++) {
			
			IOSElement2AndroidElementRelation ios2androidERTemp = anchorRelationList.get(i);
			
			iOSXCUITestElement iosAnchor = ios2androidERTemp.iOSPageElement;
			AndroidGUIElement androidAnchor = ios2androidERTemp.androidPageElement;
			
			int iosX1_ = iosAnchor.Ex;
			int iosY1_ = iosAnchor.Ey;
			int iosX2_ = (iosAnchor.Ex + iosAnchor.Ewidth);
			int iosY2_ = (iosAnchor.Ey + iosAnchor.Eheight);
			
			int androidX1_ = androidAnchor.Ex1;
			int androidY1_ = androidAnchor.Ey1;
			int androidX2_ = androidAnchor.Ex2;
			int androidY2_ = androidAnchor.Ey2;
			
			if( iosX1 < iosX1_) {
				if(iosY1 < iosY1_) {
					iosRelativeLocation = iosRelativeLocation + "2";
				}
				else {
					iosRelativeLocation = iosRelativeLocation + "4";
				}
				
			}
			else {
				if(iosY1 < iosY1_) {
					iosRelativeLocation = iosRelativeLocation + "1";
				}
				else {
					iosRelativeLocation = iosRelativeLocation + "0";
				}
			}
			
			if( androidX1 < androidX1_) {
				if(androidY1 < androidY1_) {
					androidRelativeLocation = androidRelativeLocation + "2";
				}
				else {
					androidRelativeLocation = androidRelativeLocation + "4";
				}
				
			}
			else {
				if(androidY1 < androidY1_) {
					androidRelativeLocation = androidRelativeLocation + "1";
				}
				else {
					androidRelativeLocation = androidRelativeLocation + "0";
				}
			}
			
			
		}
		
//		if(iosRelativeLocation.equals(androidRelativeLocation)) {
//			return true;
//		}
		
//		if(compareRelativeLocationTypeString(iosRelativeLocation, androidRelativeLocation, 2)) {
//			return true;
//		}
		
//		if(compareRelativeLocationTypeString_percent(iosRelativeLocation, androidRelativeLocation, 0.001)) {
//			return true;
//		}
		
		if(compareRelativeLocationTypeString_hybird(iosRelativeLocation, androidRelativeLocation, 0, 0.5)) {
			return true;
		}
		
		
		return false;
	}
	
	
	public static boolean isCandidateRuleUsingRelativePosition(iOSXCUITestElement iosElement, AndroidGUIElement androidElement, List<IOSElement2AndroidElementRelation> anchorRelationList, int tolerentLength, double tolerentPercentLength) {
		
		
		int iosX1 = iosElement.Ex;
		int iosY1 = iosElement.Ey;
		int iosX2 = (iosElement.Ex + iosElement.Ewidth);
		int iosY2 = (iosElement.Ey + iosElement.Eheight);
		
		int androidX1 = androidElement.Ex1;
		int androidY1 = androidElement.Ey1;
		int androidX2 = androidElement.Ex2;
		int androidY2 = androidElement.Ey2;
		
		
		String iosRelativeLocation = "";
		String androidRelativeLocation = "";
		
		int len = anchorRelationList.size();
		
		for(int i = 0; i < len; i ++) {
			
			IOSElement2AndroidElementRelation ios2androidERTemp = anchorRelationList.get(i);
			
			iOSXCUITestElement iosAnchor = ios2androidERTemp.iOSPageElement;
			AndroidGUIElement androidAnchor = ios2androidERTemp.androidPageElement;
			
			int iosX1_ = iosAnchor.Ex;
			int iosY1_ = iosAnchor.Ey;
			int iosX2_ = (iosAnchor.Ex + iosAnchor.Ewidth);
			int iosY2_ = (iosAnchor.Ey + iosAnchor.Eheight);
			
			int androidX1_ = androidAnchor.Ex1;
			int androidY1_ = androidAnchor.Ey1;
			int androidX2_ = androidAnchor.Ex2;
			int androidY2_ = androidAnchor.Ey2;
			
			if( iosX1 < iosX1_) {
				if(iosY1 < iosY1_) {
					iosRelativeLocation = iosRelativeLocation + "2";
				}
				else {
					iosRelativeLocation = iosRelativeLocation + "4";
				}
				
			}
			else {
				if(iosY1 < iosY1_) {
					iosRelativeLocation = iosRelativeLocation + "1";
				}
				else {
					iosRelativeLocation = iosRelativeLocation + "0";
				}
			}
			
			if( androidX1 < androidX1_) {
				if(androidY1 < androidY1_) {
					androidRelativeLocation = androidRelativeLocation + "2";
				}
				else {
					androidRelativeLocation = androidRelativeLocation + "4";
				}
				
			}
			else {
				if(androidY1 < androidY1_) {
					androidRelativeLocation = androidRelativeLocation + "1";
				}
				else {
					androidRelativeLocation = androidRelativeLocation + "0";
				}
			}
			
			
		}
		
//		if(iosRelativeLocation.equals(androidRelativeLocation)) {
//			return true;
//		}
		
//		if(compareRelativeLocationTypeString(iosRelativeLocation, androidRelativeLocation, 2)) {
//			return true;
//		}
		
//		if(compareRelativeLocationTypeString_percent(iosRelativeLocation, androidRelativeLocation, 0.001)) {
//			return true;
//		}
		
//		if(compareRelativeLocationTypeString_hybird(iosRelativeLocation, androidRelativeLocation, 0, 0.5)) {
//			return true;
//		}
		
		if(compareRelativeLocationTypeString_hybird(iosRelativeLocation, androidRelativeLocation, tolerentLength, tolerentPercentLength)) {
			return true;
		}
		
		
		return false;
	}
	
	
	
	public static boolean compareRelativeLocationTypeString(String rL1, String rL2, int tolerentLength) {
		
		int lenInputString = rL1.length();
		
		if(lenInputString <= tolerentLength) {
			return true;
		}
		
		int lenDiffInput = 0;
		
		for(int i = 0 ; i < lenInputString; i ++) {
			
			if(rL1.charAt(i) != rL2.charAt(i)) {
				lenDiffInput ++;
			}
			
		}
		if(lenDiffInput <= tolerentLength) {
			return true;
		}
		
		return false;
	}
	
	public static boolean compareRelativeLocationTypeString_percent(String rL1, String rL2, double tolerentPercentLength) {
		
		int lenInputString = rL1.length();
		
		if(tolerentPercentLength == 1.0) {
			return true;
		}
		
		if(lenInputString == 0) {
			return true;
		}
		
		int lenDiffInput = 0;
		
		for(int i = 0 ; i < lenInputString; i ++) {
			
			if(rL1.charAt(i) != rL2.charAt(i)) {
				lenDiffInput ++;
			}
			
		}
		double inputPercent = lenDiffInput/lenInputString;
		if(inputPercent <= tolerentPercentLength) {
			return true;
		}
		
		return false;
	}
	
	public static boolean compareRelativeLocationTypeString_hybird(String rL1, String rL2, int tolerentLength, double tolerentPercentLength) {
		
		int lenInputString = rL1.length();
		
		
		if(lenInputString == 0) {
			return true;
		}
		
		int lenDiffInput = 0;
		
		for(int i = 0 ; i < lenInputString; i ++) {
			
			if(rL1.charAt(i) != rL2.charAt(i)) {
				lenDiffInput ++;
			}
			
		}
		
		if(tolerentPercentLength == 1.0) {
			return true;
		}
		
		if((new Integer(lenInputString)).doubleValue()*tolerentPercentLength <= (new Integer(tolerentLength)).doubleValue()) {
			if(lenDiffInput <= tolerentLength) {
				return true;
			}
		}
		else {
			double inputPercent = (new Integer(lenDiffInput)).doubleValue()/(new Integer(lenInputString)).doubleValue();
			if(inputPercent <= tolerentPercentLength) {
				return true;
			}
		}
		
		
		
		
		return false;
	}
	
}
