package br.ufba.roomsmanager.dao;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Hibernate
{
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    static
    {
        try
        {
//          Configuration configuration = new Configuration();
            Configuration configuration = new Configuration().configure();

            serviceRegistry = (ServiceRegistry) new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        catch (HibernateException he)
        {
            System.err.println("Error creating Session: " + he);
            throw new ExceptionInInitializerError(he);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    } 
}