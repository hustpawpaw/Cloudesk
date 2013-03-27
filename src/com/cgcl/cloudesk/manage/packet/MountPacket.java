package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class MountPacket extends PacketBase {
	private String				os = null;
	private String				userspacePath = null;
	private String				domuIp = null;
	
	public MountPacket()
	{
		super(PacketConfig.kFirstUseRspPacketType, PacketConfig.kDataServerModuleId);
	}
	
	public MountPacket(String os, String userspacePath, String domuIp)
	{
		super(PacketConfig.kFirstUseRspPacketType, PacketConfig.kDataServerModuleId);
		this.os = os;
		this.userspacePath = userspacePath;
		this.domuIp = domuIp;
	}
	
	public String getDomuIp() {
		return domuIp;
	}
	
	public void setDomuIp(String domuIp) {
		this.domuIp = domuIp;
	}
	
	public String getOs() {
		return os;
	}
	
	public void setOs(String os) {
		this.os = os;
	}
	
	public String getUserspacePath() {
		return userspacePath;
	}
	
	public void setUserspacePath(String userspacePath) {
		this.userspacePath = userspacePath;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		os = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(os);
		userspacePath = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(userspacePath);
		domuIp = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(domuIp);
		return length;
	}
	
	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, os);
		length += Serializer.serialize(buf, offset + length, userspacePath);
		length += Serializer.serialize(buf, offset + length, domuIp);
		return length;
	}
	
	public int length() {
		int length = super.length();
		length += Serializer.length(os);
		length += Serializer.length(userspacePath);
		length += Serializer.length(domuIp);
		return length;
	}
}
