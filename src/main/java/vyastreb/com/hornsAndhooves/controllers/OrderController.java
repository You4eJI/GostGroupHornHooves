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
import vyastreb.com.hornsAndhooves.exceptions.NoSuchEmployeeException;
import vyastreb.com.hornsAndhooves.exceptions.NoSuchFurnitureException;
import vyastreb.com.hornsAndhooves.exceptions.NoSuchOrderException;
import vyastreb.com.hornsAndhooves.furnitures.Furniture;
import vyastreb.com.hornsAndhooves.repository.EmployeeRepository;
import vyastreb.com.hornsAndhooves.repository.OrderRepository;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderRepository orderRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    ResponseEntity<Order> createOrder(@RequestBody Order order) {
        if (!isFurnitureCorrect(order.getFurniture())) {
            throw new NoSuchFurnitureException("There is no such furniture");
        }

        if (order.getEmployeeName() != null) {
            assignEmployee(order);
        } else {
            String department = autoDefineDepartment(order.getFurniture());
            order.setDepartment(department);
            assignDepartment(order);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.save(order));
    }

    @GetMapping
    List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    Order getOrderById(@PathVariable Long id) {
        if (!doesOrderExist(id))
            throw new NoSuchOrderException("There is no such an order");
        return orderRepository.findById(id).get();
    }

    @GetMapping("/department/{name}")
    List<Order> getOrdersByDepartment(@PathVariable String name) {
        return orderRepository.findOrdersByDepartment(name);
    }

    @GetMapping("employee/{name}")
    List<Order> getOrdersByEmployee(@PathVariable String name) {
        return orderRepository.findOrdersByEmployee(name);
    }

    @GetMapping("/time/{id}")
    String gettimeLeft(@PathVariable Long id) {
        if (!doesOrderExist(id))
            throw new NoSuchOrderException("There is no such an order");

        Date currentDate = new Date();
        Order order = orderRepository.findById(id).get();
        Date orderDate = order.getDueDate();
        long diffInMillis = orderDate.getTime() - currentDate.getTime();
        long diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        return diff + " days";
    }

    @GetMapping("/unfinished")
    List<Order> getUnfinishedOrders() {
        Date date = new Date();
        return orderRepository.findOrdersByDate(date);
    }


    @PutMapping("/{id}")
    ResponseEntity<Order> updateOrder(@RequestBody Order order, @PathVariable Long id) {
        if (!doesOrderExist(id))
            throw new NoSuchOrderException("There is no such an order");
        if (!isFurnitureCorrect(order.getFurniture()))
            throw new NoSuchFurnitureException("There is no such furniture");

        Order oldOrder = orderRepository.findById(id).get();
        oldOrder.setFurniture(order.getFurniture());
        oldOrder.setCreationDate(order.getCreationDate());
        oldOrder.setDueDate(order.getDueDate());
        if (oldOrder.getEmployeeName() != null) {
            reassignEmployee(oldOrder, order);
        } else {
            String department = autoDefineDepartment(order.getFurniture());
            order.setDepartment(department);
            reassignDepartment(oldOrder, order);
        }
        oldOrder.setName(order.getName());
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.save(oldOrder));
    }

    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable Long id) {
        if (!doesOrderExist(id))
            throw new NoSuchOrderException("There is no such an order");

        Order order = orderRepository.findById(id).get();
        if (order.getEmployeeName() != null) {
            deleteOrderFromEmployee(order);
        } else {
            deleteOrderFromDepartment(order);
        }
        orderRepository.deleteById(id);
    }

    boolean isFurnitureCorrect(String furniture) {
        List<Furniture> allFurniture = getAllFurniture();

        for (Furniture furn : allFurniture) {
            if (furn.getName().equals(furniture.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private String autoDefineDepartment(String furniture) {
        Map<String, List<Furniture>> departments = new HashMap<>();
        departments.put(OfficeFurnitureDepartment.name, OfficeFurnitureDepartment.officeFurniture);
        departments.put(SoftFurnitureDepartment.name, SoftFurnitureDepartment.softFurniture);
        departments.put(StoreSystemDepartment.name, StoreSystemDepartment.storeFurniture);

        String targetDepartment = "";
        for (String department : departments.keySet()) {
            for (Furniture furn : departments.get(department)) {
                if (furn.getName().equals(furniture)) {
                    targetDepartment = department;
                    return targetDepartment;
                }
            }
        }
        return targetDepartment;
    }

    private List<Furniture> getAllFurniture() {
        List<Furniture> allFurniture = new ArrayList<>();
        allFurniture.addAll(OfficeFurnitureDepartment.officeFurniture);
        allFurniture.addAll(SoftFurnitureDepartment.softFurniture);
        allFurniture.addAll(StoreSystemDepartment.storeFurniture);
        return allFurniture;
    }

    private void assignEmployee(Order order) {
        List<Employee> employees = employeeRepository.findEmployeesByName(order.getEmployeeName());
        if (employees.isEmpty())
            throw new NoSuchEmployeeException("There is no employee with such a name");

        Employee employee = employees.get(0);
        employee.getOrders().add(order.getName());
        employeeRepository.save(employee);
    }

    private void assignDepartment(Order order) {
        List<Employee> employees = employeeRepository.findEmployeesByDepartment(order.getDepartment());
        for (Employee employee : employees) {
            employee.getOrders().add(order.getName());
            employeeRepository.save(employee);
        }
    }

    private void reassignEmployee(Order oldOrder, Order newOrder) {
        Employee employee = employeeRepository.findEmployeesByName(oldOrder.getEmployeeName()).get(0);
        employee.getOrders().remove(oldOrder.getName());
        employee.getOrders().add(newOrder.getName());
        employeeRepository.save(employee);
    }

    private void reassignDepartment(Order oldOrder, Order newOrder) {
        List<Employee> employees = employeeRepository.findEmployeesByDepartment(oldOrder.getDepartment());
        for (Employee employee : employees) {
            employee.getOrders().remove(oldOrder.getName());
            employee.getOrders().add(newOrder.getName());
            employeeRepository.save(employee);
        }
    }

    private void deleteOrderFromEmployee(Order order) {
        Employee employee = employeeRepository.findEmployeesByName(order.getEmployeeName()).get(0);
        employee.getOrders().remove(order.getName());
        employeeRepository.save(employee);
    }

    private void deleteOrderFromDepartment(Order order) {
        String department = autoDefineDepartment(order.getFurniture());
        List<Employee> employees = employeeRepository.findEmployeesByDepartment(department);
        for (Employee employee : employees) {
            employee.getOrders().remove(order.getName());
            employeeRepository.save(employee);
        }
    }

    private boolean doesOrderExist(Long id) {
        Optional<Order> optOrder = orderRepository.findById(id);
        return optOrder.isPresent();
    }
}
