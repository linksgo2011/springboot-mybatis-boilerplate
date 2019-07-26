package boilerplate.controller;

import boilerplate.dao.UserMapper;
import boilerplate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.instrument.Instrumentation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    public static final int UNIT = 1000;

    @Autowired
    private UserMapper userMapper;

    // 方案1 一次性查询
    @GetMapping("/replication")
    public Map testReplication() {
        Map map = new HashMap();
        map.put("startTime", System.currentTimeMillis());
        List<User> users = userMapper.findAllByTaskId(1024);
        map.put("usersCount", users.size());
        map.put("endTime", System.currentTimeMillis());
        map.put("timeConsuming", (long) map.get("endTime") - (long) map.get("startTime"));
        return map;
    }

    // 方案2 分页查询
    @GetMapping("/replication2")
    public Map testReplication2() {
        Map map = new HashMap();
        map.put("startTime", System.currentTimeMillis());
        int count = userMapper.countAllByTaskId(1024);
        double pages = Math.ceil(count / UNIT);
        for (int i = 0; i < pages; i++) {
            List<User> users = userMapper.findAllByTaskIdAndPage(1024, i * UNIT, UNIT);
            System.out.println(users);
        }
        map.put("endTime", System.currentTimeMillis());
        map.put("timeConsuming", (long) map.get("endTime") - (long) map.get("startTime"));
        return map;
    }

    // 方案3
    // insert into user_temp(id,phone_nbr,open_id,project_from,task_id) select id,phone_nbr,open_id,project_from,task_id from user where task_id=1024
    @GetMapping("/replication3")
    public Map testReplication3() {
        Map map = new HashMap();
        map.put("startTime", System.currentTimeMillis());
        userMapper.replicateDataByTaskId(1024);
        map.put("endTime", System.currentTimeMillis());
        map.put("timeConsuming", (long) map.get("endTime") - (long) map.get("startTime"));
        return map;
    }

}
