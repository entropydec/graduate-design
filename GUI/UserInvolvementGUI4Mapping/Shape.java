package org.ruihua.GUITrans.AppsGUITransformDLProj.UserInvolvementGUI4Mapping;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.*;

import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.AndroidGUIElement;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.iOSXCUITestElement;

/**
 * Notice!
 * Mark the Refernce from https://github.com/HansGerry/Painting
 * */

public abstract class Shape {
	
	public int x1, y1, x2, y2;
	public Color color;
	public int width;
	
	public int currentChoice;
	public int length;
	public BufferedImage image;
	public JPanel board;
	
	//find the target e2e mapping relation for removing line (relation), also work when the rectangle is in a relation
	public int elementOrRelationIndex;
	//only for line (relation)
	public Shape startControl;
	public Shape endControl;
	//only for rectangle (control)
	public Shape linkRelationLine;
	//whether it is in relation for any rectangle
	public boolean inRelation;
	//This will be used when the rectangle is not in a relation, and this is only work for rectangles.
	public int typeiOSorAndroid;
	
	
	public static final int INT_IOS_ELEMENT = 0;
	public static final int INT_ANDROID_ELEMENT = 1;
	
	
	public abstract void draw(Graphics2D g);
}
