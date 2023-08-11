package lk.ijse.thogakadeHibernateApp.config;

import lk.ijse.thogakadeHibernateApp.entity.Customer;
import lk.ijse.thogakadeHibernateApp.entity.Item; // Import the Item class
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryConfig {

    private static SessionFactoryConfig factoryConfig;
    private final SessionFactory sessionFactory;

    private SessionFactoryConfig() {
        sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class) // Add Item.class here
                .buildSessionFactory();
    }

    public static SessionFactoryConfig getInstance() {
        return (null == factoryConfig)
                ? factoryConfig = new SessionFactoryConfig()
                : factoryConfig;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
