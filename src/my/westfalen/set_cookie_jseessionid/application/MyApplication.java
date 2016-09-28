package my.westfalen.set_cookie_jseessionid.application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;

public class MyApplication extends Application{

	//创建application时，不要忘记注册
	private static RequestQueue queue;

	@Override
	public void onCreate() {
		super.onCreate();
		queue = Volley.newRequestQueue(this);
	}
	
	public static RequestQueue getQueue() {
		return queue;
	}
	public static void setQueue(RequestQueue queue) {
		MyApplication.queue = queue;
	}
}
