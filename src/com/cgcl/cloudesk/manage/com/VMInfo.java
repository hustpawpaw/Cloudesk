package com.cgcl.cloudesk.manage.com;

import com.cgcl.cloudesk.manage.util.Serializer;

public class VMInfo {
	/*private String os = null;
	private int ip;
	private VUEList appNameList = new VUEList();
	private VUEList appPathList = new VUEList();*/
	private String os;
	private int ip;
	private int vncPmIP;
	private short  vncPmPort;
	private RunningAppInfo[] runningAppInfos;
	public VMInfo()
	{
	
	}
	public VMInfo(String os, int ip, RunningAppInfo[] runningAppInfos)
	{
		this.os = os;
		this.ip = ip;
		this.runningAppInfos = runningAppInfos;
	}
	
	
	public RunningAppInfo[] getRunningAppInfos() {
		return runningAppInfos;
	}
	public void setRunningAppInfos(RunningAppInfo[] runningAppInfos) {
		this.runningAppInfos = runningAppInfos;
	}
	
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public int getIp() {
		return ip;
	}
	public void setIp(int ip) {
		this.ip = ip;
	}
	
	public int getVncPmIP()
	{
		return vncPmIP;
	}
	public void setVncPmIP(int vncPmIP)
	{
		this.vncPmIP = vncPmIP;
	}
	public short getVncPmPort()
	{
		return vncPmPort;
	}
	public void setVncPmPort(short vncPmPort)
	{
		this.vncPmPort = vncPmPort;
	}
	public int deserialize(byte[] buf, int offset) 
	{
		int length = 0;
		os = Serializer.deserializeString(buf, offset + length);
		System.out.println("os = " + os);
		length += Serializer.length(os);
		ip = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		vncPmIP = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		vncPmPort = Serializer.deserializeShort(buf, offset + length);
		length += 2;
		
		int runningAppInfoNum = Serializer.deserializeInt(buf, offset + length);
		System.out.println("runningAppInfoNum = " + runningAppInfoNum);
		length += 4;
		runningAppInfos = new RunningAppInfo[runningAppInfoNum];
		for(int i = 0 ; i < runningAppInfoNum ; i++)
		{
			System.out.println("runningAppInfoNumIndex = " + i);
			runningAppInfos[i] = new RunningAppInfo();
			length += runningAppInfos[i].deserialize(buf, offset + length);
		}
		return length;
	}
	
	public int serialize(byte[] buf, int offset) {
		int length = 0;
		length += Serializer.serialize(buf, offset + length, os);
		length += Serializer.serialize(buf, offset + length, ip);
		length += Serializer.serialize(buf, offset + length, vncPmIP);
		length += Serializer.serialize(buf, offset + length, vncPmPort);
		length += Serializer.serialize(buf, offset + length, runningAppInfos.length);
		for(int i = 0 ; i < runningAppInfos.length ; i++)
		{
			length += runningAppInfos[i].serialize(buf, offset + length);
		}
		return length;
	}
	
	public int length()
	{
		int length = 0;
		
		length += Serializer.length(os);
		length += 4;
		length += 4;
		length += 2;
		
		length += 4;
		for(int i = 0 ; i < runningAppInfos.length ; i++)
		{
			length += runningAppInfos[i].length();
		}
		return length;
	}
	
	/*public VMInfo(String os, int ip, VUEList appNameList, VUEList appPathList)
	{
		this.os = os;
		this.ip = ip;
		this.appNameList = appNameList;
		this.appPathList = appPathList;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public int getIp() {
		return ip;
	}

	public void setIp(int ip) {
		this.ip = ip;
	}

	public VUEList getAppNameList() {
		return appNameList;
	}

	public void setAppNameList(VUEList appNameList) {
		this.appNameList = appNameList;
	}

	public VUEList getAppPathList() {
		return appPathList;
	}

	public void setAppPathList(VUEList appPathList) {
		this.appPathList = appPathList;
	}
	
	public int deserialize(byte[] buf, int offset) 
	{
		int length = 0;
		os = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(os);
		System.out.println("os length = " + length);
		System.out.println("os = " + os);
		ip = Serializer.deserializeInt(buf, offset + length);
		System.out.println("ip = " + ip);
		length += 4;
		
		int appNameListLength = Serializer.deserializeInt(buf, offset + length);
		System.out.println("appNameListLength = " + appNameListLength);
		length += 4;
		System.out.println("os length = " + length);
		if( 0 != appNameListLength )
		{
			for(int i = 0 ; i < appNameListLength ; i++)
			{
				System.out.println("appNameListLength index = " + i);
				String appName = Serializer.deserializeString(buf, offset + length);
				System.out.println("appName = " + appName);
				length += Serializer.length(appName);
				System.out.println("length_1 = " + length);
				System.out.println("offset = " + offset);
				String appPath = Serializer.deserializeString(buf, offset + length);	
				System.out.println("appPath = " + appPath);
				length += Serializer.length(appPath);
				System.out.println("length_2 = " + length);
				//System.out.println("appNameList : " + appNameList);
				//System.out.println("appPathList : " + appPathList);
				appNameList.insert(appName);
				appPathList.insert(appPath);
			}
		}
		else
		{
			System.out.println("appNameList = null");
			appNameList = null;
			appPathList = null;
		}
		return length;
	}
	
	public int serialize(byte[] buf, int offset) {
		int length = 0;
		length += Serializer.serialize(buf, offset + length, os);
		length += Serializer.serialize(buf, offset + length, ip);
		
		if(appNameList != null)
		{
			length += Serializer.serialize(buf, offset + length, appNameList.size());
			for(int i = 0 ; i < appNameList.size() ; i++)
			{
				length += Serializer.serialize(buf, offset + length, appNameList.get(i));
				length += Serializer.serialize(buf, offset + length, appPathList.get(i));
			}
		}
		else
		{
			length += Serializer.serialize(buf, offset + length, 0);
		}
		
		return length;
	}
	
	public int length()
	{
		int length = 0;
		length += Serializer.length(os);
		length += 4;
		
		length += 4;
		if( null != appNameList )
		{
			for(int i = 0 ; i < appNameList.size() ; i++)
			{
				length += Serializer.length(appNameList.get(i));
				length += Serializer.length(appPathList.get(i));
			}
		}
		return length;
	}*/

}
