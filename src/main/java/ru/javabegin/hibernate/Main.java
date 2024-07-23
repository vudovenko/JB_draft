package ru.javabegin.hibernate;


import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import ru.javabegin.hibernate.entity.User;

@Log4j2
public class Main {

    public static void main(String[] args) {

        log.info("Hibernate tutorial started");

        //сразу получаем готовый SessionFactory и сразу создаем сессию
        Session session = HibernateUtil.getSessionFactory().openSession();

        log.debug("Statistics1:");
        log.debug("hit " + HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheHitCount());
        log.debug("miss " + HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheMissCount());
        log.debug("put " + HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCachePutCount());

        for (String s : HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheRegionNames()) {
            log.debug(s);
        }

        User u1 = session.get(User.class, 20034L);
        log.info(u1);
        session.close();// закрыть первую сессию

        log.debug("Statistics2:");
        log.debug("hit " + HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheHitCount());
        log.debug("miss " + HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheMissCount());
        log.debug("put " + HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCachePutCount());

        for (String s : HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheRegionNames()) {
            log.debug(s);
        }

        // откроем новую сессию
        session = HibernateUtil.getSessionFactory().openSession();
        User u2 = session.get(User.class, 20034L); // должен получить его из L2C
        log.info(u2);

        session.close();// закрыть сессию

        log.debug("Statistics3:");
        log.debug("hit " + HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheHitCount());
        log.debug("miss " + HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheMissCount());
        log.debug("put " + HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCachePutCount());

        for (String s : HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheRegionNames()) {
            log.debug(s);
        }

        HibernateUtil.close(); // закрыть Session Factory - очищается кеш 2го уровня
    }
}
