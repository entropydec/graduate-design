package AppsGUITransformDLProj.UserInvolvementGUI4Mapping;

import java.awt.Graphics2D;

/**
 * Notice!
 * Mark the Refernce from https://github.com/HansGerry/Painting
 * */

public class Images extends Shape {

	public void draw(Graphics2D g) {
		g.drawImage(image, x1, y1, board);
	}
}
