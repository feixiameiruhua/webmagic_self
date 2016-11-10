package com.simple.database;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.simple.database.mapper.BlogMapper;
import com.simple.database.pojo.Blog;
import com.simple.database.pojo.BlogExample;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Blog2PageProcess implements PageProcessor{

	private Site site = Site.me().setRetryTimes(3).setSleepTime(0);
	
	// 计数器
	private final AtomicLong count = new AtomicLong(0);

	ApplicationContext ac = new ClassPathXmlApplicationContext("spring/applicationContext*.xml");

	 @Override
	    public void process(Page page) {
		 Blog blog = new Blog();
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
	        	page.putField("author", page.getHtml().xpath("//div[@id='blog_title']/h2/a/text()"));
	        	count.incrementAndGet();
	        	System.out.println("抓取到的第："+count.get()+"条有效数据");
	        	BlogMapper blogMapper = ac.getBean(BlogMapper.class);
				//把数据插入到数据库;后期插入之前要先查
				blog.setTitle(String.valueOf(page.getResultItems().get("title")));
				blog.setUrl(String.valueOf(page.getResultItems().get("url")));
				blog.setAuthor(String.valueOf(page.getResultItems().get("author")));
				blog.setInsertTime(new Date());
				blog.setSid(2);
				BlogExample blogExample = new BlogExample();
				blogExample.createCriteria().andUrlEqualTo(String.valueOf(page.getResultItems().get("url")));
				List<Blog> list = blogMapper.selectByExample(blogExample);
				if(null!=blogMapper.selectByExample(blogExample)){//如果不存在则插入
					blogMapper.insert(blog);
				}
				try {
					Thread.sleep(3000);//休息1秒钟
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
