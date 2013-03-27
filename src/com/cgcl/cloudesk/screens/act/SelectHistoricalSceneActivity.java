package com.cgcl.cloudesk.screens.act;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cgcl.cloudesk.cloudeskmain.screens.act.R;
import com.cgcl.cloudesk.manage.app.AppData;
import com.cgcl.cloudesk.manage.com.VUEList;
import com.cgcl.cloudesk.manage.config.ProtoConfig;
import com.cgcl.cloudesk.manage.config.UIConfig;
import com.cgcl.cloudesk.screens.act.aid.MyApplicationAid;




public class SelectHistoricalSceneActivity extends BaseActivity {
	/**
	 * @Class��RegisterActivity.java
	 * @Author�� yujia
	 * @Date��2011-5-3
	 * @Version�� 1.0.0
	 */
	/** -----------------------------˽�г�----------------------------- */
	private RadioGroup worksetlistGroup;
	private Button restorebtn;
	private ArrayList<RadioButton> radioButtons = null;
	private String[] worksetNameList = null;
	static boolean isError = false;

	/** -----------------------------�������----------------------------- */
	/** ----------------------------��Ա����---------------------------- */
	/** -------------------------handler����------------------------------ */
	/** ------------------------activity��������-------------------------- */
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		  this.CurrentActivity = this;
		AppData appData = (AppData)getApplicationContext();
		setUIController(appData.getController());
		setContentView(R.layout.select_historical_scene);
		this.setType(UIConfig.selectHistoricalSceneFormType);
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
		restorebtn = (Button)findViewById(R.id.restore_btn);
		worksetlistGroup = (RadioGroup)findViewById(R.id.worklistgroup);
		
	}
	
	private void listeners()
	{
		restorebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				String worksetName = null;			
				if(worksetlistGroup.getChildCount() != 0)
				{			
					int worksetID = worksetlistGroup.indexOfChild(findViewById(worksetlistGroup.getCheckedRadioButtonId()));
					
					worksetName = worksetNameList[worksetID];
					controller.restoreSelectedWorkset(worksetName);
					Intent mainActivity = new Intent(SelectHistoricalSceneActivity.this, MainActivity.class);
					SelectHistoricalSceneActivity.this.startActivity(mainActivity);
				}
				else 
				{
					worksetName = ProtoConfig.NONEWORKSET;
				}
			
			}
		});
	}
	
	public void displayWorksetList(VUEList worksetVUEList)
	{
		radioButtons.clear();
		
		worksetNameList = new String[worksetVUEList.size()];
		
		for( int i = 0 ; i < worksetVUEList.size() ; i++ )
		{
			worksetNameList[i] = worksetVUEList.get(i);
		}
		if( worksetlistGroup.getChildCount() != 0)
		{
			int listSize = worksetVUEList.size();
			
			for(int i = 0 ; i < listSize ; i++)
			{
				RadioButton rb = new RadioButton(this);
				rb.setText(worksetNameList[i]);
				radioButtons.add(rb);
			}
			
		}
		this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(RadioButton rb:radioButtons)
				{
					worksetlistGroup.addView(rb);
				}	
			}
		});
				
		
	}
	
	public void restoreInitWorkset(String initWorksetName)
	{
		controller.restoreSelectedWorkset(initWorksetName);
		Intent mainActivity = new Intent(SelectHistoricalSceneActivity.this, MainActivity.class);
		SelectHistoricalSceneActivity.this.startActivity(mainActivity);
		SelectHistoricalSceneActivity.this.finish();
		
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
	/** --------------------------��д�¼���������-------------------------- */

	/** -----------------------˽�л�protected������------------------------- */

	/** --------------------------------�ڲ���------------------------------ */
    @Override
    public void showError()
	{
    	//isError = true;
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
				AlertDialog.Builder builder = new AlertDialog.Builder(SelectHistoricalSceneActivity.this);
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

