package AppsGUITransformDLProj.GUI.position;

public class RelativePosition {
	
	public BasicPosition realtivePosition;
	public BasicPosition referencePosition;
	
	public RelativePosition() {
		this.realtivePosition = new BasicPosition();
		this.referencePosition = new BasicPosition();
	}
	
	public RelativePosition(double realtivePositionX, double realtivePositionY){
		this.realtivePosition = new BasicPosition();
		this.referencePosition = new BasicPosition();
		this.realtivePosition.x = realtivePositionX;
		this.realtivePosition.y = realtivePositionY;
	}
}
