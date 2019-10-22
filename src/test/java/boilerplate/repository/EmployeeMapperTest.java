package boilerplate.repository;

import boilerplate.entity.Employee;
import boilerplate.repository.EmployeeMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@MybatisTest
public class EmployeeMapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @After
    public void tearDown() throws Exception {
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"employee");
    }

    @Test
    public void shouldFetchAllEmployees() {
        // given
        jdbcTemplate.execute("INSERT INTO EMPLOYEE VALUES(1,'zhangsan', 21);");
        // when
        List<Employee> employeeList = employeeMapper.selectAll();
        // then
        assertEquals(1,employeeList.size());
    }
}
