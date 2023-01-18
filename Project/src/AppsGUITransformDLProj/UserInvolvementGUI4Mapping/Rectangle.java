package AppsGUITransformDLProj.UserInvolvementGUI4Mapping;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Notice!
 * Mark the Refernce from https://github.com/HansGerry/Painting
 * */

public class Rectangle extends Shape {
	
	public static final int INT_RECTANGLE_TOLERANT = 3; 
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(width));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}
	
	public static boolean inRectangleField(int x1, int y1, int x2, int y2, int pointX, int pointY) {
		
		if((x1 < x2)&&(y1 < y2)) {
			
			if( (
				(pointX < x2 + INT_RECTANGLE_TOLERANT) && (pointX > x1 - INT_RECTANGLE_TOLERANT) && (
					( (pointY < y1 + INT_RECTANGLE_TOLERANT)&&(pointY > y1 - INT_RECTANGLE_TOLERANT) ) || ( (pointY < y2 + INT_RECTANGLE_TOLERANT)&&(pointY > y2 - INT_RECTANGLE_TOLERANT) )
						)
				|| (pointY <= y2) && (pointY >= y1) && (
						( (pointX < x1 + INT_RECTANGLE_TOLERANT)&&(pointX > x1 - INT_RECTANGLE_TOLERANT) ) || ( (pointX < x2 + INT_RECTANGLE_TOLERANT)&&(pointX > x2 - INT_RECTANGLE_TOLERANT) )
						)
					) ){
				return true;
			}
			else {
				return false;
			}
			
		}
		else if((x1 < x2)&&(y1 > y2)) {
			
			if( (
					(pointX < x2 + INT_RECTANGLE_TOLERANT) && (pointX > x1 - INT_RECTANGLE_TOLERANT) && (
						( (pointY < y1 + INT_RECTANGLE_TOLERANT)&&(pointY > y1 - INT_RECTANGLE_TOLERANT) ) || ( (pointY < y2 + INT_RECTANGLE_TOLERANT)&&(pointY > y2 - INT_RECTANGLE_TOLERANT) )
							)
					|| (pointY <= y1) && (pointY >= y2) && (
							( (pointX < x1 + INT_RECTANGLE_TOLERANT)&&(pointX > x1 - INT_RECTANGLE_TOLERANT) ) || ( (pointX < x2 + INT_RECTANGLE_TOLERANT)&&(pointX > x2 - INT_RECTANGLE_TOLERANT) )
							)
						) ){
					return true;
				}
				else {
					return false;
				}
			
		}
		else if((x1 > x2)&&(y1 < y2)) {
			
			if( (
					(pointX < x1 + INT_RECTANGLE_TOLERANT) && (pointX > x2 - INT_RECTANGLE_TOLERANT) && (
						( (pointY < y1 + INT_RECTANGLE_TOLERANT)&&(pointY > y1 - INT_RECTANGLE_TOLERANT) ) || ( (pointY < y2 + INT_RECTANGLE_TOLERANT)&&(pointY > y2 - INT_RECTANGLE_TOLERANT) )
							)
					|| (pointY <= y2) && (pointY >= y1) && (
							( (pointX < x1 + INT_RECTANGLE_TOLERANT)&&(pointX > x1 - INT_RECTANGLE_TOLERANT) ) || ( (pointX < x2 + INT_RECTANGLE_TOLERANT)&&(pointX > x2 - INT_RECTANGLE_TOLERANT) )
							)
						) ){
					return true;
				}
				else {
					return false;
				}
			
			
		}
		else if((x1 > x2)&&(y1 > y2)) {
			
			if( (
					(pointX < x1 + INT_RECTANGLE_TOLERANT) && (pointX > x2 - INT_RECTANGLE_TOLERANT) && (
						( (pointY < y1 + INT_RECTANGLE_TOLERANT)&&(pointY > y1 - INT_RECTANGLE_TOLERANT) ) || ( (pointY < y2 + INT_RECTANGLE_TOLERANT)&&(pointY > y2 - INT_RECTANGLE_TOLERANT) )
							)
					|| (pointY <= y1) && (pointY >= y2) && (
							( (pointX < x1 + INT_RECTANGLE_TOLERANT)&&(pointX > x1 - INT_RECTANGLE_TOLERANT) ) || ( (pointX < x2 + INT_RECTANGLE_TOLERANT)&&(pointX > x2 - INT_RECTANGLE_TOLERANT) )
							)
						) ){
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
