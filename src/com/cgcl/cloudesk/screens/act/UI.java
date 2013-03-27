package com.cgcl.cloudesk.screens.act;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.cgcl.cloudesk.manage.app.File;
import com.cgcl.cloudesk.manage.com.AppInfo;
import com.cgcl.cloudesk.manage.com.VUEList;

public interface UI {
	/**
	 * Starts the UI
	 */
	void						start();
	
	/**
	 * Shows in the screen
	 */
	void						show();
	
	/**
	 * Notifies the result of registration
	 * @param bSucceedToRegister
	 */
	void						notifyRegisterResult(boolean bSucceedToRegister);
	
	/**
	 * Notifies the result of login
	 * @param bSucceedToLogin
	 * @param restore
	 */
	void						notifyLoginResult(boolean bSucceedToLogin);
	
	/**
	 * Notifies all app's information
	 * @param apps
	 */
	void						notifyAllAppInfo(Vector<AppInfo> nativeApps, Vector<AppInfo> customizeApps, Vector<AppInfo> uncustomizeApps);
	/**
	 * Notifies customized app's information
	 * @param nativeApps
	 * @param customizeApps
	 */
	void 						notifyCustomizeAppInfo(AppInfo[] nativeApps, AppInfo[] customizeApps, Map<String, String> namePathPair);
	/**
	 * Notifies the result of customize app request
	 * @param bSucceedToRequest
	 */
	void 						notifyCustomizeRequestResult(boolean bSucceedToRequest, AppInfo appInfo);
	/**
	 * Noifies the result of uninstall app request
	 * @param bSuccessToRequest
	 * @param AppID
	 */
	void						notifyUnCustomizeRequestResult(boolean bSuccessToRequest, String appID);
	/**
	 * Notifies the result of saving the current scene
	 * @param saceSceneResult
	 */
	void						notifySaveSceneResult(int saceSceneResult);
	/**
	 * Notifies the information of worksets
	 */
	void						notifyWorksetInfo(VUEList worksetList);
	/**
	 * Notifies  file list in folder
	 */
	void 						notifyFileList(List<File> fileList);
	
	/**
	 * Notifies the result of Scoring
	 */
	void						notifyScoringResult(String ScoringResult);
	/**
	 * Notifies the result of GetGrade
	 */
	void						notifyGetGradeResult(LinkedList<String> gradeList);

}
