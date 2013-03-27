package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class RestoreScenePacket extends PacketBase {
	private String				username = null;
	private String				time = null;
	
	public RestoreScenePacket()
	{
		super(PacketConfig.kRestoreScenePacket, PacketConfig.kAuthorizeModuleId);
	}
	
	public RestoreScenePacket(String username, String time)
	{
		super(PacketConfig.kRestoreScenePacket, PacketConfig.kAuthorizeModuleId);
		this.username = username;
		this.time = time;
	}
	

	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
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
		time = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(time);
		return length;
	}
	
	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, username);
		length += Serializer.serialize(buf, offset + length, time);
		return length;
	}
	
	public int length() {
		int length = super.length();
		length += Serializer.length(username);
		length += Serializer.length(time);
		return length;
	}
}
