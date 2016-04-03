package com.yisu.afterten;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityManager {
	
	private List<Activity> listActivity;
	private static ActivityManager manager;
	private ActivityManager() {
		listActivity = new ArrayList<Activity>();
	}
	public static ActivityManager getInstance(){
		if (manager == null ) {
			manager = new ActivityManager();
		}
		return manager;
	}

	public void addActivity(Activity activity){
		if (!listActivity.contains(activity)) {
			listActivity.add(activity);
		}
	}
	
	public void removeActivity(Activity activity){
		if (listActivity.contains(activity)) {
			listActivity.remove(activity);
		}
	}
	
	public void startActivity(Activity activity){
		activity.overridePendingTransition(R.anim.activity_in_from_right, R.anim.activity_out_to_left);
	}
	public void exitActivityAnimation(Activity activity){
		removeActivity(activity);
		activity.finish();
		activity.overridePendingTransition(R.anim.activity_in_from_left, R.anim.activity_out_to_right);
	}
	public List<Activity> getListActivity() {
		return listActivity;
	}
	
}
