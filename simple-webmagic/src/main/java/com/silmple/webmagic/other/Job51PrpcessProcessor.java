package com.silmple.webmagic.other;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Job51PrpcessProcessor implements PageProcessor{

	private Site site = Site.me().setRetryTimes(3).setSleepTime(0);
	
	 @Override
	    public void process(Page page) {
		 	page.addTargetRequests(page.getHtml().links().regex("(http://chenhao6.blog.51cto.com/\\w+)").all());
		 	page.addTargetRequests(page.getHtml().links().regex("(http://chenhao6.blog.51cto.com/\\w+/\\w+)").all());
//	        page.addTargetRequests(page.getHtml().links().regex("(http://simplelife.blog.51cto.com/\\w+/\\w+/\\w+)").all());
//	        page.addTargetRequests(page.getHtml().links().regex("(http://simplelife.blog.51cto.com/\\w+/\\w+/\\w+/\\w+)").all());
	        page.putField("title", page.getHtml().xpath("//div[@class='showTitle']/").toString());
//	        page.putField("url", page.getHtml().xpath("//h3[@class='artTitle']").links().toString());
	        if (page.getResultItems().get("title")==null ){
	            //skip this page
	            page.setSkip(true);
	        }
//	        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
	    }

	    @Override
	    public Site getSite() {
	        return site;
	    }

	    public static void main(String[] args) {
	        Spider.create(new Blog2PageProcess()).addUrl("http://chenhao6.blog.51cto.com/").thread(5).run();
	    }

}
