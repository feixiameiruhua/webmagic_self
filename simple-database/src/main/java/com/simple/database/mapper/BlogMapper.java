package com.simple.database.mapper;

import com.simple.database.pojo.Blog;
import com.simple.database.pojo.BlogExample;
import com.simple.database.utils.PageUtil;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BlogMapper {
    int countByExample(BlogExample example);

    int deleteByExample(BlogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    int insertSelective(Blog record);

    List<Blog> selectByExample(BlogExample example);

    Blog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByExample(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);

	Integer queryAllBlogCounts(@Param("blog")Blog blog);

	List<Blog> queryAllBlog(@Param("blog")Blog blog, @Param("pageUtil")PageUtil pageUtil);
}