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
	 * ����model���ȡ��֤�룬�ٵ���view��ʵ����ʾ��֤��ͼƬ
	 */
	@Override
	public void LaodImage() {
		
		//�����󣬻�ȡ��֤��ͼƬ
		model.loadImage(new IBitmapCallback() {
			@Override
			public void onBitmapLoaded(Bitmap bm) {
				
				//�ӿڻص���������bitmap
				view.showImage(bm);
				
			}
		});
		
	}


	/**
	 * ע���˺ŵķ���
	 */
	@Override
	public void regist(User user, String code) {
		model.regist(user, code,new IRegistStateCallback() {
			
			@Override
			public void RegistSuccessed() {
				//ע��ɹ�ʱ�����õķ���
				view.showRegistSuccessed();
				
			}
			
			@Override
			public void RegistFailed() {
				//ע��ɹ�ʱ�����õķ���
				view.showRegistFailed();
				
			}
		});
	}

}
