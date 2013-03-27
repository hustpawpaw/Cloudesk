package com.cgcl.cloudesk.manage.app;

import com.cgcl.cloudesk.manage.core.Controller;
import com.cgcl.cloudesk.manage.net.Transmitter;
import com.cgcl.cloudesk.manage.vnc.Vnc;
import com.cgcl.cloudesk.screens.act.MoblieClientUI;

import android.app.Application;

public class AppData extends Application {
	private Controller			controller = null;
	private Transmitter			transmitter = null;
	private Vnc					vnc = null;
	private MoblieClientUI		ui = null;
	
	private boolean				isInstall=false;  //判定主界面是否是第一次载入
	
	private Controller		vmController = null;
	private Transmitter			vmTransmitter = null;
	public Controller getController() {
		return controller;
	}
	public void setController(Controller controller) {
		this.controller = controller;
	}
	public Transmitter getTransmitter() {
		return transmitter;
	}
	public void setTransmitter(Transmitter transmitter) {
		this.transmitter = transmitter;
	}
	public Vnc getVnc() {
		return vnc;
	}
	public void setVnc(Vnc vnc) {
		this.vnc = vnc;
	}
	public MoblieClientUI getUi() {
		return ui;
	}
	public void setUi(MoblieClientUI ui) {
		this.ui = ui;
	}
	public Controller getVmController() {
		return vmController;
	}
	public void setVmController(Controller vmController) {
		this.vmController = vmController;
	}
	public Transmitter getVmTransmitter() {
		return vmTransmitter;
	}
	public void setVmTransmitter(Transmitter vmTransmitter) {
		this.vmTransmitter = vmTransmitter;
	}
	public boolean isInstall() {
		return isInstall;
	}
	public void setInstall(boolean isInstall) {
		this.isInstall = isInstall;
	}
	
	
}
