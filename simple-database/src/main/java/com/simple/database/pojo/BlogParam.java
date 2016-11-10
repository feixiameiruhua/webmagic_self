package com.simple.database.pojo;

public class BlogParam {
	
	private String author;/*作者*/

	private String title;/*标题*/

	private String from;/*博客来源*/
	
	private String url;/*地址*/

	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "BlogParam [author=" + author + ", title=" + title + ", from="
				+ from + ", url=" + url + "]";
	}
}
