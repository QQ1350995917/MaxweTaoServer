package org.maxwe.tao.server.common.response;

/**
 * Created by Pengwei Ding on 2016-11-11 14:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VPageData {
    private int totalPageNumber;
    private int currentPageIndex;
    private int sizeInPage;
    private Object dataInPage;

    public VPageData() {
        super();
    }

    public VPageData(int totalPageNumber, int currentPageIndex, int sizeInPage, Object dataInPage) {
        this.totalPageNumber = totalPageNumber;
        this.currentPageIndex = currentPageIndex;
        this.sizeInPage = sizeInPage;
        this.dataInPage = dataInPage;
    }

    public int getTotalPageNumber() {
        return totalPageNumber;
    }

    public void setTotalPageNumber(int totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public int getSizeInPage() {
        return sizeInPage;
    }

    public void setSizeInPage(int sizeInPage) {
        this.sizeInPage = sizeInPage;
    }

    public Object getDataInPage() {
        return dataInPage;
    }

    public void setDataInPage(Object dataInPage) {
        this.dataInPage = dataInPage;
    }
}
