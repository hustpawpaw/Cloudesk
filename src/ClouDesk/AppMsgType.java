package ClouDesk;

public class AppMsgType {
	public static final int msgTypeListApp = 0;
	
	public static final int msgTypeRequestApp = 1;
	
	public static final int msgTypeMount = 2;
	
	public static final int msgTypeUnmount = 3;
	
	public static final int msgTypeExist = 4;
	
	public static final int msgTypeNoExist = 5;
	
	public static final int msgTypeFailed = 6;
	
	public static final int msgTypeSuccess = 7;
	
	//client to server
	public static final int msgTypeSetSize = 20;
	
	public static final int msgTypeCloseWindow = 21;
	
	public static final int msgTypeSetFocus = 22;
	
	public static final int msgTypePointerEvent = 23;
	
	
	
	
	
	
	
	
	// server to client
	 public static final int srvSetClientSize = 30;
	
	 public static final int srvCloseClientWindow = 31;
	 
	 public static final int srvShowPPT = 32;
	 
	 public static final int srvExitPPT = 33;
}
