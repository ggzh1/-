package news;

public class NewsData {
	private String newsTitle;//新闻标题
	private String newsDate; //新闻发布时间
	private String newsImgUrl; // 新闻图片Url地址
	private String newsUrl; //新闻详情Url地址

	@Override
	public String toString() {
		return "NewsData{" +
				"newsTitle='" + newsTitle + '\'' +
				", newsDate='" + newsDate + '\'' +
				", newsImgUrl='" + newsImgUrl + '\'' +
				", newsUrl='" + newsUrl + '\'' +
				'}';
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

	public String getNewsImgUrl() {
		return newsImgUrl;
	}

	public void setNewsImgUrl(String newsImgUrl) {
		this.newsImgUrl = newsImgUrl;
	}

	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

	public NewsData(String newsTitle, String newsDate, String newsImgUrl, String newsUrl) {
		this.newsTitle = newsTitle;
		this.newsDate = newsDate;
		this.newsImgUrl = newsImgUrl;
		this.newsUrl = newsUrl;
	}

	public NewsData() {
	}
}