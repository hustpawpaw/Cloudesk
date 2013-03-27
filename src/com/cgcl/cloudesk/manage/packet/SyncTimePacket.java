package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class SyncTimePacket extends PacketBase {
	private String				time = null;
	
	public SyncTimePacket()
	{
		super(PacketConfig.kSyncTimePacketType, PacketConfig.kClientModuleId);
	}
	
	public SyncTimePacket(String time)
	{
		super(PacketConfig.kSyncTimePacketType, PacketConfig.kClientModuleId);
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		time = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(time);
		return length;
	}
	
	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, time);
		return length;
	}
	
	public int length() {
		int length = super.length();
		length += Serializer.length(time);
		return length;
	}
}
