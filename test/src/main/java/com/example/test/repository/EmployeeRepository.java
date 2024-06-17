package com.example.test.repository;

import com.example.test.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class EmployeeMapper implements RowMapper<Employee> {
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setFullName(rs.getString("full_name"));
            employee.setBirthday(rs.getString("birthday"));
            employee.setAddress(rs.getString("address"));
            employee.setPosition(rs.getString("position"));
            employee.setDepartment(rs.getString("department"));
            return employee;
        }
    }

    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM employees", new EmployeeMapper());
    }

    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new EmployeeMapper()).stream().findFirst();
    }

    public void save(Employee employee) {
        if (employee.getId() == null || employee.getId() == 0) {
            jdbcTemplate.update(
                    "INSERT INTO employees (full_name, birthday, address, position, department) VALUES (?, ?, ?, ?, ?)",
                    employee.getFullName(), employee.getBirthday(), employee.getAddress(), employee.getPosition(), employee.getDepartment());
        } else {
            jdbcTemplate.update(
                    "UPDATE employees SET full_name = ?, birthday = ?, address = ?, position = ?, department = ? WHERE id = ?",
                    employee.getFullName(), employee.getBirthday(), employee.getAddress(), employee.getPosition(), employee.getDepartment(), employee.getId());
        }
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM employees WHERE id = ?", id);
    }
}
