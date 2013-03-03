package com.municipalengineering.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.municipalengineering.entity.UserBean;
import com.tools.NetUitl;
import com.tools.ObjectTool;
import com.tools.StringTool;
import com.tools.XmlPaser;

public class Login extends Activity {
	private static UserBean mUserBean;
	private TextView mpassWorldTextView;
	private TextView mUserNameTextView;
	private CheckBox isRemberPass;
	private final static String Tag = "Login";
	private final static String user_key = "user";
	private final int VALIDATE_USER_CODE = 1;
	private boolean isLoging = false;
	private AsyncHttpClient client = new AsyncHttpClient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		Button btnLogin = (Button) findViewById(R.id.login_submit);
		mUserNameTextView = (TextView) findViewById(R.id.login_name);
		mpassWorldTextView = (TextView) findViewById(R.id.login_password);
		isRemberPass = (CheckBox) findViewById(R.id.login_checkBox1);
		String userString = getSharedPreferences(Tag, 0).getString(user_key,
				null);

		if (userString != null && !userString.equals("")) {
			mUserBean = (UserBean) ObjectTool.StringToObject(userString);
			Log.i(Tag, mUserBean.toString());
			isRemberPass.setChecked(true);
			mUserNameTextView.setText(mUserBean.getUserName());
			mpassWorldTextView.setText(mUserBean.getPassward());
		} else {
			mUserBean = new UserBean();
		}

		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isLoging) {
					return;
				}
				mUserBean.setPassward(mpassWorldTextView.getText().toString());
				mUserBean.setUserName(mUserNameTextView.getText().toString());
				if (mUserBean.getPassward().equals("")
						|| mUserBean.getUserName().equals("")) {
					Toast.makeText(Login.this,
							getString(R.string.user_input_erro),
							Toast.LENGTH_LONG).show();
					return;
				}
				if (NetUitl.isConnect(getBaseContext())) {
					client.get("", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							validateUser(content);
						}

						@Override
						public void onFailure(Throwable error) {
							netEorror();
						};
					});

				} else {
					Toast.makeText(Login.this,
							getString(R.string.net_connect_lost),
							Toast.LENGTH_LONG).show();
					return;
				}
				disableLogin();
			}

		});
	}

	protected void disableLogin() {
		isLoging = false;
	}

	private void validateUser(String content) {
		String userId = (String) XmlPaser.paserSingleTag(content, "string");
		if (StringTool.isNumber(userId)) {
			mUserBean.setUserId(userId);
			saveUserBean(isRemberPass.isChecked());
			startActivity(new Intent(this, MainActivity.class));

		} else {
			Toast.makeText(this, content, Toast.LENGTH_LONG).show();
			isLoging = false;
		}
	}

	private void saveUserBean(final boolean isSave) {
		new Thread() {
			public void run() {
				SharedPreferences.Editor editor = getSharedPreferences(Tag, 0)
						.edit();
				editor.remove(user_key);
				if (isSave) {
					editor.putString(user_key,
							ObjectTool.ObjectToString(mUserBean));
				}
				editor.commit();
			};

		}.start();

	}

	private void netEorror() {
		Toast.makeText(this, getString(R.string.net_connect_erro), 5 * 1000)
				.show();
		isLoging = false;
	}

	public static final UserBean getUserBean() {
		if (mUserBean == null) {
			// throw new RuntimeException("!!mUserBean is null !!");

		}
		return mUserBean;
	}

}
