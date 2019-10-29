package boilerplate.repository;


import boilerplate.core.CommonMapper;
import boilerplate.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends CommonMapper<User> {
}
