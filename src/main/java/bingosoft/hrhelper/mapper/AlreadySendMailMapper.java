package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.model.AlreadySendMail;

import java.sql.SQLException;

public interface AlreadySendMailMapper {
    int deleteByPrimaryKey(String id) throws SQLException;

    int insert(AlreadySendMail record);

    int insertSelective(AlreadySendMail record);

    AlreadySendMail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AlreadySendMail record);

    int updateByPrimaryKeyWithBLOBs(AlreadySendMail record);

    int updateByPrimaryKey(AlreadySendMail record);
}