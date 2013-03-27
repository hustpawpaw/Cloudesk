package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class UserLoginPacket extends PacketBase {
	private String				username = null;
	private String				password = null;
	
	public UserLoginPacket()
	{
		super(PacketConfig.kUserLoginPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public UserLoginPacket(String username, String password)
	{
		super(PacketConfig.kUserLoginPacketType, PacketConfig.kAuthorizeModuleId);
		this.username = username;
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
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
		password = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(password);
		return length;
	}
	
	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, username);
		length += Serializer.serialize(buf, offset + length, password);
		return length;
	}
	
	public int length() {
		int length = super.length();
		length += Serializer.length(username);
		length += Serializer.length(password);
		return length;
	}
}
