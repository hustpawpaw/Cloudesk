package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;

public class WarningPacket extends PacketBase 
{
	int length;
	
	
	public WarningPacket()
	{
		super(PacketConfig.kWarningPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	
	public WarningPacket(int length)
	{
		super(PacketConfig.kWarningPacketType, PacketConfig.kAuthorizeModuleId);
		this.length = length;
	}
	
	
	
}
