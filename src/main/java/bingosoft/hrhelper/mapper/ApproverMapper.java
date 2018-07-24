package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.model.Approver;

public interface ApproverMapper {
    int insert(Approver record);

    int insertSelective(Approver record);
}