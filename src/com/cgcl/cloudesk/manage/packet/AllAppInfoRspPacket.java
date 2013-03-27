package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.com.AppInfo;
import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class AllAppInfoRspPacket extends PacketBase {
	private AppInfo[]			nativeApps = null;
	private AppInfo[]			customizeApps = null;
	private AppInfo[]			uncustomizeApps = null;
	
	public AllAppInfoRspPacket()
	{
		super(PacketConfig.kAllAppInfoRspPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public AllAppInfoRspPacket(AppInfo[] nativeApps, AppInfo[] customizeApps, AppInfo[] uncustomizeApps)
	{
		super(PacketConfig.kAllAppInfoRspPacketType, PacketConfig.kAuthorizeModuleId);
		this.nativeApps = nativeApps;
		this.customizeApps = customizeApps;
		this.uncustomizeApps = uncustomizeApps;
	}

	public AppInfo[] getCustomizeApps() {
		return customizeApps;
	}

	public void setCustomizeApps(AppInfo[] customizeApps) {
		this.customizeApps = customizeApps;
	}

	public AppInfo[] getNativeApps() {
		return nativeApps;
	}

	public void setNativeApps(AppInfo[] nativeApps) {
		this.nativeApps = nativeApps;
	}

	public AppInfo[] getUncustomizeApps() {
		return uncustomizeApps;
	}

	public void setUncustomizeApps(AppInfo[] uncustomizeApps) {
		this.uncustomizeApps = uncustomizeApps;
	}

	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		System.out.println("base length: " + length + " offset: " + offset);
		int nativeAppsNum = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		System.out.println("nativeAppsNum: " + nativeAppsNum);
		if(0 != nativeAppsNum)
		{
			nativeApps = new AppInfo[nativeAppsNum];
			for(int i = 0; i < nativeAppsNum; ++i)
			{
				nativeApps[i] = new AppInfo();
				System.out.println("i = " + i);
				length += nativeApps[i].deserialize(buf, offset + length);
			}
		}
		else
		{
			nativeApps = null;
		}
		
		int customizeAppsNum = Serializer.deserializeInt(buf, offset + length);
		System.out.println("oooooooooooooooooooooooooooocustomizeAppsNum: " + customizeAppsNum);
		length += 4;
		if(0 != customizeAppsNum)
		{
			customizeApps = new AppInfo[customizeAppsNum];
			for(int i = 0; i < customizeAppsNum; ++i)
			{
				customizeApps[i] = new AppInfo();
				length += customizeApps[i].deserialize(buf, offset + length);
			}
		}
		else
		{
			customizeApps = null;
		}
		
		int uncustomizeAppsNum = Serializer.deserializeInt(buf, offset + length);
		System.out.println("oooooooooooooooooooooooooooouncustomizeAppsNum: " + uncustomizeAppsNum);
		length += 4;
		if(0 != uncustomizeAppsNum)
		{
			uncustomizeApps = new AppInfo[uncustomizeAppsNum];
			for(int i = 0; i < uncustomizeAppsNum; ++i)
			{
				uncustomizeApps[i] = new AppInfo();
				length += uncustomizeApps[i].deserialize(buf, offset + length);
			}
		}
		else
		{
			uncustomizeApps = null;
		}
		
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		
		if(null != nativeApps)
		{
			length += Serializer.serialize(buf, offset + length, nativeApps.length);
			for(int i = 0; i < nativeApps.length; ++i)
			{
				length += nativeApps[i].serialize(buf, offset + length);
			}
		}
		else
		{
			length += Serializer.serialize(buf, offset + length, 0);
		}
		
		if(null != customizeApps)
		{
			length += Serializer.serialize(buf, offset + length, customizeApps.length);
			for(int i = 0; i < customizeApps.length; ++i)
			{
				length += customizeApps[i].serialize(buf, offset + length);
			}
		}
		else
		{
			length += Serializer.serialize(buf, offset + length, 0);
		}
		
		if(null != uncustomizeApps)
		{
			length += Serializer.serialize(buf, offset + length, uncustomizeApps.length);
			for(int i = 0; i < uncustomizeApps.length; ++i)
			{
				length += uncustomizeApps[i].serialize(buf, offset + length);
			}
		}
		else
		{
			length += Serializer.serialize(buf, offset + length, 0);
		}
		
		return length;
	}

	public int length() {
		int length = super.length();
		
		length += 4;
		if(null != nativeApps)
		{
			for(int i = 0; i < nativeApps.length; ++i)
			{
				length += nativeApps[i].length();
			}
		}
		
		length += 4;
		if(null != customizeApps)
		{
			for(int i = 0; i < customizeApps.length; ++i)
			{
				length += customizeApps[i].length();
			}
		}
		
		length += 4;
		if(null != uncustomizeApps)
		{
			for(int i = 0; i < uncustomizeApps.length; ++i)
			{
				length += uncustomizeApps[i].length();
			}
		}
		
		return length;
	}
}
