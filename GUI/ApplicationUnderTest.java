package org.ruihua.GUITrans.AppsGUITransformDLProj.util;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.ruihua.GUITrans.AppsGUITransformDLProj.AppiumExecutor.CommunicateWithAUTbyAppium;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.AndroidGUIElement;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.AndroidGUIPage;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.iOSXCUITestElement;
import org.ruihua.GUITrans.AppsGUITransformDLProj.GUI.iOSXCUITestPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class ApplicationUnderTest {
	
	public CommunicateWithAUTbyAppium comSUT;
	public HashMap<String, String> iOSdevice_iphone6;
	public HashMap<String, String> androiddevice_SM_N9008;
	
	public HashMap<String, String> iOSDevice_iphone6_plus;
	public HashMap<String, String> androidDevice_Redmi_K30_ProZE;
	
	public ApplicationUnderTest() {
		//iOS device
		this.iOSdevice_iphone6 = new HashMap<String, String>();
		this.iOSdevice_iphone6.put("deviceName", "iPhone 6");
		this.iOSdevice_iphone6.put("platformName", "ios");
		this.iOSdevice_iphone6.put("platformVersion", "12.4");
		this.iOSdevice_iphone6.put("udid", "15ae4befc2ea6592339b09c28b72657ce44ab281");
		//people daily
//		this.iOSdevice_iphone6.put("bundleId", "cn.com.people.peopledailyphone");
		
		this.iOSdevice_iphone6.put("automationName", "XCUITest");
		this.iOSdevice_iphone6.put("driverUrl", "http://localhost:4723/wd/hub");//
		//Android device
		this.androiddevice_SM_N9008 = new HashMap<String, String>();
		this.androiddevice_SM_N9008.put("deviceName", "SM-N9008");
		this.androiddevice_SM_N9008.put("platformName", "Android");
		this.androiddevice_SM_N9008.put("platformVersion", "5.0");
		this.androiddevice_SM_N9008.put("udid", "a2884907");
		//people daily
//		this.androiddevice_SM_N9008.put("appPackage", "com.peopledailychina.activity");
//		this.androiddevice_SM_N9008.put("appActivity", ".activity.WelcomeActivity");
		
		this.androiddevice_SM_N9008.put("driverUrl", "http://localhost:4725/wd/hub");//
		//CommunicateWithAUTbyAppium
		this.comSUT = new CommunicateWithAUTbyAppium();
		this.comSUT.setPlatformInformationIOS(this.iOSdevice_iphone6);
		this.comSUT.setPlatformInformationAndroid(this.androiddevice_SM_N9008);
		
		//optional iOS device
		this.iOSDevice_iphone6_plus = new HashMap<String, String>();
		this.iOSDevice_iphone6_plus.put("deviceName", "iPhone");
		this.iOSDevice_iphone6_plus.put("platformName", "ios");
		this.iOSDevice_iphone6_plus.put("platformVersion", "12.0");
		this.iOSDevice_iphone6_plus.put("udid", "1acd248b99c48c636f5fa2ff4ad0e136184ccd20");
		this.iOSDevice_iphone6_plus.put("automationName", "XCUITest");
		this.iOSDevice_iphone6_plus.put("driverUrl", "http://localhost:4723/wd/hub");
		//optional Android device
		this.androidDevice_Redmi_K30_ProZE = new HashMap<String, String>();
		this.androidDevice_Redmi_K30_ProZE.put("deviceName", "Redmi");
		this.androidDevice_Redmi_K30_ProZE.put("platformName", "Android");
		this.androidDevice_Redmi_K30_ProZE.put("platformVersion", "10");
		this.androidDevice_Redmi_K30_ProZE.put("udid", "4187d3d1");
		this.androidDevice_Redmi_K30_ProZE.put("driverUrl", "http://localhost:4725/wd/hub");//
		
		
		
	}
	
	public void collectAppPages() {
		
		
	}
	
	public void takeIOSRootPageInfo(String fileFolderName, String appName) {
		
		//ApplicationUnderTest.outputConsole2File(fileFolderName);
		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		IOSDriver iOSDriver = null;
		int whileLabel = 0;
		int countLabel = 5;
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				System.out.println("========== Step 1 ===========");
				iOSDriver= this.comSUT.setupIOSAppiumConnectionWithApp();
				System.out.println("========== Step 2 ===========");
				this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName+"rootPage");
				System.out.println("========== Step 3 ===========");
				this.comSUT.takeScreenShot(iOSDriver, fileFolderName+"rootPage");
				this.comSUT.resetIOSApp(iOSDriver);
			} catch (Exception ex) {
				ex.printStackTrace();
				if(countLabel > 0) {
					whileLabel = 0;
					countLabel --;
					System.out.println("Retry " + (5 - countLabel) + " to get iOS root page.");
					this.comSUT.resetIOSApp(iOSDriver);
				}
				else {
					System.out.println("Fail to getting iOS root page.");
					this.comSUT.resetIOSApp(iOSDriver);
				}
				
			}
		}
		
		
		iOSXCUITestPage appRootPage = GUIPageXMLFileReader.readiOSPageXMLFile(fileFolderName+"rootPage.xml");
		
	}
	
	
	public void exploreIOSAppLevelOnePagesFromRootPage(String fileFolderName, String appName) {
		//Start from root
		// fileFolderName/AppName_demo_0/iOS/
		ApplicationUnderTest.outputConsole2File(fileFolderName);
		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		IOSDriver iOSDriver= this.comSUT.setupIOSAppiumConnectionWithApp();
		this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName+"rootPage");
		this.comSUT.takeScreenShot(iOSDriver, fileFolderName+"rootPage");
		this.comSUT.resetIOSApp(iOSDriver);
		
		iOSXCUITestPage appRootPage = GUIPageXMLFileReader.readiOSPageXMLFile(fileFolderName+"rootPage.xml");
		iOSXCUITestPage.traverseVisitPageTree(appRootPage.elementRoot, comSUT, fileFolderName);
	}
	
	public void takeAndroidRootPageInfo(String fileFolderName, String appName) {
		//Start from root
		// fileFolderName/AppName_demo_0/iOS/
		
		//this.outputConsole2File(fileFolderName);
		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		AndroidDriver androidDriver = null;
		int whileLabel = 0;
		int countLabel = 5;
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				System.out.println("========== Step 1 ===========");
				androidDriver= this.comSUT.setupAndroidAppiumConnectionWithApp();
				System.out.println("========== Step 2 ===========");
				this.comSUT.takeScreenPageSource(androidDriver, fileFolderName+"rootPage");
				System.out.println("========== Step 3 ===========");
				this.comSUT.takeScreenShot(androidDriver, fileFolderName+"rootPage");
				this.comSUT.resetAndroidApp(androidDriver);
			} catch (Exception ex) {
				ex.printStackTrace();
				if(countLabel > 0) {
					whileLabel = 0;
					countLabel --;
					System.out.println("Retry " + (5 - countLabel) + " to get Android root page.");
					this.comSUT.resetAndroidApp(androidDriver);
				}
				else {
					System.out.println("Fail to getting Android root page.");
					this.comSUT.resetAndroidApp(androidDriver);
				}
				
			}
		}
		
		
		//AndroidDriver androidDriver= this.comSUT.setupAndroidAppiumConnectionWithApp();
		
		
		AndroidGUIPage appRootPage = GUIPageXMLFileReader.readAndroidPageXMLFile(fileFolderName+"rootPage.xml");
		
		
	}
	
	
	public void exploreAndroidAppLevelOnePagesFromRootPage(String fileFolderName, String appName) {
		//Start from root
		// fileFolderName/AppName_demo_0/iOS/
		
		//this.outputConsole2File(fileFolderName);
		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		AndroidDriver androidDriver= this.comSUT.setupAndroidAppiumConnectionWithApp();
		this.comSUT.takeScreenPageSource(androidDriver, fileFolderName+"rootPage");
		this.comSUT.takeScreenShot(androidDriver, fileFolderName+"rootPage");
		this.comSUT.resetAndroidApp(androidDriver);
		
		AndroidGUIPage appRootPage = GUIPageXMLFileReader.readAndroidPageXMLFile(fileFolderName+"rootPage.xml");
		
		AndroidGUIPage.traverseVisitPageTree(appRootPage.elementRoot, comSUT, fileFolderName);
		
	}
	
	public void parallelExectueiOSandAndroidElements2TriggerNewPage(MapCorrespondingGUIPage_20200403 mappingGUIPageOptSet, List<IOSElement2AndroidElementRelation> wholeList, String fileFolderName, int relationOrder) {
		
		IOSDriver iOSDriver = null;
		AndroidDriver androidDriver = null;
		int whileLabel = 0;
		int countLabel = 3;
		
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				
				System.out.println("========== Step 1.1: setup iOS driver ===========");
				iOSDriver= this.comSUT.setupIOSAppiumConnectionWithApp();
				System.out.println("========== Step 1.2: take the source code of the iOS root page  ===========");
				this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName+"iOS/"+relationOrder+"_rootPage");
				this.comSUT.resetIOSApp(iOSDriver);
				
				} catch (Exception ex) {
					ex.printStackTrace();
					if(countLabel > 0) {
						whileLabel = 0;
						countLabel --;
						System.out.println("Retry " + (3 - countLabel) + " to get current iOS root page.");
						this.comSUT.resetIOSApp(iOSDriver);
					}
					else {
						System.out.println("Fail to getting current iOS root page.");
						this.comSUT.resetIOSApp(iOSDriver);
					}
		
				}
		}
		
		iOSDriver = null;
		androidDriver = null;
		whileLabel = 0;
		countLabel = 3;
		
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				
				System.out.println("========== Step 2.1: setup Android driver ===========");
				androidDriver= this.comSUT.setupAndroidAppiumConnectionWithApp();
				System.out.println("========== Step 2.2: take the source code of the Android root page ===========");
				this.comSUT.takeScreenPageSource(androidDriver, fileFolderName+"Android/"+relationOrder+"_rootPage");
				this.comSUT.resetAndroidApp(androidDriver);
				
				} catch (Exception ex) {
					ex.printStackTrace();
					if(countLabel > 0) {
						whileLabel = 0;
						countLabel --;
						System.out.println("Retry " + (3 - countLabel) + " to get current Android root page.");
						this.comSUT.resetAndroidApp(androidDriver);
						
					}
					else {
						System.out.println("Fail to getting current Android root page.");
						this.comSUT.resetAndroidApp(androidDriver);
			
					}
		
				}
		}
		
		
		
		///////////////////
		System.out.println("========== Step 3.1: read the source code of the iOS and Android root page  ===========");
		iOSXCUITestPage appiOSRootPage = GUIPageXMLFileReader.readiOSPageXMLFile(fileFolderName+"iOS/"+relationOrder+"_rootPage.xml");
		AndroidGUIPage appAndroidRootPage = GUIPageXMLFileReader.readAndroidPageXMLFile(fileFolderName+"Android/"+relationOrder+"_rootPage.xml");
		
		System.out.println("========== Step 3.2: calculate the relaton list based on the current iOS and Android root page  ===========");
		List<IOSElement2AndroidElementRelation> currentRelationList = mappingGUIPageOptSet.findConfirmedCorrespondingControlsfromTwoCorrespondingPages(appiOSRootPage, appAndroidRootPage);
		System.out.println("Length of the current mapping relation list: " + currentRelationList.size());
		
		System.out.println("========== Step 3.3: choose one new possible page mapping relaton in order ===========");
		System.out.println("Length of the original whole list: " + wholeList.size());
		IOSElement2AndroidElementRelation selectedPossibleRelation = mappingGUIPageOptSet.chooseOneNewPossiblePageMappingRelation(wholeList, currentRelationList);
		if(selectedPossibleRelation != null) {
			wholeList.add(selectedPossibleRelation);
		}
		
		String classNameIOS = selectedPossibleRelation.iOSPageElement.EHead;
		int orderNumIOS = selectedPossibleRelation.iOSPageElement.locationTypeOrder;
		String classNameAndroid = selectedPossibleRelation.androidPageElement.EHead;
		int orderNumAndroid = selectedPossibleRelation.androidPageElement.locationTypeOrder;
		///////////////////
		
		
		
		iOSDriver = null;
		androidDriver = null;
		whileLabel = 0;
		countLabel = 5;
		
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				
				System.out.println("========== Step 4.1.1: setup iOS driver ===========");
				iOSDriver= this.comSUT.setupIOSAppiumConnectionWithApp();
				System.out.println("========== Step 4.1.2: Click (tap) iOS element ===========");
//				this.comSUT.clickIOSGUIElement(iOSDriver, classNameIOS, orderNumIOS, selectedPossibleRelation.textSimilarity);
				System.out.println("========== Step 4.1.3: take iOS page source code ===========");
				this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName + "iOS/" + relationOrder + classNameIOS + "[" + orderNumIOS + "]");
				System.out.println("========== Step 4.1.4: take iOS page screen ===========");
				this.comSUT.takeScreenShot(iOSDriver, fileFolderName + "iOS/" + relationOrder + classNameIOS + "[" + orderNumIOS + "]");
				
				this.comSUT.resetIOSApp(iOSDriver);
				
				} catch (Exception ex) {
					ex.printStackTrace();
					if(countLabel > 0) {
						whileLabel = 0;
						countLabel --;
						System.out.println("Retry " + (5 - countLabel) + " to get current iOS page.");
						this.comSUT.resetIOSApp(iOSDriver);
					}
					else {
						System.out.println("Fail to getting current iOS page.");
						this.comSUT.resetIOSApp(iOSDriver);
					}
		
				}
		}
		
		
		iOSDriver = null;
		androidDriver = null;
		whileLabel = 0;
		countLabel = 5;
		
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				
				System.out.println("========== Step 4.2.1: setup Android driver ===========");
				androidDriver= this.comSUT.setupAndroidAppiumConnectionWithApp();
				System.out.println("========== Step 4.2.2: Click (tap) Android element ===========");
//				this.comSUT.clickAndroidGUIElement(androidDriver, classNameAndroid, orderNumAndroid, selectedPossibleRelation.textSimilarity);
				System.out.println("========== Step 4.2.3: take Android page source code ===========");
				this.comSUT.takeScreenPageSource(androidDriver, fileFolderName + "Android/" + relationOrder + "_" + classNameAndroid + "[" + orderNumAndroid + "]");
				System.out.println("========== Step 4.2.4: take Android page screen ===========");
				this.comSUT.takeScreenShot(androidDriver, fileFolderName + "Android/" + relationOrder + "_" + classNameAndroid + "[" + orderNumAndroid + "]");
				
				this.comSUT.resetAndroidApp(androidDriver);
				
				} catch (Exception ex) {
					ex.printStackTrace();
					if(countLabel > 0) {
						whileLabel = 0;
						countLabel --;
						System.out.println("Retry " + (5 - countLabel) + " to get current Android page.");
						this.comSUT.resetAndroidApp(androidDriver);
						
					}
					else {
						System.out.println("Fail to getting current Android page.");
						this.comSUT.resetAndroidApp(androidDriver);
			
					}
		
				}
		}
		
		
		
//		while(whileLabel == 0) {
//			try {
//				whileLabel = 1;
//				
//				
//				System.out.println("========== Step 1.1: setup iOS driver ===========");
//				iOSDriver= this.comSUT.setupIOSAppiumConnectionWithApp();
//				System.out.println("========== Step 1.2: setup Android driver ===========");
//				androidDriver= this.comSUT.setupAndroidAppiumConnectionWithApp();
//				
//				System.out.println("========== Step 2.1: take the source code of the iOS root page  ===========");
//				this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName+"iOS/"+relationOrder+"_rootPage");
//				System.out.println("========== Step 2.2: take the source code of the Android root page ===========");
//				this.comSUT.takeScreenPageSource(androidDriver, fileFolderName+"Android/"+relationOrder+"_rootPage");
//				
//				System.out.println("========== Step 3.1: read the source code of the iOS and Android root page  ===========");
//				iOSXCUITestPage appiOSRootPage = GUIPageXMLFileReader.readiOSPageXMLFile(fileFolderName+"iOS/"+relationOrder+"_rootPage.xml");
//				AndroidGUIPage appAndroidRootPage = GUIPageXMLFileReader.readAndroidPageXMLFile(fileFolderName+"Android/"+relationOrder+"_rootPage.xml");
//				
//				System.out.println("========== Step 3.2: calculate the relaton list based on the current iOS and Android root page  ===========");
//				List<IOSElement2AndroidElementRelation> currentRelationList = mappingGUIPageOptSet.findConfirmedCorrespondingControlsfromTwoCorrespondingPages(appiOSRootPage, appAndroidRootPage);
//				System.out.println("Length of the current mapping relation list: " + currentRelationList.size());
//				
//				System.out.println("========== Step 3.3: choose one new possible page mapping relaton in order ===========");
//				System.out.println("Length of the original whole list: " + wholeList.size());
//				IOSElement2AndroidElementRelation selectedPossibleRelation = mappingGUIPageOptSet.chooseOneNewPossiblePageMappingRelation(wholeList, currentRelationList);
//				if(selectedPossibleRelation != null) {
//					wholeList.add(selectedPossibleRelation);
//				}
//				
//				
//				String classNameIOS = selectedPossibleRelation.iOSPageElement.EHead;
//				int orderNumIOS = selectedPossibleRelation.iOSPageElement.locationTypeOrder;
//				String classNameAndroid = selectedPossibleRelation.androidPageElement.EHead;
//				int orderNumAndroid = selectedPossibleRelation.androidPageElement.locationTypeOrder;
//				
//				System.out.println("========== Step 4.1.1: Click (tap) Android element ===========");
//				this.comSUT.clickAndroidGUIElement(androidDriver, classNameAndroid, orderNumAndroid, selectedPossibleRelation.textSimilarity);
//				System.out.println("========== Step 4.1.2: take Android page source code ===========");
//				this.comSUT.takeScreenPageSource(androidDriver, fileFolderName + "Android/" + relationOrder + "_" + classNameAndroid + "[" + orderNumAndroid + "]");
//				System.out.println("========== Step 4.1.3: take Android page screen ===========");
//				this.comSUT.takeScreenShot(androidDriver, fileFolderName + "Android/" + relationOrder + "_" + classNameAndroid + "[" + orderNumAndroid + "]");
//				
//				this.comSUT.resetAndroidApp(androidDriver);
//				
//				System.out.println("========== Step 4.2.1: Click (tap) iOS element ===========");
//				this.comSUT.clickIOSGUIElement(iOSDriver, classNameIOS, orderNumIOS, selectedPossibleRelation.textSimilarity);
//				System.out.println("========== Step 4.2.2: take iOS page source code ===========");
//				this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName + "iOS/" + relationOrder + classNameIOS + "[" + orderNumIOS + "]");
//				System.out.println("========== Step 4.2.3: take iOS page screen ===========");
//				this.comSUT.takeScreenShot(iOSDriver, fileFolderName + "iOS/" + relationOrder + classNameIOS + "[" + orderNumIOS + "]");
//				
//				this.comSUT.resetIOSApp(iOSDriver);
//				
//				
//			} catch (Exception ex) {
//				ex.printStackTrace();
//				if(countLabel > 0) {
//					whileLabel = 0;
//					countLabel --;
//					System.out.println("Retry " + (8 - countLabel) + " to get iOS and Android page.");
//					this.comSUT.resetIOSApp(iOSDriver);
//					this.comSUT.resetAndroidApp(androidDriver);
//					
//				}
//				else {
//					System.out.println("Fail to getting iOS and Android  page.");
//					this.comSUT.resetIOSApp(iOSDriver);
//					this.comSUT.resetAndroidApp(androidDriver);
//					
//				}
//				
//			}
//		}
		
		
	}
	
	
	
	
	public void takeSelectedNumberofiOSPagesInfoInvolvingManualOpt(String fileFolderName, String appName, int timeInterval, int numberOfPages) {
		
		IOSDriver iOSDriver = null;
		int whileLabel = 0;
		int countLabel = 5;
		
		int relationOrder = 0;
		
		iOSDriver= this.comSUT.setupIOSAppiumConnectionWithApp();
		
		for(; relationOrder < numberOfPages; relationOrder ++) {
			
			System.out.println("########### After " + timeInterval + "s, please prepare the " + relationOrder + "th target App page ......");
			
			try { Thread.sleep( timeInterval*1000 );
			} catch (InterruptedException e) { e.printStackTrace(); }
			
			System.out.println("/////////// End of " + timeInterval + "s, the " + relationOrder + "th App page information will be collected immediately ......");
			
			whileLabel = 1;
			while(whileLabel == 1) {
				
				try {
					this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName + "iOS/" + relationOrder);
					this.comSUT.takeScreenShot(iOSDriver, fileFolderName + "iOS/" + relationOrder);
					
				} catch (Exception ex) {
					ex.printStackTrace();
					whileLabel = 0;
					if(countLabel > 0) {
						countLabel --;
						System.out.println("Retry " + (5 - countLabel) + " to get the " + relationOrder + "th iOS page.");
						this.comSUT.resetIOSApp(iOSDriver);
						whileLabel = 1;
						iOSDriver= this.comSUT.setupIOSAppiumConnectionWithApp();
					}
					else {
						System.out.println("Fail to getting the " + relationOrder + "th iOS page.");
						this.comSUT.resetIOSApp(iOSDriver);
					}
					
				}
				whileLabel = 0;
			}
			
		}
		
		this.comSUT.resetIOSApp(iOSDriver);
		
		
		
	}
	
	public void takeSelectedNumberofAndroidPagesInfoInvolvingManualOpt(String fileFolderName, String appName, int timeInterval, int numberOfPages) {
		
		AndroidDriver androidDriver = null;
		int whileLabel = 0;
		int countLabel = 5;
		
		int relationOrder = 0;
		
		androidDriver= this.comSUT.setupAndroidAppiumConnectionWithApp();
		
		for(; relationOrder < numberOfPages; relationOrder ++) {
			
			System.out.println("########### After " + timeInterval + "s, please prepare the " + relationOrder + "th target App page ......");
			
			try { Thread.sleep( timeInterval*1000 );
			} catch (InterruptedException e) { e.printStackTrace(); }
			
			System.out.println("/////////// End of " + timeInterval + "s, the " + relationOrder + "th App page information will be collected immediately ......");
			
			whileLabel = 1;
			while(whileLabel == 1) {
				
				try {
					this.comSUT.takeScreenPageSource(androidDriver, fileFolderName + "Android/" + relationOrder);
					this.comSUT.takeScreenShot(androidDriver, fileFolderName + "Android/" + relationOrder);
					
				} catch (Exception ex) {
					ex.printStackTrace();
					whileLabel = 0;
					if(countLabel > 0) {
						countLabel --;
						System.out.println("Retry " + (5 - countLabel) + " to get the " + relationOrder + "th Android page.");
						this.comSUT.resetAndroidApp(androidDriver);
						whileLabel = 1;
						androidDriver= this.comSUT.setupAndroidAppiumConnectionWithApp();
					}
					else {
						System.out.println("Fail to getting the " + relationOrder + "th Android page.");
						this.comSUT.resetAndroidApp(androidDriver);
					}
					
				}
				whileLabel = 0;
			}
			
		}
		
		this.comSUT.resetAndroidApp(androidDriver);
		
		
		
	}
	
	
	public boolean parallelExectueiOSandAndroidElements2TriggerNewPage_2(MapCorrespondingGUIPage_20200403 mappingGUIPageOptSet, List<IOSElement2AndroidElementRelation> wholeList, String fileFolderName, int relationOrder) {
		
		
		///////////////////
		System.out.println("========== Step 1.1: read the source code of the iOS and Android root page  ===========");
		iOSXCUITestPage appiOSRootPage = null;
		AndroidGUIPage appAndroidRootPage = null;
		if(relationOrder == 0) {
			appiOSRootPage = GUIPageXMLFileReader.readiOSPageXMLFile(fileFolderName+"iOS/"+"rootPage.xml");
			appAndroidRootPage = GUIPageXMLFileReader.readAndroidPageXMLFile(fileFolderName+"Android/"+"rootPage.xml");
		}
		else {
			appiOSRootPage = GUIPageXMLFileReader.readiOSPageXMLFile(fileFolderName+"iOS/"+relationOrder+"_rootPage.xml");
			appAndroidRootPage = GUIPageXMLFileReader.readAndroidPageXMLFile(fileFolderName+"Android/"+relationOrder+"_rootPage.xml");
			if((appiOSRootPage == null) ) {
				appiOSRootPage = GUIPageXMLFileReader.readiOSPageXMLFile(fileFolderName+"iOS/"+"rootPage.xml");
			}
			if(appAndroidRootPage == null) {
				appAndroidRootPage = GUIPageXMLFileReader.readAndroidPageXMLFile(fileFolderName+"Android/"+"rootPage.xml");
			}
			
		}
		
		System.out.println("========== Step 1.2: calculate the relaton list based on the current iOS and Android root page  ===========");
		
		List<IOSElement2AndroidElementRelation> currentRelationList = mappingGUIPageOptSet.findConfirmedCorrespondingControlsfromTwoCorrespondingPages(appiOSRootPage, appAndroidRootPage);
		System.out.println("Length of the current mapping relation list: " + currentRelationList.size());
		
		System.out.println("========== Step 1.3: choose one new possible page mapping relaton in order ===========");
		System.out.println("Length of the original whole list: " + wholeList.size());
		IOSElement2AndroidElementRelation selectedPossibleRelation = mappingGUIPageOptSet.chooseOneNewPossiblePageMappingRelation(wholeList, currentRelationList);
		if(selectedPossibleRelation != null) {
			wholeList.add(selectedPossibleRelation);
		}
		else {
			System.out.println("========== The candidate possible relation is NULL, then skip this turn. ==========");
			return false;
		}
		
		String classNameIOS = selectedPossibleRelation.iOSPageElement.EHead;
		int orderNumIOS = selectedPossibleRelation.iOSPageElement.locationTypeOrder;
		String classNameAndroid = selectedPossibleRelation.androidPageElement.EHead;
		int orderNumAndroid = selectedPossibleRelation.androidPageElement.locationTypeOrder;
		
		int x1 = selectedPossibleRelation.iOSPageElement.Ex;
		int w = selectedPossibleRelation.iOSPageElement.Ewidth;
		int y1 = selectedPossibleRelation.iOSPageElement.Ey;
		int h = selectedPossibleRelation.iOSPageElement.Eheight;
		
		int xa1 = selectedPossibleRelation.androidPageElement.Ex1;
		int xa2 = selectedPossibleRelation.androidPageElement.Ex2;
		int ya1 = selectedPossibleRelation.androidPageElement.Ey1;
		int ya2 = selectedPossibleRelation.androidPageElement.Ey2;
		
		///////////////////
		
		IOSDriver iOSDriver = null;
		AndroidDriver androidDriver = null;
		int whileLabel = 0;
		int countLabel = 4;
		
		
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				
				System.out.println("========== Step 2.1: setup iOS driver ===========");
				iOSDriver= this.comSUT.setupIOSAppiumConnectionWithApp();
				System.out.println("========== Step 2.1.1: take the source code of the current iOS root page for the next relation  ===========");
				this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName+"iOS/"+(relationOrder+1)+"_rootPage");
				System.out.println("========== Step 2.2: Click (tap) iOS element ===========");
				this.comSUT.clickIOSGUIElement(iOSDriver, classNameIOS, orderNumIOS, selectedPossibleRelation.textSimilarity, x1, w, y1, h);
				System.out.println("========== Step 2.3: take iOS page source code ===========");
				this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName + "iOS/" + relationOrder + "_" + classNameIOS + "[" + orderNumIOS + "]");
				System.out.println("========== Step 2.4: take iOS page screen ===========");
				this.comSUT.takeScreenShot(iOSDriver, fileFolderName + "iOS/" + relationOrder + "_" + classNameIOS + "[" + orderNumIOS + "]");
				
				this.comSUT.resetIOSApp(iOSDriver);
				
				} catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("When setuping iOS part: " + ex.toString());
					if(countLabel > 0) {
						whileLabel = 0;
						countLabel --;
						System.out.println("Retry " + (4 - countLabel) + " to get current iOS page.");
						this.comSUT.resetIOSApp(iOSDriver);
					}
					else {
						System.out.println("Fail to getting current iOS page.");
						this.comSUT.resetIOSApp(iOSDriver);
					}
		
				}
		}
		
		
		iOSDriver = null;
		androidDriver = null;
		whileLabel = 0;
		countLabel = 4;
		
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				
				System.out.println("========== Step 3.1: setup Android driver ===========");
				androidDriver= this.comSUT.setupAndroidAppiumConnectionWithApp();
				System.out.println("========== Step 3.1.1: take the source code of the current Android root page for the next relation ===========");
				this.comSUT.takeScreenPageSource(androidDriver, fileFolderName+"Android/"+(relationOrder+1)+"_rootPage");
				System.out.println("========== Step 3.2: Click (tap) Android element ===========");
				this.comSUT.clickAndroidGUIElement(androidDriver, classNameAndroid, orderNumAndroid, selectedPossibleRelation.textSimilarity, xa1, xa2, ya1, ya2);
				System.out.println("========== Step 3.3: take Android page source code ===========");
				this.comSUT.takeScreenPageSource(androidDriver, fileFolderName + "Android/" + relationOrder + "_" + classNameAndroid + "[" + orderNumAndroid + "]");
				System.out.println("========== Step 3.4: take Android page screen ===========");
				this.comSUT.takeScreenShot(androidDriver, fileFolderName + "Android/" + relationOrder + "_" + classNameAndroid + "[" + orderNumAndroid + "]");
				
				this.comSUT.resetAndroidApp(androidDriver);
				
				} catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("When setuping Android part: " + ex.toString());
					if(countLabel > 0) {
						whileLabel = 0;
						countLabel --;
						System.out.println("Retry " + (4 - countLabel) + " to get current Android page.");
						this.comSUT.resetAndroidApp(androidDriver);
						
					}
					else {
						System.out.println("Fail to getting current Android page.");
						this.comSUT.resetAndroidApp(androidDriver);
			
					}
		
				}
		}
		
		
		return true;
		
	}
	
	
	
	
	
	public void testClickIOSElement(String className, int orderNum, String fileFolderName) {
		
		IOSDriver iOSDriver = null;
		
		int whileLabel = 0;
		int countLabel = 5;
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				System.out.println("========== Step 1 ===========");
				iOSDriver= this.comSUT.setupIOSAppiumConnectionWithApp();
				System.out.println("========== Step save root page ===========");
				this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName + "rootPage");
				System.out.println("========== Step save root page pic ===========");
				this.comSUT.takeScreenShot(iOSDriver, fileFolderName + "rootPage");
				
				iOSXCUITestPage pageTemp = GUIPageXMLFileReader.readiOSPageXMLFile(fileFolderName + "rootPage.xml");
				iOSXCUITestElement temp = iOSXCUITestPage.findPageTreeNodebyAttributeLabel(pageTemp, "我的");
				
				iOSXCUITestElement.printElement(temp);
				
				iOSXCUITestPage.printPageTree(pageTemp);
				
				System.out.println("========== Step 2 ===========");
				this.comSUT.clickIOSGUIElement_testCO(iOSDriver, temp.EHead, temp.locationTypeOrder);
				System.out.println("========== Step 3 ===========");
				this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName + className + "[" + orderNum + "]");
				System.out.println("========== Step 4 ===========");
				this.comSUT.takeScreenShot(iOSDriver, fileFolderName + className + "[" + orderNum + "]");
				this.comSUT.resetIOSApp(iOSDriver);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Overall exception!::" + className + "[" + orderNum + "]");
				if(countLabel > 0) {
					whileLabel = 0;
					countLabel --;
					System.out.println("Retry to setup IOS driver and click IOS Page Element " + (5 - countLabel) + ".");
					this.comSUT.resetIOSApp(iOSDriver);				
				}
				else {
					System.out.println("Warning: Fail to setuping IOS driver or clicking the IOS Page Element!");
					this.comSUT.resetIOSApp(iOSDriver);
				}
				
			}
		}
		
		
	}
	
	public void testClickAndroidElementTriggerNewPage(String className, int orderNum, String fileFolderName) {
		
		AndroidDriver androidDriver = null;
		System.out.println("Tigger Element::" + className + "[" + orderNum + "]");
		
		int whileLabel = 0;
		int countLabel = 5;
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				System.out.println("========== Step 1 ===========");
				androidDriver= this.comSUT.setupAndroidAppiumConnectionWithApp();
				
				System.out.println("========== Step save root page ===========");
				this.comSUT.takeScreenPageSource(androidDriver, fileFolderName + "rootPage");
				System.out.println("========== Step save root page pic ===========");
				this.comSUT.takeScreenShot(androidDriver, fileFolderName + "rootPage");
				
				AndroidGUIPage pageTemp = GUIPageXMLFileReader.readAndroidPageXMLFile(fileFolderName + "rootPage.xml");
				AndroidGUIElement temp = AndroidGUIPage.findPageTreeNodebyAttributeText(pageTemp, "我的");
				
				AndroidGUIElement.printElement(temp);
				
				AndroidGUIPage.printPageTree(pageTemp);
				
				
				
				System.out.println("========== Step 2 ===========");
				this.comSUT.clickAndroidGUIElement_testCO(androidDriver, temp.EHead, temp.locationTypeOrder);
				System.out.println("========== Step 3 ===========");
				this.comSUT.takeScreenPageSource(androidDriver, fileFolderName + className + "[" + orderNum + "]");
				System.out.println("========== Step 4 ===========");
				this.comSUT.takeScreenShot(androidDriver, fileFolderName + className + "[" + orderNum + "]");
				this.comSUT.resetAndroidApp(androidDriver);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Overall exception!::" + className + "[" + orderNum + "]");
				if(countLabel > 0) {
					whileLabel = 0;
					countLabel --;
					System.out.println("Retry to setup Android driver and click Android Page Element " + (5 - countLabel) + ".");
					this.comSUT.resetAndroidApp(androidDriver);				
				}
				else {
					System.out.println("Warning: Fail to Setuping Android driver or clicking Android Page Element!");
					this.comSUT.resetAndroidApp(androidDriver);
				}
				
			}
		}
		
	}
	
	
	public void clickIOSElementTriggerNewPage(String className, int orderNum, String fileFolderName, String originalSameText) {
		
		System.out.println("Tigger Element::" + className + "[" + orderNum + "]");
		IOSDriver iOSDriver = null;
		
		int whileLabel = 0;
		int countLabel = 5;
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				System.out.println("========== Step 1 ===========");
				iOSDriver= this.comSUT.setupIOSAppiumConnectionWithApp();
				System.out.println("========== Step 2 ===========");
				//this.comSUT.clickIOSGUIElement(iOSDriver, className, orderNum, originalSameText);
				System.out.println("========== Step 3 ===========");
				this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName + className + "[" + orderNum + "]");
				System.out.println("========== Step 4 ===========");
				this.comSUT.takeScreenShot(iOSDriver, fileFolderName + className + "[" + orderNum + "]");
				this.comSUT.resetIOSApp(iOSDriver);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Overall exception!::" + className + "[" + orderNum + "]");
				if(countLabel > 0) {
					whileLabel = 0;
					countLabel --;
					System.out.println("Retry to setup IOS driver and click IOS Page Element " + (5 - countLabel) + ".");
					this.comSUT.resetIOSApp(iOSDriver);				
				}
				else {
					System.out.println("Warning: Fail to setuping IOS driver or clicking the IOS Page Element!");
					this.comSUT.resetIOSApp(iOSDriver);
				}
				
			}
		}
		
		/*
		int whileLabel = 0;
		int countLabel = 3;
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				iOSDriver= this.comSUT.setupIOSAppiumConnectionWithApp();
			} catch (Exception ex) {
				ex.printStackTrace();
				if(countLabel > 0) {
					whileLabel = 0;
					countLabel --;
					System.out.println("Retry to setup IOS driver " + (3 - countLabel) + ".");
					this.comSUT.resetIOSApp(iOSDriver);
				}
				else {
					System.out.println("Warning: Fail to Setuping IOS driver!");
					this.comSUT.resetIOSApp(iOSDriver);
				}
				
			}
		}
			//iOSDriver = this.comSUT.setupIOSAppiumConnectionWithApp();
		try {	
			this.comSUT.clickIOSGUIElement(iOSDriver, className, orderNum);
			this.comSUT.takeScreenPageSource(iOSDriver, fileFolderName + className + "[" + orderNum + "]");
			this.comSUT.takeScreenShot(iOSDriver, fileFolderName + className + "[" + orderNum + "]");
			this.comSUT.resetIOSApp(iOSDriver);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Overall exception!::" + className + "[" + orderNum + "]");
			this.comSUT.resetIOSApp(iOSDriver);
		}
		*/
		
	}
	
	public void clickAndroidElementTriggerNewPage(String className, int orderNum, String fileFolderName, String originalSameText) {
		
		AndroidDriver androidDriver = null;
		System.out.println("Tigger Element::" + className + "[" + orderNum + "]");
		
		int whileLabel = 0;
		int countLabel = 5;
		while(whileLabel == 0) {
			try {
				whileLabel = 1;
				System.out.println("========== Step 1 ===========");
				androidDriver= this.comSUT.setupAndroidAppiumConnectionWithApp();
				System.out.println("========== Step 2 ===========");
//				this.comSUT.clickAndroidGUIElement(androidDriver, className, orderNum, originalSameText);
				System.out.println("========== Step 3 ===========");
				this.comSUT.takeScreenPageSource(androidDriver, fileFolderName + className + "[" + orderNum + "]");
				System.out.println("========== Step 4 ===========");
				this.comSUT.takeScreenShot(androidDriver, fileFolderName + className + "[" + orderNum + "]");
				this.comSUT.resetAndroidApp(androidDriver);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Overall exception!::" + className + "[" + orderNum + "]");
				if(countLabel > 0) {
					whileLabel = 0;
					countLabel --;
					System.out.println("Retry to setup Android driver and click Android Page Element " + (5 - countLabel) + ".");
					this.comSUT.resetAndroidApp(androidDriver);				
				}
				else {
					System.out.println("Warning: Fail to Setuping Android driver or clicking Android Page Element!");
					this.comSUT.resetAndroidApp(androidDriver);
				}
				
			}
		}
		//androidDriver = this.comSUT.setupAndroidAppiumConnectionWithApp();
		/*
		int whileLabel_S2 = 0;
		int countLabel_S2 = 3;
		while(whileLabel_S2 == 0) {
			try {
				whileLabel_S2 = 1;
				System.out.println("========== Step 2 [" + whileLabel_S2 + "] ===========");
				this.comSUT.clickAndroidGUIElement(androidDriver, className, orderNum);
			} catch (Exception ex) {
				ex.printStackTrace();
				if(countLabel_S2 > 0) {
					whileLabel_S2 = 0;
					countLabel_S2 --;
					System.out.println("Retry to click Android Page Element " + (3 - countLabel) + ".");
					//this.comSUT.resetAndroidApp(androidDriver);
				}
				else {
					System.out.println("Warning: Fail to Click Android Page Element!");
					//this.comSUT.resetAndroidApp(androidDriver);
				}
				
			}
		}
		*/
			
		/*
		try {
			System.out.println("========== Step 3 ===========");
			this.comSUT.takeScreenPageSource(androidDriver, fileFolderName + className + "[" + orderNum + "]");
			System.out.println("========== Step 4 ===========");
			this.comSUT.takeScreenShot(androidDriver, fileFolderName + className + "[" + orderNum + "]");
			this.comSUT.resetAndroidApp(androidDriver);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Overall exception!::" + className + "[" + orderNum + "]");
			comSUT.resetAndroidApp(androidDriver);
		}
		*/
	}
	
	
	
	public static void outputConsole2File(String fileName) {
		String oC2F = fileName + "log.txt";
		File fileforOC2F = new File(oC2F);
		if(!fileforOC2F.getParentFile().exists()) {fileforOC2F.getParentFile().mkdirs();}
		OutConsole2File outConsole2File = new OutConsole2File();
		outConsole2File.setOutConsole2File(oC2F);
	}
	
	
	public static void main(String args[]) {
		ApplicationUnderTest selectedApp = new ApplicationUnderTest();
		
		//iOS
		//selectedApp.exploreIOSAppLevelOnePagesFromRootPage("/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/AppName_demo_SohuIOS_1/iOS/", "");
		//System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		//sina
		selectedApp.iOSdevice_iphone6.put("bundleId", "com.tencent.info");
		selectedApp.comSUT.setPlatformInformationIOS(selectedApp.iOSdevice_iphone6);
		selectedApp.exploreIOSAppLevelOnePagesFromRootPage("/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/AppName_demo_TencentNewsIOS_1/iOS/", "");
		
		
		
		//Android
		//selectedApp.exploreAndroidAppLevelOnePagesFromRootPage("/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/AppName_demo_Sina_0/Android/", "");
		//System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
	}
}
