package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.model.Model;

import java.sql.SQLException;

public interface ModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(Model record) throws SQLException;

    int insertSelective(Model record);

    Model selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Model record) throws SQLException;

    int updateByPrimaryKeyWithBLOBs(Model record);

    int updateByPrimaryKey(Model record);
}