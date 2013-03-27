package ClouDesk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.cgcl.cloudesk.cloudeskmain.screens.act.R;
import com.cgcl.cloudesk.manage.log.LogService;
import com.cgcl.cloudesk.manage.vnc.Vnc;

import android.androidVNC.ConnectionBean;
import android.androidVNC.VncCanvasActivity;

public class AppConnection implements Runnable {
	public VncCanvasActivity getActivity() {
		return activity;
	}

	public void setActivity(VncCanvasActivity activity) {
		this.activity = activity;
	}

	private VncCanvasActivity activity = null;

	private static AppConnection instance = null;

	public boolean serverSetSize = false;

	public boolean isFirstRun = true;

	private Thread thread;

	private int width_ = 640;

	private int height_ = 480;

	private int remote_desktop_width = 1024;

	private int remote_desktop_height = 480;

	private String appPassword = "123";

	private String appName = "";

	//private String FilePathOnServer = "D:/1.doc";

	//private int windowHWND = 0;

	public int isFocus = 1;

	private Socket socket = null;

	private DataInputStream in = null;

	private DataOutputStream out = null;;

	public String getAppName() {
		return this.appName;
	}

	public int remoteDesktopWidth() {
		return remote_desktop_width;
	}

	public int remoteDesktopHeight() {
		return remote_desktop_height;
	}

	public void setRemoteDesktopWidth(int w) {
		remote_desktop_width = w;
	}

	public void setRemoteDesktopHeight(int h) {
		remote_desktop_height = h;
	}

	public int AppWidth() {
		return width_;
	}

	public int AppHeight() {
		return height_;
	}

	public void SetAppWidth(int width) {
		width_ = width;
	}

	public void SetAppHeight(int height) {
		height_ = height;
	}

	public static AppConnection getInstance() {
		if (instance == null) {
			instance = new AppConnection();
		}
		return instance;
	}

	public String getPassword() {
		return appPassword;
	}

	public boolean connectApp(String vncIP, int vncPort, String controlIP,
			String appName, String appPath, int windowHWND) throws Exception {
			//added by dhm 2011/1005
			this.appName = appName;
			InetAddress iaddr = InetAddress.getByName(controlIP);
			SocketAddress  address =   new  InetSocketAddress(iaddr, 6100); 
			socket = new Socket();
				//socket = new Socket(iaddr, 6100 , 5000);
			socket.connect(address, 5000);
			socket.setSoTimeout(60*60*1000);			
			
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			out.writeByte(AppMsgType.msgTypeRequestApp);
			LogService.getInstance().WriteLog("Output: "+"msgTypeRequestApp");
			out.writeInt(windowHWND);
			out.flush();

			int type = in.readByte();
			
			if (type == AppMsgType.msgTypeExist) //
			{
				System.out.println("app had open");
				width_ = in.readInt();
				height_ = in.readInt();

				thread = new Thread(this);
				thread.start();

				return true;
			}
			LogService.getInstance().WriteLog("app not open");
			//System.out.println();
			
			out.writeInt(appPath.getBytes("gb2312").length);
			out.write(appPath.getBytes("gb2312"), 0, appPath.getBytes("gb2312").length);

			//out.writeBytes(appPath);
			out.flush();
			System.out.println("app open path is " + appPath);

			out.writeInt(appName.getBytes("gb2312").length);
			out.write(appName.getBytes("gb2312"), 0, appName.getBytes("gb2312").length);
			out.flush();

			type = in.readByte();
			if (type == AppMsgType.msgTypeFailed) {
				System.out.println("open app fail");
				return false;
			}
			System.out.println("open app success");
			windowHWND = in.readInt();
			width_ = in.readInt();
			height_ = in.readInt();

			System.out.println("open app info :: windowHWND " + windowHWND + "width_ " + width_
					+ " height_" + height_);

			thread = new Thread(this);
			thread.start();
		return true;
	}

	public boolean requestSetSize(int width, int height) {
		
		
		try {
			out.writeByte(AppMsgType.msgTypeSetSize);
			out.writeInt(width);
			out.writeInt(height);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean requestCloseWindow() {
		System.out.println("send msgTypeClientWindow");
		//System.out.println("-----------------aaaaaaaaaaa---------------");
		try {
			out.writeByte(AppMsgType.msgTypeCloseWindow);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (!socket.isClosed())
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					LogService.getInstance().WriteLog("AppConnetionsocketError"+e.getMessage());
				}
			this.activity.finish();
		}
		return true;
	}

	public boolean requestSetFocus() {
		System.out.println("requestSetFocus set focus " + socket.toString());

		this.isFocus = 0;
		try {
			out.writeByte(AppMsgType.msgTypeSetFocus);
			out.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Thread.sleep(500); //
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.isFocus = 1;

		return true;
	}

	
	public void run() {

		System.out.println(" thread start");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		try {
			while (true) {
				
				int type;
				type = in.readByte();
				System.out.println("read message type " + type);
				LogService.getInstance().WriteLog("read message type"+type);
				switch (type) {
				case AppMsgType.srvSetClientSize: {
					width_ = in.readInt();
					height_ = in.readInt();
					System.out.println("server send w " + width_ + " h " + height_);

					if (isFirstRun) { 
						isFirstRun = false;
					} else
						serverSetSize = true;
					

					break;
				}
				case AppMsgType.srvCloseClientWindow: {
					try{
						if (!socket.isClosed())
							socket.close();
					} catch (Exception e1) {
						LogService.getInstance().WriteLog("up Recive AppConnection Packet Error"+ e1.getMessage());
					}
					//in.close();
					//out.close();
					this.activity.finish();
					System.out.println("aaaaaa------------aaaaaaaaaaa");
					break;
				}
				
				case AppMsgType.srvShowPPT: {
					//in.close();
					//out.close();
					this.activity.setConnectionInputMode(R.id.itemInputTouchPanZoomMouse);
					System.out.println("bbbb------------bbbb");
					break;
				}
				
				case AppMsgType.srvExitPPT: {
					//in.close();
					//out.close();
					//this.activity.setConnectionInputMode(R.id.itemFitToScreen);
					AppConnection.getInstance().requestCloseWindow();
					System.out.println("bbbb------------bbbb");
					break;
				}
				
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogService.getInstance().WriteLog("Recive AppConnection Packet Error"+ e.getMessage());
			this.activity.finish();
			e.printStackTrace();
		}

	}

}
