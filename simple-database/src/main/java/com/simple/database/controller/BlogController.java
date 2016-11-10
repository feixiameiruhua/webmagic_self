package com.simple.database.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simple.database.pojo.Blog;
import com.simple.database.service.BlogService;
import com.simple.database.utils.PageUtil;

/**
 * 查询所有博客内容123456
 */
@Controller
@RequestMapping("blog")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BlogController.class);
	/**
	 * 多条件组合查询博客
	 * @param blogParam
	 * @return
	 */
	@RequestMapping(value="query", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAllBlogByParams(Blog blog,PageUtil pageUtil){
		LOGGER.info(">>>>>>>>> blogParam = "+blog+">>>>>>>> pageUtil = "+pageUtil);
		try {
			if(null==pageUtil.getPage()){
				pageUtil.setPage(1);
			}
			if(null==pageUtil.getRows()){
				pageUtil.setRows(10);
			}
			 Integer count = this.blogService.queryAllBlogCounts(blog);
			 LOGGER.info(">>>>>>>>> 共查询到："+count+"条博客记录");
			if(count>0){
				Map<String,Object> maps = this.blogService.queryAllBlog(blog,pageUtil);
				LOGGER.info(">>>>>>>>> 查询结果 = "+maps);
				return maps;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("多条件组合查询博客异常！");
		}
		return null;
	}
}
