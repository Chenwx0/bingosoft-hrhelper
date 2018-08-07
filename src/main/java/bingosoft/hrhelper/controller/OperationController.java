package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.model.Operation;
import bingosoft.hrhelper.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
