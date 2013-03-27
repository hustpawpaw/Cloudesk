package com.cgcl.cloudesk.manage.log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class LogService {
	private static LogService logService = null;
	private FileWriter writer;
	private static String log;
	private java.io.File file;
	public static LogService getInstance()
	{
		if (logService == null)
			logService = new LogService();
		return logService;
	}
	private LogService()
	{
		SimpleDateFormat   formatter   =   new   SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");     
		Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
		String   str   =   formatter.format(curDate);     
		str += ".txt";
		log = "/sdcard/Logs/"+str;	
		File fileDir = new File("/sdcard/Logs");
		if (!fileDir.exists())
			fileDir.mkdirs();
		file = new java.io.File(log);
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void WriteLog(String content)
	{
		try {
			writer = new FileWriter(log,true);
			writer.write(System.currentTimeMillis() + ":");
			writer.write(content+"\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
