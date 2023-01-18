package AppsGUITransformDLProj.GUI;

import java.util.*;

import AppsGUITransformDLProj.GUI.position.*;

public class AndroidElementRelativePositionSetInPage {

	public AndroidGUIElement specifiedControl;
	public List<RelativePosition> relativePositionSet;
	public AndroidGUIPage specifiedPage;
	
	public AndroidElementRelativePositionSetInPage() {
		this.relativePositionSet = new ArrayList<RelativePosition>();
	}
	
	public void printAndroidELementRelativePositionSet() {
		System.out.print("* iOS element relative positions, absolute postion (<" + this.specifiedControl.Ex1 + "," 
				+ this.specifiedControl.Ey1 + "><" + this.specifiedControl.Ex2 + ","
						+ this.specifiedControl.Ey2 + ">) [");
				
				int len = relativePositionSet.size();
				for(int i = 0; i < len; i ++) {
					System.out.print("<" + this.relativePositionSet.get(i).realtivePosition.x + "," + this.relativePositionSet.get(i).realtivePosition.y + "> ");
				}
				System.out.println("]");
	}
	
}
