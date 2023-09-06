package test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import com.example.intelrobot.R;

import java.util.ArrayList;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    private DBOpenHelper mDBOpenHelper;
    private TextView mTvLoginactivityRegister;
    private RelativeLayout mRlLoginactivityTop;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    private LinearLayout mLlLoginactivityTwo;
    private Button mBtLoginactivityLogin;
    private int mRetryCount = 0;
    private static final int MAX_RETRY_COUNT = 3;
    private static final int WAIT_TIME = 5000; // 5 seconds

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);

        // Check if "remember me" is checked and auto-fill username and password
        SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        String password = sharedPref.getString("password", "");
        if (!TextUtils.isEmpty(username)) {
            mEtLoginactivityUsername.setText(username);
            if (!TextUtils.isEmpty(password)) {
                mEtLoginactivityPassword.setText(decryptPassword(password));
            }
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();
    }

    private String decryptPassword(String password) {
        // TODO: Implement password decryption here
        return password;
    }

    private void initView() {
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.tv_loginactivity_register);
        mRlLoginactivityTop = findViewById(R.id.rl_loginactivity_top);
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        mLlLoginactivityTwo = findViewById(R.id.ll_loginactivity_two);

        mBtLoginactivityLogin.setOnClickListener(this);
        mTvLoginactivityRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_loginactivity_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.bt_loginactivity_login:
                String name = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);

                        // Store username and password in SharedPreferences if "remember me" is checked
                        CheckBox cbRemember = findViewById(R.id.cb_auto_login);
                        if (cbRemember.isChecked()) {
                            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("username", name);
                            editor.putString("password", encryptPassword(password));
                            editor.apply();
                        }

                        finish();
                    } else {
                        mRetryCount++;
                        if (mRetryCount >= MAX_RETRY_COUNT) {
                            mBtLoginactivityLogin.setEnabled(false);
                            Toast.makeText(this, "您已连续尝试登录3次，请稍后再试", Toast.LENGTH_SHORT).show();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mBtLoginactivityLogin.setEnabled(true);
                                    mRetryCount = 0;
                                }
                            }, WAIT_TIME);
                        } else {
                            Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private String encryptPassword(String password) {
        // TODO: Implement password encryption here
        return password;
    }
}
