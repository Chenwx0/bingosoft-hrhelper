package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 chenwx
 * @功能描述 用户业务控制类
 * @创建时间 2018-08-19 14:39:39
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 获取业务负责人列表
     * @return
     */
    @GetMapping("/listPrincipal")
    public Result listPrincipal(){

        Result result = userService.listPrincipal();
        return result;
    }
}
