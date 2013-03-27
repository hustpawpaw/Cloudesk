package com.cgcl.cloudesk.manage.com;

import com.cgcl.cloudesk.manage.config.ComConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class AppInfo implements Serializable {
	private String				ID = null;
	private byte[]				icon = null;
	private int					osType = ComConfig.windowsType;
	private String				path = null;
	private String				version = null;

	public AppInfo()
	{
		
	}
	
	public AppInfo(String ID, byte[] icon, int osType, String path, String version)
	{
		this.ID = ID;
		this.icon = icon;
		this.osType = osType;
		this.path = path;
		this.version = version;
	}
	
	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public int getOsType() {
		return osType;
	}

	public void setOsType(int osType) {
		this.osType = osType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int deserialize(byte[] buf, int offset) {
		int length = 0;
		ID = Serializer.deserializeString(buf, offset + length);
		length += Serializer.deserializeInt(buf, offset + length) + 4;
		icon = Serializer.deserializeByteArray(buf, offset + length);
		length += Serializer.length(icon);
		osType = Serializer.deserializeInt(buf, offset + length);
		length += 4;
		path = Serializer.deserializeString(buf, offset + length);
		length += Serializer.deserializeInt(buf, offset + length) + 4;
		version = Serializer.deserializeString(buf, offset + length);
		length += Serializer.deserializeInt(buf, offset + length) + 4;
		return length;
	}

	public int length() {
		int length = 0;
		length += Serializer.length(ID);
		length += Serializer.length(icon);
		length += 4;
		length += Serializer.length(path);
		length += Serializer.length(version);
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = 0;
		length += Serializer.serialize(buf, offset + length, ID);
		length += Serializer.serializeByteArray(buf, offset + length, icon);
		length += Serializer.serialize(buf, offset + length, osType);
		length += Serializer.serialize(buf, offset + length, path);
		length += Serializer.serialize(buf, offset + length, version);
		return length;
	}
}
