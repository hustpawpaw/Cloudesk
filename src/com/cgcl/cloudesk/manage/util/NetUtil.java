package com.cgcl.cloudesk.manage.util;

import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.packet.AllAppInfoRspPacket;
import com.cgcl.cloudesk.manage.packet.CustomizeAppInfoRspPacket;
import com.cgcl.cloudesk.manage.packet.CustomizeAppRspPacket;
import com.cgcl.cloudesk.manage.packet.GetFileListRspPacket;
import com.cgcl.cloudesk.manage.packet.GetGradeRspPacket;
import com.cgcl.cloudesk.manage.packet.GetUserDirRspPacket;
import com.cgcl.cloudesk.manage.packet.MountPacket;
import com.cgcl.cloudesk.manage.packet.PacketBase;
import com.cgcl.cloudesk.manage.packet.RestoreSceneRspPacket;
import com.cgcl.cloudesk.manage.packet.SaveSceneRspPacket;
import com.cgcl.cloudesk.manage.packet.ScoringRspPacket;
import com.cgcl.cloudesk.manage.packet.UnCustomizeAppRspPacket;
import com.cgcl.cloudesk.manage.packet.UnmountPacket;
import com.cgcl.cloudesk.manage.packet.UserLoginRspPacket;
import com.cgcl.cloudesk.manage.packet.UserRegRspPacket;
import com.cgcl.cloudesk.manage.packet.WarningPacket;
import com.cgcl.cloudesk.manage.packet.WorksetInfoRspPacket;

public class NetUtil {
	/**
	 * Produces a packet from buf
	 * @param buf byte array that contains the packet content
	 * @param offset start point of buf
	 * @param packet produced packet, null indicates no sufficient content
	 * @return length of new produced packet, if the content is not sufficient then return 0
	 */
	public static PacketBase produce(byte[] buf, int offset, int len)
	{
		if(PacketConfig.kPacketBaseLen > len)
		{
			System.out.println("haha1");
			return null;
		}
		int packetLen = Serializer.deserializeInt(buf, offset + PacketConfig.kLenPos);
		System.out.println("packetLen = " + packetLen);
		if( len <= buf.length )
		{
			System.out.println("len <= NetConfig.inputBufLen : " + "len = " + len);
			if( packetLen > buf.length)
			{
				System.out.println("packetLen > NetConfig.inputBufLen : " + "packetLen = " + packetLen);
				WarningPacket packet = new WarningPacket(packetLen);
				System.out.println("packet too long!");
				return packet;
			}
		}
		if(packetLen > len)
		{
			System.out.println("haha2");
			return null;
		}
		
		PacketBase packet = null;
		char type = Serializer.deserializeChar(buf, offset + PacketConfig.kTypePos);
		
		switch(type)
		{
			// to be added
			case PacketConfig.kUserRegRspPacketType:
			{
				System.out.println("receive kUserRegRspPacketType");
				UserRegRspPacket userRegRspPacket = new UserRegRspPacket();
				userRegRspPacket.deserialize(buf, offset);
				packet = userRegRspPacket;
				break;
			}
			
			case PacketConfig.kUserLoginRspPacketType:
			{
				System.out.println("receive kUserLoginRspPacketType");
				UserLoginRspPacket userLoginRspPacket = new UserLoginRspPacket();
				System.out.println("UserLoginRspPacket userLoginRspPacket = new UserLoginRspPacket();");
				userLoginRspPacket.deserialize(buf, offset);
				System.out.println("userLoginRspPacket.deserialize(buf, offset);");
				packet = userLoginRspPacket;
				System.out.println("packet = userLoginRspPacket;");
				break;
			}
			
			case PacketConfig.kAllAppInfoRspPacketType:
			{
				System.out.println("receive kAllAppInfoRspPacketType");
				AllAppInfoRspPacket allAppInfoRspPacket = new AllAppInfoRspPacket();
				allAppInfoRspPacket.deserialize(buf, offset);
				packet = allAppInfoRspPacket;
				break;
			}	
			
			case PacketConfig.kCustomizeAppInfoRspPacketType:
			{
				System.out.println("receive kCustomizeAppInfoRspPacketType");
				CustomizeAppInfoRspPacket customizeAppInfoRspPacket = new CustomizeAppInfoRspPacket();
				customizeAppInfoRspPacket.deserialize(buf, offset);
				packet = customizeAppInfoRspPacket;
				break;
			}
			
			case PacketConfig.kCustomizeAppRspPacketType:
			{
				System.out.println("receive kCustomizeAppRspPacketType");
				CustomizeAppRspPacket customizeAppRspPacket = new CustomizeAppRspPacket();
				customizeAppRspPacket.deserialize(buf, offset);
				packet = customizeAppRspPacket;
				break;
			}
			
			case PacketConfig.kSaveSceneRspPacketType:
			{
				System.out.println("receive kSaveSceneRspPacketType");
				SaveSceneRspPacket saveSceneRspPacket = new SaveSceneRspPacket();
				saveSceneRspPacket.deserialize(buf, offset);
				packet = saveSceneRspPacket;
				break;
			}
			
			case PacketConfig.kUnmountPacketType:
			{
				System.out.println("receive kUnmountPacketType*");
				UnmountPacket unmountPacket = new UnmountPacket();
				unmountPacket.deserialize(buf, offset);
				packet = unmountPacket;
				break;
			}
			
			case PacketConfig.kMountPacketType:
			{
				System.out.println("receive kMountPacketType");
				MountPacket mountPacket = new MountPacket();
				mountPacket.deserialize(buf, offset);
				packet = mountPacket;
				break;
			}
			
			case PacketConfig.kWorksetInfoRspPacketType:
			{
				System.out.println("receive kWorksetInfoRspPacketType");
				WorksetInfoRspPacket worksetInfoRspPacket = new WorksetInfoRspPacket();
				worksetInfoRspPacket.deserialize(buf, offset);
				packet = worksetInfoRspPacket;
				break;
			}
			
			case PacketConfig.kRestoreSceneRspPacketType:
			{
				System.out.println("receive kRestoreSceneRspPacket");
				RestoreSceneRspPacket restoreSceneRspPacket = new RestoreSceneRspPacket();
				restoreSceneRspPacket.deserialize(buf, offset);
				packet = restoreSceneRspPacket;
				break;
			}
			
			case PacketConfig.kUnCustomizeAppRspPacketType:
			{
				System.out.println("receive kUnCustomizeAppRspPacketType");
				UnCustomizeAppRspPacket unCustomizeAppRspPacket = new UnCustomizeAppRspPacket();
				unCustomizeAppRspPacket.deserialize(buf, offset);
				packet = unCustomizeAppRspPacket;
				break;
			}
			
			case PacketConfig.kGetUserDirRspPacketType:
			{
				System.out.println("receive kGetUserDirRspPacketType");
				GetUserDirRspPacket getUserDirRspPacket = new GetUserDirRspPacket();
				getUserDirRspPacket.deserialize(buf, offset);
				packet = getUserDirRspPacket;
				break;
			}
			
			case PacketConfig.kGetFileListPacketRspType:
			{
				System.out.println("receive kGetFileListRspPacketType");
				GetFileListRspPacket getFileListRspPacket = new GetFileListRspPacket();
				getFileListRspPacket.deserialize(buf, offset);
				packet = getFileListRspPacket;
				break;
			}
			// added by dhm 2011 7.17
			case PacketConfig.kGetGradePacketRspType:
			{
				System.out.println("receive kGetGradePacketRspType");
				GetGradeRspPacket getGradeRspPacket = new GetGradeRspPacket();
				getGradeRspPacket.deserialize(buf, offset);
				packet = getGradeRspPacket;
				break;
			}
			
			case PacketConfig.kScoringPacketRspType:
			{
				System.out.println("receive kScoringRspPacketType");
				ScoringRspPacket scoringRspPacket = new ScoringRspPacket ();
				scoringRspPacket.deserialize(buf, offset);
				packet = scoringRspPacket;
				break;
			}
			
			default:
			{
				System.out.println("fatal error, unhandled packet!!!!!!!!!!!!!!!!!!!!!!!!!!");
				break;
			}
		}
		System.out.println("return packet");
		return packet;
	}
}
