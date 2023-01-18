package AppsGUITransformDLProj.UserInvolvementGUI4Mapping;

import java.awt.BasicStroke;
import java.awt.Graphics2D;



public class Line extends Shape {
	
	public static final double DOUBLE_TOLERANT = 10;
	public static final int INT_START_AND_END_TOLERANT = 5;
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.setBackground(color);
		g.setStroke(new BasicStroke(width));
		g.drawLine(x1, y1, x2, y2);
	}
	
	public static boolean inLineField(int x1, int y1, int x2, int y2, int pointX, int pointY) {
		
		/**
		 * If x1 == x2 and y1 == y2, then the line fail in creation?
		 * */
		
		
//		System.out.println("Trigger func:inLineField");
		
		if((x1 > x2)&&((pointX < (x2 - INT_START_AND_END_TOLERANT))||(pointX > (x1 + INT_START_AND_END_TOLERANT)))) {
			return false;
		}
		if((x1 < x2)&&((pointX > (x2 + INT_START_AND_END_TOLERANT))||(pointX < (x1 - INT_START_AND_END_TOLERANT)))) {
			return false;
		}
		if((y1 > y2)&&((pointY < (y2 - INT_START_AND_END_TOLERANT))||(pointY > (y1 + INT_START_AND_END_TOLERANT)))) {
			return false;
		}
		if((y1 < y2)&&((pointY > (y2 + INT_START_AND_END_TOLERANT))||(pointY < (y1 - INT_START_AND_END_TOLERANT)))) {
			return false;
		}
		
		
		System.out.println(x1 + "," + y1 + "," + x2 + "," + y2);
		double kRate = 0;
		
		if(x2 != x1) {
			kRate = (new Integer(y2 - y1)).doubleValue()/(new Integer(x2 - x1)).doubleValue();
			
			double yPointStandard = (new Integer(y1)).doubleValue() + (new Integer(pointX - x1)).doubleValue()*kRate;
			
			double yDistance = Math.abs((new Integer(pointY)).doubleValue() - yPointStandard);
			
			System.out.println("Distance: " + kRate + "," + yPointStandard + "," + yDistance);
			
			if(yDistance <= DOUBLE_TOLERANT) {
				return true;
			}
			else {
				return false;
			}
			
		}
		else {
			System.out.println("++++++ x2 == x1 ++++++");
			
			if((pointX <= (x1 + INT_START_AND_END_TOLERANT)) && (pointX >= (x1 - INT_START_AND_END_TOLERANT))) {
				
				if(((y1 > y2)&&(pointY >= y2)&&(pointY <= y1))||((y1 <= y2)&&(pointY >= y1)&&(pointY <= y2)) ) {
					return true;
				}
				else {
					return false;
				}
				
			}
			else {
				return false;
			}
			
		}
		
		
		
		
	}
}
