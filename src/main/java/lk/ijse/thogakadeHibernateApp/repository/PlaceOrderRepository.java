package lk.ijse.thogakadeHibernateApp.repository;

import lk.ijse.thogakadeHibernateApp.config.SessionFactoryConfig;
import lk.ijse.thogakadeHibernateApp.entity.Customer;
import lk.ijse.thogakadeHibernateApp.entity.Item;
import lk.ijse.thogakadeHibernateApp.entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PlaceOrderRepository {

    private final Session session;

    public PlaceOrderRepository() {
        session = SessionFactoryConfig.getInstance().getSession();
    }

    public List<Integer> getAllCustomerIds() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
        Root<Customer> root = query.from(Customer.class);
        query.select(root.get("id"));
        return session.createQuery(query).getResultList();
    }

    public List<String> getAllItemIds() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
        Root<Item> root = query.from(Item.class);
        query.select(root.get("code"));
        return session.createQuery(query).getResultList();
    }


    public String getCustomerName(int customerId) {
        Customer customer = session.get(Customer.class, customerId);
        if (customer != null) {
            return customer.getNameIdentifier().getFullName();
        }
        return "";
    }

    public Item getItemData(String itemCode) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Item> query = criteriaBuilder.createQuery(Item.class);
        Root<Item> root = query.from(Item.class);
        query.where(criteriaBuilder.equal(root.get("code"), itemCode));
        return session.createQuery(query).uniqueResult();
    }

    public boolean saveOrder(int customerId, String itemCode, int quantity, double total) {
        Transaction transaction = session.beginTransaction();
        try {
            Customer customer = session.load(Customer.class, customerId);
            Item item = getItemData(itemCode);

            if (customer != null && item != null) {
                Order order = new Order();
                order.setCustomer(customer);
                order.setItem(item);
                order.setOrderQty(quantity);
                order.setTotalPrice(total);
                session.save(order);

                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
