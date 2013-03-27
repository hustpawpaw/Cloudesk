package com.cgcl.cloudesk.manage.core;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import com.cgcl.cloudesk.manage.app.File;
import com.cgcl.cloudesk.manage.com.AppInfo;
import com.cgcl.cloudesk.manage.com.Handler;
import com.cgcl.cloudesk.manage.com.VMInfo;
import com.cgcl.cloudesk.manage.com.VUEList;
import com.cgcl.cloudesk.manage.config.ComConfig;
import com.cgcl.cloudesk.manage.config.CoreConfig;
import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.heartbeat.HeartBeatThread;
import com.cgcl.cloudesk.manage.log.LogService;
import com.cgcl.cloudesk.manage.net.Transmittable;
import com.cgcl.cloudesk.manage.packet.AllAppInfoReqPacket;
import com.cgcl.cloudesk.manage.packet.AllAppInfoRspPacket;
import com.cgcl.cloudesk.manage.packet.AllCustomizeAppInfoRspPacket;
import com.cgcl.cloudesk.manage.packet.CustomizeAppInfoReqPacket;
import com.cgcl.cloudesk.manage.packet.CustomizeAppInfoRspPacket;
import com.cgcl.cloudesk.manage.packet.CustomizeAppPacket;
import com.cgcl.cloudesk.manage.packet.CustomizeAppRspPacket;
import com.cgcl.cloudesk.manage.packet.GetFileListPacket;
import com.cgcl.cloudesk.manage.packet.GetFileListRspPacket;
import com.cgcl.cloudesk.manage.packet.GetGradeReqPacket;
import com.cgcl.cloudesk.manage.packet.GetGradeRspPacket;
import com.cgcl.cloudesk.manage.packet.GetUserDirPacket;
import com.cgcl.cloudesk.manage.packet.GetUserDirRspPacket;
import com.cgcl.cloudesk.manage.packet.PacketBase;
import com.cgcl.cloudesk.manage.packet.RestoreScenePacket;
import com.cgcl.cloudesk.manage.packet.RestoreSceneRspPacket;
import com.cgcl.cloudesk.manage.packet.SaveScenePacket;
import com.cgcl.cloudesk.manage.packet.SaveSceneRspPacket;
import com.cgcl.cloudesk.manage.packet.SavingAppInfoPacket;
import com.cgcl.cloudesk.manage.packet.ScoringReqPacket;
import com.cgcl.cloudesk.manage.packet.ScoringRspPacket;
import com.cgcl.cloudesk.manage.packet.UnCustomizeAppPacket;
import com.cgcl.cloudesk.manage.packet.UnCustomizeAppRspPacket;
import com.cgcl.cloudesk.manage.packet.UnMountRspPacket;
import com.cgcl.cloudesk.manage.packet.UserLoginPacket;
import com.cgcl.cloudesk.manage.packet.UserLoginRspPacket;
import com.cgcl.cloudesk.manage.packet.UserLogoutPacket;
import com.cgcl.cloudesk.manage.packet.UserRegPacket;
import com.cgcl.cloudesk.manage.packet.UserRegRspPacket;
import com.cgcl.cloudesk.manage.packet.WorksetInfoReqPacket;
import com.cgcl.cloudesk.manage.packet.WorksetInfoRspPacket;
import com.cgcl.cloudesk.manage.util.ArrayUtil;
import com.cgcl.cloudesk.manage.util.IPUtil;
import com.cgcl.cloudesk.manage.vnc.Vnc;
import com.cgcl.cloudesk.screens.act.UI;

public class Controller implements Handler, UIController, VncController {
	// other components
	private String 				worksetName = null;
	private Transmittable		transmitter = null;
	private UI					ui = null;
	private Vnc					vnc = null;
	private String 				userID = null;
	private VMInfo[]			VMs = null;
	private Map<String, String> namePathPair = null;
	private LinkedList<String>  gradeList = null;
	private VUEList				runningAppNameList = new VUEList();
	private VUEList				runningAppPathList = new VUEList();
	private int 				mountCount = 0;
	//private HeartBeatThread		heartBeatThread = new HeartBeatThread();
	//private static int 			firstTime = 0;
	public static Vector<AppInfo>		nativeAppInfosVector = new Vector<AppInfo>(); 
	public static Vector<AppInfo>		customizeAppInfosVector = new Vector<AppInfo>();
	public static Vector<AppInfo> 		unCustomizeAppInfosVector = new Vector<AppInfo>();
	public static String username;
	//private VUEMobileClient 	vueMobileClient;
	
	// state
	private int					currentState = CoreConfig.initialState;
	

	
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	public VMInfo[] getVMs() 
	{
		return VMs;
	}

	/**
	 * Initializes with transmitter, ui, vnc
	 * @param transmitter transmits packets
	 * @param ui shows information about the application
	 * @param vnc completes main function of the application
	 */
	public void init(Transmittable transmitter, UI ui, Vnc vnc)
	{
		this.transmitter = transmitter;
		this.ui = ui;
		this.vnc = vnc;
		//this.vueMobileClient = vueMobileClient;

	}
	
	/*public VUEMobileClient getVUEMobileClient()
	{
		return this.vueMobileClient;
	}*/
	/**
	 * Starts the controller and other modules
	 */
	public void start()
	{
		transmitter.start();
		ui.start();
	}
	
	/**
	 * Terminates the controller and other modules
	 */
	public void terminate()
	{
		transmitter.terminate();
		//heartBeatThread.stop();
		// to be added
	}
	
	public void handle(PacketBase packet) {
		switch(currentState)
		{
		case CoreConfig.initialState:
			handleInInitialState(packet);
			break;
			
		case CoreConfig.onlineState:
			handleInOnlineState(packet);
			break;
		}
	}
	
	public void handleInInitialState(PacketBase packet)
	{
		LogService.getInstance().WriteLog("handle packet : packet type = " + Integer.toString((int)packet.getType()));
		switch( packet.getType() )
		{
		case PacketConfig.kUserRegRspPacketType:
			System.out.println("handle kUserRegRspPacketType");
			UserRegRspPacket userRegRspPacket = (UserRegRspPacket)packet;
			ui.notifyRegisterResult( userRegRspPacket.isBSuccess() );
			break;
		case PacketConfig.kUserLoginRspPacketType:
			System.out.println("handle kUserLoginRspPacketType");
			UserLoginRspPacket userLoginRspPacket = (UserLoginRspPacket)packet;
			if( userLoginRspPacket.isBSuccess() == true)
			{
				currentState = CoreConfig.onlineState;
				//heartBeatThread.setTransmitter(transmitter);
				//heartBeatThread.setPingPacket(userID);
				//heartBeatThread.start();
			}
			ui.notifyLoginResult( userLoginRspPacket.isBSuccess() );
			break;
			// to be added
		}
	}
	
	@SuppressWarnings("static-access")
	public void handleInOnlineState(PacketBase packet)
	{
		LogService.getInstance().WriteLog("handle packet : packet type = " + Integer.toString((int)packet.getType()));
		switch( packet.getType() )
		{
		case PacketConfig.kAllAppInfoRspPacketType:
			System.out.println("handle kAllAppInfoRspPacketType");
			AllAppInfoRspPacket allAppInfoRspPacket = (AllAppInfoRspPacket)packet;
			
			Controller.unCustomizeAppInfosVector = ArrayUtil.ArrayToVector(allAppInfoRspPacket.getUncustomizeApps());
			Controller.customizeAppInfosVector = ArrayUtil.ArrayToVector(allAppInfoRspPacket.getCustomizeApps());
			
			ui.notifyAllAppInfo( Controller.nativeAppInfosVector, Controller.customizeAppInfosVector, Controller.unCustomizeAppInfosVector );
			
			break;
		case PacketConfig.kCustomizeAppInfoRspPacketType:
			System.out.println("handle kCustomizeAppInfoRspPacketType");
			CustomizeAppInfoRspPacket customizeAppInfoRspPacket = (CustomizeAppInfoRspPacket)packet;
			Controller.nativeAppInfosVector = ArrayUtil.ArrayToVector(customizeAppInfoRspPacket.getNativeApps());
			Controller.customizeAppInfosVector = ArrayUtil.ArrayToVector(customizeAppInfoRspPacket.getCustomizeApps());
			ui.notifyCustomizeAppInfo(customizeAppInfoRspPacket.getNativeApps(), customizeAppInfoRspPacket.getCustomizeApps(), this.namePathPair);
			break;
		case PacketConfig.kAllCustomizeAppInfoRspType:	
			System.out.println("handle kAllCustomizeAppInfoRspType");
			AllCustomizeAppInfoRspPacket allCustomizeAppInfoRspPacket = (AllCustomizeAppInfoRspPacket)packet;
			Controller.unCustomizeAppInfosVector = ArrayUtil.ArrayToVector(allCustomizeAppInfoRspPacket.getAllCustomizeAppInfo());
			System.out.println("receive unCustomizeApp " + allCustomizeAppInfoRspPacket.getAllCustomizeAppInfo().length);
			break;
		case PacketConfig.kCustomizeAppRspPacketType:
			System.out.println("handle kCustomizeAppRspPacketType");
			CustomizeAppRspPacket customizeAppRspPacket = (CustomizeAppRspPacket)packet;
			if( customizeAppRspPacket.isBSuccess())
			{
				Controller.customizeAppInfosVector.add(customizeAppRspPacket.getAppInfo());
				/*if(!Controller.customizeAppInfosVector.contains(customizeAppRspPacket.getAppInfo()))
				{
					Controller.customizeAppInfosVector.addElement(customizeAppRspPacket.getAppInfo());
					for(int i = 0 ; i < Controller.unCustomizeAppInfosVector.size() ; i ++)
					{
						AppInfo tempAppInfo = (AppInfo)Controller.unCustomizeAppInfosVector.elementAt(i);
						if( customizeAppRspPacket.getAppInfo().getID().equals(tempAppInfo.getID()))
						{
							System.out.println("unCustomizeAppInfosVector remove at " + i);
							Controller.unCustomizeAppInfosVector.removeElementAt(i);
							break;
						}
					}
					
					System.out.println("after customize customizeAppInfosVector size = " + Controller.customizeAppInfosVector.size());
					System.out.println("after customize unCustomizeAppInfosVector size = " + Controller.unCustomizeAppInfosVector.size());
				}*/
			} 
			
			//ui.notifyCustomizeRequestResult(customizeAppRspPacket.isBSuccess(), customizeAppRspPacket.getAppInfo());
			break;
		case PacketConfig.kSaveSceneRspPacketType:
			SaveSceneRspPacket saveSceneRspPacket = (SaveSceneRspPacket)packet;
			if( saveSceneRspPacket.getErrorCode() == CoreConfig.succeed )
			{
				SavingAppInfoPacket savingAppInfoPacket = new SavingAppInfoPacket();
				savingAppInfoPacket.setUserName(userID);
				savingAppInfoPacket.setSaveTime(saveSceneRspPacket.getSaveTime());
				savingAppInfoPacket.setRunningAppInfos(null);
				System.out.println("runningAppNameList : " + runningAppNameList.toString());
				System.out.println("runningAppPathList : " + runningAppPathList.toString());
				transmitter.sendPacket(savingAppInfoPacket);
			}
			mountCount = 0;
			currentState = CoreConfig.initialState;
			ui.notifySaveSceneResult(saveSceneRspPacket.getErrorCode());
			System.out.println("send savingAppInfoPacket");
			break;
		case PacketConfig.kUnmountPacketType:
			System.out.println("handler kUnmountPacket");
			UnMountRspPacket unMountRspPacket = new UnMountRspPacket();
			mountCount--;
			System.out.println("mountcount--  mountCount = " + mountCount);
			if( 0 == mountCount )
			{
				unMountRspPacket.setUserName(userID);
				unMountRspPacket.setResult(CoreConfig.saveSuccessfully);
				transmitter.sendPacket(unMountRspPacket);
				System.out.println("send unMountRspPacket : " + unMountRspPacket.getResult());
			}
			break;
		case PacketConfig.kMountPacketType:
			//to be modified
			mountCount++;
			System.out.println("mountcount++  mountCount = " + mountCount);
			System.out.println("handler KMountPacket");
			break;
		case PacketConfig.kWorksetInfoRspPacketType:
			System.out.println("handler WorksetInfoRspPacket");
			WorksetInfoRspPacket worksetInfoRspPacket = (WorksetInfoRspPacket)packet;
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ui.notifyWorksetInfo(worksetInfoRspPacket.getWorksetList());
			break;
		case PacketConfig.kRestoreSceneRspPacketType:
			System.out.println("handler kRestoreSceneRspPacket");
			VMs = ((RestoreSceneRspPacket)packet).VMs;
			this.requestUserDir(userID);
			break;
			
			
		case PacketConfig.kUnCustomizeAppRspPacketType:
			System.out.println("handler kUnCustomizeAppRspPacket");
			UnCustomizeAppRspPacket unCustomizeAppRspPacket = (UnCustomizeAppRspPacket)packet;
			if( unCustomizeAppRspPacket.isBSuccess() )
			{
				for(int i = 0 ; i < Controller.customizeAppInfosVector.size() ; i++)
				{
					AppInfo tmpAppInfo = (AppInfo)((Controller.customizeAppInfosVector).elementAt(i));
					String appID = tmpAppInfo.getID();
					if( appID.equals(unCustomizeAppRspPacket.getAppID()) )
					{
						Controller.unCustomizeAppInfosVector.addElement(tmpAppInfo);
						Controller.customizeAppInfosVector.removeElementAt(i);
						break;
					}
				}
			}
			
			System.out.println("after unCustomize customizeAppInfosVector size = " + Controller.customizeAppInfosVector.size());
			System.out.println("after unCustomize unCustomizeAppInfosVector size = " + Controller.unCustomizeAppInfosVector.size());
			//ui.notifyUnCustomizeRequestResult(unCustomizeAppRspPacket.isBSuccess(), unCustomizeAppRspPacket.getAppID());
			break;
		
		case PacketConfig.kGetUserDirRspPacketType:
			GetUserDirRspPacket getUserDirRspPacket = (GetUserDirRspPacket)packet;
			this.namePathPair = getUserDirRspPacket.getNamePathPair();
			this.requestCustomizeAppInfo();
			break;
		
		case PacketConfig.kGetFileListPacketRspType:
			
			GetFileListRspPacket getFileListRspPacket = (GetFileListRspPacket)packet;
			List<File> fileList = getFileListRspPacket.getFileList();
			ui.notifyFileList(fileList);
			break;
		//added by dhm 2011.7.17
		case PacketConfig.kGetGradePacketRspType:			
			GetGradeRspPacket getGradePacket = (GetGradeRspPacket)packet;
			this.gradeList = getGradePacket.getGradeDetails();
			ui.notifyGetGradeResult(gradeList);
			break;
		case PacketConfig.kScoringPacketRspType:			
			ScoringRspPacket scoringRspPacket = (ScoringRspPacket)packet;
			String msg = scoringRspPacket.getRspMsg();
			ui.notifyScoringResult(msg);
			break;
		}
	}
	
	

	public void shiftToUI() {
		ui.show();
	}

	public void register(String username, String password, String description) {
		UserRegPacket packet = new UserRegPacket(username, password, description);
		transmitter.sendPacket(packet);
		System.out.println("send UserRegPacket");
	}

	public void login(String username, String password) {
		UserLoginPacket packet = new UserLoginPacket(username, password);
		userID = username;
		transmitter.sendPacket(packet);
		System.out.println("send UserLoginPacket");
	}
	
	public void requestAllAppInfo() {
		AllAppInfoReqPacket packet = new AllAppInfoReqPacket();
		packet.setUserID(userID);
		transmitter.sendPacket(packet);
		System.out.println("send AllAppInfoReqPacket");
	}
	
	public void requestCustomizeAppInfo()
	{
		CustomizeAppInfoReqPacket packet = new CustomizeAppInfoReqPacket();
		packet.setUserID(userID);
		packet.setUseTime(worksetName);
		System.out.println("userID = " + userID + " worksetName = " + worksetName);
		transmitter.sendPacket(packet);
		System.out.println("send CustomizeAppInfoReqPacket");
	}
	
	
	public void startSelectedApp(String appName, String appPath, int hwnd , int osType) throws Exception
	{
		String vncIP = null;
		String vmIP  = null ;
		int vncPort  = 0;
		String ostype = null;
		
		if(osType == ComConfig.windowsType){
			ostype = "windows";
		}else{
			ostype = "linux";
		}
		
		if(VMs[0].getOs().equals(ostype)){
			vncIP   = IPUtil.intToString(VMs[0].getVncPmIP());
			vncPort = VMs[0].getVncPmPort();
			vmIP    = IPUtil.intToString(VMs[0].getIp());
		}else{
			vncIP   = IPUtil.intToString(VMs[1].getVncPmIP());
			vncPort = VMs[1].getVncPmPort();
			vmIP    = IPUtil.intToString(VMs[1].getIp());
		}
		System.out.println("vncIP:"+vncIP+"  vncPort:"+vncPort+"  appName"+appName+"  vmIP"+vmIP+"  appPath"+appPath+"  hwnd"+hwnd);
		vnc.startApp(vncIP, vncPort, vmIP, appName, appPath, hwnd);
		runningAppNameList.insert(appName);
		runningAppPathList.insert(appPath);
		
	}

	public void customizeAppsRequest(String[] appIDs) 
	{
		CustomizeAppPacket packet = new CustomizeAppPacket();
		packet.setAppIDs(appIDs);
		packet.setUserID(userID);
		transmitter.sendPacket(packet);
		System.out.println("send CustomizeAppPacket");
	}

	public void unCustomizeAppsRequest(String[] appIDs)
	{
		for(int i = 0 ; i < appIDs.length ; i++)
		{
			UnCustomizeAppPacket packet = new UnCustomizeAppPacket();
			packet.setUserID(userID);
			packet.setAppID(appIDs[i]);
			transmitter.sendPacket(packet);
			System.out.println("send UnCustomizeAppPacket");
		}
		
	}
	
	public void save() 
	{
		SaveScenePacket packet = new SaveScenePacket();
		packet.setUsername(userID);
		transmitter.sendPacket(packet);
		System.out.println("send SaveScenePacket");
		
	}

	public void logout() {
		UserLogoutPacket packet = new UserLogoutPacket();
		packet.setUsername(userID);
		transmitter.sendPacket(packet);
		currentState = CoreConfig.initialState;
		System.out.println("send UserLogoutPacket");

		
	}

	public String notifyClient() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void requestWorksetList()
	{
		WorksetInfoReqPacket packet = new WorksetInfoReqPacket();
		packet.setUserName(userID);
		System.out.println("userName : " + packet.getUserName());
		transmitter.sendPacket(packet);
		System.out.println("send WorksetInfoReqPacket");
	}

	public void restoreSelectedWorkset( String worksetName ) {
		this.worksetName = worksetName;
		RestoreScenePacket packet = new RestoreScenePacket();
		packet.setUsername(userID);
		packet.setTime(worksetName);
		System.out.println("worksetName : " + packet.getTime());
		System.out.println("userName : " + packet.getUsername()); 
		transmitter.sendPacket(packet);
		
	}
	
	public void requestUserDir(String userName)
	{
		GetUserDirPacket packet = new GetUserDirPacket();
		packet.setUserName(userName);
		transmitter.sendPacket(packet);
	}
	
	public void requestFileList(String dir)
	{
		GetFileListPacket packet = new GetFileListPacket();
		packet.setDir(dir);
		transmitter.sendPacket(packet);
	}
	public void notifyClient(String result) {
		// TODO Auto-generated method stub
		
	}
	//added by dhm 2011.07.16
	public void getGrade(String username) {
		GetGradeReqPacket packet = new GetGradeReqPacket();
		packet.setUserID(username);
		transmitter.sendPacket(packet);
		System.out.println("send GetGradeReqPacket");
	}
	
	public void scoring(LinkedList<String> scoringList) {
		ScoringReqPacket packet = new ScoringReqPacket();
		packet.setScoringList(scoringList);
		transmitter.sendPacket(packet);
		System.out.println("send ScoringReqPacket");
	}
}