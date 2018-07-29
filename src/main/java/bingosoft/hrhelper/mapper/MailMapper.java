package bingosoft.hrhelper.mapper;

import java.util.List;

import bingosoft.hrhelper.model.Mail;

public interface MailMapper {
    int deleteByPrimaryKey(String id);

    int insert(Mail record);

    int insertSelective(Mail record);

    Mail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Mail record);

    int updateByPrimaryKey(Mail record);

	void deleteAll();

	List<Mail> listAll();
}