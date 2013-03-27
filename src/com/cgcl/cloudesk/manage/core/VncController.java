package com.cgcl.cloudesk.manage.core;

public interface VncController {
	/**
	 * Shifts to UI
	 */
	void						shiftToUI();	
	/**
	 * notify client the result
	 */
	void 						notifyClient(String result);
}
