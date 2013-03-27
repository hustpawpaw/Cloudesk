package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class GetUserDirPacket extends PacketBase{
	private String	userName = null;
	
	public GetUserDirPacket()
	{
		super(PacketConfig.kGetUserDirPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public GetUserDirPacket(String userName)
	{
		super(PacketConfig.kGetUserDirPacketType, PacketConfig.kAuthorizeModuleId);
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		userName = Serializer.deserializeString(buf, offset + length);
		length += Serializer.deserializeInt(buf, offset + length) + 4;
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, userName);
		return length;
	}

	public int length() {
		int length = super.length();
		length += Serializer.length(userName);
		return length;
	}
}
