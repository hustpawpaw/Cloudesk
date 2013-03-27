package com.cgcl.cloudesk.manage.packet;

import java.util.LinkedList;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class GetGradeRspPacket extends PacketBase {
	private LinkedList<String> GradeDetails = null;
	private int packetLen;
	
	public GetGradeRspPacket()
	{
		super(PacketConfig.kGetGradePacketRspType, PacketConfig.kAuthorizeModuleId);
		GradeDetails = new LinkedList<String>();
	}
	
	public LinkedList<String> getGradeDetails()
	{
		return this.GradeDetails;
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
			GradeDetails.add(t_str);
		}
		packetLen = length;
		return length;
	}

	public int length() {
		return packetLen;
	}

}
