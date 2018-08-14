package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.CurrentUser;
import bingosoft.hrhelper.mapper.ModelMapper;
import bingosoft.hrhelper.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
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

    //添加邮件模板
    public void addModel(Model model){
        model.setId(UUID.randomUUID().toString());
        model.setCreateBy(CurrentUser.getUserId());
        model.setCreateTime(new Date());

        try {
            modelMapper.insert(model);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    //更新邮件模板
    public void updateModel(Model model){
        model.setUpdateBy(CurrentUser.getUserId());
        model.setUpdateTime(new Date());

        modelMapper.updateByPrimaryKey(model);
    }

    //删除邮件模板
    public void deleteModel(Model model){
        modelMapper.deleteByPrimaryKey(model.getId());
    }

    //查看邮件模板（待定）

}
