package com.cgcl.cloudesk.screens.act;
import com.cgcl.cloudesk.manage.app.AppData;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.cgcl.cloudesk.cloudeskmain.screens.act.R;
import com.cgcl.cloudesk.manage.app.AppData;
import com.cgcl.cloudesk.manage.config.UIConfig;
import com.cgcl.cloudesk.screens.act.aid.MyApplicationAid;



public class CurtainActivity extends BaseActivity{
	
		private VideoView videoView = null;
	  
	 
	  private String strVideoPath = "";
	  
	  /** Called when the activity is first created. */
	  
	 // public void onCreate(Bundle savedInstanceState)
	  //{
		/*  super.onCreate(savedInstanceState);
			AppData appData = (AppData)getApplicationContext();
			setUIController(appData.getController());
			setContentView(R.layout.curtain);  

	    strVideoPath = this.getIntent().getStringExtra("videoPath");   
	    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(200, 300);
	    videoView.setLayoutParams(lp);
	    
	    videoView.setVideoURI(Uri.parse("file://"+strVideoPath));
	    videoView.setMediaController(new MediaController(this));
	    videoView.start();

	  }*/
		   private String path = "";
			private VideoView mVideoView;
			private static int i = 0;
			private int width;
			private int heigh;	
			private Dialog dialog;
			@Override
			public void onCreate(Bundle icicle) {
				super.onCreate(icicle);
			    strVideoPath = this.getIntent().getStringExtra("videoPath");  
				//去掉头信息
			    this.CurrentActivity = this;
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
			                WindowManager.LayoutParams.FLAG_FULLSCREEN);  
					Bundle bundle = this.getIntent().getExtras();
					//判断手机屏幕的方向
					DisplayMetrics dm = new DisplayMetrics(); 
			        getWindowManager().getDefaultDisplay().getMetrics(dm);
					width=dm.widthPixels;
					heigh=dm.heightPixels;
					setContentView(R.layout.curtain);
						//横屏
				
		        
				 
				mVideoView = (VideoView) findViewById(R.id.videoView1);
//				mVideoView.setBackgroundDrawable(getWallpaper());
				mVideoView.setVideoPath(strVideoPath);
				MediaController controller = new MediaController(this);
				mVideoView.setMediaController(controller);
				mVideoView.requestFocus();
				mVideoView.setOnPreparedListener(new OnPreparedListener() {
					// 开始播放
					@Override
					public void onPrepared(MediaPlayer mp) {
						mVideoView.setBackgroundColor(Color.argb(0, 0, 255, 0));
					}
				});
				//播放完毕
				mVideoView.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
					}
				});
			}
			@Override
			protected void onResume() {
				super.onResume();
				mVideoView.seekTo(i);
				mVideoView.start();
			}
			@Override
			protected void onStop() {
				super.onStop();
				mVideoView.pause();
				i = mVideoView.getCurrentPosition();
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
							AlertDialog.Builder builder = new AlertDialog.Builder(CurtainActivity.this);
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
