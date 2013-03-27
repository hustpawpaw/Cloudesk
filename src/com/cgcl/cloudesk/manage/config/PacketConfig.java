package com.cgcl.cloudesk.manage.config;

import com.cgcl.cloudesk.manage.com.Guid;

public class PacketConfig {
	/**
	 * Offset of magic inside PacketBase
	 */
	public static final int		kMagicPos = 0;
	
	/**
	 * Offset of version inside PacketBase
	 */
	public static final int		kVersionPos = kMagicPos + 4;
	
	/**
	 * Offset of len inside PacketBase
	 */
	public static final int		kLenPos = kVersionPos + 2;
	
	/**
	 * Offset of type inside PacketBase
	 */
	public static final int		kTypePos = kLenPos + 4;
	
	/**
	 * Offset of moduleId inside PacketBase
	 */
	public static final int		kModuleIdPos = kTypePos + 2;
	
	/**
	 * Offset of guid inside PacketBase
	 */
	public static final int		kGuidPos = kModuleIdPos + 2;
	
	/**
	 * Length of PacketBase
	 */
	public static final int		kPacketBaseLen = kGuidPos + ComConfig.guidLen;
	
	/**
	 * Default magic
	 */
	public static final int		kDefaultMagic = 0xffffffff;
	
	/**
	 * Current version
	 */
	public static final char	kCurrentVersion = 0x0001;
	
	/**
	 * Default module id
	 */
	public static final char	kDefaultModuleId = 0;
	
	/**
	 * Default guid
	 */
	public static final Guid	kDefaultGuid = new Guid();
	
	/**
	 * Default reference
	 */
	public static final int		kDefaultReference = 0xffffffff;
	
	/**
	 * Id of Authrorize module
	 */
	public static final char	kAuthorizeModuleId = 0x0103;
	
	/**
	 * Id of Client module
	 */
	public static final char	kClientModuleId = 0x0200;
	
	/**
	 * Id of dataserver module
	 */
	public static final char	kDataServerModuleId = 0x0202;
	/**
	 * Type of warning packet
	 */
	public static final char 	kWarningPacketType = 0;
	/**
	 * Type of reg packet
	 */
	public static final char	kUserRegPacketType = 1;
	
	/**
	 * Type of reg responding packet
	 */
	public static final char	kUserRegRspPacketType = 2;
	
	/**
	 * Type of login packet
	 */
	public static final char	kUserLoginPacketType = 3;
	
	/**
	 * Type of login responding packet
	 */
	public static final char	kUserLoginRspPacketType = 4;
	
	/**
	 * Type of first use responding packet
	 */
	public static final char	kFirstUseRspPacketType = 6;
	
	/**
	 * Type of save scene packet
	 */
	public static final char	kSaveScenePacketType = 12;
	/**
	 * 
	 */
	public static final char	kSaveSceneRspPacketType = 38;
	
	/**
	 * Type of mount packet
	 */
	public static final char	kMountPacketType = 32;
	
	/**
	 * Type of unmount packet
	 */
	public static final char	kUnmountPacketType = 34;
	/**
	 * Type of unmount responding packet
	 */
	public static final char	kUnMountRspPacketType = 35;
	/**
	 * Type of restore scene packet
	 */
	public static final char	kRestoreScenePacket = 41;
	
	/**
	 * Type of synchronize time packet
	 */
	public static final char	kSyncTimePacketType = 42;
	
	
	/**
	 * Type of logout packet
	 */
	public static final char	kUserLogoutPacketType = 45;
	
	/**
	 * Type of logout responding packet
	 */
	public static final char	kUserLogoutRspPacketType = 46;
	
	/**
	 * Type of the packet that requests information of all apps
	 */
	public static final char	kAllAppInfoReqPacketType = 50;
	
	/**
	 * Type of the packet that responds information of all apps
	 */
	public static final char	kAllAppInfoRspPacketType = 51;
	
	/**
	 * Type of the packet that requests information of customized apps
	 */
	public static final char	kCustomizeAppInfoReqPacketType = 52;
	
	/**
	 * Type of the packet that responds information of customized apps
	 */
	public static final char	kCustomizeAppInfoRspPacketType = 53;
	
	/**
	 * Type of customize app packet
	 */
	public static final char	kCustomizeAppPacketType = 54;
	
	/**
	 * Type of customize app responding packet
	 */
	public static final char	kCustomizeAppRspPacketType = 55;
	/**
	 * Type of app information to be saved packet
	 */
	public static final char 	kSavingAppInfoPacketType = 56;
	/**
	 * Type of workset information request packet
	 */
	public static final char 	kWorksetInfoReqPacketType = 57;
	/**
	 * Type of workset information responds packet
	 */
	public static final char 	kWorksetInfoRspPacketType = 58;
	/**
	 * Type of respond packet to the request of workset
	 */
	public static final char	kRestoreSceneRspPacketType = 59;
	
	/**
	 * Type of packet to uninstall the application
	 */
	public static final char 	kUnCustomizeAppPacketType = 60;
	/**
	 * Type of packet to response to the uninstall application packet
	 */
	public static final char	kUnCustomizeAppRspPacketType = 61;
	
	public static final char 	kAllCustomizeAppInfoType = 112;
	/**
	 * Type of packet to response to allCustomizeAppInfoReqPacket
	 */
	public static final char 	kAllCustomizeAppInfoRspType = 113;
	/**
	 * Type of packet to send heart beat information
	 */
	
	public static final char	kPingPacketType = 260;
	/**
	 * Type of packet to get the directory information
	 */
	public static final char	kGetUserDirPacketType 		= 300;
	
	public static final char	kGetUserDirRspPacketType 	= 301;
	
	public static final char	kGetFileListPacketType 		= 302;
	
	public static final char	kGetFileListPacketRspType 	= 303;
	
	/**
	 * gradeSystem Packet;
	 */
	public static final char 	kGetIssuePacketReqType		= 400;
	
	public static final char 	kGetIssuePacketRspType 		= 401;
	
	public static final char 	kGetGradePacketReqType		= 402;

	public static final char 	kGetGradePacketRspType 		= 403;
	
	public static final char 	kScoringPacketReqType		= 404;
	
	public static final char 	kScoringPacketRspType 		= 405;
}
