package com.cgcl.cloudesk.manage.util;

import java.io.UnsupportedEncodingException;

public class Serializer {
	/**
	 * Serializes boolean to buf
	 * @param buf byte array that serialize to
	 * @param offset start point of buf
	 * @param b boolean that serialize from
	 * @return serialized length
	 */
	public static int serialize(byte[] buf, int offset, boolean b)
	{
		if(b)
		{
			serialize(buf, offset, (byte)1);
		}
		else
		{
			serialize(buf, offset, (byte)0);
		}
		return 1;
	}
	
	/**
	 * Deserializes a boolean from buf
	 * @param buf byte array that deserialize from
	 * @param offset start point of buf
	 * @return boolean deserialized from buf
	 */
	public static boolean deserializeBoolean(byte[] buf, int offset)
	{
		byte b = deserializeByte(buf, offset);
		if(0 != b)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Serializes byte to buf
	 * @param buf byte array that serialize to
	 * @param offset start point of buf
	 * @param b byte that serialize from
	 * @return serialized length
	 */
	public static int serialize(byte[] buf, int offset, byte b)
	{
		buf[offset++] = b;
		return 1;
	}
	
	/**
	 * Deserializes a byte from buf
	 * @param buf byte array that deserialize from
	 * @param offset start point of buf
	 * @return byte deserialized from buf
	 */
	public static byte deserializeByte(byte[] buf, int offset)
	{
		byte b = (byte)0;
		b = buf[offset];
		return b;
	}
	
	/**
	 * Serializes char to buf
	 * @param buf byte array that serialize to
	 * @param offset start point of buf
	 * @param c char that serialize from
	 * @return serialized length
	 */
	public static int serialize(byte[] buf, int offset, char c)
	{
		buf[offset++] = (byte)c;
		buf[offset++] = (byte)(c >> 8);
		return 2;
	}
	
	/**
	 * Deserializes a char from buf
	 * @param buf byte array that deserialize from
	 * @param offset start point of buf
	 * @return char deserialized from buf
	 */
	public static char deserializeChar(byte[] buf, int offset)
	{		
		char c = (char)0;
		c += buf[offset + 1] & 0xff;
		c <<= 8;
		c += buf[offset] & 0xff;
		return c;
	}
	/**
	 * Serialize short to buf
	 * @param buf byte array that serialize to
	 * @param offset start point if buf
	 * @return serialized length
	 */
	public static int serialize(byte[] buf, int offset, short n)
	{
		buf[offset++] = (byte)n;
		buf[offset++] = (byte)(n >> 8);
		return 2;
	}
	/**
	 * Deserialize a short from buf
	 * @param buf byte array that deserialize from
	 * @param offset start point of buf
	 * @return short desrtialize from buf
	 */
	public static short deserializeShort(byte[] buf, int offset)
	{
		short n = 0;
		n += buf[offset + 1] & 0xff;
		n <<= 8;
		n += buf[offset] & 0xff;
		return n;
	}
	/**
	 * Serializes int to buf
	 * @param buf byte array that serialize to
	 * @param offset start point of buf
	 * @param n int that serialize from
	 * @return serialized length
	 */
	public static int serialize(byte[] buf, int offset, int n)
	{
		buf[offset++] = (byte)n;
		buf[offset++] = (byte)(n >> 8);
		buf[offset++] = (byte)(n >> 16);
		buf[offset++] = (byte)(n >> 24);
		return 4;
	}
	
	
	/**
	 * Deserialize an int from buf
	 * @param buf byte array that deserialize from
	 * @param offset start point of buf
	 * @return int deserialized from buf
	 */
	public static int deserializeInt(byte[] buf, int offset)
	{
		int n = 0;
		n += buf[offset + 3] & 0xff;
		n <<= 8;
		n += buf[offset + 2] & 0xff;
		n <<= 8;
		n += buf[offset + 1] & 0xff;
		n <<= 8;
		n += buf[offset] & 0xff;
		return n;
	}
	
	
	
	/**
	 * Serializes byte array to buf
	 * @param buf byte array that serialize to
	 * @param offset start point of buf
	 * @param array byte array that serialize from
	 * @param arrayOffset start point of array
	 * @param arrayLen serialized length
	 * @return serialized length
	 */
	public static int serialize(byte[] buf, int offset, byte[] array, int arrayOffset, int arrayLen)
	{
		System.arraycopy(array, arrayOffset, buf, offset, arrayLen);
		return arrayLen;
	}
	
	/**
	 * Deserializes byte array from buf
	 * @param buf byte array that deserialize from
	 * @param offset start point of buf
	 * @param array byte array that deserialize to
	 * @param arrayOffset start point of array
	 * @param arrayLen deserialized length
	 * @return deserialized length
	 */
	public static int deserialize(byte[] buf, int offset, byte[] array, int arrayOffset, int arrayLen)
	{
		System.arraycopy(buf, offset, array, arrayOffset, arrayLen);
		return arrayLen;
	}
	
	/**
	 * Serializes byte array to buf
	 * @param buf byte array that serialize to
	 * @param offset start point of buf
	 * @param array byte array that serialize from
	 * @return array's length
	 */
	public static int serialize(byte[] buf, int offset, byte[] array)
	{
		return serialize(buf, offset, array, 0, array.length);
	}
	
	/**
	 * Deserializes byte array from buf
	 * @param buf byte array that deserialize from
	 * @param offset start point of buf
	 * @param array byte array that deserialize to
	 * @return array's length
	 */
	public static int deserialize(byte[] buf, int offset, byte[] array)
	{
		return deserialize(buf, offset, array, 0, array.length);
	}
	
	/**
	 * Serializes String to buf
	 * @param buf byte array that serialize to
	 * @param offset start point of buf
	 * @param str String that serialize from
	 * @return serialized length
	 */
	public static int serialize(byte[] buf, int offset, String str)
	{
		int len = 0;
		try {
			len += serialize( buf, offset + len, str.getBytes("gb2312").length );
			len += serialize( buf, offset + len, str.getBytes("gb2312") );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return len;
	}
	
	/**
	 * Deserializes String from buf
	 * @param buf byte array that deserialize from
	 * @param offset start point of buf
	 * @return String deserialized from buf
	 */
	public static String deserializeString(byte[] buf, int offset)
	{
		int len = 0;
		int strLen = deserializeInt(buf, offset + len);
		len += 4;
		byte[] tmpBuf = new byte[strLen];
		len += deserialize(buf, offset + len, tmpBuf);
		try {
			String ret = new String(tmpBuf, "GB2312");
			return ret;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Serializes byte array to buf with the size in the front
	 * @param buf byte array that serialize to
	 * @param offset start point of buf
	 * @param byteArray byte array that serialize from
	 * @return serialized length
	 */
	public static int serializeByteArray(byte[] buf, int offset, byte[] byteArray)
	{
		int len = 0;
		if(null != byteArray)
		{
			len += serialize(buf, offset + len, byteArray.length);
			len += serialize(buf, offset + len, byteArray);
		}
		else
		{
			len += serialize(buf, offset + len, 0);
		}
		return len;
	}
	
	/**
	 * Deserializes byte array from buf
	 * @param buf byte array that deserialize from
	 * @param offset start point of buf
	 * @return byte[] deserialized from buf
	 */
	public static byte[] deserializeByteArray(byte[] buf, int offset)
	{
		byte[] byteArray = null;
		int len = 0;
		int byteArrayLen = deserializeInt(buf, offset + len);
		len += 4;
		if(0 != byteArrayLen)
		{
			byteArray = new byte[byteArrayLen];
			len += deserialize(buf, offset + len, byteArray);
		}
		return byteArray;
	}
	
	/**
	 * Gets the length of boolean
	 * @param b 
	 * @return 1
	 */
	public static int length(boolean b)
	{
		return 1;
	}
	
	/**
	 * Gets the length of serialized str
	 * @param str String that serialize from 
	 * @return length of serialized str
	 */
	public static int length(String str)
	{
		int len = 0;
		len += 4;
		try {
			len += str.getBytes("GB2312").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return len;
	}
	
	/**
	 * Gets the length of serialized byteArray
	 * @param byteArray byte array that serialize from 
	 * @return length of serialized byteArray
	 */
	public static int length(byte[] byteArray)
	{
		int len = 0;
		len += 4;
		if(null != byteArray)
		{
			len += byteArray.length;
		}
		return len;
	}
}
