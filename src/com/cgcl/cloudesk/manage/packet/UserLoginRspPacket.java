package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class UserLoginRspPacket extends PacketBase {
	private boolean				bSuccess = false;
	
	public UserLoginRspPacket()
	{
		super(PacketConfig.kUserLoginRspPacketType, PacketConfig.kClientModuleId);
	}
	
	public UserLoginRspPacket(boolean bSuccess)
	{
		super(PacketConfig.kUserLoginRspPacketType, PacketConfig.kClientModuleId);
		this.bSuccess = bSuccess;
	}

	public boolean isBSuccess() {
		return bSuccess;
	}

	public void setBSuccess(boolean success) {
		bSuccess = success;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		bSuccess = Serializer.deserializeBoolean(buf, offset + length);
		length += Serializer.length(bSuccess);
		return length;
	}
	
	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, bSuccess);
		return length;
	}
	
	public int length() {
		int length = super.length();
		length += Serializer.length(bSuccess);
		return length;
	}
}
