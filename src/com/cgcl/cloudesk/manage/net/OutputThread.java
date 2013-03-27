package com.cgcl.cloudesk.manage.net;

import java.io.IOException;
import java.io.OutputStream;

import com.cgcl.cloudesk.manage.com.Queue;
import com.cgcl.cloudesk.manage.config.NetConfig;
import com.cgcl.cloudesk.manage.log.LogService;
import com.cgcl.cloudesk.manage.packet.PacketBase;
import com.cgcl.cloudesk.screens.act.BaseActivity;
import com.cgcl.cloudesk.screens.act.MainActivity;

public class OutputThread extends Thread {
	private boolean				bTerminate = false;
	private OutputStream		os = null;
	private Queue				outputPacketQueue = new Queue();
	private byte[]				outputBuf = new byte[NetConfig.outputBufLen];
	
	public OutputThread(OutputStream os)
	{
		this.os = os;
	}

	public synchronized void output(PacketBase packet)
	{
		outputPacketQueue.push(packet);
		notify();
	}
	
	public void run()
	{
		// OutputThread can only run once
		if(bTerminate)
		{
			return;
		}
		
		boolean bStop = false;
		
		while(!bStop)
		{
			synchronized(this)
			{
				// wait for output packet
				while( outputPacketQueue.isEmpty() )
				{
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
				}
			}
			
			while( !outputPacketQueue.isEmpty() )
			{
				PacketBase packet = (PacketBase)outputPacketQueue.pop();
				
				// if packet is null, then quit
				if(null == packet)
				{
					bStop = true;
					break;
				}
				
				// output the packet
				int len = packet.serialize(outputBuf, 0);
				try {
					os.write(outputBuf, 0, len);
					LogService.getInstance().WriteLog("Output : packet type = " + Integer.toString((int)packet.getType()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//0927
					LogService.getInstance().WriteLog("OutputError : packet type = " + Integer.toString((int)packet.getType())+ " " + e.getMessage());
					//LogService.getInstance().WriteLog("ReadError : " + e.getMessage()+ " inputBufPos = " + inputBufPos);
				//	if (MainActivity.isInitSuceessed == false)
						((BaseActivity) BaseActivity.CurrentActivity).showError();
				}
				
			}
		}
		
		// clear and prepare for next start
		outputPacketQueue.clear();
		bTerminate = true;
	}
	
	public void terminate()
	{
		output(null);
		try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogService.getInstance().WriteLog("OutputTerminateError = " +" " + e.getMessage());
		}
	}
}
