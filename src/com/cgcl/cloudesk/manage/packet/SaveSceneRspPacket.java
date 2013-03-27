package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class SaveSceneRspPacket extends PacketBase {
	private String saveTime;
	private int    errorCode;
	
	public SaveSceneRspPacket()
	{
		super(PacketConfig.kSaveSceneRspPacketType, PacketConfig.kClientModuleId);
	}
	
	public SaveSceneRspPacket(String saveTime , int errorCode)
	{
		super(PacketConfig.kSaveSceneRspPacketType, PacketConfig.kClientModuleId);
		this.saveTime = saveTime;
		this.errorCode = errorCode;
	}

	public String getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		saveTime = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(saveTime);
		errorCode = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		return length;
	}
	
	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, saveTime);
		length += Serializer.serialize(buf, offset + length, errorCode);
		return length;
	}
	
	public int length() {
		int length = super.length();
		length += Serializer.length(saveTime);
		length += 4;
		return length;
	}
	
}
