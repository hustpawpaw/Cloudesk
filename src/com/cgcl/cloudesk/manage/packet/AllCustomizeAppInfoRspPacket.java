package com.cgcl.cloudesk.manage.packet;


import com.cgcl.cloudesk.manage.com.AppInfo;
import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;



public class AllCustomizeAppInfoRspPacket extends PacketBase {

	private AppInfo[] allCustomizeAppInfoList = null;
	
	public AllCustomizeAppInfoRspPacket(){
		super(PacketConfig.kAllCustomizeAppInfoRspType, PacketConfig.kAuthorizeModuleId);
	}
	
	public AllCustomizeAppInfoRspPacket(AppInfo[] allCustomizeAppInfo){
		super(PacketConfig.kAllCustomizeAppInfoRspType, PacketConfig.kAuthorizeModuleId);
		this.allCustomizeAppInfoList = allCustomizeAppInfo;
	}

	public AppInfo[] getAllCustomizeAppInfo() {
		return allCustomizeAppInfoList;
	}

	public void setAllCustomizeAppInfo(AppInfo[] allCustomizeAppInfoList) {
		this.allCustomizeAppInfoList = allCustomizeAppInfoList;
	}
	
	public int deserialize(byte[] buf, int offset) {
		int length = super.deserialize(buf, offset);
		int allCustomizeAppInfoLength = Serializer.deserializeInt(buf, offset + length);
		
		allCustomizeAppInfoList = new AppInfo[allCustomizeAppInfoLength];
		length += 4;
		if( 0 != allCustomizeAppInfoLength )
		{
			for(int i = 0 ; i < allCustomizeAppInfoLength ; i++)
			{
				AppInfo appInfo = new AppInfo();
				appInfo.deserialize(buf, offset + length);
				length += appInfo.length();
				allCustomizeAppInfoList[i] = appInfo;
			}
		}
		else
		{
			allCustomizeAppInfoList = null;
		}
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = super.serialize(buf, offset);
		if( null != allCustomizeAppInfoList )
		{
			length += Serializer.serialize(buf, offset + length, allCustomizeAppInfoList.length);
			for(int i = 0 ; i < allCustomizeAppInfoList.length ; i++)
			{
				length += allCustomizeAppInfoList[i].serialize(buf, offset + length);
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
		length += 4;
		if( null != allCustomizeAppInfoList )
		{
			for(int i = 0 ; i < allCustomizeAppInfoList.length ; i++)
				length += allCustomizeAppInfoList[i].length();
		}
		return length;
	}
	
}
