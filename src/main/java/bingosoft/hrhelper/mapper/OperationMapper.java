package bingosoft.hrhelper.mapper;

import java.sql.SQLException;
import java.util.List;

import bingosoft.hrhelper.form.OperationListForm;
import bingosoft.hrhelper.form.OperationMenuForm;
import bingosoft.hrhelper.model.Operation;

public interface OperationMapper {
    int deleteByPrimaryKey(String id) throws SQLException;

    int insert(Operation record) throws SQLException;

    int insertSelective(Operation record) throws SQLException;

    Operation selectByPrimaryKey(String id) throws SQLException;

    int updateByPrimaryKeySelective(Operation record) throws SQLException;

    int updateByPrimaryKey(Operation record) throws SQLException;

	String ifSpecial(String operationId);

    /**
     * 获取业务菜单
     * @param userId
     * @return
     * @throws SQLException
     */
	List<OperationMenuForm> getOperationMenu(String userId) throws SQLException;

    /**
     * 获取业务列表
     * @return
     * @throws SQLException
     */
	List<OperationListForm> listOperation() throws SQLException;
}