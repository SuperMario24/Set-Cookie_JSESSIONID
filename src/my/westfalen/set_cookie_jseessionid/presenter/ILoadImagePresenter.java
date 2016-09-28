package my.westfalen.set_cookie_jseessionid.presenter;

import my.westfalen.set_cookie_jseessionid.callback.IRegistStateCallback;
import my.westfalen.set_cookie_jseessionid.entity.User;

public interface ILoadImagePresenter {
	public void LaodImage();
	public void regist(User user,String code);
}
