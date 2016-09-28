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
		//��ʼ���ؼ�
		initViews();
		
		//��activity����ʱ�ͷ������ȡ��֤��ͼƬ
		presenter.LaodImage();
		
		//�ؼ��ļ�����
		setListeners();
	}
	
	/**
	 * �ؼ��ļ�����
	 */
	private void setListeners() {
		/**
		 * ���ͼƬ������֤��
		 */
		ivCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.LaodImage();
			}
		});
		
		/**
		 * ���ע�ᰴťʱ�������˷�����ע��
		 */
		btnCommit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//����Ҫ��ȡ�ؼ����ݣ�����װ��User���У�ʵ��Serializable���л��ӿ�
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
	 * ע��ɹ�ʱ
	 */
	@Override
	public void showRegistSuccessed() {
		Toast.makeText(this, "ע��ɹ�", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * ע��ʧ��ʱ
	 */
	@Override
	public void showRegistFailed() {
		Toast.makeText(this, "ע��ʧ��", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * ��ʾ��֤��ͼƬ
	 */
	@Override
	public void showImage(Bitmap bm) {
		ivCode.setImageBitmap(bm);
		
	}

	/**
	 * ��ʼ���ؼ�
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
