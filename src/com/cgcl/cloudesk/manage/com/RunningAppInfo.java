package com.cgcl.cloudesk.manage.com;

import com.cgcl.cloudesk.manage.util.Serializer;

public class RunningAppInfo implements Serializable {

	private int handle;
	private String appName;
	private String appPath;
	
	public RunningAppInfo()
	{
		
	}
	
	public RunningAppInfo(int handle, String appName, String appPath)
	{
		this.handle = handle;
		this.appName = appName;
		this.appPath = appPath;
	}
	
	public int getHandle() {
		return handle;
	}

	public void setHandle(int handle) {
		this.handle = handle;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

	public int deserialize(byte[] buf, int offset) {
		int length = 0;
		System.out.println("length = " + length);
		handle = Serializer.deserializeInt(buf, offset + length);
		System.out.println("length = " + length);
		length += 4;
		appName = Serializer.deserializeString(buf, offset + length);
		System.out.println("length = " + length);
		System.out.println("appName = " + appName);
		length += Serializer.length(appName);
		appPath = Serializer.deserializeString(buf, offset + length);
		System.out.println("length = " + length);
		System.out.println("appPath = " + appPath);
		length += Serializer.length(appPath);
		System.out.println("length = " + length);
		return length;
	}

	public int length() {
		int length = 0;
		length += 4;
		length += Serializer.length(appName);
		length += Serializer.length(appPath);
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = 0;
		length += Serializer.serialize(buf, offset + length, handle);
		length += Serializer.serialize(buf, offset + length, appName);
		length += Serializer.serialize(buf, offset + length, appPath);
		return length;
	}

}
