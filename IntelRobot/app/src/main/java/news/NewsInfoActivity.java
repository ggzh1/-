package news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.intelrobot.R;

public class NewsInfoActivity extends Activity {
	
	private ImageView ivImg;
	private TextView tvTitle;
	private TextView tvDate;
	private WebView webView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        initViews();
	}
	
	/**
	 * 初始化数据
	 */
	public void initViews(){
		ivImg = (ImageView) this.findViewById(R.id.iv_img);
		tvTitle = (TextView) this.findViewById(R.id.tv_title);
		tvDate = (TextView) this.findViewById(R.id.tv_date);
		webView = (WebView) this.findViewById(R.id.wv_content);
		
		/**
		 * 获得传递过来的数据
		 */
		Intent intent = this.getIntent();
        String newsTitle = intent.getStringExtra("newsTitle");
        String newsDate = intent.getStringExtra("newsDate");
        String newsImgUrl = intent.getStringExtra("newsImgUrl");
        String newsUrl = intent.getStringExtra("newsUrl");
        
        
        getImage(this, newsImgUrl, ivImg);
        
        /**
         * 显示新闻信息
         */
        tvTitle.setText(newsTitle);
        tvDate.setText(newsDate);
        webView.loadUrl(newsUrl);
		
	}
	
	/**
         * 加载网络图片
         */
	public void getImage(Context context, String imgUrl,
						 final ImageView imageView) {
 
			RequestQueue mQueue = Volley.newRequestQueue(context);
			ImageRequest imageRequest = new ImageRequest(imgUrl,
					new Response.Listener<Bitmap>() {
						@Override
						public void onResponse(Bitmap response) {
							imageView.setImageBitmap(response);
						}
					}, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			mQueue.add(imageRequest);
		}
	
}