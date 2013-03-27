package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class GetFileListPacket extends PacketBase{
	private String	dir = null;
	
	public GetFileListPacket()
	{
		super(PacketConfig.kGetFileListPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public GetFileListPacket(String dir)
	{
		super(PacketConfig.kAllAppInfoReqPacketType, PacketConfig.kAuthorizeModuleId);
		this.dir = dir;
	}
	
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		dir = Serializer.deserializeString(buf, offset + length);
		length += Serializer.deserializeInt(buf, offset + length) + 4;
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, dir);
		return length;
	}

	public int length() {
		int length = super.length();
		length += Serializer.length(dir);
		return length;
	}
}
