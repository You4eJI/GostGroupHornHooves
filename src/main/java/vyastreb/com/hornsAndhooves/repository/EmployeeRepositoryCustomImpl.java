package vyastreb.com.hornsAndhooves.repository;

import vyastreb.com.hornsAndhooves.domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Employee> findEmployeesByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

        Root<Employee> employeeRoot = cq.from(Employee.class);

        cq.select(employeeRoot).where(cb.equal(employeeRoot.get("fullName"), name));
        TypedQuery<Employee> query = em.createQuery(cq);

        List<Employee> employees = query.getResultList();

        return employees;
    }

    @Override
    public List<Employee> findEmployeesByDepartment(String department) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

        Root<Employee> employeeRoot = cq.from(Employee.class);

        cq.select(employeeRoot).where(cb.equal(employeeRoot.get("department"), department));
        TypedQuery<Employee> query = em.createQuery(cq);

        List<Employee> employees = query.getResultList();

        return employees;
    }
}
