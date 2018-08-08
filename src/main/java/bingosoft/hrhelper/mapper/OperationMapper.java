package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.form.OperationMenuForm;
import bingosoft.hrhelper.model.Mail;
import bingosoft.hrhelper.model.Operation;

import java.util.List;

public interface OperationMapper {
    int deleteByPrimaryKey(String id);

    int insert(Operation record);

    int insertSelective(Operation record);

    Operation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Operation record);

    int updateByPrimaryKey(Operation record);

    List<OperationMenuForm> getOperationMenu(String userId);
}