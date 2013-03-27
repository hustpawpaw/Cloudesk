package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class SaveScenePacket extends PacketBase {
	private String				username = null;
	
	public SaveScenePacket()
	{
		super(PacketConfig.kSaveScenePacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public SaveScenePacket(String username)
	{
		super(PacketConfig.kSaveScenePacketType, PacketConfig.kAuthorizeModuleId);
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		username = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(username);
		return length;
	}
	
	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, username);
		return length;
	}
	
	public int length() {
		int length = super.length();
		length += Serializer.length(username);
		return length;
	}
}
