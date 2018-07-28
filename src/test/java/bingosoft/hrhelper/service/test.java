package bingosoft.hrhelper.service;

import bingosoft.hrhelper.mapper.UserMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @创建人 chenwx
 * @功能描述
 * @创建时间 2018-07-26 12:27:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class test {

    @Test
    public void test(){
        System.out.print("这是单元测试");
    }
}
