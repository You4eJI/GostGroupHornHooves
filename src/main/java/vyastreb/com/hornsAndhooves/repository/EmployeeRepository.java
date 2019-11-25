package vyastreb.com.hornsAndhooves.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vyastreb.com.hornsAndhooves.domain.Employee;

@Repository
public interface  EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {
}
