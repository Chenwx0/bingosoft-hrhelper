package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import bingosoft.hrhelper.form.UserForm;
import bingosoft.hrhelper.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * @创建人 chenwx
 * @功能描述 用户业务服务类
 * @创建时间 2018-08-19 14:54:54
 */
@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserMapper userMapper;

    /**
     * 获取业务负责人列表
     * @return
     */
    public Result<List<UserForm>> listPrincipal(){

        Result<List<UserForm>> result = new Result<>();

        try {
            List<UserForm> list = userMapper.listPrincipal();
            result.setResultEntity(list);
        } catch (SQLException e) {
            logger.error(TipMessage.QUERY_FAIL, e);
            result.setSuccess(false);
            result.setMessage(TipMessage.QUERY_FAIL);
        }

        return result;
    }
}
