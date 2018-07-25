package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.model.Operation;

public interface OperationMapper {
    int deleteByPrimaryKey(String id);

    int insert(Operation record);

    int insertSelective(Operation record);

    Operation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Operation record);

    int updateByPrimaryKey(Operation record);
}