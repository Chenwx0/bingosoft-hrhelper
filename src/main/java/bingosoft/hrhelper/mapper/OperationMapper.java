package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.model.Operation;

public interface OperationMapper {
    int insert(Operation record);

    int insertSelective(Operation record);
}