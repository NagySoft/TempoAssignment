package com.tempo.sample.news.data;

public class SearchModel {
    private String query;
    private String fromDate;
    private String sortBy;
    private int pageSize;
    private int page;

    public SearchModel(String query, String fromDate, String sortBy) {
        this.query = query;
        this.fromDate = fromDate;
        this.sortBy = sortBy;
    }

    public SearchModel(String query, String fromDate, String sortBy, int page) {
        this.query = query;
        this.fromDate = fromDate;
        this.sortBy = sortBy;
        this.page = page;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
