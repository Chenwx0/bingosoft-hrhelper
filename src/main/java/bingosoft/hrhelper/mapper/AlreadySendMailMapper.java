package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.model.AlreadySendMail;

public interface AlreadySendMailMapper {
    int deleteByPrimaryKey(String id);

    int insert(AlreadySendMail record);

    int insertSelective(AlreadySendMail record);

    AlreadySendMail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AlreadySendMail record);

    int updateByPrimaryKey(AlreadySendMail record);
}