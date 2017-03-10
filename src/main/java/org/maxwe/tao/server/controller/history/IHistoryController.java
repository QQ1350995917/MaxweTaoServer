package org.maxwe.tao.server.controller.history;

/**
 * Created by Pengwei Ding on 2017-01-09 18:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public interface IHistoryController {
    /**
     * 根据授权者的fromId查询（包括授权码交易和激活码生成）
     */
    void history();

    /**
     * 根据ID查询授权业绩（激活业绩）
     * 该接口供返点查询使用
     */
    void rebate();
}
