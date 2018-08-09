package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.common.CurrentUser;
import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.form.OperationMenuForm;
import bingosoft.hrhelper.model.Mail;
import bingosoft.hrhelper.model.Operation;
import bingosoft.hrhelper.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cc
 * @date 2018-08-06 20:28:28
 */
@RestController
@RequestMapping(path = "/operation") 
public class OperationController {

    @Autowired
    OperationService operationService;

    @GetMapping(path = "/addOperation")
    public void addOperation(Operation operation){
        operationService.addOperation(operation);
    }

    @GetMapping(path = "/deleteOperation")
    public void deleteOperation(Operation operation){
        operationService.deleteOperation(operation);
    }

    @GetMapping(path = "/updateOperation")
    public void updateOperation(Operation operation){
        operationService.updateOperation(operation);
    }

    /**
     * 获取业务菜单
     * @return 业务信息集合
     */
    @GetMapping("/getOperationMenu")
    public Result getOperationMenu(){
        String userId = CurrentUser.getUserId();
        Result<List<OperationMenuForm>> result = operationService.getOperationMenu(userId);
        return result;
    }
}
