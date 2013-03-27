package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class UnmountPacket extends PacketBase {
	private String				os = null;
	
	public UnmountPacket()
	{
		super(PacketConfig.kUnmountPacketType, PacketConfig.kDataServerModuleId);
	}
	
	public UnmountPacket(String os)
	{
		super(PacketConfig.kUnmountPacketType, PacketConfig.kDataServerModuleId);
		this.os = os;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		os = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(os);
		return length;
	}
	
	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, os);
		return length;
	}
	
	public int length() {
		int length = super.length();
		length += Serializer.length(os);
		return length;
	}
}
