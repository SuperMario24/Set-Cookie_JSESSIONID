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
	 * ��ȡ��֤��ͼƬ
	 *  ͨ��Volley��ȡbitmap����ȡ��֤��ʱ���ѷ���˴�������JSESSIONID�����Զ�������
	 */
	@Override
	public Bitmap loadImage(final IBitmapCallback callback) {
		//��֤��ͼƬ�Ľӿ�
		String url = "http://45.78.24.178:8080/dang/user/getImage.action";

		//��application�л�ȡRequestQueue,����ÿ�ζ�Ҫ��������
		RequestQueue queue = MyApplication.getQueue();

		//����ͼƬ����
		ImageRequest ir = new ImageRequest(url, new Listener<Bitmap>() {
			// ����ɹ�ʱ��ִ�еķ���������˷��ص�response����һ��bitmap����
			@Override
			public void onResponse(Bitmap response) {
				//��ȡ��Bitmapʱ��ʹ�ýӿڻص�
				callback.onBitmapLoaded(response);
			}
		}, 130, 50, Bitmap.Config.ARGB_8888, new ErrorListener() {//130,50ΪĿ��ͼƬ�Ĵ�С, ֮��Ĳ���ΪͼƬ�������������ȡʧ��ʱִ�еķ���

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("info", error.getMessage());
			}
		}){
			/**
			 * �����ÿ������һ����֤��ͼƬʱ��Ҳ�ᷢ��һ��JSESSIONID
			 * ��дparseNetworkResponse��������ȡJSESSIONID
			 */
			@Override
			protected Response<Bitmap> parseNetworkResponse(
					NetworkResponse response) {
				/**
				 * response �Ƿ�������Ӧ�����ݣ���ȡ����Ϣͷ����һ��HashMap
				 * ��ֵ��Ϊ<"Set-Cookie",JSESSIONID>
				 * 
				 */
				Map<String, String> headers = response.headers;
				//��ȡJESSIONID
				String sessionid = headers.get("Set-Cookie");
				if(sessionid != null){

					//��JESSIONID����̳�Stringrequest���Զ�������
					CommonRequest.JSESSIONID = sessionid.split(";")[0];
				}

				return super.parseNetworkResponse(response);
			}

		};

		//��ͼƬ������뵽�������
		queue.add(ir);

		return null;
	}


	/**
	 * ������ע��ķ���
	 */
	@Override
	public void regist(final User user, final String code, final IRegistStateCallback callback) {
		//ע���û��Ľӿ�
		String url = "http://45.78.24.178:8080/dang/user/register.action";
		RequestQueue queue = MyApplication.getQueue();

		CommonRequest cr = new CommonRequest(Request.Method.POST, url, new Listener<String>() {
			//����response ��һ��JSON����
			@Override
			public void onResponse(String response) {
				/**
				 *����Ľ��
				 * { "code":1001 } 
				 * { "code":1002, "error_msg":"ע��ʧ�ܴ�����Ϣ" }
				 */
				try {
					JSONObject obj = new JSONObject(response);
					int resultCode = obj.getInt("code");
					Log.i("info", "resultCode:"+resultCode);
					if(resultCode == 1001){ 
						//ע��ɹ���ִ�гɹ��Ļص�
						callback.RegistSuccessed();
					}else {
						//ע��ʧ�ܣ�ִ��ʧ�ܵĻص�
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
			 * ��д�����getParams()����,��ע������ݷ��������
			 * 
			 * �ӿ��е�Ո�󅢔�
			 * user.email �û����� 
			 * user.nickname �û��ǳ� 
			 * user.password �û����� 
			 * number ��֤�� 
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
