package com.simple.database.utils;

/**
 * 
 * @项目名称：wupao-job
 * @类名称：PageUtil
 * @类描述：接收客户端自动提交page和rows(页码和页数)
 * @创建人：王炎
 * @创建时间：2016年2月24日 下午1:14:30
 * @version：
 */
public class PageUtil  implements java.io.Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	// 当前页
	private Integer page;
	// 每页多少条
	private Integer rows;
	// 总页数
	private Integer totalPage;
	// 总记录条数
	private Integer totalRows;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		if(rows==null){
			rows=15;
		}
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getTotalPage() {
		if(totalRows==null){
			totalRows=0;
		}
		if (totalRows % getRows() == 0) {
			totalPage = totalRows / getRows();
		} else {
			totalPage = totalRows / getRows() + 1;
		}
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	

	public PageUtil(Integer page, Integer rows, Integer totalPage,
			Integer totalRows) {
		super();
		this.page = page;
		this.rows = rows;
		this.totalPage = totalPage;
		this.totalRows = totalRows;
	}
	

	public PageUtil() {
		super();
	}

	@Override
	public String toString() {
		return "PageUtil [page=" + page + ", rows=" + rows + "]";
	}

}