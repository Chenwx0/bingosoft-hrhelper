package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.model.Approve;

public interface ApproveMapper {
    int deleteByPrimaryKey(String id);

    int insert(Approve record);

    int insertSelective(Approve record);

    Approve selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Approve record);

    int updateByPrimaryKey(Approve record);

	Result getHistoryRecord(String approve_name);
}