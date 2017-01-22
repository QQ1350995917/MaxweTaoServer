package org.maxwe.tao.server.service.api.goods;

/**
 * Created by Pengwei Ding on 2017-01-18 14:54.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class GoodsFilterEntity {
    private static final String DES = "_des";//排序（降序）
    private static final String ASC = "_asc";// 排序（升序）
    private static final String TOTAL_SALES = "total_sales"; //销量，
    private static final String TK_RATE = "tk_rate";// 淘客佣金比率
    private static final String TK_TOTAL_SALES = "tk_total_sales";// 累计推广量
    private static final String TK_TOTAL_COMMI = "tk_total_commi";// 总支出佣金

    public static final String TOTAL_SALES_DES = TOTAL_SALES + DES;
    public static final String TOTAL_SALES_ASC = TOTAL_SALES + ASC;
    public static final String TK_RATE_DES = TK_RATE + ASC;
    public static final String TK_RATE_ASC = TK_RATE + ASC;
    public static final String TK_TOTAL_SALES_DES = TK_TOTAL_SALES + ASC;
    public static final String TK_TOTAL_SALES_ASC = TK_TOTAL_SALES + ASC;
    public static final String TK_TOTAL_COMMI_DES = TK_TOTAL_COMMI + ASC;
    public static final String TK_TOTAL_COMMI_ASC = TK_TOTAL_COMMI + ASC;

    private String q;
    private String sort;
    private int page_no;
    private int page_size;

    public GoodsFilterEntity() {
    }

    public GoodsFilterEntity(String q, String sort, int page_no, int page_size) {
        this.q = q;
        this.sort = sort;
        this.page_no = page_no;
        this.page_size = page_size;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getPage_no() {
        return page_no;
    }

    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }
}
