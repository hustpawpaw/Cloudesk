package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class AllAppInfoReqPacket extends PacketBase {
	private String				userID = null;
	
	public AllAppInfoReqPacket()
	{
		super(PacketConfig.kAllAppInfoReqPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public AllAppInfoReqPacket(String userID)
	{
		super(PacketConfig.kAllAppInfoReqPacketType, PacketConfig.kAuthorizeModuleId);
		this.userID = userID;
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		userID = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(userID);
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, userID);
		return length;
	}

	public int length() {
		int length = super.length();
		length += Serializer.length(userID);
		return length;
	}
}
