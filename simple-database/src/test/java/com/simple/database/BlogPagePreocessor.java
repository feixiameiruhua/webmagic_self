/**
 * 
 */
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

/**
 * @项目名称：wupao-spider
 * @类名称：BlogPagePreocessor
 * @类描述：
 * @创建人：席在盛
 * @创建时间：2016年5月5日 上午10:35:44
 * @version
 */
public class BlogPagePreocessor implements PageProcessor {

	// 详情页匹配表达式
//	private static final String URL_PATTERN = "http://chenhao6\\.blog\\.51cto\\.com/6228054/\\d+";
	private static final String URL_PATTERN = "http://simplelife\\.blog\\.51cto\\.com/9954761/\\d+";

	// 计数器
	private final AtomicLong count = new AtomicLong(0);

	// 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me().setRetryTimes(3)// 设置重试次数
			.setDomain("www.51blog.com")// 设置域名
			.setSleepTime(1000);// 设置抓取间隔

	//读取spring的配置文件
	ApplicationContext ac = new ClassPathXmlApplicationContext("spring/applicationContext*.xml");

	// 具体的解析逻辑
	public void process(Page page) {
		//创建对象
		Blog blog = new Blog();
		// 详情页处理
		if (page.getUrl().regex(URL_PATTERN).match()) {

			page.putField("title",
					page.getHtml().xpath("//div[@class='showTitle']/text()")
							.get());
			// 设置跳过
			if (page.getResultItems().get("title") == null) {
				page.setSkip(true);
			} else {
				page.putField("author", page.getHtml().xpath("//div[@class='blogName']/a/h1/text()").toString());
//				page.putField("content",page.getHtml().xpath("//div[@class=='showContent']/p/span/text()").all());
				count.incrementAndGet();
				System.out.println("抓取到的第："+count.get()+"条有效数据");
				BlogMapper blogMapper = ac.getBean(BlogMapper.class);
				//把数据插入到数据库;后期插入之前要先查
				blog.setTitle(String.valueOf(page.getResultItems().get("title")));
				blog.setUrl(String.valueOf(page.getUrl()));
				blog.setAuthor(String.valueOf(page.getResultItems().get("author")));
				blog.setInsertTime(new Date());
				blog.setSid(1);
				blogMapper.insert(blog);
			}
			// 扩展目标页面
		} else {
			page.addTargetRequests(page.getHtml().css("div.blogList").links()
					.regex(".*/9954761/\\d+").all());
			page.addTargetRequests(page.getHtml().css("div.pages").links()
					.regex(".*/9954761/p-\\d+").all());
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	/**
	 * @描述：
	 * @创建人：席在盛
	 * @创建时间：2016年5月5日 上午10:35:44
	 * @param args
	 */
	public static void main(String[] args) {
		Spider.create(new BlogPagePreocessor())
				.addUrl("http://simplelife.blog.51cto.com/9954761/p-1")
				.addPipeline(new ConsolePipeline()).thread(3).run();

	}

}
