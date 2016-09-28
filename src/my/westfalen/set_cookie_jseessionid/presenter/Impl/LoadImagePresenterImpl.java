package my.westfalen.set_cookie_jseessionid.presenter.Impl;

import android.graphics.Bitmap;
import my.westfalen.set_cookie_jseessionid.callback.IBitmapCallback;
import my.westfalen.set_cookie_jseessionid.callback.IRegistStateCallback;
import my.westfalen.set_cookie_jseessionid.entity.User;
import my.westfalen.set_cookie_jseessionid.model.ILoadImageModel;
import my.westfalen.set_cookie_jseessionid.model.Impl.UserModelImpl;
import my.westfalen.set_cookie_jseessionid.presenter.ILoadImagePresenter;
import my.westfalen.set_cookie_jseessionid.view.IRegistView;

public class LoadImagePresenterImpl implements ILoadImagePresenter{

	private ILoadImageModel model;
	private IRegistView view;
	
	
	public LoadImagePresenterImpl(IRegistView view) {
		super();
		this.view = view;
		model = new UserModelImpl();
	}


	/**
	 * 调用model层获取验证码，再调用view层实现显示验证码图片
	 */
	@Override
	public void LaodImage() {
		
		//发请求，获取验证码图片
		model.loadImage(new IBitmapCallback() {
			@Override
			public void onBitmapLoaded(Bitmap bm) {
				
				//接口回调传回来的bitmap
				view.showImage(bm);
				
			}
		});
		
	}


	/**
	 * 注册账号的方法
	 */
	@Override
	public void regist(User user, String code) {
		model.regist(user, code,new IRegistStateCallback() {
			
			@Override
			public void RegistSuccessed() {
				//注册成功时，调用的方法
				view.showRegistSuccessed();
				
			}
			
			@Override
			public void RegistFailed() {
				//注册成功时，调用的方法
				view.showRegistFailed();
				
			}
		});
	}

}
