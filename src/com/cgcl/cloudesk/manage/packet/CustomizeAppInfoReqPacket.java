package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class CustomizeAppInfoReqPacket extends PacketBase {
	private String				userID = null;
	private String 				useTime = null;
	
	public CustomizeAppInfoReqPacket()
	{
		super(PacketConfig.kCustomizeAppInfoReqPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public CustomizeAppInfoReqPacket(String userID, String useTime)
	{
		super(PacketConfig.kCustomizeAppInfoReqPacketType, PacketConfig.kAuthorizeModuleId);
		this.userID = userID;
		this.useTime = useTime;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		userID = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(userID);
		useTime = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(useTime);
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, userID);
		length += Serializer.serialize(buf, offset + length, useTime);
		return length;
	}

	public int length() {
		int length = super.length();
		length += Serializer.length(userID);
		length += Serializer.length(useTime);
		return length;
	}
}
