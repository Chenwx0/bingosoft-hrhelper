package bingosoft.hrhelper.mapper;

import java.util.List;

import bingosoft.hrhelper.form.OperationMenuForm;
import bingosoft.hrhelper.model.Operation;

public interface OperationMapper {
    int deleteByPrimaryKey(String id);

    int insert(Operation record);

    int insertSelective(Operation record);

    Operation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Operation record);

    int updateByPrimaryKey(Operation record);

	String ifSpecial(String operationId);

	List<OperationMenuForm> getOperationMenu(String userId);
}