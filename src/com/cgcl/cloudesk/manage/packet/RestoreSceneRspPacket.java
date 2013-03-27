package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.com.VMInfo;
import com.cgcl.cloudesk.manage.config.NetConfig;
import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.IPUtil;
import com.cgcl.cloudesk.manage.util.Serializer;

public class RestoreSceneRspPacket extends PacketBase {

	public VMInfo[] VMs;
	
	public RestoreSceneRspPacket()
	{
		super(PacketConfig.kRestoreSceneRspPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public RestoreSceneRspPacket(VMInfo[] VMs)
	{
		super(PacketConfig.kRestoreSceneRspPacketType, PacketConfig.kAuthorizeModuleId);
		this.VMs = VMs;
	}


	public int deserialize(byte[] buf, int offset)
	{
		int length = super.deserialize(buf, offset);
		int VMNum = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		System.out.println("VMNum = " + VMNum);
		VMs = new VMInfo[VMNum];
		for(int i = 0 ; i < VMNum ; i++)
		{
			System.out.println("i = " + i);
			VMs[i] = new VMInfo();
			length += VMs[i].deserialize(buf, offset + length);
			if( VMs[i].getOs().equals("windows") )
			{
				NetConfig.windowsIP = IPUtil.intToString(VMs[i].getIp());
				System.out.println("NetConfig.windowsIP : " + NetConfig.windowsIP );
			}
			if( VMs[i].getOs().equals("linux") )
			{
				NetConfig.linuxIP = IPUtil.intToString(VMs[i].getIp());
				System.out.println("NetConfig.linuxIP : " + NetConfig.linuxIP );
			}
		}
		
		return length;	
	}
	
	
	
	public int serialize(byte[] buf, int offset)
	{
		int length = super.serialize(buf, offset);
		if( ( null != VMs ) || ( 0 != VMs.length) )
		{
			length += Serializer.serialize(buf, offset + length, VMs.length);
			for(int i = 0 ; i < VMs.length ; i++)
			{
				length += VMs[i].serialize(buf, offset + length);
			}
		}
		else
		{
			length += Serializer.serialize(buf, offset + length, 0);
		}
		
		return length;
		
	}
	
	public int length()
	{
		int length = super.length();
		length += 4;
		if(VMs != null)
		{
			for( int i = 0 ; i < VMs.length ; i++)
			{
				length += VMs[i].length();
			}
		}
		
		return length;
		
	}
	
	
}
