package weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.intelrobot.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvCity;
    private TextView tvWeather;
    private TextView tvTemp;
    private TextView tvWind;
    private TextView tvpm;
    private ImageView ivIcon;
    private Map<String,String> map;
    private List<Map<String,String>> list;
    private String temp,weather,name,pm,wind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainw);
        intView();
        try {
            InputStream is=this.getResources().openRawResource(R.raw.weather1);

            List<WeatherInfo> weatherInfos=WeatherService.getInfsFromXML(is);
            list =new ArrayList<Map<String,String>>();
            for (WeatherInfo info:weatherInfos){
                map=new HashMap<String, String>();
                map.put("temp",info.getTemp());
                map.put("weather",info.getWeather());
                map.put("name",info.getName());
                map.put("pm",info.getPm());
                map.put("wind",info.getWind());
                list.add(map);
            }
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"解析信息失败",Toast.LENGTH_SHORT).show();
        }
        getMap(1,R.drawable.sun);
    }
    private void intView(){
        tvCity =(TextView) findViewById(R.id.tv_city);
        tvWeather=(TextView) findViewById(R.id.tv_weather);
        tvTemp=(TextView) findViewById(R.id.tv_temp);
        tvWind=(TextView) findViewById(R.id.tv_wind);
        tvpm=(TextView) findViewById(R.id.tv_pm);
        ivIcon =(ImageView) findViewById(R.id.iv_icon);
        findViewById(R.id.btn_sh).setOnClickListener(this);
        findViewById(R.id.btn_gz).setOnClickListener(this);
        findViewById(R.id.btn_bj).setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_sh:
                getMap(0,R.drawable.cloud_sun1);
                break;
            case R.id.btn_bj:
                getMap(1,R.drawable.sun);
                break;
            case R.id.btn_gz:
                getMap(2,R.drawable.clouds);
                break;
        }
    }
    private void getMap(int number,int iconNumber){
        Map<String,String> cityMap=list.get(number);
        temp=cityMap.get("temp");
        weather=cityMap.get("weather");
        name=cityMap.get("name");
        pm=cityMap.get("pm");
        wind=cityMap.get("wind");
        tvCity.setText(name);
        tvWeather.setText(weather);
        tvTemp.setText(""+temp);
        tvWind.setText("风力："+wind);
        tvpm.setText("pm:"+pm);
        ivIcon.setImageResource(iconNumber);
    }
}
