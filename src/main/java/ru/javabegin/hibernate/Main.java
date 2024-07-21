package ru.javabegin.hibernate;

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
        Query<Long> query = session
                .createQuery("select count(*) " +
                        "from User u " +
                        "where u.email like :email", Long.class);
        query.setParameter("email", "%mail%");
        Long count = query.getSingleResult();

        // Если использовать импорт из JPQL
//        Query query = session.createQuery("from User u where u.id = :id", User.class);
//        query.setParameter("id", 10030L);
//        User user = (User) query.getSingleResult();

        log.info("Users count: " + count);

        session.close();// закрыть сессию

        HibernateUtil.close(); // закрыть Session Factory
    }

}
