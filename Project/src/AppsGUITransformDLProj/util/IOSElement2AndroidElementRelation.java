package AppsGUITransformDLProj.util;

import AppsGUITransformDLProj.GUI.*;

public class IOSElement2AndroidElementRelation {
	
	public iOSXCUITestElement iOSPageElement;
	public AndroidGUIElement androidPageElement;
	
	public String textSimilarity;
	
	public double overlapIntersection;
	
	public double overlapUnionSet;
	
	
	public void printRelation() {
		System.out.println(
				"<" + iOSPageElement.EHead + "[" + iOSPageElement.locationTypeOrder + "], "
				+ androidPageElement.EHead + "[" + androidPageElement.locationTypeOrder + "](Comparison Text: " + androidPageElement.EText + ")>, "
				+ "textSimilarity: " + textSimilarity
				+ ", overlapIntersection: " + overlapIntersection
				+ ", overlapUnionSet: " + overlapUnionSet
				);
	}
	
}
