package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.com.VUEList;
import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class WorksetInfoRspPacket extends PacketBase {

	VUEList worksetList = null;
	
	public WorksetInfoRspPacket()
	{
		super(PacketConfig.kWorksetInfoRspPacketType, PacketConfig.kAuthorizeModuleId);
		worksetList = new VUEList();
	}
	
	public WorksetInfoRspPacket(VUEList worksetList)
	{
		super(PacketConfig.kWorksetInfoRspPacketType, PacketConfig.kAuthorizeModuleId);
		this.worksetList = worksetList;
	}

	public VUEList getWorksetList() 
	{
		return worksetList;
	}

	public void setWorksetList(VUEList worksetList) 
	{
		this.worksetList = worksetList;
	}
	
	public int deserialize(byte buf[], int offset)
	{
		
		int length = super.deserialize(buf, offset);
		int worksetNum = Serializer.deserializeInt(buf, offset + length);
		System.out.println("worksetNum : " + worksetNum);
		length += 4;
		if( worksetNum != 0)
		{
			
			for(int i = 0 ; i < worksetNum ; i++)
			{
				String worksetItem = Serializer.deserializeString(buf, offset + length);
				worksetList.insert(worksetItem);
				length += Serializer.length(worksetItem);
			}
		}
		
		
		return length;
	}
	
	public int serialize(byte buf[], int offset)
	{
		int length = super.serialize(buf, offset);
		if( worksetList != null)
		{
			length += Serializer.serialize(buf, offset + length, worksetList.size());
			for(int i = 0 ; i < worksetList.size() ; i++)
			{
				length += Serializer.serialize(buf, offset + length, worksetList.get(i));
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
		for(int i = 0 ; i < worksetList.size() ; i++)
		{
			length += Serializer.length(worksetList.get(i));
		}
		
		return length;
	}
	
}
