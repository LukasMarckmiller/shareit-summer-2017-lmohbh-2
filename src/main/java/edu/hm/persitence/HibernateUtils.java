package edu.hm.persitence; /*
 * (C) 2017, Lukas Marckmiller, l.marckmiller@hm.edu on 13.06.2017.
 * Java 1.8.0_121, Windows 10 Pro 64bit
 * Intel Core i5-6600K CPU/3.50GHz overclocked 4.1GHz, 4 cores, 16000 MByte RAM)
 * with IntelliJ IDEA 2017.1.1
 */

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.List;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    private static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    public static void doInsert(Serializable serializable)
    {
        Transaction transaction = null;
        Session currentSession = null;
        Object returnValue = null;
        try {
            currentSession = HibernateUtils.getSessionFactory().openSession();
            transaction = currentSession.beginTransaction();
            currentSession.persist(serializable);
            transaction.commit();
        }
        catch (HibernateException e)
        {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace(); //maybe throw exception here
        }
        finally {
            if (currentSession != null)
                currentSession.close();
        }
    }

    public static List doSelectWhere(String tableName,String filterColumn,Object value) {
        Transaction transaction = null;
        Session currentSession = null;
        List result = null;
        try {
            currentSession = HibernateUtils.getSessionFactory().openSession();
            transaction = currentSession.beginTransaction();
            String qStr = filterColumn == null
                    ? "FROM " + tableName
                    : "FROM " + tableName + " " + tableName.charAt(0) + " WHERE " + tableName.charAt(0) + "." + filterColumn + " = '" + value + '\'';
            result = currentSession.createQuery(qStr).list();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace(); //maybe throw exception here
        } finally {
            if (currentSession != null)
                currentSession.close();
        }
        return result;
    }
    public static void doUpdate(String tableName,String filterColumn,Serializable key,Serializable newEntity) {
        Transaction transaction = null;
        Session currentSession = null;
        List result = null;
        try {
            currentSession = HibernateUtils.getSessionFactory().openSession();
            transaction = currentSession.beginTransaction();
            currentSession.update(currentSession.get(newEntity.getClass(), key));
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace(); //maybe throw exception here
        } finally {
            if (currentSession != null)
                currentSession.close();
        }
    }
}
