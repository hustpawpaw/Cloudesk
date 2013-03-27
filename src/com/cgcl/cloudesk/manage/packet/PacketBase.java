package com.cgcl.cloudesk.manage.packet;

import com.cgcl.cloudesk.manage.com.Guid;
import com.cgcl.cloudesk.manage.com.Serializable;
import com.cgcl.cloudesk.manage.config.PacketConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public abstract class PacketBase implements Serializable {
	private int					magic = PacketConfig.kDefaultMagic;
	private char				version = PacketConfig.kCurrentVersion;
	private int					len = PacketConfig.kPacketBaseLen;
	private char				type = (char)0;
	private char				moduleId = PacketConfig.kDefaultModuleId;
	private Guid				guid = PacketConfig.kDefaultGuid;
	private int					reference = PacketConfig.kDefaultReference;

	public PacketBase(char type, char moduleId)
	{
		this.len = PacketConfig.kPacketBaseLen;
		this.type = type;
		this.moduleId = moduleId;
	}
	
	public Guid getGuid() {
		return guid;
	}

	public void setGuid(Guid guid) {
		this.guid = guid;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getMagic() {
		return magic;
	}

	public void setMagic(int magic) {
		this.magic = magic;
	}

	public char getModuleId() {
		return moduleId;
	}

	public void setModuleId(char moduleId) {
		this.moduleId = moduleId;
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public char getVersion() {
		return version;
	}

	public void setVersion(char version) {
		this.version = version;
	}

	public int deserialize(byte[] buf, int offset) {
		magic = Serializer.deserializeInt(buf, offset + PacketConfig.kMagicPos);
		version = Serializer.deserializeChar(buf, offset + PacketConfig.kVersionPos);
		len = Serializer.deserializeInt(buf, offset + PacketConfig.kLenPos);
		type = Serializer.deserializeChar(buf, offset + PacketConfig.kTypePos);
		moduleId = Serializer.deserializeChar(buf, offset + PacketConfig.kModuleIdPos);
		guid.deserialize(buf, offset + PacketConfig.kGuidPos);
		return PacketConfig.kPacketBaseLen;
	}

	public int serialize(byte[] buf, int offset) {
		Serializer.serialize(buf, offset + PacketConfig.kMagicPos, magic);
		Serializer.serialize(buf, offset + PacketConfig.kVersionPos, version);
		Serializer.serialize(buf, offset + PacketConfig.kLenPos, len);
		Serializer.serialize(buf, offset + PacketConfig.kTypePos, type);
		Serializer.serialize(buf, offset + PacketConfig.kModuleIdPos, moduleId);
		guid.serialize(buf, offset + PacketConfig.kGuidPos);
		return PacketConfig.kPacketBaseLen;
	}

	public int length() {
		return PacketConfig.kPacketBaseLen;
	}
}
