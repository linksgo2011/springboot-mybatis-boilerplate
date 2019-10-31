package boilerplate.service;

import boilerplate.core.AbstractService;
import boilerplate.entity.Token;
import boilerplate.repository.TokenMapper;
import boilerplate.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService extends AbstractService<boilerplate.entity.User> implements boilerplate.core.Service<boilerplate.entity.User> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenMapper tokenMapper;

    public String login(String username, String password) {

        boilerplate.entity.User user = userMapper.selectOneByExample(
                new Example(User.class).createCriteria().andEqualTo("username", username)
        );

        if (null == user) {
            throw new UsernameNotFoundException("User name not found");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        if (!user.getPassword().equals(hashedPassword)) {
            throw new UsernameNotFoundException("User name not found");
        }

        String token = generateToken();
        tokenMapper.deleteByExample(new Example(Token.class).createCriteria().andEqualTo("userId", user.getId()));
        tokenMapper.insert(Token.builder().token(token).userId(user.getId()).expires(generateExpires()).build());
        return token;
    }

    public Optional<User> findByToken(String token) {
        Token tokenRecord = tokenMapper.selectByPrimaryKey(token);
        if (null == tokenRecord || tokenRecord.getExpires().getTime() > System.currentTimeMillis()) {
            return Optional.empty();
        }
        boilerplate.entity.User user = userMapper.selectByPrimaryKey(tokenRecord.getUserId());

        if (null != user) {
            User userDetail = new User(
                    user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList("USER")
            );

            return Optional.of(userDetail);
        }
        return Optional.empty();
    }

    private Timestamp generateExpires() {
        return new Timestamp(System.currentTimeMillis() + 7 * 24 * 60 * 60);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
