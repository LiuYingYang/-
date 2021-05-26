package com.medusa.basemall.utils;

import java.util.List;

/**
 * 分页
 *
 * @param<T>
 */
public class MongoPageModel<T> {

    //结果集
    private List<T> list;
    //查询记录数
    private int rowCount;
    //每页多少条数据
    private int pageSize;
    //第几页
    private int pageNum;
    //跳过几条数
    private int skip = 0;

    private Integer minSort;

    private Integer maxSort;

    /**
     * 总页数
     *
     * @return
     */
    public int getTotalPages() {
        return (rowCount + pageSize - 1) / pageSize;
    }

    public List<T> getDatas() {
        return list;
    }

    public void setDatas(List<T> list) {
        this.list = list;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSkip() {
        skip = (pageNum - 1) * pageSize;
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getMinSort() {
        return minSort;
    }

    public void setMinSort(Integer minSort) {
        this.minSort = minSort;
    }

    public Integer getMaxSort() {
        return maxSort;
    }

    public void setMaxSort(Integer maxSort) {
        this.maxSort = maxSort;
    }
}
