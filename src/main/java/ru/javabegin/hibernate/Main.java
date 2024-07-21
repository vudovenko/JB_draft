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

        Query<User> query = session.createQuery("from User", User.class);
        query.setMaxResults(2);

        List<User> users = query.getResultList();

        log.info(users.get(0).getRoles());

        session.close();// закрыть сессию

        HibernateUtil.close(); // закрыть Session Factory
    }

}
