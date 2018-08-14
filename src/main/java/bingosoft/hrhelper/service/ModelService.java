package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.CurrentUser;
import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import bingosoft.hrhelper.form.OperationMenuForm;
import bingosoft.hrhelper.mapper.ModelMapper;
import bingosoft.hrhelper.model.Model;
import bingosoft.hrhelper.model.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 邮件模板服务类
 *
 * @author cc
 * @date 2018-08-04 15:52:52
 */
@Service
public class ModelService {

    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    ModelMapper modelMapper;

    /**
     * 新增邮件模板
     * @param model
     */
    public void addModel(Model model){
    	Result result = new Result();

        // 参数校验
        if (model == null){
            result.setSuccess(false);
            result.setMessage(TipMessage.PARAM_NULL);
        }

        try{
        	model.setId(UUID.randomUUID().toString());
            model.setCreateBy(CurrentUser.getUserId());
            model.setCreateTime(new Date());
            result.setMessage(TipMessage.CREATE_SUCCESS);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(TipMessage.CREATE_FAIL);
            logger.error(TipMessage.CREATE_FAIL,e);
        }
        

        try {
            modelMapper.insert(model);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 更新邮件模板
     * @param model
     */
    public void updateModel(Model model){
    	Result result = new Result();
        // 参数校验
        if (model == null){
            result.setSuccess(false);
            result.setMessage(TipMessage.PARAM_NULL);
        }

        try{
        	model.setUpdateBy(CurrentUser.getUserId());
            model.setUpdateTime(new Date());
            modelMapper.updateByPrimaryKey(model);
            result.setMessage(TipMessage.UPDATE_SUCCESS);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(TipMessage.UPDATE_FAIL);
            logger.error(TipMessage.UPDATE_FAIL,e);
        }
    }

    /**
     * 删除邮件模板
     * @param model
     */
    public void deleteModel(Model model){
        modelMapper.deleteByPrimaryKey(model.getId());
    }

    //查看邮件模板（待定） 
}
