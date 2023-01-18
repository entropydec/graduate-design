package AppsGUITransformDLProj.UserInvolvementGUI4Mapping;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import AppsGUITransformDLProj.GUI.iOSXCUITestElement;
import AppsGUITransformDLProj.util.DataFileWrite;
import AppsGUITransformDLProj.util.IOSElement2AndroidElementRelation;

/**
 * Notice!
 * Mark the Refernce from https://github.com/HansGerry/Painting
 * */

public class MappingRelationDisplayFrame4SupportCheckSceenShotComparison extends JFrame {
	
	
//	public static Color c = Color.black;
	
	public Graphics2D g;
	
//	public static int index = 0;
//	public static Shape[] itemList = new Shape[5000];
	
	public static int index = 0;
	public static List<Shape> itemList = new ArrayList<Shape>();
	
	private DisplayPanel displayArea;
	
	
	public static int stroke = 1;
	public static Color color = Color.black;
	public static int currentChoice = 20;
	
	
	private static int frameWidth = 1260;
	private static int frameHeight = 520;
	private static final String focusedLabelName = "NodePIC.png";
	private static Shape focusedLine;
	private static int focusedLineIndex = -1;
	
	private JPopupMenu rightButtonMenu;
	private JMenu topFuncMenu;
	
	private JTextField textField;
	private JButton button;
	private JButton button_next;
	private JButton button_last;
	
	public static final int INT_GAP_BETWEEN_TWO_PICTURES = 10;
	
	public static int lastiOSPage;//only use for locating the controls
	public static int lastAndroidPage;//only use for locating the controls
	public static int lastAndroidPage_0;
	public static int lastAndroidPage_1;
	public static int lastAndroidPage_2;
	public static int lastAndroidPage_3;
	public static int lastAndroidPage_4;
	
	public StartAssistantUI mappingRelationCheckerInvolveUser;//Note! This member crossed with this class, so that the two classes can crossover.
	
	public static boolean isMappedControlsLoaded = false;
	
	public static final int INT_IOS_GUI_CONTROL = 0;
	public static final int INT_ANDROID_GUI_CONTROL = 1;
	
	public static int saved = 1;//indicate whether the current infomation showed in the frame is saved.
	
	public static int startRectangleIndex;
	public static int endRectangleIndex;
	
	public static int currentRectangleIndex = -1;
	
	public List<String> easyTestSetWithoutLabel;
	public List<String> easyTestSetWithLabel;
	public String easyTestCaseSetIndexFileWithLabelinProcess;
	
	public static int currentControlMappingRelation = -1;
	public static int currentControlMappingRelationinOnePage = -1;
	
	
	public MappingRelationDisplayFrame4SupportCheckSceenShotComparison(String s
//			, List<String> easyTestSetDataWithoutLabel, List<String> easyTestSetDataWithLabel, String eTestCaseSetIndexFileWithLabelinProcess
			) {
		init(s);
//		this.easyTestSetWithoutLabel = easyTestSetDataWithoutLabel;
//		this.easyTestSetWithLabel = easyTestSetDataWithLabel;
//		this.easyTestCaseSetIndexFileWithLabelinProcess = eTestCaseSetIndexFileWithLabelinProcess;
//		this.mappingRelationCheckerInvolveUser.currentPageRelationIndex ++;
		this.setVisible(true);
	}
	
	
	public void init(String s) {
		
		this.setTitle(s);
		this.setSize(frameWidth, frameHeight);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		this.setResizable(false);
		
		this.setLayout(null);
		
		displayArea = new DisplayPanel();
		displayArea.setBounds(0, 20, 1260, 500);
		this.add(displayArea);
		
		
		
		button_next = new JButton("Next");
		initNextButton();
		
		button_last = new JButton("Last");
		initLastButton();
		
		textField = new JTextField(10);
		initInputText();
		
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
				
			}
			
		});
		
//		displayArea.createNewGraphics();
		
		
	}
	
	public void initInputText() {
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(null);
		
		textField.setBounds(0, 0, 1000, 20);
		inputPanel.add(textField);
		
		
		inputPanel.setBounds(200, 0, 1000, 20);
		this.add(inputPanel);
	}
	
	public void initInputButton() {
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(null);
		
		button.setBounds(0, 0, 100, 20);
		inputPanel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sure Button Click");
				String str = textField.getText();
				if(!str.equals("")) {
					
					updateEasyTestSetWithLabel(str);
					
				}
				
				
				textField.setText("");
				repaint();
				
				
			}
		});
		
		
		inputPanel.setBounds(900, 0, 100, 20);
		this.add(inputPanel);
	}
	
	public void initNextButton() {
		
		JPanel nextButtonPanel = new JPanel();
		nextButtonPanel.setLayout(null);
		
		button_next.setBounds(0, 0, 100, 20);
		nextButtonPanel.add(button_next);
		button_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Next Button Click");
				
				String controlsInfo = mappingRelationCheckerInvolveUser.nextETC();
				textField.setText(controlsInfo);
				
				repaint();
				
				
			}
		});
		
		
		nextButtonPanel.setBounds(0, 0, 100, 20);
		this.add(nextButtonPanel);
		repaint();
	}
	public void initLastButton() {
		
		JPanel lastButtonPanel = new JPanel();
		lastButtonPanel.setLayout(null);
		
		button_last.setBounds(0, 0, 100, 20);
		lastButtonPanel.add(button_last);
		button_last.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Last Button Click");
				
				String controlsInfo = mappingRelationCheckerInvolveUser.lastETC();
				textField.setText(controlsInfo);
				
				repaint();
				
				
			}
		});
		
		
		lastButtonPanel.setBounds(100, 0, 100, 20);
		this.add(lastButtonPanel);
		repaint();
	}
	
	
	class ExistingDataFileFilter extends FileFilter {
		public boolean accept(File f) {
			//System.out.println("Mapping_Relation_Database: " + f.getName());
			
			if((f.isDirectory())&&(f.getName().contains("Mapping_Relation_Database"))) {
				return true;
			}
			else {
				return false;
			}
		}
		public String getDescription() {
			return "[Data File]";
		}
	}
	
	
	
	
	
	
	private boolean nextMappedControls() {
		
		int tempCurrentControlMappingRelationinOnePage = MappingRelationDisplayFrame4SupportCheckSceenShotComparison.currentControlMappingRelationinOnePage + 1;
		
		if(tempCurrentControlMappingRelationinOnePage < 
				this.mappingRelationCheckerInvolveUser
				.pageMappingRelations4AllAppsFromInitialFile
				.get(this.mappingRelationCheckerInvolveUser.currentAppIndex)
				.get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex)
				.matchElements.size()
				) {
			MappingRelationDisplayFrame4SupportCheckSceenShotComparison.currentControlMappingRelationinOnePage = tempCurrentControlMappingRelationinOnePage;
		}
		else {
			
			int tempCurrentPageRelationIndex = this.mappingRelationCheckerInvolveUser.currentPageRelationIndex + 1;
			
			if(tempCurrentPageRelationIndex < this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).size()) {
				this.mappingRelationCheckerInvolveUser.currentPageRelationIndex = tempCurrentPageRelationIndex;
				MappingRelationDisplayFrame4SupportCheckSceenShotComparison.currentControlMappingRelationinOnePage = 0;
			}
			else {
				int tempCurrentAppIndex = this.mappingRelationCheckerInvolveUser.currentAppIndex + 1;
				if(tempCurrentAppIndex < this.mappingRelationCheckerInvolveUser.allAppsInfo.size()) {
					this.mappingRelationCheckerInvolveUser.currentAppIndex = tempCurrentAppIndex;
					this.mappingRelationCheckerInvolveUser.currentPageRelationIndex = 0;
					MappingRelationDisplayFrame4SupportCheckSceenShotComparison.currentControlMappingRelationinOnePage = 0;
				}
				else {
					//!!!!!!!!!!!!!!!!!!!
					System.out.println("No more apps!");
					return false;
				}
			}
			
		}
		
		
		return true;
	}
	
	public void updateEasyTestSetWithLabel(String label) {
		String etcTemp = this.easyTestSetWithoutLabel.get(MappingRelationDisplayFrame4SupportCheckSceenShotComparison.currentControlMappingRelation);
		String[] src = etcTemp.split("\n");
		String temp = src[0] + " " + label;
		this.easyTestSetWithLabel.add(temp);
		DataFileWrite.writeEasyTestSetWithLabelBasedonControlMappingRelationsOneByOne2File(this.easyTestCaseSetIndexFileWithLabelinProcess, temp);
		
	}
	
	public void loadNextMappedControls() {
		
		boolean nextMappedControlCouple = this.nextMappedControls();
		if(!nextMappedControlCouple) {
			System.out.println("=========== error ==========");
		}
		System.out.println(
				this.mappingRelationCheckerInvolveUser.currentAppIndex + ", " + 
				this.mappingRelationCheckerInvolveUser.currentPageRelationIndex + ", " + 
				MappingRelationDisplayFrame4SupportCheckSceenShotComparison.currentControlMappingRelationinOnePage
				);
		this.inputTwoPictures(
				this.mappingRelationCheckerInvolveUser.allAppsInfo.get(this.mappingRelationCheckerInvolveUser.currentAppIndex) 
				+ this.mappingRelationCheckerInvolveUser.STRING_IOS_PAGE_FOLDER_MARK
				+ this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex).iOSPageFileName + ".png", 
				this.mappingRelationCheckerInvolveUser.allAppsInfo.get(this.mappingRelationCheckerInvolveUser.currentAppIndex)
				+ this.mappingRelationCheckerInvolveUser.STRING_ANDROID_PAGE_FOLDER_MARK
				+ this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex).androidPageFileName + ".png"
				);
		
		List<IOSElement2AndroidElementRelation> elementRelationListTemp = this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex).matchElements;
		
		List<Integer> controliOS = new ArrayList<Integer>();
		List<Integer> controlAndroid = new ArrayList<Integer>();
		
		iOSXCUITestElement iosElementTemp = elementRelationListTemp.get(MappingRelationDisplayFrame4SupportCheckSceenShotComparison.currentControlMappingRelationinOnePage).iOSPageElement;
		controliOS.add(iosElementTemp.Ex); controliOS.add(iosElementTemp.Ey); 
		controliOS.add((iosElementTemp.Ex + iosElementTemp.Ewidth));
		controliOS.add((iosElementTemp.Ey + iosElementTemp.Eheight));
		
		AndroidGUIElement androidElementTemp = elementRelationListTemp.get(MappingRelationDisplayFrame4SupportCheckSceenShotComparison.currentControlMappingRelationinOnePage).androidPageElement;
		controlAndroid.add(androidElementTemp.Ex1); controlAndroid.add(androidElementTemp.Ey1);
		controlAndroid.add(androidElementTemp.Ex2); controlAndroid.add(androidElementTemp.Ey2);
		
		this.inputRectsAndRelation(controliOS, controlAndroid, MappingRelationDisplayFrame4SupportCheckSceenShotComparison.currentControlMappingRelationinOnePage);
//		this.inputRectsAndRelation(controliOS, controlAndroid, 0);
		
		//Check here!
		String etcTemp = this.easyTestSetWithoutLabel.get(MappingRelationDisplayFrame4SupportCheckSceenShotComparison.currentControlMappingRelation);
		String[] src = etcTemp.split("\n");
		String[] args = src[0].split(" ");
//		if(
//				args[3].equals(this.mappingRelationCheckerInvolveUser.allAppsInfo.get(this.mappingRelationCheckerInvolveUser.currentAppIndex) 
//				+ this.mappingRelationCheckerInvolveUser.STRING_IOS_PAGE_FOLDER_MARK
//				+ this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex).iOSPageFileName + ".xml"
//				)
//				&&
//				args[4].equals(this.mappingRelationCheckerInvolveUser.allAppsInfo.get(this.mappingRelationCheckerInvolveUser.currentAppIndex)
//						+ this.mappingRelationCheckerInvolveUser.STRING_ANDROID_PAGE_FOLDER_MARK
//						+ this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex).androidPageFileName + ".xml"
//						)
//				&&
//				args[0].equals(iosElementTemp.EHead + "[" + iosElementTemp.locationTypeOrder + "]"
//						)
//				&&
//				args[1].equals(androidElementTemp.EHead + "[" + androidElementTemp.locationTypeOrder + "]"
//						)
//				) {
//			//do nothing
//		}
//		else {
//			System.out.println("================================================= Order Error =================================================");
//		}
		System.out.println("================================================= Check =================================================");
		System.out.println(args[0] + ", " + iosElementTemp.EHead + "[" + iosElementTemp.locationTypeOrder + "]");
		System.out.println(args[1] + ", " + androidElementTemp.EHead + "[" + androidElementTemp.locationTypeOrder + "]");
		System.out.println(args[3]);
		System.out.println(this.mappingRelationCheckerInvolveUser.allAppsInfo.get(this.mappingRelationCheckerInvolveUser.currentAppIndex) 
				+ this.mappingRelationCheckerInvolveUser.STRING_IOS_PAGE_FOLDER_MARK
				+ this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex).iOSPageFileName + ".xml");
		System.out.println(args[4]);
		System.out.println(this.mappingRelationCheckerInvolveUser.allAppsInfo.get(this.mappingRelationCheckerInvolveUser.currentAppIndex)
				+ this.mappingRelationCheckerInvolveUser.STRING_ANDROID_PAGE_FOLDER_MARK
				+ this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex).androidPageFileName + ".xml");
		
		
		
		
	}
	
	
	
	
	
	class DisplayPanel extends JPanel{
		
		
		public DisplayPanel() {
			//Consider more
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			this.setBackground(Color.lightGray);
			
			this.addMouseListener(new MouseAction());
			this.addMouseMotionListener(new MouseMotion());
			
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
//			System.out.println("=============== " + MappingRelationDisplayFrame4SupportCheckSceenShotComparison.index);
			for(int j = 0; j < MappingRelationDisplayFrame4SupportCheckSceenShotComparison.index; j ++) {
				draw(g2d, itemList.get(j));
			}
		}
		
		public void draw(Graphics2D g2d, Shape shape) {
			shape.draw(g2d);
		}
		public void createNewGraphics() {
			
			
			
			switch (currentChoice) {
			case 0:
				MappingRelationDisplayFrame4SupportCheckSceenShotComparison.itemList.add(new Images());
				MappingRelationDisplayFrame4SupportCheckSceenShotComparison.itemList.get(index).currentChoice = 0;
				break;
			case 4:
				MappingRelationDisplayFrame4SupportCheckSceenShotComparison.itemList.add(new Line());
				MappingRelationDisplayFrame4SupportCheckSceenShotComparison.itemList.get(index).currentChoice = 4;
				break;
			case 5:
				MappingRelationDisplayFrame4SupportCheckSceenShotComparison.itemList.add(new Rectangle());
				MappingRelationDisplayFrame4SupportCheckSceenShotComparison.itemList.get(index).currentChoice = 5;
				break;
			case 20:
				break;
			case 21:
				break;
			}
			MappingRelationDisplayFrame4SupportCheckSceenShotComparison.itemList.get(index).color = color;
			MappingRelationDisplayFrame4SupportCheckSceenShotComparison.itemList.get(index).width = stroke;
			
		}
		
		class MouseAction extends MouseAdapter{
			
			public void mouseClicked(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				
			}
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				//nothing
				
				
				
				
				
				
			}
			public void mouseExited(MouseEvent e) {
				//nothing
			}
			
		}
		
		class MouseMotion extends MouseMotionAdapter {
			
			public void mouseDragged(MouseEvent e) {
				
				if((currentChoice == 4)&&(startRectangleIndex != -1)) {
					
					System.out.println("Drag(4) " + currentRectangleIndex);
					
					if(currentRectangleIndex == -1) {//In case that, two rectangle link together.
					}
					else {
						itemList.get(currentRectangleIndex).width = 1;
						currentRectangleIndex = -1;
						repaint();
					}
					
					Rectangle rectangleTemp = null;
					int indexTemp = -1;
					
					for(int k = MappingRelationDisplayFrame4SupportCheckSceenShotComparison.index - 1; k >= 0; k --) {
						
						if((itemList.get(k).currentChoice == 5) && (itemList.get(k).typeiOSorAndroid == Shape.INT_ANDROID_ELEMENT) && (!itemList.get(k).inRelation) &&
								(Rectangle.inRectangleField(itemList.get(k).x1, itemList.get(k).y1, itemList.get(k).x2, itemList.get(k).y2, e.getX(), e.getY())) 
								){
							System.out.println("=== in Rectangle field " + itemList.get(k).inRelation + "===");
							rectangleTemp = (Rectangle)itemList.get(k);
							indexTemp = k;
							break;
						}
					}
					
					if(rectangleTemp != null) {
						currentRectangleIndex = indexTemp;
						itemList.get(currentRectangleIndex).width = 3;
						
						
					}
					else {
						
//						if(currentRectangleIndex == -1) {
//							//do nothing
//						}
//						else {
//							
//							itemList.get(currentRectangleIndex).width = 1;
//							currentRectangleIndex = -1;
//						}
						
					}
					
					itemList.get(index-1).x2 = e.getX();
					itemList.get(index-1).y2 = e.getY();
					displayArea.repaint();
					
				}
				
				
			}
			public void mouseMoved(MouseEvent e) {
				
				if(currentChoice == 4) {
					
					if(currentRectangleIndex == -1) {
					}
					else {
						itemList.get(currentRectangleIndex).width = 1;
						currentRectangleIndex = -1;
						repaint();
					}
					
					Rectangle rectangleTemp = null;
					int indexTemp = -1;
					
					for(int k = MappingRelationDisplayFrame4SupportCheckSceenShotComparison.index - 1; k >= 0; k --) {
						
						if((itemList.get(k).currentChoice == 5) && (itemList.get(k).typeiOSorAndroid == Shape.INT_IOS_ELEMENT) && (!itemList.get(k).inRelation) &&
								(Rectangle.inRectangleField(itemList.get(k).x1, itemList.get(k).y1, itemList.get(k).x2, itemList.get(k).y2, e.getX(), e.getY())) 
								){
							System.out.println("=== Mouse moved: in Rectangle field" + itemList.get(k).typeiOSorAndroid + " ===");
							rectangleTemp = (Rectangle)itemList.get(k);
							indexTemp = k;
							break;
						}
					}
					
					if(rectangleTemp != null) {
						System.out.println("=== focus on one rectangle ===");
						currentRectangleIndex = indexTemp;
						
						System.out.println("index: " + index + ", indexTemp: " + indexTemp);
						
						//itemList.get(currentRectangleIndex).color = Color.green;
						itemList.get(currentRectangleIndex).width = 3;
						
////						itemList.remove(index);
////						itemList.add(new Rectangle());
//						
//						System.out.println("index: " + index + ", indexTemp: " + indexTemp + ", len:" + itemList.size());
//						
//						itemList.get(index).currentChoice = 5;
////						itemList.get(index).color = itemList.get(currentRectangleIndex).color;
//						itemList.get(index).color = Color.green;
//						itemList.get(index).width = 10;
//						itemList.get(index).x1 = itemList.get(currentRectangleIndex).x1;
//						itemList.get(index).x2 = itemList.get(currentRectangleIndex).x2;
//						itemList.get(index).y1 = itemList.get(currentRectangleIndex).y1;
//						itemList.get(index).y2 = itemList.get(currentRectangleIndex).y2;
						repaint();
						
					}
					else {
						System.out.println("=== no rectangle ===");
						if(currentRectangleIndex == -1) {
							//do nothing
							repaint();
						}
						else {
							itemList.get(currentRectangleIndex).width = 1;
							repaint();
							currentRectangleIndex = -1;
						}
						
					}
					
					
					
					
					
				}
				
				
				
				
			}
			
			
		}
		
		
	}
	
	public void inputImageDemo(String picName1, String picName2, List<Integer> control1, List<Integer> control2) {
		//picName1 is and iOS page picture and picName2 is an Android page picture.
		
		
		int offsetW = 50;
		
		BufferedImage image1;
		BufferedImage image2;
		
		List<Integer> c1 = PictureTransform.transformGUIControlLocation(control1.get(0), control1.get(1), control1.get(2), control1.get(3), PictureTransform.INT_IOS_PLATFORM);
		List<Integer> c2 = PictureTransform.transformGUIControlLocation(control2.get(0), control2.get(1), control2.get(2), control2.get(3), PictureTransform.INT_ANDROID_PLATFORM);
		
//		BufferedImage imageNode;
		
		try {
			index = 0;
			currentChoice = 0;
			image1 = (BufferedImage) PictureTransform.changePictureSize(picName1, PictureTransform.INT_IOS_PLATFORM);
			image2 = (BufferedImage) PictureTransform.changePictureSize(picName2, PictureTransform.INT_ANDROID_PLATFORM);
//			imageNode = (BufferedImage) PictureTransform.changePictureSize(picNameNode, PictureTransform.INT_ANDROID_PLATFORM);
			
			
			int baseW1 = image1.getWidth();
			int baseW2 = image2.getWidth();
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = (frameWidth - baseW1 - baseW2 - offsetW)/2;
			itemList.get(index).y1 = 0;
			itemList.get(index).image = image1;
			itemList.get(index).board = displayArea;
			//itemList.get(index).color = Color.blue;
			displayArea.repaint();
			index ++;
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(index-1).x1 + baseW1 + offsetW;
			itemList.get(index).y1 = 0;
			itemList.get(index).image = image2;
			itemList.get(index).board = displayArea;
			displayArea.repaint();
			index ++;
			
			currentChoice = 5;
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(index-2).x1 + c1.get(0);
			itemList.get(index).y1 = c1.get(1);
			itemList.get(index).x2 = itemList.get(index-2).x1 + c1.get(2);
			itemList.get(index).y2 = c1.get(3);
			displayArea.repaint();
			index ++;
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(index-2).x1 + c2.get(0);
			itemList.get(index).y1 = c2.get(1);
			itemList.get(index).x2 = itemList.get(index-2).x1 + c2.get(2);
			itemList.get(index).y2 = c2.get(3);
			displayArea.repaint();
			index ++;
			
//			System.out.println(itemList[index].x1 + "," + itemList[index].y1 + "," + itemList[index].x2 + "," + itemList[index].y2);
			
			currentChoice = 4;
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(index-2).x2;
			itemList.get(index).y1 = itemList.get(index-2).y2;
			itemList.get(index).x2 = itemList.get(index-1).x1;
			itemList.get(index).y2 = itemList.get(index-1).y1;
			displayArea.repaint();
			index ++;
			
//			currentChoice = 0;
//			displayArea.createNewGraphics();
//			itemList[index].x1 = itemList[index - 1].x1;
//			itemList[index].y1 = itemList[index - 1].y1;
//			itemList[index].image = imageNode;
//			itemList[index].board = displayArea;
//			displayArea.repaint();
//			index ++;
			currentChoice = 20;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void inputTwoPictures(String picNameiOS, String picNameAndroid) {
		
		int offSetW = INT_GAP_BETWEEN_TWO_PICTURES;
		BufferedImage imageiOS;
		BufferedImage imageAndroid;
		
		try {
//			index = 0;
			currentChoice = 0;
			imageiOS = (BufferedImage) PictureTransform.changePictureSize4CheckMultiCandidates(picNameiOS, PictureTransform.INT_IOS_PLATFORM);
			imageAndroid = (BufferedImage) PictureTransform.changePictureSize4CheckMultiCandidates(picNameAndroid, PictureTransform.INT_ANDROID_PLATFORM);
			
			int baseWiOS = imageiOS.getWidth();
			int baseWAdnroid = imageAndroid.getWidth();
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = 5;
			itemList.get(index).y1 = 0;
			itemList.get(index).image = imageiOS;
			itemList.get(index).board = displayArea;
			index ++;
			lastiOSPage = index-1;
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(index-1).x1 + baseWiOS + offSetW;
			itemList.get(index).y1 = 0;
			itemList.get(index).image = imageAndroid;
			itemList.get(index).board = displayArea;
			index ++;
			lastAndroidPage_0 = index-1;
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(index-1).x1 + baseWAdnroid + offSetW;
			itemList.get(index).y1 = 0;
			itemList.get(index).image = imageAndroid;
			itemList.get(index).board = displayArea;
			index ++;
			lastAndroidPage_1 = index-1;
			
			displayArea.repaint();
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(index-1).x1 + baseWAdnroid + offSetW;
			itemList.get(index).y1 = 0;
			itemList.get(index).image = imageAndroid;
			itemList.get(index).board = displayArea;
			index ++;
			lastAndroidPage_2 = index-1;
			
			displayArea.repaint();
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(index-1).x1 + baseWAdnroid + offSetW;
			itemList.get(index).y1 = 0;
			itemList.get(index).image = imageAndroid;
			itemList.get(index).board = displayArea;
			index ++;
			lastAndroidPage_3 = index-1;
			
			displayArea.repaint();
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(index-1).x1 + baseWAdnroid + offSetW;
			itemList.get(index).y1 = 0;
			itemList.get(index).image = imageAndroid;
			itemList.get(index).board = displayArea;
			index ++;
			lastAndroidPage_4 = index-1;
			
			displayArea.repaint();
			
			currentChoice = 20;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void inputRectsAndRelation(List<Integer> controliOS, List<Integer> controlAndroid, int relationIndex) {
		
		
		List<Integer> ciOS = PictureTransform.transformGUIControlLocation(controliOS.get(0), controliOS.get(1), controliOS.get(2), controliOS.get(3), PictureTransform.INT_IOS_PLATFORM);
		List<Integer> cAndroid = PictureTransform.transformGUIControlLocation(controlAndroid.get(0), controlAndroid.get(1), controlAndroid.get(2), controlAndroid.get(3), PictureTransform.INT_ANDROID_PLATFORM);
		
		try {
			
			currentChoice = 5;
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(lastiOSPage).x1 + ciOS.get(0);
			itemList.get(index).y1 = ciOS.get(1);
			itemList.get(index).x2 = itemList.get(lastiOSPage).x1 + ciOS.get(2);
			itemList.get(index).y2 = ciOS.get(3);
			itemList.get(index).color = Color.blue;
			itemList.get(index).elementOrRelationIndex = relationIndex;
			itemList.get(index).inRelation = true;
			itemList.get(index).typeiOSorAndroid = Shape.INT_IOS_ELEMENT;
			//displayArea.repaint();
			index ++;
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(lastAndroidPage).x1 + cAndroid.get(0);
			itemList.get(index).y1 = cAndroid.get(1);
			itemList.get(index).x2 = itemList.get(lastAndroidPage).x1 + cAndroid.get(2);
			itemList.get(index).y2 = cAndroid.get(3);
			itemList.get(index).color = Color.red;
			itemList.get(index).elementOrRelationIndex = relationIndex;
			itemList.get(index).inRelation = true;
			//displayArea.repaint();
			index ++;
			
			
			currentChoice = 4;
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(index-2).x2;
			itemList.get(index).y1 = itemList.get(index-2).y2;
			itemList.get(index).x2 = itemList.get(index-1).x1;
			itemList.get(index).y2 = itemList.get(index-1).y1;
			itemList.get(index).color = Color.black;
			itemList.get(index).elementOrRelationIndex = relationIndex;
			itemList.get(index).inRelation = true;
			itemList.get(index).typeiOSorAndroid = Shape.INT_ANDROID_ELEMENT;
			itemList.get(index).startControl = itemList.get(index-2);
			itemList.get(index).endControl = itemList.get(index-1);
			//displayArea.repaint();
			itemList.get(index-2).linkRelationLine = itemList.get(index);
			itemList.get(index-1).linkRelationLine = itemList.get(index);
			index ++;
			
			
			displayArea.repaint();
			
			currentChoice = 20;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void inputRectsWithoutRelation(List<Integer> controliOS, int label, int elementIndex) {
		
		
		
		
//		List<Integer> ciOS = PictureTransform.transformGUIControlLocation(controliOS.get(0), controliOS.get(1), controliOS.get(2), controliOS.get(3), PictureTransform.INT_IOS_PLATFORM);
//		List<Integer> cAndroid = PictureTransform.transformGUIControlLocation(controlAndroid.get(0), controlAndroid.get(1), controlAndroid.get(2), controlAndroid.get(3), PictureTransform.INT_ANDROID_PLATFORM);
		
		try {
			
			if(label == INT_IOS_GUI_CONTROL) {
				List<Integer> ciOS = PictureTransform.transformGUIControlLocation(controliOS.get(0), controliOS.get(1), controliOS.get(2), controliOS.get(3), PictureTransform.INT_IOS_PLATFORM);
				
				currentChoice = 5;
				displayArea.createNewGraphics();
				itemList.get(index).x1 = itemList.get(lastiOSPage).x1 + ciOS.get(0);
				itemList.get(index).y1 = ciOS.get(1);
				itemList.get(index).x2 = itemList.get(lastiOSPage).x1 + ciOS.get(2);
				itemList.get(index).y2 = ciOS.get(3);
				itemList.get(index).color = Color.blue;
				itemList.get(index).elementOrRelationIndex = elementIndex;
				itemList.get(index).inRelation = false;
				itemList.get(index).typeiOSorAndroid = Shape.INT_IOS_ELEMENT;
				//displayArea.repaint();
				index ++;
			}
			else {
				List<Integer> ciOS = PictureTransform.transformGUIControlLocation(controliOS.get(0), controliOS.get(1), controliOS.get(2), controliOS.get(3), PictureTransform.INT_ANDROID_PLATFORM);
				
				currentChoice = 5;
				displayArea.createNewGraphics();
				itemList.get(index).x1 = itemList.get(lastAndroidPage).x1 + ciOS.get(0);
				itemList.get(index).y1 = ciOS.get(1);
				itemList.get(index).x2 = itemList.get(lastAndroidPage).x1 + ciOS.get(2);
				itemList.get(index).y2 = ciOS.get(3);
				itemList.get(index).color = Color.red;
				itemList.get(index).elementOrRelationIndex = elementIndex;
				itemList.get(index).inRelation = false;
				itemList.get(index).typeiOSorAndroid = Shape.INT_ANDROID_ELEMENT;
				//displayArea.repaint();
				index ++;
			}
			
			
			
			
//			displayArea.createNewGraphics();
//			itemList.get(index).x1 = itemList.get(lastAndroidPage).x1 + cAndroid.get(0);
//			itemList.get(index).y1 = cAndroid.get(1);
//			itemList.get(index).x2 = itemList.get(lastAndroidPage).x1 + cAndroid.get(2);
//			itemList.get(index).y2 = cAndroid.get(3);
//			//displayArea.repaint();
//			index ++;
			
			displayArea.repaint();
			
			currentChoice = 20;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void inputRectAndItsColor(List<Integer> controliOS, int label, int elementIndex, Color color, int lastPageIndex) {
		
		
		
		
//		List<Integer> ciOS = PictureTransform.transformGUIControlLocation(controliOS.get(0), controliOS.get(1), controliOS.get(2), controliOS.get(3), PictureTransform.INT_IOS_PLATFORM);
//		List<Integer> cAndroid = PictureTransform.transformGUIControlLocation(controlAndroid.get(0), controlAndroid.get(1), controlAndroid.get(2), controlAndroid.get(3), PictureTransform.INT_ANDROID_PLATFORM);
		
		try {
			
			if(label == INT_IOS_GUI_CONTROL) {
				List<Integer> ciOS = PictureTransform.transformGUIControlLocation4CheckMultiCandidates(controliOS.get(0), controliOS.get(1), controliOS.get(2), controliOS.get(3), PictureTransform.INT_IOS_PLATFORM);
				
				currentChoice = 5;
				displayArea.createNewGraphics();
				itemList.get(index).x1 = itemList.get(lastiOSPage).x1 + ciOS.get(0);
				itemList.get(index).y1 = ciOS.get(1);
				itemList.get(index).x2 = itemList.get(lastiOSPage).x1 + ciOS.get(2);
				itemList.get(index).y2 = ciOS.get(3);
				itemList.get(index).color = color;
				itemList.get(index).elementOrRelationIndex = elementIndex;
				itemList.get(index).inRelation = false;
				itemList.get(index).typeiOSorAndroid = Shape.INT_IOS_ELEMENT;
				//displayArea.repaint();
				index ++;
			}
			else {
				
				int indexHere = -1;
				
				if(lastPageIndex == 0) {
					indexHere = lastAndroidPage_0;
				}
				else if(lastPageIndex == 1) {
					indexHere = lastAndroidPage_1;
				}
				else if(lastPageIndex == 2) {
					indexHere = lastAndroidPage_2;
				}
				else if(lastPageIndex == 3) {
					indexHere = lastAndroidPage_3;
				}
				else if(lastPageIndex == 4) {
					indexHere = lastAndroidPage_4;
				}
				
				List<Integer> ciOS = PictureTransform.transformGUIControlLocation4CheckMultiCandidates(controliOS.get(0), controliOS.get(1), controliOS.get(2), controliOS.get(3), PictureTransform.INT_ANDROID_PLATFORM);
				
				currentChoice = 5;
				displayArea.createNewGraphics();
				itemList.get(index).x1 = itemList.get(indexHere).x1 + ciOS.get(0);
				itemList.get(index).y1 = ciOS.get(1);
				itemList.get(index).x2 = itemList.get(indexHere).x1 + ciOS.get(2);
				itemList.get(index).y2 = ciOS.get(3);
				itemList.get(index).color = color;
				itemList.get(index).elementOrRelationIndex = elementIndex;
				itemList.get(index).inRelation = false;
				itemList.get(index).typeiOSorAndroid = Shape.INT_ANDROID_ELEMENT;
				//displayArea.repaint();
				index ++;
			}
			
			
			
			
//			displayArea.createNewGraphics();
//			itemList.get(index).x1 = itemList.get(lastAndroidPage).x1 + cAndroid.get(0);
//			itemList.get(index).y1 = cAndroid.get(1);
//			itemList.get(index).x2 = itemList.get(lastAndroidPage).x1 + cAndroid.get(2);
//			itemList.get(index).y2 = cAndroid.get(3);
//			//displayArea.repaint();
//			index ++;
			
			displayArea.repaint();
			
			currentChoice = 20;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	public void clearDisplayFrame() {
		index = 0;
		itemList.clear();
		currentChoice = 20;
		displayArea.repaint();
	}
	
	
	
	
	
	
	public void focusOnSelectedLine(Shape s) {
		
		int offSet = 6;
		
		BufferedImage imageStart;
//		BufferedImage imageEnd;
		
		try {
			currentChoice = 0;
			imageStart = (BufferedImage) PictureTransform.changePictureSize(focusedLabelName, PictureTransform.INT_FOCUSED_LINE_LABEL);
//			imageEnd = (BufferedImage) PictureTransform.changePictureSize(focusedLabelName, PictureTransform.INT_ANDROID_PLATFORM);
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = s.x1 - offSet;
			itemList.get(index).y1 = s.y1 - offSet;
			itemList.get(index).image = imageStart;
			itemList.get(index).board = displayArea;
			displayArea.repaint();
			index ++;
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = s.x2 - offSet;
			itemList.get(index).y1 = s.y2 - offSet;
			itemList.get(index).image = imageStart;
			itemList.get(index).board = displayArea;
			displayArea.repaint();
			index ++;
			
			currentChoice = 21;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void inputRectangle(int x1, int y1, int x2, int y2) {
		
		currentChoice = 5;
		
		displayArea.createNewGraphics();
		
		itemList.get(index).x1 = x1;
		itemList.get(index).y1 = y1;
		itemList.get(index).x2 = x2;
		itemList.get(index).y2 = y2;
		
		displayArea.repaint();
		
		index ++;
		
		
	}
	
	public void printItemList() {
		int len = this.itemList.size();
		for(int i = 0; i < len; i ++) {
			System.out.print("[" + itemList.get(i).currentChoice + "(" + itemList.get(i).x1 + "," + itemList.get(i).y1 + "),(" + itemList.get(i).x2 + "," + itemList.get(i).y2 + ")] ");
		}
		System.out.println();
	}
	
	
	
}
