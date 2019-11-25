package vyastreb.com.hornsAndhooves.repository;

import vyastreb.com.hornsAndhooves.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Order> findOrdersByDate(Date date) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> orderRoot = cq.from(Order.class);

        cq.where(cb.greaterThan(orderRoot.get("dueDate"), date));
        TypedQuery<Order> query = em.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public List<Order> findOrdersByDepartment(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> orderRoot = cq.from(Order.class);

        cq.select(orderRoot).where(cb.equal(orderRoot.get("department"), name));
        TypedQuery<Order> query = em.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public List<Order> findOrdersByEmployee(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> orderRoot = cq.from(Order.class);

        cq.select(orderRoot).where(cb.equal(orderRoot.get("employeeName"), name));
        TypedQuery<Order> query = em.createQuery(cq);

        return query.getResultList();
    }
}
