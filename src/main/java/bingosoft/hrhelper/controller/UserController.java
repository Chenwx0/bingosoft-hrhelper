package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.mapper.UserMapper;
import bingosoft.hrhelper.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @创建人 chenwx
 * @功能描述
 * @创建时间 2018-07-20 10:54:54
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;
    @GetMapping(path = "/create")
    public int createUser(User user){
        user.setId(UUID.randomUUID().toString());
        user.setFirstName("测试");
        user.setLastName("一");
        int result = userMapper.insert(user);
        return result;
    };

}
