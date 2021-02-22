import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateConn {
    public static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        //sessionFactory = null;
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure("META-INF/hibernate.cfg.xml");
                configuration.addAnnotatedClass(Question.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                return sessionFactory;
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

}
