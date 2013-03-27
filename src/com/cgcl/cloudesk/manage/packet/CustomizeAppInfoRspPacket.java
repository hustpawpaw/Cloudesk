package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.com.AppInfo;
import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class CustomizeAppInfoRspPacket extends PacketBase {
	private AppInfo[]			nativeApps = null;
	private AppInfo[]			customizeApps = null;
	
	public CustomizeAppInfoRspPacket()
	{
		super(PacketConfig.kCustomizeAppInfoRspPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public CustomizeAppInfoRspPacket(AppInfo[] nativeApps, AppInfo[] customizeApps)
	{
		super(PacketConfig.kCustomizeAppInfoRspPacketType, PacketConfig.kAuthorizeModuleId);
		this.nativeApps = nativeApps;
		this.customizeApps = customizeApps;
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

	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		
		int nativeAppsNum = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		System.out.println("*********nativeAppsNum " + nativeAppsNum);
		if(0 != nativeAppsNum)
		{
			nativeApps = new AppInfo[nativeAppsNum];
			for(int i = 0; i < nativeAppsNum; ++i)
			{
				nativeApps[i] = new AppInfo();
				length += nativeApps[i].deserialize(buf, offset + length);
				System.out.println("i = " + i);
			}
		}
		else
		{
			System.out.println("nativeApps = null");
			nativeApps = null;
		}

		int customizeAppsNum = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		System.out.println("*********customizeAppsNum " + customizeAppsNum);
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
			System.out.println("customizeApps = null");
			customizeApps = null;
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
		
		return length;
	}
}
