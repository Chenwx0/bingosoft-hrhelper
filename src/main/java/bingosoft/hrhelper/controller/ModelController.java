package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.model.Model;
import bingosoft.hrhelper.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 chenwx
 * @功能描述 模板业务控制类
 * @创建时间 2018-08-04 15:06:06
 */
@RestController
@RequestMapping(path = "/model")
@CrossOrigin
public class ModelController {

    @Autowired
    ModelService modelService;

    @GetMapping(path = "/addModel")
    public void addModel(Model model){
        modelService.addModel(model);
    }

    @GetMapping(path = "/deleteModel")
    public void deleteModel(Model model){
        modelService.deleteModel(model);
    }

    @GetMapping(path = "/updateModel")
    public void updateModel(Model model){
        modelService.updateModel(model);
    }
}
