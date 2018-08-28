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
    
    /**
     * 遍历所有邮件
     * @return
     */
    List<Mail> listAll();

    /**
     * 删除所有邮件
     * @return
     */
    void deleteAll();

    /**
     * 取消“已取消”邮件的生成
     * @return
     */
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
    
    /**
     * 根据业务ID和员工ID查找总数
     * @param record
     * @return
     */
	int selectByEidRid(Mail record);
	
	/**
	 * 通过规则删除邮件
	 * @param rule_id
	 */
	int deleteMailByRule(String rule_id);

    /**
     * 通过业务ID删除邮件
     * @param operationId
     * @return
     */
    int deleteByOperationId(String operationId) throws SQLException;

	void deleteByRuleId(String ruleId);
}