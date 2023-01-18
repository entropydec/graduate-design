package AppsGUITransformDLProj.GUI;

import java.util.*;

import AppsGUITransformDLProj.GUI.position.*;

public class IOSElementRelativePositionSetInPage {
	
	public iOSXCUITestElement specifiedControl;
	public List<RelativePosition> relativePositionSet;
	public iOSXCUITestPage specifiedPage;
	
	public IOSElementRelativePositionSetInPage() {
		//this.specificControl = new iOSXCUITestElement();
		this.relativePositionSet = new ArrayList<RelativePosition>();
		//this.specificPage = new iOSXCUITestPage();
	}
	
	public void printIOSELementRelativePositionSet() {
		System.out.print("* iOS element relative positions, absolute postion (<" + this.specifiedControl.Ex + "," 
		+ this.specifiedControl.Ey + "><" + (this.specifiedControl.Ex + this.specifiedControl.Ewidth) + ","
				+ (this.specifiedControl.Ey + this.specifiedControl.Eheight) + ">) [");
		
		int len = relativePositionSet.size();
		for(int i = 0; i < len; i ++) {
			System.out.print("<" + this.relativePositionSet.get(i).realtivePosition.x + "," + this.relativePositionSet.get(i).realtivePosition.y + "> ");
		}
		System.out.println("]");
	}
	
}
