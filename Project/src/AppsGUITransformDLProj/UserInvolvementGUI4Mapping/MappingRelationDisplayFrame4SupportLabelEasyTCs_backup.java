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

public class MappingRelationDisplayFrame4SupportLabelEasyTCs_backup extends JFrame {
	
	
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
	
	
	private static int frameWidth = 1000;
	private static int frameHeight = 720;
	private static final String focusedLabelName = "NodePIC.png";
	private static Shape focusedLine;
	private static int focusedLineIndex = -1;
	
	private JPopupMenu rightButtonMenu;
	private JMenu topFuncMenu;
	
	private JTextField textField;
	private JButton button;
	private JButton button_next;
	private JButton button_last;
	
	public static final int INT_GAP_BETWEEN_TWO_PICTURES = 50;
	
	public static int lastiOSPage;//only use for locating the controls
	public static int lastAndroidPage;//only use for locating the controls
	
	public MappingRelationCheckerInvolveUser mappingRelationCheckerInvolveUser;//Note! This member crossed with this class, so that the two classes can crossover.
	
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
	
	
	public MappingRelationDisplayFrame4SupportLabelEasyTCs_backup(String s, List<String> easyTestSetDataWithoutLabel, List<String> easyTestSetDataWithLabel, String eTestCaseSetIndexFileWithLabelinProcess) {
		init(s);
		this.easyTestSetWithoutLabel = easyTestSetDataWithoutLabel;
		this.easyTestSetWithLabel = easyTestSetDataWithLabel;
		this.easyTestCaseSetIndexFileWithLabelinProcess = eTestCaseSetIndexFileWithLabelinProcess;
		this.mappingRelationCheckerInvolveUser.currentPageRelationIndex ++;
		this.setVisible(true);
	}
	
	public MappingRelationDisplayFrame4SupportLabelEasyTCs_backup(String s, String picName1, String picName2) {
		init(s);
		List<Integer> control1 = new ArrayList<Integer>();
		control1.add(283); control1.add(619); control1.add(373); control1.add(667);
		List<Integer> control2 = new ArrayList<Integer>();
		control2.add(810); control2.add(1866); control2.add(1080); control2.add(1920);
		inputImageDemo(picName1, picName2, control1, control2);
//		inputRectangle(283, 619, 373, 667);
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
		displayArea.setBounds(0, 20, 1000, 900);
		this.add(displayArea);
		
		rightButtonMenu = new JPopupMenu();
		initRightButtonMenu();
		
		topFuncMenu = new JMenu("Functions");
		initTopFuncMenu();
		
		button_next = new JButton("Next");
		initNextButton();
		
		button_last = new JButton("Last");
		initLastButton();
		
		textField = new JTextField(10);
		initInputText();
		
		button = new JButton("Sure");
		initInputButton();
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
				if(currentChoice == 4) {
					displayArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					itemList.remove(index);
					currentChoice = 20;
				}
				else if((currentChoice == 21)||(currentChoice == 22)) {
					focusedLine = null;
					itemList.remove(index - 1);
					itemList.remove(index - 2);
					index = index - 2;
					currentChoice = 20;
				}
				else {}//all currentChoice should be 20
				repaint();
				
				
				if(MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.saved == 0) {
//					System.out.println("ok:" + JOptionPane.OK_OPTION);
//					System.out.println("yes:" + JOptionPane.YES_OPTION);
//					System.out.println("no:" + JOptionPane.NO_OPTION);
//					System.out.println("cancel:" + JOptionPane.CANCEL_OPTION);
					
					
					int comfirmFeedback = JOptionPane.showConfirmDialog(null, "Do you want to save the changes?", "Notice", JOptionPane.YES_NO_CANCEL_OPTION);
					
					System.out.println("### comfirmFeedback: " + comfirmFeedback);
					if(comfirmFeedback == JOptionPane.YES_OPTION) {
						//Write to the data file
						mappingRelationCheckerInvolveUser.updateE2ERelationsFromCache2DataFiles();
						System.exit(0);
					}
					else if(comfirmFeedback == JOptionPane.NO_OPTION) {
						//Do not write to the data file
						//mappingRelationCheckerInvolveUser.updateE2ERelationsFromDataFiles2Cache();
						System.exit(0);
					}
					else {
						repaint();
						//do nothing
					}
				}
				else if(MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.saved == 1) {
					System.exit(0);
				}
				else {
					
				}
				
			}
			
		});
		
//		displayArea.createNewGraphics();
		
		
	}
	
	public void initInputText() {
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(null);
		
		textField.setBounds(0, 0, 200, 20);
		inputPanel.add(textField);
		
		
		inputPanel.setBounds(700, 0, 200, 20);
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
				
				MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.currentControlMappingRelation ++;
				loadNextMappedControls();
				
				
				repaint();
				
				
			}
		});
		
		
		nextButtonPanel.setBounds(500, 0, 100, 20);
		this.add(nextButtonPanel);
	}
	public void initLastButton() {
		
		JPanel lastButtonPanel = new JPanel();
		lastButtonPanel.setLayout(null);
		
		button_last.setBounds(0, 0, 100, 20);
		lastButtonPanel.add(button_last);
		button_last.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Last Button Click");
				
				
				repaint();
				
				
			}
		});
		
		
		lastButtonPanel.setBounds(600, 0, 100, 20);
		this.add(lastButtonPanel);
	}
	
	public void initTopFuncMenu() {
		
		JMenuBar jMenuBar = new JMenuBar();
		
		JMenu Starting = new JMenu("Start working");
		topFuncMenu.add(Starting);
		
		
		
		
		JMenuItem deleteCurrentPageMappingRelation = new JMenuItem("Delete Current Page Mapping Relation");
		topFuncMenu.add(deleteCurrentPageMappingRelation);
		deleteCurrentPageMappingRelation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Delete Current Page Mapping Relation of topFuncMenu is clicked.");
				
				
				if(currentChoice == 4) {
					displayArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					itemList.remove(index);
					currentChoice = 20;
				}
				else if((currentChoice == 21)||(currentChoice == 22)) {//this should not happen, when delete only occured without control mapping
					focusedLine = null;
					itemList.remove(index - 1);
					itemList.remove(index - 2);
					index = index - 2;
					currentChoice = 20;
				}
				else {}//all currentChoice should be 20
				
				
				
				repaint();
				int comfirmFeedback = JOptionPane.showConfirmDialog(null, "Do you want to delete the mapped pages?", "Notice", JOptionPane.OK_CANCEL_OPTION);
//				System.out.println("### comfirmFeedback: " + comfirmFeedback);
				if(comfirmFeedback == JOptionPane.OK_OPTION) {
					//delete 
					
					itemList.clear();
					index = 0;
					lastAndroidPage = -1;
					lastiOSPage = -1;
					
					boolean hasPageCouple = mappingRelationCheckerInvolveUser.deleteCurrentMappedPages();
					//do not need to update cache! (only in this saving opt)
					isMappedControlsLoaded = false;
					
					MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.saved = 1;
					
					repaint();
					
					if(!hasPageCouple) {
						JOptionPane.showMessageDialog(null, "There is no couple of mapped pages!");
					}
					
				}
				else if(comfirmFeedback == JOptionPane.CANCEL_OPTION) {
					//stay at current mapped pages, do not go to next pages
				}
				else {
					//stay at current mapped pages, do not go to next pages
				}
				
				
				
				repaint();
				
				
				
				
			}
		});
		
		
		JMenuItem nextTwoMappedPages = new JMenuItem("Next Mapped Pages Couple");
		topFuncMenu.add(nextTwoMappedPages);
		nextTwoMappedPages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Next Mapped Pages Couple of topFuncMenu is clicked.");
				
				
				if(currentChoice == 4) {
					displayArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					itemList.remove(index);
					currentChoice = 20;
				}
				else if((currentChoice == 21)||(currentChoice == 22)) {
					focusedLine = null;
					itemList.remove(index - 1);
					itemList.remove(index - 2);
					index = index - 2;
					currentChoice = 20;
				}
				else {}//all currentChoice should be 20
				
				
				if(MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.saved == 0) {
					
					repaint();
					int comfirmFeedback = JOptionPane.showConfirmDialog(null, "Do you want to save the changes?", "Notice", JOptionPane.YES_NO_CANCEL_OPTION);
					
					
					System.out.println("### comfirmFeedback: " + comfirmFeedback);
					//opts to save information into datafile
					if(comfirmFeedback == JOptionPane.YES_OPTION) {
						//Write cache to the data file
						mappingRelationCheckerInvolveUser.updateE2ERelationsFromCache2DataFiles();
						
					}
					else if(comfirmFeedback == JOptionPane.NO_OPTION) {
						//Do not write to the data file, and update the cache from date files
						mappingRelationCheckerInvolveUser.updateE2ERelationsFromDataFiles2Cache();
						
					}
					else {
						//do nothing
						repaint();
						return ;
					}
					
				}
				
				itemList.clear();
				index = 0;
				lastAndroidPage = -1;
				lastiOSPage = -1;
				
				MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.saved = 1;
				
				boolean hasNextPageCouple = mappingRelationCheckerInvolveUser.loadMappedPages();
				
				if(!hasNextPageCouple) {
					JOptionPane.showMessageDialog(null, "No more mapped pages!");
				}
				
				isMappedControlsLoaded = false;
				
				repaint();
			}
		});
		
		
		JMenuItem lastTwoMappedPages = new JMenuItem("Last Mapped Pages Couple");
		topFuncMenu.add(lastTwoMappedPages);
		lastTwoMappedPages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Last Mapped Pages Couple of topFuncMenu is clicked.");
				
				
				if(currentChoice == 4) {
					displayArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					itemList.remove(index);
					currentChoice = 20;
				}
				else if((currentChoice == 21)||(currentChoice == 22)) {
					focusedLine = null;
					itemList.remove(index - 1);
					itemList.remove(index - 2);
					index = index - 2;
					currentChoice = 20;
				}
				else {}//all currentChoice should be 20
				
				
				if(MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.saved == 0) {
					
					repaint();
					int comfirmFeedback = JOptionPane.showConfirmDialog(null, "Do you want to save the changes?", "Notice", JOptionPane.YES_NO_CANCEL_OPTION);
					
					
					System.out.println("### comfirmFeedback: " + comfirmFeedback);
					//opts to save information into datafile
					if(comfirmFeedback == JOptionPane.YES_OPTION) {
						//Write cache to the data file
						mappingRelationCheckerInvolveUser.updateE2ERelationsFromCache2DataFiles();
						
					}
					else if(comfirmFeedback == JOptionPane.NO_OPTION) {
						//Do not write to the data file, and update the cache from date files
						mappingRelationCheckerInvolveUser.updateE2ERelationsFromDataFiles2Cache();
						
					}
					else {
						//do nothing
						repaint();
						return ;
					}
					
				}
				
				itemList.clear();
				index = 0;
				lastAndroidPage = -1;
				lastiOSPage = -1;
				
				MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.saved = 1;
				
				boolean hasLastPageCouple = mappingRelationCheckerInvolveUser.loadLastMappedPages();
				if(!hasLastPageCouple) {
					JOptionPane.showMessageDialog(null, "This is the first couple of mapped pages!");
				}
				isMappedControlsLoaded = false;
				
				repaint();
			}
		});
		
		
		
		
		topFuncMenu.addSeparator();
		jMenuBar.add(topFuncMenu);
		jMenuBar.setBounds(0, 0, 700, 20);
		this.add(jMenuBar);
		//setJMenuBar(jMenuBar);
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
	
	
	
	public void initRightButtonMenu() {
		//JMenuBar jMenuBar = new JMenuBar();
		
		JMenuItem mDel = new JMenuItem("Delete the Selected Relation");
		rightButtonMenu.add(mDel);
		mDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("mDel from Right button is clicked.");
				
				//Only happen when currentChoice == 22
				
				//update cache
				List<Integer> unmappedEleIndex = mappingRelationCheckerInvolveUser.deleteOneSelectedRelation(itemList.get(focusedLineIndex).elementOrRelationIndex);
				
				focusedLine = null;
				itemList.remove(index - 1);
				itemList.remove(index - 2);
				index = index - 2;
				
				itemList.get(focusedLineIndex).startControl.inRelation = false;
				itemList.get(focusedLineIndex).startControl.linkRelationLine = null;
				itemList.get(focusedLineIndex).startControl.elementOrRelationIndex = unmappedEleIndex.get(0);
				itemList.get(focusedLineIndex).endControl.inRelation = false;
				itemList.get(focusedLineIndex).endControl.linkRelationLine = null;
				itemList.get(focusedLineIndex).endControl.elementOrRelationIndex = unmappedEleIndex.get(1);
				
				itemList.remove(focusedLineIndex);
				index = index - 1;
				
				
				displayArea.repaint();
				currentChoice = 20;
				
				MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.saved = 0;
				
				
				
				
			}
			
		});
		
		//setJMenuBar(jMenuBar);
		
	}
	
	
	
	private boolean nextMappedControls() {
		
		int tempCurrentControlMappingRelationinOnePage = MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.currentControlMappingRelationinOnePage + 1;
		
		if(tempCurrentControlMappingRelationinOnePage < 
				this.mappingRelationCheckerInvolveUser
				.pageMappingRelations4AllAppsFromInitialFile
				.get(this.mappingRelationCheckerInvolveUser.currentAppIndex)
				.get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex)
				.matchElements.size()
				) {
			MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.currentControlMappingRelationinOnePage = tempCurrentControlMappingRelationinOnePage;
		}
		else {
			
			int tempCurrentPageRelationIndex = this.mappingRelationCheckerInvolveUser.currentPageRelationIndex + 1;
			
			if(tempCurrentPageRelationIndex < this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).size()) {
				this.mappingRelationCheckerInvolveUser.currentPageRelationIndex = tempCurrentPageRelationIndex;
				MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.currentControlMappingRelationinOnePage = 0;
			}
			else {
				int tempCurrentAppIndex = this.mappingRelationCheckerInvolveUser.currentAppIndex + 1;
				if(tempCurrentAppIndex < this.mappingRelationCheckerInvolveUser.allAppsInfo.size()) {
					this.mappingRelationCheckerInvolveUser.currentAppIndex = tempCurrentAppIndex;
					this.mappingRelationCheckerInvolveUser.currentPageRelationIndex = 0;
					MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.currentControlMappingRelationinOnePage = 0;
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
		String etcTemp = this.easyTestSetWithoutLabel.get(MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.currentControlMappingRelation);
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
		
		System.out.println("=========== Step 1 ==========");
		System.out.println(
				this.mappingRelationCheckerInvolveUser.currentAppIndex + ", " + 
				this.mappingRelationCheckerInvolveUser.currentPageRelationIndex + ", " + 
				MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.currentControlMappingRelationinOnePage
				);
		
		System.out.println("=========== Step 2 ==========");
		this.inputTwoPictures(
				this.mappingRelationCheckerInvolveUser.allAppsInfo.get(this.mappingRelationCheckerInvolveUser.currentAppIndex) 
				+ this.mappingRelationCheckerInvolveUser.STRING_IOS_PAGE_FOLDER_MARK
				+ this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex).iOSPageFileName + ".png", 
				this.mappingRelationCheckerInvolveUser.allAppsInfo.get(this.mappingRelationCheckerInvolveUser.currentAppIndex)
				+ this.mappingRelationCheckerInvolveUser.STRING_ANDROID_PAGE_FOLDER_MARK
				+ this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex).androidPageFileName + ".png"
				);
		
		System.out.println(
				this.mappingRelationCheckerInvolveUser.allAppsInfo.get(this.mappingRelationCheckerInvolveUser.currentAppIndex) 
				+ this.mappingRelationCheckerInvolveUser.STRING_IOS_PAGE_FOLDER_MARK
				+ this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex).iOSPageFileName + ".png"
				+ ", " + 
				this.mappingRelationCheckerInvolveUser.allAppsInfo.get(this.mappingRelationCheckerInvolveUser.currentAppIndex)
				+ this.mappingRelationCheckerInvolveUser.STRING_ANDROID_PAGE_FOLDER_MARK
				+ this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex).androidPageFileName + ".png"
				
				);
		
		
		System.out.println("=========== Step 3 ==========");
		List<IOSElement2AndroidElementRelation> elementRelationListTemp = this.mappingRelationCheckerInvolveUser.pageMappingRelations4AllAppsFromInitialFile.get(this.mappingRelationCheckerInvolveUser.currentAppIndex).get(this.mappingRelationCheckerInvolveUser.currentPageRelationIndex).matchElements;
		
		System.out.println("Len of e2eR size: " + elementRelationListTemp.size());
		
		
		
		List<Integer> controliOS = new ArrayList<Integer>();
		List<Integer> controlAndroid = new ArrayList<Integer>();
		
		iOSXCUITestElement iosElementTemp = elementRelationListTemp.get(MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.currentControlMappingRelationinOnePage).iOSPageElement;
		controliOS.add(iosElementTemp.Ex); controliOS.add(iosElementTemp.Ey); 
		controliOS.add((iosElementTemp.Ex + iosElementTemp.Ewidth));
		controliOS.add((iosElementTemp.Ey + iosElementTemp.Eheight));
		
		AndroidGUIElement androidElementTemp = elementRelationListTemp.get(MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.currentControlMappingRelationinOnePage).androidPageElement;
		controlAndroid.add(androidElementTemp.Ex1); controlAndroid.add(androidElementTemp.Ey1);
		controlAndroid.add(androidElementTemp.Ex2); controlAndroid.add(androidElementTemp.Ey2);
		
		this.inputRectsAndRelation(controliOS, controlAndroid, MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.currentControlMappingRelationinOnePage);
//		this.inputRectsAndRelation(controliOS, controlAndroid, 0);
		
		//Check here!
		String etcTemp = this.easyTestSetWithoutLabel.get(MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.currentControlMappingRelation);
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
			System.out.println("=============== " + MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.index);
			for(int j = 0; j < MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.index; j ++) {
				draw(g2d, itemList.get(j));
			}
		}
		
		public void draw(Graphics2D g2d, Shape shape) {
			shape.draw(g2d);
		}
		public void createNewGraphics() {
			
			
			
			switch (currentChoice) {
			case 0:
				MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.itemList.add(new Images());
				MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.itemList.get(index).currentChoice = 0;
				break;
			case 4:
				MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.itemList.add(new Line());
				MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.itemList.get(index).currentChoice = 4;
				break;
			case 5:
				MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.itemList.add(new Rectangle());
				MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.itemList.get(index).currentChoice = 5;
				break;
			case 20:
				break;
			case 21:
				break;
			}
			MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.itemList.get(index).color = color;
			MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.itemList.get(index).width = stroke;
			
		}
		
		class MouseAction extends MouseAdapter{
			
			public void mouseClicked(MouseEvent e) {
				
				System.out.println("Click (all kinds) : " + "(" + currentChoice + ")" + e.getX() + "," + e.getY());
				
				if(e.getButton() == MouseEvent.BUTTON3) {
					
					System.out.println("Click (right button) : " + e.getX() + "," + e.getY());
					
					if(currentChoice == 21) {
						rightButtonMenu.show(MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.this, e.getX(), e.getY());
						currentChoice = 22;
						//Further, relate this part to the database.
					}
					else if(currentChoice == 22) {
						currentChoice = 21;
						//do nothing
					}
					else if(currentChoice == 4) {
						displayArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						repaint();//?
					}
					else {
						//do nothing
					}
					
					
					printItemList();
					
					return ;
				}
				
				if((currentChoice != 4)&&(currentChoice != 21)&&(currentChoice != 22)) {
					currentChoice = 20;
				}
				
				if(currentChoice == 4) {
					itemList.remove(index);
					displayArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					currentChoice = 20;
				}
				
				if(currentChoice == 20) {//prepare to focus
					
					int mouseX = e.getX();
					int mouseY = e.getY();
					
					System.out.println("Click 20: " + mouseX + "," + mouseY);
					
					Line lineTemp = null;
					int indexTemp = -1;
					
					for(int j = MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.index - 1; j >= 0; j --) {
						
//						System.out.println("=== in for loop ===");
						
						if((itemList.get(j).currentChoice == 4) &&
								(Line.inLineField(itemList.get(j).x1, itemList.get(j).y1, itemList.get(j).x2, itemList.get(j).y2, mouseX, mouseY))
								){
							System.out.println("=== in line field ===");
							focusedLine = itemList.get(j);
							lineTemp = (Line)itemList.get(j);
							indexTemp = j;
							break;
						}
					}
					
					if(lineTemp != null) {
						System.out.println("=== get focused Line ===");
						focusedLineIndex = indexTemp;
						focusOnSelectedLine(lineTemp);
					}
					
				}
				else if(currentChoice == 21) {//cancel focus
					
					int mouseX = e.getX();
					int mouseY = e.getY();
					
					System.out.println("Click 21: " + mouseX + "," + mouseY);
					
					if(Line.inLineField(focusedLine.x1, focusedLine.y1, focusedLine.x2, focusedLine.y2, mouseX, mouseY)) {
						//do nothing
					}
					else {
						focusedLine = null;
						focusedLineIndex = -1;
						itemList.remove(index - 1);
						itemList.remove(index - 2);
						index = index - 2;
						displayArea.repaint();
						currentChoice = 20;
					}
				}
				else if(currentChoice == 22) {
					currentChoice = 21;
				}
				//else {}
				
				printItemList();
			}
			
			public void mousePressed(MouseEvent e) {
				
				System.out.println("Press (all kinds) : " + "(" + currentChoice + ")" + e.getX() + "," + e.getY());
				
				
				
				if(currentChoice == 4) {
					
//					Rectangle rectangleTemp = null;
//					int indexTemp = -1;
//					
//					for(int k = MappingRelationDisplayFrame.index - 1; k >= 0; k --) {
//						
//						if((itemList.get(k).currentChoice == 5) && (itemList.get(k).typeiOSorAndroid == Shape.INT_IOS_ELEMENT) &&
//								(Rectangle.inRectangleField(itemList.get(k).x1, itemList.get(k).y1, itemList.get(k).x2, itemList.get(k).y2, e.getX(), e.getY())) 
//								){
//							System.out.println("=== in Rectangle field ===");
//							rectangleTemp = (Rectangle)itemList.get(k);
//							indexTemp = k;
//							break;
//						}
//					}
//					
//					if(rectangleTemp != null) {
//						System.out.println("=== get starting rectangle ===");
//						startRectangleIndex = indexTemp;
//						
//						itemList.get(index).x1 = itemList.get(startRectangleIndex).x2;
//						itemList.get(index).y1 = itemList.get(startRectangleIndex).y2;
//						
//					}
//					else {
//						
//						startRectangleIndex = -1;
//					}
					
					
					
					if(currentRectangleIndex != -1) {
						System.out.println("Press(4) " + currentRectangleIndex);
						startRectangleIndex = currentRectangleIndex;
						itemList.get(index).x1 = e.getX();
						itemList.get(index).y1 = e.getY();
						
						index ++;
					}
					else {
						//do nothing
						System.out.println("Press(4) " + currentRectangleIndex);
					}
					
					currentRectangleIndex = -1;
					
					
//					int locationX = 0;
//					int locationY = 0;
					
//					itemList.get(index).x1 = itemList.get(index).x2 = e.getX();
//					itemList.get(index).y1 = itemList.get(index).y2 = e.getY();
				}

				
				
				printItemList();
			}
			
			public void mouseReleased(MouseEvent e) {
				
				
				System.out.println("Release (all kinds) : " + "(" + currentChoice + ")" + e.getX() + "," + e.getY());
				
				if((currentChoice == 4)&&(startRectangleIndex != -1)){
					
					Rectangle rectangleTemp = null;
					int indexTemp = -1;
					
					for(int k = MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.index - 2; k >= 0; k --) {
						
						if((itemList.get(k).currentChoice == 5) && (itemList.get(k).typeiOSorAndroid == Shape.INT_ANDROID_ELEMENT) && (!itemList.get(k).inRelation) &&
								(Rectangle.inRectangleField(itemList.get(k).x1, itemList.get(k).y1, itemList.get(k).x2, itemList.get(k).y2, e.getX(), e.getY())) 
								){
							System.out.println("=== in Rectangle field ===");
							rectangleTemp = (Rectangle)itemList.get(k);
							indexTemp = k;
							break;
						}
					}
					
					if(rectangleTemp != null) {
						System.out.println("=== get ending rectangle ===");
						endRectangleIndex = indexTemp;
						
						itemList.get(index-1).x2 = itemList.get(endRectangleIndex).x1;
						itemList.get(index-1).y2 = itemList.get(endRectangleIndex).y1;
						//repaint();
						//index ++;
						createNewGraphics();
						itemList.get(startRectangleIndex).width = 1;
						itemList.get(endRectangleIndex).width = 1;
						
						itemList.get(startRectangleIndex).inRelation = true;
						itemList.get(startRectangleIndex).linkRelationLine = itemList.get(index-1);
						itemList.get(endRectangleIndex).inRelation = true;
						itemList.get(endRectangleIndex).linkRelationLine = itemList.get(index-1);
						
						itemList.get(index-1).inRelation = true;
						itemList.get(index-1).startControl = itemList.get(startRectangleIndex);
						itemList.get(index-1).endControl = itemList.get(endRectangleIndex);
						
						int e2eNewRelationIndex = mappingRelationCheckerInvolveUser.addOneNewE2ERelation(itemList.get(startRectangleIndex).elementOrRelationIndex, itemList.get(endRectangleIndex).elementOrRelationIndex);
						
						itemList.get(startRectangleIndex).elementOrRelationIndex = e2eNewRelationIndex;
						itemList.get(endRectangleIndex).elementOrRelationIndex = e2eNewRelationIndex;
						itemList.get(index-1).elementOrRelationIndex = e2eNewRelationIndex;
						
						MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.saved = 0;
						
					}
					else {
						index --;
						itemList.remove(index);
						createNewGraphics();
						//repaint();
						itemList.get(startRectangleIndex).width = 1;
					}
					System.out.println("Test: startRectangleIndex " + startRectangleIndex + ", index " + index);
					
					
					currentRectangleIndex = -1;
					startRectangleIndex = -1;
					endRectangleIndex = -1;
					
					repaint();
					//if no start rectangle then try not to draw the rectangle
					//if() {
					//	
					//}
					
					
					
//					itemList.get(index-1).x2 = e.getX();
//					itemList.get(index-1).y2 = e.getY();
//					repaint();
//					//index ++;
//					createNewGraphics();
				}
				
				printItemList();
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
					
					for(int k = MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.index - 1; k >= 0; k --) {
						
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
					
					for(int k = MappingRelationDisplayFrame4SupportLabelEasyTCs_backup.index - 1; k >= 0; k --) {
						
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
			imageiOS = (BufferedImage) PictureTransform.changePictureSize(picNameiOS, PictureTransform.INT_IOS_PLATFORM);
			imageAndroid = (BufferedImage) PictureTransform.changePictureSize(picNameAndroid, PictureTransform.INT_ANDROID_PLATFORM);
			
			int baseWiOS = imageiOS.getWidth();
			int baseWAdnroid = imageAndroid.getWidth();
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = (frameWidth - baseWiOS - baseWAdnroid - offSetW)/2;
			itemList.get(index).y1 = 0;
			itemList.get(index).image = imageiOS;
			itemList.get(index).board = displayArea;
			//displayArea.repaint();
			index ++;
			lastiOSPage = index-1;
			
			displayArea.createNewGraphics();
			itemList.get(index).x1 = itemList.get(index-1).x1 + baseWiOS + offSetW;
			itemList.get(index).y1 = 0;
			itemList.get(index).image = imageAndroid;
			itemList.get(index).board = displayArea;
			//displayArea.repaint();
			index ++;
			lastAndroidPage = index-1;
			
			
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
