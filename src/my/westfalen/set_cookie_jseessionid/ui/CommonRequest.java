package my.westfalen.set_cookie_jseessionid.ui;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.media.JetPlayer;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
/**
 * 自定义类继承StringRequest，注册时使用，用于关联请求和服务端返回来的JSESSIONID
 * @author saber
 *
 */
public class CommonRequest extends StringRequest{

	public static String JSESSIONID = null;
	
	public CommonRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}
	
	public CommonRequest(String url, Listener<String> listener,ErrorListener errorListener) {
		super( url, listener, errorListener);
	}

	/**
	 * 重写getHeader获取消息头方法 ，如果不写，父类也会自己调用
	 */
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {

		Map<String, String> headers = super.getHeaders();
		
		//如果没有消息头，就创建，用于存储JSESSIONID
		if(headers == null|| headers.equals(Collections.emptyMap())) {
			headers = new HashMap<String, String> ();
		}
		/**
		 * 如果有session则传递sessionid
		 */
		if(JSESSIONID != null){
			headers.put("Cookie", JSESSIONID);
		}
		
		return headers;
	}
	
	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {

		Map<String, String> headers = response.headers;
		String sessinid = headers.get("set-Cookie");
		if(sessinid != null){
			JSESSIONID = sessinid.split(";")[0];
		}
		String parsed;
		try {
			parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
	}
	
	
	
	
	
	
	
	
	
}
