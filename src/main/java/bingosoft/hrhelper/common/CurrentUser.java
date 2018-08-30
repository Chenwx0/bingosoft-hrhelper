package bingosoft.hrhelper.common;

import bingosoft.hrhelper.model.User;

/**
 * @创建人 chenwx
 * @功能描述
 * @创建时间 2018-08-04 16:04:04
 */
public class CurrentUser {

    public static String getUserId(){
        return "1";
    }

    public static User getCurrentUser(){
        User user = new User();
        user.setId("1");
        user.setIsAdmin(1);
        return user;
    }
}
