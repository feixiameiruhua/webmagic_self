package com.silmple.webmagic.other;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 
 * @author 方伟
 *
 */
public class ImageDownloadPageProcess implements PageProcessor {

	// 详情页匹配表达式
	// private static final String URL_PATTERN =
	// "http://simplelife\\.blog\\.51cto\\.com/9954761/\\d+";
	private static final String URL_PATTERN = "http://wmtp\\.net/\\d+";

	// 计数器
	private final AtomicLong count = new AtomicLong(0);

	// 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me().setRetryTimes(3)// 设置重试次数
			.setDomain("www.wmtp.net")// 设置域名
			.setSleepTime(1000);// 设置抓取间隔

	// 具体的解析逻辑
	public void process(Page page) {
		// 详情页处理
		if (page.getUrl().regex(URL_PATTERN).match()) {
			page.putField("img",
					page.getHtml().xpath("//div[@class='content-c']/p/")
							.links());
			// 设置跳过
			if (page.getResultItems().get("img") == null) {
				page.setSkip(true);
			}else{
				count.incrementAndGet();
				System.out.println("抓取到的第：" + count.get() + "条有效数据");
				System.out.println("=========================下载========================");
				List<String> imgSrc = new ArrayList<String>();
				imgSrc.add(String.valueOf(page.getResultItems().get("img")));
				CatchImage cm = new CatchImage();  
				cm.getImageSrc(imgSrc); 
				//下载图片  
				cm.Download(imgSrc);
			}
			// 扩展目标页面
		} else {
			page.addTargetRequests(page.getHtml().css("div#mainbox").links()
					.regex(".*/\\d+").all());
			page.addTargetRequests(page.getHtml().css("div#pages").links()
					.regex(".*/page/\\d+").all());
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	/**
	 * @author fangwei
	 * @param args
	 */
	public static void main(String[] args) {
		Spider.create(new ImageDownloadPageProcess())
				.addUrl("http://wmtp.net/page/1")
				.addPipeline(new ConsolePipeline()).thread(3).run();

	}

}
