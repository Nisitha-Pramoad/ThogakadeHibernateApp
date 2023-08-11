package lk.ijse.thogakadeHibernateApp.repository;

import lk.ijse.thogakadeHibernateApp.config.SessionFactoryConfig;
import lk.ijse.thogakadeHibernateApp.entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ItemRepository {

    private final Session session;

    public ItemRepository() {
        session = SessionFactoryConfig
                .getInstance()
                .getSession();
    }

    public boolean saveItem(Item item) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(item);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Item getItem(int code) {
        try {
            Item item = session.get(Item.class, code);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean updateItem(Item item) {
        Transaction transaction = session.beginTransaction();
        try {
            session.update(item);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteItem(Item item) {
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(item);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
