package com.simple.database;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import com.simple.database.mapper.BlogMapper;
import com.simple.database.pojo.Blog;
import com.simple.database.service.BlogService;

public class BolgYuanPageProcess implements PageProcessor {
	
	// 详情页匹配表达式
//		private static final String URL_PATTERN = "http://chenhao6\\.blog\\.51cto\\.com/6228054/\\d+";
		private static final String URL_PATTERN = ".*/\\w+/\\d+\\.html";

		// 计数器
		private final AtomicLong count = new AtomicLong(0);
		
	
	// 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me().setRetryTimes(3)// 设置重试次数
			.setDomain("www.cnblogs.com")// 设置域名
			.setSleepTime(1000);// 设置抓取间隔
	ApplicationContext ac = new ClassPathXmlApplicationContext("spring/applicationContext*.xml");

	@Override
	public void process(Page page) {
		Blog blog = new Blog();
		//详情页
		if(page.getUrl().regex(URL_PATTERN).match()){
			page.putField("title", page.getHtml().xpath("//h2/a/text()").get());
			if(page.getResultItems().get("title")==null){
				page.setSkip(true);
			}else{
				page.putField("url", page.getUrl());
				page.putField("author", page.getHtml().css("a#lnkBlogTitle").xpath("/a/text()"));
				count.incrementAndGet();//+1
				System.out.println("【兴趣e族】抓取到第:"+count.get()+"条有效数据");
				BlogMapper blogMapper = ac.getBean(BlogMapper.class);
				//把数据插入到数据库;后期插入之前要先查
				blog.setTitle(String.valueOf(page.getResultItems().get("title")));
				blog.setUrl(String.valueOf(page.getResultItems().get("url")));
				blog.setAuthor(String.valueOf(page.getResultItems().get("author")));
				blog.setInsertTime(new Date());
				blog.setSid(2);
				blogMapper.insert(blog);
			}
			//分页
		}else{
			page.addTargetRequests(page.getHtml().css("div.post-list-item").links().regex(".*/\\w+/\\d+\\.html").all());
			page.addTargetRequests(page.getHtml().css("div#pager").links().regex(".*page=\\d+").all());
		}
		

	}

	@Override
	public Site getSite() {
		return site;
	}
	
	/**
	 * @描述：
	 * @创建人：方伟
	 * @创建时间：2016年5月5日 上午10:35:44
	 * @param args
	 */
	public static void main(String[] args) {
		Spider.create(new BolgYuanPageProcess())
				.addUrl("http://www.cnblogs.com/mushroom/default.aspx?page=1")
				.addPipeline(new ConsolePipeline()).thread(3).run();

	}

}
