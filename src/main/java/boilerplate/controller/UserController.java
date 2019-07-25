package boilerplate.controller;

import boilerplate.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/users")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    public String testReplication() {
        userMapper.findAllByTaskId(1024);
        return "hello";
    }

}
