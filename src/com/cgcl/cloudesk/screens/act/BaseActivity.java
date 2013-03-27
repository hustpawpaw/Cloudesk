package com.cgcl.cloudesk.screens.act;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.cgcl.cloudesk.cloudeskmain.screens.act.R;
import com.cgcl.cloudesk.manage.app.AppData;
import com.cgcl.cloudesk.manage.config.UIConfig;
import com.cgcl.cloudesk.manage.core.UIController;
import com.cgcl.cloudesk.screens.act.aid.MyApplicationAid;

public class BaseActivity extends Activity {
	protected UIController		controller = null;
	private int					type = UIConfig.invalidFormType;
	public static BaseActivity CurrentActivity = null;
	public void setCurrentActivity(BaseActivity currentActivity)
	{
		this.CurrentActivity = currentActivity;	
	}
	public void setUIController(UIController controller)
	{
		this.controller = controller;
	}
	
	public int getType() 
	{
		return type;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	public void showError()
	{

		controller.logout();
		AppData appData = (AppData)getApplicationContext();
	   	appData.setInstall(false);
	   	MainActivity.isInitSuceessed=false;  
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.runOnUiThread(new Runnable(){
			
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.CurrentActivity);
				builder.setTitle(R.string.error_load_title);
				builder.setMessage(R.string.error_network_content);
				builder.setPositiveButton(R.string.exit_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
						AppData appData = (AppData)getApplicationContext();
		        	   	appData.setInstall(false);     	   	
		        	    MyApplicationAid.getInstance().exit();
		        	    System.exit(0);
					}
					
			});
				builder.show();
			}
	 });
	}	
}
