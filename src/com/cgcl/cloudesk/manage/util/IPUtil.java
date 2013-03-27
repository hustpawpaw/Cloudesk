package com.cgcl.cloudesk.manage.util;



public class IPUtil {
	public static String intToString(long IP)
	{
		StringBuffer sb = new StringBuffer("");
		sb.append(String.valueOf( ( (IP)>>24)& 0x000000ff) );
		sb.append(".");
		sb.append(String.valueOf((IP & 0x00ffffff)>>16));
		sb.append(".");
		sb.append(String.valueOf((IP & 0x0000ffff)>>8));
		sb.append(".");
		sb.append(String.valueOf((IP & 0x000000ff)));
		return sb.toString();
	}

}
