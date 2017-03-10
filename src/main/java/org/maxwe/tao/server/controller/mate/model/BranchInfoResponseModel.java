package org.maxwe.tao.server.controller.mate.model;

import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.service.level.LevelEntity;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-03 17:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理查看下级信息的响应模型
 */
public class BranchInfoResponseModel extends ResponseModel<BranchInfoRequestModel> {
    private MateModel branch;
    private List<LevelEntity> levels;//同时返回所有等级，便于客户端展示

    public BranchInfoResponseModel() {
        super();
    }

    public BranchInfoResponseModel(BranchInfoRequestModel requestModel) {
        super(requestModel);
    }

    public BranchInfoResponseModel(BranchInfoRequestModel requestModel, MateModel branch, List<LevelEntity> levels) {
        super(requestModel);
        this.branch = branch;
        this.levels = levels;
    }

    public MateModel getBranch() {
        return branch;
    }

    public void setBranch(MateModel branch) {
        this.branch = branch;
    }

    public List<LevelEntity> getLevels() {
        return levels;
    }

    public void setLevels(List<LevelEntity> levels) {
        this.levels = levels;
    }
}
