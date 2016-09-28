package my.westfalen.set_cookie_jseessionid.activity;

import java.io.Serializable;




import my.westfalen.set_cookie_jseessionid.R;
import my.westfalen.set_cookie_jseessionid.entity.User;
import my.westfalen.set_cookie_jseessionid.presenter.Impl.LoadImagePresenterImpl;
import my.westfalen.set_cookie_jseessionid.view.IRegistView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegistActivity extends Activity implements IRegistView{

	private EditText etLoginame;
	private EditText etPassword;
	private EditText etRealname;
	private EditText etCode;
	private Button btnCommit;
	private ImageView ivCode;
	private LoadImagePresenterImpl presenter;
	
	public RegistActivity(){
		presenter = new LoadImagePresenterImpl(this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		//初始化控件
		initViews();
		
		//在activity创建时就发请求获取验证码图片
		presenter.LaodImage();
		
		//控件的监听器
		setListeners();
	}
	
	/**
	 * 控件的监听器
	 */
	private void setListeners() {
		/**
		 * 点击图片更换验证码
		 */
		ivCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.LaodImage();
			}
		});
		
		/**
		 * 点击注册按钮时，向服务端发请求注册
		 */
		btnCommit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//首先要获取控件内容，并封装在User类中，实现Serializable序列化接口
				String loginName = etLoginame.getText().toString();
				String password = etPassword.getText().toString();
				String realName = etRealname.getText().toString();
				
				User user = new User(loginName,password,realName);
				String code = etCode.getText().toString();
				
				presenter.regist(user,code);
				
			}
			
		});
		
	}
	
	/**
	 * 注册成功时
	 */
	@Override
	public void showRegistSuccessed() {
		Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 注册失败时
	 */
	@Override
	public void showRegistFailed() {
		Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 显示验证码图片
	 */
	@Override
	public void showImage(Bitmap bm) {
		ivCode.setImageBitmap(bm);
		
	}

	/**
	 * 初始化控件
	 */
	private void initViews() {
		etLoginame = (EditText) findViewById(R.id.etLoginname);
		etPassword = (EditText) findViewById(R.id.etPwd);
		etRealname = (EditText) findViewById(R.id.etRealname);
		etCode = (EditText) findViewById(R.id.etCode);
		btnCommit = (Button) findViewById(R.id.btnLogin);
		ivCode = (ImageView) findViewById(R.id.ivCode);
	}
	
}
