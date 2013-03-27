package com.cgcl.cloudesk.manage.packet;

import java.util.LinkedList;
import java.util.List;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class ScoringReqPacket extends PacketBase {
	private LinkedList<String> scoringList ;
	private int packetLen;
	public ScoringReqPacket()
	{
		super(PacketConfig.kScoringPacketReqType, PacketConfig.kAuthorizeModuleId);
		scoringList  = new LinkedList<String>();
	}
	
	public ScoringReqPacket(LinkedList<String> t)
	{
		super(PacketConfig.kScoringPacketReqType, PacketConfig.kAuthorizeModuleId);
		this.scoringList  = t;
	}
	
	public LinkedList<String> getScoringList()
	{
		return this.scoringList;
	}
	
	public void setScoringList(LinkedList<String> t)
	{
		this.scoringList = t;
	}
	
	public int serialize(byte buf[], int offset)
	{
		int length = super.serialize(buf, offset);
		if( scoringList != null)
		{
			length += Serializer.serialize(buf, offset + length, scoringList.size());
			for(int i = 0 ; i < scoringList.size() ; i++)
			{
				length += Serializer.serialize(buf, offset + length, scoringList.get(i));
			}
		}
		else
		{
			length += Serializer.serialize(buf, offset + length, 0);
		}
		packetLen = length;
		return length;
	}
	
	public int length() {
		int length = super.length();
		
		if( scoringList != null)
		{
			length += 4;
			for(int i = 0 ; i < scoringList.size() ; i++)
			{
				length += Serializer.length(scoringList.get(i));
			}
		}
		else
		{
			length += 4;
		}
		return length;
	}
}
