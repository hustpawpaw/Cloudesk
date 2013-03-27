package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class WorksetInfoReqPacket extends PacketBase {

	private String userName = null;
	
	public WorksetInfoReqPacket()
	{
		super(PacketConfig.kWorksetInfoReqPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public WorksetInfoReqPacket(String userName)
	{
		super(PacketConfig.kWorksetInfoReqPacketType, PacketConfig.kAuthorizeModuleId);
		this.userName = userName;
	}

	public String getUserName() 
	{
		return userName;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
	}
	
	public int deserialize(byte[] buf, int offset)
	{
		int length = super.deserialize(buf, offset);
		userName = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(userName);
		return length;
	}
	
	public int serialize(byte[] buf, int offset)
	{
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, userName);
		return length;
	}
	
	public int length()
	{
		int length = super.length();
		length += Serializer.length(userName);
		return length;
	}
}
