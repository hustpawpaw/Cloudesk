package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;





public class AllCustomizeAppInfoPacket extends PacketBase {
	private String userName ;
	
	public AllCustomizeAppInfoPacket()
	{
		super(PacketConfig.kAllCustomizeAppInfoType, PacketConfig.kAuthorizeModuleId);
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		userName = Serializer.deserializeString(buf, offset);
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		Serializer.serialize(buf, offset + length, userName);
		length += Serializer.length(userName);
		return length;
	}

	public int length() {
		int length = super.length();
		length += Serializer.length(userName);
		return length;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
