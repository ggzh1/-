package music;


import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.intelrobot.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static SeekBar sb;
    private static TextView tv_progress,tv_total;
    private ObjectAnimator animator;
    private MusicService.MusicControl musicControl;
    MyServiceConn conn;
    Intent intent;
    private boolean isUnbind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        init();
    }
    private void init(){

        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_total = (TextView) findViewById(R.id.tv_total);
        sb = (SeekBar) findViewById(R.id.sb);

        findViewById(R.id.btn_play).setOnClickListener(this);
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_continue_play).setOnClickListener(this);
        findViewById(R.id.btn_exit).setOnClickListener(this);

        intent = new Intent(this,MusicService.class);
        conn = new MyServiceConn();
        bindService(intent,conn,BIND_AUTO_CREATE);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == seekBar.getProgress()){
                    animator.pause();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                musicControl.seekTo(progress);
            }
        });
        ImageView iv_music = findViewById(R.id.iv_music);
        animator = ObjectAnimator.ofFloat(iv_music,"rotation", 0f,360.0f);
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        //
    }

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Bundle bundle = msg.getData();
            int duration = bundle.getInt("duration");
            int currentPosition = bundle.getInt("currentPosition");
            Log.i("------->", duration + ":::" + currentPosition);
            sb.setMax(duration);
            sb.setProgress(currentPosition);
            int minute = duration/1000/60;
            int second = duration/1000%60;
            String strMinute = null;
            String strSecond = null;
            if(minute<10){
                strMinute = "0"+minute;
            }else{
                strMinute = minute+"";
            }
            if (second<10){
                strSecond = "" +second;
            }else {
                strSecond = second+"";
            }
            tv_total.setText(strMinute+":"+strSecond);
            minute = currentPosition/1000/60;
            second = currentPosition/1000%60;
            if(minute<10){
                strMinute = "0"+minute;
            }else{
                strMinute = minute+"";
            }
            if (second<10){
                strSecond = "" +second;
            }else {
                strSecond = second+"";
            }
            tv_progress.setText(strMinute+":"+strSecond);
        }
    };

    class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicControl = (MusicService.MusicControl) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
    private void unbind(boolean isUnbind){
        if (!isUnbind ){
            musicControl.pausePlay();
            unbindService(conn);
            stopService(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                musicControl.play();
                animator.start();
                break;
            case R.id.btn_pause:
                musicControl.pausePlay();
                animator.pause();
                break;
            case R.id.btn_continue_play:
                musicControl.continuePlay();
                animator.start();
                break;
            case R.id.btn_exit:
                unbind(isUnbind);
                isUnbind = true;
                finish();
                break;
        }
    }
    //    @Override
    protected void onDestory(){
        super.onDestroy();
        unbind(isUnbind);
    }
}