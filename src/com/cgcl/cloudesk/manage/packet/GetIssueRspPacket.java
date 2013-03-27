package com.cgcl.cloudesk.manage.packet;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cgcl.cloudesk.manage.app.File;
import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class GetIssueRspPacket extends PacketBase {
	private LinkedList<String>issueDetails = null;
	private int packetLen;
	
	public GetIssueRspPacket()
	{
		super(PacketConfig.kGetIssuePacketRspType, PacketConfig.kAuthorizeModuleId);
		issueDetails = new LinkedList<String>();
	}
	
	public LinkedList<String> getIssueDetails()
	{
		return this.issueDetails;
	}
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		//int totalSize;
		//int mapSize;
		int lenSize;
		String t_str;
		
		lenSize = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		for(int i = 0; i < lenSize; i++){
			t_str = Serializer.deserializeString(buf, offset + length);
			length += Serializer.length(t_str);
			//length += Serializer.deserializeInt(buf, offset + length) + 4;
			issueDetails.add(t_str);
		}
		packetLen = length;
		return length;
	}
	
	public int length() {
		return packetLen;
	}
}
