package com.silmple.webmagic.other;

import java.util.concurrent.atomic.AtomicLong;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Blog2PageProcess implements PageProcessor{

	private Site site = Site.me().setRetryTimes(3).setSleepTime(0);
	
	// 计数器
	private final AtomicLong count = new AtomicLong(0);
		
	 @Override
	    public void process(Page page) {
		 	page.addTargetRequests(page.getHtml().links().regex("(http://blog.csdn.net/\\w+)").all());
		 	page.addTargetRequests(page.getHtml().links().regex("(http://blog.csdn.net/\\w+/\\w+)").all());
	        page.addTargetRequests(page.getHtml().links().regex("(http://blog.csdn.net/\\w+/\\w+/\\w+)").all());
	        page.addTargetRequests(page.getHtml().links().regex("(http://blog.csdn.net/\\w+/\\w+/\\w+/\\w+)").all());
	        page.putField("title", page.getHtml().xpath("//div[@class='article_title']/h1/span/a/text()").toString());
	        if (page.getResultItems().get("title")==null){
	            //skip this page
	            page.setSkip(true);
	        }else{
	        	page.putField("url", page.getHtml().xpath("//div[@class='article_title']/h1/span/").links().toString());
	        	count.incrementAndGet();
	        	System.out.println("抓取到的第："+count.get()+"条有效数据");
	        }
//	        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
	    }

	    @Override	
	    public Site getSite() {
	        return site;
	    }

	    public static void main(String[] args) {
	        Spider.create(new Blog2PageProcess()).addUrl("http://blog.csdn.net/").thread(5).run();
	    }

}
