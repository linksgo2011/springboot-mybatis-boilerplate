package boilerplate.repository;


import boilerplate.core.CommonMapper;
import boilerplate.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCommonMapper extends CommonMapper<User> {
}
