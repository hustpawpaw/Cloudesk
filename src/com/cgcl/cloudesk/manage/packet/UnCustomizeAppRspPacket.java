package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class UnCustomizeAppRspPacket extends PacketBase{
	private boolean bSuccess;
	private String appID;
	
	public UnCustomizeAppRspPacket()
	{
		super(PacketConfig.kUnCustomizeAppRspPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public UnCustomizeAppRspPacket(boolean bSuccess, String appID)
	{
		super(PacketConfig.kUnCustomizeAppRspPacketType, PacketConfig.kAuthorizeModuleId);
		this.bSuccess = bSuccess;
		this.appID = appID;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		bSuccess = Serializer.deserializeBoolean(buf, offset + length);
		length += Serializer.length(bSuccess);
		appID = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(appID);
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, bSuccess);
		length += Serializer.serialize(buf, offset + length, appID);
		return length;
	}

	public int length() {
		int length = super.length();
		length += Serializer.length(bSuccess);
		length += Serializer.length(appID);
		return length;
	}

	public boolean isBSuccess() {
		return bSuccess;
	}

	public void setBSuccess(boolean success) {
		bSuccess = success;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}
	
	
}
