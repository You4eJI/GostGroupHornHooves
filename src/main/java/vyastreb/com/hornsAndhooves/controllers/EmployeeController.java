package vyastreb.com.hornsAndhooves.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vyastreb.com.hornsAndhooves.departments.OfficeFurnitureDepartment;
import vyastreb.com.hornsAndhooves.departments.SoftFurnitureDepartment;
import vyastreb.com.hornsAndhooves.departments.StoreSystemDepartment;
import vyastreb.com.hornsAndhooves.domain.Employee;
import vyastreb.com.hornsAndhooves.domain.Order;
import vyastreb.com.hornsAndhooves.exceptions.NoSuchDepartmentException;
import vyastreb.com.hornsAndhooves.exceptions.NoSuchEmployeeException;
import vyastreb.com.hornsAndhooves.repository.EmployeeRepository;
import vyastreb.com.hornsAndhooves.repository.OrderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeRepository employeeRepository;
    private OrderRepository orderRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    ResponseEntity<Employee> createEmployee(@RequestBody Employee newEmployee) {
        if (!isDepartmentCorrect(newEmployee.getDepartment()))
            throw new NoSuchDepartmentException("There is no such a department");
        return ResponseEntity.status(HttpStatus.OK).body(employeeRepository.save(newEmployee));
    }

    @GetMapping("/{id}")
    Employee getEmployeeById(@PathVariable Long id) {
        if (!doesEmployeeExist(id))
            throw new NoSuchEmployeeException("There is no employee with such a name");
        return employeeRepository.findById(id).get();
    }

    @GetMapping
    List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    @PutMapping("/{id}")
    ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        if (!doesEmployeeExist(id))
            throw new NoSuchEmployeeException("There is no employee with such a name");
        if (!isDepartmentCorrect(employee.getDepartment()))
            throw new NoSuchDepartmentException("There is no such a department");

        Employee oldEmployee = employeeRepository.findById(id).get();
        oldEmployee.setDepartment(employee.getDepartment());

        if (employee.getFullName() != null) {
            oldEmployee.setFullName(employee.getFullName());

            List<String> orders = oldEmployee.getOrders();
            reassignOrders(orders, employee);
        }

        List<String> orders = employee.getOrders();
        if (orders != null) {
            reassignOrders(orders, oldEmployee);
            oldEmployee.getOrders().addAll(orders);
        }

        return ResponseEntity.status(HttpStatus.OK).body(employeeRepository.save(oldEmployee));
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id) {
        if (!doesEmployeeExist(id))
            throw new NoSuchEmployeeException("There is no employee with such a name");

        Employee employee = employeeRepository.findById(id).get();
        String department = employee.getDepartment();
        List<Employee> departmentEmployees = employeeRepository.findEmployeesByDepartment(department);
        departmentEmployees.remove(employee);

        if (departmentEmployees.isEmpty()) {
            removeLastEmployee(id);
            return;
        }

        List<String> orders = employee.getOrders();
        Random rand = new Random();
        Employee randomEmployee = departmentEmployees.get(rand.nextInt(departmentEmployees.size()));
        randomEmployee.getOrders().addAll(orders);

        reassignOrders(orders, randomEmployee);
        employeeRepository.save(randomEmployee);
        employeeRepository.deleteById(id);
    }

    private void reassignOrders(List<String> orders, Employee newEmployee) {
        for (String orderName : orders) {
            Optional<Order> foundOrder = orderRepository.findByName(orderName);
            if (foundOrder.isPresent()) {
                Order order = foundOrder.get();
                order.setEmployeeName(newEmployee.getFullName());
                orderRepository.save(order);
            }
        }
    }

    private void removeLastEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).get();
        List<String> employeeOrders = employee.getOrders();
        for (String stringOrder : employeeOrders) {
            Optional<Order> order = orderRepository.findByName(stringOrder);
            orderRepository.delete(order.get());
        }
        employeeRepository.deleteById(id);
    }

    private boolean isDepartmentCorrect(String department){
        switch (department.toLowerCase()) {
            case OfficeFurnitureDepartment.name:
            case SoftFurnitureDepartment.name:
            case StoreSystemDepartment.name:
                return true;
        }
        return false;
    }

    private boolean doesEmployeeExist(Long id) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);
        return optEmployee.isPresent();
    }
}
