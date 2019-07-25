package boilerplate.dao;

import boilerplate.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAllByTaskId(@Param("taskId") int taskId);
}
