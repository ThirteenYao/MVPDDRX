package com.thirteenyao.rrdd.base.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/** 基本分页信息类*/
public class PageBean<T> extends BaseModel{
    /** 当前页数 */
	@JSONField(name="pageNo")
	private int pageNo = 0;
    /** 分页大小 */
	@JSONField(name="pageSize")
	private int pageSize = 20;
    /** 总页数 */
	@JSONField(name="totalPage")
	private int totalPage;
    /** 总数量 */
	private int count = 0;

    /** 数据源 */
    private List<T> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
