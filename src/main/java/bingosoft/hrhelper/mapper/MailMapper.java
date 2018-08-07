package bingosoft.hrhelper.mapper;

import java.util.List;

import bingosoft.hrhelper.form.MailListForm;
import bingosoft.hrhelper.model.Mail;
import org.apache.ibatis.annotations.Param;

public interface MailMapper {
    int deleteByPrimaryKey(String id);

    int insert(Mail record);

    int insertSelective(Mail record);

    Mail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Mail record);

    int updateByPrimaryKeyWithBLOBs(Mail record);

    int updateByPrimaryKey(Mail record);

	List<Mail> listAll();

	void deleteAll();

    /**
     * 查询待发送邮件列表
     * @param recipient
     * @param operationId
     * @param startTime
     * @param endTime
     * @return
     */
	List<MailListForm> selectNotSendMailList(@Param("recipient") String recipient, @Param("operationId") String operationId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询已发送邮件列表
     * @param recipient
     * @param operationId
     * @param startTime
     * @param endTime
     * @return
     */
    List<MailListForm> selectSentMailList(@Param("recipient") String recipient, @Param("operationId") String operationId, @Param("startTime") String startTime, @Param("endTime") String endTime);

}