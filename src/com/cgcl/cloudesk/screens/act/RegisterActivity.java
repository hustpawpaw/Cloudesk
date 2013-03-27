package com.cgcl.cloudesk.screens.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.cgcl.cloudesk.screens.act.aid.MyApplicationAid;

public class RegisterActivity extends BaseActivity {

	/**
	 * @Class��RegisterActivity.java
	 * @Author�� yujia
	 * @Date��2011-5-3
	 * @Version�� 1.0.0
	 */
	
	/** -----------------------------˽�г�----------------------------- */
	private EditText userEdt;
	private EditText passwordEdt;
	private Button registerBtn;


	/** -----------------------------�������----------------------------- */
	/** ----------------------------��Ա����---------------------------- */
	/** -------------------------handler����------------------------------ */
	/** ------------------------activity��������-------------------------- */
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.CurrentActivity = this;
		AppData appData = (AppData)getApplicationContext();
		setUIController(appData.getController());
		setType(UIConfig.registerFormType);
		setContentView(R.layout.new_register);
		MyApplicationAid.getInstance().addActivity(this);
		findviews();
		listeners();
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		MoblieClientUI.currentActivity = this;
	}


	/** ---------------------���������������ʼ��------------------------- */
	private void findviews()
	{
		userEdt = (EditText) findViewById(R.id.regsister_username_edt);
		passwordEdt = (EditText) findViewById(R.id.regsiter_password_edt);
		registerBtn = (Button) findViewById(R.id.instant_register_btn);

	}
	
	private void listeners()
	{
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				String name = userEdt.getText().toString();
				String pwd = passwordEdt.getText().toString();
				controller.register(name, pwd, "");
			}
		});
	}
	public void notifyRegisterResult(boolean registerResult)
	{
		if(registerResult)
		{
//			Intent loginActivityIntent = new Intent(RegisterActivity.this, LoginActivity.class);	
//			RegisterActivity.this.startActivity(loginActivityIntent);
			this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					showToast(getResources().getString(R.string.resgister_success));
				}
			});
		}
		else
		{
			this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					showToast(getResources().getString(R.string.multiple_register));
				}
			});
		}
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
    
	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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
					AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
