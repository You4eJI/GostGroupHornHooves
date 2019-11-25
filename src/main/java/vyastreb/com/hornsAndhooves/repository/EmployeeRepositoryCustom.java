package vyastreb.com.hornsAndhooves.repository;

import vyastreb.com.hornsAndhooves.domain.Employee;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<Employee> findEmployeesByName(String name);
    List<Employee> findEmployeesByDepartment(String department);
}
