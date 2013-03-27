package com.cgcl.cloudesk.manage.com;

import com.cgcl.cloudesk.manage.util.Serializer;

public class CustomizeAppInfo implements Serializable {
	private String				appID = null;

	public int deserialize(byte[] buf, int offset) {
		int length = 0;
		appID = Serializer.deserializeString(buf, offset + length);
		length += Serializer.length(appID);
		return length;
	}

	public int length() {
		int length = 0;
		length += Serializer.length(appID);
		return length;
	}

	public int serialize(byte[] buf, int offset) {
		int length = 0;
		length += Serializer.serialize(buf, offset + length, appID);
		return length;
	}

}
