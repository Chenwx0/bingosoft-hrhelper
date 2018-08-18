package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.model.Attachment;

public interface AttachmentMapper {
    int deleteByPrimaryKey(String attachmentId);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    Attachment selectByPrimaryKey(String attachmentId);

    int updateByPrimaryKeySelective(Attachment record);

    int updateByPrimaryKey(Attachment record);
}