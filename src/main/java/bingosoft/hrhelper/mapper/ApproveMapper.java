package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.model.Approve;

public interface ApproveMapper {
    int insert(Approve record);

    int insertSelective(Approve record);
}