package com.cgcl.cloudesk.manage.core;

import java.io.IOException;
import java.util.LinkedList;

import com.cgcl.cloudesk.manage.com.VMInfo;
import com.cgcl.cloudesk.manage.packet.GetGradeReqPacket;
import com.cgcl.cloudesk.manage.packet.ScoringReqPacket;


public interface UIController {
	/**
	 * Registers
	 * @param username
	 * @param password
	 * @param description
	 */
	void						register(String username, String password, String description);
	
	/**
	 * Logins
	 * @param username
	 * @param password
	 */
	void						login(String username, String password);
	
	/**
	 * Requests all app's information
	 */
	void						requestAllAppInfo();
	
	/**
	 * Request customized app's information
	 */
	void						requestCustomizeAppInfo();
	/**
	 * Start app which is selected by user
	 * @param IP
	 * @param appName
	 * @param hwnd
	 * @throws IOException 
	 * @throws Exception 
	 */
	void 						startSelectedApp(String appName, String appPath, int hwnd , int osType) throws  Exception;
	/**
	 * Send customize apps request to centerserver
	 * @param appIDs
	 */
	void 						customizeAppsRequest(String[] appIDs);
	/**
	 * Send uninstall apps request to centerserver
	 * @param appIDs
	 */
	void 						unCustomizeAppsRequest(String[] appIDs);
	/**
	 * Store the current scene
	 */
	void						save();
	/**
	 * User logout
	 */
	void						logout();
	/**
	 * request workset information
	 */
	void 						requestWorksetList();
	/**
	 * resotre selected workset
	 */
	void						restoreSelectedWorkset( String worksetName );
	/**
	 * get the instance of getVUEMobileClient
	 */
	//VUEMobileClient				getVUEMobileClient();
	/**
	 * get user dir
	 */
	void 						requestUserDir( String userName );
	/**
	 * get file list
	 */
	void						requestFileList(String dir);
	/**
	 * get VMs' info from controller
	 */
	VMInfo[] 					getVMs();
	/**
	 * terminate io threads
	 */
	void 						terminate();
	//added by dhm 2011/7/17  gradeSystem packets
	public void getGrade(String username); 
	public void scoring(LinkedList<String> scoringList) ;
}
