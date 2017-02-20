package org.maxwe.tao.server.service.tao.bao.category;

/**
 * Created by Pengwei Ding on 2017-02-19 10:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 淘宝返回类目信息
 */
public class TaoCategoryResponseEntity {
    private long[] parent_ids;// Number [] [12,34]父类目ID数组
    private long level;//Number 2类目层级
    private boolean leaf_category;// Boolean false是否叶子类目（只有叶子类目才能发布商品）
    private String name;// String Consumer Electronics类目名称
    private long[] category_id;// Number 56类目ID
    private long[] child_ids;// Number [] [78,90]子类目ID数组

    public TaoCategoryResponseEntity() {
        super();
    }

    public long[] getParent_ids() {
        return parent_ids;
    }

    public void setParent_ids(long[] parent_ids) {
        this.parent_ids = parent_ids;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public boolean isLeaf_category() {
        return leaf_category;
    }

    public void setLeaf_category(boolean leaf_category) {
        this.leaf_category = leaf_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long[] getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long[] category_id) {
        this.category_id = category_id;
    }

    public long[] getChild_ids() {
        return child_ids;
    }

    public void setChild_ids(long[] child_ids) {
        this.child_ids = child_ids;
    }
}
