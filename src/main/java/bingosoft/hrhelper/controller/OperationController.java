package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.common.CurrentUser;
import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.form.OperationMenuForm;
import bingosoft.hrhelper.model.Mail;
import bingosoft.hrhelper.model.Operation;
import bingosoft.hrhelper.service.OperationService;
import leap.web.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author cc
 * @date 2018-08-06 20:28:28
 */
@RestController
@RequestMapping(path = "operation")
@CrossOrigin
public class OperationController {

    @Autowired
    OperationService operationService;

    @PostMapping("/add")
    public Result addOperation(@RequestBody Operation operation){

        Result result = operationService.addOperation(operation);
        return result;
    }

    @DeleteMapping("/del")
    public Result deleteOperation(String operationId){

        Result result = operationService.deleteOperation(operationId);
        return result;
    }

    @PatchMapping("/update")
    public Result updateOperation(@RequestBody Operation operation){

        Result result = operationService.updateOperation(operation);
        return result;
    }

    /**
     * 获取业务列表
     * @return
     */
    @GetMapping("/list")
    public Result getOperation(){

        Result result = operationService.listOperation();
        return result;
    }

    /**
     * 获取业务详情
     * @param operationId
     * @return
     */
    @GetMapping("/get")
    public Result getOperation(String operationId){

        Result result = operationService.getOperation(operationId);
        return result;
    }

    /**
     * 获取业务菜单
     * @return 业务信息集合
     */
    @GetMapping("/getOperationMenu")
    public Result getOperationMenu(HttpServletResponse response){
        String userId = CurrentUser.getUserId();
        Result<List<OperationMenuForm>> result = operationService.getOperationMenu(userId);
        return result;
    }
}
