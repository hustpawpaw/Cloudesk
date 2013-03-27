package com.cgcl.cloudesk.manage.com;

import com.cgcl.cloudesk.manage.config.ComConfig;
import com.cgcl.cloudesk.manage.util.Serializer;

public class Guid implements Serializable {
	/** 
	 * Inside buffer of the Guid
	 */
	private byte[]				insideBuf = new byte[ComConfig.guidLen];

	public byte[] getInsideBuf() {
		return insideBuf;
	}

	public void setInsideBuf(byte[] insideBuf) {
		this.insideBuf = insideBuf;
	}

	public int deserialize(byte[] buf, int offset) {
		return Serializer.deserialize(buf, offset, insideBuf);
	}

	public int serialize(byte[] buf, int offset) {
		return Serializer.serialize(buf, offset, insideBuf);
	}

	public int length() {
		return insideBuf.length;
	}
}
