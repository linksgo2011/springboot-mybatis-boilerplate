package boilerplate.repository;

import boilerplate.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<Employee> selectAll();
    void insert(@Param("employee") Employee employee);
}
