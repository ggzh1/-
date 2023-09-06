package test;

import Robot.ChatRobotActivity;
import alter.AlterActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.intelrobot.R;
import notepad.bean.NotepadActivity;
import test.PersonalInfoActivity;
import test.loginActivity;

/**
 * 此类 implements View.OnClickListener 之后，
 * 就可以把onClick事件写到onCreate()方法之外
 * 这样，onCreate()方法中的代码就不会显得很冗余
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnPersonalInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnPersonalInfo = findViewById(R.id.btn_personal_info);
        mBtnPersonalInfo = findViewById(R.id.btn_music_player);
        mBtnPersonalInfo = findViewById(R.id.btn_notebook);
        mBtnPersonalInfo = findViewById(R.id.btn_weather);
        mBtnPersonalInfo = findViewById(R.id.btn_chatbot);
        mBtnPersonalInfo = findViewById(R.id.btn_notebook2);
        mBtnPersonalInfo = findViewById(R.id.btn_news);

        mBtnPersonalInfo.setOnClickListener(this);
        initView();
    }

    private void initView() {
        // 初始化控件对象
        Button mBtMainLogout = findViewById(R.id.bt_main_logout);
        Button mBtMainLogout1 = findViewById(R.id.btn_personal_info);
        Button mBtMainLogout2 = findViewById(R.id.btn_music_player);
        Button mBtMainLogout3 = findViewById(R.id.btn_notebook);
        Button mBtMainLogout4 = findViewById(R.id.btn_weather);
        Button mBtMainLogout5 = findViewById(R.id.btn_chatbot);
        Button mBtMainLogout6 = findViewById(R.id.btn_notebook2);
        Button mBtMainLogout7 = findViewById(R.id.btn_news);
        // 绑定点击监听器
        mBtMainLogout.setOnClickListener(this);
        mBtMainLogout1.setOnClickListener(this);
        mBtMainLogout2.setOnClickListener(this);
        mBtMainLogout3.setOnClickListener(this);
        mBtMainLogout4.setOnClickListener(this);
        mBtMainLogout5.setOnClickListener(this);
        mBtMainLogout6.setOnClickListener(this);
        mBtMainLogout7.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_personal_info:
                Intent intent = new Intent(this, PersonalInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_main_logout:
                Intent intent1 = new Intent(this, loginActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_music_player:
                Intent intent2 = new Intent(this, music.MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_notebook:
                Intent intent3 = new Intent(this, NotepadActivity.class);
                startActivity(intent3);
                break;

            case R.id.btn_chatbot:
                Intent intent5 = new Intent(this, ChatRobotActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_notebook2:
                Intent intent6 = new Intent(this, AlterActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_news:
                Intent intent7 = new Intent(this, news.MainActivity.class);
                startActivity(intent7);
                break;
            case R.id.btn_weather:
                Intent intent8 = new Intent(this, weather.MainActivity.class);
                startActivity(intent8);
                break;
        }
    }

}

