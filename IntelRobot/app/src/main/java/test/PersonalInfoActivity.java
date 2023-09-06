package test;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.intelrobot.R;


import java.util.ArrayList;

public class PersonalInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvName;
    private TextView mTvPassword;
    private EditText mEtNewPassword;
    private CheckBox mCbClearAutoLogin;
    private Button mBtnSave;

    private DBOpenHelper mDBOpenHelper;

    private User mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        mTvName = findViewById(R.id.tv_name);
        mTvPassword = findViewById(R.id.tv_loginactivity_password);
        mEtNewPassword = findViewById(R.id.et_new_password);
        mCbClearAutoLogin = findViewById(R.id.cb_clear_auto_login);
        mBtnSave = findViewById(R.id.btn_save);

        mBtnSave.setOnClickListener(this);

        mDBOpenHelper = new DBOpenHelper(this);

        // 获取当前用户的信息
        SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        if (!TextUtils.isEmpty(username)) {
            ArrayList<User> data = mDBOpenHelper.getAllData();
            for (int i = 0; i < data.size(); i++) {
                User user = data.get(i);
                if (username.equals(user.getName())) {
                    mCurrentUser = user;
                    break;
                }
            }
            if (mCurrentUser != null) {
                mTvName.setText(mCurrentUser.getName());
                mTvPassword.setText(mCurrentUser.getPassword());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                String newPassword = mEtNewPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(newPassword)) {
                    // 更新当前用户的密码
                    updatePassword(mCurrentUser, newPassword);
                    Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();

                    // 如果选中了“清除自动登录状态”的复选框，则清除自动登录状态
                    if (mCbClearAutoLogin.isChecked()) {
                        SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.remove("username");
                        editor.remove("password");
                        editor.apply();
                    }

                    // 关闭当前页面，返回上一页
                    finish();
                }
                break;
        }
    }

    /**
     * 更新用户密码
     *
     * @param user        需要更新密码的用户对象
     * @param newPassword 新密码
     */
    private void updatePassword(User user, String newPassword) {
        mDBOpenHelper.updatePassword(user, newPassword);
        // 更新当前用户的密码
        mCurrentUser.setPassword(newPassword);
        // 更新密码显示
        mTvPassword.setText(newPassword);
    }
}