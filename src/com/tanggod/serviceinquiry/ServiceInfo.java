package com.tanggod.serviceinquiry;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class ServiceInfo {

	private String 		appLabel;    	//应用程序标签
	private Drawable 	appIcon ;  		//应用程序图像
	private String 		shortClassName  ;  //该Service的类名
	private String 		packageName ;    //应用程序所对应的包名
	private Intent 		intent ;  //该Service组件所对应的Intent
	private int 		pid ;  //该应用程序所在的进程号
	private String 		processName ;  // 服务名称
	private	long		activeSince;
	private int			crashCount;
	
	
	public ServiceInfo() {
		// TODO Auto-generated constructor stub
	}

	public void setAppIcon(Drawable loadIcon) {
		// TODO Auto-generated method stub
		appIcon		= loadIcon;
	}

	public void setAppLabel(String string) {
		// TODO Auto-generated method stub
		appLabel 	= string;
	}

	public void setShortClassName(String string) {
		// TODO Auto-generated method stub
		shortClassName		= string;
	}

	public void setPackageName(String string) {
		// TODO Auto-generated method stub
		packageName		= string;
	}

	public void setIntent(Intent i) {
		// TODO Auto-generated method stub
		intent	= i;
	}

	public void setPid(int p) {
		// TODO Auto-generated method stub
		pid	= p;
	}

	public void setProcessName(String string) {
		// TODO Auto-generated method stub
		processName		= string;
	}
	public String getProcessName(String string) {
		// TODO Auto-generated method stub
		return processName;
	}

	public void setActiveSince(long t) {
		// TODO Auto-generated method stub
		activeSince		= t;
	}

	public Drawable getAppIcon() {
		// TODO Auto-generated method stub
		return appIcon;
	}

	public String getAppLabel() {
		// TODO Auto-generated method stub
		return appLabel;
	}

	public String getServiceName() {
		// TODO Auto-generated method stub
		return shortClassName;
	}

	public long getActiveSince() {
		// TODO Auto-generated method stub
		return activeSince;
	}

	public Intent getIntent() {
		// TODO Auto-generated method stub
		return intent;
	}

	public void setCrashCount(int n) {
		// TODO Auto-generated method stub
		crashCount = n;
	}

	public int getCrashCount() {
		// TODO Auto-generated method stub
		return crashCount;
	}

	public String getShortClassName() {
		// TODO Auto-generated method stub
		return shortClassName;
	}

}
