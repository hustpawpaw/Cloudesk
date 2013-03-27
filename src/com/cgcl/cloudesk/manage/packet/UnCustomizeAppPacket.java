package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class UnCustomizeAppPacket extends PacketBase {

	private String	userID;
	private String  appID;
	
	public UnCustomizeAppPacket()
	{
		super(PacketConfig.kUnCustomizeAppPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public UnCustomizeAppPacket(String userID, String appID)
	{
		super(PacketConfig.kUnCustomizeAppPacketType, PacketConfig.kAuthorizeModuleId);
		this.userID = userID;
		this.appID = appID;
	}
	
	public int deserialize(byte[] buf, int offset)
	{
		int length = super.deserialize(buf, offset);
		userID = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(userID);
		appID = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(appID);
		return length;
	}
	
	public int serialize(byte[] buf, int offset)
	{
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, userID);
		length += Serializer.serialize(buf, offset + length, appID);
		return length;
	}
	
	public int length()
	{
		int length = super.length();
		length += Serializer.length(userID);
		length += Serializer.length(appID);
		return length;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}
	
	
}
