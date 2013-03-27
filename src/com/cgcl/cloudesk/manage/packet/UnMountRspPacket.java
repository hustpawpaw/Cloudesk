package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class UnMountRspPacket extends PacketBase{
	private String userName;
	private String result;
	
	public UnMountRspPacket()
	{
		super(PacketConfig.kUnMountRspPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public UnMountRspPacket(String userName, String result)
	{
		super(PacketConfig.kUnMountRspPacketType, PacketConfig.kAuthorizeModuleId);
		this.userName = userName;
		this.result = result;
	}

	public String getUserName() 
	{
		return userName;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getResult() 
	{
		return result;
	}

	public void setResult(String result) 
	{
		this.result = result;
	}
	
	public int deserialize(byte[] buf, int offset)
	{
		int length = super.deserialize(buf, offset);
		userName = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(userName);
		result = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(result);
		return length;
	}
	
	public int serialize(byte[] buf, int offset)
	{
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, userName);
		length += Serializer.serialize(buf, offset + length, result);
		return length;
	}
	
	public int length()
	{
		int length = super.length();
		length += Serializer.length(userName);
		length += Serializer.length(result);
		return length;
	}
}
