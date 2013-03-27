package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class CustomizeAppPacket extends PacketBase {
	private String				userID = null;
	private String[]			appIDs = null;
	
	public CustomizeAppPacket()
	{
		super(PacketConfig.kCustomizeAppPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public CustomizeAppPacket(String userID, String[] appIDs)
	{
		super(PacketConfig.kCustomizeAppPacketType, PacketConfig.kAuthorizeModuleId);
		this.userID = userID;
		this.appIDs = appIDs;
	}

	public String[] getAppIDs() {
		return appIDs;
	}

	public void setAppIDs(String[] appIDs) {
		this.appIDs = appIDs;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		userID = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(userID);
		
		int appIDsNum = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		if(0 != appIDsNum)
		{
			appIDs = new String[appIDsNum];
			for(int i = 0; i < appIDsNum; ++i)
			{
				appIDs[i] = Serializer.deserializeString(buf, offset + length);
				length += Serializer.length( appIDs[i] );
			}
		}
		else
		{
			appIDs = null;
		}
		
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		length += Serializer.serialize(buf, offset + length, userID);
		
		if(null != appIDs)
		{
			length += Serializer.serialize(buf, offset + length, appIDs.length);
			for(int i = 0; i < appIDs.length; ++i)
			{
				length += Serializer.serialize( buf, offset + length, appIDs[i] );
			}
		}
		else
		{
			length += Serializer.serialize(buf, offset + length, 0);
		}
		
		return length;
	}

	public int length() {
		int length = super.length();
		length += Serializer.length(userID);

		length += 4;
		if(null != appIDs)
		{
			for(int i = 0; i < appIDs.length; ++i)
			{
				length += Serializer.length( appIDs[i] );
			}
		}
		
		return length;
	}
}
