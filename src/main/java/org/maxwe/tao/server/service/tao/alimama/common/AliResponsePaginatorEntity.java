package org.maxwe.tao.server.service.tao.alimama.common;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-02-24 20; //52.
 * Email; // www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description; // @TODO
 */
public class AliResponsePaginatorEntity implements Serializable {
    private long length; // 10,
    private long offset; // 0,
    private long page; // 1,
    private long beginIndex; // 1,
    private long endIndex; // 10,
    private long items; // 19844,
    private long lastPage; // 1985,
    private long itemsPerPage; // 10,
    private long previousPage; // 1,
    private long nextPage; // 2,
    private long pages; // 1985,
    private long firstPage; // 1,
    private int[] slider; // [1,2,3,4,5,6,7]

    public AliResponsePaginatorEntity() {
        super();
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(long beginIndex) {
        this.beginIndex = beginIndex;
    }

    public long getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(long endIndex) {
        this.endIndex = endIndex;
    }

    public long getItems() {
        return items;
    }

    public void setItems(long items) {
        this.items = items;
    }

    public long getLastPage() {
        return lastPage;
    }

    public void setLastPage(long lastPage) {
        this.lastPage = lastPage;
    }

    public long getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(long itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public long getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(long previousPage) {
        this.previousPage = previousPage;
    }

    public long getNextPage() {
        return nextPage;
    }

    public void setNextPage(long nextPage) {
        this.nextPage = nextPage;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(long firstPage) {
        this.firstPage = firstPage;
    }

    public int[] getSlider() {
        return slider;
    }

    public void setSlider(int[] slider) {
        this.slider = slider;
    }
}
