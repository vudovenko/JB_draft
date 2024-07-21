package ru.javabegin.hibernate;

import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.query.Query;
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

        // 2 варианта
        Query<User> query = session
                .createQuery("select new User(u.email, u.username) " +
                        "from User u " +
                        "where u.id = :id", User.class);
        query.setParameter("id", 10037L);
        User user = query.getSingleResult();

        log.info("User with 2 values: " + user);

        Query<Object[]> query1 = session
                .createQuery("select u.email, u.username " +
                        "from User u " +
                        "where u.id = :id", Object[].class);
        query1.setParameter("id", 10037L);
        Object[] userObject = query1.getSingleResult();

        log.info("User with 2 values: " + userObject[0] + " " + userObject[1]);

        session.close();// закрыть сессию

        HibernateUtil.close(); // закрыть Session Factory
    }

}
