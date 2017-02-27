package org.maxwe.tao.server.service.trade;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.history.HistoryEntity;

import java.sql.SQLException;

/**
 * Created by Pengwei Ding on 2017-01-10 10:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TradeServices implements ITradeServices {

    @Override
    public boolean grant(AgentEntity agentEntity, HistoryEntity historyEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int updateAgent = Db.update("UPDATE agent SET spendCodes = spendCodes + 1, leftCodes = leftCodes - 1 " +
                        "where id = ? ", agentEntity.getId());

                Record historyRecord = new Record()
                        .set("id", historyEntity.getId())
                        .set("fromId", historyEntity.getFromId())
                        .set("toId", historyEntity.getToId())
                        .set("type", historyEntity.getType())
                        .set("actCode", historyEntity.getActCode())
                        .set("codeNum", historyEntity.getCodeNum());
                boolean isSave = Db.save("history", historyRecord);
                return updateAgent == 1 && isSave;
            }
        });
        return succeed;

    }

    @Override
    public boolean trade(AgentEntity fromAgentEntity, AgentEntity toAgentEntity, HistoryEntity historyEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int toAgent = Db.update("UPDATE agent SET haveCodes = haveCodes + ?, leftCodes = leftCodes + ? " +
                        "where id = ? ", historyEntity.getCodeNum(), historyEntity.getCodeNum(), toAgentEntity.getId());

                int fromAgent = Db.update("UPDATE agent SET spendCodes = spendCodes + ?, leftCodes = leftCodes - ? " +
                        "where id = ? ", historyEntity.getCodeNum(), historyEntity.getCodeNum(), fromAgentEntity.getId());

                Record historyRecord = new Record()
                        .set("id", historyEntity.getId())
                        .set("fromId", historyEntity.getFromId())
                        .set("toId", historyEntity.getToId())
                        .set("type", historyEntity.getType())
                        .set("actCode", historyEntity.getActCode())
                        .set("codeNum", historyEntity.getCodeNum());
                boolean isSave = Db.save("history", historyRecord);

                return fromAgent == 1 && toAgent == 1 && isSave;
            }
        });
        return succeed;
    }
}
