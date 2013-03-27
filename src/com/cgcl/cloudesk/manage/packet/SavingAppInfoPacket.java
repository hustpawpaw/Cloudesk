package com.cgcl.cloudesk.manage.packet;

import java.util.Vector;

import com.cgcl.cloudesk.manage.com.RunningAppInfo;
import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class SavingAppInfoPacket extends PacketBase {

	private String userName;
	private String saveTime;
	private static Vector runningAppInfos;
	
	public SavingAppInfoPacket()
	{
		super(PacketConfig.kSavingAppInfoPacketType, PacketConfig.kAuthorizeModuleId);
	}
	
	public SavingAppInfoPacket(String userName, String saveTime, Vector runningAppInfos)
	{
		super(PacketConfig.kSavingAppInfoPacketType, PacketConfig.kAuthorizeModuleId);
		this.userName = userName;
		this.saveTime = saveTime;
		this.runningAppInfos = runningAppInfos;
	}
	
	public static Vector getRunningAppInfos()
	{
		return runningAppInfos;
	}

	public static void setRunningAppInfos(Vector runningAppInfos)
	{
		SavingAppInfoPacket.runningAppInfos = runningAppInfos;
	}

	public String getUserName() 
	{
		return userName;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getSaveTime() 
	{
		return saveTime;
	}

	public void setSaveTime(String saveTime) 
	{
		this.saveTime = saveTime;
	}

	public int deserialize(byte[] buf, int offset)
	{
		int length = super.deserialize(buf, offset);
		
		userName = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(userName);
		saveTime = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(saveTime);
		
		
		int appInfoLength = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		if( 0 != appInfoLength )
		{
			for(int i = 0 ; i < appInfoLength ; i++)
			{
				RunningAppInfo runningAppInfo = new RunningAppInfo();
				runningAppInfo.deserialize(buf, offset + length);
				
				length += runningAppInfo.length();
				runningAppInfos.addElement(runningAppInfo);
			}
		}
		else
		{
			runningAppInfos = null;
		}
		
		return length;
	}
	
	
	
	public int serialize(byte[] buf, int offset)
	{
		int length = super.serialize(buf, offset);
		
		length += Serializer.serialize(buf, offset + length, userName);
		length += Serializer.serialize(buf, offset + length, saveTime);
		
		if(runningAppInfos != null)
		{
			length += Serializer.serialize(buf, offset + length, runningAppInfos.size());
			for(int i = 0 ; i < runningAppInfos.size() ; i++)
			{
				RunningAppInfo runningAppInfo = (RunningAppInfo)runningAppInfos.elementAt(i);
				length += runningAppInfo.serialize(buf, offset + length);
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
		length += Serializer.length(userName);
		length += Serializer.length(saveTime);
		length += 4;
		if( null != runningAppInfos )
		{
			for(int i = 0 ; i < runningAppInfos.size() ; i++)
			{
				RunningAppInfo runningAppInfo = (RunningAppInfo)runningAppInfos.elementAt(i);
				length += runningAppInfo.length();
			}
		}
		return length;
	}
}
