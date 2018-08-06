package bingosoft.hrhelper.mapper;

import java.util.List;

import bingosoft.hrhelper.model.CancelRecord;

public interface CancelRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(CancelRecord record);

    int insertSelective(CancelRecord record);

    CancelRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CancelRecord record);

    int updateByPrimaryKey(CancelRecord record);

	List<CancelRecord> list();
}