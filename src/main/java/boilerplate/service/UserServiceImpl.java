package boilerplate.service;

import boilerplate.core.AbstractService;
import boilerplate.entity.User;
import boilerplate.repository.UserCommonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/10/28.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserCommonMapper userMapper;

}
