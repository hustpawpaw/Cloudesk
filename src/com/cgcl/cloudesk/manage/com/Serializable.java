package com.cgcl.cloudesk.manage.com;

public interface Serializable {
	/**
	 * Serializes the content of object derived from Serializable to buf
	 * @param buf byte array that serialize to
	 * @param offset start point of buf
	 * @return serialized length
	 */
	int							serialize(byte[] buf, int offset);
	
	/**
	 * Deserializes the content of object derived from Serializable from buf
	 * @param buf byte array that serialize from
	 * @param offset start point of buf
	 * @return deserialized length
	 */
	int							deserialize(byte[] buf, int offset);
	
	/**
	 * Gets the length of object derived from Serializable
	 * @return current object's length
	 */
	int							length();
}
