package us.superkill.mean_machine.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
     
	private static final Logger log = LogManager.getLogger(HibernateUtil.class);
    //Annotation based configuration
    private static SessionFactory sessionFactory;
    
    private static SessionFactory buildSessionFactory() {
    	try {
    		Configuration configuration = new Configuration();
    		configuration.configure("hibernate.cfg.xml");
    		log.debug("Hibernate Annotation Configuration loaded");
    		
//    		configuration.addClass(us.superkill.mean_machine.facebook.Friend.class);
    		ServiceRegistry serviceRegistry = 
    				new StandardServiceRegistryBuilder()
    					.configure()
    					.build();
    		log.debug("Hibernate Annotation serviceRegistry created");
    		
    		SessionFactory sessionFactory = 
    				new MetadataSources(serviceRegistry)
    					.buildMetadata()
    					.buildSessionFactory();
    		return sessionFactory;
    	} catch (Throwable ex) {
    		log.error("Initial SessionFactory creation failed." + ex);
    		throw new ExceptionInInitializerError(ex);
    	}
    }
    
    public static SessionFactory getSessionFactory() {
    	if (sessionFactory == null) {
    		sessionFactory = buildSessionFactory();
    	}
    	return sessionFactory;
    }
}
