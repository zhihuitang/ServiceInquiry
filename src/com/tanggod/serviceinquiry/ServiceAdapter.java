package com.tanggod.serviceinquiry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.tanggod.servicelist.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ServiceAdapter extends BaseAdapter {


	Context 			mContext;
	LayoutInflater		infater;
	List<ServiceInfo>	mServiceInfoList;
	
	public ServiceAdapter() {
		// TODO Auto-generated constructor stub
	}

	public ServiceAdapter(Context context, List<ServiceInfo> serviceInfoList) {
		// TODO Auto-generated constructor stub
		mContext			= context;
		mServiceInfoList	= serviceInfoList;
		
		infater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mServiceInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mServiceInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unused")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View		view = null;
		ViewHolder	viewHolder	= null;
		
		if( (view == null) || (convertView.getTag() == null) ){
			view 	= infater.inflate(R.layout.item, null);
			viewHolder	= new ViewHolder(view);
			view.setTag(viewHolder);
		}else{
			view	= convertView;
			viewHolder	= (ViewHolder)convertView.getTag();
		}
		
		ServiceInfo runServiceInfo = (ServiceInfo) getItem(position);
		viewHolder.imgViewAppIcon.setImageDrawable(runServiceInfo.getAppIcon());
		viewHolder.tvAppLabel.setText(runServiceInfo.getAppLabel());
		viewHolder.tvServiceName.setText(String.format("%s", runServiceInfo.getServiceName()));
		viewHolder.tvCrashCount.setText(String.format("Crash Count:%d", runServiceInfo.getCrashCount()));
//		viewHolder.tvShortClassName.setText(String.format("Short Class Name:%s", runServiceInfo.getShortClassName()));
		
//		Date date	= new Date(runServiceInfo.getActiveSince());
//		SimpleDateFormat	sdf	 = new SimpleDateFormat("EEE dd-MMM-yyyy HH:mm");
//		String s	= sdf.format(date);
//		String s = getStandardDate(runServiceInfo.getActiveSince());

		viewHolder.tvActiveSince.setText(String.format("Active Since: %d", runServiceInfo.getActiveSince())) ;

		return view;
	}

	public class ViewHolder {
		private	ImageView		imgViewAppIcon;		// icon		
		private TextView 		tvAppLabel;    //应用程序标签
		private TextView 		tvServiceName  ;  //该Service的类名
		private	TextView		tvActiveSince;
		private TextView		tvCrashCount;
		private TextView		tvShortClassName;

		public ViewHolder(View view) {
			// TODO Auto-generated constructor stub
			this.imgViewAppIcon	= (ImageView)view.findViewById(R.id.img);
			this.tvAppLabel		= (TextView)view.findViewById(R.id.app_name);
			this.tvServiceName 	= (TextView)view.findViewById(R.id.service_name);
			this.tvActiveSince 	= (TextView)view.findViewById(R.id.active_time);
			this.tvCrashCount	= (TextView)view.findViewById(R.id.crash_count);
//			this.tvShortClassName	= (TextView)view.findViewById(R.id.short_class_name);
		}

	}
	/** 
	 * 将时间戳转为代表"距现在多久之前"的字符串 
	 * @param timeStr   时间戳 
	 * @return 
	 */  
	public String getStandardDate(long time) {  
	  
	    StringBuffer sb = new StringBuffer();  
	  
	  //  long time = System.currentTimeMillis() - (t*1000);
	    
	    long mill = (long) Math.ceil(time /1000);//秒前  
	  
	    long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前  
	  
	    long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时  
	  
	    long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前  
	  
	    if (day - 1 > 0) {  
	        sb.append(day + "天");  
	    } else if (hour - 1 > 0) {  
	        if (hour >= 24) {  
	            sb.append("1天");  
	        } else {  
	            sb.append(hour + "小时");  
	        }  
	    } else if (minute - 1 > 0) {  
	        if (minute == 60) {  
	            sb.append("1小时");  
	        } else {  
	            sb.append(minute + "分钟");  
	        }  
	    } else if (mill - 1 > 0) {  
	        if (mill == 60) {  
	            sb.append("1分钟");  
	        } else {  
	            sb.append(mill + "秒");  
	        }  
	    } else {  
	        sb.append("刚刚");  
	    }  
	    if (!sb.toString().equals("刚刚")) {  
	        sb.append("前");  
	    }  
	    return sb.toString();  
	}  
}
