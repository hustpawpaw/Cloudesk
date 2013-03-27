package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class UserRegPacket extends PacketBase {
	private String				username = null;
	private String				password = null;
	private String				description = null;
	
	public UserRegPacket()
	{
		super(PacketConfig.kUserRegPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public UserRegPacket(String username, String password, String description)
	{
		super(PacketConfig.kUserRegPacketType, PacketConfig.kAuthorizeModuleId);
		this.username = username;
		this.password = password;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
		description = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(description);
		return length;
	}
	
	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, username);
		length += Serializer.serialize(buf, offset + length, password);
		length += Serializer.serialize(buf, offset + length, description);
		return length;
	}
	
	public int length() {
		int length = super.length();
		length += Serializer.length(username);
		length += Serializer.length(password);
		length += Serializer.length(description);
		return length;
	}
}
