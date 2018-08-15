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
    
   /**
    * 增加新业务
    * @param operation
    */
    public void addOperation(Operation operation){
    	Result<List<OperationMenuForm>> result = new Result<>();

        // 参数校验
        if (operation == null){
            result.setSuccess(false);
            result.setMessage(TipMessage.PARAM_NULL);
        }

        try{
        	operation.setId(UUID.randomUUID().toString());
            operation.setCreateBy(CurrentUser.getUserId());
            operation.setCreateTime(new Date());
            operationMapper.insert(operation);
            result.setMessage(TipMessage.CREATE_SUCCESS);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(TipMessage.CREATE_FAIL);
            logger.error(TipMessage.CREATE_FAIL,e);
        }
    }

    /**
     * 更新业务
     * @param operation
     */
    public void updateOperation(Operation operation){
    	Result<List<OperationMenuForm>> result = new Result<>();

        // 参数校验
        if (operation == null){
            result.setSuccess(false);
            result.setMessage(TipMessage.PARAM_NULL);
        }

        try{
            operation.setUpdateBy(CurrentUser.getUserId());
            operation.setCreateTime(new Date());
            operationMapper.updateByPrimaryKey(operation);
            result.setMessage(TipMessage.UPDATE_SUCCESS);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(TipMessage.UPDATE_FAIL);
            logger.error(TipMessage.UPDATE_FAIL,e);
        }
    }

    
   /**
    * 删除业务
    * @param operation
    */
    public void deleteOperation(Operation operation){
    	Result<List<OperationMenuForm>> result = new Result<>();

        // 参数校验
        if (operation == null){
            result.setSuccess(false);
            result.setMessage(TipMessage.PARAM_NULL);
        }

        try{
        	operationMapper.deleteByPrimaryKey(operation.getId());
            result.setMessage(TipMessage.DELETE_SUCCESS);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(TipMessage.DELETE_FAIL);
            logger.error(TipMessage.DELETE_FAIL,e);
        }
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
