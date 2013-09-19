package com.tanggod.serviceinquiry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.tanggod.servicelist.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/*
 * 列出手机中所有开启的 Service
 *  list all running service of android mobile
 */
public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {
	TextView tvSummary;
	ListView mListView;
	List<ServiceInfo> serviceInfoList = null;
	String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvSummary = (TextView) findViewById(R.id.textView1);
		mListView = (ListView) findViewById(R.id.listView1);

		mListView.setOnItemClickListener(this);

		ListRunningServiceInfo();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		default:
			break;
		}

	}

	private void ListRunningServiceInfo() {
		// TODO Auto-generated method stub
		Log.i(TAG, "ListRUnningServiceInfo");

		ActivityManager mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager.getRunningServices(100);

		serviceInfoList = new ArrayList<ServiceInfo>();

		tvSummary.setText(String.format("Running Services Count: %d", mServiceList.size()));
		
		for (ActivityManager.RunningServiceInfo runningServiceInfoItem : mServiceList) {
			// 获得Service所在的进程的信息
			int pid = runningServiceInfoItem.pid; // service所在的进程ID号
			int uid = runningServiceInfoItem.uid; // 用户ID 类似于Linux的权限不同，ID也就不同 比如 root等

			// process name
			String processName = runningServiceInfoItem.process;

			// active time
			long activeSince = runningServiceInfoItem.activeSince;

			// 如果该Service是通过Bind方法方式连接，则clientCount代表了service连接客户端的数目
			int clientCount = runningServiceInfoItem.clientCount;
			int crashCount	= runningServiceInfoItem.crashCount;

			// 获得该Service的组件信息 可能是pkgname/servicename
			ComponentName serviceCMP = runningServiceInfoItem.service;
			String shortClassName = serviceCMP.getShortClassName();
			String pkgName = serviceCMP.getPackageName();

			// 这儿我们通过service的组件信息，利用PackageManager获取该service所在应用程序的包名 ，图标等
			PackageManager packageManager = this.getPackageManager(); // 获取PackagerManager对象;
			try {
				// 获取该pkgName的信息
				ApplicationInfo appInfo = packageManager.getApplicationInfo(pkgName, 0);

				ServiceInfo serviceInfo = new ServiceInfo();
				serviceInfo.setAppIcon(appInfo.loadIcon(packageManager));
				serviceInfo.setAppLabel(appInfo.loadLabel(packageManager) + "");
				serviceInfo.setShortClassName(shortClassName);
				serviceInfo.setPackageName(pkgName);
				serviceInfo.setCrashCount(crashCount);
				// 设置该service的组件信息
				Intent intent = new Intent();
				intent.setComponent(serviceCMP);
				serviceInfo.setIntent(intent);

				serviceInfo.setPid(pid);
				serviceInfo.setProcessName(processName);
				serviceInfo.setActiveSince(activeSince);

				// 添加至集合中
				serviceInfoList.add(serviceInfo);

			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Comparator<ServiceInfo>	comparableAppLabel;
		comparableAppLabel = new ComparableAppLabel();
		Collections.sort(serviceInfoList, comparableAppLabel);
		
		ServiceAdapter serviceAdapter = new ServiceAdapter(MainActivity.this, serviceInfoList);
		mListView.setAdapter(serviceAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int postion, long arg3) {
		// TODO Auto-generated method stub
		final Intent serviceIntent = serviceInfoList.get(postion).getIntent();

		Log.i(TAG, "onItemClick");
//		new AlertDialog.Builder(MainActivity.this).setTitle("是否停止服务")
		new AlertDialog.Builder(MainActivity.this).setTitle(R.string.stop_service)
				.setMessage(R.string.stop_information)
				.setPositiveButton(R.string.btn_stop, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// 停止该Service
						//由于权限不够的问题，为了避免应用程序出现异常，捕获该SecurityException ，并弹出对话框
						try {
							stopService(serviceIntent);
							//startActivity(serviceIntent);
						} catch (SecurityException sEx) {
							//发生异常 说明权限不够
							Log.e(TAG, "deny the permission");
							new AlertDialog.Builder(MainActivity.this).setTitle(R.string.stop_fail)
									.setMessage(R.string.fail_reason).create().show();
						} catch (Exception e) {
							e.printStackTrace();
						}
						// 刷新界面
						// 获得正在运行的Service信息
						ListRunningServiceInfo();
						// 对集合排序
						//Collections.sort(serviceInfoList, new comparatorServiceLable());
					}

				}).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss(); // 取消对话框
					}
				}).create().show();
	}
	public class ComparableAppLabel implements Comparator<ServiceInfo> {

		@Override
		public int compare(ServiceInfo lhs, ServiceInfo rhs) {
			// TODO Auto-generated method stub
			return lhs.getAppLabel().compareTo(rhs.getAppLabel());
		}

	}

}
