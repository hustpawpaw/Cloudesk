package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class ScoringRspPacket extends PacketBase {
private String				RspMsg = null;
	
	public ScoringRspPacket()
	{
		super(PacketConfig.kScoringPacketRspType, PacketConfig.kAuthorizeModuleId);
	}
	
	public ScoringRspPacket(String RspMsg)
	{
		super(PacketConfig.kScoringPacketRspType, PacketConfig.kAuthorizeModuleId);
		this.RspMsg = RspMsg;
	}
	
	public String getRspMsg() {
		return RspMsg;
	}

	public void setRspMsg(String RspMsg) {
		this.RspMsg = RspMsg;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		RspMsg = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(RspMsg);
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, RspMsg);
		return length;
	}

	public int length() {
		int length = super.length();
		length += Serializer.length(RspMsg);
		return length;
	}
}
