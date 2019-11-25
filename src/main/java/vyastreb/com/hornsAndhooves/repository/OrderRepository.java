package vyastreb.com.hornsAndhooves.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vyastreb.com.hornsAndhooves.domain.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    Optional<Order> findByName(String name);
}
