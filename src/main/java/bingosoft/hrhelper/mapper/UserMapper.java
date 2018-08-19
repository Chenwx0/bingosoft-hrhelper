package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.form.UserForm;
import bingosoft.hrhelper.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id) throws SQLException;

    int insert(User record) throws SQLException;

    int insertSelective(User record) throws SQLException;

    User selectByPrimaryKey(String id) throws SQLException;

    int updateByPrimaryKeySelective(User record) throws SQLException;

    int updateByPrimaryKey(User record) throws SQLException;

    /**
     * 获取业务负责人列表
     * @return
     */
    List<UserForm> listPrincipal() throws SQLException;
}