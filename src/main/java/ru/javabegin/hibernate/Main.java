package ru.javabegin.hibernate;

import jakarta.persistence.Query;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import ru.javabegin.hibernate.entity.User;

import java.util.List;

@Log4j2
public class Main {

    public static void main(String[] args) {

        log.info("Hibernate tutorial started");

        //сразу получаем готовый SessionFactory и сразу создаем сессию
        Session session = HibernateUtil.getSessionFactory().openSession();

        // JPQL -> HQL
        // тут используется универсальный синтаксис, который подойдет как для JPQL, так и HQL
        Query query = session.createQuery("from User u " +
                "where u.id = :text", User.class);
        query.setParameter("text", 10025L);
        List<User> users = query.getResultList();

        log.info("Found users: " + users.size());

        session.close();// закрыть сессию

        HibernateUtil.close(); // закрыть Session Factory
    }

}
