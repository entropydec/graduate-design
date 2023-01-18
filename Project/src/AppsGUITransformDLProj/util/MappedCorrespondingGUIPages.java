package AppsGUITransformDLProj.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AppsGUITransformDLProj.GUI.AndroidGUIPage;
import AppsGUITransformDLProj.GUI.GUIPageXMLFileReader;
import AppsGUITransformDLProj.GUI.iOSXCUITestPage;

public class MappedCorrespondingGUIPages {
	
	/**
	 * App Lists, 
	 * overallFileFolder,
	 * 
	 * Set iOS pages and android pages
	 * Set the relation information
	 * */
	
	//Start with AppName
	public List<String> appList;
	
	public String overallFileFolder;
	
	public List<String> appIOSPageFileFolder;
	public List<String> appAndroidPageFileFolder;
	
	public List<HashMap<String, iOSXCUITestPage>> iOSPageSet;
	public List<HashMap<String, AndroidGUIPage>> androidPageSet;
	
	public List<List<IOSPage2AndroidPageRelation>> mappedCorrespondingPages;
	
	
	public MappedCorrespondingGUIPages(String oFFolder) {
		
		this.overallFileFolder = oFFolder;
		
		this.appList = takeAppFileFolderNameList(oFFolder);
		
		this.appIOSPageFileFolder = new ArrayList<String>();
		this.appAndroidPageFileFolder = new ArrayList<String>();
		
		this.iOSPageSet = new ArrayList<HashMap<String, iOSXCUITestPage>>();
		this.androidPageSet = new ArrayList<HashMap<String, AndroidGUIPage>>();
		
		int lenAppInfoList = this.appList.size();
		for(int i = 0; i < lenAppInfoList; i ++) {
			String tempIOSFileString = "" + this.overallFileFolder + this.appList.get(i) + "/iOS/";
			String tempAndroidFileString = "" + this.overallFileFolder + this.appList.get(i) + "/Android/";
			this.appIOSPageFileFolder.add(tempIOSFileString);
			this.appAndroidPageFileFolder.add(tempAndroidFileString);
			
			HashMap<String, iOSXCUITestPage> tempIOSPageSet = new HashMap<String, iOSXCUITestPage>();
			HashMap<String, AndroidGUIPage> tempAndroidPageSet = new HashMap<String, AndroidGUIPage>();
			
			this.iOSPageSet.add(tempIOSPageSet);
			this.androidPageSet.add(tempAndroidPageSet);
			
			//List<IOSPage2AndroidPageRelation> tempmappedCorrespondingPages = new ArrayList<IOSPage2AndroidPageRelation>();
			//this.mappedCorrespondingPages.add(tempmappedCorrespondingPages);
		}
		
		this.mappedCorrespondingPages = new ArrayList<List<IOSPage2AndroidPageRelation>>();
		
	}
	
	public void initalMappedCorrespondingGUIPageSet() {
		
		this.setiOSPageSet();
		this.setAndroidPageSet();
		this.constructAllCorrespondingPageRelations();
		
		this.printMappedCorrespondingPages();
	}
	
	
	public static List<String> takeAppFileFolderNameList(String overallFileFolder) {
		
		List<String> result = new ArrayList<String>();
		
		File dirFile = new File(overallFileFolder);
		if(!dirFile.isDirectory()) {
			if(dirFile.isFile()) {
				System.out.println("#################### Error in setiOSPageSet: dir isFile ####################");
			}
			System.out.println("#################### Error in setiOSPageSet: dir is not file or dir ####################");
			return null;
		}
		
		String[] fileFolderList = dirFile.list();
		int lenDirFile = fileFolderList.length;
		for(int i = 0; i < lenDirFile; i ++) {
			if(!fileFolderList[i].startsWith("AppName")) {
				continue;
			}
			File dirFileTemp = new File(overallFileFolder + fileFolderList[i]);
			System.out.println("" + fileFolderList[i]);
			
			if(dirFileTemp.isDirectory()) {
				result.add(fileFolderList[i]);
			}
		}
		
		return result;
	}
	
	/**
	 * Set iOS and Android page sets
	 * */
	public void setiOSPageSet() {
		
		int lenIOSSet = this.iOSPageSet.size();
		for(int l = 0; l < lenIOSSet; l ++) {
			this.iOSPageSet.get(l).clear();
		}
		
		int lengthofAppList = this.appList.size();
		
		for(int i = 0; i < lengthofAppList; i ++) {
			
			//System.out.println("file folder: " + i + " - " + this.appIOSPageFileFolder.get(i));
			
			File dirFile = new File(this.appIOSPageFileFolder.get(i));
			if(!dirFile.isDirectory()) {
				if(dirFile.isFile()) {
					System.out.println("#################### Error in setiOSPageSetforParallelExecution: dir isFile ####################");
				}
				System.out.println("#################### Error in setiOSPageSetforParallelExecution: dir is not file or dir ####################");
				return ;
			}
			String[] fileList = dirFile.list();
			int lenDirFile = fileList.length;
			for(int j = 0; j < lenDirFile; j ++) {
				
				if(fileList[j].endsWith(".xml")
						&& !fileList[j].contains("_rootPage")
						
						) {
					String fileNameTemp = this.appIOSPageFileFolder.get(i) + fileList[j];
					//System.out.println(this.appList.get(i) + ": " + fileList[j]);
					iOSXCUITestPage pageTemp = GUIPageXMLFileReader.readiOSPageXMLFile(fileNameTemp);
					this.iOSPageSet.get(i).put(fileList[j], pageTemp);
				}
			}
			
		}
		
		this.printiOSPageSet();
	}
	
	
	public void setAndroidPageSet() {
		
		int lenAndroidSet = this.androidPageSet.size();
		for(int l = 0; l < lenAndroidSet; l ++) {
			this.androidPageSet.get(l).clear();
		}
		
		int lengthofAppList = this.appList.size();
		
		for(int i = 0; i < lengthofAppList; i ++) {
			
			//System.out.println("file folder: " + i + " - " + this.appIOSPageFileFolder.get(i));
			
			File dirFile = new File(this.appAndroidPageFileFolder.get(i));
			if(!dirFile.isDirectory()) {
				if(dirFile.isFile()) {
					System.out.println("#################### Error in setAndroidPageSetforParallelExecution: dir isFile ####################");
				}
				System.out.println("#################### Error in setAndroidPageSetforParallelExecution: dir is not file or dir ####################");
				return ;
			}
			String[] fileList = dirFile.list();
			int lenDirFile = fileList.length;
			for(int j = 0; j < lenDirFile; j ++) {
				
				if(fileList[j].endsWith(".xml")
						&& !fileList[j].contains("_rootPage")
						
						) {
					String fileNameTemp = this.appAndroidPageFileFolder.get(i) + fileList[j];
					//System.out.println(this.appList.get(i) + ": " + fileList[j]);
					AndroidGUIPage pageTemp = GUIPageXMLFileReader.readAndroidPageXMLFile(fileNameTemp);
					this.androidPageSet.get(i).put(fileList[j], pageTemp);
				}
			}
			
		}
		
		this.printAndroidPageSet();
	}
	
	public void constructAllCorrespondingPageRelations() {
		
		int lenofAppInfo = this.appList.size();
		
		for(int i = 0; i < lenofAppInfo; i ++) {
			
			HashMap<String, iOSXCUITestPage> tempIOSPageSet = this.iOSPageSet.get(i);
			HashMap<String, AndroidGUIPage> tempAndroidPageSet = this.androidPageSet.get(i);
			
			this.mappedCorrespondingPages.add(this.constructPMRelationsinSingleApp(tempIOSPageSet, tempAndroidPageSet));
			
		}
		
	}
	
	private List<IOSPage2AndroidPageRelation> constructPMRelationsinSingleApp(HashMap<String, iOSXCUITestPage> tempIOSPageSet, HashMap<String, AndroidGUIPage> tempAndroidPageSet) {
		
		List<IOSPage2AndroidPageRelation> resultIOSPage2AndroidPageRelations = new ArrayList<IOSPage2AndroidPageRelation>();
		
		String iOSFileNameTemp = "";
		iOSXCUITestPage iOSPageTemp = null;
		String androidFileNameTemp = "";
		AndroidGUIPage androidPageTemp = null;
		
		int identifier = 0;
		while(identifier < 25) {//this parameter is related with the countMax of the page colletcting process
			
			IOSPage2AndroidPageRelation relationTemp = new IOSPage2AndroidPageRelation();
			relationTemp.identifier = identifier;
			
			iOSFileNameTemp = "";
			iOSPageTemp = null;
			
			for(Map.Entry<String , iOSXCUITestPage> entryIOS: tempIOSPageSet.entrySet()) {
				String fileNameTemp = entryIOS.getKey();
				String[] args = fileNameTemp.split("_");
				if(args.length > 1) {
					if(Integer.parseInt(args[0]) == identifier) {
						iOSFileNameTemp = fileNameTemp;
						iOSPageTemp = entryIOS.getValue();
					}
				}
			}
			
			androidFileNameTemp = "";
			androidPageTemp = null;
			
			for(Map.Entry<String , AndroidGUIPage> entryAndroid: tempAndroidPageSet.entrySet()) {
				String fileNameTemp = entryAndroid.getKey();
				String[] args = fileNameTemp.split("_");
				if(args.length > 1) {
					if(Integer.parseInt(args[0]) == identifier) {
						androidFileNameTemp = fileNameTemp;
						androidPageTemp = entryAndroid.getValue();
					}
				}
			}
			
			
			if((iOSPageTemp != null) && (androidPageTemp != null) ) {
				relationTemp.iOSPageFileName = iOSFileNameTemp;
				relationTemp.iOSPage = iOSPageTemp;
				relationTemp.androidPageFileName = androidFileNameTemp;
				relationTemp.androidPage = androidPageTemp;
				
				System.out.println("When fill in an relation: iOS page file name:" + relationTemp.iOSPageFileName + ", android page file name: " + androidFileNameTemp);
				
			}
			
			resultIOSPage2AndroidPageRelations.add(relationTemp);
			
			
			identifier ++;
		}
		
		
		return resultIOSPage2AndroidPageRelations;
	}
	
	
	
	
	
	
	
	/**
	 * Print Methods
	 * */
	
	public void printiOSPageSet() {
		int len = this.iOSPageSet.size();
		System.out.println("iOS page set length: " + len);
		for(int i = 0; i < len; i ++) {
			System.out.println("======================================");
			for(Map.Entry<String, iOSXCUITestPage> entryPSet: this.iOSPageSet.get(i).entrySet()) {
				
				System.out.println("" + this.appList.get(i) + ": " + entryPSet.getKey());
				
			}
			
		}
		System.out.println("======================================");
	}
	
	public void printAndroidPageSet() {
		int len = this.androidPageSet.size();
		System.out.println("Android page set length: " + len);
		for(int i = 0; i < len; i ++) {
			System.out.println("======================================");
			for(Map.Entry<String, AndroidGUIPage> entryPSet: this.androidPageSet.get(i).entrySet()) {
				
				System.out.println("" + this.appList.get(i) + ": " + entryPSet.getKey());
				
			}
			
		}
		System.out.println("======================================");
	}
	
	public void printMappedCorrespondingPages() {
		
		int len = this.mappedCorrespondingPages.size();
		
		for(int i = 0; i < len; i ++) {
			
			System.out.println("App Info:" + this.appList.get(i));
			
			List<IOSPage2AndroidPageRelation> temp = this.mappedCorrespondingPages.get(i);
			int len2 = temp.size();
			
			for(int j = 0; j < len2; j ++) {
				
				temp.get(j).printIOSPage2AndroidPageRelation();
				
			}
			
		}
		
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		String overallFileFolder = "/Users/jiruihua/Desktop/AppGUIMapping/CrossPlatoformExamples/00-ProjTestFolder/Demo_April_2/";
		
		MappedCorrespondingGUIPages mappedCorrespondingGUIPages = new MappedCorrespondingGUIPages(overallFileFolder);
		mappedCorrespondingGUIPages.setiOSPageSet();
		mappedCorrespondingGUIPages.setAndroidPageSet();
		
		mappedCorrespondingGUIPages.constructAllCorrespondingPageRelations();
		mappedCorrespondingGUIPages.printMappedCorrespondingPages();
		
	}
	
}
