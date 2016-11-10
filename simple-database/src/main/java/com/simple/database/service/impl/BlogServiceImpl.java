package com.simple.database.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.database.mapper.BlogMapper;
import com.simple.database.pojo.Blog;
import com.simple.database.service.BlogService;
import com.simple.database.utils.PageUtil;

@Service
public class BlogServiceImpl implements BlogService{

	@Autowired
	private BlogMapper blogMapper;
	
	@SuppressWarnings("unused")
	@Override
	public Map<String,Object> queryAllBlog(Blog blog,PageUtil pageUtil) {
		Map<String,Object> maps = new HashMap<String, Object>();
		//分页助手
		PageHelper.startPage(pageUtil.getPage(), pageUtil.getRows());
		//分页查询
		List<Blog> blogLists =  this.blogMapper.queryAllBlog(blog,pageUtil);
		//获取分页数据
		PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogLists);
		//将数据放入map中
		if(null!=pageInfo){
			pageUtil.setTotalRows((int)pageInfo.getTotal());
			maps.put("blogs", pageInfo.getList());
		}else{
			maps.put("blogLists", new ArrayList<Blog>());
		}
		maps.put("pageUtil", pageUtil);
		return maps;
	}

	@Override
	public Integer queryAllBlogCounts(Blog blog) {
		return this.blogMapper.queryAllBlogCounts(blog);
	}

	@Override
	public int insert(Blog record) {
		return this.blogMapper.insert(record);
	}

	@Override
	public int insertSelective(Blog record) {
		return this.blogMapper.insertSelective(record);
	}

}
