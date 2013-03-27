package com.cgcl.cloudesk.manage.util;

import java.util.Vector;

import com.cgcl.cloudesk.manage.com.AppInfo;
public class ArrayUtil {
	/**
	 * delete the elements which are in both dest[destBegin,destEnd) and src[srcBegin, srcEnd) form src 
	 * @param src
	 * @param dest
	 * @param destBegin
	 * @param destEnd
	 */
	public static void ArraySub(AppInfo[] src, int srcBegin, int srcEnd, AppInfo[] dest, int destBegin, int destEnd)
	{
		if( (null == dest) || (null == src) )
			return;
		if( destBegin < 0 || destBegin >= destEnd)
			return;
		if(	srcBegin < 0 || srcBegin >= srcEnd )
			return;
		for(int i = destBegin ; i < destEnd ; i++)
		{
			for(int j = 0 ; j < srcEnd; j++)
			{
				String destAppID = dest[i].getID();
				String srcAppID  = src[j].getID();
				if( destAppID.equals(srcAppID) )
				{
					if( j < (srcEnd - 1) )
						System.arraycopy(src, j+1, src, j, srcEnd-j-1);
					break;
				}
			}
		}
	}
	
	/**
	 * delete element whose appID equals the given appID param from src[srcBegin, srcEnd)
	 * @param src
	 * @param appID
	 */
	public static void ArrayDelete(AppInfo[] src, int srcBegin, int srcEnd, String appID)
	{
		if( null == src || null == appID)
			return;
		if( srcBegin >= srcEnd )
			return;
		for(int i = srcBegin ; i < srcEnd ; i++)
		{
			if( appID.equals(src[i].getID()) )
			{
				if( i < (srcEnd-1) )
				{
					System.arraycopy(src, i+1, src, i, srcEnd-i-1);
					break;
				}
			}
		}
	}
	
	
	public static AppInfo[] VectorToArray(Vector<AppInfo> vec)
	{
		if( null == vec || 0 == vec.size())
			return new AppInfo[vec.size()];
		AppInfo[] StringArray = new AppInfo[vec.size()];
		for(int i = 0 ; i < vec.size() ; i++)
		{
			StringArray[i] = (AppInfo)vec.elementAt(i);
		}
		return StringArray;
	}
	
	public static Vector<AppInfo> ArrayToVector(AppInfo[] AppInfoArray)
	{
		if( null== AppInfoArray || 0 == AppInfoArray.length )
			return new Vector<AppInfo>();
		Vector<AppInfo> vector = new Vector<AppInfo>();
		for(int i = 0 ; i < AppInfoArray.length ; i++)
		{
			vector.addElement(AppInfoArray[i]);
		}
		
		return vector;
	}
}
