package my.westfalen.set_cookie_jseessionid.model;

import my.westfalen.set_cookie_jseessionid.callback.IBitmapCallback;
import my.westfalen.set_cookie_jseessionid.callback.IRegistStateCallback;
import my.westfalen.set_cookie_jseessionid.entity.User;
import android.graphics.Bitmap;

public interface ILoadImageModel {
	public Bitmap loadImage(IBitmapCallback callback);

	public void regist(User user,String code, IRegistStateCallback callback);
}
