package com.cgcl.cloudesk.screens.act;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.cgcl.cloudesk.manage.app.File;
import com.cgcl.cloudesk.manage.com.AppInfo;
import com.cgcl.cloudesk.manage.com.VUEList;
import com.cgcl.cloudesk.manage.config.ProtoConfig;
import com.cgcl.cloudesk.manage.config.UIConfig;
import com.cgcl.cloudesk.manage.core.Controller;

public class MoblieClientUI implements UI{
	static public BaseActivity currentActivity;
	public MoblieClientUI()
	{
	}
	
	public void start() {
		/*System.out.println("UI.start");
		currentForm = forms[UIConfig.loginFormIndex];
		if( currentForm == null )
			System.out.println("currentForm null!");
		show();*/
	}
	
	public void show() {
		//currentForm.show();
	}

	public void notifyLoginResult(boolean bSucceedToLogin) {
		if( UIConfig.loginFormType == currentActivity.getType())
		{
			LoginActivity loginActivity = (LoginActivity)currentActivity;
			loginActivity.notifyLoginResult(bSucceedToLogin);
		}
		
	}

	public void notifyRegisterResult(boolean bSucceedToRegister) {
		if( UIConfig.registerFormType == currentActivity.getType() )
		{
			RegisterActivity registerActivity = (RegisterActivity)currentActivity;
			registerActivity.notifyRegisterResult(bSucceedToRegister);
		}
	}

	public void notifyAllAppInfo(Vector<AppInfo> nativeApps, Vector<AppInfo> customizeApps, Vector<AppInfo> uncustomizeApps) {
		if( UIConfig.customizeFormType == currentActivity.getType() )
		{
			CustomizeActivity customizeActivity = (CustomizeActivity)currentActivity;
			customizeActivity.displayListView(Controller.nativeAppInfosVector, Controller.customizeAppInfosVector, Controller.unCustomizeAppInfosVector);
		}
	}
	
	public void notifyCustomizeAppInfo(AppInfo[] nativeApps, AppInfo[] customizeApps, Map<String, String> namePathPair)
	{
		if( UIConfig.mainFormType == currentActivity.getType())
		{
			
			MainActivity mainActivity = (MainActivity)currentActivity;
			
			mainActivity.dispalyAppInfo(nativeApps, customizeApps, namePathPair);
		}
		
	}

	public void notifyCustomizeRequestResult(boolean succeedToRequest, AppInfo appInfo) 
	{
		/*if( UIConfig.customizeFormType == currentActivity.getType() )
		{
			CustomizeActivity customizeActivity = (CustomizeActivity)currentActivity;
			customizeActivity.notifyCustomizeFormShift();
		}
		MainActivity mainActivity = (MainActivity)activities[UIConfig.mainFormIndex];
		
		mainActivity.dispalyAppInfo(ArrayUtil.VectorToArray(Controller.nativeAppInfosVector), ArrayUtil.VectorToArray(Controller.customizeAppInfosVector));
		mainActivity.notifyCustomizeAppResult(succeedToRequest, appInfo);
		*/
	}
	
	public void notifyUnCustomizeRequestResult(boolean bSuccessToRequest, String appID)
	{
		/*if( UIConfig.customizeFormType == currentActivity.getType() )
		{
			CustomizeActivity customizeActivity = (CustomizeActivity)currentActivity;
		//	customizeActivity.notifyCustomizeFormShift();
		}
		MainActivity mainActivity = (MainActivity)activities[UIConfig.mainFormIndex];
		mainActivity.dispalyAppInfo(ArrayUtil.VectorToArray(Controller.nativeAppInfosVector), ArrayUtil.VectorToArray(Controller.customizeAppInfosVector));
		mainActivity.notifyUnCustomizeAppResult(bSuccessToRequest, appID);
		*/
	}
	public void notifySaveSceneResult(int saveSceneResult) {
		if( UIConfig.mainFormType == currentActivity.getType())
		{
			MainActivity mainActivity = (MainActivity)currentActivity;
			mainActivity.notifySaveSceneResult(saveSceneResult);
		}
		
	}
	
	public void notifyWorksetInfo(VUEList worksetList)
	{
		
		if( UIConfig.selectHistoricalSceneFormType == currentActivity.getType())
		{
		 System.out.println("notifyWorksetInfo");
		 SelectHistoricalSceneActivity selectHistoricalSceneForm = (SelectHistoricalSceneActivity)currentActivity;
		 if(worksetList.size() > 1)
		 {
			 System.out.println("worksetList.size() > 1");
			 selectHistoricalSceneForm.displayWorksetList(worksetList);
		 }
		 else if(worksetList.size() == 1)
		 {
			 System.out.println("worksetList.size() == 1");
			 selectHistoricalSceneForm.restoreInitWorkset(worksetList.get(0));
		 }
		 else if( (worksetList.size() == 0) || (worksetList == null) )
		 {
			 System.out.println("worksetList.size() == 0");
			 selectHistoricalSceneForm.restoreInitWorkset(ProtoConfig.NONEWORKSET);
		 }
		}
	}
	
	public void notifyFileList(List<File> fileList)
	{
		if(UIConfig.mainFormType == currentActivity.getType())
		{
			MainActivity mainActivity = (MainActivity)currentActivity;
			mainActivity.notifyFileList(fileList);
		}
	}
	
	public void	notifyScoringResult(String scoringResult)
	{
		if(UIConfig.gradeSystemFormType == currentActivity.getType())
		{
			DyGradeSystemActivity gradesystem = (DyGradeSystemActivity)currentActivity;
			gradesystem.notifyScoringResult(scoringResult);
		}
	}
	public void	notifyGetGradeResult(LinkedList<String> gradeList)
	{
		
		if(UIConfig.gradeSystemFormType == currentActivity.getType())
		{
				DyGradeSystemActivity dyActivity = (DyGradeSystemActivity)currentActivity;
				dyActivity.notifyGetGradeResult(gradeList);			
		}
	}
}
