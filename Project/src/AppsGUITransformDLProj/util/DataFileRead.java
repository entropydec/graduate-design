package AppsGUITransformDLProj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import AppsGUITransformDLProj.GUI.AndroidGUIElement;
import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import AppsGUITransformDLProj.GUI.iOSXCUITestElement;
import AppsGUITransformDLProj.GUI.iOSXCUITestPage;
import AppsGUITransformDLProj.UserInvolvementGUI4Mapping.IOSPage2AndroidElementRelationWithLabel;
import AppsGUITransformDLProj.UserInvolvementGUI4Mapping.MappingRelationCheckerInvolveUser;

public class DataFileRead {
	
	//read from Data File
	
	public static void readAppInformationAndPageMappingRelationsFromDataFile(String pMRDataFile, MappingRelationCheckerInvolveUser mRCIU) {
		
		List<String> appNameAndLocation = new ArrayList<String>();
		List<List<IOSPage2AndroidPageRelation>> pageMappingRelations4AllApps = new ArrayList<List<IOSPage2AndroidPageRelation>>();
		
		List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = null;
		
		
		File file = new File(pMRDataFile);
		BufferedReader reader = null;
		String tempString = null;
		
		try {
			
			reader = new BufferedReader(new FileReader(file));
			
			while((tempString = reader.readLine()) != null) {
				
				System.out.println(tempString);
				
				if(tempString == "") {
					break;
				}
				
				
				if(tempString.startsWith("[AppPagesLocation]")) {
					
					String[] args = tempString.split(" ");
					appNameAndLocation.add(args[1]);
					pageMappingRelationListTemp = new ArrayList<IOSPage2AndroidPageRelation>();
					pageMappingRelations4AllApps.add(pageMappingRelationListTemp);
					
				}
				else if(tempString.startsWith("///////[Relations]")) {
					
					IOSPage2AndroidPageRelation pMRelationTemp = new IOSPage2AndroidPageRelation();
					
					String[] args = tempString.split(" ");
					String[] idAndiOS = args[1].split("\\)");
					String[] idRough = idAndiOS[0].split("\\(");
					int identifierTemp = Integer.parseInt(idRough[1]);
					
					pMRelationTemp.identifier = identifierTemp;
					pMRelationTemp.iOSPageFileName = idAndiOS[1];
					pMRelationTemp.androidPageFileName = args[3];
					
					pageMappingRelationListTemp.add(pMRelationTemp);
				}
				else {
					System.out.println("Error occur in the PMR Data File");
				}
				
				
				
				
				
				
				
			}
			
			
			
			
			
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("===== Error occur when reading CMR from DF: " + pMRDataFile + " =====");
		}
		
		
		mRCIU.allAppsInfo = appNameAndLocation;
		mRCIU.pageMappingRelations4AllAppsFromInitialFile = pageMappingRelations4AllApps;
	}
	
	public static void readAppInformationAndPageMappingRelations2MergeMultiKindAppsFromDataFile(String pMRDataFileDir, MappingRelationCheckerInvolveUser mRCIU, List<String> easyTestSetDataWithLabel_StringVersion) {
		
		List<String> appNameAndLocation = new ArrayList<String>();
		List<List<IOSPage2AndroidPageRelation>> pageMappingRelations4AllApps = new ArrayList<List<IOSPage2AndroidPageRelation>>();
//		List<IOSPage2AndroidElementRelationWithLabel> easyTestSetDataWithLabel = new ArrayList<IOSPage2AndroidElementRelationWithLabel>();
//		List<String> easyTestSetDataWithLabel_StringVersion = new ArrayList<String>();
		
		
		mRCIU.allAppsInfo = appNameAndLocation;
		mRCIU.pageMappingRelations4AllAppsFromInitialFile = pageMappingRelations4AllApps;
		
		
		File dirFile = new File(pMRDataFileDir);
		if(!dirFile.isDirectory()) {
			if(dirFile.isFile()) {
				System.out.println("#################### Error: basic dir isFile ####################");
			}
			System.out.println("#################### Error: basic dir is not file or dir ####################");
			return ;
		}
		
		
		String[] fileList = dirFile.list();
		int lenDirFile = fileList.length;
		for(int i = 0; i < lenDirFile; i ++) {
			
			//Categories of App types
			String pMRDataFileCategoriesDir = pMRDataFileDir + fileList[i] + "/";
//			System.out.println(pMRDataFileCategoriesDir);
			if(fileList[i].startsWith(".") || fileList[i].startsWith("TestSetWithLabelinProcess")) {
				continue;
			}
			
//			System.out.println("# For Check: " + pMRDataFileCategoriesDir);
			File dirPMRDataCategoriesFile = new File(pMRDataFileCategoriesDir);
			if(!dirPMRDataCategoriesFile.isDirectory()) {
				if(dirPMRDataCategoriesFile.isFile()) {
					System.out.println("#################### Error: basic dir isFile ####################");
				}
				System.out.println("#################### Error: basic dir is not file or dir ####################");
				return ;
			}
			String[] fileAppList = dirPMRDataCategoriesFile.list();
			int lenAppListDir = fileAppList.length;
			for(int j = 0; j < lenAppListDir; j ++) {
				
				//Apps in one category
				String pMRDataFileAppsDir = pMRDataFileCategoriesDir + fileAppList[j] + "/";
//				System.out.println(pMRDataFileAppsDir);
				if(fileAppList[j].startsWith(".")) {
					continue;
				}
				
				File dirPMRDataAppDetailsFile = new File(pMRDataFileAppsDir);
				if(!dirPMRDataAppDetailsFile.isDirectory()) {
					if(dirPMRDataAppDetailsFile.isFile()) {
						System.out.println("#################### Error: basic dir isFile ####################");
					}
					System.out.println("#################### Error: basic dir is not file or dir ####################");
					return ;
				}
				String[] fileAppDetailsList = dirPMRDataAppDetailsFile.list();
				int lenAppDetailsDir = fileAppDetailsList.length;
				for(int k = 0; k < lenAppDetailsDir; k ++) {
					
					if(fileAppDetailsList[k].startsWith("Mapping")) {
						//Find the data file of every App
						System.out.println(pMRDataFileAppsDir + fileAppDetailsList[k]);
						
						String revisedMatchedPageAppLocation_basic = pMRDataFileAppsDir;
						
						String pMRDataFileFullPath = pMRDataFileAppsDir + fileAppDetailsList[k] + "/PageMappingRelations.txt";
						
						
						List<IOSPage2AndroidPageRelation> pageMappingRelationListTemp = null;
						
						File file = new File(pMRDataFileFullPath);
						BufferedReader reader = null;
						String tempString = null;
						
						//Since current file system collects information app by app
						String currentAppLocation = null;
						
						try {
							
							reader = new BufferedReader(new FileReader(file));
							
							while((tempString = reader.readLine()) != null) {
								
//								System.out.println(tempString);
								
								if(tempString == "") {
									break;
								}
								
								
								
								
								if(tempString.startsWith("[AppPagesLocation]")) {
									
									String[] args = tempString.split(" ");
									
									String[] args_revisedAppNameAndLocation = args[1].split("/");
									String revisedMatchedPageAppLocation_full = revisedMatchedPageAppLocation_basic 
											+ args_revisedAppNameAndLocation[args_revisedAppNameAndLocation.length - 1] + "/"
											;
									System.out.println("XXX: " + revisedMatchedPageAppLocation_full);
									
									appNameAndLocation.add(revisedMatchedPageAppLocation_full);
									pageMappingRelationListTemp = new ArrayList<IOSPage2AndroidPageRelation>();
									pageMappingRelations4AllApps.add(pageMappingRelationListTemp);
									
									currentAppLocation = revisedMatchedPageAppLocation_full;
									
								}
								else if(tempString.startsWith("///////[Relations]")) {
									
									IOSPage2AndroidPageRelation pMRelationTemp = new IOSPage2AndroidPageRelation();
									
									String[] args = tempString.split(" ");
									String[] idAndiOS = args[1].split("\\)");
									String[] idRough = idAndiOS[0].split("\\(");
									int identifierTemp = Integer.parseInt(idRough[1]);
									
									pMRelationTemp.identifier = identifierTemp;
									pMRelationTemp.iOSPageFileName = idAndiOS[1];
									pMRelationTemp.androidPageFileName = args[3];
									
									pageMappingRelationListTemp.add(pMRelationTemp);
								}
								else {
									System.out.println("Error occur in the PMR Data File");
								}
								
								
								
								
								
								
								
							}
							
							
							
							
							
						} catch(Exception ex) {
							ex.printStackTrace();
//							System.out.println("===== Error occur when reading CMR from DF: " + pMRDataFile + " =====");
						}
						
						
						String cMRDataFileFullPath = pMRDataFileAppsDir + fileAppDetailsList[k] + "/TestSetWithLabelinProcess.txt";
						
//						easyTestSetDataWithLabel
						
						File file_CMR = new File(cMRDataFileFullPath);
						
						try {
							
							reader = new BufferedReader(new FileReader(file_CMR));
							
							while((tempString = reader.readLine()) != null) {
								
//								System.out.println(tempString);
								
								if(tempString == "") {
									break;
								}
								
								String[] args = tempString.split(" ");// 0: ios control, 1: android control, 2: app name, 3: iOS page location, 4: Android page location, 5: type
								String iosEle = args[0];
								String[] iosArgs = iosEle.split("\\[");
								String iosHead = iosArgs[0];
								String[] iosOrder = iosArgs[1].split("\\]");
								int iosTypeOrder = Integer.parseInt(iosOrder[0]);
								
								String androidEle = args[1];
								String[] androidArgs = androidEle.split("\\[");
								String androidHead = androidArgs[0];
								String[] androidOrder = androidArgs[1].split("\\]");
								int androidTypeOrder = Integer.parseInt(androidOrder[0]);
								
								String appName = args[2];
								
								String iosPageLocation = args[3];
								String[] iosPageArgs = iosPageLocation.split("/");
								String iosPageLocationEnd = iosPageArgs[iosPageArgs.length-1];
								String iosPageLocationCrossPlatformAdaptation = currentAppLocation + "iOS/" + iosPageLocationEnd;
								
								String androidPageLocation = args[4];
								String[] androidPageArgs = androidPageLocation.split("/");
								String androidPageLocationEnd = androidPageArgs[androidPageArgs.length-1];
								String androidPageLocationCrossPlatformAdaptation = currentAppLocation + "Android/" + androidPageLocationEnd;
								
//								IOSPage2AndroidElementRelationWithLabel tempCMRwithLabel = new IOSPage2AndroidElementRelationWithLabel();
								
								String recorder = iosEle + " " + androidEle + " " + appName + " " + iosPageLocationCrossPlatformAdaptation + " " + androidPageLocationCrossPlatformAdaptation;
								
								if(args.length > 5) {
									String e2eRelationType = args[5];
									
									recorder = recorder + " " + e2eRelationType;
									
									
									
									
									
									
									
//									for(int i = 0; i < lenofE2eRelationWithLabelList; i ++) {
//										if(easyTestSetDataWithoutLabelinCache.get(i).e2eRelation.iOSPageElement.EHead.equals(iosHead)
//												&& easyTestSetDataWithoutLabelinCache.get(i).e2eRelation.iOSPageElement.locationTypeOrder == iosTypeOrder
//												&& easyTestSetDataWithoutLabelinCache.get(i).e2eRelation.androidPageElement.EHead.equals(androidHead)
//												&& easyTestSetDataWithoutLabelinCache.get(i).e2eRelation.androidPageElement.locationTypeOrder == androidTypeOrder
//												&& easyTestSetDataWithoutLabelinCache.get(i).pageLocationEnd.equals(iosPageLocationEnd)
//												) {
//											easyTestSetDataWithoutLabelinCache.get(i).relationType = e2eRelationType;
//										}
//									}
								}
								
								easyTestSetDataWithLabel_StringVersion.add(recorder);
								
								
							}
							
							
							
						} catch(Exception ex) {
							ex.printStackTrace();
							//	System.out.println("===== Error occur when reading CMR from DF: " + pMRDataFile + " =====");
						}

						
						
						
					}
					
					
					
				}
				
				
				
				
				
			}
			
			
			
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	public static void readControlMappingRelationsFromDataFile(String controlMappingRelationsDataFile, IOSPage2AndroidPageRelation pageRelation) {
		
		System.out.println("===== read CMR from DF: " + controlMappingRelationsDataFile + " =====");
		
		List<IOSElement2AndroidElementRelation> e2eMappingRelations = new ArrayList<IOSElement2AndroidElementRelation>();
		
		File file = new File(controlMappingRelationsDataFile);
		BufferedReader reader = null;
		String tempString = null;
		
		try {
			
			reader = new BufferedReader(new FileReader(file));
			
			while((tempString = reader.readLine()) != null) {
				
				if(tempString == "") {
					break;
				}
				
				IOSElement2AndroidElementRelation e2eElementRelationTemp = new IOSElement2AndroidElementRelation();
				
				String[] args = tempString.split(" ");
				String iosEle = args[0];
				String[] iosArgs = iosEle.split("\\[");
				String iosHead = iosArgs[0];
				String[] iosOrder = iosArgs[1].split("\\]");
				int iosTypeOrder = Integer.parseInt(iosOrder[0]);
				
				String androidEle = args[3];
				String[] androidArgs = androidEle.split("\\[");
				String androidHead = androidArgs[0];
				String[] androidOrder = androidArgs[1].split("\\]");
				int androidTypeOrder = Integer.parseInt(androidOrder[0]);
				
				System.out.println("====== ios: " + iosHead + ", " + iosTypeOrder + "; android: " + androidHead + ", " + androidTypeOrder + " ======");
				
				e2eElementRelationTemp.iOSPageElement = iOSXCUITestPage.findPageTreeNodebyFileName(pageRelation.iOSPage, iosHead, iosTypeOrder);
				e2eElementRelationTemp.androidPageElement = AndroidGUIPage.findPageTreeNodebyFileName(pageRelation.androidPage, androidHead, androidTypeOrder);
				
				e2eMappingRelations.add(e2eElementRelationTemp);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("===== Error occur when reading CMR from DF: " + controlMappingRelationsDataFile + " =====");
		}
		
		pageRelation.matchElements = e2eMappingRelations;
		
	}
	
	
	public static void readUnmappedControlsFromDataFile(String unmappedControlsDataFile, IOSPage2AndroidPageRelation pageRelation) {
		
		System.out.println("===== read UCs from DF: " + unmappedControlsDataFile + " =====");
		
		List<iOSXCUITestElement> unmappediOSElementsTemp = new ArrayList<iOSXCUITestElement>();
		List<AndroidGUIElement> unmappedAndroidElementsTemp = new ArrayList<AndroidGUIElement>();
		
		File file = new File(unmappedControlsDataFile);
		BufferedReader reader = null;
		String tempString = null;
		
		int typeLabel = -1;
		
		try {
			
			reader = new BufferedReader(new FileReader(file));
			
			while((tempString = reader.readLine()) != null) {
				
				if(tempString == "") {
					break;
				}
				
				if(tempString.equals("[iOS]")) {
					typeLabel = 0;
				}
				else if(tempString.equals("[Android]")) {
					typeLabel = 1;
				}
				else {
					
					if(typeLabel == 0) {
						String[] args = tempString.split("\\]");
						String[] iosEle = args[0].split("\\[");
						String iosHead = iosEle[0];
						int iosOrder = Integer.parseInt(iosEle[1]);
						unmappediOSElementsTemp.add(iOSXCUITestPage.findPageTreeNodebyFileName(pageRelation.iOSPage, iosHead, iosOrder));
					}
					else if(typeLabel == 1) {
						String[] args = tempString.split("\\]");
						String[] androidEle = args[0].split("\\[");
						String androidHead = androidEle[0];
						int androidOrder = Integer.parseInt(androidEle[1]);
						unmappedAndroidElementsTemp.add(AndroidGUIPage.findPageTreeNodebyFileName(pageRelation.androidPage, androidHead, androidOrder));
						
					}
					else {}
					
				}
			}
			
			
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("===== Error occur when reading UCs from DF: " + unmappedControlsDataFile + " =====");
		}
		
		pageRelation.unmappedIOSElements_2 = unmappediOSElementsTemp;
		pageRelation.unmappedAndroidElements_2 = unmappedAndroidElementsTemp;
		
		
		
	}
	
	
	public static List<List<String>> readTestSetDataFromDataFile(String testsetDataFile) {
		
		File file = new File(testsetDataFile);
		BufferedReader reader = null;
		String tempString = null;
		
		List<List<String>> result = new ArrayList<List<String>>();
		
		try {
			
			reader = new BufferedReader(new FileReader(file));
			
			while((tempString = reader.readLine()) != null) {
				
				List<String> temp = new ArrayList<String>();
				String[] src = tempString.split(" ");
				int len = src.length;
				for(int i = 0; i < len; i ++) {
					temp.add(src[i]);
				}
				
				result.add(temp);
			}
			
			
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("===== Error occur when reading ETCs from DF: " + testsetDataFile + " =====");
		}
		
		
		return result;
	}
	
	public static List<String> readTestSetDataExecutionResultFromDataFile(String testsetExeResultDataFile){
		
		List<String> results = new ArrayList<String>();
		
		File file = new File(testsetExeResultDataFile);
		BufferedReader reader = null;
		String tempString = null;
		
		 try {
			 
			reader = new BufferedReader(new FileReader(file));
			int labelStart = 0;
			while((tempString = reader.readLine()) != null) {
				
				if(tempString.startsWith("0th")) {
					labelStart = 1;
				}
				if(labelStart == 1) {
					results.add(tempString);
				}
				
			}
			 
		 } catch(Exception ex) {
				ex.printStackTrace();
				System.out.println("===== Error occur when reading ETCs from DF: " + testsetExeResultDataFile + " =====");
			}
		
		
		return results;
	}
	
	
	public static void readEasyTestSetWithLabelFromDataFile(String easyTestSetDataFile, List<IOSPage2AndroidElementRelationWithLabel> easyTestSetDataWithoutLabelinCache) {
		
		System.out.println("===== read Label CMR from DF: " + easyTestSetDataFile + " =====");
		
		int lenofE2eRelationWithLabelList = easyTestSetDataWithoutLabelinCache.size();
		
		List<IOSElement2AndroidElementRelation> e2eMappingRelations = new ArrayList<IOSElement2AndroidElementRelation>();
		
		File file = new File(easyTestSetDataFile);
		BufferedReader reader = null;
		String tempString = null;
		
		try {
			
			reader = new BufferedReader(new FileReader(file));
			
			while((tempString = reader.readLine()) != null) {
				
				if(tempString == "") {
					break;
				}
				
				String[] args = tempString.split(" ");// 0: ios control, 1: android control, 2: app name, 3: iOS page location, 4: Android page location, 5: type
				String iosEle = args[0];
				String[] iosArgs = iosEle.split("\\[");
				String iosHead = iosArgs[0];
				String[] iosOrder = iosArgs[1].split("\\]");
				int iosTypeOrder = Integer.parseInt(iosOrder[0]);
				
				String androidEle = args[1];
				String[] androidArgs = androidEle.split("\\[");
				String androidHead = androidArgs[0];
				String[] androidOrder = androidArgs[1].split("\\]");
				int androidTypeOrder = Integer.parseInt(androidOrder[0]);
				
				String appName = args[2];
				
				String iosPageLocation = args[3];
				String[] iosPageArgs = iosPageLocation.split("/");
				String iosPageLocationEnd = iosPageArgs[iosPageArgs.length-1];
				
				String androidPageLocation = args[4];
				String[] androidPageArgs = androidPageLocation.split("/");
				String androidPageLocationEnd = androidPageArgs[androidPageArgs.length-1];
				
				if(args.length > 5) {
					String e2eRelationType = args[5];
					
					for(int i = 0; i < lenofE2eRelationWithLabelList; i ++) {
						if(easyTestSetDataWithoutLabelinCache.get(i).e2eRelation.iOSPageElement.EHead.equals(iosHead)
								&& easyTestSetDataWithoutLabelinCache.get(i).e2eRelation.iOSPageElement.locationTypeOrder == iosTypeOrder
								&& easyTestSetDataWithoutLabelinCache.get(i).e2eRelation.androidPageElement.EHead.equals(androidHead)
								&& easyTestSetDataWithoutLabelinCache.get(i).e2eRelation.androidPageElement.locationTypeOrder == androidTypeOrder
								&& easyTestSetDataWithoutLabelinCache.get(i).pageLocationEnd.equals(iosPageLocationEnd)
								) {
							easyTestSetDataWithoutLabelinCache.get(i).relationType = e2eRelationType;
						}
					}
				}
				
				
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("===== Error occur when reading CMR from DF: " + easyTestSetDataFile + " =====");
		}
		
	}
	
	
	
	
	
	
	
}
