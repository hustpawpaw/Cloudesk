package com.cgcl.cloudesk.manage.packet;

import java.util.LinkedList;
import java.util.List;

import com.cgcl.cloudesk.manage.app.File;
import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class GetFileListRspPacket extends PacketBase{
	private List<File>	fileList = null;
	
	private int packetLen;
	
	public GetFileListRspPacket()
	{
		super(PacketConfig.kAllAppInfoRspPacketType, PacketConfig.kAuthorizeModuleId);
		fileList = new LinkedList<File>();
	}
	
	public List<File> getFileList() {
		return fileList;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		int lenSize;
		
		lenSize = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		for(int i = 0; i < lenSize; i++){
			File file 	  = new File();
			file.type     = (char)Serializer.deserializeByte(buf, offset + length);
			length += 1;
			file.fileName = Serializer.deserializeString(buf, offset + length);
			length += Serializer.length(file.fileName);
			//length += Serializer.deserializeInt(buf, offset + length) + 4;
			fileList.add(file);
		}
		packetLen = length;
		return length;
	}
	
	public int length() {
		return packetLen;
	}
}
