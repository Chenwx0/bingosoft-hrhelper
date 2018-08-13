package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 chenwx
 * @功能描述 员工信息业务控制类
 * @创建时间 2018-08-13 18:06:06
 */
@RestController
@RequestMapping("employee")
public class EmployeeController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeService employeeService;

    /**
     * 获取员工邮件地址列表
     * @param employeeName
     * @return 查询结果
     */
    @GetMapping("/listEmail")
    public Result getEmailList(String employeeName){
        Result result = employeeService.getEmailList(employeeName);
        return result;
    }
}
