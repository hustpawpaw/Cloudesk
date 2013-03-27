package com.cgcl.cloudesk.screens.act;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.io.FilenameFilter;

import android.androidVNC.VncCanvasActivity;
import android.androidVNC.VncConstants;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.cgcl.cloudesk.cloudeskmain.screens.act.R;
import com.cgcl.cloudesk.manage.app.AppData;
import com.cgcl.cloudesk.manage.app.File;
import com.cgcl.cloudesk.manage.com.AppInfo;
import com.cgcl.cloudesk.manage.com.VMInfo;
import com.cgcl.cloudesk.manage.config.ComConfig;
import com.cgcl.cloudesk.manage.config.CoreConfig;
import com.cgcl.cloudesk.manage.config.UIConfig;
import com.cgcl.cloudesk.manage.core.Controller;
import com.cgcl.cloudesk.manage.log.LogService;
import com.cgcl.cloudesk.manage.net.Transmitter;
import com.cgcl.cloudesk.manage.util.IPUtil;
import com.cgcl.cloudesk.manage.vnc.Vnc;
import com.cgcl.cloudesk.screens.act.aid.MyApplicationAid;


public class MainActivity extends BaseActivity {
	
	

	// 系统菜单项的编号
	private static final int CUSTOMIZE_ID = Menu.FIRST;
	
	private static final int GRADESYSTEM_ID = Menu.FIRST + 1;
	
	private static final int ABOUT_ID = Menu.FIRST+ 2;
	
	private static final int EXIT_ID = Menu.FIRST + 3;
	
	public static boolean uninstall = false;
	
	public static boolean isInitSuceessed = false;//是否初始化完成的标志位
	
	public static boolean TimeOut = false;

	public static GridItem item;
	
	private View view;
	
	private static GridView folderListGridView;
	
	private static ArrayList<GridItem> folderListItems =null;
	
	private static FolderGridItemAdapter folderListAdapter =null;
	
	private static String videoFolderPath="/sdcard/cloudeskVideo";
	
	private static String videoFolderName="录像";
	
	private ImageButton closeButton;
	
	private LinearLayout rootLayout;
	
	private PopupWindow folderWindow; //文件夹二级目录界面
	
	private LinearLayout mainLayout;
	
	private LinearLayout windowsLoadingBar;
	
	private LinearLayout linuxLoadingBar;
	
	private LinearLayout folderLoadingBar;
	
	private static ArrayList<GridItem> windowsItems = null;
	
	private static ArrayList<GridItem> linuxItems = null;
	
	private static ArrayList<GridItem> folderItems = null;
	
	private static GridItemAdapter windowsAdapter = null;
	
	private static GridItemAdapter linuxAdapter = null;
	
	private static GridItemAdapter folderAdapter = null;
	
	private static MyGridView windowsGridView;
	
	private static MyGridView linuxGridView;
	
	private static MyGridView folderGridView;
	
	private static String currentNamePath = null;
	
	private static boolean flag = false;
	
	private static Object lockObject = new Object();

	private static Object folderListLockObject = new Object();
	
	private static Object TempLockObject = new Object();
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.cloudesk_main);
		MyApplicationAid.getInstance().addActivity(this);
		AppData appData = (AppData)getApplicationContext();
		setUIController(appData.getController());
		setType(UIConfig.mainFormType);
		this.CurrentActivity = this;
		findviews();
		listeners();
		if (windowsItems == null)
		{
			
		
			
			flag = false ;
			new Thread(){
				public void run()
				{
					try {
						Thread.sleep(180000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (!flag)
					{
						
						MainActivity.this.showError();
					}
				}
			}.start();
		}
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v("start", "ok");
		MoblieClientUI.currentActivity = this;
		
	}
	
	@Override
	public void showError()
	{
		controller.logout();
		AppData appData = (AppData)getApplicationContext();
	   	appData.setInstall(false);
	   	isInitSuceessed=false;  
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
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("resume", "ok");
		AppData appData = (AppData)getApplicationContext();
		if(appData.isInstall()==true)
		{
			refreshgridview();//刷新view
		}
		else
		{
			appData.setInstall(true);
		}
			
	}
	

	private void refreshgridview() {  //刷新定制应用程序
	
	
		if (windowsItems == null)
			return ;
		windowsLoadingBar.setVisibility(View.VISIBLE);
    	linuxLoadingBar.setVisibility(View.VISIBLE);
		folderLoadingBar.setVisibility(View.VISIBLE);
		windowsGridView.setVisibility(View.GONE);
		linuxGridView.setVisibility(View.GONE);
		folderGridView.setVisibility(View.GONE);
		
		if(windowsItems!=null){
			Iterator<GridItem> itWindow = windowsItems.iterator();
			while(itWindow.hasNext())
			{
				if(itWindow.next().isCustomize==true)
				{
					itWindow.remove();
				}
			}
		}	
		if(linuxItems!=null)
		{
			Iterator<GridItem> itLinux = linuxItems.iterator();
			while(itLinux.hasNext())
			{
				if(itLinux.next().isCustomize==true)
				{
					itLinux.remove();
				}
			}
		}

		if(Controller.customizeAppInfosVector!=null)
		{
			for(AppInfo appinfo:Controller.customizeAppInfosVector)
			{
				if(appinfo.getOsType()==ComConfig.windowsType)
				{
					this.windowsItems.add(new GridItem(Bytes2Bimap(appinfo.getIcon()), appinfo.getID(), appinfo.getPath(),ComConfig.windowsType,true));
				}
				else if(appinfo.getOsType()==ComConfig.linuxType){
					this.linuxItems.add(new GridItem(Bytes2Bimap(appinfo.getIcon()), appinfo.getID(), appinfo.getPath(),ComConfig.linuxType,true));
				}
			}
		}
			
				windowsLoadingBar.setVisibility(View.GONE);
				linuxLoadingBar.setVisibility(View.GONE);
				folderLoadingBar.setVisibility(View.GONE);
			
				
				windowsGridView.setVisibility(View.VISIBLE);
				linuxGridView.setVisibility(View.VISIBLE);
				folderGridView.setVisibility(View.VISIBLE);
		
				windowsGridView.setAdapter(windowsAdapter);
				linuxGridView.setAdapter(linuxAdapter);
				folderGridView.setAdapter(folderAdapter);
		
	}


	private void findviews()
	{
		rootLayout=(LinearLayout)findViewById(R.id.rootLayout);
		mainLayout =(LinearLayout)findViewById(R.id.mainLayout);
		view = getLayoutInflater().inflate(R.layout.folder_list, null);
		
		folderWindow =new PopupWindow(view, 800, 600, true); //要设true
		folderListGridView = (GridView)view.findViewById(R.id.folder_list_grid);
		closeButton=(ImageButton)view.findViewById(R.id.close_btn);
		
//		windowsGridView = (GridView)findViewById(R.id.windows_grid);
//		linuxGridView = (GridView)findViewById(R.id.linux_grid);
//		folderGridView =(GridView)findViewById(R.id.folder_grid);
		windowsGridView = new MyGridView(this);
		linuxGridView = new MyGridView(this);
		folderGridView = new MyGridView(this);
		
		windowsGridView.setPadding(10, 10, 10, 10);
		windowsGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		windowsGridView.setNumColumns(8);
		windowsGridView.setColumnWidth(80);
		windowsGridView.setVerticalSpacing(20);
		windowsGridView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		windowsGridView.setGravity(Gravity.CENTER_HORIZONTAL);
		mainLayout.addView(windowsGridView,2);
		
		linuxGridView.setPadding(10, 10, 10, 10);
		linuxGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		linuxGridView.setNumColumns(8);
		linuxGridView.setColumnWidth(80);
		linuxGridView.setVerticalSpacing(20);
		linuxGridView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		linuxGridView.setGravity(Gravity.CENTER_HORIZONTAL);
		mainLayout.addView(linuxGridView,5);
		
		folderGridView.setPadding(10, 10, 10, 10);
		folderGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		folderGridView.setNumColumns(8);
		folderGridView.setColumnWidth(80);
		folderGridView.setVerticalSpacing(20);
		folderGridView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		folderGridView.setGravity(Gravity.CENTER_HORIZONTAL);
		mainLayout.addView(folderGridView,8);
		
		
		
		windowsLoadingBar=(LinearLayout)findViewById(R.id.windows_login_progressbar);
		linuxLoadingBar=(LinearLayout)findViewById(R.id.linux_login_progressbar);
		folderLoadingBar=(LinearLayout)findViewById(R.id.folder_login_progressbar);
		
	}
	
	private void listeners()
	{
		windowsGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				GridItem item = MainActivity.this.windowsItems.get(position);
				if (item != null) {
					try {
						item.startApp();
						Intent intent = new Intent(MainActivity.this, VncCanvasActivity.class);
						intent.putExtra(VncConstants.CONNECTION,Vnc.selected.Gen_getValues());
						intent.putExtra("appType", "windows");
						System.out.println("begin start VncCanvasActivity");
						startActivity(intent);
						System.out.println("end start VncCanvasActivity");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						LogService.getInstance().WriteLog("OpenAppFailed:"+ e.getMessage());
						showToast("连接超时,请稍后再试");
					}				
				}
			}
		});
		linuxGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				GridItem item = MainActivity.this.linuxItems.get(position);
				if (item != null) {
					try {
						item.startApp();
						Intent intent = new Intent(MainActivity.this, VncCanvasActivity.class);
						intent.putExtra(VncConstants.CONNECTION,Vnc.selected.Gen_getValues());
						intent.putExtra("appType", "linux");
						System.out.println("begin start VncCanvasActivity");
						startActivity(intent);
						System.out.println("end start VncCanvasActivity");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						LogService.getInstance().WriteLog("OpenAppFailed:"+ e.getMessage());
						showToast("连接超时,请稍后再试");
					}
					
				}
			}
		});
		
		folderGridView.setOnItemClickListener(new OnItemClickListener() { //文件夹列表

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				GridItem item = MainActivity.this.folderItems.get(position);
				if (item != null) {
					if (!item.isLocal)
						try {
							item.openfolder();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							LogService.getInstance().WriteLog("openFolderSocketError:"+e.getMessage());
							showToast("打开文件夹超时，请检查网络设置或稍后再试");
						}
					else
						item.openlocalfolder(item.namepath);
				}
			}
		});
		
		folderListGridView.setOnItemClickListener(new OnItemClickListener() { //文件夹二级列表

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				GridItem item = MainActivity.this.folderListItems.get(position);
				if (item != null) {
					if (item.isVideo)
					{
						Intent intent = new Intent(MainActivity.this, CurtainActivity.class);
						intent.putExtra("videoPath",item.namepath);
						startActivity(intent);
					}
					else
					{
						try {
							item.startApp();
							Intent intent = new Intent(MainActivity.this, VncCanvasActivity.class);
							intent.putExtra(VncConstants.CONNECTION,Vnc.selected.Gen_getValues());
							System.out.println("begin start VncCanvasActivity");
							startActivity(intent);
							System.out.println("end start VncCanvasActivity");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							LogService.getInstance().WriteLog("OpenAppFailed:"+ e.getMessage());
							showToast("连接超时,请稍后再试");
						}
						
					}
				}
			}
		});
//		folderListGridView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				System.out.print("fuck!");
//			}
//		});
		closeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if(folderWindow != null)
				{
					folderWindow.dismiss();
					
				}
			}
		});
		
		
		
		
	}
	
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {//menu事件
		// TODO Auto-generated method stub
		menu.add(0, CUSTOMIZE_ID, 0, R.string.main_customize).setIcon(R.drawable.icon_customize);
		menu.add(0, GRADESYSTEM_ID, 0, R.string.main_gradesystem).setIcon(R.drawable.icon_gradesystem);
		menu.add(0, ABOUT_ID, 0, R.string.main_about).setIcon(R.drawable.icon_about);
		menu.add(0, EXIT_ID, 0, R.string.main_exit).setIcon(R.drawable.icon_exit);
		return true;

	}
    
    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()){
		case CUSTOMIZE_ID:
			if(isInitSuceessed == true)
			{
				
				Intent intent = new Intent(MainActivity.this, CustomizeActivity.class);
				startActivity(intent);
			}
			else {		
				AlertDialog.Builder customBuilder = new AlertDialog.Builder(MainActivity.this);
				customBuilder.setTitle(R.string.customize_title);
				customBuilder.setMessage(R.string.customize_content);
				customBuilder.setPositiveButton(R.string.exit_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
					}
				});
				customBuilder.show();
			}
			break;
		case GRADESYSTEM_ID:
			if(isInitSuceessed == true)
			{
			Intent intent = new Intent(MainActivity.this, DyGradeSystemActivity.class);
			startActivity(intent);
			}
			else {		
				AlertDialog.Builder customBuilder = new AlertDialog.Builder(MainActivity.this);
				customBuilder.setTitle("正在载入中，请稍候");
				customBuilder.setMessage(R.string.customize_content);
				customBuilder.setPositiveButton(R.string.exit_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
					}
				});
				customBuilder.show();
			}
			break;
			
		case ABOUT_ID:
			StringBuffer sb = new StringBuffer();

			sb.append(getResources().getString(R.string.activity_is_about_title)).append("\n\n");
			sb.append(getResources().getString(R.string.activity_is_about_content)).append("\n\n");			
			
			AlertDialog.Builder aboutBuilder = new AlertDialog.Builder(MainActivity.this);
			aboutBuilder.setTitle(R.string.about_tilte);
			aboutBuilder.setMessage(sb.toString());
			aboutBuilder.setPositiveButton(R.string.exit_ok, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.dismiss();
				}
			});

			aboutBuilder.show();

			break;
			
		case EXIT_ID:
			exit_cloudesk();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	
	
    public void showLocalFileList(List<java.io.File> listFile)
    {	
    	 
							
				
		this.folderListItems = new ArrayList<GridItem>();
		this.folderListAdapter = new FolderGridItemAdapter(this.folderListItems);
		Resources res=getResources();
		Bitmap icon=BitmapFactory.decodeResource(res, R.drawable.icon);
		Bitmap wordIcon =BitmapFactory.decodeResource(res, R.drawable.word);
		Bitmap pptIcon = BitmapFactory.decodeResource(res, R.drawable.ppt);
		Bitmap pdfIcon = BitmapFactory.decodeResource(res, R.drawable.pdf);
		Bitmap movieIcon = BitmapFactory.decodeResource(res, R.drawable.movie);
		
			for(java.io.File file:listFile)
			{
				if(file.getName().toUpperCase().endsWith(".DOC"))
					{
						this.folderListItems.add(new GridItem(wordIcon, file.getName(), currentNamePath+file.getName(),ComConfig.windowsType ));
					}
					else if (file.getName().toUpperCase().endsWith(".PPT")) {
						this.folderListItems.add(new GridItem(pptIcon, file.getName(), currentNamePath+file.getName(),ComConfig.windowsType ));
					}
					else if (file.getName().toUpperCase().endsWith(".MP4")||file.getName().toUpperCase().endsWith(".3GP") || file.getName().toUpperCase().endsWith(".WMV"))
					{
						this.folderListItems.add(new GridItem(true,true, movieIcon, file.getName(), currentNamePath+file.getName()));
					}
					else {
						this.folderListItems.add(new GridItem(icon, file.getName(), currentNamePath+file.getName(),ComConfig.windowsType ));
					}
			}
			folderListGridView.setAdapter(folderListAdapter);
			folderWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);	    
		
    };
	
	public void notifyFileList(List<File> fileList)  //文件夹二级窗口显示
	{
		AppData appData = (AppData)getApplicationContext();		
		 this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
						try {
							
							synchronized (folderListLockObject) {
								folderListLockObject.wait();
							}
							folderListGridView.setAdapter(folderListAdapter);
							folderWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
							
						}
						catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			});
		
		this.folderListItems = new ArrayList<GridItem>();
		this.folderListAdapter = new FolderGridItemAdapter(this.folderListItems);
		Resources res=getResources();
		Bitmap icon=BitmapFactory.decodeResource(res, R.drawable.icon);
		Bitmap wordIcon =BitmapFactory.decodeResource(res, R.drawable.word);
		Bitmap pptIcon = BitmapFactory.decodeResource(res, R.drawable.ppt);
		Bitmap pdfIcon = BitmapFactory.decodeResource(res, R.drawable.pdf);
		Bitmap movieIcon = BitmapFactory.decodeResource(res, R.drawable.movie);
		if(fileList!=null)
		{
			for(File file:fileList)
			{
				if(file.type == 1)
				{
					if(file.fileName.contains(".doc"))
					{
						this.folderListItems.add(new GridItem(wordIcon, file.fileName, currentNamePath+file.fileName,ComConfig.windowsType ));
					}
					else if (file.fileName.contains(".ppt")) {
						this.folderListItems.add(new GridItem(pptIcon, file.fileName, currentNamePath+file.fileName,ComConfig.windowsType ));
					}
					else if (file.fileName.toUpperCase().endsWith(".PDF"))
					{
						this.folderListItems.add(new GridItem(pdfIcon, file.fileName, currentNamePath+file.fileName,ComConfig.windowsType ));
					}
					else {
						this.folderListItems.add(new GridItem(icon, file.fileName, currentNamePath+file.fileName,ComConfig.windowsType ));
					}
					
				}
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		synchronized (folderListLockObject) {
			folderListLockObject.notify();
		}
    
		
		
		if( null != appData.getVmController())
		{
			appData.getVmController().terminate();
			appData.setVmController(null);
		}
		
	}
	
	

	
	public void dispalyAppInfo(AppInfo[] nativeApps, AppInfo[] customizeApps, Map<String, String> namePathPair)
	{
		flag = true;
		this. runOnUiThread(new Runnable() { 
            @Override 
                public void run() { 
                   // refresh ui 鐨勬搷浣滀唬鐮�
//            	windowsLoadingBar.setVisibility(View.VISIBLE);
//            	linuxLoadingBar.setVisibility(View.VISIBLE);
//        		folderLoadingBar.setVisibility(View.VISIBLE);
//        		windowsGridView.setVisibility(View.GONE);
//        		linuxGridView.setVisibility(View.GONE);
//        		folderGridView.setVisibility(View.GONE);
            	try {
            		synchronized (lockObject){
            			lockObject.wait();
            		}
            		
            		windowsLoadingBar.setVisibility(View.GONE);
            		linuxLoadingBar.setVisibility(View.GONE);
            		folderLoadingBar.setVisibility(View.GONE);

            		windowsGridView.setVisibility(View.VISIBLE);
            		linuxGridView.setVisibility(View.VISIBLE);
            		folderGridView.setVisibility(View.VISIBLE);
            		
            		windowsGridView.setAdapter(windowsAdapter);
	            	linuxGridView.setAdapter(linuxAdapter);
	            	folderGridView.setAdapter(folderAdapter);
	           		isInitSuceessed = true;
            		
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              } 
         });

		
		this.windowsItems = new ArrayList<GridItem>();
		this.windowsAdapter = new GridItemAdapter(this.windowsItems);
		
		this.linuxItems = new ArrayList<GridItem>();
		this.linuxAdapter = new GridItemAdapter(this.linuxItems);
		
		this.folderItems = new ArrayList<GridItem>();
		this.folderAdapter = new GridItemAdapter(this.folderItems);
		
		if(nativeApps!=null)
		{
			for(AppInfo appinfo:nativeApps)
			{	
				
				if(appinfo.getOsType()==ComConfig.windowsType)
				{
					
					this.windowsItems.add(new GridItem(Bytes2Bimap(appinfo.getIcon()), appinfo.getID(), appinfo.getPath(),ComConfig.windowsType));
				}
				else if(appinfo.getOsType()==ComConfig.linuxType) {
					this.linuxItems.add(new GridItem(Bytes2Bimap(appinfo.getIcon()), appinfo.getID(), appinfo.getPath(),ComConfig.linuxType));
				}
			}
			
			
		}
		
		if(customizeApps!=null) 
		{
			for(AppInfo appinfo:customizeApps)
			{
				if(appinfo.getOsType()==ComConfig.windowsType)
				{
					this.windowsItems.add(new GridItem(Bytes2Bimap(appinfo.getIcon()), appinfo.getID(), appinfo.getPath(),ComConfig.windowsType, true));
				}
				else if(appinfo.getOsType()==ComConfig.linuxType){
					this.linuxItems.add(new GridItem(Bytes2Bimap(appinfo.getIcon()), appinfo.getID(), appinfo.getPath(),ComConfig.linuxType, true));
				}
			}
		}
		
		Resources res=getResources();
		Bitmap folderIcon=BitmapFactory.decodeResource(res, R.drawable.folder);
		Iterator iter = namePathPair.entrySet().iterator();
		//录像文件夹
		java.io.File file = new java.io.File(videoFolderPath);
		if (file.exists())
			this.folderItems.add(new GridItem(true, folderIcon, videoFolderName, videoFolderPath));
		
		while(iter.hasNext())
		{
			Map.Entry<String,String> entry = (Map.Entry<String,String>)iter.next();
			
			this.folderItems.add(new GridItem(folderIcon, entry.getKey(), entry.getValue()));
		}
		doSpecialProcess();

		synchronized (lockObject){
			lockObject.notify();
		}
	}
	ArrayList<GridItem> tempFolderItems;
	public void getSpecial(String key)
	{
		int t = -1;
		for (int i = 0; i < this.folderItems.size(); ++i)
		{
			if (this.folderItems.get(i).appName.startsWith(key))
			{
				tempFolderItems.add(this.folderItems.get(i));
				t = i;
				break;
			}
		}
		if (t != -1)
			this.folderItems.remove(t);
		return ;
	}
	
	public void doSpecialProcess()
	{
		tempFolderItems = new ArrayList<GridItem>();
		getSpecial("录像");
		getSpecial("简报");
		getSpecial("任务书");
		getSpecial("2007");
		getSpecial("2008");
		getSpecial("2009");
		getSpecial("2010");
		getSpecial("2011");
		for (int i = 1; i <= 8; ++i)
		{
			getSpecial("课题"+i);		
		}
		for (int i = 0; i < this.folderItems.size(); ++i)
		{
			tempFolderItems.add(this.folderItems.get(i));
		}
		this.folderItems = new ArrayList<GridItem>();
		for (int i = 0 ; i < this.tempFolderItems.size();++i)
		{
			this.folderItems.add(tempFolderItems.get(i));
		}
		this.folderAdapter = new GridItemAdapter(this.folderItems);
		return ;
	}
	public void notifyCustomizeAppResult(boolean bCustomizeRestlt, AppInfo appInfo)
	{
	}
	
	public void notifyUnCustomizeAppResult(boolean bSuccessToRequest,String appID)
	{
	}
	
	public void notifySaveSceneResult(int saveSceneResult)
	{
	}
	/* ===================== 鍥惧舰杞崲 ======================== */
	private Bitmap Bytes2Bimap(byte[] b) //byte转成bitmap
	{
		if(b.length!=0)
		{
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		else {
			return null;
		}
	}
	
	
	/* ===================== Adapter ======================== */
	
	private class GridItem{
		private Bitmap icon;
		public String appName;
		private String namepath;
		private String appPath;	
		private int osType;
		
		private boolean isCustomize;
		private boolean isLocal = false;
		private boolean isVideo = false;

		
		private GridItem(Bitmap icon, String appName, String appPath, int osType,boolean isCustomize) { //定制文件初始化
			this.icon = icon;
			this.appName = appName;
			this.appPath =appPath;
			this.osType = osType;
			this.isCustomize = isCustomize;
			this.namepath=null;
		}
		
		private GridItem(Bitmap icon, String appName, String appPath, int osType) { //基础文件初始化
			this.icon = icon;
			this.appName = appName;
			this.appPath =appPath;
			this.osType = osType;
			this.isCustomize = false;
			this.namepath=null;
		}
		
		private GridItem(Bitmap icon, String appName, String namepath) //文件夹列表初始化
		{
			this.icon = icon;
			this.appName = appName;
			this.namepath = namepath;
			this.isCustomize =false;
			this.osType = -1;//没有ostype
			this.appPath =null;
		}
		
		private GridItem(boolean isLocal, Bitmap icon, String appName, String namepath) //文件夹列表初始化
		{
			this.isLocal = isLocal;
			this.icon = icon;
			this.appName = appName;
			this.namepath = namepath;
			this.isCustomize =false;
			this.osType = -1;//没有ostype
			this.appPath =null;
		}
		
		private GridItem(boolean isLocal, boolean isVideo, Bitmap icon, String appName, String namepath) //文件夹列表初始化
		{
			this.isLocal = isLocal;
			this.isVideo = isVideo;
			this.icon = icon;
			this.appName = appName;
			this.namepath = namepath;
			this.isCustomize =false;
			this.osType = -1;//没有ostype
			this.appPath =null;
		}
		private void linkToVM() throws Exception
		{
			String windowsIP = null;
			Transmitter tmpTransmitter = null;
			Controller  tmpController = new Controller();
			tmpController.setCurrentState(CoreConfig.onlineState);
			AppData appData = (AppData)getApplicationContext();
			appData.setVmController(tmpController);
			
			VMInfo[] vmInfo = controller.getVMs();
			for(int i = 0 ; i < vmInfo.length ; i++)
			{
				if(vmInfo[i].getOs().equals("windows"))
				{
					windowsIP = IPUtil.intToString(vmInfo[i].getIp());
				}
			}
			
		
				tmpTransmitter = new Transmitter(appData.getVmController(), windowsIP, "50001");
				appData.setVmTransmitter(tmpTransmitter);
			
			
			appData.getVmController().init(appData.getVmTransmitter(), new MoblieClientUI(), new Vnc());
			appData.getVmController().start();
		}
			
		public void openfolder() throws Exception  //打开文件夹
		{
			AppData appData = (AppData)getApplicationContext();
			linkToVM();
			
			currentNamePath = namepath;
			appData.getVmController().requestFileList(namepath);
			
//			folderWindow.showAtLocation(rootLayout, Gravity.CENTER, 0, 0);
//			folderListItems = new ArrayList<GridItem>();
//			folderListAdapter = new GridItemAdapter(folderListItems);
//			Resources res=getResources();
//			Bitmap icon=BitmapFactory.decodeResource(res, R.drawable.icon);
//			
//			MainActivity.this.folderListItems.add(new GridItem(icon, "word", currentNamePath+"word",ComConfig.windowsType ));
//			folderListGridView.setAdapter(folderListAdapter);
		}
		
		public void openlocalfolder(String path)
		{	
			ArrayList<java.io.File> listFile = new ArrayList<java.io.File>();
		    ArrayList<String> listName = new ArrayList<String>();
			listName.clear();
			listFile.clear();
			videoFileFilter vf = new videoFileFilter();
			java.io.File file =new java.io.File(path);
	        java.io.File[] files = file.listFiles(vf);
	   
	        for(java.io.File f : files){
	             listName.add(f.getName());
	             listFile.add(f);
	           }
	        currentNamePath = namepath+"/";
	        showLocalFileList(listFile);    
	        
		}
		
		public void startApp() throws Exception
		{
		
			controller.startSelectedApp(this.appName, this.appPath, 0, this.osType);
		}

	}
	private class GridItemAdapter extends BaseAdapter
	{
		private ArrayList<GridItem> items;
		
		private GridItemAdapter(ArrayList<GridItem> items) {
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
		public View getView(int position, View convertView, ViewGroup parent) {  //设定模板参数
			// TODO Auto-generated method stub
			View view =convertView;
			GridItem item;
			if(view ==null){
				view = getLayoutInflater().inflate(R.layout.grid_item, null);
			}
			if((item = this.items.get(position))==null){
				return view;
			}
			ImageView iv =(ImageView)view.findViewById(R.id.image_item);
			TextView tv =(TextView)view.findViewById(R.id.text_item);
			
			tv.setText(item.appName);
			iv.setImageBitmap(item.icon);
			
			return view;
		}	
		
	}
	private class FolderGridItemAdapter extends BaseAdapter
	{
		private ArrayList<GridItem> items;
		
		private FolderGridItemAdapter(ArrayList<GridItem> items) {
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
		public View getView(int position, View convertView, ViewGroup parent) {  //设定模板参数
			// TODO Auto-generated method stub
			View view =convertView;
			GridItem item;
			if(view ==null){
				view = getLayoutInflater().inflate(R.layout.folder_item, null);
			}
			if((item = this.items.get(position))==null){
				return view;
			}
			ImageView iv =(ImageView)view.findViewById(R.id.image_item1);
			TextView tv =(TextView)view.findViewById(R.id.text_item1);
			
			tv.setText(item.appName);
			iv.setImageBitmap(item.icon);
			
			return view;
		}	
		
	}
	public class MyGridView extends GridView{ //重写Gridview的onMeasure事件，保证不会出现UI显示上的bug
    	public MyGridView(Context context, AttributeSet attrs){
    		super(context,attrs);
    	}
    	public MyGridView(Context context) 	{
    		super(context);
    	}
    	public MyGridView(Context context,AttributeSet attrs, int defStyle){
    		super(context, attrs, defStyle);
    	}
    	@Override
    	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
    		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
    		super.onMeasure(widthMeasureSpec, expandSpec);
    	}
    	
    }

	private void exit_cloudesk() //退出cloudesk
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle(R.string.exit_title);
		builder.setMessage(R.string.exit_confirm);
		builder.setPositiveButton(R.string.exit_ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				controller.logout();
        	   	AppData appData = (AppData)getApplicationContext();
        	   	appData.setInstall(false);
        	   	isInitSuceessed=false;        	   	
        	    MyApplicationAid.getInstance().exit();
        	    System.exit(0);
			}
		});
		builder.setNeutralButton(R.string.exit_cancel, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
			}
		});
		builder.show();
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
           if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                  // Do something.
        	   	exit_cloudesk();
//        	   	controller.logout();
//        	   	AppData appData = (AppData)getApplicationContext();
//        	   	appData.setInstall(false);
//              this.finish();//直接调用杀死当前activity方法.
                return true;
           }
      return super.onKeyDown(keyCode, event);
    } 
    //added by dhm 2011/10/05
    public void showTimeOut()
	{
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
				// TODO Auto-generated method stub
				AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
				builder1.setTitle("Warning");
				builder1.setMessage("连接超时，请稍后再试");
				builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}					
			});
				int k  = 1; 
				if (MainActivity.this != null)
					k = 2;
				int j = k +2;
				builder1.create().show();	
			}
			});				
		
	} 
	private void showToast(String msg) {
		Toast toast = Toast.makeText(MainActivity.this, 
				msg, Toast.LENGTH_LONG); 
				toast.setGravity(Gravity.CENTER, 0, 0); 
				toast.show();
	}
}
class videoFileFilter implements FilenameFilter
{

	@Override
	public boolean accept(java.io.File dir, String name) {
		// TODO Auto-generated method stub
		return (name.toUpperCase().endsWith(".MP4") ||  name.toUpperCase().endsWith(".3GP")||  name.toUpperCase().endsWith(".WMV"));
	}
};