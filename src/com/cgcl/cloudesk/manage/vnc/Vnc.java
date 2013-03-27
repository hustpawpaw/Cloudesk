package com.cgcl.cloudesk.manage.vnc;

import java.io.IOException;

import ClouDesk.AppConnection;
import android.androidVNC.ConnectionBean;


public class Vnc {

	public static ConnectionBean selected;
	//private ConnectionBean selected;

	public Vnc(){}
	public void startApp(String vncIP, int vncPort, String vmIP,
			String appName, String appPath, int hwnd) throws Exception

	{
		selected = new ConnectionBean();
		selected.setAddress(vncIP);
		selected.setPort(vncPort+5900);
		selected.setForceFull(0);
		selected.setNickname("root");
		selected.setPassword("virtual4");
		selected.setKeepPassword(false);
		selected.setUseLocalCursor(false);
		selected.setColorModel("C24bit");
		selected.setUseRepeater(false);
		AppConnection.getInstance().connectApp(vncIP ,vncPort+5900,vmIP, appName, appPath, hwnd);
	}
}
