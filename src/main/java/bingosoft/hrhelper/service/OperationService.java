package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.CurrentUser;
import bingosoft.hrhelper.mapper.OperationMapper;
import bingosoft.hrhelper.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * 业务
 *
 * @author cc
 * @date 2018-08-06 20:18:18
 */
@Service
public class OperationService {

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
}
