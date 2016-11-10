package com.simple.database.service;

import java.util.Map;

import com.simple.database.pojo.Blog;
import com.simple.database.utils.PageUtil;

public interface BlogService {

	Map<String, Object> queryAllBlog(Blog blog, PageUtil pageUtil);

	Integer queryAllBlogCounts(Blog blog);

	int insert(Blog record);

	int insertSelective(Blog record);

}
