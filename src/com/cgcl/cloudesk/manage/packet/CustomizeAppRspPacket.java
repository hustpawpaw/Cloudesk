package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.com.AppInfo;
import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class CustomizeAppRspPacket extends PacketBase {
	private boolean				bSuccess = false;
	private AppInfo				appInfo;
	
	public CustomizeAppRspPacket()
	{
		super(PacketConfig.kCustomizeAppRspPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public CustomizeAppRspPacket(boolean bSuccess, AppInfo appInfo)
	{
		super(PacketConfig.kCustomizeAppRspPacketType, PacketConfig.kAuthorizeModuleId);
		this.bSuccess = bSuccess;
		this.appInfo = appInfo;
	}

	public boolean isBSuccess() {
		return bSuccess;
	}

	public void setBSuccess(boolean success) {
		bSuccess = success;
	}
	
	public void setAppInfo(AppInfo appInfo)
	{
		this.appInfo = appInfo;
	}

	
	public AppInfo getAppInfo() {
		return appInfo;
	}

	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		bSuccess = Serializer.deserializeBoolean(buf, offset + length);
		length += Serializer.length(bSuccess);
		appInfo = new AppInfo();
		appInfo.deserialize(buf, offset + length);
		length += appInfo.length();
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, bSuccess);
		length += appInfo.serialize(buf, offset + length);
		return length;
	}

	public int length() {
		int length = super.length();
		length += Serializer.length(bSuccess);
		length += appInfo.length();
		return length;
	}
}
