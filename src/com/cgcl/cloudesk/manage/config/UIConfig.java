package com.cgcl.cloudesk.manage.config;

public class UIConfig {
	/**
	 * Number of forms
	 */
	public static final int		totalFormsNum = 6;
	
	/**
	 * Type of invalid forms
	 */
	public static final int		invalidFormType = -1;
	
	/**
	 * Type of register form
	 */
	public static final int		registerFormType = 0;
	
	/**
	 * Type of login form
	 */
	public static final int		loginFormType = 1;
	
	/**
	 * Type of main form
	 */
	public static final int		mainFormType = 2;
	
	/**
	 * Type of customize form
	 */
	public static final int		customizeFormType = 3;
	
	/**
	 * Type of select historical scene form
	 */
	public static final int		selectHistoricalSceneFormType = 4;
	
	/**
	 * Type of gradeSystem form
	 */
	public static final int		gradeSystemFormType = 5;
	
	/**
	 * Index of invalid forms
	 */
	public static final int		invalidFormIndex = -1;
	
	/**
	 * Index of register form
	 */
	public static final int		registerFormIndex = 0;
	
	/**
	 * Index of login form
	 */
	public static final int		loginFormIndex = 1;
	
	/**
	 * Index of main form
	 */
	public static final int		mainFormIndex = 2;
	
	/**
	 * Index of customize form
	 */
	public static final int		customizeFormIndex = 3;
	
	/**
	 * Index of select historical scene form
	 */
	public static final int		selectHistoricalSceneFormIndex = 4;
	
	/**
	 * Index of gradeSystem form
	 */
	public static final int		gradeSystemFormIndex = 5;
	
	/**
	 * Title of register form
	 */
	public static final String	registerFormTitle = "ע��";
	
	/**
	 * Title of login form
	 */
	public static final String	loginFormTitle = "��¼";
	
	/**
	 * Title of main form
	 */
	public static final String	mainFormTitle = "�����";
	
	/**
	 * Title of customize form
	 */
	public static final String	customizeFormTitle = "����/ж��Ӧ�ó���";
	
	/**
	 * Title of select historical scene form
	 */
	public static final String	selectHistoricalSceneFormTitle = "�ָ���ʷ����";
	
	/**
	 * Invalid shift of one form to another 
	 */
	public static final int[][]	invalidFormShifts = { 
		{registerFormIndex, loginFormIndex}, {registerFormIndex, customizeFormIndex},
		{loginFormIndex, registerFormIndex}, {loginFormIndex, selectHistoricalSceneFormIndex},
		{mainFormIndex, customizeFormIndex}, {mainFormIndex, selectHistoricalSceneFormIndex},
		{customizeFormIndex, registerFormIndex}, {customizeFormIndex, mainFormIndex},
		{selectHistoricalSceneFormIndex, mainFormIndex},{mainFormIndex, loginFormIndex}
	};
}
