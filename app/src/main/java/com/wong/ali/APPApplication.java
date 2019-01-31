package com.wong.ali;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.wong.ali.constant.Constant;
import com.wong.ali.utils.SharedPreferencesHelper;

public class APPApplication extends Application {

	private int count = 0;
	private SharedPreferencesHelper helper;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
			}

			@Override
			public void onActivityStarted(Activity activity) {
				count ++;
			}

			@Override
			public void onActivityResumed(Activity activity) {
			}

			@Override
			public void onActivityPaused(Activity activity) {
			}

			@Override
			public void onActivityStopped(Activity activity) {
				if(count > 0) {
					count--;
				}
			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

			}

			@Override
			public void onActivityDestroyed(Activity activity) {
			}
		});
		//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
		
		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
			
			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub
				//x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
				Log.d("app", " onViewInitFinished is " + arg0);
			}
			
			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub
			}
		};
		//x5内核初始化接口
		QbSdk.initX5Environment(getApplicationContext(),  cb);


		initConfig();
	}

	/**
	 * 判断app是否在后台
	 * @return
	 */
	public boolean isBackground(){
		if(count <= 0){
			return true;
		} else {
			return false;
		}
	}

	//init all config of the app
	private void initConfig(){

		//init the default engine
		initSearchEngine();
	}

	//init the default engine
	private void initSearchEngine(){
		helper = new SharedPreferencesHelper(
				getApplicationContext(), Constant.SHARED_DB);

		if(!helper.contain(Constant.SEARCH_ENGINE)){
			helper.put(Constant.SEARCH_ENGINE,Constant.BAI_DU);
		}
	}
}
