package com.cgcl.cloudesk.screens.act;


import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cgcl.cloudesk.cloudeskmain.screens.act.R;
import com.cgcl.cloudesk.manage.app.AppData;

import com.cgcl.cloudesk.manage.config.UIConfig;
import com.cgcl.cloudesk.manage.core.Controller;
import com.cgcl.cloudesk.manage.log.LogService;
import com.cgcl.cloudesk.manage.net.Transmitter;
import com.cgcl.cloudesk.manage.vnc.Vnc;

import com.cgcl.cloudesk.screens.act.aid.*;
/**
 * @Class閿熸枻鎷稶sernameLoginActivity.java
 * @Author閿熸枻鎷�yujia
 * @Date閿熸枻鎷�011-4-28
 * @Version閿熸枻鎷�1.0.0
 */
public class LoginActivity extends BaseActivity {


	/** -----------------------------绉侀敓鍙鎷�----------------------------- */


	/** -----------------------------閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�--------------------------- */
	private EditText usernameEdt;
	private EditText passwordEdt;
	private boolean isInitSuccessed; //判断初始化是否成功
	private Button instantBtn;
	private Button newRegisterBtn;
	private String username;
	
	// added by dhm 09/27
	private boolean flag = false; // 判断登录是否超时
	
	private boolean initAppData()
	{
		
		//AppConnection.getInstance().connectApp("192.168.201.25" ,0 ,"192.168.201.25", "C:\\Users\\Administrator\\Desktop\\系统安装文档.doc", "C:\\Users\\Administrator\\Desktop\\系统安装文档.doc", 0);

		AppData appData = (AppData)getApplicationContext();
		appData.setController(new Controller());
		try {
			appData.setTransmitter(new Transmitter(appData.getController(), "192.168.201.201", "9000"));
		} catch (Exception e)	{
			//code added here	
			LogService.getInstance().WriteLog("init error : " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		appData.setVnc(new Vnc());
		appData.setUi(new MoblieClientUI());
		appData.getController().init(appData.getTransmitter(), appData.getUi(), appData.getVnc());
        appData.getController().start();
        return true;
	}

//	private void linkToVM()
//	{
//		Transmitter tmpTransmitter = null;
//		Controller  tmpController = new Controller();
//		AppData appData = (AppData)getApplicationContext();
//		appData.setVmController(tmpController);
//		
//		
//		try {
//			tmpTransmitter = new Transmitter(appData.getVmController(), "192.168.201.211", "50001");
//			appData.setVmTransmitter(tmpTransmitter);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
//		
//		appData.getVmController().init(appData.getVmTransmitter(), new MoblieClientUI(), new Vnc());
//		appData.getVmController().start();
//	}
	/** ----------------------------閿熸枻鎷峰憳閿熸枻鎷�閿熸枻鎷�--------------------------- */
	/** -------------------------handler閿熸枻鎷烽敓鏂ゆ嫹------------------------------ */
	/** ------------------------activity閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹-------------------------- */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        isInitSuccessed = initAppData();
        this.CurrentActivity = this;
        if(true == isInitSuccessed){
        	AppData appData = (AppData)getApplicationContext();
    		setUIController(appData.getController());
    		MyApplicationAid.getInstance().addActivity(this);
            setType(UIConfig.loginFormType);
            setContentView(R.layout.username_login);
            findViews();
            listeners();           
        }
        else{
        	this.finish();
        }
        
        
    }
    
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		MoblieClientUI.currentActivity = this;
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(false == isInitSuccessed){
			showToast(getResources().getString(R.string.network_error));
		}
	}

	/** ---------------------閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓缁炵》鎷烽敓锟�----------------------- */

	private void findViews() {
		usernameEdt = (EditText) findViewById(R.id.cloudesk_username_edt);
		passwordEdt = (EditText) findViewById(R.id.cloudesk_password_edt);
		instantBtn = (Button) findViewById(R.id.cloudesk_instant_login_btn);
		newRegisterBtn = (Button) findViewById(R.id.new_register_btn);
	}

	private void listeners()
	{
		instantBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				username = usernameEdt.getText().toString();
				String password = passwordEdt.getText().toString();
				System.out.println("**********************login");
				controller.login(username, password);
				flag = false ;
				new Thread(){
					public void run()
					{
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (!flag)
						{
							LoginActivity.this.showNetError();
						}
					}
				}.start();
				
			 //   flag = false;
			/*	LoginActivity.this.runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (!flag)
						{
							LoginActivity.this.showError();
						}
					}
					
				});
				*/
			}
		});
		
		newRegisterBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
				LoginActivity.this.startActivity(registerActivity);
			}
		});
	}
	
	public void showNetError()
	{
	 this.runOnUiThread(new Runnable(){
		
		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
				builder.setTitle(R.string.error_lag_title);
				builder.setMessage(R.string.error_network_content);
				builder.setPositiveButton(R.string.exit_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
						controller.logout();
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
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
	public void notifyLoginResult(boolean loginResult)
	{
		flag = true;
		
		if(loginResult)
		{
			Controller.username = username;
			Intent selectHistoricalSceneActivity = new Intent(LoginActivity.this, SelectHistoricalSceneActivity.class);	
			LoginActivity.this.startActivity(selectHistoricalSceneActivity);
			controller.requestWorksetList();
		}
		else
		{
			this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					showToast(getResources().getString(R.string.username_or_password_error));
				}
			});
			
		}
	}
	
	public void init()
	{
		
	}

	
	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}
	

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
           if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                  // Do something.
                  this.finish();//直接调用杀死当前activity方法.
                  return true;
           }
      return super.onKeyDown(keyCode, event);
    } 
    
    @Override
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
				AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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