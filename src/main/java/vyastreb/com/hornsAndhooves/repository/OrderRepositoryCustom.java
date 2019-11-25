package vyastreb.com.hornsAndhooves.repository;

import vyastreb.com.hornsAndhooves.domain.Order;

import java.util.Date;
import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findOrdersByDate(Date date);
    List<Order> findOrdersByDepartment(String name);
    List<Order> findOrdersByEmployee(String name);
}
