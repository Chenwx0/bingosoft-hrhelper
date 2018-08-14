package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.form.MailListForm;
import bingosoft.hrhelper.model.Mail;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface MailMapper {
    int deleteByPrimaryKey(String id) throws SQLException;

    int insert(Mail record);

    int insertSelective(Mail record);

    Mail selectByPrimaryKey(String id) throws SQLException;

    int updateByPrimaryKeySelective(Mail record) throws SQLException;

    int updateByPrimaryKeyWithBLOBs(Mail record);

    int updateByPrimaryKey(Mail record)  throws SQLException;

    List<Mail> listAll();

    void deleteAll();

    void deleteCancelMail(Mail record);

    /**
     * 查询待发送邮件列表
     * @param params
     * @return
     */
    List<MailListForm> selectListNotSend(Map<String,String> params) throws SQLException;

    /**
     * 查询已发送不需审批邮件列表
     * @param params
     * @return
     */
    List<MailListForm> selectListsentNoApprove(Map<String,String> params) throws SQLException;

    /**
     * 查询已发送需审批邮件列表
     * @param params
     * @return
     */
    List<MailListForm> selectListSentApprove(Map<String,String> params) throws SQLException;
}