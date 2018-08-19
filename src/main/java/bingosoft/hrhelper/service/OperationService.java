package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.CurrentUser;
import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import bingosoft.hrhelper.form.OperationListForm;
import bingosoft.hrhelper.form.OperationMenuForm;
import bingosoft.hrhelper.mapper.MailMapper;
import bingosoft.hrhelper.mapper.OperationMapper;
import bingosoft.hrhelper.mapper.RuleMapper;
import bingosoft.hrhelper.model.Mail;
import bingosoft.hrhelper.model.Operation;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import leap.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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
    public static final String OPERATION_ID_NULL = "业务ID不能为空";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OperationMapper operationMapper;
    @Autowired
    RuleMapper ruleMapper;
    @Autowired
    MailMapper mailMapper;
    
   /**
    * 增加新业务
    * @param operation
    */
    public Result addOperation(Operation operation){

    	Result result = new Result();

        // 参数校验
        if (operation == null){
            result.setSuccess(false);
            result.setMessage(TipMessage.PARAM_NULL);
            return result;
        }

        try{
        	operation.setId(UUID.randomUUID().toString());
            operation.setCreateBy(CurrentUser.getUserId());
            operation.setCreateTime(new Date());
            operationMapper.insert(operation);
            result.setMessage(TipMessage.CREATE_SUCCESS);
        }catch (SQLException e){
            result.setSuccess(false);
            result.setMessage(TipMessage.CREATE_FAIL);
            logger.error(TipMessage.CREATE_FAIL,e);
        }

        return result;
    }

    /**
     * 更新业务
     * @param operation
     */
    public Result updateOperation(Operation operation){

    	Result result = new Result();

        // 参数校验
        if (operation == null){
            result.setSuccess(false);
            result.setMessage(OPERATION_ID_NULL);
            return result;
        }

        try{
            operation.setUpdateBy(CurrentUser.getUserId());
            operation.setUpdateTime(new Date());
            operationMapper.updateByPrimaryKeySelective(operation);
            result.setMessage(TipMessage.UPDATE_SUCCESS);
        }catch (SQLException e){
            result.setSuccess(false);
            result.setMessage(TipMessage.UPDATE_FAIL);
            logger.error(TipMessage.UPDATE_FAIL,e);
        }
        return result;
    }

    
   /**
    * 删除业务
    * @param operationId
    */
   @Transactional
    public Result deleteOperation(String operationId){
    	Result result = new Result();

        // 参数校验
        if (Strings.isEmpty(operationId)){
            result.setSuccess(false);
            result.setMessage(OPERATION_ID_NULL);
            return result;
        }

        try{
            mailMapper.deleteByOperationId(operationId);
            ruleMapper.deleteByOperationId(operationId);
        	operationMapper.deleteByPrimaryKey(operationId);
            result.setMessage(TipMessage.DELETE_SUCCESS);
        }catch (SQLException e){
            result.setSuccess(false);
            result.setMessage(TipMessage.DELETE_FAIL);
            logger.error(TipMessage.DELETE_FAIL,e);
        }

        return result;
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

    public Result<List<OperationListForm>> listOperation(){

        Result<List<OperationListForm>> result = new Result<>();

        try {
            List<OperationListForm> list = operationMapper.listOperation();
            if (list.size()>0){
                result.setResultEntity(list);
            }else {
                result.setSuccess(false);
                result.setMessage(TipMessage.NO_DATA);
            }
        } catch (SQLException e) {
            logger.error(TipMessage.QUERY_FAIL, e);
            result.setSuccess(false);
            result.setMessage(TipMessage.QUERY_FAIL);
        }

        return result;
    }

    /**
     * 获取业务详情
     * @param operationId
     * @return 查询结果
     */
    public Result<Operation> getOperation(String operationId){

        Result<Operation> result = new Result<>();

        //参数校验
        if (Strings.isEmpty(operationId)){
            result.setSuccess(false);
            result.setMessage(OPERATION_ID_NULL);
            return result;
        }

        try {
            Operation operation = operationMapper.selectByPrimaryKey(operationId);
            if (operation != null){
                result.setResultEntity(operation);
            }else {
                result.setSuccess(false);
                result.setMessage(TipMessage.NO_DATA);
            }
        } catch (SQLException e) {
            logger.error(TipMessage.QUERY_FAIL,e);
            result.setSuccess(false);
            result.setMessage(TipMessage.QUERY_FAIL);
        }

        return result;
    }
}
