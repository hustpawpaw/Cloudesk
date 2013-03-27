package com.cgcl.cloudesk.screens.act;

import java.util.ArrayList;
import java.util.Vector;

import com.cgcl.cloudesk.cloudeskmain.screens.act.R;
import com.cgcl.cloudesk.manage.app.AppData;
import com.cgcl.cloudesk.manage.com.AppInfo;
import com.cgcl.cloudesk.manage.config.UIConfig;
import com.cgcl.cloudesk.manage.core.Controller;
import com.cgcl.cloudesk.screens.act.aid.MyApplicationAid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class CustomizeActivity extends BaseActivity {

	private Button commitbtn;
	
	private Button resetbtn;
	
	private ListView customizeListView;
	
	private ArrayList<ListItem> customizeListItems;
	
	private ListItemAdapter customizeListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		  this.CurrentActivity = this;
		AppData appData = (AppData)getApplicationContext();
		setUIController(appData.getController());
		setType(UIConfig.customizeFormType);
		setContentView(R.layout.customize_list);
		Log.v("yujia", "customize start");
		findviews();
		listeners();
		this.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				controller.requestAllAppInfo();		
			}
			
		});
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		MoblieClientUI.currentActivity = this;
	}
	
	private void findviews() {
		// TODO Auto-generated method stub
		commitbtn = (Button)findViewById(R.id.commit_btn);
		resetbtn =(Button)findViewById(R.id.reset_btn);
		
		customizeListView =(ListView)findViewById(R.id.customize_listview);
		
	}
	
	private void listeners() {
		// TODO Auto-generated method stub
		commitbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<String> customizeAppList = new ArrayList<String>();
				ArrayList<String> uncustomizeAppList = new ArrayList<String>();
				if(customizeListItems!=null)
				{
					for(ListItem listItem:customizeListItems)
					{
						if(listItem.checked==true){
							customizeAppList.add(listItem.id);
						}
						else {
							uncustomizeAppList.add(listItem.id);
						}
					}
				}
				
				String[] customappStrings = new String[customizeAppList.size()];
				String[] uncustomappStrings =new String[uncustomizeAppList.size()];
				int i = 0;
				if(customizeAppList!=null)
				{
					for(String appString:customizeAppList)
					{
						customappStrings[i] = appString;
						i++;
					}
				}
				i = 0;
				if(uncustomizeAppList!=null)
				{
					for(String appString:uncustomizeAppList)
					{
						uncustomappStrings[i] =appString;
						i++;
					}
				}
				
				
				Controller.customizeAppInfosVector.clear();
				
				if(customappStrings!=null)
				{
					controller.customizeAppsRequest(customappStrings);
				}
				if(uncustomappStrings!=null)
				{
					controller.unCustomizeAppsRequest(uncustomappStrings);
				}
				
				showToast(getResources().getString(R.string.commit_success));
			}
		});
		
		resetbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				controller.requestAllAppInfo();
				displayListView(Controller.nativeAppInfosVector, Controller.customizeAppInfosVector, Controller.unCustomizeAppInfosVector);
			}
		});
		
		customizeListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				ListItem item = CustomizeActivity.this.customizeListItems.get(position);
				if (item != null) {
					item.checkedItem();
					CheckBox cBox = (CheckBox)view.findViewById(R.id.list_cb);
					cBox.setChecked(item.checked);
				}
			}
		});
	}
	
	public void displayListView(Vector<AppInfo> nativeApps, Vector<AppInfo> customizeApps, Vector<AppInfo> uncustomizeApps) {
		// TODO Auto-generated method stub
		this.customizeListItems = new ArrayList<ListItem>();
		this.customizeListAdapter = new ListItemAdapter(customizeListItems);
		
		if(customizeApps!=null)
		{
			for(AppInfo appInfo:customizeApps)
			{
//				customizeListItems.add(new ListItem(appInfo.getID(), true));
				customizeListItems.add(new ListItem(appInfo.getID(), true, Bytes2Bimap(appInfo.getIcon())));
			}
		}

		if(uncustomizeApps!=null)
		{
			for(AppInfo appInfo:uncustomizeApps)
			{
//				customizeListItems.add(new ListItem(appInfo.getID(), false));
				customizeListItems.add(new ListItem(appInfo.getID(), false, Bytes2Bimap(appInfo.getIcon())));
			}
		}
		this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				customizeListView.setAdapter(customizeListAdapter);
			}
		});
		
		
	}

	/* ===================== Adapter ======================== */

	
	
	private class ListItem
	{
		private boolean checked;
		private String id;
		private Bitmap icon;
		
//		private ListItem(String id, boolean checked)
//		{
//			this.id = id;
//			this.checked = checked;
//		}
		
		private ListItem(String id, boolean checked, Bitmap icon)
		{
			this.id = id;
			this.checked = checked;
			this.icon = icon;
		}
		
		private void checkedItem()
		{
			if(this.checked==false)
			{
				this.checked = true;
			}
			else {
				this.checked = false;
			}
		}
	}
	
	private class ListItemAdapter extends BaseAdapter
	{
		private ArrayList<ListItem> items;
		
		private ListItemAdapter(ArrayList<ListItem> items) {
			this.items = items;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return this.items.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view =convertView;
			ListItem item;
			if(view ==null){
				view = getLayoutInflater().inflate(R.layout.list_item, null);
			}
			if((item = this.items.get(position))==null){
				return view;
			}
			TextView tv =(TextView)view.findViewById(R.id.list_tv);
			CheckBox cb =(CheckBox)view.findViewById(R.id.list_cb);
			ImageView iv = (ImageView)view.findViewById(R.id.list_iv);
			
			tv.setText(item.id);
			cb.setChecked(item.checked);
			iv.setImageBitmap(item.icon);
			
			return view;
		}
		
		
	}
	
	private Bitmap Bytes2Bimap(byte[] b) //byte杞崲bitmap
	{
		if(b.length!=0)
		{
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		else {
			return null;
		}
	}

	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
           if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                  // Do something.
        	   	  AppData appData = (AppData)getApplicationContext();
     	   	      appData.setInstall(true);
                  this.finish();//鐩存帴璋冪敤鏉�褰撳墠activity鏂规硶.
        	   	 
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
				AlertDialog.Builder builder = new AlertDialog.Builder(CustomizeActivity.this);
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
