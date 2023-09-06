package news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.intelrobot.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    /**
     * 新闻列表请求接口
     */
    public static final String URL = "http://v.juhe.cn/toutiao/index?type=top&key=5329c113f52973c6400f03d3977b39e1";

    /**
     * ListView对象
     */
    private ListView listView;

    /**
     * 新闻集合对象
     */
    private List<NewsData> datas;

    /**
     * 自定义的Adapter对象
     */
    private  MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = (ListView) this.findViewById(R.id.listView);

        datas = new ArrayList<NewsData>();

        getDatas(URL);

        /**
         * 实例化Adapter对象(注意:必须要写在在getDatas() 方法后面,不然datas中没有数据)
         */
        adapter = new MyAdapter(this, datas);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {

                /**
                 * 创建一个意图
                 */
                Intent intent = new Intent(MainActivity.this,NewsInfoActivity.class);

                /**
                 * 在datas中通过点击的位置position通过get()方法获得具体某个新闻
                 * 的数据然后通过Intent的putExtra()传递到NewsInfoActivity中
                 */
                intent.putExtra("newsTitle", datas.get(position).getNewsTitle());
                intent.putExtra("newsDate", datas.get(position).getNewsDate());
                intent.putExtra("newsImgUrl", datas.get(position).getNewsImgUrl());
                intent.putExtra("newsUrl", datas.get(position).getNewsUrl());

                MainActivity.this.startActivity(intent);//启动Activity

            }
        });

    }

    /**
     * 通过接口获取新闻列表的方法
     * @param url
     */
    public void getDatas(String url){

        final RequestQueue mQueue = Volley.newRequestQueue(this);

        JsonObjectRequest stringRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {

                            /**
                             * 对返回的json数据进行解析,然后装入datas集合中
                             */
                            JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                            JSONArray jsonArray = jsonObject2.getJSONArray("data");

                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                NewsData data = new NewsData();
                                data.setNewsTitle(item.getString("title"));
                                data.setNewsDate(item.getString("date"));
                                data.setNewsImgUrl(item.getString("thumbnail_pic_s"));
                                data.setNewsUrl(item.getString("url"));
                                datas.add(data);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        /**
                         * 请求成功后为ListView设置Adapter
                         */
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );

        mQueue.add(stringRequest);

    }


}