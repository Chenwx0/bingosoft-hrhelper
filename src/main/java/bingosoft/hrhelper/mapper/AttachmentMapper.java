package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.model.Attachment;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface AttachmentMapper {
    int deleteByPrimaryKey(String attachmentId) throws SQLException;

    int insert(Attachment record) throws SQLException;

    int insertSelective(Attachment record) throws SQLException;

    Attachment selectByPrimaryKey(String attachmentId) throws SQLException;

    int updateByPrimaryKeySelective(Attachment record) throws SQLException;

    int updateByPrimaryKey(Attachment record) throws SQLException;

    /**
     * 根据邮件id或规则id获取附件
     * @param mailId
     * @param ruleId
     * @return
     * @throws SQLException
     */
    List<Attachment> listAttachment(@Param("mailId") String mailId, @Param("ruleId") String ruleId) throws SQLException;

    /**
     * 根据附件名字及邮件id或规则id获取附件
     * @param type
     * @param id
     * @param attachmentName
     * @return
     */
    Attachment getAttachmentByName(@Param("type") String type, @Param("id") String id, @Param("attachmentName") String attachmentName);
}