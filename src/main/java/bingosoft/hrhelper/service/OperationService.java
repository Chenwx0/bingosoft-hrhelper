package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.CurrentUser;
import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import bingosoft.hrhelper.form.OperationMenuForm;
import bingosoft.hrhelper.mapper.OperationMapper;
import bingosoft.hrhelper.model.Mail;
import bingosoft.hrhelper.model.Operation;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 业务
 *
 * @author cc
 * @date 2018-08-06 20:18:18
 */
@Service
public class OperationService {

    public static final String USER_ID_NULL = "用户ID不能为空";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OperationMapper operationMapper;

    //添加新业务
    public void addOperation(Operation operation){
        operation.setId(UUID.randomUUID().toString());
        operation.setCreateBy(CurrentUser.getUserId());
        operation.setCreateTime(new Date());

        operationMapper.insert(operation);
    }

    //更新业务
    public void updateOperation(Operation operation){
        operation.setUpdateBy(CurrentUser.getUserId());
        operation.setCreateTime(new Date());

        operationMapper.updateByPrimaryKey(operation);
    }

    //删除业务
    public void deleteOperation(Operation operation){
        operationMapper.deleteByPrimaryKey(operation.getId());
    }

    /**
     * 获取业务菜单
     * @param userId
     * @return 业务信息集合
     */
    public Result<List<OperationMenuForm>> getOperationMenu(String userId){
        Result<List<OperationMenuForm>> result = new Result<>();

        // 参数校验
        if (userId == null || userId.length()==0){
            result.setSuccess(false);
            result.setMessage(USER_ID_NULL);
            return result;
        }

        try{
            List<OperationMenuForm> operations = operationMapper.getOperationMenu(userId);
            result.setResultEntity(operations);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(TipMessage.QUERY_FAIL);
            logger.error(TipMessage.QUERY_FAIL,e);
        }
        return result;
    }
}
