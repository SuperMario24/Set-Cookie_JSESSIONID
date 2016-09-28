package my.westfalen.set_cookie_jseessionid.model.Impl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import my.westfalen.set_cookie_jseessionid.application.MyApplication;
import my.westfalen.set_cookie_jseessionid.callback.IBitmapCallback;
import my.westfalen.set_cookie_jseessionid.callback.IRegistStateCallback;
import my.westfalen.set_cookie_jseessionid.entity.User;
import my.westfalen.set_cookie_jseessionid.model.ILoadImageModel;
import my.westfalen.set_cookie_jseessionid.ui.CommonRequest;
import android.graphics.Bitmap;
import android.os.IInterface;
import android.util.Log;

public class UserModelImpl implements ILoadImageModel{

	/**
	 * 获取验证码图片
	 *  通过Volley获取bitmap，获取验证码时，把服务端传回来的JSESSIONID存入自定义类中
	 */
	@Override
	public Bitmap loadImage(final IBitmapCallback callback) {
		//验证码图片的接口
		String url = "http://45.78.24.178:8080/dang/user/getImage.action";

		//从application中获取RequestQueue,避免每次都要创建对象
		RequestQueue queue = MyApplication.getQueue();

		//创建图片请求
		ImageRequest ir = new ImageRequest(url, new Listener<Bitmap>() {
			// 请求成功时，执行的方法，服务端返回的response就是一个bitmap对象
			@Override
			public void onResponse(Bitmap response) {
				//获取到Bitmap时，使用接口回调
				callback.onBitmapLoaded(response);
			}
		}, 130, 50, Bitmap.Config.ARGB_8888, new ErrorListener() {//130,50为目标图片的大小, 之后的参数为图片的质量、请求获取失败时执行的方法

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("info", error.getMessage());
			}
		}){
			/**
			 * 服务端每发回来一个验证码图片时，也会发来一个JSESSIONID
			 * 重写parseNetworkResponse方法，获取JSESSIONID
			 */
			@Override
			protected Response<Bitmap> parseNetworkResponse(
					NetworkResponse response) {
				/**
				 * response 是服务器响应的数据，获取其消息头，是一个HashMap
				 * 键值对为<"Set-Cookie",JSESSIONID>
				 * 
				 */
				Map<String, String> headers = response.headers;
				//获取JESSIONID
				String sessionid = headers.get("Set-Cookie");
				if(sessionid != null){

					//把JESSIONID存入继承Stringrequest的自定义类中
					CommonRequest.JSESSIONID = sessionid.split(";")[0];
				}

				return super.parseNetworkResponse(response);
			}

		};

		//将图片请求加入到请求队列
		queue.add(ir);

		return null;
	}


	/**
	 * 发请求，注册的方法
	 */
	@Override
	public void regist(final User user, final String code, final IRegistStateCallback callback) {
		//注册用户的接口
		String url = "http://45.78.24.178:8080/dang/user/register.action";
		RequestQueue queue = MyApplication.getQueue();

		CommonRequest cr = new CommonRequest(Request.Method.POST, url, new Listener<String>() {
			//解析response 是一个JSON对象
			@Override
			public void onResponse(String response) {
				/**
				 *请求的结果
				 * { "code":1001 } 
				 * { "code":1002, "error_msg":"注册失败错误信息" }
				 */
				try {
					JSONObject obj = new JSONObject(response);
					int resultCode = obj.getInt("code");
					Log.i("info", "resultCode:"+resultCode);
					if(resultCode == 1001){ 
						//注册成功，执行成功的回调
						callback.RegistSuccessed();
					}else {
						//注册失败，执行失败的回调
						callback.RegistFailed();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}



			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		}){
			/**
			 * 重写父类的getParams()方法,把注册的内容发给服务端
			 * 
			 * 接口中的請求參數
			 * user.email 用户邮箱 
			 * user.nickname 用户昵称 
			 * user.password 用户密码 
			 * number 验证码 
			 * 
			 */
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("user.email", user.getName());
				map.put("user.nickname", user.getRealname());
				map.put("user.password", user.getPassword());
				map.put("number", code);
				return map;
			}

		};

		queue.add(cr);

	}

}
