package boilerplate.dao;

import boilerplate.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAllByTaskId(@Param("taskId") int taskId);
    List<User> findAllByTaskIdAndPage(
            @Param("taskId") int taskId,
            @Param("start") int start,
            @Param("limit") int limit
    );
    int countAllByTaskId(@Param("taskId") int taskId);
    void replicateDataByTaskId(@Param("taskId") int taskId);
}
