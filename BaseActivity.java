package com.yisu.afterten;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager.getInstance().addActivity(this);
		setContentView();
		getIntentData();
		initView();
		initLinstener();
		initData();
	}

	/**
	 * 初始化布局
	 */
	public abstract void setContentView();
	/**
	 * 获取Intent数据
	 */
	public abstract void getIntentData();
	/**
	 * 初始化控件
	 */
	public abstract void initView();
	/**
	 * 初始化监听
	 */
	public abstract void initLinstener();
	/**
	 * 初始化数据
	 */
	public abstract void initData();
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					onInputHidden();
					v.clearFocus();
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		try {
			if (getWindow().superDispatchTouchEvent(ev)) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return onTouchEvent(ev);
	}

	public void onInputHidden(){
		Log.d("TAG", "input_hidden");
	}
	
	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityManager.getInstance().removeActivity(this);
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		ActivityManager.getInstance().exitActivityAnimation(this);
	}
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		ActivityManager.getInstance().startActivity(this);
	}
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		ActivityManager.getInstance().startActivity(this);
	}
	public void exitActivityAnimation(){
		ActivityManager.getInstance().exitActivityAnimation(this);
	}
}
