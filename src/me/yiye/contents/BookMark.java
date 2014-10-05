package me.yiye.contents;

import me.yiye.utils.DateUtil;

public class BookMark {
	private String title;
	private String summary;
	private String url;
	private String imgurl;
	private long uploaddate;
	
	private int favour;	// 收藏
	private int praise;	// 赞
	private int commentary;	//评论
	
	@Override
	public String toString() {
		return "title " + title + " summary " + summary + " url " + url + " imgurl " + imgurl
				+ " uploaddate " + uploaddate;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgUrl() {
		return imgurl;
	}

	public void setImgUrl(String img) {
		this.imgurl = img;
	}

	public String getUploaddate() {

		return DateUtil.timeStampToString(uploaddate);
	}

	public void setUploaddate(long uploaddate) {
		this.uploaddate = uploaddate;
	}
	
	public long getUploadDateTimeStamp() {
		return uploaddate;
	}

	public int getFavour() {
		return favour;
	}

	public void setFavour(int favour) {
		this.favour = favour;
	}

	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
	}

	public int getCommentary() {
		return commentary;
	}

	public void setCommentary(int commentary) {
		this.commentary = commentary;
	}
}
