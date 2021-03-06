package boilerplate.controller;

import boilerplate.entity.Employee;
import boilerplate.repository.EmployeeMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping("")
    @CrossOrigin
    public ResponseEntity<List<Employee>> getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        return ResponseEntity.ok(employeeMapper.selectAll());
    }

    @PostMapping("")
    public ResponseEntity<Employee> insert(@RequestBody Employee employee) {
        employeeMapper.insert(employee);
        return ResponseEntity.created(URI.create("/employees/" + employee.getId())).body(employee);
    }
}
