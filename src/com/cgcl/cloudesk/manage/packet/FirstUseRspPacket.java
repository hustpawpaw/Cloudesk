package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class FirstUseRspPacket extends PacketBase {
	private String				appServerIp = null;
	private String				os = null;
	
	public FirstUseRspPacket()
	{
		super(PacketConfig.kFirstUseRspPacketType, PacketConfig.kClientModuleId);
	}
	
	public FirstUseRspPacket(String appServerIp, String os)
	{
		super(PacketConfig.kFirstUseRspPacketType, PacketConfig.kClientModuleId);
		this.appServerIp = appServerIp;
		this.os = os;
	}
	
	public String getAppServerIp() {
		return appServerIp;
	}
	
	public void setAppServerIp(String appServerIp) {
		this.appServerIp = appServerIp;
	}
	
	public String getOs() {
		return os;
	}
	
	public void setOs(String os) {
		this.os = os;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		appServerIp = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(appServerIp);
		os = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(os);
		return length;
	}
	
	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, appServerIp);
		length += Serializer.serialize(buf, offset + length, os);
		return length;
	}
	
	public int length() {
		int length = super.length();
		length += Serializer.length(appServerIp);
		length += Serializer.length(os);
		return length;
	}
}
