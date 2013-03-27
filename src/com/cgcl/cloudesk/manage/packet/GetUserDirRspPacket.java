package com.cgcl.cloudesk.manage.packet;

import java.util.HashMap;
import java.util.Map;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class GetUserDirRspPacket extends PacketBase{
	private Map<String , String> namePathPair;
	
	int    packetLen = 0;
	
	public GetUserDirRspPacket()
	{
		super(PacketConfig.kGetUserDirPacketType, PacketConfig.kAuthorizeModuleId);
		namePathPair = new HashMap<String, String>();
	}
	
	
	
	public Map<String , String> getNamePathPair() {
		return namePathPair;
	}

	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		int mapSize;
		
		mapSize = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		
		for(int i = 0; i < mapSize ; i ++) {
			String dirName = Serializer.deserializeString(buf, offset + length);
			length += Serializer.length(dirName);
			//length += Serializer.deserializeInt(buf, offset + length) + 4;
			String path = Serializer.deserializeString(buf, offset + length);
			length += Serializer.length(path);
			//length += Serializer.deserializeInt(buf, offset + length) + 4;
			
			namePathPair.put(dirName, path);
		}
		packetLen = length;
		return length;
	}

	public int length() {
		return packetLen;
	}
}

